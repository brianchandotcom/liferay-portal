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

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.GRID,
	service = DDMFormFieldValueRenderer.class
)
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

		Set<String> rowOptions = rows.getOptionsValues();

		for (String rowOption : rowOptions) {
			if (valuesJSONObject.has(rowOption)) {
				String columnOption = valuesJSONObject.getString(rowOption);

				LocalizedValue rowLabel = rows.getOptionLabels(rowOption);
				LocalizedValue columnLabel = columns.getOptionLabels(
					columnOption);

				sb.append(getLabel(rowLabel, rowOption, locale));

				sb.append(StringPool.COLON);
				sb.append(StringPool.SPACE);

				sb.append(getLabel(columnLabel, columnOption, locale));

				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormFieldValue ddmFormFieldValue, String optionType) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return (DDMFormFieldOptions)ddmFormField.getProperty(optionType);
	}

	protected String getLabel(
		LocalizedValue label, String defaultLabel, Locale locale) {

		if (label != null) {
			return HtmlUtil.escape(label.getString(locale));
		}

		return defaultLabel;
	}

	@Reference
	protected GridDDMFormFieldValueAccessor gridDDMFormFieldValueAccessor;

}