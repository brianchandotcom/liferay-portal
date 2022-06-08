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

import java.io.IOException;

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

			options.addHeader(
				"Authorization", "Bearer " + _getAccessToken(false));

			options.setLocation(
				HttpComponentsUtil.addParameter(
					_instanceUrl + "/services/data/v54.0/query/", "q",
					queryString));

			String responseJSON = _http.URLtoString(options);
			Http.Response response = options.getResponse();

			if (response.getResponseCode() ==
					HttpURLConnection.HTTP_UNAUTHORIZED) {

				options.addHeader(
					"Authorization", "Bearer " + _getAccessToken(true));

				responseJSON = _http.URLtoString(options);
				response = options.getResponse();
			}

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
				_log.debug(
					"Unable to execute query: " + queryString, exception);
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

	private JSONObject _autenticate() throws PortalException {
		try {
			Http.Options options = new Http.Options();

			options.addPart(
				"client_id", _salesforceConfiguration.consumerKey());
			options.addPart(
				"client_secret", _salesforceConfiguration.consumerSecret());
			options.addPart("grant_type", "password");
			options.addPart("password", _salesforceConfiguration.password());
			options.addPart("username", _salesforceConfiguration.username());
			options.setLocation(
				_salesforceConfiguration.url() + "/services/oauth2/token");
			options.setPost(true);

			String responseJSON = _http.URLtoString(options);
			Http.Response response = options.getResponse();

			if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				_log.error("Unable to authenticate with Salesforce");

				throw new PortalException(
					StringBundler.concat(
						"Unable to authenticate with Salesforce. Unexpected ",
						"response status ", response.getResponseCode(),
						" with response message: ", responseJSON));
			}

			return _jsonFactory.createJSONObject(_http.URLtoString(options));
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with Salesforce");

			throw new PortalException(
				"Unable to authenticate with Salesforce", ioException);
		}
	}

	private String _getAccessToken(boolean forceAuthentication)
		throws PortalException {

		if (Validator.isNull(_accessToken) || forceAuthentication) {
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