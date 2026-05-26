/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public PropertyImpl(String propertyName) {
		super(ProjectionType.PROPERTY, propertyName);
	}

	@Override
	public Order asc() {
		return new OrderImpl(getPropertyName(), true);
	}

	@Override
	public Projection avg() {
		return new ProjectionImpl(ProjectionType.AVG, getPropertyName());
	}

	@Override
	public Criterion between(Object min, Object max) {
		return new CriterionImpl(
			CriterionType.BETWEEN, getPropertyName(), min, max);
	}

	@Override
	public Projection count() {
		return new ProjectionImpl(ProjectionType.COUNT, getPropertyName());
	}

	@Override
	public Order desc() {
		return new OrderImpl(getPropertyName(), false);
	}

	@Override
	public Criterion eq(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_EQ, getPropertyName(), subselect);
	}

	@Override
	public Criterion eq(Object value) {
		return new CriterionImpl(CriterionType.EQ, getPropertyName(), value);
	}

	@Override
	public Criterion eqAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_EQ_ALL, getPropertyName(), subselect);
	}

	@Override
	public Criterion eqProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return eqProperty(propertyImpl.getPropertyName());
	}

	@Override
	public Criterion eqProperty(String other) {
		return new CriterionImpl(
			CriterionType.EQ_PROPERTY, getPropertyName(), other);
	}

	@Override
	public Criterion ge(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_GE, getPropertyName(), subselect);
	}

	@Override
	public Criterion ge(Object value) {
		return new CriterionImpl(CriterionType.GE, getPropertyName(), value);
	}

	@Override
	public Criterion geAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_GE_ALL, getPropertyName(), subselect);
	}

	@Override
	public Criterion geProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return geProperty(propertyImpl.getPropertyName());
	}

	@Override
	public Criterion geProperty(String other) {
		return new CriterionImpl(
			CriterionType.GE_PROPERTY, getPropertyName(), other);
	}

	@Override
	public Criterion geSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_GE_SOME, getPropertyName(), subselect);
	}

	@Override
	public Property getProperty(String propertyName) {
		return new PropertyImpl(propertyName);
	}

	@Override
	public Projection group() {
		return new ProjectionImpl(
			ProjectionType.GROUP_PROPERTY, getPropertyName());
	}

	@Override
	public Criterion gt(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_GT, getPropertyName(), subselect);
	}

	@Override
	public Criterion gt(Object value) {
		return new CriterionImpl(CriterionType.GT, getPropertyName(), value);
	}

	@Override
	public Criterion gtAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_GT_ALL, getPropertyName(), subselect);
	}

	@Override
	public Criterion gtProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return gtProperty(propertyImpl.getPropertyName());
	}

	@Override
	public Criterion gtProperty(String other) {
		return new CriterionImpl(
			CriterionType.GT_PROPERTY, getPropertyName(), other);
	}

	@Override
	public Criterion gtSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_GT_SOME, getPropertyName(), subselect);
	}

	@Override
	public Criterion in(char[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(Collection<?> values) {
		return new CriterionImpl(
			CriterionType.IN, getPropertyName(), values.toArray());
	}

	@Override
	public Criterion in(double[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_IN, getPropertyName(), subselect);
	}

	@Override
	public Criterion in(float[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(int[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(long[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(Object[] values) {
		return new CriterionImpl(CriterionType.IN, getPropertyName(), values);
	}

	@Override
	public Criterion in(short[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion isEmpty() {
		return new CriterionImpl(CriterionType.IS_EMPTY, getPropertyName());
	}

	@Override
	public Criterion isNotEmpty() {
		return new CriterionImpl(CriterionType.IS_NOT_EMPTY, getPropertyName());
	}

	@Override
	public Criterion isNotNull() {
		return new CriterionImpl(CriterionType.IS_NOT_NULL, getPropertyName());
	}

	@Override
	public Criterion isNull() {
		return new CriterionImpl(CriterionType.IS_NULL, getPropertyName());
	}

	@Override
	public Criterion le(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_LE, getPropertyName(), subselect);
	}

	@Override
	public Criterion le(Object value) {
		return new CriterionImpl(CriterionType.LE, getPropertyName(), value);
	}

	@Override
	public Criterion leAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_LE_ALL, getPropertyName(), subselect);
	}

	@Override
	public Criterion leProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return leProperty(propertyImpl.getPropertyName());
	}

	@Override
	public Criterion leProperty(String other) {
		return new CriterionImpl(
			CriterionType.LE_PROPERTY, getPropertyName(), other);
	}

	@Override
	public Criterion leSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_LE_SOME, getPropertyName(), subselect);
	}

	@Override
	public Criterion like(Object value) {
		return new CriterionImpl(CriterionType.LIKE, getPropertyName(), value);
	}

	@Override
	public Criterion lt(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_LT, getPropertyName(), subselect);
	}

	@Override
	public Criterion lt(Object value) {
		return new CriterionImpl(CriterionType.LT, getPropertyName(), value);
	}

	@Override
	public Criterion ltAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_LT_ALL, getPropertyName(), subselect);
	}

	@Override
	public Criterion ltProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return ltProperty(propertyImpl.getPropertyName());
	}

	@Override
	public Criterion ltProperty(String other) {
		return new CriterionImpl(
			CriterionType.LT_PROPERTY, getPropertyName(), other);
	}

	@Override
	public Criterion ltSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_LT_SOME, getPropertyName(), subselect);
	}

	@Override
	public Projection max() {
		return new ProjectionImpl(ProjectionType.MAX, getPropertyName());
	}

	@Override
	public Projection min() {
		return new ProjectionImpl(ProjectionType.MIN, getPropertyName());
	}

	@Override
	public Criterion ne(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_NE, getPropertyName(), subselect);
	}

	@Override
	public Criterion ne(Object value) {
		return new CriterionImpl(CriterionType.NE, getPropertyName(), value);
	}

	@Override
	public Criterion neProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return neProperty(propertyImpl.getPropertyName());
	}

	@Override
	public Criterion neProperty(String other) {
		return new CriterionImpl(
			CriterionType.NE_PROPERTY, getPropertyName(), other);
	}

	@Override
	public Criterion notIn(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.SUBQUERY_NOT_IN, getPropertyName(), subselect);
	}

	@Override
	public String toString() {
		return getPropertyName();
	}

}