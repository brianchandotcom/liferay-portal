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

package com.liferay.analytics.settings.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author André Miranda
 */
public class AnalyticsSettingsUtil {

	public static HttpResponse doGet(long companyId, String path)
		throws PortalException {

		try {
			return _request(
				null, companyId,
				new HttpGet(
					String.format(
						"%s/%s", getAsahFaroBackendURL(companyId), path)));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	public static HttpResponse doPatch(
			JSONObject bodyJSONObject, long companyId, String path)
		throws PortalException {

		try {
			return _request(
				bodyJSONObject, companyId,
				new HttpPatch(
					String.format(
						"%s/%s", getAsahFaroBackendURL(companyId), path)));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	public static HttpResponse doPost(
			JSONObject bodyJSONObject, long companyId, String path)
		throws PortalException {

		try {
			return _request(
				bodyJSONObject, companyId,
				new HttpPost(
					String.format(
						"%s/%s", getAsahFaroBackendURL(companyId), path)));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	public static HttpResponse doPost(
			String asahFaroBackendURL, JSONObject bodyJSONObject,
			long companyId, String path)
		throws PortalException {

		if (Validator.isNull(asahFaroBackendURL)) {
			return doPost(bodyJSONObject, companyId, path);
		}

		try {
			return _request(
				bodyJSONObject, companyId,
				new HttpPost(String.format("%s/%s", asahFaroBackendURL, path)));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	public static HttpResponse doPut(
			JSONObject bodyJSONObject, long companyId, String path)
		throws PortalException {

		try {
			return _request(
				bodyJSONObject, companyId,
				new HttpPut(
					String.format(
						"%s/%s", getAsahFaroBackendURL(companyId), path)));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	public static String getAsahFaroBackendDataSourceId(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsDataSourceId");
	}

	public static String getAsahFaroBackendSecuritySignature(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsFaroBackendSecuritySignature");
	}

	public static String getAsahFaroBackendURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsFaroBackendURL");
	}

	public static String getConnectionType(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsConnectionType");
	}

	public static boolean isAnalyticsEnabled(long companyId) {
		if (Validator.isNull(getAsahFaroBackendDataSourceId(companyId)) ||
			Validator.isNull(getAsahFaroBackendSecuritySignature(companyId)) ||
			Validator.isNull(getAsahFaroBackendURL(companyId))) {

			return false;
		}

		return true;
	}

	private static HttpResponse _request(
			JSONObject bodyJSONObject, long companyId,
			HttpRequestBase httpRequestBase)
		throws IOException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.useSystemProperties();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			if ((bodyJSONObject != null) &&
				(httpRequestBase instanceof HttpEntityEnclosingRequestBase)) {

				HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase =
					(HttpEntityEnclosingRequestBase)httpRequestBase;

				httpEntityEnclosingRequestBase.setEntity(
					new StringEntity(
						bodyJSONObject.toString(), StandardCharsets.UTF_8));
			}

			httpRequestBase.setHeader(
				"Content-type", ContentType.APPLICATION_JSON.toString());
			httpRequestBase.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				getAsahFaroBackendSecuritySignature(companyId));

			HttpResponse httpResponse = closeableHttpClient.execute(
				httpRequestBase);

			HttpEntity httpEntity = httpResponse.getEntity();

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			httpEntity.writeTo(byteArrayOutputStream);

			httpResponse.setEntity(
				new ByteArrayEntity(
					byteArrayOutputStream.toByteArray(),
					ContentType.get(httpEntity)));

			return httpResponse;
		}
	}

}