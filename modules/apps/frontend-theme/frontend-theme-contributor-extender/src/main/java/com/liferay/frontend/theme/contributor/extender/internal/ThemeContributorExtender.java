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

package com.liferay.frontend.theme.contributor.extender.internal;

import com.liferay.osgi.felix.util.AbstractExtender;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

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
		_logger = new Logger(bundleContext);

		start(bundleContext);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		stop(bundleContext);
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		_logger.log(
			Logger.LOG_DEBUG, StringBundler.concat("[", bundle, "] ", s));
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		String type = _getProperty(
			bundle, "Liferay-Theme-Contributor-Type", "themeContributorType");

		if (type == null) {
			return null;
		}

		BundleWebResourcesImpl bundleWebResources = _scanForResources(bundle);

		if (bundleWebResources == null) {
			return null;
		}

		int themeContributorWeight = GetterUtil.getInteger(
			_getProperty(
				bundle, "Liferay-Theme-Contributor-Weight",
				"themeContributorWeight"));

		return new ThemeContributorExtension(
			bundle, bundleWebResources, themeContributorWeight);
	}

	@Override
	protected void error(String s, Throwable t) {
		_logger.log(Logger.LOG_ERROR, s, t);
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable t) {
		_logger.log(
			Logger.LOG_WARNING, StringBundler.concat("[", bundle, "] ", s), t);
	}

	private String _getProperty(
		Bundle bundle, String headerName, String jsonName) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String type = headers.get(headerName);

		if (type == null) {
			URL entryURL = bundle.getEntry("/package.json");

			if (entryURL != null) {
				try (Reader reader =
						new InputStreamReader(entryURL.openStream())) {

					JSONTokener jsonTokener = new JSONTokener(reader);

					JSONObject packageJSONObject = new JSONObject(jsonTokener);

					JSONObject liferayThemeJSONObject =
						packageJSONObject.optJSONObject("liferayTheme");

					if (liferayThemeJSONObject != null) {
						type = liferayThemeJSONObject.getString(jsonName);
					}
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}

		return type;
	}

	private BundleWebResourcesImpl _scanForResources(Bundle bundle) {
		final List<String> cssResourcePaths = new ArrayList<>();
		final List<String> jsResourcePaths = new ArrayList<>();

		Enumeration<URL> cssEntries = bundle.findEntries(
			"/META-INF/resources", "*.css", true);
		Enumeration<URL> jsEntries = bundle.findEntries(
			"/META-INF/resources", "*.js", true);

		if (cssEntries != null) {
			while (cssEntries.hasMoreElements()) {
				URL url = cssEntries.nextElement();

				String path = url.getFile();

				path = path.replace("/META-INF/resources", "");

				int index = path.lastIndexOf('/');

				if (!StringPool.UNDERLINE.equals(path.charAt(index + 1)) &&
					!path.endsWith("_rtl.css")) {

					cssResourcePaths.add(path);
				}
			}
		}

		if (jsEntries != null) {
			while (jsEntries.hasMoreElements()) {
				URL url = jsEntries.nextElement();

				String path = url.getFile();

				jsResourcePaths.add(path.replace("/META-INF/resources", ""));
			}
		}

		if (cssResourcePaths.isEmpty() && jsResourcePaths.isEmpty()) {
			return null;
		}

		return new BundleWebResourcesImpl(cssResourcePaths, jsResourcePaths);
	}

	private Logger _logger;

}