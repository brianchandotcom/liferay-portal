/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.model.DefaultModelHintsImpl;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.model.impl.ResourceActionImpl;
import com.liferay.portal.service.impl.ClassNameLocalServiceImpl;
import com.liferay.portal.service.impl.ResourceActionLocalServiceImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PortalInstances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();

		entityCache.removeCache(ClassNameImpl.class.getName());
		entityCache.removeCache(ResourceActionImpl.class.getName());

		finderCache.removeCache(ClassNameImpl.class.getName());
		finderCache.removeCache(ResourceActionImpl.class.getName());

		createControlTable(TEST_CONTROL_TABLE_NAME);

		addDBPartitions();

		_resourceActions = ReflectionTestUtil.getFieldValue(
			ResourceActionLocalServiceImpl.class, "_resourceActions");

		_resourceActions.clear();

		DBPartitionUtil.forEachCompanyId(
			companyId -> _resourceActionLocalService.checkResourceActions());

		insertPartitionRequiredData();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		deletePartitionRequiredData();

		removeDBPartitions();

		dropTable(TEST_CONTROL_TABLE_NAME);

		disableDBPartition();

		entityCache.removeCache(ClassNameImpl.class.getName());
		entityCache.removeCache(ResourceActionImpl.class.getName());

		finderCache.removeCache(ClassNameImpl.class.getName());
		finderCache.removeCache(ResourceActionImpl.class.getName());

		if (_resourceActions != null) {
			_resourceActions.clear();
		}

		DBPartitionUtil.forEachCompanyId(
			companyId -> _resourceActionLocalService.checkResourceActions());
	}

	@After
	public void tearDown() throws Exception {
		if (dbInspector.hasIndex(TEST_CONTROL_TABLE_NAME, TEST_INDEX_NAME)) {
			dropIndex(TEST_CONTROL_TABLE_NAME);
		}

		dropTable(TEST_TABLE_NAME);
	}

	@Test
	public void testAddIndexControlTable() throws Exception {
		DBPartitionUtil.forEachCompanyId(
			companyId -> createIndex(TEST_CONTROL_TABLE_NAME));

		Assert.assertTrue(
			dbInspector.hasIndex(TEST_CONTROL_TABLE_NAME, TEST_INDEX_NAME));
	}

	@Test
	public void testAddUniqueIndexControlTable() throws Exception {
		DBPartitionUtil.forEachCompanyId(
			companyId -> createUniqueIndex(TEST_CONTROL_TABLE_NAME));

		Assert.assertTrue(
			dbInspector.hasIndex(TEST_CONTROL_TABLE_NAME, TEST_INDEX_NAME));
	}

	@Test
	public void testAlterControlTable() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					StringBundler.concat(
						"alter table ", TEST_CONTROL_TABLE_NAME, " add column ",
						TEST_CONTROL_TABLE_NEW_COLUMN, " bigint")));

			Assert.assertTrue(
				dbInspector.hasColumn(
					TEST_CONTROL_TABLE_NAME, TEST_CONTROL_TABLE_NEW_COLUMN));
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					if (dbInspector.hasColumn(
							TEST_CONTROL_TABLE_NAME,
							TEST_CONTROL_TABLE_NEW_COLUMN)) {

						db.runSQL(
							StringBundler.concat(
								"alter table ", TEST_CONTROL_TABLE_NAME,
								" drop column ",
								TEST_CONTROL_TABLE_NEW_COLUMN));
					}
				});
		}
	}

	@Test
	public void testCopyClassName() throws Exception {
		String classNameValue = "";
		long classNameId = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select value, classNameId from ClassName_ order by " +
					"classNameId asc limit 1; ");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				classNameValue = resultSet.getString(1);
				classNameId = resultSet.getLong(2);
			}
		}

		ClassName nullClassName = ReflectionTestUtil.getFieldValue(
			ClassNameLocalServiceImpl.class, "_nullClassName");

		long finalClassNameId = classNameId;
		String finalClassNameValue = classNameValue;

		DBPartitionUtil.forEachCompanyId(
			companyId -> {
				ClassName className = _classNameLocalService.fetchClassName(
					finalClassNameValue);

				Assert.assertNotEquals(nullClassName, className);
				Assert.assertEquals(
					finalClassNameId, className.getClassNameId());
				Assert.assertEquals(finalClassNameValue, className.getValue());
			});
	}

	@Test
	public void testCopyResourceAction() throws Exception {
		String actionId = "";
		long bitwiseValue = 0;
		String name = "";
		long resourceActionId = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select resourceActionId, name, actionId, bitwiseValue from " +
					"ResourceAction order by resourceActionId asc limit 1;");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				actionId = resultSet.getString(3);
				bitwiseValue = resultSet.getLong(4);
				name = resultSet.getString(2);
				resourceActionId = resultSet.getLong(1);
			}
		}

		String finalActionId = actionId;
		long finalBitwiseValue = bitwiseValue;
		String finalName = name;
		long finalResourceActionId = resourceActionId;

		DBPartitionUtil.forEachCompanyId(
			companyId -> {
				ResourceAction resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						finalName, finalActionId);

				Assert.assertNotNull(resourceAction);
				Assert.assertEquals(
					finalBitwiseValue, resourceAction.getBitwiseValue());
				Assert.assertEquals(
					finalResourceActionId,
					resourceAction.getResourceActionId());
			});
	}

	@Test
	public void testDropIndexControlTable() throws Exception {
		createIndex(TEST_CONTROL_TABLE_NAME);

		DBPartitionUtil.forEachCompanyId(
			companyId -> dropIndex(TEST_CONTROL_TABLE_NAME));

		Assert.assertTrue(
			!dbInspector.hasIndex(TEST_CONTROL_TABLE_NAME, TEST_INDEX_NAME));
	}

	@Test
	public void testGetClassName() throws Exception {
		Set<ClassName> classNames = new CopyOnWriteArraySet<>();

		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> Assert.assertTrue(
					classNames.add(
						_classNameLocalService.getClassName(
							"class.name.test"))));

			Assert.assertEquals(
				classNames.toString(), _companyLocalService.getCompaniesCount(),
				classNames.size());
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> _classNameLocalService.deleteClassName(
					_classNameLocalService.fetchClassName("class.name.test")));
		}
	}

	@Test
	public void testGetResourceAction() throws Exception {
		Set<ResourceAction> resourceActions = new CopyOnWriteArraySet<>();

		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					_resourceActionLocalService.addResourceAction(
						"resource.action.test", "TEST", companyId);

					_resourceActionLocalService.checkResourceActions(
						"resource.action.test",
						Collections.singletonList("TEST"));
				});

			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					ResourceAction resourceAction =
						_resourceActionLocalService.getResourceAction(
							"resource.action.test", "TEST");

					Assert.assertTrue(resourceActions.add(resourceAction));

					Assert.assertEquals(
						(long)companyId, resourceAction.getBitwiseValue());
				});

			Assert.assertEquals(
				resourceActions.toString(),
				_companyLocalService.getCompaniesCount(),
				resourceActions.size());
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					ResourceAction resourceAction =
						_resourceActionLocalService.fetchResourceAction(
							"resource.action.test", "TEST");

					if (resourceAction != null) {
						_resourceActionLocalService.deleteResourceAction(
							resourceAction);
					}
				});
		}
	}

	@Test(expected = PortalException.class)
	public void testIllegalDatabasePartitionSchemaNamePrefix()
		throws Exception {

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					DBPartitionUtil.class,
					"_DATABASE_PARTITION_SCHEMA_NAME_PREFIX",
					"VeryLongIdentifier")) {

			DBPartitionUtil.checkDatabasePartitionSchemaNamePrefix();
		}
	}

	@Test
	public void testRegenerateViews() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					StringBundler.concat(
						"alter table ", TEST_CONTROL_TABLE_NAME, " add column ",
						TEST_CONTROL_TABLE_NEW_COLUMN, " bigint")));

			DBPartitionUtil.forEachCompanyId(
				companyId -> Assert.assertTrue(
					dbInspector.hasColumn(
						TEST_CONTROL_TABLE_NAME,
						TEST_CONTROL_TABLE_NEW_COLUMN)));
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					if (dbInspector.hasColumn(
							TEST_CONTROL_TABLE_NAME,
							TEST_CONTROL_TABLE_NEW_COLUMN)) {

						db.runSQL(
							StringBundler.concat(
								"alter table ", TEST_CONTROL_TABLE_NAME,
								" drop column ",
								TEST_CONTROL_TABLE_NEW_COLUMN));
					}
				});
		}
	}

	@Test
	public void testUpdateIndexes() throws Exception {
		DataSource dataSource = InfrastructureUtil.getDataSource();

		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					createAndPopulateTable(TEST_TABLE_NAME);

					Assert.assertFalse(
						dbInspector.hasIndex(TEST_TABLE_NAME, TEST_INDEX_NAME));

					try (Connection connection = dataSource.getConnection()) {
						db.updateIndexes(
							connection, TEST_TABLE_NAME,
							getCreateIndexSQL(TEST_TABLE_NAME), true);
					}

					Assert.assertTrue(
						dbInspector.hasIndex(TEST_TABLE_NAME, TEST_INDEX_NAME));
				});
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> dropTable(TEST_TABLE_NAME));
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		DBPartitionUpgradeProcess dbPartitionUpgradeProcess =
			new DBPartitionUpgradeProcess();

		dbPartitionUpgradeProcess.upgrade();

		long[] expectedCompanyIds = PortalInstances.getCompanyIdsBySQL();

		Arrays.sort(expectedCompanyIds);

		long[] actualCompanyIds = dbPartitionUpgradeProcess.getCompanyIds();

		Arrays.sort(actualCompanyIds);

		Assert.assertArrayEquals(expectedCompanyIds, actualCompanyIds);
	}

	public class DBPartitionUpgradeProcess extends UpgradeProcess {

		public long[] getCompanyIds() {
			return ArrayUtil.toArray(_companyIds.toArray(new Long[0]));
		}

		@Override
		protected void doUpgrade() throws Exception {
			_companyIds.add(CompanyThreadLocal.getCompanyId());
		}

		private volatile List<Long> _companyIds = new CopyOnWriteArrayList<>();

	}

	@Inject
	protected static EntityCache entityCache;

	@Inject
	protected static FinderCache finderCache;

	private static final String _CLASS_NAME_VALUE = "class.name.test";

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	private static Map<String, ResourceAction> _resourceActions;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private class ClassNameModelHints extends DefaultModelHintsImpl {

		@Override
		public List<String> getModels() {
			return Arrays.asList(_CLASS_NAME_VALUE);
		}

	}

}