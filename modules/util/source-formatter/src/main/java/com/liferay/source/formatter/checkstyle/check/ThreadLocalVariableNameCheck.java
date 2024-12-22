/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Alan Huang
 */
public class ThreadLocalVariableNameCheck extends VariableNameCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (!modifiersDetailAST.branchContains(TokenTypes.FINAL) ||
			!modifiersDetailAST.branchContains(TokenTypes.LITERAL_STATIC)) {

			return;
		}

		String variableTypeName = getTypeName(detailAST, false);

		if ((variableTypeName == null) ||
			!variableTypeName.endsWith("ThreadLocal")) {

			return;
		}

		String variableName = getName(detailAST);

		if (StringUtil.endsWith(variableName, "ThreadLocal")) {
			log(
				detailAST, _MSG_INCORRECT_ENDING_VARIABLE, "*ThreadLocal",
				"ThreadLocal");

			return;
		}

		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW)) {

			return;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST == null) {
			return;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.PLUS)) {

			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.STRING_LITERAL)) {

			return;
		}

		String expectedLiteralString = "." + variableName;
		String value = StringUtil.unquote(nextSiblingDetailAST.getText());

		if (!StringUtil.equals(expectedLiteralString, value)) {
			log(
				detailAST, _MSG_LITERAL_STRING, variableName,
				expectedLiteralString);
		}
	}

	private static final String _MSG_INCORRECT_ENDING_VARIABLE =
		"variable.incorrect.ending";

	private static final String _MSG_LITERAL_STRING =
		"literal.string.incorrect";

}