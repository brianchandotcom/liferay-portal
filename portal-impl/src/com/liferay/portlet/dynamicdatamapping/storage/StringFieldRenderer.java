/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;

import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Basto
 */
public class StringFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		String value = String.valueOf(field.getValue());

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		DDMStructure ddmStructure = field.getDDMStructure();

		String fieldType = ddmStructure.getFieldType(field.getName());

		if (!fieldType.equals(DDMImpl.TYPE_RADIO) &&
			!fieldType.equals(DDMImpl.TYPE_SELECT)) {

			return value;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray(value);

		StringBundler sb = new StringBundler(valuesJSONArray.length() * 2);

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			Map<String, String> fieldsMap = ddmStructure.getFields(
				field.getName(), FieldConstants.VALUE,
				valuesJSONArray.getString(i), LocaleUtil.toLanguageId(locale));

			if (fieldsMap == null) {
				continue;
			}

			sb.append(fieldsMap.get(FieldConstants.LABEL));

			if ((i + 1) < valuesJSONArray.length()) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

}