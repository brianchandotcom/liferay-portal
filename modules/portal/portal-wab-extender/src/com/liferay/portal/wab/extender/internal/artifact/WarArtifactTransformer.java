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

package com.liferay.portal.wab.extender.internal.artifact;

import java.io.File;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class WarArtifactTransformer implements ArtifactUrlTransformer {

	@Override
	public boolean canHandle(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(_WAR_EXTENSION)) {
			return true;
		}

		return false;
	}

	@Override
	public URL transform(URL artifact) throws Exception {
		String path = artifact.getPath();

		int x = path.lastIndexOf("/");
		int y = path.lastIndexOf(_WAR_EXTENSION);

		String contextName = path.substring(x + 1, y);

		Matcher matcher = _CONTEXT_NAME_PATTERN.matcher(contextName);

		if (matcher.matches()) {
			contextName = matcher.group(1);
		}

		String pathWithQueryString =
			artifact.getPath() + "?Web-ContextPath=/" + contextName;

		URL newURL = new URL("file", null, pathWithQueryString);

		newURL = new URL("webbundle", null, newURL.toString());

		return newURL;
	}

	private static final Pattern _CONTEXT_NAME_PATTERN = Pattern.compile(
		"(.*?)-\\d+\\.\\d+\\.\\d+\\.\\d+");

	private static final String _WAR_EXTENSION = ".war";

}