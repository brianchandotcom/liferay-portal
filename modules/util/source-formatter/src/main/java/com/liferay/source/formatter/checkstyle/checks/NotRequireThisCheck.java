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
public class NotRequireThisCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> thisDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_THIS);

		outerLoop:
		for (DetailAST thisDetailAST : thisDetailASTList) {
			if (thisDetailAST.getPreviousSibling() != null) {
				continue;
			}

			DetailAST parentDetailAST = thisDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				continue;
			}

			DetailAST nameDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.IDENT);

			String name = nameDetailAST.getText();

			List<DetailAST> definitionDetailASTList = getAllChildTokens(
				detailAST, true, TokenTypes.PARAMETER_DEF, TokenTypes.RESOURCE,
				TokenTypes.VARIABLE_DEF);

			for (DetailAST definitionDetailAST : definitionDetailASTList) {
				DetailAST definitionNameDetailAST =
					definitionDetailAST.findFirstToken(TokenTypes.IDENT);

				if (name.equals(definitionNameDetailAST.getText())) {
					continue outerLoop;
				}
			}

			log(thisDetailAST, _MSG_VARIABLE_THIS_NOT_REQUIRED, name);
		}
	}

	private static final String _MSG_VARIABLE_THIS_NOT_REQUIRED =
		"variable.not.require.this";

}