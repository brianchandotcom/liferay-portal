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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMXML;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.xml.XMLSchema;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.xml.XMLSchemaImpl;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DDMXML.class)
public class DDMXMLImpl implements DDMXML {

	@Override
	public Fields getFields(DDMStructure structure, String xml)
		throws PortalException {

		return getFields(structure, null, xml, null);
	}

	@Override
	public Fields getFields(
			DDMStructure structure, XPath xPath, String xml,
			List<String> fieldNames)
		throws PortalException {

		Document document = null;

		try {
			document = _saxReader.read(xml);
		}
		catch (DocumentException de) {
			if (_log.isDebugEnabled()) {
				_log.debug(de.getMessage(), de);
			}

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

			if (!structure.hasField(fieldName) ||
				((fieldNames != null) && !fieldNames.contains(fieldName))) {

				continue;
			}

			String fieldDataType = structure.getFieldDataType(fieldName);

			List<Element> dynamicContentElements =
				dynamicElementElement.elements("dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String fieldValue = dynamicContentElement.getText();

				String languageId = dynamicContentElement.attributeValue(
					"language-id");

				Locale locale = LocaleUtil.fromLanguageId(languageId);

				Serializable fieldValueSerializable =
					FieldConstants.getSerializable(fieldDataType, fieldValue);

				Field field = fields.get(fieldName);

				if (field == null) {
					field = new Field();

					String defaultLanguageId =
						dynamicElementElement.attributeValue(
							"default-language-id");

					if (Validator.isNull(defaultLanguageId)) {
						defaultLanguageId = rootElement.attributeValue(
							"default-locale");
					}

					Locale defaultLocale = LocaleUtil.fromLanguageId(
						defaultLanguageId);

					field.setDefaultLocale(defaultLocale);

					field.setDDMStructureId(structure.getStructureId());
					field.setName(fieldName);
					field.setValue(locale, fieldValueSerializable);

					fields.put(field);
				}
				else {
					field.addValue(locale, fieldValueSerializable);
				}
			}
		}

		return fields;
	}

	@Override
	public String getXML(Document document, Fields fields) {
		Element rootElement = null;

		try {
			if (document != null) {
				rootElement = document.getRootElement();
			}
			else {
				document = _saxReader.createDocument();

				rootElement = document.addElement("root");
			}

			Iterator<Field> itr = fields.iterator(true);

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

	@Override
	public String getXML(Fields fields) {
		return getXML(null, fields);
	}

	public XMLSchema getXMLSchema() {
		if (_xmlSchema == null) {
			XMLSchemaImpl xmlSchemaImpl = new XMLSchemaImpl();

			xmlSchemaImpl.setSchemaLanguage("http://www.w3.org/2001/XMLSchema");
			xmlSchemaImpl.setSystemId(
				"http://www.liferay.com/dtd/liferay-ddm-structure_6_2_0.xsd");

			_xmlSchema = xmlSchemaImpl;
		}

		return _xmlSchema;
	}

	@Override
	public String validateXML(String xml) throws PortalException {
		try {
			Document document = _saxReader.read(xml, getXMLSchema());

			validate(document);

			return document.asXML();
		}
		catch (StructureDefinitionException sde) {
			throw sde;
		}
		catch (StructureDuplicateElementException sdee) {
			throw sdee;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid XML content " + e.getMessage(), e);
			}

			throw new StructureDefinitionException(e);
		}
	}

	protected void appendField(Element element, Field field) {
		Element dynamicElementElement = element.addElement("dynamic-element");

		dynamicElementElement.addAttribute(
			"default-language-id",
			LocaleUtil.toLanguageId(field.getDefaultLocale()));
		dynamicElementElement.addAttribute("name", field.getName());

		for (Locale locale : field.getAvailableLocales()) {
			List<Serializable> values = field.getValues(locale);

			for (Serializable value : values) {
				Element dynamicContentElement =
					dynamicElementElement.addElement("dynamic-content");

				dynamicContentElement.addAttribute(
					"language-id", LocaleUtil.toLanguageId(locale));

				updateField(dynamicContentElement, value);
			}
		}
	}

	protected List<Node> getElementsByName(Document document, String name) {
		name = HtmlUtil.escapeXPathAttribute(name);

		XPath xPathSelector = _saxReader.createXPath(
			"//dynamic-element[@name=".concat(
				name
			).concat(
				"]"
			));

		return xPathSelector.selectNodes(document);
	}

	@Reference(unbind = "-")
	protected void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	protected void updateField(
		Element dynamicContentElement, Serializable fieldValue) {

		dynamicContentElement.clearContent();

		if (fieldValue instanceof Date) {
			Date valueDate = (Date)fieldValue;

			fieldValue = valueDate.getTime();
		}

		String valueString = String.valueOf(fieldValue);

		dynamicContentElement.addCDATA(valueString.trim());
	}

	protected void validate(Document document) throws Exception {
		XPath xPathSelector = _saxReader.createXPath("//dynamic-element");

		List<Node> nodes = xPathSelector.selectNodes(document);

		Set<String> elementNames = new HashSet<>();

		for (Node node : nodes) {
			Element element = (Element)node;

			String name = StringUtil.toLowerCase(
				element.attributeValue("name"));

			if (Validator.isNull(name)) {
				throw new StructureDefinitionException(
					"Element must have a name attribute " +
						element.formattedString());
			}

			if (name.startsWith(DDMStructureConstants.XSD_NAME_RESERVED)) {
				throw new StructureDefinitionException(
					"Element name " + name + " is reserved");
			}

			if (elementNames.contains(name)) {
				throw new StructureDuplicateElementException(
					"Element with name " + name + " already exists");
			}

			elementNames.add(name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMXMLImpl.class);

	private SAXReader _saxReader;
	private XMLSchema _xmlSchema;

}