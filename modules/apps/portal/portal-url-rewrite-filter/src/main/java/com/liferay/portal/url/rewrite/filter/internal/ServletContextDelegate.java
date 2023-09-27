/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.rewrite.filter.internal;

import java.io.InputStream;

import java.util.Objects;

import javax.servlet.ServletContext;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * @author Shuyang Zhou
 */
public class ServletContextDelegate {

	public ServletContextDelegate(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public InputStream getResourceAsStream(String path) {
		if (Objects.equals(path, UrlRewriteFilter.DEFAULT_WEB_CONF_PATH)) {
			return ServletContextDelegate.class.getResourceAsStream(
				"dependencies/urlrewrite.xml");
		}

		return _servletContext.getResourceAsStream(path);
	}

	private final ServletContext _servletContext;

}