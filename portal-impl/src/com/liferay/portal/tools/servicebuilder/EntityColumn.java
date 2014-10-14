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

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Shuyang Zhou
 */
public class EntityColumn implements Cloneable, Comparable<EntityColumn> {

	public EntityColumn(String name) {
		this(
			name, null, null, false, false, false, null, null, true, true,
			false, null, null, null, null, true, true, false, false, false,
			false);
	}

	public EntityColumn(
		String name, String dbName, String type, boolean primary,
		boolean accessor, boolean filterPrimary, String ejbName,
		String mappingTable, boolean caseSensitive, boolean orderByAscending,
		boolean orderColumn, String comparator, String arrayableOperator,
		String idType, String idParam, boolean convertNull, boolean lazy,
		boolean localized, boolean jsonEnabled, boolean containerModel,
		boolean parentContainerModel) {

		_name = name;
		_dbName = dbName;
		_type = type;
		_primary = primary;
		_accessor = accessor;
		_filterPrimary = filterPrimary;
		_humanName = ServiceBuilder.toHumanName(name);
		_methodName = TextFormatter.format(name, TextFormatter.G);
		_ejbName = ejbName;
		_mappingTable = mappingTable;
		_caseSensitive = caseSensitive;
		_orderByAscending = orderByAscending;
		_orderColumn = orderColumn;
		_comparator = comparator;
		_arrayableOperator = arrayableOperator;
		_idType = idType;
		_idParam = idParam;
		_convertNull = convertNull;
		_lazy = lazy;
		_localized = localized;
		_jsonEnabled = jsonEnabled;
		_containerModel = containerModel;
		_parentContainerModel = parentContainerModel;
	}

	public EntityColumn(
		String name, String dbName, String type, boolean primary,
		boolean accessor, boolean filterPrimary, String ejbName,
		String mappingTable, String idType, String idParam, boolean convertNull,
		boolean lazy, boolean localized, boolean jsonEnabled,
		boolean containerModel, boolean parentContainerModel) {

		this(
			name, dbName, type, primary, accessor, filterPrimary, ejbName,
			mappingTable, true, true, false, null, null, idType, idParam,
			convertNull, lazy, localized, jsonEnabled, containerModel,
			parentContainerModel);
	}

	@Override
	public Object clone() {
		return new EntityColumn(
			getName(), getDBName(), getType(), isPrimary(), isAccessor(),
			isFilterPrimary(), getEJBName(), getMappingTable(),
			isCaseSensitive(), isOrderByAscending(), isOrderColumn(),
			getComparator(), getArrayableOperator(), getIdType(), getIdParam(),
			isConvertNull(), isLazy(), isLocalized(), isJsonEnabled(),
			isContainerModel(), isParentContainerModel());
	}

	@Override
	public int compareTo(EntityColumn entityColumn) {
		return _name.compareTo(entityColumn._name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EntityColumn)) {
			return false;
		}

		EntityColumn col = (EntityColumn)obj;

		String name = col.getName();

		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getArrayableOperator() {
		return _arrayableOperator;
	}

	public String getComparator() {
		return _comparator;
	}

	public String getDBName() {
		return _dbName;
	}

	public String getEJBName() {
		return _ejbName;
	}

	public String getGenericizedType() {
		if (_type.equals("Map")) {
			return "Map<String, Serializable>";
		}

		return _type;
	}

	public String getHumanCondition(boolean arrayable) {
		StringBundler sb = new StringBundler(6);

		sb.append(_name);
		sb.append(" ");
		sb.append(convertComparatorToHtml(_comparator));
		sb.append(" ");

		if (arrayable && hasArrayableOperator()) {
			if (isArrayableAndOperator()) {
				sb.append("all ");
			}
			else {
				sb.append("any ");
			}
		}

		sb.append("&#63;");

		return sb.toString();
	}

	public String getHumanName() {
		return _humanName;
	}

	public String getHumanNames() {
		return TextFormatter.formatPlural(getHumanName());
	}

	public String getIdParam() {
		return _idParam;
	}

	public String getIdType() {
		return _idType;
	}

