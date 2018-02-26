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

/**
 * @author Hugo Huijser
 */
public class SemiColonCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.SEMI};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST previousSiblingAST = detailAST.getPreviousSibling();

		if (previousSiblingAST == null) {
			return;
		}

		if ((previousSiblingAST.getType() == TokenTypes.CLASS_DEF) ||
			(previousSiblingAST.getType() == TokenTypes.CTOR_DEF) ||
			(previousSiblingAST.getType() == TokenTypes.ENUM_DEF) ||
			(previousSiblingAST.getType() == TokenTypes.INTERFACE_DEF) ||
			(previousSiblingAST.getType() == TokenTypes.METHOD_DEF)) {

			log(detailAST.getLineNo(), _MSG_UNNECESSARY_SEMI_COLON);
		}
		else if (previousSiblingAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
			DetailAST nextSiblingAST = detailAST.getNextSibling();

			if ((nextSiblingAST != null) &&
				(nextSiblingAST.getType() == TokenTypes.RCURLY)) {

				log(detailAST.getLineNo(), _MSG_UNNECESSARY_SEMI_COLON);
			}
		}
	}

	private static final String _MSG_UNNECESSARY_SEMI_COLON =
		"semi.colon.unnecessary";

}