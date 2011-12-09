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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
	protected String doRender(ThemeDisplay themeDisplay, Field field) {
		String value = String.valueOf(field.getValue());

		try {
			DDMStructure ddmStructure = field.getDDMStructure();

			String fieldName = field.getName();
			String fieldType = ddmStructure.getFieldType(fieldName);

			if (fieldType.equals("radio") || fieldType.equals("select")) {
				String[] values = StringUtil.split(value);

				StringBundler sb = new StringBundler(values.length * 2);

				for (int i = 0; i < values.length; i++) {
					Map<String, String> option = ddmStructure.getFields(
						fieldName, FieldConstants.VALUE, values[i]);

					if (option != null) {
						sb.append(option.get(FieldConstants.LABEL));

						if (i < (values.length - 1)) {
							sb.append(", ");
						}
					}
				}

				value = sb.toString();
			}
		} catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable render field value: " + value, e);
			}
		}

		return value;
	}

	private static Log _log = LogFactoryUtil.getLog(StringFieldRenderer.class);

}