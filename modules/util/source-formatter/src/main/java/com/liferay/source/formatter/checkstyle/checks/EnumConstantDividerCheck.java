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
		DetailAST nextEnumConstantDefinitionDetailAST =
			getNextEnumConstantDefinitionDetailAST(detailAST);

		if (nextEnumConstantDefinitionDetailAST != null) {
			_checkDivider(detailAST, nextEnumConstantDefinitionDetailAST);
		}
	}

	private void _checkDivider(
		DetailAST enumConstantDefinitionDetailAST1,
		DetailAST enumConstantDefinitionDetailAST2) {

		int endLineNumberConstant1 = DetailASTUtil.getEndLineNumber(
			enumConstantDefinitionDetailAST1);
		int startLineNumberConstant2 = DetailASTUtil.getStartLineNumber(
			enumConstantDefinitionDetailAST2);

		if (endLineNumberConstant1 == startLineNumberConstant2) {
			return;
		}

		String nextLine = getLine(endLineNumberConstant1);
		String nextNextLine = StringUtil.trim(
			getLine(endLineNumberConstant1 + 1));

		if (Validator.isNull(nextLine) && !nextNextLine.startsWith("/")) {
			log(endLineNumberConstant1 + 1, _MSG_UNNECESSARY_EMPTY_LINE);
		}
	}

	private static final String _MSG_UNNECESSARY_EMPTY_LINE =
		"empty.line.unnecessary";

}