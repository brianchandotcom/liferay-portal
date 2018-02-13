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

package com.liferay.portal.kernel.process;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.io.File;
import java.io.FileFilter;

import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Shuyang Zhou
 * @deprecated As of 7.0.0, replaced by {@link com.liferay.petra.process.
 *             ClassPathUtil} and {@link com.liferay.portal.util.
 *             PortalClassPathUtil}
 */
@Deprecated
@ProviderType
public class ClassPathUtil {

	public static String buildClassPath(Class<?>... classes) {
		if (ArrayUtil.isEmpty(classes)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(classes.length * 2);

		for (Class<?> clazz : classes) {
			sb.append(_buildClassPath(clazz.getClassLoader(), clazz.getName()));
			sb.append(File.pathSeparator);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<URL> getClassPathURLs(ClassLoader classLoader) {
		Set<URL> urls = new LinkedHashSet<>();

		while (classLoader != null) {
			if (classLoader instanceof URLClassLoader) {
				URLClassLoader urlClassLoader = (URLClassLoader)classLoader;

				Collections.addAll(urls, urlClassLoader.getURLs());
			}

			classLoader = classLoader.getParent();
		}

		return urls;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link com.liferay.petra.process.
	 *             ClassPathUtil#getClassPathURLs(String)}
	 */
	@Deprecated
	public static URL[] getClassPathURLs(String classPath)
		throws MalformedURLException {

		return com.liferay.petra.process.ClassPathUtil.getClassPathURLs(
			classPath);
	}

	public static String getGlobalClassPath() {
		return _globalClassPath;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link com.liferay.petra.process.
	 *             ClassPathUtil#getJVMClassPath(boolean)}
	 */
	@Deprecated
	public static String getJVMClassPath(boolean includeBootClassPath) {
		return com.liferay.petra.process.ClassPathUtil.getJVMClassPath(
			includeBootClassPath);
	}

	public static String getPortalClassPath() {
		return _portalClassPath;
	}

	public static ProcessConfig getPortalProcessConfig() {
		return _portalProcessConfig;
	}

	public static void initializeClassPaths(ServletContext servletContext) {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (classLoader == null) {
			classLoader = ClassLoaderUtil.getContextClassLoader();
		}

		StringBundler sb = new StringBundler(8);

		String appServerGlobalClassPath = _buildClassPath(
			classLoader, ServletException.class.getName());

		sb.append(appServerGlobalClassPath);

		sb.append(File.pathSeparator);

		String portalGlobalClassPath = _buildClassPath(
			classLoader, CentralizedThreadLocal.class.getName(),
			PortalException.class.getName());

		sb.append(portalGlobalClassPath);

		_globalClassPath = sb.toString();

		sb.append(File.pathSeparator);
		sb.append(
			_buildClassPath(
				classLoader, "com.liferay.portal.servlet.MainServlet"));

		if (servletContext != null) {
			sb.append(File.pathSeparator);
			sb.append(servletContext.getRealPath(""));
			sb.append("/WEB-INF/classes");
		}

		_portalClassPath = sb.toString();

		Builder builder = new Builder();

		builder.setArguments(Arrays.asList("-Djava.awt.headless=true"));
		builder.setBootstrapClassPath(_globalClassPath);
		builder.setReactClassLoader(classLoader);
		builder.setRuntimeClassPath(_portalClassPath);

		_portalProcessConfig = builder.build();
	}

	private static String _buildClassPath(
		ClassLoader classloader, String... classNames) {

		Set<File> fileSet = new HashSet<>();

		for (String className : classNames) {
			File[] files = _listClassPathFiles(classloader, className);

			if (files != null) {
				Collections.addAll(fileSet, files);
			}
		}

		File[] files = fileSet.toArray(new File[fileSet.size()]);

		Arrays.sort(files);

		StringBundler sb = new StringBundler(files.length * 2);

		for (File file : files) {
			sb.append(file.getAbsolutePath());
			sb.append(File.pathSeparator);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static File[] _listClassPathFiles(
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

				return null;
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

		if ((ServerDetector.isJBoss() || ServerDetector.isWildfly()) &&
			(protocol.equals("vfs") || protocol.equals("vfsfile") ||
			 protocol.equals("vfszip"))) {

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

				return null;
			}

			String classesDirName = path.substring(
				0, path.length() - pathOfClass.length());

			if (!classesDirName.endsWith("/WEB-INF/classes/")) {
				_log.error(
					StringBundler.concat(
						"Class ", className, " is not loaded from a standard ",
						"location (/WEB-INF/classes)"));

				return null;
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

			return null;
		}

		return dir.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return false;
					}

					String name = file.getName();

					if (name.equals("bundleFile") || name.endsWith(".jar")) {
						return true;
					}

					return false;
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(ClassPathUtil.class);

	private static String _globalClassPath;
	private static String _portalClassPath;
	private static ProcessConfig _portalProcessConfig;

}