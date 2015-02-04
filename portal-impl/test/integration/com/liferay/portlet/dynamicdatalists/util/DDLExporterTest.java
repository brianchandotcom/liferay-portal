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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordSetTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestHelper;

import java.io.File;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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

		setUpDDMFormFieldDataTypes();
		setUpDDMFormFieldValues();
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.delete("record-set.xml");
		FileUtil.delete("record-set.csv");
	}

	@Test
	public void testCSVExport() throws Exception {
		DDMForm ddmForm = DDLRecordTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		createDDMFormFields(ddmForm);

		DDMFormValues ddmFormValues = DDLRecordTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		createDDMFormFieldValues(ddmFormValues);

		DDLRecordSet recordSet = addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLExporter ddlExporter = new DDLCSVExporter();

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		File file = new File("record-set.csv");

		FileUtil.write(file, bytes);

		String expectedFileContent = read("test-record-set-export.csv");
		String actualFileContent = FileUtil.read(file);

		Assert.assertEquals(expectedFileContent, actualFileContent);
	}

	@Test
	public void testXMLExport() throws Exception {
		DDMForm ddmForm = DDLRecordTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		createDDMFormFields(ddmForm);

		DDMFormValues ddmFormValues = DDLRecordTestUtil.createDDMFormValues(
			ddmForm, _availableLocales, _defaultLocale);

		createDDMFormFieldValues(ddmFormValues);

		DDLRecordSet recordSet = addRecordSet(ddmForm);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			_group, recordSet);

		recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		DDLExporter ddlExporter = new DDLXMLExporter();

		byte[] bytes = ddlExporter.export(recordSet.getRecordSetId());

		File file = new File("record-set.xml");

		FileUtil.write(file, bytes);

		String expectedFileContent = read("test-record-set-export.xml");
		String actualFileContent = FileUtil.read(file);

		Assert.assertEquals(expectedFileContent, actualFileContent);
	}

	protected DDLRecordSet addRecordSet(DDMForm ddmForm) throws Exception {
		String definition = DDMFormXSDSerializerUtil.serialize(ddmForm);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(_group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			definition, StorageType.JSON.toString());

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group);

		return recordSetTestHelper.addRecordSet(ddmStructure);
	}

	protected DDMFormField createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = DDLRecordTestUtil.createDDMFormField(
			name, name, type, dataType, true, false, false);

		if (type.equals("radio") || type.equals("select")) {
			setDDMFormFieldOptions(ddmFormField, 3);
		}

		return ddmFormField;
	}

	protected void createDDMFormFields(DDMForm ddmForm) {
		for (DDMFormFieldType ddmFormFieldType : DDMFormFieldType.values()) {
			String fieldName = "Field" + ddmFormFieldType.ordinal();

			ddmForm.addDDMFormField(
				createDDMFormField(
					fieldName, ddmFormFieldType.getValue(),
					_ddmFormFieldDataTypes.get(ddmFormFieldType)));
		}
	}

	protected Value createDDMFormFieldValue(String valueString) {
		Value value = new LocalizedValue(_defaultLocale);

		value.addString(_defaultLocale, valueString);

		return value;
	}

	protected void createDDMFormFieldValues(DDMFormValues ddmFormValues) {
		for (DDMFormFieldType type : DDMFormFieldType.values()) {
			String fieldName = "Field" + type.ordinal();

			Value fieldValue = createDDMFormFieldValue(_fieldValues.get(type));

			ddmFormValues.addDDMFormFieldValue(
				DDLRecordTestUtil.createDDMFormFieldValue(
					fieldName, fieldValue));
		}
	}

	protected String createDocumentLibraryDDMFormFieldValue() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("title", fileEntry.getTitle());
		jsonObject.put("name", fileEntry.getTitle());
		jsonObject.put("uuid", fileEntry.getUuid());
		jsonObject.put("tempFile", "false");

		return jsonObject.toString();
	}

	protected String createGeolocationDDMFormFieldValue() throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("latitude", "-8.035");
		jsonObject.put("longitude", "-34.918");

		return jsonObject.toString();
	}

	protected String createLinkToPageDDMFormFieldValue() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Link to Page content", false);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", layout.getGroupId());
		jsonObject.put("layoutId", layout.getLayoutId());
		jsonObject.put("privateLayout", layout.getPrivateLayout());

		return jsonObject.toString();
	}

	protected String createListDDMFormFieldValue() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put("Value 1");

		return jsonArray.toString();
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/portlet/dynamicdatalists/dependencies/" + fileName);
	}

	protected void setDDMFormFieldOptions(
		DDMFormField ddmFormField, int availableOptions) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 1; i <= availableOptions; i++) {
			ddmFormFieldOptions.addOptionLabel(
				"Value " + i, LocaleUtil.US, "Option " + i);
		}

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

	protected Map<DDMFormFieldType, String> setUpDDMFormFieldDataTypes() {
		_ddmFormFieldDataTypes = new HashMap<>();

		_ddmFormFieldDataTypes.put(DDMFormFieldType.CHECKBOX, "boolean");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.DATE, "date");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.DECIMAL, "double");
		_ddmFormFieldDataTypes.put(
			DDMFormFieldType.DOCUMENT_LIBRARY, "document-library");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.GEOLOCATION, "geolocation");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.INTEGER, "integer");
		_ddmFormFieldDataTypes.put(
			DDMFormFieldType.LINK_TO_PAGE, "link-to-page");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.NUMBER, "number");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.RADIO, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.SELECT, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.TEXT, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.TEXT_AREA, "string");
		_ddmFormFieldDataTypes.put(DDMFormFieldType.TEXT_HTML, "html");

		return _ddmFormFieldDataTypes;
	}

	protected Map<DDMFormFieldType, String> setUpDDMFormFieldValues()
		throws Exception {

		_fieldValues = new HashMap<>();

		_fieldValues.put(DDMFormFieldType.CHECKBOX, "false");
		_fieldValues.put(DDMFormFieldType.DATE, "1/1/70");
		_fieldValues.put(DDMFormFieldType.DECIMAL, "1.0");
		_fieldValues.put(
			DDMFormFieldType.DOCUMENT_LIBRARY,
			createDocumentLibraryDDMFormFieldValue());
		_fieldValues.put(
			DDMFormFieldType.GEOLOCATION, createGeolocationDDMFormFieldValue());
		_fieldValues.put(DDMFormFieldType.INTEGER, "2");
		_fieldValues.put(
			DDMFormFieldType.LINK_TO_PAGE, createLinkToPageDDMFormFieldValue());
		_fieldValues.put(DDMFormFieldType.NUMBER, "3");
		_fieldValues.put(DDMFormFieldType.RADIO, createListDDMFormFieldValue());
		_fieldValues.put(
			DDMFormFieldType.SELECT,  createListDDMFormFieldValue());
		_fieldValues.put(DDMFormFieldType.TEXT, "Text content");
		_fieldValues.put(DDMFormFieldType.TEXT_AREA, "Text Area content");
		_fieldValues.put(DDMFormFieldType.TEXT_HTML, "Text HTML content");

		return _fieldValues;
	}

	private Set<Locale> _availableLocales;
	private Map<DDMFormFieldType, String> _ddmFormFieldDataTypes;
	private Locale _defaultLocale;
	private Map<DDMFormFieldType, String> _fieldValues;
	private Group _group;

	private enum DDMFormFieldType {

		CHECKBOX("checkbox"), DATE("ddm-date"), DECIMAL("ddm-decimal"),
		DOCUMENT_LIBRARY("ddm-documentlibrary"), GEOLOCATION("ddm-geolocation"),
		INTEGER("ddm-integer"), LINK_TO_PAGE("ddm-link-to-page"),
		NUMBER("ddm-number"), RADIO("radio"), SELECT("select"), TEXT("text"),
		TEXT_AREA("textarea"), TEXT_HTML("ddm-text-html");

		public String getValue() {
			return _value;
		}

		private DDMFormFieldType(String value) {
			_value = value;
		}

		private final String _value;

	}

}