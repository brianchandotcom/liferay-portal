/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
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
	property = "ddm.form.serializer.type=json",
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
			ddmForm.getDDMFormFields(), _ddmFormFieldTypeServicesRegistry,
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

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

	@Reference
	private JSONFactory _jsonFactory;

}