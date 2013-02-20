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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.util.Collection;
import java.util.Map;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.allEq(propertyNameValues));
	}

	public Criterion and(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.and(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()));
	}

	public Criterion between(String propertyName, Object lo, Object hi) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.between(propertyName, lo, hi));
	}

	public Conjunction conjunction() {
		return new ConjunctionImpl(
			org.hibernate.criterion.Restrictions.conjunction());
	}

	public Disjunction disjunction() {
		return new DisjunctionImpl(
			org.hibernate.criterion.Restrictions.disjunction());
	}

	public Criterion eq(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eq(propertyName, value));
	}

	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eqProperty(
				propertyName, otherPropertyName));
	}

	public Criterion ge(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ge(propertyName, value));
	}

	public Criterion geProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.geProperty(
				propertyName, otherPropertyName));
	}

	public Criterion gt(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gt(propertyName, value));
	}

	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gtProperty(
				propertyName, otherPropertyName));
	}

	public Criterion ilike(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ilike(propertyName, value));
	}

	public Criterion in(String propertyName, Collection<Object> values) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values));
	}

	public Criterion in(String propertyName, Object[] values) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values));
	}

	public Criterion isEmpty(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isEmpty(propertyName));
	}

	public Criterion isNotEmpty(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotEmpty(propertyName));
	}

	public Criterion isNotNull(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotNull(propertyName));
	}

	public Criterion isNull(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNull(propertyName));
	}

	public Criterion le(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.le(propertyName, value));
	}

	public Criterion leProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.leProperty(
				propertyName, otherPropertyName));
	}

	public Criterion like(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.like(propertyName, value));
	}

	public Criterion lt(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.lt(propertyName, value));
	}

	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ltProperty(
				propertyName, otherPropertyName));
	}

	public Criterion ne(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ne(propertyName, value));
	}

	public Criterion neProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.neProperty(
				propertyName, otherPropertyName));
	}

	public Criterion not(Criterion expression) {
		CriterionImpl expressionImpl = (CriterionImpl)expression;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.not(
				expressionImpl.getWrappedCriterion()));
	}

	public Criterion or(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.or(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()));
	}

	public Criterion sizeEq(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeEq(propertyName, size));
	}

	public Criterion sizeGe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size));
	}

	public Criterion sizeGt(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size));
	}

	public Criterion sizeLe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLe(propertyName, size));
	}

	public Criterion sizeLt(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLt(propertyName, size));
	}

	public Criterion sizeNe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeNe(propertyName, size));
	}

}