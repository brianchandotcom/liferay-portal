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

package com.liferay.commerce.wish.list.internal.util;

import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.commerce.wish.list.util.CommerceWishListHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceWishListHelperImpl implements CommerceWishListHelper {

	@Override
	public PortletURL getCommerceWishListPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = null;

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String portletId =
			CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT;

		long plid = _portal.getPlidFromPortletId(groupId, portletId);

		if (plid > 0) {
			portletURL = _portletURLFactory.create(
				httpServletRequest, portletId, plid,
				PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = _portletURLFactory.create(
				httpServletRequest, portletId, PortletRequest.RENDER_PHASE);
		}

		return portletURL;
	}

	@Override
	public int getCurrentCommerceWishListItemsCount(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceWishList commerceWishList = getCurrentCommerceWishList(
			httpServletRequest);

		if (commerceWishList == null) {
			return 0;
		}

		return _commerceWishListItemService.getCommerceWishListItemsCount(
			commerceWishList.getCommerceWishListId());
	}

	protected CommerceWishList getCurrentCommerceWishList(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long userId = _portal.getUserId(httpServletRequest);

		if (userId <= 0) {
			return null;
		}

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		return _commerceWishListService.getDefaultCommerceWishList(
			groupId, userId);
	}

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}