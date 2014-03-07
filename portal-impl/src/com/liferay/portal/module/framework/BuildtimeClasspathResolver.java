/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Raymond Augé
 */
public class BuildtimeClasspathResolver implements ClasspathResolver {

	@Override
	public URL[] getClasspathURLs() throws Exception {
		URL url = this.getClass().getResource("/jars.txt");

		URLConnection urlConnection = url.openConnection();

		File jarsTxtFile = new File(url.toURI());

		File projectRootDir = jarsTxtFile.getParentFile().getParentFile();

		URI projectRootURI = projectRootDir.toURI();

		String[] jarFiles = StringUtil.split(
			StringUtil.read(urlConnection.getInputStream()));

		URL[] urls = new URL[jarFiles.length];

		for (int i = 0; i < urls.length; i++) {
			URI resolvedURI = projectRootURI.resolve(jarFiles[i]);

			urls[i] = resolvedURI.toURL();
		}

		return urls;
	}

}