/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureImpl extends DDMStructureBaseImpl {

	public DDMStructureImpl() {
	}

	public List<String> getAvailableLocales() {
		Document document = _getDocument();

		Element rootElement = document.getRootElement();

		String availableLocales = rootElement.attributeValue(
			"available-locales");

		return ListUtil.fromArray(StringUtil.split(availableLocales));
	}

	public String getDefaultLocale() {
		Document document = _getDocument();

		Element rootElement = document.getRootElement();

		return rootElement.attributeValue("default-locale");
	}

	public String getFieldDataType(String fieldName) {
		return getFieldProperty(fieldName, "dataType");
	}

	public boolean getFieldDisplayChildLabelAsValue(String fieldName) {
		return GetterUtil.getBoolean(
			getFieldProperty(fieldName, "displayChildLabelAsValue"));
	}

	public String getFieldLabel(String fieldName, Locale locale) {
		return getFieldLabel(fieldName, locale.getLanguage());
	}

	public String getFieldLabel(String fieldName, String locale) {
		return GetterUtil.getString(
			getFieldProperty(fieldName, "label", locale), fieldName);
	}

	public Set<String> getFieldNames() {
		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		return fieldsMap.keySet();
	}

	public String getFieldProperty(String fieldName, String property) {
		return getFieldProperty(fieldName, property, getDefaultLocale());
	}

	public String getFieldProperty(
		String fieldName, String property, String locale) {

		Map<String, Map<String, String>> fieldsMap = _getFieldsMap(locale);

		Map<String, String> field = fieldsMap.get(fieldName);

		return field.get(property);
	}

	public boolean getFieldRequired(String fieldName) {
		return GetterUtil.getBoolean(getFieldProperty(fieldName, "required"));
	}

	public Map<String, String> getFields(
		String fieldName, String attributeName, String attributeValue) {

		return getFields(
			fieldName, attributeName, attributeValue, getDefaultLocale());
	}

	public Map<String, String> getFields(
		String fieldName, String attributeName, String attributeValue,
		String locale) {

		try {
			StringBundler sb = new StringBundler(7);

			sb.append("//dynamic-element[@name=\"");
			sb.append(fieldName);
			sb.append("\"] //dynamic-element[@");
			sb.append(attributeName);
			sb.append("=\"");
			sb.append(attributeValue);
			sb.append("\"]");

			XPath xPathSelector = SAXReaderUtil.createXPath(sb.toString());

			Node node = xPathSelector.selectSingleNode(_getDocument());

			if (node != null) {
				return _getField(
					(Element)node.asXPathResult(node.getParent()), locale);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	public Map<String, Map<String, String>> getFieldsMap() {
		return _getFieldsMap(getDefaultLocale());
	}

	public Map<String, Map<String, String>> getFieldsMap(String locale) {
		return _getFieldsMap(locale);
	}

	public String getFieldType(String fieldName) {
		return getFieldProperty(fieldName, "type");
	}

	public boolean hasField(String fieldName) {
		Map<String, Map<String, String>> fieldsMap = getFieldsMap();

		return fieldsMap.containsKey(fieldName);
	}

	@Override
	public void setXsd(String xsd) {
		super.setXsd(xsd);

		_document = null;
		_fieldsMap = null;
	}

	private Document _getDocument() {
		if (_document == null) {
			try {
				_document = SAXReaderUtil.read(getXsd());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _document;
	}

	private Map<String, String> _getField(Element element, String locale) {
		Map<String, String> field = new HashMap<String, String>();

		List<String> availableLocales = getAvailableLocales();

		if ((locale != null) && !(availableLocales.contains(locale))) {
			locale = getDefaultLocale();
		}

		String xPathExpression =
			"meta-data[@locale=\"".concat(locale).concat("\"]");

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

		return field;
	}

	private Map<String, Map<String, String>> _getFieldsMap(String locale) {
		if (_fieldsMap == null) {
			synchronized (this) {
				if (_fieldsMap == null) {
					_fieldsMap =
						new LinkedHashMap<String, Map<String, String>>();

					XPath xPathSelector = SAXReaderUtil.createXPath(
						"//dynamic-element[@dataType]");

					List<Node> nodes = xPathSelector.selectNodes(
						_getDocument());

					Iterator<Node> itr = nodes.iterator();

					while (itr.hasNext()) {
						Element element = (Element)itr.next();

						String name = element.attributeValue("name");

						_fieldsMap.put(name, _getField(element, locale));
					}
				}
			}
		}

		return _fieldsMap;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureImpl.class);

	private Document _document;
	private Map<String, Map<String, String>> _fieldsMap;

}