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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.XugglerInstallException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.DummyOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xuggler.Xuggler;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.xuggler.XugglerImpl;

import com.xuggle.ferry.JNILibrary;

import java.io.File;
import java.io.PrintStream;

import java.lang.reflect.Field;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Shuyang Zhou
 */
public class XugglerAutoInstallHelper {

	public static void installNativeLibraries() throws ProcessException {
		if (isNativeLibraryInstalled()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Xuggler is already installed");
			}

			return;
		}

		String xugglerJarFile = getXugglerJarFileName();

		if (xugglerJarFile == null) {
			_log.error(
				"Xuggler auto install is not supported on system: " +
					System.getProperty("os.name") + "/" +
						System.getProperty("os.arch"));

			return;
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(
			ClassLoader.getSystemClassLoader());

		try {
			Xuggler xuggler = new XugglerImpl();

			try {
				xuggler.installNativeLibraries(xugglerJarFile);
			}
			catch (XugglerInstallException.MuseBeURLClassLoader xie) {
				if (_log.isDebugEnabled()) {
					_log.debug(xie, xie);
				}
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			if (xuggler.isNativeLibraryInstalled()) {
				if (_log.isInfoEnabled()) {
					_log.info("Xuggler installed successfully");
				}
			}
			else {
				_log.error("Xuggler auto install failed");
			}
		}
		finally {
			PortalClassLoaderUtil.setClassLoader(classLoader);
		}
	}

	public static class IsNativeLibraryInstalledCallable
		implements Callable<Boolean> {

		@Override
		public Boolean call() throws Exception {
			PrintStream printStream = System.out;

			System.setOut(new PrintStream(new DummyOutputStream()));

			Package pkg = JNILibrary.class.getPackage();

			Logger logger = Logger.getLogger(pkg.getName());

			Level level = logger.getLevel();

			logger.setLevel(Level.OFF);

			String property = System.getProperty(
				"java.util.logging.config.file");

			System.setProperty("java.util.logging.config.file", "configFile");

			Xuggler xuggler = new XugglerImpl();

			Field informAdministratorField = ReflectionUtil.getDeclaredField(
				XugglerImpl.class, "_informAdministrator");

			informAdministratorField.setBoolean(xuggler, false);

			try {
				return xuggler.isNativeLibraryInstalled();
			}
			finally {
				if (property == null) {
					System.clearProperty("java.util.logging.config.file");
				}
				else {
					System.setProperty(
						"java.util.logging.config.file", property);
				}

				logger.setLevel(level);

				System.setOut(printStream);
			}
		}

	}

	private static String getXugglerJarFileName() {
		String bitmode = OSDetector.getBitmode();

		if (Validator.isNull(bitmode) ||
			(!bitmode.equals("32") && !bitmode.equals("64"))) {

			return null;
		}

		if (OSDetector.isApple()) {
			return PropsUtil.get(
				PropsKeys.XUGGLER_JAR_FILE, new Filter(bitmode + "-mac"));
		}

		if (OSDetector.isLinux()) {
			return PropsUtil.get(
				PropsKeys.XUGGLER_JAR_FILE, new Filter(bitmode + "-linux"));
		}

		if (OSDetector.isWindows()) {
			return PropsUtil.get(
				PropsKeys.XUGGLER_JAR_FILE, new Filter(bitmode + "-win"));
		}

		return null;
	}

	private static boolean isNativeLibraryInstalled() {
		Properties properties = PropsUtil.getProperties(
			PropsKeys.XUGGLER_JAR_FILE, false);

		Set<Object> jarFiles = SetUtil.fromCollection(properties.values());

		jarFiles.remove(getXugglerJarFileName());

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Set<URL> urls = ClassPathUtil.getClassPathURLs(contextClassLoader);

		Iterator<URL> iterator = urls.iterator();

		while (iterator.hasNext()) {
			URL url = iterator.next();

			String protocol = url.getProtocol();

			if (protocol.equals("file")) {
				File file = new File(url.getPath());

				Matcher matcher = _pattern.matcher(file.getName());

				if (matcher.matches()) {
					if (jarFiles.contains(matcher.replaceAll("$1$2"))) {
						file.delete();

						iterator.remove();
					}
				}
			}
		}

		URLClassLoader urlClassLoader = new URLClassLoader(
			urls.toArray(new URL[urls.size()]), null);

		currentThread.setContextClassLoader(urlClassLoader);

		try {
			Class<Callable<Boolean>> clazz = (Class<Callable<Boolean>>)
				urlClassLoader.loadClass(
					IsNativeLibraryInstalledCallable.class.getName());

			Callable<Boolean> callable = clazz.newInstance();

			return callable.call();
		}
		catch (Exception e) {
			return false;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		XugglerAutoInstallHelper.class);

	private static final Pattern _pattern = Pattern.compile(
		"(.*)-\\d+-\\d+(\\.jar)");

}