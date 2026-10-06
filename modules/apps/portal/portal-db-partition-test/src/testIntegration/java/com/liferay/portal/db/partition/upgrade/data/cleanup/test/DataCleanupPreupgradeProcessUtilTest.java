/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.CompanyProviderClassTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.data.cleanup.DataCleanupPreupgradeProcessUtil;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class DataCleanupPreupgradeProcessUtilTest
	extends BaseDBPartitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"),
			new LiferayIntegrationTestRule() {
				{
					skipTestRule(CompanyProviderClassTestRule.INSTANCE);
				}
			},
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();
	}

	@Before
	public void setUp() throws Exception {
		for (long companyId : COMPANY_IDS) {
			db.runSQL(
				dbPartitionDB.getCreatePartitionSQL(
					connection, getPartitionName(companyId)));
		}
	}

	@After
	public void tearDown() throws Exception {
		try {
			dropSchemas();
		}
		finally {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setCompanyIdWithSafeCloseable(
						PortalInstancePool.getDefaultCompanyId())) {

				dropTable(_TABLE_NAME);
			}
		}
	}

	@Test
	public void testGetPrimaryKeyColumnName() throws Exception {
		_runSQL(COMPANY_IDS[0], getCreateTableSQL(_TABLE_NAME));

		String createTableSQL = StringBundler.concat(
			"create table ", _TABLE_NAME,
			" (testColumn bigint, companyId bigint)");

		_runSQL(COMPANY_IDS[1], createTableSQL);

		long defaultCompanyId = PortalInstancePool.getDefaultCompanyId();

		_runSQL(defaultCompanyId, createTableSQL);

		DataCleanupPreupgradeProcessUtil.enableCache();

		try {
			Assert.assertNull(_getPrimaryKeyColumnName(defaultCompanyId));
			Assert.assertEquals(
				dbInspector.normalizeName("testColumn"),
				_getPrimaryKeyColumnName(COMPANY_IDS[0]));
			Assert.assertNull(_getPrimaryKeyColumnName(COMPANY_IDS[1]));
		}
		finally {
			DataCleanupPreupgradeProcessUtil.disableCache();
		}
	}

	private String _getPrimaryKeyColumnName(long companyId) throws Exception {
		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setRawCompanyIdWithSafeCloseable(companyId);
			Connection connection = dataSource.getConnection()) {

			return DataCleanupPreupgradeProcessUtil.getPrimaryKeyColumnName(
				connection, new DBInspector(connection), _TABLE_NAME);
		}
	}

	private void _runSQL(long companyId, String sql) throws Exception {
		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setRawCompanyIdWithSafeCloseable(companyId);

			Connection connection = dataSource.getConnection();

			Statement statement = connection.createStatement()) {

			statement.execute(sql);
		}
	}

	private static final String _TABLE_NAME =
		TEST_TABLE_NAME + RandomTestUtil.randomString();

}