/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.company.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.impl.ResourceActionLocalServiceImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.util.PortalInstances;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
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

		_resourceActions = ReflectionTestUtil.getFieldValue(
			ResourceActionLocalServiceImpl.class, "_resourceActions");

		_regenerateResourceActions();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		disableDBPartition();

		_regenerateResourceActions();
	}

	@Test
	public void testAddDBPartitionCompanyWhenRenamingFails() throws Exception {
		_company = CompanyTestUtil.addCompany();

		boolean standaloneDBPartition = false;

		try {
			_companyLocalService.extractDBPartitionCompany(
				_company.getCompanyId());

			standaloneDBPartition = true;

			Company defaultCompany = _companyLocalService.getCompany(
				PortalInstances.getDefaultCompanyId());

			try {
				_company = _companyLocalService.addDBPartitionCompany(
					_company.getCompanyId(), null, null,
					defaultCompany.getWebId());

				standaloneDBPartition = false;

				Assert.fail("Should fail due to duplicate web ID");
			}
			catch (PortalException portalException) {
				long[] companyIds = PortalInstances.getCompanyIdsBySQL();

				Assert.assertFalse(
					ArrayUtil.contains(companyIds, _company.getCompanyId()));

				_checkStandaloneDBPartitionTables(
					_company.getCompanyId(), "Company", "VirtualHost");
			}
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {_company.getCompanyId()});
			}
		}
	}

	@Test
	public void testExtractAndAddDBPartitionCompany() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_companyLocalService.extractDBPartitionCompany(_company.getCompanyId());

		String name = "new" + _company.getName();
		String virtualHostName = "new" + _company.getVirtualHostname();
		String webId = "new" + _company.getWebId();

		boolean standaloneDBPartition = true;

		try {
			_company = _companyLocalService.addDBPartitionCompany(
				_company.getCompanyId(), name, virtualHostName, webId);

			standaloneDBPartition = false;

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			Assert.assertTrue(
				ArrayUtil.contains(companyIds, _company.getCompanyId()));

			Assert.assertEquals(name, _company.getName());
			Assert.assertEquals(virtualHostName, _company.getVirtualHostname());
			Assert.assertEquals(webId, _company.getWebId());
		}
		finally {
			if (standaloneDBPartition) {
				removeDBPartitions(new long[] {_company.getCompanyId()});
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
				dbPartitionDB.getCatalog(connection, "lparttest_" + companyId),
				dbPartitionDB.getSchema(connection, "lparttest_" + companyId),
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

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	private static Map<String, ResourceAction> _resourceActions;

	@DeleteAfterTestRun
	private Company _company;

}