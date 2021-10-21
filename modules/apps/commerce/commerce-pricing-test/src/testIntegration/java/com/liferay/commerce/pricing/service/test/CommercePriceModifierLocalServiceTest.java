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

package com.liferay.commerce.pricing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.exception.CommercePriceModifierAmountException;
import com.liferay.commerce.pricing.exception.CommercePriceModifierTargetException;
import com.liferay.commerce.pricing.exception.CommercePriceModifierTitleException;
import com.liferay.commerce.pricing.exception.CommercePriceModifierTypeException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalService;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelLocalService;
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

import java.util.List;

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
public class CommercePriceModifierLocalServiceTest {

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
			_user.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	@Test
	public void testCreatePriceModifierWithCategoryTarget() throws Exception {
		frutillaRule.scenario(
			"A price modifier with category target is created for a price list"
		).given(
			"A catalog with at least a product and a price list"
		).and(
			"A category containing at least a product"
		).when(
			"The price modifier is created"
		).then(
			"The price modifiers has the category as a target"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_user.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_user.getGroupId(), assetVocabulary.getVocabularyId());

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_CATEGORIES,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier1.getGroupId(),
			commercePriceModifier1.getCommercePriceModifierId(),
			AssetCategory.class.getName(), assetCategory.getCategoryId());

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierLocalService.getCommercePriceModifiers(
				commercePriceList.getCommercePriceListId());

		CommercePriceModifier commercePriceModifier2 =
			commercePriceModifiers.get(0);

		Assert.assertEquals(
			commercePriceModifier1.getCommercePriceModifierId(),
			commercePriceModifier2.getCommercePriceModifierId());

