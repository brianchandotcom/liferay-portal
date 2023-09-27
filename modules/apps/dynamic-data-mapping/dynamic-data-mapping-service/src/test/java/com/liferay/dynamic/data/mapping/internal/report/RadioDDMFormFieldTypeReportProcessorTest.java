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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcos Martins
 */
public class RadioDDMFormFieldTypeReportProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
			DDMFormFieldTypeConstants.RADIO
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "option1");
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_radioDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldTypeConstants.RADIO
				).put(
					"values", JSONFactoryUtil.createJSONObject("{option1 : 1}")
				),
				0, DDMFormInstanceReportConstants.EVENT_DELETE_RECORD_VERSION);

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
			DDMFormFieldTypeConstants.RADIO
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "option1");
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_radioDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldTypeConstants.RADIO
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldTypeConstants.RADIO,
			processedFieldJSONObject.getString("type"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(1, valuesJSONObject.getLong("option1"));
	}

	@Test
	public void testProcessDDMFormInstanceReportWithEmptyField()
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
			DDMFormFieldTypeConstants.RADIO
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "");
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_radioDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldTypeConstants.RADIO
				).put(
					"values", JSONFactoryUtil.createJSONObject()
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		Assert.assertEquals(
			DDMFormFieldTypeConstants.RADIO,
			processedFieldJSONObject.getString("type"));

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertFalse(valuesJSONObject.has(""));
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
			DDMFormFieldTypeConstants.RADIO
		);

		Value value = new LocalizedValue();

		value.addString(value.getDefaultLocale(), "option1");
		value.setDefaultLocale(LocaleUtil.US);

		Mockito.when(
			ddmFormFieldValue.getValue()
		).thenReturn(
			value
		);

		JSONObject processedFieldJSONObject =
			_radioDDMFormFieldTypeReportProcessor.process(
				ddmFormFieldValue,
				JSONUtil.put(
					"type", DDMFormFieldTypeConstants.RADIO
				).put(
					"values", JSONFactoryUtil.createJSONObject("{option1 : 1}")
				),
				0, DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION);

		JSONObject valuesJSONObject = processedFieldJSONObject.getJSONObject(
			"values");

		Assert.assertEquals(2, valuesJSONObject.getLong("option1"));
	}

	private final RadioDDMFormFieldTypeReportProcessor
		_radioDDMFormFieldTypeReportProcessor =
			new RadioDDMFormFieldTypeReportProcessor();

}