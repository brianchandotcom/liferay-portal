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

package com.liferay.portal.servlet.filters.sessionid;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		super(httpServletRequest);

		_httpServletResponse = httpServletResponse;
	}

	@Override
	public HttpSession getSession() {
		HttpSession httpSession = super.getSession();

		process(httpSession);

		return httpSession;
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession httpSession = super.getSession(create);

		process(httpSession);

		return httpSession;
	}

	protected void process(HttpSession httpSession) {
		if ((httpSession == null) || !httpSession.isNew() || !isSecure() ||
			isRequestedSessionIdFromCookie()) {

			return;
		}

		Object jSessionIdAlreadySet = getAttribute(_JSESSIONID_ALREADY_SET);

		if (jSessionIdAlreadySet != null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + httpSession.getId());
		}

		Cookie cookie = new Cookie(_JSESSIONID, httpSession.getId());

		cookie.setMaxAge(-1);

		String contextPath = getContextPath();

		if (Validator.isNotNull(contextPath)) {
			cookie.setPath(contextPath);
		}
		else {
			cookie.setPath(StringPool.SLASH);
		}

		CookiesManagerUtil.addCookie(
			CookiesConstants.CONSENT_TYPE_NECESSARY, cookie,
			(HttpServletRequest)super.getRequest(), _httpServletResponse);

		setAttribute(_JSESSIONID_ALREADY_SET, Boolean.TRUE);
	}

	private static final String _JSESSIONID = "JSESSIONID";

	private static final String _JSESSIONID_ALREADY_SET =
		"JSESSIONID_ALREADY_SET";

	private static final Log _log = LogFactoryUtil.getLog(
		SessionIdServletRequest.class);

	private final HttpServletResponse _httpServletResponse;

}