/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.util.PortalInstances;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class CountryRegionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Country country = _countryLocalService.fetchCountryByA2(
			PortalInstances.getDefaultCompanyId(), "US");

		_regionsCount = _regionLocalService.getRegionsCount(
			country.getCountryId());

		_company = CompanyTestUtil.addCompany();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_companyLocalService.deleteCompany(_company);
	}

	@Test
	public void testUpgradeProcessRegionCreation() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				Country country = _countryLocalService.fetchCountryByA2(
					companyId, "US");

				_regionLocalService.deleteCountryRegions(
					country.getCountryId());
			});

		_runUpgrade();

		_companyLocalService.forEachCompanyId(
			companyId -> {
				Country country = _countryLocalService.fetchCountryByA2(
					companyId, "US");

				Assert.assertEquals(
					_regionsCount,
					_regionLocalService.getRegionsCount(
						country.getCountryId()));
			});

		_verifyCounter();
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private void _verifyCounter() {
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

	private static final String _CLASS_NAME =
		"com.liferay.address.internal.upgrade.v1_0_1." +
			"CountryRegionUpgradeProcess";

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static RegionLocalService _regionLocalService;

	private static int _regionsCount;

	@Inject(
		filter = "(&(component.name=com.liferay.address.internal.upgrade.registry.AddressUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}