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

package com.liferay.journal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class JournalConverterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_enLocale = LocaleUtil.fromLanguageId("en_US");
		_ptLocale = LocaleUtil.fromLanguageId("pt_BR");

		_group = GroupTestUtil.addGroup();

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		String definition = read("test-ddm-structure-all-fields.xml");

		DDMForm ddmForm = deserialize(definition);

		_ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class), null,
			"Test Structure", ddmForm, StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		Registry registry = RegistryUtil.getRegistry();

		_journalConverter = registry.getService(
			registry.getServiceReference(JournalConverter.class));
	}

	@Test
	public void testGetContentFromListField() throws Exception {
		Fields fields = new Fields();

		fields.put(getListField(_ddmStructure.getStructureId()));

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "list_INSTANCE_pcm9WPVX");

		fields.put(fieldsDisplayField);

		String expectedContent = read("test-journal-content-list-field.xml");

		String actualContent = _journalConverter.getContent(
			_ddmStructure, fields, _ddmStructure.getGroupId());

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromMultiListField() throws Exception {
		Fields fields = new Fields();

		Field multiListField = getMultiListField(
			_ddmStructure.getStructureId());

		fields.put(multiListField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "multi_list_INSTANCE_9X5wVsSv");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-multi-list-field.xml");

		String actualContent = _journalConverter.getContent(
			_ddmStructure, fields, _ddmStructure.getGroupId());

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromNestedFields() throws Exception {
		Fields fields = getNestedFields(_ddmStructure.getStructureId());

		String expectedContent = read("test-journal-content-nested-fields.xml");

		String actualContent = _journalConverter.getContent(
			_ddmStructure, fields, _ddmStructure.getGroupId());

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromTextAreaField() throws Exception {
		Fields fields = new Fields();

		fields.put(getTextAreaField(_ddmStructure.getStructureId()));

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "text_area_INSTANCE_RFnJ1nCn");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-text-area-field.xml");

		String actualContent = _journalConverter.getContent(
			_ddmStructure, fields, _ddmStructure.getGroupId());

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromTextBoxField() throws Exception {
		Fields fields = new Fields();

		fields.put(getTextBoxField(_ddmStructure.getStructureId()));

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"text_box_INSTANCE_ND057krU,text_box_INSTANCE_HvemvQgl," +
				"text_box_INSTANCE_enAnbvq6");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-text-box-repeatable-field.xml");

		String actualContent = _journalConverter.getContent(
			_ddmStructure, fields, _ddmStructure.getGroupId());

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromTextField() throws Exception {
		Fields fields = new Fields();

		fields.put(getTextField(_ddmStructure.getStructureId()));

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "text_INSTANCE_bf4sdx6Q");

		fields.put(fieldsDisplayField);

		String expectedContent = read("test-journal-content-text-field.xml");

		String actualContent = _journalConverter.getContent(
			_ddmStructure, fields, _ddmStructure.getGroupId());

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetFieldsFromContentWithListElement() throws Exception {
		Fields expectedFields = new Fields();

		expectedFields.put(getListField(_ddmStructure.getStructureId()));

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "list_INSTANCE_pcm9WPVX");

		expectedFields.put(fieldsDisplayField);

		String content = read("test-journal-content-list-field.xml");

		Fields actualFields = _journalConverter.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithMultiListElement()
		throws Exception {

		Fields expectedFields = new Fields();

		Field multiListField = getMultiListField(
			_ddmStructure.getStructureId());

		expectedFields.put(multiListField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "multi_list_INSTANCE_9X5wVsSv");

		expectedFields.put(fieldsDisplayField);

		String content = read("test-journal-content-multi-list-field.xml");

		Fields actualFields = _journalConverter.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithNestedElements() throws Exception {
		Fields expectedFields = getNestedFields(_ddmStructure.getStructureId());

		String content = read("test-journal-content-nested-fields.xml");

		Fields actualFields = _journalConverter.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithUnlocalizedElement()
		throws Exception {

		Fields expectedFields = new Fields();

		Field textField = getTextField(_ddmStructure.getStructureId());

		textField.setValue(_ptLocale, textField.getValue(_enLocale));

		expectedFields.put(textField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "text_INSTANCE_Okhyj7Ni");

		expectedFields.put(fieldsDisplayField);

		String content = read(
			"test-journal-content-text-unlocalized-field.xml");

		Fields actualFields = _journalConverter.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetLinkToLayoutValue() throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element element = document.addElement("dynamic-element");

		Layout layout = LayoutTestUtil.addLayout(_group);

		StringBundler sb = new StringBundler(5);

		sb.append(layout.getLayoutId());
		sb.append(StringPool.AT);
		sb.append(layout.isPublicLayout() ? "public" : "private");
		sb.append(StringPool.AT);
		sb.append(layout.getGroupId());

		element.addText(sb.toString());

		String value = ReflectionTestUtil.invoke(
			_journalConverter, "_getLinkToLayoutValue",
			new Class<?>[] {Locale.class, Element.class}, LocaleUtil.US,
			element);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

		Assert.assertEquals(layout.getGroupId(), jsonObject.getLong("groupId"));
		Assert.assertEquals(
			layout.getLayoutId(), jsonObject.getLong("layoutId"));
		Assert.assertEquals(
			layout.getName(LocaleUtil.US), jsonObject.getString("name"));
		Assert.assertFalse(jsonObject.getBoolean("privateLayout"));
	}

	protected void assertEquals(
		DDMForm expectedDDMForm, DDMForm actualDDMForm) {

		Map<String, DDMFormField> expectedDDMFormFieldsMap =
			expectedDDMForm.getDDMFormFieldsMap(true);

		Map<String, DDMFormField> actualDDMFormFieldsMap =
			actualDDMForm.getDDMFormFieldsMap(true);

		for (Map.Entry<String, DDMFormField> expectedEntry :
				expectedDDMFormFieldsMap.entrySet()) {

			DDMFormField actualDDMFormField = actualDDMFormFieldsMap.get(
				expectedEntry.getKey());

			assertEquals(expectedEntry.getValue(), actualDDMFormField);
		}
	}

	protected void assertEquals(
		DDMFormField expectedDDMFormField, DDMFormField actualDDMFormField) {

		Assert.assertEquals(
			expectedDDMFormField.getDataType(),
			actualDDMFormField.getDataType());
		assertEquals(
			expectedDDMFormField.getDDMFormFieldOptions(),
			actualDDMFormField.getDDMFormFieldOptions());
		Assert.assertEquals(
			expectedDDMFormField.getIndexType(),
			actualDDMFormField.getIndexType());
		assertEquals(
			expectedDDMFormField.getLabel(), actualDDMFormField.getLabel());
		Assert.assertEquals(
			expectedDDMFormField.getName(), actualDDMFormField.getName());
		assertEquals(
			expectedDDMFormField.getStyle(), actualDDMFormField.getStyle());
		assertEquals(
			expectedDDMFormField.getTip(), actualDDMFormField.getTip());
		Assert.assertEquals(
			expectedDDMFormField.getType(), actualDDMFormField.getType());
		Assert.assertEquals(
			expectedDDMFormField.isMultiple(), actualDDMFormField.isMultiple());
		Assert.assertEquals(
			expectedDDMFormField.isRepeatable(),
			actualDDMFormField.isRepeatable());
		Assert.assertEquals(
			expectedDDMFormField.isRequired(), actualDDMFormField.isRequired());
	}

	protected void assertEquals(
		DDMFormFieldOptions expectedDDMFormFieldOptions,
		DDMFormFieldOptions actualDDMFormFieldOptions) {

		Set<String> expectedOptionValues =
			expectedDDMFormFieldOptions.getOptionsValues();

		for (String expectedOptionValue : expectedOptionValues) {
			LocalizedValue expectedOptionLabels =
				expectedDDMFormFieldOptions.getOptionLabels(
					expectedOptionValue);

			LocalizedValue actualOptionLabels =
				actualDDMFormFieldOptions.getOptionLabels(expectedOptionValue);

			assertEquals(expectedOptionLabels, actualOptionLabels);
		}
	}

	protected void assertEquals(
		LocalizedValue expectedLocalizedValue,
		LocalizedValue actualLocalizedValue) {

		Set<Locale> expectedAvailableLocales =
			expectedLocalizedValue.getAvailableLocales();

		for (Locale expectedLocale : expectedAvailableLocales) {
			String expectedValue = expectedLocalizedValue.getString(
				expectedLocale);

			String actualValue = actualLocalizedValue.getString(expectedLocale);

			Assert.assertEquals(expectedValue, actualValue);
		}
	}

	protected void assertEquals(String expectedContent, String actualContent)
		throws Exception {

		Map<String, Map<Locale, List<String>>> expectedFieldsMap = getFieldsMap(
			expectedContent);

		Map<String, Map<Locale, List<String>>> actualFieldsMap = getFieldsMap(
			actualContent);

		Assert.assertEquals(expectedFieldsMap, actualFieldsMap);
	}

	protected DDMForm deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_xsdDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected Field getFieldsDisplayField(long ddmStructureId, String value) {
		Field fieldsDisplayField = new Field();

		fieldsDisplayField.setDDMStructureId(ddmStructureId);
		fieldsDisplayField.setName(DDM.FIELDS_DISPLAY_NAME);
		fieldsDisplayField.setValue(value);

		return fieldsDisplayField;
	}

	protected Map<String, Map<Locale, List<String>>> getFieldsMap(
			String content)
		throws Exception {

		Map<String, Map<Locale, List<String>>> fieldsMap = new HashMap<>();

		Document document = UnsecureSAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			udpateFieldsMap(dynamicElementElement, fieldsMap);
		}

		return fieldsMap;
	}

	protected Field getListField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("list");

		field.addValue(_enLocale, "[\"value 01\"]");

		return field;
	}

	protected Field getMultiListField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("multi_list");

		field.addValue(_enLocale, "[\"value 01\",\"value 02\"]");

		return field;
	}

	protected Fields getNestedFields(long ddmStructureId) {
		Fields fields = new Fields();

		// Contact

		Field contact = new Field();

		contact.setDDMStructureId(ddmStructureId);
		contact.setName("contact");

		List<Serializable> enValues = new ArrayList<>();

		enValues.add("joe");
		enValues.add("richard");

		contact.setValues(_enLocale, enValues);

		List<Serializable> ptValues = new ArrayList<>();

		ptValues.add("joao");
		ptValues.add("ricardo");

		contact.addValues(_ptLocale, ptValues);

		fields.put(contact);

		// Phone

		Field phone = new Field();

		phone.setDDMStructureId(ddmStructureId);
		phone.setName("phone");

		List<Serializable> values = new ArrayList<>();

		values.add("123");
		values.add("456");

		phone.setValues(_enLocale, values);
		phone.addValues(_ptLocale, values);

		fields.put(phone);

		// Ext

		Field ext = new Field();

		ext.setDDMStructureId(ddmStructureId);
		ext.setName("ext");

		values = new ArrayList<>();

		values.add("1");
		values.add("2");
		values.add("3");
		values.add("4");
		values.add("5");

		ext.setValues(_enLocale, values);
		ext.addValues(_ptLocale, values);

		fields.put(ext);

		StringBundler sb = new StringBundler(5);

		sb.append("contact_INSTANCE_RF3do1m5,phone_INSTANCE_QK6B0wK9,");
		sb.append("ext_INSTANCE_L67MPqQf,ext_INSTANCE_8uxzZl41,");
		sb.append("ext_INSTANCE_S58K861T,contact_INSTANCE_CUeFxcrA,");
		sb.append("phone_INSTANCE_lVTcTviF,ext_INSTANCE_cZalDSll,");
		sb.append("ext_INSTANCE_HDrK2Um5");

		Field fieldsDisplayField = new Field(
			ddmStructureId, DDM.FIELDS_DISPLAY_NAME, sb.toString());

		fields.put(fieldsDisplayField);

		return fields;
	}

	protected Field getTextAreaField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("text_area");

		field.addValue(_enLocale, "<p>Hello World!</p>");

		return field;
	}

	protected Field getTextBoxField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("text_box");

		List<Serializable> enValues = new ArrayList<>();

		enValues.add("one");
		enValues.add("two");
		enValues.add("three");

		field.addValues(_enLocale, enValues);

		List<Serializable> ptValues = new ArrayList<>();

		ptValues.add("um");
		ptValues.add("dois");
		ptValues.add("tres");

		field.addValues(_ptLocale, ptValues);

		return field;
	}

	protected Field getTextField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("text");

		field.addValue(_enLocale, "one");
		field.addValue(_ptLocale, "um");

		return field;
	}

	protected List<String> getValues(
		Map<Locale, List<String>> valuesMap, Locale locale) {

		List<String> values = valuesMap.get(locale);

		if (values == null) {
			values = new ArrayList<>();

			valuesMap.put(locale, values);
		}

		return values;
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/journal/dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void udpateFieldsMap(
		Element dynamicElementElement,
		Map<String, Map<Locale, List<String>>> fieldsMap) {

		List<Element> childrenDynamicElementElements =
			dynamicElementElement.elements("dynamic-element");

		for (Element childrenDynamicElementElement :
				childrenDynamicElementElements) {

			udpateFieldsMap(childrenDynamicElementElement, fieldsMap);
		}

		String name = dynamicElementElement.attributeValue("name");

		Map<Locale, List<String>> valuesMap = fieldsMap.get(name);

		if (valuesMap == null) {
			valuesMap = new HashMap<>();

			fieldsMap.put(name, valuesMap);
		}

		List<Element> dynamicContentElements = dynamicElementElement.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			Locale locale = LocaleUtil.fromLanguageId(
				dynamicContentElement.attributeValue("language-id"));

			List<String> values = getValues(valuesMap, locale);

			List<Element> optionElements = dynamicContentElement.elements(
				"option");

			if (!optionElements.isEmpty()) {
				for (Element optionElement : optionElements) {
					values.add(optionElement.getText());
				}
			}
			else {
				values.add(dynamicContentElement.getText());
			}
		}
	}

	@Inject(filter = "ddm.form.deserializer.type=xsd")
	private static DDMFormDeserializer _xsdDDMFormDeserializer;

	private DDMStructure _ddmStructure;
	private Locale _enLocale;

	@DeleteAfterTestRun
	private Group _group;

	private JournalConverter _journalConverter;
	private Locale _ptLocale;

}