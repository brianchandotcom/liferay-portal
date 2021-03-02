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

package com.liferay.commerce.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalServiceUtil;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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

import java.util.ArrayList;
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
 * @author Riccardo Alberti
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CommerceDiscountLocalServiceTest {

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

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

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

		_commercePricingConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommercePricingConfiguration.class);
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}
	}

	@Test
	public void testCreateFixedDiscountWithTargetProduct() throws Exception {
		frutillaRule.scenario(
			"When a fixed amount discount is targeting a product in a " +
				"catalog it shall be possible to retrieve it with the " +
					"discount discovery"
		).given(
			"A catalog a product and a discount targeting that product"
		).when(
			"The discount is discovered"
		).then(
			"The discount is matching the created one"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_user.getGroupId(), RandomTestUtil.nextDouble(),
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
				_company.getCompanyId(), cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			commerceDiscounts.toString(), 1, commerceDiscounts.size());

		Assert.assertEquals(commerceDiscount, commerceDiscounts.get(0));
	}

	@Test
	public void testCreatePercentageDiscountWithTargetProduct()
		throws Exception {

		frutillaRule.scenario(
			"When a percentage amount discount is targeting a product in a " +
				"catalog it shall be possible to retrieve it with the " +
					"discount discovery"
		).given(
			"A catalog a product and a discount targeting that product"
		).when(
			"The discount is discovered"
		).then(
			"The discount is matching the created one"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addPercentageCommerceDiscount(
				_user.getGroupId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommerceDiscountConstants.LEVEL_L1,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
				_company.getCompanyId(), cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			commerceDiscounts.toString(), 1, commerceDiscounts.size());

		Assert.assertEquals(commerceDiscount, commerceDiscounts.get(0));
	}

	@Test
	public void testGetOrderCommerceDiscountByHierarchy1() throws Exception {
		frutillaRule.scenario(
			"When multiple discounts are defined for the same target the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple discounts"
		).when(
			"The discount is discovered"
		).then(
			"The discount with highest rank is retrieved"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		long[] commerceAccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommerceDiscount commerceDiscountTotal1 =
			CommerceDiscountTestUtil.addChannelOrderDiscount(
				_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_TOTAL);

		_orderAssertEquals(
			commerceDiscountTotal1, CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscountTotal2 =
			CommerceDiscountTestUtil.addAccountGroupOrderDiscount(
				_user.getGroupId(), commerceAccountGroups,
				CommerceDiscountConstants.TARGET_TOTAL);

		_orderAssertEquals(
			commerceDiscountTotal2, CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscountTotal3 =
			CommerceDiscountTestUtil.addAccountGroupAndChannelOrderDiscount(
				_user.getGroupId(), commerceAccountGroups,
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_TOTAL);

		_orderAssertEquals(
			commerceDiscountTotal3, CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscountTotal4 =
			CommerceDiscountTestUtil.addAccountOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_TOTAL);

		_orderAssertEquals(
			commerceDiscountTotal4, CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscountTotal5 =
			CommerceDiscountTestUtil.addAccountAndChannelOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_TOTAL);

		_orderAssertEquals(
			commerceDiscountTotal5, CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscountShipping1 =
			CommerceDiscountTestUtil.addChannelOrderDiscount(
				_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_SHIPPING);

		_orderAssertEquals(
			commerceDiscountShipping1,
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscount commerceDiscountShipping2 =
			CommerceDiscountTestUtil.addAccountGroupOrderDiscount(
				_user.getGroupId(), commerceAccountGroups,
				CommerceDiscountConstants.TARGET_SHIPPING);

		_orderAssertEquals(
			commerceDiscountShipping2,
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscount commerceDiscountShipping3 =
			CommerceDiscountTestUtil.addAccountGroupAndChannelOrderDiscount(
				_user.getGroupId(), commerceAccountGroups,
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_SHIPPING);

		_orderAssertEquals(
			commerceDiscountShipping3,
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscount commerceDiscountShipping4 =
			CommerceDiscountTestUtil.addAccountOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_SHIPPING);

		_orderAssertEquals(
			commerceDiscountShipping4,
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscount commerceDiscountShipping5 =
			CommerceDiscountTestUtil.addAccountAndChannelOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_SHIPPING);

		_orderAssertEquals(
			commerceDiscountShipping5,
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscount commerceDiscountSubtotal1 =
			CommerceDiscountTestUtil.addChannelOrderDiscount(
				_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		_orderAssertEquals(
			commerceDiscountSubtotal1,
			CommerceDiscountConstants.TARGET_SUBTOTAL);

		CommerceDiscount commerceDiscountSubtotal2 =
			CommerceDiscountTestUtil.addAccountGroupOrderDiscount(
				_user.getGroupId(), commerceAccountGroups,
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		_orderAssertEquals(
			commerceDiscountSubtotal2,
			CommerceDiscountConstants.TARGET_SUBTOTAL);

		CommerceDiscount commerceDiscountSubtotal3 =
			CommerceDiscountTestUtil.addAccountGroupAndChannelOrderDiscount(
				_user.getGroupId(), commerceAccountGroups,
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		_orderAssertEquals(
			commerceDiscountSubtotal3,
			CommerceDiscountConstants.TARGET_SUBTOTAL);

		CommerceDiscount commerceDiscountSubtotal4 =
			CommerceDiscountTestUtil.addAccountOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		_orderAssertEquals(
			commerceDiscountSubtotal4,
			CommerceDiscountConstants.TARGET_SUBTOTAL);

		CommerceDiscount commerceDiscountSubtotal5 =
			CommerceDiscountTestUtil.addAccountAndChannelOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		_orderAssertEquals(
			commerceDiscountSubtotal5,
			CommerceDiscountConstants.TARGET_SUBTOTAL);
	}

	@Test
	public void testGetOrderCommerceDiscountByHierarchy2() throws Exception {
		frutillaRule.scenario(
			"A discount is qualified by an account and a channel is not " +
				"applicable to the same account on another channel"
		).given(
			"A catalog with a discount on account and channel"
		).when(
			"The discount is discovered given a different channel"
		).then(
			"No discount is returned"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		CommerceDiscountTestUtil.addAccountAndChannelOrderDiscount(
			_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
			_commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.TARGET_TOTAL);

		List<CommerceDiscount> commerceDiscounts =
			_getOrderCommerceDiscountByHierarchy(
				_company.getCompanyId(),
				_commerceAccount.getCommerceAccountId(),
				RandomTestUtil.nextLong(),
				CommerceDiscountConstants.TARGET_TOTAL);

		Assert.assertEquals(
			commerceDiscounts.toString(), 0, commerceDiscounts.size());
	}

	@Test
	public void testGetOrderCommerceDiscountByHierarchy3() throws Exception {
		frutillaRule.scenario(
			"A discount is qualified by an account group and a channel is " +
				"not applicable to the same account group on another channel"
		).given(
			"A catalog with a discount on account group and channel"
		).when(
			"The discount is discovered given a different channel"
		).then(
			"No discount is returned"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		long[] commerceAccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommerceDiscountTestUtil.addAccountGroupAndChannelOrderDiscount(
			_user.getGroupId(), commerceAccountGroups,
			_commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.TARGET_TOTAL);

		List<CommerceDiscount> commerceDiscounts =
			_getOrderCommerceDiscountByHierarchy(
				_company.getCompanyId(),
				_commerceAccount.getCommerceAccountId(),
				RandomTestUtil.nextLong(),
				CommerceDiscountConstants.TARGET_TOTAL);

		Assert.assertEquals(
			commerceDiscounts.toString(), 0, commerceDiscounts.size());
	}

	@Test
	public void testGetProductCommerceDiscountByHierarchy1() throws Exception {
		frutillaRule.scenario(
			"When multiple discounts are defined for the same target the " +
				"highest in the hierarchy shall be taken"
		).given(
			"A catalog with multiple discounts"
		).when(
			"The discount is discovered"
		).then(
			"The discount with highest rank is retrieved"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommerceDiscount commerceUnqualifiedDiscount =
			CommerceDiscountTestUtil.addPercentageCommerceDiscount(
				_user.getGroupId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommerceDiscountConstants.LEVEL_L2,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		_productAssertEquals(
			commerceUnqualifiedDiscount, cpDefinition.getCPDefinitionId());

		CommerceDiscount commerceChannelDiscount =
			CommerceDiscountTestUtil.addChannelDiscount(
				_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.LEVEL_L1,
				cpDefinition.getCPDefinitionId());

		_productAssertEquals(
			commerceChannelDiscount, cpDefinition.getCPDefinitionId());

		long[] commerceAccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommerceDiscount commerceAccountGroupsDiscount =
			CommerceDiscountTestUtil.addAccountGroupDiscount(
				_user.getGroupId(), commerceAccountGroups,
				CommerceDiscountConstants.LEVEL_L3,
				cpDefinition.getCPDefinitionId());

		_productAssertEquals(
			commerceAccountGroupsDiscount, cpDefinition.getCPDefinitionId());

		CommerceDiscount commerceAccountGroupsAndChannelDiscount =
			CommerceDiscountTestUtil.addAccountGroupAndChannelDiscount(
				_user.getGroupId(), commerceAccountGroups,
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.LEVEL_L3,
				cpDefinition.getCPDefinitionId());

		_productAssertEquals(
			commerceAccountGroupsAndChannelDiscount,
			cpDefinition.getCPDefinitionId());

		CommerceDiscount commerceAccountDiscount =
			CommerceDiscountTestUtil.addAccountDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.LEVEL_L4,
				cpDefinition.getCPDefinitionId());

		_productAssertEquals(
			commerceAccountDiscount, cpDefinition.getCPDefinitionId());

		CommerceDiscount commerceAccountAndChannelDiscount =
			CommerceDiscountTestUtil.addAccountAndChannelDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.LEVEL_L4,
				cpDefinition.getCPDefinitionId());

		_productAssertEquals(
			commerceAccountAndChannelDiscount,
			cpDefinition.getCPDefinitionId());
	}

	@Test
	public void testGetProductCommerceDiscountByHierarchy2() throws Exception {
		frutillaRule.scenario(
			"A discount is qualified by an account and a channel is not " +
				"applicable to the same account on another channel"
		).given(
			"A catalog with a discount on account and channel"
		).when(
			"The discount is discovered given a different channel"
		).then(
			"No discount is returned"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommerceDiscountTestUtil.addAccountAndChannelDiscount(
			_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
			_commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.LEVEL_L4,
			cpDefinition.getCPDefinitionId());

		List<CommerceDiscount> commerceDiscounts =
			_getProductCommerceDiscountByHierarchy(
				_company.getCompanyId(),
				_commerceAccount.getCommerceAccountId(),
				RandomTestUtil.nextLong(), cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			commerceDiscounts.toString(), 0, commerceDiscounts.size());
	}

	@Test
	public void testGetProductCommerceDiscountByHierarchy3() throws Exception {
		frutillaRule.scenario(
			"A discount is qualified by an account group and a channel is " +
				"not applicable to the same account group on another channel"
		).given(
			"A catalog with a discount on account group and channel"
		).when(
			"The discount is discovered given a different channel"
		).then(
			"No discount is returned"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		long[] commerceAccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommerceDiscountTestUtil.addAccountGroupAndChannelDiscount(
			_user.getGroupId(), commerceAccountGroups,
			_commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.LEVEL_L3,
			cpDefinition.getCPDefinitionId());

		List<CommerceDiscount> commerceDiscounts =
			_getProductCommerceDiscountByHierarchy(
				_company.getCompanyId(),
				_commerceAccount.getCommerceAccountId(),
				RandomTestUtil.nextLong(), cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			commerceDiscounts.toString(), 0, commerceDiscounts.size());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private List<CommerceDiscount> _getOrderCommerceDiscountByHierarchy(
			long companyId, long commerceAccountId, long commerceChannelId,
			String commerceDiscountTargetType)
		throws Exception {

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getAccountAndChannelCommerceDiscounts(
				commerceAccountId, commerceChannelId,
				commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountCommerceDiscounts(
				commerceAccountId, commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccountId);

		commerceDiscounts =
			_commerceDiscountLocalService.
				getAccountGroupAndChannelCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId,
					commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroupIds, commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			_commerceDiscountLocalService.getChannelCommerceDiscounts(
				commerceChannelId, commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		return _commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
			companyId, commerceDiscountTargetType);
	}

	private List<CommerceDiscount> _getProductCommerceDiscountByHierarchy(
			long companyId, long commerceAccountId, long commerceChannelId,
			long cpDefinitionId)
		throws Exception {

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getAccountAndChannelCommerceDiscounts(
				commerceAccountId, commerceChannelId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountCommerceDiscounts(
				commerceAccountId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccountId);

		commerceDiscounts =
			_commerceDiscountLocalService.
				getAccountGroupAndChannelCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroupIds, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			_commerceDiscountLocalService.getChannelCommerceDiscounts(
				commerceChannelId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		return _commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
			companyId, cpDefinitionId);
	}

	private void _orderAssertEquals(
			CommerceDiscount expectedDiscount, String type)
		throws Exception {

		List<CommerceDiscount> commerceDiscounts =
			_getOrderCommerceDiscountByHierarchy(
				_company.getCompanyId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(), type);

		CommerceDiscount commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			expectedDiscount.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());
	}

	private void _productAssertEquals(
			CommerceDiscount expectedDiscount, long cpDefinitionId)
		throws Exception {

		List<CommerceDiscount> commerceDiscounts =
			_getProductCommerceDiscountByHierarchy(
				_company.getCompanyId(),
				_commerceAccount.getCommerceAccountId(),
				_commerceChannel.getCommerceChannelId(), cpDefinitionId);

		CommerceDiscount commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			expectedDiscount.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());
	}

	private CommerceAccount _commerceAccount;
	private CommerceAccountGroup _commerceAccountGroup;

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Inject
	private CommerceAccountHelper _commerceAccountHelper;

	private CommerceCatalog _commerceCatalog;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private final List<CommerceOrder> _commerceOrders = new ArrayList<>();
	private CommercePricingConfiguration _commercePricingConfiguration;
	private Company _company;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}