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

package com.liferay.segments.criteria;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Represents a segment criteria as a composition of {@link Criterion} objects.
 *
 * @author Eduardo García
 */
public final class Criteria implements Serializable {

	public Criteria() {
	}

	public void addCriterion(
		String key, Type type, String filterString, Conjunction conjunction) {

		if (Validator.isNull(filterString)) {
			return;
		}

		Criterion criterion = getCriterion(key);

		if (criterion == null) {
			_criteria.put(key, new Criterion(type, filterString, conjunction));

			return;
		}

		criterion = new Criterion(
			type,
			_combineFilters(
				criterion.getFilterString(), filterString, conjunction),
			Conjunction.parse(criterion.getConjunction()));

		_criteria.put(key, criterion);
	}

	public void addFilter(
		Type type, String filterString, Conjunction conjunction) {

		if (Validator.isNull(filterString)) {
			return;
		}

		String typeValueFilterString = _filterStrings.get(type.getValue());

		if (Validator.isNull(typeValueFilterString)) {
			_filterStrings.put(type.getValue(), filterString);

			return;
		}

		_filterStrings.put(
			type.getValue(),
			_combineFilters(typeValueFilterString, filterString, conjunction));
	}

	public Map<String, Criterion> getCriteria() {
		return _criteria;
	}

	public Criterion getCriterion(String key) {
		return _criteria.get(key);
	}

	public String getFilterString(Type type) {
		return _filterStrings.get(type.getValue());
	}

	public Conjunction getTypeConjunction(Type type) {
		Collection<Criterion> criteria = _criteria.values();

		Stream<Criterion> stream = criteria.stream();

		return stream.filter(
			criterion -> Objects.equals(
				type.getValue(), criterion.getTypeValue())
		).map(
			criterion -> Conjunction.parse(criterion.getConjunction())
		).findFirst(
		).orElse(
			Conjunction.AND
		);
	}

	public static final class Criterion implements Serializable {

		public Criterion() {
		}

		public Criterion(
			Type type, String filterString, Conjunction conjunction) {

			_filterString = filterString;
			_conjunction = conjunction.getValue();

			_typeValue = type.getValue();
		}

		public String getConjunction() {
			return _conjunction;
		}

		public String getFilterString() {
			return _filterString;
		}

		public String getTypeValue() {
			return _typeValue;
		}

		private String _conjunction;
		private String _filterString;
		private String _typeValue;

	}

	public enum Conjunction {

		AND("and"), OR("or");

		public static Conjunction parse(String value) {
			if (Objects.equals(AND.getValue(), value)) {
				return AND;
			}
			else if (Objects.equals(OR.getValue(), value)) {
				return OR;
			}

			throw new IllegalArgumentException("Invalid value " + value);
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Conjunction(String value) {
			_value = value;
		}

		private final String _value;

	}

	public enum Type {

		CONTEXT("context"), MODEL("model");

		public static Type parse(String value) {
			if (Objects.equals(CONTEXT.getValue(), value)) {
				return CONTEXT;
			}
			else if (Objects.equals(MODEL.getValue(), value)) {
				return MODEL;
			}

			throw new IllegalArgumentException("Invalid value " + value);
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

	private String _combineFilters(
		String filterString1, String filterString2, Conjunction conjunction) {

		StringBundler sb = new StringBundler(9);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(filterString1);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SPACE);
		sb.append(conjunction.getValue());
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(filterString2);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private Map<String, Criterion> _criteria = new HashMap<>();
	private Map<String, String> _filterStrings = new HashMap<>();

}