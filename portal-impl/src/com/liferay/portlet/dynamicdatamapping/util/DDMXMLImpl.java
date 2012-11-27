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
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public class DDMXMLImpl implements DDMXML {

	public String formatXML(Document document) throws IOException {
		return document.formattedString(_XML_INDENT);
	}

	public String formatXML(String xml) throws DocumentException, IOException {

		// This is only supposed to format your xml, however, it will also
		// unwantingly change &#169; and other characters like it into their
		// respective readable versions

		xml = StringUtil.replace(xml, "&#", "[$SPECIAL_CHARACTER$]");

		try {
			xml = XMLFormatter.toString(xml, _XML_INDENT);
		}
		catch (org.dom4j.DocumentException de) {
			throw new DocumentException(de.getMessage());
		}

		xml = StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "&#");

		return xml;
	}

	public String getXML(Fields fields) throws Exception {
		return getXML(0, fields, false);
	}

	public String getXML(long ddmContentId, Fields fields, boolean mergeFields)
		throws Exception {

		Document document = null;
		Element rootElement = null;

		if (mergeFields && (ddmContentId > 0)) {
			DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(
				ddmContentId);

			document = SAXReaderUtil.read(ddmContent.getXml());
			rootElement = document.getRootElement();
		}
		else {
			document = SAXReaderUtil.createDocument();
			rootElement = document.addElement("root");
		}

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			Object value = field.getValue();

			if (value instanceof Date) {
				Date valueDate = (Date)value;

				value = valueDate.getTime();
			}

			String valueString = String.valueOf(value);

			if (valueString != null) {
				valueString = valueString.trim();
			}

			Element dynamicElementElement = getElementByName(
				document, field.getName());

			if (dynamicElementElement == null) {
				appendField(rootElement, field.getName(), valueString);
			}
			else {
				updateField(
					dynamicElementElement, field.getName(), valueString);
			}
		}

		return document.formattedString();
	}

	public String updateXMLDefaultLocale(
			String xml, Locale contentDefaultLocale,
			Locale contentNewDefaultLocale)
		throws DocumentException, IOException {

		if (LocaleUtil.equals(contentDefaultLocale, contentNewDefaultLocale)) {
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

	protected Element appendField(
		Element rootElement, String fieldName, String fieldValue) {

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addElement("dynamic-content");

		updateField(dynamicElementElement, fieldName, fieldValue);

		return dynamicElementElement;
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

	protected Element getElementByName(Document document, String name) {
		name = HtmlUtil.escapeXPathAttribute(name);

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@name=".concat(name).concat("]"));

		List<Node> nodes = xPathSelector.selectNodes(document);

		if (nodes.size() == 1) {
			return (Element)nodes.get(0);
		}
		else {
			return null;
		}
	}

	protected void updateField(
		Element dynamicElementElement, String fieldName, String value) {

		Element dynamicContentElement = dynamicElementElement.element(
			"dynamic-content");

		dynamicElementElement.addAttribute("name", fieldName);

		dynamicContentElement.clearContent();

		dynamicContentElement.addCDATA(value);
	}

	private static final String _AVAILABLE_LOCALES = "available-locales";

	private static final String _DEFAULT_LOCALE = "default-locale";

	private static final String _DYNAMIC_ELEMENT = "dynamic-element";

	private static final String _LOCALE = "locale";

	private static final String _XML_INDENT = "  ";

}