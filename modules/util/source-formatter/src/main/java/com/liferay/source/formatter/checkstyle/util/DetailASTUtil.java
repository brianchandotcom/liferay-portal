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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class DetailASTUtil {

	public static final int ALL_TYPES = -1;

	public static DetailAST findTypeAST(DetailAST methodAST, String name) {
		List<DetailAST> localVariableDefASTList = getAllChildTokens(
			methodAST, true, TokenTypes.VARIABLE_DEF);

		DetailAST typeAST = _findTypeAST(localVariableDefASTList, name);

		if (typeAST != null) {
			return typeAST;
		}

		List<DetailAST> parameterDefASTList = getParameterDefs(methodAST);

		typeAST = _findTypeAST(parameterDefASTList, name);

		if (typeAST != null) {
			return typeAST;
		}

		DetailAST classAST = methodAST.getParent();

		while (classAST != null) {
			List<DetailAST> globalVariableDefASTList = getAllChildTokens(
				classAST, false, TokenTypes.VARIABLE_DEF);

			typeAST = _findTypeAST(globalVariableDefASTList, name);

			if (typeAST != null) {
				return typeAST;
			}

			classAST = classAST.getParent();
		}

		return null;
	}

	public static List<DetailAST> getAllChildTokens(
		DetailAST detailAST, boolean recursive, int... tokenTypes) {

		return _getAllChildTokens(detailAST, recursive, null, tokenTypes);
	}

	public static int getEndLine(DetailAST detailAST) {
		int endLine = detailAST.getLineNo();

		for (DetailAST childAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childAST.getLineNo() > endLine) {
				endLine = childAST.getLineNo();
			}
		}

		return endLine;
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

	public static int getStartLine(DetailAST detailAST) {
		int startLine = detailAST.getLineNo();

		for (DetailAST childAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childAST.getLineNo() < startLine) {
				startLine = childAST.getLineNo();
			}
		}

		return startLine;
	}

	public static String getTypeName(DetailAST detailAST) {
		DetailAST typeAST = detailAST.findFirstToken(TokenTypes.TYPE);

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

		return sb.toString();
	}

	public static Set<String> getVariableTypeNames(
		DetailAST detailAST, String variableName) {

		Set<String> variableTypeNames = new HashSet<>();

		List<DetailAST> definitionASTList = new ArrayList<>();

		if (variableName.matches("_[a-z].*")) {
			definitionASTList = getAllChildTokens(
				_getClassAST(detailAST), true, TokenTypes.PARAMETER_DEF,
				TokenTypes.VARIABLE_DEF);
		}
		else if (variableName.matches("[a-z].*")) {
			definitionASTList = getAllChildTokens(
				detailAST, true, TokenTypes.PARAMETER_DEF,
				TokenTypes.VARIABLE_DEF);
		}

		for (DetailAST definitionAST : definitionASTList) {
			DetailAST nameAST = definitionAST.findFirstToken(TokenTypes.IDENT);

			if (nameAST == null) {
				continue;
			}

			String name = nameAST.getText();

			if (name.equals(variableName)) {
				DetailAST typeAST = definitionAST.findFirstToken(
					TokenTypes.TYPE);

				nameAST = typeAST.findFirstToken(TokenTypes.IDENT);

				if (nameAST == null) {
					return variableTypeNames;
				}

				variableTypeNames.add(nameAST.getText());
			}
		}

		return variableTypeNames;
	}

	public static boolean hasParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentAST = detailAST.getParent();

		while (parentAST != null) {
			if (ArrayUtil.contains(tokenTypes, parentAST.getType())) {
				return true;
			}

			parentAST = parentAST.getParent();
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

	private static DetailAST _findTypeAST(
		List<DetailAST> defASTList, String name) {

		for (DetailAST defAST : defASTList) {
			DetailAST nameAST = defAST.findFirstToken(TokenTypes.IDENT);

			String curName = nameAST.getText();

			if (curName.equals(name)) {
				return defAST.findFirstToken(TokenTypes.TYPE);
			}
		}

		return null;
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

	private static DetailAST _getClassAST(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST.getParent() == null) {
				break;
			}

			return parentAST.getParent();
		}

		return null;
	}

}