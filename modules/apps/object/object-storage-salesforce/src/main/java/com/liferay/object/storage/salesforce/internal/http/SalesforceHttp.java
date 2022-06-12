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

package com.liferay.object.storage.salesforce.internal.http;

import com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration;
import com.liferay.object.storage.salesforce.internal.web.cache.SalesforceAccessTokenWebCacheItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(enabled = false, immediate = true, service = SalesforceHttp.class)
public class SalesforceHttp {

	public JSONObject get(long companyId, long groupId, String location) {
		try {
			return _invoke(companyId, groupId, location, Http.Method.GET, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private JSONObject _getSalesforceAccessTokenJSONObject(
		SalesforceConfiguration salesforceConfiguration) {

		return SalesforceAccessTokenWebCacheItem.get(salesforceConfiguration);
	}

	private SalesforceConfiguration _getSalesforceConfiguration(
		long companyId, long groupId) {

		try {
			if (groupId == 0) {
				return ConfigurationProviderUtil.getCompanyConfiguration(
					SalesforceConfiguration.class, companyId);
			}

			return ConfigurationProviderUtil.getGroupConfiguration(
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

		options.setLocation(
			StringBundler.concat(
				jsonObject.getString("instance_url"), "/services/data/v54.0/",
				location));
		options.setMethod(method);

		return _http.URLtoByteArray(options);
	}

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}