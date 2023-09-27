/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;

/**
 * @author Raymond Augé
 */
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	public void afterPropertiesSet() {
		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		_databaseInMaxParameters = GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.DATABASE_IN_MAX_PARAMETERS,
				new Filter(dbType.getName())),
			Integer.MAX_VALUE);
	}

	@Override
	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return new CriterionImpl(Restrictions.allEq(propertyNameValues));
	}

	@Override
	public Criterion and(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsCriterionImpl = (CriterionImpl)lhs;
		CriterionImpl rhsCriterionImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			Restrictions.and(
				lhsCriterionImpl.getWrappedCriterion(),
				rhsCriterionImpl.getWrappedCriterion()));
	}

	@Override
	public Criterion between(String propertyName, Object lo, Object hi) {
		return new CriterionImpl(Restrictions.between(propertyName, lo, hi));
	}

	@Override
	public Conjunction conjunction() {
		return new ConjunctionImpl(Restrictions.conjunction());
	}

	@Override
	public Disjunction disjunction() {
		return new DisjunctionImpl(Restrictions.disjunction());
	}

	@Override
	public Criterion eq(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.eq(propertyName, value));
	}

	@Override
	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			Restrictions.eqProperty(propertyName, otherPropertyName));
	}

	@Override
	public Criterion ge(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.ge(propertyName, value));
	}

	@Override
	public Criterion geProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			Restrictions.geProperty(propertyName, otherPropertyName));
	}

	@Override
	public Criterion gt(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.gt(propertyName, value));
	}

	@Override
	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			Restrictions.gtProperty(propertyName, otherPropertyName));
	}

	@Override
	public Criterion ilike(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.ilike(propertyName, value));
	}

	@Override
	public Criterion in(String propertyName, Collection<?> values) {
		int size = values.size();

		if (size > _databaseInMaxParameters) {
			Disjunction disjunction = disjunction();
			int end = _databaseInMaxParameters;
			List<?> list = ListUtil.fromCollection(values);
			int start = 0;

			while (start < size) {
				disjunction.add(
					new CriterionImpl(
						Restrictions.in(
							propertyName, ListUtil.subList(list, start, end))));

				end += _databaseInMaxParameters;
				start += _databaseInMaxParameters;
			}

			return disjunction;
		}

		return new CriterionImpl(Restrictions.in(propertyName, values));
	}

	@Override
	public Criterion in(String propertyName, Object[] values) {
		return in(propertyName, ListUtil.fromArray(values));
	}

	@Override
	public Criterion isEmpty(String propertyName) {
		return new CriterionImpl(Restrictions.isEmpty(propertyName));
	}

	@Override
	public Criterion isNotEmpty(String propertyName) {
		return new CriterionImpl(Restrictions.isNotEmpty(propertyName));
	}

	@Override
	public Criterion isNotNull(String propertyName) {
		return new CriterionImpl(Restrictions.isNotNull(propertyName));
	}

	@Override
	public Criterion isNull(String propertyName) {
		return new CriterionImpl(Restrictions.isNull(propertyName));
	}

	@Override
	public Criterion le(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.le(propertyName, value));
	}

	@Override
	public Criterion leProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			Restrictions.leProperty(propertyName, otherPropertyName));
	}

	@Override
	public Criterion like(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.like(propertyName, value));
	}

	@Override
	public Criterion lt(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.lt(propertyName, value));
	}

	@Override
	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			Restrictions.ltProperty(propertyName, otherPropertyName));
	}

	@Override
	public Criterion ne(String propertyName, Object value) {
		return new CriterionImpl(Restrictions.ne(propertyName, value));
	}

	@Override
	public Criterion neProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			Restrictions.neProperty(propertyName, otherPropertyName));
	}

	@Override
	public Criterion not(Criterion expression) {
		CriterionImpl expressionCriterionImpl = (CriterionImpl)expression;

		return new CriterionImpl(
			Restrictions.not(expressionCriterionImpl.getWrappedCriterion()));
	}

	@Override
	public Criterion or(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsCriterionImpl = (CriterionImpl)lhs;
		CriterionImpl rhsCriterionImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			Restrictions.or(
				lhsCriterionImpl.getWrappedCriterion(),
				rhsCriterionImpl.getWrappedCriterion()));
	}

	@Override
	public Criterion sizeEq(String propertyName, int size) {
		return new CriterionImpl(Restrictions.sizeEq(propertyName, size));
	}

	@Override
	public Criterion sizeGe(String propertyName, int size) {
		return new CriterionImpl(Restrictions.sizeGe(propertyName, size));
	}

	@Override
	public Criterion sizeGt(String propertyName, int size) {
		return new CriterionImpl(Restrictions.sizeGe(propertyName, size));
	}

	@Override
	public Criterion sizeLe(String propertyName, int size) {
		return new CriterionImpl(Restrictions.sizeLe(propertyName, size));
	}

	@Override
	public Criterion sizeLt(String propertyName, int size) {
		return new CriterionImpl(Restrictions.sizeLt(propertyName, size));
	}

	@Override
	public Criterion sizeNe(String propertyName, int size) {
		return new CriterionImpl(Restrictions.sizeNe(propertyName, size));
	}

	@Override
	public Criterion sqlRestriction(String sql) {
		return new CriterionImpl(Restrictions.sqlRestriction(sql));
	}

	@Override
	public Criterion sqlRestriction(String sql, Object value, Type type) {
		return new CriterionImpl(
			Restrictions.sqlRestriction(
				sql, value, TypeTranslator.translate(type)));
	}

	@Override
	public Criterion sqlRestriction(String sql, Object[] values, Type[] types) {
		org.hibernate.type.Type[] hibernateTypes =
			new org.hibernate.type.Type[types.length];

		for (int i = 0; i < types.length; i++) {
			hibernateTypes[i] = TypeTranslator.translate(types[i]);
		}

		return new CriterionImpl(
			Restrictions.sqlRestriction(sql, values, hibernateTypes));
	}

	private int _databaseInMaxParameters;

}