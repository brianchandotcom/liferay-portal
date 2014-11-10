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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSourceProcessor extends BaseSourceProcessor {

	public static String stripJavaImports(
			String content, String packageDir, String className)
		throws IOException {

		Matcher matcher = _importsPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String imports = matcher.group();

		Set<String> classes = ClassUtil.getClasses(
			new UnsyncStringReader(content), className);

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.contains("import ")) {
				continue;
			}

			int importX = line.indexOf(" ");
			int importY = line.lastIndexOf(".");

			String importPackage = line.substring(importX + 1, importY);

			if (importPackage.equals(packageDir) ||
				importPackage.equals("java.lang")) {

				continue;
			}

			String importClass = line.substring(importY + 1, line.length() - 1);

			if (importClass.equals("*") || classes.contains(importClass)) {
				sb.append(line);
				sb.append("\n");
			}
		}

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		imports = importsFormatter.format(sb.toString());

		content =
			content.substring(0, matcher.start()) + imports +
				content.substring(matcher.end());

		// Ensure a blank line exists between the package and the first import

		content = content.replaceFirst(
			"(?m)^[ \t]*(package .*;)\\s*^[ \t]*import", "$1\n\nimport");

		// Ensure a blank line exists between the last import (or package if
		// there are no imports) and the class comment

		content = content.replaceFirst(
			"(?m)^[ \t]*((?:package|import) .*;)\\s*^[ \t]*/\\*\\*",
			"$1\n\n/**");

		return content;
	}

	protected static int getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	protected static String sortAnnotations(String content, String indent)
		throws IOException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		String annotation = StringPool.BLANK;
		String previousAnnotation = StringPool.BLANK;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.equals(indent + StringPool.CLOSE_CURLY_BRACE)) {
				return content;
			}

			if (StringUtil.count(line, StringPool.TAB) == indent.length()) {
				if (Validator.isNotNull(previousAnnotation) &&
					(previousAnnotation.compareTo(annotation) > 0)) {

					content = StringUtil.replaceFirst(
						content, previousAnnotation, annotation);
					content = StringUtil.replaceLast(
						content, annotation, previousAnnotation);

					return sortAnnotations(content, indent);
				}

				if (line.startsWith(indent + StringPool.AT)) {
					if (Validator.isNotNull(annotation)) {
						previousAnnotation = annotation;
					}

					annotation = line + "\n";
				}
				else {
					annotation = StringPool.BLANK;
					previousAnnotation = StringPool.BLANK;
				}
			}
			else {
				if (Validator.isNull(annotation)) {
					return content;
				}

				annotation += line + "\n";
			}
		}

		return content;
	}

	protected String applyDiamondOperator(String content) {
		Matcher matcher = _diamondOperatorPattern.matcher(content);

		while (matcher.find()) {
			String parameterType = matcher.group(5);

			if (parameterType.contains("Object")) {
				String constructorParameter = matcher.group(6);

				if (Validator.isNotNull(constructorParameter)) {
					continue;
				}
			}

			String match = matcher.group();

			String replacement = StringUtil.replaceFirst(
				match, "<" + parameterType + ">", "<>");

			return StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	protected void checkFinderCacheInterfaceMethod(
		String fileName, String content) {

		if (!fileName.endsWith("FinderImpl.java") ||
			!content.contains("public static final FinderPath")) {

			return;
		}

		Matcher matcher = _fetchByPrimaryKeysMethodPattern.matcher(content);

		if (!matcher.find()) {
			processErrorMessage(
				fileName,
				"LPS-49552: Missing override of BasePersistenceImpl." +
					"fetchByPrimaryKeys(Set<Serializable>): " + fileName);
		}
	}

	protected String checkIfClause(
			String ifClause, String fileName, int lineCount)
		throws IOException {

		String ifClauseSingleLine = StringUtil.replace(
			ifClause,
			new String[] {
				StringPool.TAB + StringPool.SPACE, StringPool.TAB,
				StringPool.OPEN_PARENTHESIS + StringPool.NEW_LINE,
				StringPool.NEW_LINE
			},
			new String[] {
				StringPool.TAB, StringPool.BLANK, StringPool.OPEN_PARENTHESIS,
				StringPool.SPACE
			});

		checkIfClauseParentheses(ifClauseSingleLine, fileName, lineCount);

		return checkIfClauseTabsAndSpaces(ifClause);
	}

	protected String checkIfClauseTabsAndSpaces(String ifClause)
		throws IOException {

		if (ifClause.contains("!(") ||
			ifClause.contains(StringPool.TAB + "//")) {

			return ifClause;
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(ifClause));

		String line = null;

		String previousLine = null;
		int previousLineLeadingWhiteSpace = 0;

		int lastCriteriumLineLeadingWhiteSpace = 0;

		int closeParenthesesCount = 0;
		int openParenthesesCount = 0;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			String originalLine = line;

			line = StringUtil.replace(
				line, StringPool.TAB, StringPool.FOUR_SPACES);

			int leadingWhiteSpace =
				line.length() - StringUtil.trimLeading(line).length();

			if (Validator.isNull(previousLine)) {
				lastCriteriumLineLeadingWhiteSpace = line.indexOf(
					StringPool.OPEN_PARENTHESIS);
			}
			else if (previousLine.endsWith("|") || previousLine.endsWith("&") ||
					 previousLine.endsWith("^")) {

				int expectedLeadingWhiteSpace =
					lastCriteriumLineLeadingWhiteSpace +
						openParenthesesCount - closeParenthesesCount;

				if (leadingWhiteSpace != expectedLeadingWhiteSpace) {
					return fixIfClause(
						ifClause, originalLine,
						leadingWhiteSpace - expectedLeadingWhiteSpace);
				}

				lastCriteriumLineLeadingWhiteSpace = leadingWhiteSpace;

				closeParenthesesCount = 0;
				openParenthesesCount = 0;
			}
			else {
				int expectedLeadingWhiteSpace = 0;

				if (previousLine.contains(StringPool.TAB + "if (")) {
					expectedLeadingWhiteSpace =
						previousLineLeadingWhiteSpace + 8;
				}
				else if (previousLine.contains(StringPool.TAB + "else if (") ||
						 previousLine.contains(StringPool.TAB + "while (")) {

					expectedLeadingWhiteSpace =
						previousLineLeadingWhiteSpace + 12;
				}

				if ((expectedLeadingWhiteSpace != 0) &&
					(leadingWhiteSpace != expectedLeadingWhiteSpace)) {

					return fixIfClause(
						ifClause, originalLine,
						leadingWhiteSpace - expectedLeadingWhiteSpace);
				}
			}

			if (line.endsWith(") {")) {
				return ifClause;
			}

			line = stripQuotes(line, CharPool.QUOTE);
			line = stripQuotes(line, CharPool.APOSTROPHE);

			closeParenthesesCount += StringUtil.count(
				line, StringPool.CLOSE_PARENTHESIS);
			openParenthesesCount += StringUtil.count(
				line, StringPool.OPEN_PARENTHESIS);

			previousLine = originalLine;
			previousLineLeadingWhiteSpace = leadingWhiteSpace;
		}

		return ifClause;
	}

	protected void checkLogLevel(
		String content, String fileName, String logLevel) {

		if (fileName.contains("Log")) {
			return;
		}

		Pattern pattern = Pattern.compile("\n(\t+)_log." + logLevel + "\\(");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int pos = matcher.start();

			while (true) {
				pos = content.lastIndexOf(
					StringPool.NEW_LINE + StringPool.TAB, pos - 1);

				char c = content.charAt(pos + 2);

				if (c != CharPool.TAB) {
					break;
				}
			}

			String codeBlock = content.substring(pos, matcher.start());
			String s =
				"_log.is" + StringUtil.upperCaseFirstLetter(logLevel) +
					"Enabled()";

			if (!codeBlock.contains(s)) {
				int lineCount = StringUtil.count(
					content.substring(0, matcher.start(1)),
					StringPool.NEW_LINE);

				lineCount += 1;

				processErrorMessage(
					fileName, "Use " + s + ": " + fileName + " " + lineCount);
			}
		}

		return;
	}

	protected void checkRegexPattern(
		String regexPattern, String fileName, int lineCount) {

		int i = regexPattern.indexOf("Pattern.compile(");

		if (i == -1) {
			return;
		}

		regexPattern = regexPattern.substring(i + 16);

		regexPattern = stripQuotes(regexPattern, CharPool.QUOTE);

		i = regexPattern.indexOf(StringPool.COMMA);

		if (i != -1) {
			regexPattern = regexPattern.substring(0, i);
		}
		else {
			regexPattern = StringUtil.replaceLast(
				regexPattern, ");", StringPool.BLANK);
		}

		regexPattern = StringUtil.replace(
			regexPattern, StringPool.PLUS, StringPool.BLANK);

		if (Validator.isNull(regexPattern)) {
			processErrorMessage(
				fileName,
				"create pattern as global var: " + fileName + " " + lineCount);
		}
	}

	protected void checkSystemEventAnnotations(String content, String fileName)
		throws Exception {

		if (!portalSource || !fileName.endsWith("PortletDataHandler.java")) {
			return;
		}

		int pos = content.indexOf("setDeletionSystemEventStagedModelTypes");

		if (pos == -1) {
			return;
		}

		String deletionSystemEventStagedModelTypes = content.substring(
			pos, content.indexOf(");", pos));

		Matcher matcher = _stagedModelTypesPattern.matcher(
			deletionSystemEventStagedModelTypes);

		while (matcher.find()) {
			String stagedModelTypeClassName = matcher.group(1);

			pos = stagedModelTypeClassName.indexOf(".class");

			if (pos == -1) {
				pos = stagedModelTypeClassName.indexOf("Constants");
			}

			if (pos == -1) {
				return;
			}

			String className = stagedModelTypeClassName.substring(0, pos);

			Pattern packageNamePattern = Pattern.compile(
				"import (com\\.liferay\\.[a-zA-Z\\.]*)\\.model\\." +
					className + ";");

			Matcher packageNameMatcher = packageNamePattern.matcher(content);

			if (!packageNameMatcher.find()) {
				return;
			}

			StringBundler sb = new StringBundler(6);

			sb.append(BASEDIR);
			sb.append(fileName.substring(0, fileName.indexOf("/src/") + 5));
			sb.append(
				StringUtil.replace(
					packageNameMatcher.group(1), StringPool.PERIOD,
					StringPool.SLASH));
			sb.append("/service/impl/");
			sb.append(className);
			sb.append("LocalServiceImpl.java");

			String localServiceImplFileName = sb.toString();

			String localServiceImplContent = fileUtil.read(
				localServiceImplFileName);

			if (localServiceImplContent == null) {
				System.out.println(
					"Unable to read " + localServiceImplFileName);

				return;
			}

			if (!localServiceImplContent.contains("@SystemEvent")) {
				processErrorMessage(
					fileName,
					"Missing deletion system event: " +
						localServiceImplFileName);
			}
		}
	}

	protected void checkUnprocessedExceptions(
			String content, File file, String packagePath, String fileName)
		throws IOException {

		List<String> importedExceptionClassNames = null;
		JavaDocBuilder javaDocBuilder = null;

		for (int lineCount = 1;;) {
			Matcher catchExceptionMatcher = _catchExceptionPattern.matcher(
				content);

			if (!catchExceptionMatcher.find()) {
				return;
			}

			String beforeCatchCode = content.substring(
				0, catchExceptionMatcher.start());

			lineCount = lineCount + StringUtil.count(beforeCatchCode, "\n") + 1;

			String exceptionClassName = catchExceptionMatcher.group(2);
			String exceptionVariableName = catchExceptionMatcher.group(3);
			String tabs = catchExceptionMatcher.group(1);

			int pos = content.indexOf(
				"\n" + tabs + StringPool.CLOSE_CURLY_BRACE,
				catchExceptionMatcher.end() - 1);

			String insideCatchCode = content.substring(
				catchExceptionMatcher.end(), pos + 1);

			Pattern exceptionVariablePattern = Pattern.compile(
				"\\W" + exceptionVariableName + "\\W");

			Matcher exceptionVariableMatcher = exceptionVariablePattern.matcher(
				insideCatchCode);

			if (exceptionVariableMatcher.find()) {
				content = content.substring(catchExceptionMatcher.start() + 1);

				continue;
			}

			if (javaDocBuilder == null) {
				javaDocBuilder = new JavaDocBuilder();

				javaDocBuilder.addSource(file);
			}

			if (importedExceptionClassNames == null) {
				importedExceptionClassNames = getImportedExceptionClassNames(
					javaDocBuilder);
			}

			String originalExceptionClassName = exceptionClassName;

			if (!exceptionClassName.contains(StringPool.PERIOD)) {
				for (String exceptionClass : importedExceptionClassNames) {
					if (exceptionClass.endsWith(
							StringPool.PERIOD + exceptionClassName)) {

						exceptionClassName = exceptionClass;

						break;
					}
				}
			}

			if (!exceptionClassName.contains(StringPool.PERIOD)) {
				exceptionClassName =
					packagePath + StringPool.PERIOD + exceptionClassName;
			}

			com.thoughtworks.qdox.model.JavaClass exceptionClass =
				javaDocBuilder.getClassByName(exceptionClassName);

			while (true) {
				String packageName = exceptionClass.getPackageName();

				if (!packageName.contains("com.liferay")) {
					break;
				}

				exceptionClassName = exceptionClass.getName();

				if (exceptionClassName.equals("PortalException") ||
					exceptionClassName.equals("SystemException")) {

					processErrorMessage(
						fileName,
						"Unprocessed " + originalExceptionClassName + ": " +
							fileName + " " + lineCount);

					break;
				}

				com.thoughtworks.qdox.model.JavaClass exceptionSuperClass =
					exceptionClass.getSuperJavaClass();

				if (exceptionSuperClass == null) {
					break;
				}

				exceptionClass = exceptionSuperClass;
			}

			content = content.substring(catchExceptionMatcher.start() + 1);
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (isGenerated(content)) {
			return content;
		}

		String className = file.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		className = className.substring(0, pos);

		String packagePath = fileName;

		int packagePathX = packagePath.indexOf("/src/");

		if (packagePathX == -1) {
			packagePathX = packagePath.indexOf("/integration/") + 8;
		}

		int packagePathY = packagePath.lastIndexOf(StringPool.SLASH);

		if ((packagePathX + 5) >= packagePathY) {
			packagePath = StringPool.BLANK;
		}
		else {
			packagePath = packagePath.substring(packagePathX + 5, packagePathY);
		}

		packagePath = StringUtil.replace(
			packagePath, StringPool.SLASH, StringPool.PERIOD);

		if (packagePath.endsWith(".model")) {
			if (content.contains("extends " + className + "Model")) {
				return content;
			}
		}

		String newContent = content;

		if (newContent.contains("$\n */")) {
			processErrorMessage(fileName, "*: " + fileName);

			newContent = StringUtil.replace(newContent, "$\n */", "$\n *\n */");
		}

		newContent = fixCopyright(newContent, absolutePath, fileName);

		if (newContent.contains(className + ".java.html")) {
			processErrorMessage(fileName, "Java2HTML: " + fileName);
		}

		if (newContent.contains(" * @author Raymond Aug") &&
			!newContent.contains(" * @author Raymond Aug\u00e9")) {

			newContent = newContent.replaceFirst(
				"Raymond Aug.++", "Raymond Aug\u00e9");

			processErrorMessage(fileName, "UTF-8: " + fileName);
		}

		newContent = fixDataAccessConnection(className, newContent);
		newContent = fixSessionKey(fileName, newContent, sessionKeyPattern);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"com.liferay.portal.PortalException",
				"com.liferay.portal.SystemException",
				"com.liferay.util.LocalizationUtil"
			},
			new String[] {
				"com.liferay.portal.kernel.exception.PortalException",
				"com.liferay.portal.kernel.exception.SystemException",
				"com.liferay.portal.kernel.util.LocalizationUtil"
			});

		newContent = StringUtil.replace(
			newContent, " final static ", " static final ");

		newContent = fixCompatClassImports(absolutePath, newContent);

		newContent = stripJavaImports(newContent, packagePath, className);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				";\n/**", "\t/*\n\t *", "catch(", "else{", "if(", "for(",
				"while(", "List <", "){\n", "]{\n", ";;\n"
			},
			new String[] {
				";\n\n/**", "\t/**\n\t *", "catch (", "else {", "if (", "for (",
				"while (", "List<", ") {\n", "] {\n", ";\n"
			});

		while (true) {
			Matcher matcher = _incorrectLineBreakPattern1.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start());

				continue;
			}

			matcher = _incorrectLineBreakPattern2.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start());

				continue;
			}

			break;
		}

		newContent = sortAnnotations(newContent, StringPool.BLANK);

		Matcher matcher = _logPattern.matcher(newContent);

		if (matcher.find()) {
			String logClassName = matcher.group(1);

			if (!logClassName.equals(className)) {
				newContent = StringUtil.replaceLast(
					newContent, logClassName + ".class)",
					className + ".class)");
			}
		}

		if (!isExcluded(_staticLogVariableExclusions, absolutePath)) {
			newContent = StringUtil.replace(
				newContent, "private Log _log",
				"private static final Log _log");
		}

		if (newContent.contains("*/\npackage ")) {
			processErrorMessage(fileName, "package: " + fileName);
		}

		if (!newContent.endsWith("\n\n}") && !newContent.endsWith("{\n}")) {
			processErrorMessage(fileName, "}: " + fileName);
		}

		if (portalSource &&
			!_allowUseServiceUtilInServiceImpl &&
			!className.equals("BaseServiceImpl") &&
			className.endsWith("ServiceImpl") &&
			newContent.contains("ServiceUtil.")) {

			processErrorMessage(fileName, "ServiceUtil: " + fileName);
		}

		// LPS-34911

		if (portalSource &&
			!isExcluded(_upgradeServiceUtilExclusions, absolutePath) &&
			fileName.contains("/portal/upgrade/") &&
			!fileName.contains("/test/") &&
			newContent.contains("ServiceUtil.")) {

			processErrorMessage(fileName, "ServiceUtil: " + fileName);
		}

		if (!isRunsOutsidePortal(absolutePath) &&
			!isExcluded(_proxyExclusions, absolutePath) &&
			newContent.contains("import java.lang.reflect.Proxy;")) {

			processErrorMessage(fileName, "Proxy: " + fileName);
		}

		if (newContent.contains("import edu.emory.mathcs.backport.java")) {
			processErrorMessage(
				fileName, "edu.emory.mathcs.backport.java: " + fileName);
		}

		if (newContent.contains("import jodd.util.StringPool")) {
			processErrorMessage(fileName, "jodd.util.StringPool: " + fileName);
		}

		// LPS-45027

		if (newContent.contains(
				"com.liferay.portal.kernel.util.UnmodifiableList")) {

			processErrorMessage(
				fileName,
				"Use java.util.Collections.unmodifiableList instead of " +
					"com.liferay.portal.kernel.util.UnmodifiableList: " +
						fileName);
		}

		// LPS-28266

		for (int pos1 = -1;;) {
			pos1 = newContent.indexOf(StringPool.TAB + "try {", pos1 + 1);

			if (pos1 == -1) {
				break;
			}

			int pos2 = newContent.indexOf(StringPool.TAB + "try {", pos1 + 1);
			int pos3 = newContent.indexOf("\"select count(", pos1);

			if ((pos2 != -1) && (pos3 != -1) && (pos2 < pos3)) {
				continue;
			}

			int pos4 = newContent.indexOf("rs.getLong(1)", pos1);
			int pos5 = newContent.indexOf(StringPool.TAB + "finally {", pos1);

			if ((pos3 == -1) || (pos4 == -1) || (pos5 == -1)) {
				break;
			}

			if ((pos3 < pos4) && (pos4 < pos5)) {
				processErrorMessage(
					fileName, "Use getInt(1) for count: " + fileName);
			}
		}

		// LPS-33070

		matcher = _processCallablePattern.matcher(content);

		if (matcher.find() &&
			!content.contains("private static final long serialVersionUID")) {

			processErrorMessage(
				fileName,
				"Assign ProcessCallable implementation a serialVersionUID: " +
					fileName);
		}

		checkLanguageKeys(fileName, newContent, languageKeyPattern);

		newContent = StringUtil.replace(
			newContent, StringPool.TAB + "for (;;) {",
			StringPool.TAB + "while (true) {");

		// LPS-36174

		if (_checkUnprocessedExceptions && !fileName.contains("/test/")) {
			checkUnprocessedExceptions(newContent, file, packagePath, fileName);
		}

		// LPS-39508

		if (!isExcluded(_secureRandomExclusions, absolutePath) &&
			!isRunsOutsidePortal(absolutePath) &&
			content.contains("java.security.SecureRandom") &&
			!content.contains("javax.crypto.KeyGenerator")) {

			processErrorMessage(
				fileName,
				"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
					"SecureRandom instead of java.security.SecureRandom: " +
						fileName);
		}

		// LPS-41315

		checkLogLevel(newContent, fileName, "debug");
		checkLogLevel(newContent, fileName, "info");
		checkLogLevel(newContent, fileName, "trace");
		checkLogLevel(newContent, fileName, "warn");

		// LPS-46632

		checkSystemEventAnnotations(newContent, fileName);

		// LPS-41205

		if (fileName.contains("/upgrade/") &&
			newContent.contains("LocaleUtil.getDefault()")) {

			processErrorMessage(
				fileName,
				"Use UpgradeProcessUtil.getDefaultLanguageId(companyId) " +
					"instead of LocaleUtil.getDefault(): " + fileName);
		}

		// LPS-46017

		newContent = StringUtil.replace(
			newContent, " static interface ", " interface ");

		// LPS-47055

		newContent = fixSystemExceptions(newContent);

		// LPS-47648

		if (portalSource && fileName.contains("/test/integration/")) {
			newContent = StringUtil.replace(
				newContent, "FinderCacheUtil.clearCache();", StringPool.BLANK);
		}

		// LPS-47682

		newContent = fixIncorrectParameterTypeForLanguageUtil(
			newContent, false, fileName);

		if (portalSource && fileName.contains("/portal-service/") &&
			content.contains("import javax.servlet.jsp.")) {

			processErrorMessage(
				fileName,
				"Never import javax.servlet.jsp.* from portal-service " +
					fileName);
		}

		// LPS-48153

		//newContent = applyDiamondOperator(newContent);

		// LPS-49552

		checkFinderCacheInterfaceMethod(fileName, newContent);

		newContent = fixIncorrectEmptyLineBeforeCloseCurlyBrace(
			newContent, fileName);

		pos = newContent.indexOf("\npublic ");

		if (pos != -1) {
			String javaClassContent = newContent.substring(pos + 1);

			String beforeJavaClass = newContent.substring(0, pos + 1);

			int javaClassLineCount =
				StringUtil.count(beforeJavaClass, "\n") + 1;

			newContent = formatJavaTerms(
				className, packagePath, file, fileName, absolutePath,
				newContent, javaClassContent, javaClassLineCount,
				_checkJavaFieldTypesExclusions,
				_javaTermAccessLevelModifierExclusions, _javaTermSortExclusions,
				_testAnnotationsExclusions);
		}

		newContent = formatJava(fileName, absolutePath, newContent);

		return StringUtil.replace(newContent, "\n\n\n", "\n\n");
	}

	protected String fixDataAccessConnection(String className, String content) {
		int x = content.indexOf("package ");

		int y = content.indexOf(CharPool.SEMICOLON, x);

		if ((x == -1) || (y == -1)) {
			return content;
		}

		String packageName = content.substring(x + 8, y);

		if (!packageName.startsWith("com.liferay.portal.kernel.upgrade") &&
			!packageName.startsWith("com.liferay.portal.kernel.verify") &&
			!packageName.startsWith("com.liferay.portal.upgrade") &&
			!packageName.startsWith("com.liferay.portal.verify")) {

			return content;
		}

		content = StringUtil.replace(
			content, "DataAccess.getConnection",
			"DataAccess.getUpgradeOptimizedConnection");

		return content;
	}

	protected String fixIfClause(String ifClause, String line, int delta) {
		String newLine = line;

		String whiteSpace = StringPool.BLANK;
		int whiteSpaceLength = Math.abs(delta);

		while (whiteSpaceLength > 0) {
			if (whiteSpaceLength >= 4) {
				whiteSpace += StringPool.TAB;

				whiteSpaceLength -= 4;
			}
			else {
				whiteSpace += StringPool.SPACE;

				whiteSpaceLength -= 1;
			}
		}

		if (delta > 0) {
			if (!line.contains(StringPool.TAB + whiteSpace)) {
				newLine = StringUtil.replaceLast(
					newLine, StringPool.TAB, StringPool.FOUR_SPACES);
			}

			newLine = StringUtil.replaceLast(
				newLine, StringPool.TAB + whiteSpace, StringPool.TAB);
		}
		else {
			newLine = StringUtil.replaceLast(
				newLine, StringPool.TAB, StringPool.TAB + whiteSpace);
		}

		newLine = StringUtil.replaceLast(
			newLine, StringPool.FOUR_SPACES, StringPool.TAB);

		return StringUtil.replace(ifClause, line, newLine);
	}

	protected String fixIncorrectEmptyLineBeforeCloseCurlyBrace(
		String content, String fileName) {

		Matcher matcher1 = _incorrectCloseCurlyBracePattern1.matcher(content);

		while (matcher1.find()) {
			String lastLine = StringUtil.trimLeading(matcher1.group(1));

			if (lastLine.startsWith("// ")) {
				continue;
			}

			String tabs = matcher1.group(2);
			int tabCount = tabs.length();

			int pos = matcher1.start();

			while (true) {
				pos = content.lastIndexOf("\n" + tabs, pos - 1);

				if (content.charAt(pos + tabCount + 1) == CharPool.TAB) {
					continue;
				}

				String codeBlock = content.substring(pos + 1, matcher1.end());

				String firstLine = codeBlock.substring(
					0, codeBlock.indexOf("\n"));

				Matcher matcher2 = _incorrectCloseCurlyBracePattern2.matcher(
					firstLine);

				if (matcher2.find()) {
					break;
				}

				return StringUtil.replaceFirst(
					content, "\n\n" + tabs + "}\n", "\n" + tabs + "}\n", pos);
			}
		}

		return content;
	}

	protected String fixSystemExceptions(String content) {
		Matcher matcher = _throwsSystemExceptionPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = matcher.group();
		String replacement = null;

		String afterException = matcher.group(3);
		String beforeException = matcher.group(2);

		if (Validator.isNull(beforeException) &&
			Validator.isNull(afterException)) {

			replacement = matcher.group(4);

			String beforeThrows = matcher.group(1);

			if (Validator.isNotNull(StringUtil.trim(beforeThrows))) {
				replacement = beforeThrows + replacement;
			}
		}
		else if (Validator.isNull(beforeException)) {
			replacement = StringUtil.replaceFirst(
				match, "SystemException, ", StringPool.BLANK);
		}
		else {
			replacement = StringUtil.replaceFirst(
				match, ", SystemException", StringPool.BLANK);
		}

		if (match.equals(replacement)) {
			return content;
		}

		return fixSystemExceptions(
			StringUtil.replaceFirst(content, match, replacement));
	}

	@Override
	protected void format() throws Exception {
		Collection<String> fileNames = null;

		if (portalSource) {
			fileNames = getPortalJavaFiles();

			_checkUnprocessedExceptions = GetterUtil.getBoolean(
				System.getProperty(
					"source.formatter.check.unprocessed.exceptions"));
		}
		else {
			fileNames = getPluginJavaFiles();
		}

		_addMissingDeprecationReleaseVersion = GetterUtil.getBoolean(
			getProperty("add.missing.deprecation.release.version"));
		_allowUseServiceUtilInServiceImpl = GetterUtil.getBoolean(
			getProperty("allow.use.service.util.in.service.impl"));
		_checkJavaFieldTypesExclusions = getPropertyList(
			"check.java.field.types.excludes.files");
		_fitOnSingleLineExclusions = getPropertyList(
			"fit.on.single.line.excludes.files");
		_hibernateSQLQueryExclusions = getPropertyList(
			"hibernate.sql.query.excludes.files");
		_javaTermAccessLevelModifierExclusions = getPropertyList(
			"javaterm.access.level.modifier.excludes.files");
		_javaTermSortExclusions = getPropertyList(
			"javaterm.sort.excludes.files");
		_lineLengthExclusions = getPropertyList("line.length.excludes.files");
		_proxyExclusions = getPropertyList("proxy.excludes.files");
		_secureRandomExclusions = getPropertyList(
			"secure.random.excludes.files");
		_staticLogVariableExclusions = getPropertyList(
			"static.log.excludes.files");
		_testAnnotationsExclusions = getPropertyList(
			"test.annotations.excludes.files");
		_upgradeServiceUtilExclusions = getPropertyList(
			"upgrade.service.util.excludes.files");

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

	protected String formatJava(
			String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineCount = 0;

			String ifClause = StringPool.BLANK;
			String packageName = StringPool.BLANK;
			String regexPattern = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				line = trimLine(line, false);

				if (line.startsWith("package ")) {
					packageName = line.substring(8, line.length() - 1);
				}

				if (line.startsWith("import ")) {
					if (line.endsWith(".*;")) {
						processErrorMessage(
							fileName, "import: " + fileName + " " + lineCount);
					}

					int pos = line.lastIndexOf(StringPool.PERIOD);

					if (pos != -1) {
						String importPackageName = line.substring(7, pos);

						if (importPackageName.equals(packageName)) {
							continue;
						}
					}
				}

				if (line.contains(StringPool.TAB + "for (") &&
					line.contains(":") && !line.contains(" :")) {

					line = StringUtil.replace(line, ":" , " :");
				}

				// LPS-42924

				if (line.contains("PortalUtil.getClassNameId(") &&
					fileName.endsWith("ServiceImpl.java")) {

					processErrorMessage(
						fileName,
						"Use classNameLocalService.getClassNameId: " +
							fileName + " " + lineCount);
				}

				// LPS-42599

				if (!isExcluded(_hibernateSQLQueryExclusions, absolutePath) &&
					line.contains("= session.createSQLQuery(") &&
					content.contains(
						"com.liferay.portal.kernel.dao.orm.Session")) {

					line = StringUtil.replace(
						line, "createSQLQuery", "createSynchronizedSQLQuery");
				}

				line = replacePrimitiveWrapperInstantiation(
					fileName, line, lineCount);

				String trimmedLine = StringUtil.trimLeading(line);

				// LPS-45649

				if (trimmedLine.startsWith("throw new IOException(") &&
					line.contains("e.getMessage()")) {

					line = StringUtil.replace(
						line, ".getMessage()", StringPool.BLANK);
				}

				// LPS-45492

				if (trimmedLine.contains("StopWatch stopWatch = null;")) {
					processErrorMessage(
						fileName,
						"Do not set stopwatch to null: " + fileName + " " +
							lineCount);
				}

				checkStringBundler(trimmedLine, fileName, lineCount);

				checkEmptyCollection(trimmedLine, fileName, lineCount);

				if (trimmedLine.startsWith("* @deprecated") &&
					_addMissingDeprecationReleaseVersion) {

					if (!trimmedLine.startsWith("* @deprecated As of ")) {
						line = StringUtil.replace(
							line, "* @deprecated",
							"* @deprecated As of " + getMainReleaseVersion());
					}
					else {
						String version = trimmedLine.substring(20);

						version = StringUtil.split(
							version, StringPool.SPACE)[0];

						version = StringUtil.replace(
							version, StringPool.COMMA, StringPool.BLANK);

						if (StringUtil.count(version, StringPool.PERIOD) == 1) {
							line = StringUtil.replaceFirst(
								line, version, version + ".0");
						}
					}
				}

				if (trimmedLine.startsWith("* @see ") &&
					(StringUtil.count(trimmedLine, StringPool.AT) > 1)) {

					processErrorMessage(
						fileName,
						"Do not use @see with another annotation: " + fileName +
							" " + lineCount);
				}

				checkInefficientStringMethods(
					line, fileName, absolutePath, lineCount);

				if (trimmedLine.startsWith(StringPool.EQUAL)) {
					processErrorMessage(
						fileName, "line break: " + fileName + " " + lineCount);
				}

				if (line.contains("ActionForm form")) {
					processErrorMessage(
						fileName,
						"Rename form to actionForm: " + fileName + " " +
							lineCount);
				}

				if (line.contains("ActionMapping mapping")) {
					processErrorMessage(
						fileName,
						"Rename mapping to ActionMapping: " + fileName + " " +
							lineCount);
				}

				if (fileName.contains("/upgrade/") &&
					line.contains("rs.getDate(")) {

					processErrorMessage(
						fileName,
						"Use rs.getTimeStamp: " + fileName + " " + lineCount);
				}

				if (!trimmedLine.equals("{") && line.endsWith("{") &&
					!line.endsWith(" {")) {

					line = StringUtil.replaceLast(line, "{", " {");
				}

				line = sortExceptions(line);

				if (trimmedLine.startsWith("if (") ||
					trimmedLine.startsWith("else if (") ||
					trimmedLine.startsWith("while (") ||
					Validator.isNotNull(ifClause)) {

					ifClause = ifClause + line + StringPool.NEW_LINE;

					if (line.endsWith(") {")) {
						String newIfClause = checkIfClause(
							ifClause, fileName, lineCount);

						if (!ifClause.equals(newIfClause) &&
							content.contains(ifClause)) {

							return StringUtil.replace(
								content, ifClause, newIfClause);
						}

						ifClause = StringPool.BLANK;
					}
					else if (line.endsWith(StringPool.SEMICOLON)) {
						ifClause = StringPool.BLANK;
					}
				}

				if (trimmedLine.startsWith("Pattern ") ||
					Validator.isNotNull(regexPattern)) {

					regexPattern = regexPattern + trimmedLine;

					if (trimmedLine.endsWith(");")) {

						// LPS-41084

						checkRegexPattern(regexPattern, fileName, lineCount);

						regexPattern = StringPool.BLANK;
					}
				}

				if (!trimmedLine.contains(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					String strippedQuotesLine = stripQuotes(
						trimmedLine, CharPool.QUOTE);

					for (int x = 0;;) {
						x = strippedQuotesLine.indexOf(StringPool.EQUAL, x + 1);

						if (x == -1) {
							break;
						}

						char c = strippedQuotesLine.charAt(x - 1);

						if (Character.isLetterOrDigit(c)) {
							line = StringUtil.replace(line, c + "=", c + " =");

							break;
						}

						if (x == (strippedQuotesLine.length() - 1)) {
							break;
						}

						c = strippedQuotesLine.charAt(x + 1);

						if (Character.isLetterOrDigit(c)) {
							line = StringUtil.replace(line, "=" + c, "= " + c);

							break;
						}
					}

					while (trimmedLine.contains(StringPool.TAB)) {
						line = StringUtil.replaceLast(
							line, StringPool.TAB, StringPool.SPACE);

						trimmedLine = StringUtil.replaceLast(
							trimmedLine, StringPool.TAB, StringPool.SPACE);
					}

					if (line.contains(StringPool.TAB + StringPool.SPACE) &&
						!previousLine.endsWith("&&") &&
						!previousLine.endsWith("||") &&
						!previousLine.contains(StringPool.TAB + "((") &&
						!previousLine.contains(
							StringPool.TAB + StringPool.LESS_THAN) &&
						!previousLine.contains(
							StringPool.TAB + StringPool.SPACE) &&
						!previousLine.contains(StringPool.TAB + "for (") &&
						!previousLine.contains(
							StringPool.TAB + "implements ") &&
						!previousLine.contains(StringPool.TAB + "throws ")) {

						line = StringUtil.replace(
							line, StringPool.TAB + StringPool.SPACE,
							StringPool.TAB);
					}

					while (trimmedLine.contains(StringPool.DOUBLE_SPACE) &&
						   !trimmedLine.contains(
							   StringPool.QUOTE + StringPool.DOUBLE_SPACE) &&
						   !fileName.contains("Test")) {

						line = StringUtil.replaceLast(
							line, StringPool.DOUBLE_SPACE, StringPool.SPACE);

						trimmedLine = StringUtil.replaceLast(
							trimmedLine, StringPool.DOUBLE_SPACE,
							StringPool.SPACE);
					}

					if (!line.contains(StringPool.QUOTE)) {
						int pos = line.indexOf(") ");

						if (pos != -1) {
							String linePart = line.substring(pos + 2);

							if (Character.isLetter(linePart.charAt(0)) &&
								!linePart.startsWith("default") &&
								!linePart.startsWith("instanceof") &&
								!linePart.startsWith("throws")) {

								line = StringUtil.replaceLast(
									line, StringPool.SPACE + linePart,
									linePart);
							}
						}

						if ((trimmedLine.startsWith("private ") ||
							 trimmedLine.startsWith("protected ") ||
							 trimmedLine.startsWith("public ")) &&
							!line.contains(StringPool.EQUAL) &&
							line.contains(" (")) {

							line = StringUtil.replace(line, " (", "(");
						}

						if (line.contains(" [")) {
							line = StringUtil.replace(line, " [", "[");
						}

						for (int x = -1;;) {
							int posComma = line.indexOf(
								StringPool.COMMA, x + 1);
							int posSemicolon = line.indexOf(
								StringPool.SEMICOLON, x + 1);

							if ((posComma == -1) && (posSemicolon == -1)) {
								break;
							}

							x = Math.min(posComma, posSemicolon);

							if (x == -1) {
								x = Math.max(posComma, posSemicolon);
							}

							if (line.length() > (x + 1)) {
								char nextChar = line.charAt(x + 1);

								if ((nextChar != CharPool.APOSTROPHE) &&
									(nextChar != CharPool.CLOSE_PARENTHESIS) &&
									(nextChar != CharPool.SPACE) &&
									(nextChar != CharPool.STAR)) {

									line = StringUtil.insert(
										line, StringPool.SPACE, x + 1);
								}
							}

							if (x > 0) {
								char previousChar = line.charAt(x - 1);

								if (previousChar == CharPool.SPACE) {
									line = line.substring(0, x - 1).concat(
										line.substring(x));
								}
							}
						}
					}

					if ((line.contains(" && ") || line.contains(" || ")) &&
						line.endsWith(StringPool.OPEN_PARENTHESIS)) {

						processErrorMessage(
							fileName, "line break: " + fileName + " " +
								lineCount);
					}

					if (trimmedLine.endsWith(StringPool.PLUS) &&
						!trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS)) {

						int closeParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);
						int openParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.OPEN_PARENTHESIS);

						if (openParenthesisCount > closeParenthesisCount) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}

					int x = strippedQuotesLine.indexOf(", ");

					if (x != -1) {
						String linePart = strippedQuotesLine.substring(0, x);

						int closeParenthesisCount = StringUtil.count(
							linePart, StringPool.CLOSE_PARENTHESIS);
						int openParenthesisCount = StringUtil.count(
							linePart, StringPool.OPEN_PARENTHESIS);

						if (closeParenthesisCount > openParenthesisCount) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}
					else if (trimmedLine.endsWith(StringPool.COMMA) &&
							 !trimmedLine.startsWith("for (")) {

						int closeParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);
						int openParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.OPEN_PARENTHESIS);

						if (closeParenthesisCount < openParenthesisCount) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}

					if (line.contains(StringPool.COMMA) &&
						!line.contains(StringPool.CLOSE_PARENTHESIS) &&
						!line.contains(StringPool.GREATER_THAN) &&
						!line.contains(StringPool.QUOTE) &&
						line.endsWith(StringPool.OPEN_PARENTHESIS)) {

						processErrorMessage(
							fileName, "line break: " + fileName + " " +
								lineCount);
					}

					if (line.endsWith(" +") || line.endsWith(" -") ||
						line.endsWith(" *") || line.endsWith(" /")) {

						x = line.indexOf(" = ");

						if (x != -1) {
							int y = line.indexOf(StringPool.QUOTE);

							if ((y == -1) || (x < y)) {
								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}
					}

					if (line.endsWith(" throws") ||
						(previousLine.endsWith(
							StringPool.OPEN_PARENTHESIS) &&
						 line.contains(" throws " ) &&
						 line.endsWith(StringPool.OPEN_CURLY_BRACE))) {

						processErrorMessage(
							fileName, "line break: " + fileName + " " +
								lineCount);
					}

					if (trimmedLine.startsWith(StringPool.PERIOD) ||
						(line.endsWith(StringPool.PERIOD) &&
						 line.contains(StringPool.EQUAL))) {

						processErrorMessage(
							fileName, "line break: " + fileName + " " +
								lineCount);
					}

					if (trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) &&
						line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

						Matcher matcher = _lineBreakPattern.matcher(
							trimmedLine);

						if (!matcher.find()) {
							processErrorMessage(
								fileName, "line break: " + fileName + " " +
									lineCount);
						}
					}
				}

				if (line.contains("    ") && !line.matches("\\s*\\*.*")) {
					if (!fileName.endsWith("StringPool.java")) {
						processErrorMessage(
							fileName, "tab: " + fileName + " " + lineCount);
					}
				}

				if (line.contains("  {") && !line.matches("\\s*\\*.*")) {
					processErrorMessage(
						fileName, "{:" + fileName + " " + lineCount);
				}

				int lineLength = getLineLength(line);

				if (!line.startsWith("import ") &&
					!line.startsWith("package ") &&
					!line.matches("\\s*\\*.*")) {

					if (fileName.endsWith("Table.java") &&
						line.contains("String TABLE_SQL_CREATE = ")) {
					}
					else if (fileName.endsWith("Table.java") &&
							 line.contains("String TABLE_SQL_DROP = ")) {
					}
					else if (fileName.endsWith("Table.java") &&
							 line.contains(" index IX_")) {
					}
					else if (lineLength > _MAX_LINE_LENGTH) {
						if (!isExcluded(
								_lineLengthExclusions, absolutePath,
								lineCount) &&
							!isAnnotationParameter(content, trimmedLine)) {

							String truncateLongLinesContent =
								getTruncateLongLinesContent(
									content, line, trimmedLine, lineCount);

							if (truncateLongLinesContent != null) {
								return truncateLongLinesContent;
							}

							processErrorMessage(
								fileName, "> 80: " + fileName + " " +
									lineCount);
						}
					}
					else {
						int lineLeadingTabCount = getLeadingTabCount(line);
						int previousLineLeadingTabCount = getLeadingTabCount(
							previousLine);

						if (!trimmedLine.startsWith("//")) {
							if (previousLine.endsWith(StringPool.COMMA) &&
								previousLine.contains(
									StringPool.OPEN_PARENTHESIS) &&
								!previousLine.contains("for (") &&
								(lineLeadingTabCount >
									previousLineLeadingTabCount)) {

								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}

							if ((lineLeadingTabCount ==
									previousLineLeadingTabCount) &&
								(previousLine.endsWith(StringPool.EQUAL) ||
								 previousLine.endsWith(
									 StringPool.OPEN_PARENTHESIS))) {

								processErrorMessage(
									fileName, "tab: " + fileName + " " +
										lineCount);
							}

							if (Validator.isNotNull(trimmedLine)) {
								if (((previousLine.endsWith(StringPool.COLON) &&
									  previousLine.contains(
										  StringPool.TAB + "for ")) ||
									 (previousLine.endsWith(
										 StringPool.OPEN_PARENTHESIS) &&
									  previousLine.contains(
										  StringPool.TAB + "if "))) &&
									((previousLineLeadingTabCount + 2) !=
										lineLeadingTabCount)) {

									processErrorMessage(
										fileName,
										"line break: " + fileName + " " +
											lineCount);
								}

								if (previousLine.endsWith(
										StringPool.OPEN_CURLY_BRACE) &&
									!trimmedLine.startsWith(
										StringPool.CLOSE_CURLY_BRACE) &&
									((previousLineLeadingTabCount + 1) !=
										lineLeadingTabCount)) {

									processErrorMessage(
										fileName,
										"tab: " + fileName + " " + lineCount);
								}
							}

							if (previousLine.endsWith(StringPool.PERIOD)) {
								int x = trimmedLine.indexOf(
									StringPool.OPEN_PARENTHESIS);

								if ((x != -1) &&
									((getLineLength(previousLine) + x) <
										_MAX_LINE_LENGTH) &&
									(trimmedLine.endsWith(
										StringPool.OPEN_PARENTHESIS) ||
									 (trimmedLine.charAt(x + 1) !=
										 CharPool.CLOSE_PARENTHESIS))) {

									processErrorMessage(
										fileName,
										"line break: " + fileName + " " +
											lineCount);
								}
							}

							int diff =
								lineLeadingTabCount -
								previousLineLeadingTabCount;

							if (trimmedLine.startsWith("throws ") &&
								((diff == 0) || (diff > 1))) {

								processErrorMessage(
									fileName, "tab: " + fileName + " " +
										lineCount);
							}

							if ((diff == 2) &&
								(previousLineLeadingTabCount > 0) &&
								line.endsWith(StringPool.SEMICOLON) &&
								!previousLine.contains(
									StringPool.TAB + "try (")) {

								line = StringUtil.replaceFirst(
									line, StringPool.TAB, StringPool.BLANK);
							}

							if ((previousLine.contains(" class " ) ||
								 previousLine.contains(" enum ")) &&
								previousLine.endsWith(
									StringPool.OPEN_CURLY_BRACE) &&
								Validator.isNotNull(line) &&
								!trimmedLine.startsWith(
									StringPool.CLOSE_CURLY_BRACE)) {

								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}

						String combinedLinesContent = getCombinedLinesContent(
							content, fileName, absolutePath, line, trimmedLine,
							lineLength, lineCount, previousLine,
							lineLeadingTabCount, previousLineLeadingTabCount);

						if ((combinedLinesContent != null) &&
							!combinedLinesContent.equals(content)) {

							return combinedLinesContent;
						}
					}
				}

				if (lineCount > 1) {
					sb.append(previousLine);

					if (Validator.isNotNull(previousLine) &&
						Validator.isNotNull(trimmedLine) &&
						!previousLine.contains("/*") &&
						!previousLine.endsWith("*/")) {

						String trimmedPreviousLine = StringUtil.trimLeading(
							previousLine);

						if ((trimmedPreviousLine.startsWith("// ") &&
							 !trimmedLine.startsWith("// ")) ||
							(!trimmedPreviousLine.startsWith("// ") &&
							 trimmedLine.startsWith("// "))) {

							sb.append("\n");
						}
						else if (!trimmedPreviousLine.endsWith(
									StringPool.OPEN_CURLY_BRACE) &&
								 !trimmedPreviousLine.endsWith(
									StringPool.COLON) &&
								 (trimmedLine.startsWith("for (") ||
								  trimmedLine.startsWith("if ("))) {

							sb.append("\n");
						}
						else if (previousLine.endsWith(
									StringPool.TAB +
										StringPool.CLOSE_CURLY_BRACE) &&
								 !trimmedLine.startsWith(
									 StringPool.CLOSE_CURLY_BRACE) &&
								 !trimmedLine.startsWith(
									 StringPool.CLOSE_PARENTHESIS) &&
								 !trimmedLine.startsWith(
									 StringPool.DOUBLE_SLASH) &&
								 !trimmedLine.equals("*/") &&
								 !trimmedLine.startsWith("catch ") &&
								 !trimmedLine.startsWith("else ") &&
								 !trimmedLine.startsWith("finally ") &&
								 !trimmedLine.startsWith("while ")) {

							sb.append("\n");
						}
					}

					sb.append("\n");
				}

				previousLine = line;
			}

			sb.append(previousLine);
		}

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		return newContent;
	}

	protected String getCombinedLinesContent(
		String content, String fileName, String line, String trimmedLine,
		int lineLength, int lineCount, String previousLine, String linePart,
		int tabDiff, boolean addToPreviousLine, boolean extraSpace,
		boolean removeTabOnNextLine) {

		if (linePart == null) {
			String combinedLine = previousLine;

			if (extraSpace) {
				combinedLine += StringPool.SPACE;
			}

			combinedLine += trimmedLine;

			String nextLine = getNextLine(content, lineCount);

			if (nextLine == null) {
				return null;
			}

			if (removeTabOnNextLine) {
				return StringUtil.replace(
					content,
					"\n" + previousLine + "\n" + line + "\n" + nextLine + "\n",
					"\n" + combinedLine + "\n" + nextLine.substring(1) + "\n");
			}

			if (line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
				(tabDiff != 0) && !previousLine.contains(" class ") &&
				Validator.isNull(nextLine)) {

				return StringUtil.replace(
					content, "\n" + previousLine + "\n" + line + "\n",
					"\n" + combinedLine);
			}

			return StringUtil.replace(
				content, "\n" + previousLine + "\n" + line + "\n",
				"\n" + combinedLine + "\n");
		}

		String firstLine = previousLine;
		String secondLine = line;

		if (addToPreviousLine) {
			if (extraSpace) {
				firstLine += StringPool.SPACE;
			}

			firstLine += linePart;

			secondLine = StringUtil.replaceFirst(
				line, linePart, StringPool.BLANK);
		}
		else {
			if (((linePart.length() + lineLength) <= _MAX_LINE_LENGTH) &&
				(line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
				 line.endsWith(StringPool.SEMICOLON))) {

				firstLine = StringUtil.replaceLast(
					firstLine, StringUtil.trim(linePart), StringPool.BLANK);

				secondLine = StringUtil.replaceLast(
					line, StringPool.TAB, StringPool.TAB + linePart);
			}
			else {
				processErrorMessage(
					fileName, "line break: " + fileName + " " + lineCount);

				return null;
			}
		}

		firstLine = StringUtil.trimTrailing(firstLine);

		return StringUtil.replace(
			content, "\n" + previousLine + "\n" + line + "\n",
			"\n" + firstLine + "\n" + secondLine + "\n");
	}

	protected String getCombinedLinesContent(
		String content, String fileName, String absolutePath, String line,
		String trimmedLine, int lineLength, int lineCount, String previousLine,
		int lineTabCount, int previousLineTabCount) {

		if (Validator.isNull(line) || Validator.isNull(previousLine) ||
			isExcluded(_fitOnSingleLineExclusions, absolutePath, lineCount)) {

			return null;
		}

		String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

		if (line.contains("// ") || line.contains("*/") ||
			line.contains("*/") || previousLine.contains("// ") ||
			previousLine.contains("*/") || previousLine.contains("*/")) {

			return null;
		}

		int tabDiff = lineTabCount - previousLineTabCount;

		if (previousLine.endsWith(" extends")) {
			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, "extends", tabDiff, false, false, false);
		}

		if (previousLine.endsWith(" implements")) {
			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, "implements ", tabDiff, false, false, false);
		}

		if (trimmedLine.startsWith("+ ") || trimmedLine.startsWith("- ") ||
			trimmedLine.startsWith("|| ") || trimmedLine.startsWith("&& ")) {

			int pos = trimmedLine.indexOf(StringPool.SPACE);

			String linePart = trimmedLine.substring(0, pos);

			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, linePart, tabDiff, true, true, false);
		}

		int previousLineLength = getLineLength(previousLine);

		if ((trimmedLine.length() + previousLineLength) < _MAX_LINE_LENGTH) {
			if (trimmedPreviousLine.startsWith("for ") &&
				previousLine.endsWith(StringPool.COLON) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, false);
			}

			if (line.endsWith(StringPool.SEMICOLON) &&
				!previousLine.endsWith(StringPool.COLON) &&
				!previousLine.endsWith(StringPool.OPEN_BRACKET) &&
				!previousLine.endsWith(StringPool.OPEN_CURLY_BRACE) &&
				!previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
				!previousLine.endsWith(StringPool.PERIOD) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, false);
			}

			if ((trimmedPreviousLine.startsWith("if ") ||
				 trimmedPreviousLine.startsWith("else ")) &&
				(previousLine.endsWith("||") || previousLine.endsWith("&&")) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, false);
			}

			if ((trimmedLine.startsWith("extends ") ||
				 trimmedLine.startsWith("implements ") ||
				 trimmedLine.startsWith("throws")) &&
				(line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
				 line.endsWith(StringPool.SEMICOLON)) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, false);
			}

			if (previousLine.endsWith(StringPool.EQUAL) &&
				line.endsWith(StringPool.OPEN_PARENTHESIS)) {

				String nextLine = getNextLine(content, lineCount);

				if (nextLine.endsWith(StringPool.SEMICOLON)) {
					return getCombinedLinesContent(
						content, fileName, line, trimmedLine, lineLength,
						lineCount, previousLine, null, tabDiff, false, true,
						true);
				}
			}
		}

		if (((trimmedLine.length() + previousLineLength) <= _MAX_LINE_LENGTH) &&
			(previousLine.endsWith(StringPool.OPEN_BRACKET) ||
			 previousLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
			 previousLine.endsWith(StringPool.PERIOD)) &&
			line.endsWith(StringPool.SEMICOLON)) {

			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, null, tabDiff, false, false, false);
		}

		if (previousLine.endsWith(StringPool.EQUAL) &&
			line.endsWith(StringPool.SEMICOLON)) {

			String tempLine = trimmedLine;

			for (int pos = 0;;) {
				pos = tempLine.indexOf(StringPool.DASH);

				if (pos == -1) {
					pos = tempLine.indexOf(StringPool.PLUS);
				}

				if (pos == -1) {
					pos = tempLine.indexOf(StringPool.SLASH);
				}

				if (pos == -1) {
					pos = tempLine.indexOf(StringPool.STAR);
				}

				if (pos == -1) {
					pos = tempLine.indexOf("||");
				}

				if (pos == -1) {
					pos = tempLine.indexOf("&&");
				}

				if (pos == -1) {
					break;
				}

				String linePart = tempLine.substring(0, pos);

				int openParenthesisCount = StringUtil.count(
					linePart, StringPool.OPEN_PARENTHESIS);
				int closeParenthesisCount = StringUtil.count(
					linePart, StringPool.CLOSE_PARENTHESIS);

				if (openParenthesisCount == closeParenthesisCount) {
					return null;
				}

				tempLine =
					tempLine.substring(0, pos) + tempLine.substring(pos + 1);
			}

			int x = trimmedLine.indexOf(StringPool.OPEN_PARENTHESIS);

			if (x == 0) {
				x = trimmedLine.indexOf(StringPool.OPEN_PARENTHESIS, 1);
			}

			if (x != -1) {
				int y = trimmedLine.indexOf(StringPool.CLOSE_PARENTHESIS, x);
				int z = trimmedLine.indexOf(StringPool.QUOTE);

				if (((x + 1) != y) && ((z == -1) || (z > x))) {
					char previousChar = trimmedLine.charAt(x - 1);

					if ((previousChar != CharPool.CLOSE_PARENTHESIS) &&
						(previousChar != CharPool.OPEN_PARENTHESIS) &&
						(previousChar != CharPool.SPACE) &&
						(previousLineLength + 1 + x) < _MAX_LINE_LENGTH) {

						String linePart = trimmedLine.substring(0, x + 1);

						if (linePart.startsWith(StringPool.OPEN_PARENTHESIS) &&
							!linePart.contains(
								StringPool.CLOSE_PARENTHESIS)) {

							return null;
						}

						return getCombinedLinesContent(
							content, fileName, line, trimmedLine, lineLength,
							lineCount, previousLine, linePart, tabDiff, true,
							true, false);
					}
				}
			}
		}

		if (previousLine.endsWith(StringPool.COMMA) &&
			(previousLineTabCount == lineTabCount) &&
			!previousLine.contains(StringPool.CLOSE_CURLY_BRACE) &&
			(line.endsWith(") {") ||
			 !line.endsWith(StringPool.OPEN_CURLY_BRACE))) {

			int x = trimmedLine.indexOf(StringPool.COMMA);

			if (x != -1) {
				while ((previousLineLength + 1 + x) < _MAX_LINE_LENGTH) {
					String linePart = trimmedLine.substring(0, x + 1);

					if (isValidJavaParameter(linePart)) {
						if (trimmedLine.equals(linePart)) {
							return getCombinedLinesContent(
								content, fileName, line, trimmedLine,
								lineLength, lineCount, previousLine, null,
								tabDiff, false, true, false);
						}
						else {
							return getCombinedLinesContent(
								content, fileName, line, trimmedLine,
								lineLength, lineCount, previousLine,
								linePart + StringPool.SPACE, tabDiff, true,
								true, false);
						}
					}

					String partAfterComma = trimmedLine.substring(x + 1);

					int pos = partAfterComma.indexOf(StringPool.COMMA);

					if (pos == -1) {
						break;
					}

					x = x + pos + 1;
				}
			}
			else if (!line.endsWith(StringPool.OPEN_PARENTHESIS) &&
					 !line.endsWith(StringPool.PLUS) &&
					 !line.endsWith(StringPool.PERIOD) &&
					 (!trimmedLine.startsWith("new ") ||
					  !line.endsWith(StringPool.OPEN_CURLY_BRACE)) &&
					 ((trimmedLine.length() + previousLineLength) <
						 _MAX_LINE_LENGTH)) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, false);
			}
		}

		if (!previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
			return null;
		}

		if (StringUtil.count(previousLine, StringPool.OPEN_PARENTHESIS) > 1) {
			int pos = trimmedPreviousLine.lastIndexOf(
				StringPool.OPEN_PARENTHESIS, trimmedPreviousLine.length() - 2);

			if ((pos > 0) &&
				Character.isLetterOrDigit(
					trimmedPreviousLine.charAt(pos -1 ))) {

				String filePart = trimmedPreviousLine.substring(pos + 1);

				if (!filePart.contains(StringPool.CLOSE_PARENTHESIS) &&
					!filePart.contains(StringPool.QUOTE)) {

					return getCombinedLinesContent(
						content, fileName, line, trimmedLine, lineLength,
						lineCount, previousLine, filePart, tabDiff, false,
						false, false);
				}
			}
		}

		if ((trimmedLine.length() + previousLineLength) > _MAX_LINE_LENGTH) {
			return null;
		}

		if (line.endsWith(StringPool.COMMA)) {
			String strippedQuotesLine = stripQuotes(
				trimmedLine, CharPool.QUOTE);

			int openParenthesisCount = StringUtil.count(
				strippedQuotesLine, StringPool.OPEN_PARENTHESIS);
			int closeParenthesisCount = StringUtil.count(
				strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);

			if (closeParenthesisCount > openParenthesisCount) {
				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, false, false);
			}
		}

		if (((line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			  !trimmedLine.startsWith("new ")) ||
			 line.endsWith(StringPool.CLOSE_PARENTHESIS)) &&
			(trimmedPreviousLine.startsWith("else ") ||
			 trimmedPreviousLine.startsWith("if ") ||
			 trimmedPreviousLine.startsWith("private ") ||
			 trimmedPreviousLine.startsWith("protected ") ||
			 trimmedPreviousLine.startsWith("public "))) {

			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, null, tabDiff, false, false, false);
		}

		return null;
	}

	protected List<String> getImportedExceptionClassNames(
		JavaDocBuilder javaDocBuilder) {

		List<String> exceptionClassNames = new ArrayList<String>();

		JavaSource javaSource = javaDocBuilder.getSources()[0];

		for (String importClassName : javaSource.getImports()) {
			if (importClassName.endsWith("Exception") &&
				!exceptionClassNames.contains(importClassName)) {

				exceptionClassNames.add(importClassName);
			}
		}

		return exceptionClassNames;
	}

	protected int getLineLength(String line) {
		int lineLength = 0;

		int tabLength = 4;

		for (char c : line.toCharArray()) {
			if (c == CharPool.TAB) {
				for (int i = 0; i < tabLength; i++) {
					lineLength++;
				}

				tabLength = 4;
			}
			else {
				lineLength++;

				tabLength--;

				if (tabLength <= 0) {
					tabLength = 4;
				}
			}
		}

		return lineLength;
	}

	protected String getNextLine(String content, int lineCount) {
		int x = -1;

		for (int i = 0; i < lineCount; i++) {
			x = content.indexOf("\n", x + 1);
		}

		if (x == -1) {
			return null;
		}

		int y = content.indexOf("\n", x + 1);

		if (y == -1) {
			return content.substring(x + 1);
		}

		return content.substring(x + 1, y);
	}

	protected Collection<String> getPluginJavaFiles() {
		Collection<String> fileNames = new TreeSet<String>();

		String[] excludes = new String[] {
			"**\\model\\*Clp.java", "**\\model\\impl\\*BaseImpl.java",
			"**\\model\\impl\\*Model.java", "**\\model\\impl\\*ModelImpl.java",
			"**\\service\\**\\service\\*Service.java",
			"**\\service\\**\\service\\*ServiceClp.java",
			"**\\service\\**\\service\\*ServiceFactory.java",
			"**\\service\\**\\service\\*ServiceUtil.java",
			"**\\service\\**\\service\\*ServiceWrapper.java",
			"**\\service\\**\\service\\ClpSerializer.java",
			"**\\service\\**\\service\\messaging\\*ClpMessageListener.java",
			"**\\service\\**\\service\\persistence\\*Finder.java",
			"**\\service\\**\\service\\persistence\\*Util.java",
			"**\\service\\base\\*ServiceBaseImpl.java",
			"**\\service\\base\\*ServiceClpInvoker.java",
			"**\\service\\http\\*JSONSerializer.java",
			"**\\service\\http\\*ServiceHttp.java",
			"**\\service\\http\\*ServiceJSON.java",
			"**\\service\\http\\*ServiceSoap.java"
		};
		String[] includes = new String[] {"**\\*.java"};

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	protected Collection<String> getPortalJavaFiles() {
		Collection<String> fileNames = new TreeSet<String>();

		String[] excludes = new String[] {
			"**\\*_IW.java", "**\\PropsValues.java", "**\\counter\\service\\**",
			"**\\jsp\\*", "**\\model\\impl\\*BaseImpl.java",
			"**\\model\\impl\\*Model.java", "**\\model\\impl\\*ModelImpl.java",
			"**\\portal\\service\\**", "**\\portal-client\\**",
			"**\\portal-web\\test\\**\\*Test.java",
			"**\\portlet\\**\\service\\**", "**\\test\\*-generated\\**",
			"**\\tools\\sourceformatter\\**"
		};
		String[] includes = new String[] {"**\\*.java"};

		fileNames.addAll(getFileNames(excludes, includes));

		excludes = new String[] {
			"**\\JavaDocFormatter.java", "**\\portal-client\\**",
			"**\\tools\\ext_tmpl\\**", "**\\*_IW.java",
			"**\\test\\**\\*PersistenceTest.java",
			"**\\tools\\sourceformatter\\**"
		};
		includes = new String[] {
			"**\\com\\liferay\\portal\\service\\ServiceContext*.java",
			"**\\model\\BaseModel.java", "**\\model\\impl\\BaseModelImpl.java",
			"**\\portal-test\\**\\portal\\service\\**\\*.java",
			"**\\service\\Base*.java",
			"**\\service\\PersistedModelLocalService*.java",
			"**\\service\\base\\PrincipalBean.java",
			"**\\service\\http\\*HttpTest.java",
			"**\\service\\http\\*SoapTest.java",
			"**\\service\\http\\TunnelUtil.java", "**\\service\\impl\\*.java",
			"**\\service\\jms\\*.java", "**\\service\\permission\\*.java",
			"**\\service\\persistence\\BasePersistence.java",
			"**\\service\\persistence\\BatchSession*.java",
			"**\\service\\persistence\\*FinderImpl.java",
			"**\\service\\persistence\\*Query.java",
			"**\\service\\persistence\\impl\\*.java",
			"**\\portal-impl\\test\\**\\*.java", "**\\util-bridges\\**\\*.java"
		};

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	protected String getTruncateLongLinesContent(
		String content, String line, String trimmedLine, int lineCount) {

		String indent = StringPool.BLANK;

		for (int i = 0; i < getLeadingTabCount(line); i++) {
			indent += StringPool.TAB;
		}

		if (line.endsWith(StringPool.OPEN_PARENTHESIS) ||
			line.endsWith(StringPool.SEMICOLON)) {

			int x = line.indexOf(" = ");

			if (x != -1) {
				String firstLine = line.substring(0, x + 2);

				if (firstLine.contains(StringPool.QUOTE)) {
					return null;
				}

				String secondLine =
					indent + StringPool.TAB + line.substring(x + 3);

				if (line.endsWith(StringPool.SEMICOLON)) {
					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n");
				}
				else if (Validator.isNotNull(getNextLine(content, lineCount))) {
					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n" +
							StringPool.TAB);
				}
			}
		}

		if (line.endsWith(StringPool.CLOSE_PARENTHESIS) ||
			line.endsWith(StringPool.COMMA) ||
			line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			line.endsWith(StringPool.SEMICOLON)) {

			int x = 0;

			while (true) {
				x = line.indexOf(", ", x + 1);

				if (x == -1) {
					break;
				}

				if (isValidJavaParameter(line.substring(0, x))) {
					String firstLine = line.substring(0, x + 1);
					String secondLine = indent + line.substring(x + 2);

					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n");
				}
			}
		}

		if ((line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			 line.endsWith(StringPool.SEMICOLON)) &&
			(trimmedLine.startsWith("private ") ||
			 trimmedLine.startsWith("protected ") ||
			 trimmedLine.startsWith("public "))) {

			String firstLine = null;

			int x = 0;

			while (true) {
				int y = line.indexOf(" extends ", x);

				if (y != -1) {
					firstLine = line.substring(0, y);

					if (StringUtil.count(firstLine, StringPool.GREATER_THAN) !=
							StringUtil.count(firstLine, StringPool.LESS_THAN)) {

						x = y + 1;

						continue;
					}
				}
				else {
					y = line.indexOf(" implements ");

					if (y == -1) {
						y = line.indexOf(" throws ");
					}

					if (y == -1) {
						break;
					}

					firstLine = line.substring(0, y);
				}

				String secondLine =
					indent + StringPool.TAB + line.substring(y + 1);

				return StringUtil.replace(
					content, "\n" + line + "\n",
					"\n" + firstLine + "\n" + secondLine + "\n");
			}
		}

		if ((line.endsWith(StringPool.CLOSE_PARENTHESIS) ||
			 line.endsWith(StringPool.OPEN_CURLY_BRACE)) &&
			(trimmedLine.startsWith("private ") ||
			 trimmedLine.startsWith("protected ") ||
			 trimmedLine.startsWith("public "))) {

			int x = line.indexOf(StringPool.OPEN_PARENTHESIS);

			if ((x != -1) &&
				(line.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS)) {

				String secondLineIndent = indent + StringPool.TAB;

				if (line.endsWith(StringPool.CLOSE_PARENTHESIS)) {
					secondLineIndent += StringPool.TAB;
				}

				String firstLine = line.substring(0, x + 1);
				String secondLine = secondLineIndent + line.substring(x + 1);

				return StringUtil.replace(
					content, "\n" + line + "\n",
					"\n" + firstLine + "\n" + secondLine + "\n");
			}
		}

		if (line.endsWith(StringPool.SEMICOLON)) {
			int x = line.indexOf(StringPool.OPEN_PARENTHESIS);

			if (x != -1) {
				char c = line.charAt(x - 1);

				if ((c != CharPool.SPACE) && (c != CharPool.TAB) &&
					(line.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS)) {

					String firstLine = line.substring(0, x + 1);

					if (firstLine.contains(StringPool.QUOTE)) {
						return null;
					}

					String secondLine =
						indent + StringPool.TAB + line.substring(x + 1);

					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n");
				}
			}
		}

		if (line.contains(StringPool.TAB + "for (") && line.endsWith(" {")) {
			int x = line.indexOf(" : ");

			if (x != -1) {
				String firstLine = line.substring(0, x + 2);
				String secondLine =
					indent + StringPool.TAB + StringPool.TAB +
						line.substring(x + 3);

				return StringUtil.replace(
					content, "\n" + line + "\n",
					"\n" + firstLine + "\n" + secondLine + "\n" + "\n");
			}
		}

		return null;
	}

	protected boolean isAnnotationParameter(String content, String line) {
		if (!line.contains(" = ") && !line.startsWith(StringPool.QUOTE)) {
			return false;
		}

		Matcher matcher = _annotationPattern.matcher(content);

		while (matcher.find()) {
			String annotationParameters = matcher.group(3);

			if (annotationParameters.contains(line)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isGenerated(String content) {
		if (content.contains("* @generated") || content.contains("$ANTLR")) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isValidJavaParameter(String javaParameter) {
		if (javaParameter.contains(" implements ") ||
			javaParameter.contains(" throws ")) {

			return false;
		}

		int quoteCount = StringUtil.count(javaParameter, StringPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return false;
		}

		javaParameter = stripQuotes(javaParameter, CharPool.QUOTE);

		int openParenthesisCount = StringUtil.count(
			javaParameter, StringPool.OPEN_PARENTHESIS);
		int closeParenthesisCount = StringUtil.count(
			javaParameter, StringPool.CLOSE_PARENTHESIS);
		int lessThanCount = StringUtil.count(
			javaParameter, StringPool.LESS_THAN);
		int greaterThanCount = StringUtil.count(
			javaParameter, StringPool.GREATER_THAN);
		int openCurlyBraceCount = StringUtil.count(
			javaParameter, StringPool.OPEN_CURLY_BRACE);
		int closeCurlyBraceCount = StringUtil.count(
			javaParameter, StringPool.CLOSE_CURLY_BRACE);

		if ((openParenthesisCount == closeParenthesisCount) &&
			(lessThanCount == greaterThanCount) &&
			(openCurlyBraceCount == closeCurlyBraceCount)) {

			return true;
		}

		return false;
	}

	protected String sortExceptions(String line) {
		if (!line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			!line.endsWith(StringPool.SEMICOLON)) {

			return line;
		}

		int x = line.indexOf("throws ");

		if (x == -1) {
			return line;
		}

		String previousException = StringPool.BLANK;

		String[] exceptions = StringUtil.split(
			line.substring(x), CharPool.SPACE);

		for (int i = 1; i < exceptions.length; i++) {
			String exception = exceptions[i];

			if (exception.equals(StringPool.OPEN_CURLY_BRACE)) {
				break;
			}

			if (exception.endsWith(StringPool.COMMA) ||
				exception.endsWith(StringPool.SEMICOLON)) {

				exception = exception.substring(0, exception.length() - 1);
			}

			if (Validator.isNotNull(previousException) &&
				(previousException.compareToIgnoreCase(exception) > 0)) {

				line = StringUtil.replace(
					line, previousException + ", " + exception,
					exception + ", " + previousException);

				return sortExceptions(line);
			}

			previousException = exception;
		}

		return line;
	}

	private static final int _MAX_LINE_LENGTH = 80;

	private static Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*;\n+)+", Pattern.MULTILINE);

	private boolean _addMissingDeprecationReleaseVersion;
	private boolean _allowUseServiceUtilInServiceImpl;
	private Pattern _annotationPattern = Pattern.compile(
		"\n(\t*)@(.+)\\(\n([\\s\\S]*?)\n(\t*)\\)");
	private Pattern _catchExceptionPattern = Pattern.compile(
		"\n(\t+)catch \\((.+Exception) (.+)\\) \\{\n");
	private List<String> _checkJavaFieldTypesExclusions;
	private boolean _checkUnprocessedExceptions;
	private Pattern _diamondOperatorPattern = Pattern.compile(
		"(return|=)\n?(\t+| )new ([A-Za-z]+)(Map|Set|List)<(.+)>" +
			"\\(\n*\t*(.*)\\);\n");
	private Pattern _fetchByPrimaryKeysMethodPattern = Pattern.compile(
		"@Override\n\tpublic Map<(.+)> fetchByPrimaryKeys\\(");
	private List<String> _fitOnSingleLineExclusions;
	private List<String> _hibernateSQLQueryExclusions;
	private Pattern _incorrectCloseCurlyBracePattern1 = Pattern.compile(
		"\n(.+)\n\n(\t+)}\n");
	private Pattern _incorrectCloseCurlyBracePattern2 = Pattern.compile(
		"(\t| )@?(class |enum |interface |new )");
	private Pattern _incorrectLineBreakPattern1 = Pattern.compile(
		"\t(catch |else |finally |for |if |try |while ).*\\{\n\n\t+\\w");
	private Pattern _incorrectLineBreakPattern2 = Pattern.compile(
		"\\{\n\n\t*\\}");
	private List<String> _javaTermAccessLevelModifierExclusions;
	private List<String> _javaTermSortExclusions;
	private Pattern _lineBreakPattern = Pattern.compile("\\}(\\)+) \\{");
	private List<String> _lineLengthExclusions;
	private Pattern _logPattern = Pattern.compile(
		"\n\tprivate static final Log _log = LogFactoryUtil.getLog\\(\n*" +
			"\t*(.+)\\.class\\)");
	private Pattern _processCallablePattern = Pattern.compile(
		"implements ProcessCallable\\b");
	private List<String> _proxyExclusions;
	private List<String> _secureRandomExclusions;
	private Pattern _stagedModelTypesPattern = Pattern.compile(
		"StagedModelType\\(([a-zA-Z.]*(class|getClassName[\\(\\)]*))\\)");
	private List<String> _staticLogVariableExclusions;
	private List<String> _testAnnotationsExclusions;
	private Pattern _throwsSystemExceptionPattern = Pattern.compile(
		"(\n\t+.*)throws(.*) SystemException(.*)( \\{|;\n)");
	private List<String> _upgradeServiceUtilExclusions;

}