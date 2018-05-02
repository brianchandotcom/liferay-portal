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

/**
 * @author Miguel Pastor
 */
public abstract class ItemNode extends BaseParentableNode {

	public ItemNode(int tokenType) {
		this(tokenType, 0, null, null);
	}

	public ItemNode(
		int level, BaseParentableNode baseParentableNode,
		CollectionNode collectionNode) {

		this(0, level, baseParentableNode, collectionNode);
	}

	public BaseParentableNode getBaseParentableNode() {
		return _baseParentableNode;
	}

	public int getLevel() {
		return _level;
	}

	public void setBaseParentableNode(BaseParentableNode baseParentableNode) {
		_baseParentableNode = baseParentableNode;
	}

	protected ItemNode(
		int tokenType, int level, BaseParentableNode baseParentableNode,
		CollectionNode collectionNode) {

		super(collectionNode, tokenType);

		_level = level;
		_baseParentableNode = baseParentableNode;
	}

	private BaseParentableNode _baseParentableNode;
	private final int _level;

}