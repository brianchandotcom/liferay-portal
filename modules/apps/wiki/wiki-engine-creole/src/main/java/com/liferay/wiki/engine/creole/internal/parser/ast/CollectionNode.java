/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast;

import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class CollectionNode extends ASTNode {

	public CollectionNode() {
		this(0, null);
	}

	public CollectionNode(int token) {
		this(token, null);
	}

	public CollectionNode(List<ASTNode> astNodes) {
		this(0, astNodes);
	}

	@Override
	public void accept(ASTVisitor astVisitor) {
		astVisitor.visit(this);
	}

	public void add(ASTNode astNode) {
		_astNodes.add(astNode);
	}

	public ASTNode get(int position) {
		return _astNodes.get(position);
	}

	public List<ASTNode> getASTNodes() {
		return _astNodes;
	}

	public int size() {
		return _astNodes.size();
	}

	protected CollectionNode(int token, List<ASTNode> astNodes) {
		super(token);

		if (astNodes != null) {
			_astNodes = astNodes;
		}
		else {
			_astNodes = new ArrayList<>();
		}
	}

	private final List<ASTNode> _astNodes;

}