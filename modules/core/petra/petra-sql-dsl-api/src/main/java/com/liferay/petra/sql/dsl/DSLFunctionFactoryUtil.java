/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.expression.step.WhenThenStep;
import com.liferay.petra.sql.dsl.factory.DSLFunctionFactory;

import java.sql.Clob;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Preston Crary
 */
public class DSLFunctionFactoryUtil {

	public static <N extends Number> Expression<N> add(
		Expression<N> expression1, Expression<N> expression2) {

		return _DSL_FUNCTION_FACTORY.add(expression1, expression2);
	}

	public static <N extends Number> Expression<N> add(
		Expression<N> expression, N value) {

		return _DSL_FUNCTION_FACTORY.add(expression, value);
	}

	public static Expression<Number> avg(
		Expression<? extends Number> expression) {

		return _DSL_FUNCTION_FACTORY.avg(expression);
	}

	public static Expression<Long> bitAnd(
		Expression<Long> expression1, Expression<Long> expression2) {

		return _DSL_FUNCTION_FACTORY.bitAnd(expression1, expression2);
	}

	public static Expression<Long> bitAnd(
		Expression<Long> expression, long value) {

		return _DSL_FUNCTION_FACTORY.bitAnd(expression, value);
	}

	public static <T> WhenThenStep<T> caseWhenThen(
		Predicate predicate, Expression<T> expression) {

		return _DSL_FUNCTION_FACTORY.caseWhenThen(predicate, expression);
	}

	public static <T> WhenThenStep<T> caseWhenThen(
		Predicate predicate, T value) {

		return _DSL_FUNCTION_FACTORY.caseWhenThen(predicate, value);
	}

	public static Expression<String> castClobText(Expression<Clob> expression) {
		return _DSL_FUNCTION_FACTORY.castClobText(expression);
	}

	public static Expression<Long> castLong(Expression<?> expression) {
		return _DSL_FUNCTION_FACTORY.castLong(expression);
	}

	public static Expression<String> castText(Expression<?> expression) {
		return _DSL_FUNCTION_FACTORY.castText(expression);
	}

	@SafeVarargs
	public static Expression<String> concat(Expression<String>... expressions) {
		return _DSL_FUNCTION_FACTORY.concat(expressions);
	}

	public static Expression<Long> count(Expression<?> expression) {
		return _DSL_FUNCTION_FACTORY.count(expression);
	}

	public static Expression<Long> countDistinct(Expression<?> expression) {
		return _DSL_FUNCTION_FACTORY.countDistinct(expression);
	}

	public static <N extends Number> Expression<N> divide(
		Expression<N> expression1, Expression<N> expression2) {

		return _DSL_FUNCTION_FACTORY.divide(expression1, expression2);
	}

	public static <N extends Number> Expression<N> divide(
		Expression<N> expression, N value) {

		return _DSL_FUNCTION_FACTORY.divide(expression, value);
	}

	public static Expression<String> lower(Expression<String> expression) {
		return _DSL_FUNCTION_FACTORY.lower(expression);
	}

	public static <T extends Comparable<T>> Expression<T> max(
		Expression<T> expression) {

		return _DSL_FUNCTION_FACTORY.max(expression);
	}

	public static <T extends Comparable<T>> Expression<T> min(
		Expression<T> expression) {

		return _DSL_FUNCTION_FACTORY.min(expression);
	}

	public static <N extends Number> Expression<N> multiply(
		Expression<N> expression1, Expression<N> expression2) {

		return _DSL_FUNCTION_FACTORY.multiply(expression1, expression2);
	}

	public static <N extends Number> Expression<N> multiply(
		Expression<N> expression, N value) {

		return _DSL_FUNCTION_FACTORY.multiply(expression, value);
	}

	public static <N extends Number> Expression<N> subtract(
		Expression<N> expression1, Expression<N> expression2) {

		return _DSL_FUNCTION_FACTORY.subtract(expression1, expression2);
	}

	public static <N extends Number> Expression<N> subtract(
		Expression<N> expression, N value) {

		return _DSL_FUNCTION_FACTORY.subtract(expression, value);
	}

	public static Expression<Number> sum(
		Expression<? extends Number> expression) {

		return _DSL_FUNCTION_FACTORY.sum(expression);
	}

	public static Expression<Long> withParentheses(Expression<?> expression) {
		return _DSL_FUNCTION_FACTORY.withParentheses(expression);
	}

	private static final DSLFunctionFactory _DSL_FUNCTION_FACTORY;

	static {
		ServiceLoader<DSLFunctionFactory> serviceLoader = ServiceLoader.load(
			DSLFunctionFactory.class,
			DSLFunctionFactory.class.getClassLoader());

		Iterator<DSLFunctionFactory> iterator = serviceLoader.iterator();

		_DSL_FUNCTION_FACTORY = iterator.next();
	}

}