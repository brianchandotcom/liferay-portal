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

package com.liferay.portal.ldap.internal.portal.settings.web.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael C. Han
 */
public abstract class PortalSettingsBaseDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJSPPath());

		try {
			requestDispatcher.include(request, response);
		}
		catch (ServletException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to include JSP " + getJSPPath(), se);
			}

			throw new IOException("Unable to include JSP " + getJSPPath(), se);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
	}

	protected abstract String getJSPPath();

	protected ServletContext servletContext;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSettingsBaseDynamicInclude.class);

}