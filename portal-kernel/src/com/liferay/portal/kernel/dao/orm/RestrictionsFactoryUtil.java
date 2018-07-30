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
public class RestrictionsFactoryUtil {

	public static Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return getRestrictionsFactory().allEq(propertyNameValues);
	}

	public static Criterion and(Criterion lhs, Criterion rhs) {
		return getRestrictionsFactory().and(lhs, rhs);
	}

	public static Criterion between(String propertyName, Object lo, Object hi) {
		return getRestrictionsFactory().between(propertyName, lo, hi);
	}

	public static Conjunction conjunction() {
		return getRestrictionsFactory().conjunction();
	}

	public static Disjunction disjunction() {
		return getRestrictionsFactory().disjunction();
	}

	public static Criterion eq(String propertyName, Object value) {
		return getRestrictionsFactory().eq(propertyName, value);
	}

	public static Criterion eqProperty(
		String propertyName, String otherPropertyName) {

		return getRestrictionsFactory().eqProperty(
			propertyName, otherPropertyName);
	}

	public static Criterion ge(String propertyName, Object value) {
		return getRestrictionsFactory().ge(propertyName, value);
	}

	public static Criterion geProperty(
		String propertyName, String otherPropertyName) {

		return getRestrictionsFactory().geProperty(
			propertyName, otherPropertyName);
	}

	public static RestrictionsFactory getRestrictionsFactory() {
		return _restrictionsFactory;
	}

	public static Criterion gt(String propertyName, Object value) {
		return getRestrictionsFactory().gt(propertyName, value);
	}

	public static Criterion gtProperty(
		String propertyName, String otherPropertyName) {

		return getRestrictionsFactory().gtProperty(
			propertyName, otherPropertyName);
	}

	public static Criterion ilike(String propertyName, Object value) {
		return getRestrictionsFactory().ilike(propertyName, value);
	}

	public static Criterion in(String propertyName, Collection<?> values) {
		return getRestrictionsFactory().in(propertyName, values);
	}

	public static Criterion in(String propertyName, Object[] values) {
		return getRestrictionsFactory().in(propertyName, values);
	}

	public static Criterion isEmpty(String propertyName) {
		return getRestrictionsFactory().isEmpty(propertyName);
	}

	public static Criterion isNotEmpty(String propertyName) {
		return getRestrictionsFactory().isNotEmpty(propertyName);
	}

	public static Criterion isNotNull(String propertyName) {
		return getRestrictionsFactory().isNotNull(propertyName);
	}

	public static Criterion isNull(String propertyName) {
		return getRestrictionsFactory().isNull(propertyName);
	}

	public static Criterion le(String propertyName, Object value) {
		return getRestrictionsFactory().le(propertyName, value);
	}

	public static Criterion leProperty(
		String propertyName, String otherPropertyName) {

		return getRestrictionsFactory().leProperty(
			propertyName, otherPropertyName);
	}

	public static Criterion like(String propertyName, Object value) {
		return getRestrictionsFactory().like(propertyName, value);
	}

	public static Criterion lt(String propertyName, Object value) {
		return getRestrictionsFactory().lt(propertyName, value);
	}

	public static Criterion ltProperty(
		String propertyName, String otherPropertyName) {

		return getRestrictionsFactory().ltProperty(
			propertyName, otherPropertyName);
	}

	public static Criterion ne(String propertyName, Object value) {
		return getRestrictionsFactory().ne(propertyName, value);
	}

	public static Criterion neProperty(
		String propertyName, String otherPropertyName) {

		return getRestrictionsFactory().neProperty(
			propertyName, otherPropertyName);
	}

	public static Criterion not(Criterion expression) {
		return getRestrictionsFactory().not(expression);
	}

	public static Criterion or(Criterion lhs, Criterion rhs) {
		return getRestrictionsFactory().or(lhs, rhs);
	}

	public static Criterion sizeEq(String propertyName, int size) {
		return getRestrictionsFactory().sizeEq(propertyName, size);
	}

	public static Criterion sizeGe(String propertyName, int size) {
		return getRestrictionsFactory().sizeGe(propertyName, size);
	}

	public static Criterion sizeGt(String propertyName, int size) {
		return getRestrictionsFactory().sizeGt(propertyName, size);
	}

	public static Criterion sizeLe(String propertyName, int size) {
		return getRestrictionsFactory().sizeLe(propertyName, size);
	}

	public static Criterion sizeLt(String propertyName, int size) {
		return getRestrictionsFactory().sizeLt(propertyName, size);
	}

	public static Criterion sizeNe(String propertyName, int size) {
		return getRestrictionsFactory().sizeNe(propertyName, size);
	}

	public static Criterion sqlRestriction(String sql) {
		return getRestrictionsFactory().sqlRestriction(sql);
	}

	public static Criterion sqlRestriction(
		String sql, Object value, Type type) {

		return getRestrictionsFactory().sqlRestriction(sql, value, type);
	}

	public static Criterion sqlRestriction(
		String sql, Object[] values, Type[] types) {

		return getRestrictionsFactory().sqlRestriction(sql, values, types);
	}

	public void setRestrictionsFactory(
		RestrictionsFactory restrictionsFactory) {

		_restrictionsFactory = restrictionsFactory;
	}

	private static RestrictionsFactory _restrictionsFactory;

}