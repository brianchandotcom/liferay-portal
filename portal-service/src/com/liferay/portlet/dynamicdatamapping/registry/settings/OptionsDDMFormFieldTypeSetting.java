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

package com.liferay.portlet.dynamicdatamapping.registry.settings;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingEditor;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingJSONConverter;

import java.util.Iterator;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class OptionsDDMFormFieldTypeSetting implements DDMFormFieldTypeSetting {

	@Override
	public DDMFormFieldTypeSettingEditor getDDMFormFieldTypeSettingEditor() {
		return new DDMFormFieldTypeSettingEditor() {

			@Override
			public String getEditorType() {
				return "Options";
			}

			@Override
			public JSONObject getOptions() {
				JSONObject options = JSONFactoryUtil.createJSONObject();

				options.put("label", "options");

				return options;
			}

		};
	}

	@Override
	public DDMFormFieldTypeSettingJSONConverter<DDMFormFieldOptions, JSONArray>
		getDDMFormFieldTypeSettingJSONConverter() {

		return new OptionsDDMFormFieldTypeSettingJSONConverter();
	}

	@Override
	public String getName() {
		return "options";
	}

	@Override
	public boolean isAdvanced() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	private static class OptionsDDMFormFieldTypeSettingJSONConverter
		implements DDMFormFieldTypeSettingJSONConverter
			<DDMFormFieldOptions, JSONArray> {

		@Override
		public DDMFormFieldOptions fromJSON(String serializedSetting) {
			try {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
					serializedSetting);

				return getDDMFormFieldOptions(jsonArray);
			}
			catch (JSONException jsone) {
				_log.error(jsone);

				return null;
			}
		}

		@Override
		public JSONArray toJSON(Object setting) {
			JSONArray jsonArray = toJSONArray((DDMFormFieldOptions)setting);

			return jsonArray;
		}

		protected void addOptionValueLabels(
			JSONObject jsonObject, DDMFormFieldOptions ddmFormFieldOptions,
			String optionValue) {

			Iterator<String> itr = jsonObject.keys();

			while (itr.hasNext()) {
				String languageId = itr.next();

				ddmFormFieldOptions.addOptionLabel(
					optionValue, LocaleUtil.fromLanguageId(languageId),
					jsonObject.getString(languageId));
			}
		}

		protected DDMFormFieldOptions getDDMFormFieldOptions(
			JSONArray jsonArray) {

			DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				String value = jsonObject.getString("value");

				ddmFormFieldOptions.addOption(value);

				addOptionValueLabels(
					jsonObject.getJSONObject("label"), ddmFormFieldOptions,
					value);
			}

			return ddmFormFieldOptions;
		}

		protected JSONArray toJSONArray(
			DDMFormFieldOptions ddmFormFieldOptions) {

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("value", optionValue);
				jsonObject.put(
					"label",
					toJSONObject(
						ddmFormFieldOptions.getOptionLabels(optionValue)));

				jsonArray.put(jsonObject);
			}

			return jsonArray;
		}

		protected JSONObject toJSONObject(LocalizedValue localizedValue) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (Locale availableLocale :
					localizedValue.getAvailableLocales()) {

				jsonObject.put(
					LocaleUtil.toLanguageId(availableLocale),
					localizedValue.getString(availableLocale));
			}

			return jsonObject;
		}

		private static final Log _log = LogFactoryUtil.getLog(
			OptionsDDMFormFieldTypeSettingJSONConverter.class);

	}

}