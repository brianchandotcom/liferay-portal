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
		Assert.assertEquals(true, parameters.get("requireConfirmation"));
	}

	@Test
	public void testGetDataType1() {
		String fieldName = "field";

		DDMFormField ddmFormField = new DDMFormField(fieldName, "numeric");

		ddmFormField.setProperty("dataType", "double");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty("changedProperties", null);

		Assert.assertEquals(
			"double",
			_numericDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataType2() {
		DDMFormField ddmFormField = new DDMFormField("field", "numeric");

		ddmFormField.setProperty("dataType", "integer");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", new HashMap<String, Object>());

		Assert.assertEquals(
			"integer",
			_numericDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDataType3() {
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
			_numericDDMFormFieldTemplateContextContributor.getDataType(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetDecimalSymbolsWithAmericanLocale() {
		Map<String, String> separatorSymbolsMap =
			_numericDDMFormFieldTemplateContextContributor.getSymbolsMap(
				LocaleUtil.US);

		Assert.assertEquals(".", separatorSymbolsMap.get("decimalSymbol"));
		Assert.assertEquals(",", separatorSymbolsMap.get("thousandsSeparator"));
	}

	@Test
	public void testGetDecimalSymbolsWithBrazilianLocale() {
		Map<String, String> separatorSymbolsMap =
			_numericDDMFormFieldTemplateContextContributor.getSymbolsMap(
				LocaleUtil.BRAZIL);

		Assert.assertEquals(",", separatorSymbolsMap.get("decimalSymbol"));
		Assert.assertEquals(".", separatorSymbolsMap.get("thousandsSeparator"));
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(_locale);

		return ddmFormFieldRenderingContext;
	}

	private void _setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private final Locale _locale = LocaleUtil.US;
	private final NumericDDMFormFieldTemplateContextContributor
		_numericDDMFormFieldTemplateContextContributor =
			new NumericDDMFormFieldTemplateContextContributor();

}