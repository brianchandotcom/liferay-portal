/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.soy.builder.util;

import com.liferay.petra.string.StringBundler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static String read(ClassLoader classLoader, String name)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(classLoader.getResourceAsStream(name)))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append('\n');
				}

				sb.append(line);
			}
		}

		return sb.toString();
	}

	public static String read(Path path) throws IOException {
		String s = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		return s.replace("\r\n", "\n");
	}

}