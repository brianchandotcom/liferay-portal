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

package com.liferay.portal.kernel.dao.orm.escape;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class EscapedRestrictionsFactory implements RestrictionsFactory {

	public EscapedRestrictionsFactory(RestrictionsFactory restrictionsFactory) {
		_restrictionsFactory = restrictionsFactory;
	}

	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		Map<String, Criterion> escapedPropertyNameValues =
			new HashMap<String, Criterion>();

		for (Map.Entry<String, Criterion> entry :
				propertyNameValues.entrySet()) {

			String propertyName = ColumnEscapeUtil.escape(entry.getKey());

			escapedPropertyNameValues.put(propertyName, entry.getValue());
		}

		return _restrictionsFactory.allEq(escapedPropertyNameValues);
	}

	public Criterion and(Criterion lhs, Criterion rhs) {
		return _restrictionsFactory.and(lhs, rhs);
	}

	public Criterion between(String propertyName, Object lo, Object hi) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.between(propertyName, lo, hi);
	}

	public Conjunction conjunction() {
		return _restrictionsFactory.conjunction();
	}

	public Disjunction disjunction() {
		return _restrictionsFactory.disjunction();
	}

	public Criterion eq(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.eq(propertyName, value);
	}

	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);
		otherPropertyName = ColumnEscapeUtil.escape(otherPropertyName);

		return _restrictionsFactory.eqProperty(propertyName, otherPropertyName);
	}

	public Criterion ge(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.ge(propertyName, value);
	}

	public Criterion geProperty(String propertyName, String otherPropertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);
		otherPropertyName = ColumnEscapeUtil.escape(otherPropertyName);

		return _restrictionsFactory.geProperty(propertyName, otherPropertyName);
	}

	public Criterion gt(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.gt(propertyName, value);
	}

	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);
		otherPropertyName = ColumnEscapeUtil.escape(otherPropertyName);

		return _restrictionsFactory.gtProperty(propertyName, otherPropertyName);
	}

	public Criterion ilike(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.ilike(propertyName, value);
	}

	public Criterion in(String propertyName, Collection<Object> values) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.in(propertyName, values);
	}

	public Criterion in(String propertyName, Object[] values) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.in(propertyName, values);
	}

	public Criterion isEmpty(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.isEmpty(propertyName);
	}

	public Criterion isNotEmpty(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.isNotEmpty(propertyName);
	}

	public Criterion isNotNull(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.isNotNull(propertyName);
	}

	public Criterion isNull(String propertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.isNull(propertyName);
	}

	public Criterion le(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.le(propertyName, value);
	}

	public Criterion leProperty(String propertyName, String otherPropertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);
		otherPropertyName = ColumnEscapeUtil.escape(otherPropertyName);

		return _restrictionsFactory.leProperty(propertyName, otherPropertyName);
	}

	public Criterion like(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.like(propertyName, value);
	}

	public Criterion lt(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.lt(propertyName, value);
	}

	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);
		otherPropertyName = ColumnEscapeUtil.escape(otherPropertyName);

		return _restrictionsFactory.ltProperty(propertyName, otherPropertyName);
	}

	public Criterion ne(String propertyName, Object value) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.ne(propertyName, value);
	}

	public Criterion neProperty(String propertyName, String otherPropertyName) {
		propertyName = ColumnEscapeUtil.escape(propertyName);
		otherPropertyName = ColumnEscapeUtil.escape(otherPropertyName);

		return _restrictionsFactory.neProperty(propertyName, otherPropertyName);
	}

	public Criterion not(Criterion expression) {
		return _restrictionsFactory.not(expression);
	}

	public Criterion or(Criterion lhs, Criterion rhs) {
		return _restrictionsFactory.or(lhs, rhs);
	}

	public Criterion sizeEq(String propertyName, int size) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.sizeEq(propertyName, size);
	}

	public Criterion sizeGe(String propertyName, int size) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.sizeGe(propertyName, size);
	}

	public Criterion sizeGt(String propertyName, int size) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.sizeGt(propertyName, size);
	}

	public Criterion sizeLe(String propertyName, int size) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.sizeLe(propertyName, size);
	}

	public Criterion sizeLt(String propertyName, int size) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.sizeLt(propertyName, size);
	}

	public Criterion sizeNe(String propertyName, int size) {
		propertyName = ColumnEscapeUtil.escape(propertyName);

		return _restrictionsFactory.sizeNe(propertyName, size);
	}

	private RestrictionsFactory _restrictionsFactory;

}