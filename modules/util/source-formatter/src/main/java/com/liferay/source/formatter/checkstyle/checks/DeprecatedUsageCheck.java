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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
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
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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
		DetailAST parentAST = detailAST.getParent();

		if (parentAST != null) {
			return;
		}

		if (AnnotationUtil.containsAnnotation(detailAST, "Deprecated")) {
			return;
		}

		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		int x = absolutePath.lastIndexOf("/");

		String directoryPath = absolutePath.substring(0, x + 1);

		List<String> importNames = DetailASTUtil.getImportNames(detailAST);
		String packageName = _getPackageName(detailAST);

		_checkDeprecatedConstructorsUsage(
			detailAST, packageName, importNames, directoryPath);
		_checkDeprecatedFieldsUsage(
			detailAST, packageName, importNames, directoryPath);
		_checkDeprecatedTypesUsage(
			detailAST, packageName, importNames, directoryPath);

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String className = nameAST.getText();

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

		List<DetailAST> literalNewASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_NEW);

		for (DetailAST literalNewAST : literalNewASTList) {
			if (_hasDeprecatedParent(literalNewAST)) {
				continue;
			}

			DetailAST lparenAST = literalNewAST.findFirstToken(
				TokenTypes.LPAREN);

			if (lparenAST == null) {
				continue;
			}

			String constructorName = _getConstructorName(literalNewAST);

			DetailAST firstChildAST = literalNewAST.getFirstChild();

			String fullyQualifiedClassName = null;

			if (firstChildAST.getType() == TokenTypes.DOT) {
				FullIdent fullIdent = FullIdent.createFullIdent(firstChildAST);

				fullyQualifiedClassName = fullIdent.getText();
			}
			else {
				fullyQualifiedClassName = _getFullyQualifiedClassName(
					constructorName, packageName, importNames);
			}

			if ((fullyQualifiedClassName == null) ||
				!fullyQualifiedClassName.startsWith("com.liferay.")) {

				continue;
			}

			ClassInfo classInfo = _getClassInfo(
				fullyQualifiedClassName, packageName, directoryPath);

			if (classInfo == null) {
				continue;
			}

			if (classInfo.isDeprecatedClass()) {
				log(
					literalNewAST.getLineNo(), _MSG_DEPRECATED_TYPE_CALL,
					fullyQualifiedClassName);

				continue;
			}

			List<String> parameterTypeNames = _getParameterTypeNames(
				literalNewAST);

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
					literalNewAST.getLineNo(), _MSG_DEPRECATED_CONSTRUCTOR_CALL,
					constructorName);
			}
		}
	}

	private void _checkDeprecatedFieldsUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		List<DetailAST> dotASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.DOT);

		for (DetailAST dotAST : dotASTList) {
			if (_hasDeprecatedParent(dotAST)) {
				continue;
			}

			DetailAST parentAST = dotAST.getParent();

			if ((parentAST.getType() == TokenTypes.DOT) ||
				(parentAST.getType() == TokenTypes.LITERAL_NEW) ||
				(parentAST.getType() == TokenTypes.METHOD_CALL)) {

				continue;
			}

			FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

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
				!fullyQualifiedClassName.startsWith("com.liferay.")) {

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
					dotAST.getLineNo(), _MSG_DEPRECATED_TYPE_CALL,
					fullyQualifiedClassName);
			}
			else {
				log(dotAST.getLineNo(), _MSG_DEPRECATED_FIELD_CALL, fieldName);
			}
		}
	}

	private void _checkDeprecatedMethodsUsage(
		DetailAST detailAST, String className, String packageName,
		List<String> importNames, String directoryPath) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			if (_hasDeprecatedParent(methodCallAST)) {
				continue;
			}

			String fullyQualifiedClassName = _getFullyQualifiedClassName(
				methodCallAST, className, packageName, importNames);

			if ((fullyQualifiedClassName == null) ||
				!fullyQualifiedClassName.startsWith("com.liferay.")) {

				continue;
			}

			ClassInfo classInfo = _getClassInfo(
				fullyQualifiedClassName, packageName, directoryPath);

			if (classInfo == null) {
				continue;
			}

			String methodName = DetailASTUtil.getMethodName(methodCallAST);

			if (classInfo.isDeprecatedClass()) {
				log(
					methodCallAST.getLineNo(), _MSG_DEPRECATED_TYPE_CALL,
					fullyQualifiedClassName);

				continue;
			}

			List<String> parameterTypeNames = _getParameterTypeNames(
				methodCallAST);

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
					methodCallAST.getLineNo(), _MSG_DEPRECATED_METHOD_CALL,
					methodName);
			}
		}
	}

	private void _checkDeprecatedTypesUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		List<DetailAST> detailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.EXTENDS_CLAUSE,
			TokenTypes.IMPLEMENTS_CLAUSE, TokenTypes.TYPE,
			TokenTypes.TYPE_ARGUMENT);

		for (DetailAST curDetailAST : detailASTList) {
			_checkDeprecatedTypeUsage(
				curDetailAST, packageName, importNames, directoryPath);
		}

		detailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_CLASS, TokenTypes.LITERAL_THIS);

		for (DetailAST curDetailAST : detailASTList) {
			DetailAST parentAST = curDetailAST.getParent();

			if (parentAST.getType() != TokenTypes.DOT) {
				continue;
			}

			DetailAST previousSiblingAST = curDetailAST.getPreviousSibling();

			if (previousSiblingAST != null) {
				_checkDeprecatedTypeUsage(
					parentAST, packageName, importNames, directoryPath);
			}
		}
	}

	private void _checkDeprecatedTypeUsage(
		DetailAST detailAST, String packageName, List<String> importNames,
		String directoryPath) {

		if (_hasDeprecatedParent(detailAST)) {
			return;
		}

		DetailAST firstChildAST = detailAST.getFirstChild();

		if (firstChildAST == null) {
			return;
		}

		String className = null;
		String fullyQualifiedClassName = null;

		if (firstChildAST.getType() == TokenTypes.IDENT) {
			className = firstChildAST.getText();

			fullyQualifiedClassName = _getFullyQualifiedClassName(
				className, packageName, importNames);
		}
		else if (firstChildAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(firstChildAST);

			className = fullIdent.getText();

			fullyQualifiedClassName = className;
		}

		if ((fullyQualifiedClassName == null) ||
			!fullyQualifiedClassName.startsWith("com.liferay.")) {

			return;
		}

		ClassInfo classInfo = _getClassInfo(
			fullyQualifiedClassName, packageName, directoryPath);

		if ((classInfo != null) && classInfo.isDeprecatedClass()) {
			log(detailAST.getLineNo(), _MSG_DEPRECATED_TYPE_CALL, className);
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

	private synchronized Map<String, String> _getBundleSymbolicNamesMap()
		throws IOException {

		if (_bundleSymbolicNamesMap != null) {
			return _bundleSymbolicNamesMap;
		}

		_bundleSymbolicNamesMap = new HashMap<>();

		String rootDirName = _getRootDirName();

		if (Validator.isNull(rootDirName)) {
			return _bundleSymbolicNamesMap;
		}

		File modulesDir = new File(rootDirName + "/modules");

		final List<File> files = new ArrayList<>();

		Files.walkFileTree(
			modulesDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					for (PathMatcher pathMatcher : _PATH_MATCHERS) {
						if (pathMatcher.matches(dirPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					if (_PATH_MATCHER.matches(filePath)) {
						files.add(filePath.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

			});

		for (File file : files) {
			String content = FileUtil.read(file);

			String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-SymbolicName");

			if ((bundleSymbolicName != null) &&
				bundleSymbolicName.startsWith("com.liferay")) {

				_bundleSymbolicNamesMap.put(
					bundleSymbolicName,
					SourceUtil.getAbsolutePath(file.getParentFile()));
			}
		}

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

			for (String extendedClassName : javaClass.getExtendedClassNames()) {
				String fullyQualifiedName = null;

				if (extendedClassName.matches("([a-z]\\w*\\.){2,}[A-Z]\\w*")) {
					fullyQualifiedName = extendedClassName;
				}
				else {
					for (String importName : javaClass.getImports()) {
						if (importName.endsWith("." + extendedClassName)) {
							fullyQualifiedName = importName;

							break;
						}
					}
				}

				ClassInfo extendedClassInfo = null;

				if (fullyQualifiedName != null) {
					extendedClassInfo = _getClassInfo(fullyQualifiedName);
				}
				else {
					fullyQualifiedName =
						javaClass.getPackageName() + "." + extendedClassName;

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
		catch (Exception e) {
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
			file = _getFile(fullyQualifiedName);
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

	private String _getConstructorName(DetailAST literalNewAST) {
		DetailAST identAST = literalNewAST.findFirstToken(TokenTypes.IDENT);

		if (identAST != null) {
			return identAST.getText();
		}

		DetailAST dotAST = literalNewAST.findFirstToken(TokenTypes.DOT);

		if (dotAST == null) {
			return null;
		}

		identAST = dotAST.findFirstToken(TokenTypes.IDENT);

		if (identAST != null) {
			return identAST.getText();
		}

		return null;
	}

	private File _getFile(String fullyQualifiedName) {
		if (fullyQualifiedName.contains(".kernel.")) {
			File file = _getFile(
				fullyQualifiedName, "portal-kernel/src/", "portal-test/src/",
				"portal-impl/test/integration/", "portal-impl/test/unit/");

			if (file != null) {
				return file;
			}
		}

		if (fullyQualifiedName.startsWith("com.liferay.portal.") ||
			fullyQualifiedName.startsWith("com.liferay.portlet.")) {

			File file = _getFile(
				fullyQualifiedName, "portal-impl/src/", "portal-test/src/",
				"portal-test-integration/src/", "portal-impl/test/integration/",
				"portal-impl/test/unit/");

			if (file != null) {
				return file;
			}
		}

		if (fullyQualifiedName.contains(".taglib.")) {
			File file = _getFile(fullyQualifiedName, "util-taglib/src/");

			if (file != null) {
				return file;
			}
		}

		try {
			File file = _getModuleFile(
				fullyQualifiedName, _getBundleSymbolicNamesMap());

			if (file != null) {
				return file;
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	private File _getFile(String fullyQualifiedName, String... dirNames) {
		String rootDirName = _getRootDirName();

		if (Validator.isNull(rootDirName)) {
			return null;
		}

		for (String dirName : dirNames) {
			StringBundler sb = new StringBundler(5);

			sb.append(rootDirName);
			sb.append("/");
			sb.append(dirName);
			sb.append(StringUtil.replace(fullyQualifiedName, '.', '/'));
			sb.append(".java");

			File file = new File(sb.toString());

			if (file.exists()) {
				return file;
			}
		}

		return null;
	}

	private String _getFullyQualifiedClassName(
		DetailAST methodCallAST, String className, String packageName,
		List<String> importNames) {

		DetailAST firstChildAST = methodCallAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.IDENT) {
			return packageName + "." + className;
		}

		firstChildAST = firstChildAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(firstChildAST);

			return fullIdent.getText();
		}

		if (firstChildAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		String s = firstChildAST.getText();

		if (s.matches("_?[a-z].*")) {
			s = DetailASTUtil.getVariableTypeName(methodCallAST, s, false);

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

	private File _getModuleFile(
		String fullyQualifiedName, Map<String, String> bundleSymbolicNamesMap) {

		for (Map.Entry<String, String> entry :
				bundleSymbolicNamesMap.entrySet()) {

			String bundleSymbolicName = entry.getKey();

			String modifiedBundleSymbolicName = bundleSymbolicName.replaceAll(
				"\\.(api|impl|service|test)$", StringPool.BLANK);

			if (!fullyQualifiedName.startsWith(modifiedBundleSymbolicName)) {
				continue;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(entry.getValue());
			sb.append("/src/main/java/");
			sb.append(StringUtil.replace(fullyQualifiedName, '.', '/'));
			sb.append(".java");

			File file = new File(sb.toString());

			if (file.exists()) {
				return file;
			}

			sb = new StringBundler(4);

			sb.append(entry.getValue());
			sb.append("/src/testIntegration/java/");
			sb.append(StringUtil.replace(fullyQualifiedName, '.', '/'));
			sb.append(".java");

			file = new File(sb.toString());

			if (file.exists()) {
				return file;
			}
		}

		return null;
	}

	private String _getPackageName(DetailAST detailAST) {
		DetailAST siblingAST = detailAST.getPreviousSibling();

		while (true) {
			if (siblingAST == null) {
				return null;
			}

			if (siblingAST.getType() == TokenTypes.PACKAGE_DEF) {
				DetailAST dotAST = siblingAST.findFirstToken(TokenTypes.DOT);

				FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

				return fullIdent.getText();
			}

			siblingAST = siblingAST.getPreviousSibling();
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

		DetailAST elistAST = detailAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
			elistAST, false, TokenTypes.EXPR);

		for (DetailAST exprAST : exprASTList) {
			DetailAST firstChildAST = exprAST.getFirstChild();

			if (firstChildAST.getType() == TokenTypes.IDENT) {
				String parameterName = firstChildAST.getText();

				String parameterTypeName = DetailASTUtil.getVariableTypeName(
					detailAST, parameterName, false);

				if (Validator.isNotNull(parameterTypeName)) {
					parameterTypeNames.add(parameterTypeName);
				}
				else {
					parameterTypeNames.add(_TYPE_UNKNOWN);
				}
			}
			else if (firstChildAST.getType() == TokenTypes.STRING_LITERAL) {
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

		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		while (true) {
			int x = absolutePath.lastIndexOf("/");

			if (x == -1) {
				_rootDirName = StringPool.BLANK;

				return _rootDirName;
			}

			absolutePath = absolutePath.substring(0, x);

			File file = new File(absolutePath + "/portal-impl");

			if (file.exists()) {
				_rootDirName = absolutePath;

				return _rootDirName;
			}
		}
	}

	private boolean _hasDeprecatedParent(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (((parentAST.getType() == TokenTypes.METHOD_DEF) ||
				 (parentAST.getType() == TokenTypes.VARIABLE_DEF)) &&
				AnnotationUtil.containsAnnotation(parentAST, "Deprecated")) {

				return true;
			}

			parentAST = parentAST.getParent();
		}
	}

	private static final FileSystem _FILE_SYSTEM = FileSystems.getDefault();

	private static final String _MSG_DEPRECATED_CONSTRUCTOR_CALL =
		"constructor.call.deprecated";

	private static final String _MSG_DEPRECATED_FIELD_CALL =
		"field.call.deprecated";

	private static final String _MSG_DEPRECATED_METHOD_CALL =
		"method.call.deprecated";

	private static final String _MSG_DEPRECATED_TYPE_CALL =
		"type.call.deprecated";

	private static final PathMatcher _PATH_MATCHER =
		_FILE_SYSTEM.getPathMatcher("glob:**/bnd.bnd");

	private static final PathMatcher[] _PATH_MATCHERS = {
		_FILE_SYSTEM.getPathMatcher("glob:**/.git/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.gradle/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.idea/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.m2/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/.settings/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/bin/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/build/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/classes/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/sql/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/src/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/test-classes/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/test-coverage/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/test-results/**"),
		_FILE_SYSTEM.getPathMatcher("glob:**/tmp/**")
	};

	private static final String _TYPE_UNKNOWN = "unknown";

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