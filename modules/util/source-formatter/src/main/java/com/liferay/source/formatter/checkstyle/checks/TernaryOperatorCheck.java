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

/**
 * @author Hugo Huijser
 */
public class TernaryOperatorCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.QUESTION};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (DetailASTUtil.getStartLine(detailAST) !=
				DetailASTUtil.getEndLine(detailAST)) {

			log(detailAST.getLineNo(), _MSG_AVOID_TERNARY_OPERATOR);
		}
	}

	private static final String _MSG_AVOID_TERNARY_OPERATOR =
		"ternary.operator.avoid";

}