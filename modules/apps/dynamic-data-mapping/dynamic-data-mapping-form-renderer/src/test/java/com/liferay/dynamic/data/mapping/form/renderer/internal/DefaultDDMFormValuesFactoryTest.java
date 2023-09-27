/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

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
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		Assert.assertEquals(
			ddmForm.getAvailableLocales(), ddmFormValues.getAvailableLocales());
	}

	@Test
	public void testDDMFormFieldValueInitialValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, false, false);

		ddmFormField.setProperty(
			"initialValue",
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US));

		ddmForm.addDDMFormField(ddmFormField);

		Value value = _getValue(new DefaultDDMFormValuesFactory(ddmForm));

		Assert.assertEquals(
			"Test", value.getString(ddmForm.getDefaultLocale()));
		Assert.assertEquals("Teste", value.getString(LocaleUtil.BRAZIL));
	}

	@Test
	public void testDDMFormFieldValueLocalizedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, false, false);

		ddmFormField.setPredefinedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				"Robert", "Roberto", LocaleUtil.US));

		ddmForm.addDDMFormField(ddmFormField);

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		Assert.assertTrue(value instanceof LocalizedValue);

		Assert.assertEquals(
			"Robert", value.getString(ddmForm.getDefaultLocale()));
		Assert.assertEquals("Roberto", value.getString(LocaleUtil.BRAZIL));
	}

	@Test
	public void testDDMFormFieldValuePredefinedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, false, false);

		ddmFormField.setPredefinedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				"Robert", "Roberto", LocaleUtil.US));
		ddmFormField.setProperty(
			"initialValue",
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US));

		ddmForm.addDDMFormField(ddmFormField);

		Value value = _getValue(new DefaultDDMFormValuesFactory(ddmForm));

		Assert.assertEquals("Roberto", value.getString(LocaleUtil.BRAZIL));

		value = _getValue(new DefaultDDMFormValuesFactory(ddmForm));

		Assert.assertEquals(
			"Robert", value.getString(ddmForm.getDefaultLocale()));
	}

	@Test
	public void testDDMFormFieldValueUnlocalizedValue() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"Name", false, false, false));

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		Assert.assertTrue(value instanceof UnlocalizedValue);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(ddmForm.getDefaultLocale()));
	}

	@Test
	public void testDefaultLocale() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		Assert.assertEquals(
			ddmForm.getDefaultLocale(), ddmFormValues.getDefaultLocale());
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
			new DefaultDDMFormValuesFactory(ddmForm);

		DDMFormValues ddmFormValues = defaultDDMFormValuesFactory.create();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		_assertDDMFormFieldValue(
			ddmFormFieldValues.get(0), StringPool.BLANK, StringPool.BLANK);
	}

	@Test
	public void testPopulate() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"parentField", null, null, null, false, true, false);

		ddmFormField.addNestedDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"nestedField", null, null, null, false, false, false, null,
				"true", null));

		ddmForm.addDDMFormField(ddmFormField);

		DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
			new DefaultDDMFormValuesFactory(ddmForm);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				ddmFormField.getName(), "someValue1"));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				ddmFormField.getName(), "someValue2"));

		defaultDDMFormValuesFactory.populate(ddmFormValues);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"parentField");

		_assertDDMFormFieldValue(
			ddmFormFieldValues.get(0), "true", "someValue1");
		_assertDDMFormFieldValue(
			ddmFormFieldValues.get(1), "true", "someValue2");
	}

	private void _assertDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, String expectedNestedValue,
		String expectedValue) {

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals(
			expectedValue, value.getString(value.getDefaultLocale()));

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		DDMFormFieldValue nestedDDMFormFieldValue =
			nestedDDMFormFieldValues.get(0);

		Value nestedValue = nestedDDMFormFieldValue.getValue();

		Assert.assertEquals(
			expectedNestedValue,
			nestedValue.getString(nestedValue.getDefaultLocale()));
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