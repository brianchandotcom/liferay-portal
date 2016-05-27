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

package com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki;

import com.liferay.wiki.engine.creole.internal.parser.ast.link.LinkNode;
import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */
public abstract class InterwikiLinkNode extends LinkNode {

	public InterwikiLinkNode() {
	}

	public InterwikiLinkNode(int token) {
		super(token);
	}

	public InterwikiLinkNode(int token, String title) {
		this(token);

		_title = title;
	}

	public InterwikiLinkNode(String title) {
		_title = title;
	}

	@Override
	public abstract void accept(ASTVisitor astVisitor);

	public String getTitle() {
		return _title;
	}

	public String getURL() {
		return getBaseURL() + _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	protected abstract String getBaseURL();

	private String _title;

}