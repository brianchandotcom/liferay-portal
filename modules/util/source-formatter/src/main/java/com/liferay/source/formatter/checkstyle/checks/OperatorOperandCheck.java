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

		if ((detailAST == null) ||
			(detailAST.getType() != TokenTypes.METHOD_CALL)) {

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