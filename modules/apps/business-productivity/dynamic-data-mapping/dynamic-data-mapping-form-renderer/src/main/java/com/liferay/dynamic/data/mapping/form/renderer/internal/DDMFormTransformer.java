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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Basto
 */
public class DDMFormTransformer {

	public DDMFormTransformer(
		DDMForm ddmForm, Map<String, String> renderedDDMFormFieldsMap) {

		_ddmForm = ddmForm;
		_renderedDDMFormFieldsMap = renderedDDMFormFieldsMap;
	}

	public List<String> getFields() {
		List<String> fields = new ArrayList<>();

		for (DDMFormField ddmFormField : _ddmForm.getDDMFormFields()) {
			fields.add(_renderedDDMFormFieldsMap.get(ddmFormField.getName()));
		}

		return fields;
	}

	private final DDMForm _ddmForm;
	private final Map<String, String> _renderedDDMFormFieldsMap;

}