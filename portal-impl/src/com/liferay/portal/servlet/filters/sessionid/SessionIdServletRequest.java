/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.sessionid;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionIdServletRequest extends HttpServletRequestWrapper {

	public SessionIdServletRequest(
		HttpServletRequest request, HttpServletResponse response) {

		super(request);

		_response = response;
	}

	@Override
	public HttpSession getSession() {
		HttpSession session = super.getSession();

		process(session);

		return session;
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession session = super.getSession(create);

		process(session);

		return session;
	}

	protected void process(HttpSession session) {
		if ((session == null) || !session.isNew() || !isSecure() ||
			isRequestedSessionIdFromCookie()) {

			return;
		}

		Object jsessionIdAlreadySet = getAttribute(_JESSIONID_ALREADY_SET);

		if (jsessionIdAlreadySet != null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + session.getId());
		}

		Cookie cookie = new Cookie(_JESSIONID, session.getId());

		cookie.setMaxAge(-1);

		String contextPath = getContextPath();

		if (Validator.isNotNull(contextPath)) {
			cookie.setPath(contextPath);
		}
		else {
			cookie.setPath(StringPool.SLASH);
		}

		CookieKeys.addCookie(
			(HttpServletRequest)super.getRequest(), _response, cookie);

		setAttribute(_JESSIONID_ALREADY_SET, Boolean.TRUE);
	}

	private static final String _JESSIONID = "JSESSIONID";

	private static final String _JESSIONID_ALREADY_SET =
		"JESSIONID_ALREADY_SET";

	private static Log _log = LogFactoryUtil.getLog(
		SessionIdServletRequest.class);

	private HttpServletResponse _response;

}