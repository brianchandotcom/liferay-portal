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

package com.liferay.commerce.cloud.server.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static String read(Class<?> clazz, String name) throws IOException {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(clazz.getResourceAsStream(name)))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append(System.lineSeparator());
				}

				sb.append(line);
			}
		}

		return sb.toString();
	}

}