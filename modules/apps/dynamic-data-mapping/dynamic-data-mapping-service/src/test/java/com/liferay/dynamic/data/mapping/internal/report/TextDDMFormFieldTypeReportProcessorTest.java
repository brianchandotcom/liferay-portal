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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Marcos Martins
 */
@RunWith(MockitoJUnitRunner.class)
public class TextDDMFormFieldTypeReportProcessorTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_textDDMFormFieldTypeReportProcessor.ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalService;
	}

	@Test
	public void testProcessDDMFormInstanceReportEditLastFiveRecords()
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
			DDMFormFieldType.TEXT
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "text 6");
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		long formInstanceRecordId = 3;

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId)
		).thenReturn(
			ddmFormInstanceRecord
		);

		JSONObject processedFieldJSONObject =
			_textDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.TEXT
				).put(
					"values",
					JSONUtil.putAll(
						JSONUtil.put(
							"formInstanceRecordId", 5
						).put(
							"value", "text 5"
						),
						JSONUtil.put(
							"formInstanceRecordId", 4
						).put(
							"value", "text 4"
						),
						JSONUtil.put(
							"formInstanceRecordId", 3
						).put(
							"value", "text 3"
						),
						JSONUtil.put(
							"formInstanceRecordId", 2
						).put(
							"value", "text 2"
						),
						JSONUtil.put(
							"formInstanceRecordId", 1
						).put(
							"value", "text 1"
						))
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.TEXT, processedFieldJSONObject.getString("type"));

		JSONArray valuesJSONArray = processedFieldJSONObject.getJSONArray(
			"values");

		JSONObject jsonObject = valuesJSONArray.getJSONObject(0);

		Assert.assertEquals("text 6", jsonObject.getString("value"));

		jsonObject = valuesJSONArray.getJSONObject(2);

		Assert.assertEquals("text 4", jsonObject.getString("value"));
	}

	@Test
	public void testProcessDDMFormInstanceReportOnDeleteEvent()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "text 6");
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
			ddmFormInstance.getFormInstanceId()
		).thenReturn(
			0L
		);

		when(
			ddmFormInstanceRecord.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

		long formInstanceRecordId = 3;

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId)
		).thenReturn(
			ddmFormInstanceRecord
		);

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		for (int i = _VALUES_MAX_LENGTH; i > 0; i--) {
			ddmFormInstanceRecords.add(_createFormInstanceRecord("text " + i));
		}

		when(
			_ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
				Mockito.eq(0L), Mockito.eq(new String[] {"field1"}),
				Mockito.eq(WorkflowConstants.STATUS_APPROVED), Mockito.eq(0),
				Mockito.eq(_VALUES_MAX_LENGTH + 1), Mockito.any())
		).thenReturn(
			new BaseModelSearchResult<DDMFormInstanceRecord>(
				ddmFormInstanceRecords, ddmFormInstanceRecords.size())
		);

		JSONObject processedFieldJSONObject =
			_textDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue, JSONUtil.put("type", DDMFormFieldType.TEXT),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.TEXT, processedFieldJSONObject.getString("type"));

		JSONArray valuesJSONArray = processedFieldJSONObject.getJSONArray(
			"values");

		JSONObject jsonObject = valuesJSONArray.getJSONObject(0);

		Assert.assertEquals("text 5", jsonObject.getString("value"));
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
			DDMFormFieldType.TEXT
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "text");
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		long formInstanceRecordId = 1;

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId)
		).thenReturn(
			ddmFormInstanceRecord
		);

		JSONObject processedFieldJSONObject =
			_textDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.TEXT
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.TEXT, processedFieldJSONObject.getString("type"));

		JSONArray valuesJSONArray = processedFieldJSONObject.getJSONArray(
			"values");

		JSONObject jsonObject = valuesJSONArray.getJSONObject(0);

		Assert.assertEquals("text", jsonObject.getString("value"));
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
			DDMFormFieldType.TEXT
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "text 2");
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		long formInstanceRecordId = 0;

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId)
		).thenReturn(
			ddmFormInstanceRecord
		);

		JSONObject processedFieldJSONObject =
			_textDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.TEXT
				).put(
					"values",
					JSONUtil.put(
						JSONUtil.put(
							"formInstanceRecordId", 1
						).put(
							"value", "text 1"
						))
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.TEXT, processedFieldJSONObject.getString("type"));

		JSONArray valuesJSONArray = processedFieldJSONObject.getJSONArray(
			"values");

		JSONObject jsonObject = valuesJSONArray.getJSONObject(0);

		Assert.assertEquals("text 2", jsonObject.getString("value"));

		jsonObject = valuesJSONArray.getJSONObject(1);

		Assert.assertEquals("text 1", jsonObject.getString("value"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithFiveExistingEntries()
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
			DDMFormFieldType.TEXT
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "text 6");
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		long formInstanceRecordId = 6;

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId)
		).thenReturn(
			ddmFormInstanceRecord
		);

		JSONObject processedFieldJSONObject =
			_textDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.TEXT
				).put(
					"values",
					JSONUtil.putAll(
						JSONUtil.put(
							"formInstanceRecordId", 5
						).put(
							"value", "text 5"
						),
						JSONUtil.put(
							"formInstanceRecordId", 4
						).put(
							"value", "text 4"
						),
						JSONUtil.put(
							"formInstanceRecordId", 3
						).put(
							"value", "text 3"
						),
						JSONUtil.put(
							"formInstanceRecordId", 2
						).put(
							"value", "text 2"
						),
						JSONUtil.put(
							"formInstanceRecordId", 1
						).put(
							"value", "text 1"
						))
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.TEXT, processedFieldJSONObject.getString("type"));

		JSONArray valuesJSONArray = processedFieldJSONObject.getJSONArray(
			"values");

		JSONObject jsonObject = valuesJSONArray.getJSONObject(0);

		Assert.assertEquals("text 6", jsonObject.getString("value"));

		jsonObject = valuesJSONArray.getJSONObject(4);

		Assert.assertEquals("text 2", jsonObject.getString("value"));
	}

	private DDMFormInstanceRecord _createFormInstanceRecord(String valueString)
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), valueString);
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		DDMFormValues ddmFormValues = mock(DDMFormValues.class);

		when(
			ddmFormValues.getDDMFormFieldValuesMap(false)
		).thenReturn(
			HashMapBuilder.<String, List<DDMFormFieldValue>>put(
				"field1", Arrays.asList(ddmFormFieldValue)
			).build()
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		return ddmFormInstanceRecord;
	}

	private static final int _VALUES_MAX_LENGTH = 5;

	@Mock
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	private final TextDDMFormFieldTypeReportProcessor
		_textDDMFormFieldTypeReportProcessor =
			new TextDDMFormFieldTypeReportProcessor();

}