/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.io.File;

import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Shuyang Zhou
 */
public class ClassPathUtil {

	public static Set<URL> getClassPathURLs(ClassLoader classLoader) {
		Set<URL> urls = new LinkedHashSet<URL>();

		while (classLoader != null) {
			if (classLoader instanceof URLClassLoader) {
				URLClassLoader urlClassLoader = (URLClassLoader)classLoader;

				urls.addAll(Arrays.asList(urlClassLoader.getURLs()));
			}

			classLoader = classLoader.getParent();
		}

		return urls;
	}

	public static URL[] getClassPathURLs(String classPath)
		throws MalformedURLException {

		String[] paths = StringUtil.split(classPath, File.pathSeparatorChar);

		Set<URL> urls = new LinkedHashSet<URL>();

		for (String path : paths) {
			File file = new File(path);

			URI uri = file.toURI();

			urls.add(uri.toURL());
		}

		return urls.toArray(new URL[urls.size()]);
	}

	public static String getGlobalClassPath() {
		return _globalClassPath;
	}

	public static String getJVMClassPath(boolean includeBootClassPath) {
		String jvmClassPath = System.getProperty("java.class.path");

		if (includeBootClassPath) {
			String bootClassPath = System.getProperty("sun.boot.class.path");

			jvmClassPath = jvmClassPath.concat(File.pathSeparator).concat(
				bootClassPath);
		}

		return jvmClassPath;
	}

	public static String getPortalClassPath() {
		return _portalClassPath;
	}

	public static void initializeClassPaths(ServletContext servletContext) {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (classLoader == null) {
			_log.error("Portal ClassLoader is null");

			return;
		}

		StringBundler sb = new StringBundler(8);

		String appServerGlobalClassPath = _buildClassPath(
			classLoader, ServletException.class.getName());

		sb.append(appServerGlobalClassPath);
		sb.append(File.pathSeparator);

		String portalGlobalClassPath = _buildClassPath(
			classLoader, PortalException.class.getName());

		sb.append(portalGlobalClassPath);

		_globalClassPath = sb.toString();

		sb.append(File.pathSeparator);
		sb.append(
			_buildClassPath(
				classLoader, "com.liferay.portal.servlet.MainServlet"));
		sb.append(File.pathSeparator);
		sb.append(servletContext.getRealPath(""));
		sb.append("/WEB-INF/classes");

		_portalClassPath = sb.toString();
	}

	private static String _buildClassPath(
		ClassLoader classloader, String className) {

		String pathOfClass = StringUtil.replace(
			className, CharPool.PERIOD, CharPool.SLASH);

		pathOfClass = pathOfClass.concat(".class");

		URL url = classloader.getResource(pathOfClass);

		if (_log.isDebugEnabled()) {
			_log.debug("Build class path from " + url);
		}

		String protocol = url.getProtocol();

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			try {
				URLConnection urlConnection = url.openConnection();

				Class<?> clazz = urlConnection.getClass();

				Method getLocalURLMethod = clazz.getDeclaredMethod(
					"getLocalURL");

				getLocalURLMethod.setAccessible(true);

				url = (URL)getLocalURLMethod.invoke(urlConnection);
			}
			catch (Exception e) {
				_log.error("Unable to resolve local URL from bundle", e);

				return StringPool.BLANK;
			}
		}

		String path = URLCodec.decodeURL(url.getPath());

		if (_log.isDebugEnabled()) {
			_log.debug("Path " + path);
		}

		path = StringUtil.replace(path, CharPool.BACK_SLASH, CharPool.SLASH);

		if (_log.isDebugEnabled()) {
			_log.debug("Decoded path " + path);
		}

		if (ServerDetector.isWebLogic() && protocol.equals("zip")) {
			path = "file:".concat(path);
		}

		if (ServerDetector.isJBoss() &&
			(protocol.equals("vfs") || protocol.equals("vfsfile"))) {

			int pos = path.indexOf(".jar/");

			if (pos != -1) {
				String jarFilePath = path.substring(0, pos + 4);

				File jarFile = new File(jarFilePath);

				if (jarFile.isFile()) {
					path = jarFilePath + '!' + path.substring(pos + 4);
				}
			}

			path = "file:".concat(path);
		}

		File dir = null;

		int pos = -1;

		if (!path.startsWith("file:") ||
			((pos = path.indexOf(CharPool.EXCLAMATION)) == -1)) {

			if (!path.endsWith(pathOfClass)) {
				_log.error(
					"Class " + className + " is not loaded from a JAR file");

				return StringPool.BLANK;
			}

			String classesDirName = path.substring(
				0, path.length() - pathOfClass.length());

			if (!classesDirName.endsWith("/WEB-INF/classes/")) {
				_log.error(
					"Class " + className + " is not loaded from a standard " +
						"location (/WEB-INF/classes)");

				return StringPool.BLANK;
			}

			String libDirName = classesDirName.substring(
				0, classesDirName.length() - "classes/".length());

			libDirName += "/lib";

			dir = new File(libDirName);
		}
		else {
			pos = path.lastIndexOf(CharPool.SLASH, pos);

			dir = new File(path.substring("file:".length(), pos));
		}

		if (!dir.isDirectory()) {
			_log.error(dir.toString() + " is not a directory");

			return StringPool.BLANK;
		}

		File[] files = dir.listFiles();

		Arrays.sort(files);

		StringBundler sb = new StringBundler(files.length * 2);

		for (File file : files) {
			sb.append(file.getAbsolutePath());
			sb.append(File.pathSeparator);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(ClassPathUtil.class);

	private static String _globalClassPath;
	private static String _portalClassPath;

}