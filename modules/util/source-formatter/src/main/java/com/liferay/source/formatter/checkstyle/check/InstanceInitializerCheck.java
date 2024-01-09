/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Alan Huang
 */
public class InstanceInitializerCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.INSTANCE_INIT};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		List<DetailAST> exprDetailASTList = getAllChildTokens(
			firstChildDetailAST, false, TokenTypes.EXPR);

		if (exprDetailASTList.size() >= 2) {
			_checkAttributeOrder(exprDetailASTList);
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		JavaClass javaClass = null;

		try {
			javaClass = _getJavaClass(absolutePath, detailAST, parentDetailAST);
		}
		catch (IOException | ParseException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return;
		}

		if (javaClass == null) {
			return;
		}

		List<DetailAST> childDetailASTList = getAllChildTokens(
			firstChildDetailAST, false, TokenTypes.EXPR, TokenTypes.LITERAL_IF);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (childDetailAST.getType() == TokenTypes.LITERAL_IF) {
				DetailAST slistDetailAST = childDetailAST.findFirstToken(
					TokenTypes.SLIST);

				for (DetailAST exprDetailAST :
						getAllChildTokens(
							slistDetailAST, false, TokenTypes.EXPR)) {

					_checkExprStatement(exprDetailAST, javaClass, true);
				}
			}
			else {
				_checkExprStatement(childDetailAST, javaClass, false);
			}
		}
	}

	private void _checkAttributeOrder(List<DetailAST> exprDetailASTList) {
		String previousVariableName = null;
		String previousMethodName = null;

		for (DetailAST exprDetailAST : exprDetailASTList) {
			DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.ASSIGN) {
				String variableName = getName(firstChildDetailAST);

				if (Validator.isNotNull(
						getTypeName(
							getVariableTypeDetailAST(
								firstChildDetailAST, variableName, false),
							false))) {

					continue;
				}

				if ((previousVariableName != null) &&
					(previousVariableName.compareToIgnoreCase(variableName) >
						0)) {

					log(
						exprDetailAST, _MSG_INCORRECT_ASSIGN_ORDER,
						variableName, previousVariableName,
						firstChildDetailAST.getLineNo());
				}
				else if (Validator.isNotNull(previousMethodName)) {
					log(
						exprDetailAST, _MSG_MOVE_ASSIGN_BEFORE_METHOD_CALL,
						variableName, previousMethodName,
						firstChildDetailAST.getLineNo());
				}

				previousVariableName = variableName;
			}
			else if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
				String methodName = getName(firstChildDetailAST);

				if (Validator.isNull(methodName) ||
					!methodName.matches("set[A-Z].+")) {

					continue;
				}

				if ((previousMethodName != null) &&
					(previousMethodName.compareToIgnoreCase(methodName) > 0)) {

					log(
						exprDetailAST, _MSG_INCORRECT_METHOD_CALL_ORDER,
						methodName, previousMethodName,
						firstChildDetailAST.getLineNo());
				}

				previousMethodName = methodName;
			}
		}
	}

	private void _checkExprStatement(
		DetailAST exprDetailAST, JavaClass javaClass,
		boolean insideIfStatement) {

		DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return;
		}

		if (firstChildDetailAST.getType() == TokenTypes.ASSIGN) {
			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				return;
			}

			String variableName = firstChildDetailAST.getText();

			String methodName =
				"set" + StringUtil.upperCaseFirstLetter(variableName);

			_checkHasReplacableMethodSignature(
				firstChildDetailAST, methodName, javaClass, insideIfStatement);
		}
		else if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
			DetailAST dotDetailAST = firstChildDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST != null) {
				return;
			}

			String methodName = getMethodName(firstChildDetailAST);

			if (!methodName.startsWith("set")) {
				return;
			}

			if (!insideIfStatement) {
				DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
					TokenTypes.ELIST);

				DetailAST childDetailAST = elistDetailAST.getFirstChild();

				if ((childDetailAST == null) ||
					(childDetailAST.getType() == TokenTypes.LAMBDA) ||
					(childDetailAST.findFirstToken(TokenTypes.METHOD_REF) !=
						null)) {

					return;
				}
			}

			_checkHasReplacableMethodSignature(
				firstChildDetailAST, methodName, javaClass, insideIfStatement);
		}
	}

	private void _checkHasReplacableMethodSignature(
		DetailAST detailAST, String methodName, JavaClass javaClass,
		boolean insideIfStatement) {

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!javaTerm.isJavaMethod() || javaTerm.isPrivate()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)javaTerm;

			if (!StringUtil.equals(methodName, javaMethod.getName())) {
				continue;
			}

			JavaSignature javaSignature = javaMethod.getSignature();

			List<JavaParameter> javaParameters = javaSignature.getParameters();

			if (javaParameters.size() != 1) {
				continue;
			}

			JavaParameter javaParameter = javaParameters.get(0);

			String parameterType = javaParameter.getParameterType();

			if (parameterType.startsWith("UnsafeSupplier")) {
				if (insideIfStatement) {
					log(
						detailAST, _MSG_INLINE_IF_STATEMENT, methodName,
						parameterType);
				}
				else {
					log(
						detailAST, _MSG_USE_SET_METHOD_INSTEAD, methodName,
						parameterType);
				}

				return;
			}
		}
	}

	private synchronized Map<String, String> _getBundleSymbolicNamesMap(
		String absolutePath) {

		if (_bundleSymbolicNamesMap == null) {
			_bundleSymbolicNamesMap = BNDSourceUtil.getBundleSymbolicNamesMap(
				_getRootDirName(absolutePath));
		}

		return _bundleSymbolicNamesMap;
	}

	private JavaClass _getJavaClass(
			String absolutePath, DetailAST detailAST, DetailAST parentDetailAST)
		throws IOException, ParseException {

		String fullyQualifiedTypeName = null;

		DetailAST firstChildDetailAST = parentDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			fullyQualifiedTypeName = getFullyQualifiedTypeName(
				firstChildDetailAST.getText(), detailAST, false);
		}
		else if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			fullyQualifiedTypeName = fullIdent.getText();
		}

		if (fullyQualifiedTypeName == null) {
			return null;
		}

		File javaFile = JavaSourceUtil.getJavaFile(
			fullyQualifiedTypeName, _getRootDirName(absolutePath),
			_getBundleSymbolicNamesMap(absolutePath));

		if (javaFile == null) {
			return null;
		}

		return JavaClassParser.parseJavaClass(
			SourceUtil.getAbsolutePath(javaFile), FileUtil.read(javaFile));
	}

	private synchronized String _getRootDirName(String absolutePath) {
		if (_rootDirName != null) {
			return _rootDirName;
		}

		_rootDirName = SourceUtil.getRootDirName(absolutePath);

		return _rootDirName;
	}

	private static final String _MSG_INCORRECT_ASSIGN_ORDER =
		"assign.order.incorrect";

	private static final String _MSG_INCORRECT_METHOD_CALL_ORDER =
		"method.call.order.incorrect";

	private static final String _MSG_INLINE_IF_STATEMENT =
		"if.statement.inline";

	private static final String _MSG_MOVE_ASSIGN_BEFORE_METHOD_CALL =
		"assign.move.before.method.call";

	private static final String _MSG_USE_SET_METHOD_INSTEAD =
		"set.method.use.instead";

	private static final Log _log = LogFactoryUtil.getLog(
		InstanceInitializerCheck.class);

	private volatile Map<String, String> _bundleSymbolicNamesMap;
	private volatile String _rootDirName;

}