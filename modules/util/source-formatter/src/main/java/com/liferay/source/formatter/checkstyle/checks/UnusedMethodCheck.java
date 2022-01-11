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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class UnusedMethodCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_DEF);

		if (methodDefinitionDetailASTList.isEmpty()) {
			return;
		}

		List<String> allowedMethodNames = getAttributeValues(
			_ALLOWED_METHOD_NAMES_KEY);

		Map<String, Set<Integer>> referencedMethodNamesMap =
			_getReferencedMethodNamesMap(detailAST);

		outerLoop:
		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			DetailAST modifiersDetailAST =
				methodDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

			if (!modifiersDetailAST.branchContains(
					TokenTypes.LITERAL_PRIVATE) ||
				AnnotationUtil.containsAnnotation(methodDefinitionDetailAST) ||
				_hasSuppressUnusedWarningsAnnotation(
					methodDefinitionDetailAST)) {

				continue;
			}

			DetailAST nameDetailAST = methodDefinitionDetailAST.findFirstToken(
				TokenTypes.IDENT);

			String name = nameDetailAST.getText();

			if (allowedMethodNames.contains(name)) {
				continue;
			}

			DetailAST parametersDetailAST =
				methodDefinitionDetailAST.findFirstToken(TokenTypes.PARAMETERS);

			Set<Integer> parameterCountSet = referencedMethodNamesMap.get(name);

			if (parameterCountSet == null) {
				log(methodDefinitionDetailAST, _MSG_UNUSED_METHOD, name);

				continue;
			}

			if (parameterCountSet.contains(-1)) {
				continue;
			}

			List<DetailAST> parameterDefinitionDetailASTList =
				getAllChildTokens(
					parametersDetailAST, false, TokenTypes.PARAMETER_DEF);

			int parameterCount = parameterDefinitionDetailASTList.size();

			boolean varArgs = false;

			if (parameterCount > 0) {
				DetailAST lastParameterDefinitionDetailAST =
					parameterDefinitionDetailASTList.get(
						parameterDefinitionDetailASTList.size() - 1);

				if (lastParameterDefinitionDetailAST.branchContains(
						TokenTypes.ELLIPSIS)) {

					varArgs = true;
				}
			}

			if (varArgs) {
				for (int curParameterCount : parameterCountSet) {
					if (curParameterCount >= (parameterCount - 1)) {
						continue outerLoop;
					}
				}

				log(methodDefinitionDetailAST, _MSG_UNUSED_METHOD, name);
			}
			else if (!parameterCountSet.contains(parameterCount)) {
				log(methodDefinitionDetailAST, _MSG_UNUSED_METHOD, name);
			}
		}
	}

	private Map<String, Set<Integer>> _addMapEntry(
		Map<String, Set<Integer>> map, String key, int value) {

		Set<Integer> set = map.get(key);

		if (set == null) {
			set = new HashSet<>();
		}

		set.add(value);

		map.put(key, set);

		return map;
	}

	private Map<String, Set<Integer>> _getReferencedMethodNamesMap(
		DetailAST classDefinitionDetailAST) {

		Map<String, Set<Integer>> referencedMethodNamesMap = new HashMap<>();

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			classDefinitionDetailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST nameDetailAST = methodCallDetailAST.getFirstChild();

			if (nameDetailAST.getType() == TokenTypes.DOT) {
				nameDetailAST = nameDetailAST.getLastChild();
			}

			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			int parameterCount = 0;

			int childCount = elistDetailAST.getChildCount();

			if (childCount > 0) {
				parameterCount = (childCount + 1) / 2;
			}

			referencedMethodNamesMap = _addMapEntry(
				referencedMethodNamesMap, nameDetailAST.getText(),
				parameterCount);
		}

		List<DetailAST> methodReferenceDetailASTList = getAllChildTokens(
			classDefinitionDetailAST, true, TokenTypes.METHOD_REF);

		for (DetailAST methodReferenceDetailAST :
				methodReferenceDetailASTList) {

			DetailAST lastChildDetailAST =
				methodReferenceDetailAST.getLastChild();

			referencedMethodNamesMap = _addMapEntry(
				referencedMethodNamesMap, lastChildDetailAST.getText(), -1);
		}

		List<DetailAST> literalNewDetailASTList = getAllChildTokens(
			classDefinitionDetailAST, true, TokenTypes.LITERAL_NEW);

		for (DetailAST literalNewDetailAST : literalNewDetailASTList) {
			DetailAST firstChildDetailAST = literalNewDetailAST.getFirstChild();

			if ((firstChildDetailAST == null) ||
				(firstChildDetailAST.getType() != TokenTypes.IDENT) ||
				!Objects.equals(firstChildDetailAST.getText(), "MethodKey")) {

				continue;
			}

			DetailAST elistDetailAST = literalNewDetailAST.findFirstToken(
				TokenTypes.ELIST);

			List<DetailAST> exprDetailASTList = getAllChildTokens(
				elistDetailAST, false, TokenTypes.EXPR);

			if (exprDetailASTList.size() < 2) {
				continue;
			}

			DetailAST exprDetailAST = exprDetailASTList.get(1);

			firstChildDetailAST = exprDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.STRING_LITERAL) {
				String text = firstChildDetailAST.getText();

				referencedMethodNamesMap = _addMapEntry(
					referencedMethodNamesMap,
					text.substring(1, text.length() - 1),
					exprDetailASTList.size() - 2);
			}
		}

		List<DetailAST> annotationDetailASTList = getAllChildTokens(
			classDefinitionDetailAST, true, TokenTypes.ANNOTATION);

		for (DetailAST annotationDetailAST : annotationDetailASTList) {
			DetailAST atDetailAST = annotationDetailAST.findFirstToken(
				TokenTypes.AT);

			FullIdent fullIdent = FullIdent.createFullIdent(
				atDetailAST.getNextSibling());

			String annotationName = fullIdent.getText();

			if (!annotationName.endsWith("Reference")) {
				continue;
			}

			List<DetailAST> annotationMemberValuePairDetailASTList =
				getAllChildTokens(
					annotationDetailAST, false,
					TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

			for (DetailAST annotationMemberValuePairDetailAST :
					annotationMemberValuePairDetailASTList) {

				DetailAST firstChildDetailAST =
					annotationMemberValuePairDetailAST.getFirstChild();

				String propertyName = firstChildDetailAST.getText();

				if (!propertyName.equals("unbind")) {
					continue;
				}

				DetailAST nextSiblingDetailAST =
					firstChildDetailAST.getNextSibling();

				fullIdent = FullIdent.createFullIdentBelow(
					nextSiblingDetailAST.getNextSibling());

				String propertyValueName = fullIdent.getText();

				if (propertyValueName.matches("\".*\"")) {
					referencedMethodNamesMap = _addMapEntry(
						referencedMethodNamesMap,
						propertyValueName.substring(
							1, propertyValueName.length() - 1),
						1);
				}
			}
		}

		return referencedMethodNamesMap;
	}

	private boolean _hasSuppressUnusedWarningsAnnotation(
		DetailAST methodDefinitionDetailAST) {

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			methodDefinitionDetailAST, "SuppressWarnings");

		if (annotationDetailAST == null) {
			return false;
		}

		List<DetailAST> literalStringDetailASTList = getAllChildTokens(
			annotationDetailAST, true, TokenTypes.STRING_LITERAL);

		for (DetailAST literalStringDetailAST : literalStringDetailASTList) {
			String s = literalStringDetailAST.getText();

			if (s.equals("\"unused\"")) {
				return true;
			}
		}

		return false;
	}

	private static final String _ALLOWED_METHOD_NAMES_KEY =
		"allowedMethodNames";

	private static final String _MSG_UNUSED_METHOD = "method.unused";

}