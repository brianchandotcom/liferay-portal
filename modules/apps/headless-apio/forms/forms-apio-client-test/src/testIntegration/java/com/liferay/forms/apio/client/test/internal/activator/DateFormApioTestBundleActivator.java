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
public class DateFormApioTestBundleActivator
	extends BaseFormApioTestBundleActivator {

	public static final String DATE_FIELD_NAME = "MyDateField";

	public static final String SITE_NAME =
		DateFormApioTestBundleActivator.class.getSimpleName() + "Site";

	@Override
	protected Class<?> getFormDefinitionClass() {
		return FormWithDateField.class;
	}

	@Override
	protected String getSiteName() {
		return SITE_NAME;
	}

	@DDMForm
	private interface FormWithDateField {

		@DDMFormField(
			label = "My Date Field", name = DATE_FIELD_NAME, type = "date"
		)
		public String dateValue();

	}

}