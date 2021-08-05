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
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class InfoFormUtil {

	public static JSONObject getConfigurationJSONObject(
		InfoForm infoForm, Locale locale) {

		JSONArray defaultFieldSetFieldsJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONArray fieldSetsJSONArray = JSONUtil.put(
			JSONUtil.put("fields", defaultFieldSetFieldsJSONArray));

		if (infoForm == null) {
			return JSONUtil.put("fieldSets", fieldSetsJSONArray);
		}

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (infoFieldSetEntry instanceof InfoField) {
				InfoField<?> infoField = (InfoField<?>)infoFieldSetEntry;

				InfoFieldType infoFieldType = infoField.getInfoFieldType();

				if (!_isValidInfoFieldType(infoFieldType)) {
					continue;
				}

				defaultFieldSetFieldsJSONArray.put(
					JSONUtil.put(
						"dataType", _getDataType(infoFieldType)
					).put(
						"label", infoField.getLabel(locale)
					).put(
						"name", infoField.getName()
					).put(
						"type", _getType(infoFieldType)
					));
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				JSONArray fieldSetFieldsJSONArray =
					JSONFactoryUtil.createJSONArray();

				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				for (InfoField<?> infoField : infoFieldSet.getAllInfoFields()) {
					InfoFieldType infoFieldType = infoField.getInfoFieldType();

					if (!_isValidInfoFieldType(infoFieldType)) {
						continue;
					}

					fieldSetFieldsJSONArray.put(
						JSONUtil.put(
							"dataType", _getDataType(infoFieldType)
						).put(
							"label", infoField.getLabel(locale)
						).put(
							"name", infoField.getName()
						).put(
							"type", _getType(infoFieldType)
						));
				}

				if (fieldSetFieldsJSONArray.length() > 0) {
					fieldSetsJSONArray.put(
						JSONUtil.put(
							"configurationRole", infoFieldSet.getLabel(locale)
						).put(
							"fields", fieldSetFieldsJSONArray
						));
				}
			}
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

	private static String _getType(InfoFieldType infoFieldType) {
		if (infoFieldType instanceof BooleanInfoFieldType) {
			return "checkbox";
		}

		return "text";
	}

	private static boolean _isValidInfoFieldType(InfoFieldType infoFieldType) {
		if (infoFieldType instanceof TextInfoFieldType) {
			return true;
		}

		return false;
	}

}