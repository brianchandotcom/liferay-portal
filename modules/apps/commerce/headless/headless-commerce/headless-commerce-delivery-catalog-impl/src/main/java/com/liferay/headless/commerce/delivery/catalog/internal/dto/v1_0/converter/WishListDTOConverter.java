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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList",
	service = DTOConverter.class
)
public class WishListDTOConverter
	implements DTOConverter<CommerceWishList, WishList> {

	@Override
	public String getContentType() {
		return WishList.class.getSimpleName();
	}

	@Override
	public WishList toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceWishList commerceWishList =
			_commerceWishListService.getCommerceWishList(
				(Long)dtoConverterContext.getId());

		return new WishList() {
			{
				defaultWishList = commerceWishList.isDefaultWishList();
				id = commerceWishList.getCommerceWishListId();
				name = commerceWishList.getName();
			}
		};
	}

	@Reference
	private CommerceWishListService _commerceWishListService;

}