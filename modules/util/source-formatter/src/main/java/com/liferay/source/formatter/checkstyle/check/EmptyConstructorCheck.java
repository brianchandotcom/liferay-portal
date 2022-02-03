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
public class EmptyConstructorCheck extends BaseCheck {

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

		if ((lastChildDetailAST.getType() != TokenTypes.SLIST) ||
			(lastChildDetailAST.getChildCount() != 1)) {

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

		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if ((modifiersDetailAST == null) ||
			!modifiersDetailAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {

			return;
		}

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		if ((parametersDetailAST == null) ||
			(parametersDetailAST.getChildCount() == 0)) {

			DetailAST firstChildDetailAST = lastChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.RCURLY) {
				log(detailAST, _MSG_UNNEEDED_EMTPY_CONSTRUCTOR);
			}
		}
	}

	private static final String _MSG_UNNEEDED_EMTPY_CONSTRUCTOR =
		"empty.constructor.unneeded";

}