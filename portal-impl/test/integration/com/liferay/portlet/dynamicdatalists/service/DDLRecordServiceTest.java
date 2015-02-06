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

package com.liferay.portlet.dynamicdatalists.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordSetTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormValuesTestUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@Sync
public class DDLRecordServiceTest extends BaseDDLServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_recordSetTestHelper = new DDLRecordSetTestHelper(group);
	}

	@Test
	public void testAddRecordWithLocalizedTextField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", true, false));

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedTextDDMFormFieldValue("Name", "Joe Bloggs"));

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testAddRecordWithNestedFieldAndSeparatorAsParentField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField separatorDDMFormField = createSeparatorDDMFormField(
			"Separator");

		separatorDDMFormField.addNestedDDMFormField(
			createTextDDMFormField("Name", true, false));

		ddmForm.addDDMFormField(separatorDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue separatorDDMFormFieldValue =
			createSeparatorDDMFormFieldValue("Separator");

		separatorDDMFormFieldValue.addNestedDDMFormFieldValue(
			createLocalizedTextDDMFormFieldValue("Name", "Joe Bloggs"));

		expectedDDMFormValues.addDDMFormFieldValue(separatorDDMFormFieldValue);

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testAddRecordWithNestedFieldsAndTextAsParentField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		DDMFormField parentDDMFormField = createTextDDMFormField(
			"Name", true, true);

		parentDDMFormField.addNestedDDMFormField(
			createTextDDMFormField("Phone", false, true));

		ddmForm.addDDMFormField(parentDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue scottDDMFormFieldValue =
			createLocalizedTextDDMFormFieldValue("Name", "Scott Joplin");

		scottDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedTextDDMFormFieldValue("Phone", "12"));

		scottDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedTextDDMFormFieldValue("Phone", "34"));

		expectedDDMFormValues.addDDMFormFieldValue(scottDDMFormFieldValue);

		DDMFormFieldValue louisDDMFormFieldValue =
			createLocalizedTextDDMFormFieldValue("Name", "Louis Armstrong");

		louisDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedTextDDMFormFieldValue("Phone", "56"));

		louisDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedTextDDMFormFieldValue("Phone", "78"));

		expectedDDMFormValues.addDDMFormFieldValue(louisDDMFormFieldValue);

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testAddRecordWithRepeatableTextField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", true, true));

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedTextDDMFormFieldValue("Name", "Joe Bloggs I"));

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedTextDDMFormFieldValue("Name", "Joe Bloggs II"));

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedTextDDMFormFieldValue("Name", "Joe Bloggs III"));

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testAddRecordWithUnlocalizedAndUnrepeatableTextField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", false, false));

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		expectedDDMFormValues.addDDMFormFieldValue(
			createUnlocalizedTextDDMFormFieldValue("Name", "Joe Bloggs"));

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testPublishRecordDraftWithoutChanges() throws Exception {
		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", true, false));

		DDLRecordSet recordSet = addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			group, recordSet);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createLocalizedTextDDMFormFieldValue("Name", "Joe Bloggs"));

		DDLRecord record = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_SAVE_DRAFT);

		Assert.assertEquals(WorkflowConstants.STATUS_DRAFT, record.getStatus());

		DDLRecordVersion recordVersion = record.getRecordVersion();

		Assert.assertTrue(recordVersion.isDraft());

		record = updateRecord(
			record.getRecordId(), record.getDDMFormValues(),
			WorkflowConstants.ACTION_PUBLISH);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, record.getStatus());

		recordVersion = record.getRecordVersion();

		Assert.assertTrue(recordVersion.isApproved());
	}

	protected DDLRecordSet addRecordSet(DDMForm ddmForm) throws Exception {
		String definition = DDMFormXSDSerializerUtil.serialize(ddmForm);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			definition, StorageType.JSON.toString());

		return _recordSetTestHelper.addRecordSet(ddmStructure);
	}

	protected void assertEquals(
			DDMFormValues expectedDDMFormValues,
			DDMFormValues actualDDMFormValues)
		throws Exception {

		String expectedSerializedDDMFormValues =
			DDMFormValuesJSONSerializerUtil.serialize(expectedDDMFormValues);

		String actualSerializedDDMFormValues =
			DDMFormValuesJSONSerializerUtil.serialize(actualDDMFormValues);

		JSONAssert.assertEquals(
			expectedSerializedDDMFormValues, actualSerializedDDMFormValues,
			false);
	}

	protected void assertRecordDDMFormValues(
			DDMForm ddmForm, DDMFormValues expectedDDMFormValues)
		throws Exception, PortalException {

		DDLRecordSet recordSet = addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			expectedDDMFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecord actualRecord = DDLRecordLocalServiceUtil.getRecord(
			record.getRecordId());

		DDMFormValues actualDDMFormValues = actualRecord.getDDMFormValues();

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(LocaleUtil.US);

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		Set<Locale> availableLocales =
			DDMFormValuesTestUtil.createAvailableLocales(LocaleUtil.US);

		return DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, availableLocales, LocaleUtil.US);
	}

	protected DDMFormFieldValue createLocalizedTextDDMFormFieldValue(
		String name, String enValue) {

		return DDMFormValuesTestUtil.createLocalizedTextDDMFormFieldValue(
			name, enValue);
	}

	protected DDMFormField createSeparatorDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "separator");

		ddmFormField.setDataType(StringPool.BLANK);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	protected DDMFormFieldValue createSeparatorDDMFormFieldValue(String name) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());

		return ddmFormFieldValue;
	}

	protected DDMFormField createTextDDMFormField(
		String name, boolean localizable, boolean repeatable) {

		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	protected DDMFormFieldValue createTextDDMFormFieldValue(
		String name, Value value) {

		return DDMFormValuesTestUtil.createTextDDMFormFieldValue(name, value);
	}

	protected DDMFormFieldValue createUnlocalizedTextDDMFormFieldValue(
		String name, String value) {

		return DDMFormValuesTestUtil.createUnlocalizedTextDDMFormFieldValue(
			name, value);
	}

	protected DDLRecord updateRecord(
			long recordId, DDMFormValues ddmFormValues, int workflowAction)
		throws Exception {

		ServiceContext serviceContext = DDLRecordTestUtil.getServiceContext(
			workflowAction);

		return DDLRecordLocalServiceUtil.updateRecord(
			TestPropsValues.getUserId(), recordId, false,
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			serviceContext);
	}

	private DDLRecordSetTestHelper _recordSetTestHelper;

}