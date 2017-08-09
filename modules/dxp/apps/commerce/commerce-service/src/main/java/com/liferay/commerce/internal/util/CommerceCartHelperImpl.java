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

import com.liferay.commerce.constants.CommerceCartConstants;
import com.liferay.commerce.constants.CommerceCartWebKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceCartHelperImpl implements CommerceCartHelper {

	@Override
	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		int type = ParamUtil.getInteger(
			httpServletRequest, "type",
			CommerceCartConstants.COMMERCE_CART_TYPE_CART);

		return getCurrentCommerceCart(httpServletRequest, type);
	}

	@Override
	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest, int type)
		throws PortalException {

		String uuid = _getCurrentCommerceCartUuid(httpServletRequest, type);

		if (Validator.isNull(uuid)) {
			return null;
		}

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(
			uuid, groupId);

		return commerceCart;
	}

	@Override
	public void updateCurrentCommerceCart(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, CommerceCart commerceCart) {

		String commerceCartUuidWebKey = _getCommerceCartIdWebKey(
			commerceCart.getType());

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(
			commerceCartUuidWebKey, commerceCart.getUuid());

		httpServletRequest.setAttribute(
			commerceCartUuidWebKey, commerceCart.getUuid());

		Cookie commerceCartIdCookie = new Cookie(
			commerceCartUuidWebKey, commerceCart.getUuid());

		String domain = CookieKeys.getDomain(httpServletRequest);

		if (Validator.isNotNull(domain)) {
			commerceCartIdCookie.setDomain(domain);
		}

		commerceCartIdCookie.setMaxAge(CookieKeys.MAX_AGE);
		commerceCartIdCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(
			httpServletRequest, httpServletResponse, commerceCartIdCookie);
	}

	private String _getCommerceCartIdWebKey(int type) {
		return CommerceCartWebKeys.COMMERCE_CART_UUID + StringPool.UNDERLINE +
			type;
	}

	private String _getCurrentCommerceCartUuid(
			HttpServletRequest httpServletRequest, int type)
		throws PortalException {

		String commerceCartUuidWebKey = _getCommerceCartIdWebKey(type);

		String commerceCartUuid = (String)httpServletRequest.getAttribute(
			commerceCartUuidWebKey);

		if (Validator.isNotNull(commerceCartUuid)) {
			return commerceCartUuid;
		}

		HttpSession httpSession = httpServletRequest.getSession();

		commerceCartUuid = (String)httpSession.getAttribute(
			commerceCartUuidWebKey);

		if (Validator.isNotNull(commerceCartUuid)) {
			httpServletRequest.setAttribute(
				commerceCartUuidWebKey, commerceCartUuid);

			return commerceCartUuid;
		}

		User user = _portal.getUser(httpServletRequest);

		if ((user != null) && !user.isDefaultUser()) {
			long groupId = _portal.getScopeGroupId(httpServletRequest);

			CommerceCart commerceCart =
				_commerceCartService.fetchDefaultCommerceCart(
					groupId, user.getUserId(), type,
					CommerceCartConstants.COMMERCE_CART_DEFAULT_TITLE);

			if (commerceCart != null) {
				httpSession.setAttribute(
					commerceCartUuidWebKey, commerceCartUuid);

				httpServletRequest.setAttribute(
					commerceCartUuidWebKey, commerceCart.getUuid());

				return commerceCart.getUuid();
			}
		}

		commerceCartUuid = CookieKeys.getCookie(
			httpServletRequest, commerceCartUuidWebKey, false);

		if (Validator.isNotNull(commerceCartUuid)) {
			httpSession.setAttribute(commerceCartUuidWebKey, commerceCartUuid);

			httpServletRequest.setAttribute(
				commerceCartUuidWebKey, commerceCartUuid);

			return commerceCartUuid;
		}

		return null;
	}

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private Portal _portal;

}