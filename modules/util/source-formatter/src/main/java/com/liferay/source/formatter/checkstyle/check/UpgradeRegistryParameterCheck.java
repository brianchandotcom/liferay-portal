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

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qi Zhang
 */
public class UpgradeRegistryParameterCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (!getAbsolutePath().endsWith("UpgradeStepRegistrator.java")) {
			return;
		}

		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

		if (!StringUtil.equals(fullIdent.getText(), "registry.register") ||
			!StringUtil.equals("register", getMethodName(detailAST))) {

			return;
		}

		DetailAST eListDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		DetailAST firstChildDetailAST = eListDetailAST.getFirstChild();

		int parameterCount = 0;

		while (firstChildDetailAST != null) {
			if (firstChildDetailAST.getType() != TokenTypes.COMMA) {
				parameterCount++;
			}

			if ((parameterCount > 2) &&
				(firstChildDetailAST.getType() != TokenTypes.COMMA)) {

				if (!_checkParameter(firstChildDetailAST)) {
					return;
				}

				_checkReferenceFileDetailAST(firstChildDetailAST);
			}

			firstChildDetailAST = firstChildDetailAST.getNextSibling();
		}
	}

	private boolean _checkParameter(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return false;
		}

		DetailAST eListDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (eListDetailAST.getChildCount() <= 0) {
			return true;
		}

		return false;
	}

	private void _checkReferenceFileDetailAST(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		String packageName = getPackageName(detailAST);

		packageName = packageName.substring(
			0, packageName.lastIndexOf(StringPool.PERIOD));

		List<String> importNames = getImportNames(detailAST);
		String targetFilePath = null;
		String parameterName = null;

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			for (String importName : importNames) {
				if (importName.endsWith(firstChildDetailAST.getText()) &&
					importName.startsWith(packageName)) {

					targetFilePath = importName;
					parameterName = firstChildDetailAST.getText();
				}
			}
		}
		else if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			String fullIdentText = fullIdent.getText();

			if (fullIdentText.startsWith(packageName)) {
				targetFilePath = fullIdentText;
				parameterName = fullIdentText;
			}
		}

		if (Validator.isNull(targetFilePath)) {
			return;
		}

		targetFilePath = StringUtil.replace(
			targetFilePath, CharPool.PERIOD, CharPool.SLASH);

		String absolutePath = getAbsolutePath();

		absolutePath = absolutePath.substring(
			0, absolutePath.indexOf("com/liferay"));

		File file = new File(absolutePath + targetFilePath + ".java");

		try {
			String content = FileUtil.read(file, false);

			if (Validator.isNull(content)) {
				return;
			}

			FileText fileText = new FileText(
				file, CheckstyleUtil.getLines(content));

			FileContents fileContents = new FileContents(fileText);

			DetailAST rootDetailAST = JavaParser.parse(fileContents);

			_checkTargetFile(rootDetailAST, parameterName, detailAST);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _checkTargetFile(
		DetailAST detailAST, String parameterName,
		DetailAST parameterDetailAST) {

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return;
			}

			if (nextSiblingDetailAST.getType() == TokenTypes.CLASS_DEF) {
				break;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}

		List<DetailAST> methodDefDetailASTs = getAllChildTokens(
			nextSiblingDetailAST, true, TokenTypes.METHOD_DEF);

		if (ListUtil.isEmpty(methodDefDetailASTs) ||
			(methodDefDetailASTs.size() > 1)) {

			return;
		}

		DetailAST methodDefDetailAST = methodDefDetailASTs.get(0);

		DetailAST identDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (!StringUtil.equals("doUpgrade", identDetailAST.getText())) {
			return;
		}

		DetailAST parametersDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		if (parametersDetailAST.getChildCount() > 0) {
			return;
		}

		DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		DetailAST childDetailAST = sListDetailAST.getFirstChild();

		String currentMethodName = null;
		String parameter1 = null;
		String parameter2 = null;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.SEMI) &&
				(tokenType != TokenTypes.RCURLY)) {

				return;
			}

			if ((tokenType == TokenTypes.SEMI) ||
				(tokenType == TokenTypes.RCURLY)) {

				childDetailAST = childDetailAST.getNextSibling();

				continue;
			}

			DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return;
			}

			String methodName = getMethodName(firstChildDetailAST);

			if (!StringUtil.equals(methodName, "alterColumnType") &&
				!StringUtil.equals(methodName, "alterTableAddColumn") &&
				!StringUtil.equals(methodName, "alterTableDropColumn")) {

				return;
			}

			List<String> parameters = _getParameterList(firstChildDetailAST);

			if (ListUtil.isEmpty(parameters)) {
				return;
			}

			if (Validator.isNull(currentMethodName)) {
				currentMethodName = methodName;

				if (StringUtil.equals(methodName, "alterColumnType")) {
					parameter1 = parameters.get(0);
					parameter2 = parameters.get(2);
				}
				else {
					parameter1 = parameters.get(0);
				}
			}
			else {
				if (!StringUtil.equals(currentMethodName, methodName) ||
					(StringUtil.equals(methodName, "alterColumnType") &&
					 (!StringUtil.equals(parameters.get(0), parameter1) ||
					  !StringUtil.equals(parameters.get(2), parameter2))) ||
					(!StringUtil.equals(methodName, "alterColumnType") &&
					 !StringUtil.equals(parameters.get(0), parameter1))) {

					return;
				}
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		log(parameterDetailAST, _MSG_PARAMETER_SIMPLY, parameterName);
	}

	private List<String> _getParameterList(DetailAST detailAST) {
		DetailAST eListDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		DetailAST firstChildDetailAST = eListDetailAST.getFirstChild();

		List<String> parameters = new ArrayList<>();

		while (firstChildDetailAST != null) {
			int tokenType = firstChildDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.COMMA)) {

				return null;
			}

			if (tokenType == TokenTypes.EXPR) {
				DetailAST childDetailAST = firstChildDetailAST.getFirstChild();

				if (childDetailAST.getType() != TokenTypes.STRING_LITERAL) {
					return null;
				}

				parameters.add(childDetailAST.getText());
			}

			firstChildDetailAST = firstChildDetailAST.getNextSibling();
		}

		return parameters;
	}

	private static final String _MSG_PARAMETER_SIMPLY = "parameter.can.simply";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeRegistryParameterCheck.class);

}