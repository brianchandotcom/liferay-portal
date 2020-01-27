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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebResourceCollectionDefinition;
import com.liferay.portal.servlet.delegate.ServletContextDelegate;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class CustomServletContextHelper
	extends ServletContextHelper implements ServletContextListener {

	public CustomServletContextHelper(
		Bundle bundle,
		List<WebResourceCollectionDefinition>
			webResourceCollectionDefinitions) {

		super(bundle);

		_bundle = bundle;
		_webResourceCollectionDefinitions = webResourceCollectionDefinitions;

		_overrideDirName = StringBundler.concat(
			PropsValues.LIFERAY_HOME, File.separator, "work", File.separator,
			_bundle.getSymbolicName(), StringPool.DASH, _bundle.getVersion());

		Class<?> clazz = getClass();

		_string = clazz.getSimpleName() + '[' + bundle + ']';
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContextClassLoaderPool.unregister(
			_servletContext.getServletContextName());

		_servletContext = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		_servletContext = ServletContextDelegate.create(
			servletContextEvent.getServletContext());

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		ServletContextClassLoaderPool.register(
			_servletContext.getServletContextName(),
			bundleWiring.getClassLoader());
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

		URL url = null;

		if (PropsValues.WORK_DIR_OVERRIDE_ENABLED &&
			(name.endsWith(".css") || name.endsWith(".js"))) {

			String overrideName = StringUtil.removeSubstring(
				name, "/META-INF/resources");

			File file = new File(_overrideDirName, overrideName);

			if (file.exists()) {
				try {
					URI uri = file.toURI();

					url = uri.toURL();
				}
				catch (MalformedURLException malformedURLException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid override URL " + file.toString(),
							malformedURLException);
					}
				}
			}
		}

		if (url == null) {
			url = BundleUtil.getResourceInBundleOrFragments(_bundle, name);
		}

		if (url == null) {
			url = BundleUtil.getResourceInBundleOrFragments(
				_bundle, "/META-INF/resources" + name);
		}

		if (url == null) {
			try {
				Enumeration<URL> enumeration = _bundle.getResources(
					"/META-INF/resources" + name);

				if ((enumeration != null) && enumeration.hasMoreElements()) {
					url = enumeration.nextElement();
				}
			}
			catch (IOException ioException) {
				_log.error(
					StringBundler.concat(
						"Unable to get resource name ", name, " on bundle ",
						_bundle),
					ioException);
			}
		}

		return url;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean handleSecurity(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if ((httpServletRequest.getDispatcherType() != DispatcherType.ASYNC) &&
			(httpServletRequest.getDispatcherType() !=
				DispatcherType.REQUEST)) {

			return true;
		}

		String path = httpServletRequest.getPathInfo();

		if (path == null) {
			return true;
		}

		if (path.indexOf('/') != 0) {
			path = '/' + path;
		}

		if (path.startsWith("/META-INF/") || path.startsWith("/OSGI-INF/") ||
			path.startsWith("/OSGI-OPT/") || path.startsWith("/WEB-INF/")) {

			return sendErrorForbidden(
				httpServletRequest, httpServletResponse, path);
		}

		if (ListUtil.isEmpty(_webResourceCollectionDefinitions)) {
			return true;
		}

		for (WebResourceCollectionDefinition webResourceCollectionDefinition :
				_webResourceCollectionDefinitions) {

			boolean forbidden = false;

			for (String urlPattern :
					webResourceCollectionDefinition.getUrlPatterns()) {

				// Servlet 3 spec 12.2

				if (urlPattern.startsWith("*.")) {
					String patternExtension = urlPattern.substring(2);

					if (Validator.isNotNull(patternExtension) &&
						Objects.equals("*", patternExtension)) {

						forbidden = true;

						break;
					}

					int index = path.lastIndexOf(".");

					String pathExtension = path.substring(index + 1);

					if (Objects.equals(patternExtension, pathExtension)) {
						forbidden = true;

						break;
					}
				}
				else if (urlPattern.endsWith("/*")) {
					if (urlPattern.equals("/*")) {
						forbidden = true;

						break;
					}

					String subpath = path;

					String urlPatternPath = urlPattern.substring(
						0, urlPattern.indexOf("/*") + 1);

					int index = subpath.lastIndexOf("/");

					if (index > 0) {
						subpath = subpath.substring(0, index + 1);
					}

					if (Objects.equals(urlPatternPath, subpath)) {
						forbidden = true;

						break;
					}
				}
				else if (Objects.equals(urlPattern, path)) {
					forbidden = true;

					break;
				}
			}

			if (forbidden) {

				// Servlet 3 spec 13.8.1

				List<String> httpMethods =
					webResourceCollectionDefinition.getHttpMethods();

				if (ListUtil.isNotEmpty(httpMethods) &&
					!httpMethods.contains(httpServletRequest.getMethod())) {

					forbidden = false;
				}

				List<String> httpMethodExceptions =
					webResourceCollectionDefinition.getHttpMethodExceptions();

				if (ListUtil.isNotEmpty(httpMethodExceptions) &&
					httpMethodExceptions.contains(
						httpServletRequest.getMethod())) {

					forbidden = false;
				}
			}

			if (forbidden) {
				return sendErrorForbidden(
					httpServletRequest, httpServletResponse, path);
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return _string;
	}

	protected boolean sendErrorForbidden(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String path) {

		try {
			ServletContext servletContext =
				httpServletRequest.getServletContext();

			servletContext.log(
				StringBundler.concat(
					"[WAB ERROR] Attempt to load illegal path ", path, " in ",
					toString()));

			httpServletResponse.sendError(
				HttpServletResponse.SC_FORBIDDEN, path);
		}
		catch (IOException ioException) {
			httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomServletContextHelper.class);

	private final Bundle _bundle;
	private final String _overrideDirName;
	private ServletContext _servletContext;
	private final String _string;
	private final List<WebResourceCollectionDefinition>
		_webResourceCollectionDefinitions;

}