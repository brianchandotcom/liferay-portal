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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ParsePrimitiveTypeCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (isExcludedPath(_RUN_OUTSIDE_PORTAL_EXCLUDES)) {
			return;
		}

		_checkParseMethodCall(detailAST, "Double", "parseDouble", "getDouble");
		_checkParseMethodCall(detailAST, "Float", "parseFloat", "getFloat");
		_checkParseMethodCall(detailAST, "Integer", "parseInt", "getInteger");
		_checkParseMethodCall(detailAST, "Long", "parseLong", "getLong");
		_checkParseMethodCall(detailAST, "Short", "parseShort", "getShort");
	}

	private boolean _catchesException(
		DetailAST methodCallDetailAST, String... exceptionNames) {

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (parentDetailAST.getType() != TokenTypes.LITERAL_TRY) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			DetailAST literalCatchDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.LITERAL_CATCH);

			parentDetailAST = parentDetailAST.getParent();

			if (literalCatchDetailAST == null) {
				continue;
			}

			DetailAST parameterDefinitionDetailAST =
				literalCatchDetailAST.findFirstToken(TokenTypes.PARAMETER_DEF);

			DetailAST typeDetailAST =
				parameterDefinitionDetailAST.findFirstToken(TokenTypes.TYPE);

			List<DetailAST> identDetailASTList =
				DetailASTUtil.getAllChildTokens(
					typeDetailAST, true, TokenTypes.IDENT);

			for (DetailAST identDetailAST : identDetailASTList) {
				if (ArrayUtil.contains(
						exceptionNames, identDetailAST.getText())) {

					return true;
				}
			}
		}
	}

	private void _checkParseMethodCall(
		DetailAST detailAST, String className, String methodName,
		String getterUtilMethodName) {

		List<DetailAST> methodCallDetailASTList = DetailASTUtil.getMethodCalls(
			detailAST, className, methodName);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			List<DetailAST> exprDetailASTList = DetailASTUtil.getAllChildTokens(
				elistDetailAST, false, TokenTypes.EXPR);

			if ((exprDetailASTList.size() == 1) &&
				!_catchesException(
					methodCallDetailAST, "Exception",
					"NumberFormatException")) {

				log(
					methodCallDetailAST, _MSG_USE_GETTER_UTIL_METHOD,
					getterUtilMethodName, className, methodName);
			}
		}
	}

	private static final String _MSG_USE_GETTER_UTIL_METHOD =
		"getter.util.method.use";

	private static final String _RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

}