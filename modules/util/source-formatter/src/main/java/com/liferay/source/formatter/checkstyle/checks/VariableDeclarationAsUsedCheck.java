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
public class VariableDeclarationAsUsedCheck extends BaseAsUsedCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> variableDefinitionDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			_checkVariableDefinition(detailAST, variableDefinitionDetailAST);
		}
	}

	private void _checkVariableDefinition(
		DetailAST detailAST, DetailAST variableDefinitionDetailAST) {

		if (hasParentWithTokenType(
				variableDefinitionDetailAST, TokenTypes.FOR_EACH_CLAUSE,
				TokenTypes.FOR_INIT)) {

			return;
		}

		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		List<DetailAST> dependentIdentDetailASTList =
			getDependentIdentDetailASTList(
				variableDefinitionDetailAST,
				variableDefinitionDetailAST.getLineNo());

		if (dependentIdentDetailASTList.isEmpty()) {
			return;
		}

		String variableName = nameDetailAST.getText();

		DetailAST firstDependentIdentDetailAST =
			dependentIdentDetailASTList.get(0);

		if (checkMoveStatement(variableDefinitionDetailAST)) {
			checkMoveAfterBranchingStatement(
				detailAST, variableDefinitionDetailAST, variableName,
				firstDependentIdentDetailAST);
			checkMoveInsideIfStatement(
				variableDefinitionDetailAST, nameDetailAST, variableName,
				dependentIdentDetailASTList);
		}

		checkInline(
			variableDefinitionDetailAST, variableName,
			_getAssignMethodCallDetailAST(variableDefinitionDetailAST),
			firstDependentIdentDetailAST, dependentIdentDetailASTList);
	}

	private DetailAST _getAssignMethodCallDetailAST(
		DetailAST variableDefinitionDetailAST) {

		DetailAST assignDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return null;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST != null) &&
			(firstChildDetailAST.getType() == TokenTypes.METHOD_CALL)) {

			return firstChildDetailAST;
		}

		return null;
	}

}