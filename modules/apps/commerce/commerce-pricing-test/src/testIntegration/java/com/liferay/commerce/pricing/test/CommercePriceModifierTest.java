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

package com.liferay.commerce.pricing.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.modifier.CommercePriceModifierHelper;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelLocalService;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.commerce.pricing.test.util.CommercePriceModifierTestUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.frutilla.FrutillaRule;

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
public class CommercePriceModifierTest {

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
			_user.getCompanyId(), _user.getGroupId(), _user.getUserId());
	}

	@Test
	public void testAbsoluteModifierTargetPricingClass() throws Exception {
		frutillaRule.scenario(
			"A type absolute price modifier modifies the price of the items " +
				"in a pricing class"
		).given(
			"A catalog with at least two product and one price list"
		).and(
			"A pricing class containing the two products"
		).and(
			"A type absolute price modifier targeting the pricing class"
		).when(
			"The price modifier is applied to the products"
		).then(
			"The original price of the two products is modified"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance1 = CPTestUtil.addCPInstance(catalog.getGroupId());

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CPInstance cpInstance2 = CPTestUtil.addCPInstance(catalog.getGroupId());

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		CommercePricingClass commercePricingClass =
			_commercePricingClassLocalService.addCommercePricingClass(
				_user.getUserId(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		_commercePricingClassCPDefinitionRelLocalService.
			addCommercePricingClassCPDefinitionRel(
				commercePricingClass.getCommercePricingClassId(),
				cpDefinition1.getCPDefinitionId(), _serviceContext);

		_commercePricingClassCPDefinitionRelLocalService.
			addCommercePricingClassCPDefinitionRel(
				commercePricingClass.getCommercePricingClassId(),
				cpDefinition2.getCPDefinitionId(), _serviceContext);

		BigDecimal price1 = BigDecimal.valueOf(30);

		BigDecimal price2 = BigDecimal.valueOf(20);

		BigDecimal amount = BigDecimal.valueOf(10);

		CommercePriceEntry commercePriceEntry1 = _addCommercePriceEntry(
			"", cpDefinition1.getCProductId(), cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), price1);

		CommercePriceEntry commercePriceEntry2 = _addCommercePriceEntry(
			"", cpDefinition2.getCProductId(), cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), price2);

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_PRODUCT_GROUPS,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_FIXED_AMOUNT,
				amount, true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier.getGroupId(),
			commercePriceModifier.getCommercePriceModifierId(),
			CommercePricingClass.class.getName(),
			commercePricingClass.getCommercePricingClassId());

		CommerceMoney priceCommerceMoney1 =
			commercePriceEntry1.getPriceCommerceMoney(
				_commerceCurrency.getCommerceCurrencyId());

		BigDecimal modifiedPrice1 =
			_commercePriceModifierHelper.applyCommercePriceModifier(
				commercePriceList.getCommercePriceListId(),
				cpInstance1.getCPDefinitionId(), priceCommerceMoney1);

		CommerceMoney priceCommerceMoney2 =
			commercePriceEntry2.getPriceCommerceMoney(
				_commerceCurrency.getCommerceCurrencyId());

		BigDecimal modifiedPrice2 =
			_commercePriceModifierHelper.applyCommercePriceModifier(
				commercePriceList.getCommercePriceListId(),
				cpInstance2.getCPDefinitionId(), priceCommerceMoney2);

		CommerceMoney finalCommerceMoney1 = _commerceMoneyFactory.create(
			_commerceCurrency, price1.add(amount));
		CommerceMoney finalCommerceMoney2 = _commerceMoneyFactory.create(
			_commerceCurrency, price2.add(amount));

		BigDecimal expectedPrice1 = finalCommerceMoney1.getPrice();

		BigDecimal expectedPrice2 = finalCommerceMoney2.getPrice();

		Assert.assertEquals(
			expectedPrice1.stripTrailingZeros(),
			modifiedPrice1.stripTrailingZeros());

		Assert.assertEquals(
			expectedPrice2.stripTrailingZeros(),
			modifiedPrice2.stripTrailingZeros());
	}

	@Test
	public void testMultiplePercentageModifierTargetCategory()
		throws Exception {

		frutillaRule.scenario(
			"When multiple type percentage price modifiers are defined the " +
				"one that produces the lowest price is applied to the targets"
		).given(
			"A catalog with at least two product and one price list"
		).and(
			"A category containing the two products"
		).and(
			"Two type percentage price modifiers targeting the category"
		).when(
			"The price modifier is applied to the products in the category"
		).then(
			"The original price of the two products is modified"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0,
				_commerceCurrency.getCommerceCurrencyId());

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_user.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_user.getGroupId(), assetVocabulary.getVocabularyId());

		long[] assetCategoryIds = {assetCategory.getCategoryId()};

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId(), assetCategoryIds);

		CPDefinition cpDefinition1 = cpInstance1.getCPDefinition();

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPDefinition cpDefinition2 = cpInstance2.getCPDefinition();

		BigDecimal price1 = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		BigDecimal price2 = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceEntry commercePriceEntry1 = _addCommercePriceEntry(
			"", cpDefinition1.getCProductId(), cpInstance1.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), price1);

		CommercePriceEntry commercePriceEntry2 = _addCommercePriceEntry(
			"", cpDefinition2.getCProductId(), cpInstance2.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), price2);

		BigDecimal amount1 = BigDecimal.valueOf(-10);

		BigDecimal amount2 = BigDecimal.valueOf(-1);

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_CATEGORIES,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_PERCENTAGE,
				amount1, true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier1.getGroupId(),
			commercePriceModifier1.getCommercePriceModifierId(),
			AssetCategory.class.getName(), assetCategory.getCategoryId());

		CommercePriceModifier commercePriceModifier2 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_CATEGORIES,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_PERCENTAGE,
				amount2, true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier2.getGroupId(),
			commercePriceModifier2.getCommercePriceModifierId(),
			AssetCategory.class.getName(), assetCategory.getCategoryId());

		CommerceMoney priceCommerceMoney1 =
			commercePriceEntry1.getPriceCommerceMoney(
				_commerceCurrency.getCommerceCurrencyId());

		CommerceMoney priceCommerceMoney2 =
			commercePriceEntry2.getPriceCommerceMoney(
				_commerceCurrency.getCommerceCurrencyId());

		BigDecimal modifiedPrice1 =
			_commercePriceModifierHelper.applyCommercePriceModifier(
				commercePriceList.getCommercePriceListId(),
				cpInstance1.getCPDefinitionId(), priceCommerceMoney1);

		BigDecimal modifiedPrice2 =
			_commercePriceModifierHelper.applyCommercePriceModifier(
				commercePriceList.getCommercePriceListId(),
				cpInstance2.getCPDefinitionId(), priceCommerceMoney2);

		RoundingMode roundingMode = RoundingMode.valueOf(
			_commerceCurrency.getRoundingMode());

		MathContext mathContext1 = new MathContext(
			modifiedPrice1.precision(), roundingMode);

		BigDecimal finalPrice1 = price1.multiply(
			BigDecimal.valueOf(0.9), mathContext1);

		CommerceMoney finalCommerceMoney1 = _commerceMoneyFactory.create(
			_commerceCurrency, finalPrice1);

		BigDecimal expectedPrice1 = finalCommerceMoney1.getPrice();

		BigDecimal expectedPrice2 = priceCommerceMoney2.getPrice();

		Assert.assertEquals(
			expectedPrice1.stripTrailingZeros(),
			modifiedPrice1.stripTrailingZeros());

		Assert.assertEquals(
			expectedPrice2.stripTrailingZeros(),
			modifiedPrice2.stripTrailingZeros());
	}

	@Test
	public void testOverridePriceTargetProduct() throws Exception {
		frutillaRule.scenario(
			"A type override price modifier overrides the price of a product"
		).given(
			"A catalog with at least one product and one price list"
		).and(
			"A type override price modifier targeting the product"
		).when(
			"The price modifier is applied to the product"
		).then(
			"The original price is overridden by the modifier"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance = CPTestUtil.addCPInstance(catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		BigDecimal price = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceEntry commercePriceEntry = _addCommercePriceEntry(
			"", cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), price);

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_PRODUCTS,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier.getGroupId(),
			commercePriceModifier.getCommercePriceModifierId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		CommerceMoney priceCommerceMoney =
			commercePriceEntry.getPriceCommerceMoney(
				_commerceCurrency.getCommerceCurrencyId());

		BigDecimal finalPrice =
			_commercePriceModifierHelper.applyCommercePriceModifier(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPDefinitionId(), priceCommerceMoney);

		RoundingMode roundingMode = RoundingMode.valueOf(
			_commerceCurrency.getRoundingMode());

		MathContext mathContext = new MathContext(
			finalPrice.precision(), roundingMode);

		BigDecimal expectedPrice = amount.round(mathContext);

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private CommercePriceEntry _addCommercePriceEntry(
			String externalReferenceCode, long cpProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price)
		throws Exception {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId());

		return CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			externalReferenceCode, cpProductId, cpInstanceUuid,
			commercePriceListId, price, BigDecimal.ZERO, serviceContext);
	}

	private static User _user;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Inject
	private CommercePriceModifierHelper _commercePriceModifierHelper;

	@Inject
	private CommercePricingClassCPDefinitionRelLocalService
		_commercePricingClassCPDefinitionRelLocalService;

	@Inject
	private CommercePricingClassLocalService _commercePricingClassLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}