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

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.expressions.CaseWhenThen;
import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.expressions.Scalar;
import com.liferay.petra.sql.dsl.functions.Function;
import com.liferay.petra.sql.dsl.functions.FunctionType;

/**
 * @author Preston Crary
 */
public class DSLFunctionUtil {

	public static <N extends Number> Function<N> add(
		Expression<N> expression1, Expression<N> expression2) {

		return new Function<>(FunctionType.ADDITION, expression1, expression2);
	}

	public static <N extends Number> Function<N> add(
		Expression<N> expression, N value) {

		return add(expression, new Scalar<>(value));
	}

	public static Function<Long> bitAnd(
		Expression<Long> expression1, Expression<Long> expression2) {

		return new Function<>(
			FunctionType.BITWISE_AND, expression1, expression2);
	}

	public static Function<Long> bitAnd(
		Expression<Long> expression, long value) {

		return bitAnd(expression, new Scalar<>(value));
	}

	public static <T> CaseWhenThen<T> casesWhenThen(
		Predicate predicate, Expression<T> expression) {

		return new CaseWhenThen<>(predicate, expression);
	}

	public static <T> CaseWhenThen<T> casesWhenThen(
		Predicate predicate, T value) {

		return casesWhenThen(predicate, new Scalar<>(value));
	}

	public static Function<String> castClobText(Expression<?> expression) {
		return new Function<>(FunctionType.CAST_CLOB_TEXT, expression);
	}

	public static Function<Long> castLong(Expression<?> expression) {
		return new Function<>(FunctionType.CAST_LONG, expression);
	}

	public static Function<String> castText(Expression<?> expression) {
		return new Function<>(FunctionType.CAST_TEXT, expression);
	}

	@SafeVarargs
	public static Function<String> concat(Expression<String>... expressions) {
		return new Function<>(FunctionType.CONCAT, expressions);
	}

	public static <N extends Number> Function<N> divide(
		Expression<N> expression1, Expression<N> expression2) {

		return new Function<>(FunctionType.DIVISION, expression1, expression2);
	}

	public static <N extends Number> Function<N> divide(
		Expression<N> expression, N value) {

		return divide(expression, new Scalar<>(value));
	}

	public static Function<String> lower(Expression<String> expression) {
		return new Function<>(FunctionType.LOWER, expression);
	}

	public static <N extends Number> Function<N> multiply(
		Expression<N> expression1, Expression<N> expression2) {

		return new Function<>(
			FunctionType.MULTIPLICATION, expression1, expression2);
	}

	public static <N extends Number> Function<N> multiply(
		Expression<N> expression, N value) {

		return multiply(expression, new Scalar<>(value));
	}

	public static <N extends Number> Function<N> subtract(
		Expression<N> expression1, Expression<N> expression2) {

		return new Function<>(
			FunctionType.SUBTRACTION, expression1, expression2);
	}

	public static <N extends Number> Function<N> subtract(
		Expression<N> expression, N value) {

		return subtract(expression, new Scalar<>(value));
	}

}