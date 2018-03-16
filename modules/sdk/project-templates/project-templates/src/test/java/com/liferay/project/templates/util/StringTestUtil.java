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

package com.liferay.project.templates.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
public class StringTestUtil {

	public static boolean contains(
		String content, String string, boolean regex) {

		boolean found = false;

		if (regex) {
			Pattern pattern = Pattern.compile(
				string, Pattern.DOTALL | Pattern.MULTILINE);

			Matcher matcher = pattern.matcher(content);

			found = matcher.matches();
		}
		else {
			found = content.contains(string);
		}

		return found;
	}

	public static String getBetweenStrings(
		String text, String textFrom, String textTo) {

		String result = text.substring(
			text.indexOf(textFrom) + textFrom.length());

		result = result.substring(0, result.indexOf(textTo));

		return result;
	}

	public static String merge(Iterable<String> strings, char separator) {
		StringBuilder sb = new StringBuilder();

		boolean first = true;

		for (String s : strings) {
			if (!first) {
				sb.append(separator);
			}

			first = false;

			sb.append(s);
		}

		return sb.toString();
	}

	public static List<String> readLines(InputStream inputStream)
		throws IOException {

		List<String> lines = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}

}