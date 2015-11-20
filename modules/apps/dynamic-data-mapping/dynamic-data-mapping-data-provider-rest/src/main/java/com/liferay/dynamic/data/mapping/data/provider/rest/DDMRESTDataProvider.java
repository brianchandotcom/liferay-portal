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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, property = "ddm.data.provider.type=rest")
public class DDMRESTDataProvider implements DDMDataProvider {

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

	protected List<KeyValuePair> doGetData(
			DDMDataProviderContext ddmDataProviderContext)
		throws PortalException {

		DDMRESTDataProviderSettings restSettings =
			ddmDataProviderContext.getSettingsInstance(
				DDMRESTDataProviderSettings.class);

		HttpRequest httpRequest = HttpRequest.get(restSettings.url());

		httpRequest.basicAuthentication(
			restSettings.username(), restSettings.password());
		httpRequest.query(ddmDataProviderContext.getParameters());

		HttpResponse httpResponse = httpRequest.send();

		JSONArray jsonArray = _jsonFactory.createJSONArray(httpResponse.body());

		List<KeyValuePair> results = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString(restSettings.key());
			String value = jsonObject.getString(restSettings.value());

			results.add(new KeyValuePair(key, value));
		}

		return results;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private volatile JSONFactory _jsonFactory;

}