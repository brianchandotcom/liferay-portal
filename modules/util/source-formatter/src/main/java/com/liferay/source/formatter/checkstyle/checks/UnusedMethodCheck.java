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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class UnusedMethodCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	public void setAllowedMethodNames(String allowedMethodNames) {
		_allowedMethodNames = ArrayUtil.append(
			_allowedMethodNames, StringUtil.split(allowedMethodNames));
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		if (parentAST != null) {
			return;
		}

		List<DetailAST> methodDefASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_DEF);

		if (methodDefASTList.isEmpty()) {
			return;
		}

		List<String> referencedMethodNames = _getReferencedMethodNames(
			detailAST);

		for (DetailAST methodDefAST : methodDefASTList) {
			DetailAST modifiersAST = methodDefAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (!modifiersAST.branchContains(TokenTypes.LITERAL_PRIVATE) ||
				_hasSuppressUnusedWarningsAnnotation(methodDefAST)) {

				continue;
			}

			DetailAST nameAST = methodDefAST.findFirstToken(TokenTypes.IDENT);

			String name = nameAST.getText();

			if (!ArrayUtil.contains(_allowedMethodNames, name) &&
				!referencedMethodNames.contains(nameAST.getText())) {

				log(methodDefAST.getLineNo(), _MSG_UNUSED_METHOD, name);
			}
		}
	}

	private List<String> _getReferencedMethodNames(DetailAST classDefAST) {
		List<String> referencedMethodNames = new ArrayList<>();

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			classDefAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST nameAST = methodCallAST.getFirstChild();

			if (nameAST.getType() == TokenTypes.DOT) {
				nameAST = nameAST.getLastChild();
			}

			referencedMethodNames.add(nameAST.getText());
		}

		List<DetailAST> methodRefASTList = DetailASTUtil.getAllChildTokens(
			classDefAST, true, TokenTypes.METHOD_REF);

		for (DetailAST methodRefAST : methodRefASTList) {
			DetailAST lastChildAST = methodRefAST.getLastChild();

			referencedMethodNames.add(lastChildAST.getText());
		}

		List<DetailAST> literalNewASTList = DetailASTUtil.getAllChildTokens(
			classDefAST, true, TokenTypes.LITERAL_NEW);

		for (DetailAST literalNewAST : literalNewASTList) {
			DetailAST firstChildAST = literalNewAST.getFirstChild();

			if ((firstChildAST == null) ||
				(firstChildAST.getType() != TokenTypes.IDENT) ||
				!Objects.equals(firstChildAST.getText(), "MethodKey")) {

				continue;
			}

			DetailAST elist = literalNewAST.findFirstToken(TokenTypes.ELIST);

			List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
				elist, false, TokenTypes.EXPR);

			if (exprASTList.size() < 2) {
				continue;
			}

			DetailAST exprAST = exprASTList.get(1);

			firstChildAST = exprAST.getFirstChild();

			if (firstChildAST.getType() == TokenTypes.STRING_LITERAL) {
				String text = firstChildAST.getText();

				referencedMethodNames.add(text.substring(1, text.length() - 1));
			}
		}

		return referencedMethodNames;
	}

	private boolean _hasSuppressUnusedWarningsAnnotation(
		DetailAST methodDefAST) {

		DetailAST annotationAST = AnnotationUtil.getAnnotation(
			methodDefAST, "SuppressWarnings");

		if (annotationAST == null) {
			return false;
		}

		List<DetailAST> literalStringASTList = DetailASTUtil.getAllChildTokens(
			annotationAST, true, TokenTypes.STRING_LITERAL);

		for (DetailAST literalStringAST : literalStringASTList) {
			String s = literalStringAST.getText();

			if (s.equals("\"unused\"")) {
				return true;
			}
		}

		return false;
	}

	private static final String _MSG_UNUSED_METHOD = "method.unused";

	private String[] _allowedMethodNames = new String[0];

}