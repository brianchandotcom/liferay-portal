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

package com.liferay.dynamic.data.lists.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.storage.FailStorageAdapter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
@Sync
public class DDLRecordServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			StorageAdapter.class, new FailStorageAdapter());
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Before
	public void setUp() throws Exception {
		_availableLocales = DDMFormTestUtil.createAvailableLocales(Locale.US);
		_defaultLocale = Locale.US;

		_group = GroupTestUtil.addGroup();

		_ddmStructureTestHelper = new DDMStructureTestHelper(
			PortalUtil.getClassNameId(DDLRecordSet.class), _group);
		_recordSetTestHelper = new DDLRecordSetTestHelper(_group);
	}

	@Test(expected = StorageException.class)
	public void testAddRecordWithFailStorage() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		DDLRecordSet ddlRecordSet = addRecordSet(
			ddmForm, FailStorageAdapter.STORAGE_TYPE);

		DDLRecordTestHelper ddlRecordTestHelper = new DDLRecordTestHelper(
			_group, ddlRecordSet);

		ddlRecordTestHelper.addRecord();
	}

	@Test
	public void testAddRecordWithLocalizedTextField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", true, false));

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedDDMFormFieldValue("Name", "Joe Bloggs"));

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
			createLocalizedDDMFormFieldValue("Name", "Joe Bloggs"));

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
			createLocalizedDDMFormFieldValue("Name", "Scott Joplin");

		scottDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedDDMFormFieldValue("Phone", "12"));

		scottDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedDDMFormFieldValue("Phone", "34"));

		expectedDDMFormValues.addDDMFormFieldValue(scottDDMFormFieldValue);

		DDMFormFieldValue louisDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("Name", "Louis Armstrong");

		louisDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedDDMFormFieldValue("Phone", "56"));

		louisDDMFormFieldValue.addNestedDDMFormFieldValue(
			createUnlocalizedDDMFormFieldValue("Phone", "78"));

		expectedDDMFormValues.addDDMFormFieldValue(louisDDMFormFieldValue);

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testAddRecordWithRepeatableTextField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", true, true));

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedDDMFormFieldValue("Name", "Joe Bloggs I"));

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedDDMFormFieldValue("Name", "Joe Bloggs II"));

		expectedDDMFormValues.addDDMFormFieldValue(
			createLocalizedDDMFormFieldValue("Name", "Joe Bloggs III"));

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testAddRecordWithUnlocalizedAndUnrepeatableTextField()
		throws Exception {

		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", false, false));

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		expectedDDMFormValues.addDDMFormFieldValue(
			createUnlocalizedDDMFormFieldValue("Name", "Joe Bloggs"));

		assertRecordDDMFormValues(ddmForm, expectedDDMFormValues);
	}

	@Test
	public void testPublishRecordDraftWithoutChanges() throws Exception {
		DDMForm ddmForm = createDDMForm();

		ddmForm.addDDMFormField(createTextDDMFormField("Name", true, false));

		DDLRecordSet recordSet = addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createLocalizedDDMFormFieldValue("Name", "Joe Bloggs"));

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
		return addRecordSet(ddmForm, StorageType.JSON.toString());
	}

	protected DDLRecordSet addRecordSet(DDMForm ddmForm, String storageType)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureTestHelper.addStructure(
			ddmForm, storageType);

		return _recordSetTestHelper.addRecordSet(ddmStructure);
	}

	protected void assertRecordDDMFormValues(
			DDMForm ddmForm, DDMFormValues expectedDDMFormValues)
		throws Exception, PortalException {

		DDLRecordSet recordSet = addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		DDLRecord record = recordTestHelper.addRecord(
			expectedDDMFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLRecord actualRecord = DDLRecordLocalServiceUtil.getRecord(
			record.getRecordId());

		DDMFormValues actualDDMFormValues = actualRecord.getDDMFormValues();

		Assert.assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(LocaleUtil.US);

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, Value value) {

		return DDMFormValuesTestUtil.createDDMFormFieldValue(name, value);
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		Set<Locale> availableLocales =
			DDMFormValuesTestUtil.createAvailableLocales(LocaleUtil.US);

		return DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, availableLocales, LocaleUtil.US);
	}

	protected DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, String enValue) {

		return DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
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

	protected DDMFormFieldValue createUnlocalizedDDMFormFieldValue(
		String name, String value) {

		return DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
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

	private static ServiceRegistration<StorageAdapter> _serviceRegistration;

	private Set<Locale> _availableLocales;
	private DDMStructureTestHelper _ddmStructureTestHelper;
	private Locale _defaultLocale;

	@DeleteAfterTestRun
	private Group _group;

	private DDLRecordSetTestHelper _recordSetTestHelper;

}