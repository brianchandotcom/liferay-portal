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

package com.liferay.commerce.discount.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.test.util.CommerceAccountGroupTestUtil;
import com.liferay.commerce.test.util.CommerceTaxTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.TestCommerceContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceDiscountTargetGrossTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(), PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_commerceCurrency.getCode());

		_commerceChannel.setDiscountsTargetNetPrice(false);

		_commerceChannelLocalService.updateCommerceChannel(_commerceChannel);

		_commerceOrders = new ArrayList<>();

		_commerceTaxMethod = CommerceTaxTestUtil.addCommerceByAddressTaxMethod(
			_user.getUserId(), _commerceChannel.getGroupId(), true);
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}

		_commerceDiscountLocalService.deleteCommerceDiscounts(
			_group.getCompanyId());
		_commerceAccountLocalService.deleteCommerceAccount(_commerceAccount);
		GroupTestUtil.deleteGroup(_group);
		_userLocalService.deleteUser(_user);
	}

	@Test
	public void testAccountGroupDiscount() throws Exception {
		frutillaRule.scenario(
			"When a discount has an account groups associated to it, it will " +
				"be applied only if the criteria are fulfilled"
		).given(
			"A product with a base price"
		).and(
			"A discount with a account groups associated. The account groups " +
				"is associated to a specific user"
		).when(
			"I try to get the final price of the product and I am the user " +
				"associated to the account groups"
		).then(
			"The final price will be calculated taking into consideration " +
				"the discount"
		);

		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		BigDecimal taxRate = BigDecimal.valueOf(rate);

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);

		CPInstance cpInstance = CPTestUtil.addCPInstance();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition);

		cpInstance.setPrice(BigDecimal.valueOf(35));

		_cpInstanceLocalService.updateCPInstance(cpInstance);

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_group.getGroupId(), 10,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		CommerceDiscountTestUtil.addDiscountCommerceAccountGroupRel(
			commerceDiscount, commerceAccountGroup);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		CommerceDiscountValue discountValue =
			commerceProductPrice.getDiscountValue();

		CommerceMoney discountAmount = discountValue.getDiscountAmount();

		BigDecimal discountAmountWithoutTaxAmount =
			CommerceTaxTestUtil.getPriceWithoutTaxAmount(
				commerceDiscount.getLevel1(), taxRate,
				RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal discountAmountPrice = discountAmount.getPrice();

		Assert.assertEquals(
			discountAmountWithoutTaxAmount.stripTrailingZeros(),
			discountAmountPrice.stripTrailingZeros());

		BigDecimal price = cpInstance.getPrice();

		BigDecimal expectedPrice = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice = expectedPrice.subtract(commerceDiscount.getLevel1());

		expectedPrice = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		CommerceMoney finalPrice = commerceProductPrice.getFinalPrice();

		BigDecimal actualPrice = finalPrice.getPrice();

		_assertPrices(
			expectedPrice.stripTrailingZeros(),
			actualPrice.stripTrailingZeros());
	}

	@Test
	public void testCommerceFixedDiscount() throws Exception {
		frutillaRule.scenario(
			"Apply a fixed price discount"
		).given(
			"A product with a base price"
		).and(
			"A discount with fixed value"
		).when(
			"I try to get the final price of the product"
		).then(
			"The final price will be calculated taking into consideration " +
				"the discount"
		);

		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		BigDecimal taxRate = BigDecimal.valueOf(rate);

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CPInstance cpInstance = CPTestUtil.addCPInstance();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition);

		cpInstance.setPrice(BigDecimal.valueOf(25));

		_cpInstanceLocalService.updateCPInstance(cpInstance);

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_group.getGroupId(), 10,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		BigDecimal price = cpInstance.getPrice();

		BigDecimal expectedPrice = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice = expectedPrice.subtract(commerceDiscount.getLevel1());

		expectedPrice = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal actualPrice = BigDecimal.ZERO;

		if (commerceProductPrice != null) {
			CommerceMoney finalPrice = commerceProductPrice.getFinalPrice();

			actualPrice = finalPrice.getPrice();
		}

		_assertPrices(
			expectedPrice.stripTrailingZeros(),
			actualPrice.stripTrailingZeros());
	}

	@Test
	public void testCommerceFixedDiscounts() throws Exception {
		frutillaRule.scenario(
			"When few fixed discount are available check how they are applied"
		).given(
			"Some products with base prices"
		).and(
			"Some discounts linked to single product or to categories"
		).when(
			"I try to get the final prices of the products"
		).then(
			"The final prices are calculated based on the available " +
				"discounts. If more discount per type are available then the " +
					"best matching is applied"
		);

		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		BigDecimal taxRate = BigDecimal.valueOf(rate);

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CPInstance cpInstance1 = CPTestUtil.addCPInstance();
		CPInstance cpInstance2 = CPTestUtil.addCPInstance();
		CPInstance cpInstance3 = CPTestUtil.addCPInstance();
		CPInstance cpInstance4 = CPTestUtil.addCPInstance();
		CPInstance cpInstance5 = CPTestUtil.addCPInstance();

		cpInstance1.setPrice(BigDecimal.valueOf(111));
		cpInstance2.setPrice(BigDecimal.valueOf(222));
		cpInstance3.setPrice(BigDecimal.valueOf(333));
		cpInstance4.setPrice(BigDecimal.valueOf(444));
		cpInstance5.setPrice(BigDecimal.valueOf(555));

		_cpInstanceLocalService.updateCPInstance(cpInstance1);
		_cpInstanceLocalService.updateCPInstance(cpInstance2);
		_cpInstanceLocalService.updateCPInstance(cpInstance3);
		_cpInstanceLocalService.updateCPInstance(cpInstance4);
		_cpInstanceLocalService.updateCPInstance(cpInstance5);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		cpDefinition1.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition1.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition1);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		cpDefinition2.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition2.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition2);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		cpDefinition3.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition3.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition3);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		cpDefinition4.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition4.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition4);

		CPDefinition cpDefinition5 = cpInstance5.getCPDefinition();

		cpDefinition5.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition5.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition5);

		long[] category1CPDefinitionIds = {
			cpDefinition1.getCPDefinitionId(),
			cpDefinition2.getCPDefinitionId(), cpDefinition3.getCPDefinitionId()
		};

		long[] category2CPDefinitionIds = {
			cpDefinition3.getCPDefinitionId(),
			cpDefinition4.getCPDefinitionId(), cpDefinition5.getCPDefinitionId()
		};

		AssetCategory assetCategory1 = CPTestUtil.addCategoryToCPDefinitions(
			_group.getGroupId(), category1CPDefinitionIds);

		AssetCategory assetCategory2 = CPTestUtil.addCategoryToCPDefinitions(
			_group.getGroupId(), category2CPDefinitionIds);

		CommerceDiscountTestUtil.addFixedCommerceDiscount(
			_group.getGroupId(), 11, CommerceDiscountConstants.TARGET_PRODUCTS,
			cpDefinition1.getCPDefinitionId());

		CommerceDiscount commerceDiscount2 =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_group.getGroupId(), 22,
				CommerceDiscountConstants.TARGET_CATEGORIES,
				assetCategory1.getCategoryId());

		CommerceDiscount commerceDiscount3 =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_group.getGroupId(), 33,
				CommerceDiscountConstants.TARGET_CATEGORIES,
				assetCategory2.getCategoryId());

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		CommerceProductPrice commerceProductPrice1 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance1.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice2 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance2.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice3 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance3.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice4 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance4.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice5 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance5.getCPInstanceId(), 1, commerceContext);

		BigDecimal price1 = cpInstance1.getPrice();

		BigDecimal expectedPrice1 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price1, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice1 = expectedPrice1.subtract(commerceDiscount2.getLevel1());

		expectedPrice1 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice1, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price2 = cpInstance2.getPrice();

		BigDecimal expectedPrice2 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price2, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice2 = expectedPrice2.subtract(commerceDiscount2.getLevel1());

		expectedPrice2 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice2, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price3 = cpInstance3.getPrice();

		BigDecimal expectedPrice3 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price3, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice3 = expectedPrice3.subtract(commerceDiscount3.getLevel1());

		expectedPrice3 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice3, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price4 = cpInstance4.getPrice();

		BigDecimal expectedPrice4 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price4, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice4 = expectedPrice4.subtract(commerceDiscount3.getLevel1());

		expectedPrice4 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice4, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price5 = cpInstance5.getPrice();

		BigDecimal expectedPrice5 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price5, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice5 = expectedPrice5.subtract(commerceDiscount3.getLevel1());

		expectedPrice5 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice5, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal actualPrice1 = BigDecimal.ZERO;
		BigDecimal actualPrice2 = BigDecimal.ZERO;
		BigDecimal actualPrice3 = BigDecimal.ZERO;
		BigDecimal actualPrice4 = BigDecimal.ZERO;
		BigDecimal actualPrice5 = BigDecimal.ZERO;

		if (commerceProductPrice1 != null) {
			CommerceMoney finalPrice1 = commerceProductPrice1.getFinalPrice();

			actualPrice1 = finalPrice1.getPrice();
		}

		if (commerceProductPrice2 != null) {
			CommerceMoney finalPrice2 = commerceProductPrice2.getFinalPrice();

			actualPrice2 = finalPrice2.getPrice();
		}

		if (commerceProductPrice3 != null) {
			CommerceMoney finalPrice3 = commerceProductPrice3.getFinalPrice();

			actualPrice3 = finalPrice3.getPrice();
		}

		if (commerceProductPrice4 != null) {
			CommerceMoney finalPrice4 = commerceProductPrice4.getFinalPrice();

			actualPrice4 = finalPrice4.getPrice();
		}

		if (commerceProductPrice5 != null) {
			CommerceMoney finalPrice5 = commerceProductPrice5.getFinalPrice();

			actualPrice5 = finalPrice5.getPrice();
		}

		_assertPrices(
			expectedPrice1.stripTrailingZeros(),
			actualPrice1.stripTrailingZeros());
		_assertPrices(
			expectedPrice2.stripTrailingZeros(),
			actualPrice2.stripTrailingZeros());
		_assertPrices(
			expectedPrice3.stripTrailingZeros(),
			actualPrice3.stripTrailingZeros());
		_assertPrices(
			expectedPrice4.stripTrailingZeros(),
			actualPrice4.stripTrailingZeros());
		_assertPrices(
			expectedPrice5.stripTrailingZeros(),
			actualPrice5.stripTrailingZeros());
	}

	@Test
	public void testCommercePercentageDiscounts() throws Exception {
		frutillaRule.scenario(
			"When few percentage discount are available check how they are " +
				"applied"
		).given(
			"Some products with base prices"
		).and(
			"Some discounts linked to single product or to categories"
		).when(
			"I try to get the final prices of the products"
		).then(
			"The final prices are calculated based on the available " +
				"discounts. If more discount per type are available then the " +
					"best matching is applied"
		);

		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		BigDecimal taxRate = BigDecimal.valueOf(rate);

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CPInstance cpInstance1 = CPTestUtil.addCPInstance();
		CPInstance cpInstance2 = CPTestUtil.addCPInstance();
		CPInstance cpInstance3 = CPTestUtil.addCPInstance();
		CPInstance cpInstance4 = CPTestUtil.addCPInstance();
		CPInstance cpInstance5 = CPTestUtil.addCPInstance();

		cpInstance1.setPrice(BigDecimal.valueOf(125));
		cpInstance2.setPrice(BigDecimal.valueOf(160));
		cpInstance3.setPrice(BigDecimal.valueOf(300));
		cpInstance4.setPrice(BigDecimal.valueOf(109));
		cpInstance5.setPrice(BigDecimal.valueOf(405));

		_cpInstanceLocalService.updateCPInstance(cpInstance1);
		_cpInstanceLocalService.updateCPInstance(cpInstance2);
		_cpInstanceLocalService.updateCPInstance(cpInstance3);
		_cpInstanceLocalService.updateCPInstance(cpInstance4);
		_cpInstanceLocalService.updateCPInstance(cpInstance5);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		cpDefinition1.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition1.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition1);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		cpDefinition2.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition2.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition2);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		cpDefinition3.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition3.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition3);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		cpDefinition4.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition4.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition4);

		CPDefinition cpDefinition5 = cpInstance5.getCPDefinition();

		cpDefinition5.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition5.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition5);

		long[] category1CPDefinitionIds = {
			cpDefinition2.getCPDefinitionId(), cpDefinition3.getCPDefinitionId()
		};

		long[] category2CPDefinitionIds = {
			cpDefinition3.getCPDefinitionId(),
			cpDefinition4.getCPDefinitionId(), cpDefinition5.getCPDefinitionId()
		};

		AssetCategory assetCategory1 = CPTestUtil.addCategoryToCPDefinitions(
			_group.getGroupId(), category1CPDefinitionIds);

		AssetCategory assetCategory2 = CPTestUtil.addCategoryToCPDefinitions(
			_group.getGroupId(), category2CPDefinitionIds);

		CommerceDiscount commerceDiscount1 =
			CommerceDiscountTestUtil.addPercentageCommerceDiscount(
				_group.getGroupId(), 10, 15, 20, 0,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition1.getCPDefinitionId());

		CommerceDiscount commerceDiscount2 =
			CommerceDiscountTestUtil.addPercentageCommerceDiscount(
				_group.getGroupId(), 30, 0, 0, 0,
				CommerceDiscountConstants.TARGET_CATEGORIES,
				assetCategory1.getCategoryId());

		CommerceDiscount commerceDiscount3 =
			CommerceDiscountTestUtil.addPercentageCommerceDiscount(
				_group.getGroupId(), 50, 0, 0, 0,
				CommerceDiscountConstants.TARGET_CATEGORIES,
				assetCategory2.getCategoryId());

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		CommerceProductPrice commerceProductPrice1 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance1.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice2 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance2.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice3 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance3.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice4 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance4.getCPInstanceId(), 1, commerceContext);

		CommerceProductPrice commerceProductPrice5 =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance5.getCPInstanceId(), 1, commerceContext);

		BigDecimal price1 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			cpInstance1.getPrice(), taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal expectedPrice1_level1 = _subtractPercentage(
			price1, commerceDiscount1.getLevel1());

		BigDecimal expectedPrice1_level2 = _subtractPercentage(
			expectedPrice1_level1, commerceDiscount1.getLevel2());

		BigDecimal expectedPrice1_level3 = _subtractPercentage(
			expectedPrice1_level2, commerceDiscount1.getLevel3());

		expectedPrice1_level3 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice1_level3, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price2 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			cpInstance2.getPrice(), taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal expectedPrice2 = _subtractPercentage(
			price2, commerceDiscount2.getLevel1());

		expectedPrice2 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice2, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price3 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			cpInstance3.getPrice(), taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal expectedPrice3 = _subtractPercentage(
			price3, commerceDiscount3.getLevel1());

		expectedPrice3 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice3, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price4 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			cpInstance4.getPrice(), taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal expectedPrice4 = _subtractPercentage(
			price4, commerceDiscount3.getLevel1());

		expectedPrice4 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice4, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal price5 = CommerceTaxTestUtil.getPriceWithTaxAmount(
			cpInstance5.getPrice(), taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal expectedPrice5 = _subtractPercentage(
			price5, commerceDiscount3.getLevel1());

		expectedPrice5 = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice5, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		BigDecimal actualPrice1 = BigDecimal.ZERO;
		BigDecimal actualPrice2 = BigDecimal.ZERO;
		BigDecimal actualPrice3 = BigDecimal.ZERO;
		BigDecimal actualPrice4 = BigDecimal.ZERO;
		BigDecimal actualPrice5 = BigDecimal.ZERO;

		if (commerceProductPrice1 != null) {
			CommerceMoney finalPrice1 = commerceProductPrice1.getFinalPrice();

			actualPrice1 = finalPrice1.getPrice();
		}

		if (commerceProductPrice2 != null) {
			CommerceMoney finalPrice2 = commerceProductPrice2.getFinalPrice();

			actualPrice2 = finalPrice2.getPrice();
		}

		if (commerceProductPrice3 != null) {
			CommerceMoney finalPrice3 = commerceProductPrice3.getFinalPrice();

			actualPrice3 = finalPrice3.getPrice();
		}

		if (commerceProductPrice4 != null) {
			CommerceMoney finalPrice4 = commerceProductPrice4.getFinalPrice();

			actualPrice4 = finalPrice4.getPrice();
		}

		if (commerceProductPrice5 != null) {
			CommerceMoney finalPrice5 = commerceProductPrice5.getFinalPrice();

			actualPrice5 = finalPrice5.getPrice();
		}

		_assertPrices(
			expectedPrice1_level3.stripTrailingZeros(),
			actualPrice1.stripTrailingZeros());
		_assertPrices(
			expectedPrice2.stripTrailingZeros(),
			actualPrice2.stripTrailingZeros());
		_assertPrices(
			expectedPrice3.stripTrailingZeros(),
			actualPrice3.stripTrailingZeros());
		_assertPrices(
			expectedPrice4.stripTrailingZeros(),
			actualPrice4.stripTrailingZeros());
		_assertPrices(
			expectedPrice5.stripTrailingZeros(),
			actualPrice5.stripTrailingZeros());
	}

	@Test
	public void testCouponCodeDiscount() throws Exception {
		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount that needs a coupon code to be available to use"
		).when(
			"I insert the correct coupon code in my context"
		).then(
			"The coupon discount is available to use and is applied for " +
				"calculating the final price"
		);

		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		BigDecimal taxRate = BigDecimal.valueOf(rate);

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		commerceOrder.setCommerceCurrencyId(
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder);

		CPInstance cpInstance = CPTestUtil.addCPInstance();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition);

		cpInstance.setPrice(BigDecimal.valueOf(35));

		_cpInstanceLocalService.updateCPInstance(cpInstance);

		String couponCode = StringUtil.randomString();

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addCouponDiscount(
				_group.getGroupId(), 10, couponCode,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, commerceOrder);

		commerceOrder = _commerceOrderLocalService.applyCouponCode(
			commerceOrder.getCommerceOrderId(), couponCode, commerceContext);

		commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, commerceOrder);

		_commerceOrders.add(commerceOrder);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		BigDecimal actualPrice = BigDecimal.ZERO;
		BigDecimal discountPrice = BigDecimal.ZERO;

		if (commerceProductPrice != null) {
			CommerceMoney finalPrice = commerceProductPrice.getFinalPrice();

			actualPrice = finalPrice.getPrice();

			CommerceDiscountValue discountValue =
				commerceProductPrice.getDiscountValue();

			CommerceMoney discountAmount = discountValue.getDiscountAmount();

			discountPrice = discountAmount.getPrice();
		}

		BigDecimal discountAmountWithoutTaxAmount =
			CommerceTaxTestUtil.getPriceWithoutTaxAmount(
				commerceDiscount.getLevel1(), taxRate,
				RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		Assert.assertEquals(
			discountAmountWithoutTaxAmount.stripTrailingZeros(),
			discountPrice.stripTrailingZeros());

		BigDecimal price = cpInstance.getPrice();

		BigDecimal expectedPrice = CommerceTaxTestUtil.getPriceWithTaxAmount(
			price, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		expectedPrice = expectedPrice.subtract(commerceDiscount.getLevel1());

		expectedPrice = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
			expectedPrice, taxRate,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));

		_assertPrices(
			expectedPrice.stripTrailingZeros(),
			actualPrice.stripTrailingZeros());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private void _assertPrices(BigDecimal expectedPrice, BigDecimal actualPrice)
		throws PortalException {

		_commerceCurrency.setFormatPattern("$###,##0.00", LocaleUtil.US);

		String formattedExpectedPrice = _commercePriceFormatter.format(
			_commerceCurrency, expectedPrice, LocaleUtil.US);

		String formattedActualPrice = _commercePriceFormatter.format(
			_commerceCurrency, actualPrice, LocaleUtil.US);

		Assert.assertEquals(formattedExpectedPrice, formattedActualPrice);
	}

	private BigDecimal _subtractPercentage(
		BigDecimal base, BigDecimal percentage) {

		BigDecimal percentageValue = percentage.divide(_ONE_HUNDRED);

		BigDecimal calculatedPercentage = percentageValue.multiply(base);

		return base.subtract(calculatedPercentage);
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private List<CommerceOrder> _commerceOrders;

	@Inject
	private CommercePriceFormatter _commercePriceFormatter;

	@Inject(filter = "commerce.price.calculation.key=v1.0")
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@DeleteAfterTestRun
	private CommerceTaxMethod _commerceTaxMethod;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}