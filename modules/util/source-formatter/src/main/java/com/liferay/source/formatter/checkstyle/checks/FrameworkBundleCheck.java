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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class FrameworkBundleCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		List<String> importNames = DetailASTUtil.getImportNames(detailAST);

		if (!importNames.contains("org.osgi.framework.Bundle")) {
			return;
		}

		List<DetailAST> detailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF);

		for (DetailAST curDetailAST : detailASTList) {
			_checkGetHeadersMethodCall(curDetailAST);
		}
	}

	private void _checkGetHeadersMethodCall(DetailAST detailAST) {
		List<DetailAST> methodCallDetailASTList = DetailASTUtil.getMethodCalls(
			detailAST, "getHeaders");

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			List<DetailAST> exprDetailASTList = DetailASTUtil.getAllChildTokens(
				elistDetailAST, false, TokenTypes.EXPR);

			if (!exprDetailASTList.isEmpty()) {
				continue;
			}

			String variableTypeName = DetailASTUtil.getVariableTypeName(
				methodCallDetailAST,
				DetailASTUtil.getVariableName(methodCallDetailAST), false);

			if (variableTypeName.equals("Bundle")) {
				log(methodCallDetailAST, _MSG_USE_BUNDLE_GET_HEADERS);
			}
		}
	}

	private static final String _MSG_USE_BUNDLE_GET_HEADERS =
		"bundle.get.headers.use";

}