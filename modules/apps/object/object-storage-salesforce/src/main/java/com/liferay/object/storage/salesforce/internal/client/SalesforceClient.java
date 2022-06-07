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

package com.liferay.object.storage.salesforce.internal.client;

import com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(
	configurationPid = "com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration",
	enabled = false, immediate = true, service = SalesforceClient.class
)
public class SalesforceClient {

	public JSONObject query(String queryString) {
		try {
			Http.Options options = new Http.Options();

			options.addHeader("Authorization", "Bearer " + _getAccessToken());

			options.setLocation(
				HttpComponentsUtil.addParameter(
					_instanceUrl + "/services/data/v54.0/query/", "q",
					queryString));

			String responseJSON = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new PortalException(
					StringBundler.concat(
						"Response code ", response.getResponseCode(), ": ",
						responseJSON));
			}

			return _jsonFactory.createJSONObject(responseJSON);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_salesforceConfiguration = ConfigurableUtil.createConfigurable(
			SalesforceConfiguration.class, properties);
	}

	private JSONObject _autenticate() {
		try {
			String tokenUrl =
				_salesforceConfiguration.url() + "/services/oauth2/token";

			Http.Options options = new Http.Options();

			options.addPart(
				"client_id", _salesforceConfiguration.consumerKey());
			options.addPart(
				"client_secret", _salesforceConfiguration.consumerSecret());
			options.addPart("grant_type", "password");
			options.addPart("username", _salesforceConfiguration.username());
			options.addPart("password", _salesforceConfiguration.password());

			options.setLocation(tokenUrl);
			options.setPost(true);

			return _jsonFactory.createJSONObject(_http.URLtoString(options));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	private String _getAccessToken() {
		if (Validator.isNull(_accessToken)) {
			JSONObject responseJSONObject = _autenticate();

			_accessToken = responseJSONObject.getString("access_token");
			_instanceUrl = responseJSONObject.getString("instance_url");
		}

		return _accessToken;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SalesforceClient.class);

	private String _accessToken;

	@Reference
	private Http _http;

	private String _instanceUrl;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile SalesforceConfiguration _salesforceConfiguration;

}