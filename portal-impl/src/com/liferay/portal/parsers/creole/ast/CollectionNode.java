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

package com.liferay.portal.parsers.creole.ast;

import com.liferay.portal.parsers.creole.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class CollectionNode extends ASTNode {

	public CollectionNode() {
		this(null, 0);
	}

	public CollectionNode(int token) {
		this(null, token);
	}

	public CollectionNode(List<ASTNode> astNodes) {
		this(astNodes, 0);
	}

	public CollectionNode(List<ASTNode> astNodes, int token) {
		super(token);

		if (astNodes != null) {
			_astNodes = astNodes;
		}
		else {
			_astNodes = new ArrayList<ASTNode>();
		}
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

	private final List<ASTNode> _astNodes;

}