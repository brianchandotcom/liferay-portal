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

package com.liferay.dynamic.data.mapping.type;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormValuesTestUtil;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class RadioDDMFormFieldValueRendererAccessorTest {

	@Before
	public void setUp() {
		availableLocales = DDMFormTestUtil.createAvailableLocales(
			LocaleUtil.US);
		defaultLocale = LocaleUtil.US;
	}

	@Test
	public void testGetRadioRenderedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			availableLocales, defaultLocale);

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Radio", "Radio", "radio", "string", false, false, false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOption("value 1");
		ddmFormFieldOptions.addOptionLabel(
			"value 1", defaultLocale, "option 1");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, availableLocales, defaultLocale);

		DDMFormFieldValue ddmFormFieldValue =
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"Radio", new UnlocalizedValue("value 1"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		RadioDDMFormFieldValueRendererAccessor
			radioDDMFormFieldValueRendererAccessor =
				createRadioDDMFormFieldValueRendererAccessor(defaultLocale);

		Assert.assertEquals(
			"option 1",
			radioDDMFormFieldValueRendererAccessor.get(ddmFormFieldValue));
	}

	protected RadioDDMFormFieldValueRendererAccessor
		createRadioDDMFormFieldValueRendererAccessor(Locale locale) {

		RadioDDMFormFieldValueAccessor radioDDMFormFieldValueAccessor =
			new RadioDDMFormFieldValueAccessor(locale);

		return new RadioDDMFormFieldValueRendererAccessor(
			radioDDMFormFieldValueAccessor);
	}

	protected Set<Locale> availableLocales;
	protected Locale defaultLocale;

}