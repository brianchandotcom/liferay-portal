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

package com.liferay.portal.servlet.filters.absoluteredirects;

import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 */
public class AbsoluteRedirectsResponse extends HttpServletResponseWrapper {

	public AbsoluteRedirectsResponse(
		HttpServletRequest request, HttpServletResponse response) {

		super(response);

		_request = request;
	}

	@Override
	public void sendRedirect(String redirect) throws IOException {
		redirect = PortalUtil.getAbsoluteURL(_request, redirect);

		_request.setAttribute(
			AbsoluteRedirectsResponse.class.getName(), redirect);

		super.sendRedirect(redirect);
	}

	private final HttpServletRequest _request;

}