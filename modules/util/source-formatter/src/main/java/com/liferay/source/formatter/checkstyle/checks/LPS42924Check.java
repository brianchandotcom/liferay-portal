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
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class LPS42924Check extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = fileContents.getFileName();

		if (!fileName.endsWith("ServiceImpl.java")) {
			return;
		}

		List<DetailAST> methodCallDetailASTList = DetailASTUtil.getMethodCalls(
			detailAST, "PortalUtil", "getClassNameId");

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			log(methodCallDetailAST, _MSG_LPS_42924);
		}
	}

	private static final String _MSG_LPS_42924 = "lps42924";

}