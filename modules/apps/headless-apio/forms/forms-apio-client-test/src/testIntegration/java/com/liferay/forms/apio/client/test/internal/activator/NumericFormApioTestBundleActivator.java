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
 * @author Paulo Cruz
 */
public class NumericFormApioTestBundleActivator
	extends BaseFormApioTestBundleActivator {

	public static final String DOUBLE_FIELD_NAME = "MyDoubleField";

	public static final String INTEGER_FIELD_NAME = "MyIntegerField";

	public static final String SITE_NAME =
		NumericFormApioTestBundleActivator.class.getSimpleName() + "Site";

	@Override
	protected Class<?> getFormDefinitionClass() {
		return FormWithNumericFields.class;
	}

	@Override
	protected String getSiteName() {
		return SITE_NAME;
	}

	@DDMForm
	private interface FormWithNumericFields {

		@DDMFormField(
			label = "My Double Field", name = DOUBLE_FIELD_NAME, type = "double"
		)
		public double doubleValue();

		@DDMFormField(
			label = "My Integer Field", name = INTEGER_FIELD_NAME,
			type = "integer"
		)
		public int integerValue();

	}

}