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

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.util.HtmlImpl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class NumericDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpHtmlUtil();
		_setUpJSONFactory();
		_setUpJSONFactoryUtil();
	}

	@Test
	public void testGetConfirmationFieldProperties() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty(
			"confirmationErrorMessage",
			DDMFormValuesTestUtil.createLocalizedValue(
				"The information does not match", _locale));
		ddmFormField.setProperty(
			"confirmationLabel",
			DDMFormValuesTestUtil.createLocalizedValue(
				"Confirm Field", _locale));
		ddmFormField.setProperty("requireConfirmation", true);

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(
			"The information does not match",
			parameters.get("confirmationErrorMessage"));
		Assert.assertEquals(
			"Confirm Field", parameters.get("confirmationLabel"));
		Assert.assertTrue((boolean)parameters.get("requireConfirmation"));
	}

	@Test
	public void testGetDataTypeChanged() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"dataType", "double"
			).build());

		Assert.assertEquals(
			"double",
			_numericDDMFormFieldTemplateContextContributor.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, "dataType"));
	}

	@Test
	public void testGetDataTypeDouble() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty("changedProperties", null);

		Assert.assertEquals(
			"double",
			_numericDDMFormFieldTemplateContextContributor.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, "dataType"));
	}

	@Test
	public void testGetDataTypeInteger() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", new HashMap<String, Object>());

		Assert.assertEquals(
			"integer",
			_numericDDMFormFieldTemplateContextContributor.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext, "dataType"));
	}

	@Test
	public void testGetInputMaskChangedProperties() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			_createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"inputMask", false
			).put(
				"inputMaskFormat",
				DDMFormValuesTestUtil.createLocalizedValue("(999)", _locale)
			).build());

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithInputMask(),
				ddmFormFieldRenderingContext);

		Assert.assertFalse((boolean)parameters.get("inputMask"));
		Assert.assertEquals("(999)", parameters.get("inputMaskFormat"));
	}

	@Test
	public void testGetInputMaskProperties() {
		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithInputMask(),
				_createDDMFormFieldRenderingContext());

		Assert.assertTrue((boolean)parameters.get("inputMask"));
		Assert.assertEquals(
			"(999) 0999-9999", parameters.get("inputMaskFormat"));
		Assert.assertEquals(
			HashMapBuilder.put(
				"decimalSymbol", "."
			).put(
				"thousandsSeparator", ","
			).build(),
			parameters.get("symbols"));
	}

	@Test
	public void testGetNumericInputMaskChangedProperties() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			_createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"numericInputMask",
				DDMFormValuesTestUtil.createLocalizedValue(
					JSONUtil.put(
						"append", "%"
					).put(
						"appendType", "suffix"
					).put(
						"decimalPlaces", 2
					).put(
						"symbols",
						JSONUtil.put(
							"decimalSymbol", "."
						).put(
							"thousandsSeparator", " "
						)
					).toString(),
					_locale)
			).build());

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithNumericInputMask(),
				ddmFormFieldRenderingContext);

		Assert.assertEquals("%", parameters.get("append"));
		Assert.assertEquals("suffix", parameters.get("appendType"));
		Assert.assertEquals(2, parameters.get("decimalPlaces"));
		Assert.assertEquals(
			JSONUtil.put(
				"decimalSymbol", "."
			).put(
				"thousandsSeparator", " "
			).toString(),
			String.valueOf(parameters.get("symbols")));
	}

	@Test
	public void testGetNumericInputMaskProperties() {
		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithNumericInputMask(),
				_createDDMFormFieldRenderingContext());

		Assert.assertEquals("$", parameters.get("append"));
		Assert.assertEquals("prefix", parameters.get("appendType"));
		Assert.assertEquals(2, parameters.get("decimalPlaces"));
		Assert.assertEquals(
			JSONUtil.put(
				"decimalSymbol", ","
			).put(
				"thousandsSeparator", "\'"
			).toString(),
			String.valueOf(parameters.get("symbols")));
	}

	@Test
	public void testGetPredefinedValue() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty(
			"predefinedValue",
			DDMFormValuesTestUtil.createLocalizedValue("42", _locale));

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(_locale);
		ddmFormFieldRenderingContext.setViewMode(true);

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals(
			"42", String.valueOf(parameters.get("predefinedValue")));
	}

	@Test
	public void testGetSymbols() {
		Map<String, String> symbolsMap =
			_numericDDMFormFieldTemplateContextContributor.getSymbolsMap(
				LocaleUtil.US);

		Assert.assertEquals(".", symbolsMap.get("decimalSymbol"));
		Assert.assertEquals(",", symbolsMap.get("thousandsSeparator"));
	}

	@Test
	public void testGetSymbolsBrazilLocale() {
		Map<String, String> symbolsMap =
			_numericDDMFormFieldTemplateContextContributor.getSymbolsMap(
				LocaleUtil.BRAZIL);

		Assert.assertEquals(",", symbolsMap.get("decimalSymbol"));
		Assert.assertEquals(".", symbolsMap.get("thousandsSeparator"));
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(_locale);

		return ddmFormFieldRenderingContext;
	}

	private DDMFormField _createDDMFormFieldWithInputMask() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("inputMask", true);
		ddmFormField.setProperty(
			"inputMaskFormat",
			DDMFormValuesTestUtil.createLocalizedValue(
				"(999) 0999-9999", _locale));

		return ddmFormField;
	}

	private DDMFormField _createDDMFormFieldWithNumericInputMask() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "double");
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
				_locale));

		return ddmFormField;
	}

	private void _setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private void _setUpJSONFactory() throws Exception {
		PowerMockito.field(
			NumericDDMFormFieldTemplateContextContributor.class, "_jsonFactory"
		).set(
			_numericDDMFormFieldTemplateContextContributor,
			new JSONFactoryImpl()
		);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private final Locale _locale = LocaleUtil.US;
	private final NumericDDMFormFieldTemplateContextContributor
		_numericDDMFormFieldTemplateContextContributor =
			new NumericDDMFormFieldTemplateContextContributor();

}