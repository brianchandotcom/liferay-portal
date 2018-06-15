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

package com.liferay.dynamic.data.lists.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.util.DDL;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Renato Rego
 */
@RunWith(Arquillian.class)
public class DDLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetDDLRecordAsJSONObject() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("TextField1");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				"TextField1", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		Group group = GroupTestUtil.addGroup();

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		JSONObject jsonObject = _ddl.getRecordJSONObject(
			record, false, ddmForm.getDefaultLocale());

		Assert.assertEquals(
			StringPool.BLANK, jsonObject.getString("TextField1"));
	}

	@Test
	public void testGetDDLRecordAsJSONObjectWithRepeatableField()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"TextField1", false, true, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue0 =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"TextField1", "Text0");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue0);

		DDMFormFieldValue ddmFormFieldValue1 =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"TextField1", "Text1");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue1);

		Group group = GroupTestUtil.addGroup();

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		JSONObject jsonObject = _ddl.getRecordJSONObject(
			record, false, ddmForm.getDefaultLocale());

		Assert.assertEquals("Text0, Text1", jsonObject.getString("TextField1"));
	}

	@Inject
	private static DDL _ddl;

}