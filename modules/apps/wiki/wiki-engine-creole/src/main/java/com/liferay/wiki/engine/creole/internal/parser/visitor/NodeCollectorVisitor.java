/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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