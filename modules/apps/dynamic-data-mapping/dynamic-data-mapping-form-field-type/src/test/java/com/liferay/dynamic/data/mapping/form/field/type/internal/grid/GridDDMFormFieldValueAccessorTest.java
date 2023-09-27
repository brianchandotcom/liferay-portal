/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Queiroz
 */
public class GridDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpGridDDMFormFieldValueAccessor();
	}

	@Test
	public void testEmpty() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Grid", "Grid", "grid", "string", false, false, true);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("row1", LocaleUtil.US, "Row 1");
		ddmFormFieldOptions.addOptionLabel("row2", LocaleUtil.US, "Row 2");

		ddmFormField.setProperty("rows", ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Grid", new UnlocalizedValue("{\"row1\":\"column1\"}"));

		ddmFormFieldValue.setDDMFormValues(ddmFormValues);

		Assert.assertTrue(
			_gridDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testGetGridValue() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Grid", new UnlocalizedValue("{\"RowValue\":\"ColumnValue\"}"));

		Assert.assertEquals(
			"{\"RowValue\":\"ColumnValue\"}",
			String.valueOf(
				_gridDDMFormFieldValueAccessor.getValue(
					ddmFormFieldValue, LocaleUtil.US)));
	}

	@Test
	public void testNotEmpty() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Grid", "Grid", "grid", "string", false, false, true);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("row1", LocaleUtil.US, "Row 1");
		ddmFormFieldOptions.addOptionLabel("row2", LocaleUtil.US, "Row 2");

		ddmFormField.setProperty("rows", ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Grid",
				new UnlocalizedValue(
					"{\"row1\":\"column1\",\"row2\":\"column1\"}"));

		ddmFormFieldValue.setDDMFormValues(ddmFormValues);

		Assert.assertFalse(
			_gridDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private static void _setUpGridDDMFormFieldValueAccessor() {
		_gridDDMFormFieldValueAccessor = new GridDDMFormFieldValueAccessor();

		ReflectionTestUtil.setFieldValue(
			_gridDDMFormFieldValueAccessor, "jsonFactory",
			new JSONFactoryImpl());
	}

	private static GridDDMFormFieldValueAccessor _gridDDMFormFieldValueAccessor;

}