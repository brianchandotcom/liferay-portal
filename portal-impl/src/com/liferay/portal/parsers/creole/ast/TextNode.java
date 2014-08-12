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

/**
 * @author Miguel Pastor
 */
public abstract class TextNode extends BaseParentableNode {

	public TextNode(ASTNode astNode) {
		super((CollectionNode)astNode);
	}

	public TextNode(int tokenType) {
		super(tokenType);
	}

	public TextNode(String content) {
		_content = content;
	}

	public String getContent() {
		return _content;
	}

	public boolean hasContent() {
		if (_content != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private final String _content;

}