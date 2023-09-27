/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast;

import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */
public class ImageNode extends URLNode {

	public ImageNode() {
	}

	public ImageNode(CollectionNode altCollectionNode, String uri) {
		super(uri);

		_altCollectionNode = altCollectionNode;
	}

	public ImageNode(int token) {
		super(token);
	}

	public ImageNode(
		int tokenType, CollectionNode altCollectionNode, String uri) {

		super(tokenType, uri);

		_altCollectionNode = altCollectionNode;
	}

	@Override
	public void accept(ASTVisitor astVisitor) {
		astVisitor.visit(this);
	}

	public CollectionNode getAltNode() {
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