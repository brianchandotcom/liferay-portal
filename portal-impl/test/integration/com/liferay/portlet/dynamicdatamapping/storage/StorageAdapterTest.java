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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.BaseDDMServiceTestCase;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class StorageAdapterTest extends BaseDDMServiceTestCase {

	@Test
	public void testBooleanField() throws Exception {
		String definition = readText("ddm-structure-boolean-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Boolean Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {true, true, true});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {false, false, false});

		dataMap.put(_ptLocale, ptValues);

		Field booleanField = new Field(
			structure.getStructureId(), "boolean", dataMap, _enLocale);

		fields.put(booleanField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testDateField() throws Exception {
		String definition = readText("ddm-structure-date-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Date Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		Date date1 = PortalUtil.getDate(0, 1, 2013);
		Date date2 = PortalUtil.getDate(0, 2, 2013);

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {date1, date2});

		dataMap.put(_enLocale, enValues);

		Date date3 = PortalUtil.getDate(0, 3, 2013);
		Date date4 = PortalUtil.getDate(0, 4, 2013);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {date3, date4});

		dataMap.put(_ptLocale, ptValues);

		Field dateField = new Field(
			structure.getStructureId(), "date", dataMap, _enLocale);

		fields.put(dateField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testDecimalField() throws Exception {
		String definition = readText("ddm-structure-decimal-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Decimal Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {1.1, 1.2, 1.3});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {2.1, 2.2, 2.3});

		dataMap.put(_ptLocale, ptValues);

		Field decimalField = new Field(
			structure.getStructureId(), "decimal", dataMap, _enLocale);

		fields.put(decimalField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testDocLibraryField() throws Exception {
		String definition = readText("ddm-structure-doc-lib-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Documents and Media Field Structure",
			definition, StorageType.XML.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		FileEntry file1 = DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1.txt");

		String file1Value = getDocLibraryFieldValue(file1);

		FileEntry file2 = DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2.txt");

		String file2Value = getDocLibraryFieldValue(file2);

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {file1Value, file2Value});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {file1Value});

		dataMap.put(_ptLocale, ptValues);

		Field documentLibraryField = new Field(
			structure.getStructureId(), "doc_library", dataMap, _enLocale);

		fields.put(documentLibraryField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testIntegerField() throws Exception {
		String definition = readText("ddm-structure-integer-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Integer Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {1, 2, 3});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {3, 4, 5});

		dataMap.put(_ptLocale, ptValues);

		Field integerField = new Field(
			structure.getStructureId(), "integer", dataMap, _enLocale);

		fields.put(integerField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testLinkToPageField() throws Exception {
		String definition = readText("ddm-structure-link-to-page-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Link to Page Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {
				"{\"layoutId\":\"1\",\"privateLayout\":false}"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {
				"{\"layoutId\":\"2\",\"privateLayout\":true}"});

		dataMap.put(_ptLocale, ptValues);

		Field linkToPageField = new Field(
			structure.getStructureId(), "link_to_page", dataMap, _enLocale);

		fields.put(linkToPageField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testNumberField() throws Exception {
		String definition = readText("ddm-structure-number-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Number Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {1, 1.5f, 2});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {3, 3.5f, 4});

		dataMap.put(_ptLocale, ptValues);

		Field numberField = new Field(
			structure.getStructureId(), "number", dataMap, _enLocale);

		fields.put(numberField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testRadioField() throws Exception {
		String definition = readText("ddm-structure-radio-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Radio Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {"[\"value 1\"]", "[\"value 2\"]"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"[\"value 2\"]", "[\"value 3\"]"});

		dataMap.put(_ptLocale, ptValues);

		Field radioField = new Field(
			structure.getStructureId(), "radio", dataMap, _enLocale);

		fields.put(radioField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testSelectField() throws Exception {
		String definition = readText("ddm-structure-select-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Select Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {"[\"value 1\",\"value 2\"]", "[\"value 3\"]"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"[\"value 2\"]", "[\"value 3\"]"});

		dataMap.put(_ptLocale, ptValues);

		Field selectField = new Field(
			structure.getStructureId(), "select", dataMap, _enLocale);

		fields.put(selectField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testTextField() throws Exception {
		String definition = readText("ddm-structure-text-field.xsd");

		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, null, "Text Field Structure", definition,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			new HashMap<Locale, List<Serializable>>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {"one", "two", "three"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"um", "dois", "tres"});

		dataMap.put(_ptLocale, ptValues);

		Field textField = new Field(
			structure.getStructureId(), "text", dataMap, _enLocale);

		fields.put(textField);

		validate(structure.getStructureId(), fields);
	}

	protected long create(
			StorageAdapter storageAdapter, long ddmStructureId, Fields fields)
		throws Exception {

		return storageAdapter.create(
			TestPropsValues.getCompanyId(), ddmStructureId, fields,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected String getDocLibraryFieldValue(FileEntry fileEntry) {
		StringBundler sb = new StringBundler(7);

		sb.append("{\"groupId\":");
		sb.append(fileEntry.getGroupId());
		sb.append(",\"uuid\":\"");
		sb.append(fileEntry.getUuid());
		sb.append("\",\"version\":\"");
		sb.append(fileEntry.getVersion());
		sb.append("\"}");

		return sb.toString();
	}

	protected void validate(long ddmStructureId, Fields fields)
		throws Exception {

		// Expando

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String expectedFieldsString = jsonSerializer.serializeDeep(fields);

		long classPK = create(_expandoStorageAdapater, ddmStructureId, fields);

		Fields actualFields = _expandoStorageAdapater.getFields(classPK);

		Assert.assertEquals(
			expectedFieldsString, jsonSerializer.serializeDeep(actualFields));

		// XML

		classPK = create(_xmlStorageAdapater, ddmStructureId, fields);

		actualFields = _xmlStorageAdapater.getFields(classPK);

		Assert.assertEquals(
			expectedFieldsString, jsonSerializer.serializeDeep(actualFields));
	}

	private static final long _CLASS_NAME_ID = PortalUtil.getClassNameId(
		DDLRecordSet.class);

	private final Locale _enLocale = LocaleUtil.fromLanguageId("en_US");
	private final StorageAdapter _expandoStorageAdapater =
		new ExpandoStorageAdapter();
	private final Locale _ptLocale = LocaleUtil.fromLanguageId("pt_BR");
	private final StorageAdapter _xmlStorageAdapater = new XMLStorageAdapter();

}