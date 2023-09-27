/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Paulino
 */
public class SearchLocationDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetValue() {
		String value = JSONUtil.put(
			"city", "Los Angeles"
		).put(
			"country", "United States"
		).put(
			"place", "Los Angeles County Museum of Art"
		).toString();

		Assert.assertEquals(
			value,
			_searchLocationDDMFormFieldValueAccessor.getValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					StringUtil.randomString(),
					DDMFormValuesTestUtil.createLocalizedValue(
						value, LocaleUtil.US)),
				LocaleUtil.US));
	}

	@Test
	public void testGetValueForEvaluation() {
		String value = JSONUtil.put(
			"city", "Los Angeles"
		).put(
			"country", "United States"
		).put(
			"place", "Los Angeles County Museum of Art"
		).toString();

		Assert.assertEquals(
			value,
			_searchLocationDDMFormFieldValueAccessor.getValueForEvaluation(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					StringUtil.randomString(),
					DDMFormValuesTestUtil.createLocalizedValue(
						value, LocaleUtil.US)),
				LocaleUtil.US));
	}

	@Test
	public void testIsEmptyWithAllValues() {
		Assert.assertFalse(
			_searchLocationDDMFormFieldValueAccessor.isEmpty(
				_createDDMFormFieldValue(
					JSONUtil.put(
						"city", "Los Angeles"
					).put(
						"country", "United States"
					).put(
						"place", "Los Angeles County Museum of Art"
					)),
				LocaleUtil.US));
	}

	@Test
	public void testIsEmptyWithoutCityValue() {
		Assert.assertTrue(
			_searchLocationDDMFormFieldValueAccessor.isEmpty(
				_createDDMFormFieldValue(
					JSONUtil.put(
						"country", "United States"
					).put(
						"place", "Los Angeles County Museum of Art"
					)),
				LocaleUtil.US));
	}

	@Test
	public void testIsEmptyWithoutPlaceValue() {
		Assert.assertTrue(
			_searchLocationDDMFormFieldValueAccessor.isEmpty(
				_createDDMFormFieldValue(
					JSONUtil.put(
						"city", "Los Angeles"
					).put(
						"country", "United States"
					)),
				LocaleUtil.US));
	}

	private static void _setUpJSONFactory(JSONFactory jsonFactory) {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_searchLocationDDMFormFieldValueAccessor, "_jsonFactory",
			jsonFactory);
	}

	private DDMFormFieldValue _createDDMFormFieldValue(JSONObject jsonObject) {
		DDMForm ddmForm = new DDMForm();

		DDMFormTestUtil.addDDMFormFields(
			ddmForm,
			DDMFormTestUtil.createSearchLocationDDMFormField(
				DDMFormValuesTestUtil.createLocalizedValue(
					StringPool.BLANK, LocaleUtil.US),
				"name",
				DDMFormValuesTestUtil.createLocalizedValue(
					Arrays.toString(new String[] {"city", "country"}),
					LocaleUtil.US)));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"name",
				DDMFormValuesTestUtil.createLocalizedValue(
					String.valueOf(jsonObject), LocaleUtil.US));

		ddmFormFieldValue.setDDMFormValues(
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm));

		return ddmFormFieldValue;
	}

	private static final SearchLocationDDMFormFieldValueAccessor
		_searchLocationDDMFormFieldValueAccessor =
			new SearchLocationDDMFormFieldValueAccessor();

}