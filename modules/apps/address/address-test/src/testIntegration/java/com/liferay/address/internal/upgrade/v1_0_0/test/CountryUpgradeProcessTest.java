/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CountryLocalization;
import com.liferay.portal.kernel.model.CountryLocalizationTable;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionLocalization;
import com.liferay.portal.kernel.model.RegionLocalizationTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class CountryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgradeCountriesRegionsCreation() throws Exception {
		_company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			try (Connection connection = DataAccess.getConnection()) {
				_deleteByCompanyId(
					connection, "Country", _company.getCompanyId());
				_deleteByCompanyId(
					connection, "CountryLocalization", _company.getCompanyId());
				_deleteByCompanyId(
					connection, "Region", _company.getCompanyId());
				_deleteByCompanyId(
					connection, "RegionLocalization", _company.getCompanyId());
			}
		}

		_runUpgrade();

		_assertCount(_company.getCompanyId());

		_verifyCounters(_company.getCompanyId());
	}

	private void _assertCount(long companyId) throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId)) {

			try (Connection connection = DataAccess.getConnection()) {
				Assert.assertNotEquals(
					0, _getCountByCompanyId(connection, "Country", companyId));
				Assert.assertNotEquals(
					0,
					_getCountByCompanyId(
						connection, "CountryLocalization", companyId));
				Assert.assertNotEquals(
					0, _getCountByCompanyId(connection, "Region", companyId));
				Assert.assertNotEquals(
					0,
					_getCountByCompanyId(
						connection, "RegionLocalization", companyId));
			}
		}
	}

	private void _deleteByCompanyId(
			Connection connection, String tableName, long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"delete from ", tableName, " where companyId = ",
					companyId))) {

			preparedStatement.execute();
		}
	}

	private int _getCountByCompanyId(
			Connection connection, String tableName, long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(*) from ", tableName, " where companyId = ",
					companyId))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.next();

				return resultSet.getInt(1);
			}
		}
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private void _verifyCounter(
		PersistedModelLocalService persistedModelLocalService,
		BaseTable<?> table, Column<?, Long> column, String className,
		long companyId) {

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId)) {

			List<Long> results = persistedModelLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					DSLFunctionFactoryUtil.max(
						column
					).as(
						"MAX_VALUE"
					)
				).from(
					table
				));

			Assert.assertTrue(
				results.get(0) <= _counterLocalService.getCurrentId(className));
		}
	}

	private void _verifyCounters(long companyId) {
		_verifyCounter(
			_countryLocalService, CountryLocalizationTable.INSTANCE,
			CountryLocalizationTable.INSTANCE.countryLocalizationId,
			CountryLocalization.class.getName(), companyId);
		_verifyCounter(
			_regionLocalService, RegionTable.INSTANCE,
			RegionTable.INSTANCE.regionId, Region.class.getName(), companyId);
		_verifyCounter(
			_regionLocalService, RegionLocalizationTable.INSTANCE,
			RegionLocalizationTable.INSTANCE.regionLocalizationId,
			RegionLocalization.class.getName(), companyId);
	}

	private static final String _CLASS_NAME =
		"com.liferay.address.internal.upgrade.v1_0_0.CountryUpgradeProcess";

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static RegionLocalService _regionLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.address.internal.upgrade.registry.AddressUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Company _company;

}