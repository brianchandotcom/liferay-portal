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

package com.liferay.analytics.reports.web.internal.client;

import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author David Arques
 */
public class AsahFaroBackendClient {

	public String doGet(long companyId, String path) {
		try {
			HttpResponse httpResponse = _request(
				companyId,
				new HttpGet(
					String.format(
						"%s/%s",
						AnalyticsReportsUtil.getAsahFaroBackendURL(companyId),
						path)));

			String response = EntityUtils.toString(httpResponse.getEntity());

			StatusLine statusLine = httpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new NestableRuntimeException(
					StringBundler.concat(
						"Unexpected response status ",
						statusLine.getStatusCode(), " with response message: ",
						response));
			}

			return response;
		}
		catch (IOException ioException) {
			throw new NestableRuntimeException(
				"Request to Asah Faro backend failed", ioException);
		}
	}

	public boolean isValidConnection(long companyId) {
		if (!AnalyticsReportsUtil.isAnalyticsConnected(companyId)) {
			return false;
		}

		try {
			String asahFaroBackendDataSourceId =
				AnalyticsReportsUtil.getAsahFaroBackendDataSourceId(companyId);

			doGet(
				companyId,
				"/api/1.0/data-sources/" + asahFaroBackendDataSourceId);

			return true;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid Asah Faro backend connection", exception);
			}

			return false;
		}
	}

	private HttpResponse _request(
			long companyId, HttpRequestBase httpRequestBase)
		throws IOException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.useSystemProperties();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			httpRequestBase.setHeader("Accept", "application/json");
			httpRequestBase.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				AnalyticsReportsUtil.getAsahFaroBackendSecuritySignature(
					companyId));

			HttpResponse httpResponse = closeableHttpClient.execute(
				httpRequestBase);

			HttpEntity httpEntity = httpResponse.getEntity();

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			httpEntity.writeTo(byteArrayOutputStream);

			httpResponse.setEntity(
				new ByteArrayEntity(byteArrayOutputStream.toByteArray()));

			return httpResponse;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendClient.class);

}