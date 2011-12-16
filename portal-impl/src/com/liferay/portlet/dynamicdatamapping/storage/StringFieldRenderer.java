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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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

		DDMStructure ddmStructure = field.getDDMStructure();

		String fieldType = ddmStructure.getFieldType(field.getName());

		if (!fieldType.equals("radio") && !fieldType.equals("select")) {
			return value;
		}

		String[] values = StringUtil.split(value);

		StringBundler sb = new StringBundler(values.length * 2);

		for (int i = 0; i < values.length; i++) {
			Map<String, String> fields = ddmStructure.getFields(
				field.getName(), FieldConstants.VALUE, values[i]);

			if (fields == null) {
				continue;
			}

			sb.append(fields.get(FieldConstants.LABEL));

			if ((i + 1) < values.length) {
				sb.append(", ");
			}
		}

		value = sb.toString();

		return value;
	}

}