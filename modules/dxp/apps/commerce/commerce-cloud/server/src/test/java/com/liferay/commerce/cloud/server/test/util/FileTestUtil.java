/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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