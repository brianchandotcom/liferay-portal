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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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

		_portalHttpSession = httpServletRequest.getSession();
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (create) {
			checkPortalSession();
		}

		if (_shared) {
			return _portalHttpSession;
		}

		HttpSession portletHttpSession = super.getSession(create);

		if ((portletHttpSession != null) &&
			(portletHttpSession != _portalHttpSession)) {

			return getSharedSessionWrapper(
				_portalHttpSession, portletHttpSession);
		}

		return portletHttpSession;
	}

	public HttpSession getSharedSession() {
		return _portalHttpSession;
	}

	protected void checkPortalSession() {
		try {
			_portalHttpSession.isNew();
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}

			_portalHttpSession = super.getSession(true);
		}
	}

	protected HttpSession getSharedSessionWrapper(
		HttpSession portalHttpSession, HttpSession portletHttpSession) {

		return new SharedSessionWrapper(portalHttpSession, portletHttpSession);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharedSessionServletRequest.class);

	private HttpSession _portalHttpSession;
	private final boolean _shared;

}