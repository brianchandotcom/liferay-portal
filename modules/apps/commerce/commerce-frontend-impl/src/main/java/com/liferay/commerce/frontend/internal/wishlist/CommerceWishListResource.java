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

package com.liferay.commerce.frontend.internal.wishlist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.frontend.internal.wishlist.model.WishListItemUpdated;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CommerceWishListResource.class)
public class CommerceWishListResource {

	@Path("/wish-list-item")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addWishListItem(
		@FormParam("commerceAccountId") long commerceAccountId,
		@FormParam("groupId") long groupId,
		@FormParam("productId") long cpDefinitionId,
		@FormParam("skuId") long cpInstanceId,
		@FormParam("options") String options,
		@Context HttpServletRequest httpServletRequest) {

		WishListItemUpdated wishListItemUpdated = new WishListItemUpdated();

		try {
			CommerceContext commerceContext = _commerceContextFactory.create(
				_portal.getCompanyId(httpServletRequest),
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(groupId),
				_portal.getUserId(httpServletRequest), 0, commerceAccountId);

			httpServletRequest.setAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceWishListItem.class.getName(), httpServletRequest);

			serviceContext.setScopeGroupId(groupId);

			CommerceWishList commerceWishList =
				_commerceWishListService.getDefaultCommerceWishList(
					groupId, _portal.getUserId(httpServletRequest));

			CPCatalogEntry cpCatalogEntry =
				_cpDefinitionHelper.getCPCatalogEntry(
					commerceAccountId, groupId, cpDefinitionId,
					_portal.getLocale(httpServletRequest));

			CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
				cpInstanceId);

			String cpInstanceUuid = StringPool.BLANK;

			if (cpInstance != null) {
				cpInstanceUuid = cpInstance.getCPInstanceUuid();
			}

			if (_commerceWishListItemService.
					getCommerceWishListItemByContainsCPInstanceCount(
						commerceWishList.getCommerceWishListId(),
						cpInstanceUuid) == 0) {

				_commerceWishListItemService.addCommerceWishListItem(
					commerceAccountId, commerceWishList.getCommerceWishListId(),
					cpCatalogEntry.getCProductId(), cpInstanceUuid, options,
					serviceContext);

				wishListItemUpdated.setSuccess(true);
			}
			else {
				CommerceWishListItem commerceWishListItem =
					_commerceWishListItemService.getCommerceWishListItem(
						commerceWishList.getCommerceWishListId(),
						cpInstanceUuid, cpCatalogEntry.getCProductId());

				_commerceWishListItemService.deleteCommerceWishListItem(
					commerceWishListItem.getCommerceWishListItemId());

				wishListItemUpdated.setSuccess(false);
			}
		}
		catch (Exception e) {
			wishListItemUpdated.setSuccess(false);

			_log.error(e, e);
		}

		return getResponse(wishListItemUpdated);
	}

	protected Response getResponse(Object object) {
		if (object == null) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		try {
			String json = _OBJECT_MAPPER.writeValueAsString(object);

			return Response.ok(
				json, MediaType.APPLICATION_JSON
			).build();
		}
		catch (JsonProcessingException jpe) {
			_log.error(jpe, jpe);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceWishListResource.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Portal _portal;

}