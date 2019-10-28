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

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=fieldset",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		FieldSetDDMFormFieldTemplateContextContributor.class
	}
)
public class FieldSetDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, List<Object>> nestedFieldsMap =
			(Map<String, List<Object>>)ddmFormFieldRenderingContext.getProperty(
				"nestedFields");

		String[] nestedFieldNames = getNestedFieldNames(
			GetterUtil.getString(ddmFormField.getProperty("nestedFieldNames")),
			nestedFieldsMap.keySet());

		List<Object> nestedFields = getNestedFields(
			nestedFieldsMap, nestedFieldNames);

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("nestedFields", nestedFields);

		parameters.put(
			"columnSize",
			getColumnSize(
				countVisibleNestedFields(nestedFields),
				GetterUtil.getString(
					ddmFormField.getProperty("orientation"), "horizontal")));

		LocalizedValue label = ddmFormField.getLabel();

		if (label != null) {
			parameters.put(
				"label",
				label.getString(ddmFormFieldRenderingContext.getLocale()));

			parameters.put("showLabel", true);
		}

		return parameters;
	}

	protected int countVisibleNestedFields(List<Object> nestedFields) {
		Stream<Object> stream = nestedFields.stream();

		return GetterUtil.getInteger(
			stream.filter(
				this::_isNestedFieldVisible
			).count());
	}

	protected int getColumnSize(int nestedFieldsSize, String orientation) {
		if (Objects.equals(orientation, "vertical")) {
			return DDMFormLayoutColumn.FULL;
		}

		if (nestedFieldsSize == 0) {
			return 0;
		}

		return 12 / nestedFieldsSize;
	}

	protected String[] getNestedFieldNames(
		String nestedFieldNames, Set<String> defaultNestedFieldNames) {

		if (Validator.isNotNull(nestedFieldNames)) {
			return StringUtil.split(nestedFieldNames);
		}

		return defaultNestedFieldNames.toArray(new String[0]);
	}

	protected List<Object> getNestedFields(
		Map<String, List<Object>> nestedFieldsMap, String[] nestedFieldNames) {

		List<Object> nestedFields = new ArrayList<>();

		for (String nestedFieldName : nestedFieldNames) {
			nestedFields.addAll(nestedFieldsMap.get(nestedFieldName));
		}

		return nestedFields;
	}

	private boolean _isNestedFieldVisible(Object nestedFieldContext) {
		return MapUtil.getBoolean(
			(Map<String, ?>)nestedFieldContext, "visible", true);
	}

}