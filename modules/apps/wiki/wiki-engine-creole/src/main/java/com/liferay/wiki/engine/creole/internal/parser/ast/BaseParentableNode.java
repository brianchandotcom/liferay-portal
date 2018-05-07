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

package com.liferay.wiki.engine.creole.internal.parser.ast;

import java.util.List;

/**
 * @author Miguel Pastor
 */
public abstract class BaseParentableNode extends ASTNode {

	public BaseParentableNode() {
		this(null, 0);
	}

	public BaseParentableNode(CollectionNode collectionNode) {
		this(collectionNode, 0);
	}

	public BaseParentableNode(int tokenType) {
		this(null, tokenType);
	}

	public void addChildASTNode(ASTNode astNode) {
		_collectionNode.add(astNode);
	}

	public ASTNode getChildASTNode(int position) {
		return _collectionNode.get(position);
	}

	public List<ASTNode> getChildASTNodes() {
		return _collectionNode.getASTNodes();
	}

	public int getChildASTNodesCount() {
		return _collectionNode.size();
	}

	protected BaseParentableNode(CollectionNode collectionNode, int tokenType) {
		super(tokenType);

		if (collectionNode != null) {
			_collectionNode = collectionNode;
		}
		else {
			_collectionNode = new CollectionNode();
		}
	}

	private final CollectionNode _collectionNode;

}