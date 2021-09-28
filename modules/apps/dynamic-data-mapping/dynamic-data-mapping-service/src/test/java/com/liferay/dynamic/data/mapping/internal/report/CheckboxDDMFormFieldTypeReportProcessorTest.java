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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rodrigo Paulino
 */
@RunWith(PowerMockRunner.class)
public class CheckboxDDMFormFieldTypeReportProcessorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
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

	private DDMFormFieldValue _mockDDMFormFieldValue(String value) {
		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"boolean"
		);

		when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldTypeConstants.CHECKBOX
		);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, value);
		localizedValue.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			localizedValue
		);

		return ddmFormFieldValue;
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private final CheckboxDDMFormFieldTypeReportProcessor
		_checkboxDDMFormFieldTypeReportProcessor =
			new CheckboxDDMFormFieldTypeReportProcessor();

}