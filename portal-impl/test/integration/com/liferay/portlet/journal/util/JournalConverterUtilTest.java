/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
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
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalConverterUtilTest {

	public JournalConverterUtilTest() throws Exception {
		String xsd = readText("dynamic-data-mapping-structure.xml");

		_ddmStructure = new DDMStructureImpl();

		_ddmStructure.setStructureId(ServiceTestUtil.nextLong());
		_ddmStructure.setXsd(xsd);

		_enLocale = LocaleUtil.fromLanguageId("en_US");
		_ptLocale = LocaleUtil.fromLanguageId("pt_BR");
	}

	@Test
	public void testGetFieldsFromXMLWithBooleanElement() throws Exception {
		Fields expectedFields = new Fields();

		Field booleanField = getBooleanField(_ddmStructure.getStructureId());

		expectedFields.put(booleanField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME,
			"boolean_INSTANCE_Okhyj6Ni,boolean_INSTANCE_1SYNQuhg");

		expectedFields.put(fieldsDisplay);

		String xml = readText(
			"sample-journal-content-boolean-repeatable-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, xml);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromXMLWithDocLibraryElement() throws Exception {
		Fields expectedFields = new Fields();

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true, "Test 1.txt");

		Field docLibraryField = getDocLibraryField(
			fileEntry, _ddmStructure.getStructureId());

		expectedFields.put(docLibraryField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "doc_library_INSTANCE_4aGOvP3N");

		expectedFields.put(fieldsDisplay);

		String xml = readText("sample-journal-content-doc-library-field.xml");

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-content");

		Document document = SAXReaderUtil.read(xml);

		Element element = (Element)xPathSelector.selectSingleNode(document);

		String previewURL = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, true);

		element.addCDATA(previewURL);

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, document.asXML());

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromXMLWithLinkToPageElement() throws Exception {
		Fields expectedFields = new Fields();

		Field linkToPageField = getLinkToPageField(
			_ddmStructure.getStructureId());

		expectedFields.put(linkToPageField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "link_INSTANCE_MiO7vIJu");

		expectedFields.put(fieldsDisplay);

		String xml = readText("sample-journal-content-link-to-page-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, xml);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromXMLWithMultiSelectElement() throws Exception {
		Fields expectedFields = new Fields();

		Field multiSelectField = getMultiSelectField(
			_ddmStructure.getStructureId());

		expectedFields.put(multiSelectField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "multi_select_INSTANCE_9X5wVsSv");

		expectedFields.put(fieldsDisplay);

		String xml = readText("sample-journal-content-multi-list-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, xml);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromXMLWithNestedElements() throws Exception {
		Fields expectedFields = getNestedFields(_ddmStructure.getStructureId());

		String xml = readText("sample-journal-content-nested-fields.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, xml);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetFieldsFromXMLWithSelectElement() throws Exception {
		Fields expectedFields = new Fields();

		Field selectField = getSelectField(_ddmStructure.getStructureId());

		expectedFields.put(selectField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "select_INSTANCE_pcm9WPVX");

		expectedFields.put(fieldsDisplay);

		String xml = readText("sample-journal-content-list-field.xml");

		Fields actualFields = JournalConverterUtil.getDDMFields(
			_ddmStructure, xml);

		assertEquals(expectedFields, actualFields);
	}

	@Test
	public void testGetXMLFromBooleanField() throws Exception {
		Fields fields = new Fields();

		Field booleanField = getBooleanField(_ddmStructure.getStructureId());

		fields.put(booleanField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME,
			"boolean_INSTANCE_Okhyj6Ni,boolean_INSTANCE_1SYNQuhg");

		fields.put(fieldsDisplay);

		String expectedXML = readText(
			"sample-journal-content-boolean-repeatable-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromDocLibraryField() throws Exception {
		Fields fields = new Fields();

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true, "Test 2.txt");

		Field docLibrary = getDocLibraryField(
			fileEntry, _ddmStructure.getStructureId());

		fields.put(docLibrary);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "doc_library_INSTANCE_4aGOvP3N");

		fields.put(fieldsDisplay);

		String expectedXML = readText(
			"sample-journal-content-doc-library-field.xml");

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-content");

		Document document = SAXReaderUtil.read(expectedXML);

		Element element = (Element)xPathSelector.selectSingleNode(document);

		String previewURL = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, true);

		element.addCDATA(previewURL);

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(document.asXML(), actualXML);
	}

	@Test
	public void testGetXMLFromLinkToPageField() throws Exception {
		Fields fields = new Fields();

		Field linkToPageField = getLinkToPageField(
			_ddmStructure.getStructureId());

		fields.put(linkToPageField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "link_INSTANCE_MiO7vIJu");

		fields.put(fieldsDisplay);

		String expectedXML = readText(
			"sample-journal-content-link-to-page-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromMultiSelectField() throws Exception {
		Fields fields = new Fields();

		Field multiSelectField = getMultiSelectField(
			_ddmStructure.getStructureId());

		fields.put(multiSelectField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "multi_select_INSTANCE_9X5wVsSv");

		fields.put(fieldsDisplay);

		String expectedXML = readText(
			"sample-journal-content-multi-list-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromNestedFields() throws Exception {
		Fields fields = getNestedFields(_ddmStructure.getStructureId());

		String expectedXML = readText(
			"sample-journal-content-nested-fields.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromSelectField() throws Exception {
		Fields fields = new Fields();

		Field selectField = getSelectField(_ddmStructure.getStructureId());

		fields.put(selectField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "select_INSTANCE_pcm9WPVX");

		fields.put(fieldsDisplay);

		String expectedXML = readText("sample-journal-content-list-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromTextAreaField() throws Exception {
		Fields fields = new Fields();

		Field textAreaField = getTextAreaField(_ddmStructure.getStructureId());

		fields.put(textAreaField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME,
			"textArea_INSTANCE_ND057krU,textArea_INSTANCE_HvemvQgl," +
			"textArea_INSTANCE_enAnbvq6");

		fields.put(fieldsDisplay);

		String expectedXML = readText(
			"sample-journal-content-text-box-repeatable-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromTextField() throws Exception {
		Fields fields = new Fields();

		Field textField = getTextField(_ddmStructure.getStructureId());

		fields.put(textField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "text_INSTANCE_bf4sdx6Q");

		fields.put(fieldsDisplay);

		String expectedXML = readText("sample-journal-content-text-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
	}

	@Test
	public void testGetXMLFromTextHTMLField() throws Exception {
		Fields fields = new Fields();

		Field textHTMLField = getTextHTMLField(_ddmStructure.getStructureId());

		fields.put(textHTMLField);

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME, "textHTML_INSTANCE_RFnJ1nCn");

		fields.put(fieldsDisplay);

		String expectedXML = readText(
			"sample-journal-content-text-area-field.xml");

		String actualXML = JournalConverterUtil.getXML(_ddmStructure, fields);

		assertEquals(expectedXML, actualXML);
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

		actualFieldsDisplayField.setValue(expectedFieldsDisplayFieldValue);

		Assert.assertEquals(expectedFields, actualFields);
	}

	protected void assertEquals(String expectedXML, String actualXML)
		throws Exception {

		Map<String, Map<Locale, List<String>>> expectedFieldsMap = getFieldsMap(
			expectedXML);

		Map<String, Map<Locale, List<String>>> actualFieldsMap = getFieldsMap(
			actualXML);

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

	protected Field getDocLibraryField(
		FileEntry fileEntry, long ddmStructureId) {

		Field docLibraryField = new Field();

		docLibraryField.setDDMStructureId(ddmStructureId);
		docLibraryField.setName("doc_library");

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

	protected Map<String, Map<Locale, List<String>>> getFieldsMap(String xml)
		throws Exception {

		Map<String, Map<Locale, List<String>>> fieldsMap =
			new HashMap<String, Map<Locale, List<String>>>();

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			udpateFieldsMap(dynamicElementElement, fieldsMap);
		}

		return fieldsMap;
	}

	protected Field getLinkToPageField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("link");

		field.addValue(
			_enLocale, "{\"layoutId\":\"1\",\"privateLayout\":false}");

		return field;
	}

	protected Field getMultiSelectField(long ddmStructureId) {

		Field field =  new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("multi_select");

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

		Field fieldsDisplay = new Field(
			DDMImpl.FIELDS_DISPLAY_NAME,
			"contact_INSTANCE_RF3do1m5,phone_INSTANCE_QK6B0wK9," +
			"ext_INSTANCE_L67MPqQf,ext_INSTANCE_8uxzZl41," +
			"ext_INSTANCE_S58K861T,contact_INSTANCE_CUeFxcrA," +
			"phone_INSTANCE_lVTcTviF,ext_INSTANCE_cZalDSll," +
			"ext_INSTANCE_HDrK2Um5");

		fields.put(fieldsDisplay);

		return fields;
	}

	protected Field getSelectField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("select");

		field.addValue(_enLocale, "[\"a\"]");

		return field;
	}

	protected Field getTextAreaField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("textArea");

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

	protected Field getTextHTMLField(long ddmStructureId) {
		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setName("textHTML");

		field.addValue(_enLocale, "<p>Hello World!</p>");

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

	protected String readText(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

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
	private Locale _enLocale;
	private Locale _ptLocale;

}