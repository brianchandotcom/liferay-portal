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

import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Enumeration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Shuyang Zhou
 */
public class Log4jExtender implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_tracker = new BundleTracker<Void>(
			bundleContext, Bundle.STARTING, null) {

			@Override
			public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				try {
					_configureLog4j(bundle, "META-INF/module-log4j.xml");
					_configureLog4j(bundle, "META-INF/module-log4j-ext.xml");
					_configureLog4j(bundle.getSymbolicName());
				}
				catch (IOException ioe) {
					_logger.error(
						"Unable to configure Log4j for bundle " +
							bundle.getSymbolicName(),
						ioe);
				}

				return null;
			}

		};

		_tracker.open();
	}

	@Override
	public void stop(BundleContext context) {
		_tracker.close();
	}

	private void _configureLog4j(Bundle bundle, String resourcePath)
		throws IOException {

		Enumeration<URL> enumeration = bundle.getResources(resourcePath);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				DOMConfigurator domConfigurator = new DOMConfigurator();

				domConfigurator.doConfigure(
					enumeration.nextElement(),
					LogManager.getLoggerRepository());
			}
		}
	}

	private void _configureLog4j(String symbolicName)
		throws MalformedURLException {

		File configFile = new File(
			PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/log4j/" + symbolicName +
				"-log4j-ext.xml");

		if (!configFile.exists()) {
			return;
		}

		DOMConfigurator domConfigurator = new DOMConfigurator();

		URI uri = configFile.toURI();

		domConfigurator.doConfigure(
			uri.toURL(), LogManager.getLoggerRepository());
	}

	private static final Logger _logger = Logger.getLogger(Log4jExtender.class);

	private volatile BundleTracker<Void> _tracker;

}