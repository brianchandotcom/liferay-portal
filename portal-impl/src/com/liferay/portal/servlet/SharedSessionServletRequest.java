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

package com.liferay.portal.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class SharedSessionServletRequest extends HttpServletRequestWrapper {

	public SharedSessionServletRequest(
		HttpServletRequest httpServletRequest, boolean shared) {

		super(httpServletRequest);

		_shared = shared;
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (_shared || !create) {
			return _getPortalHttpSession(create);
		}

		return new SharedSessionWrapper(
			_getPortalHttpSession(true), super.getSession(true));
	}

	public HttpSession getSharedSession() {
		return _getPortalHttpSession(true);
	}

	private HttpSession _getPortalHttpSession(boolean create) {
		HttpSession httpSession = super.getSession(false);

		if (httpSession == null) {
			httpSession = super.getSession(create);
		}

		return httpSession;
	}

	private final boolean _shared;

}