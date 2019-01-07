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

package com.liferay.forms.apio.client.test.internal.activator;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.petra.string.StringPool;

import java.util.Map;

/**
 * @author Marcelo Mello
 */
public class GridFormApioTestBundleActivator
	extends BaseFormApioTestBundleActivator {

	public static final String GRID_FIELD_NAME = "MyGridField";

	public static final String SITE_NAME =
		GridFormApioTestBundleActivator.class.getSimpleName() + "Site";

	@Override
	protected com.liferay.dynamic.data.mapping.model.DDMForm createDDMForm() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			super.createDDMForm();

		_setGridOptions(ddmForm);

		return ddmForm;
	}

	@Override
	protected Class<?> getFormDefinitionClass() {
		return FormWithGridField.class;
	}

	@Override
	protected String getSiteName() {
		return SITE_NAME;
	}

	private DDMFormFieldOptions _createDDMFormFieldOptions(
		String optionPrefix, int optionsSize) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 1; i <= optionsSize; i++) {
			ddmFormFieldOptions.addOptionLabel(
				optionPrefix + i, null, optionPrefix + StringPool.SPACE + i);
		}

		return ddmFormFieldOptions;
	}

	private void _setGridOptions(
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm) {

		Map<String, com.liferay.dynamic.data.mapping.model.DDMFormField>
			ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(false);

		com.liferay.dynamic.data.mapping.model.DDMFormField ddmFormField =
			ddmFormFieldsMap.get(GRID_FIELD_NAME);

		ddmFormField.setProperty(
			"columns", _createDDMFormFieldOptions("Column", 3));

		ddmFormField.setProperty("rows", _createDDMFormFieldOptions("Row", 3));
	}

	@DDMForm
	private interface FormWithGridField {

		@DDMFormField(
			label = "My Grid Field", name = GRID_FIELD_NAME, type = "grid"
		)
		public String gridValue();

	}

}