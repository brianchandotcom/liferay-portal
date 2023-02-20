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

import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Stian Sigvartsen
 */
public class AuthVerifierServletRequest extends ProtectedServletRequest {

	public AuthVerifierServletRequest(
		HttpServletRequest httpServletRequest, long userId, String authType) {

		super(httpServletRequest, String.valueOf(userId), authType);

		_userId = userId;

		httpServletRequest.removeAttribute(WebKeys.USER);
		httpServletRequest.setAttribute(WebKeys.USER_ID, userId);
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)getRequest();

		if (HttpServletRequest.FORM_AUTH.equals(getAuthType())) {
			return httpServletRequest.getSession(create);
		}

		HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession == null) {
			if (create) {
				_isolatedHttpSession = new IsolatedHttpSession(
					httpServletRequest.getSession(true));
			}
			else {
				_isolatedHttpSession = null;
			}
		}
		else if ((_isolatedHttpSession == null) ||
				 !httpSession.equals(_isolatedHttpSession._httpSession)) {

			_isolatedHttpSession = new IsolatedHttpSession(httpSession);
		}

		return _isolatedHttpSession;
	}

	private IsolatedHttpSession _isolatedHttpSession;
	private final Long _userId;

	private class IsolatedHttpSession extends HttpSessionWrapper {

		public IsolatedHttpSession(HttpSession httpSession) {
			super(httpSession);

			_httpSession = httpSession;
		}

		@Override
		public Object getAttribute(String name) {
			if (name.equals(WebKeys.USER_ID)) {
				return _userId;
			}

			return _attributes.get(name);
		}

		@Override
		public void setAttribute(String name, Object value) {
			_attributes.put(name, value);
		}

		private final Map<String, Object> _attributes = new HashMap<>();
		private final HttpSession _httpSession;

	}

}