	public String getMappingTable() {
		return _mappingTable;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String getMethodNames() {
		return TextFormatter.formatPlural(_methodName);
	}

	public String getMethodUserUuidName() {
		return _methodName.substring(0, _methodName.length() - 2) + "Uuid";
	}

	public String getName() {
		return _name;
	}

	public String getNames() {
		return TextFormatter.formatPlural(_name);
	}

	public String getType() {
		return _type;
	}

	public String getUserUuidHumanName() {
		return ServiceBuilder.toHumanName(getUserUuidName());
	}

	public String getUserUuidName() {
		return _name.substring(0, _name.length() - 2) + "Uuid";
	}

	public boolean hasArrayableOperator() {
		if (Validator.isNotNull(_arrayableOperator)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	public boolean isAccessor() {
		return _accessor;
	}

	public boolean isArrayableAndOperator() {
		if (_arrayableOperator.equals("AND")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isCaseSensitive() {
		return _caseSensitive;
	}

	public boolean isCollection() {
		if (_type.equals("Collection")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isContainerModel() {
		return _containerModel;
	}

	public boolean isConvertNull() {
		return _convertNull;
	}

	public boolean isFilterPrimary() {
		return _filterPrimary;
	}

	public boolean isFinderPath() {
		return _finderPath;
	}

	public boolean isJsonEnabled() {
		return _jsonEnabled;
	}

	public boolean isLazy() {
		return _lazy;
	}

	public boolean isLocalized() {
		return _localized;
	}

	public boolean isMappingManyToMany() {
		return Validator.isNotNull(_mappingTable);
	}

	public boolean isOrderByAscending() {
		return _orderByAscending;
	}

	public boolean isOrderColumn() {
		return _orderColumn;
	}

	public boolean isParentContainerModel() {
		return _parentContainerModel;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public boolean isPrimitiveType() {
		return isPrimitiveType(true);
	}

	public boolean isPrimitiveType(boolean includeWrappers) {
		if (Character.isLowerCase(_type.charAt(0))) {
			return true;
		}

		if (!includeWrappers) {
			return false;
		}

		if (_type.equals("Boolean")) {
			return true;
		}
		else if (_type.equals("Byte")) {
			return true;
		}
		else if (_type.equals("Char")) {
			return true;
		}
		else if (_type.equals("Double")) {
			return true;
		}
		else if (_type.equals("Float")) {
			return true;
		}
		else if (_type.equals("Integer")) {
			return true;
		}
		else if (_type.equals("Long")) {
			return true;
		}
		else if (_type.equals("Short")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isUserUuid() {
		if (_type.equals("long") && _methodName.endsWith("UserId")) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setArrayableOperator(String arrayableOperator) {
		_arrayableOperator = StringUtil.toUpperCase(arrayableOperator);
	}

	public void setCaseSensitive(boolean caseSensitive) {
		_caseSensitive = caseSensitive;
	}

	public void setComparator(String comparator) {
		_comparator = comparator;
	}

	public void setContainerModel(boolean containerModel) {
		_containerModel = containerModel;
	}

	public void setConvertNull(boolean convertNull) {
		_convertNull = convertNull;
	}

	public void setDBName(String dbName) {
		_dbName = dbName;
	}

	public void setFinderPath(boolean finderPath) {
		_finderPath = finderPath;
	}

	public void setIdParam(String idParam) {
		_idParam = idParam;
	}

	public void setIdType(String idType) {
		_idType = idType;
	}

	public void setLazy(boolean lazy) {
		_lazy = lazy;
	}

	public void setLocalized(boolean localized) {
		_localized = localized;
	}

	public void setOrderByAscending(boolean orderByAscending) {
		_orderByAscending = orderByAscending;
	}

	public void setOrderColumn(boolean orderColumn) {
		_orderColumn = orderColumn;
	}

	public void setParentContainerModel(boolean parentContainerModel) {
		_parentContainerModel = parentContainerModel;
	}

	public void validate() {
		if (Validator.isNotNull(_arrayableOperator)) {
			if (!_type.equals("char") && !_type.equals("int") &&
				!_type.equals("long") && !_type.equals("short") &&
				!_type.equals("String")) {

				throw new IllegalArgumentException(
					"Type " + _type + " cannot be arrayable");
			}
		}

		String comparator = _comparator;

		if (comparator == null) {
			comparator = StringPool.EQUAL;
		}

		if (_arrayableOperator.equals("AND") &&
			!comparator.equals(StringPool.NOT_EQUAL)) {

			throw new IllegalArgumentException(
				"Illegal combination of arrayable \"AND\" and comparator \"" +
					comparator + "\"");
		}

		if (_arrayableOperator.equals("OR") &&
			!comparator.equals(StringPool.EQUAL) &&
			!comparator.equals(StringPool.LIKE)) {

			throw new IllegalArgumentException(
				"Illegal combination of arrayable \"OR\" and comparator \"" +
					comparator + "\"");
		}
	}

	protected String convertComparatorToHtml(String comparator) {
		if (comparator.equals(">")) {
			return "&gt;";
		}

		if (comparator.equals("<")) {
			return "&lt;";
		}

		if (comparator.equals(">=")) {
			return "&ge;";
		}

		if (comparator.equals("<=")) {
			return "&le;";
		}

		if (comparator.equals("!=")) {
			return "&ne;";
		}

		return comparator;
	}

	private final boolean _accessor;
	private String _arrayableOperator;
	private boolean _caseSensitive;
	private String _comparator;
	private boolean _containerModel;
	private boolean _convertNull;
	private String _dbName;
	private final String _ejbName;
	private final boolean _filterPrimary;
	private boolean _finderPath;
	private final String _humanName;
	private String _idParam;
	private String _idType;
	private final boolean _jsonEnabled;
	private boolean _lazy;
	private boolean _localized;
	private final String _mappingTable;
	private final String _methodName;
	private final String _name;
	private boolean _orderByAscending;
	private boolean _orderColumn;
	private boolean _parentContainerModel;
	private final boolean _primary;
	private final String _type;

}