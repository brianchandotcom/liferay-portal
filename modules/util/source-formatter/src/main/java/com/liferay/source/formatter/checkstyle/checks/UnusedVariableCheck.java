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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class UnusedVariableCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if ((detailAST.getType() == TokenTypes.CLASS_DEF) &&
			(detailAST.getParent() != null)) {

			return;
		}

		List<DetailAST> variableDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.VARIABLE_DEF);

		if (variableDefinitionDetailASTList.isEmpty()) {
			return;
		}

		List<String> tokenNames = _getTokenNames(detailAST);

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			DetailAST modifiersDetailAST =
				variableDefinitionDetailAST.findFirstToken(
					TokenTypes.MODIFIERS);

			if (detailAST.getType() == TokenTypes.CLASS_DEF) {
				if (modifiersDetailAST.branchContains(TokenTypes.ANNOTATION) ||
					!modifiersDetailAST.branchContains(
						TokenTypes.LITERAL_PRIVATE)) {

					continue;
				}
			}
			else if (modifiersDetailAST.getChildCount() > 0) {
				continue;
			}

			DetailAST nameDetailAST =
				variableDefinitionDetailAST.findFirstToken(TokenTypes.IDENT);

			String variableName = nameDetailAST.getText();

			if (!variableName.equals("serialVersionUID") &&
				(Collections.frequency(tokenNames, variableName) == 1)) {

				log(
					variableDefinitionDetailAST, _MSG_UNUSED_VARIABLE,
					variableName);
			}
		}
	}

	private List<String> _getTokenNames(DetailAST detailAST) {
		List<String> tokenNames = new ArrayList<>();

		List<DetailAST> nameDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST nameDetailAST : nameDetailASTList) {
			DetailAST parentDetailAST = nameDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.DOT) {
				if (detailAST.getType() != TokenTypes.CLASS_DEF) {
					DetailAST previousSiblingDetailAST =
						nameDetailAST.getPreviousSibling();

					if (previousSiblingDetailAST != null) {
						continue;
					}
				}
			}
			else if (parentDetailAST.getType() == TokenTypes.METHOD_CALL) {
				continue;
			}

			tokenNames.add(nameDetailAST.getText());
		}

		return tokenNames;
	}

	private static final String _MSG_UNUSED_VARIABLE = "variable.unused";

}