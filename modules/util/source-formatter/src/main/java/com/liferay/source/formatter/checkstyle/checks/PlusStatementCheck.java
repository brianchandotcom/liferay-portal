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

	private void _checkPlusOperator(DetailAST detailAST) {
		if (detailAST.getChildCount() != 2) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		String literalString1 = _getLiteralString(firstChildDetailAST);

		if (literalString1 == null) {
			return;
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		String literalString2 = _getLiteralString(lastChildDetailAST);

		if (literalString2 == null) {
			return;
		}

		if (firstChildDetailAST.getLineNo() == lastChildDetailAST.getLineNo()) {
			log(
				firstChildDetailAST, MSG_COMBINE_LITERAL_STRINGS,
				literalString1, literalString2);

			return;
		}

		if (_isRegexPattern(detailAST)) {
			return;
		}

		checkLiteralStringStartAndEndCharacter(
			literalString1, literalString2, detailAST.getLineNo());

		String line1 = getLine(lastChildDetailAST.getLineNo() - 2);
		String line2 = getLine(lastChildDetailAST.getLineNo() - 1);

		if (_getLeadingTabCount(line1) == _getLeadingTabCount(line2)) {
			return;
		}

		int lineLength1 = CommonUtil.lengthExpandedTabs(
			line1, line1.length(), getTabWidth());

		String trimmedLine2 = StringUtil.trim(line2);

		if ((lineLength1 + trimmedLine2.length() - 4) <= getMaxLineLength()) {
			log(
				lastChildDetailAST, MSG_COMBINE_LITERAL_STRINGS, literalString1,
				literalString2);

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() == TokenTypes.PLUS) &&
			((lineLength1 + literalString2.length()) <= getMaxLineLength())) {

			log(
				detailAST, MSG_COMBINE_LITERAL_STRINGS, literalString1,
				literalString2);

			return;
		}

		int pos = getStringBreakPos(
			literalString1, literalString2, getMaxLineLength() - lineLength1);

		if (pos != -1) {
			log(
				lastChildDetailAST, MSG_MOVE_LITERAL_STRING,
				literalString2.substring(0, pos + 1));
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

	private String _getLiteralString(DetailAST detailAST) {
		String literalString = null;

		if (detailAST.getType() == TokenTypes.STRING_LITERAL) {
			literalString = detailAST.getText();
		}
		else if ((detailAST.getType() == TokenTypes.PLUS) &&
				 (detailAST.getChildCount() == 2)) {

			DetailAST lastChildDetailAST = detailAST.getLastChild();

			if (lastChildDetailAST.getType() == TokenTypes.STRING_LITERAL) {
				literalString = lastChildDetailAST.getText();
			}
		}

		if (literalString != null) {
			return literalString.substring(1, literalString.length() - 1);
		}

		return null;
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

			List<DetailAST> nameDetailASTList = getAllChildTokens(
				firstChildDetailAST, false, TokenTypes.IDENT);

			if (nameDetailASTList.size() != 2) {
				return false;
			}

			DetailAST classNameDetailAST = nameDetailASTList.get(0);
			DetailAST methodNameDetailAST = nameDetailASTList.get(1);

			String methodCallClassName = classNameDetailAST.getText();
			String methodCallMethodName = methodNameDetailAST.getText();

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