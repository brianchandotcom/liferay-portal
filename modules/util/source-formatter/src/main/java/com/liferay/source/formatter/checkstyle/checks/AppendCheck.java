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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class AppendCheck extends BaseStringConcatenationCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, "append");

		boolean previousParameterIsLiteralString = false;

		for (int i = 0; i < methodCallDetailASTList.size(); i++) {
			DetailAST methodCallDetailAST = methodCallDetailASTList.get(i);

			String variableName = getVariableName(methodCallDetailAST);

			String variableTypeName = getVariableTypeName(
				methodCallDetailAST, variableName, false);

			if (!variableTypeName.equals("StringBundler")) {
				continue;
			}

			DetailAST parameterDetailAST = _getParameterDetailAST(
				methodCallDetailAST);

			if (parameterDetailAST == null) {
				continue;
			}

			_checkPlusOperator(parameterDetailAST);

			if (parameterDetailAST.getType() != TokenTypes.STRING_LITERAL) {
				previousParameterIsLiteralString = false;

				continue;
			}
			else if (!previousParameterIsLiteralString) {
				previousParameterIsLiteralString = true;

				continue;
			}

			DetailAST previousMethodCallDetailAST = methodCallDetailASTList.get(
				i - 1);

			if (!variableName.equals(
					getVariableName(previousMethodCallDetailAST))) {

				continue;
			}

			if (_containsMethodCall(
					detailAST, variableName, "setIndex", "setStringAt")) {

				continue;
			}

			DetailAST previousParameterDetailAST = _getParameterDetailAST(
				previousMethodCallDetailAST);

			if ((previousParameterDetailAST != null) &&
				(previousParameterDetailAST.getType() ==
					TokenTypes.STRING_LITERAL)) {

				_checkLiteralStrings(
					methodCallDetailAST, previousMethodCallDetailAST,
					parameterDetailAST.getText(),
					previousParameterDetailAST.getText());
			}
		}
	}

	private void _checkLiteralStrings(
		DetailAST methodCallDetailAST, DetailAST previousMethodCallDetailAST,
		String literalStringValue, String previousLiteralStringValue) {

		if (getEndLineNumber(previousMethodCallDetailAST) !=
				(methodCallDetailAST.getLineNo() - 1)) {

			return;
		}

		previousLiteralStringValue = previousLiteralStringValue.substring(
			1, previousLiteralStringValue.length() - 1);

		if (previousLiteralStringValue.endsWith("\\n")) {
			return;
		}

		literalStringValue = literalStringValue.substring(
			1, literalStringValue.length() - 1);

		checkLiteralStringStartAndEndCharacter(
			previousLiteralStringValue, literalStringValue,
			previousMethodCallDetailAST.getLineNo());

		if (_hasIncorrectLineBreaks(methodCallDetailAST) |
			_hasIncorrectLineBreaks(previousMethodCallDetailAST)) {

			return;
		}

		if (literalStringValue.startsWith("<") ||
			literalStringValue.endsWith(">") ||
			previousLiteralStringValue.startsWith("<") ||
			previousLiteralStringValue.endsWith(">")) {

			return;
		}

		String previousLine = getLine(
			previousMethodCallDetailAST.getLineNo() - 1);

		int previousLineLength = CommonUtil.lengthExpandedTabs(
			previousLine, previousLine.length(), getTabWidth());

		if ((previousLineLength + literalStringValue.length()) <=
				getMaxLineLength()) {

			log(
				methodCallDetailAST, MSG_COMBINE_LITERAL_STRINGS,
				previousLiteralStringValue, literalStringValue);
		}
		else {
			int pos = getStringBreakPos(
				previousLiteralStringValue, literalStringValue,
				getMaxLineLength() - previousLineLength);

			if (pos != -1) {
				log(
					methodCallDetailAST, MSG_MOVE_LITERAL_STRING,
					literalStringValue.substring(0, pos + 1));
			}
		}
	}

	private void _checkPlusOperator(DetailAST parameterDetailAST) {
		if (parameterDetailAST.getType() != TokenTypes.PLUS) {
			return;
		}

		List<DetailAST> literalStringDetailASTList = getAllChildTokens(
			parameterDetailAST, true, TokenTypes.STRING_LITERAL);

		if (!literalStringDetailASTList.isEmpty()) {
			log(parameterDetailAST, MSG_INCORRECT_PLUS);
		}
	}

	private boolean _containsMethodCall(
		DetailAST detailAST, String variableName, String... methodNames) {

		for (String methodName : methodNames) {
			List<DetailAST> methodCallDetailASTList = getMethodCalls(
				detailAST, variableName, methodName);

			if (!methodCallDetailASTList.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private DetailAST _getParameterDetailAST(DetailAST methodCallDetailAST) {
		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST exprDetailAST = elistDetailAST.findFirstToken(
			TokenTypes.EXPR);

		if (exprDetailAST == null) {
			return null;
		}

		return exprDetailAST.getFirstChild();
	}

	private boolean _hasIncorrectLineBreaks(DetailAST methodCallDetailAST) {
		if (getStartLineNumber(methodCallDetailAST) != getEndLineNumber(
				methodCallDetailAST)) {

			log(methodCallDetailAST, _MSG_INCORRECT_LINE_BREAK);

			return true;
		}

		return false;
	}

	private static final String _MSG_INCORRECT_LINE_BREAK =
		"line.break.incorrect";

}