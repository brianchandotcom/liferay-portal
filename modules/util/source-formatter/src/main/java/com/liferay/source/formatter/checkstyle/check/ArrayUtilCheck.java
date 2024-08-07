/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Alan Huang
 */
public class ArrayUtilCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.EQUAL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES)) {
			return;
		}

		_checkArrayLengthIsZero(detailAST);
	}

	private void _checkArrayEqualsNullAssertion(
		DetailAST detailAST, String variableName) {

		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.LPAREN)) {

			return;
		}

		DetailAST identDetailAST = detailAST.getFirstChild();

		if ((identDetailAST == null) ||
			(identDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(identDetailAST.getText(), variableName)) {

			return;
		}

		DetailAST nextSiblingDetailAST = identDetailAST.getNextSibling();

		if (nextSiblingDetailAST.getType() != TokenTypes.LITERAL_NULL) {
			return;
		}

		log(detailAST.getParent(), _MSG_USE_ARRAY_UTIL_IS_EMPTY, variableName);
	}

	private void _checkArrayLengthIsZero(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.DOT)) {

			return;
		}

		List<String> names = getNames(firstChildDetailAST, false);

		if ((names.size() != 2) || !StringUtil.equals(names.get(1), "length")) {
			return;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.NUM_INT) ||
			!StringUtil.equals(nextSiblingDetailAST.getText(), "0")) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.LOR) {
			return;
		}

		DetailAST equalDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.EQUAL);

		if (!equals(equalDetailAST, detailAST)) {
			_checkArrayEqualsNullAssertion(equalDetailAST, names.get(0));
		}
	}

	private static final String _MSG_USE_ARRAY_UTIL_IS_EMPTY =
		"array.util.is.empty.use";

}