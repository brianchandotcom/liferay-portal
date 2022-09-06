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

package com.liferay.headless.commerce.admin.pricing.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.pricing.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.pricing.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.DiscountAccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.DiscountCategoryResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.DiscountProductResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.DiscountResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.DiscountRuleResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.PriceEntryResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.PriceListAccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.PriceListResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0.TierPriceResourceImpl;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountAccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountCategoryResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountProductResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountRuleResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.PriceEntryResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.PriceListAccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.PriceListResource;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.TierPriceResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Zoltán Takács
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDiscountResourceComponentServiceObjects(
			_discountResourceComponentServiceObjects);
		Mutation.setDiscountAccountGroupResourceComponentServiceObjects(
			_discountAccountGroupResourceComponentServiceObjects);
		Mutation.setDiscountCategoryResourceComponentServiceObjects(
			_discountCategoryResourceComponentServiceObjects);
		Mutation.setDiscountProductResourceComponentServiceObjects(
			_discountProductResourceComponentServiceObjects);
		Mutation.setDiscountRuleResourceComponentServiceObjects(
			_discountRuleResourceComponentServiceObjects);
		Mutation.setPriceEntryResourceComponentServiceObjects(
			_priceEntryResourceComponentServiceObjects);
		Mutation.setPriceListResourceComponentServiceObjects(
			_priceListResourceComponentServiceObjects);
		Mutation.setPriceListAccountGroupResourceComponentServiceObjects(
			_priceListAccountGroupResourceComponentServiceObjects);
		Mutation.setTierPriceResourceComponentServiceObjects(
			_tierPriceResourceComponentServiceObjects);

		Query.setDiscountResourceComponentServiceObjects(
			_discountResourceComponentServiceObjects);
		Query.setDiscountAccountGroupResourceComponentServiceObjects(
			_discountAccountGroupResourceComponentServiceObjects);
		Query.setDiscountCategoryResourceComponentServiceObjects(
			_discountCategoryResourceComponentServiceObjects);
		Query.setDiscountProductResourceComponentServiceObjects(
			_discountProductResourceComponentServiceObjects);
		Query.setDiscountRuleResourceComponentServiceObjects(
			_discountRuleResourceComponentServiceObjects);
		Query.setPriceEntryResourceComponentServiceObjects(
			_priceEntryResourceComponentServiceObjects);
		Query.setPriceListResourceComponentServiceObjects(
			_priceListResourceComponentServiceObjects);
		Query.setPriceListAccountGroupResourceComponentServiceObjects(
			_priceListAccountGroupResourceComponentServiceObjects);
		Query.setTierPriceResourceComponentServiceObjects(
			_tierPriceResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Pricing";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-pricing-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createDiscount",
			new ObjectValuePair<>(DiscountResourceImpl.class, "postDiscount"));
		_resourceMethodPairs.put(
			"mutation#createDiscountBatch",
			new ObjectValuePair<>(
				DiscountResourceImpl.class, "postDiscountBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountByExternalReferenceCode",
			new ObjectValuePair<>(
				DiscountResourceImpl.class,
				"deleteDiscountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchDiscountByExternalReferenceCode",
			new ObjectValuePair<>(
				DiscountResourceImpl.class,
				"patchDiscountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscount",
			new ObjectValuePair<>(
				DiscountResourceImpl.class, "deleteDiscount"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountBatch",
			new ObjectValuePair<>(
				DiscountResourceImpl.class, "deleteDiscountBatch"));
		_resourceMethodPairs.put(
			"mutation#patchDiscount",
			new ObjectValuePair<>(DiscountResourceImpl.class, "patchDiscount"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountAccountGroup",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"deleteDiscountAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountAccountGroupBatch",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"deleteDiscountAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountAccountGroup",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountAccountGroup",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"postDiscountIdDiscountAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountAccountGroupBatch",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"postDiscountIdDiscountAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountCategory",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class, "deleteDiscountCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountCategoryBatch",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class,
				"deleteDiscountCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountCategory",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountCategory"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountCategory",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class,
				"postDiscountIdDiscountCategory"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountCategoryBatch",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class,
				"postDiscountIdDiscountCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountProduct",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class, "deleteDiscountProduct"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountProductBatch",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class,
				"deleteDiscountProductBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountProduct",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountProduct"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountProduct",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class,
				"postDiscountIdDiscountProduct"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountProductBatch",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class,
				"postDiscountIdDiscountProductBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountRule"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class, "deleteDiscountRule"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountRuleBatch",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class, "deleteDiscountRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#patchDiscountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class, "patchDiscountRule"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class, "postDiscountIdDiscountRule"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountRuleBatch",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"postDiscountIdDiscountRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceEntryByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"deletePriceEntryByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchPriceEntryByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"patchPriceEntryByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deletePriceEntry",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class, "deletePriceEntry"));
		_resourceMethodPairs.put(
			"mutation#deletePriceEntryBatch",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class, "deletePriceEntryBatch"));
		_resourceMethodPairs.put(
			"mutation#patchPriceEntry",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class, "patchPriceEntry"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceEntry",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceEntry"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceEntry",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class, "postPriceListIdPriceEntry"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceEntryBatch",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"postPriceListIdPriceEntryBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceList",
			new ObjectValuePair<>(
				PriceListResourceImpl.class, "postPriceList"));
		_resourceMethodPairs.put(
			"mutation#createPriceListBatch",
			new ObjectValuePair<>(
				PriceListResourceImpl.class, "postPriceListBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceListResourceImpl.class,
				"deletePriceListByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchPriceListByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceListResourceImpl.class,
				"patchPriceListByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deletePriceList",
			new ObjectValuePair<>(
				PriceListResourceImpl.class, "deletePriceList"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListBatch",
			new ObjectValuePair<>(
				PriceListResourceImpl.class, "deletePriceListBatch"));
		_resourceMethodPairs.put(
			"mutation#patchPriceList",
			new ObjectValuePair<>(
				PriceListResourceImpl.class, "patchPriceList"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListAccountGroup",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"deletePriceListAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListAccountGroupBatch",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"deletePriceListAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceListAccountGroup",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceListAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListAccountGroup",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"postPriceListIdPriceListAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListAccountGroupBatch",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"postPriceListIdPriceListAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceEntryByExternalReferenceCodeTierPrice",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class,
				"postPriceEntryByExternalReferenceCodeTierPrice"));
		_resourceMethodPairs.put(
			"mutation#createPriceEntryIdTierPrice",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class, "postPriceEntryIdTierPrice"));
		_resourceMethodPairs.put(
			"mutation#createPriceEntryIdTierPriceBatch",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class, "postPriceEntryIdTierPriceBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteTierPriceByExternalReferenceCode",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class,
				"deleteTierPriceByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchTierPriceByExternalReferenceCode",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class,
				"patchTierPriceByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteTierPrice",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class, "deleteTierPrice"));
		_resourceMethodPairs.put(
			"mutation#deleteTierPriceBatch",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class, "deleteTierPriceBatch"));
		_resourceMethodPairs.put(
			"mutation#patchTierPrice",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class, "patchTierPrice"));
		_resourceMethodPairs.put(
			"query#discounts",
			new ObjectValuePair<>(
				DiscountResourceImpl.class, "getDiscountsPage"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCode",
			new ObjectValuePair<>(
				DiscountResourceImpl.class,
				"getDiscountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#discount",
			new ObjectValuePair<>(DiscountResourceImpl.class, "getDiscount"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountAccountGroups",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountAccountGroups",
			new ObjectValuePair<>(
				DiscountAccountGroupResourceImpl.class,
				"getDiscountIdDiscountAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountCategories",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountCategoriesPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountCategories",
			new ObjectValuePair<>(
				DiscountCategoryResourceImpl.class,
				"getDiscountIdDiscountCategoriesPage"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountProducts",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountProductsPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountProducts",
			new ObjectValuePair<>(
				DiscountProductResourceImpl.class,
				"getDiscountIdDiscountProductsPage"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountRules",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountRulesPage"));
		_resourceMethodPairs.put(
			"query#discountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class, "getDiscountRule"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountRules",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"getDiscountIdDiscountRulesPage"));
		_resourceMethodPairs.put(
			"query#priceEntryByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"getPriceEntryByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#priceEntry",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class, "getPriceEntry"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceEntries",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceEntriesPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceEntries",
			new ObjectValuePair<>(
				PriceEntryResourceImpl.class,
				"getPriceListIdPriceEntriesPage"));
		_resourceMethodPairs.put(
			"query#priceLists",
			new ObjectValuePair<>(
				PriceListResourceImpl.class, "getPriceListsPage"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceListResourceImpl.class,
				"getPriceListByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#priceList",
			new ObjectValuePair<>(PriceListResourceImpl.class, "getPriceList"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceListAccountGroup",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceListAccountGroupPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceListAccountGroups",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"getPriceListIdPriceListAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#priceEntryByExternalReferenceCodeTierPrices",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class,
				"getPriceEntryByExternalReferenceCodeTierPricesPage"));
		_resourceMethodPairs.put(
			"query#priceEntryIdTierPrices",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class, "getPriceEntryIdTierPricesPage"));
		_resourceMethodPairs.put(
			"query#tierPriceByExternalReferenceCode",
			new ObjectValuePair<>(
				TierPriceResourceImpl.class,
				"getTierPriceByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#tierPrice",
			new ObjectValuePair<>(TierPriceResourceImpl.class, "getTierPrice"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountResource>
		_discountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountAccountGroupResource>
		_discountAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountCategoryResource>
		_discountCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountProductResource>
		_discountProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountRuleResource>
		_discountRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceEntryResource>
		_priceEntryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListResource>
		_priceListResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListAccountGroupResource>
		_priceListAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TierPriceResource>
		_tierPriceResourceComponentServiceObjects;

}