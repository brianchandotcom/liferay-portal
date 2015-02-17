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

package com.liferay.portlet.dynamicdatamapping.util.test;

import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureTestUtil {

	public static DDMStructure addStructure(long groupId, String className)
		throws Exception {

		return addStructure(
			groupId, className, 0, getSampleStructureDefinition(),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, Locale defaultLocale)
		throws Exception {

		return addStructure(
			groupId, className, 0, getSampleStructureDefinition(),
			defaultLocale, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, long parentStructureId)
		throws Exception {

		return addStructure(
			groupId, className, parentStructureId,
			getSampleStructureDefinition(), LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, long parentStructureId,
			String definition, Locale defaultLocale,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(defaultLocale, "Test Structure");

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);
		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);
		String ddlStorageType = GetterUtil.getString(
			PropsUtil.get(PropsKeys.DYNAMIC_DATA_LISTS_STORAGE_TYPE));

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, parentStructureId,
			PortalUtil.getClassNameId(className), null, nameMap, null, ddmForm,
			ddmFormLayout, ddlStorageType, DDMStructureConstants.TYPE_DEFAULT,
			serviceContext);
	}

	public static DDMStructure addStructure(
			long groupId, String className, String definition)
		throws Exception {

		return addStructure(
			groupId, className, 0, definition, LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, String definition,
			Locale defaultLocale)
		throws Exception {

		return addStructure(
			groupId, className, 0, definition, defaultLocale,
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(String className) throws Exception {
		return addStructure(
			TestPropsValues.getGroupId(), className, 0,
			getSampleStructureDefinition(), LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0,
			getSampleStructureDefinition(
				"name", new Locale[] {LocaleUtil.US}, defaultLocale),
			defaultLocale, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, Locale[] availableLocales, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0,
			getSampleStructureDefinition(
				"name", availableLocales, defaultLocale),
			defaultLocale, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(String className, String definition)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, definition,
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, String definition, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, definition,
			defaultLocale, ServiceContextTestUtil.getServiceContext());
	}

	public static String getSampleStructuredContent() {
		return getSampleStructuredContent("name", "title");
	}

	public static String getSampleStructuredContent(
		Map<Locale, String> contents, String defaultLocale) {

		return getSampleStructuredContent("name", contents, defaultLocale);
	}

	public static String getSampleStructuredContent(String keywords) {
		return getSampleStructuredContent("name", keywords);
	}

	public static String getSampleStructuredContent(
		String name, Map<Locale, String> contents, String defaultLocale) {

		StringBundler sb = new StringBundler(2 * contents.size());

		for (Map.Entry<Locale, String> content : contents.entrySet()) {
			Locale locale = content.getKey();

			sb.append(LocaleUtil.toLanguageId(locale));
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		Document document = createDocumentContent(sb.toString(), defaultLocale);

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("index-type", "keyword");
		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", "text");

		for (Map.Entry<Locale, String> content : contents.entrySet()) {
			Element dynamicContentElement = dynamicElementElement.addElement(
				"dynamic-content");

			dynamicContentElement.addAttribute(
				"language-id", LocaleUtil.toLanguageId(content.getKey()));
			dynamicContentElement.addCDATA(content.getValue());
		}

		return document.asXML();
	}

	public static String getSampleStructuredContent(
		String name, String keywords) {

		Map<Locale, String> contents = new HashMap<>();

		contents.put(Locale.US, keywords);

		return getSampleStructuredContent(name, contents, "en_US");
	}

	public static String getSampleStructureDefinition() {
		return getSampleStructureDefinition("name");
	}

	public static String getSampleStructureDefinition(
		Locale[] availableLocales, Locale defaultLocale) {

		return getSampleStructureDefinition(
			"name", availableLocales, defaultLocale);
	}

	public static String getSampleStructureDefinition(String name) {
		return getSampleStructureDefinition(
			name, new Locale[] {LocaleUtil.US}, LocaleUtil.US);
	}

	public static String getSampleStructureDefinition(
		String name, Locale[] availableLocales, Locale defaultLocale) {

		return getSampleStructureDefinition(
			name, "string", true, "text", availableLocales, defaultLocale);
	}

	public static String getSampleStructureDefinition(
		String name, String dataType, boolean repeatable, String type,
		Locale[] availableLocales, Locale defaultLocale) {

		Document document = createDocumentStructure(
			availableLocales, defaultLocale);

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("dataType", dataType);
		dynamicElementElement.addAttribute("indexType", "text");
		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute(
			"repeatable", Boolean.toString(repeatable));
		dynamicElementElement.addAttribute("required", "false");
		dynamicElementElement.addAttribute("type", type);

		Element metaDataElement = dynamicElementElement.addElement("meta-data");

		metaDataElement.addAttribute(
			"locale", LocaleUtil.toLanguageId(defaultLocale));

		Element labelElement = metaDataElement.addElement("entry");

		labelElement.addAttribute("name", "label");
		labelElement.addCDATA("Field");

		return document.asXML();
	}

	public static Map<String, Map<String, String>> getXSDMap(String xsd)
		throws Exception {

		Map<String, Map<String, String>> map = new HashMap<>();

		Document document = SAXReaderUtil.read(xsd);

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-element");

		List<Node> nodes = xPathSelector.selectNodes(document);

		for (Node node : nodes) {
			Element dynamicElementElement = (Element)node;

			String elementName = getElementName(dynamicElementElement);

			map.put(elementName, getElementMap(dynamicElementElement));
		}

		return map;
	}

	protected static Document createDocumentContent(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

	protected static Document createDocumentStructure(
		Locale[] availableLocales, Locale defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute(
			"available-locales",
			StringUtil.merge(LocaleUtil.toLanguageIds(availableLocales)));
		rootElement.addAttribute(
			"default-locale", LocaleUtil.toLanguageId(defaultLocale));

		return document;
	}

	protected static Map<String, String> getElementMap(Element element) {
		Map<String, String> elementMap = new HashMap<>();

		// Attributes

		for (Attribute attribute : element.attributes()) {
			elementMap.put(attribute.getName(), attribute.getValue());
		}

		// Metadata

		for (Element metadadataElement : element.elements("meta-data")) {
			String metadataLanguageId = metadadataElement.attributeValue(
				"locale");

			for (Element entryElement : metadadataElement.elements("entry")) {
				String entryName = entryElement.attributeValue("name");

				elementMap.put(
					entryName.concat(metadataLanguageId),
					entryElement.getText());
			}
		}

		return elementMap;
	}

	protected static String getElementName(Element element) {
		StringBuilder sb = new StringBuilder();

		sb.append(element.attributeValue("name"));

		Element parentElement = element.getParent();

		while (true) {
			if ((parentElement == null) ||
				parentElement.getName().equals("root")) {

				break;
			}

			sb.insert(
				0, parentElement.attributeValue("name") + StringPool.SLASH);

			parentElement = parentElement.getParent();
		}

		String type = element.attributeValue("type");

		if (Validator.equals(type, "option")) {
			sb.append(StringPool.SLASH);

			sb.append(element.attributeValue("value"));
		}

		return sb.toString();
	}

}