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

import com.liferay.dynamic.data.mapping.io.impl.DDMFormJSONDeserializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Before;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({LocaleUtil.class})
public class DDMFormJSONDeserializerTest
	extends BaseDDMFormDeserializerTestCase {

	@Before
	public void setUp() {
		setUpDDMFormFieldTypeRegistryUtil();
		setUpDDMFormJSONDeserializerUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpJSONFactoryUtil();
	}

	@Override
	protected DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		return DDMFormJSONDeserializerUtil.deserialize(serializedDDMForm);
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
	protected void setUpDDMFormJSONDeserializerUtil() {
		DDMFormJSONDeserializerUtil ddmFormJSONDeserializerUtil =
			new DDMFormJSONDeserializerUtil();

		ddmFormJSONDeserializerUtil.setDDMFormJSONDeserializer(
			new DDMFormJSONDeserializerImpl());
	}

	@Override
	protected void testBooleanDDMFormField(DDMFormField ddmFormField) {
		super.testBooleanDDMFormField(ddmFormField);

		Assert.assertEquals("true", ddmFormField.getVisibilityExpression());
	}

	@Override
	protected void testDecimalDDMFormField(DDMFormField ddmFormField) {
		super.testDecimalDDMFormField(ddmFormField);

		Assert.assertEquals("false", ddmFormField.getVisibilityExpression());
	}

}