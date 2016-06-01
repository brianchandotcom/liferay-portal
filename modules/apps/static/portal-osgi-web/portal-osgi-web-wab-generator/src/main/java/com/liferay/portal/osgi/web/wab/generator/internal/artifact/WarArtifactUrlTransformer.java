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

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.osgi.web.wab.generator.internal.util.ManifestUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

import org.osgi.framework.Constants;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class WarArtifactUrlTransformer implements ArtifactUrlTransformer {

	@Override
	public boolean canHandle(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".war")) {
			return true;
		}

		return false;
	}

	@Override
	public URL transform(URL artifact) throws Exception {
		String path = artifact.getPath();

		int x = path.lastIndexOf('/');
		int y = path.lastIndexOf(".war");

		String symbolicName = path.substring(x + 1, y);

		Matcher matcher = _pattern.matcher(symbolicName);

		if (matcher.matches()) {
			symbolicName = matcher.group(1);
		}

		String contextName = null;

		if ("file".equals(artifact.getProtocol())) {
			if (ManifestUtil.isValidOSGiBundle(artifact.getPath())) {
				return artifact;
			}

			contextName = _readServletContextName(
				new File(URLCodec.decodeURL(artifact.getPath())));
		}
		else {
			Path tempFilePath = Files.createTempFile(null, null);

			try (InputStream inputStream = artifact.openStream()) {
				Files.copy(inputStream, tempFilePath);

				contextName = _readServletContextName(tempFilePath.toFile());
			}
			finally {
				Files.delete(tempFilePath);
			}
		}

		if (contextName == null) {
			contextName = symbolicName;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(artifact.getPath());
		sb.append("?");
		sb.append(Constants.BUNDLE_SYMBOLICNAME);
		sb.append("=");
		sb.append(symbolicName);
		sb.append("&Web-ContextPath=/");
		sb.append(contextName);

		URL url = new URL("file", null, sb.toString());

		url = new URL("webbundle", null, url.toString());

		return url;
	}

	private String _readServletContextName(File file) throws IOException {
		try (ZipFile zipFile = new ZipFile(file);
			InputStream inputStream = zipFile.getInputStream(
				new ZipEntry("WEB-INF/liferay-plugin-package.properties"))) {

			Properties properties = new Properties();

			properties.load(inputStream);

			return properties.getProperty("servlet-context-name");
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+\\.\\d+)?");

}