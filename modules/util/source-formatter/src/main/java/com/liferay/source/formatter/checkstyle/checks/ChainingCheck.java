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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

			_checkAllowedChaining(methodCallAST);

			if (_isInsideAnonymousClassVariableDefinition(methodCallAST)) {
				continue;
			}

			List<String> chain = _getChain(methodCallAST);

			int chainSize = chain.size();

			if (chainSize == 1) {
				continue;
			}

			if (chainSize == 2) {
				if (dotAST == null) {
					continue;
				}

				_checkMethodName(chain, "getClass", methodCallAST, detailAST);

				String name1 = chain.get(0);

				if ((name1.equals("getParamValue") ||
					 name1.equals("getValue")) &&
					DetailASTUtil.hasParentWithTokenType(
						detailAST, TokenTypes.ENUM_DEF)) {

					continue;
				}

				String name2 = chain.get(1);

				if (name1.equals("concat") || name2.equals("concat")) {
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

			if (_isAllowedChainingMethodCall(detailAST, methodCallAST, chain)) {
				if (chainSize > 1) {
					_checkStyling(detailAST, methodCallAST);
				}

				continue;
			}

			int concatsCount = Collections.frequency(chain, "concat");

			if (concatsCount > 2) {
				log(methodCallAST, _MSG_AVOID_TOO_MANY_CONCAT);

				continue;
			}

			if ((chainSize == 3) && (concatsCount == 2)) {
				continue;
			}

			log(
				methodCallAST, _MSG_AVOID_CHAINING,
				DetailASTUtil.getMethodName(methodCallAST));
		}
	}

	private void _checkAllowedChaining(DetailAST methodCallAST) {
		DetailAST parentAST = methodCallAST.getParent();

		while (true) {
			if ((parentAST.getType() == TokenTypes.DOT) ||
				(parentAST.getType() == TokenTypes.METHOD_CALL)) {

				parentAST = parentAST.getParent();

				continue;
			}

			break;
		}

		if (parentAST.getType() != TokenTypes.EXPR) {
			return;
		}

		parentAST = parentAST.getParent();

		if (parentAST.getType() != TokenTypes.ASSIGN) {
			return;
		}

		parentAST = parentAST.getParent();

		if (parentAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		String classOrVariableName = _getClassOrVariableName(methodCallAST);

		if (!Objects.equals(classOrVariableName, "Optional") &&
			!Objects.equals(classOrVariableName, "Stream") &&
			!Objects.equals(classOrVariableName, "Try")) {

			return;
		}

		DetailAST variableNameAST = parentAST.findFirstToken(TokenTypes.IDENT);

		String variableName = variableNameAST.getText();

		String variableTypeName = DetailASTUtil.getVariableTypeName(
			methodCallAST, variableName, false);

		if (!classOrVariableName.equals(variableTypeName)) {
			return;
		}

		List<DetailAST> identASTList = _getIdentASTList(
			parentAST.getParent(), variableName);

		if ((identASTList.size() == 2) &&
			Objects.equals(
				_findFirstParent(
					identASTList.get(0), TokenTypes.ELIST,
					TokenTypes.LITERAL_IF),
				_findFirstParent(
					identASTList.get(1), TokenTypes.ELIST,
					TokenTypes.LITERAL_IF))) {

			log(
				methodCallAST, _MSG_ALLOWED_CHAINING,
				StringBundler.concat(
					classOrVariableName, StringPool.PERIOD,
					DetailASTUtil.getMethodName(methodCallAST)));
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

			log(methodCallAST, _MSG_AVOID_CHAINING, methodName);
		}
	}

	private void _checkStyling(DetailAST detailAST, DetailAST methodCallAST) {
		if (_isInsideConstructorThisCall(methodCallAST, detailAST) ||
			DetailASTUtil.hasParentWithTokenType(
				methodCallAST, TokenTypes.SUPER_CTOR_CALL)) {

			return;
		}

		for (int i = DetailASTUtil.getStartLineNumber(methodCallAST) + 1;
			 i <= DetailASTUtil.getEndLineNumber(methodCallAST); i++) {

			String line = StringUtil.trim(getLine(i - 1));

			if (line.startsWith(").")) {
				return;
			}
		}

		log(
			methodCallAST, _MSG_INCORRECT_STYLING,
			DetailASTUtil.getMethodName(methodCallAST));
	}

	private DetailAST _findFirstParent(DetailAST detailAST, int... types) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return null;
			}

			if (ArrayUtil.contains(types, parentAST.getType())) {
				return parentAST;
			}

			parentAST = parentAST.getParent();
		}
	}

	private List<String> _getChain(DetailAST methodCallAST) {
		List<String> chain = new ArrayList<>();

		chain.add(DetailASTUtil.getMethodName(methodCallAST));

		while (true) {
			DetailAST parentAST = methodCallAST.getParent();

			if (parentAST.getType() != TokenTypes.DOT) {
				return chain;
			}

			DetailAST grandParentAST = parentAST.getParent();

			if (grandParentAST.getType() != TokenTypes.METHOD_CALL) {
				DetailAST siblingAST = methodCallAST.getNextSibling();

				if (siblingAST.getType() == TokenTypes.IDENT) {
					chain.add(siblingAST.getText());
				}

				return chain;
			}

			methodCallAST = grandParentAST;

			chain.add(DetailASTUtil.getMethodName(methodCallAST));
		}
	}

	private String _getClassOrVariableName(DetailAST methodCallAST) {
		DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

		if (dotAST == null) {
			return null;
		}

		DetailAST nameAST = null;

		DetailAST firstChildAST = dotAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.LITERAL_NEW) {
			nameAST = firstChildAST.findFirstToken(TokenTypes.IDENT);
		}
		else {
			nameAST = dotAST.findFirstToken(TokenTypes.IDENT);
		}

		if (nameAST != null) {
			return nameAST.getText();
		}

		return null;
	}

	private List<DetailAST> _getIdentASTList(DetailAST detailAST, String name) {
		List<DetailAST> identASTList = new ArrayList<>();

		for (DetailAST identAST :
				DetailASTUtil.getAllChildTokens(
					detailAST, true, TokenTypes.IDENT)) {

			if (name.equals(identAST.getText())) {
				identASTList.add(identAST);
			}
		}

		return identASTList;
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

			String fileName = StringUtil.replace(
				fileContents.getFileName(), CharPool.BACK_SLASH,
				CharPool.SLASH);

			String className = JavaSourceUtil.getClassName(fileName);

			for (String allowedClassName : _allowedClassNames) {
				if (className.matches(allowedClassName)) {
					return true;
				}
			}

			FileText fileText = fileContents.getText();

			String content = (String)fileText.getFullText();

			if (content.contains("extends PowerMockito")) {
				return true;
			}

			return false;
		}

		String classOrVariableName = _getClassOrVariableName(methodCallAST);

		if (classOrVariableName != null) {
			if (_isLambdaVariable(methodCallAST, classOrVariableName)) {
				return true;
			}

			for (String allowedClassName : _allowedClassNames) {
				if (classOrVariableName.matches(allowedClassName)) {
					return true;
				}
			}

			String variableTypeName = DetailASTUtil.getVariableTypeName(
				methodCallAST, classOrVariableName, false);

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
				detailAST, outerMethodCallAST, _getChain(outerMethodCallAST));
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

	private boolean _isLambdaVariable(
		DetailAST methodCallAST, String variableName) {

		DetailAST parentAST = methodCallAST.getParent();

		while (parentAST != null) {
			if (parentAST.getType() != TokenTypes.LAMBDA) {
				parentAST = parentAST.getParent();

				continue;
			}

			DetailAST nameAST = parentAST.findFirstToken(TokenTypes.IDENT);

			if ((nameAST != null) && variableName.equals(nameAST.getText())) {
				return true;
			}

			return false;
		}

		return false;
	}

	private static final String _MSG_ALLOWED_CHAINING = "chaining.allowed";

	private static final String _MSG_AVOID_CHAINING = "chaining.avoid";

	private static final String _MSG_AVOID_TOO_MANY_CONCAT =
		"concat.avoid.too.many";

	private static final String _MSG_INCORRECT_STYLING = "styling.incorrect";

	private String[] _allowedClassNames = new String[0];
	private String[] _allowedMethodNames = new String[0];
	private String[] _allowedVariableTypeNames = new String[0];

}