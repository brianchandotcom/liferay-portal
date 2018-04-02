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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;

import java.io.File;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class DeprecatedMethodCallsCheck extends BaseCheck {

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

		if (AnnotationUtility.containsAnnotation(detailAST, "Deprecated")) {
			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String className = nameAST.getText();

		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		int x = absolutePath.lastIndexOf("/");

		String directoryPath = absolutePath.substring(0, x + 1);

		List<String> importNames = _getImportNames(detailAST);
		String packageName = _getPackageName(detailAST);

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		outerLoop:
		for (DetailAST methodCallAST : methodCallASTList) {
			if (_hasDeprecatedParent(methodCallAST)) {
				continue;
			}

			String methodName = DetailASTUtil.getMethodName(methodCallAST);

			Tuple javaMethodsTuple = _getJavaMethodsTuple(
				methodCallAST, className, packageName, importNames,
				directoryPath);

			if (javaMethodsTuple == null) {
				continue;
			}

			boolean inheritsThirdParty = (boolean)javaMethodsTuple.getObject(1);

			List<String> parameterTypeNames = _getParameterTypeNames(
				methodCallAST);

			if (inheritsThirdParty &&
				parameterTypeNames.contains(_TYPE_UNKNOWN)) {

				continue;
			}

			boolean deprecated = false;

			List<JavaMethod> javaMethods =
				(List<JavaMethod>)javaMethodsTuple.getObject(0);

			innerLoop:
			for (JavaMethod javaMethod : javaMethods) {
				List<JavaParameter> parameters = _getParameters(javaMethod);

				if (!methodName.equals(javaMethod.getName()) ||
					(parameterTypeNames.size() != parameters.size())) {

					continue;
				}

				if (!javaMethod.hasAnnotation("Deprecated")) {
					continue outerLoop;
				}

				for (int i = 0; i < parameterTypeNames.size(); i++) {
					JavaParameter parameter = parameters.get(i);

					String parameterTypeName1 = parameter.getParameterType();

					int pos = parameterTypeName1.indexOf("<");

					if (pos != -1) {
						parameterTypeName1 = parameterTypeName1.substring(
							0, pos);
					}

					String parameterTypeName2 = parameterTypeNames.get(i);

					if (!parameterTypeName1.equals(parameterTypeName2) &&
						!parameterTypeName2.equals(_TYPE_UNKNOWN)) {

						continue innerLoop;
					}
				}

				deprecated = true;
			}

			if (deprecated) {
				log(
					methodCallAST.getLineNo(), _MSG_DEPRECATED_METHOD_CALL,
					methodName);
			}
		}
	}

	private synchronized Map<String, String> _getBundleSymbolicNamesMap()
		throws Exception {

		if (_bundleSymbolicNamesMap != null) {
			return _bundleSymbolicNamesMap;
		}

		File modulesDir = new File(_getRootDirName() + "/modules");

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

		_bundleSymbolicNamesMap = new HashMap<>();

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
		for (String dirName : dirNames) {
			StringBundler sb = new StringBundler(5);

			sb.append(_getRootDirName());
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

	private Tuple _getJavaMethodsTuple(
		DetailAST methodCallAST, String className, String packageName,
		List<String> importNames, String directoryPath) {

		DetailAST firstChildAST = methodCallAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.IDENT) {
			return _getJavaMethodsTuple(
				packageName + "." + className, directoryPath);
		}

		firstChildAST = firstChildAST.getFirstChild();

		if (firstChildAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		String s = firstChildAST.getText();

		if (s.matches("_?[a-z].*")) {
			s = DetailASTUtil.getVariableTypeName(methodCallAST, s);

			if (Validator.isNull(s)) {
				return null;
			}
		}

		for (String importName : importNames) {
			if (importName.endsWith("." + s)) {
				return _getJavaMethodsTuple(importName, null);
			}
		}

		return _getJavaMethodsTuple(packageName + "." + s, directoryPath);
	}

	private Tuple _getJavaMethodsTuple(File file) {
		boolean inheritsThirdParty = false;
		List<JavaMethod> javaMethods = new ArrayList<>();

		try {
			String content = FileUtil.read(file);

			JavaClass javaClass = JavaClassParser.parseJavaClass(
				SourceUtil.getAbsolutePath(file), content);

			for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
				if (javaTerm.isJavaMethod()) {
					JavaMethod javaMethod = (JavaMethod)javaTerm;

					javaMethods.add(javaMethod);
				}
			}

			List<String> inheritedClassNames =
				javaClass.getExtendedClassNames();

			inheritedClassNames.addAll(javaClass.getImplementedClassNames());

			for (String inheritedClassName : inheritedClassNames) {
				String fullyQualifiedName = null;

				if (inheritedClassName.matches("([a-z]\\w*\\.){2,}[A-Z]\\w*")) {
					fullyQualifiedName = inheritedClassName;
				}
				else {
					for (String importName : javaClass.getImports()) {
						if (importName.endsWith("." + inheritedClassName)) {
							fullyQualifiedName = importName;

							break;
						}
					}
				}

				Tuple inheritedJavaMethodsTuple = null;

				if (fullyQualifiedName != null) {
					inheritedJavaMethodsTuple = _getJavaMethodsTuple(
						fullyQualifiedName, null);
				}
				else {
					fullyQualifiedName =
						javaClass.getPackageName() + "." + inheritedClassName;

					String absolutePath = SourceUtil.getAbsolutePath(file);

					int x = absolutePath.lastIndexOf("/");

					String directoryPath = absolutePath.substring(0, x + 1);

					inheritedJavaMethodsTuple = _getJavaMethodsTuple(
						fullyQualifiedName, directoryPath);
				}

				List<JavaMethod> inheritedJavaMethods =
					(List<JavaMethod>)inheritedJavaMethodsTuple.getObject(0);

				javaMethods.addAll(inheritedJavaMethods);

				if (!inheritsThirdParty) {
					inheritsThirdParty =
						(boolean)inheritedJavaMethodsTuple.getObject(1);
				}
			}
		}
		catch (Exception e) {
			return null;
		}

		return new Tuple(javaMethods, inheritsThirdParty);
	}

	private Tuple _getJavaMethodsTuple(
		String fullyQualifiedName, String directoryPath) {

		Tuple javaMethodsTuple = _javaMethodsTupleMap.get(fullyQualifiedName);

		if (javaMethodsTuple != null) {
			return javaMethodsTuple;
		}

		File file = null;

		if (directoryPath != null) {
			int y = fullyQualifiedName.lastIndexOf(".");

			String fileName =
				directoryPath + fullyQualifiedName.substring(y + 1) + ".java";

			file = new File(fileName);

			if (!file.exists()) {
				return new Tuple(Collections.emptyList(), true);
			}
		}
		else {
			file = _getFile(fullyQualifiedName);
		}

		if (file == null) {
			return new Tuple(Collections.emptyList(), true);
		}

		javaMethodsTuple = _getJavaMethodsTuple(file);

		if (javaMethodsTuple == null) {
			return null;
		}

		_javaMethodsTupleMap.put(fullyQualifiedName, javaMethodsTuple);

		return javaMethodsTuple;
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
		DetailAST sibling = detailAST.getPreviousSibling();

		while (true) {
			if (sibling == null) {
				return null;
			}

			if (sibling.getType() == TokenTypes.PACKAGE_DEF) {
				DetailAST dotAST = sibling.findFirstToken(TokenTypes.DOT);

				FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

				return fullIdent.getText();
			}

			sibling = sibling.getPreviousSibling();
		}
	}

	private List<JavaParameter> _getParameters(JavaMethod method) {
		JavaSignature signature = method.getSignature();

		return signature.getParameters();
	}

	private List<String> _getParameterTypeNames(DetailAST methodCallAST) {
		List<String> parameterTypeNames = new ArrayList<>();

		DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
			elistAST, false, TokenTypes.EXPR);

		for (DetailAST exprAST : exprASTList) {
			DetailAST firstChild = exprAST.getFirstChild();

			if (firstChild.getType() == TokenTypes.IDENT) {
				String parameterName = firstChild.getText();

				String parameterTypeName = DetailASTUtil.getVariableTypeName(
					methodCallAST, parameterName);

				if (Validator.isNotNull(parameterTypeName)) {
					parameterTypeNames.add(parameterTypeName);
				}
				else {
					parameterTypeNames.add(_TYPE_UNKNOWN);
				}
			}
			else if (firstChild.getType() == TokenTypes.STRING_LITERAL) {
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
				return null;
			}

			absolutePath = absolutePath.substring(0, x);

			File file = new File(absolutePath + "/portal-impl");

			if (file.exists()) {
				return absolutePath;
			}
		}
	}

	private boolean _hasDeprecatedParent(DetailAST methodCallAST) {
		DetailAST parentAST = methodCallAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (((parentAST.getType() == TokenTypes.METHOD_DEF) ||
				 (parentAST.getType() == TokenTypes.VARIABLE_DEF)) &&
				AnnotationUtility.containsAnnotation(parentAST, "Deprecated")) {

				return true;
			}

			parentAST = parentAST.getParent();
		}
	}

	private static final FileSystem _FILE_SYSTEM = FileSystems.getDefault();

	private static final String _MSG_DEPRECATED_METHOD_CALL =
		"method.call.deprecated";

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

	private Map<String, String> _bundleSymbolicNamesMap;
	private final Map<String, Tuple> _javaMethodsTupleMap = new HashMap<>();
	private String _rootDirName;

}