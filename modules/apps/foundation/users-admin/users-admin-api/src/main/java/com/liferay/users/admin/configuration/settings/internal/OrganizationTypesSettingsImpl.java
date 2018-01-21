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

package com.liferay.users.admin.configuration.settings.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.internal.configuration.OrganizationsTypesConfiguration;
import com.liferay.users.admin.kernel.organization.types.OrganizationTypesSettings;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.users.admin.internal.configuration.OrganizationsTypesConfiguration",
	immediate = true, service = OrganizationTypesSettings.class
)
public class OrganizationTypesSettingsImpl
	implements OrganizationTypesSettings {

	@Override
	public String[] getChildrenTypes(String type) {
		JSONObject typeJSONObject = _jsonObject.getJSONObject(type);

		JSONArray jsonArray = typeJSONObject.getJSONArray("childrenTypes");

		return ArrayUtil.toStringArray(jsonArray);
	}

	@Override
	public String[] getTypes() {
		return _types;
	}

	@Override
	public boolean isCountryEnabled(String type) {
		JSONObject typeJSONObject = _jsonObject.getJSONObject(type);

		return typeJSONObject.getBoolean("countryEnabled");
	}

	@Override
	public boolean isCountryRequired(String type) {
		JSONObject typeJSONObject = _jsonObject.getJSONObject(type);

		return typeJSONObject.getBoolean("countryRequired");
	}

	@Override
	public boolean isRootable(String type) {
		JSONObject typeJSONObject = _jsonObject.getJSONObject(type);

		return typeJSONObject.getBoolean("rootable");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		OrganizationsTypesConfiguration typesConfiguration =
			ConfigurableUtil.createConfigurable(
				OrganizationsTypesConfiguration.class, properties);

		try {
			String json = typesConfiguration.json();

			json = StringUtil.replace(json, "\\,", ",");

			_jsonObject = _jsonFactory.createJSONObject(json);

			_types = ArrayUtil.toStringArray(_jsonObject.names());
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to parse the organization types configuration JSON",
				jsone);

			_jsonObject = _jsonFactory.getUnmodifiableJSONObject();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationTypesSettingsImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	private volatile JSONObject _jsonObject;
	private volatile String[] _types;

}