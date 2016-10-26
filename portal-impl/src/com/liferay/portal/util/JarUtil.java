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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Shuyang Zhou
 */
public class JarUtil {

	public static Path downloadAndInstallJar(
			URL url, String libPath, String name)
		throws Exception {

		String protocol = url.getProtocol();

		if (PortalRunMode.isTestMode() &&
			(protocol.equals(Http.HTTP) || protocol.equals(Http.HTTPS))) {

			String urlString = url.toExternalForm();

			if (!urlString.contains("mirrors")) {
				try {
					InetAddress.getAllByName("mirrors");

					String newURLString = StringUtil.replace(
						urlString, "://", "://mirrors/");

					url = new URL(newURLString);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Swapping URL from " + urlString + " to " +
								newURLString);
					}
				}
				catch (UnknownHostException uhe) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to resolve \"mirrors\"");
					}
				}
			}
		}

		Path path = Paths.get(libPath, name);

		if (_log.isInfoEnabled()) {
			_log.info("Downloading " + url + " to " + path);
		}

		try (InputStream inputStream = url.openStream()) {
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Downloaded " + url + " to " + path);
		}

		return path;
	}

	public static void downloadAndInstallJar(
			URL url, String libPath, String name, URLClassLoader urlClassLoader)
		throws Exception {

		Path path = downloadAndInstallJar(url, libPath, name);

		URI uri = path.toUri();

		if (_log.isInfoEnabled()) {
			_log.info("Installing " + path + " to " + urlClassLoader);
		}

		_addURLMethod.invoke(urlClassLoader, uri.toURL());

		if (_log.isInfoEnabled()) {
			_log.info("Installed " + path + " to " + urlClassLoader);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JarUtil.class);

	private static final Method _addURLMethod;

	static {
		try {
			_addURLMethod = ReflectionUtil.getDeclaredMethod(
				URLClassLoader.class, "addURL", URL.class);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}