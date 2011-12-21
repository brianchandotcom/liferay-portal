/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.Map;

/**
 * @author Bruno Basto
 */
public class StringFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(ThemeDisplay themeDisplay, Field field)
		throws Exception {

		String value = String.valueOf(field.getValue());

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		DDMStructure ddmStructure = field.getDDMStructure();

		String fieldType = ddmStructure.getFieldType(field.getName());

		if (!fieldType.equals("radio") && !fieldType.equals("select")) {
			return value;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray(value);

		int length = valuesJSONArray.length();

		StringBundler sb = new StringBundler(length * 2);

		for (int i = 0; i < length; i++) {
			Map<String, String> fields = ddmStructure.getFields(
				field.getName(), FieldConstants.VALUE,
				valuesJSONArray.getString(i));

			if (fields == null) {
				continue;
			}

			sb.append(fields.get(FieldConstants.LABEL));

			if ((i + 1) < length) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

}