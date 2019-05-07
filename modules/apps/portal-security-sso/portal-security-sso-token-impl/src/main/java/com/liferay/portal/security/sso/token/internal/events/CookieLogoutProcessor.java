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

package com.liferay.portal.security.sso.token.internal.events;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.token.events.LogoutProcessor;
import com.liferay.portal.security.sso.token.events.LogoutProcessorType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "logout.processor.type=" + LogoutProcessorType.COOKIE,
	service = LogoutProcessor.class
)
public class CookieLogoutProcessor implements LogoutProcessor {

	@Override
	public void logout(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String... parameters) {

		String domain = CookieKeys.getDomain(httpServletRequest);

		for (String parameter : parameters) {
			Cookie cookie = new Cookie(parameter, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				cookie.setDomain(domain);
			}

			cookie.setMaxAge(0);
			cookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(
				httpServletRequest, httpServletResponse, cookie);
		}
	}

}