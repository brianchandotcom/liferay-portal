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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
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
		URL url = null;

		int pos = name.indexOf(TemplateConstants.SERVLET_SEPARATOR);

		if (pos == -1) {
			return url;
		}

		String servletContextName = name.substring(0, pos);

		String servletContextPath = ContextPathUtil.getContextPath(
			StringPool.SLASH + servletContextName);

		String contextPath = PortalUtil.getPathContext();

		if (Validator.isNull(servletContextName) ||
			servletContextPath.equals(contextPath)) {

			servletContextName = contextPath;
		}

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext != null) {
			String templateName = name.substring(
				pos + TemplateConstants.SERVLET_SEPARATOR.length());

			if (_log.isDebugEnabled()) {
				_log.debug(
					name + " is associated with the servlet context " +
						servletContextName + " " + servletContext);
			}

			url = servletContext.getResource(templateName);

			if ((url == null) && templateName.endsWith("/init_custom.ftl")) {
				if (_log.isWarnEnabled()) {
					_log.warn("The template " + name + " should be created");
				}

				String portalServletContextName = PortalUtil.getPathContext();

				ServletContext portalServletContext = ServletContextPool.get(
					portalServletContextName);

				url = portalServletContext.getResource(
					"/html/themes/_unstyled/template/init_custom.ftl");
			}
		}
		else {
			_log.error(
				name + " is not valid because " + servletContextName +
					" does not map to a servlet context");
		}

		return url;
	}

	private static Log _log = LogFactoryUtil.getLog(
		FreeMarkerServletResourceParser.class);

}