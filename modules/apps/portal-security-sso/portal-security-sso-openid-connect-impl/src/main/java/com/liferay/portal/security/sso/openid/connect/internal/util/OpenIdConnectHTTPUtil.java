/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;

import com.nimbusds.common.contenttype.ContentType;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * @author Christian Moura
 */
public class OpenIdConnectHTTPUtil {

	public static Http.Options toHttpOptions(HTTPRequest httpRequest) {
		Http.Options httpOptions = new Http.Options();

		URL url = httpRequest.getURL();

		httpOptions.setLocation(url.toString());

		int connectTimeout = httpRequest.getConnectTimeout();
		int readTimeout = httpRequest.getReadTimeout();

		if ((connectTimeout > 0) || (readTimeout > 0)) {
			httpOptions.setTimeout(Math.max(connectTimeout, readTimeout));
		}

		if (HTTPRequest.Method.POST.equals(httpRequest.getMethod())) {
			httpOptions.setPost(true);
		}

		Map<String, List<String>> headerMap = httpRequest.getHeaderMap();

		if (headerMap != null) {
			for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
				for (String value : entry.getValue()) {
					httpOptions.addHeader(entry.getKey(), value);
				}
			}
		}

		String requestBody = httpRequest.getBody();

		if ((requestBody != null) && !requestBody.isEmpty()) {
			String contentType = "application/x-www-form-urlencoded";

			ContentType entityContentType = httpRequest.getEntityContentType();

			if (entityContentType != null) {
				contentType = entityContentType.toString();
			}

			httpOptions.setBody(requestBody, contentType, StringPool.UTF8);
		}

		return httpOptions;
	}

	public static HTTPResponse toHTTPResponse(
			Http.Options httpOptions, String responseContent)
		throws ParseException {

		Http.Response liferayHttpResponse = httpOptions.getResponse();

		HTTPResponse httpResponse = new HTTPResponse(
			liferayHttpResponse.getResponseCode());

		httpResponse.setBody(responseContent);

		String responseContentType = liferayHttpResponse.getContentType();

		if (responseContentType != null) {
			httpResponse.setContentType(responseContentType);
		}

		return httpResponse;
	}

}
