/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.petra.string.StringBundler;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class LiteralStringEqualsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.STRING_LITERAL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if ((nextSiblingDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(nextSiblingDetailAST.getText(), "equals")) {

			return;
		}

		DetailAST elistDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST firstChildDetailAST = elistDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			log(detailAST, _MSG_USE_OBJECTS_EQUALS_1);
		}
		else {
			String variableName = firstChildDetailAST.getText();

			log(
				detailAST, _MSG_USE_OBJECTS_EQUALS_2,
				StringBundler.concat(
					variableName, ".equals(", detailAST.getText(), ")"),
				variableName);
		}
	}

	private static final String _MSG_USE_OBJECTS_EQUALS_1 =
		"objects.equals.use.1";

	private static final String _MSG_USE_OBJECTS_EQUALS_2 =
		"objects.equals.use.2";

}