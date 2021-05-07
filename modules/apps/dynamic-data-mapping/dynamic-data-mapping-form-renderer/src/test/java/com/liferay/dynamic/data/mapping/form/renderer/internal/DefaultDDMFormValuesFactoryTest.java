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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DefaultDDMFormValuesFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAvailableLocales() {
		Set<Locale> expectedAvailableLocales = SetUtil.fromArray(
			new Locale[] {LocaleUtil.BRAZIL});

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		Assert.assertEquals(
			expectedAvailableLocales, ddmFormValues.getAvailableLocales());
	}

	@Test
	public void testDDMFormFieldValueInitialValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", false, false, false);

		ddmFormField.setProperty(
			"initialValue",
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US));

		ddmForm.addDDMFormField(ddmFormField);

		Value value = _getValue(
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL));

		Assert.assertEquals("Teste", value.getString(LocaleUtil.BRAZIL));

		value = _getValue(
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.US));

		Assert.assertEquals("Test", value.getString(LocaleUtil.US));
	}

	@Test
	public void testDDMFormFieldValueLocalizedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField("Name", true, false, false));

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		Assert.assertTrue(value instanceof LocalizedValue);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(StringPool.BLANK, value.getString(LocaleUtil.US));
	}

	@Test
	public void testDDMFormFieldValuePredefinedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", false, false, false);

		ddmFormField.setPredefinedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				"Robert", "Roberto", LocaleUtil.US));
		ddmFormField.setProperty(
			"initialValue",
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US));

		ddmForm.addDDMFormField(ddmFormField);

		Value value = _getValue(
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL));

		Assert.assertEquals("Roberto", value.getString(LocaleUtil.BRAZIL));

		value = _getValue(
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.SPAIN));

		Assert.assertEquals("Robert", value.getString(LocaleUtil.SPAIN));
	}

	@Test
	public void testDDMFormFieldValueUnlocalizedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"Name", false, false, false));

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		Assert.assertTrue(value instanceof UnlocalizedValue);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));
	}

	@Test
	public void testDefaultLocale() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		Assert.assertEquals(
			LocaleUtil.BRAZIL, ddmFormValues.getDefaultLocale());
	}

	@Test
	public void testNestedDDMFormFieldValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", false, false, false);

		nameDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField("Age", false, false, false));

		ddmForm.addDDMFormField(nameDDMFormField);

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm, LocaleUtil.BRAZIL);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		DDMFormFieldValue nameDDMFormFieldValue = ddmFormFieldValues.get(0);

		Value nameValue = nameDDMFormFieldValue.getValue();

		Assert.assertEquals(
			StringPool.BLANK, nameValue.getString(LocaleUtil.BRAZIL));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			nameDDMFormFieldValue.getNestedDDMFormFieldValues();

		DDMFormFieldValue ageDDMFormFieldValue = nestedDDMFormFieldValues.get(
			0);

		Value ageValue = ageDDMFormFieldValue.getValue();

		Assert.assertEquals(
			StringPool.BLANK, ageValue.getString(LocaleUtil.BRAZIL));
	}

	private Value _getValue(
		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory) {

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		return ddmFormFieldValue.getValue();
	}

}