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

package com.liferay.osb.faro.engine.client.http.client;

import com.liferay.osb.faro.engine.client.util.TokenUtil;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.net.URI;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Shinn Lok
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationClientHttpRequestInterceptor
	implements ClientHttpRequestInterceptor {

	public AuthenticationClientHttpRequestInterceptor(FaroProject faroProject) {
		_faroProject = faroProject;
	}

	@Override
	public ClientHttpResponse intercept(
			HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution)
		throws IOException {

		try {
			HttpHeaders httpHeaders = httpRequest.getHeaders();

			httpHeaders.add(
				_ASAH_PROJECT_ID_HEADER, _faroProject.getProjectId());

			httpHeaders.add(
				_ASAH_SECURITY_SIGNATURE_HEADER,
				DigestUtils.sha256Hex(
					StringUtil.merge(
						Arrays.asList(
							TokenUtil.getOSBAsahSecurityToken(),
							HttpRequestUtil.getOriginalURL(httpRequest)),
						StringPool.BLANK)));
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return clientHttpRequestExecution.execute(httpRequest, bytes);
	}

	private static final String _ASAH_PROJECT_ID_HEADER = "OSB-Asah-Project-ID";

	private static final String _ASAH_SECURITY_SIGNATURE_HEADER =
		"OSB-Asah-Faro-Backend-Security-Signature";

	private final FaroProject _faroProject;

	private static class HttpRequestUtil {

		public static String getOriginalURL(HttpRequest httpRequest) {
			StringBuilder sb = new StringBuilder();

			sb.append(_getScheme(httpRequest));
			sb.append("://");
			sb.append(_getServerName(httpRequest));

			int serverPort = _getServerPort(httpRequest);

			if (serverPort > 0) {
				sb.append(":");
				sb.append(serverPort);
			}

			return sb.toString();
		}

		private static String _getHttpHeaderValue(
			HttpHeaders httpHeaders, String headerKey) {

			List<String> values = httpHeaders.get(headerKey);

			if (ListUtil.isEmpty(values)) {
				return null;
			}

			return values.get(0);
		}

		private static String _getScheme(HttpRequest httpRequest) {
			String forwardedProtocol = _getHttpHeaderValue(
				httpRequest.getHeaders(), "X-Forwarded-Proto");

			if (forwardedProtocol != null) {
				return forwardedProtocol;
			}

			URI uri = httpRequest.getURI();

			return uri.getScheme();
		}

		private static String _getServerName(HttpRequest httpRequest) {
			String forwardedHost = _getHttpHeaderValue(
				httpRequest.getHeaders(), "X-Forwarded-Host");

			if (forwardedHost != null) {
				return forwardedHost;
			}

			URI uri = httpRequest.getURI();

			return uri.getHost();
		}

		private static int _getServerPort(HttpRequest httpRequest) {
			int serverPort = 0;

			String forwardedPort = _getHttpHeaderValue(
				httpRequest.getHeaders(), "X-Forwarded-Port");

			if (forwardedPort != null) {
				serverPort = GetterUtil.getInteger(forwardedPort);
			}
			else {
				URI uri = httpRequest.getURI();

				serverPort = uri.getPort();
			}

			if ((serverPort == 80) || (serverPort == 443)) {
				return -1;
			}

			return serverPort;
		}

	}

}