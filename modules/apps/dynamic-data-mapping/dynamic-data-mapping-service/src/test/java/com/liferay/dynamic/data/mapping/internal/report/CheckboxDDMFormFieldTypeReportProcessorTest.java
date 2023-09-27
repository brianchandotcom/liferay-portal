/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rodrigo Paulino
 */
public class CheckboxDDMFormFieldTypeReportProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpJSONFactoryUtil();
	}

	@Test
	public void testProcessDDMFormInstanceReportOnDeleteEvent()
		throws Exception {

		JSONObject processedJSONObject =
			_checkboxDDMFormFieldTypeReportProcessor.process(
				_mockDDMFormFieldValue("true"),
				JSONUtil.put("values", JSONUtil.put("true", 1)), 0,
				DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);

		JSONObject valuesJSONObject = processedJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(0, valuesJSONObject.getLong("true"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithExistingData()
		throws Exception {

		JSONObject processedJSONObject =
			_checkboxDDMFormFieldTypeReportProcessor.process(
				_mockDDMFormFieldValue("true"),
				JSONUtil.put("values", JSONUtil.put("true", 1)), 0,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject valuesJSONObject = processedJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(2, valuesJSONObject.getLong("true"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithFalseValue()
		throws Exception {

		JSONObject processedJSONObject =
			_checkboxDDMFormFieldTypeReportProcessor.process(
				_mockDDMFormFieldValue("something other than true"),
				JSONUtil.put(
					"type", DDMFormFieldTypeConstants.CHECKBOX
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject valuesJSONObject = processedJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(1, valuesJSONObject.getLong("false"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithoutPreviousData()
		throws Exception {

		JSONObject processedJSONObject =
			_checkboxDDMFormFieldTypeReportProcessor.process(
				_mockDDMFormFieldValue("true"),
				JSONUtil.put("values", JSONFactoryUtil.createJSONObject()), 0,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject valuesJSONObject = processedJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(1, valuesJSONObject.getLong("true"));
	}

	private static void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private DDMFormFieldValue _mockDDMFormFieldValue(String value) {
		DDMFormFieldValue ddmFormFieldValue = Mockito.mock(
			DDMFormFieldValue.class);

		Mockito.when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"boolean"
		);

		Mockito.when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldTypeConstants.CHECKBOX
		);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, value);
		localizedValue.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			localizedValue
		);

		return ddmFormFieldValue;
	}

	private final CheckboxDDMFormFieldTypeReportProcessor
		_checkboxDDMFormFieldTypeReportProcessor =
			new CheckboxDDMFormFieldTypeReportProcessor();

}