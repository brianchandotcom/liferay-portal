/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public class CheckboxMultipleDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_checkboxMultipleDDMFormFieldValueAccessor =
			new CheckboxMultipleDDMFormFieldValueAccessor();

		_checkboxMultipleDDMFormFieldValueAccessor.jsonFactory = _jsonFactory;
	}

	@Test
	public void testGetCheckboxMultipleValue1() {
		JSONArray expectedJSONArray = createJSONArray("value 1");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"CheckboxMultiple",
				new UnlocalizedValue(expectedJSONArray.toString()));

		JSONArray actualJSONArray =
			_checkboxMultipleDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals(
			expectedJSONArray.toString(), actualJSONArray.toString());
	}

	@Test
	public void testGetCheckboxMultipleValue2() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"CheckboxMultiple", new UnlocalizedValue(StringPool.BLANK));

		JSONArray actualJSONArray =
			_checkboxMultipleDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertTrue(actualJSONArray.length() == 0);
	}

	@Test
	public void testGetCheckboxMultipleValueForEvaluation() {
		JSONArray expectedJSONArray = createJSONArray("Option / test");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"CheckboxMultiple", "Checkbox Multiple", "checkbox-multiple",
			"string", false, false, false);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		String optionValue = "Option12345678";

		ddmFormFieldOptions.addOptionLabel(
			optionValue, LocaleUtil.US,
			String.valueOf(expectedJSONArray.get(0)));

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		JSONArray optionJSONArray = createJSONArray(optionValue);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"CheckboxMultiple",
				new UnlocalizedValue(optionJSONArray.toString()));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		JSONArray actualJSONArray =
			_checkboxMultipleDDMFormFieldValueAccessor.getValueForEvaluation(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals(
			expectedJSONArray.toString(), actualJSONArray.toString());
	}

	protected JSONArray createJSONArray(String... strings) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String string : strings) {
			jsonArray.put(string);
		}

		return jsonArray;
	}

	private CheckboxMultipleDDMFormFieldValueAccessor
		_checkboxMultipleDDMFormFieldValueAccessor;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}