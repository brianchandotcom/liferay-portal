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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceCartHelperImpl implements CommerceCartHelper {

	@Override
	public CommerceCart getCurrentCart(
		HttpServletRequest httpServletRequest, int type){

		long commerceCartId = _getCurrentCommerceCartId(
			httpServletRequest, type);

		if(commerceCartId == 0){
			return null;
		}

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(commerceCartId);

		if(!isCommerceCartValid(commerceCart,httpServletRequest)){
			return null;
		}

		return commerceCart;
	}

	private long _getCurrentCommerceCartId(
		HttpServletRequest httpServletRequest, int type){

		String commerceCartIdWebKey = formatCommerceCartIdWebKey(type);

		Long commerceCartIdObj = (Long)httpServletRequest.getAttribute(commerceCartIdWebKey);

		if (commerceCartIdObj != null) {
			return commerceCartIdObj.longValue();
		}

		HttpSession session = httpServletRequest.getSession();

		commerceCartIdObj = (Long)session.getAttribute(commerceCartIdWebKey);

		if (commerceCartIdObj != null) {
			httpServletRequest.setAttribute(commerceCartIdWebKey, commerceCartIdObj);

			return commerceCartIdObj.longValue();
		}

		String cookieCommerceCartId = CookieKeys.getCookie(
			httpServletRequest, commerceCartIdWebKey, false);

		if (Validator.isNotNull(cookieCommerceCartId)) {

			commerceCartIdObj =  GetterUtil.getLong(cookieCommerceCartId);

			session.setAttribute(commerceCartIdWebKey, commerceCartIdObj);

			httpServletRequest.setAttribute(
				commerceCartIdWebKey, commerceCartIdObj);

			return commerceCartIdObj;
		}

		return 0;
	}

	@Override
	public void updateCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		long commerceCartId) {

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(commerceCartId);

		if(!isCommerceCartValid(commerceCart,httpServletRequest)){
			return;
		}

		String commerceCartIdWebKey = formatCommerceCartIdWebKey(commerceCart.getType());

		Cookie commerceCartIdCookie = new Cookie(
			commerceCartIdWebKey,
			String.valueOf(commerceCartId));

		String domain = CookieKeys.getDomain(httpServletRequest);

		if (Validator.isNotNull(domain)) {
			commerceCartIdCookie.setDomain(domain);
		}

		commerceCartIdCookie.setMaxAge(CookieKeys.MAX_AGE);
		commerceCartIdCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(httpServletRequest, httpServletResponse, commerceCartIdCookie);
	}

	private boolean isCommerceCartValid(CommerceCart commerceCart, HttpServletRequest httpServletRequest){

		if(commerceCart == null){
			return false;
		}

		long userId = _portal.getUserId(httpServletRequest);

		if(commerceCart.getUserId() != userId){
			return false;
		}

		return true;
	}

	private String formatCommerceCartIdWebKey(int type){
		return CommerceCartWebKeys.COMMERCE_CART_ID + StringPool.UNDERLINE + type;
	}

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private Portal _portal;

}
