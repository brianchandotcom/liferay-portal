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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class InfoFormUtil {

	public static JSONObject getConfigurationJSONObject(
		InfoForm infoForm, Locale locale) {

		if (infoForm == null) {
			return JSONUtil.put(
				"fieldSets",
				JSONUtil.put(
					JSONUtil.put("fields", JSONFactoryUtil.createJSONArray())));
		}

		JSONArray defaultFieldSetFieldsJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONArray fieldSetsJSONArray = JSONFactoryUtil.createJSONArray();

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (infoFieldSetEntry instanceof InfoField) {
				InfoField<?> infoField = (InfoField<?>)infoFieldSetEntry;

				InfoFieldType infoFieldType = infoField.getInfoFieldType();

				if (!_isValidInfoFieldType(infoFieldType)) {
					continue;
				}

				defaultFieldSetFieldsJSONArray.put(
					_getFieldSetFieldJSONObject(
						infoField, infoFieldType, locale));
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				JSONArray fieldSetFieldsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (InfoField<?> infoField : infoFieldSet.getAllInfoFields()) {
					InfoFieldType infoFieldType = infoField.getInfoFieldType();

					if (!_isValidInfoFieldType(infoFieldType)) {
						continue;
					}

					fieldSetFieldsJSONArray.put(
						_getFieldSetFieldJSONObject(
							infoField, infoFieldType, locale));
				}

				if (!JSONUtil.isEmpty(fieldSetFieldsJSONArray)) {
					fieldSetsJSONArray.put(
						JSONUtil.put(
							"fields", fieldSetFieldsJSONArray
						).put(
							"label",
							() -> {
								InfoLocalizedValue<String>
									labelInfoLocalizedValue =
										infoFieldSet.
											getLabelInfoLocalizedValue();

								if (labelInfoLocalizedValue == null) {
									return null;
								}

								return labelInfoLocalizedValue.getValue(locale);
							}
						).put(
							"name", infoFieldSet.getName()
						));
				}
			}
		}

		if (!JSONUtil.isEmpty(defaultFieldSetFieldsJSONArray)) {
			fieldSetsJSONArray.put(
				JSONUtil.put("fields", defaultFieldSetFieldsJSONArray));
		}

		return JSONUtil.put("fieldSets", fieldSetsJSONArray);
	}

	private static String _getDataType(InfoFieldType infoFieldType) {
		if (infoFieldType instanceof BooleanInfoFieldType) {
			return "object";
		}
		else if (infoFieldType instanceof NumberInfoFieldType) {
			return "double";
		}

		return "string";
	}

	private static JSONObject _getFieldSetFieldJSONObject(
		InfoField infoField, InfoFieldType infoFieldType, Locale locale) {

		JSONObject jsonObject = JSONUtil.put(
			"dataType", _getDataType(infoFieldType)
		).put(
			"label", infoField.getLabel(locale)
		).put(
			"name", infoField.getName()
		).put(
			"type", _getType(infoFieldType)
		);

		if (infoFieldType instanceof SelectInfoFieldType) {
			List<SelectInfoFieldType.Option> options =
				(List<SelectInfoFieldType.Option>)infoField.getAttribute(
					SelectInfoFieldType.OPTIONS);

			if (options == null) {
				options = Collections.emptyList();
			}

			try {
				jsonObject.put(
					"typeOptions",
					JSONUtil.put(
						"multiSelect",
						() -> GetterUtil.getBoolean(
							infoField.getAttribute(
								SelectInfoFieldType.MULTIPLE))
					).put(
						"validValues",
						JSONUtil.toJSONArray(
							options,
							option -> JSONUtil.put(
								"label", String.valueOf(option.getLabel(locale))
							).put(
								"value", String.valueOf(option.getValue())
							))
					));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return jsonObject;
	}

	private static String _getType(InfoFieldType infoFieldType) {
		if (infoFieldType instanceof BooleanInfoFieldType) {
			return "checkbox";
		}
		else if (infoFieldType instanceof SelectInfoFieldType) {
			return "select";
		}

		return "text";
	}

	private static boolean _isValidInfoFieldType(InfoFieldType infoFieldType) {
		if (infoFieldType instanceof SelectInfoFieldType ||
			infoFieldType instanceof TextInfoFieldType) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(InfoFormUtil.class);

}