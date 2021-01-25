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

package com.liferay.portal.log4j.extender.internal;

import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Enumeration;

import org.apache.log4j.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Shuyang Zhou
 */
public class Log4jExtenderBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_bundleTracker = new BundleTracker<Bundle>(
			bundleContext, ~(Bundle.INSTALLED | Bundle.UNINSTALLED), null) {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				try {
					_configureLog4j(bundle, "module-log4j.xml");
					_configureLog4j(bundle, "module-log4j-ext.xml");
					_configureLog4j(bundle.getSymbolicName());
				}
				catch (MalformedURLException malformedURLException) {
					_logger.error(
						"Unable to configure Log4j for bundle " +
							bundle.getSymbolicName(),
						malformedURLException);
				}

				return bundle;
			}

		};

		_bundleTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_bundleTracker.close();
	}

	private void _configureLog4j(Bundle bundle, String resourcePath) {
		Enumeration<URL> enumeration = bundle.findEntries(
			"META-INF", resourcePath, false);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Log4JUtil.configureLog4J(enumeration.nextElement());
			}
		}
	}

	private void _configureLog4j(String symbolicName)
		throws MalformedURLException {

		File configFile = new File(
			StringBundler.concat(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR, "/log4j/", symbolicName,
				"-log4j-ext.xml"));

		if (!configFile.exists()) {
			return;
		}

		URI uri = configFile.toURI();

		Log4JUtil.configureLog4J(uri.toURL());
	}

	private static final Logger _logger = Logger.getLogger(
		Log4jExtenderBundleActivator.class);

	private volatile BundleTracker<Bundle> _bundleTracker;

}