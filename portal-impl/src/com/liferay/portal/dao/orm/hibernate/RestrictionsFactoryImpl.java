/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.util.Collection;
import java.util.Map;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	@Override
	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.allEq(propertyNameValues));
	}

	@Override
	public Criterion and(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.and(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()));
	}

	@Override
	public Criterion between(String propertyName, Object lo, Object hi) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.between(propertyName, lo, hi));
	}

	@Override
	public Conjunction conjunction() {
		return new ConjunctionImpl(
			org.hibernate.criterion.Restrictions.conjunction());
	}

	@Override
	public Disjunction disjunction() {
		return new DisjunctionImpl(
			org.hibernate.criterion.Restrictions.disjunction());
	}

	@Override
	public Criterion eq(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eq(propertyName, value));
	}

	@Override
	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eqProperty(
				propertyName, otherPropertyName));
	}

	@Override
	public Criterion ge(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ge(propertyName, value));
	}

	@Override
	public Criterion geProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.geProperty(
				propertyName, otherPropertyName));
	}

	@Override
	public Criterion gt(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gt(propertyName, value));
	}

	@Override
	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gtProperty(
				propertyName, otherPropertyName));
	}

	@Override
	public Criterion ilike(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ilike(propertyName, value));
	}

	@Override
	public Criterion in(String propertyName, Collection<Object> values) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values));
	}

	@Override
	public Criterion in(String propertyName, Object[] values) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values));
	}

	@Override
	public Criterion isEmpty(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isEmpty(propertyName));
	}

	@Override
	public Criterion isNotEmpty(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotEmpty(propertyName));
	}

	@Override
	public Criterion isNotNull(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotNull(propertyName));
	}

	@Override
	public Criterion isNull(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNull(propertyName));
	}

	@Override
	public Criterion le(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.le(propertyName, value));
	}

	@Override
	public Criterion leProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.leProperty(
				propertyName, otherPropertyName));
	}

	@Override
	public Criterion like(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.like(propertyName, value));
	}

	@Override
	public Criterion lt(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.lt(propertyName, value));
	}

	@Override
	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ltProperty(
				propertyName, otherPropertyName));
	}

	@Override
	public Criterion ne(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ne(propertyName, value));
	}

	@Override
	public Criterion neProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.neProperty(
				propertyName, otherPropertyName));
	}

	@Override
	public Criterion not(Criterion expression) {
		CriterionImpl expressionImpl = (CriterionImpl)expression;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.not(
				expressionImpl.getWrappedCriterion()));
	}

	@Override
	public Criterion or(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.or(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()));
	}

	@Override
	public Criterion sizeEq(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeEq(propertyName, size));
	}

	@Override
	public Criterion sizeGe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size));
	}

	@Override
	public Criterion sizeGt(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size));
	}

	@Override
	public Criterion sizeLe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLe(propertyName, size));
	}

	@Override
	public Criterion sizeLt(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLt(propertyName, size));
	}

	@Override
	public Criterion sizeNe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeNe(propertyName, size));
	}

	@Override
	public Criterion sqlRestriction(String sql) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sqlRestriction(sql));
	}

	@Override
	public Criterion sqlRestriction(String sql, Object value, Type type) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sqlRestriction(
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
			org.hibernate.criterion.Restrictions.sqlRestriction(
				sql, values, hibernateTypes));
	}

}