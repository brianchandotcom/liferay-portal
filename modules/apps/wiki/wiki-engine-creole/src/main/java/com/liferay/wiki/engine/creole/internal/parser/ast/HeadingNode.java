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

import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */
public class HeadingNode
	extends BaseParentableNode implements Comparable<HeadingNode> {

	public HeadingNode(CollectionNode collectionNode, int level) {
		super(collectionNode);

		_level = level;
	}

	public HeadingNode(int level) {
		_level = level;
	}

	@Override
	public void accept(ASTVisitor astVisitor) {
		astVisitor.visit(this);
	}

	@Override
	public int compareTo(HeadingNode headingNode) {
		if (_level < headingNode.getLevel()) {
			return -1;
		}
		else if (_level > headingNode.getLevel()) {
			return 1;
		}

		return 0;
	}

	public int getLevel() {
		return _level;
	}

	public void setLevel(int level) {
		_level = level;
	}

	private int _level;

}