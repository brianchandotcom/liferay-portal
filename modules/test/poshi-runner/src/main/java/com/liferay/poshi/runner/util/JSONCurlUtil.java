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

package com.liferay.poshi.runner.util;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Michael Hashimoto
 */
public class JSONCurlUtil {

	public static String get(String curlOptions, String jsonPath)
		throws Exception {

		Runtime runtime = Runtime.getRuntime();

		Process process = runtime.exec("curl " + curlOptions);

		InputStreamReader inputStreamReader = new InputStreamReader(
			process.getInputStream());

		BufferedReader inputBufferedReader = new BufferedReader(
			inputStreamReader);

		String line = null;

		StringBuilder sb = new StringBuilder();

		while ((line = inputBufferedReader.readLine()) != null) {
			sb.append(line);
		}

		System.out.println(sb.toString());

		DocumentContext documentContext = JsonPath.parse(sb.toString());

		Object object = documentContext.read(jsonPath);

		return object.toString();
	}

}