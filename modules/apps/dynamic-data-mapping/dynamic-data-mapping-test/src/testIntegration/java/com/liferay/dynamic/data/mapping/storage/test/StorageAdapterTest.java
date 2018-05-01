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

package com.liferay.dynamic.data.mapping.storage.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.dynamic.data.mapping.internal.util.DDMImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.test.BaseDDMServiceTestCase;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class StorageAdapterTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_classNameId = PortalUtil.getClassNameId(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet");

		_enLocale = LocaleUtil.fromLanguageId("en_US");
		_ptLocale = LocaleUtil.fromLanguageId("pt_BR");
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormValuesToFieldsConverter();
		setUpFieldsToDDMFormValuesConverter();
		setUpJSONStorageAdapter();
	}

	@Test
	public void testBooleanField() throws Exception {
		String definition = read("ddm-structure-boolean-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Boolean Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {true, true, true});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {false, false, false});

		dataMap.put(_ptLocale, ptValues);

		Field booleanField = new Field(
			structure.getStructureId(), "boolean", dataMap, _enLocale);

		fields.put(booleanField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"boolean_INSTANCE_rztm,boolean_INSTANCE_ovho," +
				"boolean_INSTANCE_krvx");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test(expected = DDMFormValuesValidationException.MustSetValidValue.class)
	public void testCreateWithInvalidDDMFieldValue() throws Exception {
		DDMStructure structure = addStructure(
			_classNameId, "Default Structure");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"text", false, false, false);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("contains(text, \"test\")");
		ddmFormFieldValidation.setErrorMessage(
			"custom validation error message");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"text", "text value");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_jsonStorageAdapter.create(
			TestPropsValues.getCompanyId(), structure.getStructureId(),
			ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Test(expected = DDMFormValuesValidationException.RequiredValue.class)
	public void testCreateWithInvalidDDMFormValues() throws Exception {
		DDMStructure structure = addStructure(
			_classNameId, "Default Structure");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"text", false, false, true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		_jsonStorageAdapter.create(
			TestPropsValues.getCompanyId(), structure.getStructureId(),
			ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Test
	public void testCreateWithValidDDMFieldValue() throws Exception {
		DDMStructure structure = addStructure(
			_classNameId, "Default Structure");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"text", false, false, false);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("NOT(equals(text, \"\"))");
		ddmFormFieldValidation.setErrorMessage(
			"custom validation error message");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"text", "not empty");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_jsonStorageAdapter.create(
			TestPropsValues.getCompanyId(), structure.getStructureId(),
			ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Test
	public void testDateField() throws Exception {
		String definition = read("ddm-structure-date-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Date Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		Serializable date1 = getDateFieldValue(0, 1, 2013, _enLocale);
		Serializable date2 = getDateFieldValue(0, 2, 2013, _enLocale);

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {date1, date2});

		dataMap.put(_enLocale, enValues);

		Serializable date3 = getDateFieldValue(0, 3, 2013, _enLocale);
		Serializable date4 = getDateFieldValue(0, 4, 2013, _enLocale);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {date3, date4});

		dataMap.put(_ptLocale, ptValues);

		Field dateField = new Field(
			structure.getStructureId(), "date", dataMap, _enLocale);

		fields.put(dateField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"date_INSTANCE_rztm,date_INSTANCE_ovho");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testDecimalField() throws Exception {
		String definition = read("ddm-structure-decimal-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Decimal Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {1.1, 1.2, 1.3});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {2.1, 2.2, 2.3});

		dataMap.put(_ptLocale, ptValues);

		Field decimalField = new Field(
			structure.getStructureId(), "decimal", dataMap, _enLocale);

		fields.put(decimalField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"decimal_INSTANCE_rztm,decimal_INSTANCE_ovho," +
				"decimal_INSTANCE_krvx");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testDocLibraryField() throws Exception {
		String definition = read("ddm-structure-doc-lib-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Documents and Media Field Structure",
			definition, StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		FileEntry file1 = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1.txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		String file1Value = getDocLibraryFieldValue(file1);

		FileEntry file2 = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2.txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		String file2Value = getDocLibraryFieldValue(file2);

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {file1Value, file2Value});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {file1Value, file2Value});

		dataMap.put(_ptLocale, ptValues);

		Field documentLibraryField = new Field(
			structure.getStructureId(), "doc_library", dataMap, _enLocale);

		fields.put(documentLibraryField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"doc_library_INSTANCE_rztm,doc_library_INSTANCE_ovho");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testIntegerField() throws Exception {
		String definition = read("ddm-structure-integer-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Integer Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {1, 2, 3});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {3, 4, 5});

		dataMap.put(_ptLocale, ptValues);

		Field integerField = new Field(
			structure.getStructureId(), "integer", dataMap, _enLocale);

		fields.put(integerField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"integer_INSTANCE_rztm,integer_INSTANCE_ovho," +
				"integer_INSTANCE_krvx");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testLinkToPageField() throws Exception {
		String definition = read("ddm-structure-link-to-page-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Link to Page Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {
				"{\"layoutId\":\"1\",\"privateLayout\":false}"
			});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"{\"layoutId\":\"2\",\"privateLayout\":true}"});

		dataMap.put(_ptLocale, ptValues);

		Field linkToPageField = new Field(
			structure.getStructureId(), "link_to_page", dataMap, _enLocale);

		fields.put(linkToPageField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(), "link_to_page_INSTANCE_rztm");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testNumberField() throws Exception {
		String definition = read("ddm-structure-number-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Number Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {1, 1.5F, 2});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {3, 3.5F, 4});

		dataMap.put(_ptLocale, ptValues);

		Field numberField = new Field(
			structure.getStructureId(), "number", dataMap, _enLocale);

		fields.put(numberField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"number_INSTANCE_rztm,number_INSTANCE_ovho,number_INSTANCE_krvx");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testRadioField() throws Exception {
		String definition = read("ddm-structure-radio-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Radio Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {"value 1", "value 2"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"value 2", "value 3"});

		dataMap.put(_ptLocale, ptValues);

		Field radioField = new Field(
			structure.getStructureId(), "radio", dataMap, _enLocale);

		fields.put(radioField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"radio_INSTANCE_rztm,radio_INSTANCE_ovho");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testSelectField() throws Exception {
		String definition = read("ddm-structure-select-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Select Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {"[\"value 1\",\"value 2\"]", "[\"value 3\"]"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"[\"value 2\"]", "[\"value 3\"]"});

		dataMap.put(_ptLocale, ptValues);

		Field selectField = new Field(
			structure.getStructureId(), "select", dataMap, _enLocale);

		fields.put(selectField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"select_INSTANCE_rztm,select_INSTANCE_ovho");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testTextField() throws Exception {
		String definition = read("ddm-structure-text-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Text Field Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap = new HashMap<>();

		List<Serializable> enValues = ListUtil.fromArray(
			new Serializable[] {"one", "two", "three"});

		dataMap.put(_enLocale, enValues);

		List<Serializable> ptValues = ListUtil.fromArray(
			new Serializable[] {"um", "dois", "tres"});

		dataMap.put(_ptLocale, ptValues);

		Field textField = new Field(
			structure.getStructureId(), "text", dataMap, _enLocale);

		fields.put(textField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"text_INSTANCE_rztm,text_INSTANCE_ovho,text_INSTANCE_krvx");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test(expected = DDMFormValuesValidationException.RequiredValue.class)
	public void testUpdateWithInvalidDDMFormValues() throws Exception {
		DDMStructure structure = addStructure(
			_classNameId, "Default Structure");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"text", false, false, true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"text", "text value");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		long classPK = _jsonStorageAdapter.create(
			TestPropsValues.getCompanyId(), structure.getStructureId(),
			ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		_jsonStorageAdapter.update(
			classPK, ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected long create(
			StorageAdapter storageAdapter, long ddmStructureId, Fields fields)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMFormValues ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		return storageAdapter.create(
			TestPropsValues.getCompanyId(), ddmStructureId, ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected Field createFieldsDisplayField(
		long ddmStructureId, String value) {

		Field fieldsDisplayField = new Field(
			ddmStructureId, DDMImpl.FIELDS_DISPLAY_NAME,
			createValuesList(value), LocaleUtil.US);

		fieldsDisplayField.setDefaultLocale(LocaleUtil.US);

		return fieldsDisplayField;
	}

	protected List<Serializable> createValuesList(String... valuesString) {
		List<Serializable> values = new ArrayList<>();

		for (String valueString : valuesString) {
			values.add(valueString);
		}

		return values;
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

	protected void setUpDDMFormValuesToFieldsConverter() {
		Registry registry = RegistryUtil.getRegistry();

		_ddmFormValuesToFieldsConverter = registry.getService(
			DDMFormValuesToFieldsConverter.class);
	}

	protected void setUpFieldsToDDMFormValuesConverter() {
		Registry registry = RegistryUtil.getRegistry();

		_fieldsToDDMFormValuesConverter = registry.getService(
			FieldsToDDMFormValuesConverter.class);
	}

	protected void setUpJSONStorageAdapter() {
		Registry registry = RegistryUtil.getRegistry();

		StorageAdapterRegistry storageAdapterRegistry = registry.getService(
			StorageAdapterRegistry.class);

		_jsonStorageAdapter = storageAdapterRegistry.getStorageAdapter(
			StorageType.JSON.toString());
	}

	protected void validate(long ddmStructureId, Fields fields)
		throws Exception {

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String expectedFieldsString = jsonSerializer.serializeDeep(fields);

		long classPK = create(_jsonStorageAdapter, ddmStructureId, fields);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMFormValues actualDDMFormValues =
			_jsonStorageAdapter.getDDMFormValues(classPK);

		Fields actualFields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, actualDDMFormValues);

		Assert.assertEquals(
			expectedFieldsString, jsonSerializer.serializeDeep(actualFields));
	}

	private static long _classNameId;
	private static Locale _enLocale;
	private static Locale _ptLocale;

	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;
	private StorageAdapter _jsonStorageAdapter;

}