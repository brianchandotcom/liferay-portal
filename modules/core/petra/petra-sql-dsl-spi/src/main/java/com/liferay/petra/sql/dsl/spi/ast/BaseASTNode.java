/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.ast;

import com.liferay.petra.sql.dsl.ast.ASTNode;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseASTNode implements ASTNode, Cloneable {

	public BaseASTNode() {
		_childASTNode = null;
	}

	public BaseASTNode(ASTNode childASTNode) {
		_childASTNode = Objects.requireNonNull(childASTNode);
	}

	public ASTNode getChild() {
		return _childASTNode;
	}

	@Override
	public void toSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (_childASTNode != null) {
			_childASTNode.toSQL(consumer, astNodeListener);

			consumer.accept(" ");
		}

		if (astNodeListener != null) {
			astNodeListener.process(this);
		}

		doToSQL(consumer, astNodeListener);
	}

	@Override
	public String toString() {
		return toSQL(null);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseASTNode> T withNewChild(ASTNode childASTNode) {
		try {
			BaseASTNode baseASTNode = (BaseASTNode)clone();

			baseASTNode._childASTNode = childASTNode;

			return (T)baseASTNode;
		}
		catch (CloneNotSupportedException cloneNotSupportedException) {
			throw new RuntimeException(cloneNotSupportedException);
		}
	}

	protected abstract void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener);

	private ASTNode _childASTNode;

}