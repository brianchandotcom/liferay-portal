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
		String entityAlias, String columnName, String dbColumnName, Type type,
		String comparator, boolean caseSensitive, boolean convertNull,
		Function<T, Object> valueExtractor) {

		this.type = type;
		_comparator = comparator;
		this.caseSensitive = caseSensitive;
		this.convertNull = convertNull;
		this.valueExtractor = valueExtractor;

		int dotIndex = columnName.indexOf('.');

		if (dotIndex == -1) {
			_keyFragment = columnName + comparator;
		}
		else {
			_keyFragment = columnName.substring(dotIndex + 1) + comparator;
		}

		if ((type == Type.STRING) && !caseSensitive) {
			hqlBind = StringBundler.concat(
				"lower(", entityAlias, columnName, ") ", comparator, " ?");
		}
		else {
			hqlBind = StringBundler.concat(
				entityAlias, columnName, " ", comparator, " ?");
		}

		if (type == Type.STRING) {
			hqlNull = StringBundler.concat(
				"(", entityAlias, columnName, " IS NULL OR ", entityAlias,
				columnName, " ", comparator, " '')");
		}
		else {
			hqlNull = null;
		}

		if (comparator.equals("<>") || comparator.equals("!=")) {
			hqlIsNull = entityAlias + columnName + " IS NOT NULL";
		}
		else {
			hqlIsNull = entityAlias + columnName + " IS NULL";
		}

		if (Objects.equals(columnName, dbColumnName)) {
			sqlBind = hqlBind;
			sqlIsNull = hqlIsNull;
			sqlNull = hqlNull;
		}
		else {
			sqlBind = StringUtil.replace(hqlBind, columnName, dbColumnName);
			sqlIsNull = StringUtil.replace(hqlIsNull, columnName, dbColumnName);
			sqlNull = StringUtil.replace(hqlNull, columnName, dbColumnName);
		}
	}

	public FinderColumn(
		String entityAlias, String columnName, Type type, String comparator,
		boolean caseSensitive, boolean convertNull,
		Function<T, Object> valueExtractor) {

		this(
			entityAlias, columnName, columnName, type, comparator,
			caseSensitive, convertNull, valueExtractor);
	}

	public void bindValue(QueryPos queryPos, Object normalizedValue) {
		if (type.isPrimitive()) {
			queryPos.add(normalizedValue);

			return;
		}

		if ((type == Type.STRING) && convertNull) {
			String stringValue = (String)normalizedValue;

			if (stringValue.isEmpty()) {
				return;
			}

			if (!caseSensitive) {
				stringValue = StringUtil.toLowerCase(stringValue);
			}

			queryPos.add(stringValue);

			return;
		}

		if (normalizedValue == null) {
			return;
		}

		if ((type == Type.STRING) && !caseSensitive) {
			normalizedValue = StringUtil.toLowerCase((String)normalizedValue);
		}

		queryPos.add(normalizedValue);
	}

	public Object extractValue(T entity) {
		return valueExtractor.apply(entity);
	}

	public String getKeyFragment() {
		return _keyFragment;
	}

	public String getSqlFragment(Object normalizedValue, boolean sqlQuery) {
		if (type.isPrimitive()) {
			if (sqlQuery) {
				return sqlBind;
			}

			return hqlBind;
		}

		if ((type == Type.STRING) && convertNull) {
			String stringValue = (String)normalizedValue;

			if (stringValue.isEmpty()) {
				if (sqlQuery) {
					return sqlNull;
				}

				return hqlNull;
			}

			if (sqlQuery) {
				return sqlBind;
			}

			return hqlBind;
		}

		if (normalizedValue == null) {
			if (sqlQuery) {
				return sqlIsNull;
			}

			return hqlIsNull;
		}

		if (sqlQuery) {
			return sqlBind;
		}

		return hqlBind;
	}

	public boolean matches(T entity, Object normalizedValue) {
		Object entityValue = valueExtractor.apply(entity);

		if ((type == Type.STRING) && !caseSensitive) {
			entityValue = StringUtil.toLowerCase((String)entityValue);
		}

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
				caseSensitive);
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
		if (type == Type.STRING) {
			if (!caseSensitive) {
				value = StringUtil.toLowerCase((String)value);
			}

			if (convertNull) {
				value = Objects.toString(value, "");
			}
		}

		return value;
	}

	public Object toFinderArg(Object normalizedValue) {
		if (normalizedValue instanceof Date date) {
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

	protected final boolean caseSensitive;
	protected final boolean convertNull;
	protected final String hqlBind;
	protected final String hqlIsNull;
	protected final String hqlNull;
	protected final String sqlBind;
	protected final String sqlIsNull;
	protected final String sqlNull;
	protected final Type type;
	protected final Function<T, Object> valueExtractor;

	private final String _comparator;
	private final String _keyFragment;

}