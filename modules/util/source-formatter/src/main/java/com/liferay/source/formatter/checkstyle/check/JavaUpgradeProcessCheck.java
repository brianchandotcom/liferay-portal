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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qi Zhang
 */
public class JavaUpgradeProcessCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) || !_isUpgradeProcess(detailAST)) {
			return;
		}

		List<DetailAST> literalIfDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_IF);

		for (DetailAST literalIfDetailAST : literalIfDetailASTList) {
			if (_hasUnnecessaryIfStatement(literalIfDetailAST)) {
				log(detailAST, _MSG_REMOVE_UNNECESSARY_METHOD);

				return;
			}
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.contains(
				"liferay-portal/portal-impl/src/com/liferay/portal/upgrade" +
					"/v6_") ||
			absolutePath.contains(
				"liferay-portal/portal-impl/src/com/liferay/portal/upgrade" +
					"/v7_0_")) {

			return;
		}

		List<DetailAST> methodDefDetailASTList = getAllChildTokens(
			detailAST, false, TokenTypes.METHOD_DEF);

		DetailAST methodDefDetailAST = null;

		for (DetailAST curDetailAST : methodDefDetailASTList) {
			DetailAST iDentDetailAST = curDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if (StringUtil.equals(iDentDetailAST.getText(), "doUpgrade")) {
				methodDefDetailAST = curDetailAST;

				break;
			}
		}

		if ((methodDefDetailAST == null) ||
			((methodDefDetailASTList.size() == 1) &&
			 !_checkIfDeleted(detailAST, methodDefDetailAST))) {

			return;
		}

		DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		if (sListDetailAST.getChildCount() == 1) {
			return;
		}

		DetailAST firstChildDetailAST = sListDetailAST.getFirstChild();

		DetailAST nextDetailAST = firstChildDetailAST;

		if (firstChildDetailAST.getType() == TokenTypes.EXPR) {
			nextDetailAST = _checkMethod(firstChildDetailAST, false);
		}

		if (nextDetailAST == null) {
			return;
		}

		while (nextDetailAST != null) {
			firstChildDetailAST = nextDetailAST;
			nextDetailAST = nextDetailAST.getNextSibling();
		}

		int tokenType = firstChildDetailAST.getType();

		while ((tokenType == TokenTypes.SEMI) ||
			   (tokenType == TokenTypes.RCURLY)) {

			firstChildDetailAST = firstChildDetailAST.getPreviousSibling();

			tokenType = firstChildDetailAST.getType();
		}

		if (tokenType == TokenTypes.EXPR) {
			_checkMethod(firstChildDetailAST, true);
		}
	}

	private boolean _checkIfDeleted(
		DetailAST detailAST, DetailAST methodDefDetailAST) {

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		DetailAST childDetailAST = objBlockDetailAST.getFirstChild();

		int objCount = 0;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.LCURLY) &&
				(tokenType != TokenTypes.RCURLY)) {

				objCount++;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		if (objCount != 1) {
			return true;
		}

		DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		childDetailAST = sListDetailAST.getFirstChild();

		int methodCount = 0;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.SEMI) &&
				(tokenType != TokenTypes.RCURLY)) {

				return true;
			}

			if ((tokenType == TokenTypes.SEMI) ||
				(tokenType == TokenTypes.RCURLY)) {

				childDetailAST = childDetailAST.getNextSibling();

				continue;
			}

			methodCount++;

			DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return true;
			}

			String methodName = getMethodName(firstChildDetailAST);

			if (!StringUtil.equals(methodName, "alterColumnName") &&
				!StringUtil.equals(methodName, "alterColumnType") &&
				!StringUtil.equals(methodName, "alterTableAddColumn") &&
				!StringUtil.equals(methodName, "alterTableDropColumn")) {

				return true;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		if (methodCount != 0) {
			log(detailAST, _MSG_DELETE_CLASS);

			return false;
		}

		return true;
	}

	private DetailAST _checkMethod(DetailAST detailAST, boolean lastLine) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return detailAST;
		}

		String methodName = getMethodName(firstChildDetailAST);

		if ((!StringUtil.equals(methodName, "alterColumnName") &&
			 !StringUtil.equals(methodName, "alterColumnType") &&
			 !StringUtil.equals(methodName, "alterTableAddColumn") &&
			 !StringUtil.equals(methodName, "alterTableDropColumn")) ||
			!_checkParameters(firstChildDetailAST)) {

			return detailAST;
		}

		int startLine = getStartLineNumber(detailAST);
		int endLine = getEndLineNumber(detailAST);

		DetailAST nextDetailAST = _getDetailAST(detailAST, lastLine);

		boolean alertMessage = false;

		while (nextDetailAST != null) {
			int tokenType = nextDetailAST.getType();

			if ((tokenType == TokenTypes.SEMI) ||
				(tokenType == TokenTypes.RCURLY)) {

				nextDetailAST = _getDetailAST(nextDetailAST, lastLine);

				continue;
			}

			if (tokenType != TokenTypes.EXPR) {
				alertMessage = true;

				break;
			}

			DetailAST childDetailAST = nextDetailAST.getFirstChild();

			if (childDetailAST.getType() != TokenTypes.METHOD_CALL) {
				alertMessage = true;

				break;
			}

			String currentMethodName = getMethodName(childDetailAST);

			if ((!StringUtil.equals(currentMethodName, "alterColumnName") &&
				 !StringUtil.equals(currentMethodName, "alterColumnType") &&
				 !StringUtil.equals(currentMethodName, "alterTableAddColumn") &&
				 !StringUtil.equals(
					 currentMethodName, "alterTableDropColumn")) ||
				!_checkParameters(childDetailAST)) {

				alertMessage = true;

				break;
			}

			if (lastLine) {
				startLine = getStartLineNumber(nextDetailAST);
			}
			else {
				endLine = getEndLineNumber(nextDetailAST);
			}

			nextDetailAST = _getDetailAST(nextDetailAST, lastLine);
		}

		String errorMessageMethod = "getPreUpgradeSteps";

		if (lastLine) {
			errorMessageMethod = "getPostUpgradeSteps";
		}

		if (alertMessage) {
			log(
				detailAST, _MSG_REPLACE_METHOD, startLine, endLine,
				errorMessageMethod);
		}

		return nextDetailAST;
	}

	private boolean _checkParameters(DetailAST detailAST) {
		List<String> parameters = _getParameterList(detailAST);

		return ListUtil.isNotEmpty(parameters);
	}

	private DetailAST _getDetailAST(DetailAST detailAST, boolean lastLine) {
		if (lastLine) {
			return detailAST.getPreviousSibling();
		}

		return detailAST.getNextSibling();
	}

	private List<String> _getParameterList(DetailAST detailAST) {
		DetailAST eListDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		DetailAST firstChildDetailAST = eListDetailAST.getFirstChild();

		List<String> parameters = new ArrayList<>();

		while (firstChildDetailAST != null) {
			int tokenType = firstChildDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.COMMA)) {

				return null;
			}

			if (tokenType == TokenTypes.EXPR) {
				DetailAST childDetailAST = firstChildDetailAST.getFirstChild();

				if (childDetailAST.getType() != TokenTypes.STRING_LITERAL) {
					return null;
				}

				parameters.add(childDetailAST.getText());
			}

			firstChildDetailAST = firstChildDetailAST.getNextSibling();
		}

		return parameters;
	}

	private String _getParameterName(DetailAST detailAST) {
		if (detailAST == null) {
			return null;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.STRING_LITERAL) {
			return null;
		}

		return firstChildDetailAST.getText();
	}

	private boolean _hasSameTableNameAndColumnName(
		DetailAST detailAST, String talbeName, String columnName) {

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			return false;
		}

		DetailAST elistDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR) ||
			!StringUtil.equals(
				talbeName, _getParameterName(firstChildDetailAST))) {

			return false;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.COMMA)) {

			return false;
		}

		nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.EXPR) ||
			!StringUtil.equals(
				columnName, _getParameterName(nextSiblingDetailAST))) {

			return false;
		}

		return true;
	}

	private boolean _hasUnnecessaryIfStatement(DetailAST detailAST) {
		DetailAST exprDetailAST = detailAST.findFirstToken(TokenTypes.EXPR);

		if (exprDetailAST.getChildCount() != 1) {
			return false;
		}

		DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return false;
		}

		String methodName = getMethodName(firstChildDetailAST);

		if (!methodName.equals("hasColumn") &&
			!methodName.equals("hasColumnType")) {

			return false;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		if (exprDetailASTList.size() < 2) {
			return false;
		}

		String columnName = _getParameterName(exprDetailASTList.get(1));
		String talbeName = _getParameterName(exprDetailASTList.get(0));

		if (Validator.isNull(columnName) || Validator.isNull(talbeName)) {
			return false;
		}

		DetailAST slistDetailAST = detailAST.findFirstToken(TokenTypes.SLIST);

		if (slistDetailAST == null) {
			return false;
		}

		DetailAST lastChildDetailAST = slistDetailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.RCURLY) {
			return false;
		}

		DetailAST previousSiblingDetailAST =
			lastChildDetailAST.getPreviousSibling();

		while (previousSiblingDetailAST != null) {
			if ((previousSiblingDetailAST.getType() != TokenTypes.EXPR) &&
				(previousSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return false;
			}

			if (previousSiblingDetailAST.getType() == TokenTypes.EXPR) {
				firstChildDetailAST = previousSiblingDetailAST.getFirstChild();

				if ((firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) ||
					!ArrayUtil.contains(
						_ALTER_METHOD_NAMES,
						getMethodName(firstChildDetailAST)) ||
					!_hasSameTableNameAndColumnName(
						firstChildDetailAST, talbeName, columnName)) {

					return false;
				}
			}

			previousSiblingDetailAST =
				previousSiblingDetailAST.getPreviousSibling();
		}

		return true;
	}

	private boolean _isUpgradeProcess(DetailAST detailAST) {
		DetailAST extendsClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.EXTENDS_CLAUSE);

		if (extendsClauseDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = extendsClauseDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return false;
		}

		String extendsClassName = getName(extendsClauseDetailAST);

		if (!extendsClassName.equals("UpgradeProcess")) {
			return false;
		}

		return true;
	}

	private static final String[] _ALTER_METHOD_NAMES = {
		"alterColumnName", "alterColumnType", "alterTableAddColumn",
		"alterTableDropColumn"
	};

	private static final String _MSG_DELETE_CLASS = "delete.class";

	private static final String _MSG_REMOVE_UNNECESSARY_METHOD =
		"remove.unnecessary.method";

	private static final String _MSG_REPLACE_METHOD = "replace.method";

}