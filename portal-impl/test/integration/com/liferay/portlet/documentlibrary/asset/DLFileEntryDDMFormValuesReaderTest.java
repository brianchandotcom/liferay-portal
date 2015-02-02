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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.DDMFormValuesReader;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DLFileEntryDDMFormValuesReaderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpGroup();
		setUpFileEntry();
	}

	@Test
	public void testGetDDMFormValues() throws Exception {
		DDMFormValuesReader ddmFormValuesReader =
			new DLFileEntryDDMFormValuesReader(
				_fileEntry, _fileEntry.getFileVersion());

		DDMFormValues expectedDDMFormValues = getExpectedDDMFormValues();

		Assert.assertEquals(
			expectedDDMFormValues, ddmFormValuesReader.getDDMFormValues());
	}

	protected DLFileEntryType addDLFileEntryType(ServiceContext serviceContext)
		throws Exception {

		return DLAppTestUtil.addDLFileEntryType(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), StringPool.BLANK, new long[0],
			serviceContext);
	}

	protected DLFileEntry addFileEntry() throws Exception {
		ServiceContext serviceContext = getServiceContext();

		DLFileEntryType dlFileEntryType = addDLFileEntryType(serviceContext);

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		Map<String, Fields> fieldsMap = createFieldsMap(ddmStructures.get(0));

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			RandomTestUtil.randomBytes());

		return DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			null, null, dlFileEntryType.getFileEntryTypeId(), fieldsMap, null,
			byteArrayInputStream, byteArrayInputStream.available(),
			serviceContext);
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		ddmForm.addDDMFormField(createTextDDMFormField("Text1"));
		ddmForm.addDDMFormField(createTextDDMFormField("Text2"));

		return ddmForm;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String instanceId, String name, String value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(new UnlocalizedValue(value));

		return ddmFormFieldValue;
	}

	protected Field createField(long structureId, String name, String value) {
		Field field  = new Field(structureId, name, value);

		field.setDefaultLocale(LocaleUtil.US);

		return field;
	}

	protected Map<String, Fields> createFieldsMap(DDMStructure ddmStructure) {
		Map<String, Fields> fieldsMap = new HashMap<>();

		Fields fields = new Fields();

		fields.put(
			createField(
				ddmStructure.getStructureId(), "Text1", "Text 1 Value"));
		fields.put(
			createField(
				ddmStructure.getStructureId(), "Text2", "Text 2 Value"));
		fields.put(
			new Field(
				ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
				"Text1_INSTANCE_baga,Text2_INSTANCE_hagt"));

		fieldsMap.put(ddmStructure.getStructureKey(), fields);

		return fieldsMap;
	}

	protected DDMFormField createTextDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.US, name);

		ddmFormField.setLabel(label);
		ddmFormField.setLocalizable(false);

		return ddmFormField;
	}

	protected DDMFormValues getExpectedDDMFormValues() throws Exception {
		DDMFormValues ddmFormValues = new DDMFormValues(null);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("baga", "Text1", "Text 1 Value"));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("hagt", "Text2", "Text 2 Value"));

		return ddmFormValues;
	}

	protected ServiceContext getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		DDMForm ddmForm = createDDMForm();

		serviceContext.setAttribute(
			"definition", DDMFormXSDSerializerUtil.serialize(ddmForm));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		return serviceContext;
	}

	protected void setUpFileEntry() throws Exception, PortalException {
		DLFileEntry dlFileEntry = addFileEntry();

		_fileEntry = DLAppLocalServiceUtil.getFileEntry(
			dlFileEntry.getFileEntryId());
	}

	protected void setUpGroup() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _group;

}