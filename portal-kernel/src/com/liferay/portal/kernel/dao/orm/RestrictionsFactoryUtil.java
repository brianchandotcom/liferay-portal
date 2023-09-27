/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import java.util.Collection;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class RestrictionsFactoryUtil {

	public static Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return _restrictionsFactory.allEq(propertyNameValues);
	}

	public static Criterion and(Criterion lhs, Criterion rhs) {
		return _restrictionsFactory.and(lhs, rhs);
	}

	public static Criterion between(String propertyName, Object lo, Object hi) {
		return _restrictionsFactory.between(propertyName, lo, hi);
	}

	public static Conjunction conjunction() {
		return _restrictionsFactory.conjunction();
	}

	public static Disjunction disjunction() {
		return _restrictionsFactory.disjunction();
	}

	public static Criterion eq(String propertyName, Object value) {
		return _restrictionsFactory.eq(propertyName, value);
	}

	public static Criterion eqProperty(
		String propertyName, String otherPropertyName) {

		return _restrictionsFactory.eqProperty(propertyName, otherPropertyName);
	}

	public static Criterion ge(String propertyName, Object value) {
		return _restrictionsFactory.ge(propertyName, value);
	}

	public static Criterion geProperty(
		String propertyName, String otherPropertyName) {

		return _restrictionsFactory.geProperty(propertyName, otherPropertyName);
	}

	public static RestrictionsFactory getRestrictionsFactory() {
		return _restrictionsFactory;
	}

	public static Criterion gt(String propertyName, Object value) {
		return _restrictionsFactory.gt(propertyName, value);
	}

	public static Criterion gtProperty(
		String propertyName, String otherPropertyName) {

		return _restrictionsFactory.gtProperty(propertyName, otherPropertyName);
	}

	public static Criterion ilike(String propertyName, Object value) {
		return _restrictionsFactory.ilike(propertyName, value);
	}

	public static Criterion in(String propertyName, Collection<?> values) {
		return _restrictionsFactory.in(propertyName, values);
	}

	public static Criterion in(String propertyName, Object[] values) {
		return _restrictionsFactory.in(propertyName, values);
	}

	public static Criterion isEmpty(String propertyName) {
		return _restrictionsFactory.isEmpty(propertyName);
	}

	public static Criterion isNotEmpty(String propertyName) {
		return _restrictionsFactory.isNotEmpty(propertyName);
	}

	public static Criterion isNotNull(String propertyName) {
		return _restrictionsFactory.isNotNull(propertyName);
	}

	public static Criterion isNull(String propertyName) {
		return _restrictionsFactory.isNull(propertyName);
	}

	public static Criterion le(String propertyName, Object value) {
		return _restrictionsFactory.le(propertyName, value);
	}

	public static Criterion leProperty(
		String propertyName, String otherPropertyName) {

		return _restrictionsFactory.leProperty(propertyName, otherPropertyName);
	}

	public static Criterion like(String propertyName, Object value) {
		return _restrictionsFactory.like(propertyName, value);
	}

	public static Criterion lt(String propertyName, Object value) {
		return _restrictionsFactory.lt(propertyName, value);
	}

	public static Criterion ltProperty(
		String propertyName, String otherPropertyName) {

		return _restrictionsFactory.ltProperty(propertyName, otherPropertyName);
	}

	public static Criterion ne(String propertyName, Object value) {
		return _restrictionsFactory.ne(propertyName, value);
	}

	public static Criterion neProperty(
		String propertyName, String otherPropertyName) {

		return _restrictionsFactory.neProperty(propertyName, otherPropertyName);
	}

	public static Criterion not(Criterion expression) {
		return _restrictionsFactory.not(expression);
	}

	public static Criterion or(Criterion lhs, Criterion rhs) {
		return _restrictionsFactory.or(lhs, rhs);
	}

	public static Criterion sizeEq(String propertyName, int size) {
		return _restrictionsFactory.sizeEq(propertyName, size);
	}

	public static Criterion sizeGe(String propertyName, int size) {
		return _restrictionsFactory.sizeGe(propertyName, size);
	}

	public static Criterion sizeGt(String propertyName, int size) {
		return _restrictionsFactory.sizeGt(propertyName, size);
	}

	public static Criterion sizeLe(String propertyName, int size) {
		return _restrictionsFactory.sizeLe(propertyName, size);
	}

	public static Criterion sizeLt(String propertyName, int size) {
		return _restrictionsFactory.sizeLt(propertyName, size);
	}

	public static Criterion sizeNe(String propertyName, int size) {
		return _restrictionsFactory.sizeNe(propertyName, size);
	}

	public static Criterion sqlRestriction(String sql) {
		return _restrictionsFactory.sqlRestriction(sql);
	}

	public static Criterion sqlRestriction(
		String sql, Object value, Type type) {

		return _restrictionsFactory.sqlRestriction(sql, value, type);
	}

	public static Criterion sqlRestriction(
		String sql, Object[] values, Type[] types) {

		return _restrictionsFactory.sqlRestriction(sql, values, types);
	}

	public void setRestrictionsFactory(
		RestrictionsFactory restrictionsFactory) {

		_restrictionsFactory = restrictionsFactory;
	}

	private static RestrictionsFactory _restrictionsFactory;

}