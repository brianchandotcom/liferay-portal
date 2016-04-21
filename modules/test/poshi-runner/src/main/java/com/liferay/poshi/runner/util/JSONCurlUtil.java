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

		curlOptions = curlOptions.trim();

		curlOptions = curlOptions.replaceAll("\\s+\\\\?\\s+", " ");

		StringBuilder sb = new StringBuilder();

		for (String curlOption : curlOptions.split(" ")) {
			if (curlOption.matches("https?:\\/\\/.+")) {
				sb.append(curlOption);
			}
			else if (curlOption.matches("[^\\=]*\\=[^\\=]*")) {
				int x = curlOption.indexOf("=");

				String param = curlOption.substring(0, x);
				String value = curlOption.substring(x + 1);

				if (value.startsWith("{") && value.endsWith("}") &&
					OSDetector.isWindows()) {

					value = value.replaceAll("\"", "\\\\\"");

					value = "\"" + value + "\"";
				}

				sb.append(" ");
				sb.append(param);
				sb.append("=");
				sb.append(value);
			}
			else {
				sb.append(" ");
				sb.append(curlOption);
			}
		}

		Runtime runtime = Runtime.getRuntime();

		System.out.println("curl " + sb.toString());

		Process process = runtime.exec("curl " + sb.toString());

		InputStreamReader inputStreamReader = new InputStreamReader(
			process.getInputStream());

		BufferedReader inputBufferedReader = new BufferedReader(
			inputStreamReader);

		String line = null;

		sb = new StringBuilder();

		while ((line = inputBufferedReader.readLine()) != null) {
			sb.append(line);
		}

		System.out.println(sb.toString());

		try {
			DocumentContext documentContext = JsonPath.parse(sb.toString());

			Object object = documentContext.read(jsonPath);

			return object.toString();
		}
		catch (Exception e) {
			return "";
		}
	}

}