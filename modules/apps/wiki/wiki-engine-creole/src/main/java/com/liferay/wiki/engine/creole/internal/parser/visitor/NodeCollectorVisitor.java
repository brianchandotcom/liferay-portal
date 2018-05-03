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

package com.liferay.wiki.engine.creole.internal.parser.visitor;

import com.liferay.wiki.engine.creole.internal.parser.ast.ASTNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.WikiPageNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public abstract class NodeCollectorVisitor extends BaseASTVisitor {

	public List<ASTNode> collect(WikiPageNode wikiPageNode) {
		_astNodes = new ArrayList<>();

		visit(wikiPageNode);

		List<ASTNode> astNodes = new ArrayList<>(_astNodes);

		_astNodes = null;

		return astNodes;
	}

	protected void addNode(ASTNode node) {
		_astNodes.add(node);
	}

	private List<ASTNode> _astNodes;

}