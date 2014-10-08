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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
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
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.ArrayList;
import java.util.Collections;
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
	public String getStorageType() {
		return StorageType.XML.toString();
	}

	@Override
	protected long doCreate(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			DDMContent.class.getName());

		DDMContent ddmContent = DDMContentLocalServiceUtil.addContent(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DDMStorageLink.class.getName(), null, DDMXMLUtil.getXML(fields),
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
			OrderByComparator<Fields> orderByComparator)
		throws Exception {

		return _doQuery(
			ddmStructureId, classPKs, fieldNames, null, orderByComparator);
	}

	@Override
	protected List<Fields> doGetFieldsListByDDMStructure(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator<Fields> orderByComparator)
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
			OrderByComparator<Fields> orderByComparator)
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

			Document document = SAXReaderUtil.read(ddmContent.getData());

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

		ddmContent.setModifiedDate(serviceContext.getModifiedDate(null));

		if (mergeFields) {
			fields = DDMUtil.mergeFields(fields, getFields(classPK));
		}

		ddmContent.setData(DDMXMLUtil.getXML(fields));

		DDMContentLocalServiceUtil.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getData(), serviceContext);
	}

	private List<Fields> _doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator<Fields> orderByComparator)
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
			Condition condition, OrderByComparator<Fields> orderByComparator)
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

			Fields fields = DDMXMLUtil.getFields(
				ddmStructure, conditionXPath, ddmContent.getData(), fieldNames);

			fieldsList.add(fields);
		}

		if (orderByComparator != null) {
			Collections.sort(fieldsList, orderByComparator);
		}

		return fieldsList;
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
		StringBundler sb = new StringBundler(3);

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

		String name = HtmlUtil.escapeXPathAttribute(
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

		String value = HtmlUtil.escapeXPathAttribute(
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
				sb.append(StringUtil.toLowerCase(logicalOperatorString));
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

}