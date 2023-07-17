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