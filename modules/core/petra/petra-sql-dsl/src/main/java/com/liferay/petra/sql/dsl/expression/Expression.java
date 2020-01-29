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

package com.liferay.petra.sql.dsl.expression;

import com.liferay.petra.sql.dsl.ast.ASTNode;
import com.liferay.petra.sql.dsl.expression.impl.AliasImpl;
import com.liferay.petra.sql.dsl.expression.impl.NullExpression;
import com.liferay.petra.sql.dsl.expression.impl.Operand;
import com.liferay.petra.sql.dsl.expression.impl.PredicateImpl;
import com.liferay.petra.sql.dsl.expression.impl.Scalar;
import com.liferay.petra.sql.dsl.expression.impl.ScalarList;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.impl.QueryExpression;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.query.sort.impl.OrderByExpressionImpl;

/**
 * @author Preston Crary
 */
public interface Expression<T> extends ASTNode {

	public default Alias<T> as(String name) {
		return new AliasImpl<>(this, name);
	}

	public default OrderByExpression ascending() {
		return new OrderByExpressionImpl(this, true);
	}

	public default OrderByExpression descending() {
		return new OrderByExpressionImpl(this, false);
	}

	public default Predicate eq(Expression<T> expression) {
		return new PredicateImpl(this, Operand.EQUAL, expression);
	}

	public default Predicate eq(T value) {
		return eq(new Scalar<>(value));
	}

	public default Predicate gt(Expression<T> expression) {
		return new PredicateImpl(this, Operand.GREATER_THAN, expression);
	}

	public default Predicate gt(T value) {
		return gt(new Scalar<>(value));
	}

	public default Predicate gte(Expression<T> expression) {
		return new PredicateImpl(
			this, Operand.GREATER_THAN_OR_EQUAL, expression);
	}

	public default Predicate gte(T value) {
		return gte(new Scalar<>(value));
	}

	public default Predicate in(DSLQuery dslQuery) {
		return new PredicateImpl(
			this, Operand.IN, new QueryExpression<>(dslQuery));
	}

	public default Predicate in(T[] values) {
		return new PredicateImpl(this, Operand.IN, new ScalarList<>(values));
	}

	public default Predicate isNotNull() {
		return new PredicateImpl(this, Operand.IS_NOT, NullExpression.INSTANCE);
	}

	public default Predicate isNull() {
		return new PredicateImpl(this, Operand.IS, NullExpression.INSTANCE);
	}

	public default Predicate like(Expression<String> expression) {
		return new PredicateImpl(this, Operand.LIKE, expression);
	}

	public default Predicate like(String s) {
		return like(new Scalar<>(s));
	}

	public default Predicate lt(Expression<T> expression) {
		return new PredicateImpl(this, Operand.LESS_THAN, expression);
	}

	public default Predicate lt(T value) {
		return lt(new Scalar<>(value));
	}

	public default Predicate lte(Expression<T> expression) {
		return new PredicateImpl(this, Operand.LESS_THAN_OR_EQUAL, expression);
	}

	public default Predicate lte(T value) {
		return lte(new Scalar<>(value));
	}

	public default Predicate neq(Expression<T> expression) {
		return new PredicateImpl(this, Operand.NOT_EQUAL, expression);
	}

	public default Predicate neq(T value) {
		return neq(new Scalar<>(value));
	}

	public default Predicate notIn(DSLQuery dslQuery) {
		return new PredicateImpl(
			this, Operand.NOT_IN, new QueryExpression<>(dslQuery));
	}

	public default Predicate notIn(T[] values) {
		return new PredicateImpl(
			this, Operand.NOT_IN, new ScalarList<>(values));
	}

	public default Predicate notLike(Expression<String> expression) {
		return new PredicateImpl(this, Operand.NOT_LIKE, expression);
	}

	public default Predicate notLike(String value) {
		return like(new Scalar<>(value));
	}

}