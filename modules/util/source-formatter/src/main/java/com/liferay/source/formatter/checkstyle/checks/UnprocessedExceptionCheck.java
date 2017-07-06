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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.ThreadSafeSortedClassLibraryBuilder;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.parser.ParseException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class UnprocessedExceptionCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_CATCH, TokenTypes.LITERAL_NEW};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		if (fileName.contains("/test/") ||
			fileName.contains("/testIntegration/")) {

			return;
		}

		if (detailAST.getType() == TokenTypes.LITERAL_CATCH) {
			FileText fileText = fileContents.getText();

			_checkUnprocessedException(
				detailAST, (String)fileText.getFullText());
		}
		else {
			_checkUnprocessedThrownException(detailAST);
		}
	}

	private void _checkUnprocessedException(
		DetailAST detailAST, String content) {

		DetailAST parameterDefAST = detailAST.findFirstToken(
			TokenTypes.PARAMETER_DEF);

		String exceptionVariableName = _getName(parameterDefAST);

		if (_containsVariable(
				detailAST.findFirstToken(TokenTypes.SLIST),
				exceptionVariableName)) {

			return;
		}

		String exceptionClassName = _getExceptionClassName(parameterDefAST);

		if (exceptionClassName == null) {
			return;
		}

		String originalExceptionClassName = exceptionClassName;

		JavaProjectBuilder javaProjectBuilder = _getJavaProjectBuilder(content);

		if (javaProjectBuilder == null) {
			return;
		}

		if (!exceptionClassName.contains(StringPool.PERIOD)) {
			for (String importedExceptionClassName :
					_getImportedExceptionClassNames(javaProjectBuilder)) {

				if (importedExceptionClassName.endsWith(
						StringPool.PERIOD + exceptionClassName)) {

					exceptionClassName = importedExceptionClassName;

					break;
				}
			}
		}

		if (!exceptionClassName.contains(StringPool.PERIOD)) {
			exceptionClassName =
				JavaSourceUtil.getPackagePath(content) + StringPool.PERIOD +
					exceptionClassName;
		}

		JavaClass exceptionClass = javaProjectBuilder.getClassByName(
			exceptionClassName);

		if (exceptionClass == null) {
			return;
		}

		while (true) {
			String packageName = exceptionClass.getPackageName();

			if (!packageName.contains("com.liferay")) {
				break;
			}

			exceptionClassName = exceptionClass.getName();

			if (exceptionClassName.equals("PortalException") ||
				exceptionClassName.equals("SystemException")) {

				log(
					parameterDefAST.getLineNo(), _MSG_UNPROCESSED_EXCEPTION,
					originalExceptionClassName);

				break;
			}

			JavaClass exceptionSuperClass = exceptionClass.getSuperJavaClass();

			if (exceptionSuperClass == null) {
				break;
			}

			exceptionClass = exceptionSuperClass;
		}
	}

	private void _checkUnprocessedThrownException(DetailAST detailAST) {
		String name = _getName(detailAST);

		if ((name == null) || !name.endsWith("Exception")) {
			return;
		}

		DetailAST parentAST = detailAST.getParent();

		if (parentAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST exprAST = parentAST;

		while (true) {
			if (parentAST == null) {
				return;
			}

			if (parentAST.getType() == TokenTypes.LITERAL_CATCH) {
				break;
			}

			parentAST = parentAST.getParent();
		}

		DetailAST parameterDefAST = parentAST.findFirstToken(
			TokenTypes.PARAMETER_DEF);

		String exceptionVariableName = _getName(parameterDefAST);

		if (_containsVariable(
				parentAST.findFirstToken(TokenTypes.SLIST),
				exceptionVariableName)) {

			return;
		}

		parentAST = exprAST.getParent();

		if ((parentAST.getType() == TokenTypes.LITERAL_THROW) ||
			(parentAST.getType() == TokenTypes.SLIST)) {

			log(
				detailAST.getLineNo(), _MSG_UNPROCESSED_EXCEPTION,
				exceptionVariableName);
		}
	}

	private boolean _containsVariable(
		DetailAST detailAST, String variableName) {

		List<DetailAST> nameASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST nameAST : nameASTList) {
			String name = nameAST.getText();

			if (name.equals(variableName)) {
				return true;
			}
		}

		return false;
	}

	private String _getExceptionClassName(DetailAST parameterDefAST) {
		DetailAST typeAST = parameterDefAST.findFirstToken(TokenTypes.TYPE);

		FullIdent typeIdent = FullIdent.createFullIdentBelow(typeAST);

		return typeIdent.getText();
	}

	private Set<String> _getImportedExceptionClassNames(
		JavaProjectBuilder javaProjectBuilder) {

		Set<String> exceptionClassNames = new HashSet<>();

		Collection<JavaSource> sources = javaProjectBuilder.getSources();

		JavaSource javaSource = sources.iterator().next();

		for (String importClassName : javaSource.getImports()) {
			if (importClassName.endsWith("Exception")) {
				exceptionClassNames.add(importClassName);
			}
		}

		return exceptionClassNames;
	}

	private JavaProjectBuilder _getJavaProjectBuilder(String content) {
		JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder(
			new ThreadSafeSortedClassLibraryBuilder());

		try {
			javaProjectBuilder.addSource(new UnsyncStringReader(content));
		}
		catch (ParseException pe) {
			return null;
		}

		return javaProjectBuilder;
	}

	private String _getName(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (nameAST != null) {
			return nameAST.getText();
		}

		DetailAST dotAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotAST != null) {
			nameAST = dotAST.findFirstToken(TokenTypes.IDENT);

			return nameAST.getText();
		}

		return null;
	}

	private static final String _MSG_UNPROCESSED_EXCEPTION =
		"exception.unprocessed";

}