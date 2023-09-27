/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.json.JSONFactoryImpl;
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
public class CheckboxMultipleDDMFormFieldTypeReportProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_checkboxMultipleDDMFormFieldTypeReportProcessor =
			new CheckboxMultipleDDMFormFieldTypeReportProcessor();

		ReflectionTestUtil.setFieldValue(
			_checkboxMultipleDDMFormFieldTypeReportProcessor, "_jsonFactory",
			new JSONFactoryImpl());
	}

	@Test
	public void testProcessDDMFormInstanceReportOnDeleteEvent()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = Mockito.mock(
			DDMFormFieldValue.class);

		Mockito.when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		Mockito.when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE
		);

		Value value = new LocalizedValue();

		value.addString(
			value.getDefaultLocale(),
			JSONUtil.put(
				"option1"
			).toString());
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
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
					"type", DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE
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

		DDMFormFieldValue ddmFormFieldValue = Mockito.mock(
			DDMFormFieldValue.class);

		Mockito.when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		Mockito.when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE
		);

		Value value = new LocalizedValue();

		value.addString(
			value.getDefaultLocale(),
			JSONUtil.put(
				"option1"
			).toString());
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
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
					"type", DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(1, processedFieldJSONObject.getInt("totalEntries"));

		Assert.assertEquals(
			DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE,
			processedFieldJSONObject.getString("type"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(1, valuesJSONObject.getLong("option1"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithExistingData()
		throws Exception {

		DDMFormFieldValue ddmFormFieldValue = Mockito.mock(
			DDMFormFieldValue.class);

		Mockito.when(
			ddmFormFieldValue.getName()
		).thenReturn(
			"field1"
		);

		Mockito.when(
			ddmFormFieldValue.getType()
		).thenReturn(
			DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE
		);

		Value value = new LocalizedValue();

		value.addString(
			value.getDefaultLocale(),
			JSONUtil.putAll(
				"option1", "option2"
			).toString());
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
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
					"type", DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE
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

	private CheckboxMultipleDDMFormFieldTypeReportProcessor
		_checkboxMultipleDDMFormFieldTypeReportProcessor;

}