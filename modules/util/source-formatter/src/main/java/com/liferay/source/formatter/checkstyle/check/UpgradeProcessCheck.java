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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;

/**
 * @author Qi Zhang
 */
public class UpgradeProcessCheck extends BaseCheck {

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

		for (DetailAST literalIfDetailAST :
				getAllChildTokens(detailAST, true, TokenTypes.LITERAL_IF)) {

			if (_isUnnecessaryIfStatement(literalIfDetailAST)) {
				log(literalIfDetailAST, _MSG_UNNECESSARY_IF_STATEMENT);

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

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		List<DetailAST> methodDefDetailASTList = getAllChildTokens(
			objBlockDetailAST, false, TokenTypes.METHOD_DEF);

		DetailAST doUpgradeMethodDefDetailAST = null;

		for (DetailAST methodDefDetailAST : methodDefDetailASTList) {
			if (StringUtil.equals(getName(methodDefDetailAST), "doUpgrade") &&
				AnnotationUtil.containsAnnotation(
					methodDefDetailAST, "Override")) {

				doUpgradeMethodDefDetailAST = methodDefDetailAST;

				break;
			}
		}

		if (doUpgradeMethodDefDetailAST == null) {
			return;
		}

		DetailAST slistDetailAST = doUpgradeMethodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		if (slistDetailAST.getChildCount() == 1) {
			return;
		}

		if ((methodDefDetailASTList.size() == 1) &&
			_isUnnecessaryUpgradeProcessClass(slistDetailAST)) {

			log(
				detailAST, _MSG_UNNECESSARY_CLASS,
				JavaSourceUtil.getClassName(absolutePath));

			return;
		}

		_checkPostUpgradeSteps(slistDetailAST);
		_checkPreUpgradeSteps(slistDetailAST);
	}

	private void _checkPostUpgradeSteps(DetailAST detailAST) {
		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.RCURLY) {
			return;
		}

		DetailAST previousSiblingDetailAST =
			lastChildDetailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		previousSiblingDetailAST =
			previousSiblingDetailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		DetailAST firstChildDetailAST =
			previousSiblingDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return;
		}

		if (ArrayUtil.contains(
				_ALTER_METHOD_NAMES, getMethodName(firstChildDetailAST)) &&
			(previousSiblingDetailAST.getPreviousSibling() != null)) {

			log(
				firstChildDetailAST,
				_MSG_MOVE_UPGRADE_STEP_INSIDE_POST_UPGRADE_STEPS);
		}
	}

	private void _checkPreUpgradeSteps(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return;
		}

		if (ArrayUtil.contains(
				_ALTER_METHOD_NAMES, getMethodName(firstChildDetailAST))) {

			log(
				firstChildDetailAST,
				_MSG_MOVE_UPGRADE_STEP_INSIDE_PRE_UPGRADE_STEPS);
		}
	}

	private boolean _containsOnlyAlterMethodCalls(
		DetailAST detailAST, String talbeName, String columnName) {

		if (detailAST == null) {
			return false;
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

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
				DetailAST firstChildDetailAST =
					previousSiblingDetailAST.getFirstChild();

				if ((firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) ||
					!ArrayUtil.contains(
						_ALTER_METHOD_NAMES,
						getMethodName(firstChildDetailAST))) {

					return false;
				}

				if (Validator.isNotNull(columnName) &&
					Validator.isNotNull(talbeName) &&
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

	private boolean _isUnnecessaryIfStatement(DetailAST detailAST) {
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

		return _containsOnlyAlterMethodCalls(
			detailAST.findFirstToken(TokenTypes.SLIST), talbeName, columnName);
	}

	private boolean _isUnnecessaryUpgradeProcessClass(DetailAST detailAST) {
		return _containsOnlyAlterMethodCalls(detailAST, null, null);
	}

	private boolean _isUpgradeProcess(DetailAST detailAST) {
		DetailAST extendsClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.EXTENDS_CLAUSE);

		if (extendsClauseDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = extendsClauseDetailAST.getFirstChild();

		if ((firstChildDetailAST.getType() != TokenTypes.IDENT) ||
			!StringUtil.equals(
				getName(extendsClauseDetailAST), "UpgradeProcess")) {

			return false;
		}

		return true;
	}

	private static final String[] _ALTER_METHOD_NAMES = {
		"alterColumnName", "alterColumnType", "alterTableAddColumn",
		"alterTableDropColumn"
	};

	private static final String
		_MSG_MOVE_UPGRADE_STEP_INSIDE_POST_UPGRADE_STEPS =
			"upgrade.step.move.inside.post.upgrade.steps";

	private static final String
		_MSG_MOVE_UPGRADE_STEP_INSIDE_PRE_UPGRADE_STEPS =
			"upgrade.step.move.inside.pre.upgrade.steps";

	private static final String _MSG_UNNECESSARY_CLASS = "class.unnecessary";

	private static final String _MSG_UNNECESSARY_IF_STATEMENT =
		"if.statement.unnecessary";

}