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
public class LambdaCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LAMBDA};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		DetailAST firstChildDetailAST = lastChildDetailAST.getFirstChild();

		if (lastChildDetailAST.getChildCount() == 2) {
			if (firstChildDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
				log(detailAST, _MSG_SIMPLIFY_LAMBDA);
			}

			return;
		}

		if ((lastChildDetailAST.getChildCount() != 3) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		if (nextSiblingDetailAST.getType() == TokenTypes.SEMI) {
			log(detailAST, _MSG_SIMPLIFY_LAMBDA);
		}
	}

	private static final String _MSG_SIMPLIFY_LAMBDA = "lambda.simplify";

}