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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ChainingCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	public void setAllowedClassNames(String allowedClassNames) {
		_allowedClassNames = StringUtil.split(allowedClassNames);
	}

	public void setAllowedMethodNames(String allowedMethodNames) {
		_allowedMethodNames = StringUtil.split(allowedMethodNames);
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			if (dotAST != null) {
				List<DetailAST> childMethodCallASTList =
					DetailASTUtil.getAllChildTokens(
						dotAST, false, TokenTypes.METHOD_CALL);

				// Only check the method that is first in the chain

				if (!childMethodCallASTList.isEmpty()) {
					continue;
				}
			}

			List<String> chainedMethodNames = _getChainedMethodNames(
				methodCallAST);

			if (chainedMethodNames.size() == 1) {
				continue;
			}

			if (_isAllowedChainingMethodCall(
					detailAST, methodCallAST, chainedMethodNames)) {

				_checkStyling(methodCallAST);

				continue;
			}

			_checkMethodName(
				chainedMethodNames, "getClass", methodCallAST, detailAST);

			if (chainedMethodNames.size() == 2) {
				continue;
			}

			int concatsCount = Collections.frequency(
				chainedMethodNames, "concat");

			if (concatsCount > 2) {
				log(methodCallAST.getLineNo(), _MSG_AVOID_TOO_MANY_CONCAT);

				continue;
			}

			if ((chainedMethodNames.size() == 3) && (concatsCount == 2)) {
				continue;
			}

			log(
				methodCallAST.getLineNo(), _MSG_AVOID_CHAINING_MULTIPLE,
				DetailASTUtil.getMethodName(methodCallAST));
		}
	}

	private void _checkMethodName(
		List<String> chainedMethodNames, String methodName,
		DetailAST methodCallAST, DetailAST detailAST) {

		String firstMethodName = chainedMethodNames.get(0);

		if (firstMethodName.equals(methodName) &&
			!_isInsideConstructorThisCall(methodCallAST, detailAST)) {

			log(methodCallAST.getLineNo(), _MSG_AVOID_CHAINING, methodName);
		}
	}

	private void _checkStyling(DetailAST methodCallAST) {
		FileContents fileContents = getFileContents();

		for (int i = DetailASTUtil.getStartLine(methodCallAST) + 1;
			 i <= DetailASTUtil.getEndLine(methodCallAST); i++) {

			String line = StringUtil.trim(fileContents.getLine(i - 1));

			if (line.startsWith(").")) {
				return;
			}
		}

		log(
			methodCallAST.getLineNo(), _MSG_INCORRECT_STYLING,
			DetailASTUtil.getMethodName(methodCallAST));
	}

	private List<String> _getChainedMethodNames(DetailAST methodCallAST) {
		List<String> chainedMethodNames = new ArrayList<>();

		chainedMethodNames.add(DetailASTUtil.getMethodName(methodCallAST));

		while (true) {
			DetailAST parentAST = methodCallAST.getParent();

			if (parentAST.getType() != TokenTypes.DOT) {
				return chainedMethodNames;
			}

			methodCallAST = parentAST.getParent();

			if (methodCallAST.getType() != TokenTypes.METHOD_CALL) {
				return chainedMethodNames;
			}

			chainedMethodNames.add(DetailASTUtil.getMethodName(methodCallAST));
		}
	}

	private DetailAST _getClassAST(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST.getParent() == null) {
				break;
			}

			return parentAST.getParent();
		}

		return null;
	}

	private String _getVariableType(DetailAST detailAST, String variableName) {
		List<DetailAST> definitionASTList = new ArrayList<>();

		if (variableName.matches("_[a-z].*")) {
			definitionASTList = DetailASTUtil.getAllChildTokens(
				_getClassAST(detailAST), true, TokenTypes.PARAMETER_DEF,
				TokenTypes.VARIABLE_DEF);
		}
		else if (variableName.matches("[a-z].*")) {
			definitionASTList = DetailASTUtil.getAllChildTokens(
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
					return null;
				}

				return nameAST.getText();
			}
		}

		return null;
	}

	private boolean _isAllowedChainingMethodCall(
		DetailAST detailAST, DetailAST methodCallAST,
		List<String> chainedMethodNames) {

		for (String allowedMethodName : _allowedMethodNames) {
			if (chainedMethodNames.contains(allowedMethodName)) {
				return true;
			}
		}

		DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

		if (dotAST == null) {
			FileContents fileContents = getFileContents();

			FileText fileText = fileContents.getText();

			String content = (String)fileText.getFullText();

			if (content.contains("extends PowerMockito")) {
				return true;
			}

			return false;
		}

		DetailAST nameAST = dotAST.findFirstToken(TokenTypes.IDENT);

		String classOrVariableName = nameAST.getText();

		if (classOrVariableName.matches(".*[Bb]uilder") ||
			classOrVariableName.equals("Mockito") ||
			classOrVariableName.equals("RestAssured")) {

			return true;
		}

		String variableType = _getVariableType(detailAST, classOrVariableName);

		if (variableType != null) {
			for (String allowedClassName : _allowedClassNames) {
				if (variableType.matches(allowedClassName)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _isInsideConstructorThisCall(
		DetailAST methodCallAST, DetailAST detailAST) {

		if (detailAST.getType() != TokenTypes.CTOR_DEF) {
			return false;
		}

		DetailAST parentAST = methodCallAST.getParent();

		while (parentAST != null) {
			String parentASTText = parentAST.getText();

			if ((parentAST.getType() == TokenTypes.CTOR_CALL) &&
				parentASTText.equals("this")) {

				return true;
			}

			parentAST = parentAST.getParent();
		}

		return false;
	}

	private static final String _MSG_AVOID_CHAINING = "chaining.avoid";

	private static final String _MSG_AVOID_CHAINING_MULTIPLE =
		"chaining.avoid.multiple";

	private static final String _MSG_AVOID_TOO_MANY_CONCAT =
		"concat.avoid.too.many";

	private static final String _MSG_INCORRECT_STYLING = "styling.incorrect";

	private String[] _allowedClassNames = new String[0];
	private String[] _allowedMethodNames = new String[0];

}