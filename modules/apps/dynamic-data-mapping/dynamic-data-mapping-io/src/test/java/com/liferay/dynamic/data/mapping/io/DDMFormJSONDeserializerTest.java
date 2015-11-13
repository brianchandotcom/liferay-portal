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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONDeserializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.portal.kernel.exception.PortalException;

import org.junit.Assert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONDeserializerTest
	extends BaseDDMFormDeserializerTestCase {

	@Override
	protected DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		return _ddmFormJSONDeserializer.deserialize(serializedDDMForm);
	}

	@Override
	protected String getDeserializerType() {
		return "json";
	}

	@Override
	protected String getTestFileExtension() {
		return ".json";
	}

	@Override
	protected void testBooleanDDMFormField(DDMFormField ddmFormField) {
		super.testBooleanDDMFormField(ddmFormField);

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		Assert.assertNotNull(ddmFormFieldValidation);
		Assert.assertEquals(
			"Boolean2282", ddmFormFieldValidation.getExpression());
		Assert.assertEquals(
			"You must check this box to continue.",
			ddmFormFieldValidation.getErrorMessage());
		Assert.assertEquals("true", ddmFormField.getVisibilityExpression());
	}

	@Override
	protected void testDecimalDDMFormField(DDMFormField ddmFormField) {
		super.testDecimalDDMFormField(ddmFormField);

		Assert.assertEquals("false", ddmFormField.getVisibilityExpression());
	}

	private final DDMFormJSONDeserializer _ddmFormJSONDeserializer =
		new DDMFormJSONDeserializerImpl();

}