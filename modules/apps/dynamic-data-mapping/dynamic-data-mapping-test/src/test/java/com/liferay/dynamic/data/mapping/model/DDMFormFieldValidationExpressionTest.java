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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormFieldValidationExpressionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpJSONFactoryUtil();
	}

	@Test
	public void testGetExpressionAnyValidation() {
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			new DDMFormFieldValidationExpression();

		ddmFormFieldValidationExpression.setValue("function({parameter})");

		Assert.assertEquals(
			"function(arg)",
			ddmFormFieldValidationExpression.getExpression(null, "arg", null));

		ddmFormFieldValidationExpression.setName("function");

		Assert.assertEquals(
			"function(arg)",
			ddmFormFieldValidationExpression.getExpression(null, "arg", null));
	}

	@Test
	public void testGetExpressionDateRangeDDMValidation() {
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			new DDMFormFieldValidationExpression();

		ddmFormFieldValidationExpression.setName("dateRange");
		ddmFormFieldValidationExpression.setValue(
			"after({parameter}) AND before({parameter})");

		Assert.assertEquals(
			"after(2021-10-28) AND before(2021-10-29)",
			ddmFormFieldValidationExpression.getExpression(
				_getDDMFormValues("EndDate", "StartDate"),
				JSONUtil.put(
					"endsOn",
					JSONUtil.put(
						"dateFieldName", "EndDate"
					).put(
						"type", "dateField"
					)
				).put(
					"startsFrom",
					JSONUtil.put(
						"dateFieldName", "StartDate"
					).put(
						"type", "dateField"
					)
				).toString(),
				null));
	}

	@Test
	public void testGetExpressionFutureDatesDDMValidation() {
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			new DDMFormFieldValidationExpression();

		ddmFormFieldValidationExpression.setName("futureDates");
		ddmFormFieldValidationExpression.setValue("after({parameter})");

		Assert.assertEquals(
			"after(2021-10-28)",
			ddmFormFieldValidationExpression.getExpression(
				_getDDMFormValues(null, "StartDate"),
				JSONUtil.put(
					"startsFrom",
					JSONUtil.put(
						"dateFieldName", "StartDate"
					).put(
						"type", "dateField"
					)
				).toString(),
				null));
	}

	@Test
	public void testGetExpressionPastDatesDDMValidation() {
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			new DDMFormFieldValidationExpression();

		ddmFormFieldValidationExpression.setName("pastDates");
		ddmFormFieldValidationExpression.setValue("before({parameter})");

		Assert.assertEquals(
			"before(2021-10-29)",
			ddmFormFieldValidationExpression.getExpression(
				_getDDMFormValues("EndDate", null),
				JSONUtil.put(
					"endsOn",
					JSONUtil.put(
						"dateFieldName", "EndDate"
					).put(
						"type", "dateField"
					)
				).toString(),
				null));
	}

	private static void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private DDMFormValues _getDDMFormValues(
		String endDateFieldName, String startDateFieldName) {

		DDMFormValues ddmFormValues = new DDMFormValues(null);

		if (endDateFieldName != null) {
			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
					endDateFieldName, "2021-10-29"));
		}

		if (startDateFieldName != null) {
			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
					startDateFieldName, "2021-10-28"));
		}

		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

}