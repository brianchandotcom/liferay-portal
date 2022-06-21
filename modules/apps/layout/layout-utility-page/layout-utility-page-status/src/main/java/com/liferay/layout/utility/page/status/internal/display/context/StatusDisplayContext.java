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

package com.liferay.layout.utility.page.status.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class StatusDisplayContext {

	public StatusDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getEscapedURL(ThemeDisplay themeDisplay) {
		String url = ParamUtil.getString(_httpServletRequest, "previousURL");

		if (Validator.isNull(url)) {
			url = PortalUtil.getCurrentURL(_httpServletRequest);
		}

		return HtmlUtil.escape(
			HttpComponentsUtil.decodeURL(themeDisplay.getPortalURL() + url));
	}

	public boolean isNoSuchResourceException() {
		String exception = ParamUtil.getString(
			_httpServletRequest, "exception");

		for (String key : SessionErrors.keySet(_httpServletRequest)) {
			key = key.substring(key.lastIndexOf(StringPool.PERIOD) + 1);

			if (key.startsWith("NoSuch") && key.endsWith("Exception")) {
				return true;
			}
		}

		if (GetterUtil.getBoolean(
				_httpServletRequest.getAttribute(
					NoSuchLayoutException.class.getName()))) {

			return true;
		}
		else if (Validator.isNotNull(exception)) {
			exception = exception.substring(
				exception.lastIndexOf(StringPool.PERIOD) + 1);

			if (exception.startsWith("NoSuch") &&
				exception.endsWith("Exception")) {

				return true;
			}
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;

}