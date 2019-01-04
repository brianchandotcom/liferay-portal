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
public class SelectFormApioTestBundleActivator
	extends BaseFormApioTestBundleActivator {

	public static final String MULTIPLE_SELECT_FIELD_NAME =
		"MyMultipleSelectField";

	public static final String SELECT_FIELD_NAME = "MySelectField";

	public static final String SITE_NAME =
		SelectFormApioTestBundleActivator.class.getSimpleName() + "Site";

	@Override
	protected Class<?> getFormDefinitionClass() {
		return FormWithSelectFields.class;
	}

	@Override
	protected String getSiteName() {
		return SITE_NAME;
	}

	@DDMForm
	private interface FormWithSelectFields {

		@DDMFormField(
			label = "My Multiple Select Field",
			name = MULTIPLE_SELECT_FIELD_NAME,
			optionLabels = {"Option 1", "Option 2", "Option 3"},
			optionValues = {"Option1", "Option2", "Option3"},
			properties = "multiple=true", type = "select"
		)
		public String multipleSelectValue();

		@DDMFormField(
			label = "My Select Field", name = SELECT_FIELD_NAME,
			optionLabels = {"Option 1", "Option 2", "Option 3"},
			optionValues = {"Option1", "Option2", "Option3"}, type = "select"
		)
		public String selectValue();

	}

}