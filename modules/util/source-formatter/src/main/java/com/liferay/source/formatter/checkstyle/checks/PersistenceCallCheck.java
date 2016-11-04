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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.ThreadSafeClassLibrary;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.DefaultDocletTagFactory;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.Type;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class PersistenceCallCheck extends AbstractCheck {

	public static final String MSG_ILLEGAL_PERSISTENCE_CALL =
		"persistence.call.illegal";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
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

		JavaDocBuilder javaDocBuilder = new JavaDocBuilder(
			new DefaultDocletTagFactory(), new ThreadSafeClassLibrary());

		javaDocBuilder.addSource(new UnsyncStringReader(content));

		JavaClass javaClass = _getJavaClass(javaDocBuilder, fileName);

		javaDocBuilder = _addExtendedClassSource(
			javaDocBuilder, javaClass, fileName);

		List<String> importNames = _getImportNames(detailAST);
		Map<String, String> variablesMap = _getVariablesMap(javaDocBuilder);

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			_checkMethodCall(
				methodCallAST, importNames, variablesMap,
				javaClass.getPackageName());
		}
	}

	private JavaDocBuilder _addExtendedClassSource(
		JavaDocBuilder javaDocBuilder, JavaClass javaClass, String fileName) {

		Type superClassType = javaClass.getSuperClass();

		String fullyQualifiedName = superClassType.getFullyQualifiedName();

		if (!fullyQualifiedName.startsWith("com.liferay")) {
			return javaDocBuilder;
		}

		int pos = fileName.lastIndexOf("/com/liferay/");

		String extendedClassFileName =
			fileName.substring(0, pos + 1) +
				StringUtil.replace(fullyQualifiedName, '.', '/') + ".java";

		try {
			javaDocBuilder.addSource(new File(extendedClassFileName));
		}
		catch (IOException ioe) {
		}

		return javaDocBuilder;
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
				log(lineNo, MSG_ILLEGAL_PERSISTENCE_CALL, importName);
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

			if (methodName.startsWith("create")) {
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
			log(lineNo, MSG_ILLEGAL_PERSISTENCE_CALL, fullyQualifiedTypeName);
		}
	}

	private List<String> _getImportNames(DetailAST detailAST) {
		List<String> importASTList = new ArrayList<>();

		DetailAST sibling = detailAST.getPreviousSibling();

		while (true) {
			if (sibling.getType() == TokenTypes.IMPORT) {
				FullIdent importIdent = FullIdent.createFullIdentBelow(sibling);

				importASTList.add(importIdent.getText());
			}
			else {
				break;
			}

			sibling = sibling.getPreviousSibling();
		}

		return importASTList;
	}

	private JavaClass _getJavaClass(
		JavaDocBuilder javaDocBuilder, String fileName) {

		int pos = fileName.lastIndexOf("/");

		String className = fileName.substring(pos + 1, fileName.length() - 5);

		for (JavaClass javaClass : javaDocBuilder.getClasses()) {
			if (className.equals(javaClass.getName())) {
				return javaClass;
			}
		}

		return null;
	}

	private Map<String, String> _getVariablesMap(
		JavaDocBuilder javaDocBuilder) {

		Map<String, String> variablesMap = new HashMap<>();

		for (JavaClass javaClass : javaDocBuilder.getClasses()) {
			for (JavaField javaField : javaClass.getFields()) {
				String fieldName = javaField.getName();

				Type fieldType = javaField.getType();

				String fullyQualifiedTypeName =
					fieldType.getFullyQualifiedName();

				variablesMap.put(fieldName, fullyQualifiedTypeName);
			}
		}

		return variablesMap;
	}

}