/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.salesforce.internal.http;

import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.object.storage.salesforce.configuration.SalesforceConfiguration;
import com.liferay.object.storage.salesforce.internal.web.cache.SalesforceAccessTokenWebCacheItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;

import java.net.HttpURLConnection;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(service = SalesforceHttp.class)
public class SalesforceHttp {

	public JSONObject delete(long companyId, long groupId, String location) {
		try {
			return _invoke(
				companyId, groupId, location, Http.Method.DELETE, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public JSONObject get(long companyId, long groupId, String location) {
		try {
			return _invoke(companyId, groupId, location, Http.Method.GET, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public JSONObject patch(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.PATCH,
				bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public JSONObject post(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.POST, bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private JSONObject _getSalesforceAccessTokenJSONObject(
		SalesforceConfiguration salesforceConfiguration) {

		JSONObject jSONObject = SalesforceAccessTokenWebCacheItem.get(
			salesforceConfiguration);

		if (jSONObject == null) {
			throw new ObjectEntryManagerHttpException(
				"Unable to authenticate with Salesforce");
		}

		return jSONObject;
	}

	private SalesforceConfiguration _getSalesforceConfiguration(
		long companyId, long groupId) {

		try {
			if (groupId == 0) {
				return _configurationProvider.getCompanyConfiguration(
					SalesforceConfiguration.class, companyId);
			}

			return _configurationProvider.getGroupConfiguration(
				SalesforceConfiguration.class, groupId);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	private JSONObject _invoke(
			long companyId, long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		byte[] bytes = _invokeAsBytes(
			companyId, groupId, location, method, bodyJSONObject);

		if (bytes == null) {
			return _jsonFactory.createJSONObject();
		}

		return _jsonFactory.createJSONObject(new String(bytes));
	}

	private byte[] _invokeAsBytes(
			long companyId, long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		Http.Options options = new Http.Options();

		if (bodyJSONObject != null) {
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		}

		JSONObject jsonObject = _getSalesforceAccessTokenJSONObject(
			_getSalesforceConfiguration(companyId, groupId));

		options.addHeader(
			"Authorization", "Bearer " + jsonObject.getString("access_token"));

		if (bodyJSONObject != null) {
			options.setBody(
				bodyJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
		}

		options.setFollowRedirects(false);
		options.setLocation(
			StringBundler.concat(
				jsonObject.getString("instance_url"), "/services/data/v54.0/",
				location));
		options.setMethod(method);

		byte[] bytes = _http.URLtoByteArray(options);

		Http.Response response = options.getResponse();

		if ((response.getResponseCode() < HttpURLConnection.HTTP_OK) ||
			(response.getResponseCode() >=
				HttpURLConnection.HTTP_MULT_CHOICE)) {

			throw new ObjectEntryManagerHttpException(
				StringBundler.concat(
					"Unexpected response code ", response.getResponseCode(),
					" with response message: ", new String(bytes)));
		}

		return bytes;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}