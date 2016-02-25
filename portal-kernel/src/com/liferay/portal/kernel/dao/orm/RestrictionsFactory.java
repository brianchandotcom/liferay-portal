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

package com.liferay.portal.kernel.dao.orm;

import java.util.Collection;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public interface RestrictionsFactory {

	public Criterion allEq(Map<String, Criterion> propertyNameValues);

	public Criterion and(Criterion lhs, Criterion rhs);

	public Criterion between(String propertyName, Object lo, Object hi);

	public Conjunction conjunction();

	public Disjunction disjunction();

	public Criterion eq(String propertyName, Object value);

	public Criterion eqProperty(String propertyName, String otherPropertyName);

	public Criterion ge(String propertyName, Object value);

	public Criterion geProperty(String propertyName, String otherPropertyName);

	public Criterion gt(String propertyName, Object value);

	public Criterion gtProperty(String propertyName, String otherPropertyName);

	public Criterion ilike(String propertyName, Object value);

	public Criterion in(String propertyName, Collection<?> values);

	public Criterion in(String propertyName, Object[] values);

	public Criterion isEmpty(String propertyName);

	public Criterion isNotEmpty(String propertyName);

	public Criterion isNotNull(String propertyName);

	public Criterion isNull(String propertyName);

	public Criterion le(String propertyName, Object value);

	public Criterion leProperty(String propertyName, String otherPropertyName);

	public Criterion like(String propertyName, Object value);

	public Criterion lt(String propertyName, Object value);

	public Criterion ltProperty(String propertyName, String otherPropertyName);

	public Criterion ne(String propertyName, Object value);

	public Criterion neProperty(String propertyName, String otherPropertyName);

	public Criterion not(Criterion expression);

	public Criterion or(Criterion lhs, Criterion rhs);

	public Criterion sizeEq(String propertyName, int size);

	public Criterion sizeGe(String propertyName, int size);

	public Criterion sizeGt(String propertyName, int size);

	public Criterion sizeLe(String propertyName, int size);

	public Criterion sizeLt(String propertyName, int size);

	public Criterion sizeNe(String propertyName, int size);

	public Criterion sqlRestriction(String sql);

	public Criterion sqlRestriction(String sql, Object value, Type type);

	public Criterion sqlRestriction(String sql, Object[] values, Type[] types);

}