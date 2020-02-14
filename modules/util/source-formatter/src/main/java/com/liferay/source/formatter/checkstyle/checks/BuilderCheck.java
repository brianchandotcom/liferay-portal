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

import com.liferay.portal.kernel.util.ArrayUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public abstract class BuilderCheck extends ChainedMethodCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.ASSIGN, TokenTypes.INSTANCE_INIT, TokenTypes.METHOD_CALL
		};
	}

	protected abstract boolean allowNullValues();

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (isExcludedPath(_RUN_OUTSIDE_PORTAL_EXCLUDES)) {
			return;
		}

		if (detailAST.getType() == TokenTypes.INSTANCE_INIT) {
			_checkAnonymousClass(detailAST);

			return;
		}

		if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			_checkBuilder(detailAST);

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.EXPR) &&
			(parentDetailAST.getType() != TokenTypes.VARIABLE_DEF)) {

			return;
		}

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		String variableName = getVariableName(detailAST, parentDetailAST);

		if (variableName == null) {
			return;
		}

		_checkNewInstance(
			detailAST, variableName, parentDetailAST, nextSiblingDetailAST);
	}

	protected abstract List<BuilderInformation> getBuilderInformationList();

	protected static class BuilderInformation {

		public BuilderInformation(
			String className, String builderClassName, String... methodNames) {

			_className = className;
			_builderClassName = builderClassName;
			_methodNames = methodNames;
		}

		public String getBuilderClassName() {
			return _builderClassName;
		}

		public String getClassName() {
			return _className;
		}

		public String[] getMethodNames() {
			return _methodNames;
		}

		private final String _builderClassName;
		private final String _className;
		private final String[] _methodNames;

	}

	private void _checkAnonymousClass(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST identDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST == null) {
			return;
		}

		String className = identDetailAST.getText();

		List<String> typeNames = getAttributeValues(_TYPE_NAMES_KEY);

		if (!typeNames.contains(className)) {
			return;
		}

		List<DetailAST> childDetailASTList = getAllChildTokens(
			detailAST, true, ALL_TYPES);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (getHiddenBefore(childDetailAST) != null) {
				return;
			}
		}

		BuilderInformation builderInformation =
			_findBuilderInformationByClassName(className);

		if (builderInformation == null) {
			return;
		}

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.SLIST) {
				continue;
			}

			DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			if (!ArrayUtil.contains(
					builderInformation.getMethodNames(),
					firstChildDetailAST.getText())) {

				return;
			}

			if (!allowNullValues()) {
				DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
					TokenTypes.ELIST);

				DetailAST childDetailAST = elistDetailAST.getFirstChild();

				while (true) {
					if (childDetailAST == null) {
						break;
					}

					if (_isNullValueExpression(childDetailAST)) {
						return;
					}

					childDetailAST = childDetailAST.getNextSibling();
				}
			}

			parentDetailAST = getParentWithTokenType(
				methodCallDetailAST, TokenTypes.DO_WHILE, TokenTypes.LAMBDA,
				TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE);

			if ((parentDetailAST != null) &&
				(detailAST.getLineNo() <= parentDetailAST.getLineNo())) {

				return;
			}
		}

		log(
			detailAST, _MSG_USE_BUILDER_INSTEAD,
			builderInformation.getBuilderClassName(), className);
	}

	private void _checkBuilder(DetailAST methodCallDetailAST) {
		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String builderClassName = firstChildDetailAST.getText();

		BuilderInformation builderInformation =
			_findBuilderInformationByBuilderClassName(builderClassName);

		if (builderInformation == null) {
			return;
		}

		String className = builderInformation.getClassName();

		List<String> typeNames = getAttributeValues(_TYPE_NAMES_KEY);

		if (!typeNames.contains(className)) {
			return;
		}

		List<DetailAST> methodVariableDetailASTList =
			_getMethodVariableDetailASTList(methodCallDetailAST);

		if (!allowNullValues()) {
			_checkNullValues(methodVariableDetailASTList, builderClassName);
		}

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while ((parentDetailAST.getType() == TokenTypes.DOT) ||
			   (parentDetailAST.getType() == TokenTypes.EXPR) ||
			   (parentDetailAST.getType() == TokenTypes.METHOD_CALL)) {

			parentDetailAST = parentDetailAST.getParent();
		}

		if (parentDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
			_checkInline(
				parentDetailAST, methodVariableDetailASTList, builderClassName);
		}

		if (parentDetailAST.getType() != TokenTypes.ASSIGN) {
			return;
		}

		DetailAST assignDetailAST = parentDetailAST;

		parentDetailAST = assignDetailAST.getParent();

		DetailAST grandParentDetailAST = parentDetailAST.getParent();

		if (grandParentDetailAST.getType() == TokenTypes.OBJBLOCK) {
			DetailAST greatGrandParentDetailAST =
				grandParentDetailAST.getParent();

			if (greatGrandParentDetailAST.getType() == TokenTypes.CLASS_DEF) {
				return;
			}
		}

		_checkInline(
			parentDetailAST, methodVariableDetailASTList, builderClassName);

		firstChildDetailAST = assignDetailAST.getFirstChild();

		DetailAST assignValueDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			assignValueDetailAST = firstChildDetailAST.getNextSibling();
		}
		else {
			assignValueDetailAST = firstChildDetailAST.getFirstChild();
		}

		if ((assignValueDetailAST == null) ||
			(assignValueDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return;
		}

		List<String> variableNames = _getVariableNames(
			parentDetailAST, "get.*");

		String variableName = getVariableName(assignDetailAST, parentDetailAST);

		variableNames.add(variableName);

		String[] builderMethodNames = builderInformation.getMethodNames();

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return;
			}

			FullIdent fullIdent = getMethodCallFullIdent(
				nextSiblingDetailAST, variableName, builderMethodNames);

			if (fullIdent != null) {
				log(
					assignDetailAST, _MSG_INCLUDE_BUILDER, fullIdent.getText(),
					fullIdent.getLineNo(), builderClassName,
					assignDetailAST.getLineNo());

				return;
			}

			for (String curVariableName : variableNames) {
				if (containsVariableName(
						nextSiblingDetailAST, curVariableName)) {

					return;
				}
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
	}

	private void _checkInline(
		DetailAST parentDetailAST, List<DetailAST> methodVariableDetailASTList,
		String builderClassName) {

		if (!isAttributeValue(_CHECK_INLINE)) {
			return;
		}

		List<String> followingVariableNames = new ArrayList<>();

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				break;
			}

			followingVariableNames.addAll(
				_getVariableNames(nextSiblingDetailAST));

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}

		List<String> inBetweenVariableNames = new ArrayList<>();

		DetailAST previousSiblingDetailAST =
			parentDetailAST.getPreviousSibling();

		while (true) {
			if (previousSiblingDetailAST == null) {
				return;
			}

			if (previousSiblingDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
				followingVariableNames.addAll(
					_getVariableNames(previousSiblingDetailAST));

				inBetweenVariableNames.addAll(
					_getVariableNames(previousSiblingDetailAST, "get.*"));

				previousSiblingDetailAST =
					previousSiblingDetailAST.getPreviousSibling();

				continue;
			}

			DetailAST identDetailAST = previousSiblingDetailAST.findFirstToken(
				TokenTypes.IDENT);

			String name = identDetailAST.getText();

			DetailAST matchingMethodVariableDetailAST = _getExprDetailAST(
				methodVariableDetailASTList, name);

			if (!followingVariableNames.contains(name) &&
				(matchingMethodVariableDetailAST != null) &&
				!_referencesNonfinalVariable(previousSiblingDetailAST)) {

				List<String> variableNames = _getVariableNames(
					previousSiblingDetailAST);

				boolean contains = false;

				for (String variableName : variableNames) {
					if (inBetweenVariableNames.contains(variableName)) {
						contains = true;

						break;
					}
				}

				if (!contains) {
					log(
						identDetailAST, _MSG_INLINE_BUILDER, name,
						identDetailAST.getLineNo(), builderClassName,
						parentDetailAST.getLineNo());
				}
			}

			followingVariableNames.addAll(
				_getVariableNames(previousSiblingDetailAST));

			inBetweenVariableNames.addAll(
				_getVariableNames(previousSiblingDetailAST, "get.*"));

			previousSiblingDetailAST =
				previousSiblingDetailAST.getPreviousSibling();
		}
	}

	private void _checkNewInstance(
		DetailAST detailAST, String variableName, DetailAST parentDetailAST,
		DetailAST nextSiblingDetailAST) {

		String newInstanceTypeName = _getNewInstanceTypeName(
			detailAST, parentDetailAST);

		if (newInstanceTypeName == null) {
			return;
		}

		List<String> typeNames = getAttributeValues(_TYPE_NAMES_KEY);

		if (!typeNames.contains(newInstanceTypeName)) {
			return;
		}

		BuilderInformation builderInformation =
			_findBuilderInformationByClassName(newInstanceTypeName);

		if (builderInformation == null) {
			return;
		}

		while (true) {
			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if (nextSiblingDetailAST == null) {
				return;
			}

			FullIdent fullIdent = getMethodCallFullIdent(
				nextSiblingDetailAST, variableName,
				builderInformation.getMethodNames());

			if (fullIdent != null) {
				DetailAST methodCallDetailAST =
					nextSiblingDetailAST.findFirstToken(TokenTypes.METHOD_CALL);

				DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
					TokenTypes.ELIST);

				DetailAST childDetailAST = elistDetailAST.getFirstChild();

				while (true) {
					if (childDetailAST == null) {
						log(
							detailAST, _MSG_USE_BUILDER,
							builderInformation.getBuilderClassName(),
							detailAST.getLineNo(), fullIdent.getLineNo());

						return;
					}

					if (!allowNullValues() &&
						_isNullValueExpression(childDetailAST)) {

						return;
					}

					childDetailAST = childDetailAST.getNextSibling();
				}
			}

			if (containsVariableName(nextSiblingDetailAST, variableName)) {
				return;
			}
		}
	}

	private void _checkNullValues(
		List<DetailAST> methodVariableDetailASTList, String builderClassName) {

		for (DetailAST methodVariableDetailAST : methodVariableDetailASTList) {
			if (_isNullValueExpression(methodVariableDetailAST)) {
				log(
					methodVariableDetailAST, _MSG_INCORRECT_NULL_VALUE,
					builderClassName);
			}
		}
	}

	private BuilderInformation _findBuilderInformationByBuilderClassName(
		String builderClassName) {

		for (BuilderInformation builderInformation :
				getBuilderInformationList()) {

			if (builderClassName.equals(
					builderInformation.getBuilderClassName())) {

				return builderInformation;
			}
		}

		return null;
	}

	private BuilderInformation _findBuilderInformationByClassName(
		String className) {

		for (BuilderInformation builderInformation :
				getBuilderInformationList()) {

			if (className.equals(builderInformation.getClassName())) {
				return builderInformation;
			}
		}

		return null;
	}

	private DetailAST _getExprDetailAST(
		List<DetailAST> exprDetailASTList, String name) {

		DetailAST exprDetailAST = null;

		for (DetailAST curExprDetailAST : exprDetailASTList) {
			List<String> variableNames = _getVariableNames(curExprDetailAST);

			if (variableNames.contains(name)) {
				if (exprDetailAST != null) {
					return null;
				}

				exprDetailAST = curExprDetailAST;
			}
		}

		return exprDetailAST;
	}

	private List<DetailAST> _getMethodVariableDetailASTList(
		DetailAST methodCallDetailAST) {

		List<DetailAST> exprDetailASTList = new ArrayList<>();

		while (true) {
			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			DetailAST childDetailAST = elistDetailAST.getFirstChild();

			while (true) {
				if (childDetailAST == null) {
					break;
				}

				if (childDetailAST.getType() != TokenTypes.COMMA) {
					exprDetailASTList.add(childDetailAST);
				}

				childDetailAST = childDetailAST.getNextSibling();
			}

			DetailAST parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				return exprDetailASTList;
			}

			methodCallDetailAST = parentDetailAST.getParent();
		}
	}

	private String _getNewInstanceTypeName(
		DetailAST assignDetailAST, DetailAST parentDetailAST) {

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		DetailAST assignValueDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			assignValueDetailAST = firstChildDetailAST.getNextSibling();
		}
		else {
			assignValueDetailAST = firstChildDetailAST.getFirstChild();
		}

		if ((assignValueDetailAST == null) ||
			(assignValueDetailAST.getType() != TokenTypes.LITERAL_NEW)) {

			return null;
		}

		DetailAST identDetailAST = assignValueDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST == null) {
			return null;
		}

		DetailAST elistDetailAST = assignValueDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if ((elistDetailAST == null) ||
			(elistDetailAST.getFirstChild() != null)) {

			return null;
		}

		return identDetailAST.getText();
	}

	private List<String> _getVariableNames(DetailAST detailAST) {
		return _getVariableNames(detailAST, null);
	}

	private List<String> _getVariableNames(
		DetailAST detailAST, String excludeRegex) {

		List<String> variableNames = new ArrayList<>();

		List<DetailAST> identDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			String variableName = identDetailAST.getText();

			if (!variableName.matches("[a-z_].*")) {
				continue;
			}

			DetailAST parentDetailAST = identDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.ASSIGN) ||
				(parentDetailAST.getType() == TokenTypes.EXPR) ||
				(parentDetailAST.getType() == TokenTypes.TYPECAST) ||
				ArrayUtil.contains(
					ARITHMETIC_OPERATOR_TOKEN_TYPES,
					parentDetailAST.getType()) ||
				ArrayUtil.contains(
					RELATIONAL_OPERATOR_TOKEN_TYPES,
					parentDetailAST.getType())) {

				variableNames.add(variableName);
			}
			else if (parentDetailAST.getType() == TokenTypes.DOT) {
				DetailAST nextSiblingDetailAST =
					identDetailAST.getNextSibling();

				if (nextSiblingDetailAST != null) {
					if ((nextSiblingDetailAST.getType() != TokenTypes.IDENT) ||
						(excludeRegex == null)) {

						variableNames.add(variableName);
					}
					else {
						String s = nextSiblingDetailAST.getText();

						if (!s.matches(excludeRegex)) {
							variableNames.add(variableName);
						}
					}
				}
			}
		}

		return variableNames;
	}

	private boolean _isNullValueExpression(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NULL) {
			return true;
		}

		if (firstChildDetailAST.getType() == TokenTypes.TYPECAST) {
			DetailAST lastChildDetailAST = firstChildDetailAST.getLastChild();

			if (lastChildDetailAST.getType() == TokenTypes.LITERAL_NULL) {
				return true;
			}
		}

		return false;
	}

	private boolean _referencesNonfinalVariable(DetailAST detailAST) {
		List<String> variableNames = _getVariableNames(detailAST);

		DetailAST previousDetailAST = detailAST.getPreviousSibling();
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (previousDetailAST != null) {
				if (previousDetailAST.getType() == TokenTypes.EXPR) {
					DetailAST firstChildDetailAST =
						previousDetailAST.getFirstChild();

					if (firstChildDetailAST.getType() == TokenTypes.ASSIGN) {
						DetailAST identDetailAST =
							firstChildDetailAST.findFirstToken(
								TokenTypes.IDENT);

						if (variableNames.contains(identDetailAST.getText())) {
							return true;
						}
					}
				}

				previousDetailAST = previousDetailAST.getPreviousSibling();

				continue;
			}

			if (parentDetailAST == null) {
				return false;
			}

			previousDetailAST = parentDetailAST;

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private static final String _CHECK_INLINE = "checkInline";

	private static final String _MSG_INCLUDE_BUILDER = "builder.include";

	private static final String _MSG_INCORRECT_NULL_VALUE =
		"null.value.incorrect";

	private static final String _MSG_INLINE_BUILDER = "builder.inline";

	private static final String _MSG_USE_BUILDER = "builder.use";

	private static final String _MSG_USE_BUILDER_INSTEAD =
		"builder.use.instead";

	private static final String _RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

	private static final String _TYPE_NAMES_KEY = "typeNames";

}