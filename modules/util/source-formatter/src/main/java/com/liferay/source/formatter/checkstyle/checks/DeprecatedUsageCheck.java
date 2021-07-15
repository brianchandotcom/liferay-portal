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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaConstructor;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class DeprecatedUsageCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) ||
			AnnotationUtil.containsAnnotation(detailAST, "Deprecated")) {

			return;
		}

		String absolutePath = getAbsolutePath();

		int x = absolutePath.lastIndexOf("/");

		String directoryPath = absolutePath.substring(0, x + 1);

		List<String> importNames = getImportNames(detailAST);
		String packageName = _getPackageName(detailAST);

		_checkDeprecatedConstructorsUsage(
			detailAST, packageName, importNames, directoryPath);
		_checkDeprecatedFieldsUsage(
			detailAST, packageName, importNames, directoryPath);
		_checkDeprecatedTypesUsage(
			detailAST, packageName, importNames, directoryPath);

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String className = nameDetailAST.getText();

		_checkDeprecatedMethodsUsage(
			detailAST, className, packageName, importNames, directoryPath);
	}

	private ClassInfo _addExtendedClassInfo(
		ClassInfo classInfo, ClassInfo extendedClassInfo,
		boolean forceDeprecated) {

		for (JavaTerm javaConstructor :
				extendedClassInfo.getJavaConstructors(true)) {

			classInfo.addJavaTerm(javaConstructor, true);
		}

		for (JavaTerm javaConstructor :
				extendedClassInfo.getJavaConstructors(false)) {

			if (forceDeprecated) {
				classInfo.addJavaTerm(javaConstructor, true);
			}
			else {
				classInfo.addJavaTerm(javaConstructor, false);
			}
		}

		for (JavaTerm javaMethod : extendedClassInfo.getJavaMethods(true)) {
			classInfo.addJavaTerm(javaMethod, true);
		}

		for (JavaTerm javaMethod : extendedClassInfo.getJavaMethods(false)) {
			if (forceDeprecated) {
				classInfo.addJavaTerm(javaMethod, true);
			}
			else {
				classInfo.addJavaTerm(javaMethod, false);
			}
		}

		for (String fieldName : extendedClassInfo.getFieldNames(true)) {
			classInfo.addFieldName(fieldName, true);
		}

		for (String fieldName : extendedClassInfo.getFieldNames(false)) {
			if (forceDeprecated) {
				classInfo.addFieldName(fieldName, true);
			}
			else {
				classInfo.addFieldName(fieldName, false);
			}
		}

		if (extendedClassInfo.isInheritsThirdParty()) {
			classInfo.setInheritsThirdParty(true);
		}

		return classInfo;
	}

	private void _checkDeprecatedConstructorsUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		List<String> allowedFullyQualifiedClassNames = getAttributeValues(
			_ALLOWED_FULLY_QUALIFIED_CLASS_NAMES_KEY);

		List<DetailAST> literalNewDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_NEW);

		for (DetailAST literalNewDetailAST : literalNewDetailASTList) {
			if (_hasDeprecatedParent(literalNewDetailAST) ||
				_hasSuppressDeprecationWarningsAnnotation(
					literalNewDetailAST)) {

				continue;
			}

			DetailAST lparenDetailAST = literalNewDetailAST.findFirstToken(
				TokenTypes.LPAREN);

			if (lparenDetailAST == null) {
				continue;
			}

			String constructorName = _getConstructorName(literalNewDetailAST);

			DetailAST firstChildDetailAST = literalNewDetailAST.getFirstChild();

			String fullyQualifiedClassName = null;

			if (firstChildDetailAST.getType() == TokenTypes.DOT) {
				FullIdent fullIdent = FullIdent.createFullIdent(
					firstChildDetailAST);

				fullyQualifiedClassName = fullIdent.getText();
			}
			else {
				fullyQualifiedClassName = _getFullyQualifiedClassName(
					constructorName, packageName, importNames);
			}

			if ((fullyQualifiedClassName == null) ||
				!fullyQualifiedClassName.startsWith("com.liferay.") ||
				allowedFullyQualifiedClassNames.contains(
					fullyQualifiedClassName)) {

				continue;
			}

			ClassInfo classInfo = _getClassInfo(
				fullyQualifiedClassName, packageName, directoryPath);

			if (classInfo == null) {
				continue;
			}

			if (classInfo.isDeprecatedClass()) {
				log(
					literalNewDetailAST, _MSG_DEPRECATED_TYPE_CALL,
					fullyQualifiedClassName);

				continue;
			}

			List<String> parameterTypeNames = _getParameterTypeNames(
				literalNewDetailAST);

			if (classInfo.isInheritsThirdParty() &&
				parameterTypeNames.contains(_TYPE_UNKNOWN)) {

				continue;
			}

			if (_containsMatch(
					constructorName, parameterTypeNames,
					classInfo.getJavaConstructors(true)) &&
				!_containsMatch(
					constructorName, parameterTypeNames,
					classInfo.getJavaConstructors(false))) {

				log(
					literalNewDetailAST, _MSG_DEPRECATED_CONSTRUCTOR_CALL,
					constructorName);
			}
		}
	}

	private void _checkDeprecatedFieldsUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		List<String> allowedFullyQualifiedClassNames = getAttributeValues(
			_ALLOWED_FULLY_QUALIFIED_CLASS_NAMES_KEY);

		List<DetailAST> dotDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.DOT);

		for (DetailAST dotDetailAST : dotDetailASTList) {
			if (_hasDeprecatedParent(dotDetailAST) ||
				_hasSuppressDeprecationWarningsAnnotation(dotDetailAST)) {

				continue;
			}

			DetailAST parentDetailAST = dotDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.DOT) ||
				(parentDetailAST.getType() == TokenTypes.LITERAL_NEW) ||
				(parentDetailAST.getType() == TokenTypes.METHOD_CALL)) {

				continue;
			}

			FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

			Matcher matcher = _fieldNamePattern.matcher(fullIdent.getText());

			if (!matcher.find()) {
				continue;
			}

			String fullyQualifiedClassName = null;

			if (matcher.group(2) != null) {
				fullyQualifiedClassName = matcher.group(1);
			}
			else {
				fullyQualifiedClassName = _getFullyQualifiedClassName(
					matcher.group(3), packageName, importNames);
			}

			if ((fullyQualifiedClassName == null) ||
				!fullyQualifiedClassName.startsWith("com.liferay.") ||
				allowedFullyQualifiedClassNames.contains(
					fullyQualifiedClassName)) {

				continue;
			}

			ClassInfo classInfo = _getClassInfo(
				fullyQualifiedClassName, packageName, directoryPath);

			if (classInfo == null) {
				continue;
			}

			List<String> deprecatedFieldNames = classInfo.getFieldNames(true);

			String fieldName = matcher.group(4);

			if (!deprecatedFieldNames.contains(fieldName)) {
				continue;
			}

			if (classInfo.isDeprecatedClass()) {
				log(
					dotDetailAST, _MSG_DEPRECATED_TYPE_CALL,
					fullyQualifiedClassName);
			}
			else {
				log(dotDetailAST, _MSG_DEPRECATED_FIELD_CALL, fieldName);
			}
		}
	}

	private void _checkDeprecatedMethodsUsage(
		DetailAST detailAST, String className, String packageName,
		List<String> importNames, String directoryPath) {

		List<String> allowedFullyQualifiedClassNames = getAttributeValues(
			_ALLOWED_FULLY_QUALIFIED_CLASS_NAMES_KEY);

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			if (_hasDeprecatedParent(methodCallDetailAST) ||
				_hasSuppressDeprecationWarningsAnnotation(
					methodCallDetailAST)) {

				continue;
			}

			String fullyQualifiedClassName = _getFullyQualifiedClassName(
				methodCallDetailAST, className, packageName, importNames);

			if ((fullyQualifiedClassName == null) ||
				!fullyQualifiedClassName.startsWith("com.liferay.") ||
				allowedFullyQualifiedClassNames.contains(
					fullyQualifiedClassName)) {

				continue;
			}

			ClassInfo classInfo = _getClassInfo(
				fullyQualifiedClassName, packageName, directoryPath);

			if (classInfo == null) {
				continue;
			}

			String methodName = getMethodName(methodCallDetailAST);

			if (classInfo.isDeprecatedClass()) {
				log(
					methodCallDetailAST, _MSG_DEPRECATED_TYPE_CALL,
					fullyQualifiedClassName);

				continue;
			}

			List<String> parameterTypeNames = _getParameterTypeNames(
				methodCallDetailAST);

			if (classInfo.isInheritsThirdParty() &&
				parameterTypeNames.contains(_TYPE_UNKNOWN)) {

				continue;
			}

			if (_containsMatch(
					methodName, parameterTypeNames,
					classInfo.getJavaMethods(true)) &&
				!_containsMatch(
					methodName, parameterTypeNames,
					classInfo.getJavaMethods(false))) {

				log(
					methodCallDetailAST, _MSG_DEPRECATED_METHOD_CALL,
					methodName);
			}
		}
	}

	private void _checkDeprecatedTypesUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		List<DetailAST> detailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.EXTENDS_CLAUSE,
			TokenTypes.IMPLEMENTS_CLAUSE, TokenTypes.TYPE,
			TokenTypes.TYPE_ARGUMENT);

		for (DetailAST curDetailAST : detailASTList) {
			_checkDeprecatedTypeUsage(
				curDetailAST, packageName, importNames, directoryPath);
		}

		detailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_CLASS, TokenTypes.LITERAL_THIS);

		for (DetailAST curDetailAST : detailASTList) {
			DetailAST parentDetailAST = curDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				continue;
			}

			DetailAST previousSiblingDetailAST =
				curDetailAST.getPreviousSibling();

			if (previousSiblingDetailAST != null) {
				_checkDeprecatedTypeUsage(
					parentDetailAST, packageName, importNames, directoryPath);
			}
		}
	}

	private void _checkDeprecatedTypeUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		if (_hasDeprecatedParent(detailAST) ||
			_hasSuppressDeprecationWarningsAnnotation(detailAST)) {

			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return;
		}

		String className = null;
		String fullyQualifiedClassName = null;

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			className = firstChildDetailAST.getText();

			fullyQualifiedClassName = _getFullyQualifiedClassName(
				className, packageName, importNames);
		}
		else if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			className = fullIdent.getText();

			fullyQualifiedClassName = className;
		}

		List<String> allowedFullyQualifiedClassNames = getAttributeValues(
			_ALLOWED_FULLY_QUALIFIED_CLASS_NAMES_KEY);

		if ((fullyQualifiedClassName == null) ||
			!fullyQualifiedClassName.startsWith("com.liferay.") ||
			allowedFullyQualifiedClassNames.contains(fullyQualifiedClassName)) {

			return;
		}

		ClassInfo classInfo = _getClassInfo(
			fullyQualifiedClassName, packageName, directoryPath);

		if ((classInfo != null) && classInfo.isDeprecatedClass()) {
			log(detailAST, _MSG_DEPRECATED_TYPE_CALL, className);
		}
	}

	private boolean _containsMatch(
		String name, List<String> parameterTypeNames,
		List<JavaTerm> javaTerms) {

		outerLoop:
		for (JavaTerm javaTerm : javaTerms) {
			if (!name.equals(javaTerm.getName())) {
				continue;
			}

			List<JavaParameter> parameters = _getParameters(javaTerm);

			if (parameters.size() != parameterTypeNames.size()) {
				continue;
			}

			for (int i = 0; i < parameterTypeNames.size(); i++) {
				JavaParameter parameter = parameters.get(i);

				String parameterTypeName1 = parameter.getParameterType();

				int pos = parameterTypeName1.indexOf("<");

				if (pos != -1) {
					parameterTypeName1 = parameterTypeName1.substring(0, pos);
				}

				pos = parameterTypeName1.lastIndexOf(".");

				if (pos != -1) {
					parameterTypeName1 = parameterTypeName1.substring(pos + 1);
				}

				String parameterTypeName2 = parameterTypeNames.get(i);

				if (!parameterTypeName1.equals(parameterTypeName2) &&
					!parameterTypeName2.equals(_TYPE_UNKNOWN)) {

					continue outerLoop;
				}
			}

			return true;
		}

		return false;
	}

	private synchronized Map<String, String> _getBundleSymbolicNamesMap() {
		if (_bundleSymbolicNamesMap != null) {
			return _bundleSymbolicNamesMap;
		}

		_bundleSymbolicNamesMap = BNDSourceUtil.getBundleSymbolicNamesMap(
			_getRootDirName());

		return _bundleSymbolicNamesMap;
	}

	private ClassInfo _getClassInfo(File file) {
		ClassInfo classInfo = new ClassInfo();

		try {
			String content = FileUtil.read(file);

			JavaClass javaClass = JavaClassParser.parseJavaClass(
				SourceUtil.getAbsolutePath(file), content);

			boolean deprecatedClass = javaClass.hasAnnotation("Deprecated");

			classInfo.setDeprecatedClass(deprecatedClass);

			for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
				if (deprecatedClass || javaTerm.hasAnnotation("Deprecated")) {
					classInfo.addJavaTerm(javaTerm, true);
				}
				else {
					classInfo.addJavaTerm(javaTerm, false);
				}
			}

			for (String fullyQualifiedName :
					javaClass.getExtendedClassNames(true)) {

				ClassInfo extendedClassInfo = null;

				if (!fullyQualifiedName.startsWith(
						javaClass.getPackageName())) {

					extendedClassInfo = _getClassInfo(fullyQualifiedName);
				}
				else {
					String absolutePath = SourceUtil.getAbsolutePath(file);

					int x = absolutePath.lastIndexOf("/");

					String directoryPath = absolutePath.substring(0, x + 1);

					extendedClassInfo = _getClassInfo(
						fullyQualifiedName, javaClass.getPackageName(),
						directoryPath);
				}

				classInfo = _addExtendedClassInfo(
					classInfo, extendedClassInfo, deprecatedClass);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return classInfo;
	}

	private ClassInfo _getClassInfo(String fullyQualifiedName) {
		return _getClassInfo(fullyQualifiedName, null, null);
	}

	private ClassInfo _getClassInfo(
		String fullyQualifiedName, String packageName, String directoryPath) {

		ClassInfo classInfo = _classInfoMap.get(fullyQualifiedName);

		if (classInfo != null) {
			return classInfo;
		}

		classInfo = new ClassInfo();

		File file = null;

		if ((packageName != null) &&
			fullyQualifiedName.startsWith(packageName)) {

			String fileName = StringBundler.concat(
				directoryPath,
				StringUtil.replace(
					fullyQualifiedName.substring(packageName.length() + 1),
					CharPool.PERIOD, CharPool.SLASH),
				".java");

			file = new File(fileName);

			if (!file.exists()) {
				file = null;
			}
		}

		if (file == null) {
			file = JavaSourceUtil.getJavaFile(
				fullyQualifiedName, _getRootDirName(),
				_getBundleSymbolicNamesMap());
		}

		if (file != null) {
			classInfo = _getClassInfo(file);
		}
		else {
			classInfo.setInheritsThirdParty(true);
		}

		_classInfoMap.put(fullyQualifiedName, classInfo);

		return classInfo;
	}

	private String _getConstructorName(DetailAST literalNewDetailAST) {
		DetailAST identDetailAST = literalNewDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST != null) {
			return identDetailAST.getText();
		}

		DetailAST dotDetailAST = literalNewDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
			return null;
		}

		identDetailAST = dotDetailAST.findFirstToken(TokenTypes.IDENT);

		if (identDetailAST != null) {
			return identDetailAST.getText();
		}

		return null;
	}

	private String _getFullyQualifiedClassName(
		DetailAST methodCallDetailAST, String className, String packageName,
		List<String> importNames) {

		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			return packageName + "." + className;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			return fullIdent.getText();
		}

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		String s = firstChildDetailAST.getText();

		if (s.matches("_?[a-z].*")) {
			s = getVariableTypeName(methodCallDetailAST, s, false);

			if (Validator.isNull(s)) {
				return null;
			}
		}

		return _getFullyQualifiedClassName(s, packageName, importNames);
	}

	private String _getFullyQualifiedClassName(
		String className, String packageName, List<String> importNames) {

		if (className == null) {
			return null;
		}

		for (String importName : importNames) {
			if (importName.endsWith("." + className)) {
				return importName;
			}
		}

		return packageName + "." + className;
	}

	private String _getPackageName(DetailAST detailAST) {
		DetailAST siblingDetailAST = detailAST.getPreviousSibling();

		while (true) {
			if (siblingDetailAST == null) {
				return null;
			}

			if (siblingDetailAST.getType() == TokenTypes.PACKAGE_DEF) {
				DetailAST dotDetailAST = siblingDetailAST.findFirstToken(
					TokenTypes.DOT);

				FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

				return fullIdent.getText();
			}

			siblingDetailAST = siblingDetailAST.getPreviousSibling();
		}
	}

	private List<JavaParameter> _getParameters(JavaTerm javaTerm) {
		JavaSignature signature = null;

		if (javaTerm instanceof JavaMethod) {
			JavaMethod javaMethod = (JavaMethod)javaTerm;

			signature = javaMethod.getSignature();
		}
		else {
			JavaConstructor javaConstructor = (JavaConstructor)javaTerm;

			signature = javaConstructor.getSignature();
		}

		return signature.getParameters();
	}

	private List<String> _getParameterTypeNames(DetailAST detailAST) {
		List<String> parameterTypeNames = new ArrayList<>();

		DetailAST elistDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		for (DetailAST exprDetailAST : exprDetailASTList) {
			DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				String parameterName = firstChildDetailAST.getText();

				String parameterTypeName = getVariableTypeName(
					detailAST, parameterName, false);

				if (Validator.isNotNull(parameterTypeName)) {
					parameterTypeNames.add(parameterTypeName);
				}
				else {
					parameterTypeNames.add(_TYPE_UNKNOWN);
				}
			}
			else if (firstChildDetailAST.getType() ==
						TokenTypes.STRING_LITERAL) {

				parameterTypeNames.add("String");
			}
			else {
				parameterTypeNames.add(_TYPE_UNKNOWN);
			}
		}

		return parameterTypeNames;
	}

	private synchronized String _getRootDirName() {
		if (_rootDirName != null) {
			return _rootDirName;
		}

		_rootDirName = SourceUtil.getRootDirName(getAbsolutePath());

		return _rootDirName;
	}

	private boolean _hasDeprecatedParent(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (((parentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
				 (parentDetailAST.getType() == TokenTypes.METHOD_DEF) ||
				 (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF)) &&
				AnnotationUtil.containsAnnotation(
					parentDetailAST, "Deprecated")) {

				return true;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private boolean _hasSuppressDeprecationWarningsAnnotation(
		DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (parentDetailAST.findFirstToken(TokenTypes.MODIFIERS) != null) {
				DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
					parentDetailAST, "SuppressWarnings");

				if (annotationDetailAST != null) {
					List<DetailAST> literalStringDetailASTList =
						getAllChildTokens(
							annotationDetailAST, true,
							TokenTypes.STRING_LITERAL);

					for (DetailAST literalStringDetailAST :
							literalStringDetailASTList) {

						String s = literalStringDetailAST.getText();

						if (s.equals("\"deprecation\"")) {
							return true;
						}
					}
				}
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private static final String _ALLOWED_FULLY_QUALIFIED_CLASS_NAMES_KEY =
		"allowedFullyQualifiedClassNames";

	private static final String _MSG_DEPRECATED_CONSTRUCTOR_CALL =
		"constructor.call.deprecated";

	private static final String _MSG_DEPRECATED_FIELD_CALL =
		"field.call.deprecated";

	private static final String _MSG_DEPRECATED_METHOD_CALL =
		"method.call.deprecated";

	private static final String _MSG_DEPRECATED_TYPE_CALL =
		"type.call.deprecated";

	private static final String _TYPE_UNKNOWN = "unknown";

	private static final Log _log = LogFactoryUtil.getLog(
		DeprecatedUsageCheck.class);

	private static final Pattern _fieldNamePattern = Pattern.compile(
		"((.*\\.)?([A-Z]\\w+))\\.(\\w+)");

	private Map<String, String> _bundleSymbolicNamesMap;
	private final Map<String, ClassInfo> _classInfoMap = new HashMap<>();
	private String _rootDirName;

	private class ClassInfo {

		public void addFieldName(String fieldName, boolean deprecated) {
			if (deprecated) {
				_deprecatedFieldNames.add(fieldName);
			}
			else {
				_fieldNames.add(fieldName);
			}
		}

		public void addJavaTerm(JavaTerm javaTerm, boolean deprecated) {
			if (javaTerm instanceof JavaConstructor) {
				if (deprecated) {
					_deprecatedJavaConstructors.add(javaTerm);
				}
				else {
					_javaConstructors.add(javaTerm);
				}
			}
			else if (javaTerm instanceof JavaMethod) {
				if (deprecated) {
					_deprecatedJavaMethods.add(javaTerm);
				}
				else {
					_javaMethods.add(javaTerm);
				}
			}
			else if (javaTerm instanceof JavaVariable) {
				if (deprecated) {
					_deprecatedFieldNames.add(javaTerm.getName());
				}
				else {
					_fieldNames.add(javaTerm.getName());
				}
			}
		}

		public List<String> getFieldNames(boolean deprecated) {
			if (deprecated) {
				return _deprecatedFieldNames;
			}

			return _fieldNames;
		}

		public List<JavaTerm> getJavaConstructors(boolean deprecated) {
			if (deprecated) {
				return _deprecatedJavaConstructors;
			}

			return _javaConstructors;
		}

		public List<JavaTerm> getJavaMethods(boolean deprecated) {
			if (deprecated) {
				return _deprecatedJavaMethods;
			}

			return _javaMethods;
		}

		public boolean isDeprecatedClass() {
			return _deprecatedClass;
		}

		public boolean isInheritsThirdParty() {
			return _inheritsThirdPary;
		}

		public void setDeprecatedClass(boolean deprecatedClass) {
			_deprecatedClass = deprecatedClass;
		}

		public void setInheritsThirdParty(boolean inheritsThirdPary) {
			_inheritsThirdPary = inheritsThirdPary;
		}

		private boolean _deprecatedClass;
		private final List<String> _deprecatedFieldNames = new ArrayList<>();
		private final List<JavaTerm> _deprecatedJavaConstructors =
			new ArrayList<>();
		private final List<JavaTerm> _deprecatedJavaMethods = new ArrayList<>();
		private final List<String> _fieldNames = new ArrayList<>();
		private boolean _inheritsThirdPary;
		private final List<JavaTerm> _javaConstructors = new ArrayList<>();
		private final List<JavaTerm> _javaMethods = new ArrayList<>();

	}

}