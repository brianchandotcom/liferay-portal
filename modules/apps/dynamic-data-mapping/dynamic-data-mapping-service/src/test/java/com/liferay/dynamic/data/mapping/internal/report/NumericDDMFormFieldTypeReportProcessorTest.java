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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
public class NumericDDMFormFieldTypeReportProcessorTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_numericDDMFormFieldTypeReportProcessor.
			ddmFormInstanceRecordLocalService =
				_ddmFormInstanceRecordLocalService;
	}

	@Test
	public void testFormatBigDecimal() {
		Assert.assertEquals(
			"12",
			_numericDDMFormFieldTypeReportProcessor.formatBigDecimal(
				new BigDecimal("12.00000000000000")));
		Assert.assertEquals(
			"12.00000000000001",
			_numericDDMFormFieldTypeReportProcessor.formatBigDecimal(
				new BigDecimal("12.00000000000001")));
		Assert.assertEquals(
			"12.000000001",
			_numericDDMFormFieldTypeReportProcessor.formatBigDecimal(
				new BigDecimal("12.0000000010000")));
	}

	@Test
	public void testGetValueBigDecimal() {
		Assert.assertEquals(
			new BigDecimal("3.5"),
			_numericDDMFormFieldTypeReportProcessor.getValueBigDecimal(
				_mockDDMFormFieldValue(LocaleUtil.BRAZIL, "field1", "3,5")));
		Assert.assertEquals(
			new BigDecimal("4.5"),
			_numericDDMFormFieldTypeReportProcessor.getValueBigDecimal(
				_mockDDMFormFieldValue(LocaleUtil.US, "field1", "4.5")));
	}

	@Test
	public void testProcessDDMFormInstanceReportOnDeleteEvent()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			LocaleUtil.US, "field1", "3");

		long formInstanceRecordId = 3;

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_mockDDMFormInstanceRecord(formInstanceRecordId);

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
			ddmFormInstance.getFormInstanceId()
		).thenReturn(
			0L
		);

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		for (int i = _VALUES_MAX_LENGTH; i > 0; i--) {
			ddmFormInstanceRecords.add(
				_createDDMFormInstanceRecord(String.valueOf(i)));
		}

		when(
			ddmFormInstance.getFormInstanceRecords()
		).thenReturn(
			ddmFormInstanceRecords
		);

		when(
			ddmFormInstanceRecord.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

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
			_numericDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"summary",
					JSONUtil.put(
						"average", "3"
					).put(
						"max", "5"
					).put(
						"min", "1"
					).put(
						"sum", "15"
					)
				).put(
					"totalEntries", 5
				).put(
					"type", DDMFormFieldType.NUMERIC
				).put(
					"values",
					JSONUtil.putAll(
						JSONUtil.put(
							"formInstanceRecordId", 5
						).put(
							"value", "5"
						),
						JSONUtil.put(
							"formInstanceRecordId", 4
						).put(
							"value", "4"
						),
						JSONUtil.put(
							"formInstanceRecordId", 3
						).put(
							"value", "3"
						),
						JSONUtil.put(
							"formInstanceRecordId", 2
						).put(
							"value", "2"
						),
						JSONUtil.put(
							"formInstanceRecordId", 1
						).put(
							"value", "1"
						))
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);

		JSONObject summaryJSONObject = processedFieldJSONObject.getJSONObject(
			"summary");

		Assert.assertEquals("3", summaryJSONObject.getString("average"));
		Assert.assertEquals("5", summaryJSONObject.getString("max"));
		Assert.assertEquals("1", summaryJSONObject.getString("min"));
		Assert.assertEquals("12", summaryJSONObject.getString("sum"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithEmptyData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			LocaleUtil.US, "field1", "1");

		long formInstanceRecordId = 1;

		_mockDDMFormInstanceRecord(formInstanceRecordId);

		JSONObject processedFieldJSONObject =
			_numericDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.NUMERIC
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject summaryJSONObject = processedFieldJSONObject.getJSONObject(
			"summary");

		Assert.assertEquals("1", summaryJSONObject.getString("average"));
		Assert.assertEquals("1", summaryJSONObject.getString("max"));
		Assert.assertEquals("1", summaryJSONObject.getString("min"));
		Assert.assertEquals("1", summaryJSONObject.getString("sum"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithExistingData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			LocaleUtil.US, "field1", "3");

		long formInstanceRecordId = 0;

		_mockDDMFormInstanceRecord(formInstanceRecordId);

		JSONObject processedFieldJSONObject =
			_numericDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"summary",
					JSONUtil.put(
						"average", "1"
					).put(
						"max", "1"
					).put(
						"min", "1"
					).put(
						"sum", "1"
					)
				).put(
					"totalEntries", 1
				).put(
					"type", DDMFormFieldType.NUMERIC
				).put(
					"values",
					JSONUtil.put(
						JSONUtil.put(
							"formInstanceRecordId", 1
						).put(
							"value", "1"
						))
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject summaryJSONObject = processedFieldJSONObject.getJSONObject(
			"summary");

		Assert.assertEquals("2", summaryJSONObject.getString("average"));
		Assert.assertEquals("3", summaryJSONObject.getString("max"));
		Assert.assertEquals("1", summaryJSONObject.getString("min"));
		Assert.assertEquals("4", summaryJSONObject.getString("sum"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithLongFieldValue()
		throws Exception {

		// LPS-118317

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			LocaleUtil.US, "field1",
			"99999999999999999999999999999999999999999");

		long formInstanceRecordId = 0;

		_mockDDMFormInstanceRecord(formInstanceRecordId);

		JSONObject processedFieldJSONObject =
			_numericDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"summary",
					JSONUtil.put(
						"average", "1"
					).put(
						"max", "1"
					).put(
						"min", "1"
					).put(
						"sum", "1"
					)
				).put(
					"totalEntries", 1
				).put(
					"type", DDMFormFieldType.NUMERIC
				).put(
					"values",
					JSONUtil.put(
						JSONUtil.put(
							"formInstanceRecordId", 1
						).put(
							"value", "1"
						))
				),
				formInstanceRecordId,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject summaryJSONObject = processedFieldJSONObject.getJSONObject(
			"summary");

		Assert.assertEquals(
			"50000000000000000000000000000000000000000",
			summaryJSONObject.getString("average"));
		Assert.assertEquals(
			"99999999999999999999999999999999999999999",
			summaryJSONObject.getString("max"));
		Assert.assertEquals("1", summaryJSONObject.getString("min"));
		Assert.assertEquals(
			"100000000000000000000000000000000000000000",
			summaryJSONObject.getString("sum"));
	}

	private DDMFormInstanceRecord _createDDMFormInstanceRecord(
			String valueString)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			LocaleUtil.US, "", valueString);

		DDMFormValues ddmFormValues = mock(DDMFormValues.class);

		when(
			ddmFormValues.getDDMFormFieldValuesMap(false)
		).thenReturn(
			HashMapBuilder.<String, List<DDMFormFieldValue>>put(
				"field1", Arrays.asList(ddmFormFieldValue)
			).build()
		);

		when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		return ddmFormInstanceRecord;
	}

	private DDMFormFieldValue _mockDDMFormFieldValue(
		Locale defaultLocale, String fieldName, String fieldValue) {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			fieldName
		);

		when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldType.NUMERIC
		);

		Value value = new LocalizedValue(defaultLocale);

		value.addString(value.getDefaultLocale(), fieldValue);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		return ddmFormFieldValue;
	}

	private DDMFormInstanceRecord _mockDDMFormInstanceRecord(
			long formInstanceRecordId)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId)
		).thenReturn(
			ddmFormInstanceRecord
		);

		return ddmFormInstanceRecord;
	}

	private static final int _VALUES_MAX_LENGTH = 5;

	@Mock
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	private final NumericDDMFormFieldTypeReportProcessor
		_numericDDMFormFieldTypeReportProcessor =
			new NumericDDMFormFieldTypeReportProcessor();

}