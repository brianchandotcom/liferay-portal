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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.io.File;
import java.io.FileFilter;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Shuyang Zhou
 */
public class PortalClassPathUtil {

	public static ProcessConfig createProcessConfig(Class<?>... classes) {
		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		builder.setArguments(
			Arrays.asList(
				"-Dconfiguration.impl.quiet=true", "-Djava.awt.headless=true",
				"-Dserver.detector.quiet=true",
				"-Dsystem.properties.quiet=true"));

		String classpath = _buildClassPath(classes);

		classpath = StringBundler.concat(
			classpath, File.pathSeparator,
			_portalProcessConfig.getBootstrapClassPath());

		builder.setBootstrapClassPath(classpath);

		builder.setProcessLogConsumer(
			processLog -> {
				if (ProcessLog.Level.DEBUG == processLog.getLevel()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else if (ProcessLog.Level.INFO == processLog.getLevel()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else if (ProcessLog.Level.WARN == processLog.getLevel()) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else {
					_log.error(
						processLog.getMessage(), processLog.getThrowable());
				}
			});
		builder.setReactClassLoader(PortalClassLoaderUtil.getClassLoader());
		builder.setRuntimeClassPath(classpath);

		return builder.build();
	}

	public static ProcessConfig getPortalProcessConfig() {
		return _portalProcessConfig;
	}

	public static void initializeClassPaths(ServletContext servletContext) {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (classLoader == null) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();
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

		String globalClassPath = sb.toString();

		sb.append(File.pathSeparator);
		sb.append(
			_buildClassPath(
				classLoader,
				"com.liferay.portal.internal.servlet.MainServlet"));

		if (servletContext != null) {
			sb.append(File.pathSeparator);
			sb.append(servletContext.getRealPath(""));
			sb.append("/WEB-INF/classes");
		}

		String portalClassPath = sb.toString();

		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		builder.setArguments(
			Arrays.asList(
				"-Dconfiguration.impl.quiet=true", "-Djava.awt.headless=true",
				"-Dserver.detector.quiet=true",
				"-Dsystem.properties.quiet=true"));
		builder.setBootstrapClassPath(globalClassPath);
		builder.setReactClassLoader(classLoader);
		builder.setRuntimeClassPath(portalClassPath);

		_portalProcessConfig = builder.build();
	}

	private static String _buildClassPath(Class<?>... classes) {
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

	private static String _buildClassPath(
		ClassLoader classLoader, String... classNames) {

		Set<File> fileSet = new HashSet<>();

		for (String className : classNames) {
			File[] files = _listClassPathFiles(classLoader, className);

			if (files != null) {
				Collections.addAll(fileSet, files);
			}
		}

		File[] files = fileSet.toArray(new File[0]);

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
		ClassLoader classLoader, String className) {

		String pathOfClass = StringUtil.replace(
			className, CharPool.PERIOD, CharPool.SLASH);

		pathOfClass = pathOfClass.concat(".class");

		URL url = classLoader.getResource(pathOfClass);

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
			catch (Exception exception) {
				_log.error(
					"Unable to resolve local URL from bundle", exception);

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

	private static final Log _log = LogFactoryUtil.getLog(
		PortalClassPathUtil.class);

	private static ProcessConfig _portalProcessConfig;

}