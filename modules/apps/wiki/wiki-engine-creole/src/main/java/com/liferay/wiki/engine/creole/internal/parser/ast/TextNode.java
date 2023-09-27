/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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