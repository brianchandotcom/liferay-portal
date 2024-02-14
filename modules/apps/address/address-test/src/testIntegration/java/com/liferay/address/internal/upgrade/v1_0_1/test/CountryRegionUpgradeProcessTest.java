/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionLocalizationTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class CountryRegionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcessRegionCreation() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				Country country = _countryLocalService.fetchCountryByA2(
					company.getCompanyId(), "US");

				if (country == null) {
					return;
				}

				_regionLocalService.deleteCountryRegions(
					country.getCountryId());
			});

		_runUpgrade();

		_companyLocalService.forEachCompany(
			company -> {
				Country country = _countryLocalService.fetchCountryByA2(
					company.getCompanyId(), "US");

				if (country == null) {
					return;
				}

				Assert.assertEquals(
					57,
					_regionLocalService.getRegionsCount(
						country.getCountryId()));
			});

		Company company = CompanyTestUtil.addCompany();

		Country country = _countryLocalService.getCountryByA2(
			company.getCompanyId(), "US");

		Assert.assertEquals(
			57, _regionLocalService.getRegionsCount(country.getCountryId()));

		_verifyCounters();
		_verifyRegionLocalizationCompanyId();
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	private void _verifyCounters() {
		List<Long> results = _regionLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				DSLFunctionFactoryUtil.max(
					RegionTable.INSTANCE.regionId
				).as(
					"MAX_VALUE"
				)
			).from(
				RegionTable.INSTANCE
			));

		Assert.assertTrue(
			results.get(0) <= _counterLocalService.getCurrentId(
				Region.class.getName()));
	}

	private void _verifyRegionLocalizationCompanyId() {
		int count = _companyLocalService.dslQueryCount(
			DSLQueryFactoryUtil.select(
				DSLFunctionFactoryUtil.count(
					RegionLocalizationTable.INSTANCE.companyId
				).as(
					"COUNT_VALUE"
				)
			).from(
				RegionLocalizationTable.INSTANCE
			).where(
				RegionLocalizationTable.INSTANCE.companyId.notIn(
					DSLQueryFactoryUtil.select(
						CompanyTable.INSTANCE.companyId
					).from(
						CompanyTable.INSTANCE
					))
			));

		Assert.assertEquals(0, count);
	}

	private static final String _CLASS_NAME =
		"com.liferay.address.internal.upgrade.v1_0_1." +
			"CountryRegionUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.address.internal.upgrade.registry.AddressUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private CountryLocalService _countryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private RegionLocalService _regionLocalService;

}