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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
@RunWith(Arquillian.class)
public class CommerceDiscountLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_company.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_user.getCompanyId(), _user.getGroupId(), _user.getUserId());

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {_user.getUserId()}, null, _serviceContext);

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
			_commerceCurrency.getCode());

		_commercePricingConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommercePricingConfiguration.class);

		_commerceOrders = new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}

		_cpDefinitionLocalService.deleteCPDefinitions(_company.getCompanyId());
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
				CommerceDiscountConstants.TARGET_PRODUCT,
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
				CommerceDiscountConstants.TARGET_PRODUCT,
				cpDefinition.getCPDefinitionId());

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
				_company.getCompanyId(), cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			commerceDiscounts.toString(), 1, commerceDiscounts.size());

		Assert.assertEquals(commerceDiscount, commerceDiscounts.get(0));
	}

	@Test
	public void testRetrieveCorrectDiscountByHierarchy() throws Exception {
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
				CommerceDiscountConstants.TARGET_PRODUCT,
				cpDefinition.getCPDefinitionId());

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
				_company.getCompanyId(), cpDefinition.getCPDefinitionId());

		CommerceDiscount commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceUnqualifiedDiscount.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());

		CommerceDiscount commerceChannelDiscount =
			CommerceDiscountTestUtil.addChannelDiscount(
				_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
				CommerceDiscountConstants.LEVEL_L1,
				cpDefinition.getCPDefinitionId());

		commerceDiscounts =
			_commerceDiscountLocalService.getChannelCommerceDiscounts(
				_commerceChannel.getCommerceChannelId(),
				cpDefinition.getCPDefinitionId());

		commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceChannelDiscount.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());

		long[] commerceAccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommerceDiscount commerceAccountGroupsDiscount =
			CommerceDiscountTestUtil.addAccountGroupDiscount(
				_user.getGroupId(), commerceAccountGroups,
				CommerceDiscountConstants.LEVEL_L3,
				cpDefinition.getCPDefinitionId());

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroups, cpDefinition.getCPDefinitionId());

		commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceAccountGroupsDiscount.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());

		CommerceDiscount commerceAccountDiscount =
			CommerceDiscountTestUtil.addAccountDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.LEVEL_L4,
				cpDefinition.getCPDefinitionId());

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountCommerceDiscounts(
				_commerceAccount.getCommerceAccountId(),
				cpDefinition.getCPDefinitionId());

		commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceAccountDiscount.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());
	}

	@Test
	public void testRetrieveOrderDiscountByHierarchy() throws Exception {
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
			_user.getUserId(), _commerceAccount.getCommerceAccountId(),
			_commerceCurrency);

		_commerceOrders.add(commerceOrder);

		long[] commerceAccountGroups =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_commerceAccount.getCommerceAccountId());

		CommerceDiscountTestUtil.addChannelOrderDiscount(
			_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscountTestUtil.addAccountGroupOrderDiscount(
			_user.getGroupId(), commerceAccountGroups,
			CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscountTotal3 =
			CommerceDiscountTestUtil.addAccountOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_TOTAL);

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountLocalService.getAccountCommerceDiscounts(
				_commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_TOTAL);

		CommerceDiscount commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceDiscountTotal3.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());

		CommerceDiscountTestUtil.addChannelOrderDiscount(
			_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscountTestUtil.addAccountGroupOrderDiscount(
			_user.getGroupId(), commerceAccountGroups,
			CommerceDiscountConstants.TARGET_SHIPPING);

		CommerceDiscount commerceDiscountShipping3 =
			CommerceDiscountTestUtil.addAccountOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_SHIPPING);

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountCommerceDiscounts(
				_commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_SHIPPING);

		commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceDiscountShipping3.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());

		CommerceDiscountTestUtil.addChannelOrderDiscount(
			_user.getGroupId(), _commerceChannel.getCommerceChannelId(),
			CommerceDiscountConstants.TARGET_SUBTOTAL);

		CommerceDiscountTestUtil.addAccountGroupOrderDiscount(
			_user.getGroupId(), commerceAccountGroups,
			CommerceDiscountConstants.TARGET_SUBTOTAL);

		CommerceDiscount commerceDiscountSubtotal3 =
			CommerceDiscountTestUtil.addAccountOrderDiscount(
				_user.getGroupId(), _commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		commerceDiscounts =
			_commerceDiscountLocalService.getAccountCommerceDiscounts(
				_commerceAccount.getCommerceAccountId(),
				CommerceDiscountConstants.TARGET_SUBTOTAL);

		commerceDiscount = commerceDiscounts.get(0);

		Assert.assertEquals(
			commerceDiscountSubtotal3.getCommerceDiscountId(),
			commerceDiscount.getCommerceDiscountId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@DeleteAfterTestRun
	private CommerceAccount _commerceAccount;

	@DeleteAfterTestRun
	private CommerceAccountGroup _commerceAccountGroup;

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Inject
	private CommerceAccountHelper _commerceAccountHelper;

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private List<CommerceOrder> _commerceOrders;
	private CommercePricingConfiguration _commercePricingConfiguration;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}