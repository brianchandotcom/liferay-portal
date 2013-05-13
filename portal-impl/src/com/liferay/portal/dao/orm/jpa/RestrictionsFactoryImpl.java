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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.util.Collection;
import java.util.Map;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	@Override
	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion and(Criterion lhs, Criterion rhs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion between(String propertyName, Object lo, Object hi) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Conjunction conjunction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Disjunction disjunction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion eq(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion ge(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion geProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion gt(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion ilike(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion in(String propertyName, Collection<Object> values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion in(String propertyName, Object[] values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion isEmpty(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion isNotEmpty(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion isNotNull(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion isNull(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion le(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion leProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion like(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion lt(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion ne(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion neProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion not(Criterion expression) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion or(Criterion lhs, Criterion rhs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sizeEq(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sizeGe(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sizeGt(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sizeLe(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sizeLt(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sizeNe(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sqlRestriction(String sql) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sqlRestriction(String sql, Object value, Type type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Criterion sqlRestriction(String sql, Object[] values, Type[] types) {
		throw new UnsupportedOperationException();
	}

}