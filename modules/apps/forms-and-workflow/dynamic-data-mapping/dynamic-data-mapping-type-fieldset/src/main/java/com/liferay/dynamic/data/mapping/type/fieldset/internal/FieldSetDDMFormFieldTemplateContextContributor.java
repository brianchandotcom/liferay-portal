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

package com.liferay.dynamic.data.mapping.type.fieldset.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=fieldset",
	service = {
		FieldSetDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class FieldSetDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		List<Object> nestedFields =
			(List<Object>)ddmFormFieldRenderingContext.getProperty(
				"nestedFields");

		String orientation = GetterUtil.getString(
			ddmFormField.getProperty("orientation"), "horizontal");

		int columnSize = getColumnSize(nestedFields, orientation);

		parameters.put("columnSize", columnSize);

		LocalizedValue label = ddmFormField.getLabel();

		if (label != null) {
			parameters.put(
				"label",
				label.getString(ddmFormFieldRenderingContext.getLocale()));

			parameters.put("showLabel", true);
		}

		return parameters;
	}

	protected int getColumnSize(List<Object> nestedFields, String orientation) {
		if (Objects.equals(orientation, "vertical")) {
			return DDMFormLayoutColumn.FULL;
		}

		if (nestedFields.isEmpty()) {
			return 0;
		}

		return 12 / nestedFields.size();
	}

}