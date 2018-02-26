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

package com.liferay.commerce.internal.order;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

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
public class CommerceOrderHelperImpl implements CommerceOrderHelper {

	@Override
	public PortletURL getCommerceCartPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_CART_CONTENT);
	}

	@Override
	public PortletURL getCommerceCheckoutPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_CHECKOUT);
	}

	@Override
	public CommerceOrder getCurrentCommerceOrder(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String uuid = null;
		long groupId = 0;

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		if (organization != null) {
			groupId = organization.getGroupId();

			uuid = _getOrganizationCurrentCommerceOrderUuid(
				themeDisplay, organization);
		}
		else {
			groupId = themeDisplay.getScopeGroupId();
			uuid = _getUserCurrentCommerceOrderUuid(themeDisplay);
		}

		if (Validator.isNull(uuid)) {
			return null;
		}

		CommerceOrder commerceOrder = _commerceOrderService.fetchCommerceOrder(
			uuid, groupId);

		commerceOrder = _checkGuestOrder(themeDisplay, commerceOrder);

		return commerceOrder;
	}

	@Override
	public int getCurrentCommerceOrderItemsCount(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		CommerceOrder commerceOrder = getCurrentCommerceOrder(
			httpServletRequest, httpServletResponse);

		if (commerceOrder == null) {
			return 0;
		}

		return _commerceOrderItemService.getCommerceOrderItemsCount(
			commerceOrder.getCommerceOrderId());
	}

	private CommerceOrder _checkGuestOrder(
			ThemeDisplay themeDisplay, CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder == null) {
			return null;
		}

		User user = themeDisplay.getUser();

		if ((user == null) || user.isDefaultUser()) {
			return commerceOrder;
		}

		long groupId = themeDisplay.getScopeGroupId();

		String domain = CookieKeys.getDomain(themeDisplay.getRequest());

		String commerceOrderUuidWebKey = _getCookieName(groupId);

		if (commerceOrder.isGuestOrder()) {
			CookieKeys.deleteCookies(
				themeDisplay.getRequest(), themeDisplay.getResponse(), domain,
				commerceOrderUuidWebKey);

			return _commerceOrderService.updateUser(
				commerceOrder.getCommerceOrderId(), user.getUserId());
		}

		String commerceOrderUuid = CookieKeys.getCookie(
			themeDisplay.getRequest(), commerceOrderUuidWebKey, false);

		if (Validator.isNull(commerceOrderUuid)) {
			return commerceOrder;
		}

		CommerceOrder cookieCommerceOrder =
			_commerceOrderService.fetchCommerceOrder(
				commerceOrderUuid, groupId);

		if ((cookieCommerceOrder == null) ||
			!cookieCommerceOrder.isGuestOrder()) {

			return commerceOrder;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			themeDisplay.getRequest());

		_commerceOrderService.mergeGuestCommerceOrder(
			cookieCommerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceOrderId(), serviceContext);

		CookieKeys.deleteCookies(
			themeDisplay.getRequest(), themeDisplay.getResponse(), domain,
			commerceOrderUuidWebKey);

		return commerceOrder;
	}

	private String _getCookieName(long groupId) {
		return CommerceOrder.class.getName() + StringPool.POUND + groupId;
	}

	private String _getOrganizationCurrentCommerceOrderUuid(
			ThemeDisplay themeDisplay, Organization organization)
		throws PortalException {

		String commerceOrderUuid = _commerceOrderUuidThreadLocal.get();

		if (Validator.isNotNull(commerceOrderUuid)) {
			return commerceOrderUuid;
		}

		User user = themeDisplay.getUser();

		CommerceOrder commerceOrder = _commerceOrderService.fetchCommerceOrder(
			organization.getGroupId(),
			CommerceOrderConstants.ORDER_STATUS_OPEN);

		if (commerceOrder != null) {
			_commerceOrderUuidThreadLocal.set(commerceOrder.getUuid());

			return commerceOrder.getUuid();
		}

		commerceOrder = _commerceOrderService.addOrganizationCommerceOrder(
			organization.getGroupId(), user.getUserId(),
			themeDisplay.getSiteGroupId(), organization.getOrganizationId());

		return commerceOrder.getUuid();
	}

	private PortletURL _getPortletURL(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		PortletURL portletURL = null;

		long groupId = _portal.getScopeGroupId(httpServletRequest);

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

	private String _getUserCurrentCommerceOrderUuid(ThemeDisplay themeDisplay)
		throws PortalException {

		String commerceOrderUuid = _commerceOrderUuidThreadLocal.get();

		if (Validator.isNotNull(commerceOrderUuid)) {
			return commerceOrderUuid;
		}

		User user = themeDisplay.getUser();

		if ((user != null) && !user.isDefaultUser()) {
			CommerceOrder commerceOrder =
				_commerceOrderService.fetchCommerceOrder(
					themeDisplay.getScopeGroupId(),
					CommerceOrderConstants.ORDER_STATUS_OPEN);

			if (commerceOrder != null) {
				_commerceOrderUuidThreadLocal.set(commerceOrder.getUuid());

				return commerceOrder.getUuid();
			}
		}

		String cookieName = _getCookieName(themeDisplay.getScopeGroupId());

		commerceOrderUuid = CookieKeys.getCookie(
			themeDisplay.getRequest(), cookieName, false);

		if (Validator.isNotNull(commerceOrderUuid)) {
			_commerceOrderUuidThreadLocal.set(commerceOrderUuid);

			return commerceOrderUuid;
		}

		CommerceOrder commerceOrder =
			_commerceOrderService.addUserCommerceOrder(
				themeDisplay.getScopeGroupId(), user.getUserId());

		if (!themeDisplay.isSignedIn()) {
			_setGuestCommerceOrder(themeDisplay, commerceOrder);
		}

		return commerceOrder.getUuid();
	}

	private void _setGuestCommerceOrder(
			ThemeDisplay themeDisplay, CommerceOrder commerceOrder)
		throws PortalException {

		User user = themeDisplay.getUser();

		if ((user != null) && !user.isDefaultUser()) {
			return;
		}

		String commerceOrderUuidWebKey = _getCookieName(
			commerceOrder.getSiteGroupId());

		Cookie cookie = new Cookie(
			commerceOrderUuidWebKey, commerceOrder.getUuid());

		String domain = CookieKeys.getDomain(themeDisplay.getRequest());

		if (Validator.isNotNull(domain)) {
			cookie.setDomain(domain);
		}

		cookie.setMaxAge(CookieKeys.MAX_AGE);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(
			themeDisplay.getRequest(), themeDisplay.getResponse(), cookie);
	}

	private static final ThreadLocal<String> _commerceOrderUuidThreadLocal =
		new CentralizedThreadLocal<>(CommerceOrderHelperImpl.class.getName());

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}