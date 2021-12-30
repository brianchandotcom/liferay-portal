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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.internal.io.util.DDMFormFieldSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.serializer.type=json",
	service = DDMFormSerializer.class
)
public class DDMFormJSONSerializer implements DDMFormSerializer {

	@Override
	public DDMFormSerializerSerializeResponse serialize(
		DDMFormSerializerSerializeRequest ddmFormSerializerSerializeRequest) {

		DDMForm ddmForm = ddmFormSerializerSerializeRequest.getDDMForm();

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		addAvailableLanguageIds(jsonObject, ddmForm.getAvailableLocales());
		addDefaultLanguageId(jsonObject, ddmForm.getDefaultLocale());
		addRules(jsonObject, ddmForm.getDDMFormRules());
		_addSuccessPageSettings(
			jsonObject, ddmForm.getDDMFormSuccessPageSettings());

		if (Validator.isNotNull(ddmForm.getDefinitionSchemaVersion())) {
			jsonObject.put(
				"definitionSchemaVersion",
				ddmForm.getDefinitionSchemaVersion());
		}

		DDMFormFieldSerializerUtil.serialize(
			ddmForm.getDDMFormFields(), _ddmFormFieldTypeServicesTracker,
			_jsonFactory, jsonObject);

		DDMFormSerializerSerializeResponse.Builder builder =
			DDMFormSerializerSerializeResponse.Builder.newBuilder(
				jsonObject.toString());

		return builder.build();
	}

	protected void addAvailableLanguageIds(
		JSONObject jsonObject, Set<Locale> availableLocales) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			jsonArray.put(LocaleUtil.toLanguageId(availableLocale));
		}

		jsonObject.put("availableLanguageIds", jsonArray);
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addRules(
		JSONObject jsonObject, List<DDMFormRule> ddmFormRules) {

		if (ddmFormRules.isEmpty()) {
			return;
		}

		jsonObject.put(
			"rules", DDMFormRuleJSONSerializer.serialize(ddmFormRules));
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected JSONObject toJSONObject(
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"body", toJSONObject(ddmFormSuccessPageSettings.getBody())
		).put(
			"enabled", ddmFormSuccessPageSettings.isEnabled()
		).put(
			"title", toJSONObject(ddmFormSuccessPageSettings.getTitle())
		);

		return jsonObject;
	}

	protected JSONObject toJSONObject(LocalizedValue localizedValue) {
		if (localizedValue == null) {
			return _jsonFactory.createJSONObject();
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Map<Locale, String> values = localizedValue.getValues();

		if (values.isEmpty()) {
			return jsonObject;
		}

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				localizedValue.getString(availableLocale));
		}

		return jsonObject;
	}

	private void _addSuccessPageSettings(
		JSONObject jsonObject,
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		jsonObject.put("successPage", toJSONObject(ddmFormSuccessPageSettings));
	}

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private JSONFactory _jsonFactory;

}