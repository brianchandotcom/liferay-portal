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
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalServiceUtil;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.test.util.CommerceAccountGroupTestUtil;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
public class CommercePriceListHierarchyDiscoveryTest {

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

		_commerceAccount1 =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceAccountGroup =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_company.getCompanyId(), RandomTestUtil.randomString(), 0,
				false, null, _serviceContext);

		CommerceAccountGroupCommerceAccountRelLocalServiceUtil.
			addCommerceAccountGroupCommerceAccountRel(
				_commerceAccountGroup.getCommerceAccountGroupId(),
				_commerceAccount1.getCommerceAccountId(), _serviceContext);

		_commerceChannel1 = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_catalog = _commerceCatalogLocalService.addCommerceCatalog(
			null, RandomTestUtil.randomString(), _commerceCurrency.getCode(),
			LocaleUtil.US.getDisplayLanguage(), _serviceContext);

		_commercePriceList1 = CommercePriceListTestUtil.addCommercePriceList(
			_catalog.getGroupId(), false, _TYPE, 1.0);
		_commercePriceList2 = CommercePriceListTestUtil.addCommercePriceList(
			_catalog.getGroupId(), false, _TYPE, 1.0);
		_commercePriceList3 = CommercePriceListTestUtil.addCommercePriceList(
			_catalog.getGroupId(), false, _TYPE, 1.0);
		_commercePriceList4 = CommercePriceListTestUtil.addCommercePriceList(
			_catalog.getGroupId(), false, _TYPE, 1.0);
		_commercePriceList5 = CommercePriceListTestUtil.addCommercePriceList(
			_catalog.getGroupId(), false, _TYPE, 1.0);

		_commerceAccount2 = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), "Business Account1", "example1@email.com",
			_serviceContext);
		_commerceAccount3 = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), "Business Account2", "example1@email.com",
			_serviceContext);
		_commerceAccount4 = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), "Business Account3", "example1@email.com",
			_serviceContext);
		_commerceAccount5 = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), "Business Account4", "example1@email.com",
			_serviceContext);
		_commerceAccount6 = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), "Business Account5", "example1@email.com",
			_serviceContext);
		_commerceAccount7 = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), "Business Account6", "example1@email.com",
			_serviceContext);

		CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
			_group.getGroupId(), _commerceAccount4);
		CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
			_group.getGroupId(), _commerceAccount5);

		_commerceChannel2 = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
		_commerceChannel3 = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
		_commerceChannel4 = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());
		_commerceChannel5 = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		long[] commerceAccount3AccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount4.getCommerceAccountId());
		long[] commerceAccount4AccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount5.getCommerceAccountId());

		CommercePriceListTestUtil.addAccountToPriceList(
			_catalog.getGroupId(), _commerceAccount2.getCommerceAccountId(),
			_commercePriceList1.getCommercePriceListId());
		CommercePriceListTestUtil.addAccountToPriceList(
			_catalog.getGroupId(), CommerceAccountConstants.ACCOUNT_ID_GUEST,
			_commercePriceList1.getCommercePriceListId());
		CommercePriceListTestUtil.addChannelToPriceList(
			_catalog.getGroupId(), _commerceChannel2.getCommerceChannelId(),
			_commercePriceList1.getCommercePriceListId());
		CommercePriceListTestUtil.addAccountToPriceList(
			_catalog.getGroupId(), _commerceAccount3.getCommerceAccountId(),
			_commercePriceList1.getCommercePriceListId());

		CommercePriceListTestUtil.addAccountToPriceList(
			_catalog.getGroupId(), _commerceAccount2.getCommerceAccountId(),
			_commercePriceList2.getCommercePriceListId());
		CommercePriceListTestUtil.addAccountGroupsToPriceList(
			_catalog.getGroupId(), commerceAccount3AccountGroups,
			_commercePriceList2.getCommercePriceListId());
		CommercePriceListTestUtil.addChannelToPriceList(
			_catalog.getGroupId(), _commerceChannel3.getCommerceChannelId(),
			_commercePriceList2.getCommercePriceListId());

		CommercePriceListTestUtil.addChannelToPriceList(
			_catalog.getGroupId(), _commerceChannel2.getCommerceChannelId(),
			_commercePriceList3.getCommercePriceListId());
		CommercePriceListTestUtil.addAccountGroupsToPriceList(
			_catalog.getGroupId(), commerceAccount4AccountGroups,
			_commercePriceList3.getCommercePriceListId());

		CommercePriceListTestUtil.addChannelToPriceList(
			_catalog.getGroupId(), _commerceChannel4.getCommerceChannelId(),
			_commercePriceList4.getCommercePriceListId());
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_company.getCompanyId());
	}

	@Test
	public void testCreateCatalogBasePriceList() throws Exception {
		frutillaRule.scenario(
			"A price list is created and set as base pricelist for a catalog"
		).given(
			"A catalog"
		).when(
			"A price list is created with flag catalog base price list true"
		).then(
			"The price list is the default price list of the catalog"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				catalog.getGroupId(), 0, 0, null, _TYPE);

		Assert.assertEquals(
			commercePriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrieveCorrectPriceListByHierarchy() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list with highest rank is retrieved"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commerceUnqualifiedPriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), false, _TYPE, 1.0);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
				_commerceChannel1.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			commerceUnqualifiedPriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());

		CommercePriceList commerceChannelPriceList =
			CommercePriceListTestUtil.addChannelPriceList(
				catalog.getGroupId(), _commerceChannel1.getCommerceChannelId(),
				_TYPE);

		discoveredPriceList = _commercePriceListDiscovery.getCommercePriceList(
			catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
			_commerceChannel1.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			commerceChannelPriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount1.getCommerceAccountId());

		CommercePriceList commerceAccountGroupPriceList =
			CommercePriceListTestUtil.addAccountGroupPriceList(
				catalog.getGroupId(), commerceAccountGroupIds, _TYPE);

		discoveredPriceList = _commercePriceListDiscovery.getCommercePriceList(
			catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
			_commerceChannel1.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			commerceAccountGroupPriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());

		CommercePriceList commerceAccountGroupAndChannelPriceList =
			CommercePriceListTestUtil.addAccountGroupAndChannelPriceList(
				catalog.getGroupId(), commerceAccountGroupIds,
				_commerceChannel1.getCommerceChannelId(), _TYPE);

		discoveredPriceList = _commercePriceListDiscovery.getCommercePriceList(
			catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
			_commerceChannel1.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			commerceAccountGroupAndChannelPriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());

		CommercePriceList commerceAccountPriceList =
			CommercePriceListTestUtil.addAccountPriceList(
				catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
				_TYPE);

		discoveredPriceList = _commercePriceListDiscovery.getCommercePriceList(
			catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
			_commerceChannel1.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			commerceAccountPriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());

		CommercePriceList commerceAccountAndChannelPriceList =
			CommercePriceListTestUtil.addAccountAndChannelPriceList(
				catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
				_commerceChannel1.getCommerceChannelId(), _TYPE);

		discoveredPriceList = _commercePriceListDiscovery.getCommercePriceList(
			catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
			_commerceChannel1.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			commerceAccountAndChannelPriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrieveCorrectPriceListByHierarchy1() throws Exception {
		frutillaRule.scenario(
			"A price list is qualified by an account and a channel is not " +
				"applicable to the same account on another channel"
		).given(
			"A catalog with a price list on account and channel"
		).when(
			"The price list is discovered given a different channel"
		).then(
			"Only the catalog base price list is returned"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceListTestUtil.addAccountAndChannelPriceList(
			catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
			_commerceChannel1.getCommerceChannelId(), _TYPE);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
				RandomTestUtil.nextLong(), null, _TYPE);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		Assert.assertEquals(
			commercePriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrieveCorrectPriceListByHierarchy2() throws Exception {
		frutillaRule.scenario(
			"A price list is qualified by an account group and a channel is " +
				"not applicable to the same account on another channel"
		).given(
			"A catalog with a price list on account group and channel"
		).when(
			"The price list is discovered given a different channel"
		).then(
			"Only the catalog base price list is returned"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount1.getCommerceAccountId());

		CommercePriceListTestUtil.addAccountGroupAndChannelPriceList(
			catalog.getGroupId(), commerceAccountGroupIds,
			_commerceChannel1.getCommerceChannelId(), _TYPE);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				catalog.getGroupId(), _commerceAccount1.getCommerceAccountId(),
				RandomTestUtil.nextLong(), null, _TYPE);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		Assert.assertEquals(
			commercePriceList.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListForAccount() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list associated to account is retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(), _commerceAccount3.getCommerceAccountId(),
				_commerceChannel2.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList1.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListForAccountGroups() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list associated to account group is retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(), _commerceAccount5.getCommerceAccountId(),
				_commerceChannel2.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList3.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListForAccountGroupsPlusChannel()
		throws Exception {

		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list associated to account group and channel is " +
				"retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(), _commerceAccount4.getCommerceAccountId(),
				_commerceChannel3.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList2.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListForAccountPlusChannel() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list associated to account and channel is retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(), _commerceAccount2.getCommerceAccountId(),
				_commerceChannel2.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList1.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListForChannel() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list associated to channel is retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(), _commerceAccount6.getCommerceAccountId(),
				_commerceChannel4.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList4.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListForGuestAccount() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The price list associated to the guest account is retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(),
				CommerceAccountConstants.ACCOUNT_ID_GUEST,
				_commerceChannel2.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList1.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Test
	public void testRetrievePriceListUnqualified() throws Exception {
		frutillaRule.scenario(
			"When multiple price list are defined for the same catalog the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple price lists"
		).when(
			"The price list is discovered"
		).then(
			"The unqualified price list is retrieved"
		);

		CommercePriceList discoveredPriceList =
			_commercePriceListDiscovery.getCommercePriceList(
				_catalog.getGroupId(), _commerceAccount7.getCommerceAccountId(),
				_commerceChannel5.getCommerceChannelId(), null, _TYPE);

		Assert.assertEquals(
			_commercePriceList5.getCommercePriceListId(),
			discoveredPriceList.getCommercePriceListId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static final String _TYPE =
		CommercePriceListConstants.TYPE_PRICE_LIST;

	private CommerceCatalog _catalog;
	private CommerceAccount _commerceAccount1;
	private CommerceAccount _commerceAccount2;
	private CommerceAccount _commerceAccount3;
	private CommerceAccount _commerceAccount4;
	private CommerceAccount _commerceAccount5;
	private CommerceAccount _commerceAccount6;
	private CommerceAccount _commerceAccount7;
	private CommerceAccountGroup _commerceAccountGroup;

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Inject
	private CommerceAccountHelper _commerceAccountHelper;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceChannel _commerceChannel1;
	private CommerceChannel _commerceChannel2;
	private CommerceChannel _commerceChannel3;
	private CommerceChannel _commerceChannel4;
	private CommerceChannel _commerceChannel5;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;
	private CommercePriceList _commercePriceList1;
	private CommercePriceList _commercePriceList2;
	private CommercePriceList _commercePriceList3;
	private CommercePriceList _commercePriceList4;
	private CommercePriceList _commercePriceList5;

	@Inject(
		filter = "commerce.price.list.discovery.key=" + CommercePricingConstants.ORDER_BY_HIERARCHY
	)
	private CommercePriceListDiscovery _commercePriceListDiscovery;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private Company _company;
	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}