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

package com.liferay.analytics.message.sender.internal;

import com.liferay.analytics.message.sender.client.AnalyticsBatchClient;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.text.Format;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = AnalyticsBatchClient.class)
public class AnalyticsBatchClientImpl
	extends BaseAnalyticsClientImpl implements AnalyticsBatchClient {

	@Override
	public File downloadResource(
		long companyId, Date resourceLastModifiedDate, String resourceName) {

		if (!isEnabled(companyId)) {
			throw new IllegalStateException(
				"Analytics batch client is not enabled");
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		try (CloseableHttpClient closeableHttpClient =
				getCloseableHttpClient()) {

			URIBuilder uriBuilder = new URIBuilder(
				analyticsConfiguration.liferayAnalyticsEndpointURL() +
					"/dxp-batch-entities");

			uriBuilder = uriBuilder.addParameter("resourceName", resourceName);

			HttpGet httpGet = new HttpGet(uriBuilder.build());

			_setDefaultRequestHeaders(companyId, httpGet);

			httpGet.setHeader(
				"If-Modified-Since",
				_modifiedSinceHeaderDateFormatter.format(
					resourceLastModifiedDate));

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
				JSONObject responseJSONObject = _jsonFactory.createJSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				processInvalidTokenMessage(
					companyId, false, responseJSONObject.getString("message"));
			}

			HttpEntity httpEntity = closeableHttpResponse.getEntity();

			if (httpEntity != null) {
				return FileUtil.createTempFile(httpEntity.getContent());
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return null;
	}

	@Override
	public void uploadResource(
		long companyId, InputStream resourceInputStream, String resourceName) {

		if (!isEnabled(companyId)) {
			throw new IllegalStateException(
				"Analytics Batch Client is not enabled");
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		try (CloseableHttpClient closeableHttpClient =
				getCloseableHttpClient()) {

			MultipartEntityBuilder multipartEntityBuilder =
				MultipartEntityBuilder.create();

			multipartEntityBuilder.addBinaryBody(
				"file", resourceInputStream, ContentType.MULTIPART_FORM_DATA,
				resourceName);

			HttpPost httpPost = new HttpPost(
				analyticsConfiguration.liferayAnalyticsEndpointURL() +
					"/dxp-batch-entities");

			httpPost.setEntity(multipartEntityBuilder.build());

			_setDefaultRequestHeaders(companyId, httpPost);

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			int statusCode = statusLine.getStatusCode();

			if (statusCode == HttpStatus.SC_FORBIDDEN) {
				JSONObject responseJSONObject = _jsonFactory.createJSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				processInvalidTokenMessage(
					companyId, false, responseJSONObject.getString("message"));
			}

			if ((statusCode < 200) || (statusCode >= 300)) {
				throw new Exception(
					String.format(
						"HTTP %s error during resource upload. %s", statusCode,
						statusLine.getReasonPhrase()));
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Upload completed successfully");
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _setDefaultRequestHeaders(
		long companyId, HttpUriRequest httpUriRequest) {

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		httpUriRequest.setHeader(
			"OSB-Asah-Data-Source-ID",
			analyticsConfiguration.liferayAnalyticsDataSourceId());
		httpUriRequest.setHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature());
		httpUriRequest.setHeader(
			"OSB-Asah-Project-ID",
			analyticsConfiguration.liferayAnalyticsProjectId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsBatchClientImpl.class);

	private static final Format _modifiedSinceHeaderDateFormatter =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz");

	@Reference
	private JSONFactory _jsonFactory;

}