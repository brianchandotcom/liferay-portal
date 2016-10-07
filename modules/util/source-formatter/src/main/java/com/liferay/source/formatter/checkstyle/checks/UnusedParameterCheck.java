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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class UnusedParameterCheck extends AbstractCheck {

	public static final String MSG_UNUSED_PARAMETER = "parameter.unused";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (!modifiersAST.branchContains(TokenTypes.LITERAL_PRIVATE)) {
			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		if (name.equals("readObject") || name.equals("writeObject")) {
			return;
		}

		List<String> parameterNames = DetailASTUtil.getParameterNames(
			detailAST);

		if (parameterNames.isEmpty()) {
			return;
		}

		DetailAST statementsAST = detailAST.findFirstToken(TokenTypes.SLIST);

		List<DetailAST> allIdentsAST = DetailASTUtil.getAllChildTokens(
			statementsAST, true, TokenTypes.IDENT);

		parameterNameLoop:
		for (String parameterName :
				DetailASTUtil.getParameterNames(detailAST)) {

			for (DetailAST identAST : allIdentsAST) {
				if (parameterName.equals(identAST.getText())) {
					continue parameterNameLoop;
				}
			}

			log(detailAST.getLineNo(), MSG_UNUSED_PARAMETER, parameterName);
		}
	}

}