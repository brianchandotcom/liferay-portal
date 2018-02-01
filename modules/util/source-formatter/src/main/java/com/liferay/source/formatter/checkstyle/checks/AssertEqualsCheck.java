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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class AssertEqualsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "Assert", "assertEquals");

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

			List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
				elistAST, false, TokenTypes.EXPR);

			if (exprASTList.size() != 2) {
				continue;
			}

			DetailAST secondExprAST = exprASTList.get(1);

			DetailAST firstChildAST = secondExprAST.getFirstChild();

			String variableName = _getVariableNameForMethodCall(
				firstChildAST, "getLength");

			if (variableName != null) {
				DetailAST typeAST = DetailASTUtil.getVariableTypeAST(
					methodCallAST, variableName);

				if ((typeAST != null) && _isHits(typeAST)) {
					log(
						methodCallAST.getLineNo(), _MSG_ASSERT_ADD_INFORMATION,
						variableName + ".toString()");
				}

				continue;
			}

			variableName = _getVariableNameForCall(firstChildAST, "length");

			if (variableName != null) {
				DetailAST typeAST = DetailASTUtil.getVariableTypeAST(
					methodCallAST, variableName);

				if ((typeAST != null) && DetailASTUtil.isArray(typeAST)) {
					log(
						methodCallAST.getLineNo(), _MSG_ASSERT_ADD_INFORMATION,
						"Arrays.toString(" + variableName + ")");
				}

				continue;
			}

			variableName = _getVariableNameForMethodCall(firstChildAST, "size");

			if (variableName != null) {
				DetailAST typeAST = DetailASTUtil.getVariableTypeAST(
					methodCallAST, variableName);

				if ((typeAST != null) && DetailASTUtil.isCollection(typeAST)) {
					log(
						methodCallAST.getLineNo(), _MSG_ASSERT_ADD_INFORMATION,
						variableName + ".toString()");
				}
			}
		}
	}

	private String _getVariableNameForCall(
		DetailAST detailAST, String methodName) {

		if (detailAST.getType() != TokenTypes.DOT) {
			return null;
		}

		List<DetailAST> nameASTList = DetailASTUtil.getAllChildTokens(
			detailAST, false, TokenTypes.IDENT);

		if (nameASTList.size() != 2) {
			return null;
		}

		DetailAST methodNameAST = nameASTList.get(1);

		if (!methodName.equals(methodNameAST.getText())) {
			return null;
		}

		DetailAST variableNameAST = nameASTList.get(0);

		return variableNameAST.getText();
	}

	private String _getVariableNameForMethodCall(
		DetailAST detailAST, String methodName) {

		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		DetailAST firstChild = detailAST.getFirstChild();

		return _getVariableNameForCall(firstChild, methodName);
	}

	private boolean _isHits(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		if (name.equals("Hits")) {
			return true;
		}

		return false;
	}

	private static final String _MSG_ASSERT_ADD_INFORMATION =
		"assert.add.information";

}