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
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Shuyang Zhou
 */
public class JarUtil {

	public static void downloadAndInstallJar(
			URL url, String libPath, String name, URLClassLoader urlClassLoader)
		throws Exception {

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