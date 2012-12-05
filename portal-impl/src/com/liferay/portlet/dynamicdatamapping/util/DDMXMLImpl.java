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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public class DDMXMLImpl implements DDMXML {

	public String formatXML(Document document) throws SystemException {
		try {
			return document.formattedString(_XML_INDENT);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public String formatXML(String xml) throws SystemException {

		// This is only supposed to format your xml, however, it will also
		// unwantingly change &#169; and other characters like it into their
		// respective readable versions

		try {
			xml = StringUtil.replace(xml, "&#", "[$SPECIAL_CHARACTER$]");
			xml = XMLFormatter.toString(xml, _XML_INDENT);
			xml = StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "&#");

			return xml;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (org.dom4j.DocumentException de) {
			throw new SystemException(de);
		}
	}

	public Fields getFields(DDMStructure structure, String xml)
		throws PortalException, SystemException {

		return getFields(structure, null, xml, null);
	}

	public Fields getFields(
			DDMStructure structure, XPath xPath, String xml,
			List<String> fieldNames)
		throws PortalException, SystemException {

		Document document = null;

		try {
			document = SAXReaderUtil.read(xml);
		}
		catch (DocumentException e) {
			return null;
		}

		if ((xPath != null) && !xPath.booleanValueOf(document)) {
			return null;
		}

		Fields fields = new Fields();

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			String fieldName = dynamicElementElement.attributeValue("name");
			String fieldValue = dynamicElementElement.elementText(
				"dynamic-content");

			if (!structure.hasField(fieldName) ||
				((fieldNames != null) && !fieldNames.contains(fieldName))) {

				continue;
			}

			String fieldDataType = structure.getFieldDataType(fieldName);

			Serializable fieldValueSerializable =
				FieldConstants.getSerializable(fieldDataType, fieldValue);

			Field field = fields.get(fieldName);

			if (field == null) {
				field = new Field(
					structure.getStructureId(), fieldName,
					fieldValueSerializable);

				fields.put(field);
			}
			else {
				field.addValue(fieldValueSerializable);
			}
		}

		return fields;
	}

	public String getXML(Document document, Fields fields)
		throws SystemException {

		Element rootElement = null;

		try {
			if (document != null) {
				rootElement = document.getRootElement();
			}
			else {
				document = SAXReaderUtil.createDocument();

				rootElement = document.addElement("root");
			}

			Iterator<Field> itr = fields.iterator();

			while (itr.hasNext()) {
				Field field = itr.next();

				List<Node> nodes = getElementsByName(document, field.getName());

				for (Node node : nodes) {
					document.remove(node);
				}

				appendField(rootElement, field);
			}

			return document.formattedString();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public String getXML(Fields fields) throws SystemException {
		return getXML(null, fields);
	}

	public String updateXMLDefaultLocale(
			String xml, Locale contentDefaultLocale,
			Locale contentNewDefaultLocale)
		throws SystemException {

		try {
			if (LocaleUtil.equals(
					contentDefaultLocale, contentNewDefaultLocale)) {

				return xml;
			}

			Document document = SAXReaderUtil.read(xml);

			Element rootElement = document.getRootElement();

			Attribute availableLocalesAttribute = rootElement.attribute(
				_AVAILABLE_LOCALES);

			String contentNewDefaultLanguageId = LocaleUtil.toLanguageId(
				contentNewDefaultLocale);

			String availableLocalesAttributeValue =
				availableLocalesAttribute.getValue();

			if (!availableLocalesAttributeValue.contains(
					contentNewDefaultLanguageId)) {

				StringBundler sb = new StringBundler(3);

				sb.append(availableLocalesAttribute.getValue());
				sb.append(StringPool.COMMA);
				sb.append(contentNewDefaultLanguageId);

				availableLocalesAttribute.setValue(sb.toString());
			}

			Attribute defaultLocaleAttribute = rootElement.attribute(
				_DEFAULT_LOCALE);

			defaultLocaleAttribute.setValue(contentNewDefaultLanguageId);

			fixElementsDefaultLocale(
				rootElement, contentDefaultLocale, contentNewDefaultLocale);

			return document.formattedString();
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void appendField(Element element, Field field) {
		for (Serializable fieldValue : field.getValues()) {
			Element dynamicElementElement = element.addElement(
				"dynamic-element");

			dynamicElementElement.addElement("dynamic-content");

			updateField(dynamicElementElement, field.getName(), fieldValue);
		}
	}

	protected void fixElementsDefaultLocale(
		Element element, Locale contentDefaultLocale,
		Locale contentNewDefaultLocale) {

		for (Element dynamicElementElement :
				element.elements(_DYNAMIC_ELEMENT)) {

			Element importMetaDataElement =
				(Element)dynamicElementElement.selectSingleNode(
					"meta-data[@locale='" + contentNewDefaultLocale.toString() +
						"']");

			if (importMetaDataElement == null) {
				Element metaDataElement =
					(Element)dynamicElementElement.selectSingleNode(
						"meta-data[@locale='" +
							contentDefaultLocale.toString() + "']");

				Element copiedMetadataElement = metaDataElement.createCopy();

				Attribute localeAttribute = copiedMetadataElement.attribute(
					_LOCALE);

				String contentNewDefaultLanguageId = LocaleUtil.toLanguageId(
					contentNewDefaultLocale);

				localeAttribute.setValue(contentNewDefaultLanguageId);

				dynamicElementElement.add(copiedMetadataElement);
			}

			fixElementsDefaultLocale(
				dynamicElementElement, contentDefaultLocale,
				contentNewDefaultLocale);
		}
	}

	protected List<Node> getElementsByName(Document document, String name) {
		name = HtmlUtil.escapeXPathAttribute(name);

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@name=".concat(name).concat("]"));

		return xPathSelector.selectNodes(document);
	}

	protected void updateField(
		Element element, String fieldName, Serializable fieldValue) {

		Element dynamicContentElement = element.element("dynamic-content");

		element.addAttribute("name", fieldName);

		dynamicContentElement.clearContent();

		if (fieldValue instanceof Date) {
			Date valueDate = (Date)fieldValue;

			fieldValue = valueDate.getTime();
		}

		String valueString = String.valueOf(fieldValue);

		if (valueString != null) {
			valueString = valueString.trim();
		}

		dynamicContentElement.addCDATA(valueString);
	}

	private static final String _AVAILABLE_LOCALES = "available-locales";

	private static final String _DEFAULT_LOCALE = "default-locale";

	private static final String _DYNAMIC_ELEMENT = "dynamic-element";

	private static final String _LOCALE = "locale";

	private static final String _XML_INDENT = "  ";

}