/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

/**
 * @author Alejandro Tardín
 */
public class MCPServerHttpUtil {

	public static String callEndpoint(
		String method, String path, String payload, String accessToken) {

		try {
			URL url = new URL(path);

			HttpURLConnection connection =
				(HttpURLConnection)url.openConnection();

			// TODO Allow guest access?

			if (accessToken != null) {
				connection.setRequestProperty(
					"Authorization", "Bearer " + accessToken);
			}

			connection.setDoOutput(true);
			connection.setRequestMethod(StringUtil.toUpperCase(method));

			if (Validator.isNotNull(payload)) {
				connection.setRequestProperty(
					"Content-Type", "application/json");

				try (OutputStream outputStream = connection.getOutputStream()) {
					outputStream.write(
						payload.getBytes(StandardCharsets.UTF_8));
				}
			}

			int status = connection.getResponseCode();

			if (status >= 300) {
				String errorMessage = StringBundler.concat(
					"Request to ", path, " failed with status ", status);

				InputStream inputStream = connection.getErrorStream();

				if (inputStream != null) {
					errorMessage += StringUtil.read(inputStream);
				}

				throw new Exception(errorMessage);
			}

			return StringUtil.read(connection.getInputStream());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}