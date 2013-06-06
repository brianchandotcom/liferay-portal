/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.service.BaseDDMServiceTestCase;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalConverterUtilTest extends BaseDDMServiceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		long classNameId = PortalUtil.getClassNameId(JournalArticle.class);

		String xsd = readText("test-ddm-structure-all-fields.xml");

		_ddmStructure = addStructure(
			classNameId, null, "Test Structure", xsd,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);
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

		String expectedContent = readText(
			"test-journal-content-boolean-repeatable-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromDocumentLibraryField() throws Exception {
		Fields fields = new Fields();

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true, "Test 2.txt");

		Field docLibrary = getDocumentLibraryField(
			fileEntry, _ddmStructure.getStructureId());

		fields.put(docLibrary);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"document_library_INSTANCE_4aGOvP3N");

		fields.put(fieldsDisplayField);

		String expectedContent = readText(
			"test-journal-content-doc-library-field.xml");

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-content");

		Document document = SAXReaderUtil.read(expectedContent);

		Element element = (Element)xPathSelector.selectSingleNode(document);

		String previewURL = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, true);

		element.addCDATA(previewURL);

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(document.asXML(), actualContent);
	}

	@Test
	public void testGetContentFromLinkToLayoutField() throws Exception {
		Fields fields = new Fields();

		Field linkToLayoutField = getLinkToLayoutField(
			_ddmStructure.getStructureId());

		fields.put(linkToLayoutField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "link_to_layout_INSTANCE_MiO7vIJu");

		fields.put(fieldsDisplayField);

		String expectedContent = readText(
			"test-journal-content-link-to-page-field.xml");

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

		String expectedContent = readText(
			"test-journal-content-list-field.xml");

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

		String expectedContent = readText(
			"test-journal-content-multi-list-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetContentFromNestedFields() throws Exception {
		Fields fields = getNestedFields(_ddmStructure.getStructureId());

		String expectedContent = readText(
			"test-journal-content-nested-fields.xml");

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

		String expectedContent = readText(
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

		String expectedContent = readText(
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

		String expectedContent = readText(
			"test-journal-content-text-field.xml");

		String actualContent = JournalConverterUtil.getContent(
			_ddmStructure, fields);

		assertEquals(expectedContent, actualContent);
	}

	@Test
	public void testGetDDMXSD() throws Exception {
		DDMStructure expectedDDMStructure = new DDMStructureImpl();

		expectedDDMStructure.setXsd(
			readText("test-ddm-structure-all-fields.xml"));

		DDMStructure actualDDMStructure = new DDMStructureImpl();

		actualDDMStructure.setXsd(
			JournalConverterUtil.getDDMXSD(
				readText("test-journal-structure-all-fields.xml")));

		Assert.assertEquals(
			expectedDDMStructure.getFieldsMap(),
			actualDDMStructure.getFieldsMap());
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

		String content = readText(
			"test-journal-content-boolean-repeatable-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithDocumentLibraryElement()
		throws Exception {

		Fields expectedFields = new Fields();

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true, "Test 1.txt");

		Field documentLibraryField = getDocumentLibraryField(
			fileEntry, _ddmStructure.getStructureId());

		expectedFields.put(documentLibraryField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(),
			"document_library_INSTANCE_4aGOvP3N");

		expectedFields.put(fieldsDisplayField);

		String content = readText("test-journal-content-doc-library-field.xml");

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

			assertEquals(expectedFields, actualFields);
		}
	}

	@Test
	public void testGetFieldsFromContentWithLinkToLayoutElement()
		throws Exception {

		Fields expectedFields = new Fields();

		Field linkToLayoutField = getLinkToLayoutField(
			_ddmStructure.getStructureId());

		expectedFields.put(linkToLayoutField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "link_to_layout_INSTANCE_MiO7vIJu");

		expectedFields.put(fieldsDisplayField);

		String content = readText(
			"test-journal-content-link-to-page-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithListElement() throws Exception {
		Fields expectedFields = new Fields();

		Field listField = getListField(_ddmStructure.getStructureId());

		expectedFields.put(listField);

		Field fieldsDisplayField = getFieldsDisplayField(
			_ddmStructure.getStructureId(), "list_INSTANCE_pcm9WPVX");

		expectedFields.put(fieldsDisplayField);

		String content = readText("test-journal-content-list-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		assertEquals(expectedFields, actualFields);
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

		String content = readText("test-journal-content-multi-list-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromContentWithNestedElements() throws Exception {
		Fields expectedFields = getNestedFields(_ddmStructure.getStructureId());

		String content = readText("test-journal-content-nested-fields.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, content);

		assertEquals(expectedFields, actualFields);
	}

	protected void assertEquals(Fields expectedFields, Fields actualFields) {
		Field expectedFieldsDisplayField = expectedFields.get(
			DDMImpl.FIELDS_DISPLAY_NAME);

		String expectedFieldsDisplayFieldValue =
			(String)expectedFieldsDisplayField.getValue();

		String regex = DDMImpl.INSTANCE_SEPARATOR.concat("\\w{8}");

		expectedFieldsDisplayFieldValue =
			expectedFieldsDisplayFieldValue.replaceAll(regex, StringPool.BLANK);

		expectedFieldsDisplayField.setValue(expectedFieldsDisplayFieldValue);

		Field actualFieldsDisplayField = actualFields.get(
			DDMImpl.FIELDS_DISPLAY_NAME);

		String actualFieldsDisplayFieldValue =
			(String)actualFieldsDisplayField.getValue();

		actualFieldsDisplayFieldValue =
			actualFieldsDisplayFieldValue.replaceAll(regex, StringPool.BLANK);

		actualFieldsDisplayField.setValue(actualFieldsDisplayFieldValue);

		Assert.assertEquals(expectedFields, actualFields);
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

		List<Serializable> enValues = new ArrayList<Serializable>();

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

		StringBundler sb = new StringBundler(7);

		sb.append("{\"groupId\":");
		sb.append(fileEntry.getGroupId());
		sb.append(",\"uuid\":\"");
		sb.append(fileEntry.getUuid());
		sb.append("\",\"version\":\"");
		sb.append(fileEntry.getVersion());
		sb.append("\"}");

		docLibraryField.addValue(_enLocale, sb.toString());

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

		Map<String, Map<Locale, List<String>>> fieldsMap =
			new HashMap<String, Map<Locale, List<String>>>();

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			udpateFieldsMap(dynamicElementElement, fieldsMap);
		}

		return fieldsMap;
	}

	protected Field getLinkToLayoutField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("link_to_layout");

		field.addValue(
			_enLocale, "{\"layoutId\":\"1\",\"privateLayout\":false}");

		return field;
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

		List<Serializable> enValues = new ArrayList<Serializable>();

		enValues.add("joe");
		enValues.add("richard");

		contact.setValues(_enLocale, enValues);

		List<Serializable> ptValues = new ArrayList<Serializable>();

		ptValues.add("joao");
		ptValues.add("ricardo");

		contact.addValues(_ptLocale, ptValues);

		fields.put(contact);

		// Phone

		Field phone = new Field();

		phone.setDDMStructureId(ddmStructureId);
		phone.setName("phone");

		List<Serializable> values = new ArrayList<Serializable>();

		values.add("123");
		values.add("456");

		phone.setValues(_enLocale, values);
		phone.addValues(_ptLocale, values);

		fields.put(phone);

		// Ext

		Field ext = new Field();

		ext.setDDMStructureId(ddmStructureId);
		ext.setName("ext");

		values = new ArrayList<Serializable>();

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

		List<Serializable> enValues = new ArrayList<Serializable>();

		enValues.add("one");
		enValues.add("two");
		enValues.add("three");

		field.addValues(_enLocale, enValues);

		List<Serializable> ptValues = new ArrayList<Serializable>();

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
			values = new ArrayList<String>();

			valuesMap.put(locale, values);
		}

		return values;
	}

	@Override
	protected String readText(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portlet/journal/dependencies/" + fileName);

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
			valuesMap = new HashMap<Locale,  List<String>>();

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

			if (optionElements.size() > 0) {
				for (Element optionElement : optionElements) {
					values.add(optionElement.getText());
				}
			}
			else {
				values.add(dynamicContentElement.getText());
			}
		}
	}

	private DDMStructure _ddmStructure;
	private Locale _enLocale = LocaleUtil.fromLanguageId("en_US");
	private Locale _ptLocale = LocaleUtil.fromLanguageId("pt_BR");

}