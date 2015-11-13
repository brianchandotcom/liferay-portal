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

package com.liferay.dynamic.data.mapping.render;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.Accessor;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public abstract class ValueAccessor
	implements Accessor<DDMFormFieldValue, String> {

	public ValueAccessor(Locale locale) {
		this.locale = locale;
	}

	@Override
	public Class<String> getAttributeClass() {
		return String.class;
	}

	@Override
	public Class<DDMFormFieldValue> getTypeClass() {
		return DDMFormFieldValue.class;
	}

	protected DDMForm getDDMForm(DDMFormFieldValue ddmFormFieldValue) {
		DDMFormValues ddmFormValues = ddmFormFieldValue.getDDMFormValues();

		return ddmFormValues.getDDMForm();
	}

	protected DDMFormField getDDMFormField(
		DDMFormFieldValue ddmFormFieldValue) {

		DDMForm ddmForm = getDDMForm(ddmFormFieldValue);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		return ddmFormFieldsMap.get(ddmFormFieldValue.getName());
	}

	protected Locale locale;

}