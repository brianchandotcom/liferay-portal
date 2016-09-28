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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class PlusStatementCheck extends AbstractCheck {

	public static final String MSG_INVALID_START_CHARACTER =
		"start.character.invalid";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PLUS};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (detailAST.getChildCount() != 2) {
			return;
		}

		String literalString1 = _getLiteralString(detailAST.getFirstChild());

		if (literalString1 == null) {
			return;
		}

		String literalString2 = _getLiteralString(detailAST.getLastChild());

		if (literalString2 == null) {
			return;
		}

		if (_isRegexPattern(detailAST)) {
			return;
		}

		char c1 = literalString1.charAt(literalString1.length() - 2);
		char c2 = literalString2.charAt(1);

		if ((c2 == CharPool.SPACE) ||
			((c1 != CharPool.SPACE) &&
			 ((c2 == CharPool.COLON) || (c2 == CharPool.DASH) ||
			  (c2 == CharPool.PERIOD) || (c2 == CharPool.SEMICOLON)))) {

			log(detailAST.getLineNo() + 1, MSG_INVALID_START_CHARACTER, c2);
		}
	}

	private String _getLiteralString(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.STRING_LITERAL) {
			return detailAST.getText();
		}

		if ((detailAST.getType() == TokenTypes.PLUS) &&
			(detailAST.getChildCount() == 2)) {

			DetailAST lastChild = detailAST.getLastChild();

			if (lastChild.getType() == TokenTypes.STRING_LITERAL) {
				return lastChild.getText();
			}
		}

		return null;
	}

	private boolean _isRegexPattern(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (parentAST != null) {
			if (parentAST.getType() != TokenTypes.METHOD_CALL) {
				parentAST = parentAST.getParent();

				continue;
			}

			DetailAST firstChild = parentAST.getFirstChild();

			if (firstChild.getType() != TokenTypes.DOT) {
				return false;
			}

			List<DetailAST> nameASTList = DetailASTUtil.getAllChildTokens(
				firstChild, TokenTypes.IDENT, false);

			if (nameASTList.size() != 2) {
				return false;
			}

			DetailAST classNameAST = nameASTList.get(0);
			DetailAST methodNameAST = nameASTList.get(1);

			String methodCallClassName = classNameAST.getText();
			String methodCallMethodName = methodNameAST.getText();

			if (methodCallClassName.equals("Pattern") &&
				methodCallMethodName.equals("compile")) {

				return true;
			}

			return false;
		}

		return false;
	}

}