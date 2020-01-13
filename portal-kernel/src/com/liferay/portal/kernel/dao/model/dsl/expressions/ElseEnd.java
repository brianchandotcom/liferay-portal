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

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class ElseEnd<T> extends BaseASTNode implements Expression<T> {

	public ElseEnd(ElseEndStep<T> elseEndStep, Expression<T> elseExpression) {
		super(elseEndStep);

		_elseExpression = Objects.requireNonNull(elseExpression);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("else ");

		_elseExpression.toSQL(consumer, astNodeListener);

		consumer.accept(" end");
	}

	public Expression<T> getElseExpression() {
		return _elseExpression;
	}

	private final Expression<T> _elseExpression;

}