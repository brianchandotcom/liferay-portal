/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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