/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.tools.bundle.support.commands.DownloadCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

import java.util.Objects;
import java.util.Properties;

import org.gradle.api.GradleException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

/**
 * @author Drew Brokke
 */
public class ResourceUtil {

	public static Resolver getClassLoaderResolver(String resourcePath) {
		return () -> {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Trying to get resource from class path: {}", resourcePath);
			}

			return Objects.requireNonNull(
				ResourceUtil.class.getResourceAsStream(resourcePath),
				"Unable to get resource from class path: " + resourcePath);
		};
	}

	public static Resolver getLocalFileResolver(File file) {
		return () -> {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Trying to get resource from local file: {}",
					file.getAbsolutePath());
			}

			_checkFileExists(file);

			return Files.newInputStream(file.toPath());
		};
	}

	public static Resolver getLocalFileResolver(
		File file, long maxAge, TemporalUnit temporalUnit) {

		return () -> {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Trying to get resource from local file with max age of " +
						"{} {}: {}",
					maxAge, temporalUnit, file.getAbsolutePath());
			}

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

	public static Resolver getURIResolver(File cacheDir, URI uri) {
		return () -> {
			if (_logger.isInfoEnabled()) {
				_logger.info("Trying to get resource from URL {}", uri);
			}

			URL url = uri.toURL();

			try {
				DownloadCommand downloadCommand = new DownloadCommand();

				downloadCommand.setCacheDir(cacheDir);
				downloadCommand.setConnectionTimeout(5 * 1000);
				downloadCommand.setPassword(null);
				downloadCommand.setQuiet(true);
				downloadCommand.setToken(false);
				downloadCommand.setUrl(url);
				downloadCommand.setUserName(null);

				downloadCommand.execute();

				Path downloadPath = downloadCommand.getDownloadPath();

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

	public static Resolver getURLResolver(File cacheDir, String url) {
		return getURIResolver(cacheDir, URI.create(url));
	}

	public static <T> T readJson(Class<T> clazz, Resolver... resolvers) {
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

	private static <T> T _withInputStream(
		Transformer<T> transformer, Resolver... resolvers) {

		for (Resolver resolver : resolvers) {
			try (InputStream inputStream = resolver.resolve()) {
				if (inputStream != null) {
					if (_logger.isInfoEnabled()) {
						_logger.info("Found resource");
					}

					return transformer.transform(inputStream);
				}
			}
			catch (Exception exception) {
				_logger.lifecycle(exception.getMessage());
			}
		}

		throw new GradleException("Unable to get resource");
	}

	private static final Logger _logger = Logging.getLogger(ResourceUtil.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}