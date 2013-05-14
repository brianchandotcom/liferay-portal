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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import com.liferay.portal.model.CacheField;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.StructureFieldException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.ArrayList;
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

	@Override
	public List<String> getAvailableLanguageIds() {
		Document document = getDocument();

		Element rootElement = document.getRootElement();

		String availableLocales = rootElement.attributeValue(
			"available-locales");

		return ListUtil.fromArray(StringUtil.split(availableLocales));
	}

	@Override
	public List<String> getChildrenFieldNames(String fieldName)
		throws PortalException, SystemException {

		List<String> fieldNames = new ArrayList<String>();

		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		for (Map<String, String> field : fieldsMap.values()) {
			String parentNameKey = _getPrivateAttributeKey("parentName");

			String parentName = field.get(parentNameKey);

			if (fieldName.equals(parentName)) {
				fieldNames.add(field.get("name"));
			}
		}

		return fieldNames;
	}

	@Override
	public String getCompleteXsd() throws PortalException, SystemException {
		if (getParentStructureId() == 0) {
			return getXsd();
		}

		DDMStructure parentStructure =
			DDMStructureLocalServiceUtil.getStructure(getParentStructureId());

		return _mergeXsds(getXsd(), parentStructure.getCompleteXsd());
	}

	@Override
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

	@Override
	public String getFieldDataType(String fieldName)
		throws PortalException, SystemException {

		return getFieldProperty(fieldName, "dataType");
	}

	@Override
	public String getFieldLabel(String fieldName, Locale locale)
		throws PortalException, SystemException {

		return getFieldLabel(fieldName, LocaleUtil.toLanguageId(locale));
	}

	@Override
	public String getFieldLabel(String fieldName, String locale)
		throws PortalException, SystemException {

		return GetterUtil.getString(
			getFieldProperty(fieldName, "label", locale), fieldName);
	}

	@Override
	public Set<String> getFieldNames() throws PortalException, SystemException {
		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		return fieldsMap.keySet();
	}

	@Override
	public String getFieldProperty(String fieldName, String property)
		throws PortalException, SystemException {

		return getFieldProperty(fieldName, property, getDefaultLanguageId());
	}

	@Override
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

	@Override
	public boolean getFieldRepeatable(String fieldName)
		throws PortalException, SystemException {

		return GetterUtil.getBoolean(getFieldProperty(fieldName, "repeatable"));
	}

	@Override
	public boolean getFieldRequired(String fieldName)
		throws PortalException, SystemException {

		return GetterUtil.getBoolean(getFieldProperty(fieldName, "required"));
	}

	@Override
	public Map<String, String> getFields(
		String fieldName, String attributeName, String attributeValue) {

		return getFields(
			fieldName, attributeName, attributeValue, getDefaultLanguageId());
	}

	@Override
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

	@Override
	public Map<String, Map<String, String>> getFieldsMap()
		throws PortalException, SystemException {

		return getFieldsMap(getDefaultLanguageId());
	}

	@Override
	public Map<String, Map<String, String>> getFieldsMap(String locale)
		throws PortalException, SystemException {

		_indexFieldsMap(locale);

		Map<String, Map<String, String>> fieldsMap = _localizedFieldsMap.get(
			locale);

		return fieldsMap;
	}

	@Override
	public String getFieldTip(String fieldName, Locale locale)
		throws PortalException, SystemException {

		return getFieldTip(fieldName, LocaleUtil.toLanguageId(locale));
	}

	@Override
	public String getFieldTip(String fieldName, String locale)
		throws PortalException, SystemException {

		return GetterUtil.getString(
			getFieldProperty(fieldName, "tip", locale), fieldName);
	}

	@Override
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

	@Override
	public List<String> getRootFieldNames()
		throws PortalException, SystemException {

		List<String> fieldNames = new ArrayList<String>();

		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		for (Map.Entry<String, Map<String, String>> entry :
				fieldsMap.entrySet()) {

			Map<String, String> field = entry.getValue();

			String parentNameKey = _getPrivateAttributeKey("parentName");

			if (!field.containsKey(parentNameKey)) {
				fieldNames.add(entry.getKey());
			}
		}

		return fieldNames;
	}

	@Override
	public List<DDMTemplate> getTemplates() throws SystemException {
		return DDMTemplateLocalServiceUtil.getTemplates(getStructureId());
	}

	@Override
	public Map<String, Map<String, String>> getTransientFieldsMap(String locale)
		throws PortalException, SystemException {

		_indexFieldsMap(locale);

		Map<String, Map<String, String>> fieldsMap =
			_localizedTransientFieldsMap.get(locale);

		return fieldsMap;
	}

	/**
	 * Returns the WebDAV URL to access the structure.
	 *
	 * @param  themeDisplay the theme display needed to build the URL. It can
	 *         set HTTPS access, the server name, the server port, the path
	 *         context, and the scope group.
	 * @param  webDAVToken the WebDAV token for the URL
	 * @return the WebDAV URL
	 */
	@Override
	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken) {
		StringBundler sb = new StringBundler(11);

		boolean secure = false;

		if (themeDisplay.isSecure() ||
			PropsValues.WEBDAV_SERVLET_HTTPS_REQUIRED) {

			secure = true;
		}

		String portalURL = PortalUtil.getPortalURL(
			themeDisplay.getServerName(), themeDisplay.getServerPort(), secure);

		sb.append(portalURL);

		sb.append(themeDisplay.getPathContext());
		sb.append(StringPool.SLASH);
		sb.append("webdav");

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getFriendlyURL());

		sb.append(StringPool.SLASH);
		sb.append(webDAVToken);
		sb.append(StringPool.SLASH);
		sb.append("Structures");
		sb.append(StringPool.SLASH);
		sb.append(getStructureId());

		return sb.toString();
	}

	@Override
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

	@Override
	public boolean isFieldPrivate(String fieldName)
		throws PortalException, SystemException {

		return GetterUtil.getBoolean(getFieldProperty(fieldName, "private"));
	}

	@Override
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

	private Map<String, String> _getPrivateField(String privateFieldName) {
		Map<String, String> privateField = new HashMap<String, String>();

		String dataType = PropsUtil.get(
			PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_DATATYPE,
			new Filter(privateFieldName));

		privateField.put("dataType", dataType);

		privateField.put("private", Boolean.TRUE.toString());

		String repeatable = PropsUtil.get(
			PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_REPEATABLE,
			new Filter(privateFieldName));

		privateField.put("repeatable", repeatable);

		return privateField;
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

		String[] privateFieldNames =
			PropsValues.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_NAMES;

		for (String privateFieldName : privateFieldNames) {
			Map<String, String> privateField = _getPrivateField(
				privateFieldName);

			fieldsMap.put(privateFieldName, privateField);
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