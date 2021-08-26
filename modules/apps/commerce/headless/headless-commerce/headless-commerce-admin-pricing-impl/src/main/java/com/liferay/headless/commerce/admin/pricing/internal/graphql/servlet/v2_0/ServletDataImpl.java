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
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

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
@Component(enabled = false, immediate = true, service = ServletData.class)
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