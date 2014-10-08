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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
	public String getStorageType() {
		return StorageType.EXPANDO.toString();
	}

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

		if (mergeFields) {
			fields = DDMUtil.mergeFields(fields, getFields(classPK));
		}

		ExpandoValueLocalServiceUtil.deleteRowValues(expandoRow.getRowId());

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

	private void _checkExpandoColumns(ExpandoTable expandoTable, Fields fields)
		throws PortalException {

		for (String name : fields.getNames()) {
			ExpandoColumn expandoColumn =
				ExpandoColumnLocalServiceUtil.getColumn(
					expandoTable.getTableId(), name);

			if (expandoColumn != null) {
				continue;
			}

			int type = ExpandoColumnConstants.STRING_LOCALIZED;

			Field field = fields.get(name);

			if (field.isRepeatable()) {
				type = ExpandoColumnConstants.STRING_ARRAY_LOCALIZED;
			}

			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), name, type);
		}
	}

	private List<Fields> _doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator<Fields> orderByComparator)
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
			Condition condition, OrderByComparator<Fields> orderByComparator)
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

					if (ddmStructure.hasField(fieldName) &&
						((fieldNames == null) ||
						 ((fieldNames != null) &&
						  fieldNames.contains(fieldName)))) {

						Field field = new Field();

						field.setDefaultLocale(expandoValue.getDefaultLocale());
						field.setDDMStructureId(ddmStructureId);
						field.setName(fieldName);

						String fieldType = ddmStructure.getFieldDataType(
							fieldName);

						Map<Locale, List<Serializable>> valuesMap =
							_getValuesMap(
								column.getType(), fieldType,
								expandoValue.getSerializable());

						field.setValuesMap(valuesMap);

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

	private long[] _getExpandoRowIds(long ddmStructureId) {
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
		throws PortalException {

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

		_checkExpandoColumns(expandoTable, fields);

		return expandoTable;
	}

	private Map<Locale, List<Serializable>> _getValuesMap(
		int columnType, String fieldType, Serializable data) {

		Map<Locale, List<Serializable>> valuesMap =
			new HashMap<Locale, List<Serializable>>();

		if (columnType == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) {
			Map<Locale, String[]> stringArrayMap = (Map<Locale, String[]>)data;

			for (Locale locale : stringArrayMap.keySet()) {
				String[] value = stringArrayMap.get(locale);

				if (ArrayUtil.isEmpty(value)) {
					continue;
				}

				valuesMap.put(locale, _transformValue(fieldType, value));
			}
		}
		else {
			Map<Locale, String> stringMap = (Map<Locale, String>)data;

			for (Locale locale : stringMap.keySet()) {
				String value = stringMap.get(locale);

				if (Validator.isNull(value)) {
					continue;
				}

				valuesMap.put(locale, _transformValue(fieldType, value));
			}
		}

		return valuesMap;
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

	private String[] _toStringArray(String type, Serializable[] values) {
		String[] stringValues = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			Serializable serializable = values[i];

			if (FieldConstants.isNumericType(type) && (serializable == null)) {
				serializable = _NUMERIC_NULL_VALUE;
			}

			stringValues[i] = String.valueOf(serializable);
		}

		return stringValues;
	}

	private List<Serializable> _transformValue(String type, String value) {
		List<Serializable> serializables = new ArrayList<Serializable>();

		if (FieldConstants.isNumericType(type) &&
			_NUMERIC_NULL_VALUE.equals(value)) {

			value = null;
		}

		Serializable serializable = FieldConstants.getSerializable(type, value);

		serializables.add(serializable);

		return serializables;
	}

	private List<Serializable> _transformValue(String type, String[] values) {
		List<Serializable> serializables = new ArrayList<Serializable>();

		for (String value : values) {
			if (FieldConstants.isNumericType(type) &&
				_NUMERIC_NULL_VALUE.equals(value)) {

				value = null;
			}

			Serializable serializable = FieldConstants.getSerializable(
				type, value);

			serializables.add(serializable);
		}

		return serializables;
	}

	private void _updateFields(
			ExpandoTable expandoTable, long classPK, Fields fields)
		throws PortalException {

		Iterator<Field> itr = fields.iterator(true);

		while (itr.hasNext()) {
			Field field = itr.next();

			Map<Locale, ?> dataMap = null;

			if (field.isRepeatable()) {
				Map<Locale, String[]> stringArrayMap =
					new HashMap<Locale, String[]>();

				for (Locale locale : field.getAvailableLocales()) {
					Serializable value = field.getValue(locale);

					if (value instanceof Date[]) {
						Date[] dates = (Date[])value;

						String[] values = new String[dates.length];

						for (int i = 0; i < dates.length; i++) {
							values[i] = String.valueOf(dates[i].getTime());
						}

						stringArrayMap.put(locale, values);
					}
					else {
						String[] values = _toStringArray(
							field.getDataType(), (Serializable[])value);

						stringArrayMap.put(locale, values);
					}
				}

				dataMap = stringArrayMap;
			}
			else {
				Map<Locale, String> stringMap = new HashMap<Locale, String>();

				for (Locale locale : field.getAvailableLocales()) {
					Serializable value = field.getValue(locale);

					if (FieldConstants.isNumericType(field.getDataType()) &&
						(value == null)) {

						value = _NUMERIC_NULL_VALUE;
					}
					else if (value instanceof Date) {
						Date date = (Date)value;

						value = date.getTime();
					}

					stringMap.put(locale, String.valueOf(value));
				}

				dataMap = stringMap;
			}

			ExpandoValueLocalServiceUtil.addValue(
				expandoTable.getCompanyId(),
				ExpandoStorageAdapter.class.getName(), expandoTable.getName(),
				field.getName(), classPK, dataMap, field.getDefaultLocale());
		}
	}

	private static final String _NUMERIC_NULL_VALUE = "NUMERIC_NULL_VALUE";

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoStorageAdapter.class);

}