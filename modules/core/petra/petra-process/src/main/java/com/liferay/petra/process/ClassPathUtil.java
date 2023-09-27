/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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