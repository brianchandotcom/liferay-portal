/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_9_1.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
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
public class CommerceChannelAccountEntryRelUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateCommerceChannelAccountEntryRelClassNameId()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addBusinessAccountEntry(
				TestPropsValues.getUserId(), "Test Business Account", null,
				null, new long[] {TestPropsValues.getUserId()}, null,
				serviceContext);

		Group group = GroupTestUtil.addGroup();

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(group.getCompanyId());

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(), commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(), serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				commerceCatalog.getGroupId(), 0.0);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.addCommerceChannel(
				RandomTestUtil.randomString(),
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, group.getGroupId(),
				RandomTestUtil.randomString(),
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				commerceCurrency.getCode(), serviceContext);

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				addCommerceChannelAccountEntryRel(
					TestPropsValues.getUserId(),
					accountEntry.getAccountEntryId(),
					CommerceDiscount.class.getName(),
					commercePriceList.getCommercePriceListId(),
					commerceChannel.getCommerceChannelId(), false,
					RandomTestUtil.randomDouble(), RandomTestUtil.nextInt());

		_runUpgrade();

		EntityCacheUtil.clearCache();

		commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRel.
						getCommerceChannelAccountEntryRelId());

		Assert.assertEquals(
			_classNameLocalService.getClassNameId(CommercePriceList.class),
			commerceChannelAccountEntryRel.getClassNameId());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.commerce.internal.upgrade.v8_9_1." +
			"CommerceChannelAccountEntryRelUpgradeProcess";

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private static CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.commerce.internal.upgrade.registry.CommerceServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}