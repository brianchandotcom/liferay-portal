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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.xml.XMLSchemaImpl;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.service.BaseDDMServiceTestCase;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
public class JournalConverterUtilTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		long classNameId = PortalUtil.getClassNameId(JournalArticle.class);

		String definition = read("test-ddm-structure-all-fields.xml");

		_ddmStructure = addStructure(
			classNameId, null, "Test Structure", definition,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test
	public void testGetContentFromBooleanField() throws Exception {
		Fields fields = new Fields();

		Field booleanField = getBooleanField(_ddmStructure.getStructureId());

		fields.put(booleanField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"boolean_INSTANCE_Okhyj6Ni,boolean_INSTANCE_1SYNQuhg");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-boolean-repeatable-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromLinkToLayoutField() throws Exception {
		Fields fields = new Fields();

		Map<String, Layout> layouts = getLayoutsMap();

		Field linkToLayoutField = getLinkToLayoutField(
			_ddmStructure.getStructureId(), layouts);

		fields.put(linkToLayoutField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"link_to_layout_INSTANCE_MiO7vIJu," +
			"link_to_layout_INSTANCE_9FLzJNUX," +
			"link_to_layout_INSTANCE_WqABvmxw," +
			"link_to_layout_INSTANCE_31abnWkB," +
			"link_to_layout_INSTANCE_pWIUF15B," +
			"link_to_layout_INSTANCE_OGQypdcj," +
			"link_to_layout_INSTANCE_TB2XZ3wn," +
			"link_to_layout_INSTANCE_3IRNS4jM");

		fields.put(fieldsDisplayField);

		String expectedContent = replaceLinksToLayoutsParameters(
			read("test-journal-content-link-to-page-field.xml"), layouts);

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromListField() throws Exception {
		Fields fields = new Fields();

		Field listField = getListField(_ddmStructure.getStructureId());

		fields.put(listField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "list_INSTANCE_pcm9WPVX");

		fields.put(fieldsDisplayField);

		String expectedContent = read("test-journal-content-list-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromMultiListField() throws Exception {
		Fields fields = new Fields();

		Field multiListField = getMultiListField(
			_ddmStructure.getStructureId());

		fields.put(multiListField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "multi-list_INSTANCE_9X5wVsSv");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-multi-list-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromNestedFields() throws Exception {
		Fields fields = getNestedFields(_ddmStructure.getStructureId());

		String expectedContent = read("test-journal-content-nested-fields.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromTextAreaField() throws Exception {
		Fields fields = new Fields();

		Field textAreaField = getTextAreaField(_ddmStructure.getStructureId());

		fields.put(textAreaField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "text_area_INSTANCE_RFnJ1nCn");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-text-area-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromTextBoxField() throws Exception {
		Fields fields = new Fields();

		Field textBoxField = getTextBoxField(_ddmStructure.getStructureId());

		fields.put(textBoxField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"text_box_INSTANCE_ND057krU,text_box_INSTANCE_HvemvQgl," +
				"text_box_INSTANCE_enAnbvq6");

		fields.put(fieldsDisplayField);

		String expectedContent = read(
			"test-journal-content-text-box-repeatable-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromTextField() throws Exception {
		Fields fields = new Fields();

		Field textField = getTextField(_ddmStructure.getStructureId());

		fields.put(textField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "text_INSTANCE_bf4sdx6Q");

		fields.put(fieldsDisplayField);

		String expectedContent = read("test-journal-content-text-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetDDMXSD() throws Exception {
		String expectedXSD = read("test-ddm-structure-all-fields.xml");

		DDMForm expectedDDMForm = DDMFormXSDDeserializerUtil.deserialize(
			expectedXSD);

		String actualXSD = JournalConverterUtil.getDDMXSD(
			read("test-journal-structure-all-fields.xml"));

		validateDDMXSD(actualXSD);

		DDMForm actualDDMForm = DDMFormXSDDeserializerUtil.deserialize(
			actualXSD);

		assertEquals(expectedDDMForm, actualDDMForm);
	}

	@Test
	public void testGetFieldsFromContentWithBooleanElement() throws Exception {
		Fields expectedFields = new Fields();

		Field booleanField = getBooleanField(_ddmStructure.getStructureId());

		expectedFields.put(booleanField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"boolean_INSTANCE_Okhyj6Ni,boolean_INSTANCE_1SYNQuhg");

		expectedFields.put(fieldsDisplayField);

		String content = read(
			"test-journal-content-boolean-repeatable-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithDocumentLibraryElement()
		throws Exception {

		Fields expectedFields = new Fields();

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1.txt");

		Field documentLibraryField = getDocumentLibraryField(
			fileEntry, _ddmStructure.getStructureId());

		expectedFields.put(documentLibraryField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"document_library_INSTANCE_4aGOvP3N");

		expectedFields.put(fieldsDisplayField);

		String content = read("test-journal-content-doc-library-field.xml");

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-content");

		Document document = SAXReaderUtil.read(content);

		Element element = (Element)xPathSelector.selectSingleNode(document);

		String[] previewURLs = new String[2];

		previewURLs[0] = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK, true,
			true);
		previewURLs[1] = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, false);

		for (int i = 0; i < previewURLs.length; i++) {
			element.addCDATA(previewURLs[i]);

			Fields actualFields = JournalConverterUtil.getDDMFields(
				_ddmStructure, document.asXML());

			Assert.assertEquals(expectedFields, actualFields);
		}
	}

	@Test
	public void testGetFieldsFromContentWithLinkToLayoutElement()
		throws Exception {

		Fields expectedFields = new Fields();

		Map<String, Layout> layoutsMap = getLayoutsMap();

		Field linkToLayoutField = getLinkToLayoutField(
			_ddmStructure.getStructureId(), layoutsMap);

		expectedFields.put(linkToLayoutField);

		StringBundler sb = new StringBundler();

		sb.append("link_to_layout_INSTANCE_MiO7vIJu,");
		sb.append("link_to_layout_INSTANCE_9FLzJNUX,");
		sb.append("link_to_layout_INSTANCE_WqABvmxw,");
		sb.append("link_to_layout_INSTANCE_31abnWkB,");
		sb.append("link_to_layout_INSTANCE_pWIUF15B,");
		sb.append("link_to_layout_INSTANCE_OGQypdcj,");
		sb.append("link_to_layout_INSTANCE_TB2XZ3wn,");
		sb.append("link_to_layout_INSTANCE_3IRNS4jM");

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), sb.toString());

		expectedFields.put(fieldsDisplayField);

		String content = replaceLinksToLayoutsParameters(
			read("test-journal-content-link-to-page-field.xml"), layoutsMap);

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithListElement() throws Exception {
		Fields expectedFields = new Fields();

		Field listField = getListField(_ddmStructure.getStructureId());

		expectedFields.put(listField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "list_INSTANCE_pcm9WPVX");

		expectedFields.put(fieldsDisplayField);

		String content = read("test-journal-content-list-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
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
			_ddmStructure.getStructureId(), "multi-list_INSTANCE_9X5wVsSv");

		expectedFields.put(fieldsDisplayField);

		String content = read("test-journal-content-multi-list-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithNestedElements() throws Exception {
		Fields expectedFields = getNestedFields(_ddmStructure.getStructureId());

		String content = read("test-journal-content-nested-fields.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		Assert.assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetJournalXSD() throws Exception {
		String expectedXSD = read("test-journal-structure-all-fields.xml");

		Map<String, Map<String, String>> expectedMap =
			JournalTestUtil.getXsdMap(expectedXSD);

		String actualXSD = JournalConverterUtil.getJournalXSD(
			read("test-ddm-structure-all-fields.xml"));

		Map<String, Map<String, String>> actualMap = JournalTestUtil.getXsdMap(
			actualXSD);

		Assert.assertEquals(expectedMap, actualMap);
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
			expectedDDMFormField.getName(),  actualDDMFormField.getName());
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

	protected Field getBooleanField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("boolean");

		List<Serializable> enValues = new ArrayList<>();

		enValues.add(true);
		enValues.add(false);

		field.addValues(_enLocale, enValues);

		return field;
	}

	protected Field getDocumentLibraryField(
		FileEntry fileEntry, long ddmStructureId) {

		Field docLibraryField = new Field();

		docLibraryField.setDDMStructureId(ddmStructureId);
		docLibraryField.setName("document_library");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("title", fileEntry.getTitle());
		jsonObject.put("uuid", fileEntry.getUuid());
		jsonObject.put("version", fileEntry.getVersion());

		docLibraryField.addValue(_enLocale, jsonObject.toString());

		return docLibraryField;
	}

	protected Field getFieldsDisplayField(long ddmStructureId, String value) {
		Field fieldsDisplayField = new Field();

		fieldsDisplayField.setDDMStructureId(ddmStructureId);
		fieldsDisplayField.setName(DDMImpl.FIELDS_DISPLAY_NAME);
		fieldsDisplayField.setValue(value);

		return fieldsDisplayField;
	}

	protected Map<String, Map<Locale, List<String>>> getFieldsMap(
			String content)
		throws Exception {

		Map<String, Map<Locale, List<String>>> fieldsMap = new HashMap<>();

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			udpateFieldsMap(dynamicElementElement, fieldsMap);
		}

		return fieldsMap;
	}

	protected Map<String, Layout> getLayoutsMap() throws Exception {
		Map<String, Layout> layouts = new LinkedHashMap<>(4);

		User user = TestPropsValues.getUser();

		layouts.put(
			_PRIVATE_LAYOUT,
			LayoutTestUtil.addLayout(group, true));
		layouts.put(
			_PRIVATE_USER_LAYOUT,
			LayoutTestUtil.addLayout(user.getGroupId(), true));
		layouts.put(
			_PUBLIC_LAYOUT,
			LayoutTestUtil.addLayout(group, false));
		layouts.put(
			_PUBLIC_USER_LAYOUT,
			LayoutTestUtil.addLayout(user.getGroupId(), false));

		return layouts;
	}

	protected Field getLinkToLayoutField(
		long ddmStructureId, Map<String, Layout> layoutsMap) {

		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("link_to_layout");

		List<Serializable> enValues = new ArrayList<>();

		for (Layout layout : layoutsMap.values()) {
			enValues.add(getLinkToLayoutFieldValue(layout, false));
			enValues.add(getLinkToLayoutFieldValue(layout, true));
		}

		field.addValues(_enLocale, enValues);

		return field;
	}

	protected String getLinkToLayoutFieldValue(
		Layout layout, boolean includeGroupId) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (includeGroupId) {
			jsonObject.put("groupId", String.valueOf(layout.getGroupId()));
		}

		jsonObject.put("layoutId", String.valueOf(layout.getLayoutId()));
		jsonObject.put("privateLayout", layout.isPrivateLayout());

		return jsonObject.toString();
	}

	protected Field getListField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("list");

		field.addValue(_enLocale, "[\"a\"]");

		return field;
	}

	protected Field getMultiListField(long ddmStructureId) {
		Field field =  new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("multi-list");

		field.addValue(_enLocale, "[\"a\",\"b\"]");

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

		Field fieldsDisplayField = new Field(
			ddmStructureId, DDMImpl.FIELDS_DISPLAY_NAME,
			"contact_INSTANCE_RF3do1m5,phone_INSTANCE_QK6B0wK9," +
			"ext_INSTANCE_L67MPqQf,ext_INSTANCE_8uxzZl41," +
			"ext_INSTANCE_S58K861T,contact_INSTANCE_CUeFxcrA," +
			"phone_INSTANCE_lVTcTviF,ext_INSTANCE_cZalDSll," +
			"ext_INSTANCE_HDrK2Um5");

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

	@Override
	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portlet/journal/dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected String replaceLinksToLayoutsParameters(
			String content, Map<String, Layout> layoutsMap)
		throws Exception {

		Layout privateLayout = layoutsMap.get(_PRIVATE_LAYOUT);
		Layout privateUserLayout = layoutsMap.get(_PRIVATE_USER_LAYOUT);
		Layout publicLayout = layoutsMap.get(_PUBLIC_LAYOUT);
		Layout publicUserLayout = layoutsMap.get(_PUBLIC_USER_LAYOUT);

		return StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_ID$]", "[$GROUP_ID_USER$]", "[$LAYOUT_ID_PRIVATE$]",
				"[$LAYOUT_ID_PRIVATE_USER$]", "[$LAYOUT_ID_PUBLIC$]",
				"[$LAYOUT_ID_PUBLIC_USER$]"
			},
			new String[] {
				String.valueOf(privateLayout.getGroupId()),
				String.valueOf(privateUserLayout.getGroupId()),
				String.valueOf(privateLayout.getLayoutId()),
				String.valueOf(privateUserLayout.getLayoutId()),
				String.valueOf(publicLayout.getLayoutId()),
				String.valueOf(publicUserLayout.getLayoutId()),
			});
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

	protected void validateDDMXSD(String xsd) throws Exception {
		DDMXMLImpl ddmXMLImpl = new DDMXMLImpl();

		XMLSchemaImpl xmlSchema = new XMLSchemaImpl();

		xmlSchema.setSchemaLanguage("http://www.w3.org/2001/XMLSchema");
		xmlSchema.setSystemId(
			"http://www.liferay.com/dtd/liferay-ddm-structure_6_2_0.xsd");

		ddmXMLImpl.setXMLSchema(xmlSchema);

		ddmXMLImpl.validateXML(xsd);
	}

	private static final String _PRIVATE_LAYOUT = "privateLayout";

	private static final String _PRIVATE_USER_LAYOUT = "privateUserLayout";

	private static final String _PUBLIC_LAYOUT = "publicLayout";

	private static final String _PUBLIC_USER_LAYOUT = "publicUserLayout";

	private DDMStructure _ddmStructure;
	private final Locale _enLocale = LocaleUtil.fromLanguageId("en_US");
	private final Locale _ptLocale = LocaleUtil.fromLanguageId("pt_BR");

}