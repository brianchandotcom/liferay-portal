/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
public class PropertyImpl implements Property {

	public PropertyImpl(boolean group, String propertyName) {
		_group = group;
		_propertyName = propertyName;
	}

	public PropertyImpl(String propertyName) {
		this(false, propertyName);
	}

	@Override
	public Order asc() {
		return new OrderImpl(true, _propertyName);
	}

	@Override
	public Projection avg() {
		return new ProjectionImpl(ProjectionType.AVG, _propertyName);
	}

	@Override
	public Criterion between(Object min, Object max) {
		return new CriterionImpl(
			CriterionType.BETWEEN, _propertyName, min, max);
	}

	@Override
	public Projection count() {
		return new ProjectionImpl(ProjectionType.COUNT, _propertyName);
	}

	@Override
	public Order desc() {
		return new OrderImpl(false, _propertyName);
	}

	@Override
	public Criterion eq(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.EQ, subselect, _propertyName);
	}

	@Override
	public Criterion eq(Object value) {
		return new CriterionImpl(CriterionType.EQ, _propertyName, value);
	}

	@Override
	public Criterion eqAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.EQ_ALL, subselect, _propertyName);
	}

	@Override
	public Criterion eqProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return eqProperty(propertyImpl._propertyName);
	}

	@Override
	public Criterion eqProperty(String other) {
		return new CriterionImpl(CriterionType.EQ, _propertyName, other);
	}

	@Override
	public Criterion ge(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.GE, subselect, _propertyName);
	}

	@Override
	public Criterion ge(Object value) {
		return new CriterionImpl(CriterionType.GE, _propertyName, value);
	}

	@Override
	public Criterion geAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.GE_ALL, subselect, _propertyName);
	}

	@Override
	public Criterion geProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return geProperty(propertyImpl._propertyName);
	}

	@Override
	public Criterion geProperty(String other) {
		return new CriterionImpl(CriterionType.GE, _propertyName, other);
	}

	@Override
	public Criterion geSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.GE_SOME, subselect, _propertyName);
	}

	@Override
	public Property getProperty(String propertyName) {
		return new PropertyImpl(
			StringBundler.concat(
				_propertyName, StringPool.PERIOD, propertyName));
	}

	public String getPropertyName() {
		return _propertyName;
	}

	@Override
	public Projection group() {
		return new PropertyImpl(true, _propertyName);
	}

	@Override
	public Criterion gt(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.GT, subselect, _propertyName);
	}

	@Override
	public Criterion gt(Object value) {
		return new CriterionImpl(CriterionType.GT, _propertyName, value);
	}

	@Override
	public Criterion gtAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.GT_ALL, subselect, _propertyName);
	}

	@Override
	public Criterion gtProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return gtProperty(propertyImpl._propertyName);
	}

	@Override
	public Criterion gtProperty(String other) {
		return new CriterionImpl(CriterionType.GT, _propertyName, other);
	}

	@Override
	public Criterion gtSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.GT_SOME, subselect, _propertyName);
	}

	@Override
	public Criterion in(char[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(Collection<?> values) {
		return new CriterionImpl(
			CriterionType.IN, _propertyName, values.toArray());
	}

	@Override
	public Criterion in(double[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion in(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.IN, subselect, _propertyName);
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
		return new CriterionImpl(CriterionType.IN, _propertyName, values);
	}

	@Override
	public Criterion in(short[] values) {
		return in(ListUtil.fromArray(values));
	}

	@Override
	public Criterion isEmpty() {
		return new CriterionImpl(CriterionType.IS_EMPTY, _propertyName);
	}

	public boolean isGroup() {
		return _group;
	}

	@Override
	public Criterion isNotEmpty() {
		return new CriterionImpl(CriterionType.IS_NOT_EMPTY, _propertyName);
	}

	@Override
	public Criterion isNotNull() {
		return new CriterionImpl(CriterionType.IS_NOT_NULL, _propertyName);
	}

	@Override
	public Criterion isNull() {
		return new CriterionImpl(CriterionType.IS_NULL, _propertyName);
	}

	@Override
	public Criterion le(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.LE, subselect, _propertyName);
	}

	@Override
	public Criterion le(Object value) {
		return new CriterionImpl(CriterionType.LE, _propertyName, value);
	}

	@Override
	public Criterion leAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.LE_ALL, subselect, _propertyName);
	}

	@Override
	public Criterion leProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return leProperty(propertyImpl._propertyName);
	}

	@Override
	public Criterion leProperty(String other) {
		return new CriterionImpl(CriterionType.LE, _propertyName, other);
	}

	@Override
	public Criterion leSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.LE_SOME, subselect, _propertyName);
	}

	@Override
	public Criterion like(Object value) {
		return new CriterionImpl(CriterionType.LIKE, _propertyName, value);
	}

	@Override
	public Criterion lt(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.LT, subselect, _propertyName);
	}

	@Override
	public Criterion lt(Object value) {
		return new CriterionImpl(CriterionType.LT, _propertyName, value);
	}

	@Override
	public Criterion ltAll(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.LT_ALL, subselect, _propertyName);
	}

	@Override
	public Criterion ltProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return ltProperty(propertyImpl._propertyName);
	}

	@Override
	public Criterion ltProperty(String other) {
		return new CriterionImpl(CriterionType.LT, _propertyName, other);
	}

	@Override
	public Criterion ltSome(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.LT_SOME, subselect, _propertyName);
	}

	@Override
	public Projection max() {
		return new ProjectionImpl(ProjectionType.MAX, _propertyName);
	}

	@Override
	public Projection min() {
		return new ProjectionImpl(ProjectionType.MIN, _propertyName);
	}

	@Override
	public Criterion ne(DynamicQuery subselect) {
		return new CriterionImpl(CriterionType.NE, subselect, _propertyName);
	}

	@Override
	public Criterion ne(Object value) {
		return new CriterionImpl(CriterionType.NE, _propertyName, value);
	}

	@Override
	public Criterion neProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return neProperty(propertyImpl._propertyName);
	}

	@Override
	public Criterion neProperty(String other) {
		return new CriterionImpl(CriterionType.NE, _propertyName, other);
	}

	@Override
	public Criterion notIn(DynamicQuery subselect) {
		return new CriterionImpl(
			CriterionType.NOT_IN, subselect, _propertyName);
	}

	@Override
	public String toString() {
		return _propertyName;
	}

	private final boolean _group;
	private final String _propertyName;

}