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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.AuthTokenUtil;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String path = request.getPathInfo();

		if (_log.isDebugEnabled()) {
			_log.debug("Path " + path);
		}

		try {
			AuthTokenUtil.checkCSRFToken(
				request, LanguageServlet.class.getName());
		}
		catch (PortalException pe) {
			_log.error("Invalid authentication token received");

			return;
		}

		if (Validator.isNotNull(path) && path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		if (pathArray.length == 0) {
			_log.error("Language id is not specified");

			return;
		}

		if (pathArray.length == 1) {
			_log.error("Language key is not specified");

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
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if (!LanguageUtil.isValidLanguageKey(locale, key)) {
			response.setDateHeader(HttpHeaders.EXPIRES, 0);
			response.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
			response.setHeader(
				HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);
		}

		response.setContentType(ContentTypes.TEXT_PLAIN_UTF8);
		response.setHeader(
			HttpHeaders.CONTENT_DISPOSITION, _CONTENT_DISPOSITION);

		ServletResponseUtil.write(response, value.getBytes(StringPool.UTF8));
	}

	private static final String _CONTENT_DISPOSITION =
		"attachment; filename=language.txt";

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageServlet.class);

}