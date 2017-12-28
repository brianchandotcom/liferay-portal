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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class UnwrappedVariableInfoCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = fileContents.getFileName();

		if (!fileName.endsWith("Tei.java")) {
			return;
		}

		String line = getLine(detailAST.getLineNo() - 1);

		if (!line.contains("private static final VariableInfo[]")) {
			return;
		}

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return;
			}

			if (parentAST.getType() == TokenTypes.CLASS_DEF) {
				DetailAST nameAST = parentAST.findFirstToken(TokenTypes.IDENT);

				String className = nameAST.getText();

				if (className.equals("Concealer")) {
					return;
				}

				break;
			}

			parentAST = parentAST.getParent();
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		log(
			detailAST.getLineNo(), _MSG_UNWRAPPED_VARIABLE_INFO,
			nameAST.getText());
	}

	private static final String _MSG_UNWRAPPED_VARIABLE_INFO =
		"variable.info.unwrapped";

}