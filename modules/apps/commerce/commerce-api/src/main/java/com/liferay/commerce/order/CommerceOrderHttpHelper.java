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

package com.liferay.commerce.order;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
public interface CommerceOrderHttpHelper {

	public CommerceOrder addCommerceOrder(HttpServletRequest httpServletRequest)
		throws PortalException;

	public void deleteCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws PortalException;

	public PortletURL getCommerceCartPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public PortletURL getCommerceCartPortletURL(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException;

	public PortletURL getCommerceCartPortletURL(
			long groupId, HttpServletRequest httpServletRequest,
			CommerceOrder commerceOrder)
		throws PortalException;

	public PortletURL getCommerceCheckoutPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public int getCommerceOrderItemsQuantity(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public String getCookieName(long commerceChannelId);

	public CommerceOrder getCurrentCommerceOrder(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public boolean isGuestCheckoutEnabled(HttpServletRequest httpServletRequest)
		throws PortalException;

	public void setCurrentCommerceOrder(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException;

}