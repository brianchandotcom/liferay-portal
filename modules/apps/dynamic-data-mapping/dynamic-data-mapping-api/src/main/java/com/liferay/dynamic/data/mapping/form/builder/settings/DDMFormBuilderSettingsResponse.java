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

package com.liferay.dynamic.data.mapping.form.builder.settings;

import com.liferay.portal.kernel.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DDMFormBuilderSettingsResponse {

	public void addSetting(String key, Object value) {
		_settings.put(key, value);
	}

	public String getDataProviderInstanceParameterSettingsURL() {
		return getSetting("dataProviderInstanceParameterSettingsURL");
	}

	public String getDataProviderInstancesURL() {
		return getSetting("dataProviderInstancesURL");
	}

	public String getFieldSetDefinitionURL() {
		return getSetting("fieldSetDefinitionURL");
	}

	public JSONArray getFieldSets() {
		return getSetting("fieldSets");
	}

	public String getFieldSettingsDDMFormContextURL() {
		return getSetting("fieldSettingsDDMFormContextURL");
	}

	public String getFormContextProviderURL() {
		return getSetting("formContextProviderURL");
	}

	public String getFunctionsMetadata() {
		return getSetting("functionsMetadata");
	}

	public String getFunctionsURL() {
		return getSetting("functionsURL");
	}

	public String getRolesURL() {
		return getSetting("rolesURL");
	}

	public String getSerializedDDMFormRules() {
		return getSetting("serializedDDMFormRules");
	}

	public <T> T getSetting(String name) {
		return (T)_settings.get(name);
	}

	public void setDataProviderInstanceParameterSettingsURL(
		String dataProviderInstanceParameterSettingsURL) {

		addSetting(
			"dataProviderInstanceParameterSettingsURL",
			dataProviderInstanceParameterSettingsURL);
	}

	public void setDataProviderInstancesURL(String dataProviderInstancesURL) {
		addSetting("dataProviderInstancesURL", dataProviderInstancesURL);
	}

	public void setFieldSetDefinitionURL(String fieldSetDefinitionURL) {
		addSetting("fieldSetDefinitionURL", fieldSetDefinitionURL);
	}

	public void setFieldSets(JSONArray fieldSets) {
		addSetting("fieldSets", fieldSets);
	}

	public void setFieldSettingsDDMFormContextURL(
		String fieldSettingsDDMFormContextURL) {

		addSetting(
			"fieldSettingsDDMFormContextURL", fieldSettingsDDMFormContextURL);
	}

	public void setFormContextProviderURL(String formContextProviderURL) {
		addSetting("formContextProviderURL", formContextProviderURL);
	}

	public void setFunctionsMetadata(String functionsMetadata) {
		addSetting("functionsMetadata", functionsMetadata);
	}

	public void setFunctionsURL(String functionsURL) {
		addSetting("functionsURL", functionsURL);
	}

	public void setRolesURL(String rolesURL) {
		addSetting("rolesURL", rolesURL);
	}

	public void setSerializedDDMFormRules(String serializedDDMFormRules) {
		addSetting("serializedDDMFormRules", serializedDDMFormRules);
	}

	private final Map<String, Object> _settings = new HashMap<>();

}