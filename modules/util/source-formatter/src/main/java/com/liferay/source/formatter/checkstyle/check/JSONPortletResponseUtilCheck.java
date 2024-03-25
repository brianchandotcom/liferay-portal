/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JSONPortletResponseUtilCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getParent() != null) {
			return;
		}

		String className = getName(detailAST);

		if (!className.endsWith("MVCActionCommand")) {
			return;
		}

		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, "JSONPortletResponseUtil", "writeJSON");

		outerLoop:
		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			List<DetailAST> exprDetailASTList = getAllChildTokens(
				elistDetailAST, false, TokenTypes.EXPR);

			if (exprDetailASTList.size() != 3) {
				continue;
			}

			DetailAST exprDetailAST = exprDetailASTList.get(2);

			DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			DetailAST parentDetailAST = methodCallDetailAST.getParent();

			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() != TokenTypes.EXPR)) {

				continue;
			}

			DetailAST previousSiblingDetailAST =
				parentDetailAST.getPreviousSibling();

			while (true) {
				if (previousSiblingDetailAST == null) {
					continue outerLoop;
				}

				if (previousSiblingDetailAST.getType() == TokenTypes.SEMI) {
					previousSiblingDetailAST =
						previousSiblingDetailAST.getPreviousSibling();

					continue;
				}

				if (previousSiblingDetailAST.getType() != TokenTypes.EXPR) {
					continue outerLoop;
				}

				String variableName = firstChildDetailAST.getText();

				firstChildDetailAST = previousSiblingDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
					String methodName = getMethodName(firstChildDetailAST);

					if (methodName.equals("hideDefaultSuccessMessage")) {
						break;
					}
				}

				if (_containsVariableName(firstChildDetailAST, variableName)) {
					continue outerLoop;
				}

				previousSiblingDetailAST =
					previousSiblingDetailAST.getPreviousSibling();
			}

			log(methodCallDetailAST, _MSG_MOVE_METHOD_CALL_BEFORE_METHOD_CALL);
		}
	}

	private boolean _containsVariableName(
		DetailAST detailAST, String variableName) {

		List<DetailAST> identDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			if (!variableName.equals(identDetailAST.getText())) {
				continue;
			}

			DetailAST parentDetailAST = identDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
				return false;
			}

			if (!isMethodNameDetailAST(identDetailAST)) {
				return true;
			}
		}

		return false;
	}

	private static final String _MSG_MOVE_METHOD_CALL_BEFORE_METHOD_CALL =
		"method.call.move.before.method.call";

}