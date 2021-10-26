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

package com.liferay.portal.kernel.servlet;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

/**
 * @author Leonardo Barros
 * @author Gregory Amerson
 */
public class DynamicResourceIncludeUtil {

	public static ServletContext getPathServletContext(String path) {
		ServletContext pathServletContext = null;

		for (ServletContext servletContext : _servletContexts) {
			String contextPath = servletContext.getContextPath();

			if (Validator.isNotNull(contextPath) &&
				path.startsWith(contextPath)) {

				if (pathServletContext == null) {
					pathServletContext = servletContext;
				}
				else {
					String pathServletContextContextPath =
						pathServletContext.getContextPath();

					if (contextPath.length() >
							pathServletContextContextPath.length()) {

						pathServletContext = servletContext;
					}
				}
			}
		}

		return pathServletContext;
	}

	public static URL getResource(ServletContext servletContext, String path) {
		if (servletContext == null) {
			return null;
		}

		path = PortalWebResourcesUtil.stripContextPath(servletContext, path);

		try {
			URL url = servletContext.getResource(path);

			if (url != null) {
				return url;
			}
		}
		catch (MalformedURLException malformedURLException) {
			if (_log.isDebugEnabled()) {
				_log.debug(malformedURLException, malformedURLException);
			}
		}

		return null;
	}

	public static URL getResource(String path) {
		ServletContext servletContext = getPathServletContext(path);

		if (servletContext != null) {
			return getResource(servletContext, path);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicResourceIncludeUtil.class);

	private static final ServiceTrackerList<ServletContext> _servletContexts =
		ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), ServletContext.class);

}