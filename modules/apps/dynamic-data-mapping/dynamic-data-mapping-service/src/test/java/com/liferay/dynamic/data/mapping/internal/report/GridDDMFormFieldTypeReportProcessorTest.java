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
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Marcos Martins
 */
@RunWith(MockitoJUnitRunner.class)
public class GridDDMFormFieldTypeReportProcessorTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_gridDDMFormFieldTypeReportProcessor.ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalService;

		_mockDDMFormInstanceRecord();
	}

	@Test
	public void testProcessDDMFormInstanceReportOnDeleteEvent()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			"field1", JSONUtil.put("option1", "option2"));

		JSONObject processedFieldJSONObject =
			_gridDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.GRID
				).put(
					"values",
					JSONFactoryUtil.createJSONObject(
						"{option1 : {option1 : 1, option2 : 1}}")
				),
				_FORM_INSTANCE_RECORD_ID,
				DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		JSONObject rowJSONObject = valuesJSONObject.getJSONObject("option1");

		Assert.assertEquals(1, rowJSONObject.getLong("option1"));
		Assert.assertEquals(0, rowJSONObject.getLong("option2"));

		JSONObject structureJSONObject = processedFieldJSONObject.getJSONObject(
			"structure");

		_assertStructure(structureJSONObject);
	}

	@Test
	public void testProcessDDMFormInstanceReportWithEmptyData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			"field1", JSONUtil.put("option1", "option2"));

		JSONObject processedFieldJSONObject =
			_gridDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.GRID
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				_FORM_INSTANCE_RECORD_ID,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.GRID, processedFieldJSONObject.getString("type"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		JSONObject rowJSONObject = valuesJSONObject.getJSONObject("option1");

		Assert.assertEquals(1, rowJSONObject.getLong("option2"));

		JSONObject structureJSONObject = processedFieldJSONObject.getJSONObject(
			"structure");

		_assertStructure(structureJSONObject);
	}

	@Test
	public void testProcessDDMFormInstanceReportWithEmptyField()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			"field1", JSONFactoryUtil.createJSONObject());

		JSONObject processedFieldJSONObject =
			_gridDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.GRID
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				_FORM_INSTANCE_RECORD_ID,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldType.GRID, processedFieldJSONObject.getString("type"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertFalse(valuesJSONObject.has(""));

		JSONObject structureJSONObject = processedFieldJSONObject.getJSONObject(
			"structure");

		_assertStructure(structureJSONObject);
	}

	@Test
	public void testProcessDDMFormInstanceReportWithExistingData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = _mockDDMFormFieldValue(
			"field1", JSONUtil.put("option1", "option2"));

		JSONObject processedFieldJSONObject =
			_gridDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldType.GRID
				).put(
					"values",
					JSONFactoryUtil.createJSONObject(
						"{option1 : {option1 : 1, option2 : 1}}")
				),
				_FORM_INSTANCE_RECORD_ID,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		JSONObject rowJSONObject = valuesJSONObject.getJSONObject("option1");

		Assert.assertEquals(1, rowJSONObject.getLong("option1"));
		Assert.assertEquals(2, rowJSONObject.getLong("option2"));

		JSONObject structureJSONObject = processedFieldJSONObject.getJSONObject(
			"structure");

		_assertStructure(structureJSONObject);
	}

	private void _assertStructure(JSONObject structureJSONObject) {
		JSONArray expectedJSONArray = JSONUtil.putAll("option1", "option2");

		JSONArray columnsJSONArray = structureJSONObject.getJSONArray(
			"columns");

		Assert.assertEquals(
			expectedJSONArray.toString(), columnsJSONArray.toString());

		JSONArray rowsJSONArray = structureJSONObject.getJSONArray("rows");

		Assert.assertEquals(
			expectedJSONArray.toString(), rowsJSONArray.toString());
	}

	private DDMFormFieldOptions _createDDMFormOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOption("option1");
		ddmFormFieldOptions.addOption("option2");

		return ddmFormFieldOptions;
	}

	private DDMFormFieldValue _mockDDMFormFieldValue(
		String name, JSONObject valueJSONObject) {

		DDMFormFieldValue ddmFormFieldValue = mock(DDMFormFieldValue.class);

		when(
			ddmFormFieldValue.getName()
		).thenReturn(
			name
		);

		when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldType.GRID
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), valueJSONObject.toString());
		value.setDefaultLocale(LocaleUtil.US);

		when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		return ddmFormFieldValue;
	}

	private DDMFormInstance _mockDDMFormInstance(String fieldName)
		throws Exception {

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		DDMStructure ddmStructure = mock(DDMStructure.class);

		DDMFormField ddmFormField = mock(DDMFormField.class);

		when(
			ddmFormField.getProperty("columns")
		).thenReturn(
			_createDDMFormOptions()
		);

		when(
			ddmFormField.getProperty("rows")
		).thenReturn(
			_createDDMFormOptions()
		);

		when(
			ddmStructure.getDDMFormField(fieldName)
		).thenReturn(
			ddmFormField
		);

		when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		return ddmFormInstance;
	}

	private void _mockDDMFormInstanceRecord()
		throws Exception, PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		DDMFormInstance ddmFormInstance = _mockDDMFormInstance("field1");

		when(
			ddmFormInstanceRecord.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

		when(
			_ddmFormInstanceRecordLocalService.getDDMFormInstanceRecord(
				_FORM_INSTANCE_RECORD_ID)
		).thenReturn(
			ddmFormInstanceRecord
		);
	}

	private static final long _FORM_INSTANCE_RECORD_ID = 0;

	@Mock
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	private final GridDDMFormFieldTypeReportProcessor
		_gridDDMFormFieldTypeReportProcessor =
			new GridDDMFormFieldTypeReportProcessor();

}