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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.token.events.LogoutProcessor;
import com.liferay.portal.security.sso.token.events.LogoutProcessorType;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "logout.processor.type=" + LogoutProcessorType.REDIRECT,
	service = LogoutProcessor.class
)
public class RedirectLogoutProcessor implements LogoutProcessor {

	@Override
	public void logout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String... parameters)
		throws IOException {

		if (ArrayUtil.isEmpty(parameters)) {
			return;
		}

		String pathInfo = httpServletRequest.getPathInfo();

		if (pathInfo.contains("/portal/logout")) {
			HttpSession session = httpServletRequest.getSession();

			session.invalidate();

			String redirectURL = parameters[0];

			if (Validator.isNotNull(redirectURL)) {
				httpServletResponse.sendRedirect(redirectURL);
			}
		}
	}

}