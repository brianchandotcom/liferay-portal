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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class PersistenceCallCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parent = detailAST.getParent();

		if (parent != null) {
			return;
		}

		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), '\\', '/');

		if (!fileName.contains("/modules/")) {
			return;
		}

		FileText fileText = fileContents.getText();

		String content = (String)fileText.getFullText();

		JavaClass javaClass = null;

		try {
			javaClass = JavaClassParser.parseJavaClass(fileName, content);
		}
		catch (Exception e) {
			return;
		}

		Map<String, String> variablesMap = _getVariablesMap(javaClass);

		variablesMap.putAll(
			_getVariablesMap(_getExtendedJavaClass(fileName, content)));

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			_checkMethodCall(
				methodCallAST, javaClass.getImports(), variablesMap,
				javaClass.getPackageName());
		}
	}

	private void _checkClass(
		String className, List<String> importNames, String packageName,
		int lineNo) {

		for (String importName : importNames) {
			if (!importName.endsWith("." + className)) {
				continue;
			}

			int pos = importName.indexOf(".service.persistence.");

			if (pos == -1) {
				return;
			}

			if (!packageName.startsWith(importName.substring(0, pos))) {
				log(lineNo, _MSG_ILLEGAL_PERSISTENCE_CALL, importName);
			}
		}
	}

	private void _checkMethodCall(
		DetailAST methodCallAST, List<String> importNames,
		Map<String, String> variablesMap, String packageName) {

		DetailAST childAST = methodCallAST.getFirstChild();

		if (childAST.getType() != TokenTypes.DOT) {
			return;
		}

		childAST = childAST.getFirstChild();

		if (childAST.getType() != TokenTypes.IDENT) {
			return;
		}

		DetailAST siblingAST = childAST.getNextSibling();

		if (siblingAST.getType() == TokenTypes.IDENT) {
			String methodName = siblingAST.getText();

			if (methodName.equals("clearCache") ||
				methodName.startsWith("create")) {

				return;
			}
		}

		String fieldName = childAST.getText();

		if (fieldName.matches("[A-Z].*")) {
			_checkClass(
				fieldName, importNames, packageName, methodCallAST.getLineNo());
		}
		else {
			_checkVariable(
				fieldName, variablesMap, packageName,
				methodCallAST.getLineNo());
		}
	}

	private void _checkVariable(
		String variableName, Map<String, String> variablesMap,
		String packageName, int lineNo) {

		String fullyQualifiedTypeName = variablesMap.get(variableName);

		if (fullyQualifiedTypeName == null) {
			return;
		}

		int pos = fullyQualifiedTypeName.indexOf(".service.persistence.");

		if (pos == -1) {
			return;
		}

		if (!packageName.startsWith(fullyQualifiedTypeName.substring(0, pos))) {
			log(lineNo, _MSG_ILLEGAL_PERSISTENCE_CALL, fullyQualifiedTypeName);
		}
	}

	private JavaClass _getExtendedJavaClass(String fileName, String content) {
		Matcher matcher = _extendedClassPattern.matcher(content);

		if (!matcher.find()) {
			return null;
		}

		String extendedClassName = matcher.group(1);

		Pattern pattern = Pattern.compile(
			"\nimport (.*\\." + extendedClassName + ");");

		matcher = pattern.matcher(content);

		if (matcher.find()) {
			extendedClassName = matcher.group(1);

			if (!extendedClassName.startsWith("com.liferay.")) {
				return null;
			}
		}

		if (!extendedClassName.contains(StringPool.PERIOD)) {
			extendedClassName =
				JavaSourceUtil.getPackageName(content) + StringPool.PERIOD +
					extendedClassName;
		}

		int pos = fileName.lastIndexOf("/com/liferay/");

		String extendedClassFileName =
			fileName.substring(0, pos + 1) +
				StringUtil.replace(extendedClassName, '.', '/') + ".java";

		try {
			return JavaClassParser.parseJavaClass(
				extendedClassFileName,
				FileUtil.read(new File(extendedClassFileName)));
		}
		catch (Exception e) {
			return null;
		}
	}

	private String _getFullyQualifiedName(
		String className, JavaClass javaClass) {

		for (String importName : javaClass.getImports()) {
			if (importName.endsWith(StringPool.PERIOD + className)) {
				return importName;
			}
		}

		return javaClass.getPackageName() + StringPool.PERIOD + className;
	}

	private Map<String, String> _getVariablesMap(JavaClass javaClass) {
		Map<String, String> variablesMap = new HashMap<>();

		if (javaClass == null) {
			return variablesMap;
		}

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!javaTerm.isJavaVariable()) {
				continue;
			}

			Pattern pattern = Pattern.compile(
				"\\s(\\S+)\\s+(\\S+\\.)?" + javaTerm.getName());

			Matcher matcher = pattern.matcher(javaTerm.getContent());

			if (!matcher.find()) {
				continue;
			}

			String fieldTypeClassName = matcher.group(1);

			if (!fieldTypeClassName.contains(StringPool.PERIOD)) {
				fieldTypeClassName = _getFullyQualifiedName(
					fieldTypeClassName, javaClass);
			}

			variablesMap.put(javaTerm.getName(), fieldTypeClassName);
		}

		return variablesMap;
	}

	private static final String _MSG_ILLEGAL_PERSISTENCE_CALL =
		"persistence.call.illegal";

	private final Pattern _extendedClassPattern = Pattern.compile(
		"\\sextends\\s+(\\w+)\\W");

}