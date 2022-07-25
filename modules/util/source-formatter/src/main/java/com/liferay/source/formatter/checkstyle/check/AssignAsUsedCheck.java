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
public class AssignAsUsedCheck extends BaseAsUsedCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> assignDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.ASSIGN);

		for (DetailAST assignDetailAST : assignDetailASTList) {
			DetailAST parentDetailAST = assignDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.SLIST) {
				_checkAssign(
					detailAST, assignDetailAST,
					getEndLineNumber(parentDetailAST));
			}
		}
	}

	private void _checkAssign(
		DetailAST detailAST, DetailAST assignDetailAST, int endRange) {

		if (hasParentWithTokenType(
				assignDetailAST, TokenTypes.FOR_EACH_CLAUSE,
				TokenTypes.FOR_INIT)) {

			return;
		}

		DetailAST nameDetailAST = assignDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (nameDetailAST == null) {
			return;
		}

		String variableName = nameDetailAST.getText();

		DetailAST typeDetailAST = getVariableTypeDetailAST(
			assignDetailAST, variableName, false);

		if (typeDetailAST == null) {
			return;
		}

		DetailAST parentDetailAST = typeDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		List<DetailAST> dependentIdentDetailASTList =
			getDependentIdentDetailASTList(
				parentDetailAST, parentDetailAST.getLineNo());

		if (dependentIdentDetailASTList.isEmpty()) {
			return;
		}

		int endLineNumber = getEndLineNumber(assignDetailAST);

		for (DetailAST dependentIdentDetailAST : dependentIdentDetailASTList) {
			int lineNumber = dependentIdentDetailAST.getLineNo();

			if (lineNumber <= endLineNumber) {
				continue;
			}

			if (lineNumber > endRange) {
				return;
			}

			if (!hasParentWithTokenType(
					assignDetailAST, TokenTypes.LITERAL_FOR,
					TokenTypes.LITERAL_WHILE)) {

				int actionLineNumber = getActionLineNumber(assignDetailAST);

				if (actionLineNumber != assignDetailAST.getLineNo()) {
					checkMoveAfterBranchingStatement(
						detailAST, assignDetailAST, variableName,
						dependentIdentDetailAST, actionLineNumber);
					checkMoveInsideIfStatement(
						assignDetailAST, nameDetailAST, variableName,
						dependentIdentDetailAST,
						dependentIdentDetailASTList.get(
							dependentIdentDetailASTList.size() - 1),
						actionLineNumber);
				}
			}

			parentDetailAST = getParentWithTokenType(
				assignDetailAST, TokenTypes.LITERAL_FOR,
				TokenTypes.LITERAL_WHILE);

			if (parentDetailAST != null) {
				List<String> names = getNames(parentDetailAST, true);

				if (!names.contains(variableName)) {
					checkInline(
						assignDetailAST, variableName, dependentIdentDetailAST,
						dependentIdentDetailASTList);
				}
			}

			return;
		}
	}

}