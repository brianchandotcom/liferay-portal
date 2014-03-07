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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author Raymond Augé
 */
public class RuntimeClasspathResolver implements ClasspathResolver {

	@Override
	public URL[] getClasspathURLs() throws Exception {
		File coreDir = new File(
			PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR, "osgi");

		_initDir(
			"com/liferay/portal/deploy/dependencies/osgi/core",
			coreDir.getAbsolutePath());
		_initDir(
			"com/liferay/portal/deploy/dependencies/osgi/portal",
			PropsValues.MODULE_FRAMEWORK_PORTAL_DIR);

		File[] files = coreDir.listFiles();

		URL[] urls = new URL[files.length];

		for (int i = 0; i < urls.length; i++) {
			urls[i] = new URL("file", null, files[i].getAbsolutePath());
		}

		return urls;
	}

	private static void _initDir(String sourcePath, String destinationPath)
		throws Exception {

		if (!FileUtil.exists(destinationPath)) {
			FileUtil.mkdirs(destinationPath);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		URL url = classLoader.getResource(sourcePath + "/jars.txt");

		URLConnection urlConnection = url.openConnection();

		String[] jarFileNames = StringUtil.split(
			StringUtil.read(urlConnection.getInputStream()));

		for (String jarFileName : jarFileNames) {
			File destinationFile = new File(destinationPath, jarFileName);

			long lastModified = urlConnection.getLastModified();

			if ((destinationFile.lastModified() < lastModified) ||
				(lastModified == 0)) {

				byte[] bytes = FileUtil.getBytes(
					classLoader.getResourceAsStream(
						sourcePath + "/" + jarFileName));

				FileUtil.write(destinationFile, bytes);
			}
		}
	}

}