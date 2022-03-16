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
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.internal.util.DDMImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.ArrayList;
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

		setUpJSONStorageAdapter();
	}

	@Test
	public void testBooleanField() throws Exception {
		String definition = read("ddm-structure-boolean-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Boolean Field Structure", definition,
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field booleanField = new Field(
			structure.getStructureId(), "boolean",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray(true, true, true)
			).put(
				_ptLocale, ListUtil.fromArray(false, false, false)
			).build(),
			_enLocale);

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

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("contains(text, \"{parameter}\")");
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				"custom validation error message", LocaleUtil.US));
		ddmFormFieldValidation.setParameterLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue("test", LocaleUtil.US));

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"text", "text value");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_defaultStorageAdapter.create(
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

		_defaultStorageAdapter.create(
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

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("NOT(equals(text, \"{parameter}\"))");
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				"custom validation error message", LocaleUtil.US));
		ddmFormFieldValidation.setParameterLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue("", LocaleUtil.US));

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"text", "not empty");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_defaultStorageAdapter.create(
			TestPropsValues.getCompanyId(), structure.getStructureId(),
			ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Test
	public void testDateField() throws Exception {
		String definition = read("ddm-structure-date-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Date Field Structure", definition,
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Map<Locale, List<Serializable>> dataMap =
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale,
				ListUtil.fromArray(
					getDateFieldValue(0, 1, 2013, _enLocale),
					getDateFieldValue(0, 2, 2013, _enLocale))
			).build();

		Serializable date3 = getDateFieldValue(0, 3, 2013, _enLocale);
		Serializable date4 = getDateFieldValue(0, 4, 2013, _enLocale);

		List<Serializable> ptValues = ListUtil.fromArray(date3, date4);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field decimalField = new Field(
			structure.getStructureId(), "decimal",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray(1.1, 1.2, 1.3)
			).put(
				_ptLocale, ListUtil.fromArray(2.1, 2.2, 2.3)
			).build(),
			_enLocale);

		fields.put(decimalField);

		Field fieldsDisplayField = createFieldsDisplayField(
			structure.getStructureId(),
			"decimal_INSTANCE_rztm,decimal_INSTANCE_ovho," +
				"decimal_INSTANCE_krvx");

		fields.put(fieldsDisplayField);

		validate(structure.getStructureId(), fields);
	}

	@Test
	public void testDefaultStorageAdapter() {
		Assert.assertNotNull(
			_storageAdapterRegistry.getDefaultStorageAdapter());
	}

	@Test
	public void testDocLibraryField() throws Exception {
		String definition = read("ddm-structure-doc-lib-field.xsd");

		DDMStructure structure = addStructure(
			_classNameId, null, "Documents and Media Field Structure",
			definition, StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		FileEntry file1 = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1.txt",
			ContentTypes.TEXT_PLAIN, "Test 1.txt", StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);

		String file1Value = getDocLibraryFieldValue(file1);

		FileEntry file2 = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2.txt",
			ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY, null,
			null, serviceContext);

		String file2Value = getDocLibraryFieldValue(file2);

		Field documentLibraryField = new Field(
			structure.getStructureId(), "doc_library",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray(file1Value, file2Value)
			).put(
				_ptLocale, ListUtil.fromArray(file1Value, file2Value)
			).build(),
			_enLocale);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field integerField = new Field(
			structure.getStructureId(), "integer",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray(1, 2, 3)
			).put(
				_ptLocale, ListUtil.fromArray(3, 4, 5)
			).build(),
			_enLocale);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field linkToPageField = new Field(
			structure.getStructureId(), "link_to_page",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale,
				ListUtil.fromArray(
					"{\"layoutId\":\"1\",\"privateLayout\":false}")
			).put(
				_ptLocale,
				ListUtil.fromArray(
					"{\"layoutId\":\"2\",\"privateLayout\":true}")
			).build(),
			_enLocale);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field numberField = new Field(
			structure.getStructureId(), "number",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray(1, 1.5F, 2)
			).put(
				_ptLocale, ListUtil.fromArray(3, 3.5F, 4)
			).build(),
			_enLocale);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field radioField = new Field(
			structure.getStructureId(), "radio",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray("value 1", "value 2")
			).put(
				_ptLocale, ListUtil.fromArray("value 2", "value 3")
			).build(),
			_enLocale);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field selectField = new Field(
			structure.getStructureId(), "select",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale,
				ListUtil.fromArray("[\"value 1\",\"value 2\"]", "[\"value 3\"]")
			).put(
				_ptLocale, ListUtil.fromArray("[\"value 2\"]", "[\"value 3\"]")
			).build(),
			_enLocale);

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
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field textField = new Field(
			structure.getStructureId(), "text",
			HashMapBuilder.<Locale, List<Serializable>>put(
				_enLocale, ListUtil.fromArray("one", "two", "three")
			).put(
				_ptLocale, ListUtil.fromArray("um", "dois", "tres")
			).build(),
			_enLocale);

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

		long classPK = _defaultStorageAdapter.create(
			TestPropsValues.getCompanyId(), structure.getStructureId(),
			ddmFormValues,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		_defaultStorageAdapter.update(
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
		return StringBundler.concat(
			"{\"groupId\":", fileEntry.getGroupId(), ",\"uuid\":\"",
			fileEntry.getUuid(), "\",\"version\":\"", fileEntry.getVersion(),
			"\"}");
	}

	protected void setUpJSONStorageAdapter() {
		_defaultStorageAdapter = _storageAdapterRegistry.getStorageAdapter(
			StorageType.DEFAULT.toString());
	}

	protected void validate(long ddmStructureId, Fields fields)
		throws Exception {

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String expectedFieldsString = jsonSerializer.serializeDeep(fields);

		long classPK = create(_defaultStorageAdapter, ddmStructureId, fields);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMFormValues actualDDMFormValues =
			_defaultStorageAdapter.getDDMFormValues(classPK);

		Fields actualFields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, actualDDMFormValues);

		Assert.assertEquals(
			expectedFieldsString, jsonSerializer.serializeDeep(actualFields));
	}

	private static long _classNameId;
	private static Locale _enLocale;
	private static Locale _ptLocale;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	private StorageAdapter _defaultStorageAdapter;

	@Inject
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Inject
	private StorageAdapterRegistry _storageAdapterRegistry;

}