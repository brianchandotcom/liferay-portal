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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String path = httpServletRequest.getPathInfo();

		if (_log.isDebugEnabled()) {
			_log.debug("Path " + path);
		}

		try {
			AuthTokenUtil.checkCSRFToken(
				httpServletRequest, LanguageServlet.class.getName());
		}
		catch (PortalException portalException) {
			_log.error(
				"Invalid authentication token received", portalException);

			PortalUtil.sendError(
				HttpServletResponse.SC_UNAUTHORIZED, portalException,
				httpServletRequest, httpServletResponse);

			return;
		}

		if (Validator.isNotNull(path) && path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		if (pathArray.length == 0) {
			_log.error("Language id is not specified");

			httpServletResponse.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				httpServletRequest.getRequestURI());

			return;
		}

		if (pathArray.length == 1) {
			_log.error("Language key is not specified");

			httpServletResponse.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				httpServletRequest.getRequestURI());

			return;
		}

		Locale locale = LocaleUtil.fromLanguageId(pathArray[0]);
		String key = pathArray[1];

		Object[] arguments = null;

		if (pathArray.length > 2) {
			arguments = new Object[pathArray.length - 2];

			System.arraycopy(pathArray, 2, arguments, 0, arguments.length);
		}

		String value = key;

		try {
			if (ArrayUtil.isEmpty(arguments)) {
				value = LanguageUtil.get(locale, key);
			}
			else {
				value = LanguageUtil.format(locale, key, arguments);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		if (!LanguageUtil.isValidLanguageKey(locale, key)) {
			httpServletResponse.setDateHeader(HttpHeaders.EXPIRES, 0);
			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
			httpServletResponse.setHeader(
				HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);
		}

		httpServletResponse.setContentType(ContentTypes.TEXT_PLAIN_UTF8);
		httpServletResponse.setHeader(
			HttpHeaders.CONTENT_DISPOSITION, _CONTENT_DISPOSITION);

		ServletResponseUtil.write(
			httpServletResponse, value.getBytes(StringPool.UTF8));
	}

	private static final String _CONTENT_DISPOSITION =
		"attachment; filename=language.txt";

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageServlet.class);

}