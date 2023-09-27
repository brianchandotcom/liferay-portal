/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Peter Shin
 */
public class CopyrightUtil {

	public static String getCopyright(File projectDir) {
		ClassLoader classLoader = CopyrightUtil.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/gradle/plugins/defaults/dependencies/copyright.txt");

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream));

		Stream<String> stream = bufferedReader.lines();

		return stream.collect(Collectors.joining("\n"));
	}

}