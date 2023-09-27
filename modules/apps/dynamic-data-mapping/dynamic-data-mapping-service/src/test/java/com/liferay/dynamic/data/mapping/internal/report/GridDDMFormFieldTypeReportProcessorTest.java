/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcos Martins
 */
public class GridDDMFormFieldTypeReportProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_gridDDMFormFieldTypeReportProcessor.ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalService;

		ReflectionTestUtil.setFieldValue(
			_gridDDMFormFieldTypeReportProcessor, "_jsonFactory",
			new JSONFactoryImpl());

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
					"type", DDMFormFieldTypeConstants.GRID
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
					"type", DDMFormFieldTypeConstants.GRID
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				_FORM_INSTANCE_RECORD_ID,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldTypeConstants.GRID,
			processedFieldJSONObject.getString("type"));

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
					"type", DDMFormFieldTypeConstants.GRID
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				_FORM_INSTANCE_RECORD_ID,
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldTypeConstants.GRID,
			processedFieldJSONObject.getString("type"));

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
					"type", DDMFormFieldTypeConstants.GRID
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

		DDMFormFieldValue ddmFormFieldValue = Mockito.mock(
			DDMFormFieldValue.class);

		Mockito.when(
			ddmFormFieldValue.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldTypeConstants.GRID
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), valueJSONObject.toString());
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		return ddmFormFieldValue;
	}

	private DDMFormInstance _mockDDMFormInstance(String fieldName)
		throws Exception {

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		DDMFormField ddmFormField = Mockito.mock(DDMFormField.class);

		Mockito.when(
			ddmFormField.getProperty("columns")
		).thenReturn(
			_createDDMFormOptions()
		);

		Mockito.when(
			ddmFormField.getProperty("rows")
		).thenReturn(
			_createDDMFormOptions()
		);

		Mockito.when(
			ddmStructure.getDDMFormField(fieldName)
		).thenReturn(
			ddmFormField
		);

		Mockito.when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		return ddmFormInstance;
	}

	private void _mockDDMFormInstanceRecord()
		throws Exception, PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord = Mockito.mock(
			DDMFormInstanceRecord.class);

		DDMFormInstance ddmFormInstance = _mockDDMFormInstance("field1");

		Mockito.when(
			ddmFormInstanceRecord.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

		Mockito.when(
			_ddmFormInstanceRecordLocalService.getDDMFormInstanceRecord(
				_FORM_INSTANCE_RECORD_ID)
		).thenReturn(
			ddmFormInstanceRecord
		);
	}

	private static final long _FORM_INSTANCE_RECORD_ID = 0;

	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService = Mockito.mock(
			DDMFormInstanceRecordLocalService.class);
	private final GridDDMFormFieldTypeReportProcessor
		_gridDDMFormFieldTypeReportProcessor =
			new GridDDMFormFieldTypeReportProcessor();

}