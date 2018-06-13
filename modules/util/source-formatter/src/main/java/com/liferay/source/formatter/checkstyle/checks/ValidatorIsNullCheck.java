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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ValidatorIsNullCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkMethod(detailAST, "Validator", "isNotNull");
		_checkMethod(detailAST, "Validator", "isNull");
	}

	private void _checkMethod(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, className, methodName);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

			DetailAST expressionAST = elistAST.findFirstToken(TokenTypes.EXPR);

			DetailAST childAST = expressionAST.getFirstChild();

			if (childAST.getType() == TokenTypes.NUM_INT) {
				log(
					methodCallAST.getLineNo(), _MSG_INVALID_METHOD_NAME,
					StringBundler.concat(className, ".", methodName, "(long)"));

				continue;
			}

			if (childAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			DetailAST typeAST = DetailASTUtil.getVariableTypeAST(
				methodCallAST, childAST.getText());

			if (typeAST == null) {
				continue;
			}

			childAST = typeAST.getFirstChild();

			if ((childAST.getType() == TokenTypes.LITERAL_INT) ||
				(childAST.getType() == TokenTypes.LITERAL_LONG)) {

				log(
					methodCallAST.getLineNo(), _MSG_INVALID_METHOD_NAME,
					StringBundler.concat(className, ".", methodName, "(long)"));

				continue;
			}

			String typeName = DetailASTUtil.getTypeName(typeAST, true);

			if (Validator.isNotNull(typeName) && !typeName.equals("Long") &&
				!typeName.equals("Object") &&
				!typeName.equals("Serializable") &&
				!typeName.equals("String")) {

				log(
					methodCallAST.getLineNo(), _MSG_RESERVED_METHOD,
					StringBundler.concat(className, ".", methodName));
			}
		}
	}

	private static final String _MSG_INVALID_METHOD_NAME = "method.invalidName";

	private static final String _MSG_RESERVED_METHOD = "method.reserved";

}