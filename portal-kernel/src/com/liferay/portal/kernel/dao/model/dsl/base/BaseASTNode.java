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
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseASTNode implements ASTNode {

	public BaseASTNode() {
		_childASTNode = null;
	}

	public BaseASTNode(ASTNode childASTNode) {
		_childASTNode = Objects.requireNonNull(childASTNode);
	}

	@Override
	public void toSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (astNodeListener != null) {
			astNodeListener.process(this);
		}

		if (_childASTNode != null) {
			_childASTNode.toSQL(consumer, astNodeListener);

			consumer.accept(" ");
		}

		doToSQL(consumer, astNodeListener);
	}

	@Override
	public String toString() {
		return toSQL(null);
	}

	protected abstract void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener);

	private final ASTNode _childASTNode;

}