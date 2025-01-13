/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.util;

import com.liferay.marketplace.settings.web.internal.model.Authorization;
import com.liferay.marketplace.settings.web.internal.model.Payload;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Keven Leone
 */
public class MarketplaceHttpUtil {

	public static Authorization exchangeToken(
			long companyId, Payload payload, String refreshToken)
		throws Exception {

		HashMap<String, String> hashMapBuilder = HashMapBuilder.put(
			"client_id", PropsValues.MARKETPLACE_CLIENT_ID
		).put(
			"code", payload.code
		).put(
			"code_verifier", payload.codeVerifier
		).put(
			"grant_type", "authorization_code"
		).put(
			"redirect_uri",
			PropsValues.MARKETPLACE_URL + PropsValues.MARKETPLACE_REDIRECT
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

		options.setLocation(PropsValues.MARKETPLACE_URL + "/o/oauth2/token");
		options.setMethod(Http.Method.POST);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(HttpUtil.URLtoByteArray(options)));

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId);

		long tokenExpiresIn =
			System.currentTimeMillis() +
				(jsonObject.getLong("expires_in") * 1000);

		portletPreferences.setValue(
			PropsKeys.MARKETPLACE_ACCESS_TOKEN,
			jsonObject.getString("access_token"));

		portletPreferences.setValue(PropsKeys.MARKETPLACE_CODE, payload.code);

		portletPreferences.setValue(
			PropsKeys.MARKETPLACE_ACCESS_TOKEN_EXPIRES_IN,
			String.valueOf(tokenExpiresIn));

		portletPreferences.setValue(
			PropsKeys.MARKETPLACE_REFRESH_TOKEN,
			jsonObject.getString("refresh_token"));

		portletPreferences.setValue(
			PropsKeys.MARKETPLACE_SERVICE_URL, payload.serviceURL);

		portletPreferences.setValue(
			PropsKeys.MARKETPLACE_SETTINGS, payload.settings);

		portletPreferences.store();

		return new Authorization(
			jsonObject.getString("access_token"), tokenExpiresIn);
	}

	private static String _toFormEncodedString(Map<String, String> map)
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

}