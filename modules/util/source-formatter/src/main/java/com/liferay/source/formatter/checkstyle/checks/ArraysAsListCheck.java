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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class ArraysAsListCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (!Objects.equals(
				DetailASTUtil.getTypeName(detailAST, false), "List") ||
			!_isAssignNewArrayList(detailAST)) {

			return;
		}

		DetailAST nextStatementDetailAST = _getNextStatementDetailAST(
			detailAST);

		if (nextStatementDetailAST == null) {
			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String variableName = nameDetailAST.getText();

		if (!_isAddMethodCall(
				nextStatementDetailAST.getFirstChild(), variableName)) {

			return;
		}

		nextStatementDetailAST = _getNextStatementDetailAST(
			nextStatementDetailAST);

		if ((nextStatementDetailAST == null) ||
			nextStatementDetailAST.branchContains(TokenTypes.LCURLY) ||
			nextStatementDetailAST.branchContains(TokenTypes.SLIST)) {

			return;
		}

		List<DetailAST> identDetailASTList = _getIdentDetailASTList(
			nextStatementDetailAST, variableName);

		if (identDetailASTList.size() != 1) {
			return;
		}

		DetailAST identDetailAST = identDetailASTList.get(0);

		DetailAST parentDetailAST = identDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		while (true) {
			nextStatementDetailAST = _getNextStatementDetailAST(
				nextStatementDetailAST);

			if (nextStatementDetailAST == null) {
				break;
			}

			identDetailASTList = _getIdentDetailASTList(
				nextStatementDetailAST, variableName);

			if (!identDetailASTList.isEmpty()) {
				return;
			}
		}

		log(detailAST, _MSG_USE_ARRAYS_AS_LIST);
	}

	private List<DetailAST> _getIdentDetailASTList(
		DetailAST detailAST, String name) {

		List<DetailAST> identDetailASTList = new ArrayList<>();

		List<DetailAST> childDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (name.equals(childDetailAST.getText())) {
				identDetailASTList.add(childDetailAST);
			}
		}

		return identDetailASTList;
	}

	private DetailAST _getNextStatementDetailAST(DetailAST detailAST) {
		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		while (true) {
			if ((nextSiblingDetailAST == null) ||
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return nextSiblingDetailAST;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
	}

	private boolean _isAddMethodCall(DetailAST detailAST, String variableName) {
		if ((detailAST == null) ||
			(detailAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return false;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		return Objects.equals(fullIdent.getText(), variableName + ".add");
	}

	private boolean _isAssignNewArrayList(DetailAST detailAST) {
		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return false;
		}

		DetailAST identDetailAST = firstChildDetailAST.getFirstChild();

		if ((identDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(identDetailAST.getText(), "ArrayList")) {

			return false;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST == null) {
			return false;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return true;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.NUM_INT) {
			return true;
		}

		return false;
	}

	private static final String _MSG_USE_ARRAYS_AS_LIST = "arrays.as.list.use";

}