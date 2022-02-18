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
public class UnnecessaryVariableDeclarationCheck
	extends BaseUnnecessaryStatementCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (modifiersDetailAST.branchContains(TokenTypes.ANNOTATION)) {
			return;
		}

		DetailAST semiDetailAST = detailAST.getNextSibling();

		if ((semiDetailAST == null) ||
			(semiDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		String variableName = getName(detailAST);

		if (!isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES)) {
			checkUnnecessaryListVariableBeforeReturn(
				detailAST, semiDetailAST, variableName,
				_MSG_UNNECESSARY_LIST_DECLARATION_BEFORE_RETURN);
		}

		checkUnnecessaryStatementBeforeReturn(
			detailAST, semiDetailAST, variableName,
			_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_RETURN);

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		checkUnnecessaryToString(
			detailAST.findFirstToken(TokenTypes.ASSIGN),
			_MSG_UNNECESSARY_VARIABLE_DECLARATION_TO_STRING);

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(detailAST, variableName);

		if (variableCallerDetailASTList.isEmpty()) {
			return;
		}

		DetailAST firstVariableCallerDetailAST =
			variableCallerDetailASTList.get(0);

		DetailAST secondVariableCallerDetailAST = null;

		if (variableCallerDetailASTList.size() > 1) {
			secondVariableCallerDetailAST = variableCallerDetailASTList.get(1);
		}

		checkUnnecessaryStatementBeforeReassign(
			detailAST, firstVariableCallerDetailAST,
			secondVariableCallerDetailAST, parentDetailAST, variableName,
			_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_REASSIGN);
	}

	private static final String
		_MSG_UNNECESSARY_LIST_DECLARATION_BEFORE_RETURN =
			"list.declaration.unnecessary.before.return";

	private static final String
		_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_REASSIGN =
			"variable.declaration.unnecessary.before.reassign";

	private static final String
		_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_RETURN =
			"variable.declaration.unnecessary.before.return";

	private static final String
		_MSG_UNNECESSARY_VARIABLE_DECLARATION_TO_STRING =
			"variable.declaration.unnecessary.to.string";

}