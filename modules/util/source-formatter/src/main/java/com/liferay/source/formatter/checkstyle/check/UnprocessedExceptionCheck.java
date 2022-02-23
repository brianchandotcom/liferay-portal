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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.util.ThreadSafeSortedClassLibraryBuilder;

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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class UnprocessedExceptionCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_CATCH, TokenTypes.LITERAL_NEW};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		if (detailAST.getType() == TokenTypes.LITERAL_CATCH) {
			FileContents fileContents = getFileContents();

			FileText fileText = fileContents.getText();

			_checkUnprocessedException(
				detailAST, (String)fileText.getFullText());
		}
		else {
			_checkUnprocessedThrownException(detailAST);
		}
	}

	private void _checkJSONException(DetailAST detailAST) {
		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, "_log", _LOG_METHOD_NAMES);

		if (methodCallDetailASTList.isEmpty()) {
			log(detailAST, _MSG_MISSING_DEBUG_LOG_JSON_EXCEPTION);
		}
	}

	private void _checkUnprocessedException(
		DetailAST detailAST, String content) {

		DetailAST parameterDefinitionDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETER_DEF);

		String exceptionVariableName = _getName(parameterDefinitionDetailAST);

		if (_containsVariable(
				detailAST.findFirstToken(TokenTypes.SLIST),
				exceptionVariableName)) {

			return;
		}

		String exceptionClassName = _getExceptionClassName(
			parameterDefinitionDetailAST);

		if (exceptionClassName == null) {
			return;
		}
		else if (exceptionClassName.equals("JSONException")) {
			_checkJSONException(detailAST);

			return;
		}

		JavaProjectBuilder javaProjectBuilder = _getJavaProjectBuilder(content);

		if (javaProjectBuilder == null) {
			return;
		}

		String originalExceptionClassName = exceptionClassName;

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
				JavaSourceUtil.getPackageName(content) + StringPool.PERIOD +
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
					parameterDefinitionDetailAST, _MSG_UNPROCESSED_EXCEPTION,
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

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST exprDetailAST = parentDetailAST;

		while (true) {
			if (parentDetailAST == null) {
				return;
			}

			if (parentDetailAST.getType() == TokenTypes.LITERAL_CATCH) {
				break;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		DetailAST parameterDefinitionDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.PARAMETER_DEF);

		String exceptionClassName = _getExceptionClassName(
			parameterDefinitionDetailAST);

		if (Objects.equals(exceptionClassName, "JSONException")) {
			return;
		}

		String exceptionVariableName = _getName(parameterDefinitionDetailAST);

		if (_containsVariable(
				parentDetailAST.findFirstToken(TokenTypes.SLIST),
				exceptionVariableName)) {

			return;
		}

		parentDetailAST = exprDetailAST.getParent();

		if ((parentDetailAST.getType() == TokenTypes.LITERAL_THROW) ||
			(parentDetailAST.getType() == TokenTypes.SLIST)) {

			log(detailAST, _MSG_UNPROCESSED_EXCEPTION, exceptionVariableName);
		}
	}

	private boolean _containsVariable(
		DetailAST detailAST, String variableName) {

		List<String> names = getNames(detailAST, true);

		return names.contains(variableName);
	}

	private String _getExceptionClassName(
		DetailAST parameterDefinitionDetailAST) {

		DetailAST typeDetailAST = parameterDefinitionDetailAST.findFirstToken(
			TokenTypes.TYPE);

		FullIdent typeIdent = FullIdent.createFullIdentBelow(typeDetailAST);

		return typeIdent.getText();
	}

	private Set<String> _getImportedExceptionClassNames(
		JavaProjectBuilder javaProjectBuilder) {

		Set<String> exceptionClassNames = new HashSet<>();

		Collection<JavaSource> sources = javaProjectBuilder.getSources();

		Iterator<JavaSource> iterator = sources.iterator();

		JavaSource javaSource = iterator.next();

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
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}

			return null;
		}

		return javaProjectBuilder;
	}

	private String _getName(DetailAST detailAST) {
		String name = getName(detailAST);

		if (name != null) {
			return name;
		}

		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST != null) {
			return getName(dotDetailAST);
		}

		return null;
	}

	private static final String[] _LOG_METHOD_NAMES = {
		"debug", "error", "info", "trace", "warn"
	};

	private static final String _MSG_MISSING_DEBUG_LOG_JSON_EXCEPTION =
		"missing.debug.log.json.exception";

	private static final String _MSG_UNPROCESSED_EXCEPTION =
		"exception.unprocessed";

	private static final Log _log = LogFactoryUtil.getLog(
		UnprocessedExceptionCheck.class);

}