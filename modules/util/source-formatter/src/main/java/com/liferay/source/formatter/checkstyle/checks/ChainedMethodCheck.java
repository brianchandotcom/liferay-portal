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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public abstract class ChainedMethodCheck extends BaseCheck {

	protected boolean containsVariableName(
		DetailAST detailAST, String variableName) {

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			if (!variableName.equals(identDetailAST.getText())) {
				continue;
			}

			DetailAST nextSiblingDetailAST = identDetailAST.getNextSibling();

			if ((nextSiblingDetailAST != null) &&
				(nextSiblingDetailAST.getType() == TokenTypes.IDENT)) {

				String s = nextSiblingDetailAST.getText();

				if (s.startsWith("get")) {
					continue;
				}
			}

			return true;
		}

		return false;
	}

	protected FullIdent getMethodCallFullIdent(
		DetailAST detailAST, String variableName, String methodName) {

		if (detailAST.getType() != TokenTypes.EXPR) {
			return null;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		while (true) {
			if ((firstChildDetailAST == null) ||
				(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

				return null;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			if (Objects.equals(
					fullIdent.getText(), variableName + "." + methodName)) {

				return fullIdent;
			}

			if (firstChildDetailAST.getType() != TokenTypes.DOT) {
				return null;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}
	}

	protected String getVariableName(
		DetailAST assignDetailAST, DetailAST parentDetailAST) {

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST nameDetailAST = assignDetailAST.getFirstChild();

			if (nameDetailAST.getType() == TokenTypes.IDENT) {
				return nameDetailAST.getText();
			}

			return null;
		}

		DetailAST nameDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (nameDetailAST != null) {
			return nameDetailAST.getText();
		}

		return null;
	}

}