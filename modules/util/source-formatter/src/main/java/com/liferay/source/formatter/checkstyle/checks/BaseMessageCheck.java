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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public abstract class BaseMessageCheck extends BaseCheck {

	protected void checkMessage(String literalStringValue, int lineNo) {
		if (Validator.isNull(literalStringValue) ||
			literalStringValue.endsWith(StringPool.TRIPLE_PERIOD)) {

			return;
		}

		String[] parts = literalStringValue.split("\\S\\. [A-Z0-9]");

		if ((parts.length == 1) ^
			!literalStringValue.endsWith(StringPool.PERIOD)) {

			log(lineNo, _MSG_INCORRECT_MESSAGE);
		}
	}

	protected String getLiteralStringValue(DetailAST exprDetailAST) {
		DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.STRING_LITERAL) {
			String s = firstChildDetailAST.getText();

			return s.substring(1, s.length() - 1);
		}

		StringBundler sb = new StringBundler();

		if (firstChildDetailAST.getType() == TokenTypes.PLUS) {
			DetailAST childDetailAST = firstChildDetailAST.getFirstChild();

			while (true) {
				if (childDetailAST.getType() != TokenTypes.STRING_LITERAL) {
					return null;
				}

				String s = childDetailAST.getText();

				sb.append(s.substring(1, s.length() - 1));

				childDetailAST = childDetailAST.getNextSibling();

				if (childDetailAST == null) {
					return sb.toString();
				}
			}
		}

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		String methodName = getMethodName(firstChildDetailAST);

		if (!methodName.equals("concat")) {
			return null;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		for (DetailAST curExprDetailAST : exprDetailASTList) {
			firstChildDetailAST = curExprDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.STRING_LITERAL) {
				return null;
			}

			String s = firstChildDetailAST.getText();

			sb.append(s.substring(1, s.length() - 1));
		}

		return sb.toString();
	}

	private static final String _MSG_INCORRECT_MESSAGE = "message.incorrect";

}