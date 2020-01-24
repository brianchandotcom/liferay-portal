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

import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.expressions.WhenThenStep;
import com.liferay.petra.sql.dsl.expressions.impl.AggregateExpression;
import com.liferay.petra.sql.dsl.expressions.impl.CaseWhenThen;
import com.liferay.petra.sql.dsl.expressions.impl.DSLFunction;
import com.liferay.petra.sql.dsl.expressions.impl.DSLFunctionType;
import com.liferay.petra.sql.dsl.expressions.impl.Scalar;

import java.sql.Clob;

/**
 * @author Preston Crary
 */
public class DSLFunctionUtil {

	public static <N extends Number> Expression<N> add(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.ADDITION, expression1, expression2);
	}

	public static <N extends Number> Expression<N> add(
		Expression<N> expression, N value) {

		return add(expression, new Scalar<>(value));
	}

	public static Expression<Number> avg(
		Expression<? extends Number> expression) {

		return new AggregateExpression<>(false, expression, "avg");
	}

	public static Expression<Long> bitAnd(
		Expression<Long> expression1, Expression<Long> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.BITWISE_AND, expression1, expression2);
	}

	public static Expression<Long> bitAnd(
		Expression<Long> expression, long value) {

		return bitAnd(expression, new Scalar<>(value));
	}

	public static <T> WhenThenStep<T> casesWhenThen(
		Predicate predicate, Expression<T> expression) {

		return new CaseWhenThen<>(predicate, expression);
	}

	public static <T> WhenThenStep<T> casesWhenThen(
		Predicate predicate, T value) {

		return casesWhenThen(predicate, new Scalar<>(value));
	}

	public static Expression<String> castClobText(Expression<Clob> expression) {
		return new DSLFunction<>(DSLFunctionType.CAST_CLOB_TEXT, expression);
	}

	public static Expression<Long> castLong(Expression<?> expression) {
		return new DSLFunction<>(DSLFunctionType.CAST_LONG, expression);
	}

	public static Expression<String> castText(Expression<?> expression) {
		return new DSLFunction<>(DSLFunctionType.CAST_TEXT, expression);
	}

	@SafeVarargs
	public static Expression<String> concat(Expression<String>... expressions) {
		return new DSLFunction<>(DSLFunctionType.CONCAT, expressions);
	}

	public static Expression<Long> count(Expression<?> expression) {
		return new AggregateExpression<>(false, expression, "count");
	}

	public static Expression<Long> countDistinct(Expression<?> expression) {
		return new AggregateExpression<>(true, expression, "count");
	}

	public static <N extends Number> Expression<N> divide(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.DIVISION, expression1, expression2);
	}

	public static <N extends Number> Expression<N> divide(
		Expression<N> expression, N value) {

		return divide(expression, new Scalar<>(value));
	}

	public static Expression<String> lower(Expression<String> expression) {
		return new DSLFunction<>(DSLFunctionType.LOWER, expression);
	}

	public static <T extends Number> Expression<T> max(
		Expression<T> expression) {

		return new AggregateExpression<>(false, expression, "max");
	}

	public static <T extends Number> Expression<T> min(
		Expression<T> expression) {

		return new AggregateExpression<>(false, expression, "min");
	}

	public static <N extends Number> Expression<N> multiply(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.MULTIPLICATION, expression1, expression2);
	}

	public static <N extends Number> Expression<N> multiply(
		Expression<N> expression, N value) {

		return multiply(expression, new Scalar<>(value));
	}

	public static <N extends Number> Expression<N> subtract(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.SUBTRACTION, expression1, expression2);
	}

	public static <N extends Number> Expression<N> subtract(
		Expression<N> expression, N value) {

		return subtract(expression, new Scalar<>(value));
	}

	public static Expression<Number> sum(
		Expression<? extends Number> expression) {

		return new AggregateExpression<>(false, expression, "sum");
	}

}