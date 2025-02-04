/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTemplateContextContributorTestCase;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 */
public class NumericDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTemplateContextContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		setUpLanguageUtil();

		_setUpHtmlParser();

		_ddmFormField.setDDMForm(getDDMForm());

		ReflectionTestUtil.setFieldValue(
			_numericDDMFormFieldTemplateContextContributor, "_jsonFactory",
			new JSONFactoryImpl());
	}

	@Test
	public void testGetConfirmationFieldProperties() {
		_ddmFormField.setProperty(
			"confirmationErrorMessage",
			DDMFormValuesTestUtil.createLocalizedValue(
				"The information does not match", _locale));
		_ddmFormField.setProperty(
			"confirmationLabel",
			DDMFormValuesTestUtil.createLocalizedValue(
				"Confirm Field", _locale));
		_ddmFormField.setProperty("requireConfirmation", true);

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, createDDMFormFieldRenderingContext());

		Assert.assertEquals(
			"The information does not match",
			parameters.get("confirmationErrorMessage"));
		Assert.assertEquals(
			"Confirm Field", parameters.get("confirmationLabel"));
		Assert.assertTrue((boolean)parameters.get("requireConfirmation"));
	}

	@Test
	public void testGetDataTypeChanged() {
		_ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"dataType", "double"
			).build());

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("double", parameters.get("dataType"));
	}

	@Test
	public void testGetDataTypeDouble() {
		_ddmFormField.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty("changedProperties", null);

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("double", parameters.get("dataType"));
	}

	@Test
	public void testGetDataTypeInteger() {
		_ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", new HashMap<String, Object>());

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("integer", parameters.get("dataType"));
	}

	@Test
	public void testGetInputMaskChangedProperties() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"inputMaskFormat",
				DDMFormValuesTestUtil.createLocalizedValue("(999)", _locale)
			).build());

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithInputMask(),
				ddmFormFieldRenderingContext);

		Assert.assertEquals("(999)", parameters.get("inputMaskFormat"));
	}

	@Test
	public void testGetInputMaskProperties() {
		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithInputMask(),
				createDDMFormFieldRenderingContext());

		Assert.assertTrue((boolean)parameters.get("inputMask"));
		Assert.assertEquals(
			"(999) 0999-9999", parameters.get("inputMaskFormat"));
	}

	@Test
	public void testGetLocalizedObjectFieldTrue() {
		_ddmFormField.setProperty("localizedObjectField", true);

		Assert.assertTrue(
			MapUtil.getBoolean(
				_numericDDMFormFieldTemplateContextContributor.getParameters(
					_ddmFormField, createDDMFormFieldRenderingContext()),
				"localizedObjectField"));
	}

	@Test
	public void testGetNumericInputMaskChangedProperties() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

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
			HashMapBuilder.put(
				"decimalSymbol", "."
			).put(
				"thousandsSeparator", " "
			).build(),
			parameters.get("symbols"));
	}

	@Test
	public void testGetNumericInputMaskProperties() {
		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_createDDMFormFieldWithNumericInputMask(),
				createDDMFormFieldRenderingContext());

		Assert.assertEquals("$", parameters.get("append"));
		Assert.assertEquals("prefix", parameters.get("appendType"));
		Assert.assertEquals(2, parameters.get("decimalPlaces"));
		Assert.assertEquals(
			HashMapBuilder.put(
				"decimalSymbol", ","
			).put(
				"thousandsSeparator", "\'"
			).build(),
			parameters.get("symbols"));
	}

	@Test
	public void testGetSymbols() {
		_ddmFormField.setProperty("dataType", "double");

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, createDDMFormFieldRenderingContext());

		Map<String, String> symbols = (Map<String, String>)parameters.get(
			"symbols");

		Assert.assertEquals(".", symbols.get("decimalSymbol"));
		Assert.assertEquals(",", symbols.get("thousandsSeparator"));
	}

	@Test
	public void testGetSymbolsBrazilLocale() {
		_ddmFormField.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.BRAZIL);

		Map<String, Object> parameters =
			_numericDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, ddmFormFieldRenderingContext);

		Map<String, String> symbols = (Map<String, String>)parameters.get(
			"symbols");

		Assert.assertEquals(",", symbols.get("decimalSymbol"));
		Assert.assertEquals(".", symbols.get("thousandsSeparator"));
	}

	@Test
	public void testGetValue() {
		_ddmFormField.setProperty("localizedObjectField", true);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		String value = JSONUtil.put(
			"en_US", "5"
		).put(
			"pt_BR", "9.47"
		).toString();

		ddmFormFieldRenderingContext.setValue(value);

		Assert.assertEquals(
			value,
			MapUtil.getString(
				_numericDDMFormFieldTemplateContextContributor.getParameters(
					_ddmFormField, ddmFormFieldRenderingContext),
				"value"));
	}

	private DDMFormField _createDDMFormFieldWithInputMask() {
		DDMFormField ddmFormField = new DDMFormField(
			"field", DDMFormFieldTypeConstants.NUMERIC);

		ddmFormField.setDDMForm(getDDMForm());
		ddmFormField.setProperty("inputMask", true);
		ddmFormField.setProperty(
			"inputMaskFormat",
			DDMFormValuesTestUtil.createLocalizedValue(
				"(999) 0999-9999", _locale));

		return ddmFormField;
	}

	private DDMFormField _createDDMFormFieldWithNumericInputMask() {
		DDMFormField ddmFormField = new DDMFormField(
			"field", DDMFormFieldTypeConstants.NUMERIC);

		ddmFormField.setDDMForm(getDDMForm());
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

	private void _setUpHtmlParser() {
		ReflectionTestUtil.setFieldValue(
			_numericDDMFormFieldTemplateContextContributor, "_htmlParser",
			_htmlParser);

		Mockito.when(
			_htmlParser.extractText(StringPool.BLANK)
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_htmlParser.extractText("5")
		).thenReturn(
			"5"
		);

		Mockito.when(
			_htmlParser.extractText("9.47")
		).thenReturn(
			"9.47"
		);
	}

	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", DDMFormFieldTypeConstants.NUMERIC);
	private final HtmlParser _htmlParser = Mockito.mock(HtmlParser.class);
	private final Locale _locale = LocaleUtil.US;
	private final NumericDDMFormFieldTemplateContextContributor
		_numericDDMFormFieldTemplateContextContributor =
			new NumericDDMFormFieldTemplateContextContributor();

}