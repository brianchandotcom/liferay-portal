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