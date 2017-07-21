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

package com.liferay.commerce.cart.internal.util;

import com.liferay.commerce.cart.constants.CommerceCartWebKeys;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.service.CommerceCartService;
import com.liferay.commerce.cart.util.CommerceCartHelper;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
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
	public CommerceCart getCurrentCart(
		HttpServletRequest httpServletRequest, int type) {

		long commerceCartId = _getCurrentCommerceCartId(
			httpServletRequest, type);

		if (commerceCartId <= 0) {
			return null;
		}

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(
			commerceCartId);

		if (!_isCommerceCartValid(commerceCart, httpServletRequest)) {
			return null;
		}

		return commerceCart;
	}

	@Override
	public void updateCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long commerceCartId) {

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(
			commerceCartId);

		if (!_isCommerceCartValid(commerceCart, httpServletRequest)) {
			return;
		}

		String commerceCartIdWebKey = _getCommerceCartIdWebKey(
			commerceCart.getType());

		Cookie commerceCartIdCookie = new Cookie(
			commerceCartIdWebKey, String.valueOf(commerceCartId));

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
		return CommerceCartWebKeys.COMMERCE_CART_ID + StringPool.UNDERLINE +
			type;
	}

	private long _getCurrentCommerceCartId(
		HttpServletRequest httpServletRequest, int type) {

		String commerceCartIdWebKey = _getCommerceCartIdWebKey(type);

		Long commerceCartId = (Long)httpServletRequest.getAttribute(
			commerceCartIdWebKey);

		if (commerceCartId != null) {
			return commerceCartId.longValue();
		}

		HttpSession httpSession = httpServletRequest.getSession();

		commerceCartId = (Long)httpSession.getAttribute(commerceCartIdWebKey);

		if (commerceCartId != null) {
			httpServletRequest.setAttribute(
				commerceCartIdWebKey, commerceCartId);

			return commerceCartId.longValue();
		}

		String cookieCommerceCartId = CookieKeys.getCookie(
			httpServletRequest, commerceCartIdWebKey, false);

		if (Validator.isNotNull(cookieCommerceCartId)) {
			commerceCartId = GetterUtil.getLong(cookieCommerceCartId);

			httpSession.setAttribute(commerceCartIdWebKey, commerceCartId);

			httpServletRequest.setAttribute(
				commerceCartIdWebKey, commerceCartId);

			return commerceCartId;
		}

		return 0;
	}

	private boolean _isCommerceCartValid(
		CommerceCart commerceCart, HttpServletRequest httpServletRequest) {

		if (commerceCart == null) {
			return false;
		}

		long userId = _portal.getUserId(httpServletRequest);

		if (commerceCart.getUserId() != userId) {
			return false;
		}

		return true;
	}

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private Portal _portal;

}