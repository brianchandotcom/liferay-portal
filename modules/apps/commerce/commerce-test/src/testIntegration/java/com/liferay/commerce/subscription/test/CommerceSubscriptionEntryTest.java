/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.commerce.subscription.test.util.CommerceSubscriptionEntryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.util.comparator.CommerceSubscriptionEntryCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class CommerceSubscriptionEntryTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_user.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
	}

	@After
	public void tearDown() throws Exception {
		_cpOptionLocalService.deleteCPOptions(_group.getCompanyId());
	}

	@Test
	public void testAddCommerceSubscriptionEntry() throws Exception {
		frutillaRule.scenario(
			"Add a product subscription entry"
		).given(
			"A SKU that is subscription enabled"
		).and(
			"Has 1 max subscription cycle number"
		).when(
			"I buy that SKU"
		).and(
			"The order is ready to be shipped (orderStatus = To Transmit)"
		).then(
			"The product subscription entry is created"
		);

		CommerceSubscriptionEntryTestUtil.setUpCommerceSubscriptionEntry(
			_user.getUserId(), _commerceChannel.getGroupId(), 1,
			_commerceCurrency.getCommerceCurrencyId(),
			_commerceSubscriptionEntryHelper);

		int commerceSubscriptionEntriesCount =
			_commerceSubscriptionEntryLocalService.
				getCommerceSubscriptionEntriesCount(
					_user.getCompanyId(), _user.getUserId());

		Assert.assertEquals(1, commerceSubscriptionEntriesCount);
	}

	@Test
	public void testOverrideCPInstanceSubscriptionInfoAllDisabled()
		throws Exception {

		frutillaRule.scenario(
			"Add an order"
		).given(
			"One subscription disabled product with two SKUs"
		).but(
			"One of them overrides the subscription info"
		).when(
			"Order is placed"
		).then(
			"No subscription entries are created"
		);

		_testOverrideCPInstanceSubscriptionInfo(false, false);
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testOverrideCPInstanceSubscriptionInfoAllEnabled()
		throws Exception {

		frutillaRule.scenario(
			"Add an order"
		).given(
			"One subscription enabled product with two SKUs"
		).but(
			"One of them overrides the subscription info"
		).when(
			"Subscription entries are created"
		).then(
			"It should not be possible to add two subscription order items " +
				"in the same order"
		);

		_testOverrideCPInstanceSubscriptionInfo(true, true);
	}

	@Test
	public void testOverrideCPInstanceSubscriptionInfoDefinitionEnabled()
		throws Exception {

		frutillaRule.scenario(
			"Add an order"
		).given(
			"One subscription enabled product with two SKUs"
		).but(
			"One of them overrides the subscription info"
		).when(
			"A subscription entry is created"
		).then(
			"It should have right subscription info"
		);

		_testOverrideCPInstanceSubscriptionInfo(true, false);
	}

	@Test
	public void testOverrideCPInstanceSubscriptionInfoInstanceEnabled()
		throws Exception {

		frutillaRule.scenario(
			"Add an order"
		).given(
			"One subscription disabled product with two SKUs"
		).but(
			"One of them overrides the subscription info"
		).when(
			"A subscription entry is created"
		).then(
			"It should have right subscription info"
		);

		_testOverrideCPInstanceSubscriptionInfo(false, true);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private void _testOverrideCPInstanceSubscriptionInfo(
			boolean cpDefinitionSubscriptionEnabled,
			boolean cpInstanceSubscriptionEnabled)
		throws Exception {

		CommerceCatalog commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		long groupId = commerceCatalog.getGroupId();

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			groupId, SimpleCPTypeConstants.NAME, false, false);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(cpDefinition);

		CPOption cpOption = CPTestUtil.addCPOption(groupId, true);

		CPTestUtil.addCPOptionValue(cpOption);
		CPTestUtil.addCPOptionValue(cpOption);

		CPDefinitionOptionRelLocalServiceUtil.addCPDefinitionOptionRel(
			cpDefinition.getCPDefinitionId(), cpOption.getCPOptionId(), true,
			ServiceContextTestUtil.getServiceContext(groupId));

		int cpDefinitionSubscriptionLength = RandomTestUtil.randomInt(1, 100);
		String cpDefinitionSubscriptionType = "daily";
		long cpDefinitionMaxSubscriptionCycles = RandomTestUtil.randomLong();

		if (cpDefinitionSubscriptionEnabled) {
			cpDefinition.setSubscriptionEnabled(true);
			cpDefinition.setSubscriptionLength(cpDefinitionSubscriptionLength);
			cpDefinition.setSubscriptionType(cpDefinitionSubscriptionType);
			cpDefinition.setMaxSubscriptionCycles(
				cpDefinitionMaxSubscriptionCycles);

			cpDefinition = CPDefinitionLocalServiceUtil.updateCPDefinition(
				cpDefinition);
		}

		int cpInstanceSubscriptionLength = RandomTestUtil.randomInt(1, 100);
		String cpInstanceSubscriptionType = "daily";
		long cpInstanceMaxSubscriptionCycles = RandomTestUtil.randomLong();

		CPTestUtil.buildCPInstances(cpDefinition);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
			_commerceChannel.getCommerceChannelId(),
			ServiceContextTestUtil.getServiceContext(
				_commerceChannel.getGroupId()));

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				cpDefinition.getGroupId());

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, cpDefinition.getCProductId(),
				cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), BigDecimal.ZERO);

			if (cpInstances.indexOf(cpInstance) == 0) {
				cpInstance.setOverrideSubscriptionInfo(true);
				cpInstance.setSubscriptionEnabled(
					cpInstanceSubscriptionEnabled);
				cpInstance.setSubscriptionLength(cpInstanceSubscriptionLength);
				cpInstance.setSubscriptionType(cpInstanceSubscriptionType);
				cpInstance.setMaxSubscriptionCycles(
					cpInstanceMaxSubscriptionCycles);

				cpInstance = CPInstanceLocalServiceUtil.updateCPInstance(
					cpInstance);
			}

			CPSubscriptionInfo cpSubscriptionInfo =
				cpInstance.getCPSubscriptionInfo();

			if (cpSubscriptionInfo != null) {
				CommerceTestUtil.addCommerceOrderItem(
					commerceOrder.getCommerceOrderId(),
					cpInstance.getCPInstanceId(), 1);
			}
		}

		_commerceSubscriptionEntryHelper.checkCommerceSubscriptions(
			commerceOrder);

		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			_commerceSubscriptionEntryLocalService.
				getCommerceSubscriptionEntries(
					_group.getCompanyId(), _user.getUserId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					new CommerceSubscriptionEntryCreateDateComparator());

		if (cpDefinitionSubscriptionEnabled && cpInstanceSubscriptionEnabled) {
			Assert.assertEquals(
				commerceSubscriptionEntries.toString(), 2,
				commerceSubscriptionEntries.size());

			CommerceSubscriptionEntry commerceSubscriptionEntry1 =
				commerceSubscriptionEntries.get(0);
			CommerceSubscriptionEntry commerceSubscriptionEntry2 =
				commerceSubscriptionEntries.get(1);

			Assert.assertEquals(
				cpDefinitionSubscriptionLength,
				commerceSubscriptionEntry1.getSubscriptionLength());
			Assert.assertEquals(
				cpDefinitionSubscriptionType,
				commerceSubscriptionEntry1.getSubscriptionType());
			Assert.assertEquals(
				cpDefinitionMaxSubscriptionCycles,
				commerceSubscriptionEntry1.getMaxSubscriptionCycles());

			Assert.assertEquals(
				cpInstanceSubscriptionLength,
				commerceSubscriptionEntry2.getSubscriptionLength());
			Assert.assertEquals(
				cpInstanceSubscriptionType,
				commerceSubscriptionEntry2.getSubscriptionType());
			Assert.assertEquals(
				cpInstanceMaxSubscriptionCycles,
				commerceSubscriptionEntry2.getMaxSubscriptionCycles());
		}
		else if (!cpDefinitionSubscriptionEnabled &&
				 !cpInstanceSubscriptionEnabled) {

			Assert.assertEquals(
				commerceSubscriptionEntries.toString(), 0,
				commerceSubscriptionEntries.size());
		}
		else {
			Assert.assertEquals(
				commerceSubscriptionEntries.toString(), 1,
				commerceSubscriptionEntries.size());

			CommerceSubscriptionEntry commerceSubscriptionEntry =
				commerceSubscriptionEntries.get(0);

			if (cpInstanceSubscriptionEnabled) {
				Assert.assertEquals(
					cpInstanceSubscriptionLength,
					commerceSubscriptionEntry.getSubscriptionLength());
				Assert.assertEquals(
					cpInstanceSubscriptionType,
					commerceSubscriptionEntry.getSubscriptionType());
				Assert.assertEquals(
					cpInstanceMaxSubscriptionCycles,
					commerceSubscriptionEntry.getMaxSubscriptionCycles());
			}
			else {
				Assert.assertEquals(
					cpDefinitionSubscriptionLength,
					commerceSubscriptionEntry.getSubscriptionLength());
				Assert.assertEquals(
					cpDefinitionSubscriptionType,
					commerceSubscriptionEntry.getSubscriptionType());
				Assert.assertEquals(
					cpDefinitionMaxSubscriptionCycles,
					commerceSubscriptionEntry.getMaxSubscriptionCycles());
			}
		}
	}

	private static User _user;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CommerceSubscriptionEntryHelper _commerceSubscriptionEntryHelper;

	@Inject
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private CPOptionLocalService _cpOptionLocalService;

	private Group _group;

}