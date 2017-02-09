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

package com.liferay.dynamic.data.mapping.type.grid.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Iterator;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(immediate = true, property = {"ddm.form.field.type.name=grid"})
public class GridDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject valuesJSONObject = gridDDMFormFieldValueAccessor.getValue(
			ddmFormFieldValue, locale);

		if (valuesJSONObject.length() == 0) {
			return StringPool.BLANK;
		}

		DDMFormFieldOptions rows = getDDMFormFieldOptions(
			ddmFormFieldValue, "rows");
		DDMFormFieldOptions columns = getDDMFormFieldOptions(
			ddmFormFieldValue, "columns");

		StringBundler sb = new StringBundler(valuesJSONObject.length() * 5);

		Iterator<String> values = valuesJSONObject.keys();

		while (values.hasNext()) {
			String value = values.next();

			LocalizedValue rowLabel = rows.getOptionLabels(value);
			LocalizedValue columnLabel = columns.getOptionLabels(
				valuesJSONObject.getString(value));

			sb.append(rowLabel != null ? rowLabel.getString(locale) : value);

			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);

			sb.append(
				columnLabel != null ? columnLabel.getString(locale) : value);
			sb.append("</br>");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormFieldValue ddmFormFieldValue, String optionType) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return (DDMFormFieldOptions)ddmFormField.getProperty(optionType);
	}

	@Reference
	protected GridDDMFormFieldValueAccessor gridDDMFormFieldValueAccessor;

}