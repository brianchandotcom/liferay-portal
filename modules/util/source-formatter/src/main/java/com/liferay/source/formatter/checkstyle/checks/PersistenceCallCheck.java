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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.Type;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		Matcher matcher = _fileNamePattern.matcher(fileName);

		if (!matcher.find()) {
			return;
		}

		FileText fileText = fileContents.getText();

		String content = (String)fileText.getFullText();

		String baseClassName = matcher.replaceAll("$2Base$3");

		if (!content.contains("extends " + baseClassName)) {
			return;
		}

		String baseFileName = matcher.replaceAll("$1base/$2Base$3$4");

		JavaDocBuilder javaDocBuilder = _getJavaDocBuilder(baseFileName);

		if (javaDocBuilder == null) {
			return;
		}

		String moduleServicePackagePath = _getModuleServicePackagePath(
			javaDocBuilder);
		Map<String, String> persistenceFields = _getAvailablePersistenceFields(
			javaDocBuilder);

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			_checkMethodCall(
				methodCallAST, moduleServicePackagePath, persistenceFields);
		}
	}

	private void _checkMethodCall(
		DetailAST methodCallAST, String moduleServicePackagePath,
		Map<String, String> persistenceFields) {

		DetailAST childAST = methodCallAST.getFirstChild();

		if (childAST.getType() != TokenTypes.DOT) {
			return;
		}

		childAST = childAST.getFirstChild();

		if (childAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String fieldName = childAST.getText();

		String fullyQualifiedTypeName = persistenceFields.get(fieldName);

		if (Validator.isNotNull(fullyQualifiedTypeName) &&
			!fullyQualifiedTypeName.startsWith(moduleServicePackagePath)) {

			log(
				methodCallAST.getLineNo(), MSG_ILLEGAL_PERSISTENCE_CALL,
				fullyQualifiedTypeName);
		}
	}

	private Map<String, String> _getAvailablePersistenceFields(
		JavaDocBuilder javaDocBuilder) {

		Map<String, String> persistenceFields = new HashMap<>();

		JavaClass javaClass = javaDocBuilder.getClasses()[0];

		for (JavaField javaField : javaClass.getFields()) {
			String fieldName = javaField.getName();

			if (!fieldName.endsWith("Persistence")) {
				continue;
			}

			Type fieldType = javaField.getType();

			String fullyQualifiedTypeName = fieldType.getFullyQualifiedName();

			persistenceFields.put(fieldName, fullyQualifiedTypeName);
		}

		return persistenceFields;
	}

	private JavaDocBuilder _getJavaDocBuilder(String fileName) {
		JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		try {
			javaDocBuilder.addSource(new File(fileName));
		}
		catch (IOException ioe) {
			return null;
		}

		return javaDocBuilder;
	}

	private String _getModuleServicePackagePath(JavaDocBuilder javaDocBuilder) {
		JavaPackage javaPackage = javaDocBuilder.getPackages()[0];

		String packageName = javaPackage.getName();

		int pos = packageName.indexOf(".service.");

		return packageName.substring(0, pos + 8);
	}

	private final Pattern _fileNamePattern = Pattern.compile(
		"(.*/modules/.*/service/)impl/(.*Service)(Impl)(\\.java)");

}