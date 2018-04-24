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
		if (isRunOutsidePortalExclude()) {
			return;
		}

		_checkParseMethodCall(detailAST, "Double", "parseDouble", "getDouble");
		_checkParseMethodCall(detailAST, "Float", "parseFloat", "getFloat");
		_checkParseMethodCall(detailAST, "Integer", "parseInt", "getInteger");
		_checkParseMethodCall(detailAST, "Long", "parseLong", "getLong");
		_checkParseMethodCall(detailAST, "Short", "parseShort", "getShort");
	}

	private boolean _catchesException(
		DetailAST methodCallAST, String... exceptionNames) {

		DetailAST parentAST = methodCallAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (parentAST.getType() != TokenTypes.LITERAL_TRY) {
				parentAST = parentAST.getParent();

				continue;
			}

			DetailAST literalCatchAST = parentAST.findFirstToken(
				TokenTypes.LITERAL_CATCH);

			parentAST = parentAST.getParent();

			if (literalCatchAST == null) {
				continue;
			}

			DetailAST parameterDefAST = literalCatchAST.findFirstToken(
				TokenTypes.PARAMETER_DEF);

			DetailAST typeAST = parameterDefAST.findFirstToken(TokenTypes.TYPE);

			List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
				typeAST, true, TokenTypes.IDENT);

			for (DetailAST identAST : identASTList) {
				if (ArrayUtil.contains(exceptionNames, identAST.getText())) {
					return true;
				}
			}
		}
	}

	private void _checkParseMethodCall(
		DetailAST detailAST, String className, String methodName,
		String getterUtilMethodName) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, className, methodName);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

			List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
				elistAST, false, TokenTypes.EXPR);

			if ((exprASTList.size() == 1) &&
				!_catchesException(
					methodCallAST, "Exception", "NumberFormatException")) {

				log(
					methodCallAST.getLineNo(), _MSG_USE_GETTER_UTIL_METHOD,
					getterUtilMethodName, className, methodName);
			}
		}
	}

	private static final String _MSG_USE_GETTER_UTIL_METHOD =
		"getter.util.method.use";

}