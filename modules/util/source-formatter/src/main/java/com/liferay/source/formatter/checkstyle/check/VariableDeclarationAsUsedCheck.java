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

package com.liferay.source.formatter.checkstyle.check;

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

		DetailAST assignDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
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

		int actionLineNumber = getActionLineNumber(variableDefinitionDetailAST);

		if (actionLineNumber != assignDetailAST.getLineNo()) {
			checkMoveAfterBranchingStatement(
				detailAST, assignDetailAST, variableName,
				firstDependentIdentDetailAST, actionLineNumber);
			checkMoveInsideIfStatement(
				assignDetailAST, nameDetailAST, variableName,
				firstDependentIdentDetailAST,
				dependentIdentDetailASTList.get(
					dependentIdentDetailASTList.size() - 1),
				actionLineNumber);
		}

		DetailAST modifiersDetailAST =
			variableDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (!modifiersDetailAST.branchContains(TokenTypes.ANNOTATION)) {
			checkInline(
				assignDetailAST, variableName, firstDependentIdentDetailAST,
				dependentIdentDetailASTList);
		}
	}

}