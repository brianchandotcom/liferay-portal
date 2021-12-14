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

package com.liferay.fragment.util.configuration;

import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface FragmentEntryConfigurationParser {

	public JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration);

	public Object getConfigurationFieldValue(
		String editableValues, String fieldName,
		FragmentConfigurationFieldDataType fragmentConfigurationFieldDataType);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #getConfigurationJSONObject(String, String, Locale)}
	 */
	@Deprecated
	public JSONObject getConfigurationJSONObject(
			String configuration, String editableValues)
		throws JSONException;

	public JSONObject getConfigurationJSONObject(
			String configuration, String editableValues, Locale locale)
		throws JSONException;

	public Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration,
		long[] segmentsEntryIds);

	public Object getFieldValue(
		FragmentConfigurationField fragmentConfigurationField, Locale locale,
		String value);

	public Object getFieldValue(
		String configuration, String editableValues, Locale locale,
		String name);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #getFieldValue(String, String, Locale, String)}
	 */
	@Deprecated
	public Object getFieldValue(
		String configuration, String editableValues, String name);

	public List<FragmentConfigurationField> getFragmentConfigurationFields(
		String configuration);

	public String translateConfiguration(
		JSONObject jsonObject, ResourceBundle resourceBundle);

}