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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.ComparisonOperator;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;
import com.liferay.portlet.dynamicdatamapping.storage.query.FieldCondition;
import com.liferay.portlet.dynamicdatamapping.storage.query.Junction;
import com.liferay.portlet.dynamicdatamapping.storage.query.LogicalOperator;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class ExpandoStorageAdapter extends BaseStorageAdapter {

	@Override
	protected long doCreate(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		ExpandoTable expandoTable = _getExpandoTable(
			companyId, ddmStructureId, fields);

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.addRow(
			expandoTable.getTableId(), CounterLocalServiceUtil.increment());

		_updateFields(expandoTable, expandoRow.getClassPK(), fields);

		DDMStorageLinkLocalServiceUtil.addStorageLink(
			expandoTable.getClassNameId(), expandoRow.getRowId(),
			ddmStructureId, serviceContext);

		return expandoRow.getRowId();
	}

	@Override
	protected void doDeleteByClass(long classPK) throws Exception {
		ExpandoRowLocalServiceUtil.deleteRow(classPK);

		DDMStorageLinkLocalServiceUtil.deleteClassStorageLink(classPK);
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				ddmStructureId);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			ExpandoRowLocalServiceUtil.deleteRow(ddmStorageLink.getClassPK());
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

		Expression expression = null;

		if (condition != null) {
			expression = _parseExpression(condition);
		}

		int count = 0;

		long[] expandoRowIds = _getExpandoRowIds(ddmStructureId);

		for (long expandoRowId : expandoRowIds) {
			List<ExpandoValue> expandoValues =
				ExpandoValueLocalServiceUtil.getRowValues(expandoRowId);

			if ((expression == null) ||
				((expression != null) &&
				 _booleanValueOf(expression, expandoValues))) {

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

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.getRow(classPK);

		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(
				expandoRow.getRowId());

		ExpandoTable expandoTable = _getExpandoTable(
			expandoRow.getCompanyId(), ddmStorageLink.getStructureId(), fields);

		List<ExpandoColumn> expandoColumns =
			ExpandoColumnLocalServiceUtil.getColumns(expandoTable.getTableId());

		if (!mergeFields) {
			for (ExpandoColumn expandoColumn : expandoColumns) {
				if (!fields.contains(expandoColumn.getName())) {
					ExpandoValueLocalServiceUtil.deleteValue(
						expandoColumn.getColumnId(), expandoRow.getRowId());
				}
			}
		}

		_updateFields(expandoTable, expandoRow.getClassPK(), fields);
	}

	private boolean _booleanValueOf(
		Expression expression, List<ExpandoValue> expandoValues) {

		try {
			StandardEvaluationContext standardEvaluationContext =
				new StandardEvaluationContext();

			standardEvaluationContext.setBeanResolver(
				new ExpandoValueBeanResolver(expandoValues));

			return expression.getValue(
				standardEvaluationContext, Boolean.class);
		}
		catch (EvaluationException ee) {
			_log.error("Unable to evaluate expression", ee);
		}

		return false;
	}

	private void _checkExpandoColumns(
			long ddmStructureId, ExpandoTable expandoTable, Fields fields)
		throws PortalException, SystemException {

		for (String name : fields.getNames()) {
			ExpandoColumn expandoColumn =
				ExpandoColumnLocalServiceUtil.getColumn(
					expandoTable.getTableId(), name);

			if (expandoColumn != null) {
				continue;
			}

			int type = _getExpandoColumnType(ddmStructureId, name);

			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), name, type);
		}
	}

	private List<Fields> _doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		return _doQuery(
			ddmStructureId, _getExpandoRowIds(ddmStructureId), fieldNames,
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
			long ddmStructureId, long[] expandoRowIds, List<String> fieldNames,
			Condition condition, OrderByComparator orderByComparator)
		throws Exception {

		List<Fields> fieldsList = new ArrayList<Fields>();

		Expression expression = null;

		if (condition != null) {
			expression = _parseExpression(condition);
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		for (long expandoRowId : expandoRowIds) {
			List<ExpandoValue> expandoValues =
				ExpandoValueLocalServiceUtil.getRowValues(expandoRowId);

			if ((expression == null) ||
				((expression != null) &&
				 _booleanValueOf(expression, expandoValues))) {

				Fields fields = new Fields();

				for (ExpandoValue expandoValue : expandoValues) {
					ExpandoColumn column = expandoValue.getColumn();

					String fieldName = column.getName();
					Serializable fieldValue = expandoValue.getSerializable();

					if (ddmStructure.hasField(fieldName) &&
						((fieldNames == null) ||
						 ((fieldNames != null) &&
						  fieldNames.contains(fieldName)))) {

						Field field = new Field(
							ddmStructureId, fieldName, fieldValue);

						fields.put(field);
					}
				}

				fieldsList.add(fields);
			}
		}

		if (orderByComparator != null) {
			Collections.sort(fieldsList, orderByComparator);
		}

		return fieldsList;
	}

	private int _getExpandoColumnType(long ddmStructureId, String name)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		String fieldDataType = ddmStructure.getFieldDataType(name);

		boolean repeatable = ddmStructure.isFieldRepeatable(name);

		if (fieldDataType.equals(FieldConstants.BOOLEAN)) {
			if (repeatable) {
				return ExpandoColumnConstants.BOOLEAN_ARRAY;
			}

			return ExpandoColumnConstants.BOOLEAN;
		}
		else if (fieldDataType.equals(FieldConstants.DATE)) {
			if (repeatable) {
				return ExpandoColumnConstants.DATE_ARRAY;
			}

			return ExpandoColumnConstants.DATE;
		}
		else if (fieldDataType.equals(FieldConstants.DOCUMENT_LIBRARY)) {
			if (repeatable) {
				return ExpandoColumnConstants.STRING_ARRAY;
			}

			return ExpandoColumnConstants.STRING;
		}
		else if (fieldDataType.equals(FieldConstants.DOUBLE)) {
			if (repeatable) {
				return ExpandoColumnConstants.DOUBLE_ARRAY;
			}

			return ExpandoColumnConstants.DOUBLE;
		}
		else if (fieldDataType.equals(FieldConstants.FILE_UPLOAD)) {
			if (repeatable) {
				return ExpandoColumnConstants.STRING_ARRAY;
			}

			return ExpandoColumnConstants.STRING;
		}
		else if (fieldDataType.equals(FieldConstants.FLOAT)) {
			if (repeatable) {
				return ExpandoColumnConstants.FLOAT_ARRAY;
			}

			return ExpandoColumnConstants.FLOAT;
		}
		else if (fieldDataType.equals(FieldConstants.INTEGER)) {
			if (repeatable) {
				return ExpandoColumnConstants.INTEGER_ARRAY;
			}

			return ExpandoColumnConstants.INTEGER;
		}
		else if (fieldDataType.equals(FieldConstants.LONG)) {
			if (repeatable) {
				return ExpandoColumnConstants.LONG_ARRAY;
			}

			return ExpandoColumnConstants.LONG;
		}
		else if (fieldDataType.equals(FieldConstants.NUMBER)) {
			if (repeatable) {
				return ExpandoColumnConstants.NUMBER_ARRAY;
			}

			return ExpandoColumnConstants.NUMBER;
		}
		else if (fieldDataType.equals(FieldConstants.SHORT)) {
			if (repeatable) {
				return ExpandoColumnConstants.SHORT_ARRAY;
			}

			return ExpandoColumnConstants.SHORT;
		}
		else if (repeatable) {
			return ExpandoColumnConstants.STRING_ARRAY;
		}
		else {
			return ExpandoColumnConstants.STRING;
		}
	}

	private long[] _getExpandoRowIds(long ddmStructureId)
		throws SystemException {

		List<Long> expandoRowIds = new ArrayList<Long>();

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				ddmStructureId);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			expandoRowIds.add(ddmStorageLink.getClassPK());
		}

		return ArrayUtil.toArray(
			expandoRowIds.toArray(new Long[expandoRowIds.size()]));
	}

	private ExpandoTable _getExpandoTable(
			long companyId, long ddmStructureId, Fields fields)
		throws PortalException, SystemException {

		ExpandoTable expandoTable = null;

		long classNameId = PortalUtil.getClassNameId(
			ExpandoStorageAdapter.class.getName());

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, classNameId, String.valueOf(ddmStructureId));
		}
		catch (NoSuchTableException nste) {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				companyId, classNameId, String.valueOf(ddmStructureId));
		}

		_checkExpandoColumns(ddmStructureId, expandoTable, fields);

		return expandoTable;
	}

	private Expression _parseExpression(Condition condition) {
		String expression = _toExpression(condition);

		try {
			ExpressionParser expressionParser = new SpelExpressionParser();

			return expressionParser.parseExpression(expression);
		}
		catch (ParseException pe) {
			_log.error("Unable to parse expression " + expression, pe);
		}

		return null;
	}

	private String _toExpression(Condition condition) {
		if (condition.isJunction()) {
			Junction junction = (Junction)condition;

			return StringPool.OPEN_PARENTHESIS.concat(
				_toExpression(junction)).concat(StringPool.CLOSE_PARENTHESIS);
		}
		else {
			FieldCondition fieldCondition = (FieldCondition)condition;

			return _toExpression(fieldCondition);
		}
	}

	private String _toExpression(FieldCondition fieldCondition) {
		StringBundler sb = new StringBundler(5);

		sb.append("(@");
		sb.append(fieldCondition.getName());

		ComparisonOperator comparisonOperator =
			fieldCondition.getComparisonOperator();

		if (comparisonOperator.equals(ComparisonOperator.LIKE)) {
			sb.append(".data matches ");
		}
		else {
			sb.append(".data == ");
		}

		String value = StringUtil.quote(
			String.valueOf(fieldCondition.getValue()));

		sb.append(value);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String _toExpression(Junction junction) {
		StringBundler sb = new StringBundler();

		LogicalOperator logicalOperator = junction.getLogicalOperator();

		Iterator<Condition> itr = junction.iterator();

		while (itr.hasNext()) {
			Condition condition = itr.next();

			sb.append(_toExpression(condition));

			if (itr.hasNext()) {
				sb.append(StringPool.SPACE);
				sb.append(logicalOperator.toString());
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private void _updateFields(
			ExpandoTable expandoTable, long classPK, Fields fields)
		throws PortalException, SystemException {

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			ExpandoValueLocalServiceUtil.addValue(
				expandoTable.getCompanyId(),
				ExpandoStorageAdapter.class.getName(), expandoTable.getName(),
				field.getName(), classPK, field.getValue());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoStorageAdapter.class);

}