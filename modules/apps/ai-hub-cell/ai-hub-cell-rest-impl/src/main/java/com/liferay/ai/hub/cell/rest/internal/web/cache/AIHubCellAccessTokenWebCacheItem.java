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
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import java.net.HttpURLConnection;

/**
 * @author Manuele Castro
 */
public class AIHubCellAccessTokenWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		AIHubCellConfiguration aiHubCellConfiguration, long companyId) {

		return (JSONObject)WebCachePoolUtil.get(
			StringBundler.concat(
				AIHubCellAccessTokenWebCacheItem.class.getName(),
				StringPool.POUND, companyId, StringPool.POUND,
				aiHubCellConfiguration.clientId(), StringPool.POUND,
				aiHubCellConfiguration.serviceURL()),
			new AIHubCellAccessTokenWebCacheItem(aiHubCellConfiguration));
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
			options.addPart("scope", "Liferay.AI.Hub.REST.everything");
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

			_refreshTime =
				(long)(jsonObject.getLong("expires_in") * 0.8 * Time.SECOND);

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

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubCellAccessTokenWebCacheItem.class);

	private final AIHubCellConfiguration _aiHubCellConfiguration;
	private long _refreshTime;

}