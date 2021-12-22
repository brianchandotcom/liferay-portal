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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class DDMFormReportDataUtilTest extends BaseDDMTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpJSONFactoryUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testGetFieldsJSONArray() throws Exception {
		JSONAssert.assertEquals(
			JSONUtil.put(
				JSONUtil.put(
					"columns", JSONFactoryUtil.createJSONObject()
				).put(
					"label", "TextField"
				).put(
					"name", "TextField"
				).put(
					"options", JSONFactoryUtil.createJSONObject()
				).put(
					"rows", JSONFactoryUtil.createJSONObject()
				).put(
					"type", "text"
				)
			).toString(),
			String.valueOf(
				DDMFormReportDataUtil.getFieldsJSONArray(
					_mockDDMFormInstanceReport())),
			JSONCompareMode.STRICT_ORDER);
	}

	@Test
	public void testGetFieldValuesJSONArray() throws Exception {
		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			DDMFormTestUtil.createDDMForm("TextField"));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"TextField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"Test 1", LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"TextField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"Test 2", LocaleUtil.US)));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"TextField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"Test 3", "Teste 3", LocaleUtil.BRAZIL));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"nestedTextField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"Test 4", "Teste 4", LocaleUtil.BRAZIL)));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = ListUtil.fromArray(
			_mockDDMFormInstanceRecord(ddmFormValues));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				"Test 1", "Test 2", "Teste 3"
			).toString(),
			String.valueOf(
				DDMFormReportDataUtil.getFieldValuesJSONArray(
					ddmFormInstanceRecords, "TextField")),
			JSONCompareMode.STRICT_ORDER);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"Teste 4"
			).toString(),
			String.valueOf(
				DDMFormReportDataUtil.getFieldValuesJSONArray(
					ddmFormInstanceRecords, "nestedTextField")),
			JSONCompareMode.STRICT_ORDER);
	}

	@Test
	public void testGetTotalItems() throws Exception {
		Assert.assertEquals(0, DDMFormReportDataUtil.getTotalItems(null));
		Assert.assertEquals(
			10,
			DDMFormReportDataUtil.getTotalItems(_mockDDMFormInstanceReport()));
	}

	private DDMFormInstance _mockDDMFormInstance() throws Exception {
		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
			ddmFormInstance.getDDMForm()
		).thenReturn(
			DDMFormTestUtil.createDDMForm("TextField")
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceRecord _mockDDMFormInstanceRecord(
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		return ddmFormInstanceRecord;
	}

	private DDMFormInstanceReport _mockDDMFormInstanceReport()
		throws Exception {

		DDMFormInstanceReport ddmFormInstanceReport = mock(
			DDMFormInstanceReport.class);

		when(
			ddmFormInstanceReport.getData()
		).thenReturn(
			JSONUtil.put(
				"totalItems", 10
			).toString()
		);

		DDMFormInstance ddmFormInstance = _mockDDMFormInstance();

		when(
			ddmFormInstanceReport.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

		return ddmFormInstanceReport;
	}

}