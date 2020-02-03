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

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class UnnecessaryAssignCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();
		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if ((firstChildDetailAST.getType() != TokenTypes.IDENT) ||
			(lastChildDetailAST.getType() == TokenTypes.LITERAL_NULL)) {

			return;
		}

		String variableName = firstChildDetailAST.getText();

		DetailAST variableTypeDetailAST = getVariableTypeDetailAST(
			detailAST, variableName, false);

		if (variableTypeDetailAST == null) {
			return;
		}

		DetailAST variableDefinitionDetailAST =
			variableTypeDetailAST.getParent();

		parentDetailAST = variableDefinitionDetailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.FOR_EACH_CLAUSE) &&
			(parentDetailAST.getType() != TokenTypes.SLIST)) {

			return;
		}

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(
				variableDefinitionDetailAST, variableName);

		if (variableCallerDetailASTList.isEmpty()) {
			log(detailAST, variableName + ": EMPTY");

			return;
		}

		DetailAST lastVariableCallerDetailAST = variableCallerDetailASTList.get(
			variableCallerDetailASTList.size() - 1);

		if (lastVariableCallerDetailAST.getLineNo() > getEndLineNumber(
				detailAST)) {

			return;
		}

		DetailAST ancestorDetailAST = getParentWithTokenType(
			detailAST, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE,
			TokenTypes.OBJBLOCK);

		if (ancestorDetailAST.getLineNo() <=
				variableDefinitionDetailAST.getLineNo()) {

			log(detailAST, variableName);
		}
	}

	private static final String _MSG_UNUSED_METHOD = "method.unused";

}