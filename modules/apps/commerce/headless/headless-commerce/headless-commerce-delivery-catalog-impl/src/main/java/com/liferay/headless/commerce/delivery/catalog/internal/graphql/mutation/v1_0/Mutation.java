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

package com.liferay.headless.commerce.delivery.catalog.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.DDMOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishListItem;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListItemResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setChannelResourceComponentServiceObjects(
		ComponentServiceObjects<ChannelResource>
			channelResourceComponentServiceObjects) {

		_channelResourceComponentServiceObjects =
			channelResourceComponentServiceObjects;
	}

	public static void setSkuResourceComponentServiceObjects(
		ComponentServiceObjects<SkuResource>
			skuResourceComponentServiceObjects) {

		_skuResourceComponentServiceObjects =
			skuResourceComponentServiceObjects;
	}

	public static void setWishListResourceComponentServiceObjects(
		ComponentServiceObjects<WishListResource>
			wishListResourceComponentServiceObjects) {

		_wishListResourceComponentServiceObjects =
			wishListResourceComponentServiceObjects;
	}

	public static void setWishListItemResourceComponentServiceObjects(
		ComponentServiceObjects<WishListItemResource>
			wishListItemResourceComponentServiceObjects) {

		_wishListItemResourceComponentServiceObjects =
			wishListItemResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createChannelsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.postChannelsPageExportBatch(
				search, _filterBiFunction.apply(channelResource, filterString),
				_sortsBiFunction.apply(channelResource, sortsString),
				callbackURL, contentType, fieldNames));
	}

	@GraphQLField(
		description = "Retrieves a SKU from selected channel and product ID."
	)
	public Sku createChannelProductSku(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("quantity") Integer quantity,
			@GraphQLName("ddmOptions") DDMOption[] ddmOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postChannelProductSku(
				channelId, productId, accountId, quantity, ddmOptions));
	}

	@GraphQLField(
		description = "Retrieves a SKU from selected channel and product ID."
	)
	public Sku createChannelProductSkuBySkuOption(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("quantity") Integer quantity,
			@GraphQLName("skuOptions") SkuOption[] skuOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postChannelProductSkuBySkuOption(
				channelId, productId, accountId, quantity, skuOptions));
	}

	@GraphQLField
	public WishList createChannelWishList(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("wishList") WishList wishList)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.postChannelWishList(
				channelId, accountId, wishList));
	}

	@GraphQLField(description = "Deletes a wishlist by wishListId.")
	public boolean deleteWishList(@GraphQLName("wishListId") Long wishListId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.deleteWishList(wishListId));

		return true;
	}

	@GraphQLField
	public Response deleteWishListBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.deleteWishListBatch(
				callbackURL, object));
	}

	@GraphQLField
	public WishList patchWishList(
			@GraphQLName("wishListId") Long wishListId,
			@GraphQLName("wishList") WishList wishList)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.patchWishList(
				wishListId, wishList));
	}

	@GraphQLField(description = "Deletes a wishlist item by wishListItemId.")
	public boolean deleteWishListItem(
			@GraphQLName("wishListItemId") Long wishListItemId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListItemResource -> wishListItemResource.deleteWishListItem(
				wishListItemId));

		return true;
	}

	@GraphQLField
	public Response deleteWishListItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListItemResource ->
				wishListItemResource.deleteWishListItemBatch(
					callbackURL, object));
	}

	@GraphQLField
	public WishListItem createWishlistWishListWishListItem(
			@GraphQLName("wishListId") Long wishListId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("wishListItem") WishListItem wishListItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListItemResource ->
				wishListItemResource.postWishlistWishListWishListItem(
					wishListId, accountId, wishListItem));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(ChannelResource channelResource)
		throws Exception {

		channelResource.setContextAcceptLanguage(_acceptLanguage);
		channelResource.setContextCompany(_company);
		channelResource.setContextHttpServletRequest(_httpServletRequest);
		channelResource.setContextHttpServletResponse(_httpServletResponse);
		channelResource.setContextUriInfo(_uriInfo);
		channelResource.setContextUser(_user);
		channelResource.setGroupLocalService(_groupLocalService);
		channelResource.setRoleLocalService(_roleLocalService);

		channelResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		channelResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(SkuResource skuResource)
		throws Exception {

		skuResource.setContextAcceptLanguage(_acceptLanguage);
		skuResource.setContextCompany(_company);
		skuResource.setContextHttpServletRequest(_httpServletRequest);
		skuResource.setContextHttpServletResponse(_httpServletResponse);
		skuResource.setContextUriInfo(_uriInfo);
		skuResource.setContextUser(_user);
		skuResource.setGroupLocalService(_groupLocalService);
		skuResource.setRoleLocalService(_roleLocalService);

		skuResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		skuResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(WishListResource wishListResource)
		throws Exception {

		wishListResource.setContextAcceptLanguage(_acceptLanguage);
		wishListResource.setContextCompany(_company);
		wishListResource.setContextHttpServletRequest(_httpServletRequest);
		wishListResource.setContextHttpServletResponse(_httpServletResponse);
		wishListResource.setContextUriInfo(_uriInfo);
		wishListResource.setContextUser(_user);
		wishListResource.setGroupLocalService(_groupLocalService);
		wishListResource.setRoleLocalService(_roleLocalService);

		wishListResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		wishListResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			WishListItemResource wishListItemResource)
		throws Exception {

		wishListItemResource.setContextAcceptLanguage(_acceptLanguage);
		wishListItemResource.setContextCompany(_company);
		wishListItemResource.setContextHttpServletRequest(_httpServletRequest);
		wishListItemResource.setContextHttpServletResponse(
			_httpServletResponse);
		wishListItemResource.setContextUriInfo(_uriInfo);
		wishListItemResource.setContextUser(_user);
		wishListItemResource.setGroupLocalService(_groupLocalService);
		wishListItemResource.setRoleLocalService(_roleLocalService);

		wishListItemResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		wishListItemResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;
	private static ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;
	private static ComponentServiceObjects<WishListResource>
		_wishListResourceComponentServiceObjects;
	private static ComponentServiceObjects<WishListItemResource>
		_wishListItemResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}