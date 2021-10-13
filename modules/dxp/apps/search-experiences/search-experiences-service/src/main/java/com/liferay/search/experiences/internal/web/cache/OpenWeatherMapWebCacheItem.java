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

package com.liferay.search.experiences.internal.web.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.search.experiences.internal.configuration.OpenWeatherMapConfiguration;

/**
 * @author Brian Wing Shun Chan
 */
public class OpenWeatherMapWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		String latitude, String longitude,
		OpenWeatherMapConfiguration openWeatherMapConfiguration) {

		return (JSONObject)WebCachePoolUtil.get(
			StringBundler.concat(
				OpenWeatherMapWebCacheItem.class.getName(), StringPool.POUND,
				latitude, StringPool.POUND, longitude),
			new OpenWeatherMapWebCacheItem(
				latitude, longitude, openWeatherMapConfiguration));
	}

	public OpenWeatherMapWebCacheItem(
		String latitude, String longitude,
		OpenWeatherMapConfiguration openWeatherMapConfiguration) {

		_latitude = latitude;
		_longitude = longitude;
		_openWeatherMapConfiguration = openWeatherMapConfiguration;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			if (!_openWeatherMapConfiguration.enabled()) {
				return JSONFactoryUtil.createJSONObject();
			}

			String url = StringBundler.concat(
				_openWeatherMapConfiguration.apiURL(), "?APPID=",
				_openWeatherMapConfiguration.apiKey(), "&format=json&lat=",
				_latitude, "&lon=", _longitude, "&units=",
				_openWeatherMapConfiguration.units());

			if (_log.isDebugEnabled()) {
				_log.debug("Reading " + url);
			}

			return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(url));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Override
	public long getRefreshTime() {
		if (_openWeatherMapConfiguration.enabled()) {
			return _openWeatherMapConfiguration.cacheTimeout();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenWeatherMapWebCacheItem.class);

	private final String _latitude;
	private final String _longitude;
	private final OpenWeatherMapConfiguration _openWeatherMapConfiguration;

}