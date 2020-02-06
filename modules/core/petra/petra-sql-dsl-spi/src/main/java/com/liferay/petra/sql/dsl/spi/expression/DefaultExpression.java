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
		return like(new Scalar<>(value));
	}

}