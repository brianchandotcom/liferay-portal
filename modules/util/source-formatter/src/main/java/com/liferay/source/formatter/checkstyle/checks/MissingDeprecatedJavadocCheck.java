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

import antlr.CommonHiddenStreamToken;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class MissingDeprecatedJavadocCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ANNOTATION};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getChildCount() != 2) {
			return;
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if ((lastChildDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(lastChildDetailAST.getText(), "Deprecated")) {

			return;
		}

		DetailAST annotationDetailAST = detailAST;

		while (true) {
			if (annotationDetailAST.getPreviousSibling() == null) {
				break;
			}

			annotationDetailAST = annotationDetailAST.getPreviousSibling();
		}

		DetailAST firstChildDetailAST = annotationDetailAST.getFirstChild();

		CommonHiddenStreamToken commonHiddenStreamToken = getHiddenBefore(
			firstChildDetailAST);

		if (commonHiddenStreamToken != null) {
			String text = commonHiddenStreamToken.getText();

			if (text.contains("@deprecated")) {
				return;
			}
		}

		log(detailAST, _MSG_MISSING_DEPRECATED_JAVADOC);
	}

	private static final String _MSG_MISSING_DEPRECATED_JAVADOC =
		"javadoc.missing.deprecated";

}