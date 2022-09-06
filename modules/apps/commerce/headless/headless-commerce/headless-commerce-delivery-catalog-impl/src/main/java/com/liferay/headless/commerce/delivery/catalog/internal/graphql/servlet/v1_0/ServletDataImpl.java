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

package com.liferay.headless.commerce.delivery.catalog.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.delivery.catalog.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.delivery.catalog.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.AttachmentResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.CategoryResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.ChannelResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.MappedProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.PinResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.ProductOptionResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.ProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.ProductSpecificationResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.RelatedProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.SkuResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.WishListItemResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.WishListResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.CategoryResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.MappedProductResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.PinResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductOptionResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.RelatedProductResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListItemResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListResource;
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
 * @author Andrea Sbarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setWishListResourceComponentServiceObjects(
			_wishListResourceComponentServiceObjects);
		Mutation.setWishListItemResourceComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects);

		Query.setAttachmentResourceComponentServiceObjects(
			_attachmentResourceComponentServiceObjects);
		Query.setCategoryResourceComponentServiceObjects(
			_categoryResourceComponentServiceObjects);
		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setMappedProductResourceComponentServiceObjects(
			_mappedProductResourceComponentServiceObjects);
		Query.setPinResourceComponentServiceObjects(
			_pinResourceComponentServiceObjects);
		Query.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
		Query.setProductOptionResourceComponentServiceObjects(
			_productOptionResourceComponentServiceObjects);
		Query.setProductSpecificationResourceComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects);
		Query.setRelatedProductResourceComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects);
		Query.setSkuResourceComponentServiceObjects(
			_skuResourceComponentServiceObjects);
		Query.setWishListResourceComponentServiceObjects(
			_wishListResourceComponentServiceObjects);
		Query.setWishListItemResourceComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Delivery.Catalog";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-delivery-catalog-graphql/v1_0";
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
			"mutation#createChannelWishList",
			new ObjectValuePair<>(
				WishListResourceImpl.class, "postChannelWishList"));
		_resourceMethodPairs.put(
			"mutation#deleteWishList",
			new ObjectValuePair<>(
				WishListResourceImpl.class, "deleteWishList"));
		_resourceMethodPairs.put(
			"mutation#deleteWishListBatch",
			new ObjectValuePair<>(
				WishListResourceImpl.class, "deleteWishListBatch"));
		_resourceMethodPairs.put(
			"mutation#patchChannelWishList",
			new ObjectValuePair<>(
				WishListResourceImpl.class, "patchChannelWishList"));
		_resourceMethodPairs.put(
			"mutation#deleteWishListItem",
			new ObjectValuePair<>(
				WishListItemResourceImpl.class, "deleteWishListItem"));
		_resourceMethodPairs.put(
			"mutation#deleteWishListItemBatch",
			new ObjectValuePair<>(
				WishListItemResourceImpl.class, "deleteWishListItemBatch"));
		_resourceMethodPairs.put(
			"mutation#createChannelWishListItem",
			new ObjectValuePair<>(
				WishListItemResourceImpl.class, "postChannelWishListItem"));
		_resourceMethodPairs.put(
			"query#channelProductAttachments",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"getChannelProductAttachmentsPage"));
		_resourceMethodPairs.put(
			"query#channelProductImages",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "getChannelProductImagesPage"));
		_resourceMethodPairs.put(
			"query#channelProductCategories",
			new ObjectValuePair<>(
				CategoryResourceImpl.class, "getChannelProductCategoriesPage"));
		_resourceMethodPairs.put(
			"query#channels",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getChannelsPage"));
		_resourceMethodPairs.put(
			"query#channelProductMappedProducts",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class,
				"getChannelProductMappedProductsPage"));
		_resourceMethodPairs.put(
			"query#channelProductPins",
			new ObjectValuePair<>(
				PinResourceImpl.class, "getChannelProductPinsPage"));
		_resourceMethodPairs.put(
			"query#channelProducts",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getChannelProductsPage"));
		_resourceMethodPairs.put(
			"query#channelProduct",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getChannelProduct"));
		_resourceMethodPairs.put(
			"query#channelProductOptions",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class,
				"getChannelProductOptionsPage"));
		_resourceMethodPairs.put(
			"query#channelProductProductSpecifications",
			new ObjectValuePair<>(
				ProductSpecificationResourceImpl.class,
				"getChannelProductProductSpecificationsPage"));
		_resourceMethodPairs.put(
			"query#channelProductRelatedProducts",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class,
				"getChannelProductRelatedProductsPage"));
		_resourceMethodPairs.put(
			"query#channelProductSkus",
			new ObjectValuePair<>(
				SkuResourceImpl.class, "getChannelProductSkusPage"));
		_resourceMethodPairs.put(
			"query#channelWishLists",
			new ObjectValuePair<>(
				WishListResourceImpl.class, "getChannelWishListsPage"));
		_resourceMethodPairs.put(
			"query#wishList",
			new ObjectValuePair<>(WishListResourceImpl.class, "getWishList"));
		_resourceMethodPairs.put(
			"query#wishListItem",
			new ObjectValuePair<>(
				WishListItemResourceImpl.class, "getWishListItem"));
		_resourceMethodPairs.put(
			"query#wishListItems",
			new ObjectValuePair<>(
				WishListItemResourceImpl.class, "getWishListItemsPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WishListResource>
		_wishListResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WishListItemResource>
		_wishListItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AttachmentResource>
		_attachmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MappedProductResource>
		_mappedProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PinResource>
		_pinResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductOptionResource>
		_productOptionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductSpecificationResource>
		_productSpecificationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<RelatedProductResource>
		_relatedProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;

}