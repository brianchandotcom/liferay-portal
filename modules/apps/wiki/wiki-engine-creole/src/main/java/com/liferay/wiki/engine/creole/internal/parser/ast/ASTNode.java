/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast;

import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

/**
 * @author Miguel Pastor
 */
public abstract class ASTNode {

	public ASTNode() {
	}

	public ASTNode(int tokenType) {
		this(new CommonToken(tokenType));
	}

	public ASTNode(Token token) {
		_token = token;
	}

	public abstract void accept(ASTVisitor astVisitor);

	public Token getToken() {
		return _token;
	}

	public void setToken(Token token) {
		_token = token;
	}

	private Token _token;

}