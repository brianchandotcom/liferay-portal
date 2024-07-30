/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class ReturnVariableDeclarationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
			objBlockDetailAST, false, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			DetailAST modifiersDetailAST =
				methodDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

			if (modifiersDetailAST.branchContains(TokenTypes.ABSTRACT)) {
				continue;
			}

			DetailAST typeDetailAST = methodDefinitionDetailAST.findFirstToken(
				TokenTypes.TYPE);

			String returnTypeName = getTypeName(typeDetailAST, false);

			if (returnTypeName.equals("void")) {
				continue;
			}

			String methodName = getName(methodDefinitionDetailAST);

			if (!methodName.matches("_?get[A-Z].+")) {
				continue;
			}

			DetailAST slistDetailAST = methodDefinitionDetailAST.findFirstToken(
				TokenTypes.SLIST);

			if (slistDetailAST == null) {
				continue;
			}

			DetailAST returnIdentDetailAST = _getReturnIdentDetailAST(
				slistDetailAST);

			if (returnIdentDetailAST == null) {
				continue;
			}

			String variableName = returnIdentDetailAST.getText();

			if (!StringUtil.equalsIgnoreCase(
					methodName.replaceFirst("_?get(.+)", "$1"), variableName)) {

				continue;
			}

			DetailAST returnVariableDefinitionDetailAST =
				getVariableDefinitionDetailAST(
					returnIdentDetailAST, variableName, false);

			if ((returnVariableDefinitionDetailAST == null) ||
				(returnVariableDefinitionDetailAST.getType() ==
					TokenTypes.PARAMETER_DEF) ||
				equals(
					slistDetailAST.getFirstChild(),
					returnVariableDefinitionDetailAST)) {

				continue;
			}

			_checkMoveVariableDeclaration(
				returnVariableDefinitionDetailAST, slistDetailAST,
				variableName);
		}
	}

	private void _checkMoveVariableDeclaration(
		DetailAST returnVariableDefinitionDetailAST, DetailAST slistDetailAST,
		String variableName) {

		DetailAST assignDetailAST =
			returnVariableDefinitionDetailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			log(
				returnVariableDefinitionDetailAST,
				_MSG_MOVE_VARIABLE_DECLARATION, variableName,
				getStartLineNumber(slistDetailAST.getFirstChild()));

			return;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NULL) {
			log(
				returnVariableDefinitionDetailAST,
				_MSG_MOVE_VARIABLE_DECLARATION, variableName,
				getStartLineNumber(slistDetailAST.getFirstChild()));

			return;
		}

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST.getChildCount() != 0) {
			return;
		}

		DetailAST objBlockDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		if (objBlockDetailAST != null) {
			return;
		}

		List<DetailAST> childDetailASTList = getAllChildTokens(
			slistDetailAST, true, TokenTypes.LITERAL_RETURN,
			TokenTypes.LITERAL_THROW);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (childDetailAST.getLineNo() <
					returnVariableDefinitionDetailAST.getLineNo()) {

				return;
			}
		}

		log(
			returnVariableDefinitionDetailAST, _MSG_MOVE_VARIABLE_DECLARATION,
			variableName, getStartLineNumber(slistDetailAST.getFirstChild()));
	}

	private DetailAST _getReturnIdentDetailAST(DetailAST detailAST) {
		DetailAST lastChildDetailAST = detailAST.getLastChild();

		DetailAST previousSiblingDetailAST =
			lastChildDetailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.LITERAL_RETURN)) {

			return null;
		}

		DetailAST firstChildDetailAST =
			previousSiblingDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.IDENT)) {

			return null;
		}

		return firstChildDetailAST;
	}

	private static final String _MSG_MOVE_VARIABLE_DECLARATION =
		"variable.declaration.move";

}