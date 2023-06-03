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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class ServiceUpdateCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		String s = fullIdent.getText();

		Matcher matcher = _serviceUpdatePattern.matcher(s);

		if (!matcher.matches() ||
			!StringUtil.equalsIgnoreCase(matcher.group(1), matcher.group(3))) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST slistDetailAST = parentDetailAST.getParent();

		if (slistDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		DetailAST elistDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		if (elistDetailAST.getChildCount() == 0) {
			return;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST nameDetailAST = firstChildDetailAST.getFirstChild();

		if (nameDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String variableName = nameDetailAST.getText();

		DetailAST typeDetailAST = getVariableTypeDetailAST(
			detailAST, variableName);

		if ((typeDetailAST == null) ||
			!StringUtil.equalsIgnoreCase(
				matcher.group(1), getTypeName(typeDetailAST, false))) {

			return;
		}

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(
				typeDetailAST.getParent(), variableName);

		int size = variableCallerDetailASTList.size();

		for (DetailAST variableCallerDetailAST : variableCallerDetailASTList) {
			if (hasParentWithTokenType(
					variableCallerDetailAST, TokenTypes.LAMBDA)) {

				return;
			}
		}

		for (int i = 0; i < size; i++) {
			if (!equals(variableCallerDetailASTList.get(i), nameDetailAST)) {
				continue;
			}

			if (i == (size - 1)) {
				return;
			}

			DetailAST firstNextVariableCallerDetailAST =
				variableCallerDetailASTList.get(i + 1);

			if (firstNextVariableCallerDetailAST.getPreviousSibling() != null) {
				break;
			}

			parentDetailAST = firstNextVariableCallerDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.ASSIGN) {
				break;
			}

			parentDetailAST = parentDetailAST.getParent();

			if ((parentDetailAST.getType() != TokenTypes.EXPR) ||
				!equals(parentDetailAST.getParent(), slistDetailAST)) {

				break;
			}

			if (i <= (size - 2)) {
				DetailAST secondNextVariableCallerDetailAST =
					variableCallerDetailASTList.get(i + 2);

				if (secondNextVariableCallerDetailAST.getLineNo() >
						getEndLineNumber(parentDetailAST)) {

					return;
				}
			}

			break;
		}

		log(detailAST, _MSG_REASSIGN_UPDATE_CALL, variableName);
	}

	private static final String _MSG_REASSIGN_UPDATE_CALL =
		"update.call.reassign";

	private static final Pattern _serviceUpdatePattern = Pattern.compile(
		"_?(\\w+?)(Local)?Service\\.update([A-Z]\\w+)");

}