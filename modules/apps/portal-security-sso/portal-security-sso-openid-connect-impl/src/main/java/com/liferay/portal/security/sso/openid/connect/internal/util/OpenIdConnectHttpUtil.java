/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import java.util.List;
import java.util.Map;

/**
 * @author Christian Moura
 */
public class OpenIdConnectHttpUtil {

	public static Http.Options toHttpOptions(HTTPRequest httpRequest) {
		Http.Options httpOptions = new Http.Options();

		Map<String, List<String>> headerMap = httpRequest.getHeaderMap();

		if (headerMap != null) {
			for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
				for (String value : entry.getValue()) {
					httpOptions.addHeader(entry.getKey(), value);
				}
			}
		}

		String requestBody = httpRequest.getBody();

		if (Validator.isNotNull(requestBody)) {
			httpOptions.setBody(
				requestBody,
				GetterUtil.getString(
					String.valueOf(httpRequest.getEntityContentType()),
					"application/x-www-form-urlencoded"),
				StringPool.UTF8);
		}

		httpOptions.setLocation(String.valueOf(httpRequest.getURL()));

		if (HTTPRequest.Method.POST.equals(httpRequest.getMethod())) {
			httpOptions.setPost(true);
		}

		int connectTimeout = httpRequest.getConnectTimeout();
		int readTimeout = httpRequest.getReadTimeout();

		if ((connectTimeout > 0) || (readTimeout > 0)) {
			httpOptions.setTimeout(Math.max(connectTimeout, readTimeout));
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

		String contentType = liferayHttpResponse.getContentType();

		if (contentType != null) {
			httpResponse.setContentType(contentType);
		}

		return httpResponse;
	}

}