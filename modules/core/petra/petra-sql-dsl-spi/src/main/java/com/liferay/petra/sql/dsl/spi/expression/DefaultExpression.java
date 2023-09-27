/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.expression.Alias;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.spi.query.QueryExpression;
import com.liferay.petra.sql.dsl.spi.query.sort.DefaultOrderByExpression;

/**
 * @author Preston Crary
 */
public interface DefaultExpression<T> extends Expression<T> {

	@Override
	public default Alias<T> as(String name) {
		return new DefaultAlias<>(this, name);
	}

	@Override
	public default OrderByExpression ascending() {
		return new DefaultOrderByExpression(this, true);
	}

	@Override
	public default OrderByExpression descending() {
		return new DefaultOrderByExpression(this, false);
	}

	@Override
	public default Predicate eq(Expression<T> expression) {
		return new DefaultPredicate(this, Operand.EQUAL, expression);
	}

	@Override
	public default Predicate eq(T value) {
		return eq(new Scalar<>(value));
	}

	@Override
	public default Predicate gt(Expression<T> expression) {
		return new DefaultPredicate(this, Operand.GREATER_THAN, expression);
	}

	@Override
	public default Predicate gt(T value) {
		return gt(new Scalar<>(value));
	}

	@Override
	public default Predicate gte(Expression<T> expression) {
		return new DefaultPredicate(
			this, Operand.GREATER_THAN_OR_EQUAL, expression);
	}

	@Override
	public default Predicate gte(T value) {
		return gte(new Scalar<>(value));
	}

	@Override
	public default Predicate in(DSLQuery dslQuery) {
		return new DefaultPredicate(
			this, Operand.IN, new QueryExpression<>(dslQuery));
	}

	@Override
	public default Predicate in(T[] values) {
		return new DefaultPredicate(this, Operand.IN, new ScalarList<>(values));
	}

	@Override
	public default Predicate isNotNull() {
		return new DefaultPredicate(
			this, Operand.IS_NOT, NullExpression.INSTANCE);
	}

	@Override
	public default Predicate isNull() {
		return new DefaultPredicate(this, Operand.IS, NullExpression.INSTANCE);
	}

	@Override
	public default Predicate like(Expression<String> expression) {
		return new DefaultPredicate(this, Operand.LIKE, expression);
	}

	@Override
	public default Predicate like(String value) {
		return like(new Scalar<>(value));
	}

	@Override
	public default Predicate lt(Expression<T> expression) {
		return new DefaultPredicate(this, Operand.LESS_THAN, expression);
	}

	@Override
	public default Predicate lt(T value) {
		return lt(new Scalar<>(value));
	}

	@Override
	public default Predicate lte(Expression<T> expression) {
		return new DefaultPredicate(
			this, Operand.LESS_THAN_OR_EQUAL, expression);
	}

	@Override
	public default Predicate lte(T value) {
		return lte(new Scalar<>(value));
	}

	@Override
	public default Predicate neq(Expression<T> expression) {
		return new DefaultPredicate(this, Operand.NOT_EQUAL, expression);
	}

	@Override
	public default Predicate neq(T value) {
		return neq(new Scalar<>(value));
	}

	@Override
	public default Predicate notIn(DSLQuery dslQuery) {
		return new DefaultPredicate(
			this, Operand.NOT_IN, new QueryExpression<>(dslQuery));
	}

	@Override
	public default Predicate notIn(T[] values) {
		return new DefaultPredicate(
			this, Operand.NOT_IN, new ScalarList<>(values));
	}

	@Override
	public default Predicate notLike(Expression<String> expression) {
		return new DefaultPredicate(this, Operand.NOT_LIKE, expression);
	}

	@Override
	public default Predicate notLike(String value) {
		return notLike(new Scalar<>(value));
	}

}