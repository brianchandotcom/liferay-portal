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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.CacheField;
import com.liferay.portlet.dynamicdatamapping.StructureFieldException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureImpl extends DDMStructureBaseImpl {

	public DDMStructureImpl() {
	}

	public List<String> getAvailableLanguageIds() {
		Document document = getDocument();

		Element rootElement = document.getRootElement();

		String availableLocales = rootElement.attributeValue(
			"available-locales");

		return ListUtil.fromArray(StringUtil.split(availableLocales));
	}

	public String getCompleteXsd() throws PortalException, SystemException {
		if (getParentStructureId() == 0) {
			return getXsd();
		}

		DDMStructure parentStructure =
			DDMStructureLocalServiceUtil.getStructure(getParentStructureId());

		return _mergeXsds(getXsd(), parentStructure.getCompleteXsd());
	}

	public String getDefaultLanguageId() {
		Document document = getDocument();

		if (document == null) {
			Locale locale = LocaleUtil.getDefault();

			return locale.toString();
		}

		Element rootElement = document.getRootElement();

		return rootElement.attributeValue("default-locale");
	}

	@Override
	public Document getDocument() {
		if (_document == null) {
			try {
				_document = SAXReaderUtil.read(getXsd());
			}
			catch (Exception e) {
				StackTraceElement[] stackTraceElements = e.getStackTrace();

				for (StackTraceElement stackTraceElement : stackTraceElements) {
					String className = stackTraceElement.getClassName();

					if (className.endsWith("DDMStructurePersistenceTest")) {
						return null;
					}
				}

				_log.error(e, e);
			}
		}

		return _document;
	}

	public String getFieldDataType(String fieldName)
		throws PortalException, SystemException {

		return getFieldProperty(fieldName, "dataType");
	}

	public String getFieldLabel(String fieldName, Locale locale)
		throws PortalException, SystemException {

		return getFieldLabel(fieldName, LocaleUtil.toLanguageId(locale));
	}

	public String getFieldLabel(String fieldName, String locale)
		throws PortalException, SystemException {

		return GetterUtil.getString(
			getFieldProperty(fieldName, "label", locale), fieldName);
	}

	public Set<String> getFieldNames() throws PortalException, SystemException {
		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		return fieldsMap.keySet();
	}

	public String getFieldProperty(String fieldName, String property)
		throws PortalException, SystemException {

		return getFieldProperty(fieldName, property, getDefaultLanguageId());
	}

	public String getFieldProperty(
			String fieldName, String property, String locale)
		throws PortalException, SystemException {

		if (!hasField(fieldName)) {
			throw new StructureFieldException();
		}

		Map<String, Map<String, String>> fieldsMap = getFieldsMap(locale);

		Map<String, String> field = fieldsMap.get(fieldName);

		return field.get(property);
	}

	public boolean getFieldRequired(String fieldName)
		throws PortalException, SystemException {

		return GetterUtil.getBoolean(getFieldProperty(fieldName, "required"));
	}

	public Map<String, String> getFields(
		String fieldName, String attributeName, String attributeValue) {

		return getFields(
			fieldName, attributeName, attributeValue, getDefaultLanguageId());
	}

	public Map<String, String> getFields(
		String fieldName, String attributeName, String attributeValue,
		String locale) {

		try {
			if ((attributeName == null) || (attributeValue == null)) {
				return null;
			}

			Map<String, Map<String, String>> fieldsMap = getTransientFieldsMap(
				locale);

			for (Map<String, String> fields : fieldsMap.values()) {
				String parentName = fields.get(
					_getPrivateAttributeKey("parentName"));

				if (!fieldName.equals(parentName)) {
					continue;
				}

				if (attributeValue.equals(fields.get(attributeName))) {
					return fields;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	public Map<String, Map<String, String>> getFieldsMap()
		throws PortalException, SystemException {

		return getFieldsMap(getDefaultLanguageId());
	}

	public Map<String, Map<String, String>> getFieldsMap(String locale)
		throws PortalException, SystemException {

		_indexFieldsMap(locale);

		Map<String, Map<String, String>> fieldsMap = _localizedFieldsMap.get(
			locale);

		return fieldsMap;
	}

	public String getFieldType(String fieldName)
		throws PortalException, SystemException {

		return getFieldProperty(fieldName, "type");
	}

	@Override
	public Map<String, Map<String, Map<String, String>>>
		getLocalizedFieldsMap() {

		return _localizedFieldsMap;
	}

	@Override
	public Map<String, Map<String, Map<String, String>>>
		getLocalizedTransientFieldsMap() {

		return _localizedTransientFieldsMap;
	}

	public List<DDMTemplate> getTemplates() throws SystemException {
		return DDMTemplateLocalServiceUtil.getTemplates(getStructureId());
	}

	public Map<String, Map<String, String>> getTransientFieldsMap(String locale)
		throws PortalException, SystemException {

		_indexFieldsMap(locale);

		Map<String, Map<String, String>> fieldsMap =
			_localizedTransientFieldsMap.get(locale);

		return fieldsMap;
	}

	public boolean hasField(String fieldName)
		throws PortalException, SystemException {

		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		boolean hasField = fieldsMap.containsKey(fieldName);

		while (!hasField && (getParentStructureId() > 0)) {
			DDMStructure parentStructure =
				DDMStructureLocalServiceUtil.getStructure(
					getParentStructureId());

			hasField = parentStructure.hasField(fieldName);
		}

		return hasField;
	}

	public boolean isFieldRepeatable(String fieldName)
		throws PortalException, SystemException {

		return GetterUtil.getBoolean(getFieldProperty(fieldName, "repeatable"));
	}

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		super.prepareLocalizedFieldsForImport(defaultImportLocale);

		Locale ddmStructureDefaultLocale = LocaleUtil.fromLanguageId(
			getDefaultLanguageId());

		try {
			setXsd(
				DDMXMLUtil.updateXMLDefaultLocale(
					getXsd(), ddmStructureDefaultLocale, defaultImportLocale));
		}
		catch (Exception e) {
			throw new LocaleException(e);
		}
	}

	@Override
	public void setDocument(Document document) {
		_document = document;
	}

	@Override
	public void setLocalizedFieldsMap(
		Map<String, Map<String, Map<String, String>>> localizedFieldsMap) {

		_localizedFieldsMap = localizedFieldsMap;
	}

	@Override
	public void setLocalizedTransientFieldsMap(
		Map<String, Map<String, Map<String, String>>>
			localizedTransientFieldsMap) {

		_localizedTransientFieldsMap = localizedTransientFieldsMap;
	}

	@Override
	public void setXsd(String xsd) {
		super.setXsd(xsd);

		_document = null;
		_localizedFieldsMap.clear();
		_localizedTransientFieldsMap.clear();
	}

	private Map<String, String> _getField(Element element, String locale) {
		Map<String, String> field = new HashMap<String, String>();

		List<String> availableLocales = getAvailableLanguageIds();

		if ((locale != null) && !availableLocales.contains(locale)) {
			locale = getDefaultLanguageId();
		}

		locale = HtmlUtil.escapeXPathAttribute(locale);

		String xPathExpression =
			"meta-data[@locale=".concat(locale).concat("]");

		XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

		Node node = xPathSelector.selectSingleNode(element);

		Element metaDataElement = (Element)node.asXPathResult(node.getParent());

		if (metaDataElement != null) {
			List<Element> childMetaDataElements = metaDataElement.elements();

			for (Element childMetaDataElement : childMetaDataElements) {
				String name = childMetaDataElement.attributeValue("name");
				String value = childMetaDataElement.getText();

				field.put(name, value);
			}
		}

		for (Attribute attribute : element.attributes()) {
			field.put(attribute.getName(), attribute.getValue());
		}

		Element parentElement = element.getParent();

		if (parentElement != null) {
			String parentName = parentElement.attributeValue("name");

			if (Validator.isNotNull(parentName)) {
				field.put(_getPrivateAttributeKey("parentName"), parentName);
			}
		}

		return field;
	}

	private String _getPrivateAttributeKey(String attributeName) {
		return StringPool.UNDERLINE.concat(attributeName).concat(
			StringPool.UNDERLINE);
	}

	private void _indexFieldsMap(String locale)
		throws PortalException, SystemException {

		Map<String, Map<String, String>> fieldsMap = _localizedFieldsMap.get(
			locale);
		Map<String, Map<String, String>> transientFieldsMap =
			_localizedTransientFieldsMap.get(locale);

		if (fieldsMap != null) {
			return;
		}

		if (getParentStructureId() > 0) {
			DDMStructure parentStructure =
				DDMStructureLocalServiceUtil.getStructure(
					getParentStructureId());

			fieldsMap = parentStructure.getFieldsMap(locale);
			transientFieldsMap = parentStructure.getTransientFieldsMap(locale);
		}
		else {
			fieldsMap = new LinkedHashMap<String, Map<String, String>>();
			transientFieldsMap =
				new LinkedHashMap<String, Map<String, String>>();
		}

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-element");

		List<Node> nodes = xPathSelector.selectNodes(getDocument());

		for (Node node : nodes) {
			Element element = (Element)node;

			String name = element.attributeValue("name");

			if (Validator.isNotNull(element.attributeValue("dataType"))) {
				fieldsMap.put(name, _getField(element, locale));
			}
			else {
				transientFieldsMap.put(name, _getField(element, locale));
			}
		}

		_localizedFieldsMap.put(locale, fieldsMap);
		_localizedTransientFieldsMap.put(locale, transientFieldsMap);
	}

	private String _mergeXsds(String xsd1, String xsd2) throws SystemException {
		try {
			Document document1 = SAXReaderUtil.read(xsd1);
			Document document2 = SAXReaderUtil.read(xsd2);

			Element rootElement1 = document1.getRootElement();
			Element rootElement2 = document2.getRootElement();

			for (Element element : rootElement1.elements()) {
				rootElement1.remove(element);

				rootElement2.add(element);
			}

			return rootElement2.formattedString();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureImpl.class);

	@CacheField
	private Document _document;

	@CacheField
	private Map<String, Map<String, Map<String, String>>> _localizedFieldsMap =
		new ConcurrentHashMap<String, Map<String, Map<String, String>>>();

	@CacheField
	private Map<String, Map<String, Map<String, String>>>
		_localizedTransientFieldsMap =
			new ConcurrentHashMap<String, Map<String, Map<String, String>>>();

}