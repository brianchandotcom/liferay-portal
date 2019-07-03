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

package com.liferay.osgi.log.service.extender.internal;

import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.admin.LoggerAdmin;
import org.osgi.service.log.admin.LoggerContext;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class OSGiLogServiceExtenderBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, LoggerAdmin.class,
			new LoggerAdminServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext context) {
		_serviceTracker.close();
	}

	private static Map<String, LogLevel> _loadLogConfigurations(Bundle bundle) {
		Map<String, LogLevel> logLevels = new HashMap<>();

		try {
			_loadLogConfigurations(
				bundle, "osgi-logging.properties", logLevels);
			_loadLogConfigurations(
				bundle, "osgi-logging-ext.properties", logLevels);
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to load OSGi logging configurations for " + bundle,
				ioe);

			return Collections.emptyMap();
		}

		return logLevels;
	}

	private static Map<String, LogLevel> _loadLogConfigurations(
			Bundle bundle, String resourcePath, Map<String, LogLevel> logLevels)
		throws IOException {

		Enumeration<URL> enumeration = bundle.findEntries(
			"META-INF", resourcePath, false);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				Properties properties = new Properties();

				try (InputStream is = url.openStream()) {
					properties.load(is);
				}

				for (String name : properties.stringPropertyNames()) {
					String value = properties.getProperty(name);

					try {
						logLevels.put(name, LogLevel.valueOf(value));
					}
					catch (IllegalArgumentException iae) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Bundle ", bundle, ", resource ",
									resourcePath, ", and logger ", name,
									" contains an invalid log level \"", value,
									"\""));
						}
					}
				}
			}
		}

		return logLevels;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OSGiLogServiceExtenderBundleActivator.class);

	private volatile ServiceTracker<LoggerAdmin, BundleTracker<LoggerContext>>
		_serviceTracker;

	private static class LoggerAdminServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<LoggerAdmin, BundleTracker<LoggerContext>> {

		@Override
		public BundleTracker<LoggerContext> addingService(
			ServiceReference<LoggerAdmin> serviceReference) {

			LoggerAdmin loggerAdmin = _bundleContext.getService(
				serviceReference);

			BundleTracker<LoggerContext> bundleTracker = new BundleTracker<>(
				_bundleContext, ~(Bundle.INSTALLED | Bundle.UNINSTALLED),
				new LoggerContextBundleTrackerCustomizer(loggerAdmin));

			bundleTracker.open();

			return bundleTracker;
		}

		@Override
		public void modifiedService(
			ServiceReference<LoggerAdmin> serviceReference,
			BundleTracker<LoggerContext> bundleTracker) {
		}

		@Override
		public void removedService(
			ServiceReference<LoggerAdmin> serviceReference,
			BundleTracker<LoggerContext> bundleTracker) {

			bundleTracker.close();

			_bundleContext.ungetService(serviceReference);
		}

		private LoggerAdminServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

	private static class LoggerContextBundleTrackerCustomizer
		implements BundleTrackerCustomizer<LoggerContext> {

		@Override
		public LoggerContext addingBundle(
			Bundle bundle, BundleEvent bundleEvent) {

			Map<String, LogLevel> logLevels = _loadLogConfigurations(bundle);

			if (logLevels.isEmpty()) {
				return null;
			}

			for (Map.Entry<String, LogLevel> entry : logLevels.entrySet()) {
				String name = "osgi.logging.".concat(entry.getKey());

				LogLevel logLevel = entry.getValue();

				Log4JUtil.setLevel(name, logLevel.toString(), false);
			}

			LoggerContext loggerContext = _loggerAdmin.getLoggerContext(
				StringBundler.concat(
					bundle.getSymbolicName(), "|", bundle.getVersion(), "|",
					bundle.getLocation()));

			loggerContext.setLogLevels(logLevels);

			return loggerContext;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			LoggerContext loggerContext) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			LoggerContext loggerContext) {

			loggerContext.clear();
		}

		private LoggerContextBundleTrackerCustomizer(LoggerAdmin loggerAdmin) {
			_loggerAdmin = loggerAdmin;
		}

		private final LoggerAdmin _loggerAdmin;

	}

}