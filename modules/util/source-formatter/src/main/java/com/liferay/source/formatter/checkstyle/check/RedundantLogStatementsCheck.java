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

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class RedundantLogStatementsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_IF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.LITERAL_ELSE) {
			return;
		}

		_checkExpression(detailAST, null, getStartLineNumber(detailAST));
	}

	private void _checkExpression(
		DetailAST detailAST, DetailAST preStatementDetailAST, int startLine) {

		DetailAST exprDetailAST = detailAST.findFirstToken(TokenTypes.EXPR);

		DetailAST exprFirstChildDetailAST = exprDetailAST.getFirstChild();

		if (exprFirstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		DetailAST dotDetailAST = exprFirstChildDetailAST.getFirstChild();

		if (dotDetailAST == null) {
			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

		Matcher matcher = _logLevelPattern.matcher(fullIdent.getText());

		if (!matcher.find()) {
			return;
		}

		String level = StringUtil.lowerCase(matcher.group(1));

		DetailAST sListDetailAST = detailAST.findFirstToken(TokenTypes.SLIST);

		int count = 0;
		DetailAST firstChildDetailAST = sListDetailAST.getFirstChild();

		while (firstChildDetailAST != null) {
			int tokenType = firstChildDetailAST.getType();

			if ((tokenType != TokenTypes.SEMI) &&
				(tokenType != TokenTypes.RCURLY)) {

				count++;
			}

			firstChildDetailAST = firstChildDetailAST.getNextSibling();
		}

		if (count > 1) {
			return;
		}

		firstChildDetailAST = sListDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		dotDetailAST = firstChildDetailAST.getFirstChild();

		if (dotDetailAST == null) {
			return;
		}

		fullIdent = FullIdent.createFullIdent(dotDetailAST);

		String fullIdentText = fullIdent.getText();

		if (!fullIdentText.matches("_log\\." + level)) {
			return;
		}

		DetailAST expressDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST elseDetailAST = detailAST.findFirstToken(
			TokenTypes.LITERAL_ELSE);

		if (elseDetailAST == null) {
			if (_compareDetailASTIgnoreLine(
					expressDetailAST, preStatementDetailAST)) {

				log(
					startLine, _MSG_REDUNDANT_LOG, startLine,
					getEndLineNumber(sListDetailAST));
			}

			return;
		}

		DetailAST elseIfDetailAST = elseDetailAST.getFirstChild();

		if ((elseIfDetailAST == null) ||
			(elseIfDetailAST.getType() != TokenTypes.LITERAL_IF)) {

			return;
		}

		_checkExpression(elseIfDetailAST, expressDetailAST, startLine);
	}

	private boolean _compareDetailASTIgnoreLine(
		DetailAST detailAST1, DetailAST detailAST2) {

		if ((detailAST1 == null) || (detailAST2 == null)) {
			if (detailAST1 == detailAST2) {
				return true;
			}

			return false;
		}

		DetailAST detailAST1ChildDetailAST = detailAST1;
		DetailAST detailAST2ChildDetailAST = detailAST2;

		while (true) {
			if ((detailAST1ChildDetailAST.getType() !=
					detailAST2ChildDetailAST.getType()) ||
				!StringUtil.equals(
					detailAST1ChildDetailAST.getText(),
					detailAST2ChildDetailAST.getText())) {

				return false;
			}

			DetailAST firstChildDetailAST1 =
				detailAST1ChildDetailAST.getFirstChild();
			DetailAST firstChildDetailAST2 =
				detailAST2ChildDetailAST.getFirstChild();

			if ((firstChildDetailAST1 != null) &&
				(firstChildDetailAST2 != null) &&
				!_compareDetailASTIgnoreLine(
					firstChildDetailAST1, firstChildDetailAST2)) {

				return false;
			}

			DetailAST nextDetailAST1 =
				detailAST1ChildDetailAST.getNextSibling();
			DetailAST nextDetailAST2 =
				detailAST2ChildDetailAST.getNextSibling();

			if ((nextDetailAST1 != null) && (nextDetailAST2 != null)) {
				detailAST1ChildDetailAST = nextDetailAST1;
				detailAST2ChildDetailAST = nextDetailAST2;

				continue;
			}

			if (nextDetailAST1 == nextDetailAST2) {
				return true;
			}

			return false;
		}
	}

	private static final String _MSG_REDUNDANT_LOG = "redundant.log.branching";

	private static final Pattern _logLevelPattern = Pattern.compile(
		"_log.is(Debug|Error|Info|Trace|Warn)Enabled");

}