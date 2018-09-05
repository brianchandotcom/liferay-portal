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
public abstract class TextNode extends BaseParentableNode {

	public TextNode(ASTNode astNode) {
		this(astNode, 0, null);
	}

	public TextNode(int tokenType) {
		this(null, tokenType, null);
	}

	public TextNode(String content) {
		this(null, 0, content);
	}

	public String getContent() {
		return _content;
	}

	public boolean hasContent() {
		if (_content != null) {
			return true;
		}

		return false;
	}

	protected TextNode(ASTNode astNode, int tokenType, String content) {
		super((CollectionNode)astNode, tokenType);

		_content = content;
	}

	private final String _content;

}