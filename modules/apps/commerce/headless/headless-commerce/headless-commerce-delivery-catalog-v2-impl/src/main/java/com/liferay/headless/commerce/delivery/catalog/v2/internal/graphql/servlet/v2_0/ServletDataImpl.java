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

package com.liferay.headless.commerce.delivery.catalog.v2.internal.graphql.servlet.v2_0;

import com.liferay.headless.commerce.delivery.catalog.v2.internal.graphql.mutation.v2_0.Mutation;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.graphql.query.v2_0.Query;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.AttachmentResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.CategoryResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.ChannelResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.ImageResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.LinkedProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.MappedProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.PinResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.ProductOptionResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.ProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.ProductSpecificationResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.RelatedProductResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.SkuResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.WishListItemResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.internal.resource.v2_0.WishListResourceImpl;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.AttachmentResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.CategoryResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.ChannelResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.ImageResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.LinkedProductResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.MappedProductResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.PinResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.ProductOptionResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.ProductResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.ProductSpecificationResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.RelatedProductResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.SkuResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.WishListItemResource;
import com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.WishListResource;
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
 * @author Crescenzo Rega
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Mutation.setSkuResourceComponentServiceObjects(
			_skuResourceComponentServiceObjects);
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
		Query.setImageResourceComponentServiceObjects(
			_imageResourceComponentServiceObjects);
		Query.setLinkedProductResourceComponentServiceObjects(
			_linkedProductResourceComponentServiceObjects);
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
		return "/headless-commerce-delivery-catalog-graphql/v2_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createChannelsPageExportBatch",
						new ObjectValuePair<>(
							ChannelResourceImpl.class,
							"postChannelsPageExportBatch"));
					put(
						"mutation#createChannelIdProductSku",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "postChannelIdProductSku"));
					put(
						"mutation#createChannelIdWishList",
						new ObjectValuePair<>(
							WishListResourceImpl.class,
							"postChannelIdWishList"));
					put(
						"mutation#deleteWishList",
						new ObjectValuePair<>(
							WishListResourceImpl.class, "deleteWishList"));
					put(
						"mutation#deleteWishListBatch",
						new ObjectValuePair<>(
							WishListResourceImpl.class, "deleteWishListBatch"));
					put(
						"mutation#patchWishList",
						new ObjectValuePair<>(
							WishListResourceImpl.class, "patchWishList"));
					put(
						"mutation#deleteWishListItem",
						new ObjectValuePair<>(
							WishListItemResourceImpl.class,
							"deleteWishListItem"));
					put(
						"mutation#deleteWishListItemBatch",
						new ObjectValuePair<>(
							WishListItemResourceImpl.class,
							"deleteWishListItemBatch"));
					put(
						"mutation#createWishlistIdWishListItem",
						new ObjectValuePair<>(
							WishListItemResourceImpl.class,
							"postWishlistIdWishListItem"));

					put(
						"query#channelIdProductAttachments",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getChannelIdProductAttachmentsPage"));
					put(
						"query#channelIdProductCategories",
						new ObjectValuePair<>(
							CategoryResourceImpl.class,
							"getChannelIdProductCategoriesPage"));
					put(
						"query#channels",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "getChannelsPage"));
					put(
						"query#channelIdProductImages",
						new ObjectValuePair<>(
							ImageResourceImpl.class,
							"getChannelIdProductImagesPage"));
					put(
						"query#channelIdProductLinkedProducts",
						new ObjectValuePair<>(
							LinkedProductResourceImpl.class,
							"getChannelIdProductLinkedProductsPage"));
					put(
						"query#channelIdProductMappedProducts",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getChannelIdProductMappedProductsPage"));
					put(
						"query#channelIdProductPins",
						new ObjectValuePair<>(
							PinResourceImpl.class,
							"getChannelIdProductPinsPage"));
					put(
						"query#channelIdProducts",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getChannelIdProductsPage"));
					put(
						"query#channelIdProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "getChannelIdProduct"));
					put(
						"query#channelIdProductProductOptions",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"getChannelIdProductProductOptionsPage"));
					put(
						"query#channelIdProductProductSpecifications",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"getChannelIdProductProductSpecificationsPage"));
					put(
						"query#channelIdProductRelatedProducts",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"getChannelIdProductRelatedProductsPage"));
					put(
						"query#channelIdProductSkus",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"getChannelIdProductSkusPage"));
					put(
						"query#channelIdWishLists",
						new ObjectValuePair<>(
							WishListResourceImpl.class,
							"getChannelIdWishListsPage"));
					put(
						"query#wishList",
						new ObjectValuePair<>(
							WishListResourceImpl.class, "getWishList"));
					put(
						"query#wishListItem",
						new ObjectValuePair<>(
							WishListItemResourceImpl.class, "getWishListItem"));
					put(
						"query#wishlistIdWishListItems",
						new ObjectValuePair<>(
							WishListItemResourceImpl.class,
							"getWishlistIdWishListItemsPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;

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
	private ComponentServiceObjects<ImageResource>
		_imageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LinkedProductResource>
		_linkedProductResourceComponentServiceObjects;

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

}