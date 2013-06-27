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

package com.liferay.portal.kernel.dao.orm;

import java.util.Collection;

/**
 * @author Brian Wing Shun Chan
 */
public interface Property extends Projection {

	public Order asc();

	public Projection avg();

	public Criterion between(Object min, Object max);

	public Projection count();

	public Order desc();

	public Criterion eq(DynamicQuery subselect);

	public Criterion eq(Object value);

	public Criterion eqAll(DynamicQuery subselect);

	public Criterion eqProperty(Property other);

	public Criterion eqProperty(String other);

	public Criterion ge(DynamicQuery subselect);

	public Criterion ge(Object value);

	public Criterion geAll(DynamicQuery subselect);

	public Criterion geProperty(Property other);

	public Criterion geProperty(String other);

	public Criterion geSome(DynamicQuery subselect);

	public Property getProperty(String propertyName);

	public Projection group();

	public Criterion gt(DynamicQuery subselect);

	public Criterion gt(Object value);

	public Criterion gtAll(DynamicQuery subselect);

	public Criterion gtProperty(Property other);

	public Criterion gtProperty(String other);

	public Criterion gtSome(DynamicQuery subselect);

	public Criterion in(char[] values);

	public Criterion in(Collection<Object> values);

	public Criterion in(double[] values);

	public Criterion in(DynamicQuery subselect);

	public Criterion in(float[] values);

	public Criterion in(int[] values);

	public Criterion in(long[] values);

	public Criterion in(Object[] values);

	public Criterion in(short[] values);

	public Criterion isEmpty();

	public Criterion isNotEmpty();

	public Criterion isNotNull();

	public Criterion isNull();

	public Criterion le(DynamicQuery subselect);

	public Criterion le(Object value);

	public Criterion leAll(DynamicQuery subselect);

	public Criterion leProperty(Property other);

	public Criterion leProperty(String other);

	public Criterion leSome(DynamicQuery subselect);

	public Criterion like(Object value);

	public Criterion lt(DynamicQuery subselect);

	public Criterion lt(Object value);

	public Criterion ltAll(DynamicQuery subselect);

	public Criterion ltProperty(Property other);

	public Criterion ltProperty(String other);

	public Criterion ltSome(DynamicQuery subselect);

	public Projection max();

	public Projection min();

	public Criterion ne(DynamicQuery subselect);

	public Criterion ne(Object value);

	public Criterion neProperty(Property other);

	public Criterion neProperty(String other);

	public Criterion notIn(DynamicQuery subselect);

}