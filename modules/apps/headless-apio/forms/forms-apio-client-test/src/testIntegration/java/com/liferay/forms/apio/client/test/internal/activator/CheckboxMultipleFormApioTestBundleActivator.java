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

/**
 * @author Marcelo Mello
 */
public class CheckboxMultipleFormApioTestBundleActivator
	extends BaseFormApioTestBundleActivator {

	public static final String CHECKBOX_MULTIPLE_FIELD_NAME =
		"MyCheckboxMultipleField";

	public static final String SITE_NAME =
		CheckboxMultipleFormApioTestBundleActivator.class.getSimpleName() +
			"Site";

	public static final String SWITCHER_FIELD_NAME = "MySwitcherField";

	@Override
	protected Class<?> getFormDefinitionClass() {
		return FormWithCheckboxMultipleField.class;
	}

	@Override
	protected String getSiteName() {
		return SITE_NAME;
	}

	@DDMForm
	private interface FormWithCheckboxMultipleField {

		@DDMFormField(
			label = "My Checkbox Multiple Field",
			name = CHECKBOX_MULTIPLE_FIELD_NAME,
			optionLabels = {"Option 1", "Option 2", "Option 3"},
			optionValues = {"Option1", "Option2", "Option3"},
			properties = "inline=true", type = "checkbox_multiple"
		)
		public String checkboxMultipleValue();

		@DDMFormField(
			label = "My Switcher Field", name = SWITCHER_FIELD_NAME,
			optionLabels = {"Option 1", "Option 2", "Option 3"},
			optionValues = {"Option1", "Option2", "Option3"},
			properties = "showAsSwitcher=true", type = "checkbox_multiple"
		)
		public String switcherValue();

	}

}