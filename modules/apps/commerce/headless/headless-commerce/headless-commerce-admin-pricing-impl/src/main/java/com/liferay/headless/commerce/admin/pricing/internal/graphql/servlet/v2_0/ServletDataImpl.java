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

package com.liferay.headless.commerce.admin.pricing.internal.graphql.servlet.v2_0;

import com.liferay.headless.commerce.admin.pricing.internal.graphql.mutation.v2_0.Mutation;
import com.liferay.headless.commerce.admin.pricing.internal.graphql.query.v2_0.Query;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.AccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.AccountResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.CategoryResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.ChannelResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountAccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountAccountResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountCategoryResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountChannelResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountProductGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountProductResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountRuleResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.DiscountSkuResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.OrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceEntryResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceListAccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceListAccountResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceListChannelResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceListDiscountResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceListOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceListResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceModifierCategoryResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceModifierProductGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceModifierProductResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.PriceModifierResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.ProductGroupResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.ProductResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.SkuResourceImpl;
import com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0.TierPriceResourceImpl;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.AccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.AccountResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.CategoryResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ChannelResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountAccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountAccountResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountCategoryResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountChannelResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountOrderTypeResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountProductGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountProductResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountRuleResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountSkuResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceEntryResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListAccountGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListAccountResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListChannelResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListDiscountResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListOrderTypeResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceModifierCategoryResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceModifierProductGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceModifierProductResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceModifierResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ProductGroupResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ProductResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.SkuResource;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.TierPriceResource;
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
		Mutation.setDiscountAccountResourceComponentServiceObjects(
			_discountAccountResourceComponentServiceObjects);
		Mutation.setDiscountAccountGroupResourceComponentServiceObjects(
			_discountAccountGroupResourceComponentServiceObjects);
		Mutation.setDiscountCategoryResourceComponentServiceObjects(
			_discountCategoryResourceComponentServiceObjects);
		Mutation.setDiscountChannelResourceComponentServiceObjects(
			_discountChannelResourceComponentServiceObjects);
		Mutation.setDiscountOrderTypeResourceComponentServiceObjects(
			_discountOrderTypeResourceComponentServiceObjects);
		Mutation.setDiscountProductResourceComponentServiceObjects(
			_discountProductResourceComponentServiceObjects);
		Mutation.setDiscountProductGroupResourceComponentServiceObjects(
			_discountProductGroupResourceComponentServiceObjects);
		Mutation.setDiscountRuleResourceComponentServiceObjects(
			_discountRuleResourceComponentServiceObjects);
		Mutation.setDiscountSkuResourceComponentServiceObjects(
			_discountSkuResourceComponentServiceObjects);
		Mutation.setPriceEntryResourceComponentServiceObjects(
			_priceEntryResourceComponentServiceObjects);
		Mutation.setPriceListResourceComponentServiceObjects(
			_priceListResourceComponentServiceObjects);
		Mutation.setPriceListAccountResourceComponentServiceObjects(
			_priceListAccountResourceComponentServiceObjects);
		Mutation.setPriceListAccountGroupResourceComponentServiceObjects(
			_priceListAccountGroupResourceComponentServiceObjects);
		Mutation.setPriceListChannelResourceComponentServiceObjects(
			_priceListChannelResourceComponentServiceObjects);
		Mutation.setPriceListDiscountResourceComponentServiceObjects(
			_priceListDiscountResourceComponentServiceObjects);
		Mutation.setPriceListOrderTypeResourceComponentServiceObjects(
			_priceListOrderTypeResourceComponentServiceObjects);
		Mutation.setPriceModifierResourceComponentServiceObjects(
			_priceModifierResourceComponentServiceObjects);
		Mutation.setPriceModifierCategoryResourceComponentServiceObjects(
			_priceModifierCategoryResourceComponentServiceObjects);
		Mutation.setPriceModifierProductResourceComponentServiceObjects(
			_priceModifierProductResourceComponentServiceObjects);
		Mutation.setPriceModifierProductGroupResourceComponentServiceObjects(
			_priceModifierProductGroupResourceComponentServiceObjects);
		Mutation.setTierPriceResourceComponentServiceObjects(
			_tierPriceResourceComponentServiceObjects);

		Query.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Query.setAccountGroupResourceComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects);
		Query.setCategoryResourceComponentServiceObjects(
			_categoryResourceComponentServiceObjects);
		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setDiscountResourceComponentServiceObjects(
			_discountResourceComponentServiceObjects);
		Query.setDiscountAccountResourceComponentServiceObjects(
			_discountAccountResourceComponentServiceObjects);
		Query.setDiscountAccountGroupResourceComponentServiceObjects(
			_discountAccountGroupResourceComponentServiceObjects);
		Query.setDiscountCategoryResourceComponentServiceObjects(
			_discountCategoryResourceComponentServiceObjects);
		Query.setDiscountChannelResourceComponentServiceObjects(
			_discountChannelResourceComponentServiceObjects);
		Query.setDiscountOrderTypeResourceComponentServiceObjects(
			_discountOrderTypeResourceComponentServiceObjects);
		Query.setDiscountProductResourceComponentServiceObjects(
			_discountProductResourceComponentServiceObjects);
		Query.setDiscountProductGroupResourceComponentServiceObjects(
			_discountProductGroupResourceComponentServiceObjects);
		Query.setDiscountRuleResourceComponentServiceObjects(
			_discountRuleResourceComponentServiceObjects);
		Query.setDiscountSkuResourceComponentServiceObjects(
			_discountSkuResourceComponentServiceObjects);
		Query.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Query.setPriceEntryResourceComponentServiceObjects(
			_priceEntryResourceComponentServiceObjects);
		Query.setPriceListResourceComponentServiceObjects(
			_priceListResourceComponentServiceObjects);
		Query.setPriceListAccountResourceComponentServiceObjects(
			_priceListAccountResourceComponentServiceObjects);
		Query.setPriceListAccountGroupResourceComponentServiceObjects(
			_priceListAccountGroupResourceComponentServiceObjects);
		Query.setPriceListChannelResourceComponentServiceObjects(
			_priceListChannelResourceComponentServiceObjects);
		Query.setPriceListDiscountResourceComponentServiceObjects(
			_priceListDiscountResourceComponentServiceObjects);
		Query.setPriceListOrderTypeResourceComponentServiceObjects(
			_priceListOrderTypeResourceComponentServiceObjects);
		Query.setPriceModifierResourceComponentServiceObjects(
			_priceModifierResourceComponentServiceObjects);
		Query.setPriceModifierCategoryResourceComponentServiceObjects(
			_priceModifierCategoryResourceComponentServiceObjects);
		Query.setPriceModifierProductResourceComponentServiceObjects(
			_priceModifierProductResourceComponentServiceObjects);
		Query.setPriceModifierProductGroupResourceComponentServiceObjects(
			_priceModifierProductGroupResourceComponentServiceObjects);
		Query.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
		Query.setProductGroupResourceComponentServiceObjects(
			_productGroupResourceComponentServiceObjects);
		Query.setSkuResourceComponentServiceObjects(
			_skuResourceComponentServiceObjects);
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
		return "/headless-commerce-admin-pricing-graphql/v2_0";
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
			"mutation#deleteDiscountAccount",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class, "deleteDiscountAccount"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountAccountBatch",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class,
				"deleteDiscountAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountAccount",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountAccount"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountAccount",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class,
				"postDiscountIdDiscountAccount"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountAccountBatch",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class,
				"postDiscountIdDiscountAccountBatch"));
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
			"mutation#deleteDiscountChannel",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class, "deleteDiscountChannel"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountChannelBatch",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class,
				"deleteDiscountChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountChannel",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountChannel"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountChannel",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class,
				"postDiscountIdDiscountChannel"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountChannelBatch",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class,
				"postDiscountIdDiscountChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountOrderType",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"deleteDiscountOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountOrderTypeBatch",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"deleteDiscountOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountOrderType",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountOrderType"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountOrderType",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"postDiscountIdDiscountOrderType"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountOrderTypeBatch",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"postDiscountIdDiscountOrderTypeBatch"));
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
			"mutation#deleteDiscountProductGroup",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"deleteDiscountProductGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountProductGroupBatch",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"deleteDiscountProductGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountProductGroup",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountProductGroup"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountProductGroup",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"postDiscountIdDiscountProductGroup"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountProductGroupBatch",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"postDiscountIdDiscountProductGroupBatch"));
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
			"mutation#createDiscountByExternalReferenceCodeDiscountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountRule"));
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
			"mutation#deleteDiscountSku",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class, "deleteDiscountSku"));
		_resourceMethodPairs.put(
			"mutation#deleteDiscountSkuBatch",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class, "deleteDiscountSkuBatch"));
		_resourceMethodPairs.put(
			"mutation#createDiscountByExternalReferenceCodeDiscountSku",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class,
				"postDiscountByExternalReferenceCodeDiscountSku"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountSku",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class, "postDiscountIdDiscountSku"));
		_resourceMethodPairs.put(
			"mutation#createDiscountIdDiscountSkuBatch",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class,
				"postDiscountIdDiscountSkuBatch"));
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
			"mutation#deletePriceListAccount",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class, "deletePriceListAccount"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListAccountBatch",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class,
				"deletePriceListAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceListAccount",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceListAccount"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListAccount",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class,
				"postPriceListIdPriceListAccount"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListAccountBatch",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class,
				"postPriceListIdPriceListAccountBatch"));
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
			"mutation#deletePriceListChannel",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class, "deletePriceListChannel"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListChannelBatch",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class,
				"deletePriceListChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceListChannel",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceListChannel"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListChannel",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class,
				"postPriceListIdPriceListChannel"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListChannelBatch",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class,
				"postPriceListIdPriceListChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListDiscount",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"deletePriceListDiscount"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListDiscountBatch",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"deletePriceListDiscountBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceListDiscount",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceListDiscount"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListDiscount",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"postPriceListIdPriceListDiscount"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListDiscountBatch",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"postPriceListIdPriceListDiscountBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListOrderType",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"deletePriceListOrderType"));
		_resourceMethodPairs.put(
			"mutation#deletePriceListOrderTypeBatch",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"deletePriceListOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceListOrderType",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceListOrderType"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListOrderType",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"postPriceListIdPriceListOrderType"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceListOrderTypeBatch",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"postPriceListIdPriceListOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceListByExternalReferenceCodePriceModifier",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"postPriceListByExternalReferenceCodePriceModifier"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceModifier",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"postPriceListIdPriceModifier"));
		_resourceMethodPairs.put(
			"mutation#createPriceListIdPriceModifierBatch",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"postPriceListIdPriceModifierBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"deletePriceModifierByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchPriceModifierByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"patchPriceModifierByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifier",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class, "deletePriceModifier"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierBatch",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class, "deletePriceModifierBatch"));
		_resourceMethodPairs.put(
			"mutation#patchPriceModifier",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class, "patchPriceModifier"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierCategory",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"deletePriceModifierCategory"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierCategoryBatch",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"deletePriceModifierCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierByExternalReferenceCodePriceModifierCategory",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"postPriceModifierByExternalReferenceCodePriceModifierCategory"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierIdPriceModifierCategory",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"postPriceModifierIdPriceModifierCategory"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierIdPriceModifierCategoryBatch",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"postPriceModifierIdPriceModifierCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierProduct",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"deletePriceModifierProduct"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierProductBatch",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"deletePriceModifierProductBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierByExternalReferenceCodePriceModifierProduct",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"postPriceModifierByExternalReferenceCodePriceModifierProduct"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierIdPriceModifierProduct",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"postPriceModifierIdPriceModifierProduct"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierIdPriceModifierProductBatch",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"postPriceModifierIdPriceModifierProductBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierProductGroup",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"deletePriceModifierProductGroup"));
		_resourceMethodPairs.put(
			"mutation#deletePriceModifierProductGroupBatch",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"deletePriceModifierProductGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierByExternalReferenceCodePriceModifierProductGroup",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"postPriceModifierByExternalReferenceCodePriceModifierProductGroup"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierIdPriceModifierProductGroup",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"postPriceModifierIdPriceModifierProductGroup"));
		_resourceMethodPairs.put(
			"mutation#createPriceModifierIdPriceModifierProductGroupBatch",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"postPriceModifierIdPriceModifierProductGroupBatch"));
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
			"query#discountAccountAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "getDiscountAccountAccount"));
		_resourceMethodPairs.put(
			"query#priceListAccountAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "getPriceListAccountAccount"));
		_resourceMethodPairs.put(
			"query#discountAccountGroupAccountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"getDiscountAccountGroupAccountGroup"));
		_resourceMethodPairs.put(
			"query#priceListAccountGroupAccountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"getPriceListAccountGroupAccountGroup"));
		_resourceMethodPairs.put(
			"query#discountCategoryCategory",
			new ObjectValuePair<>(
				CategoryResourceImpl.class, "getDiscountCategoryCategory"));
		_resourceMethodPairs.put(
			"query#priceModifierCategoryCategory",
			new ObjectValuePair<>(
				CategoryResourceImpl.class,
				"getPriceModifierCategoryCategory"));
		_resourceMethodPairs.put(
			"query#discountChannelChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getDiscountChannelChannel"));
		_resourceMethodPairs.put(
			"query#priceListChannelChannel",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getPriceListChannelChannel"));
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
			"query#discountByExternalReferenceCodeDiscountAccounts",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountAccountsPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountAccounts",
			new ObjectValuePair<>(
				DiscountAccountResourceImpl.class,
				"getDiscountIdDiscountAccountsPage"));
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
			"query#discountByExternalReferenceCodeDiscountChannels",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountChannelsPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountChannels",
			new ObjectValuePair<>(
				DiscountChannelResourceImpl.class,
				"getDiscountIdDiscountChannelsPage"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountOrderTypes",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountOrderTypes",
			new ObjectValuePair<>(
				DiscountOrderTypeResourceImpl.class,
				"getDiscountIdDiscountOrderTypesPage"));
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
			"query#discountByExternalReferenceCodeDiscountProductGroups",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountProductGroupsPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountProductGroups",
			new ObjectValuePair<>(
				DiscountProductGroupResourceImpl.class,
				"getDiscountIdDiscountProductGroupsPage"));
		_resourceMethodPairs.put(
			"query#discountRule",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class, "getDiscountRule"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountRules",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountRulesPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountRules",
			new ObjectValuePair<>(
				DiscountRuleResourceImpl.class,
				"getDiscountIdDiscountRulesPage"));
		_resourceMethodPairs.put(
			"query#discountByExternalReferenceCodeDiscountSkus",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class,
				"getDiscountByExternalReferenceCodeDiscountSkusPage"));
		_resourceMethodPairs.put(
			"query#discountIdDiscountSkus",
			new ObjectValuePair<>(
				DiscountSkuResourceImpl.class,
				"getDiscountIdDiscountSkusPage"));
		_resourceMethodPairs.put(
			"query#discountOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "getDiscountOrderTypeOrderType"));
		_resourceMethodPairs.put(
			"query#priceListOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class, "getPriceListOrderTypeOrderType"));
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
			"query#priceListByExternalReferenceCodePriceListAccounts",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceListAccountsPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceListAccounts",
			new ObjectValuePair<>(
				PriceListAccountResourceImpl.class,
				"getPriceListIdPriceListAccountsPage"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceListAccountGroups",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceListAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceListAccountGroups",
			new ObjectValuePair<>(
				PriceListAccountGroupResourceImpl.class,
				"getPriceListIdPriceListAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceListChannels",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceListChannelsPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceListChannels",
			new ObjectValuePair<>(
				PriceListChannelResourceImpl.class,
				"getPriceListIdPriceListChannelsPage"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceListDiscounts",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceListDiscountsPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceListDiscounts",
			new ObjectValuePair<>(
				PriceListDiscountResourceImpl.class,
				"getPriceListIdPriceListDiscountsPage"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceListOrderTypes",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceListOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceListOrderTypes",
			new ObjectValuePair<>(
				PriceListOrderTypeResourceImpl.class,
				"getPriceListIdPriceListOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#priceListByExternalReferenceCodePriceModifiers",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"getPriceListByExternalReferenceCodePriceModifiersPage"));
		_resourceMethodPairs.put(
			"query#priceListIdPriceModifiers",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"getPriceListIdPriceModifiersPage"));
		_resourceMethodPairs.put(
			"query#priceModifierByExternalReferenceCode",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class,
				"getPriceModifierByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#priceModifier",
			new ObjectValuePair<>(
				PriceModifierResourceImpl.class, "getPriceModifier"));
		_resourceMethodPairs.put(
			"query#priceModifierByExternalReferenceCodePriceModifierCategories",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"getPriceModifierByExternalReferenceCodePriceModifierCategoriesPage"));
		_resourceMethodPairs.put(
			"query#priceModifierIdPriceModifierCategories",
			new ObjectValuePair<>(
				PriceModifierCategoryResourceImpl.class,
				"getPriceModifierIdPriceModifierCategoriesPage"));
		_resourceMethodPairs.put(
			"query#priceModifierByExternalReferenceCodePriceModifierProducts",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"getPriceModifierByExternalReferenceCodePriceModifierProductsPage"));
		_resourceMethodPairs.put(
			"query#priceModifierIdPriceModifierProducts",
			new ObjectValuePair<>(
				PriceModifierProductResourceImpl.class,
				"getPriceModifierIdPriceModifierProductsPage"));
		_resourceMethodPairs.put(
			"query#priceModifierByExternalReferenceCodePriceModifierProductGroups",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"getPriceModifierByExternalReferenceCodePriceModifierProductGroupsPage"));
		_resourceMethodPairs.put(
			"query#priceModifierIdPriceModifierProductGroups",
			new ObjectValuePair<>(
				PriceModifierProductGroupResourceImpl.class,
				"getPriceModifierIdPriceModifierProductGroupsPage"));
		_resourceMethodPairs.put(
			"query#discountProductProduct",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getDiscountProductProduct"));
		_resourceMethodPairs.put(
			"query#priceEntryIdProduct",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getPriceEntryIdProduct"));
		_resourceMethodPairs.put(
			"query#priceModifierProductProduct",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getPriceModifierProductProduct"));
		_resourceMethodPairs.put(
			"query#discountProductGroupProductGroup",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class,
				"getDiscountProductGroupProductGroup"));
		_resourceMethodPairs.put(
			"query#priceModifierProductGroupProductGroup",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class,
				"getPriceModifierProductGroupProductGroup"));
		_resourceMethodPairs.put(
			"query#discountSkuSku",
			new ObjectValuePair<>(SkuResourceImpl.class, "getDiscountSkuSku"));
		_resourceMethodPairs.put(
			"query#priceEntryIdSku",
			new ObjectValuePair<>(SkuResourceImpl.class, "getPriceEntryIdSku"));
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
	private ComponentServiceObjects<DiscountAccountResource>
		_discountAccountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountAccountGroupResource>
		_discountAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountCategoryResource>
		_discountCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountChannelResource>
		_discountChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountOrderTypeResource>
		_discountOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountProductResource>
		_discountProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountProductGroupResource>
		_discountProductGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountRuleResource>
		_discountRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiscountSkuResource>
		_discountSkuResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceEntryResource>
		_priceEntryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListResource>
		_priceListResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListAccountResource>
		_priceListAccountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListAccountGroupResource>
		_priceListAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListChannelResource>
		_priceListChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListDiscountResource>
		_priceListDiscountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceListOrderTypeResource>
		_priceListOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceModifierResource>
		_priceModifierResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceModifierCategoryResource>
		_priceModifierCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceModifierProductResource>
		_priceModifierProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PriceModifierProductGroupResource>
		_priceModifierProductGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TierPriceResource>
		_tierPriceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountGroupResource>
		_accountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductGroupResource>
		_productGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;

}