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

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Mahmoud Azzam
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/wish-list.properties",
	scope = ServiceScope.PROTOTYPE, service = WishListResource.class
)
public class WishListResourceImpl extends BaseWishListResourceImpl {

	@Override
	public void deleteWishList(Long wishListId) throws Exception {
		_commerceWishListService.deleteCommerceWishList(wishListId);
	}

	@Override
	public Page<WishList> getChannelWishListsPage(
			Long channelId, Long accountId, Pagination pagination)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannel(channelId);

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		return Page.of(
			transform(
				_commerceWishListService.getCommerceWishLists(
					commerceChannel.getSiteGroupId(), contextUser.getUserId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				commerceWishList -> _toWishList(commerceWishList)),
			pagination,
			_commerceWishListService.getCommerceWishListsCount(
				commerceChannel.getSiteGroupId(), contextUser.getUserId()));
	}

	@Override
	public WishList getWishList(Long wishListId) throws Exception {
		return _toWishList(
			_commerceWishListService.getCommerceWishList(wishListId));
	}

	@Override
	public WishList patchWishList(Long wishListId, WishList wishList)
		throws Exception {

		CommerceWishList commerceWishList =
			_commerceWishListService.getCommerceWishList(wishListId);

		return _toWishList(
			_commerceWishListService.updateCommerceWishList(
				wishListId,
				GetterUtil.getString(
					wishList.getName(), commerceWishList.getName()),
				GetterUtil.getBoolean(
					wishList.getDefaultWishList(),
					commerceWishList.isDefaultWishList())));
	}

	@Override
	public WishList postChannelWishList(
			Long channelId, Long accountId, WishList wishList)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		return _toWishList(
			_commerceWishListService.addCommerceWishList(
				GetterUtil.getString(wishList.getName()),
				GetterUtil.getBoolean(wishList.getDefaultWishList()),
				_serviceContextHelper.getServiceContext(
					commerceChannel.getSiteGroupId())));
	}

	private WishList _toWishList(CommerceWishList commerceWishList)
		throws Exception {

		return _wishListDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, commerceWishList.getCommerceWishListId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.WishListDTOConverter)"
	)
	private DTOConverter<CommerceWishList, WishList> _wishListDTOConverter;

}