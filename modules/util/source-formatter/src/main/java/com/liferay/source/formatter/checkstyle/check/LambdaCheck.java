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
			DetailAST parametersDetailAST = detailAST.findFirstToken(
				TokenTypes.PARAMETERS);

			if ((parametersDetailAST == null) ||
				(parametersDetailAST.getChildCount() != 0)) {

				return;
			}

			DetailAST exprDetailAST = detailAST.findFirstToken(TokenTypes.EXPR);

			if ((exprDetailAST == null) ||
				(exprDetailAST.getLineNo() != detailAST.getLineNo())) {

				return;
			}

			DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

			if ((firstChildDetailAST != null) &&
				(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

				return;
			}

			DetailAST parameterDetailAST = getParameterDetailAST(
				firstChildDetailAST);

			if (parameterDetailAST != null) {
				return;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() != TokenTypes.DOT) ||
				(firstChildDetailAST.getChildCount(TokenTypes.IDENT) != 2)) {

				return;
			}

			DetailAST nextSiblingDetailAST =
				firstChildDetailAST.getNextSibling();

			if ((nextSiblingDetailAST.getType() != TokenTypes.ELIST) ||
				nextSiblingDetailAST.hasChildren()) {

				return;
			}

			log(detailAST, _MSG_SIMPLIFY_LAMBDA_2);

			return;
		}

		DetailAST firstChildDetailAST = lastChildDetailAST.getFirstChild();

		if (lastChildDetailAST.getChildCount() == 2) {
			if (firstChildDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
				log(detailAST, _MSG_SIMPLIFY_LAMBDA_1);
			}

			return;
		}

		if ((lastChildDetailAST.getChildCount() != 3) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		if (nextSiblingDetailAST.getType() == TokenTypes.SEMI) {
			log(detailAST, _MSG_SIMPLIFY_LAMBDA_1);
		}
	}

	private static final String _MSG_SIMPLIFY_LAMBDA_1 = "lambda.simplify.1";

	private static final String _MSG_SIMPLIFY_LAMBDA_2 = "lambda.simplify.2";

}