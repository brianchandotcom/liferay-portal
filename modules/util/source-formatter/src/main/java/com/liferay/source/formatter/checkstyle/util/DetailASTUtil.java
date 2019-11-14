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

package com.liferay.source.formatter.checkstyle.util;

import antlr.CommonASTWithHiddenTokens;
import antlr.CommonHiddenStreamToken;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class DetailASTUtil {

	public static final int ALL_TYPES = -1;

	public static List<DetailAST> getAllChildTokens(
		DetailAST detailAST, boolean recursive, int... tokenTypes) {

		return _getAllChildTokens(detailAST, recursive, null, tokenTypes);
	}

	public static int getEndLineNumber(DetailAST detailAST) {
		int endLineNumber = detailAST.getLineNo();

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childDetailAST.getLineNo() > endLineNumber) {
				endLineNumber = childDetailAST.getLineNo();
			}
		}

		return endLineNumber;
	}

	public static CommonHiddenStreamToken getHiddenBefore(DetailAST detailAST) {
		CommonASTWithHiddenTokens commonASTWithHiddenTokens =
			(CommonASTWithHiddenTokens)detailAST;

		return commonASTWithHiddenTokens.getHiddenBefore();
	}

	public static List<String> getImportNames(DetailAST detailAST) {
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

	public static List<DetailAST> getMethodCalls(
		DetailAST detailAST, String methodName) {

		return getMethodCalls(detailAST, null, methodName);
	}

	public static List<DetailAST> getMethodCalls(
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

	public static String getMethodName(DetailAST detailAST) {
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

	public static List<DetailAST> getParameterDefs(DetailAST detailAST) {
		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return new ArrayList<>();
		}

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return getAllChildTokens(
			parametersDetailAST, false, TokenTypes.PARAMETER_DEF);
	}

	public static List<String> getParameterNames(DetailAST detailAST) {
		List<String> parameterNames = new ArrayList<>();

		for (DetailAST parameterDefinitionDetailAST :
				getParameterDefs(detailAST)) {

			DetailAST identDetailAST =
				parameterDefinitionDetailAST.findFirstToken(TokenTypes.IDENT);

			parameterNames.add(identDetailAST.getText());
		}

		return parameterNames;
	}

	public static DetailAST getParentWithTokenType(
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

	public static String getSignature(DetailAST detailAST) {
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

	public static int getStartLineNumber(DetailAST detailAST) {
		int startLineNumber = detailAST.getLineNo();

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childDetailAST.getLineNo() < startLineNumber) {
				startLineNumber = childDetailAST.getLineNo();
			}
		}

		return startLineNumber;
	}

	public static String getTypeName(
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

	public static String getVariableName(DetailAST methodCallDetailAST) {
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

	public static DetailAST getVariableTypeDetailAST(
		DetailAST detailAST, String variableName) {

		DetailAST previousDetailAST = detailAST;

		while (true) {
			if ((previousDetailAST.getType() == TokenTypes.CLASS_DEF) ||
				(previousDetailAST.getType() == TokenTypes.ENUM_DEF) ||
				(previousDetailAST.getType() == TokenTypes.INTERFACE_DEF)) {

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
			else if (previousDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
				if (variableName.equals(_getVariableName(previousDetailAST))) {
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

	public static String getVariableTypeName(
		DetailAST detailAST, String variableName,
		boolean includeTypeArguments) {

		return getTypeName(
			getVariableTypeDetailAST(detailAST, variableName),
			includeTypeArguments);
	}

	public static boolean hasParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentDetailAST = getParentWithTokenType(
			detailAST, tokenTypes);

		if (parentDetailAST != null) {
			return true;
		}

		return false;
	}

	public static boolean isArray(DetailAST detailAST) {
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

	public static boolean isAtLineEnd(DetailAST detailAST, String line) {
		String text = detailAST.getText();

		if (line.endsWith(text) &&
			((detailAST.getColumnNo() + text.length()) == line.length())) {

			return true;
		}

		return false;
	}

	public static boolean isAtLineStart(DetailAST detailAST, String line) {
		for (int i = 0; i < detailAST.getColumnNo(); i++) {
			char c = line.charAt(i);

			if ((c != CharPool.SPACE) && (c != CharPool.TAB)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isCollection(DetailAST detailAST) {
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

	private static List<DetailAST> _getAllChildTokens(
		DetailAST detailAST, boolean recursive, List<DetailAST> list,
		int... tokenTypes) {

		if (list == null) {
			list = new ArrayList<>();
		}

		DetailAST childDetailAST = detailAST.getFirstChild();

		while (childDetailAST != null) {
			if (ArrayUtil.contains(tokenTypes, childDetailAST.getType()) ||
				ArrayUtil.contains(tokenTypes, ALL_TYPES)) {

				list.add(childDetailAST);
			}

			if (recursive) {
				list = _getAllChildTokens(
					childDetailAST, recursive, list, tokenTypes);
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		return list;
	}

	private static String _getVariableName(
		DetailAST variableDefinitionDetailAST) {

		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

}