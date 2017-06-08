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

package com.liferay.deployment.helper.util;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 * @author Raymond Augé
 * @author David Truong
 */
public class DeploymentHelperUtil {

	public static String getSymbolicName(String path) {
		String symbolicName = "";

		try {
			int x = path.lastIndexOf('/');
			int y = path.lastIndexOf(".");

			symbolicName = path.substring(x + 1, y);

			Matcher matcher = _pattern.matcher(symbolicName);

			if (matcher.matches()) {
				symbolicName = matcher.group(1);
			}
		}
		catch (Exception e) {
		}

		return symbolicName;
	}

	public static URL getWebBundleURL(URL artifact) throws IOException {
		String path = artifact.getPath();

		String symbolicName = getSymbolicName(path);

		String contextName = null;

		try {
			contextName = _readServletContextName(artifact);
		}
		catch (Exception e) {
		}

		if (contextName == null) {
			contextName = symbolicName;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(artifact.getPath());
		sb.append("?");
		sb.append("Bundle-SymbolicName");
		sb.append("=");
		sb.append(symbolicName);
		sb.append("&Web-ContextPath=/");
		sb.append(contextName);

		URL url = new URL("file", null, sb.toString());

		url = new URL("webbundle", null, url.toString());

		return url;
	}

	private static String _readServletContextName(URL url) throws Exception {
		URI uri = url.toURI();

		Map<String, String> zipProperties = new HashMap<>();

		zipProperties.put("create", "false");
		zipProperties.put("encoding", StandardCharsets.UTF_8.name());

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				new URI("jar:" + uri.getScheme(), uri.getPath(), null),
				zipProperties)) {

			Path path = fileSystem.getPath(
				"WEB-INF", "liferay-plugin-package.properties");

			Properties properties = new Properties();

			try (InputStream inputStream = Files.newInputStream(path)) {
				properties.load(inputStream);
			}

			return properties.getProperty("servlet-context-name");
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+\\.\\d+)?");

}