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

package com.liferay.commerce.price.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.price.CommerceProductOptionValueRelativePriceRequest;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.CommerceProductTestUtil;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.test.util.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CommerceProductPriceCalculationTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(), PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_group = GroupTestUtil.addGroup();

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_user.getCompanyId(), _user.getGroupId(), _user.getUserId());

		_commerceCatalog = _commerceCatalogLocalService.addCommerceCatalog(
			RandomTestUtil.randomString(), _commerceCurrency.getCode(),
			LocaleUtil.US.getDisplayLanguage(), null, _serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		List<CPDefinition> cpDefinitions =
			_cpDefinitionLocalService.getCPDefinitions(
				_commerceCatalog.getGroupId(), WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinition cpDefinition : cpDefinitions) {
			_cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}

		_commerceDiscountLocalService.deleteCommerceDiscounts(
			_user.getCompanyId());

		_commerceAccountLocalService.deleteCommerceAccount(
			_commerceAccount.getCommerceAccountId());

		_commerceCatalogLocalService.deleteCommerceCatalog(_commerceCatalog);
	}

	@Test
	public void testCalculatePriceDynamicOptionSKU() throws Exception {
		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values linked to SKUs with price type " +
				"dynamic"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(150));

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(200));

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal expectedPrice = cpInstance.getPrice();

		expectedPrice = expectedPrice.add(cpInstance1.getPrice());

		expectedPrice = expectedPrice.add(cpInstance2.getPrice());

		expectedPrice = expectedPrice.add(cpInstance3.getPrice());

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceDynamicOptionSKUWithPromo() throws Exception {
		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values linked to SKUs with price type " +
				"dynamic"
		).and(
			"Some linked SKUs have a promo price"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(150);
		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		cpInstance2.setPrice(cpInstancePrice2);
		cpInstance2.setPromoPrice(cpInstancePromoPrice2);

		_cpInstanceLocalService.updateCPInstance(cpInstance2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(200);

		cpInstance3.setPrice(cpInstancePrice3);

		_cpInstanceLocalService.updateCPInstance(cpInstance3);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal expectedPrice = cpInstance.getPrice();

		expectedPrice = expectedPrice.add(cpInstance1.getPrice());

		expectedPrice = expectedPrice.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.add(cpInstancePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceDynamicOptionSKUWithQuantities()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values linked to SKUs with price type " +
				"dynamic"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(150));

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(200));

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		int quantity1 = 1;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, quantity1));

		int quantity2 = 3;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, quantity2));

		int quantity3 = 10;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, quantity3));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal expectedPrice = cpInstance.getPrice();

		BigDecimal cpInstancePrice1 = cpInstance1.getPrice();

		expectedPrice = expectedPrice.add(
			cpInstancePrice1.multiply(BigDecimal.valueOf(quantity1)));

		BigDecimal cpInstancePrice2 = cpInstance2.getPrice();

		expectedPrice = expectedPrice.add(
			cpInstancePrice2.multiply(BigDecimal.valueOf(quantity2)));

		BigDecimal cpInstancePrice3 = cpInstance3.getPrice();

		expectedPrice = expectedPrice.add(
			cpInstancePrice3.multiply(BigDecimal.valueOf(quantity3)));

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithDiscount()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values some linked to SKU and some with " +
				"price type dynamic"
		).and(
			"The product has a discount applied on it"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		double discountAmount = 10;

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommerceDiscountTestUtil.addFixedCommerceDiscount(
			_group.getGroupId(), discountAmount,
			CommerceDiscountConstants.TARGET_PRODUCTS,
			cpDefinition.getCPDefinitionId());

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(150);
		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		cpInstance2.setPrice(cpInstancePrice2);
		cpInstance2.setPromoPrice(cpInstancePromoPrice2);

		_cpInstanceLocalService.updateCPInstance(cpInstance2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(200);

		cpInstance3.setPrice(cpInstancePrice3);

		_cpInstanceLocalService.updateCPInstance(cpInstance3);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.subtract(
			BigDecimal.valueOf(discountAmount));

		expectedPrice = expectedPrice.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithOptionDiscount()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values some linked to SKU and some with " +
				"price type dynamic"
		).and(
			"Some linked SKUs have discount applied on"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(150);
		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		cpInstance2.setPrice(cpInstancePrice2);
		cpInstance2.setPromoPrice(cpInstancePromoPrice2);

		_cpInstanceLocalService.updateCPInstance(cpInstance2);

		CPDefinition cpDefinition = cpInstance2.getCPDefinition();

		double discountAmount = 10;

		CommerceDiscountTestUtil.addFixedCommerceDiscount(
			_group.getGroupId(), discountAmount,
			CommerceDiscountConstants.TARGET_PRODUCTS,
			cpDefinition.getCPDefinitionId());

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(200);

		cpInstance3.setPrice(cpInstancePrice3);

		_cpInstanceLocalService.updateCPInstance(cpInstance3);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.subtract(
			BigDecimal.valueOf(discountAmount));

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithPriceList()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values some linked to SKU and some with " +
				"price type dynamic"
		).and(
			"Some linked SKUs have their price defined in a price list"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(300));

		CPDefinition cpDefinition = cpInstance1.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		BigDecimal cpInstancePriceEntryPrice1 = BigDecimal.valueOf(100);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cProduct.getCProductId(), cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), StringPool.BLANK,
			cpInstancePriceEntryPrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(150);
		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		cpInstance2.setPrice(cpInstancePrice2);
		cpInstance2.setPromoPrice(cpInstancePromoPrice2);

		_cpInstanceLocalService.updateCPInstance(cpInstance2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(200));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(
			cpInstancePriceEntryPrice1);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithPromo() throws Exception {
		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values some linked to SKU and some with " +
				"price type dynamic"
		).and(
			"Some linked SKUs have a promo price"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(150);
		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		cpInstance2.setPrice(cpInstancePrice2);
		cpInstance2.setPromoPrice(cpInstancePromoPrice2);

		_cpInstanceLocalService.updateCPInstance(cpInstance2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(200));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceStaticOptionNoSKU() throws Exception {
		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values not linked to SKUs with price " +
				"type static"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		BigDecimal optionValuePrice1 = BigDecimal.valueOf(10);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice1, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				1));

		BigDecimal optionValuePrice2 = BigDecimal.valueOf(15);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice2, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				1));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice3, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(optionValuePrice1);

		expectedPrice = expectedPrice.add(optionValuePrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceStaticOptionNoSKUWithQuantities()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values not linked to SKUs with price " +
				"type static"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		BigDecimal optionValuePrice1 = BigDecimal.valueOf(10);

		int quantity1 = 1;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice1, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				quantity1));

		BigDecimal optionValuePrice2 = BigDecimal.valueOf(15);

		int quantity2 = 11;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice2, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				quantity2));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		int quantity3 = 10;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice3, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				quantity3));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(optionValuePrice1);

		expectedPrice = expectedPrice.add(optionValuePrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceStaticOptionSKU() throws Exception {
		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values linked to SKUs with price type " +
				"static"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(150));

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(200));

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		BigDecimal optionValuePrice1 = BigDecimal.valueOf(10);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice1,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		BigDecimal optionValuePrice2 = BigDecimal.valueOf(15);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice2,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(optionValuePrice1);

		expectedPrice = expectedPrice.add(optionValuePrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceStaticOptionWithSKUWithQuantities()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values with price type static"
		).and(
			"an option value linked to a cpInstance"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned and the quantity of the linked " +
				"option is taken into account"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		BigDecimal optionValuePrice1 = BigDecimal.valueOf(10);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(100));

		int quantity1 = 10;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance1.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice1,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, quantity1));

		BigDecimal optionValuePrice2 = BigDecimal.valueOf(15);

		int quantity2 = 11;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice2, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				quantity2));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		int quantity3 = 10;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				0, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				optionValuePrice3, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				quantity3));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		BigDecimal expectedPrice = cpInstancePrice.add(
			optionValuePrice1.multiply(BigDecimal.valueOf(quantity1)));

		expectedPrice = expectedPrice.add(optionValuePrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceWithParentPriceEntry() throws Exception {
		frutillaRule.scenario(
			"The price of a product is calculated"
		).given(
			"A product with a price entry in a parent price list"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(35));

		CommercePriceList parentPriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), 0.0);

		BigDecimal cpInstancePrice = BigDecimal.valueOf(50);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cProduct.getCProductId(), cpInstance.getCPInstanceUuid(),
			parentPriceList.getCommercePriceListId(), StringPool.BLANK,
			cpInstancePrice);

		CommerceAccount commerceAccount1 =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		CommercePriceList childPriceList =
			CommercePriceListTestUtil.addAccountPriceList(
				_commerceCatalog.getGroupId(),
				commerceAccount1.getCommerceAccountId(),
				CommercePriceListConstants.TYPE_PRICE_LIST);

		childPriceList.setParentCommercePriceListId(
			parentPriceList.getCommercePriceListId());

		_commercePriceListLocalService.updateCommercePriceList(childPriceList);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, commerceAccount1, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		Assert.assertEquals(
			parentPriceList.getCommercePriceListId(),
			commerceProductPrice.getCommercePriceListId());

		CommerceMoney finalPrice = commerceProductPrice.getFinalPrice();

		Assert.assertEquals(cpInstancePrice, finalPrice.getPrice());
	}

	@Test
	public void testGetCPDefinitionMinimumPrice() throws Exception {
		frutillaRule.scenario(
			"Calculate a minimum price of a product definition based on its " +
				"options configuration"
		).given(
			"A product with a dynamic, static and null priceType options"
		).and(
			"each option has two option values"
		).when(
			"The minimum price of the product is calculated"
		).then(
			"The correct minimum price is returned"
		);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(20));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(30));

		CPOption dynamicPriceTypeCPOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			cpInstance1.getCPInstanceId(),
			dynamicPriceTypeCPOption.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
			BigDecimal.valueOf(50), 1, true, true, _serviceContext);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			cpInstance2.getCPInstanceId(),
			dynamicPriceTypeCPOption.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
			BigDecimal.valueOf(100), 1, true, true, _serviceContext);

		CPOption staticPriceTypeCPOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel staticPriceTypeOptionValueRel1 =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(), 0,
				staticPriceTypeCPOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				BigDecimal.valueOf(5), 1, true, true, _serviceContext);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(), 0,
			staticPriceTypeCPOption.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
			BigDecimal.valueOf(10), 1, true, true, _serviceContext);

		CPOption nullPriceTypeCPOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(), 0,
			nullPriceTypeCPOption.getCPOptionId(), null, null, 0, false, true,
			_serviceContext);

		_cpInstanceLocalService.buildCPInstances(
			bundleCPDefinition.getCPDefinitionId(), _serviceContext);

		BigDecimal bundleCPInstanceMinPrice = BigDecimal.TEN;

		_updateBundleCPInstancePrices(
			bundleCPDefinition.getCPInstances(), bundleCPInstanceMinPrice);

		CommerceMoney bundleMinimumPrice =
			_commerceProductPriceCalculation.getCPDefinitionMinimumPrice(
				bundleCPDefinition.getCPDefinitionId(),
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null));

		BigDecimal expectedMinPrice = bundleCPInstanceMinPrice;

		expectedMinPrice = expectedMinPrice.add(cpInstance1.getPrice());

		expectedMinPrice = expectedMinPrice.add(
			staticPriceTypeOptionValueRel1.getPrice());

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(expectedMinPrice),
			CPTestUtil.stripTrailingZeros(bundleMinimumPrice.getPrice()));
	}

	@Test
	public void testGetCPDefinitionMinimumPriceWithNonRequiredOption()
		throws Exception {

		frutillaRule.scenario(
			"Calculate a minimum price of a product definition based on its " +
				"options configuration"
		).given(
			"A product with a required and non-required, static priceType " +
				"options"
		).when(
			"The minimum price of the product is calculated"
		).then(
			"Non required option value price should be ignored"
		).and(
			"the correct minimum price is returned"
		);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption requiredCPOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(), 0,
			requiredCPOption.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
			BigDecimal.valueOf(50), 1, false, false, _serviceContext);

		CPOption nonRequiredCPOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel requiredCPDefinitionOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(), 0,
				nonRequiredCPOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				BigDecimal.valueOf(100), 1, true, true, _serviceContext);

		_cpInstanceLocalService.buildCPInstances(
			bundleCPDefinition.getCPDefinitionId(), _serviceContext);

		BigDecimal bundleCPInstanceMinPrice = BigDecimal.TEN;

		_updateBundleCPInstancePrices(
			bundleCPDefinition.getCPInstances(), bundleCPInstanceMinPrice);

		CommerceMoney bundleMinimumPrice =
			_commerceProductPriceCalculation.getCPDefinitionMinimumPrice(
				bundleCPDefinition.getCPDefinitionId(),
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null));

		BigDecimal expectedMinPrice = bundleCPInstanceMinPrice;

		expectedMinPrice = expectedMinPrice.add(
			requiredCPDefinitionOptionValueRel.getPrice());

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(expectedMinPrice),
			CPTestUtil.stripTrailingZeros(bundleMinimumPrice.getPrice()));
	}

	@Test
	public void testGetCPDefinitionOptionValueRelativePrice1()
		throws Exception {

		frutillaRule.scenario(
			"Calculate a relative price of a product option value in a " +
				"product bundle"
		).given(
			"Price-contributing static option, and two option values"
		).and(
			"each option value defines a SKU of a different price"
		).and(
			"one option value is selected in the UI"
		).when(
			"The relative price of the non-selected option value is calculated"
		).and(
			"the relative price of the selected option value is calculated"
		).then(
			"The correct relative prices are returned"
		);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption bundleOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel bundleOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(), 0,
				bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				BigDecimal.valueOf(50), 1, false, true, _serviceContext);

		CPDefinitionOptionValueRel selectedBundleOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(), 0,
				bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				BigDecimal.valueOf(100), 1, false, true, _serviceContext);

		CPInstance selectedCPInstance =
			CPTestUtil.addCPDefinitionCPInstanceWithPrice(
				bundleCPDefinition.getCPDefinitionId(),
				_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
					selectedBundleOptionValueRel),
				BigDecimal.valueOf(200));

		CPInstance cpInstance = CPTestUtil.addCPDefinitionCPInstanceWithPrice(
			bundleCPDefinition.getCPDefinitionId(),
			_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
				bundleOptionValueRel),
			BigDecimal.valueOf(300));

		CommerceProductOptionValueRelativePriceRequest.Builder builder =
			new CommerceProductOptionValueRelativePriceRequest.Builder(
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null),
				bundleOptionValueRel);

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						cpInstance.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).selectedCPInstanceId(
						selectedCPInstance.getCPInstanceId()
					).selectedCPInstanceMinQuantity(
						1
					).selectedCPDefinitionOptionValueRel(
						selectedBundleOptionValueRel
					).build());

		BigDecimal bundleOptionValueRelPrice = bundleOptionValueRel.getPrice();

		BigDecimal expectedRelativePrice = bundleOptionValueRelPrice.subtract(
			selectedBundleOptionValueRel.getPrice());

		BigDecimal cpInstancePrice = cpInstance.getPrice();

		expectedRelativePrice = expectedRelativePrice.add(
			cpInstancePrice.subtract(selectedCPInstance.getPrice()));

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(expectedRelativePrice),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));

		builder = new CommerceProductOptionValueRelativePriceRequest.Builder(
			new TestCommerceContext(
				_commerceCurrency, null, _user, _group, _commerceAccount, null),
			selectedBundleOptionValueRel);

		commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						selectedCPInstance.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).selectedCPInstanceId(
						selectedCPInstance.getCPInstanceId()
					).selectedCPInstanceMinQuantity(
						1
					).selectedCPDefinitionOptionValueRel(
						selectedBundleOptionValueRel
					).build());

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(BigDecimal.ZERO),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));
	}

	@Test
	public void testGetCPDefinitionOptionValueRelativePrice2()
		throws Exception {

		frutillaRule.scenario(
			"Calculate a relative price of a product option value in a " +
				"product bundle"
		).given(
			"Price-contributing static option, and two option values"
		).and(
			"each option value defines a SKU of a different price"
		).and(
			"no option value is selected in the UI"
		).when(
			"The relative prices of the option values are calculated"
		).then(
			"The correct relative prices are returned"
		);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption bundleOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel bundleOptionValueRel1 =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(), 0,
				bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				BigDecimal.valueOf(50), 1, false, true, _serviceContext);

		CPDefinitionOptionValueRel bundleOptionValueRel2 =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(), 0,
				bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC,
				BigDecimal.valueOf(100), 1, false, true, _serviceContext);

		CPInstance cpInstance1 = CPTestUtil.addCPDefinitionCPInstanceWithPrice(
			bundleCPDefinition.getCPDefinitionId(),
			_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
				bundleOptionValueRel1),
			BigDecimal.valueOf(200));

		CPInstance cpInstance2 = CPTestUtil.addCPDefinitionCPInstanceWithPrice(
			bundleCPDefinition.getCPDefinitionId(),
			_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
				bundleOptionValueRel2),
			BigDecimal.valueOf(300));

		CommerceProductOptionValueRelativePriceRequest.Builder builder =
			new CommerceProductOptionValueRelativePriceRequest.Builder(
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null),
				bundleOptionValueRel1);

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						cpInstance1.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).build());

		BigDecimal expectedRelativePrice = cpInstance1.getPrice();

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(
				expectedRelativePrice.add(bundleOptionValueRel1.getPrice())),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));

		builder = new CommerceProductOptionValueRelativePriceRequest.Builder(
			new TestCommerceContext(
				_commerceCurrency, null, _user, _group, _commerceAccount, null),
			bundleOptionValueRel2);

		commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						cpInstance2.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).build());

		expectedRelativePrice = cpInstance2.getPrice();

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(
				expectedRelativePrice.add(bundleOptionValueRel2.getPrice())),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));
	}

	@Test
	public void testGetCPDefinitionOptionValueRelativePrice3()
		throws Exception {

		frutillaRule.scenario(
			"Calculate a relative price of a product option value in a " +
				"product bundle"
		).given(
			"Price-contributing dynamic option, and two option values"
		).and(
			"each option value defines a SKU of a different price"
		).and(
			"no option value is selected in the UI"
		).when(
			"The relative prices of the option values are calculated"
		).then(
			"The correct relative prices are returned"
		);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(20));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(30));

		CPOption bundleOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel bundleOptionValueRel1 =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(),
				cpInstance1.getCPInstanceId(), bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
				BigDecimal.valueOf(50), 1, false, true, _serviceContext);

		CPDefinitionOptionValueRel bundleOptionValueRel2 =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(),
				cpInstance2.getCPInstanceId(), bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
				BigDecimal.valueOf(100), 1, false, true, _serviceContext);

		CPInstance bundleCPInstance1 =
			CPTestUtil.addCPDefinitionCPInstanceWithPrice(
				bundleCPDefinition.getCPDefinitionId(),
				_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
					bundleOptionValueRel1),
				BigDecimal.valueOf(200));

		CPInstance bundleCPInstance2 =
			CPTestUtil.addCPDefinitionCPInstanceWithPrice(
				bundleCPDefinition.getCPDefinitionId(),
				_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
					bundleOptionValueRel2),
				BigDecimal.valueOf(300));

		CommerceProductOptionValueRelativePriceRequest.Builder builder =
			new CommerceProductOptionValueRelativePriceRequest.Builder(
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null),
				bundleOptionValueRel1);

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						bundleCPInstance1.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).build());

		BigDecimal expectedRelativePrice = cpInstance1.getPrice();

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(
				expectedRelativePrice.add(bundleCPInstance1.getPrice())),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));

		builder = new CommerceProductOptionValueRelativePriceRequest.Builder(
			new TestCommerceContext(
				_commerceCurrency, null, _user, _group, _commerceAccount, null),
			bundleOptionValueRel2);

		commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						bundleCPInstance2.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).build());

		expectedRelativePrice = cpInstance2.getPrice();

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(
				expectedRelativePrice.add(bundleCPInstance2.getPrice())),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));
	}

	@Test
	public void testGetCPDefinitionOptionValueRelativePrice4()
		throws Exception {

		frutillaRule.scenario(
			"Calculate a relative price of a product option value in a " +
				"product bundle"
		).given(
			"Price-contributing dynamic option, and two option values"
		).and(
			"each option value defines a SKU of a different price"
		).and(
			"one option value is selected in the UI"
		).when(
			"The relative price of the non-selected option value is calculated"
		).and(
			"The relative price of the selected option value is calculated"
		).then(
			"The correct relative prices are returned"
		);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(20));

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId(), BigDecimal.valueOf(30));

		CPOption bundleOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel bundleOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(),
				cpInstance1.getCPInstanceId(), bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
				BigDecimal.valueOf(50), 1, false, true, _serviceContext);

		CPDefinitionOptionValueRel selectedBundleOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(),
				bundleCPDefinition.getCPDefinitionId(),
				cpInstance2.getCPInstanceId(), bundleOption.getCPOptionId(),
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
				BigDecimal.valueOf(100), 1, false, true, _serviceContext);

		CPInstance selectedBundleCPInstance =
			CPTestUtil.addCPDefinitionCPInstanceWithPrice(
				bundleCPDefinition.getCPDefinitionId(),
				_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
					selectedBundleOptionValueRel),
				BigDecimal.valueOf(200));

		CPInstance bundleCPInstance =
			CPTestUtil.addCPDefinitionCPInstanceWithPrice(
				bundleCPDefinition.getCPDefinitionId(),
				_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
					bundleOptionValueRel),
				BigDecimal.valueOf(300));

		CommerceProductOptionValueRelativePriceRequest.Builder builder =
			new CommerceProductOptionValueRelativePriceRequest.Builder(
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null),
				bundleOptionValueRel);

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						bundleCPInstance.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).selectedCPDefinitionOptionValueRel(
						selectedBundleOptionValueRel
					).selectedCPInstanceId(
						selectedBundleCPInstance.getCPInstanceId()
					).selectedCPInstanceMinQuantity(
						1
					).build());

		BigDecimal optionValuePrice = cpInstance1.getPrice();
		BigDecimal selectedOptionValuePrice = cpInstance2.getPrice();

		BigDecimal expectedRelativePrice = optionValuePrice.subtract(
			selectedOptionValuePrice);

		BigDecimal bundleCPInstancePrice = bundleCPInstance.getPrice();

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(
				expectedRelativePrice.add(
					bundleCPInstancePrice.subtract(
						selectedBundleCPInstance.getPrice()))),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));

		builder = new CommerceProductOptionValueRelativePriceRequest.Builder(
			new TestCommerceContext(
				_commerceCurrency, null, _user, _group, _commerceAccount, null),
			selectedBundleOptionValueRel);

		commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						selectedBundleCPInstance.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).selectedCPInstanceId(
						selectedBundleCPInstance.getCPInstanceId()
					).selectedCPInstanceMinQuantity(
						1
					).selectedCPDefinitionOptionValueRel(
						selectedBundleOptionValueRel
					).build());

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(BigDecimal.ZERO),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));
	}

	@Test
	public void testGetCPDefinitionOptionValueRelativePrice5()
		throws Exception {

		frutillaRule.scenario(
			"Calculate a relative price of a product option value"
		).given(
			"PriceType == null product option, and related option value"
		).and(
			"a option value defines a SKU with price"
		).when(
			"The relative prices of the option value is calculated"
		).then(
			"BigDecimal.ZERO relative price should be returned"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption cpOption = CPTestUtil.addCPOption(
			_commerceCatalog.getGroupId(),
			CPTestUtil.getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionValueRel nullPriceTypeOptionValueRel =
			CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
				_commerceCatalog.getGroupId(), cpDefinition.getCPDefinitionId(),
				0, cpOption.getCPOptionId(), null, null, 0, false, true,
				_serviceContext);

		CPInstance cpInstance = CPTestUtil.addCPDefinitionCPInstanceWithPrice(
			cpDefinition.getCPDefinitionId(),
			_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
				nullPriceTypeOptionValueRel),
			BigDecimal.valueOf(200));

		CommerceProductOptionValueRelativePriceRequest.Builder builder =
			new CommerceProductOptionValueRelativePriceRequest.Builder(
				new TestCommerceContext(
					_commerceCurrency, null, _user, _group, _commerceAccount,
					null),
				nullPriceTypeOptionValueRel);

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.
				getCPDefinitionOptionValueRelativePrice(
					builder.cpInstanceId(
						cpInstance.getCPInstanceId()
					).cpInstanceMinQuantity(
						1
					).build());

		Assert.assertEquals(
			CPTestUtil.stripTrailingZeros(BigDecimal.ZERO),
			CPTestUtil.stripTrailingZeros(commerceMoney.getPrice()));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private Map<Long, List<Long>>
		_getCPDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		Map<Long, List<Long>> map = new HashMap<>();

		map.put(
			cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
			Arrays.asList(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId()));

		return map;
	}

	private void _updateBundleCPInstancePrices(
		List<CPInstance> cpInstances, BigDecimal minPrice) {

		for (int i = 0; i < cpInstances.size(); i++) {
			CPInstance cpInstance = cpInstances.get(i);

			cpInstance.setPrice(minPrice.add(BigDecimal.valueOf(i)));

			_cpInstanceLocalService.updateCPInstance(cpInstance);
		}
	}

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject(filter = "commerce.price.calculation.key=v1.0")
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Inject
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}