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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Provides a base abstract class to implement servlets that return JavaScript
 * modules tracked by the {@link
 * com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry}.
 *
 * @author Adolfo Pérez
 */
public abstract class BaseBuiltInJSModuleServlet extends HttpServlet {

	public BaseBuiltInJSModuleServlet() {
		_workDirName = StringBundler.concat(
			PropsValues.LIFERAY_HOME, File.separator, "work");
	}

	@Override
	public void destroy() {
		_bundleSymbolicNameServiceTrackerMap.close();
	}

	@Override
	public void init() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleSymbolicNameServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundle.getBundleContext(), ResourceBundleLoader.class,
				"bundle.symbolic.name");
	}

	protected abstract MimeTypes getMimeTypes();

	/**
	 * Returns the requested resource descriptor. This is a template method that
	 * must be implemented by subclasses to lookup the requested resource.
	 *
	 * @param  pathInfo the request's pathInfo
	 * @return the {@link String} content of the resource or <code>null</code>
	 */
	protected abstract ResourceDescriptor getResourceDescriptor(
		String pathInfo);

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String pathInfo = httpServletRequest.getPathInfo();

		ResourceDescriptor resourceDescriptor = getResourceDescriptor(pathInfo);

		if (resourceDescriptor == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		_setContentType(httpServletResponse, pathInfo);

		String languageId = httpServletRequest.getParameter("languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		_sendResource(
			httpServletResponse, resourceDescriptor, locale, pathInfo);
	}

	private void _sendResource(
			HttpServletResponse httpServletResponse,
			ResourceDescriptor resourceDescriptor, Locale locale,
			String pathInfo)
		throws IOException {

		JSPackage jsPackage = resourceDescriptor.getJsPackage();

		URL url = null;

		if (PropsValues.WORK_DIR_OVERRIDE_ENABLED && pathInfo.endsWith(".js")) {
			JSBundle jsBundle = jsPackage.getJSBundle();

			File file = new File(
				_workDirName,
				StringBundler.concat(
					jsBundle.getName(), StringPool.DASH, jsBundle.getVersion(),
					File.separator, resourceDescriptor.getPackagePath()));

			if (file.exists()) {
				try {
					URI uri = file.toURI();

					url = uri.toURL();
				}
				catch (MalformedURLException murle) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid override URL " + file.toString(), murle);
					}
				}
			}
		}

		if (url == null) {
			url = jsPackage.getResourceURL(resourceDescriptor.getPackagePath());
		}

		if (url == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		try (InputStream inputStream = url.openStream()) {
			String content = StringUtil.read(inputStream);

			httpServletResponse.setCharacterEncoding(StringPool.UTF8);

			PrintWriter printWriter = httpServletResponse.getWriter();

			String extension = FileUtil.getExtension(pathInfo);

			if (extension.equals("js")) {
				JSBundle jsBundle = jsPackage.getJSBundle();

				ResourceBundleLoader resourceBundleLoader =
					_bundleSymbolicNameServiceTrackerMap.getService(
						jsBundle.getName());

				if (resourceBundleLoader != null) {
					content = LanguageUtil.process(
						() -> resourceBundleLoader.loadResourceBundle(locale),
						locale, content);
				}
			}

			printWriter.print(content);
		}
		catch (IOException ioe) {
			_log.error("Unable to read " + resourceDescriptor.toString(), ioe);

			httpServletResponse.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"Unable to read file");
		}
	}

	private void _setContentType(
		HttpServletResponse httpServletResponse, String pathInfo) {

		String extension = FileUtil.getExtension(pathInfo);

		if (extension.equals("js")) {
			httpServletResponse.setContentType(
				ContentTypes.TEXT_JAVASCRIPT_UTF8);
		}
		else if (extension.equals("map")) {
			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		}
		else {
			MimeTypes mimeTypes = getMimeTypes();

			httpServletResponse.setContentType(
				mimeTypes.getContentType(pathInfo));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBuiltInJSModuleServlet.class);

	private ServiceTrackerMap<String, ResourceBundleLoader>
		_bundleSymbolicNameServiceTrackerMap;
	private final String _workDirName;

}