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

package com.liferay.commerce.order.content.web.internal.frontend.data.set.provider;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.commerce.order.content.web.internal.model.WishList;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.Format;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "fds.data.provider.key=" + CommerceOrderFDSNames.WISH_LISTS,
	service = FDSDataProvider.class
)
public class CommerceWishListFDSDataProvider
	implements FDSDataProvider<WishList> {

	@Override
	public List<WishList> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Format dateFormat = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		return TransformUtil.transform(
			_commerceWishListService.getCommerceWishLists(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), null),
			commerceWishList -> new WishList(
				commerceWishList.getUserName(),
				dateFormat.format(commerceWishList.getCreateDate()),
				_commerceWishListItemService.getCommerceWishListItemsCount(
					commerceWishList.getCommerceWishListId()),
				commerceWishList.getName(),
				commerceWishList.getCommerceWishListId()));
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceWishListService.getCommerceWishListsCount(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId());
	}

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

}