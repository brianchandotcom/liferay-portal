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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

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
public class ChainingCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	public void setAllowedClassNames(String allowedClassNames) {
		_allowedClassNames = ArrayUtil.append(
			_allowedClassNames, StringUtil.split(allowedClassNames));
	}

	public void setAllowedMethodNames(String allowedMethodNames) {
		_allowedMethodNames = ArrayUtil.append(
			_allowedMethodNames, StringUtil.split(allowedMethodNames));
	}

	public void setAllowedVariableTypeNames(String allowedVariableTypeNames) {
		_allowedVariableTypeNames = ArrayUtil.append(
			_allowedVariableTypeNames,
			StringUtil.split(allowedVariableTypeNames));
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
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

			if (_isInsideAnonymousClassVariableDefinition(methodCallAST)) {
				continue;
			}

			List<String> chainedMethodNames = _getChainedMethodNames(
				methodCallAST);

			int chainSize = chainedMethodNames.size();

			if (chainSize == 1) {
				continue;
			}

			if (chainSize == 2) {
				if (dotAST == null) {
					continue;
				}

				_checkMethodName(
					chainedMethodNames, "getClass", methodCallAST, detailAST);

				String methodName1 = chainedMethodNames.get(0);

				if ((methodName1.equals("getParamValue") ||
					 methodName1.equals("getValue")) &&
					DetailASTUtil.hasParentWithTokenType(
						detailAST, TokenTypes.ENUM_DEF)) {

					continue;
				}

				String methodName2 = chainedMethodNames.get(1);

				if (methodName1.equals("concat") ||
					methodName2.equals("concat")) {

					continue;
				}

				FileContents fileContents = getFileContents();

				String fileName = StringUtil.replace(
					fileContents.getFileName(), CharPool.BACK_SLASH,
					CharPool.SLASH);

				if (fileName.contains("/test/") ||
					fileName.contains("/testIntegration/")) {

					continue;
				}
			}

			if (_isAllowedChainingMethodCall(
					detailAST, methodCallAST, chainedMethodNames)) {

				if (chainSize > 2) {
					_checkStyling(methodCallAST);
				}

				continue;
			}

			int concatsCount = Collections.frequency(
				chainedMethodNames, "concat");

			if (concatsCount > 2) {
				log(methodCallAST.getLineNo(), _MSG_AVOID_TOO_MANY_CONCAT);

				continue;
			}

			if ((chainSize == 3) && (concatsCount == 2)) {
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
			!_isInsideConstructorThisCall(methodCallAST, detailAST) &&
			!DetailASTUtil.hasParentWithTokenType(
				methodCallAST, TokenTypes.SUPER_CTOR_CALL)) {

			log(methodCallAST.getLineNo(), _MSG_AVOID_CHAINING, methodName);
		}
	}

	private void _checkStyling(DetailAST methodCallAST) {
		for (int i = DetailASTUtil.getStartLine(methodCallAST) + 1;
			 i <= DetailASTUtil.getEndLine(methodCallAST); i++) {

			String line = StringUtil.trim(getLine(i - 1));

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

	private DetailAST _getOuterMethodCallAST(DetailAST detailAST) {
		while (true) {
			if ((detailAST.getType() != TokenTypes.DOT) &&
				(detailAST.getType() != TokenTypes.METHOD_CALL)) {

				return null;
			}

			DetailAST parentAST = detailAST.getParent();

			if ((detailAST.getType() == TokenTypes.METHOD_CALL) &&
				(parentAST.getType() != TokenTypes.DOT)) {

				break;
			}

			detailAST = parentAST;
		}

		while (true) {
			DetailAST parentAST = detailAST.getParent();

			if (parentAST == null) {
				return null;
			}

			if (parentAST.getType() == TokenTypes.METHOD_CALL) {
				detailAST = parentAST;

				break;
			}

			detailAST = parentAST;
		}

		while (true) {
			DetailAST childAST = detailAST.getFirstChild();

			if ((detailAST.getType() != TokenTypes.DOT) &&
				(detailAST.getType() != TokenTypes.METHOD_CALL)) {

				return null;
			}

			if ((detailAST.getType() == TokenTypes.DOT) &&
				(childAST.getType() != TokenTypes.METHOD_CALL)) {

				return detailAST.getParent();
			}

			detailAST = childAST;
		}
	}

	private boolean _isAllowedChainingMethodCall(
		DetailAST detailAST, DetailAST methodCallAST,
		List<String> chainedMethodNames) {

		if (_isInsideConstructorThisCall(methodCallAST, detailAST) ||
			DetailASTUtil.hasParentWithTokenType(
				methodCallAST, TokenTypes.SUPER_CTOR_CALL)) {

			return true;
		}

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

		DetailAST nameAST = null;

		DetailAST firstChild = dotAST.getFirstChild();

		if (firstChild.getType() == TokenTypes.LITERAL_NEW) {
			nameAST = firstChild.findFirstToken(TokenTypes.IDENT);
		}
		else {
			nameAST = dotAST.findFirstToken(TokenTypes.IDENT);
		}

		if (nameAST != null) {
			String classOrVariableName = nameAST.getText();

			for (String allowedClassName : _allowedClassNames) {
				if (classOrVariableName.matches(allowedClassName)) {
					return true;
				}
			}

			String variableTypeName = DetailASTUtil.getVariableTypeName(
				methodCallAST, classOrVariableName);

			if (Validator.isNotNull(variableTypeName)) {
				for (String allowedVariableTypeName :
						_allowedVariableTypeNames) {

					if (variableTypeName.matches(allowedVariableTypeName)) {
						return true;
					}
				}
			}
		}

		DetailAST outerMethodCallAST = _getOuterMethodCallAST(methodCallAST);

		if (outerMethodCallAST != null) {
			return _isAllowedChainingMethodCall(
				detailAST, outerMethodCallAST,
				_getChainedMethodNames(outerMethodCallAST));
		}

		return false;
	}

	private boolean _isInsideAnonymousClassVariableDefinition(
		DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (parentAST != null) {
			if ((parentAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentAST.getType() == TokenTypes.METHOD_DEF)) {

				return false;
			}

			if (parentAST.getType() == TokenTypes.VARIABLE_DEF) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.OBJBLOCK) {
					return true;
				}

				return false;
			}

			parentAST = parentAST.getParent();
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
	private String[] _allowedVariableTypeNames = new String[0];

}