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
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class IndentationCheck extends AbstractCheck {

	public static final String MSG_INCORRECT_INDENTATION =
		"indentation.incorrect";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String line = fileContents.getLine(detailAST.getLineNo() - 1);

		int expectedTabCount = _getExpectedTabCount(detailAST);

		if (expectedTabCount == -1) {
			return;
		}

		int tabCount = _getLeadingTabCount(line);

		if (tabCount != expectedTabCount) {
			log(
				detailAST.getLineNo(), MSG_INCORRECT_INDENTATION, tabCount,
				expectedTabCount);
		}
	}

	private int _adjustTabCountForAnonymousClass(
		DetailAST literalNewAST, int tabCount) {

		DetailAST objblockAST = literalNewAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		if (objblockAST == null) {
			return tabCount;
		}

		DetailAST parentAST = _findParent(
			literalNewAST, TokenTypes.LITERAL_RETURN);

		if (parentAST == null) {
			parentAST = _findParent(literalNewAST, TokenTypes.VARIABLE_DEF);
		}

		if (parentAST == null) {
			parentAST = _findParent(literalNewAST, TokenTypes.ASSIGN);

			if (parentAST != null) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.RESOURCE) {
					parentAST = parentAST.getParent();
				}
			}
		}

		if (parentAST == null) {
			parentAST = _findParent(literalNewAST, TokenTypes.METHOD_CALL);
		}

		if (parentAST == null) {
			parentAST = _findParent(literalNewAST, TokenTypes.LITERAL_NEW);
		}

		if (parentAST == null) {
			return tabCount;
		}

		int literalNewLineNumber = literalNewAST.getLineNo();
		int parentLineNumber = DetailASTUtil.getStartLine(parentAST);

		if (literalNewLineNumber == parentLineNumber) {
			if (parentAST.getType() == TokenTypes.RESOURCES) {
				return tabCount + 2;
			}

			return tabCount;
		}

		FileContents fileContents = getFileContents();

		String literalNewLine = fileContents.getLine(literalNewLineNumber - 1);
		String parentLine = fileContents.getLine(parentLineNumber - 1);

		int literalNewLineTabCount = _getLeadingTabCount(literalNewLine);
		int parentLineTabCount = _getLeadingTabCount(parentLine);

		return tabCount + literalNewLineTabCount - parentLineTabCount;
	}

	private DetailAST _findParent(DetailAST detailAST, int type) {
		DetailAST match = null;

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return match;
			}

			if (parentAST.getType() == type) {
				match = parentAST;
			}

			if (parentAST.getType() == TokenTypes.OBJBLOCK) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LITERAL_NEW) {
					return match;
				}

				continue;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _getExpectedTabCount(DetailAST detailAST) {
		int expectedTabCount = 0;

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return expectedTabCount;
			}

			if (parentAST.getType() == TokenTypes.LAMBDA) {
				return -1;
			}

			if (parentAST.getType() == TokenTypes.LITERAL_NEW) {
				expectedTabCount = _adjustTabCountForAnonymousClass(
					parentAST, expectedTabCount);
			}

			if ((parentAST.getType() == TokenTypes.LITERAL_SWITCH) ||
				(parentAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				expectedTabCount++;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

}