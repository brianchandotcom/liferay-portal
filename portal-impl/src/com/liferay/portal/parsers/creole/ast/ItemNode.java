/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Miguel Pastor
 */
public abstract class ItemNode extends BaseParentableNode {

	public ItemNode(int tokenType) {
		super(tokenType);
	}

	public ItemNode(int level, BaseParentableNode parent, CollectionNode collectionNode) {
		super(collectionNode);

		_level = level;
		_parent = parent;
	}

	public int getLevel() {
		return _level;
	}

	public BaseParentableNode getParent() {
		return _parent;
	}

	public void setParent(BaseParentableNode parent) {
		_parent = parent;
	}

	private int _level;

	private BaseParentableNode _parent = null;
}