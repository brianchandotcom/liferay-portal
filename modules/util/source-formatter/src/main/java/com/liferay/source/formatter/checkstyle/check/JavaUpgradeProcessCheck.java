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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Qi Zhang
 */
public class JavaUpgradeProcessCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.CLASS_DEF) {
			_checkIfDeleted(detailAST);

			return;
		}

		DetailAST identDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (!StringUtil.equals(identDetailAST.getText(), "doUpgrade") ||
			!_checkExtends(detailAST)) {

			return;
		}

		DetailAST sListDetailAST = detailAST.findFirstToken(TokenTypes.SLIST);

		if (sListDetailAST.getChildCount() == 1) {
			return;
		}

		DetailAST firstChildDetailAST = sListDetailAST.getFirstChild();

		DetailAST nextDetailAST = firstChildDetailAST;

		if (firstChildDetailAST.getType() == TokenTypes.EXPR) {
			nextDetailAST = _checkMethod(firstChildDetailAST, false);
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

	private boolean _checkExtends(DetailAST detailAST) {
		DetailAST classDefDetailAST;

		if (detailAST.getType() == TokenTypes.CLASS_DEF) {
			classDefDetailAST = detailAST;
		}
		else {
			classDefDetailAST = getParentWithTokenType(
				detailAST, TokenTypes.CLASS_DEF);
		}

		DetailAST extendsDetailAST = classDefDetailAST.findFirstToken(
			TokenTypes.EXTENDS_CLAUSE);

		if (extendsDetailAST == null) {
			return false;
		}

		List<DetailAST> extendsDetailASTs = getAllChildTokens(
			extendsDetailAST, false, TokenTypes.IDENT);

		Stream<DetailAST> extendsDetailASTsStream = extendsDetailASTs.stream();

		return extendsDetailASTsStream.anyMatch(
			e -> StringUtil.equals(e.getText(), "UpgradeProcess"));
	}

	private void _checkIfDeleted(DetailAST detailAST) {
		if (!_checkExtends(detailAST)) {
			return;
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.contains(
				"liferay-portal/portal-impl/src/com/liferay/portal/upgrade")) {

			return;
		}

		List<DetailAST> methodDefDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_DEF);

		if (ListUtil.isEmpty(methodDefDetailASTs) ||
			(methodDefDetailASTs.size() > 1)) {

			return;
		}

		DetailAST methodDefDetailAST = methodDefDetailASTs.get(0);

		DetailAST identDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (!StringUtil.equals("doUpgrade", identDetailAST.getText())) {
			return;
		}

		DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		DetailAST childDetailAST = sListDetailAST.getFirstChild();

		String currentMethodName = null;
		String parameter1 = null;

		int methodCount = 0;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.SEMI) &&
				(tokenType != TokenTypes.RCURLY)) {

				return;
			}

			if ((tokenType == TokenTypes.SEMI) ||
				(tokenType == TokenTypes.RCURLY)) {

				childDetailAST = childDetailAST.getNextSibling();

				continue;
			}

			methodCount++;

			DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return;
			}

			String methodName = getMethodName(firstChildDetailAST);

			if (!StringUtil.equals(methodName, "alterColumnName") &&
				!StringUtil.equals(methodName, "alterColumnType") &&
				!StringUtil.equals(methodName, "alterTableAddColumn") &&
				!StringUtil.equals(methodName, "alterTableDropColumn")) {

				return;
			}

			List<String> parameters = _getParameterList(firstChildDetailAST);

			if (ListUtil.isEmpty(parameters)) {
				return;
			}

			if (Validator.isNull(currentMethodName)) {
				currentMethodName = methodName;

				parameter1 = parameters.get(0);
			}
			else {
				if (StringUtil.equals(currentMethodName, "alterColumnName") ||
					StringUtil.equals(currentMethodName, "alterColumnType") ||
					!StringUtil.equals(currentMethodName, methodName) ||
					!StringUtil.equals(parameters.get(0), parameter1)) {

					return;
				}
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		if (methodCount != 0) {
			log(detailAST, _MSG_DELETE_CLASS);
		}
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

	private static final String _MSG_DELETE_CLASS = "delete.class";

	private static final String _MSG_REPLACE_METHOD = "replace.method";

}