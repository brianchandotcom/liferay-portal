/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.List;
import java.util.Map;

/**
 * @author Alan Huang
 */
public class RESTDTOSetCallCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INSTANCE_INIT};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		if (detailAST.getType() == TokenTypes.CLASS_DEF) {
			_checkClassDeclaration(detailAST, absolutePath);
		}
		else if (detailAST.getType() == TokenTypes.INSTANCE_INIT) {
			_checkInstanceInitializer(detailAST, absolutePath);
		}
	}

	private void _checkClassDeclaration(
		DetailAST detailAST, String absolutePath) {

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			objBlockDetailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST == null) {
				continue;
			}

			String methodName = getMethodName(methodCallDetailAST);

			if (!methodName.startsWith("set")) {
				continue;
			}

			String variableName = getVariableName(methodCallDetailAST);

			String fullyQualifiedTypeName = getVariableTypeName(
				methodCallDetailAST, variableName, false, false, true);

			if (fullyQualifiedTypeName == null) {
				continue;
			}

			_checkSetCall(
				absolutePath, methodCallDetailAST, methodName,
				fullyQualifiedTypeName);
		}
	}

	private void _checkInstanceInitializer(
		DetailAST detailAST, String absolutePath) {

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		List<DetailAST> childDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.ASSIGN, TokenTypes.METHOD_CALL);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (childDetailAST.getType() == TokenTypes.ASSIGN) {
				DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
					continue;
				}

				String variableName = firstChildDetailAST.getText();

				String methodName =
					"set" + StringUtil.upperCaseFirstLetter(variableName);

				log(childDetailAST, _MSG_USE_SET_METHOD_INSTEAD, methodName);
			}
			else {
				DetailAST dotDetailAST = childDetailAST.findFirstToken(
					TokenTypes.DOT);

				if (dotDetailAST != null) {
					continue;
				}

				String methodName = getMethodName(childDetailAST);

				if (!methodName.startsWith("set")) {
					continue;
				}

				parentDetailAST = getParentWithTokenType(
					childDetailAST, TokenTypes.INSTANCE_INIT);

				if (parentDetailAST == null) {
					continue;
				}

				parentDetailAST = parentDetailAST.getParent();

				if ((parentDetailAST == null) ||
					(parentDetailAST.getType() != TokenTypes.OBJBLOCK)) {

					continue;
				}

				parentDetailAST = parentDetailAST.getParent();

				if ((parentDetailAST == null) ||
					(parentDetailAST.getType() != TokenTypes.LITERAL_NEW)) {

					continue;
				}

				String fullyQualifiedTypeName = null;

				DetailAST firstChildDetailAST = parentDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
					fullyQualifiedTypeName = getFullyQualifiedTypeName(
						firstChildDetailAST.getText(), parentDetailAST, false);
				}
				else if (firstChildDetailAST.getType() == TokenTypes.DOT) {
					FullIdent fullIdent = FullIdent.createFullIdent(
						firstChildDetailAST);

					fullyQualifiedTypeName = fullIdent.getText();
				}

				if (fullyQualifiedTypeName == null) {
					continue;
				}

				_checkSetCall(
					absolutePath, childDetailAST, methodName,
					fullyQualifiedTypeName);
			}
		}
	}

	private void _checkSetCall(
		String absolutePath, DetailAST detailAST, String methodName,
		String fullyQualifiedTypeName) {

		if (!fullyQualifiedTypeName.startsWith("com.liferay.") ||
			!fullyQualifiedTypeName.contains(".dto.v") ||
			!_isRESTDTOType(absolutePath, fullyQualifiedTypeName)) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.LITERAL_IF) {
			log(detailAST, _MSG_INLINE_IF_STATEMENT, methodName);
		}
		else {
			DetailAST elistDetailAST = detailAST.findFirstToken(
				TokenTypes.ELIST);

			DetailAST childDetailAST = elistDetailAST.getFirstChild();

			if ((childDetailAST == null) ||
				(childDetailAST.getType() == TokenTypes.LAMBDA) ||
				(childDetailAST.findFirstToken(TokenTypes.METHOD_REF) !=
					null)) {

				return;
			}

			log(detailAST, _MSG_USE_SET_METHOD_INSTEAD, methodName);
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

	private synchronized String _getRootDirName(String absolutePath) {
		if (_rootDirName != null) {
			return _rootDirName;
		}

		_rootDirName = SourceUtil.getRootDirName(absolutePath);

		return _rootDirName;
	}

	private boolean _isRESTDTOType(
		String absolutePath, String fullyQualifiedTypeName) {

		File file = JavaSourceUtil.getJavaFile(
			fullyQualifiedTypeName, _getRootDirName(absolutePath),
			_getBundleSymbolicNamesMap(absolutePath));

		if (file == null) {
			return false;
		}

		String dtoFileAbsolutePath = SourceUtil.getAbsolutePath(file);

		int x = StringUtil.lastIndexOfAny(
			dtoFileAbsolutePath,
			new String[] {"-api/src/main/", "-client/src/main/"});

		if (x == -1) {
			return false;
		}

		String restOpenAPIFilePath =
			dtoFileAbsolutePath.substring(0, x) + "-impl/rest-openapi.yaml";

		file = new File(restOpenAPIFilePath);

		if (!file.exists()) {
			return false;
		}

		return true;
	}

	private static final String _MSG_INLINE_IF_STATEMENT =
		"if.statement.inline";

	private static final String _MSG_USE_SET_METHOD_INSTEAD =
		"set.method.use.instead";

	private volatile Map<String, String> _bundleSymbolicNamesMap;
	private volatile String _rootDirName;

}