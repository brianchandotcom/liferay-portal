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

package com.liferay.portlet.dynamicdatalists.util;

import java.io.File;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordSetTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestHelper;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;

/**
 * @author Renato Rego
 */
@Sync
public class DDLExporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);
	
	@Before
	public void setUp() throws Exception {

		_availableLocales = DDLRecordTestUtil.createAvailableLocales(Locale.US);
		_defaultLocale = Locale.US;
		_group = GroupTestUtil.addGroup();
		
		setUpFieldTypes();
		setUpFieldDataTypes();
		setUpFieldValues();
	}
	
	@After
	public void tearDown() throws Exception {

		FileUtil.delete("export-xml-file.xml");
		FileUtil.delete("export-csv-file.csv");
	}
	
	@Test
	public void testLocalizedXMLExport() throws Exception {

		boolean localizedTest = true;
		
		DDMForm ddmForm = DDLRecordTestUtil.createDDMForm(
				_availableLocales, _defaultLocale);
		
		createAllDDMFormFields(ddmForm, localizedTest);
		
		DDMFormValues ddmFormValues = 
				DDLRecordTestUtil.createDDMFormValues(
						ddmForm, _availableLocales, _defaultLocale);
		
		createAllDDMFormFieldValues(ddmFormValues, localizedTest);
		
		DDLRecordSet recordSet = addRecordSet(ddmForm);
		
		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
				_group, recordSet);
		
		recordTestHelper.addRecord(
				ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
		
		DDLXMLExporter ddlXMLExporter = new DDLXMLExporter();
		
		byte[] exportFileBytes = ddlXMLExporter.export(
				recordSet.getRecordSetId());
		File exportFile = new File("export-xml-file.xml");
		
		FileUtil.write(exportFile, exportFileBytes);
		
		String expectedFileContent = read("expected-xml-file.xml");
		String actualFileContent = FileUtil.read(exportFile);
		
		Assert.assertEquals(expectedFileContent, actualFileContent);
	}

	@Test
	public void testUnlocalizedXMLExport() throws Exception {

		boolean localizedTest = false;
		
		DDMForm ddmForm = DDLRecordTestUtil.createDDMForm(
				_availableLocales, _defaultLocale);
		
		createAllDDMFormFields(ddmForm, localizedTest);
		
		DDMFormValues ddmFormValues = 
				DDLRecordTestUtil.createDDMFormValues(
						ddmForm, _availableLocales, _defaultLocale);
		
		createAllDDMFormFieldValues(ddmFormValues, localizedTest);
		
		DDLRecordSet recordSet = addRecordSet(ddmForm);
		
		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
				_group, recordSet);
		
		recordTestHelper.addRecord(
				ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
		
		DDLXMLExporter ddlXMLExporter = new DDLXMLExporter();
		
		byte[] exportFileBytes = ddlXMLExporter.export(
				recordSet.getRecordSetId());
		File exportFile = new File("export-xml-file.xml");
		
		FileUtil.write(exportFile, exportFileBytes);
		
		String expectedFileContent = read("expected-xml-file.xml");
		String actualFileContent = FileUtil.read(exportFile);
		
		Assert.assertEquals(expectedFileContent, actualFileContent);
	}
	
	@Test
	public void testLocalizedCSVExport() throws Exception {
		
		boolean localizedTest = true;
		
		DDMForm ddmForm = DDLRecordTestUtil.createDDMForm(
				_availableLocales, _defaultLocale);
		
		createAllDDMFormFields(ddmForm, localizedTest);
		
		DDMFormValues ddmFormValues = 
				DDLRecordTestUtil.createDDMFormValues(
						ddmForm, _availableLocales, _defaultLocale);
		
		createAllDDMFormFieldValues(ddmFormValues, localizedTest);
		
		DDLRecordSet recordSet = addRecordSet(ddmForm);
		
		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
				_group, recordSet);
		
		recordTestHelper.addRecord(
				ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
		
		DDLCSVExporter ddlCSVExporter = new DDLCSVExporter();
		
		byte[] exportFileBytes = ddlCSVExporter.export(
				recordSet.getRecordSetId());
		File exportFile = new File("export-csv-file.csv");
		
		FileUtil.write(exportFile, exportFileBytes);
		
		String expectedFileContent = read(
				"expected-csv-file.csv") + StringPool.NEW_LINE;
		String actualFileContent = FileUtil.read(exportFile);
		
		Assert.assertEquals(expectedFileContent, actualFileContent);
	}

	@Test
	public void testUnlocalizedCSVExport() throws Exception {
		
		boolean localizedTest = false;
		
		DDMForm ddmForm = DDLRecordTestUtil.createDDMForm(
				_availableLocales, _defaultLocale);
		
		createAllDDMFormFields(ddmForm, localizedTest);
		
		DDMFormValues ddmFormValues = 
				DDLRecordTestUtil.createDDMFormValues(
						ddmForm, _availableLocales, _defaultLocale);
		
		createAllDDMFormFieldValues(ddmFormValues, localizedTest);
		
		DDLRecordSet recordSet = addRecordSet(ddmForm);
		
		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
				_group, recordSet);
		
		recordTestHelper.addRecord(
				ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
		
		DDLCSVExporter ddlCSVExporter = new DDLCSVExporter();
		
		byte[] exportFileBytes = ddlCSVExporter.export(
				recordSet.getRecordSetId());
		File exportFile = new File("export-csv-file.csv");
		
		FileUtil.write(exportFile, exportFileBytes);
		
		String expectedFileContent = read(
				"expected-csv-file.csv") + StringPool.NEW_LINE;
		String actualFileContent = FileUtil.read(exportFile);
		
		Assert.assertEquals(expectedFileContent, actualFileContent);
	}
	
	private void createAllDDMFormFields(
			DDMForm ddmForm, boolean localizedFields) {
		
		for (FieldTypes type : FieldTypes.values()) {
		
			String fieldName = "Field" + type.ordinal();
			String fieldType = _fieldTypes.get(type);
			String fieldDataType = _fieldDataTypes.get(type);
			
			ddmForm.addDDMFormField(DDLRecordTestUtil.createDDMFormField(
					fieldName, fieldName, fieldType, fieldDataType,
					localizedFields, false, false));
		}
	}
	
	private void createAllDDMFormFieldValues(
			DDMFormValues ddmFormValues, boolean localizedValues) {
		
		for (FieldTypes type : FieldTypes.values()) {
			
			String fieldName = "Field" + type.ordinal();
			Value fieldValue = createFieldValue(
					_fieldValues.get(type), localizedValues);
			
			ddmFormValues.addDDMFormFieldValue(
					DDLRecordTestUtil.createDDMFormFieldValue(
							fieldName, fieldValue));
		}
	}
	
	private Value createFieldValue(String valueString, boolean isLocalized) {
		
		Value value;
		
		if (isLocalized) {
			value = new LocalizedValue(_defaultLocale);
			
			value.addString(_defaultLocale, valueString);
		}
		else {
			value = new UnlocalizedValue(valueString);
		}
		
		return value;
	}
	
	private DDLRecordSet addRecordSet(DDMForm ddmForm) throws Exception {
		
		String definition = DDMFormXSDSerializerUtil.serialize(ddmForm);

		DDMStructureTestHelper ddmStructureTestHelper = 
				new DDMStructureTestHelper(_group);
		
		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			definition, StorageType.JSON.toString());

		DDLRecordSetTestHelper recordSetTestHelper = 
				new DDLRecordSetTestHelper(_group);
		
		return recordSetTestHelper.addRecordSet(ddmStructure);
	}
	
	private String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), getBasePath() + fileName);
	}
	
	private String getBasePath() {
		return "com/liferay/portlet/dynamicdatalists/dependencies/";
	}
	
	private Map<FieldTypes, String> setUpFieldValues() throws Exception {
		
		_fieldValues = Collections.synchronizedMap(
				new EnumMap<FieldTypes, String>(FieldTypes.class));

		_fieldValues.put(FieldTypes.BOOLEAN, "false");
		_fieldValues.put(FieldTypes.DATE, "1/1/70");
		_fieldValues.put(FieldTypes.DECIMAL, "1.0");
		_fieldValues.put(FieldTypes.DOCUMENTS_AND_MEDIA,
				createDocumentsAndMediaFieldValue());
		_fieldValues.put(FieldTypes.GEOLOCATION, createGeolocationFieldValue());
		_fieldValues.put(FieldTypes.HTML, "html field content");
		_fieldValues.put(FieldTypes.INTEGER, "2");
		_fieldValues.put(FieldTypes.LINK_TO_PAGE, createLinkToPageFieldValue());
		_fieldValues.put(FieldTypes.NUMBER, "3");
		_fieldValues.put(FieldTypes.RADIO, createRadioAndSelectFieldsValue());
		_fieldValues.put(FieldTypes.SELECT, createRadioAndSelectFieldsValue());
		_fieldValues.put(FieldTypes.TEXT, "text field content");
		_fieldValues.put(FieldTypes.TEXT_BOX, "text box field content");
		
		return _fieldValues;
	}

	private String createDocumentsAndMediaFieldValue() throws Exception {
		
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(_group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"documents_and_media.txt");
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("title", fileEntry.getTitle());
		jsonObject.put("name", fileEntry.getTitle());
		jsonObject.put("uuid", fileEntry.getUuid());
		jsonObject.put("tempFile", "false");
		
		return jsonObject.toString();
	}

	private String createGeolocationFieldValue() throws Exception {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("latitude", "-8.035");
		jsonObject.put("longitude", "-34.918");
		
		return jsonObject.toString();
	}
	
	private String createLinkToPageFieldValue() throws Exception {
		
		Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), "Link to page field content", false);
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		jsonObject.put("layoutId", layout.getLayoutId());
		jsonObject.put("groupId", layout.getGroupId());
		jsonObject.put("privateLayout", layout.getPrivateLayout());
		
		return jsonObject.toString();
	}
	
	private String createRadioAndSelectFieldsValue() throws Exception {
		
		return "[" + StringPool.QUOTE + "value 1" + StringPool.QUOTE + "]";
	}
	
	private Map<FieldTypes, String> setUpFieldTypes() {
		
		_fieldTypes = Collections.synchronizedMap(
				new EnumMap<FieldTypes, String>(FieldTypes.class));

		_fieldTypes.put(FieldTypes.BOOLEAN, DDMFormFieldType.CHECKBOX);
		_fieldTypes.put(FieldTypes.DATE, DDMFormFieldType.DATE);
		_fieldTypes.put(FieldTypes.DECIMAL, DDMFormFieldType.DECIMAL);
		_fieldTypes.put(FieldTypes.DOCUMENTS_AND_MEDIA, 
				DDMFormFieldType.DOCUMENT_LIBRARY);
		_fieldTypes.put(FieldTypes.GEOLOCATION, DDMFormFieldType.GEOLOCATION);
		_fieldTypes.put(FieldTypes.HTML, DDMFormFieldType.TEXT_HTML);
		_fieldTypes.put(FieldTypes.INTEGER, DDMFormFieldType.INTEGER);
		_fieldTypes.put(FieldTypes.LINK_TO_PAGE, DDMFormFieldType.LINK_TO_PAGE);
		_fieldTypes.put(FieldTypes.NUMBER, DDMFormFieldType.NUMBER);
		_fieldTypes.put(FieldTypes.RADIO, DDMFormFieldType.RADIO);
		_fieldTypes.put(FieldTypes.SELECT, DDMFormFieldType.SELECT);
		_fieldTypes.put(FieldTypes.TEXT, DDMFormFieldType.TEXT);
		_fieldTypes.put(FieldTypes.TEXT_BOX, DDMFormFieldType.TEXT_AREA);
		
		return _fieldTypes;
	}

	private Map<FieldTypes, String> setUpFieldDataTypes() {
		
		_fieldDataTypes = Collections.synchronizedMap(
				new EnumMap<FieldTypes, String>(FieldTypes.class));

		_fieldDataTypes.put(FieldTypes.BOOLEAN, "boolean");
		_fieldDataTypes.put(FieldTypes.DATE, "date");
		_fieldDataTypes.put(FieldTypes.DECIMAL, "double");
		_fieldDataTypes.put(FieldTypes.DOCUMENTS_AND_MEDIA, "document-library");
		_fieldDataTypes.put(FieldTypes.GEOLOCATION, "geolocation");
		_fieldDataTypes.put(FieldTypes.HTML, "html");
		_fieldDataTypes.put(FieldTypes.INTEGER, "integer");
		_fieldDataTypes.put(FieldTypes.LINK_TO_PAGE, "link-to-page");
		_fieldDataTypes.put(FieldTypes.NUMBER, "number");
		_fieldDataTypes.put(FieldTypes.RADIO, "string");
		_fieldDataTypes.put(FieldTypes.SELECT, "string");
		_fieldDataTypes.put(FieldTypes.TEXT, "string");
		_fieldDataTypes.put(FieldTypes.TEXT_BOX, "string");
		
		return _fieldDataTypes;
	}
	
	private enum FieldTypes {
		BOOLEAN,
		DATE,
		DECIMAL,
		DOCUMENTS_AND_MEDIA,
		GEOLOCATION,
		HTML,
		INTEGER,
		LINK_TO_PAGE,
		NUMBER,
		RADIO,
		SELECT,
		TEXT,
		TEXT_BOX
	}
	
	private Set<Locale> _availableLocales;
	private Locale _defaultLocale;
	private Group _group;
	private Map<FieldTypes, String> _fieldTypes;
	private Map<FieldTypes, String> _fieldDataTypes;
	private Map<FieldTypes, String> _fieldValues;
}