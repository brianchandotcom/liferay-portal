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
public class StaticCollectionCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.STATIC_INIT};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST previousSiblingAST = detailAST.getPreviousSibling();

		while (true) {
			if ((previousSiblingAST == null) ||
				(previousSiblingAST.getType() != TokenTypes.VARIABLE_DEF)) {

				return;
			}

			_checkVariable(previousSiblingAST, detailAST);

			previousSiblingAST = previousSiblingAST.getPreviousSibling();
		}
	}

	private void _checkVariable(
		DetailAST variableDefAST, DetailAST staticBlockAST) {

		DetailAST modifiersAST = variableDefAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (!modifiersAST.branchContains(TokenTypes.FINAL) ||
			!modifiersAST.branchContains(TokenTypes.LITERAL_STATIC)) {

			return;
		}

		String typeName = DetailASTUtil.getTypeName(variableDefAST, false);

		if (!typeName.equals("List") && !typeName.equals("Set")) {
			return;
		}

		DetailAST identAST = variableDefAST.findFirstToken(TokenTypes.IDENT);

		String variableName = identAST.getText();

		int variableNameCount = _getIdentCount(staticBlockAST, variableName);

		if (variableNameCount == 0) {
			return;
		}

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			staticBlockAST, variableName, "add");

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST parentAST = methodCallAST.getParent();

			parentAST = parentAST.getParent();

			parentAST = parentAST.getParent();

			if (parentAST.getType() != TokenTypes.STATIC_INIT) {
				return;
			}
		}

		if (methodCallASTList.size() == variableNameCount) {
			log(
				variableDefAST, _MSG_UNNEEDED_STATIC_BLOCK, typeName,
				variableName);
		}
	}

	private int _getIdentCount(DetailAST detailAST, String name) {
		int count = 0;

		for (DetailAST identAST :
				DetailASTUtil.getAllChildTokens(
					detailAST, true, TokenTypes.IDENT)) {

			if (name.equals(identAST.getText())) {
				count++;
			}
		}

		return count;
	}

	private static final String _MSG_UNNEEDED_STATIC_BLOCK =
		"static.block.unneeded";

}