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

package com.liferay.wiki.engine.creole.internal.parser.ast.link;

import com.liferay.wiki.engine.creole.internal.parser.ast.CollectionNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.URLNode;
import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */
public class LinkNode extends URLNode {

	public LinkNode() {
	}

	public LinkNode(int token) {
		super(token);
	}

	public LinkNode(int token, String link) {
		super(token, link);
	}

	public LinkNode(String link) {
		super(link);
	}

	@Override
	public void accept(ASTVisitor astVisitor) {
		astVisitor.visit(this);
	}

	public CollectionNode getAltCollectionNode() {
		return _altCollectionNode;
	}

	public boolean hasAltCollectionNode() {
		if (_altCollectionNode != null) {
			return true;
		}

		return false;
	}

	public void setAltCollectionNode(CollectionNode altCollectionNode) {
		_altCollectionNode = altCollectionNode;
	}

	private CollectionNode _altCollectionNode;

}