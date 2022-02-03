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
public class ConstructorGlobalVariableDeclarationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
			return;
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		DetailAST literalThrowsDetailAST = detailAST.findFirstToken(
			TokenTypes.LITERAL_THROWS);

		if (literalThrowsDetailAST != null) {
			return;
		}

		List<DetailAST> constructorDefinitionDetailASTList = getAllChildTokens(
			parentDetailAST, false, TokenTypes.CTOR_DEF);

		if (constructorDefinitionDetailASTList.size() > 1) {
			return;
		}

		List<DetailAST> expressionDetailASTList = getAllChildTokens(
			lastChildDetailAST, false, TokenTypes.EXPR);

		for (DetailAST expressionDetailAST : expressionDetailASTList) {
			DetailAST firstChildDetailAST = expressionDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.ASSIGN) {
				continue;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			String variableName = firstChildDetailAST.getText();

			if (!variableName.startsWith("_")) {
				continue;
			}

			DetailAST nextSiblingDetailAST =
				firstChildDetailAST.getNextSibling();

			if (nextSiblingDetailAST.getType() != TokenTypes.LITERAL_NEW) {
				continue;
			}

			firstChildDetailAST = nextSiblingDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			DetailAST objBlockDetailAST = nextSiblingDetailAST.findFirstToken(
				TokenTypes.OBJBLOCK);

			if (objBlockDetailAST != null) {
				continue;
			}

			DetailAST elistDetailAST = nextSiblingDetailAST.findFirstToken(
				TokenTypes.ELIST);

			if ((elistDetailAST == null) ||
				(elistDetailAST.getChildCount() > 0)) {

				continue;
			}

			List<DetailAST> variableDefDetailASTList = getAllChildTokens(
				parentDetailAST, false, TokenTypes.VARIABLE_DEF);

			for (DetailAST variableDefDetailAST : variableDefDetailASTList) {
				DetailAST identDetailAST = variableDefDetailAST.findFirstToken(
					TokenTypes.IDENT);

				if (variableName.equals(identDetailAST.getText())) {
					log(
						firstChildDetailAST, _MSG_DECLARE_GLOBAL_VARIABLE_VALUE,
						variableName, variableDefDetailAST.getLineNo());

					break;
				}
			}
		}
	}

	private static final String _MSG_DECLARE_GLOBAL_VARIABLE_VALUE =
		"global.variable.value.declare";

}