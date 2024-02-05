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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.Map;

/**
 * @author Alan Huang
 */
public class RESTDTOSetCallCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, TokenTypes.METHOD_CALL)) {

			_checkSetCall(absolutePath, childDetailAST);
		}
	}

	private void _checkSetCall(String absolutePath, DetailAST detailAST) {
		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			return;
		}

		String methodName = getMethodName(detailAST);

		if (!methodName.startsWith("set")) {
			return;
		}

		String variableName = getVariableName(detailAST);

		if (variableName == null) {
			return;
		}

		String variableTypeName = getVariableTypeName(
			detailAST, variableName, false, false, true);

		if ((variableTypeName == null) ||
			!variableTypeName.startsWith("com.liferay.") ||
			!variableTypeName.contains(".dto.v") ||
			!_isRESTDTO(absolutePath, variableTypeName)) {

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

	private boolean _isRESTDTO(String absolutePath, String variableTypeName) {
		if (variableTypeName == null) {
			return false;
		}

		File javaFile = JavaSourceUtil.getJavaFile(
			variableTypeName, _getRootDirName(absolutePath),
			_getBundleSymbolicNamesMap(absolutePath));

		if (javaFile == null) {
			return false;
		}

		String javaFileAbsolutePath = SourceUtil.getAbsolutePath(javaFile);

		int x = StringUtil.lastIndexOfAny(
			javaFileAbsolutePath,
			new String[] {"-api/src/main/", "-client/src/main/"});

		if (x == -1) {
			return false;
		}

		String restOpenAPIFilePath =
			javaFileAbsolutePath.substring(0, x) + "-impl/rest-openapi.yaml";

		File file = new File(restOpenAPIFilePath);

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