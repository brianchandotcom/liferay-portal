/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ArrayUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class OperatorOperandCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return ArrayUtil.append(
			ARITHMETIC_OPERATOR_TOKEN_TYPES, RELATIONAL_OPERATOR_TOKEN_TYPES);
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (_isInsideGlobalVariableDefinition(detailAST)) {
			return;
		}

		_checkOperand(detailAST, detailAST.getFirstChild(), "left");

		if (ArrayUtil.contains(
				ARITHMETIC_OPERATOR_TOKEN_TYPES, detailAST.getType())) {

			_checkOperand(detailAST, detailAST.getLastChild(), "right");
		}
	}

	private void _checkOperand(
		DetailAST operatorDetailAST, DetailAST detailAST, String side) {

		if (detailAST == null) {
			return;
		}

		DetailAST exprDetailAST = getParentWithTokenType(
			detailAST, TokenTypes.EXPR);

		if (exprDetailAST != null) {
			DetailAST parentDetailAST = exprDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.LITERAL_WHILE) {
				return;
			}
		}

		if (detailAST.getType() == TokenTypes.LPAREN) {
			DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if ((nextSiblingDetailAST.getType() == TokenTypes.RPAREN) &&
				(detailAST.getLineNo() != nextSiblingDetailAST.getLineNo())) {

				log(
					detailAST, _MSG_IMPROVE_READABILITY, side,
					operatorDetailAST.getText());
			}

			return;
		}

		if (detailAST.getType() == TokenTypes.RPAREN) {
			DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

			previousSiblingDetailAST =
				previousSiblingDetailAST.getPreviousSibling();

			if ((previousSiblingDetailAST.getType() == TokenTypes.LPAREN) &&
				(detailAST.getLineNo() !=
					previousSiblingDetailAST.getLineNo())) {

				log(
					detailAST, _MSG_IMPROVE_READABILITY, side,
					operatorDetailAST.getText());
			}

			return;
		}

		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		if (isAtLineEnd(detailAST, getLine(detailAST.getLineNo() - 1))) {
			log(
				detailAST, _MSG_IMPROVE_READABILITY, side,
				operatorDetailAST.getText());

			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if ((firstChildDetailAST.getType() == TokenTypes.DOT) &&
			isAtLineEnd(
				firstChildDetailAST,
				getLine(firstChildDetailAST.getLineNo() - 1))) {

			log(
				firstChildDetailAST, _MSG_IMPROVE_READABILITY, side,
				operatorDetailAST.getText());
		}
	}

	private boolean _isInsideGlobalVariableDefinition(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if ((parentDetailAST.getType() == TokenTypes.CLASS_DEF) ||
				(parentDetailAST.getType() == TokenTypes.ENUM_DEF) ||
				(parentDetailAST.getType() == TokenTypes.INTERFACE_DEF)) {

				return true;
			}

			if ((parentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentDetailAST.getType() == TokenTypes.METHOD_DEF)) {

				return false;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private static final String _MSG_IMPROVE_READABILITY =
		"readability.improve";

}