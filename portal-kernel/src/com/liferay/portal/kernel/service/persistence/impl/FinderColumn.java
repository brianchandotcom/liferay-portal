/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class FinderColumn<T extends BaseModel<T>> {

	public FinderColumn(
		String entityAlias, String columnName, Type type, String comparator,
		boolean caseSensitive, boolean convertNull,
		Function<T, Object> valueExtractor) {

		_type = type;
		_comparator = comparator;
		_caseSensitive = caseSensitive;
		_convertNull = convertNull;
		_valueExtractor = valueExtractor;

		int dotIndex = columnName.indexOf('.');

		if (dotIndex == -1) {
			_keyFragment = columnName + comparator;
		}
		else {
			_keyFragment = columnName.substring(dotIndex + 1) + comparator;
		}

		if ((type == Type.STRING) && !caseSensitive) {
			_sqlBind = StringBundler.concat(
				"lower(", entityAlias, columnName, ") ", comparator, " ?");
		}
		else {
			_sqlBind = StringBundler.concat(
				entityAlias, columnName, " ", comparator, " ?");
		}

		if (type == Type.STRING) {
			_sqlNull = StringBundler.concat(
				"(", entityAlias, columnName, " IS NULL OR ", entityAlias,
				columnName, " ", comparator, " '')");
		}
		else {
			_sqlNull = null;
		}

		if (comparator.equals("<>") || comparator.equals("!=")) {
			_sqlIsNull = entityAlias + columnName + " IS NOT NULL";
		}
		else {
			_sqlIsNull = entityAlias + columnName + " IS NULL";
		}
	}

	public void bindValue(QueryPos queryPos, Object normalizedValue) {
		if (_type.isPrimitive()) {
			queryPos.add(normalizedValue);

			return;
		}

		if ((_type == Type.STRING) && _convertNull) {
			String stringValue = (String)normalizedValue;

			if (stringValue.isEmpty()) {
				return;
			}

			if (!_caseSensitive) {
				stringValue = StringUtil.toLowerCase(stringValue);
			}

			queryPos.add(stringValue);

			return;
		}

		if (normalizedValue == null) {
			return;
		}

		if ((_type == Type.STRING) && !_caseSensitive) {
			normalizedValue = StringUtil.toLowerCase((String)normalizedValue);
		}

		queryPos.add(normalizedValue);
	}

	public Object extractValue(T entity) {
		return _valueExtractor.apply(entity);
	}

	public String getKeyFragment() {
		return _keyFragment;
	}

	public String getSqlFragment(Object normalizedValue) {
		if (_type.isPrimitive()) {
			return _sqlBind;
		}

		if ((_type == Type.STRING) && _convertNull) {
			String stringValue = (String)normalizedValue;

			if (stringValue.isEmpty()) {
				return _sqlNull;
			}

			return _sqlBind;
		}

		if (normalizedValue == null) {
			return _sqlIsNull;
		}

		return _sqlBind;
	}

	public boolean matches(T entity, Object normalizedValue) {
		Object entityValue = _valueExtractor.apply(entity);

		if (_comparator.equals("=")) {
			return Objects.equals(entityValue, normalizedValue);
		}

		if (_comparator.equals("!=") || _comparator.equals("<>")) {
			return !Objects.equals(entityValue, normalizedValue);
		}

		if ((entityValue == null) || (normalizedValue == null)) {
			return false;
		}

		if (_comparator.equals("LIKE")) {
			return StringUtil.wildcardMatches(
				(String)entityValue, (String)normalizedValue, '_', '%', '\\',
				_caseSensitive);
		}

		@SuppressWarnings("rawtypes")
		Comparable comparable = (Comparable)entityValue;

		@SuppressWarnings("unchecked")
		int comparisonResult = comparable.compareTo(normalizedValue);

		if (_comparator.equals(">")) {
			if (comparisonResult > 0) {
				return true;
			}

			return false;
		}

		if (_comparator.equals(">=")) {
			if (comparisonResult >= 0) {
				return true;
			}

			return false;
		}

		if (_comparator.equals("<")) {
			if (comparisonResult < 0) {
				return true;
			}

			return false;
		}

		if (_comparator.equals("<=")) {
			if (comparisonResult <= 0) {
				return true;
			}

			return false;
		}

		return false;
	}

	public Object normalizeValue(Object value) {
		if ((_type == Type.STRING) && _convertNull) {
			return Objects.toString(value, "");
		}

		return value;
	}

	public Object toFinderArg(Object normalizedValue) {
		if (normalizedValue instanceof Date) {
			Date date = (Date)normalizedValue;

			return date.getTime();
		}

		return normalizedValue;
	}

	public enum Type {

		BIG_DECIMAL(false), BOOLEAN(true), DATE(false), DOUBLE(true),
		FLOAT(true), INTEGER(true), LONG(true), SHORT(true), STRING(false);

		public boolean isPrimitive() {
			return _primitive;
		}

		private Type(boolean primitive) {
			_primitive = primitive;
		}

		private final boolean _primitive;

	}

	private final boolean _caseSensitive;
	private final String _comparator;
	private final boolean _convertNull;
	private final String _keyFragment;
	private final String _sqlBind;
	private final String _sqlIsNull;
	private final String _sqlNull;
	private final Type _type;
	private final Function<T, Object> _valueExtractor;

}