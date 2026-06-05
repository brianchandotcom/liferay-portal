/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.internal.client;

import com.liferay.account.validator.vies.configuration.VIESAccountEntryValidatorConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.nio.charset.StandardCharsets;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author Crescenzo Rega
 */
public class VIESClient {

	public JSONObject checkVatNumber(long companyId, JSONObject jsonObject) {
		try (CloseableHttpResponse closeableHttpResponse =
				_closeableHttpClient.execute(
					_getHttpPost(companyId, jsonObject))) {

			if (_log.isTraceEnabled()) {
				StatusLine statusLine = closeableHttpResponse.getStatusLine();

				_log.trace(
					"Server returned status " + statusLine.getStatusCode());
			}

			return JSONFactoryUtil.createJSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(), StandardCharsets.UTF_8));
		}
		catch (Exception exception) {
			_log.error("Unable to check VAT number", exception);

			return JSONUtil.put(
				"actionSucceed", false
			).put(
				"errorWrappers",
				JSONUtil.putAll(
					JSONUtil.put(
						"error", "IO_ERROR"
					).put(
						"message", exception.getMessage()
					))
			);
		}
	}

	private HttpPost _getHttpPost(long companyId, JSONObject jsonObject)
		throws ConfigurationException {

		VIESAccountEntryValidatorConfiguration
			viesAccountEntryValidatorConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					VIESAccountEntryValidatorConfiguration.class, companyId);

		HttpPost httpPost = new HttpPost(
			viesAccountEntryValidatorConfiguration.viesEndpointURL());

		httpPost.addHeader("Accept", "application/json");
		httpPost.setEntity(
			new StringEntity(
				JSONUtil.put(
					"countryCode", jsonObject.getString("countryCode")
				).put(
					"vatNumber", jsonObject.getString("vatNumber")
				).toString(),
				ContentType.APPLICATION_JSON));

		return httpPost;
	}

	private static final Log _log = LogFactoryUtil.getLog(VIESClient.class);

	private static final CloseableHttpClient _closeableHttpClient =
		HttpClientBuilder.create(
		).setRetryHandler(
			new DefaultHttpRequestRetryHandler(1, true)
		).build();

}