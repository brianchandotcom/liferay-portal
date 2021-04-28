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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Marcos Martins
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckboxMultipleDDMFormFieldTypeReportProcessorTest
	extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testProcessDDMFormInstanceReportOnDeleteEvent()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldType.CHECKBOX_MULTIPLE
		);

		Value value = new LocalizedValue();

		value.addString(
			value.getDefaultLocale(),
			JSONUtil.put(
				"option1"
			).toString());
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_checkboxMultipleDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"totalEntries", 1
				).put(
					"type", DDMFormFieldType.CHECKBOX_MULTIPLE
				).put(
					"values", JSONUtil.put("option1", 1)
				),
				0, DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);

		Assert.assertEquals(0, processedFieldJSONObject.getInt("totalEntries"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(0, valuesJSONObject.getLong("option1"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithEmptyData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldType.CHECKBOX_MULTIPLE
		);

		Value value = new LocalizedValue();

		value.addString(
			value.getDefaultLocale(),
			JSONUtil.put(
				"option1"
			).toString());
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_checkboxMultipleDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"totalEntries", 0
				).put(
					"type", DDMFormFieldType.CHECKBOX_MULTIPLE
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(1, processedFieldJSONObject.getInt("totalEntries"));

		Assert.assertEquals(
			DDMFormFieldType.CHECKBOX_MULTIPLE,
			processedFieldJSONObject.getString("type"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(1, valuesJSONObject.getLong("option1"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithExistingData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldType.CHECKBOX_MULTIPLE
		);

		Value value = new LocalizedValue();

		value.addString(
			value.getDefaultLocale(),
			JSONUtil.putAll(
				"option1", "option2"
			).toString());
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_checkboxMultipleDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"totalEntries", 1
				).put(
					"type", DDMFormFieldType.CHECKBOX_MULTIPLE
				).put(
					"values", JSONUtil.put("option1", 1)
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(2, processedFieldJSONObject.getInt("totalEntries"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(2, valuesJSONObject.getLong("option1"));
		Assert.assertEquals(1, valuesJSONObject.getLong("option2"));
	}

	private final CheckboxMultipleDDMFormFieldTypeReportProcessor
		_checkboxMultipleDDMFormFieldTypeReportProcessor =
			new CheckboxMultipleDDMFormFieldTypeReportProcessor();

}