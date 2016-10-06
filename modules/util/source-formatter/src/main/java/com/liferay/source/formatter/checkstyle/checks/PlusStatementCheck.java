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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class PlusStatementCheck extends AbstractCheck {

	public static final String MSG_COMBINE_LITERAL_STRINGS =
		"literal.string.combine";

	public static final String MSG_INVALID_END_CHARACTER =
		"end.character.invalid";

	public static final String MSG_INVALID_START_CHARACTER =
		"start.character.invalid";

	public static final String MSG_MOVE_LITERAL_STRING = "literal.string.move";

	public static final String MSG_STATEMENT_TOO_LONG =
		"plus.statement.too.long";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PLUS};
	}

	public void setMaxLineLength(int maxLineLength) {
		_maxLineLength = maxLineLength;
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		_checkMultiLinesPlusStatement(detailAST);

		if (detailAST.getChildCount() != 2) {
			return;
		}

		DetailAST firstChild = detailAST.getFirstChild();

		String literalString1 = _getLiteralString(firstChild);

		if (literalString1 == null) {
			return;
		}

		DetailAST lastChild = detailAST.getLastChild();

		String literalString2 = _getLiteralString(lastChild);

		if (literalString2 == null) {
			return;
		}

		if (firstChild.getLineNo() == lastChild.getLineNo()) {
			log(
				firstChild.getLineNo(), MSG_COMBINE_LITERAL_STRINGS,
				literalString1, literalString2);

			return;
		}

		if (_isRegexPattern(detailAST)) {
			return;
		}

		if (literalString1.endsWith(StringPool.SLASH)) {
			log(
				detailAST.getLineNo(), MSG_INVALID_END_CHARACTER,
				literalString1.charAt(literalString1.length() - 1));
		}

		if (literalString2.startsWith(StringPool.SPACE) ||
			(!literalString1.endsWith(StringPool.SPACE) &&
			 literalString2.matches("^[-:;.].*"))) {

			log(
				lastChild.getLineNo(), MSG_INVALID_START_CHARACTER,
				literalString2.charAt(0));

			return;
		}

		String[] lines = getLines();

		String line1 = lines[lastChild.getLineNo() - 2];
		String line2 = lines[lastChild.getLineNo() - 1];

		int tabCount1 = _getLeadingTabCount(line1);
		int tabCount2 = _getLeadingTabCount(line2);

		if (tabCount1 == tabCount2) {
			return;
		}

		int lineLength1 = CommonUtils.lengthExpandedTabs(
			line1, line1.length(), getTabWidth());

		String trimmedLine2 = StringUtil.trim(line2);

		if ((lineLength1 + trimmedLine2.length() - 4) <= _maxLineLength) {
			log(
				lastChild.getLineNo(), MSG_COMBINE_LITERAL_STRINGS,
				literalString1, literalString2);

			return;
		}

		DetailAST parentAST = detailAST.getParent();

		if ((parentAST.getType() == TokenTypes.PLUS) &&
			((lineLength1 + literalString2.length()) <= _maxLineLength)) {

			log(
				detailAST.getLineNo(), MSG_COMBINE_LITERAL_STRINGS,
				literalString1, literalString2);

			return;
		}

		int pos = _getStringBreakPos(
			literalString1, literalString2, (_maxLineLength - lineLength1));

		if (pos != -1) {
			log(
				lastChild.getLineNo(), MSG_MOVE_LITERAL_STRING,
				literalString2.substring(0, pos + 1));
		}
	}

	private void _checkMultiLinesPlusStatement(DetailAST detailAST) {
		DetailAST firstChildAST = detailAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.PLUS) {
			return;
		}

		if (DetailASTUtil.hasParentWithTokenType(
				detailAST, TokenTypes.ANNOTATION) ||
			!DetailASTUtil.hasParentWithTokenType(
				detailAST, TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF)) {

			return;
		}

		Set<Integer> lineNumbers = new HashSet<>();

		lineNumbers.add(detailAST.getLineNo());

		DetailAST parentAST = detailAST;

		while (true) {
			if (parentAST.getType() != TokenTypes.PLUS) {
				break;
			}

			DetailAST lastChildAST = parentAST.getLastChild();

			lineNumbers.add(lastChildAST.getLineNo());

			parentAST = parentAST.getParent();
		}

		if (lineNumbers.size() > 3) {
			log(detailAST.getLineNo(), MSG_STATEMENT_TOO_LONG);
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

			DetailAST lastChild = detailAST.getLastChild();

			if (lastChild.getType() == TokenTypes.STRING_LITERAL) {
				literalString = lastChild.getText();
			}
		}

		if (literalString != null) {
			return literalString.substring(1, literalString.length() - 1);
		}

		return null;
	}

	private int _getStringBreakPos(String s1, String s2, int i) {
		if (s2.startsWith(StringPool.SLASH)) {
			int pos = s2.lastIndexOf(StringPool.SLASH, i);

			if (pos > 0) {
				return pos - 1;
			}

			return -1;
		}

		if (s1.endsWith(StringPool.DASH)) {
			return Math.max(
				s2.lastIndexOf(StringPool.DASH, i - 1),
				s2.lastIndexOf(StringPool.SPACE, i - 1));
		}

		if (s1.endsWith(StringPool.PERIOD)) {
			return Math.max(
				s2.lastIndexOf(StringPool.PERIOD, i - 1),
				s2.lastIndexOf(StringPool.SPACE, i - 1));
		}

		if (s1.endsWith(StringPool.SPACE)) {
			return s2.lastIndexOf(StringPool.SPACE, i - 1);
		}

		return -1;
	}

	private boolean _isRegexPattern(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (parentAST != null) {
			if (parentAST.getType() != TokenTypes.METHOD_CALL) {
				parentAST = parentAST.getParent();

				continue;
			}

			DetailAST firstChild = parentAST.getFirstChild();

			if (firstChild.getType() != TokenTypes.DOT) {
				return false;
			}

			List<DetailAST> nameASTList = DetailASTUtil.getAllChildTokens(
				firstChild, TokenTypes.IDENT, false);

			if (nameASTList.size() != 2) {
				return false;
			}

			DetailAST classNameAST = nameASTList.get(0);
			DetailAST methodNameAST = nameASTList.get(1);

			String methodCallClassName = classNameAST.getText();
			String methodCallMethodName = methodNameAST.getText();

			if (methodCallClassName.equals("Pattern") &&
				methodCallMethodName.equals("compile")) {

				return true;
			}

			return false;
		}

		return false;
	}

	private int _maxLineLength = 80;

}