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
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalService;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelLocalService;
import com.liferay.commerce.product.CommerceProductTestUtil;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Calendar;
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
public class CommerceProductPriceCalculationV2Test {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_group.getCompanyId());
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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

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

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.add(cpInstancePrice2);

		expectedPrice = expectedPrice.add(cpInstancePrice3);

		expectedPrice = expectedPrice.add(cpInstancePrice4);

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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		CommercePriceList commercePromotion =
			CommercePriceListTestUtil.addPromotion(catalog.getGroupId(), 0.0);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePromotion.getCommercePriceListId(), cpInstancePromoPrice2,
			false, null, null, null, null, true, true);

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

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

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.add(cpInstancePrice2);

		expectedPrice = expectedPrice.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.add(cpInstancePrice4);

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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		int quantity1 = 1;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, quantity1));

		int quantity2 = 3;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, quantity2));

		int quantity3 = 10;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, quantity3));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.add(
			cpInstancePrice2.multiply(BigDecimal.valueOf(quantity1)));

		expectedPrice = expectedPrice.add(
			cpInstancePrice3.multiply(BigDecimal.valueOf(quantity2)));

		expectedPrice = expectedPrice.add(
			cpInstancePrice4.multiply(BigDecimal.valueOf(quantity3)));

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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		double discountAmount = 10;

		CommerceDiscountTestUtil.addFixedCommerceDiscount(
			_group.getGroupId(), discountAmount,
			CommerceDiscountConstants.TARGET_PRODUCTS,
			cpDefinition1.getCPDefinitionId());

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		CommercePriceList commercePromotion =
			CommercePriceListTestUtil.addPromotion(catalog.getGroupId(), 0.0);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePromotion.getCommercePriceListId(), cpInstancePromoPrice2,
			false, null, null, null, null, true, true);

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.subtract(
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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		CommercePriceList commercePromotion =
			CommercePriceListTestUtil.addPromotion(catalog.getGroupId(), 0.0);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePromotion.getCommercePriceListId(), cpInstancePromoPrice2,
			true, null, null, null, null, true, true);

		double discountAmount = 10;

		CommerceDiscountTestUtil.addFixedCommerceDiscount(
			_group.getGroupId(), discountAmount,
			CommerceDiscountConstants.TARGET_PRODUCTS,
			cpDefinition3.getCPDefinitionId());

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.subtract(
			BigDecimal.valueOf(discountAmount));

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithOptionLineDiscount()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values some linked to SKU and some with " +
				"price type dynamic"
		).and(
			"Some linked SKUs have discount defined at price entry level " +
				"applied on"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		BigDecimal level1 = BigDecimal.valueOf(10);
		BigDecimal level2 = BigDecimal.valueOf(10);
		BigDecimal level3 = BigDecimal.valueOf(10);
		BigDecimal level4 = BigDecimal.valueOf(10);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3, false,
			level1, level2, level3, level4, true, true);

		double discountAmount = 10;

		CommerceDiscountTestUtil.addFixedCommerceDiscount(
			_group.getGroupId(), discountAmount,
			CommerceDiscountConstants.TARGET_PRODUCTS,
			cpDefinition3.getCPDefinitionId());

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct3 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal discountedPercentage1 = level1.divide(_HUNDRED);

		discountedPercentage1 = _ONE.subtract(discountedPercentage1);

		BigDecimal discountedPercentage2 = level2.divide(_HUNDRED);

		discountedPercentage2 = _ONE.subtract(discountedPercentage2);

		BigDecimal discountedPercentage3 = level3.divide(_HUNDRED);

		discountedPercentage3 = _ONE.subtract(discountedPercentage3);

		BigDecimal discountedPercentage4 = level4.divide(_HUNDRED);

		discountedPercentage4 = _ONE.subtract(discountedPercentage4);

		BigDecimal discountedCPInstancePrice2 = cpInstancePrice3.multiply(
			discountedPercentage1);

		discountedCPInstancePrice2 = discountedCPInstancePrice2.multiply(
			discountedPercentage2);

		discountedCPInstancePrice2 = discountedCPInstancePrice2.multiply(
			discountedPercentage3);

		discountedCPInstancePrice2 = discountedCPInstancePrice2.multiply(
			discountedPercentage4);

		BigDecimal expectedPrice = cpInstancePrice1.add(
			discountedCPInstancePrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithPriceModifier()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values some linked to SKU and some with " +
				"price type dynamic"
		).and(
			"Some linked SKUs have their price modifiers applied on"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList basePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		BigDecimal cpInstanceBasePrice1 = BigDecimal.valueOf(100);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			basePriceList.getCommercePriceListId(), cpInstanceBasePrice1);

		BigDecimal modifierAmount = BigDecimal.valueOf(-10);

		CommercePriceModifier commercePriceModifier = _addCommercePriceModifier(
			commercePriceList.getGroupId(),
			CommercePriceModifierConstants.TARGET_PRODUCTS,
			commercePriceList.getCommercePriceListId(),
			CommercePriceModifierConstants.MODIFIER_TYPE_FIXED_AMOUNT,
			modifierAmount, true);

		_commercePriceModifierRelLocalService.addCommercePriceModifierRel(
			commercePriceModifier.getCommercePriceModifierId(),
			CPDefinition.class.getName(), cpDefinition2.getCPDefinitionId(),
			_serviceContext);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		CommercePriceList commercePromotion =
			CommercePriceListTestUtil.addPromotion(catalog.getGroupId(), 0.0);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePromotion.getCommercePriceListId(), cpInstancePromoPrice2,
			false, null, null, null, null, true, true);

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(200);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstanceBasePrice1.add(modifierAmount);

		expectedPrice = expectedPrice.add(cpInstancePrice1);

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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice3 = BigDecimal.valueOf(150);

		CPDefinition cpDefinition3 = cpInstance3.getCPDefinition();

		CProduct cProduct3 = cpDefinition3.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice3);

		BigDecimal cpInstancePromoPrice2 = BigDecimal.valueOf(100);

		CommercePriceList commercePromotion =
			CommercePriceListTestUtil.addPromotion(catalog.getGroupId(), 0.0);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition3.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePromotion.getCommercePriceListId(), cpInstancePromoPrice2,
			false, null, null, null, null, true, true);

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice4 = BigDecimal.valueOf(200);

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		CPDefinition cpDefinition4 = cpInstance4.getCPDefinition();

		CProduct cProduct4 = cpDefinition4.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct4.getCProductId(),
			cpInstance4.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice4);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 1));

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.add(cpInstancePromoPrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Test
	public void testCalculatePriceMixedOptionSKUWithTierPrice()
		throws Exception {

		frutillaRule.scenario(
			"The price of a product with 3 option values selected is calculated"
		).given(
			"A product with 3 option values linked to SKUs with price type " +
				"static"
		).and(
			"some linked SKUs have tier price entries"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned given the quantity"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				StringPool.BLANK, cProduct2.getCProductId(),
				cpInstance2.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		BigDecimal price5 = BigDecimal.valueOf(40);

		CommercePriceEntryTestUtil.addCommerceTierPriceEntry(
			commercePriceEntry.getCommercePriceEntryId(), StringPool.BLANK,
			price5, 5, false, false, null, null, null, null, true, true);

		BigDecimal price10 = BigDecimal.valueOf(30);

		CommercePriceEntryTestUtil.addCommerceTierPriceEntry(
			commercePriceEntry.getCommercePriceEntryId(), StringPool.BLANK,
			price10, 10, false, false, null, null, null, null, true, true);

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC, 7));

		BigDecimal optionValuePrice2 = BigDecimal.valueOf(15);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice2,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal tier1OptionValuePrice1 = cpInstancePrice2.multiply(
			BigDecimal.valueOf(4));
		BigDecimal tier2OptionValuePrice1 = price5.multiply(
			BigDecimal.valueOf(3));

		BigDecimal expectedPrice = cpInstancePrice1.add(tier1OptionValuePrice1);

		expectedPrice = expectedPrice.add(tier2OptionValuePrice1);

		expectedPrice = expectedPrice.add(optionValuePrice2);

		expectedPrice = expectedPrice.add(optionValuePrice3);

		Assert.assertEquals(
			expectedPrice,
			finalPrice.setScale(expectedPrice.scale(), RoundingMode.HALF_UP));
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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice = BigDecimal.valueOf(35);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice);

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

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice = BigDecimal.valueOf(35);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice);

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

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice = BigDecimal.valueOf(35);

		CPDefinition cpDefinition = cpInstance1.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPInstance cpInstance3 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPInstance cpInstance4 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		BigDecimal optionValuePrice1 = BigDecimal.valueOf(10);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance2.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice1,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		BigDecimal optionValuePrice2 = BigDecimal.valueOf(15);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance3.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice2,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		BigDecimal optionValuePrice3 = BigDecimal.valueOf(20);

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpInstance4.getCPInstanceId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), optionValuePrice3,
				CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC, 1));

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

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
			"an option value linked to a cpInstance1"
		).when(
			"The price of the product is calculated"
		).then(
			"The correct price is returned and the quantity of the linked " +
				"option is taken into account"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice1 = BigDecimal.valueOf(35);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CProduct cProduct1 = cpDefinition1.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct1.getCProductId(),
			cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice1);

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		BigDecimal optionValuePrice1 = BigDecimal.valueOf(10);

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice2 = BigDecimal.valueOf(100);

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CProduct cProduct2 = cpDefinition2.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct2.getCProductId(),
			cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstancePrice2);

		int quantity1 = 10;

		commerceOptionValues.add(
			CommerceProductTestUtil.getCommerceOptionValue(
				cpDefinition2.getCPDefinitionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
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
			cpInstance1.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(1);
		commerceProductPriceRequest.setSecure(true);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			commerceOptionValues);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceCommerceMoney.getPrice();

		BigDecimal expectedPrice = cpInstancePrice1.add(
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

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList parentPriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		BigDecimal cpInstancePrice = BigDecimal.valueOf(35);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cProduct.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			parentPriceList.getCommercePriceListId(), cpInstancePrice);

		CommerceAccount commerceAccount1 =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		CommercePriceList childPriceList =
			CommercePriceListTestUtil.addAccountPriceList(
				catalog.getGroupId(), commerceAccount1.getCommerceAccountId(),
				CommercePriceListConstants.TYPE_PRICE_LIST);

		childPriceList.setParentCommercePriceListId(
			parentPriceList.getCommercePriceListId());

		_commercePriceListLocalService.updateCommercePriceList(childPriceList);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, null, _user, _group, _commerceAccount, null);

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

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		Assert.assertEquals(
			cpInstancePrice, finalPriceCommerceMoney.getPrice());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private CommercePriceModifier _addCommercePriceModifier(
			long groupId, String target, long commercePriceListId, String type,
			BigDecimal amount, boolean neverExpire)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			_user.getTimeZone());

		return _commercePriceModifierLocalService.addCommercePriceModifier(
			groupId, RandomTestUtil.randomString(), target, commercePriceListId,
			type, amount, 0.0, true, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), neverExpire, serviceContext);
	}

	private static final BigDecimal _HUNDRED = BigDecimal.valueOf(100);

	private static final BigDecimal _ONE = BigDecimal.ONE;

	private static User _user;

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CommercePriceModifierLocalService
		_commercePriceModifierLocalService;

	@Inject
	private CommercePriceModifierRelLocalService
		_commercePriceModifierRelLocalService;

	@Inject
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}