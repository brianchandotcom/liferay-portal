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

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
				catch (IOException ioException) {
					_logger.error(
						"Unable to configure Log4j for bundle " +
							bundle.getSymbolicName(),
						ioException);
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

	private void _configureLog4j(Bundle bundle, String resourcePath)
		throws IOException {

		Enumeration<URL> enumeration = bundle.findEntries(
			"META-INF", resourcePath, false);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				DOMConfigurator domConfigurator = new DOMConfigurator();

				domConfigurator.doConfigure(
					new UnsyncStringReader(
						_getURLContent(enumeration.nextElement())),
					LogManager.getLoggerRepository());
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

		DOMConfigurator domConfigurator = new DOMConfigurator();

		URI uri = configFile.toURI();

		domConfigurator.doConfigure(
			new UnsyncStringReader(_getURLContent(uri.toURL())),
			LogManager.getLoggerRepository());
	}

	private String _escapeXMLAttribute(String s) {
		return StringUtil.replace(
			s,
			new char[] {
				CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.LESS_THAN,
				CharPool.QUOTE
			},
			new String[] {"&amp;", "&apos;", "&lt;", "&quot;"});
	}

	private String _getLiferayHome() {
		if (_liferayHome == null) {
			_liferayHome = _escapeXMLAttribute(
				PropsUtil.get(PropsKeys.LIFERAY_HOME));
		}

		return _liferayHome;
	}

	private String _getURLContent(URL url) {
		String urlContent = null;

		try (InputStream inputStream = url.openStream()) {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(
				inputStream, unsyncByteArrayOutputStream, -1, true);

			byte[] bytes = unsyncByteArrayOutputStream.toByteArray();

			urlContent = new String(bytes, StringPool.UTF8);
		}
		catch (Exception exception) {
			_logger.error(exception, exception);

			return null;
		}

		return StringUtil.replace(
			urlContent, "@liferay.home@", _getLiferayHome());
	}

	private static final Logger _logger = Logger.getLogger(
		Log4jExtenderBundleActivator.class);

	private static String _liferayHome;

	private volatile BundleTracker<Bundle> _bundleTracker;

}