/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.context;

import com.liferay.dynamic.data.mapping.model.DDMForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DDMFormContextDeserializerRequest {

	public static DDMFormContextDeserializerRequest with(
		DDMForm ddmForm, String serializedFormContext) {

		DDMFormContextDeserializerRequest ddmFormContextDeserializerRequest =
			with(serializedFormContext);

		ddmFormContextDeserializerRequest.setDDMForm(ddmForm);

		return ddmFormContextDeserializerRequest;
	}

	public static DDMFormContextDeserializerRequest with(
		String serializedFormContext) {

		DDMFormContextDeserializerRequest ddmFormContextDeserializerRequest =
			new DDMFormContextDeserializerRequest();

		ddmFormContextDeserializerRequest.setSerializedFormContex(
			serializedFormContext);

		return ddmFormContextDeserializerRequest;
	}

	public void addProperty(String key, Object value) {
		_properties.put(key, value);
	}

	public DDMForm getDDMForm() {
		return getProperty("ddmForm");
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public String getSerializedFormContext() {
		return getProperty("serializedFormContext");
	}

	public void setDDMForm(DDMForm ddmForm) {
		addProperty("ddmForm", ddmForm);
	}

	public void setSerializedFormContex(String serializedFormContext) {
		addProperty("serializedFormContext", serializedFormContext);
	}

	private DDMFormContextDeserializerRequest() {
	}

	private final Map<String, Object> _properties = new HashMap<>();

}