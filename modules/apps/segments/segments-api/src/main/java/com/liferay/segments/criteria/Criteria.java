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

import java.util.HashMap;
import java.util.Map;

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
		String key, String filterString, Conjunction conjunction) {

		_criterionMap.put(key, new Criterion(filterString, conjunction));
	}

	public void addFilter(String filterString, Conjunction conjunction) {
		if (Validator.isNull(filterString)) {
			return;
		}

		if (Validator.isNull(_filterString)) {
			_filterString = filterString;

			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(_filterString);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SPACE);
		sb.append(conjunction.getValue());
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(filterString);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		_filterString = sb.toString();
	}

	public Criterion getCriterion(String key) {
		return _criterionMap.get(key);
	}

	public Map<String, Criterion> getCriterionMap() {
		return _criterionMap;
	}

	public String getFilterString() {
		return _filterString;
	}

	public static final class Criterion implements Serializable {

		public Criterion() {
		}

		public Criterion(String filterString, Conjunction conjunction) {
			_filterString = filterString;
			_conjunction = conjunction.getValue();
		}

		public String getConjunction() {
			return _conjunction;
		}

		public String getFilterString() {
			return _filterString;
		}

		private String _conjunction;
		private String _filterString;

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

	private Map<String, Criterion> _criterionMap = new HashMap();
	private String _filterString;

}