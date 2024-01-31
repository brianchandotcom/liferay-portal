/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.company.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.db.DBPartitionDB;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.impl.CompanyLocalServiceImpl;
import com.liferay.portal.service.impl.ResourceActionLocalServiceImpl;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.util.PortalInstances;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class CompanyLocalServiceDBPartitionTest
	extends BaseDBPartitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();

		_defaultCompanyId = PortalInstances.getDefaultCompanyId();

		_resourceActions = ReflectionTestUtil.getFieldValue(
			ResourceActionLocalServiceImpl.class, "_resourceActions");

		_regenerateResourceActions();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		disableDBPartition();

		_regenerateResourceActions();
	}

	@Before
	public void setUp() throws Exception {
		_scheduleJob(_defaultCompanyId, _JOB_NAME);
		_scheduleJob(_defaultCompanyId, _JOB_NAME + "test");
	}

	@After
	public void tearDown() throws Exception {
		_schedulerEngine.delete(_JOB_GROUP_NAME, StorageType.PERSISTED);
	}

	@Test
	public void testAddCompany() throws Exception {
		int dbPartitionsCount = _getDBPartitionsCount();

		_company = CompanyTestUtil.addCompany();

		Assert.assertTrue(
			ArrayUtil.contains(
				PortalInstances.getCompanyIdsBySQL(), _company.getCompanyId()));

		Assert.assertEquals(dbPartitionsCount + 1, _getDBPartitionsCount());
	}

	@Test
	public void testAddCompanyWhenCompanyLocalServiceFails() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();
		int dbPartitionsCount = _getDBPartitionsCount();

		Company company = null;

		AopInvocationHandler aopInvocationHandler =
			ProxyUtil.fetchInvocationHandler(
				_companyLocalService, AopInvocationHandler.class);

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					(CompanyLocalServiceImpl)aopInvocationHandler.getTarget(),
					"_dlFileEntryTypeLocalService", null)) {

			company = _companyLocalService.addCompany(
				RandomTestUtil.randomLong(), "test.com", "test.com", "test.com",
				0, true, null, null, null, null, null, null);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertArrayEquals(
				companyIds, PortalInstances.getCompanyIdsBySQL());
			Assert.assertEquals(dbPartitionsCount, _getDBPartitionsCount());
			Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
		}
		finally {
			if (company != null) {
				removeDBPartitions(new long[] {company.getCompanyId()});
			}
		}
	}

	@Test
	public void testAddCompanyWhenDBPartitionUtilFails() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();
		int dbPartitionsCount = _getDBPartitionsCount();

		Company company = null;

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					DBPartitionUtil.class, "_dbPartitionDB",
					ProxyUtil.newProxyInstance(
						DBPartitionDB.class.getClassLoader(),
						new Class<?>[] {DBPartitionDB.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(), "getCreateTableSQL")) {

								throw new Exception();
							}

							return method.invoke(dbPartitionDB, args);
						}))) {

			company = CompanyTestUtil.addCompany();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertArrayEquals(
				companyIds, PortalInstances.getCompanyIdsBySQL());
			Assert.assertEquals(dbPartitionsCount, _getDBPartitionsCount());
			Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
		}
		finally {
			if (company != null) {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@Test
	public void testAddDBPartitionCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		_scheduleJob(company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		_companyLocalService.extractDBPartitionCompany(company.getCompanyId());

		Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
		Assert.assertEquals(1, _getJobsCount(company.getCompanyId()));

		String name = "new" + company.getName();
		String virtualHostName = "new" + company.getVirtualHostname();
		String webId = "new" + company.getWebId();

		boolean standaloneDBPartition = true;

		try {
			company = _companyLocalService.addDBPartitionCompany(
				company.getCompanyId(), name, virtualHostName, webId);

			standaloneDBPartition = false;

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			Assert.assertTrue(
				ArrayUtil.contains(companyIds, company.getCompanyId()));

			Assert.assertEquals(name, company.getName());
			Assert.assertEquals(virtualHostName, company.getVirtualHostname());
			Assert.assertEquals(webId, company.getWebId());

			Assert.assertEquals(
				_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {company.getCompanyId()});
			}
			else {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@Test
	public void testAddDBPartitionCompanyWhenCompanyLocalServiceFails()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		_scheduleJob(company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		boolean standaloneDBPartition = false;

		try {
			_companyLocalService.extractDBPartitionCompany(
				company.getCompanyId());

			standaloneDBPartition = true;

			Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
			Assert.assertEquals(1, _getJobsCount(company.getCompanyId()));

			Company defaultCompany = _companyLocalService.getCompany(
				PortalInstances.getDefaultCompanyId());

			try {
				_companyLocalService.addDBPartitionCompany(
					company.getCompanyId(), null, null,
					defaultCompany.getWebId());

				standaloneDBPartition = false;

				Assert.fail();
			}
			catch (PortalException portalException) {
				long[] companyIds = PortalInstances.getCompanyIdsBySQL();

				Assert.assertFalse(
					ArrayUtil.contains(companyIds, company.getCompanyId()));

				Assert.assertEquals(
					_JOB_COUNT, _getJobsCount(_defaultCompanyId));

				_checkStandaloneDBPartitionTables(
					company.getCompanyId(), "Company", "VirtualHost");
			}
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {company.getCompanyId()});
			}
			else {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@Test
	public void testAddDBPartitionCompanyWhenDBPartitionUtilFails()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		_scheduleJob(company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		boolean standaloneDBPartition = false;

		try {
			_companyLocalService.extractDBPartitionCompany(
				company.getCompanyId());

			standaloneDBPartition = true;

			Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
			Assert.assertEquals(1, _getJobsCount(company.getCompanyId()));

			try (AutoCloseable autoCloseable =
					ReflectionTestUtil.setFieldValueWithAutoCloseable(
						DBPartitionUtil.class, "_dbPartitionDB",
						ProxyUtil.newProxyInstance(
							DBPartitionDB.class.getClassLoader(),
							new Class<?>[] {DBPartitionDB.class},
							(proxy, method, args) -> {
								if (Objects.equals(
										method.getName(), "getCreateViewSQL") &&
									StringUtil.equalsIgnoreCase(
										(String)args[2], "VirtualHost")) {

									throw new Exception();
								}

								return method.invoke(dbPartitionDB, args);
							}))) {

				company = _companyLocalService.addDBPartitionCompany(
					company.getCompanyId(), null, null, null);

				standaloneDBPartition = false;

				Assert.fail();
			}
			catch (PortalException portalException) {
				long[] companyIds = PortalInstances.getCompanyIdsBySQL();

				Assert.assertFalse(
					ArrayUtil.contains(companyIds, company.getCompanyId()));

				Assert.assertEquals(
					_JOB_COUNT, _getJobsCount(_defaultCompanyId));

				_checkStandaloneDBPartitionTables(
					company.getCompanyId(), "Company", "VirtualHost");
			}
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {company.getCompanyId()});
			}
			else {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@Test
	public void testDeleteCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		_scheduleJob(company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		int dbPartitionsCount = _getDBPartitionsCount();

		_companyLocalService.deleteCompany(company);

		Assert.assertFalse(
			ArrayUtil.contains(
				PortalInstances.getCompanyIdsBySQL(), company.getCompanyId()));
		Assert.assertEquals(dbPartitionsCount - 1, _getDBPartitionsCount());
		Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
	}

	@Test
	public void testDeleteCompanyWhenDBPartitionUtilFails() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_scheduleJob(_company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					DBPartitionUtil.class, "_dbPartitionDB",
					ProxyUtil.newProxyInstance(
						DBPartitionDB.class.getClassLoader(),
						new Class<?>[] {DBPartitionDB.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(), "getDropPartitionSQL")) {

								throw new Exception();
							}

							return method.invoke(dbPartitionDB, args);
						}))) {

			_companyLocalService.deleteCompany(_company);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(
				ArrayUtil.contains(
					PortalInstances.getCompanyIdsBySQL(),
					_company.getCompanyId()));

			Assert.assertEquals(
				_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));
		}
	}

	@Test
	public void testExtractDBPartitionCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		_scheduleJob(company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		boolean standaloneDBPartition = false;

		try {
			_companyLocalService.extractDBPartitionCompany(
				company.getCompanyId());

			Assert.assertFalse(
				ArrayUtil.contains(
					PortalInstances.getCompanyIdsBySQL(),
					company.getCompanyId()));

			standaloneDBPartition = true;

			Assert.assertEquals(_JOB_COUNT, _getJobsCount(_defaultCompanyId));
			Assert.assertEquals(1, _getJobsCount(company.getCompanyId()));

			_checkStandaloneDBPartitionTables(
				company.getCompanyId(), "Company", "VirtualHost");
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {company.getCompanyId()});
			}
			else {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@Test
	public void testExtractDBPartitionCompanyWhenDBPartitionUtilFails()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		_scheduleJob(company.getCompanyId(), _JOB_NAME);

		Assert.assertEquals(_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));

		int tablesCount = _getTablesCount(company.getCompanyId());
		int viewsCount = _getViewsCount(company.getCompanyId());

		boolean standaloneDBPartition = false;

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					DBPartitionUtil.class, "_dbPartitionDB",
					ProxyUtil.newProxyInstance(
						DBPartitionDB.class.getClassLoader(),
						new Class<?>[] {DBPartitionDB.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(), "getCreateTableSQL") &&
								StringUtil.equalsIgnoreCase(
									(String)args[2], "VirtualHost")) {

								throw new Exception();
							}

							return method.invoke(dbPartitionDB, args);
						}))) {

			_companyLocalService.extractDBPartitionCompany(
				company.getCompanyId());

			standaloneDBPartition = true;

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				tablesCount, _getTablesCount(company.getCompanyId()));
			Assert.assertEquals(
				viewsCount, _getViewsCount(company.getCompanyId()));
			Assert.assertTrue(
				ArrayUtil.contains(
					PortalInstances.getCompanyIdsBySQL(),
					company.getCompanyId()));
			Assert.assertEquals(
				_JOB_COUNT + 1, _getJobsCount(_defaultCompanyId));
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {company.getCompanyId()});
			}
			else {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	private static void _regenerateResourceActions() throws Exception {
		_resourceActions.clear();

		DBPartitionUtil.forEachCompanyId(
			companyId -> _resourceActionLocalService.checkResourceActions());
	}

	private void _checkStandaloneDBPartitionTables(
			long companyId, String... expectedTableNames)
		throws Exception {

		List<String> tableNames = new ArrayList<>();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getTables(
				dbPartitionDB.getCatalog(
					connection, getPartitionName(companyId)),
				dbPartitionDB.getSchema(
					connection, getPartitionName(companyId)),
				null, new String[] {"TABLE"})) {

			while (resultSet.next()) {
				tableNames.add(
					StringUtil.toUpperCase(resultSet.getString("TABLE_NAME")));
			}
		}

		for (String expectedTableName : expectedTableNames) {
			Assert.assertTrue(
				tableNames.contains(StringUtil.toUpperCase(expectedTableName)));
		}
	}

	private int _getDBPartitionsCount() throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getSchemas()) {
			if (resultSet.last()) {
				return resultSet.getRow();
			}
		}

		try (ResultSet resultSet = databaseMetaData.getCatalogs()) {
			while (resultSet.last()) {
				return resultSet.getRow();
			}
		}

		throw new SQLException("At least one database partition is required");
	}

	private int _getJobsCount(long companyId) throws Exception {
		String partitionName = getPartitionName(companyId);

		if (companyId == _defaultCompanyId) {
			partitionName = dbPartitionDB.getDefaultPartitionName(connection);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(1) from ", partitionName,
					".QUARTZ_JOB_DETAILS"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		throw new Exception("Table does not exist");
	}

	private List<String> _getObjectNames(String objectType, long companyId)
		throws Exception {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		List<String> objectNames = new ArrayList<>();

		String partitionName = getPartitionName(companyId);

		try (ResultSet resultSet = databaseMetaData.getTables(
				dbPartitionDB.getCatalog(connection, partitionName),
				dbPartitionDB.getSchema(connection, partitionName), null,
				new String[] {objectType})) {

			while (resultSet.next()) {
				objectNames.add(resultSet.getString("TABLE_NAME"));
			}
		}

		return objectNames;
	}

	private int _getTablesCount(long companyId) throws Exception {
		List<String> tableNames = _getObjectNames("TABLE", companyId);

		return tableNames.size();
	}

	private int _getViewsCount(long companyId) throws Exception {
		List<String> viewNames = _getObjectNames("VIEW", companyId);

		return viewNames.size();
	}

	private void _scheduleJob(long companyId, String jobName) throws Exception {
		Trigger trigger = _triggerFactory.createTrigger(
			StringBundler.concat(jobName, StringPool.AT, companyId),
			_JOB_GROUP_NAME, null, null, 1, TimeUnit.DAY);

		_schedulerEngine.schedule(
			trigger, StringPool.BLANK, _JOB_GROUP_NAME, new Message(),
			StorageType.PERSISTED);
	}

	private static final int _JOB_COUNT = 2;

	private static final String _JOB_GROUP_NAME = "liferay/test";

	private static final String _JOB_NAME = "test";

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static long _defaultCompanyId;

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	private static Map<String, ResourceAction> _resourceActions;

	@Inject(
		filter = "component.name=com.liferay.portal.scheduler.quartz.internal.QuartzSchedulerEngine"
	)
	private static SchedulerEngine _schedulerEngine;

	@Inject(
		filter = "component.name=com.liferay.portal.scheduler.quartz.internal.QuartzTriggerFactory"
	)
	private static TriggerFactory _triggerFactory;

	@DeleteAfterTestRun
	private Company _company;

}