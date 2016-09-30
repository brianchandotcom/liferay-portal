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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class PlusStatementCheck extends AbstractCheck {

	public static final String MSG_INVALID_END_CHARACTER =
		"end.character.invalid";

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

		if (literalString1.endsWith(StringPool.SLASH)) {
			log(
				detailAST.getLineNo(), MSG_INVALID_END_CHARACTER,
				literalString1.charAt(literalString1.length() - 1));
		}

		if (literalString2.startsWith(StringPool.SPACE) ||
			(!literalString1.endsWith(StringPool.SPACE) &&
			 literalString2.matches("^[-:;.].*"))) {

			log(
				detailAST.getLineNo() + 1, MSG_INVALID_START_CHARACTER,
				literalString2.charAt(0));
		}
	}

	private String _getLiteralString(DetailAST detailAST) {
		String literalString = null;

		if (detailAST.getType() == TokenTypes.STRING_LITERAL) {
			literalString = detailAST.getText();
		}
		else if ((detailAST.getType() == TokenTypes.PLUS) &&
				 (detailAST.getChildCount() == 2)) {

			DetailAST lastChild = detailAST.getLastChild();

			if (lastChild.getType() == TokenTypes.STRING_LITERAL) {
				literalString = lastChild.getText();
			}
		}

		if (literalString != null) {
			return literalString.substring(1, literalString.length() - 1);
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