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

package com.liferay.site.initializer.extender.internal.lxc;

import com.liferay.petra.string.StringPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class LXCServletContextDelegate {

	public LXCServletContextDelegate(
		File siteInitializerFolder, String servletContextName) {

		_siteInitializerFolder = siteInitializerFolder;
		_servletContextName = servletContextName;
	}

	public String getContextPath() {
		return StringPool.BLANK;
	}

	public URL getResource(String path) throws MalformedURLException {
		File resourceFile = new File(
			_siteInitializerFolder, PathUtil.removePrefix(path));

		if (resourceFile.exists()) {
			URI uri = resourceFile.toURI();

			return uri.toURL();
		}

		return null;
	}

	public InputStream getResourceAsStream(String path) {
		File resourceFile = new File(
			_siteInitializerFolder, PathUtil.removePrefix(path));

		if (resourceFile.exists()) {
			try {
				return new FileInputStream(resourceFile);
			}
			catch (FileNotFoundException fileNotFoundException) {
				throw new IllegalStateException(fileNotFoundException);
			}
		}

		return null;
	}

	public Set<String> getResourcePaths(String path) {
		File searchDir = new File(
			_siteInitializerFolder, PathUtil.removePrefix(path));

		if (!searchDir.exists()) {
			return Collections.emptySet();
		}

		if (!path.endsWith("/")) {
			path = path.concat("/");
		}

		Set<String> resourcePaths = new HashSet<>();

		for (File file : searchDir.listFiles()) {
			String resourcePath = path.concat(file.getName());

			if (file.isDirectory()) {
				resourcePath = resourcePath.concat("/");
			}

			resourcePaths.add(resourcePath);
		}

		return resourcePaths;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	private final String _servletContextName;
	private final File _siteInitializerFolder;

}