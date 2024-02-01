/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import com.google.gson.Gson;

import com.liferay.portal.tools.bundle.support.commands.DownloadCommand;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.nio.file.Files;

import java.util.Objects;

import org.gradle.api.GradleException;

/**
 * @author Drew Brokke
 */
public class ResourceUtil {

	public static Resolver getClassLoaderResolver(String resourcePath) {
		return () -> Objects.requireNonNull(
			ResourceUtil.class.getResourceAsStream(resourcePath),
			"Could not get resource from classpath: " + resourcePath);
	}

	public static Resolver getURLResolver(File cacheDir, String url) {
		return () -> {
			try {
				DownloadCommand downloadCommand = new DownloadCommand();

				downloadCommand.setCacheDir(cacheDir);
				downloadCommand.setConnectionTimeout(5 * 1000);
				downloadCommand.setPassword(null);
				downloadCommand.setQuiet(true);
				downloadCommand.setToken(false);
				downloadCommand.setUrl(new URL(url));
				downloadCommand.setUserName(null);

				downloadCommand.execute();

				return Files.newInputStream(downloadCommand.getDownloadPath());
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

	public static <T> T readJson(Class<T> clazz, Resolver... resolvers) {
		for (Resolver resolver : resolvers) {
			try (InputStream inputStream = resolver.resolve()) {
				if (inputStream == null) {
					continue;
				}

				return _gson.fromJson(
					new InputStreamReader(inputStream), clazz);
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}

		throw new GradleException("Unable to get resource");
	}

	@FunctionalInterface
	public interface Resolver {

		public InputStream resolve() throws Exception;

	}

	private static final Gson _gson = new Gson();

}