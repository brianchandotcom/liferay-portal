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

package com.liferay.portal.theme.contributor.extender;

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.apache.felix.utils.extender.AbstractExtender;
import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael Bradford
 */
@Component(immediate = true)
public class ThemeContributorExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;
		_logger = new Logger(bundleContext);

		start(bundleContext);
	}

	@Deactivate
	protected void deactivate() throws Exception {
		stop(_bundleContext);

		_bundleContext = null;
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + s);
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Dictionary<String, String> headers = bundle.getHeaders();

		String type = headers.get("Theme-Contributor-Type");

		if (type == null) {
			URL entryURL = bundle.getEntry("/package.json");

			if (entryURL != null) {
				try (Reader reader =
					new InputStreamReader(entryURL.openStream())) {

					JSONTokener jsonTokener = new JSONTokener(reader);

					JSONObject packageJsonObject = new JSONObject(jsonTokener);

					JSONObject liferayThemeJSONObject =
						packageJsonObject.getJSONObject("liferayTheme");

					type = liferayThemeJSONObject.getString(
						"themeContributorType");
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}

		if (type == null) {
			return null;
		}

		BundleWebResources bundleWebResources = _scanForResources(bundle);

		if (bundleWebResources == null) {
			return null;
		}

		return new ThemeContributorExtension(bundle, bundleWebResources);
	}

	@Override
	protected void error(String s, Throwable t) {
		_logger.log(Logger.LOG_ERROR, s, t);
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable t) {
		_logger.log(Logger.LOG_WARNING, "[" + bundle + "] " + s, t);
	}

	protected static class BundleWebResources {

		public BundleWebResources(
			Collection<String> cssResourcePaths,
			Collection<String> jsResourcePaths) {

			_cssResourcePaths = cssResourcePaths;
			_jsResourcePaths = jsResourcePaths;
		}

		public Collection<String> getCssResourcePaths() {
			return _cssResourcePaths;
		}

		public Collection<String> getJsResourcePaths() {
			return _jsResourcePaths;
		}

		private final Collection<String> _cssResourcePaths;
		private final Collection<String> _jsResourcePaths;

	}

	private BundleWebResources _scanForResources(Bundle bundle) {
		final List<String> cssResourcePaths = new ArrayList<>();
		final List<String> jsResourcePaths = new ArrayList<>();

		Enumeration<URL> cssEntries = bundle.findEntries(
			"/META-INF/resources", "*.css", true);
		Enumeration<URL> jsEntries = bundle.findEntries(
			"/META-INF/resources", "*.js", true);

		if (cssEntries != null) {
			while (cssEntries.hasMoreElements()) {
				URL entry = cssEntries.nextElement();

				String path = entry.getFile();

				path = path.replace("/META-INF/resources", "");

				int lastIndexOfSlash = path.lastIndexOf('/');

				if (!StringPool.UNDERLINE.equals(
						path.charAt(lastIndexOfSlash + 1)) &&
					!path.endsWith("_rtl.css")) {

					cssResourcePaths.add(path);
				}
			}
		}

		if (jsEntries != null) {
			while (jsEntries.hasMoreElements()) {
				URL entry = jsEntries.nextElement();

				String path = entry.getFile();

				jsResourcePaths.add(path.replace("/META-INF/resources", ""));
			}
		}

		if (cssResourcePaths.isEmpty() && jsResourcePaths.isEmpty()) {
			return null;
		}
		else {
			return new BundleWebResources(cssResourcePaths, jsResourcePaths);
		}
	}

	private BundleContext _bundleContext;
	private Logger _logger;

}