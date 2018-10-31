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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * @author Hugo Huijser
 */
public class SubnameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		if (detailAST.getType() == TokenTypes.METHOD_DEF) {
			if (name.matches("(^_?sub|.*Sub)[A-Z].*") &&
				!AnnotationUtil.containsAnnotation(detailAST, "Override")) {

				log(detailAST, _MSG_METHOD_INVALID_NAME, name);
			}
		}
		else if (name.matches("^_?sub[A-Z].*")) {
			if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
				log(detailAST, _MSG_PARAMETER_INVALID_NAME, name);
			}
			else {
				log(detailAST, _MSG_VARIABLE_INVALID_NAME, name);
			}
		}
	}

	private static final String _MSG_METHOD_INVALID_NAME = "method.invalidName";

	private static final String _MSG_PARAMETER_INVALID_NAME =
		"parameter.invalidName";

	private static final String _MSG_VARIABLE_INVALID_NAME =
		"variable.invalidName";

}