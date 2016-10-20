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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public abstract class OAuthUtil {

	public String getValueFromResponse(String response, String field)
		throws IOException {

		JSONObject jsonObject = new JSONObject(response);

		Object value = jsonObject.get(field);

		return value.toString();
	}

	public void writeResponseToFile(String response, String dest)
		throws IOException {

		File destFile = new File(dest);

		destFile.delete();

		response = response.substring(1, response.length() - 1);

		String[] byteValues = response.split(",");

		byte[] bytes = new byte[byteValues.length];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = Byte.parseByte(byteValues[i].trim());
		}

		FileUtils.writeByteArrayToFile(destFile, bytes);
	}

}