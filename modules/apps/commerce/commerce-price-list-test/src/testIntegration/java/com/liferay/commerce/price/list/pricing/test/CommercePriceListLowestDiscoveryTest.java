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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
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
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_accountEntry = CommerceAccountTestUtil.getPersonAccountEntry(
			_user.getUserId());

		_accountGroup = _accountGroupLocalService.addAccountGroup(
			_serviceContext.getUserId(), null, RandomTestUtil.randomString(),
			_serviceContext);

		_accountGroup.setExternalReferenceCode(null);
		_accountGroup.setDefaultAccountGroup(false);
		_accountGroup.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		_accountGroup.setExpandoBridgeAttributes(_serviceContext);

		_accountGroup = _accountGroupLocalService.updateAccountGroup(
			_accountGroup);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId());

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_group.getCompanyId());
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
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(), 0,
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
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(), 0,
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_accountEntry.getAccountEntryId());

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
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(), 0,
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
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(), 0,
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		CommercePriceList commerceAccountPriceList =
			CommercePriceListTestUtil.addAccountPriceList(
				_commerceCatalog.getGroupId(),
				_accountEntry.getAccountEntryId(), _TYPE);

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
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(), 0,
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());

		CommercePriceList commerceAccountAndChannelPriceList =
			CommercePriceListTestUtil.addAccountAndChannelPriceList(
				_commerceCatalog.getGroupId(),
				_accountEntry.getAccountEntryId(),
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
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(), 0,
				cpInstance.getCPInstanceUuid(), _TYPE);

		Assert.assertEquals(
			expectedPriceList.getCommercePriceListId(),
			discoveredCommercePriceList.getCommercePriceListId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static final String _TYPE =
		CommercePriceListConstants.TYPE_PRICE_LIST;

	private static User _user;

	private AccountEntry _accountEntry;
	private AccountGroup _accountGroup;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private CommerceAccountHelper _commerceAccountHelper;

	private CommerceCatalog _commerceCatalog;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject(
		filter = "component.name=com.liferay.commerce.price.list.internal.discovery.CommercePriceListLowestDiscoveryImpl"
	)
	private CommercePriceListDiscovery _commercePriceListDiscovery;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}