		Assert.assertEquals(
			CommercePriceModifierConstants.TARGET_CATEGORIES,
			commercePriceModifier2.getTarget());

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier2.getCommercePriceModifierId(),
				AssetCategory.class.getName());

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRels.get(0);

		Assert.assertEquals(
			assetCategory.getCategoryId(),
			commercePriceModifierRel.getClassPK());
	}

	@Test(expected = CommercePriceModifierAmountException.class)
	public void testCreatePriceModifierWithInvalidAmount() throws Exception {
		frutillaRule.scenario(
			"Creating a price modifier with invalid amount will raise an " +
				"exception"
		).given(
			"A catalog and a price list"
		).when(
			"I try to create a price modifier with invalid amount"
		).then(
			"An exception shall be raised"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CommercePriceModifierTestUtil.addCommercePriceModifier(
			catalog.getGroupId(),
			CommercePriceModifierConstants.TARGET_PRODUCT_GROUPS,
			commercePriceList.getCommercePriceListId(),
			CommercePriceModifierConstants.MODIFIER_TYPE_PERCENTAGE, null,
			true);
	}

	@Test(expected = CommercePriceModifierTargetException.class)
	public void testCreatePriceModifierWithInvalidTarget() throws Exception {
		frutillaRule.scenario(
			"Creating a price modifier with invalid target will raise an " +
				"exception"
		).given(
			"A catalog and a price list"
		).when(
			"I try to create a price modifier with invalid target"
		).then(
			"An exception shall be raised"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifierTestUtil.addCommercePriceModifier(
			catalog.getGroupId(), RandomTestUtil.randomString(),
			commercePriceList.getCommercePriceListId(),
			CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount, true);
	}

	@Test(expected = CommercePriceModifierTypeException.class)
	public void testCreatePriceModifierWithInvalidType() throws Exception {
		frutillaRule.scenario(
			"Creating a price modifier with invalid type will raise an " +
				"exception"
		).given(
			"A catalog and a price list"
		).when(
			"I try to create a price modifier with invalid type"
		).then(
			"An exception shall be raised"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifierTestUtil.addCommercePriceModifier(
			catalog.getGroupId(),
			CommercePriceModifierConstants.TARGET_PRODUCT_GROUPS,
			commercePriceList.getCommercePriceListId(),
			RandomTestUtil.randomString(), amount, true);
	}

	@Test
	public void testCreatePriceModifierWithNoTarget() throws Exception {
		frutillaRule.scenario(
			"A price modifier with no targets is created for a price list"
		).given(
			"A catalog and a price list"
		).when(
			"The price modifier is created with no target"
		).then(
			"The price modifier has target catalog"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierLocalService.getCommercePriceModifiers(
				commercePriceList.getCommercePriceListId());

		CommercePriceModifier commercePriceModifier2 =
			commercePriceModifiers.get(0);

		Assert.assertEquals(
			commercePriceModifier1.getCommercePriceModifierId(),
			commercePriceModifier2.getCommercePriceModifierId());

		Assert.assertEquals(
			CommercePriceModifierConstants.TARGET_CATALOG,
			commercePriceModifier2.getTarget());
	}

	@Test(expected = CommercePriceModifierTitleException.class)
	public void testCreatePriceModifierWithNullTitle() throws Exception {
		frutillaRule.scenario(
			"Creating a price modifier with null of empty title will raise " +
				"an exception"
		).given(
			"A catalog and a price list"
		).when(
			"I try to create a price modifier with no title"
		).then(
			"An exception shall be raised"
		);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceModifierTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifierTestUtil.addCommercePriceModifier(
			catalog.getGroupId(), null,
			CommercePriceModifierConstants.TARGET_PRODUCTS,
			commercePriceList.getCommercePriceListId(),
			CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount, true);
	}

	@Test
	public void testCreatePriceModifierWithPricingClassTarget()
		throws Exception {

		frutillaRule.scenario(
			"A price modifier with pricing class target is created for a " +
				"price list"
		).given(
			"A catalog with at least a product and a price list"
		).and(
			"A pricing class containing at least a product"
		).when(
			"The price modifier is created"
		).then(
			"The price modifiers has the pricing class as a target"
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

		CommercePricingClass commercePricingClass =
			_commercePricingClassLocalService.addCommercePricingClass(
				_user.getUserId(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		_commercePricingClassCPDefinitionRelLocalService.
			addCommercePricingClassCPDefinitionRel(
				commercePricingClass.getCommercePricingClassId(),
				cpDefinition.getCPDefinitionId(), _serviceContext);

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_PRODUCT_GROUPS,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier1.getGroupId(),
			commercePriceModifier1.getCommercePriceModifierId(),
			CommercePricingClass.class.getName(),
			commercePricingClass.getCommercePricingClassId());

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierLocalService.getCommercePriceModifiers(
				commercePriceList.getCommercePriceListId());

		CommercePriceModifier commercePriceModifier2 =
			commercePriceModifiers.get(0);

		Assert.assertEquals(
			commercePriceModifier1.getCommercePriceModifierId(),
			commercePriceModifier2.getCommercePriceModifierId());

		Assert.assertEquals(
			CommercePriceModifierConstants.TARGET_PRODUCT_GROUPS,
			commercePriceModifier2.getTarget());

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier2.getCommercePriceModifierId(),
				CommercePricingClass.class.getName());

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRels.get(0);

		Assert.assertEquals(
			commercePricingClass.getCommercePricingClassId(),
			commercePriceModifierRel.getClassPK());

		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels =
				_commercePricingClassCPDefinitionRelLocalService.
					getCommercePricingClassCPDefinitionRels(
						commercePricingClass.getCommercePricingClassId());

		Assert.assertEquals(
			commercePricingClassCPDefinitionRels.toString(), 1,
			commercePricingClassCPDefinitionRels.size());

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				commercePricingClassCPDefinitionRels.get(0);

		Assert.assertEquals(
			cpDefinition.getCPDefinitionId(),
			commercePricingClassCPDefinitionRel.getCPDefinitionId());
	}

	@Test
	public void testCreatePriceModifierWithProductTarget() throws Exception {
		frutillaRule.scenario(
			"A price modifier with product target is created for a price list"
		).given(
			"A catalog with at least a product and a price list"
		).when(
			"The price modifier is created"
		).then(
			"The price modifiers has the catalog product as a target"
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

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_PRODUCTS,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier1.getGroupId(),
			commercePriceModifier1.getCommercePriceModifierId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierLocalService.getCommercePriceModifiers(
				commercePriceList.getCommercePriceListId());

		CommercePriceModifier commercePriceModifier2 =
			commercePriceModifiers.get(0);

		Assert.assertEquals(
			commercePriceModifier1.getCommercePriceModifierId(),
			commercePriceModifier2.getCommercePriceModifierId());

		Assert.assertEquals(
			CommercePriceModifierConstants.TARGET_PRODUCTS,
			commercePriceModifier2.getTarget());

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier2.getCommercePriceModifierId(),
				CPDefinition.class.getName());

		Assert.assertEquals(
			commercePriceModifierRels.toString(), 1,
			commercePriceModifierRels.size());

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRels.get(0);

		Assert.assertEquals(
			cpDefinition.getCPDefinitionId(),
			commercePriceModifierRel.getClassPK());
	}

	@Test
	public void testDeletePriceModifier() throws Exception {
		frutillaRule.scenario(
			"When a price modifier is deleted also its rels are deleted"
		).given(
			"A price modifier with a specific target"
		).when(
			"I delete all modifiers attached to a price list"
		).then(
			"The price modifier is deleted and the old rels are deleted"
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

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_PRODUCTS,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier1.getGroupId(),
			commercePriceModifier1.getCommercePriceModifierId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier1.getCommercePriceModifierId(),
				CPDefinition.class.getName());

		Assert.assertEquals(
			commercePriceModifierRels.toString(), 1,
			commercePriceModifierRels.size());

		_commercePriceModifierLocalService.
			deleteCommercePriceModifiersByCommercePriceListId(
				commercePriceList.getCommercePriceListId());

		CommercePriceModifier commercePriceModifier2 =
			_commercePriceModifierLocalService.fetchCommercePriceModifier(
				commercePriceModifier1.getCommercePriceModifierId());

		Assert.assertNull(commercePriceModifier2);

		commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier1.getCommercePriceModifierId(),
				CPDefinition.class.getName());

		Assert.assertEquals(
			commercePriceModifierRels.toString(), 0,
			commercePriceModifierRels.size());
	}

	@Test
	public void testUpdatePriceModifierTarget() throws Exception {
		frutillaRule.scenario(
			"It is possible to update the target of a price modifier"
		).given(
			"A price modifier with a specific target"
		).when(
			"I change the target of the modifier"
		).then(
			"The price modifier target is updated and the old rels are deleted"
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

		BigDecimal amount = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CommercePriceModifier commercePriceModifier1 =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				catalog.getGroupId(),
				CommercePriceModifierConstants.TARGET_PRODUCTS,
				commercePriceList.getCommercePriceListId(),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, amount,
				true);

		CommercePriceModifierTestUtil.addCommercePriceModifierRel(
			commercePriceModifier1.getGroupId(),
			commercePriceModifier1.getCommercePriceModifierId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier1.getCommercePriceModifierId(),
				CPDefinition.class.getName());

		Assert.assertEquals(
			commercePriceModifierRels.toString(), 1,
			commercePriceModifierRels.size());

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRels.get(0);

		Assert.assertEquals(
			cpDefinition.getCPDefinitionId(),
			commercePriceModifierRel.getClassPK());

		CommercePriceModifier commercePriceModifier2 =
			CommercePriceModifierTestUtil.updateCommercePriceModifier(
				catalog.getGroupId(),
				commercePriceModifier1.getCommercePriceModifierId(),
				CommercePriceModifierConstants.TARGET_PRODUCT_GROUPS);

		commercePriceModifierRels =
			_commercePriceModifierRelLocalService.getCommercePriceModifierRels(
				commercePriceModifier2.getCommercePriceModifierId(),
				CPDefinition.class.getName());

		Assert.assertEquals(
			commercePriceModifierRels.toString(), 0,
			commercePriceModifierRels.size());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static User _user;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommercePriceModifierLocalService
		_commercePriceModifierLocalService;

	@Inject
	private CommercePriceModifierRelLocalService
		_commercePriceModifierRelLocalService;

	@Inject
	private CommercePricingClassCPDefinitionRelLocalService
		_commercePricingClassCPDefinitionRelLocalService;

	@Inject
	private CommercePricingClassLocalService _commercePricingClassLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}