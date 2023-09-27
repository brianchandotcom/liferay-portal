/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

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