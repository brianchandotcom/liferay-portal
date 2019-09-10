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
public class AnonymousClassCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> addBackgroundTaskMethodCallDetailASTList =
			DetailASTUtil.getMethodCalls(detailAST, "addBackgroundTask");

		for (DetailAST addBackgroundTaskMethodCallDetailAST :
				addBackgroundTaskMethodCallDetailASTList) {

			_checkAddBackgroundTaskMethodCall(
				addBackgroundTaskMethodCallDetailAST);
		}
	}

	private void _checkAddBackgroundTaskMethodCall(
		DetailAST methodCallDetailAST) {

		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String variableName = firstChildDetailAST.getText();

		String typeName = DetailASTUtil.getVariableTypeName(
			methodCallDetailAST, variableName, false);

		if ((typeName == null) || !typeName.equals("BackgroundTaskManager")) {
			return;
		}

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = DetailASTUtil.getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		if (exprDetailASTList.size() != 6) {
			return;
		}

		DetailAST exprDetailAST = exprDetailASTList.get(4);

		firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String mapVariableName = firstChildDetailAST.getText();

		DetailAST typeDetailAST = DetailASTUtil.getVariableTypeDetailAST(
			methodCallDetailAST, mapVariableName);

		if (typeDetailAST == null) {
			return;
		}

		DetailAST parentDetailAST = typeDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		DetailAST assignDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		if (firstChildDetailAST.findFirstToken(TokenTypes.OBJBLOCK) != null) {
			log(
				assignDetailAST, _MSG_INCORRECT_ANONYMOUS_CLASS,
				mapVariableName, variableName + ".addBackgroundTask");
		}
	}

	private static final String _MSG_INCORRECT_ANONYMOUS_CLASS =
		"anonymous.class.incorrect";

}