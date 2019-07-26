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

package com.liferay.talend.runtime.client;

import com.liferay.talend.runtime.client.exception.ResponseContentClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javax.ws.rs.core.Response;

/**
 * @author Igor Beslic
 */
public class ResponseHandler {

	public JsonObject asJsonObject(Response response) {
		JsonReader jsonReader = Json.createReader(
			(InputStream)response.getEntity());

		return jsonReader.readObject();
	}

	public String asText(Response response) {
		InputStream inputStream = (InputStream)response.getEntity();

		InputStreamReader inputStreamReader = new InputStreamReader(
			inputStream);

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		StringBuffer sb = new StringBuffer();

		try {
			while (bufferedReader.ready()) {
				sb.append(bufferedReader.readLine());

				if (bufferedReader.ready()) {
					sb.append(" ");
				}
			}
		}
		catch (IOException ioe) {
			throw new ResponseContentClientException(
				"Unable to read response content", response.getStatus(), ioe);
		}

		return sb.toString();
	}

}