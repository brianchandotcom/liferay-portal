/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.factory;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.expression.step.WhenThenStep;
import com.liferay.petra.sql.dsl.factory.DSLFunctionFactory;
import com.liferay.petra.sql.dsl.spi.expression.AggregateExpression;
import com.liferay.petra.sql.dsl.spi.expression.DSLFunction;
import com.liferay.petra.sql.dsl.spi.expression.DSLFunctionType;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.sql.dsl.spi.expression.step.CaseWhenThen;

import java.sql.Clob;

/**
 * @author Preston Crary
 */
public class DefaultDSLFunctionFactory implements DSLFunctionFactory {

	@Override
	public <N extends Number> Expression<N> add(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.ADDITION, expression1, expression2);
	}

	@Override
	public <N extends Number> Expression<N> add(
		Expression<N> expression, N value) {

		return add(expression, new Scalar<>(value));
	}

	@Override
	public Expression<Number> avg(Expression<? extends Number> expression) {
		return new AggregateExpression<>(false, expression, "avg");
	}

	@Override
	public Expression<Long> bitAnd(
		Expression<Long> expression1, Expression<Long> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.BITWISE_AND, expression1, expression2);
	}

	@Override
	public Expression<Long> bitAnd(Expression<Long> expression, long value) {
		return bitAnd(expression, new Scalar<>(value));
	}

	@Override
	public <T> WhenThenStep<T> caseWhenThen(
		Predicate predicate, Expression<T> expression) {

		return new CaseWhenThen<>(predicate, expression);
	}

	@Override
	public <T> WhenThenStep<T> caseWhenThen(Predicate predicate, T value) {
		return caseWhenThen(predicate, new Scalar<>(value));
	}

	@Override
	public Expression<String> castClobText(Expression<Clob> expression) {
		return new DSLFunction<>(DSLFunctionType.CAST_CLOB_TEXT, expression);
	}

	@Override
	public Expression<Long> castLong(Expression<?> expression) {
		return new DSLFunction<>(DSLFunctionType.CAST_LONG, expression);
	}

	@Override
	public Expression<String> castText(Expression<?> expression) {
		return new DSLFunction<>(DSLFunctionType.CAST_TEXT, expression);
	}

	@Override
	@SafeVarargs
	public final Expression<String> concat(Expression<String>... expressions) {
		return new DSLFunction<>(DSLFunctionType.CONCAT, expressions);
	}

	@Override
	public Expression<Long> count(Expression<?> expression) {
		return new AggregateExpression<>(false, expression, "count");
	}

	@Override
	public Expression<Long> countDistinct(Expression<?> expression) {
		return new AggregateExpression<>(true, expression, "count");
	}

	@Override
	public <N extends Number> Expression<N> divide(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.DIVISION, expression1, expression2);
	}

	@Override
	public <N extends Number> Expression<N> divide(
		Expression<N> expression, N value) {

		return divide(expression, new Scalar<>(value));
	}

	@Override
	public Expression<String> lower(Expression<String> expression) {
		return new DSLFunction<>(DSLFunctionType.LOWER, expression);
	}

	@Override
	public <T extends Comparable<T>> Expression<T> max(
		Expression<T> expression) {

		return new AggregateExpression<>(false, expression, "max");
	}

	@Override
	public <T extends Comparable<T>> Expression<T> min(
		Expression<T> expression) {

		return new AggregateExpression<>(false, expression, "min");
	}

	@Override
	public <N extends Number> Expression<N> multiply(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.MULTIPLICATION, expression1, expression2);
	}

	@Override
	public <N extends Number> Expression<N> multiply(
		Expression<N> expression, N value) {

		return multiply(expression, new Scalar<>(value));
	}

	@Override
	public <N extends Number> Expression<N> subtract(
		Expression<N> expression1, Expression<N> expression2) {

		return new DSLFunction<>(
			DSLFunctionType.SUBTRACTION, expression1, expression2);
	}

	@Override
	public <N extends Number> Expression<N> subtract(
		Expression<N> expression, N value) {

		return subtract(expression, new Scalar<>(value));
	}

	@Override
	public Expression<Number> sum(Expression<? extends Number> expression) {
		return new AggregateExpression<>(false, expression, "sum");
	}

	@Override
	public Expression<Long> withParentheses(Expression<?> expression) {
		return new DSLFunction<>(DSLFunctionType.WITH_PARENTHESES, expression);
	}

}