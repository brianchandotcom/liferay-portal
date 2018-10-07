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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;

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

		for (DetailAST childAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childAST.getLineNo() > endLineNumber) {
				endLineNumber = childAST.getLineNo();
			}
		}

		return endLineNumber;
	}

	public static List<String> getImportNames(DetailAST detailAST) {
		DetailAST rootAST = detailAST;

		while (true) {
			if (rootAST.getParent() != null) {
				rootAST = rootAST.getParent();
			}
			else if (rootAST.getPreviousSibling() != null) {
				rootAST = rootAST.getPreviousSibling();
			}
			else {
				break;
			}
		}

		List<String> importNamesList = new ArrayList<>();

		DetailAST siblingAST = rootAST.getNextSibling();

		while (true) {
			if (siblingAST.getType() == TokenTypes.IMPORT) {
				FullIdent importIdent = FullIdent.createFullIdentBelow(
					siblingAST);

				importNamesList.add(importIdent.getText());
			}
			else {
				break;
			}

			siblingAST = siblingAST.getNextSibling();
		}

		return importNamesList;
	}

	public static List<DetailAST> getMethodCalls(
		DetailAST detailAST, String methodName) {

		return getMethodCalls(detailAST, null, methodName);
	}

	public static List<DetailAST> getMethodCalls(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> list = new ArrayList<>();

		List<DetailAST> methodCallASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			if (dotAST == null) {
				continue;
			}

			List<DetailAST> nameASTList = getAllChildTokens(
				dotAST, false, TokenTypes.IDENT);

			if (nameASTList.size() != 2) {
				continue;
			}

			DetailAST classNameAST = nameASTList.get(0);
			DetailAST methodNameAST = nameASTList.get(1);

			String methodCallClassName = classNameAST.getText();
			String methodCallMethodName = methodNameAST.getText();

			if (((className == null) ||
				 methodCallClassName.equals(className)) &&
				methodCallMethodName.equals(methodName)) {

				list.add(methodCallAST);
			}
		}

		return list;
	}

	public static String getMethodName(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		DetailAST dotAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotAST == null) {
			DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

			return nameAST.getText();
		}

		List<DetailAST> nameASTList = getAllChildTokens(
			dotAST, false, TokenTypes.IDENT);

		DetailAST methodNameAST = nameASTList.get(nameASTList.size() - 1);

		return methodNameAST.getText();
	}

	public static List<DetailAST> getParameterDefs(DetailAST detailAST) {
		List<DetailAST> list = new ArrayList<>();

		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return list;
		}

		DetailAST parametersAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return getAllChildTokens(
			parametersAST, false, TokenTypes.PARAMETER_DEF);
	}

	public static List<String> getParameterNames(DetailAST detailAST) {
		List<String> parameterNames = new ArrayList<>();

		for (DetailAST parameterDefinitionAST : getParameterDefs(detailAST)) {
			DetailAST identAST = parameterDefinitionAST.findFirstToken(
				TokenTypes.IDENT);

			parameterNames.add(identAST.getText());
		}

		return parameterNames;
	}

	public static DetailAST getParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentAST = detailAST.getParent();

		while (parentAST != null) {
			if (ArrayUtil.contains(tokenTypes, parentAST.getType())) {
				return parentAST;
			}

			parentAST = parentAST.getParent();
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

		DetailAST parametersAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		List<DetailAST> parameterDefASTList = getAllChildTokens(
			parametersAST, false, TokenTypes.PARAMETER_DEF);

		if (parameterDefASTList.isEmpty()) {
			sb.append(CharPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		for (DetailAST parameterDefAST : parameterDefASTList) {
			sb.append(getTypeName(parameterDefAST, true));
			sb.append(CharPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	public static int getStartLineNumber(DetailAST detailAST) {
		int startLineNumber = detailAST.getLineNo();

		for (DetailAST childAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childAST.getLineNo() < startLineNumber) {
				startLineNumber = childAST.getLineNo();
			}
		}

		return startLineNumber;
	}

	public static String getTypeName(
		DetailAST detailAST, boolean includeTypeArguments) {

		if (detailAST == null) {
			return StringPool.BLANK;
		}

		DetailAST typeAST = detailAST;

		if (detailAST.getType() != TokenTypes.TYPE) {
			typeAST = detailAST.findFirstToken(TokenTypes.TYPE);
		}

		DetailAST childAST = typeAST.getFirstChild();

		if (childAST == null) {
			return StringPool.BLANK;
		}

		int arrayDimension = 0;

		while (childAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			arrayDimension++;

			childAST = childAST.getFirstChild();
		}

		StringBundler sb = new StringBundler(1 + arrayDimension);

		FullIdent typeIdent = FullIdent.createFullIdent(childAST);

		sb.append(typeIdent.getText());

		for (int i = 0; i < arrayDimension; i++) {
			sb.append("[]");
		}

		if (!includeTypeArguments) {
			return sb.toString();
		}

		DetailAST typeArgumentsAST = typeAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsAST == null) {
			return sb.toString();
		}

		sb.append(CharPool.LESS_THAN);

		List<DetailAST> typeArgumentASTList = getAllChildTokens(
			typeArgumentsAST, false, TokenTypes.TYPE_ARGUMENT);

		for (DetailAST typeArgumentAST : typeArgumentASTList) {
			FullIdent typeArgumenIdent = FullIdent.createFullIdentBelow(
				typeArgumentAST);

			sb.append(typeArgumenIdent.getText());

			sb.append(CharPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.GREATER_THAN);

		return sb.toString();
	}

	public static String getVariableName(DetailAST methodCallAST) {
		DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

		if (dotAST == null) {
			return null;
		}

		DetailAST nameAST = dotAST.findFirstToken(TokenTypes.IDENT);

		if (nameAST == null) {
			return null;
		}

		return nameAST.getText();
	}

	public static DetailAST getVariableTypeAST(
		DetailAST detailAST, String variableName) {

		DetailAST previousAST = detailAST;

		while (true) {
			if ((previousAST.getType() == TokenTypes.CLASS_DEF) ||
				(previousAST.getType() == TokenTypes.ENUM_DEF) ||
				(previousAST.getType() == TokenTypes.INTERFACE_DEF)) {

				DetailAST objBlockAST = previousAST.findFirstToken(
					TokenTypes.OBJBLOCK);

				List<DetailAST> variableDefASTList = getAllChildTokens(
					objBlockAST, false, TokenTypes.VARIABLE_DEF);

				for (DetailAST variableDefAST : variableDefASTList) {
					if (variableName.equals(_getVariableName(variableDefAST))) {
						return variableDefAST.findFirstToken(TokenTypes.TYPE);
					}
				}
			}
			else if ((previousAST.getType() == TokenTypes.FOR_EACH_CLAUSE) ||
					 (previousAST.getType() == TokenTypes.FOR_INIT)) {

				List<DetailAST> variableDefASTList = getAllChildTokens(
					previousAST, false, TokenTypes.VARIABLE_DEF);

				for (DetailAST variableDefAST : variableDefASTList) {
					if (variableName.equals(_getVariableName(variableDefAST))) {
						return variableDefAST.findFirstToken(TokenTypes.TYPE);
					}
				}
			}
			else if ((previousAST.getType() == TokenTypes.LITERAL_CATCH) ||
					 (previousAST.getType() == TokenTypes.PARAMETERS)) {

				List<DetailAST> parameterDefASTList = getAllChildTokens(
					previousAST, false, TokenTypes.PARAMETER_DEF);

				for (DetailAST parameterDefAST : parameterDefASTList) {
					if (variableName.equals(
							_getVariableName(parameterDefAST))) {

						return parameterDefAST.findFirstToken(TokenTypes.TYPE);
					}
				}
			}
			else if (previousAST.getType() ==
						TokenTypes.RESOURCE_SPECIFICATION) {

				DetailAST recourcesAST = previousAST.findFirstToken(
					TokenTypes.RESOURCES);

				List<DetailAST> resourceASTList = getAllChildTokens(
					recourcesAST, false, TokenTypes.RESOURCE);

				for (DetailAST resourceAST : resourceASTList) {
					if (variableName.equals(_getVariableName(resourceAST))) {
						return resourceAST.findFirstToken(TokenTypes.TYPE);
					}
				}
			}
			else if (previousAST.getType() == TokenTypes.VARIABLE_DEF) {
				if (variableName.equals(_getVariableName(previousAST))) {
					return previousAST.findFirstToken(TokenTypes.TYPE);
				}
			}

			DetailAST previousSiblingAST = previousAST.getPreviousSibling();

			if (previousSiblingAST != null) {
				previousAST = previousSiblingAST;

				continue;
			}

			DetailAST parentAST = previousAST.getParent();

			if (parentAST != null) {
				previousAST = parentAST;

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
			getVariableTypeAST(detailAST, variableName), includeTypeArguments);
	}

	public static boolean hasParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentAST = getParentWithTokenType(detailAST, tokenTypes);

		if (parentAST != null) {
			return true;
		}

		return false;
	}

	public static boolean isArray(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.TYPE) {
			return false;
		}

		DetailAST arrayDeclaratorAST = detailAST.findFirstToken(
			TokenTypes.ARRAY_DECLARATOR);

		if (arrayDeclaratorAST != null) {
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

	public static boolean isCollection(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.TYPE) {
			return false;
		}

		DetailAST typeArgumentsAST = detailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsAST == null) {
			return false;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

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

		DetailAST childAST = detailAST.getFirstChild();

		while (childAST != null) {
			if (ArrayUtil.contains(tokenTypes, childAST.getType()) ||
				ArrayUtil.contains(tokenTypes, ALL_TYPES)) {

				list.add(childAST);
			}

			if (recursive) {
				list = _getAllChildTokens(
					childAST, recursive, list, tokenTypes);
			}

			childAST = childAST.getNextSibling();
		}

		return list;
	}

	private static String _getVariableName(DetailAST variableDefAST) {
		DetailAST nameAST = variableDefAST.findFirstToken(TokenTypes.IDENT);

		return nameAST.getText();
	}

}