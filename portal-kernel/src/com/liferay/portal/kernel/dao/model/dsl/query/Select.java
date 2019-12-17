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

package com.liferay.portal.kernel.dao.model.dsl.query;

import com.liferay.portal.kernel.dao.model.dsl.Statement;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Select implements FromStep, Statement {

	public Select(boolean distinct, Expression<?>[] expressions) {
		_distinct = distinct;
		_expressions = Objects.requireNonNull(expressions);
	}

	@Override
	public void accept(ASTNodeVisitor astNodeVisitor) {
		astNodeVisitor.visit(this);
	}

	public Expression<?>[] getExpressions() {
		return _expressions;
	}

	public boolean isDistinct() {
		return _distinct;
	}

	@Override
	public String toString() {
		ASTNodeVisitor astNodeVisitor = new DefaultASTNodeVisitor();

		astNodeVisitor.visit(this);

		return astNodeVisitor.toString();
	}

	private final boolean _distinct;
	private final Expression<?>[] _expressions;

}