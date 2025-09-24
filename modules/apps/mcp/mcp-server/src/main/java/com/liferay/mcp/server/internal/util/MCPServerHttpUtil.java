/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

/**
 * @author Alejandro Tardín
 */
public class MCPServerHttpUtil {

	public static String callEndpoint(
			String method, String path, String payload,
			String authorizationHeader)
		throws IOException {

		Http http = HttpUtil.getHttp();

		Http.Options options = new Http.Options();

		if (Validator.isNotNull(payload)) {
			options.setBody(
				payload, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		}

		options.setHeaders(
			HashMapBuilder.put(
				"Authorization", () -> authorizationHeader
			).build());

		options.setLocation(path);
		options.setMethod(Http.Method.valueOf(StringUtil.toUpperCase(method)));

		String content = http.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode >= 300) {
			throw new RuntimeException(
				StringBundler.concat(
					"Request to ", path, " failed with status ", responseCode,
					content));
		}

		return content;
	}

}