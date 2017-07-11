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

import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;

import com.liferay.whip.util.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Constants;

/**
 * @author Matthew Tambara
 * @author Raymond Augé
 */
public class ArtifactURLUtil {

	public static URL transform(URL artifact) throws IOException {
		String path = artifact.getPath();

		int x = path.lastIndexOf('/');
		int y = path.lastIndexOf(".war");

		String symbolicName = path.substring(x + 1, y);

		Matcher matcher = _pattern.matcher(symbolicName);

		if (matcher.matches()) {
			symbolicName = matcher.group(1);
		}

		String contextName = null;

		try (Jar jar = new Jar("WAR", artifact.openStream())) {
			if (jar.getBsn() != null) {
				return artifact;
			}

			contextName = _readServletContextName(jar);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		if (contextName == null) {
			contextName = symbolicName;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(artifact.getPath());
		sb.append("?");
		sb.append(Constants.BUNDLE_SYMBOLICNAME);
		sb.append("=");
		sb.append(symbolicName);
		sb.append("&Web-ContextPath=/");
		sb.append(contextName);
		sb.append("&protocol=file");

		return new URL("webbundle", null, sb.toString());
	}

	private static String _readServletContextName(Jar jar) throws Exception {
		Resource resource = jar.getResource(
			"WEB-INF/liferay-plugin-package.properties");

		if (resource == null) {
			return null;
		}

		Properties properties = new Properties();

		try (InputStream inputStream = resource.openInputStream()) {
			properties.load(inputStream);
		}

		return properties.getProperty("servlet-context-name");
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+\\.\\d+)?");

}