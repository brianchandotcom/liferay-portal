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

package com.liferay.site.initializer.testray.extra.java.function.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Base64;

import org.json.JSONObject;

/**
 * @author Nícolas Moura
 */
public class HttpClient {

	public static JSONObject get(String path) {
		JSONObject jsonObject = null;

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL(path);

			connection = (HttpURLConnection)url.openConnection();

			// Request setup

			String auth = "test@liferay.com:test";

			byte[] encodedAuth = Base64.getEncoder(
			).encode(
				auth.getBytes(StandardCharsets.UTF_8)
			);
			String authHeaderValue = "Basic " + new String(encodedAuth);

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", authHeaderValue);
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			if (status > 299) {
				reader = new BufferedReader(
					new InputStreamReader(connection.getErrorStream()));

				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}

				reader.close();
			}
			else {
				reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}

				reader.close();
			}

			jsonObject = new JSONObject(responseContent.toString());
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			connection.disconnect();
		}

		return jsonObject;
	}

	public static JSONObject post(String urlTarget, JSONObject params) {
		JSONObject jsonObject = null;

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL(urlTarget);

			// bodyPost

			byte[] postDataBytes = parseBodyData(params);

			// Request setup

			String auth = "test@liferay.com:test";

			byte[] encodedAuth = Base64.getEncoder(
			).encode(
				auth.getBytes(StandardCharsets.UTF_8)
			);
			String authHeaderValue = "Basic " + new String(encodedAuth);

			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", authHeaderValue);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty(
				"Content-Length", String.valueOf(postDataBytes.length));
			connection.setDoOutput(true);
			connection.getOutputStream(
			).write(
				postDataBytes
			);

			int status = connection.getResponseCode();

			if (status > 299) {
				reader = new BufferedReader(
					new InputStreamReader(connection.getErrorStream()));

				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}

				reader.close();
			}
			else {
				reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}

				reader.close();
			}

			jsonObject = new JSONObject(responseContent.toString());
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			connection.disconnect();
		}

		return jsonObject;
	}

	private static byte[] parseBodyData(JSONObject postData)
		throws IOException, MalformedURLException {

		return postData.toString(
		).getBytes(
			"UTF-8"
		);
	}

	private static HttpURLConnection connection;

}