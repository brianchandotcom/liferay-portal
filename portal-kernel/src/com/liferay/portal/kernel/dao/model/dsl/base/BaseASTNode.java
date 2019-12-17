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

package com.liferay.portal.kernel.dao.model.dsl.base;

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNode;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public abstract class BaseASTNode implements ASTNode {

	public BaseASTNode(ASTNode childASTNode) {
		_childASTNode = Objects.requireNonNull(childASTNode);
	}

	@Override
	public final void accept(ASTNodeVisitor astNodeVisitor) {
		_childASTNode.accept(astNodeVisitor);

		astNodeVisitor.visited(_childASTNode);

		doAccept(astNodeVisitor);
	}

	@Override
	public String toString() {
		ASTNodeVisitor astNodeVisitor = new DefaultASTNodeVisitor();

		accept(astNodeVisitor);

		return astNodeVisitor.toString();
	}

	protected abstract void doAccept(ASTNodeVisitor astNodeVisitor);

	private final ASTNode _childASTNode;

}