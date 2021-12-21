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

package com.liferay.dynamic.data.mapping.form.field.type.internal.util;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class NumericDDMFormFieldTypeUtilTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Test
	public void testGetInputMaskParametersDouble() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("inputMask", true);
		ddmFormField.setProperty(
			"numericInputMask",
			DDMFormValuesTestUtil.createLocalizedValue(
				JSONUtil.put(
					"append", "$"
				).put(
					"appendType", "prefix"
				).put(
					"decimalPlaces", 2
				).put(
					"symbols",
					JSONUtil.put(
						"decimalSymbol", ","
					).put(
						"thousandsSeparator", "\'"
					)
				).toString(),
				LocaleUtil.US));

		Map<String, Object> parameters =
			NumericDDMFormFieldTypeUtil.getParameters(
				"double", ddmFormField, new DDMFormFieldRenderingContext());

		Assert.assertEquals(parameters.toString(), 6, parameters.size());
		Assert.assertTrue(parameters.containsKey("append"));
		Assert.assertTrue(parameters.containsKey("appendType"));
		Assert.assertTrue(parameters.containsKey("decimalPlaces"));
		Assert.assertTrue(parameters.containsKey("inputMask"));
		Assert.assertTrue(parameters.containsKey("numericInputMask"));
		Assert.assertTrue(parameters.containsKey("symbols"));
	}

	@Test
	public void testGetInputMaskParametersInteger() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("inputMask", true);

		Map<String, Object> parameters =
			NumericDDMFormFieldTypeUtil.getParameters(
				"integer", ddmFormField, new DDMFormFieldRenderingContext());

		Assert.assertEquals(parameters.toString(), 2, parameters.size());
		Assert.assertTrue(parameters.containsKey("inputMask"));
		Assert.assertTrue(parameters.containsKey("inputMaskFormat"));
	}

	@Test
	public void testGetParametersDouble() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		Map<String, Object> parameters =
			NumericDDMFormFieldTypeUtil.getParameters(
				"double", new DDMFormField("field", "numeric"),
				ddmFormFieldRenderingContext);

		Assert.assertEquals(parameters.toString(), 2, parameters.size());
		Assert.assertEquals(
			HashMapBuilder.put(
				"en_US",
				HashMapBuilder.put(
					"decimalSymbol", "."
				).put(
					"thousandsSeparator", ","
				).build()
			).put(
				"pt_BR",
				HashMapBuilder.put(
					"decimalSymbol", ","
				).put(
					"thousandsSeparator", "."
				).build()
			).build(),
			parameters.get("localizedSymbols"));
		Assert.assertEquals(
			HashMapBuilder.put(
				"decimalSymbol", "."
			).put(
				"thousandsSeparator", ","
			).build(),
			parameters.get("symbols"));
	}

	@Test
	public void testGetParametersInteger() {
		Map<String, Object> parameters =
			NumericDDMFormFieldTypeUtil.getParameters(
				"integer", new DDMFormField("field", "numeric"),
				new DDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.isEmpty());
	}

}