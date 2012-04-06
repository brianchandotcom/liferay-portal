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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.ComparisonOperator;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;
import com.liferay.portlet.dynamicdatamapping.storage.query.FieldCondition;
import com.liferay.portlet.dynamicdatamapping.storage.query.FieldConditionImpl;
import com.liferay.portlet.dynamicdatamapping.storage.query.Junction;
import com.liferay.portlet.dynamicdatamapping.storage.query.LogicalOperator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class XMLStorageAdapter extends BaseStorageAdapter {

	@Override
	protected long doCreate(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			DDMContent.class.getName());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

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

			_appendField(rootElement, field.getName(), valueString);
		}

		DDMContent ddmContent = DDMContentLocalServiceUtil.addContent(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DDMStorageLink.class.getName(), null, document.formattedString(),
			serviceContext);

		DDMStorageLinkLocalServiceUtil.addStorageLink(
			classNameId, ddmContent.getPrimaryKey(), ddmStructureId,
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	@Override
	protected void doDeleteByClass(long classPK) throws Exception {
		DDMContentLocalServiceUtil.deleteDDMContent(classPK);

		DDMStorageLinkLocalServiceUtil.deleteClassStorageLink(classPK);
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				ddmStructureId);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			DDMContentLocalServiceUtil.deleteDDMContent(
				ddmStorageLink.getClassPK());
		}

		DDMStorageLinkLocalServiceUtil.deleteStructureStorageLinks(
			ddmStructureId);
	}

	@Override
	protected List<Fields> doGetFieldsListByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			ddmStructureId, classPKs, fieldNames, null, orderByComparator);
	}

	@Override
	protected List<Fields> doGetFieldsListByDDMStructure(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(ddmStructureId, fieldNames, null, orderByComparator);
	}

	@Override
	protected Map<Long, Fields> doGetFieldsMapByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		return _doQuery(ddmStructureId, classPKs, fieldNames);
	}

	@Override
	protected List<Fields> doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			ddmStructureId, fieldNames, condition, orderByComparator);
	}

	@Override
	protected int doQueryCount(long ddmStructureId, Condition condition)
		throws Exception {

		XPath conditionXPath = null;

		if (condition != null) {
			conditionXPath = _parseCondition(condition);
		}

		int count = 0;

		long[] classPKs = _getStructureClassPKs(ddmStructureId);

		for (long classPK : classPKs) {
			DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(
				classPK);

			Document document = SAXReaderUtil.read(ddmContent.getXml());

			if ((conditionXPath == null) ||
				((conditionXPath != null) &&
				 conditionXPath.booleanValueOf(document))) {

				count++;
			}
		}

		return count;
	}

	@Override
	protected void doUpdate(
			long classPK, Fields fields, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception {

		DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(classPK);

		Document document = null;

		Element rootElement = null;

		if (mergeFields) {
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

			String fieldName = field.getName();
			String fieldValue = String.valueOf(value);

			Element dynamicElementElement = _getElementByName(
				document, fieldName);

			if (dynamicElementElement == null) {
				_appendField(rootElement, fieldName, fieldValue);
			}
			else {
				_updateField(dynamicElementElement, fieldName, fieldValue);
			}
		}

		ddmContent.setModifiedDate(serviceContext.getModifiedDate(null));
		ddmContent.setXml(document.formattedString());

		DDMContentLocalServiceUtil.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getXml(), serviceContext);
	}

	private Element _appendField(
		Element rootElement, String fieldName, String fieldValue) {

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addElement("dynamic-content");

		_updateField(dynamicElementElement, fieldName, fieldValue);

		return dynamicElementElement;
	}

	private List<Fields> _doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			ddmStructureId, _getStructureClassPKs(ddmStructureId), fieldNames,
			condition, orderByComparator);
	}

	private Map<Long, Fields> _doQuery(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		Map<Long, Fields> fieldsMap = new HashMap<Long, Fields>();

		List<Fields> fieldsList = _doQuery(
			ddmStructureId, classPKs, fieldNames, null, null);

		for (int i = 0; i < fieldsList.size(); i++) {
			Fields fields = fieldsList.get(i);

			fieldsMap.put(classPKs[i], fields);
		}

		return fieldsMap;
	}

	private List<Fields> _doQuery(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			Condition condition, OrderByComparator orderByComparator)
		throws Exception {

		List<Fields> fieldsList = new ArrayList<Fields>();

		XPath conditionXPath = null;

		if (condition != null) {
			conditionXPath = _parseCondition(condition);
		}

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.getDDMStructure(ddmStructureId);

		for (long classPK : classPKs) {
			DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(
				classPK);

			Document document = SAXReaderUtil.read(ddmContent.getXml());

			if ((conditionXPath != null) &&
				!conditionXPath.booleanValueOf(document)) {

				continue;
			}

			Fields fields = new Fields();

			Element rootElement = document.getRootElement();

			List<Element> dynamicElementElements = rootElement.elements(
				"dynamic-element");

			for (Element dynamicElementElement : dynamicElementElements) {
				String fieldName = dynamicElementElement.attributeValue("name");
				String fieldValue = dynamicElementElement.elementText(
					"dynamic-content");

				if (!ddmStructure.hasField(fieldName) ||
					((fieldNames != null) && !fieldNames.contains(fieldName))) {

					continue;
				}

				String fieldDataType = ddmStructure.getFieldDataType(fieldName);

				Serializable fieldValueSerializable =
					FieldConstants.getSerializable(fieldDataType, fieldValue);

				Field field = new Field(
					ddmStructureId, fieldName, fieldValueSerializable);

				fields.put(field);
			}

			fieldsList.add(fields);
		}

		if (orderByComparator != null) {
			Collections.sort(fieldsList, orderByComparator);
		}

		return fieldsList;
	}

	private Element _getElementByName(Document document, String name) {
		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@name='".concat(name).concat("']"));

		List<Node> nodes = xPathSelector.selectNodes(document);

		if (nodes.size() == 1) {
			return (Element)nodes.get(0);
		}
		else {
			return null;
		}
	}

	private long[] _getStructureClassPKs(long ddmStructureId) throws Exception {
		List<Long> classPKs = new ArrayList<Long>();

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				ddmStructureId);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			classPKs.add(ddmStorageLink.getClassPK());
		}

		return ArrayUtil.toArray(classPKs.toArray(new Long[classPKs.size()]));
	}

	private XPath _parseCondition(Condition condition) {
		StringBundler sb = new StringBundler(4);

		sb.append("//dynamic-element");
		sb.append(StringPool.OPEN_BRACKET);
		sb.append(_toXPath(condition));
		sb.append(StringPool.CLOSE_BRACKET);

		return SAXReaderUtil.createXPath(sb.toString());
	}

	private String _toXPath(Condition condition) {
		StringBundler sb = new StringBundler();

		if (condition.isJunction()) {
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(_toXPath((Junction)condition));
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}
		else {
			sb.append(_toXPath((FieldConditionImpl)condition));
		}

		return sb.toString();
	}

	private String _toXPath(FieldCondition fieldCondition) {
		StringBundler sb = new StringBundler(6);

		sb.append("(@name=");

		String name = StringUtil.quote(
			String.valueOf(fieldCondition.getName()));

		sb.append(name);

		ComparisonOperator comparisonOperator =
			fieldCondition.getComparisonOperator();

		if (comparisonOperator.equals(ComparisonOperator.LIKE)) {
			sb.append(" and matches(dynamic-content, ");
		}
		else {
			sb.append(" and dynamic-content= ");
		}

		String value = StringUtil.quote(
			String.valueOf(fieldCondition.getValue()));

		sb.append(value);

		if (comparisonOperator.equals(ComparisonOperator.LIKE)) {
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String _toXPath(Junction junction) {
		StringBundler sb = new StringBundler();

		LogicalOperator logicalOperator = junction.getLogicalOperator();

		String logicalOperatorString = logicalOperator.toString();

		Iterator<Condition> itr = junction.iterator();

		while (itr.hasNext()) {
			Condition condition = itr.next();

			sb.append(_toXPath(condition));

			if (itr.hasNext()) {
				sb.append(StringPool.SPACE);
				sb.append(logicalOperatorString.toLowerCase());
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private void _updateField(
		Element dynamicElementElement, String fieldName, String value) {

		Element dynamicContentElement = dynamicElementElement.element(
			"dynamic-content");

		dynamicElementElement.addAttribute("name", fieldName);

		dynamicContentElement.clearContent();

		dynamicContentElement.addCDATA(value);
	}

}