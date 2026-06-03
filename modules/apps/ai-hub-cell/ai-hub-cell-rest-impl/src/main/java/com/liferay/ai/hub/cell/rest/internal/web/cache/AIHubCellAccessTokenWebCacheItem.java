/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.internal.web.cache;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.net.HttpURLConnection;

import java.util.Date;

/**
 * @author Manuele Castro
 */
public class AIHubCellAccessTokenWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		AIHubCellConfiguration aiHubCellConfiguration, long companyId) {

		String key = StringBundler.concat(
			AIHubCellAccessTokenWebCacheItem.class.getName(), StringPool.POUND,
			companyId, StringPool.POUND, aiHubCellConfiguration.clientId(),
			StringPool.POUND, aiHubCellConfiguration.serviceURL());

		JSONObject jsonObject = (JSONObject)WebCachePoolUtil.get(
			key, new AIHubCellAccessTokenWebCacheItem(aiHubCellConfiguration));

		if (!_isExpired(jsonObject)) {
			return jsonObject;
		}

		WebCachePoolUtil.remove(key);

		return (JSONObject)WebCachePoolUtil.get(
			key, new AIHubCellAccessTokenWebCacheItem(aiHubCellConfiguration));
	}

	public AIHubCellAccessTokenWebCacheItem(
		AIHubCellConfiguration aiHubCellConfiguration) {

		_aiHubCellConfiguration = aiHubCellConfiguration;
	}

	@Override
	public Object convert(String key) {
		try {
			Http.Options options = new Http.Options();

			options.addPart("client_id", _aiHubCellConfiguration.clientId());
			options.addPart(
				"client_secret", _aiHubCellConfiguration.clientSecret());
			options.addPart("grant_type", "client_credentials");
			options.setLocation(
				_aiHubCellConfiguration.serviceURL() + "/o/oauth2/token");
			options.setPost(true);

			String responseJSON = HttpUtil.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Response code ", response.getResponseCode(), ": ",
							responseJSON));
				}

				return null;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				responseJSON);

			if (Validator.isBlank(jsonObject.getString("access_token"))) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"The token response has no access token: " +
							responseJSON);
				}

				return null;
			}

			long expirationTime = _getExpirationTime(jsonObject);

			if (expirationTime > 0) {
				jsonObject.put("expirationTime", expirationTime);

				_refreshTime =
					(long)((expirationTime - System.currentTimeMillis()) * 0.8);
			}

			return jsonObject;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	@Override
	public long getRefreshTime() {
		return _refreshTime;
	}

	private static boolean _isExpired(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		long expirationTime = jsonObject.getLong("expirationTime", 0);

		if ((expirationTime > 0) &&
			(expirationTime <= (System.currentTimeMillis() + Time.MINUTE))) {

			return true;
		}

		return false;
	}

	private long _getExpirationTime(JSONObject jsonObject) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(
				jsonObject.getString("access_token"));

			JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

			Date expirationDate = jwtClaimsSet.getExpirationTime();

			if (expirationDate != null) {
				return expirationDate.getTime();
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse and verify the JWT token", exception);
			}
		}

		long expiresIn = jsonObject.getLong("expires_in", 0);

		if (expiresIn > 0) {
			return System.currentTimeMillis() + (expiresIn * Time.SECOND);
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubCellAccessTokenWebCacheItem.class);

	private final AIHubCellConfiguration _aiHubCellConfiguration;
	private long _refreshTime;

}