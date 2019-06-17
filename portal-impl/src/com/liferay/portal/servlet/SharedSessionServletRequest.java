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

import com.liferay.portal.resiliency.spi.agent.SPIAgentRequest;

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

		_portalSession = httpServletRequest.getSession();
		_shared = shared;
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
			return _portalSession;
		}

		HttpSession portletSession = super.getSession(create);

		if ((portletSession != null) && (portletSession != _portalSession)) {
			SPIAgentRequest.populatePortletSessionAttributes(
				this, portletSession);

			return getSharedSessionWrapper(_portalSession, portletSession);
		}

		return portletSession;
	}

	public HttpSession getSharedSession() {
		return _portalSession;
	}

	protected void checkPortalSession() {
		try {
			_portalSession.isNew();
		}
		catch (IllegalStateException ise) {
			_portalSession = super.getSession(true);
		}
	}

	protected HttpSession getSharedSessionWrapper(
		HttpSession portalSession, HttpSession portletSession) {

		return new SharedSessionWrapper(portalSession, portletSession);
	}

	private HttpSession _portalSession;
	private final boolean _shared;

}