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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
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
 * @author Gregory Amerson
 */
public class ArtifactURLUtil {

	public static URL transform(URL artifact) throws IOException {
		String path = artifact.getPath();

		int x = path.lastIndexOf('/');
		int y = path.lastIndexOf(CharPool.PERIOD);

		String symbolicName = path.substring(x + 1, y);
		String fileExtension = path.substring(y + 1);

		Matcher matcher = _pattern.matcher(symbolicName);

		if (matcher.matches()) {
			symbolicName = matcher.group(1);
		}

		String contextName = null;

		if (fileExtension.equals("war")) {
			try (Jar jar = new Jar("WAR", artifact.openStream())) {
				if (jar.getBsn() != null) {
					return artifact;
				}

				contextName = _readServletContextName(jar);
			}
			catch (Exception exception) {
				ReflectionUtil.throwException(exception);
			}
		}

		if (contextName == null) {
			contextName = symbolicName;
		}

		return new URL(
			"webbundle", null,
			StringBundler.concat(
				artifact.getPath(), "?", Constants.BUNDLE_SYMBOLICNAME, "=",
				symbolicName, "&Web-ContextPath=/", contextName,
				"&protocol=file&fileExtension=", fileExtension));
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