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
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JSONUtilCheck extends BaseChainedMethodCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN, TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			_checkChainedPutCalls(detailAST);

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.EXPR) &&
			(parentDetailAST.getType() != TokenTypes.VARIABLE_DEF)) {

			return;
		}

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		DetailAST methodCallDetailAST = _getMethodCallDetailAST(
			detailAST, parentDetailAST);

		if (methodCallDetailAST == null) {
			return;
		}

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST.getChildCount() > 0) {
			return;
		}

		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		FullIdent fullIdent1 = FullIdent.createFullIdent(firstChildDetailAST);

		String methodName = fullIdent1.getText();

		if (!methodName.equals("JSONFactoryUtil.createJSONArray") &&
			!methodName.equals("JSONFactoryUtil.createJSONObject")) {

			return;
		}

		String variableName = getVariableName(detailAST, parentDetailAST);

		if (variableName == null) {
			return;
		}

		while (true) {
			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if (nextSiblingDetailAST == null) {
				return;
			}

			FullIdent fullIdent2 = getMethodCallFullIdent(
				nextSiblingDetailAST, variableName, "put");

			if (fullIdent2 != null) {
				log(
					detailAST, _MSG_USE_JSON_UTIL_PUT, methodName,
					fullIdent1.getLineNo(), variableName + ".put",
					fullIdent2.getLineNo(), "JSONUtil.put");
			}

			if (containsVariableName(nextSiblingDetailAST, variableName)) {
				return;
			}
		}
	}

	private void _checkChainedPutCalls(DetailAST methodCallDetailAST) {
		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		if (!Objects.equals(fullIdent.getText(), "JSONUtil.put")) {
			return;
		}

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST.getChildCount() != 1) {
			return;
		}

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		DetailAST nextSiblingDetailAST = methodCallDetailAST.getNextSibling();

		if ((nextSiblingDetailAST.getType() == TokenTypes.IDENT) &&
			Objects.equals(nextSiblingDetailAST.getText(), "put")) {

			log(methodCallDetailAST, _MSG_USE_JSON_UTIL_PUT_ALL);
		}
	}

	private DetailAST _getMethodCallDetailAST(
		DetailAST assignDetailAST, DetailAST parentDetailAST) {

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		DetailAST assignValueDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			assignValueDetailAST = firstChildDetailAST.getNextSibling();
		}
		else {
			assignValueDetailAST = firstChildDetailAST.getFirstChild();
		}

		if ((assignValueDetailAST != null) &&
			(assignValueDetailAST.getType() == TokenTypes.METHOD_CALL)) {

			return assignValueDetailAST;
		}

		return null;
	}

	private static final String _MSG_USE_JSON_UTIL_PUT = "json.util.put.use";

	private static final String _MSG_USE_JSON_UTIL_PUT_ALL =
		"json.util.put.all.use";

}