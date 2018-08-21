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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MethodNamingCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkDoMethodName(detailAST);
	}

	private void _checkDoMethodName(DetailAST detailAST) {
		String name = _getMethodName(detailAST);

		Matcher matcher = _doMethodNamePattern.matcher(name);

		if (!matcher.find()) {
			return;
		}

		String noDoName =
			"_" + StringUtil.toLowerCase(matcher.group(1)) + matcher.group(2);
		String noUnderscoreName = name.substring(1);

		DetailAST parentAST = detailAST.getParent();

		List<DetailAST> methodDefASTList = DetailASTUtil.getAllChildTokens(
			parentAST, false, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefAST : methodDefASTList) {
			String methodName = _getMethodName(methodDefAST);

			if (methodName.equals(noUnderscoreName) ||
				(methodName.equals(noDoName) &&
				 Objects.equals(
					 DetailASTUtil.getSignature(detailAST),
					 DetailASTUtil.getSignature(methodDefAST)))) {

				return;
			}
		}

		log(detailAST.getLineNo(), _MSG_RENAME_METHOD, name, noDoName);
	}

	private String _getMethodName(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameAST.getText();
	}

	private static final String _MSG_RENAME_METHOD = "method.rename";

	private final Pattern _doMethodNamePattern = Pattern.compile(
		"^_do([A-Z])(.*)$");

}