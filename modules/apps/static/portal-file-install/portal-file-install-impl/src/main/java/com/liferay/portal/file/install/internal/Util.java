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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Collections;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.logging.Level;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

	public static void jarDir(File directory, File zipName) throws IOException {
		try (OutputStream outputStream1 = new FileOutputStream(zipName);
			OutputStream outputStream2 = new BufferedOutputStream(
				outputStream1)) {

			jarDir(directory, outputStream2);
		}
	}

	public static void jarDir(File directory, OutputStream outputStream)
		throws IOException {

		try (JarOutputStream jarOutputStream = new JarOutputStream(
				outputStream)) {

			jarOutputStream.setLevel(Deflater.NO_COMPRESSION);

			String path = StringPool.BLANK;

			File manifest = new File(directory, JarFile.MANIFEST_NAME);

			if (manifest.exists()) {
				byte[] readBuffer = new byte[8192];

				try (FileInputStream fileInputStream = new FileInputStream(
						manifest)) {

					ZipEntry zipEntry = new ZipEntry(JarFile.MANIFEST_NAME);

					jarOutputStream.putNextEntry(zipEntry);

					int bytesIn = fileInputStream.read(readBuffer);

					while (bytesIn != -1) {
						jarOutputStream.write(readBuffer, 0, bytesIn);

						bytesIn = fileInputStream.read(readBuffer);
					}
				}

				jarOutputStream.closeEntry();
			}

			zipDir(
				directory, jarOutputStream, path,
				Collections.singleton(JarFile.MANIFEST_NAME));
		}
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

	public static void zipDir(
			File directory, ZipOutputStream zipOutputStream, String path,
			Set<String> exclusions)
		throws IOException {

		File[] files = directory.listFiles();
		byte[] readBuffer = new byte[8192];
		int bytesIn;

		for (File file : files) {
			String name = file.getName();

			if (file.isDirectory()) {
				String prefix = StringBundler.concat(
					path, name, StringPool.SLASH);

				zipOutputStream.putNextEntry(new ZipEntry(prefix));

				zipDir(file, zipOutputStream, prefix, exclusions);

				continue;
			}

			String entryName = path.concat(name);

			if (!exclusions.contains(entryName)) {
				try (FileInputStream fileInputStream = new FileInputStream(
						file)) {

					ZipEntry zipEntry = new ZipEntry(entryName);

					zipOutputStream.putNextEntry(zipEntry);

					bytesIn = fileInputStream.read(readBuffer);

					while (bytesIn != -1) {
						zipOutputStream.write(readBuffer, 0, bytesIn);

						bytesIn = fileInputStream.read(readBuffer);
					}
				}
			}
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