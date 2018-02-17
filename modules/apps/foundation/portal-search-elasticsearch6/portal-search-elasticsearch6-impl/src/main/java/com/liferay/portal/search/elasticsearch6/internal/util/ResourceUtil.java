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

package com.liferay.portal.search.elasticsearch6.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author Michael C. Han
 */
public class ResourceUtil {

	public static String getResourceAsString(
		Class<?> clazz, String resourceName) {

		try (InputStream inputStream = clazz.getResourceAsStream(
				resourceName)) {

			return StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource: " + resourceName, ioe);
		}
	}

	public static File getResourceAsTempFile(Class<?> clazz, String name)
		throws IOException {

		int index = name.lastIndexOf(CharPool.PERIOD);

		File file = File.createTempFile(
			name.substring(0, index), name.substring(index));

		file.deleteOnExit();

		try (InputStream inputStream = clazz.getResourceAsStream(name)) {
			FileUtils.copyInputStreamToFile(inputStream, file);
		}

		return file;
	}

}