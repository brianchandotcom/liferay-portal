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
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class OperatorOperandCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return ArrayUtil.append(
			_ARITHMETIC_OPERATOR_TOKEN_TYPES, _RELATIONAL_OPERATOR_TOKEN_TYPES);
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (_isInsideGlobalVariableDefinition(detailAST)) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if ((firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) &&
			DetailASTUtil.isAtLineEnd(
				firstChildDetailAST,
				getLine(firstChildDetailAST.getLineNo() - 1))) {

			log(
				firstChildDetailAST, _MSG_IMPROVE_READABILITY, "left",
				detailAST.getText());
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if ((lastChildDetailAST.getType() == TokenTypes.METHOD_CALL) &&
			ArrayUtil.contains(
				_ARITHMETIC_OPERATOR_TOKEN_TYPES, detailAST.getType()) &&
			DetailASTUtil.isAtLineEnd(
				lastChildDetailAST,
				getLine(lastChildDetailAST.getLineNo() - 1))) {

			log(
				lastChildDetailAST, _MSG_IMPROVE_READABILITY, "right",
				detailAST.getText());
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

	private static final int[] _ARITHMETIC_OPERATOR_TOKEN_TYPES = {
		TokenTypes.DIV, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.PLUS,
		TokenTypes.STAR
	};

	private static final String _MSG_IMPROVE_READABILITY =
		"readability.improve";

	private static final int[] _RELATIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
		TokenTypes.LT, TokenTypes.NOT_EQUAL
	};

}