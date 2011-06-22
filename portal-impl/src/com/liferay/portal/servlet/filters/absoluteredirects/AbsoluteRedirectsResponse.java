/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.absoluteredirects;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Jorge Ferrer
 */
public class AbsoluteRedirectsResponse extends HttpServletResponseWrapper {

	public AbsoluteRedirectsResponse(g
		HttpServletRequest request, HttpServletResponse response) {

		super(response);

		_request = request;
	}

	@Override
	public void sendRedirect(String redirect) throws IOException {
		String portalURL = getPortalURL();

		if (Validator.isNotNull(portalURL) &&
			redirect.startsWith(StringPool.SLASH)) {

			redirect = portalURL + redirect;
		}

		if (!CookieKeys.hasSessionId(_request)) {
			redirect = PortalUtil.getURLWithSessionId(
				redirect, _request.getSession().getId());
		}

		_request.setAttribute(
			AbsoluteRedirectsResponse.class.getName(), redirect);

		super.sendRedirect(redirect);
	}

	protected String getPortalURL() {
		return PortalUtil.getPortalURL(_request);
	}

	private HttpServletRequest _request;

}