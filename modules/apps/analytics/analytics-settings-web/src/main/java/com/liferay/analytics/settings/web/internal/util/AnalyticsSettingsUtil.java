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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author André Miranda
 */
public class AnalyticsSettingsUtil {

	public static HttpResponse doPost(
			JSONObject bodyJSONObject, long companyId, String path)
		throws IOException {

		return _request(
			bodyJSONObject, companyId,
			new HttpPost(
				String.format(
					"%s/%s", getAsahFaroBackendURL(companyId), path)));
	}

	public static HttpResponse doPut(
			JSONObject bodyJSONObject, long companyId, String path)
		throws IOException {

		return _request(
			bodyJSONObject, companyId,
			new HttpPut(
				String.format(
					"%s/%s", getAsahFaroBackendURL(companyId), path)));
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
			HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase)
		throws IOException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			if (bodyJSONObject != null) {
				httpEntityEnclosingRequestBase.setEntity(
					new StringEntity(bodyJSONObject.toString()));
			}

			httpEntityEnclosingRequestBase.setHeader(
				"Content-type", "application/json");
			httpEntityEnclosingRequestBase.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				getAsahFaroBackendSecuritySignature(companyId));

			HttpResponse httpResponse = closeableHttpClient.execute(
				httpEntityEnclosingRequestBase);

			HttpEntity httpEntity = httpResponse.getEntity();

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			httpEntity.writeTo(byteArrayOutputStream);

			httpResponse.setEntity(
				new ByteArrayEntity(byteArrayOutputStream.toByteArray()));

			return httpResponse;
		}
	}

}