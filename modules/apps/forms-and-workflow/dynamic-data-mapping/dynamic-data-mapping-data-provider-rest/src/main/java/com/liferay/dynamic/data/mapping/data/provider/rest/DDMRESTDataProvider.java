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

package com.liferay.dynamic.data.mapping.data.provider.rest;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseEntry;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, property = "ddm.data.provider.type=rest")
public class DDMRESTDataProvider implements DDMDataProvider {

	public DDMDataProviderResponse getData(
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

	protected DDMDataProviderResponseEntry createDDMDataProviderResponseEntry(
		String key, String value) {

		DDMDataProviderResponseEntry ddmDataProviderResponseEntry =
			new DDMDataProviderResponseEntry();

		ddmDataProviderResponseEntry.setProperty(key, value);

		return ddmDataProviderResponseEntry;
	}

	protected DDMDataProviderResponse doGetData(
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

		DDMDataProviderResponse ddmDataProviderResponse = _portalCache.get(
			cacheKey);

		if ((ddmDataProviderResponse != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmDataProviderResponse;
		}

		HttpResponse httpResponse = httpRequest.send();

		JSONArray jsonArray = _jsonFactory.createJSONArray(httpResponse.body());

		ddmDataProviderResponse = new DDMDataProviderResponse();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString(
				ddmRESTDataProviderSettings.key());
			String value = jsonObject.getString(
				ddmRESTDataProviderSettings.value());

			ddmDataProviderResponse.addDDMDataProviderResponseEntry(
				createDDMDataProviderResponseEntry(key, value));
		}

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(cacheKey, ddmDataProviderResponse);
		}

		return ddmDataProviderResponse;
	}

	protected String getCacheKey(HttpRequest httpRequest) {
		return httpRequest.url();
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMDataProviderResponse>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
	}

	private JSONFactory _jsonFactory;
	private PortalCache<String, DDMDataProviderResponse> _portalCache;

}