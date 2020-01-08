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

package com.liferay.portal.kernel.dao.model.dsl.expressions;

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNode;
import com.liferay.portal.kernel.dao.model.dsl.operands.Operand;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderByExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface Expression<T> extends ASTNode {

	public default Alias<T> as(String name) {
		return new Alias<>(this, name);
	}

	public default OrderByExpression ascending() {
		return new OrderByExpression(this, true);
	}

	public default OrderByExpression descending() {
		return new OrderByExpression(this, false);
	}

	public default Predicate eq(Expression<T> expression) {
		return new Predicate(this, Operand.EQUAL, expression);
	}

	public default Predicate eq(T value) {
		return eq(new Scalar<>(value));
	}

	public default String getAlias() {
		return null;
	}

	public default Predicate gt(Expression<T> expression) {
		return new Predicate(this, Operand.GREATER_THAN, expression);
	}

	public default Predicate gt(T value) {
		return gt(new Scalar<>(value));
	}

	public default Predicate gte(Expression<T> expression) {
		return new Predicate(this, Operand.GREATER_THAN_OR_EQUAL, expression);
	}

	public default Predicate gte(T value) {
		return gte(new Scalar<>(value));
	}

	public default Predicate in(Query query) {
		return new Predicate(
			this, Operand.IN,
			(sb, astNodeListener) -> {
				sb.append("(");
				query.toSQL(sb, astNodeListener);
				sb.append(")");
			});
	}

	public default Predicate in(T[] values) {
		return new Predicate(this, Operand.IN, new ScalarList<>(values));
	}

	public default Predicate isNotNull() {
		return new Predicate(this, Operand.IS_NOT, NullExpression.INSTANCE);
	}

	public default Predicate isNull() {
		return new Predicate(this, Operand.IS, NullExpression.INSTANCE);
	}

	public default Predicate like(Expression<String> expression) {
		return new Predicate(this, Operand.LIKE, expression);
	}

	public default Predicate like(String s) {
		return like(new Scalar<>(s));
	}

	public default Predicate lt(Expression<T> expression) {
		return new Predicate(this, Operand.LESS_THAN, expression);
	}

	public default Predicate lt(T value) {
		return lt(new Scalar<>(value));
	}

	public default Predicate lte(Expression<T> expression) {
		return new Predicate(this, Operand.LESS_THAN_OR_EQUAL, expression);
	}

	public default Predicate lte(T value) {
		return lte(new Scalar<>(value));
	}

	public default Predicate neq(Expression<T> expression) {
		return new Predicate(this, Operand.NOT_EQUAL, expression);
	}

	public default Predicate neq(T value) {
		return neq(new Scalar<>(value));
	}

	public default Expression<T> unwrapAlias() {
		return this;
	}

}