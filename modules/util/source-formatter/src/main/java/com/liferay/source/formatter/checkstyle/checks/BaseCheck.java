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

import antlr.CommonASTWithHiddenTokens;
import antlr.CommonHiddenStreamToken;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterCheckUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public abstract class BaseCheck extends AbstractCheck {

	@Override
	public int[] getAcceptableTokens() {
		return getDefaultTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	public void setAttributes(String attributes) throws JSONException {
		_attributesJSONObject = new JSONObjectImpl(attributes);
	}

	public void setExcludes(String excludes) throws JSONException {
		_excludesJSONObject = new JSONObjectImpl(excludes);
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!isAttributeValue(SourceFormatterCheckUtil.ENABLED_KEY, true)) {
			return;
		}

		if (!isAttributeValue(CheckstyleUtil.SHOW_DEBUG_INFORMATION_KEY)) {
			doVisitToken(detailAST);

			return;
		}

		long startTime = System.currentTimeMillis();

		doVisitToken(detailAST);

		long endTime = System.currentTimeMillis();

		Class<?> clazz = getClass();

		DebugUtil.increaseProcessingTime(
			clazz.getSimpleName(), endTime - startTime);
	}

	protected abstract void doVisitToken(DetailAST detailAST);

	protected boolean equals(DetailAST detailAST1, DetailAST detailAST2) {
		return Objects.equals(detailAST1.toString(), detailAST2.toString());
	}

	protected String getAbsolutePath() {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		return SourceUtil.getAbsolutePath(fileName);
	}

	protected List<DetailAST> getAllChildTokens(
		DetailAST detailAST, boolean recursive, int... tokenTypes) {

		return DetailASTUtil.getAllChildTokens(
			detailAST, recursive, tokenTypes);
	}

	protected String getAttributeValue(String attributeKey) {
		return getAttributeValue(attributeKey, StringPool.BLANK);
	}

	protected String getAttributeValue(
		String attributeKey, String defaultValue) {

		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap, attributeKey,
			defaultValue, getAbsolutePath(), getBaseDirName());
	}

	protected List<String> getAttributeValues(String attributeKey) {
		return SourceFormatterCheckUtil.getJSONObjectValues(
			_attributesJSONObject, attributeKey, _attributeValuesMap,
			getAbsolutePath(), getBaseDirName());
	}

	protected String getBaseDirName() {
		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap,
			CheckstyleUtil.BASE_DIR_NAME_KEY, StringPool.BLANK, null, null,
			true);
	}

	protected int getEndLineNumber(DetailAST detailAST) {
		int endLineNumber = detailAST.getLineNo();

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childDetailAST.getLineNo() > endLineNumber) {
				endLineNumber = childDetailAST.getLineNo();
			}
		}

		return endLineNumber;
	}

	protected CommonHiddenStreamToken getHiddenBefore(DetailAST detailAST) {
		CommonASTWithHiddenTokens commonASTWithHiddenTokens =
			(CommonASTWithHiddenTokens)detailAST;

		return commonASTWithHiddenTokens.getHiddenBefore();
	}

	protected List<String> getImportNames(DetailAST detailAST) {
		DetailAST rootDetailAST = detailAST;

		while (true) {
			if (rootDetailAST.getParent() != null) {
				rootDetailAST = rootDetailAST.getParent();
			}
			else if (rootDetailAST.getPreviousSibling() != null) {
				rootDetailAST = rootDetailAST.getPreviousSibling();
			}
			else {
				break;
			}
		}

		List<String> importNamesList = new ArrayList<>();

		DetailAST siblingDetailAST = rootDetailAST.getNextSibling();

		while (true) {
			if ((siblingDetailAST == null) ||
				(siblingDetailAST.getType() != TokenTypes.IMPORT)) {

				return importNamesList;
			}

			FullIdent importIdent = FullIdent.createFullIdentBelow(
				siblingDetailAST);

			importNamesList.add(importIdent.getText());

			siblingDetailAST = siblingDetailAST.getNextSibling();
		}
	}

	protected List<DetailAST> getMethodCalls(
		DetailAST detailAST, String methodName) {

		return getMethodCalls(detailAST, null, methodName);
	}

	protected List<DetailAST> getMethodCalls(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> list = new ArrayList<>();

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST == null) {
				continue;
			}

			List<DetailAST> nameDetailASTList = getAllChildTokens(
				dotDetailAST, false, TokenTypes.IDENT);

			if (nameDetailASTList.size() != 2) {
				continue;
			}

			DetailAST classNameDetailAST = nameDetailASTList.get(0);
			DetailAST methodNameDetailAST = nameDetailASTList.get(1);

			String methodCallClassName = classNameDetailAST.getText();
			String methodCallMethodName = methodNameDetailAST.getText();

			if (((className == null) ||
				 methodCallClassName.equals(className)) &&
				methodCallMethodName.equals(methodName)) {

				list.add(methodCallDetailAST);
			}
		}

		return list;
	}

	protected String getMethodName(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			DetailAST nameDetailAST = detailAST.findFirstToken(
				TokenTypes.IDENT);

			return nameDetailAST.getText();
		}

		List<DetailAST> nameDetailASTList = getAllChildTokens(
			dotDetailAST, false, TokenTypes.IDENT);

		DetailAST methodNameDetailAST = nameDetailASTList.get(
			nameDetailASTList.size() - 1);

		return methodNameDetailAST.getText();
	}

	protected List<DetailAST> getParameterDefs(DetailAST detailAST) {
		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return new ArrayList<>();
		}

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return getAllChildTokens(
			parametersDetailAST, false, TokenTypes.PARAMETER_DEF);
	}

	protected List<String> getParameterNames(DetailAST detailAST) {
		List<String> parameterNames = new ArrayList<>();

		for (DetailAST parameterDefinitionDetailAST :
				getParameterDefs(detailAST)) {

			DetailAST identDetailAST =
				parameterDefinitionDetailAST.findFirstToken(TokenTypes.IDENT);

			parameterNames.add(identDetailAST.getText());
		}

		return parameterNames;
	}

	protected DetailAST getParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (parentDetailAST != null) {
			if (ArrayUtil.contains(tokenTypes, parentDetailAST.getType())) {
				return parentDetailAST;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		return null;
	}

	protected String getSignature(DetailAST detailAST) {
		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append(CharPool.OPEN_PARENTHESIS);

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		List<DetailAST> parameterDefinitionDetailASTList = getAllChildTokens(
			parametersDetailAST, false, TokenTypes.PARAMETER_DEF);

		if (parameterDefinitionDetailASTList.isEmpty()) {
			sb.append(CharPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		for (DetailAST parameterDefinitionDetailAST :
				parameterDefinitionDetailASTList) {

			sb.append(getTypeName(parameterDefinitionDetailAST, true));
			sb.append(CharPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected int getStartLineNumber(DetailAST detailAST) {
		int startLineNumber = detailAST.getLineNo();

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childDetailAST.getLineNo() < startLineNumber) {
				startLineNumber = childDetailAST.getLineNo();
			}
		}

		return startLineNumber;
	}

	protected String getTypeName(
		DetailAST detailAST, boolean includeTypeArguments) {

		if (detailAST == null) {
			return StringPool.BLANK;
		}

		DetailAST typeDetailAST = detailAST;

		if ((detailAST.getType() != TokenTypes.TYPE) &&
			(detailAST.getType() != TokenTypes.TYPE_ARGUMENT)) {

			typeDetailAST = detailAST.findFirstToken(TokenTypes.TYPE);
		}

		DetailAST childDetailAST = typeDetailAST.getFirstChild();

		if (childDetailAST == null) {
			return StringPool.BLANK;
		}

		int arrayDimension = 0;

		while (childDetailAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			arrayDimension++;

			childDetailAST = childDetailAST.getFirstChild();
		}

		StringBundler sb = new StringBundler(1 + arrayDimension);

		FullIdent typeIdent = FullIdent.createFullIdent(childDetailAST);

		sb.append(typeIdent.getText());

		for (int i = 0; i < arrayDimension; i++) {
			sb.append("[]");
		}

		if (!includeTypeArguments) {
			return sb.toString();
		}

		DetailAST typeArgumentsDetailAST = typeDetailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsDetailAST == null) {
			return sb.toString();
		}

		sb.append(CharPool.LESS_THAN);

		List<DetailAST> typeArgumentDetailASTList = getAllChildTokens(
			typeArgumentsDetailAST, false, TokenTypes.TYPE_ARGUMENT);

		for (DetailAST typeArgumentDetailAST : typeArgumentDetailASTList) {
			sb.append(getTypeName(typeArgumentDetailAST, true));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.GREATER_THAN);

		return sb.toString();
	}

	protected List<DetailAST> getVariableCallerDetailASTList(
		DetailAST variableDefinitionDetailAST, String variableName) {

		List<DetailAST> variableCallerDetailASTList = new ArrayList<>();

		DetailAST parentDetailAST = variableDefinitionDetailAST.getParent();

		boolean globalVariable = false;
		DetailAST rangeDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.OBJBLOCK) {
			rangeDetailAST = parentDetailAST;

			globalVariable = true;
		}
		else if (parentDetailAST.getType() == TokenTypes.SLIST) {
			rangeDetailAST = parentDetailAST;
		}
		else {
			if (parentDetailAST.getType() != TokenTypes.LITERAL_CATCH) {
				parentDetailAST = parentDetailAST.getParent();
			}

			rangeDetailAST = parentDetailAST.getLastChild();
		}

		if ((rangeDetailAST.getType() != TokenTypes.OBJBLOCK) &&
			(rangeDetailAST.getType() != TokenTypes.SLIST)) {

			return variableCallerDetailASTList;
		}

		List<DetailAST> nameDetailASTList = getAllChildTokens(
			rangeDetailAST, true, TokenTypes.IDENT);

		for (DetailAST nameDetailAST : nameDetailASTList) {
			if (!variableName.equals(nameDetailAST.getText())) {
				continue;
			}

			parentDetailAST = nameDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.METHOD_CALL) ||
				(parentDetailAST.getType() == TokenTypes.PARAMETER_DEF) ||
				(parentDetailAST.getType() == TokenTypes.VARIABLE_DEF)) {

				continue;
			}

			if (parentDetailAST.getType() == TokenTypes.DOT) {
				if (globalVariable ||
					equals(parentDetailAST.getFirstChild(), nameDetailAST)) {

					variableCallerDetailASTList.add(nameDetailAST);
				}
			}
			else {
				variableCallerDetailASTList.add(nameDetailAST);
			}
		}

		return variableCallerDetailASTList;
	}

	protected String getVariableName(DetailAST methodCallDetailAST) {
		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
			return null;
		}

		DetailAST nameDetailAST = dotDetailAST.findFirstToken(TokenTypes.IDENT);

		if (nameDetailAST == null) {
			return null;
		}

		return nameDetailAST.getText();
	}

	protected DetailAST getVariableTypeDetailAST(
		DetailAST detailAST, String variableName) {

		return getVariableTypeDetailAST(detailAST, variableName, true);
	}

	protected DetailAST getVariableTypeDetailAST(
		DetailAST detailAST, String variableName,
		boolean includeGlobalVariables) {

		DetailAST previousDetailAST = detailAST;

		while (true) {
			if (includeGlobalVariables &&
				((previousDetailAST.getType() == TokenTypes.CLASS_DEF) ||
				 (previousDetailAST.getType() == TokenTypes.ENUM_DEF) ||
				 (previousDetailAST.getType() == TokenTypes.INTERFACE_DEF))) {

				DetailAST objBlockDetailAST = previousDetailAST.findFirstToken(
					TokenTypes.OBJBLOCK);

				List<DetailAST> variableDefinitionDetailASTList =
					getAllChildTokens(
						objBlockDetailAST, false, TokenTypes.VARIABLE_DEF);

				for (DetailAST variableDefinitionDetailAST :
						variableDefinitionDetailASTList) {

					if (variableName.equals(
							_getVariableName(variableDefinitionDetailAST))) {

						return variableDefinitionDetailAST.findFirstToken(
							TokenTypes.TYPE);
					}
				}
			}
			else if ((previousDetailAST.getType() ==
						TokenTypes.FOR_EACH_CLAUSE) ||
					 (previousDetailAST.getType() == TokenTypes.FOR_INIT)) {

				List<DetailAST> variableDefinitionDetailASTList =
					getAllChildTokens(
						previousDetailAST, false, TokenTypes.VARIABLE_DEF);

				for (DetailAST variableDefinitionDetailAST :
						variableDefinitionDetailASTList) {

					if (variableName.equals(
							_getVariableName(variableDefinitionDetailAST))) {

						return variableDefinitionDetailAST.findFirstToken(
							TokenTypes.TYPE);
					}
				}
			}
			else if ((previousDetailAST.getType() ==
						TokenTypes.LITERAL_CATCH) ||
					 (previousDetailAST.getType() == TokenTypes.PARAMETERS)) {

				List<DetailAST> parameterDefinitionDetailASTList =
					getAllChildTokens(
						previousDetailAST, false, TokenTypes.PARAMETER_DEF);

				for (DetailAST parameterDefinitionDetailAST :
						parameterDefinitionDetailASTList) {

					if (variableName.equals(
							_getVariableName(parameterDefinitionDetailAST))) {

						return parameterDefinitionDetailAST.findFirstToken(
							TokenTypes.TYPE);
					}
				}
			}
			else if (previousDetailAST.getType() ==
						TokenTypes.RESOURCE_SPECIFICATION) {

				DetailAST recourcesDetailAST = previousDetailAST.findFirstToken(
					TokenTypes.RESOURCES);

				List<DetailAST> resourceDetailASTList = getAllChildTokens(
					recourcesDetailAST, false, TokenTypes.RESOURCE);

				for (DetailAST resourceDetailAST : resourceDetailASTList) {
					if (variableName.equals(
							_getVariableName(resourceDetailAST))) {

						return resourceDetailAST.findFirstToken(
							TokenTypes.TYPE);
					}
				}
			}
			else if ((previousDetailAST.getType() == TokenTypes.VARIABLE_DEF) &&
					 variableName.equals(_getVariableName(previousDetailAST))) {

				DetailAST parentDetailAST = previousDetailAST.getParent();

				if (includeGlobalVariables ||
					(parentDetailAST.getType() != TokenTypes.OBJBLOCK)) {

					return previousDetailAST.findFirstToken(TokenTypes.TYPE);
				}
			}

			DetailAST previousSiblingDetailAST =
				previousDetailAST.getPreviousSibling();

			if (previousSiblingDetailAST != null) {
				previousDetailAST = previousSiblingDetailAST;

				continue;
			}

			DetailAST parentDetailAST = previousDetailAST.getParent();

			if (parentDetailAST != null) {
				previousDetailAST = parentDetailAST;

				continue;
			}

			break;
		}

		return null;
	}

	protected String getVariableTypeName(
		DetailAST detailAST, String variableName,
		boolean includeTypeArguments) {

		return getTypeName(
			getVariableTypeDetailAST(detailAST, variableName),
			includeTypeArguments);
	}

	protected boolean hasParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentDetailAST = getParentWithTokenType(
			detailAST, tokenTypes);

		if (parentDetailAST != null) {
			return true;
		}

		return false;
	}

	protected boolean isArray(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.TYPE) {
			return false;
		}

		DetailAST arrayDeclaratorDetailAST = detailAST.findFirstToken(
			TokenTypes.ARRAY_DECLARATOR);

		if (arrayDeclaratorDetailAST != null) {
			return true;
		}

		return false;
	}

	protected boolean isAtLineEnd(DetailAST detailAST, String line) {
		String text = detailAST.getText();

		if (line.endsWith(text) &&
			((detailAST.getColumnNo() + text.length()) == line.length())) {

			return true;
		}

		return false;
	}

	protected boolean isAtLineStart(DetailAST detailAST, String line) {
		for (int i = 0; i < detailAST.getColumnNo(); i++) {
			char c = line.charAt(i);

			if ((c != CharPool.SPACE) && (c != CharPool.TAB)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isAttributeValue(String attributeKey) {
		return GetterUtil.getBoolean(getAttributeValue(attributeKey));
	}

	protected boolean isAttributeValue(
		String attributeKey, boolean defaultValue) {

		String attributeValue = getAttributeValue(attributeKey);

		if (Validator.isNull(attributeValue)) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(attributeValue);
	}

	protected boolean isCollection(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.TYPE) {
			return false;
		}

		DetailAST typeArgumentsDetailAST = detailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsDetailAST == null) {
			return false;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		if (name.matches(".*(Collection|List|Map|Set)")) {
			return true;
		}

		return false;
	}

	protected boolean isExcludedPath(String key) {
		return SourceFormatterCheckUtil.isExcludedPath(
			_excludesJSONObject, _excludesValuesMap, key, getAbsolutePath(), -1,
			null, getBaseDirName());
	}

	protected static final int ALL_TYPES = DetailASTUtil.ALL_TYPES;

	protected static final int[] ARITHMETIC_OPERATOR_TOKEN_TYPES = {
		TokenTypes.DIV, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.PLUS,
		TokenTypes.STAR
	};

	protected static final int[] RELATIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
		TokenTypes.LT, TokenTypes.NOT_EQUAL
	};

	private String _getVariableName(DetailAST variableDefinitionDetailAST) {
		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

	private JSONObject _attributesJSONObject = new JSONObjectImpl();
	private final Map<String, String> _attributeValueMap =
		new ConcurrentHashMap<>();
	private final Map<String, List<String>> _attributeValuesMap =
		new ConcurrentHashMap<>();
	private JSONObject _excludesJSONObject = new JSONObjectImpl();
	private final Map<String, List<String>> _excludesValuesMap =
		new ConcurrentHashMap<>();

}