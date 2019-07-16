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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * @author Hugo Huijser
 */
public class CamelCaseNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkName(detailAST, "re", "reCaptcha");
		_checkName(detailAST, "sub");
	}

	private void _checkName(
		DetailAST detailAST, String s, String... allowedNames) {

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		String lowerCaseName = StringUtil.toLowerCase(name);

		for (String allowedName : allowedNames) {
			if (lowerCaseName.contains(StringUtil.toLowerCase(allowedName))) {
				return;
			}
		}

		if (detailAST.getType() == TokenTypes.METHOD_DEF) {
			if (!AnnotationUtil.containsAnnotation(detailAST, "Override")) {
				String regex = StringBundler.concat(
					"(^_", s, "|.*", TextFormatter.format(s, TextFormatter.G),
					")[A-Z].*");

				if (name.matches(regex)) {
					log(detailAST, _MSG_METHOD_INVALID_NAME, s, name);
				}
			}
		}
		else {
			String regex = StringBundler.concat("^_?", s, "[A-Z].*");

			if (name.matches(regex)) {
				if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
					log(detailAST, _MSG_PARAMETER_INVALID_NAME, s, name);
				}
				else {
					log(detailAST, _MSG_VARIABLE_INVALID_NAME, s, name);
				}
			}
		}
	}

	private static final String _MSG_METHOD_INVALID_NAME = "method.invalidName";

	private static final String _MSG_PARAMETER_INVALID_NAME =
		"parameter.invalidName";

	private static final String _MSG_VARIABLE_INVALID_NAME =
		"variable.invalidName";

}