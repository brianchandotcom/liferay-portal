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

package com.liferay.dynamic.data.mapping.type.numeric.internal;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class NumericDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Test
	public void testGetDataType1() {
		String fieldName = "field";

		DDMFormField ddmFormField = new DDMFormField(fieldName, "numeric");

		ddmFormField.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"ddmFormFieldEvaluationResult", null);

		Assert.assertEquals(
			"double",
			_numericDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataType2() {
		String fieldName = "field";
		String fieldInstance = "field_instance";

		DDMFormField ddmFormField = new DDMFormField(fieldName, "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(fieldName, fieldInstance);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"ddmFormFieldEvaluationResult", ddmFormFieldEvaluationResult);

		Assert.assertEquals(
			"integer",
			_numericDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataType3() {
		String fieldName = "field";
		String fieldInstance = "field_instance";

		DDMFormField ddmFormField = new DDMFormField(fieldName, "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(fieldName, fieldInstance);

		ddmFormFieldEvaluationResult.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"ddmFormFieldEvaluationResult", ddmFormFieldEvaluationResult);

		Assert.assertEquals(
			"double",
			_numericDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	private final NumericDDMFormFieldTemplateContextContributor
		_numericDDMFormFieldTemplateContextContributor =
			new NumericDDMFormFieldTemplateContextContributor();

}