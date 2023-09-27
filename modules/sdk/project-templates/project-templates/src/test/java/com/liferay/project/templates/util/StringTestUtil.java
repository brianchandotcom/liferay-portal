/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
public class StringTestUtil {

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