/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.upgrade.v3_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
public class FriendlyURLEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateFriendlyURLEntryGroupId() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		FriendlyURLEntry friendlyURLEntry1 =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				TestPropsValues.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class), 0,
				RandomTestUtil.randomString(), serviceContext);

		friendlyURLEntry1.setGroupId(0);

		friendlyURLEntry1 =
			_friendlyURLEntryLocalService.updateFriendlyURLEntry(
				friendlyURLEntry1);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization1 =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
				friendlyURLEntry1.getFriendlyURLEntryId(), "en_US");

		friendlyURLEntryLocalization1.setGroupId(0);

		_friendlyURLEntryLocalService.updateFriendlyURLEntryLocalization(
			friendlyURLEntry1, "en_US", RandomTestUtil.randomString());

		FriendlyURLEntry friendlyURLEntry2 =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				TestPropsValues.getGroupId(),
				_classNameLocalService.getClassNameId(CProduct.class), 0,
				RandomTestUtil.randomString(), serviceContext);

		friendlyURLEntry2.setGroupId(0);

		friendlyURLEntry2 =
			_friendlyURLEntryLocalService.updateFriendlyURLEntry(
				friendlyURLEntry2);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization2 =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
				friendlyURLEntry2.getFriendlyURLEntryId(), "en_US");

		friendlyURLEntryLocalization2.setGroupId(0);

		_friendlyURLEntryLocalService.updateFriendlyURLEntryLocalization(
			friendlyURLEntry2, "en_US", RandomTestUtil.randomString());

		_runUpgrade();

		EntityCacheUtil.clearCache();

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		friendlyURLEntry1 = _friendlyURLEntryLocalService.getFriendlyURLEntry(
			friendlyURLEntry1.getFriendlyURLEntryId());

		Assert.assertEquals(
			company.getGroupId(), friendlyURLEntry1.getGroupId());

		friendlyURLEntryLocalization1 =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
				friendlyURLEntry1.getFriendlyURLEntryId(), "en_US");

		Assert.assertEquals(
			company.getGroupId(), friendlyURLEntryLocalization1.getGroupId());

		friendlyURLEntry2 = _friendlyURLEntryLocalService.getFriendlyURLEntry(
			friendlyURLEntry2.getFriendlyURLEntryId());

		Assert.assertEquals(
			company.getGroupId(), friendlyURLEntry2.getGroupId());

		friendlyURLEntryLocalization2 =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
				friendlyURLEntry2.getFriendlyURLEntryId(), "en_US");

		Assert.assertEquals(
			company.getGroupId(), friendlyURLEntryLocalization2.getGroupId());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.commerce.product.internal.upgrade.v3_2_0." +
			"FriendlyURLEntryUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.commerce.product.internal.upgrade.registry.CommerceProductServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}