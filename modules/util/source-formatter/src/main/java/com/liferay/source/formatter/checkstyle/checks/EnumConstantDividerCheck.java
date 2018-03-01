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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * @author Hugo Huijser
 */
public class EnumConstantDividerCheck extends BaseEnumConstantCheck {

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST nextEnumConstantDefAST = getNextEnumConstantDefAST(detailAST);

		if (nextEnumConstantDefAST != null) {
			_checkDivider(detailAST, nextEnumConstantDefAST);
		}
	}

	private void _checkDivider(
		DetailAST enumConstantDefAST1, DetailAST enumConstantDefAST2) {

		int endLineConstant1 = DetailASTUtil.getEndLine(enumConstantDefAST1);
		int startLineConstant2 = DetailASTUtil.getStartLine(
			enumConstantDefAST2);

		if (endLineConstant1 == startLineConstant2) {
			return;
		}

		String nextLine = getLine(endLineConstant1);
		String nextNextLine = StringUtil.trim(getLine(endLineConstant1 + 1));

		if (Validator.isNull(nextLine) && !nextNextLine.startsWith("/")) {
			log(endLineConstant1 + 1, _MSG_UNNECESSARY_EMPTY_LINE);
		}
	}

	private static final String _MSG_UNNECESSARY_EMPTY_LINE =
		"empty.line.unnecessary";

}