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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdHttpSession;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;
import com.liferay.portal.kernel.util.ServerDetector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class SharedSessionServletRequest extends HttpServletRequestWrapper {

	public SharedSessionServletRequest(
		HttpServletRequest request, boolean shared) {

		super(request);

		_portalSession = request.getSession();

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter() &&
			!(_portalSession instanceof CompoundSessionIdHttpSession)) {

			_portalSession = new CompoundSessionIdHttpSession(_portalSession);
		}

		_shared = shared;
	}

	@Override
	public HttpSession getSession() {
		if (_shared) {
			return _portalSession;
		}
		else {
			return getSharedSessionWrapper(_portalSession, super.getSession());
		}
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (_shared) {
			return _portalSession;
		}
		else {
			return getSharedSessionWrapper(
				_portalSession, super.getSession(create));
		}
	}

	public HttpSession getSharedSession() {
		return _portalSession;
	}

	protected HttpSession getSharedSessionWrapper(
		HttpSession portalSession, HttpSession portletSession) {

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter() &&
			!(portalSession instanceof CompoundSessionIdHttpSession)) {

			portalSession = new CompoundSessionIdHttpSession(portalSession);
		}

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter() &&
			!(portletSession instanceof CompoundSessionIdHttpSession)) {

			portletSession = new CompoundSessionIdHttpSession(portletSession);
		}

		if (ServerDetector.isJetty()) {
			return new JettySharedSessionWrapper(portalSession, portletSession);
		}
		else {
			return new SharedSessionWrapper(portalSession, portletSession);
		}
	}

	private HttpSession _portalSession;
	private boolean _shared;

}