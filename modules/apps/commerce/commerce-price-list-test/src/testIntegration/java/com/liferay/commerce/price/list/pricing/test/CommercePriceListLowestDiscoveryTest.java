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

package com.liferay.commerce.price.list.pricing.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalServiceUtil;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Alberti
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CommercePriceListLowestDiscoveryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_company.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_company.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceAccountGroup =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_company.getCompanyId(), RandomTestUtil.randomString(), 0,
				false, null, _serviceContext);

		CommerceAccountGroupCommerceAccountRelLocalServiceUtil.
			addCommerceAccountGroupCommerceAccountRel(
				_commerceAccountGroup.getCommerceAccountGroupId(),
				_commerceAccount.getCommerceAccountId(), _serviceContext);

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_company.getCompanyId(), _company.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_company.getCompanyId());
	}

	@Test
	public void testRetrieveCorrectPriceListByLowestEntry() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"price list that provides the lowest price entry shall be taken"
		).given(
			"A catalog with multiple price lists and one product"
		).when(
			"The price list is discovered"
		).then(
			"The price list that gives the lowest price is retrieved"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceList commerceUnqualifiedPriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), false, _TYPE, 1.0);

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				"", cpDefinition.getCProductId(),
				cpInstance.getCPInstanceUuid(),
				commerceUnqualifiedPriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		BigDecimal lowestPrice = commercePriceEntry.getPrice();

		CommercePriceList expectedPriceList = commerceUnqualifiedPriceList;

		CommercePriceList discoveredCommercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		CommercePriceList commerceChannelPriceList =
			CommercePriceListTestUtil.addChannelPriceList(
				_commerceCatalog.getGroupId(),
				_commerceChannel.getCommerceChannelId(), _TYPE);

		commercePriceEntry = CommercePriceEntryTestUtil.addCommercePriceEntry(
			"", cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commerceChannelPriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		if (lowestPrice.compareTo(commercePriceEntry.getPrice()) > 0) {
			lowestPrice = commercePriceEntry.getPrice();
			expectedPriceList = commerceChannelPriceList;
		}

		discoveredCommercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommercePriceList commerceAccountGroupPriceList =
			CommercePriceListTestUtil.addAccountGroupPriceList(
				_commerceCatalog.getGroupId(), commerceAccountGroupIds, _TYPE);

		commercePriceEntry = CommercePriceEntryTestUtil.addCommercePriceEntry(
			"", cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commerceAccountGroupPriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		if (lowestPrice.compareTo(commercePriceEntry.getPrice()) > 0) {
			lowestPrice = commercePriceEntry.getPrice();
			expectedPriceList = commerceAccountGroupPriceList;
		}

		discoveredCommercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		CommercePriceList commerceAccountGroupAndChannelPriceList =
			CommercePriceListTestUtil.addAccountGroupAndChannelPriceList(
				_commerceCatalog.getGroupId(), commerceAccountGroupIds,
				_commerceChannel.getCommerceChannelId(), _TYPE);

		commercePriceEntry = CommercePriceEntryTestUtil.addCommercePriceEntry(
			"", cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commerceAccountGroupAndChannelPriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		if (lowestPrice.compareTo(commercePriceEntry.getPrice()) > 0) {
			lowestPrice = commercePriceEntry.getPrice();
			expectedPriceList = commerceAccountGroupAndChannelPriceList;
		}

		discoveredCommercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		CommercePriceList commerceAccountPriceList =
			CommercePriceListTestUtil.addAccountPriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(), _TYPE);

		commercePriceEntry = CommercePriceEntryTestUtil.addCommercePriceEntry(
			"", cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commerceAccountPriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		if (lowestPrice.compareTo(commercePriceEntry.getPrice()) > 0) {
			lowestPrice = commercePriceEntry.getPrice();
			expectedPriceList = commerceAccountPriceList;
		}

		discoveredCommercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		CommercePriceList commerceAccountAndChannelPriceList =
			CommercePriceListTestUtil.addAccountAndChannelPriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(), _TYPE);

		commercePriceEntry = CommercePriceEntryTestUtil.addCommercePriceEntry(
			"", cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commerceAccountAndChannelPriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		if (lowestPrice.compareTo(commercePriceEntry.getPrice()) > 0) {
			expectedPriceList = commerceAccountAndChannelPriceList;
		}

		discoveredCommercePriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_commerceCatalog.getGroupId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static final String _TYPE =
		CommercePriceListConstants.TYPE_PRICE_LIST;

	private CommerceAccount _commerceAccount;
	private CommerceAccountGroup _commerceAccountGroup;

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Inject
	private CommerceAccountHelper _commerceAccountHelper;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	private CommerceCatalog _commerceCatalog;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject(
		filter = "commerce.price.list.discovery.key=" + CommercePricingConstants.ORDER_BY_LOWEST_ENTRY
	)
	private CommercePriceListDiscovery _commercePriceListDiscovery;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private Company _company;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}