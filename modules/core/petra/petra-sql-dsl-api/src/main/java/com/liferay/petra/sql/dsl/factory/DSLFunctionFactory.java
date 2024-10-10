/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.factory;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.expression.step.WhenThenStep;

import java.sql.Clob;

/**
 * @author Preston Crary
 */
public interface DSLFunctionFactory {

	public <N extends Number> Expression<N> add(
		Expression<N> expression1, Expression<N> expression2);

	public <N extends Number> Expression<N> add(
		Expression<N> expression, N value);

	public Expression<Number> avg(Expression<? extends Number> expression);

	public Expression<Long> bitAnd(
		Expression<Long> expression1, Expression<Long> expression2);

	public Expression<Long> bitAnd(Expression<Long> expression, long value);

	public <T> WhenThenStep<T> caseWhenThen(
		Predicate predicate, Expression<T> expression);

	public <T> WhenThenStep<T> caseWhenThen(Predicate predicate, T value);

	public Expression<String> castClobText(Expression<Clob> expression);

	public Expression<Long> castLong(Expression<?> expression);

	public Expression<String> castText(Expression<?> expression);

	@SuppressWarnings("unchecked")
	public Expression<String> concat(Expression<String>... expressions);

	public Expression<Long> count(Expression<?> expression);

	public Expression<Long> countDistinct(Expression<?> expression);

	public <N extends Number> Expression<N> divide(
		Expression<N> expression1, Expression<N> expression2);

	public <N extends Number> Expression<N> divide(
		Expression<N> expression, N value);

	public Expression<String> lower(Expression<String> expression);

	public <T extends Comparable<T>> Expression<T> max(
		Expression<T> expression);

	public <T extends Comparable<T>> Expression<T> min(
		Expression<T> expression);

	public <N extends Number> Expression<N> multiply(
		Expression<N> expression1, Expression<N> expression2);

	public <N extends Number> Expression<N> multiply(
		Expression<N> expression, N value);

	public <N extends Number> Expression<N> subtract(
		Expression<N> expression1, Expression<N> expression2);

	public <N extends Number> Expression<N> subtract(
		Expression<N> expression, N value);

	public Expression<Number> sum(Expression<? extends Number> expression);

	public Expression<Long> withParentheses(Expression<?> expression);

}