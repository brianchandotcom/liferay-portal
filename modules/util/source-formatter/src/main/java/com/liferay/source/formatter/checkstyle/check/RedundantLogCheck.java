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
public class RedundantLogCheck extends BaseCheck {

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

		_checkRedundantLog(detailAST, null, detailAST.getLineNo());
	}

	private void _checkRedundantLog(
		DetailAST detailAST, DetailAST previousElistDetailAST,
		int startLineNumber) {

		DetailAST exprDetailAST = detailAST.findFirstToken(TokenTypes.EXPR);

		DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		Matcher matcher = _logLevelPattern.matcher(
			_getMethodCallFullIdent(firstChildDetailAST));

		if (!matcher.find()) {
			return;
		}

		DetailAST slistDetailAST = detailAST.findFirstToken(TokenTypes.SLIST);

		firstChildDetailAST = slistDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		while (nextSiblingDetailAST != null) {
			if ((nextSiblingDetailAST.getType() != TokenTypes.RCURLY) &&
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		String methodCallFullIdent = _getMethodCallFullIdent(
			firstChildDetailAST);

		if (!methodCallFullIdent.matches(
				"_log\\." +
					StringUtil.lowerCaseFirstLetter(matcher.group(1)))) {

			return;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST literalElseDetailAST = detailAST.findFirstToken(
			TokenTypes.LITERAL_ELSE);

		if (literalElseDetailAST == null) {
			if (_compareDetailAST(elistDetailAST, previousElistDetailAST)) {
				log(
					startLineNumber, _MSG_REDUNDANT_LOG, startLineNumber,
					getEndLineNumber(slistDetailAST));
			}

			return;
		}

		firstChildDetailAST = literalElseDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.LITERAL_IF)) {

			return;
		}

		_checkRedundantLog(
			firstChildDetailAST, elistDetailAST, startLineNumber);
	}

	private boolean _compareDetailAST(
		DetailAST detailAST1, DetailAST detailAST2) {

		if ((detailAST1 == null) || (detailAST2 == null)) {
			if (detailAST1 == detailAST2) {
				return true;
			}

			return false;
		}

		while (true) {
			if ((detailAST1.getType() != detailAST2.getType()) ||
				!StringUtil.equals(
					detailAST1.getText(), detailAST2.getText()) ||
				!_compareDetailAST(
					detailAST1.getFirstChild(), detailAST2.getFirstChild())) {

				return false;
			}

			DetailAST nextSiblingDetailAST1 = detailAST1.getNextSibling();
			DetailAST nextSiblingDetailAST2 = detailAST2.getNextSibling();

			if ((nextSiblingDetailAST1 != null) &&
				(nextSiblingDetailAST2 != null)) {

				detailAST1 = nextSiblingDetailAST1;
				detailAST2 = nextSiblingDetailAST2;

				continue;
			}

			if (nextSiblingDetailAST1 == nextSiblingDetailAST2) {
				return true;
			}

			return false;
		}
	}

	private String _getMethodCallFullIdent(DetailAST detailAST) {
		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			return getName(detailAST);
		}

		FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

		return fullIdent.getText();
	}

	private static final String _MSG_REDUNDANT_LOG = "log.redundant";

	private static final Pattern _logLevelPattern = Pattern.compile(
		"_log.is(Debug|Error|Info|Trace|Warn)Enabled");

}