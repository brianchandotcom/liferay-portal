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

package com.liferay.petra.process;

import com.liferay.petra.string.StringUtil;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class ClassPathUtil {

	public static URL[] getClassPathURLs(String classPath)
		throws MalformedURLException {

		List<String> paths = StringUtil.split(
			classPath, File.pathSeparatorChar);

		Set<URL> urls = new LinkedHashSet<>();

		for (String path : paths) {
			File file = new File(path);

			URI uri = file.toURI();

			urls.add(uri.toURL());
		}

		return urls.toArray(new URL[0]);
	}

	public static String getJVMClassPath(boolean includeBootClassPath) {
		String jvmClassPath = System.getProperty("java.class.path");

		if (includeBootClassPath) {
			String bootClassPath = System.getProperty("sun.boot.class.path");

			if (bootClassPath != null) {
				jvmClassPath = jvmClassPath.concat(
					File.pathSeparator
				).concat(
					bootClassPath
				);
			}
		}

		return jvmClassPath;
	}

}