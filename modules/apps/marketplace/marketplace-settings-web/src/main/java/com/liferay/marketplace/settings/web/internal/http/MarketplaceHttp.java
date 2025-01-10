/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.http;

import com.liferay.marketplace.settings.web.internal.configuration.MarketplaceConfigurationUtil;
import com.liferay.marketplace.settings.web.internal.model.Authorization;
import com.liferay.marketplace.settings.web.internal.model.Payload;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Keven Leone
 */
@Component(service = MarketplaceHttp.class)
public class MarketplaceHttp {

	public Authorization exchangeToken(
			long companyId, Payload payload, String refreshToken)
		throws Exception {

		HashMap<String, String> hashMapBuilder = HashMapBuilder.put(
			"client_id", payload.clientId
		).put(
			"code", payload.code
		).put(
			"code_verifier", payload.codeVerifier
		).put(
			"grant_type", "authorization_code"
		).put(
			"redirect_uri", payload.url + payload.redirect
		).build();

		if (refreshToken != null) {
			hashMapBuilder.put("grant_type", "refresh_token");
			hashMapBuilder.put("refresh_token", refreshToken);

			hashMapBuilder.remove("code_verifier");
		}

		Http.Options options = new Http.Options();

		options.setBody(
			_toFormEncodedString(hashMapBuilder),
			ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED, StringPool.UTF8);

		options.setLocation(payload.url + "/o/oauth2/token");
		options.setMethod(Http.Method.POST);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			new String(_http.URLtoByteArray(options)));

		MarketplaceConfigurationUtil.saveMarketplaceConfiguration(
			companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"accessToken", jsonObject.getString("access_token")
			).put(
				"clientId", payload.clientId
			).put(
				"code", payload.code
			).put(
				"expiresIn",
				System.currentTimeMillis() +
					(jsonObject.getLong("expires_in") * 1000)
			).put(
				"redirect", payload.redirect
			).put(
				"refreshToken", jsonObject.getString("refresh_token")
			).put(
				"serviceURL", payload.serviceURL
			).put(
				"settings", payload.settings
			).put(
				"url", payload.url
			).build());

		return new Authorization(
			jsonObject.getString("access_token"), payload.serviceURL,
			payload.settings, payload.url);
	}

	private String _toFormEncodedString(Map<String, String> map)
		throws Exception {

		StringBuilder encodedString = new StringBuilder();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (encodedString.length() > 0) {
				encodedString.append("&");
			}

			encodedString.append(
				URLEncoder.encode(entry.getKey(), StringPool.UTF8)
			).append(
				"="
			).append(
				URLEncoder.encode(entry.getValue(), StringPool.UTF8)
			);
		}

		return encodedString.toString();
	}

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}