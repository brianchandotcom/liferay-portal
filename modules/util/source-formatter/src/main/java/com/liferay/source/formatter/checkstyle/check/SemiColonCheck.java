/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * @author Hugo Huijser
 */
public class SemiColonCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.EMPTY_STAT, TokenTypes.SEMI};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.EMPTY_STAT) {
			DetailAST parentDetailAST = detailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.LITERAL_WHILE) {
				log(detailAST, _MSG_UNNECESSARY_SEMI_COLON);
			}

			return;
		}

		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		if (previousSiblingDetailAST == null) {
			return;
		}

		if (TokenUtil.isOfType(
				previousSiblingDetailAST, TokenTypes.CLASS_DEF,
				TokenTypes.CTOR_DEF, TokenTypes.ENUM_DEF,
				TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF,
				TokenTypes.STATIC_INIT)) {

			log(detailAST, _MSG_UNNECESSARY_SEMI_COLON);
		}
		else if (previousSiblingDetailAST.getType() ==
					TokenTypes.ENUM_CONSTANT_DEF) {

			DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

			if ((nextSiblingDetailAST != null) &&
				(nextSiblingDetailAST.getType() == TokenTypes.RCURLY)) {

				log(detailAST, _MSG_UNNECESSARY_SEMI_COLON);
			}
		}
	}

	private static final String _MSG_UNNECESSARY_SEMI_COLON =
		"semi.colon.unnecessary";

}