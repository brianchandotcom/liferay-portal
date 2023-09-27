/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class PlusStatementCheck extends BaseStringConcatenationCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PLUS};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkPlusOperator(detailAST);
	}

	private void _checkLiteralStrings(
		DetailAST detailAST, DetailAST leftHandOperandDetailAST,
		DetailAST rightHandOperandDetailAST) {

		String value1 = getStringValue(leftHandOperandDetailAST);
		String value2 = getStringValue(rightHandOperandDetailAST);

		if (rightHandOperandDetailAST.getLineNo() ==
				leftHandOperandDetailAST.getLineNo()) {

			log(
				leftHandOperandDetailAST, MSG_COMBINE_LITERAL_STRINGS, value1,
				value2);

			return;
		}

		if (_isRegexPattern(detailAST)) {
			return;
		}

		checkLiteralStringStartAndEndCharacter(
			value1, value2, detailAST.getLineNo());

		String line1 = getLine(rightHandOperandDetailAST.getLineNo() - 2);
		String line2 = getLine(rightHandOperandDetailAST.getLineNo() - 1);

		if (_getLeadingTabCount(line1) == _getLeadingTabCount(line2)) {
			return;
		}

		int lineLength1 = CommonUtil.lengthExpandedTabs(
			line1, line1.length(), getTabWidth());

		String trimmedLine2 = StringUtil.trim(line2);

		if ((lineLength1 + trimmedLine2.length() - 4) <= getMaxLineLength()) {
			log(
				rightHandOperandDetailAST, MSG_COMBINE_LITERAL_STRINGS, value1,
				value2);

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() == TokenTypes.PLUS) &&
			((lineLength1 + value2.length()) <= getMaxLineLength())) {

			log(detailAST, MSG_COMBINE_LITERAL_STRINGS, value1, value2);

			return;
		}

		int pos = getStringBreakPos(
			value1, value2, getMaxLineLength() - lineLength1);

		if (pos != -1) {
			log(
				rightHandOperandDetailAST, MSG_MOVE_LITERAL_STRING,
				value2.substring(0, pos + 1), "previous");
		}

		checkLiteralStringBreaks(
			rightHandOperandDetailAST, line1, line2, value1, value2);
	}

	private void _checkPlusOperator(DetailAST detailAST) {
		if (detailAST.getChildCount() != 2) {
			return;
		}

		DetailAST leftHandOperandDetailAST = _getOperandDetailAST(
			detailAST.getFirstChild());
		DetailAST rightHandOperandDetailAST = _getOperandDetailAST(
			detailAST.getLastChild());

		if ((leftHandOperandDetailAST.getType() == TokenTypes.STRING_LITERAL) &&
			(rightHandOperandDetailAST.getType() ==
				TokenTypes.STRING_LITERAL)) {

			_checkLiteralStrings(
				detailAST, leftHandOperandDetailAST, rightHandOperandDetailAST);

			return;
		}

		if (leftHandOperandDetailAST.getType() == TokenTypes.STRING_LITERAL) {
			checkCombineOperand(
				leftHandOperandDetailAST, rightHandOperandDetailAST);
		}
		else if (rightHandOperandDetailAST.getType() ==
					TokenTypes.STRING_LITERAL) {

			checkCombineOperand(
				rightHandOperandDetailAST, leftHandOperandDetailAST);
		}
	}

	private int _getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	private DetailAST _getOperandDetailAST(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.PLUS) {
			return detailAST.getLastChild();
		}

		return detailAST;
	}

	private boolean _isRegexPattern(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (parentDetailAST != null) {
			if (parentDetailAST.getType() != TokenTypes.METHOD_CALL) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			DetailAST firstChildDetailAST = parentDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.DOT) {
				return false;
			}

			List<String> names = getNames(firstChildDetailAST, false);

			if (names.size() != 2) {
				return false;
			}

			String methodCallClassName = names.get(0);
			String methodCallMethodName = names.get(1);

			if (methodCallMethodName.equals("matches") ||
				(methodCallClassName.equals("Pattern") &&
				 methodCallMethodName.equals("compile"))) {

				return true;
			}

			return false;
		}

		return false;
	}

}