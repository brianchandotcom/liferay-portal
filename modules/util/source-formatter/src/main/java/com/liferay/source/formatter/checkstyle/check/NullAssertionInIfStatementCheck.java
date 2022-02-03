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

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class NullAssertionInIfStatementCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_IF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		DetailAST exprDetailAST = firstChildDetailAST.getNextSibling();

		List<DetailAST> compareDetailASTList = getAllChildTokens(
			exprDetailAST, true, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL);

		for (DetailAST compareDetailAST : compareDetailASTList) {
			DetailAST identDetailAST = compareDetailAST.getFirstChild();

			if ((identDetailAST == null) ||
				(identDetailAST.getType() != TokenTypes.IDENT)) {

				continue;
			}

			DetailAST nextSiblingDetailAST = identDetailAST.getNextSibling();

			if (nextSiblingDetailAST.getType() != TokenTypes.LITERAL_NULL) {
				continue;
			}

			DetailAST parentDetailAST = compareDetailAST.getParent();

			List<DetailAST> methodCallDetailASTList = getAllChildTokens(
				parentDetailAST, true, TokenTypes.METHOD_CALL);

			if (methodCallDetailASTList.isEmpty()) {
				continue;
			}

			int compareColumnNumber = compareDetailAST.getColumnNo();
			int compareStartLineNumber = getStartLineNumber(compareDetailAST);

			String variableName = identDetailAST.getText();

			for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
				if (!variableName.equals(
						getVariableName(methodCallDetailAST))) {

					continue;
				}

				int methodCallColumnNumber = methodCallDetailAST.getColumnNo();
				int methodCallStartLineNumber = getStartLineNumber(
					methodCallDetailAST);

				if ((compareStartLineNumber == methodCallStartLineNumber) &&
					(compareColumnNumber > methodCallColumnNumber)) {

					log(
						nextSiblingDetailAST, _MSG_MOVE_NULL_CHECK,
						variableName);
				}

				if (compareStartLineNumber > methodCallStartLineNumber) {
					log(
						nextSiblingDetailAST, _MSG_MOVE_NULL_CHECK,
						variableName);
				}
			}
		}
	}

	private static final String _MSG_MOVE_NULL_CHECK = "null.check.move";

}