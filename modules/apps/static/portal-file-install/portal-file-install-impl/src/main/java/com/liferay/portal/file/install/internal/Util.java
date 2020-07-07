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

package com.liferay.portal.file.install.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.logging.Level;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

/**
 * @author Matthew Tambara
 */
public class Util {

	public static int getGlobalLogLevel(BundleContext bundleContext) {
		String string = bundleContext.getProperty(DirectoryWatcher.LOG_LEVEL);

		if (string == null) {
			String level = DirectoryWatcher.LOG_LEVEL;

			level = StringUtil.toUpperCase(level);

			level = StringUtil.replace(
				level, CharPool.PERIOD, CharPool.UNDERLINE);

			string = System.getProperty(level);
		}

		if (string == null) {
			string = "1";
		}

		int logLevel = Logger.LOG_ERROR;

		try {
			logLevel = Integer.parseInt(string);
		}
		catch (NumberFormatException numberFormatException) {
		}

		return logLevel;
	}

	public static long loadChecksum(
		Bundle bundle, BundleContext bundleContext) {

		String key = _getBundleKey(bundle);

		File file = bundleContext.getDataFile(key.concat(_CHECKSUM_SUFFIX));

		if (!file.exists()) {
			return Long.MIN_VALUE;
		}

		try (InputStream inputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(
				inputStream)) {

			return dataInputStream.readLong();
		}
		catch (Exception exception) {
			return Long.MIN_VALUE;
		}
	}

	public static void log(
		BundleContext bundleContext, int logLevel, int msgLevel, String message,
		Throwable t) {

		Logger logger = _getLogger(bundleContext);

		logger.log(logLevel, msgLevel, message, t);
	}

	public static void log(
		BundleContext bundleContext, int msgLevel, String message,
		Throwable t) {

		Logger logger = _getLogger(bundleContext);

		logger.log(getGlobalLogLevel(bundleContext), msgLevel, message, t);
	}

	public static void storeChecksum(
		Bundle bundle, long checksum, BundleContext bundleContext) {

		String key = _getBundleKey(bundle);

		File file = bundleContext.getDataFile(key.concat(_CHECKSUM_SUFFIX));

		try (OutputStream outputStream = new FileOutputStream(file);
			DataOutputStream dataOutputStream = new DataOutputStream(
				outputStream)) {

			dataOutputStream.writeLong(checksum);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public interface Logger {

		public static final int LOG_DEBUG = 4;

		public static final int LOG_ERROR = 1;

		public static final int LOG_INFO = 3;

		public static final int LOG_WARNING = 2;

		public boolean isValidLogger(BundleContext bundleContext);

		public void log(
			int logLevel, int msgLevel, String message, Throwable throwable);

	}

	private static String _getBundleKey(Bundle bundle) {
		return String.valueOf(bundle.getBundleId());
	}

	private static Logger _getLogger(BundleContext bundleContext) {
		try {
			bundleContext.getBundle();

			if ((_logger != null) && _logger.isValidLogger(bundleContext)) {
				return _logger;
			}

			_logger = new OsgiLogger(bundleContext);
		}
		catch (Throwable t) {
			_logger = new DefaultLogger(bundleContext);
		}

		return _logger;
	}

	private static final String _CHECKSUM_SUFFIX = ".checksum";

	private static Logger _logger;

	private static class DefaultLogger implements Logger {

		public DefaultLogger(BundleContext bundleContext) {
			this.bundleContext = bundleContext;

			String string = bundleContext.getProperty(
				DirectoryWatcher.LOG_DEFAULT);

			if (string == null) {
				String logLevel = DirectoryWatcher.LOG_DEFAULT;

				logLevel = StringUtil.toUpperCase(logLevel);

				logLevel = StringUtil.replace(
					logLevel, CharPool.PERIOD, CharPool.UNDERLINE);

				string = System.getProperty(logLevel);
			}

			if (string == null) {
				string = DirectoryWatcher.LOG_STDOUT;
			}

			_logDefault = string;
		}

		@Override
		public boolean isValidLogger(BundleContext context) {
			return true;
		}

		@Override
		public void log(
			int logLevel, int messageLevel, String message,
			Throwable throwable) {

			if ((logLevel > 0) && (messageLevel <= logLevel)) {
				if (_logDefault.equals(DirectoryWatcher.LOG_JUL)) {
					Level level = Level.FINEST;

					if (messageLevel == 1) {
						level = Level.SEVERE;
					}
					else if (messageLevel == 2) {
						level = Level.WARNING;
					}
					else if (messageLevel == 3) {
						level = Level.INFO;
					}
					else if (messageLevel == 4) {
						level = Level.FINE;
					}

					java.util.logging.Logger logger =
						java.util.logging.Logger.getLogger("fileinstall");

					logger.log(level, message, throwable);
				}
				else {
					if (throwable == null) {
						System.out.println(message + "");
					}
					else {
						System.out.println(message + ": " + throwable);

						throwable.printStackTrace(System.out);
					}
				}
			}
		}

		protected BundleContext bundleContext;

		private final String _logDefault;

	}

	private static class OsgiLogger extends DefaultLogger {

		public OsgiLogger(BundleContext bundleContext) {
			super(bundleContext);

			try {
				Class<?> clazz = getClass();

				ClassLoader classLoader = clazz.getClassLoader();

				classLoader.loadClass(LogService.class.getName());
			}
			catch (ClassNotFoundException classNotFoundException) {
				throw new NoClassDefFoundError(
					classNotFoundException.getMessage());
			}
		}

		public boolean isValidLogger(BundleContext bundleContext) {
			if (this.bundleContext == bundleContext) {
				return true;
			}

			return false;
		}

		public void log(
			int logLevel, int messageLevel, String message,
			Throwable throwable) {

			if ((logLevel > 0) && (messageLevel <= logLevel)) {
				LogService logService = _getLogService();

				if (logService != null) {
					logService.log(messageLevel, message, throwable);
				}
				else {
					super.log(logLevel, messageLevel, message, throwable);
				}
			}
		}

		private LogService _getLogService() {
			ServiceReference<LogService> serviceReference =
				bundleContext.getServiceReference(LogService.class);

			if (serviceReference != null) {
				return bundleContext.getService(serviceReference);
			}

			return null;
		}

	}

}