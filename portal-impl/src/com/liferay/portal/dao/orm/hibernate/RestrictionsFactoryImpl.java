/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	@Override
	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return new CriterionImpl(CriterionType.ALL_EQ, propertyNameValues);
	}

	@Override
	public Criterion and(Criterion lhs, Criterion rhs) {
		return new CriterionImpl(CriterionType.AND, lhs, rhs);
	}

	@Override
	public Criterion between(String propertyName, Object lo, Object hi) {
		return new CriterionImpl(CriterionType.BETWEEN, propertyName, lo, hi);
	}

	@Override
	public Conjunction conjunction() {
		return new ConjunctionImpl();
	}

	@Override
	public Disjunction disjunction() {
		return new DisjunctionImpl();
	}

	@Override
	public Criterion eq(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.EQ, propertyName, value);
	}

	@Override
	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			CriterionType.EQ, propertyName, otherPropertyName);
	}

	@Override
	public Criterion ge(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.GE, propertyName, value);
	}

	@Override
	public Criterion geProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			CriterionType.GE, propertyName, otherPropertyName);
	}

	@Override
	public Criterion gt(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.GT, propertyName, value);
	}

	@Override
	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			CriterionType.GT, propertyName, otherPropertyName);
	}

	@Override
	public Criterion ilike(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.ILIKE, propertyName, value);
	}

	@Override
	public Criterion in(String propertyName, Collection<?> values) {
		int size = values.size();

		if (size > DBManagerUtil.getDBInMaxParameters()) {
			Disjunction disjunction = disjunction();
			int end = DBManagerUtil.getDBInMaxParameters();
			List<?> list = ListUtil.fromCollection(values);
			int start = 0;

			while (start < size) {
				List<?> subList = ListUtil.subList(list, start, end);

				disjunction.add(
					new CriterionImpl(
						CriterionType.IN, propertyName, subList.toArray()));

				end += DBManagerUtil.getDBInMaxParameters();
				start += DBManagerUtil.getDBInMaxParameters();
			}

			return disjunction;
		}

		return new CriterionImpl(
			CriterionType.IN, propertyName, values.toArray());
	}

	@Override
	public Criterion in(String propertyName, Object[] values) {
		return in(propertyName, ListUtil.fromArray(values));
	}

	@Override
	public Criterion isEmpty(String propertyName) {
		return new CriterionImpl(CriterionType.IS_EMPTY, propertyName);
	}

	@Override
	public Criterion isNotEmpty(String propertyName) {
		return new CriterionImpl(CriterionType.IS_NOT_EMPTY, propertyName);
	}

	@Override
	public Criterion isNotNull(String propertyName) {
		return new CriterionImpl(CriterionType.IS_NOT_NULL, propertyName);
	}

	@Override
	public Criterion isNull(String propertyName) {
		return new CriterionImpl(CriterionType.IS_NULL, propertyName);
	}

	@Override
	public Criterion le(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.LE, propertyName, value);
	}

	@Override
	public Criterion leProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			CriterionType.LE, propertyName, otherPropertyName);
	}

	@Override
	public Criterion like(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.LIKE, propertyName, value);
	}

	@Override
	public Criterion lt(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.LT, propertyName, value);
	}

	@Override
	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			CriterionType.LT, propertyName, otherPropertyName);
	}

	@Override
	public Criterion ne(String propertyName, Object value) {
		return new CriterionImpl(CriterionType.NE, propertyName, value);
	}

	@Override
	public Criterion neProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			CriterionType.NE, propertyName, otherPropertyName);
	}

	@Override
	public Criterion not(Criterion expression) {
		return new CriterionImpl(CriterionType.NOT, expression);
	}

	@Override
	public Criterion or(Criterion lhs, Criterion rhs) {
		return new CriterionImpl(CriterionType.OR, lhs, rhs);
	}

	@Override
	public Criterion sizeEq(String propertyName, int size) {
		return new CriterionImpl(CriterionType.EQ, propertyName, size);
	}

	@Override
	public Criterion sizeGe(String propertyName, int size) {
		return new CriterionImpl(CriterionType.GE, propertyName, size);
	}

	@Override
	public Criterion sizeGt(String propertyName, int size) {
		return new CriterionImpl(CriterionType.GT, propertyName, size);
	}

	@Override
	public Criterion sizeLe(String propertyName, int size) {
		return new CriterionImpl(CriterionType.LE, propertyName, size);
	}

	@Override
	public Criterion sizeLt(String propertyName, int size) {
		return new CriterionImpl(CriterionType.LT, propertyName, size);
	}

	@Override
	public Criterion sizeNe(String propertyName, int size) {
		return new CriterionImpl(CriterionType.NE, propertyName, size);
	}

	@Override
	public Criterion sqlRestriction(String sql) {
		return new CriterionImpl(sql, null, null);
	}

	@Override
	public Criterion sqlRestriction(String sql, Object value, Type type) {
		return new CriterionImpl(sql, new Type[] {type}, new Object[] {value});
	}

	@Override
	public Criterion sqlRestriction(String sql, Object[] values, Type[] types) {
		return new CriterionImpl(sql, types, values);
	}

}