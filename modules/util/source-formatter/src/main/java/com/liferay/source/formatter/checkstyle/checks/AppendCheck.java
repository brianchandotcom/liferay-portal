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
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class AppendCheck extends StringConcatenationCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "append");

		boolean previousParameterIsLiteralString = false;

		for (int i = 0; i < methodCallASTList.size(); i++) {
			DetailAST methodCallAST = methodCallASTList.get(i);

			String variableName = _getVariableName(methodCallAST);

			String variableTypeName = DetailASTUtil.getVariableTypeName(
				methodCallAST, variableName);

			if (!variableTypeName.equals("StringBundler")) {
				continue;
			}

			DetailAST parameterDetailAST = _getParameterDetailAST(
				methodCallAST);

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

			DetailAST previousMethodCallAST = methodCallASTList.get(i - 1);

			if (!variableName.equals(_getVariableName(previousMethodCallAST))) {
				continue;
			}

			if (_containsMethodCall(
					detailAST, variableName, "setIndex", "setStringAt")) {

				continue;
			}

			DetailAST previousParameterDetailAST = _getParameterDetailAST(
				previousMethodCallAST);

			if ((previousParameterDetailAST != null) &&
				(previousParameterDetailAST.getType() ==
					TokenTypes.STRING_LITERAL)) {

				_checkLiteralStrings(
					methodCallAST, previousMethodCallAST,
					parameterDetailAST.getText(),
					previousParameterDetailAST.getText());
			}
		}
	}

	private void _checkLiteralStrings(
		DetailAST methodCallAST, DetailAST previousMethodCallAST,
		String literalStringValue, String previousLiteralStringValue) {

		if (DetailASTUtil.getEndLine(previousMethodCallAST) !=
				(methodCallAST.getLineNo() - 1)) {

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
			previousMethodCallAST.getLineNo());

		if (_hasIncorrectLineBreaks(methodCallAST) |
			_hasIncorrectLineBreaks(previousMethodCallAST)) {

			return;
		}

		if (literalStringValue.startsWith("<") ||
			literalStringValue.endsWith(">") ||
			previousLiteralStringValue.startsWith("<") ||
			previousLiteralStringValue.endsWith(">")) {

			return;
		}

		String previousLine = getLine(previousMethodCallAST.getLineNo() - 1);

		int previousLineLength = CommonUtils.lengthExpandedTabs(
			previousLine, previousLine.length(), getTabWidth());

		if ((previousLineLength + literalStringValue.length()) <=
				maxLineLength) {

			log(
				methodCallAST.getLineNo(), MSG_COMBINE_LITERAL_STRINGS,
				previousLiteralStringValue, literalStringValue);
		}
		else {
			int pos = getStringBreakPos(
				previousLiteralStringValue, literalStringValue,
				maxLineLength - previousLineLength);

			if (pos != -1) {
				log(
					methodCallAST.getLineNo(), MSG_MOVE_LITERAL_STRING,
					literalStringValue.substring(0, pos + 1));
			}
		}
	}

	private void _checkPlusOperator(DetailAST parameterDetailAST) {
		if (parameterDetailAST.getType() != TokenTypes.PLUS) {
			return;
		}

		List<DetailAST> literalStringASTList = DetailASTUtil.getAllChildTokens(
			parameterDetailAST, true, TokenTypes.STRING_LITERAL);

		if (!literalStringASTList.isEmpty()) {
			log(parameterDetailAST.getLineNo(), _MSG_INCORRECT_PLUS);
		}
	}

	private boolean _containsMethodCall(
		DetailAST detailAST, String variableName, String... methodNames) {

		for (String methodName : methodNames) {
			List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
				detailAST, variableName, methodName);

			if (!methodCallASTList.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private DetailAST _getParameterDetailAST(DetailAST methodCallAST) {
		DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

		DetailAST exprAST = elistAST.findFirstToken(TokenTypes.EXPR);

		if (exprAST == null) {
			return null;
		}

		return exprAST.getFirstChild();
	}

	private String _getVariableName(DetailAST methodCallAST) {
		DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

		if (dotAST == null) {
			return null;
		}

		DetailAST nameAST = dotAST.findFirstToken(TokenTypes.IDENT);

		if (nameAST == null) {
			return null;
		}

		return nameAST.getText();
	}

	private boolean _hasIncorrectLineBreaks(DetailAST methodCallAST) {
		if (DetailASTUtil.getStartLine(methodCallAST) !=
				DetailASTUtil.getEndLine(methodCallAST)) {

			log(methodCallAST.getLineNo(), _MSG_INCORRECT_LINE_BREAK);

			return true;
		}

		return false;
	}

	private static final String _MSG_INCORRECT_LINE_BREAK =
		"line.break.incorrect";

	private static final String _MSG_INCORRECT_PLUS = "plus.incorrect";

}