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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public abstract class BaseBuilderCheck extends BaseChainedMethodCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.ASSIGN, TokenTypes.INSTANCE_INIT, TokenTypes.METHOD_CALL
		};
	}

	protected abstract boolean allowNullValues();

	protected abstract List<BuilderInformation> doGetBuilderInformationList();

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES) ||
			ListUtil.isEmpty(getAttributeValues(_ENFORCE_BUILDER_NAMES_KEY))) {

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

		if (variableName != null) {
			_checkAssignVariableStatement(
				detailAST, variableName, nextSiblingDetailAST);
		}
	}

	protected abstract String getAssignClassName(DetailAST assignDetailAST);

	protected List<String> getAvoidCastStringMethodNames() {
		return Collections.emptyList();
	}

	protected List<BuilderInformation> getBuilderInformationList() {
		List<BuilderInformation> builderInformationList =
			doGetBuilderInformationList();

		List<String> enforceBuilderNames = getAttributeValues(
			_ENFORCE_BUILDER_NAMES_KEY);

		return ListUtil.filter(
			builderInformationList,
			builderInformation -> enforceBuilderNames.contains(
				builderInformation.getBuilderClassName()));
	}

	protected String getNewInstanceTypeName(DetailAST assignDetailAST) {
		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		DetailAST assignValueDetailAST = null;

		DetailAST parentDetailAST = assignDetailAST.getParent();

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

	protected Map<String, String[][]> getReservedKeywordsMap() {
		return Collections.emptyMap();
	}

	protected abstract List<String> getSupportsFunctionMethodNames();

	protected abstract boolean isSupportsNestedMethodCalls();

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

		BuilderInformation builderInformation =
			_findBuilderInformationByClassName(className);

		if (builderInformation == null) {
			return;
		}

		List<DetailAST> childDetailASTList = getAllChildTokens(
			detailAST, true, ALL_TYPES);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (getHiddenBefore(childDetailAST) != null) {
				return;
			}
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

			if (isSupportsNestedMethodCalls()) {
				parentDetailAST = getParentWithTokenType(
					methodCallDetailAST, TokenTypes.DO_WHILE, TokenTypes.LAMBDA,
					TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_TRY,
					TokenTypes.LITERAL_WHILE);

				if ((parentDetailAST != null) &&
					(detailAST.getLineNo() <= parentDetailAST.getLineNo())) {

					return;
				}

				parentDetailAST = getParentWithTokenType(
					methodCallDetailAST, TokenTypes.LITERAL_ELSE);

				if ((parentDetailAST != null) &&
					(detailAST.getLineNo() <= parentDetailAST.getLineNo())) {

					firstChildDetailAST = parentDetailAST.getFirstChild();

					if (firstChildDetailAST.getType() ==
							TokenTypes.LITERAL_IF) {

						return;
					}
				}
			}
			else if (!equals(parentDetailAST.getParent(), detailAST)) {
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
		}

		log(
			detailAST, _MSG_USE_BUILDER_INSTEAD,
			builderInformation.getBuilderClassName(), className);
	}

	private void _checkAssignVariableStatement(
		DetailAST assignDetailAST, String variableName,
		DetailAST nextSiblingDetailAST) {

		BuilderInformation builderInformation =
			_findBuilderInformationByClassName(
				getAssignClassName(assignDetailAST));

		if (builderInformation == null) {
			return;
		}

		while (true) {
			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if ((nextSiblingDetailAST == null) ||
				hasPrecedingPlaceholder(nextSiblingDetailAST)) {

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
							assignDetailAST, _MSG_USE_BUILDER,
							builderInformation.getBuilderClassName(),
							assignDetailAST.getLineNo(), fullIdent.getLineNo());

						return;
					}

					if ((!allowNullValues() &&
						 _isNullValueExpression(childDetailAST)) ||
						containsVariableName(childDetailAST, variableName)) {

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

		_checkUnneededCastString(methodCallDetailAST);

		Map<String, List<DetailAST>> expressionDetailASTMap =
			_getExpressionDetailASTMap(methodCallDetailAST, false);

		if (!allowNullValues()) {
			_checkNullValues(expressionDetailASTMap, builderClassName);
		}

		_checkReservedKeywords(expressionDetailASTMap);

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		int endLineNumber = getEndLineNumber(parentDetailAST);
		int startLineNumber = getStartLineNumber(parentDetailAST);

		while ((parentDetailAST.getType() == TokenTypes.DOT) ||
			   (parentDetailAST.getType() == TokenTypes.EXPR) ||
			   (parentDetailAST.getType() == TokenTypes.METHOD_CALL)) {

			endLineNumber = getEndLineNumber(parentDetailAST);
			startLineNumber = getStartLineNumber(parentDetailAST);

			parentDetailAST = parentDetailAST.getParent();
		}

		if (parentDetailAST.getType() == TokenTypes.ELIST) {
			while (true) {
				DetailAST grandParentDetailAST = parentDetailAST.getParent();

				if (grandParentDetailAST == null) {
					return;
				}

				if (grandParentDetailAST.getType() == TokenTypes.SLIST) {
					_checkInline(
						parentDetailAST, expressionDetailASTMap,
						builderClassName, startLineNumber, endLineNumber);

					return;
				}

				parentDetailAST = grandParentDetailAST;
			}
		}

		if (parentDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
			_checkInline(
				parentDetailAST, expressionDetailASTMap, builderClassName,
				startLineNumber, endLineNumber);

			return;
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
			parentDetailAST, expressionDetailASTMap, builderClassName,
			startLineNumber, endLineNumber);

		if (isJSPFile()) {
			return;
		}

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
		DetailAST variableDefinitionDetailAST, DetailAST parentDetailAST,
		String builderClassName, List<String> supportsFunctionMethodNames,
		Map<String, List<DetailAST>> expressionDetailASTMap,
		int startLineNumber, int endlineNumber) {

		DetailAST identDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String matchingMethodName = _getInlineExpressionMethodName(
			expressionDetailASTMap, ListUtil.fromArray(identDetailAST));

		if (!supportsFunctionMethodNames.contains(matchingMethodName) ||
			_referencesNonfinalVariable(variableDefinitionDetailAST)) {

			return;
		}

		List<DetailAST> dependentIdentDetailASTList =
			getDependentIdentDetailASTList(
				variableDefinitionDetailAST, startLineNumber);

		if (dependentIdentDetailASTList.isEmpty()) {
			return;
		}

		String variableName = identDetailAST.getText();

		for (int i = dependentIdentDetailASTList.size() - 1; i >= 0; i--) {
			DetailAST dependentIdentDetailAST = dependentIdentDetailASTList.get(
				i);

			if (variableName.equals(dependentIdentDetailAST.getText()) &&
				(dependentIdentDetailAST.getLineNo() > endlineNumber)) {

				return;
			}
		}

		matchingMethodName = _getInlineExpressionMethodName(
			expressionDetailASTMap, dependentIdentDetailASTList);

		if (matchingMethodName == null) {
			return;
		}

		List<DetailAST> additionalDependentDetailASTList =
			_getAdditionalDependentDetailASTList(
				dependentIdentDetailASTList,
				variableDefinitionDetailAST.getLineNo(), startLineNumber);

		if (additionalDependentDetailASTList.isEmpty()) {
			log(
				identDetailAST, _MSG_INLINE_BUILDER_1, identDetailAST.getText(),
				identDetailAST.getLineNo(), builderClassName, startLineNumber);

			return;
		}

		DetailAST lastAdditionalDependentDetailAST =
			additionalDependentDetailASTList.get(
				additionalDependentDetailASTList.size() - 1);

		if (lastAdditionalDependentDetailAST.getLineNo() >=
				parentDetailAST.getLineNo()) {

			return;
		}

		for (DetailAST additionalDependentDetailAST :
				additionalDependentDetailASTList) {

			List<DetailAST> assignDetailASTList = getAllChildTokens(
				additionalDependentDetailAST, true, TokenTypes.ASSIGN);

			for (DetailAST assignDetailAST : assignDetailASTList) {
				String assignVariableName = null;

				DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
					assignVariableName = firstChildDetailAST.getText();
				}
				else {
					DetailAST previousSiblingDetailAST =
						assignDetailAST.getPreviousSibling();

					if ((previousSiblingDetailAST != null) &&
						(previousSiblingDetailAST.getType() ==
							TokenTypes.IDENT)) {

						assignVariableName = previousSiblingDetailAST.getText();
					}
				}

				if (assignVariableName == null) {
					return;
				}

				if (!variableName.equals(assignVariableName)) {
					for (int i = dependentIdentDetailASTList.size() - 1; i >= 0;
						 i--) {

						DetailAST dependentIdentDetailAST =
							dependentIdentDetailASTList.get(i);

						if (assignVariableName.equals(
								dependentIdentDetailAST.getText()) &&
							(dependentIdentDetailAST.getLineNo() >
								endlineNumber)) {

							return;
						}
					}
				}
			}

			List<DetailAST> methodCallDetailASTList = getAllChildTokens(
				additionalDependentDetailAST, true, TokenTypes.METHOD_CALL);

			for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
				DetailAST firstChildDetailAST =
					methodCallDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() == TokenTypes.DOT) {
					FullIdent fullIdent = FullIdent.createFullIdent(
						firstChildDetailAST);

					String methodCall = fullIdent.getText();

					if (!methodCall.startsWith(variableName + ".") &&
						!methodCall.contains(".get")) {

						return;
					}
				}
				else if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
					String methodName = firstChildDetailAST.getText();

					if (!methodName.matches("_?get.*")) {
						return;
					}
				}
				else {
					return;
				}
			}
		}

		log(
			identDetailAST, _MSG_INLINE_BUILDER_2, variableName,
			identDetailAST.getLineNo(),
			_getLineNumbers(additionalDependentDetailASTList), builderClassName,
			startLineNumber);
	}

	private void _checkInline(
		DetailAST parentDetailAST,
		Map<String, List<DetailAST>> expressionDetailASTMap,
		String builderClassName, int startLineNumber, int endLineNumber) {

		if (!isAttributeValue(_CHECK_INLINE)) {
			return;
		}

		List<String> supportsFunctionMethodNames =
			getSupportsFunctionMethodNames();

		if (supportsFunctionMethodNames.isEmpty()) {
			return;
		}

		int branchStatementLineNumber = -1;

		List<DetailAST> branchingStatementDetailASTList = getAllChildTokens(
			parentDetailAST.getParent(), true, TokenTypes.LITERAL_BREAK,
			TokenTypes.LITERAL_CONTINUE, TokenTypes.LITERAL_RETURN);

		for (DetailAST branchingStatementDetailAST :
				branchingStatementDetailASTList) {

			int lineNumber = branchingStatementDetailAST.getLineNo();

			if (lineNumber >= startLineNumber) {
				break;
			}

			branchStatementLineNumber = lineNumber;
		}

		List<DetailAST> variableDefinitionDetailASTList = getAllChildTokens(
			parentDetailAST.getParent(), false, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			int lineNumber = variableDefinitionDetailAST.getLineNo();

			if (lineNumber >= startLineNumber) {
				return;
			}

			if (branchStatementLineNumber < lineNumber) {
				_checkInline(
					variableDefinitionDetailAST, parentDetailAST,
					builderClassName, supportsFunctionMethodNames,
					expressionDetailASTMap, startLineNumber, endLineNumber);
			}
		}
	}

	private void _checkNullValues(
		Map<String, List<DetailAST>> expressionDetailASTMap,
		String builderClassName) {

		for (Map.Entry<String, List<DetailAST>> entry :
				expressionDetailASTMap.entrySet()) {

			for (DetailAST expressionDetailAST : entry.getValue()) {
				if (_isNullValueExpression(expressionDetailAST)) {
					log(
						expressionDetailAST, _MSG_INCORRECT_NULL_VALUE,
						builderClassName);
				}
			}
		}
	}

	private void _checkReservedKeywords(
		Map<String, List<DetailAST>> expressionDetailASTMap) {

		Map<String, String[][]> reservedKeywordsMap = getReservedKeywordsMap();

		for (Map.Entry<String, String[][]> entry :
				reservedKeywordsMap.entrySet()) {

			String methodName = entry.getKey();

			List<DetailAST> expressionDetailASTList =
				expressionDetailASTMap.get(methodName);

			if (expressionDetailASTList == null) {
				continue;
			}

			for (DetailAST expressionDetailAST : expressionDetailASTList) {
				DetailAST previousDetailAST =
					expressionDetailAST.getPreviousSibling();

				if (previousDetailAST != null) {
					continue;
				}

				DetailAST firstChildDetailAST =
					expressionDetailAST.getFirstChild();

				String value = null;

				if (firstChildDetailAST.getType() ==
						TokenTypes.STRING_LITERAL) {

					value = StringUtil.removeChar(
						firstChildDetailAST.getText(), CharPool.QUOTE);
				}
				else if (firstChildDetailAST.getType() == TokenTypes.DOT) {
					FullIdent fullIdent = FullIdent.createFullIdent(
						firstChildDetailAST);

					value = fullIdent.getText();
				}
				else {
					continue;
				}

				String[][] reservedKeywordsArray = entry.getValue();

				for (String[] reservedKeywordArray : reservedKeywordsArray) {
					String reservedKey = reservedKeywordArray[0];

					if (value.equals(reservedKey)) {
						log(
							expressionDetailAST, _MSG_RESERVED_KEYWORD,
							methodName, value, reservedKeywordArray[1]);

						break;
					}
				}
			}
		}
	}

	private void _checkUnneededCastString(DetailAST methodCallDetailAST) {
		List<String> avoidCastStringMethodNames =
			getAvoidCastStringMethodNames();

		if (avoidCastStringMethodNames.isEmpty()) {
			return;
		}

		Map<String, List<DetailAST>> expressionDetailASTMap =
			_getExpressionDetailASTMap(methodCallDetailAST, true);

		for (String avoidCastStringMethodName :
				getAvoidCastStringMethodNames()) {

			List<DetailAST> expressionDetailASTList =
				expressionDetailASTMap.get(avoidCastStringMethodName);

			if (expressionDetailASTList == null) {
				continue;
			}

			outerLoop:
			for (DetailAST expressionDetailAST : expressionDetailASTList) {
				if (expressionDetailAST.getType() != TokenTypes.EXPR) {
					continue;
				}

				DetailAST childDetailAST = expressionDetailAST.getFirstChild();

				if (childDetailAST.getType() != TokenTypes.METHOD_CALL) {
					continue;
				}

				FullIdent fullIdent = FullIdent.createFullIdentBelow(
					childDetailAST);

				String methodCall = fullIdent.getText();

				if (!methodCall.equals("String.valueOf") &&
					!methodCall.endsWith(".toString")) {

					continue;
				}

				// LiferayPortletResponse.createActionURL and
				// LiferayPortletResponse.createRenderURL should be cast to
				// String to avoid compile errors.

				List<DetailAST> identDetailASTList = getAllChildTokens(
					expressionDetailAST, true, TokenTypes.IDENT);

				for (DetailAST identDetailAST : identDetailASTList) {
					String name = identDetailAST.getText();

					if (name.equals("createActionURL") ||
						name.equals("createRenderURL")) {

						continue outerLoop;
					}
				}

				log(
					expressionDetailAST, _MSG_UNNEEDED_STRING_CAST,
					avoidCastStringMethodName);
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

		if (className == null) {
			return null;
		}

		for (BuilderInformation builderInformation :
				getBuilderInformationList()) {

			if (className.equals(builderInformation.getClassName())) {
				return builderInformation;
			}
		}

		return null;
	}

	private List<DetailAST> _getAdditionalDependentDetailASTList(
		List<DetailAST> dependentIdentDetailASTList, int startLineNumber,
		int endLineNumber) {

		List<DetailAST> dependentDetailASTList = new ArrayList<>();

		for (DetailAST dependentIdentDetailAST : dependentIdentDetailASTList) {
			if (dependentIdentDetailAST.getLineNo() >= endLineNumber) {
				return dependentDetailASTList;
			}

			DetailAST detailAST = dependentIdentDetailAST;

			while (true) {
				DetailAST parentDetailAST = detailAST.getParent();

				if (parentDetailAST.getLineNo() < startLineNumber) {
					if (!dependentDetailASTList.contains(detailAST)) {
						dependentDetailASTList.add(detailAST);
					}

					break;
				}

				detailAST = parentDetailAST;
			}
		}

		return dependentDetailASTList;
	}

	private Map<String, List<DetailAST>> _getExpressionDetailASTMap(
		DetailAST methodCallDetailAST, boolean lastExpressionOnly) {

		Map<String, List<DetailAST>> expressionDetailASTMap = new HashMap<>();

		while (true) {
			String methodName = getMethodName(methodCallDetailAST);

			List<DetailAST> expressionDetailASTList =
				expressionDetailASTMap.get(methodName);

			if (expressionDetailASTList == null) {
				expressionDetailASTList = new ArrayList<>();
			}

			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			if (lastExpressionOnly) {
				DetailAST childDetailAST = elistDetailAST.getLastChild();

				if (childDetailAST != null) {
					expressionDetailASTList.add(childDetailAST);
				}
			}
			else {
				DetailAST childDetailAST = elistDetailAST.getFirstChild();

				while (true) {
					if (childDetailAST == null) {
						break;
					}

					if (childDetailAST.getType() != TokenTypes.COMMA) {
						expressionDetailASTList.add(childDetailAST);
					}

					childDetailAST = childDetailAST.getNextSibling();
				}
			}

			if (!expressionDetailASTList.isEmpty()) {
				expressionDetailASTMap.put(methodName, expressionDetailASTList);
			}

			DetailAST parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				return expressionDetailASTMap;
			}

			methodCallDetailAST = parentDetailAST.getParent();
		}
	}

	private String _getInlineExpressionMethodName(
		Map<String, List<DetailAST>> expressionDetailASTMap,
		List<DetailAST> dependentIdentDetailASTList) {

		String methodName = null;

		for (Map.Entry<String, List<DetailAST>> entry :
				expressionDetailASTMap.entrySet()) {

			for (DetailAST expressionDetailAST : entry.getValue()) {
				List<String> variableNames = _getVariableNames(
					expressionDetailAST);

				for (DetailAST dependentIdentDetailAST :
						dependentIdentDetailASTList) {

					if (variableNames.contains(
							dependentIdentDetailAST.getText())) {

						if (methodName != null) {
							return null;
						}

						methodName = entry.getKey();

						break;
					}
				}
			}
		}

		return methodName;
	}

	private String _getLineNumbers(List<DetailAST> detailASTList) {
		StringBundler sb = new StringBundler(detailASTList.size() * 2);

		for (DetailAST detailAST : detailASTList) {
			sb.append(detailAST.getLineNo());
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
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
					CONDITIONAL_OPERATOR_TOKEN_TYPES,
					parentDetailAST.getType()) ||
				ArrayUtil.contains(
					RELATIONAL_OPERATOR_TOKEN_TYPES,
					parentDetailAST.getType()) ||
				ArrayUtil.contains(
					UNARY_OPERATOR_TOKEN_TYPES, parentDetailAST.getType())) {

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

		for (String variableName : variableNames) {
			DetailAST variableTypeDetailAST = getVariableTypeDetailAST(
				detailAST, variableName);

			if (variableTypeDetailAST == null) {
				return true;
			}

			DetailAST variableDefinitionDetailAST =
				variableTypeDetailAST.getParent();

			DetailAST parentDetailAST = variableDefinitionDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.OBJBLOCK) {
				DetailAST modifiersDetailAST =
					variableDefinitionDetailAST.findFirstToken(
						TokenTypes.MODIFIERS);

				if ((modifiersDetailAST == null) ||
					!modifiersDetailAST.branchContains(TokenTypes.FINAL)) {

					return true;
				}

				continue;
			}

			List<DetailAST> variableCallerDetailASTList =
				getVariableCallerDetailASTList(
					variableDefinitionDetailAST, variableName);

			for (DetailAST variableCallerDetailAST :
					variableCallerDetailASTList) {

				parentDetailAST = variableCallerDetailAST.getParent();

				if ((parentDetailAST.getType() == TokenTypes.ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.BAND_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.BOR_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.BXOR_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.DEC) ||
					(parentDetailAST.getType() == TokenTypes.DIV_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.INC) ||
					(parentDetailAST.getType() == TokenTypes.MINUS_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.MOD_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.PLUS_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.POST_DEC) ||
					(parentDetailAST.getType() == TokenTypes.POST_INC) ||
					(parentDetailAST.getType() == TokenTypes.SL_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.SR_ASSIGN) ||
					(parentDetailAST.getType() == TokenTypes.STAR_ASSIGN)) {

					return true;
				}
			}
		}

		return false;
	}

	private static final String _CHECK_INLINE = "checkInline";

	private static final String _ENFORCE_BUILDER_NAMES_KEY =
		"enforceBuilderNames";

	private static final String _MSG_INCLUDE_BUILDER = "builder.include";

	private static final String _MSG_INCORRECT_NULL_VALUE =
		"null.value.incorrect";

	private static final String _MSG_INLINE_BUILDER_1 = "builder.inline.1";

	private static final String _MSG_INLINE_BUILDER_2 = "builder.inline.2";

	private static final String _MSG_RESERVED_KEYWORD = "keyword.reserved";

	private static final String _MSG_UNNEEDED_STRING_CAST =
		"string.cast.unneeded";

	private static final String _MSG_USE_BUILDER = "builder.use";

	private static final String _MSG_USE_BUILDER_INSTEAD =
		"builder.use.instead";

}