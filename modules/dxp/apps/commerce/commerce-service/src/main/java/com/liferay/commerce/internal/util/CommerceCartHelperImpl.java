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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceCartHelperImpl implements CommerceCartHelper {

	@Override
	public PortletURL getCommerceCartPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = null;

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String portletId = CommercePortletKeys.COMMERCE_CART_CONTENT;

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
	public PortletURL getCommerceCheckoutPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = null;

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		long plid = _portal.getPlidFromPortletId(
			groupId, CommercePortletKeys.COMMERCE_CHECKOUT);

		if (plid > 0) {
			portletURL = _portletURLFactory.create(
				httpServletRequest, CommercePortletKeys.COMMERCE_CHECKOUT, plid,
				PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = _portletURLFactory.create(
				httpServletRequest, CommercePortletKeys.COMMERCE_CHECKOUT,
				PortletRequest.RENDER_PHASE);
		}

		return portletURL;
	}

	@Override
	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		long groupId = 0;

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		if (organization != null) {
			groupId = organization.getGroupId();
		}
		else {
			groupId = _portal.getScopeGroupId(httpServletRequest);
		}

		String uuid = _getCurrentCommerceCartUuid(
			httpServletRequest, httpServletResponse, groupId);

		if (Validator.isNull(uuid)) {
			return null;
		}

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(
			uuid, groupId);

		commerceCart = _checkGuestCart(
			httpServletRequest, httpServletResponse, commerceCart);

		return commerceCart;
	}

	@Override
	public int getCurrentCommerceCartItemsCount(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		CommerceCart commerceCart = getCurrentCommerceCart(
			httpServletRequest, httpServletResponse);

		if (commerceCart == null) {
			return 0;
		}

		return _commerceCartItemService.getCommerceCartItemsCount(
			commerceCart.getCommerceCartId());
	}

	private CommerceCart _checkGuestCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, CommerceCart commerceCart)
		throws PortalException {

		if (commerceCart == null) {
			return null;
		}

		User user = _portal.getUser(httpServletRequest);

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		if ((user == null) || user.isDefaultUser()) {
			return commerceCart;
		}

		String domain = CookieKeys.getDomain(httpServletRequest);

		String commerceCartUuidWebKey = _getCommerceCartUuidWebKey(groupId);

		if (commerceCart.isGuestCart()) {
			CookieKeys.deleteCookies(
				httpServletRequest, httpServletResponse, domain,
				commerceCartUuidWebKey);

			return _commerceCartService.updateUser(
				commerceCart.getCommerceCartId(), user.getUserId());
		}

		String commerceCartUuid = CookieKeys.getCookie(
			httpServletRequest, commerceCartUuidWebKey, false);

		if (Validator.isNull(commerceCartUuid)) {
			return commerceCart;
		}

		CommerceCart cookieCommerceCart =
			_commerceCartService.fetchCommerceCart(commerceCartUuid, groupId);

		if ((cookieCommerceCart == null) || !cookieCommerceCart.isGuestCart()) {
			return commerceCart;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		_commerceCartService.mergeGuestCommerceCart(
			cookieCommerceCart.getCommerceCartId(),
			commerceCart.getCommerceCartId(), serviceContext);

		CookieKeys.deleteCookies(
			httpServletRequest, httpServletResponse, domain,
			commerceCartUuidWebKey);

		return commerceCart;
	}

	private String _getCommerceCartUuidWebKey(long groupId) {
		return
			CommerceWebKeys.COMMERCE_CART_UUID + StringPool.UNDERLINE + groupId;
	}

	private String _getCurrentCommerceCartUuid(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long groupId)
		throws PortalException {

		String commerceCartUuid = _commerceCartUuidThreadLocal.get();

		if (Validator.isNotNull(commerceCartUuid)) {
			return commerceCartUuid;
		}

		User user = _portal.getUser(httpServletRequest);

		if ((user != null) && !user.isDefaultUser()) {
			CommerceCart commerceCart =
				_commerceCartService.fetchDefaultCommerceCart(
					groupId, user.getUserId(), true);

			if (commerceCart != null) {
				_commerceCartUuidThreadLocal.set(commerceCart.getUuid());

				return commerceCart.getUuid();
			}
		}

		String commerceCartUuidWebKey = _getCommerceCartUuidWebKey(groupId);

		commerceCartUuid = CookieKeys.getCookie(
			httpServletRequest, commerceCartUuidWebKey, false);

		if (Validator.isNotNull(commerceCartUuid)) {
			_commerceCartUuidThreadLocal.set(commerceCartUuid);

			return commerceCartUuid;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCart.class.getName(), httpServletRequest);

		serviceContext.setScopeGroupId(groupId);

		CommerceCart commerceCart = _commerceCartService.addCommerceCart(
			CommerceCartConstants.DEFAULT_TITLE, true, serviceContext);

		if (!serviceContext.isSignedIn()) {
			_setGuestCommerceCart(
				httpServletRequest, httpServletResponse, commerceCart);
		}

		return commerceCart.getUuid();
	}

	private void _setGuestCommerceCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, CommerceCart commerceCart)
		throws PortalException {

		User user = _portal.getUser(httpServletRequest);

		if ((user != null) && !user.isDefaultUser()) {
			return;
		}

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String commerceCartUuidWebKey = _getCommerceCartUuidWebKey(groupId);

		Cookie cookie = new Cookie(
			commerceCartUuidWebKey, commerceCart.getUuid());

		String domain = CookieKeys.getDomain(httpServletRequest);

		if (Validator.isNotNull(domain)) {
			cookie.setDomain(domain);
		}

		cookie.setMaxAge(CookieKeys.MAX_AGE);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(httpServletRequest, httpServletResponse, cookie);
	}

	private static final ThreadLocal<String> _commerceCartUuidThreadLocal =
		new AutoResetThreadLocal<>(CommerceCartHelperImpl.class.getName());

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}