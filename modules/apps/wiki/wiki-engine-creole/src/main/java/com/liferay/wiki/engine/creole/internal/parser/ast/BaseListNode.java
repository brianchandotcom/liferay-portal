/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast;

/**
 * @author Miguel Pastor
 */
public abstract class BaseListNode extends BaseParentableNode {

	public BaseListNode() {
	}

	public BaseListNode(
		BaseParentableNode baseParentableNode, CollectionNode collectionNode) {

		super(collectionNode);

		_baseParentableNode = baseParentableNode;
	}

	public BaseListNode(int token) {
		super(token);
	}

	public BaseParentableNode getBaseParentableNode() {
		return _baseParentableNode;
	}

	public void setParent(BaseParentableNode baseParentableNode) {
		_baseParentableNode = baseParentableNode;
	}

	private BaseParentableNode _baseParentableNode;

}