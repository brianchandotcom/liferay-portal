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
		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST != null) {
				List<DetailAST> childMethodCallDetailASTList =
					DetailASTUtil.getAllChildTokens(
						dotDetailAST, false, TokenTypes.METHOD_CALL);

				// Only check the method that is first in the chain

				if (!childMethodCallDetailASTList.isEmpty()) {
					continue;
				}
			}

			_checkAllowedChaining(methodCallDetailAST);

			if (_isInsideAnonymousClassVariableDefinition(
					methodCallDetailAST)) {

				continue;
			}

			List<String> chain = _getChain(methodCallDetailAST);

			int chainSize = chain.size();

			if (chainSize == 1) {
				continue;
			}

			if (chainSize == 2) {
				if (dotDetailAST == null) {
					continue;
				}

				_checkMethodName(
					chain, "getClass", methodCallDetailAST, detailAST);

				String name1 = chain.get(0);
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

			if (_isAllowedChainingMethodCall(
					detailAST, methodCallDetailAST, chain)) {

				if (chainSize > 1) {
					_checkStyling(detailAST, methodCallDetailAST);
				}

				continue;
			}

			int concatsCount = Collections.frequency(chain, "concat");

			if (concatsCount > 2) {
				log(methodCallDetailAST, _MSG_AVOID_TOO_MANY_CONCAT);

				continue;
			}

			if ((chainSize == 3) && (concatsCount == 2)) {
				continue;
			}

			log(
				methodCallDetailAST, _MSG_AVOID_CHAINING,
				DetailASTUtil.getMethodName(methodCallDetailAST));
		}
	}

	private void _checkAllowedChaining(DetailAST methodCallDetailAST) {
		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while (true) {
			if ((parentDetailAST.getType() == TokenTypes.DOT) ||
				(parentDetailAST.getType() == TokenTypes.METHOD_CALL)) {

				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			break;
		}

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.ASSIGN) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		String classOrVariableName = _getClassOrVariableName(
			methodCallDetailAST);

		if (!Objects.equals(classOrVariableName, "Optional") &&
			!Objects.equals(classOrVariableName, "Stream") &&
			!Objects.equals(classOrVariableName, "Try")) {

			return;
		}

		DetailAST variableNameDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String variableName = variableNameDetailAST.getText();

		String variableTypeName = DetailASTUtil.getVariableTypeName(
			methodCallDetailAST, variableName, false);

		if (!classOrVariableName.equals(variableTypeName)) {
			return;
		}

		List<DetailAST> identDetailASTList = _getIdentDetailASTList(
			parentDetailAST.getParent(), variableName);

		if ((identDetailASTList.size() == 2) &&
			Objects.equals(
				_findFirstParent(
					identDetailASTList.get(0), TokenTypes.ELIST,
					TokenTypes.LITERAL_IF),
				_findFirstParent(
					identDetailASTList.get(1), TokenTypes.ELIST,
					TokenTypes.LITERAL_IF))) {

			log(
				methodCallDetailAST, _MSG_ALLOWED_CHAINING,
				StringBundler.concat(
					classOrVariableName, StringPool.PERIOD,
					DetailASTUtil.getMethodName(methodCallDetailAST)));
		}
	}

	private void _checkMethodName(
		List<String> chainedMethodNames, String methodName,
		DetailAST methodCallDetailAST, DetailAST detailAST) {

		String firstMethodName = chainedMethodNames.get(0);

		if (firstMethodName.equals(methodName) &&
			!_isInsideConstructorThisCall(methodCallDetailAST, detailAST) &&
			!DetailASTUtil.hasParentWithTokenType(
				methodCallDetailAST, TokenTypes.SUPER_CTOR_CALL)) {

			log(methodCallDetailAST, _MSG_AVOID_CHAINING, methodName);
		}
	}

	private void _checkStyling(
		DetailAST detailAST, DetailAST methodCallDetailAST) {

		if (_isInsideConstructorThisCall(methodCallDetailAST, detailAST) ||
			DetailASTUtil.hasParentWithTokenType(
				methodCallDetailAST, TokenTypes.SUPER_CTOR_CALL)) {

			return;
		}

		for (int i = DetailASTUtil.getStartLineNumber(methodCallDetailAST) + 1;
			 i <= DetailASTUtil.getEndLineNumber(methodCallDetailAST); i++) {

			String line = StringUtil.trim(getLine(i - 1));

			if (line.startsWith(").")) {
				return;
			}
		}

		log(
			methodCallDetailAST, _MSG_INCORRECT_STYLING,
			DetailASTUtil.getMethodName(methodCallDetailAST));
	}

	private DetailAST _findFirstParent(DetailAST detailAST, int... types) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return null;
			}

			if (ArrayUtil.contains(types, parentDetailAST.getType())) {
				return parentDetailAST;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private List<String> _getChain(DetailAST methodCallDetailAST) {
		List<String> chain = new ArrayList<>();

		chain.add(DetailASTUtil.getMethodName(methodCallDetailAST));

		while (true) {
			DetailAST parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				return chain;
			}

			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST.getType() != TokenTypes.METHOD_CALL) {
				DetailAST siblingDetailAST =
					methodCallDetailAST.getNextSibling();

				if (siblingDetailAST.getType() == TokenTypes.IDENT) {
					chain.add(siblingDetailAST.getText());
				}

				return chain;
			}

			methodCallDetailAST = grandParentDetailAST;

			chain.add(DetailASTUtil.getMethodName(methodCallDetailAST));
		}
	}

	private String _getClassOrVariableName(DetailAST methodCallDetailAST) {
		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
			return null;
		}

		DetailAST nameDetailAST = null;

		DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NEW) {
			nameDetailAST = firstChildDetailAST.findFirstToken(
				TokenTypes.IDENT);
		}
		else {
			nameDetailAST = dotDetailAST.findFirstToken(TokenTypes.IDENT);
		}

		if (nameDetailAST != null) {
			return nameDetailAST.getText();
		}

		return null;
	}

	private List<DetailAST> _getIdentDetailASTList(
		DetailAST detailAST, String name) {

		List<DetailAST> identDetailASTList = new ArrayList<>();

		for (DetailAST identDetailAST :
				DetailASTUtil.getAllChildTokens(
					detailAST, true, TokenTypes.IDENT)) {

			if (name.equals(identDetailAST.getText())) {
				identDetailASTList.add(identDetailAST);
			}
		}

		return identDetailASTList;
	}

	private DetailAST _getOuterMethodCallDetailAST(DetailAST detailAST) {
		while (true) {
			if ((detailAST.getType() != TokenTypes.DOT) &&
				(detailAST.getType() != TokenTypes.METHOD_CALL)) {

				return null;
			}

			DetailAST parentDetailAST = detailAST.getParent();

			if ((detailAST.getType() == TokenTypes.METHOD_CALL) &&
				(parentDetailAST.getType() != TokenTypes.DOT)) {

				break;
			}

			detailAST = parentDetailAST;
		}

		while (true) {
			DetailAST parentDetailAST = detailAST.getParent();

			if (parentDetailAST == null) {
				return null;
			}

			if (parentDetailAST.getType() == TokenTypes.METHOD_CALL) {
				detailAST = parentDetailAST;

				break;
			}

			detailAST = parentDetailAST;
		}

		while (true) {
			if ((detailAST.getType() != TokenTypes.DOT) &&
				(detailAST.getType() != TokenTypes.METHOD_CALL)) {

				return null;
			}

			DetailAST childDetailAST = detailAST.getFirstChild();

			if ((detailAST.getType() == TokenTypes.DOT) &&
				(childDetailAST.getType() != TokenTypes.METHOD_CALL)) {

				return detailAST.getParent();
			}

			detailAST = childDetailAST;
		}
	}

	private boolean _isAllowedChainingMethodCall(
		DetailAST detailAST, DetailAST methodCallDetailAST,
		List<String> chainedMethodNames) {

		if (_isInsideConstructorThisCall(methodCallDetailAST, detailAST) ||
			DetailASTUtil.hasParentWithTokenType(
				methodCallDetailAST, TokenTypes.SUPER_CTOR_CALL)) {

			return true;
		}

		for (String allowedMethodName : _allowedMethodNames) {
			if (chainedMethodNames.contains(allowedMethodName)) {
				return true;
			}
		}

		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
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

		String classOrVariableName = _getClassOrVariableName(
			methodCallDetailAST);

		if (classOrVariableName != null) {
			if (_isLambdaVariable(methodCallDetailAST, classOrVariableName)) {
				return true;
			}

			for (String allowedClassName : _allowedClassNames) {
				if (classOrVariableName.matches(allowedClassName)) {
					return true;
				}
			}

			String variableTypeName = DetailASTUtil.getVariableTypeName(
				methodCallDetailAST, classOrVariableName, false);

			if (Validator.isNotNull(variableTypeName)) {
				for (String allowedVariableTypeName :
						_allowedVariableTypeNames) {

					if (variableTypeName.matches(allowedVariableTypeName)) {
						return true;
					}
				}
			}
		}

		DetailAST outerMethodCallDetailAST = _getOuterMethodCallDetailAST(
			methodCallDetailAST);

		if (outerMethodCallDetailAST != null) {
			return _isAllowedChainingMethodCall(
				detailAST, outerMethodCallDetailAST,
				_getChain(outerMethodCallDetailAST));
		}

		return false;
	}

	private boolean _isInsideAnonymousClassVariableDefinition(
		DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (parentDetailAST != null) {
			if ((parentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentDetailAST.getType() == TokenTypes.METHOD_DEF)) {

				return false;
			}

			if (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.OBJBLOCK) {
					return true;
				}

				return false;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		return false;
	}

	private boolean _isInsideConstructorThisCall(
		DetailAST methodCallDetailAST, DetailAST detailAST) {

		if (detailAST.getType() != TokenTypes.CTOR_DEF) {
			return false;
		}

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while (parentDetailAST != null) {
			String parentDetailASTText = parentDetailAST.getText();

			if ((parentDetailAST.getType() == TokenTypes.CTOR_CALL) &&
				parentDetailASTText.equals("this")) {

				return true;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		return false;
	}

	private boolean _isLambdaVariable(
		DetailAST methodCallDetailAST, String variableName) {

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while (parentDetailAST != null) {
			if (parentDetailAST.getType() != TokenTypes.LAMBDA) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			DetailAST nameDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if ((nameDetailAST != null) &&
				variableName.equals(nameDetailAST.getText())) {

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