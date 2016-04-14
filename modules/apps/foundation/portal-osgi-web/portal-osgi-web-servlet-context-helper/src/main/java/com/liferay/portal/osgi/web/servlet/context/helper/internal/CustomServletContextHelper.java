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

package com.liferay.portal.osgi.web.servlet.context.helper.internal;

import com.liferay.osgi.util.BundleUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebResourceCollectionDefinition;

import java.io.IOException;

import java.net.URL;

import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class CustomServletContextHelper
	extends ServletContextHelper implements ServletContextListener {

	public CustomServletContextHelper(
		Bundle bundle, Boolean wabShapedBundle,
		List<WebResourceCollectionDefinition>
			webResourceCollectionDefinitions) {

		super(bundle);

		_bundle = bundle;
		_wabShapedBundle = wabShapedBundle;
		_webResourceCollectionDefinitions = webResourceCollectionDefinitions;

		Class<?> clazz = getClass();

		_string = clazz.getSimpleName() + '[' + bundle + ']';
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		_servletContext = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		_servletContext = servletContextEvent.getServletContext();
	}

	@Override
	public String getRealPath(String path) {
		return null;
	}

	@Override
	public URL getResource(String name) {
		if ((name == null) || name.contains("*")) {
			return null;
		}

		if (name.isEmpty()) {
			name = StringPool.SLASH;
		}
		else if (name.charAt(0) != CharPool.SLASH) {
			name = StringPool.SLASH.concat(name);
		}

		URL url = BundleUtil.getResourceInBundleOrFragments(_bundle, name);

		if ((url == null) && !_wabShapedBundle) {
			return BundleUtil.getResourceInBundleOrFragments(
				_bundle, "/META-INF/resources" + name);
		}

		return url;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean handleSecurity(
		HttpServletRequest request, HttpServletResponse response) {

		String path = null;

		if (request.getDispatcherType() == DispatcherType.INCLUDE) {
			String pathInfo = (String)request.getAttribute(
				RequestDispatcher.INCLUDE_PATH_INFO);

			path = pathInfo;
		}
		else {
			path = request.getPathInfo();
		}

		if (path == null) {
			return true;
		}

		if (path.indexOf('/') != 0) {
			path = '/' + path;
		}

		if (path.startsWith("/META-INF/") || path.startsWith("/OSGI-INF/") ||
			path.startsWith("/OSGI-OPT/") || path.startsWith("/WEB-INF/")) {

			return forbiddenPath(request, response, path);
		}

		if (ListUtil.isNotEmpty(_webResourceCollectionDefinitions)) {
			for (WebResourceCollectionDefinition
					webResourceCollectionDefinition :
						_webResourceCollectionDefinitions) {

				boolean forbidden = false;

				// url pattern checks

				for (String urlPattern :
						webResourceCollectionDefinition.getUrlPatterns()) {

					if (urlPattern.startsWith("*.")) {

						// Servlet 3 spec section 12.2: Extension mapping

						//Check for *.* pattern
						String patternExtension = urlPattern.substring(2);

						if (Validator.isNotNull(patternExtension) &&
							Validator.equals("*", patternExtension)) {

							forbidden = true;
							break;
						}

						//Check for extension
						int pathExtensionLastIndexOf = path.lastIndexOf(".");

						String pathExtension = path.substring(
							pathExtensionLastIndexOf + 1);

						if (Validator.equals(patternExtension, pathExtension)) {
							forbidden = true;
							break;
						}
					}
					else if (urlPattern.endsWith("/*")) {
						// Servlet 3 spec section 12.2: Path mapping

						if (urlPattern.equals("/*")) {
							forbidden = true;
							break;
						}

						String pathCompare = path;

						String urlPatternPath = urlPattern.substring(
							0, urlPattern.indexOf("/*") +1);

						int pathCompareSlashIndexOf = pathCompare.lastIndexOf(
							"/");

						if (pathCompareSlashIndexOf > 0) {
							pathCompare = pathCompare.substring(
								0, pathCompareSlashIndexOf +1);
						}

						if (Validator.equals(urlPatternPath, pathCompare)) {
							forbidden = true;
							break;
						}
					}
					else if (Validator.equals(urlPattern, path)) {
						forbidden = true;
						break;
					}
				}

				if (forbidden) {
					String requestMethod = request.getMethod();

					// Http method checks. Servlet 3 spec. 13.8.1

					List<String> httpMethods =
						webResourceCollectionDefinition.getHttpMethods();

					if (ListUtil.isNotEmpty(httpMethods) &&
						!httpMethods.contains(requestMethod)) {

						forbidden = false;
					}

					// Http method exception checks. Servlet 3 spec. 13.8.1

					List<String> httpMethodExceptions =
						webResourceCollectionDefinition.
							getHttpMethodExceptions();

					if (ListUtil.isNotEmpty(httpMethodExceptions) &&
						httpMethodExceptions.contains(requestMethod)) {

						forbidden = false;
					}
				}

				if (forbidden) {
					return forbiddenPath(request, response, path);
				}
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return _string;
	}

	protected boolean forbiddenPath(
		HttpServletRequest request, HttpServletResponse response, String path) {

		try {
			ServletContext servletContext = request.getServletContext();

			servletContext.log(
				"[WAB ERROR] Attempt to load illegal path " + path + " in " +
					toString());

			response.sendError(HttpServletResponse.SC_FORBIDDEN, path);
		}
		catch (IOException ioe) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

		return false;
	}

	private final Bundle _bundle;
	private ServletContext _servletContext;
	private final String _string;
	private final boolean _wabShapedBundle;
	private final List<WebResourceCollectionDefinition>
		_webResourceCollectionDefinitions;

}