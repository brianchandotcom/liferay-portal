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

package com.liferay.commerce.initializer.customer.portal.internal.tools.util;

import com.liferay.commerce.initializer.customer.portal.internal.tools.CustomerPortalSampleForecastsBuilder;
import com.liferay.petra.string.StringPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;

/**
 * @author Andrea Di Giorgi
 */
public class CustomerPortalToolsUtil {

	public static String read(String name) throws IOException {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader =
			CustomerPortalSampleForecastsBuilder.class.getClassLoader();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
					classLoader.getResourceAsStream(name),
					StandardCharsets.UTF_8))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	public static void write(Path path, JSONArray jsonArray)
		throws IOException {

		String json = jsonArray.toString(StringPool.FOUR_SPACES.length());

		json = json.replace(StringPool.FOUR_SPACES, StringPool.TAB);

		Files.createDirectories(path.getParent());

		Files.write(path, json.getBytes(StandardCharsets.UTF_8));
	}

}