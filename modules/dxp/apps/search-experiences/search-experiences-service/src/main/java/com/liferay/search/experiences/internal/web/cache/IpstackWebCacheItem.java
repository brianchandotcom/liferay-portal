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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.search.experiences.exception.NonPublicIPAddressException;
import com.liferay.search.experiences.internal.configuration.IpstackConfiguration;

import java.beans.ExceptionListener;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @author Brian Wing Shun Chan
 */
public class IpstackWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		ExceptionListener exceptionListener, String ipAddress,
		IpstackConfiguration ipstackConfiguration) {

		return (JSONObject)WebCachePoolUtil.get(
			IpstackWebCacheItem.class.getName() + StringPool.POUND + ipAddress,
			new IpstackWebCacheItem(
				exceptionListener, ipAddress, ipstackConfiguration));
	}

	public IpstackWebCacheItem(
		ExceptionListener exceptionListener, String ipAddress,
		IpstackConfiguration ipstackConfiguration) {

		_exceptionListener = exceptionListener;
		_ipAddress = ipAddress;
		_ipstackConfiguration = ipstackConfiguration;
	}

	@Override
	public JSONObject convert(String key) {
		try {
			if (!_ipstackConfiguration.enabled() || !_isPublicIPAddress()) {
				return JSONFactoryUtil.createJSONObject();
			}

			String apiURL = _ipstackConfiguration.apiURL();

			if (!apiURL.endsWith("/")) {
				apiURL += "/";
			}

			String url = StringBundler.concat(
				apiURL, _ipAddress, "?access_key=",
				_ipstackConfiguration.apiKey());

			if (_log.isDebugEnabled()) {
				_log.debug("Reading " + url);
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				HttpUtil.URLtoString(url));

			_validateResponse(jsonObject);

			return jsonObject;
		}
		catch (Exception exception) {
			_exceptionListener.exceptionThrown(exception);

			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Override
	public long getRefreshTime() {
		if (_ipstackConfiguration.enabled()) {
			return _ipstackConfiguration.cacheTimeout();
		}

		return 0;
	}

	private boolean _isPublicIPAddress() throws Exception {
		Inet4Address inet4Address = (Inet4Address)InetAddress.getByName(
			_ipAddress);

		if (inet4Address.isSiteLocalAddress() ||
			inet4Address.isAnyLocalAddress() ||
			inet4Address.isLinkLocalAddress() ||
			inet4Address.isLoopbackAddress() ||
			inet4Address.isMulticastAddress()) {

			_exceptionListener.exceptionThrown(
				new NonPublicIPAddressException(
					StringBundler.concat(
						"IPStack is enabled but geolocation could not be ",
						"resolved for a non public IP address ", _ipAddress,
						". Search context attribute 'search.experiences.ip.",
						"address' can be used to test a public address")));

			return false;
		}

		return true;
	}

	private void _validateResponse(JSONObject jsonObject) {
		boolean success = jsonObject.getBoolean("success", true);

		if (success) {
			return;
		}

		_exceptionListener.exceptionThrown(
			new RuntimeException(
				StringBundler.concat(
					"IPStack: ",
					JSONUtil.getValueAsString(
						jsonObject, "JSONObject/error", "Object/info"),
					" (",
					JSONUtil.getValueAsString(
						jsonObject, "JSONObject/error", "Object/code"),
					")")));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IpstackWebCacheItem.class);

	private final ExceptionListener _exceptionListener;
	private final String _ipAddress;
	private final IpstackConfiguration _ipstackConfiguration;

}