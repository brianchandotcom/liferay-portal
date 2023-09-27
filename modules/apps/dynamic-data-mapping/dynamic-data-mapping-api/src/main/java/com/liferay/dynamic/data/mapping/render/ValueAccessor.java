/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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