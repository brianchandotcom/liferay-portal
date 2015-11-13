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

package com.liferay.portal.wab.generator.internal.artifact;

import java.io.File;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

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

		int x = path.lastIndexOf("/");
		int y = path.lastIndexOf(".war");

		String contextName = path.substring(x + 1, y);

		Matcher matcher = _pattern.matcher(contextName);

		if (matcher.matches()) {
			contextName = matcher.group(1);
		}

		String pathWithQueryString =
			artifact.getPath() + "?Web-ContextPath=/" + contextName;

		URL url = new URL("file", null, pathWithQueryString);

		url = new URL("webbundle", null, url.toString());

		return url;
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+\\.\\d+)?");

}