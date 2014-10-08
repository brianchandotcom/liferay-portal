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

package com.liferay.portal.microsofttranslator;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslatorAuthenticator {

	public MicrosoftTranslatorAuthenticator() {
		init(true);
	}

	public MicrosoftTranslatorAuthenticator(
		String clientId, String clientSecret) {

		_clientId = clientId;
		_clientSecret = clientSecret;

		init(true);
	}

	public String getAccessToken() {
		init(false);

		return _accessToken;
	}

	public String getError() {
		return _error;
	}

	public void init(boolean manual) {
		if (manual || isStale()) {
			doInit();
		}
	}

	protected void doInit() {
		if (Validator.isNull(_clientId)) {
			_clientId = PropsValues.MICROSOFT_TRANSLATOR_CLIENT_ID;
			_clientSecret = PropsValues.MICROSOFT_TRANSLATOR_CLIENT_SECRET;
		}

		try {
			Http.Options options = new Http.Options();

			StringBundler sb = new StringBundler(5);

			sb.append("grant_type=client_credentials&client_id=");
			sb.append(HttpUtil.encodeURL(_clientId));
			sb.append("&client_secret=");
			sb.append(HttpUtil.encodeURL(_clientSecret));
			sb.append("&scope=http://api.microsofttranslator.com");

			options.setBody(
				sb.toString(), ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED,
				StringPool.UTF8);

			options.setLocation(_URL);

			options.setPost(true);

			String jsonString = HttpUtil.URLtoString(options);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				jsonString);

			_error = jsonObject.getString("error_description");

			if (_error != null) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to initialize access token: " + _error);
				}
			}

			_accessToken = jsonObject.getString("access_token");

			if (_accessToken != null) {
				_log.info("Access token " + _accessToken);
			}

			_initTime = System.currentTimeMillis();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to initialize authentication token", e);
			}
		}
	}

	protected boolean isStale() {
		if ((_initTime + _EXPIRE_TIME) > System.currentTimeMillis()) {
			return false;
		}
		else {
			return true;
		}
	}

	private static final long _EXPIRE_TIME = 10 * Time.MINUTE;

	private static final String _URL =
		"https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";

	private static final Log _log = LogFactoryUtil.getLog(
		MicrosoftTranslatorAuthenticator.class);

	private String _accessToken;
	private String _clientId;
	private String _clientSecret;
	private String _error;
	private long _initTime;

}