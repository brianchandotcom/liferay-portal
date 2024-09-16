/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.upgrade.v5_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balazs Breier
 */
@RunWith(Arquillian.class)
public class CPTaxCategoryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateCPTaxCategoryExternalReferenceCode()
		throws Exception {

		String externalReferenceCode1 = RandomTestUtil.randomString();
		String externalReferenceCode2 = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		CPTaxCategory cpTaxCategory1 =
			_cpTaxCategoryLocalService.addCPTaxCategory(
				externalReferenceCode1, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), serviceContext);

		CPTaxCategory cpTaxCategory2 =
			_cpTaxCategoryLocalService.addCPTaxCategory(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), serviceContext);

		cpTaxCategory2.setCompanyId(0);

		cpTaxCategory2.setExternalReferenceCode(externalReferenceCode1);

		cpTaxCategory2 = _cpTaxCategoryLocalService.updateCPTaxCategory(
			cpTaxCategory2);

		CPTaxCategory cpTaxCategory3 =
			_cpTaxCategoryLocalService.addCPTaxCategory(
				externalReferenceCode2, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), serviceContext);

		_runUpgrade();

		EntityCacheUtil.clearCache();

		cpTaxCategory1 = _cpTaxCategoryLocalService.getCPTaxCategory(
			cpTaxCategory1.getCPTaxCategoryId());

		Assert.assertNotEquals(
			externalReferenceCode1, cpTaxCategory1.getExternalReferenceCode());

		cpTaxCategory2 = _cpTaxCategoryLocalService.getCPTaxCategory(
			cpTaxCategory2.getCPTaxCategoryId());

		Assert.assertNotEquals(
			externalReferenceCode1, cpTaxCategory2.getExternalReferenceCode());
		Assert.assertNotEquals(
			cpTaxCategory1.getExternalReferenceCode(),
			cpTaxCategory2.getExternalReferenceCode());

		cpTaxCategory3 = _cpTaxCategoryLocalService.getCPTaxCategory(
			cpTaxCategory3.getCPTaxCategoryId());

		Assert.assertEquals(
			externalReferenceCode2, cpTaxCategory3.getExternalReferenceCode());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.commerce.product.internal.upgrade.v5_0_0." +
			"CPTaxCategoryUpgradeProcess";

	@Inject
	private static CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.commerce.product.internal.upgrade.registry.CommerceProductServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}