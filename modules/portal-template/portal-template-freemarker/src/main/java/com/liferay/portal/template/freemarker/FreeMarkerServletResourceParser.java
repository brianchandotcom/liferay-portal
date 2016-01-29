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

package com.liferay.portal.template.freemarker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.template.URLResourceParser;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.net.URL;

import javax.servlet.ServletContext;

/**
 * @author Mika Koivisto
 */
public class FreeMarkerServletResourceParser extends URLResourceParser {

	@Override
	public URL getURL(String name) throws IOException {
		int pos = name.indexOf(TemplateConstants.SERVLET_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		String servletContextName = name.substring(0, pos);

		if (servletContextName.equals(PortalUtil.getPathContext())) {
			servletContextName = PortalUtil.getServletContextName();
		}

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			_log.error(
				name + " is invalid because " + servletContextName +
					" does not map to a servlet context");

			return null;
		}

		String templateName = name.substring(
			pos + TemplateConstants.SERVLET_SEPARATOR.length());

		if (_log.isDebugEnabled()) {
			_log.debug(
				name + " is associated with the servlet context " +
					servletContextName + " " + servletContext);
		}

		URL url = servletContext.getResource(templateName);

		if (url == null) {
			url = PortalWebResourcesUtil.getResource(templateName);
		}

		if ((url == null) && templateName.endsWith("/init_custom.ftl")) {
			if (_log.isWarnEnabled()) {
				_log.warn("The template " + name + " should be created");
			}

			ServletContext themeClassicServletContext =
				PortalWebResourcesUtil.getServletContext(
					PortalWebResourceConstants.RESOURCE_TYPE_THEME_CLASSIC);

			url = themeClassicServletContext.getResource(
				"/classic/templates/init_custom.ftl");
		}

		return url;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerServletResourceParser.class);

}