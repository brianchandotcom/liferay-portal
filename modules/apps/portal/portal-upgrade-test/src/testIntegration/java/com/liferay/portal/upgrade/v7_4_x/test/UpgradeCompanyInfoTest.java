/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeCompanyInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class UpgradeCompanyInfoTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgradeMovesCompanyColumnsToCompanyInfo() throws Exception {
		_company = CompanyTestUtil.addCompany();

		long[] companyIds = {
			TestPropsValues.getCompanyId(), _company.getCompanyId()
		};

		Map<Long, Map<String, String>> originalCompanyInfoValuesMap =
			new HashMap<>();

		for (long companyId : companyIds) {
			originalCompanyInfoValuesMap.put(
				companyId, _getCompanyInfoValues(companyId));
		}

		DB db = DBManagerUtil.getDB();

		try {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setCompanyIdWithSafeCloseable(
						PortalInstancePool.getDefaultCompanyId());

				Connection connection = DataAccess.getConnection()) {

				db.alterTableAddColumn(connection, "Company", "logoId", "LONG");

				for (String[] column : _COMPANY_INFO_COLUMNS) {
					db.alterTableAddColumn(
						connection, "Company", column[0], column[1]);
				}
			}

			Map<String, String> clearedCompanyInfoValues = new HashMap<>();

			for (String[] column : _COMPANY_INFO_COLUMNS) {
				clearedCompanyInfoValues.put(column[0], null);
			}

			clearedCompanyInfoValues.put("logoId", "0");

			Map<Long, Map<String, String>> expectedCompanyInfoValuesMap =
				new HashMap<>();

			for (long companyId : companyIds) {
				Map<String, String> expectedCompanyInfoValues = new HashMap<>();

				for (String[] column : _COMPANY_INFO_COLUMNS) {
					expectedCompanyInfoValues.put(
						column[0], RandomTestUtil.randomString(10) + companyId);
				}

				expectedCompanyInfoValues.put(
					"logoId", String.valueOf(RandomTestUtil.nextLong()));

				expectedCompanyInfoValuesMap.put(
					companyId, expectedCompanyInfoValues);

				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setCompanyIdWithSafeCloseable(
							PortalInstancePool.getDefaultCompanyId())) {

					_updateValues(
						companyId, "Company", expectedCompanyInfoValues);
				}

				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setCompanyIdWithSafeCloseable(
							companyId)) {

					_updateValues(
						companyId, "CompanyInfo", clearedCompanyInfoValues);
				}
			}

			UpgradeProcess upgradeProcess = new UpgradeCompanyInfo();

			for (UpgradeStep upgradeStep : upgradeProcess.getUpgradeSteps()) {
				upgradeStep.upgrade();
			}

			for (long companyId : companyIds) {
				Assert.assertEquals(
					expectedCompanyInfoValuesMap.get(companyId),
					_getCompanyInfoValues(companyId));
			}

			try (Connection connection = DataAccess.getConnection()) {
				DBInspector dbInspector = new DBInspector(connection);

				Assert.assertFalse(
					"logoId", dbInspector.hasColumn("Company", "logoId"));

				for (String[] column : _COMPANY_INFO_COLUMNS) {
					Assert.assertFalse(
						column[0], dbInspector.hasColumn("Company", column[0]));
				}
			}
		}
		finally {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setCompanyIdWithSafeCloseable(
						PortalInstancePool.getDefaultCompanyId());

				Connection connection = DataAccess.getConnection()) {

				DBInspector dbInspector = new DBInspector(connection);

				if (dbInspector.hasColumn("Company", "logoId")) {
					db.alterTableDropColumn(connection, "Company", "logoId");
				}

				for (String[] column : _COMPANY_INFO_COLUMNS) {
					if (dbInspector.hasColumn("Company", column[0])) {
						db.alterTableDropColumn(
							connection, "Company", column[0]);
					}
				}
			}

			for (long companyId : companyIds) {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setCompanyIdWithSafeCloseable(
							companyId)) {

					_updateValues(
						companyId, "CompanyInfo",
						originalCompanyInfoValuesMap.get(companyId));
				}
			}

			EntityCacheUtil.clearCache();
			FinderCacheUtil.clearCache();
		}
	}

	@Test
	public void testUpgradeRetainsCompanyInfoDataWhenAlreadyMigrated()
		throws Exception {

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String name = company.getName();

		UpgradeProcess upgradeProcess = new UpgradeCompanyInfo();

		upgradeProcess.upgrade();

		company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(name, company.getName());
	}

	private String _getCompanyInfoColumnNamesSQL() {
		StringBundler sb = new StringBundler();

		for (String[] column : _COMPANY_INFO_COLUMNS) {
			sb.append(column[0]);
			sb.append(", ");
		}

		sb.append("logoId");

		return sb.toString();
	}

	private Map<String, String> _getCompanyInfoValues(long companyId)
		throws Exception {

		Map<String, String> companyInfoValues = new HashMap<>();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId);

			Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", _getCompanyInfoColumnNamesSQL(),
					" from CompanyInfo where companyId = ?"))) {

			preparedStatement.setLong(1, companyId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				for (String[] column : _COMPANY_INFO_COLUMNS) {
					companyInfoValues.put(
						column[0], resultSet.getString(column[0]));
				}

				companyInfoValues.put("logoId", resultSet.getString("logoId"));
			}
		}

		return companyInfoValues;
	}

	private void _updateValues(
			long companyId, String tableName, Map<String, String> values)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set logoId = ?");

		for (String[] column : _COMPANY_INFO_COLUMNS) {
			sb.append(", ");
			sb.append(column[0]);
			sb.append(" = ?");
		}

		sb.append(" where companyId = ?");

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setLong(
				1, GetterUtil.getLong(values.get("logoId")));

			int parameterIndex = 2;

			for (String[] column : _COMPANY_INFO_COLUMNS) {
				preparedStatement.setString(
					parameterIndex++, values.get(column[0]));
			}

			preparedStatement.setLong(parameterIndex, companyId);

			preparedStatement.executeUpdate();
		}
	}

	private static final String[][] _COMPANY_INFO_COLUMNS = {
		{"homeURL", "STRING null"}, {"indexNameCurrent", "VARCHAR(75) null"},
		{"indexNameNext", "VARCHAR(75) null"}, {"industry", "VARCHAR(75) null"},
		{"legalId", "VARCHAR(75) null"}, {"legalName", "VARCHAR(75) null"},
		{"legalType", "VARCHAR(75) null"}, {"name", "VARCHAR(75) null"},
		{"sicCode", "VARCHAR(75) null"}, {"size_", "VARCHAR(75) null"},
		{"tickerSymbol", "VARCHAR(75) null"}, {"type_", "VARCHAR(75) null"}
	};

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

}