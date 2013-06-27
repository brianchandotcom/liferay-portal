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

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;

/**
 * @author Brian Wing Shun Chan
 */
public class PropertyImpl extends ProjectionImpl implements Property {

	public PropertyImpl(org.hibernate.criterion.Property property) {
		super(property);

		_property = property;
	}

	@Override
	public Order asc() {
		return new OrderImpl(_property.asc());
	}

	@Override
	public Projection avg() {
		return new ProjectionImpl(_property.avg());
	}

	@Override
	public Criterion between(Object min, Object max) {
		return new CriterionImpl(_property.between(min, max));
	}

	@Override
	public Projection count() {
		return new ProjectionImpl(_property.count());
	}

	@Override
	public Order desc() {
		return new OrderImpl(_property.desc());
	}

	@Override
	public Criterion eq(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.eq(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion eq(Object value) {
		return new CriterionImpl(_property.eq(value));
	}

	@Override
	public Criterion eqAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.eqAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion eqProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.eqProperty(propertyImpl.getWrappedProperty()));
	}

	@Override
	public Criterion eqProperty(String other) {
		return new CriterionImpl(_property.eqProperty(other));
	}

	@Override
	public Criterion ge(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ge(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion ge(Object value) {
		return new CriterionImpl(_property.ge(value));
	}

	@Override
	public Criterion geAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.geAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion geProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.geProperty(propertyImpl.getWrappedProperty()));
	}

	@Override
	public Criterion geProperty(String other) {
		return new CriterionImpl(_property.geProperty(other));
	}

	@Override
	public Criterion geSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.geSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Property getProperty(String propertyName) {
		return new PropertyImpl(_property.getProperty(propertyName));
	}

	public org.hibernate.criterion.Property getWrappedProperty() {
		return _property;
	}

	@Override
	public Projection group() {
		return new ProjectionImpl(_property.group());
	}

	@Override
	public Criterion gt(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.gt(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion gt(Object value) {
		return new CriterionImpl(_property.gt(value));
	}

	@Override
	public Criterion gtAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.gtAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion gtProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.gtProperty(propertyImpl.getWrappedProperty()));
	}

	@Override
	public Criterion gtProperty(String other) {
		return new CriterionImpl(_property.gtProperty(other));
	}

	@Override
	public Criterion gtSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.gtSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion in(char[] values) {
		Object list = ListUtil.toList(values);

		return in((Collection<Object>)list);
	}

	@Override
	public Criterion in(Collection<Object> values) {
		return new CriterionImpl(_property.in(values));
	}

	@Override
	public Criterion in(double[] values) {
		Object list = ListUtil.toList(values);

		return in((Collection<Object>)list);
	}

	@Override
	public Criterion in(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.in(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion in(float[] values) {
		Object list = ListUtil.toList(values);

		return in((Collection<Object>)list);
	}

	@Override
	public Criterion in(int[] values) {
		Object list = ListUtil.toList(values);

		return in((Collection<Object>)list);
	}

	@Override
	public Criterion in(long[] values) {
		Object list = ListUtil.toList(values);

		return in((Collection<Object>)list);
	}

	@Override
	public Criterion in(Object[] values) {
		return new CriterionImpl(_property.in(values));
	}

	@Override
	public Criterion in(short[] values) {
		Object list = ListUtil.toList(values);

		return in((Collection<Object>)list);
	}

	@Override
	public Criterion isEmpty() {
		return new CriterionImpl(_property.isEmpty());
	}

	@Override
	public Criterion isNotEmpty() {
		return new CriterionImpl(_property.isNotEmpty());
	}

	@Override
	public Criterion isNotNull() {
		return new CriterionImpl(_property.isNotNull());
	}

	@Override
	public Criterion isNull() {
		return new CriterionImpl(_property.isNull());
	}

	@Override
	public Criterion le(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.le(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion le(Object value) {
		return new CriterionImpl(_property.le(value));
	}

	@Override
	public Criterion leAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.leAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion leProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.leProperty(propertyImpl.getWrappedProperty()));
	}

	@Override
	public Criterion leProperty(String other) {
		return new CriterionImpl(_property.leProperty(other));
	}

	@Override
	public Criterion leSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.leSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion like(Object value) {
		return new CriterionImpl(_property.like(value));
	}

	@Override
	public Criterion lt(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.lt(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion lt(Object value) {
		return new CriterionImpl(_property.lt(value));
	}

	@Override
	public Criterion ltAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ltAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion ltProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.ltProperty(propertyImpl.getWrappedProperty()));
	}

	@Override
	public Criterion ltProperty(String other) {
		return new CriterionImpl(_property.ltProperty(other));
	}

	@Override
	public Criterion ltSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ltSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Projection max() {
		return new ProjectionImpl(_property.max());
	}

	@Override
	public Projection min() {
		return new ProjectionImpl(_property.min());
	}

	@Override
	public Criterion ne(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ne(dynamicQueryImpl.getDetachedCriteria()));
	}

	@Override
	public Criterion ne(Object value) {
		return new CriterionImpl(_property.ne(value));
	}

	@Override
	public Criterion neProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.neProperty(propertyImpl.getWrappedProperty()));
	}

	@Override
	public Criterion neProperty(String other) {
		return new CriterionImpl(_property.neProperty(other));
	}

	@Override
	public Criterion notIn(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.notIn(dynamicQueryImpl.getDetachedCriteria()));
	}

	private org.hibernate.criterion.Property _property;

}