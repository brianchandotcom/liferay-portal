/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.release.util.internal.util.HttpUtil;
import com.liferay.release.util.internal.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Drew Brokke
 */
public class ResourceUtil {

	public static Resolver getClassLoaderResolver(
		Class<?> clazz, String resourcePath) {

		return () -> {
			_logInfo(
				"Trying to get resource from class path: {}", resourcePath);

			return Objects.requireNonNull(
				clazz.getResourceAsStream(resourcePath),
				"Unable to get resource from class path: " + resourcePath);
		};
	}

	public static Resolver getClassLoaderResolver(String resourcePath) {
		return getClassLoaderResolver(ResourceUtil.class, resourcePath);
	}

	public static Resolver getLocalFileResolver(File file) {
		return () -> {
			_logInfo(
				"Trying to get resource from local file: {}",
				file.getAbsolutePath());

			_checkFileExists(file);

			return Files.newInputStream(file.toPath());
		};
	}

	public static Resolver getLocalFileResolver(
		File file, long maxAge, TemporalUnit temporalUnit) {

		return () -> {
			_logInfo(
				"Trying to get resource from local file with max age of {} " +
					"{}: {}",
				maxAge, temporalUnit, file.getAbsolutePath());

			_checkFileExists(file);

			BasicFileAttributes basicFileAttributes = Files.readAttributes(
				file.toPath(), BasicFileAttributes.class);

			FileTime fileTime = basicFileAttributes.lastModifiedTime();

			Duration age = Duration.between(
				fileTime.toInstant(), Instant.now());

			if (age.compareTo(Duration.of(maxAge, temporalUnit)) > 0) {
				throw new Exception(
					String.format(
						"Cached file %s is older than max age of %s %s", file,
						maxAge, temporalUnit));
			}

			return Files.newInputStream(file.toPath());
		};
	}

	public static Resolver getURIResolver(
		File cacheDir, int connectionTimeout, URI uri) {

		return () -> {
			_logInfo("Trying to get resource from URL {}", uri);

			URL url = uri.toURL();

			try {
				if (Objects.equals(uri.getScheme(), "file")) {
					return Files.newInputStream(Paths.get(uri));
				}

				Path downloadPath = HttpUtil.downloadFile(
					uri, null, null, cacheDir.toPath(), connectionTimeout);

				Files.setLastModifiedTime(
					downloadPath, FileTime.from(Instant.now()));

				return Files.newInputStream(downloadPath);
			}
			catch (Exception exception) {
				throw new Exception(
					String.format(
						"Unable to get resource from URL %s: %s", url,
						exception.getMessage()),
					exception);
			}
		};
	}

	public static Resolver getURIResolver(File cacheDir, URI uri) {
		return getURIResolver(cacheDir, 5 * 1000, uri);
	}

	public static Resolver getURLResolver(File cacheDir, String url) {
		return getURIResolver(cacheDir, URI.create(url));
	}

	public static <T> T readJSON(Class<T> clazz, Resolver... resolvers) {
		return _withInputStream(
			inputStream -> _objectMapper.readValue(inputStream, clazz),
			resolvers);
	}

	public static Properties readProperties(Resolver... resolvers) {
		return _withInputStream(
			inputStream -> {
				Properties properties = new Properties();

				properties.load(inputStream);

				return properties;
			},
			resolvers);
	}

	public static String readString(Resolver... resolvers) {
		return _withInputStream(StringUtil::read, resolvers);
	}

	@FunctionalInterface
	public interface Resolver {

		public InputStream resolve() throws Exception;

	}

	@FunctionalInterface
	public interface Transformer<T> {

		public T transform(InputStream inputStream) throws Exception;

	}

	private static void _checkFileExists(File file) throws Exception {
		if (!file.exists()) {
			throw new FileNotFoundException(
				"Unable to get resource from local file: " +
					file.getAbsolutePath());
		}
	}

	private static void _logInfo(String message, Object... args) {
		if ((_logger != null) && _logger.isInfoEnabled()) {
			_logger.info(message, args);
		}
	}

	private static <T> T _withInputStream(
		Transformer<T> transformer, Resolver... resolvers) {

		InputStream inputStream1 = null;

		for (Resolver resolver : resolvers) {
			try {
				inputStream1 = resolver.resolve();
			}
			catch (Exception exception) {
				_logInfo(exception.getMessage());
			}

			if (inputStream1 != null) {
				break;
			}
		}

		if (inputStream1 == null) {
			return null;
		}

		try (InputStream inputStream2 = inputStream1) {
			_logInfo("Found resource\n");

			return transformer.transform(inputStream2);
		}
		catch (Exception exception) {
			throw new RuntimeException("Unable to read resource", exception);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		ResourceUtil.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}