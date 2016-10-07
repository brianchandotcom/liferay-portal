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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class NotRequireThisCheck extends AbstractCheck {

	public static final String MSG_VARIABLE_THIS_NOT_REQUIRED =
		"variable.not.require.this";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		List<DetailAST> thisASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_THIS);

		outerLoop:
		for (DetailAST thisAST : thisASTList) {
			if (_isMethodCall(thisAST) ||
				(thisAST.getPreviousSibling() != null)) {

				continue;
			}

			DetailAST parentAST = thisAST.getParent();

			if (parentAST.getType() != TokenTypes.DOT) {
				continue;
			}

			DetailAST nameAST = parentAST.findFirstToken(TokenTypes.IDENT);

			String name = nameAST.getText();

			List<DetailAST> definitionASTList = DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.PARAMETER_DEF,
				TokenTypes.VARIABLE_DEF);

			for (DetailAST definitionAST : definitionASTList) {
				DetailAST definitionNameAST = definitionAST.findFirstToken(
					TokenTypes.IDENT);

				if (name.equals(definitionNameAST.getText())) {
					continue outerLoop;
				}
			}

			log(thisAST.getLineNo(), MSG_VARIABLE_THIS_NOT_REQUIRED, name);
		}
	}

	private boolean _isMethodCall(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST.getType() == TokenTypes.METHOD_CALL) {
				return true;
			}

			if (parentAST.getType() != TokenTypes.DOT) {
				return false;
			}

			parentAST = parentAST.getParent();
		}
	}

}