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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
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
 * @review
 */
public final class Criteria implements Serializable {

	public Criteria() {
	}

	public void addCriterion(
		String key, Type type, String filterString, Conjunction conjunction) {

		_criterionMap.put(key, new Criterion(type, filterString, conjunction));
	}

	public void addFilter(
		Type type, String filterString, Conjunction conjunction) {

		if (Validator.isNull(filterString)) {
			return;
		}

		String curFilterString = _filterStringMap.get(type.getValue());

		if (Validator.isNull(curFilterString)) {
			_filterStringMap.put(type.getValue(), filterString);

			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(curFilterString);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SPACE);
		sb.append(conjunction.getValue());
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(filterString);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		_filterStringMap.put(type.getValue(), sb.toString());
	}

	public Criterion getCriterion(String key) {
		return _criterionMap.get(key);
	}

	public Map<String, Criterion> getCriterionMap() {
		return _criterionMap;
	}

	public String getFilterString(Type type) {
		return _filterStringMap.get(type.getValue());
	}

	public Conjunction getTypeConjunction(Type type) {
		Collection<Criterion> criterionList = _criterionMap.values();

		Stream<Criterion> stream = criterionList.stream();

		return stream.filter(
			criterion -> Objects.equals(type.getValue(), criterion.getType())
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

			_type = type.getValue();
			_filterString = filterString;
			_conjunction = conjunction.getValue();
		}

		public String getConjunction() {
			return _conjunction;
		}

		public String getFilterString() {
			return _filterString;
		}

		public String getType() {
			return _type;
		}

		private String _conjunction;
		private String _filterString;
		private String _type;

	}

	public enum Conjunction {

		AND("and"), OR("or");

		public static Conjunction parse(String value) {
			if (AND.getValue().equals(value)) {
				return AND;
			}
			else if (OR.getValue().equals(value)) {
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
			if (CONTEXT.getValue().equals(value)) {
				return CONTEXT;
			}
			else if (MODEL.getValue().equals(value)) {
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

	private Map<String, Criterion> _criterionMap = new HashMap();
	private Map<String, String> _filterStringMap = new HashMap();

}