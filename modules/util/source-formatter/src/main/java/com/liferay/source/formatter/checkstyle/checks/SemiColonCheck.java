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

package com.liferay.source.formatter.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class SemiColonCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.EMPTY_STAT, TokenTypes.SEMI};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.EMPTY_STAT) {
			DetailAST parentDetailAST = detailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.LITERAL_WHILE) {
				log(detailAST, _MSG_UNNECESSARY_SEMI_COLON);
			}

			return;
		}

		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		if (previousSiblingDetailAST == null) {
			return;
		}

		if ((previousSiblingDetailAST.getType() == TokenTypes.CLASS_DEF) ||
			(previousSiblingDetailAST.getType() == TokenTypes.CTOR_DEF) ||
			(previousSiblingDetailAST.getType() == TokenTypes.ENUM_DEF) ||
			(previousSiblingDetailAST.getType() == TokenTypes.INTERFACE_DEF) ||
			(previousSiblingDetailAST.getType() == TokenTypes.METHOD_DEF) ||
			(previousSiblingDetailAST.getType() == TokenTypes.STATIC_INIT)) {

			log(detailAST, _MSG_UNNECESSARY_SEMI_COLON);
		}
		else if (previousSiblingDetailAST.getType() ==
					TokenTypes.ENUM_CONSTANT_DEF) {

			DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

			if ((nextSiblingDetailAST != null) &&
				(nextSiblingDetailAST.getType() == TokenTypes.RCURLY)) {

				log(detailAST, _MSG_UNNECESSARY_SEMI_COLON);
			}
		}
	}

	private static final String _MSG_UNNECESSARY_SEMI_COLON =
		"semi.colon.unnecessary";

}