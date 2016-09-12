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

package com.liferay.dynamic.data.mapping.data.provider.rest.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumer;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.data.provider.type=rest",
	service = {DDMDataProvider.class, DDMDataProviderConsumer.class}
)
public class DDMRESTDataProvider
	implements DDMDataProvider, DDMDataProviderConsumer {

	@Override
	public DDMDataProviderConsumerResponse execute(
			DDMDataProviderConsumerRequest ddmDataProviderConsumerRequest)
		throws DDMDataProviderException {

		try {
			DDMDataProviderContext ddmDataProviderContext =
				ddmDataProviderConsumerRequest.getDDMDataProviderContext();

			DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
				ddmDataProviderContext.getSettingsInstance(
					DDMRESTDataProviderSettings.class);

			HttpRequest httpRequest = HttpRequest.get(
				ddmRESTDataProviderSettings.url());

			if (Validator.isNotNull(ddmRESTDataProviderSettings.username())) {
				httpRequest.basicAuthentication(
					ddmRESTDataProviderSettings.username(),
					ddmRESTDataProviderSettings.password());
			}

			httpRequest.query(ddmDataProviderContext.getParameters());

			HttpResponse httpResponse = httpRequest.send();

			String httpResponseBody = httpResponse.body();

			JSONArray jsonArray = null;

			try {
				jsonArray = _jsonFactory.createJSONArray(httpResponseBody);
			}
			catch (JSONException jsone) {
				jsonArray = _jsonFactory.createJSONArray();

				jsonArray.put(_jsonFactory.createJSONObject(httpResponseBody));
			}

			return createDDMDataProviderConsumerResponse(jsonArray);
		}
		catch (Exception e) {
			throw new DDMDataProviderException(e);
		}
	}

	@Override
	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		try {
			return doGetData(ddmDataProviderContext);
		}
		catch (PortalException pe) {
			throw new DDMDataProviderException(pe);
		}
	}

	@Override
	public Class<?> getSettings() {
		return DDMRESTDataProviderSettings.class;
	}

	protected DDMDataProviderConsumerResponse
		createDDMDataProviderConsumerResponse(JSONArray jsonArray) {

		List<Map<Object, Object>> data = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Iterator<String> keysIterator = jsonObject.keys();

			Map<Object, Object> map = new HashMap<>();

			data.add(map);

			while (keysIterator.hasNext()) {
				String key = keysIterator.next();

				map.put(key, getJSONObjectValue(jsonObject, key));
			}
		}

		DDMDataProviderConsumerResponse ddmDataProviderConsumerResponse =
			new DDMDataProviderConsumerResponse();

		ddmDataProviderConsumerResponse.setData(data);

		return ddmDataProviderConsumerResponse;
	}

	protected List<KeyValuePair> doGetData(
			DDMDataProviderContext ddmDataProviderContext)
		throws PortalException {

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			ddmDataProviderContext.getSettingsInstance(
				DDMRESTDataProviderSettings.class);

		HttpRequest httpRequest = HttpRequest.get(
			ddmRESTDataProviderSettings.url());

		if (Validator.isNotNull(ddmRESTDataProviderSettings.username())) {
			httpRequest.basicAuthentication(
				ddmRESTDataProviderSettings.username(),
				ddmRESTDataProviderSettings.password());
		}

		httpRequest.query(ddmDataProviderContext.getParameters());

		String cacheKey = getCacheKey(httpRequest);

		DDMRESTDataProviderResult ddmRESTDataProviderResult = _portalCache.get(
			cacheKey);

		if ((ddmRESTDataProviderResult != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmRESTDataProviderResult.getKeyValuePairs();
		}

		HttpResponse httpResponse = httpRequest.send();

		JSONArray jsonArray = _jsonFactory.createJSONArray(httpResponse.body());

		List<KeyValuePair> results = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString(
				ddmRESTDataProviderSettings.key());
			String value = jsonObject.getString(
				ddmRESTDataProviderSettings.value());

			results.add(new KeyValuePair(key, value));
		}

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(cacheKey, new DDMRESTDataProviderResult(results));
		}

		return results;
	}

	protected String getCacheKey(HttpRequest httpRequest) {
		return httpRequest.url();
	}

	protected Object getJSONObjectValue(JSONObject jsonObject, String key) {
		if (!jsonObject.has(key)) {
			return StringPool.BLANK;
		}

		if (jsonObject.getJSONArray(key) != null) {
			return jsonObject.getJSONArray(key);
		}
		else if (jsonObject.getJSONObject(key) != null) {
			return jsonObject.getJSONObject(key);
		}

		return jsonObject.get(key);
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMRESTDataProviderResult>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
	}

	private JSONFactory _jsonFactory;
	private PortalCache<String, DDMRESTDataProviderResult> _portalCache;

	private static class DDMRESTDataProviderResult implements Serializable {

		public DDMRESTDataProviderResult(List<KeyValuePair> keyValuePairs) {
			_keyValuePairs = keyValuePairs;
		}

		public List<KeyValuePair> getKeyValuePairs() {
			return _keyValuePairs;
		}

		private final List<KeyValuePair> _keyValuePairs;

	}

}