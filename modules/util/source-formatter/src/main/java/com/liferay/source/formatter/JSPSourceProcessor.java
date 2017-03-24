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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.checks.CopyrightCheck;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.JSPEmptyLinesCheck;
import com.liferay.source.formatter.checks.JSPIfStatementCheck;
import com.liferay.source.formatter.checks.JSPLanguageKeysCheck;
import com.liferay.source.formatter.checks.JSPSessionKeysCheck;
import com.liferay.source.formatter.checks.JSPTagAttributesCheck;
import com.liferay.source.formatter.checks.JSPWhitespaceCheck;
import com.liferay.source.formatter.checks.MethodCallsOrderCheck;
import com.liferay.source.formatter.checks.ResourceBundleCheck;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.ThreadSafeClassLibrary;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.DefaultDocletTagFactory;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JSPSourceProcessor extends BaseSourceProcessor {

	protected void addImportCounts(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		while (matcher.find()) {
			String importName = matcher.group(1);

			int count = 0;

			if (_importCountMap.containsKey(importName)) {
				count = _importCountMap.get(importName);
			}
			else {
				int pos = importName.lastIndexOf(CharPool.PERIOD);

				String importClassName = importName.substring(pos + 1);

				if (_importClassNames.contains(importClassName)) {
					_duplicateImportClassNames.add(importClassName);
				}
				else {
					_importClassNames.add(importClassName);
				}
			}

			_importCountMap.put(importName, count + 1);
		}
	}

	protected List<String> addIncludedAndReferencedFileNames(
		List<String> fileNames, Set<String> checkedFileNames) {

		Set<String> includedAndReferencedFileNames = new HashSet<>();

		for (String fileName : fileNames) {
			if (!checkedFileNames.add(fileName)) {
				continue;
			}

			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			includedAndReferencedFileNames.addAll(
				getJSPIncludeFileNames(fileName, fileNames));
			includedAndReferencedFileNames.addAll(
				getJSPReferenceFileNames(fileName, fileNames));
		}

		if (includedAndReferencedFileNames.isEmpty()) {
			return fileNames;
		}

		for (String fileName : includedAndReferencedFileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.SLASH, CharPool.BACK_SLASH);

			if (!fileNames.contains(fileName)) {
				fileNames.add(fileName);
			}
		}

		return addIncludedAndReferencedFileNames(fileNames, checkedFileNames);
	}

	protected void addJSPUnusedImports(
		String fileName, List<String> importLines, List<String> unneededImports,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames) {

		for (String importLine : importLines) {
			int x = importLine.indexOf(CharPool.QUOTE);
			int y = importLine.indexOf(CharPool.QUOTE, x + 1);

			if ((x == -1) || (y == -1)) {
				continue;
			}

			String className = importLine.substring(x + 1, y);

			className = className.substring(
				className.lastIndexOf(CharPool.PERIOD) + 1);

			String regex = "[^A-Za-z0-9_\"]" + className + "[^A-Za-z0-9_\"]";

			if (hasUnusedJSPTerm(
					fileName, regex, "class", checkedForIncludesFileNames,
					includeFileNames)) {

				unneededImports.add(importLine);
			}
		}
	}

	protected String buildFullPathIncludeFileName(
		String fileName, String includeFileName) {

		String path = fileName;

		while (true) {
			int y = path.lastIndexOf(CharPool.SLASH);

			if (y == -1) {
				return StringPool.BLANK;
			}

			String fullPathIncludeFileName =
				path.substring(0, y) + includeFileName;

			if (_jspContents.containsKey(fullPathIncludeFileName) &&
				!fullPathIncludeFileName.equals(fileName)) {

				return fullPathIncludeFileName;
			}

			path = path.substring(0, y);
		}
	}

	protected void checkDefineObjectsVariables(
		String fileName, String content, String absolutePath) {

		for (String[] defineObject : _LIFERAY_THEME_DEFINE_OBJECTS) {
			checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "liferay-theme");
		}

		for (String[] defineObject : _PORTLET_DEFINE_OBJECTS) {
			checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "portlet");
		}

		if (!portalSource && !subrepository) {
			return;
		}

		try {
			for (String directoryName :
					getPluginsInsideModulesDirectoryNames()) {

				if (absolutePath.contains(directoryName)) {
					return;
				}
			}
		}
		catch (Exception e) {
		}

		for (String[] defineObject : _LIFERAY_FRONTEND_DEFINE_OBJECTS) {
			checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "liferay-frontend");
		}
	}

	protected void checkDefineObjectsVariables(
		String fileName, String content, String objectType, String variableName,
		String value, String tag) {

		int x = -1;

		while (true) {
			x = content.indexOf(
				objectType + " " + variableName + " = " + value + ";", x + 1);

			if (x == -1) {
				return;
			}

			int y = content.lastIndexOf("<%", x);

			if ((y == -1) ||
				(getLevel(content.substring(y, x), "{", "}") > 0)) {

				continue;
			}

			processMessage(
				fileName,
				"Use '" + tag + ":defineObjects' or rename var, see LPS-62493",
				getLineCount(content, x));
		}
	}

	protected void checkSubnames(String fileName, String content) {
		Matcher matcher = _subnamePattern.matcher(content);

		while (matcher.find()) {
			processMessage(
				fileName,
				"'sub' should be followed by a lowercase character for '" +
					matcher.group(1) + "'");
		}
	}

	protected void checkValidatorEquals(String fileName, String content) {
		Matcher matcher = validatorEqualsPattern.matcher(content);

		while (matcher.find()) {
			processMessage(
				fileName,
				"Use Objects.equals(Object, Object) instead of " +
					"Validator.equals(Object, Object), see LPS-65135",
				getLineCount(content, matcher.start()));
		}
	}

	protected String compressImportsOrTaglibs(
		String fileName, String content, String attributePrefix) {

		if (!fileName.endsWith("init.jsp") && !fileName.endsWith("init.jspf")) {
			return content;
		}

		int x = content.indexOf(attributePrefix);

		int y = content.lastIndexOf(attributePrefix);

		y = content.indexOf("%>", y);

		if ((x == -1) || (y == -1) || (x > y)) {
			return content;
		}

		String importsOrTaglibs = content.substring(x, y);

		importsOrTaglibs = StringUtil.replace(
			importsOrTaglibs, new String[] {"%>\r\n<%@ ", "%>\n<%@ "},
			new String[] {"%><%@\r\n", "%><%@\n"});

		return content.substring(0, x) + importsOrTaglibs +
			content.substring(y);
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		Set<String> checkedForIncludesFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		String newContent = formatJSP(
			fileName, absolutePath, content, checkedForIncludesFileNames,
			includeFileNames);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"<br/>", "@page import", "\"%>", ")%>", "function (",
				"javascript: ", "){\n", ";;\n"
			},
			new String[] {
				"<br />", "@ page import", "\" %>", ") %>", "function(",
				"javascript:", ") {\n", ";\n"
			});

		newContent = fixRedirectBackURL(newContent);

		newContent = fixCompatClassImports(absolutePath, newContent);

		newContent = fixIncorrectClosingTag(newContent);

		newContent = fixEmptyJavaSourceTag(newContent);

		newContent = fixUnparameterizedClassType(newContent);

		if (_stripJSPImports && !_jspContents.isEmpty()) {
			try {
				newContent = formatJSPImportsOrTaglibs(
					fileName, newContent, _compressedJSPImportPattern,
					_uncompressedJSPImportPattern, true,
					checkedForIncludesFileNames, includeFileNames);
				newContent = formatJSPImportsOrTaglibs(
					fileName, newContent, _compressedJSPTaglibPattern,
					_uncompressedJSPTaglibPattern, false,
					checkedForIncludesFileNames, includeFileNames);
			}
			catch (RuntimeException re) {
				_stripJSPImports = false;
			}
		}

		if ((portalSource || subrepository) &&
			content.contains("page import=") &&
			!fileName.contains("init.jsp") &&
			!fileName.contains("init-ext.jsp") &&
			!fileName.contains("/taglib/aui/") &&
			!fileName.endsWith("touch.jsp") &&
			(fileName.endsWith(".jspf") || content.contains("include file="))) {

			processMessage(fileName, "Move imports to init.jsp");
		}

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"alert('<%= LanguageUtil.", "alert(\"<%= LanguageUtil.",
				"confirm('<%= LanguageUtil.", "confirm(\"<%= LanguageUtil."
			},
			new String[] {
				"alert('<%= UnicodeLanguageUtil.",
				"alert(\"<%= UnicodeLanguageUtil.",
				"confirm('<%= UnicodeLanguageUtil.",
				"confirm(\"<%= UnicodeLanguageUtil."
			});

		newContent = compressImportsOrTaglibs(
			fileName, newContent, "<%@ page import=");
		newContent = compressImportsOrTaglibs(
			fileName, newContent, "<%@ taglib uri=");

		newContent = removeUnusedTaglibs(
			fileName, newContent, checkedForIncludesFileNames,
			includeFileNames);

		checkSubnames(fileName, newContent);

		newContent = formatStringBundler(fileName, newContent, -1);

		newContent = formatTaglibVariable(fileName, newContent);

		newContent = fixXSSVulnerability(fileName, newContent);

		// LPS-47682

		newContent = fixIncorrectParameterTypeForLanguageUtil(
			newContent, true, fileName);

		// LPS-48156

		newContent = checkPrincipalException(newContent);

		newContent = formatLogFileName(absolutePath, newContent);

		newContent = formatDefineObjects(newContent);

		// LPS-62989

		if ((portalSource || subrepository) && isModulesFile(absolutePath) &&
			newContent.contains("import=\"com.liferay.registry.Registry")) {

			processMessage(
				fileName,
				"Do not use com.liferay.registry.Registry in modules, see " +
					"LPS-62989");
		}

		// LPS-64335

		if ((portalSource || subrepository) && isModulesFile(absolutePath) &&
			newContent.contains("import=\"com.liferay.util.ContentUtil")) {

			processMessage(
				fileName,
				"Do not use com.liferay.util.ContentUtil in modules, see " +
					"LPS-64335");
		}

		// LPS-62786

		checkPropertyUtils(fileName, newContent);

		// LPS-63953

		checkStringUtilReplace(fileName, newContent);

		checkGetterUtilGet(fileName, newContent);

		checkValidatorEquals(fileName, newContent);

		checkDefineObjectsVariables(fileName, newContent, absolutePath);

		Matcher matcher = _javaClassPattern.matcher(newContent);

		if (matcher.find()) {
			String javaClassContent = matcher.group();

			javaClassContent = javaClassContent.substring(1);

			String javaClassName = matcher.group(2);

			int javaClassLineCount = getLineCount(
				newContent, matcher.start() + 1);

			newContent = formatJavaTerms(
				javaClassName, null, file, fileName, absolutePath, newContent,
				javaClassContent, javaClassLineCount, StringPool.BLANK, null,
				null, null);
		}

		JSPSourceTabCalculator jspSourceTabCalculator =
			new JSPSourceTabCalculator();

		newContent = jspSourceTabCalculator.calculateTabs(
			fileName, newContent, (JSPSourceProcessor)this);

		if (!content.equals(newContent)) {
			_jspContents.put(fileName, newContent);
		}

		return newContent;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> fileNames = getFileNames(excludes, getIncludes());

		if (fileNames.isEmpty()) {
			return fileNames;
		}

		if (sourceFormatterArgs.isFormatCurrentBranch() ||
			sourceFormatterArgs.isFormatLatestAuthor() ||
			sourceFormatterArgs.isFormatLocalChanges()) {

			return addIncludedAndReferencedFileNames(
				fileNames, new HashSet<String>());
		}

		return fileNames;
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	protected String fixEmptyJavaSourceTag(String content) {
		Matcher matcher = _emptyJavaSourceTagPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replace(
				content, matcher.group(), StringPool.BLANK);
		}

		return content;
	}

	protected String fixIncorrectClosingTag(String content) {
		Matcher matcher = _incorrectClosingTagPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, " />\n", "\n" + matcher.group(1) + "/>\n",
				matcher.end(1));
		}

		return content;
	}

	protected String fixRedirectBackURL(String content) {
		Matcher matcher = _redirectBackURLPattern.matcher(content);

		String newContent = content;

		while (matcher.find()) {
			newContent = StringUtil.replaceFirst(
				newContent, matcher.group(),
				matcher.group(1) + "\n\n" + matcher.group(2), matcher.start());
		}

		return newContent;
	}

	protected String fixXSSVulnerability(String fileName, String content) {
		Matcher matcher1 = _xssPattern.matcher(content);

		String jspVariable = null;
		int vulnerabilityPos = -1;

		while (matcher1.find()) {
			jspVariable = matcher1.group(1);

			String anchorVulnerability = " href=\"<%= " + jspVariable + " %>";
			String inputVulnerability = " value=\"<%= " + jspVariable + " %>";

			vulnerabilityPos = Math.max(
				getTaglibXSSVulnerabilityPos(content, anchorVulnerability),
				getTaglibXSSVulnerabilityPos(content, inputVulnerability));

			if (vulnerabilityPos != -1) {
				break;
			}

			Pattern pattern = Pattern.compile(
				"('|\\(\"| \"|\\.)<%= " + jspVariable + " %>");

			Matcher matcher2 = pattern.matcher(content);

			if (matcher2.find()) {
				vulnerabilityPos = matcher2.start();

				break;
			}
		}

		if (vulnerabilityPos != -1) {
			return StringUtil.replaceFirst(
				content, "<%= " + jspVariable + " %>",
				"<%= HtmlUtil.escape(" + jspVariable + ") %>",
				vulnerabilityPos);
		}

		return content;
	}

	protected String formatDefineObjects(String content) {
		Matcher matcher = _missingEmptyLineBetweenDefineOjbectsPattern.matcher(
			content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		String previousDefineObjectsTag = null;

		matcher = _defineObjectsPattern.matcher(content);

		while (matcher.find()) {
			String defineObjectsTag = matcher.group(1);

			if (Validator.isNotNull(previousDefineObjectsTag) &&
				(previousDefineObjectsTag.compareTo(defineObjectsTag) > 0)) {

				content = StringUtil.replaceFirst(
					content, previousDefineObjectsTag, defineObjectsTag);
				content = StringUtil.replaceLast(
					content, defineObjectsTag, previousDefineObjectsTag);

				return content;
			}

			previousDefineObjectsTag = defineObjectsTag;
		}

		return content;
	}

	protected String formatJSP(
			String fileName, String absolutePath, String content,
			Set<String> checkedForIncludesFileNames,
			Set<String> includeFileNames)
		throws Exception {

		StringBundler sb = new StringBundler();

		String currentException = null;
		String previousException = null;

		boolean hasUnsortedExceptions = false;

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			String previousLine = StringPool.BLANK;

			boolean javaSource = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (line.contains("<aui:button ") &&
					line.contains("type=\"button\"")) {

					processMessage(
						fileName, "No need to set 'type=button' for aui:button",
						lineCount);
				}

				if (line.contains("debugger.")) {
					processMessage(fileName, "Do not use debugger", lineCount);
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (line.matches(".*\\WgetClass\\(\\)\\..+")) {
					processMessage(
						fileName, "Avoid chaining on 'getClass'", lineCount);
				}

				checkEmptyCollection(trimmedLine, fileName, lineCount);

				line = formatEmptyArray(line);

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
				}

				checkInefficientStringMethods(
					line, fileName, absolutePath, lineCount, javaSource);

				if (javaSource) {
					if ((portalSource || subrepository) &&
						!isExcludedPath(
							_UNUSED_VARIABLES_EXCLUDES, absolutePath,
							lineCount) &&
						!_jspContents.isEmpty() &&
						hasUnusedVariable(
							fileName, trimmedLine, checkedForIncludesFileNames,
							includeFileNames)) {

						continue;
					}
				}

				// LPS-47179

				if (line.contains(".sendRedirect(") &&
					!fileName.endsWith("_jsp.jsp")) {

					processMessage(
						fileName,
						"Do not use sendRedirect in jsp, see LPS-47179",
						lineCount);
				}

				// LPS-55341

				if (javaSource) {
					line = StringUtil.replace(
						line, "LanguageUtil.get(locale,",
						"LanguageUtil.get(request,");
				}
				else {
					Matcher matcher = javaSourceInsideJSPLinePattern.matcher(
						line);

					while (matcher.find()) {
						String match = matcher.group(1);

						String replacement = StringUtil.replace(
							match, "LanguageUtil.get(locale,",
							"LanguageUtil.get(request,");

						line = StringUtil.replace(line, match, replacement);
					}
				}

				if (!fileName.endsWith("test.jsp") &&
					line.contains("System.out.print")) {

					processMessage(
						fileName, "Do not call 'System.out.print'", lineCount);
				}

				if (trimmedLine.matches("^\\} ?(catch|else|finally) .*")) {
					processMessage(
						fileName, "There should be a line break after '}'",
						lineCount);
				}

				if (!hasUnsortedExceptions) {
					int x = line.indexOf("<liferay-ui:error exception=\"<%=");

					if (x != -1) {
						int y = line.indexOf(".class %>", x);

						if (y != -1) {
							currentException = line.substring(x, y);

							if (Validator.isNotNull(previousException) &&
								(previousException.compareToIgnoreCase(
									currentException) > 0)) {

								currentException = line;
								previousException = previousLine;

								hasUnsortedExceptions = true;
							}
						}
					}

					if (!hasUnsortedExceptions) {
						previousException = currentException;
						currentException = null;
					}
				}

				if (!fileName.endsWith("/touch.jsp")) {
					int x = line.indexOf("<%@ include file");

					if (x != -1) {
						x = line.indexOf(CharPool.QUOTE, x);

						int y = line.indexOf(CharPool.QUOTE, x + 1);

						if (y != -1) {
							String includeFileName = line.substring(x + 1, y);

							Matcher matcher = _jspIncludeFilePattern.matcher(
								includeFileName);

							if (!matcher.find()) {
								processMessage(
									fileName,
									"Incorrect include '" + includeFileName +
										"'",
									lineCount);
							}
						}
					}
				}

				line = replacePrimitiveWrapperInstantiation(line);

				if (lineCount > 1) {
					sb.append(previousLine);
					sb.append("\n");
				}

				previousLine = line;
			}

			sb.append(previousLine);
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		if (hasUnsortedExceptions) {
			if ((StringUtil.count(content, currentException) > 1) ||
				(StringUtil.count(content, previousException) > 1)) {

				processMessage(
					fileName, "Unsorted exception '" + currentException + "'");
			}
			else {
				content = StringUtil.replaceFirst(
					content, previousException, currentException);

				content = StringUtil.replaceLast(
					content, currentException, previousException);
			}
		}

		return content;
	}

	protected String formatJSPImportsOrTaglibs(
			String fileName, String content, Pattern compressedPattern,
			Pattern uncompressedPattern, boolean checkUnusedImports,
			Set<String> checkedForIncludesFileNames,
			Set<String> includeFileNames)
		throws IOException {

		if (fileName.endsWith("init-ext.jsp")) {
			return content;
		}

		Matcher matcher = compressedPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String imports = matcher.group();

		String newImports = StringUtil.replace(
			imports, new String[] {"<%@\r\n", "<%@\n", " %><%@ "},
			new String[] {"\r\n<%@ ", "\n<%@ ", " %>\n<%@ "});

		if (checkUnusedImports) {
			List<String> importLines = new ArrayList<>();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(newImports));

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.contains("import=")) {
					importLines.add(line);
				}
			}

			List<String> unneededImports = getJSPDuplicateImports(
				fileName, content, importLines);

			addJSPUnusedImports(
				fileName, importLines, unneededImports,
				checkedForIncludesFileNames, includeFileNames);

			for (String unneededImport : unneededImports) {
				newImports = StringUtil.replace(
					newImports, unneededImport, StringPool.BLANK);
			}
		}

		content = StringUtil.replaceFirst(content, imports, newImports);

		ImportsFormatter importsFormatter = new JSPImportsFormatter();

		return importsFormatter.format(content, uncompressedPattern);
	}

	protected String formatLogFileName(String absolutePath, String content) {
		if (!isModulesFile(absolutePath) &&
			!absolutePath.contains("/portal-web/")) {

			return content;
		}

		Matcher matcher = _logPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String logFileName = StringUtil.replace(
			absolutePath, CharPool.PERIOD, CharPool.UNDERLINE);

		logFileName = StringUtil.replace(
			logFileName, CharPool.SLASH, CharPool.PERIOD);

		logFileName = StringUtil.replace(
			logFileName, CharPool.DASH, CharPool.UNDERLINE);

		int x = logFileName.lastIndexOf(".portal_web.");

		if (x != -1) {
			logFileName = logFileName.substring(x + 1);
		}
		else {
			x = logFileName.lastIndexOf(".docroot.");

			if (x == -1) {
				x = Math.max(
					logFileName.lastIndexOf(
						".src.main.resources.META_INF.resources."),
					logFileName.lastIndexOf(".src.META_INF.resources."));
			}

			if (x == -1) {
				return content;
			}

			x = logFileName.lastIndexOf(StringPool.PERIOD, x - 1);

			logFileName = "com_liferay_" + logFileName.substring(x + 1);

			logFileName = StringUtil.replace(
				logFileName,
				new String[] {
					".docroot.", ".src.main.resources.META_INF.resources.",
					".src.META_INF.resources."
				},
				new String[] {
					StringPool.PERIOD, StringPool.PERIOD, StringPool.PERIOD
				});
		}

		return StringUtil.replace(
			content, matcher.group(),
			"Log _log = LogFactoryUtil.getLog(\"" + logFileName + "\")");
	}

	protected String formatTaglibVariable(String fileName, String content) {
		Matcher matcher = _taglibVariablePattern.matcher(content);

		while (matcher.find()) {
			String taglibValue = matcher.group(3);

			if (taglibValue.contains("\\\"") ||
				(taglibValue.contains(StringPool.APOSTROPHE) &&
				 taglibValue.contains(StringPool.QUOTE))) {

				continue;
			}

			String taglibName = matcher.group(2);
			String nextTag = matcher.group(4);

			if (!nextTag.contains(taglibName)) {
				processMessage(
					fileName,
					"No need to specify taglib variable '" + taglibName + "'",
					getLineCount(content, matcher.start()));

				continue;
			}

			content = StringUtil.replaceFirst(
				content, taglibName, taglibValue, matcher.start(4));

			return content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());
		}

		return content;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	protected List<String> getJSPDuplicateImports(
		String fileName, String content, List<String> importLines) {

		List<String> duplicateImports = new ArrayList<>();

		for (String importLine : importLines) {
			int x = content.indexOf("<%@ include file=");

			if (x == -1) {
				continue;
			}

			int y = content.indexOf("<%@ page import=");

			if (y == -1) {
				continue;
			}

			if ((x < y) && isJSPDuplicateImport(fileName, importLine, false)) {
				duplicateImports.add(importLine);
			}
		}

		return duplicateImports;
	}

	protected Set<String> getJSPIncludeFileNames(
		String fileName, Collection<String> fileNames) {

		Set<String> includeFileNames = new HashSet<>();

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return includeFileNames;
		}

		for (int x = 0;;) {
			x = content.indexOf("<%@ include file=", x);

			if (x == -1) {
				break;
			}

			x = content.indexOf(CharPool.QUOTE, x);

			if (x == -1) {
				break;
			}

			int y = content.indexOf(CharPool.QUOTE, x + 1);

			if (y == -1) {
				break;
			}

			String includeFileName = content.substring(x + 1, y);

			if (!includeFileName.startsWith(StringPool.SLASH)) {
				includeFileName = StringPool.SLASH + includeFileName;
			}

			Matcher matcher = _jspIncludeFilePattern.matcher(includeFileName);

			if (!matcher.find()) {
				throw new RuntimeException(
					"Invalid include " + includeFileName);
			}

			String extension = matcher.group(1);

			if (extension.equals("svg")) {
				x = y;

				continue;
			}

			includeFileName = buildFullPathIncludeFileName(
				fileName, includeFileName);

			if ((includeFileName.endsWith("jsp") ||
				 includeFileName.endsWith("jspf")) &&
				!includeFileName.endsWith("html/common/init.jsp") &&
				!includeFileName.endsWith("html/portlet/init.jsp") &&
				!includeFileName.endsWith("html/taglib/init.jsp") &&
				!fileNames.contains(includeFileName)) {

				includeFileNames.add(includeFileName);
			}

			x = y;
		}

		return includeFileNames;
	}

	protected Set<String> getJSPReferenceFileNames(
		String fileName, Collection<String> fileNames) {

		Set<String> referenceFileNames = new HashSet<>();

		if (!fileName.endsWith("init.jsp") && !fileName.endsWith("init.jspf") &&
			!fileName.contains("init-ext.jsp")) {

			return referenceFileNames;
		}

		for (Map.Entry<String, String> entry : _jspContents.entrySet()) {
			String referenceFileName = entry.getKey();

			if (fileNames.contains(referenceFileName)) {
				continue;
			}

			String sharedPath = fileName.substring(
				0, StringUtil.startsWithWeight(referenceFileName, fileName));

			if (Validator.isNull(sharedPath) ||
				!sharedPath.contains(StringPool.SLASH)) {

				continue;
			}

			if (!sharedPath.endsWith(StringPool.SLASH)) {
				sharedPath = sharedPath.substring(
					0, sharedPath.lastIndexOf(CharPool.SLASH) + 1);
			}

			String content = null;

			for (int x = -1;;) {
				x = sharedPath.indexOf(CharPool.SLASH, x + 1);

				if (x == -1) {
					break;
				}

				if (content == null) {
					content = entry.getValue();
				}

				if (content.contains(
						"<%@ include file=\"" + fileName.substring(x)) ||
					content.contains(
						"<%@ include file=\"" + fileName.substring(x + 1))) {

					referenceFileNames.add(referenceFileName);

					break;
				}
			}
		}

		return referenceFileNames;
	}

	protected int getTaglibXSSVulnerabilityPos(
		String content, String vulnerability) {

		int x = -1;

		while (true) {
			x = content.indexOf(vulnerability, x + 1);

			if (x == -1) {
				return x;
			}

			String tagContent = null;

			int y = x;

			while (true) {
				y = content.lastIndexOf(CharPool.LESS_THAN, y - 1);

				if (y == -1) {
					return -1;
				}

				if (content.charAt(y + 1) == CharPool.PERCENT) {
					continue;
				}

				tagContent = content.substring(y, x);

				if (getLevel(tagContent, "<", ">") == 1) {
					break;
				}
			}

			if (!tagContent.startsWith("<aui:") &&
				!tagContent.startsWith("<liferay-portlet:") &&
				!tagContent.startsWith("<liferay-util:") &&
				!tagContent.startsWith("<portlet:")) {

				return x;
			}
		}
	}

	protected String getUtilTaglibSrcDirName() {
		if (_utilTaglibSrcDirName != null) {
			return _utilTaglibSrcDirName;
		}

		File utilTaglibDir = getFile("util-taglib/src", PORTAL_MAX_DIR_LEVEL);

		String utilTaglibSrcDirName = null;

		if (utilTaglibDir != null) {
			utilTaglibSrcDirName = utilTaglibDir.getAbsolutePath();

			utilTaglibSrcDirName = StringUtil.replace(
				utilTaglibSrcDirName, CharPool.BACK_SLASH, CharPool.SLASH);

			utilTaglibSrcDirName += StringPool.SLASH;
		}
		else {
			utilTaglibSrcDirName = StringPool.BLANK;
		}

		_utilTaglibSrcDirName = utilTaglibSrcDirName;

		return _utilTaglibSrcDirName;
	}

	protected String getVariableName(String line) {
		if (!line.endsWith(";") || line.startsWith("//")) {
			return null;
		}

		String variableName = null;

		int x = line.indexOf(" = ");

		if (x == -1) {
			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1, line.length() - 1);
			}
		}
		else {
			line = line.substring(0, x);

			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1);
			}
		}

		if (Validator.isVariableName(variableName)) {
			return variableName;
		}

		return null;
	}

	protected boolean hasUnusedJSPTerm(
		String fileName, String regex, String type,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames) {

		includeFileNames.add(fileName);

		Set<String> checkedForUnusedJSPTerm = new HashSet<>();

		return !isJSPTermRequired(
			fileName, regex, type, checkedForUnusedJSPTerm,
			checkedForIncludesFileNames, includeFileNames);
	}

	protected boolean hasUnusedVariable(
		String fileName, String line, Set<String> checkedForIncludesFileNames,
		Set<String> includeFileNames) {

		if (line.contains(": ")) {
			return false;
		}

		String variableName = getVariableName(line);

		if (Validator.isNull(variableName) || variableName.equals("false") ||
			variableName.equals("true")) {

			return false;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("((/)|(\\*)|(\\+(\\+)?)|(-(-)?)|\\(|=)?( )?");
		sb.append(variableName);
		sb.append("( )?(\\.");
		sb.append("|(((\\+)|(-)|(\\*)|(/)|(%)|(\\|)|(&)|(\\^))?(=))");
		sb.append("|(\\+(\\+)?)|(-(-)?)");
		sb.append("|(\\)))?");

		return hasUnusedJSPTerm(
			fileName, sb.toString(), "variable", checkedForIncludesFileNames,
			includeFileNames);
	}

	protected boolean isJSPDuplicateImport(
		String fileName, String importLine, boolean checkFile) {

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		int x = importLine.indexOf("page");

		if (x == -1) {
			return false;
		}

		if (checkFile && content.contains(importLine.substring(x))) {
			return true;
		}

		int y = content.indexOf("<%@ include file=");

		if (y == -1) {
			return false;
		}

		y = content.indexOf(CharPool.QUOTE, y);

		if (y == -1) {
			return false;
		}

		int z = content.indexOf(CharPool.QUOTE, y + 1);

		if (z == -1) {
			return false;
		}

		String includeFileName = content.substring(y + 1, z);

		includeFileName = buildFullPathIncludeFileName(
			fileName, includeFileName);

		return isJSPDuplicateImport(includeFileName, importLine, true);
	}

	protected boolean isJSPTermRequired(
		String fileName, String regex, String type,
		Set<String> checkedForUnusedJSPTerm,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames) {

		if (checkedForUnusedJSPTerm.contains(fileName)) {
			return false;
		}

		checkedForUnusedJSPTerm.add(fileName);

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find() &&
			(!type.equals("variable") || (checkedForUnusedJSPTerm.size() > 1) ||
			 matcher.find())) {

			return true;
		}

		if (!checkedForIncludesFileNames.contains(fileName)) {
			includeFileNames.addAll(
				getJSPIncludeFileNames(fileName, includeFileNames));
			includeFileNames.addAll(
				getJSPReferenceFileNames(fileName, includeFileNames));
		}

		checkedForIncludesFileNames.add(fileName);

		String[] includeFileNamesArray = includeFileNames.toArray(
			new String[includeFileNames.size()]);

		for (String includeFileName : includeFileNamesArray) {
			if (!checkedForUnusedJSPTerm.contains(includeFileName) &&
				isJSPTermRequired(
					includeFileName, regex, type, checkedForUnusedJSPTerm,
					checkedForIncludesFileNames, includeFileNames)) {

				return true;
			}
		}

		return false;
	}

	protected void moveFrequentlyUsedImportsToCommonInit(int minCount)
		throws IOException {

		if (_importCountMap.isEmpty()) {
			return;
		}

		String commonInitFileName = "portal-web/docroot/html/common/init.jsp";

		File commonInitFile = null;
		String commonInitFileContent = null;

		int x = -1;

		for (Map.Entry<String, Integer> importCount :
				_importCountMap.entrySet()) {

			Integer count = importCount.getValue();

			if (count < minCount) {
				continue;
			}

			String importName = importCount.getKey();

			int y = importName.lastIndexOf(CharPool.PERIOD);

			String importClassName = importName.substring(y + 1);

			if (_duplicateImportClassNames.contains(importClassName)) {
				continue;
			}

			if (commonInitFileContent == null) {
				commonInitFile = new File(commonInitFileName);

				commonInitFileContent = FileUtil.read(commonInitFile);

				x = commonInitFileContent.indexOf("<%@ page import");
			}

			commonInitFileContent = StringUtil.insert(
				commonInitFileContent,
				"<%@ page import=\"" + importName + "\" %>\n", x);
		}

		if (commonInitFileContent != null) {
			FileUtil.write(commonInitFile, commonInitFileContent);

			_jspContents.put(commonInitFileName, commonInitFileContent);
		}
	}

	@Override
	protected void populateFileChecks() throws Exception {
		_fileChecks.add(new JSPWhitespaceCheck());

		_fileChecks.add(
			new CopyrightCheck(
				getContent(
					sourceFormatterArgs.getCopyrightFileName(),
					PORTAL_MAX_DIR_LEVEL)));
		_fileChecks.add(new JSPEmptyLinesCheck());
		_fileChecks.add(new JSPIfStatementCheck());
		_fileChecks.add(new JSPSessionKeysCheck());
		_fileChecks.add(
			new JSPTagAttributesCheck(
				portalSource, subrepository,
				_getPrimitiveTagAttributeDataTypes(), _getTagJavaClassesMap()));
		_fileChecks.add(
			new MethodCallsOrderCheck(getExcludes(METHOD_CALL_SORT_EXCLUDES)));

		if (portalSource || subrepository) {
			_fileChecks.add(
				new ResourceBundleCheck(
					getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES)));
		}

		if (portalSource) {
			_fileChecks.add(
				new JSPLanguageKeysCheck(
					getExcludes(LANGUAGE_KEYS_CHECK_EXCLUDES),
					getPortalLanguageProperties()));
		}
	}

	@Override
	protected void preFormat() throws Exception {
		_moveFrequentlyUsedImportsToCommonInit = GetterUtil.getBoolean(
			getProperty("move.frequently.used.imports.to.common.init"));

		String[] excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> allFileNames = getFileNames(
			sourceFormatterArgs.getBaseDirName(), null, excludes,
			getIncludes(), true);

		try {
			for (String fileName : allFileNames) {
				fileName = StringUtil.replace(
					fileName, CharPool.BACK_SLASH, CharPool.SLASH);

				File file = new File(fileName);

				String absolutePath = getAbsolutePath(fileName);

				String content = FileUtil.read(file);

				Matcher matcher = _includeFilePattern.matcher(content);

				String newContent = content;

				while (matcher.find()) {
					newContent = StringUtil.replaceFirst(
						newContent, matcher.group(),
						"@ include file=\"" + matcher.group(1) + "\"",
						matcher.start());
				}

				processFormattedFile(file, fileName, content, newContent);

				if (portalSource && _moveFrequentlyUsedImportsToCommonInit &&
					fileName.endsWith("/init.jsp") &&
					!isModulesFile(absolutePath, true) &&
					!fileName.endsWith("/common/init.jsp")) {

					addImportCounts(content);
				}

				_jspContents.put(fileName, newContent);
			}

			if (portalSource && _moveFrequentlyUsedImportsToCommonInit) {
				moveFrequentlyUsedImportsToCommonInit(4);
			}
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	protected String removeUnusedTaglibs(
		String fileName, String content,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames) {

		if (!portalSource && !subrepository) {
			return content;
		}

		Matcher matcher = _taglibURIPattern.matcher(content);

		while (matcher.find()) {
			String regex =
				StringPool.LESS_THAN + matcher.group(1) + StringPool.COLON;

			if (hasUnusedJSPTerm(
					fileName, regex, "taglib", checkedForIncludesFileNames,
					includeFileNames)) {

				return StringUtil.removeSubstring(content, matcher.group());
			}
		}

		return content;
	}

	private Set<String> _getPrimitiveTagAttributeDataTypes() {
		return SetUtil.fromArray(
			new String[] {"boolean", "double", "int", "long"});
	}

	private Map<String, JavaClass> _getTagJavaClassesMap() throws Exception {
		Map<String, JavaClass> tagJavaClassesMap = new HashMap<>();

		List<String> tldFileNames = getFileNames(
			sourceFormatterArgs.getBaseDirName(), null,
			new String[] {"**/dependencies/**", "**/util-taglib/**"},
			new String[] {"**/*.tld"}, true);

		outerLoop:
		for (String tldFileName : tldFileNames) {
			tldFileName = StringUtil.replace(
				tldFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File tldFile = new File(tldFileName);

			String content = FileUtil.read(tldFile);

			Document document = readXML(content);

			Element rootElement = document.getRootElement();

			Element shortNameElement = rootElement.element("short-name");

			String shortName = shortNameElement.getStringValue();

			List<Element> tagElements = rootElement.elements("tag");

			String srcDir = null;

			for (Element tagElement : tagElements) {
				Element tagClassElement = tagElement.element("tag-class");

				String tagClassName = tagClassElement.getStringValue();

				if (!tagClassName.startsWith("com.liferay")) {
					continue;
				}

				if (srcDir == null) {
					if (tldFileName.contains("/src/")) {
						srcDir = tldFile.getAbsolutePath();

						srcDir = StringUtil.replace(
							srcDir, CharPool.BACK_SLASH, CharPool.SLASH);

						srcDir =
							srcDir.substring(0, srcDir.lastIndexOf("/src/")) +
								"/src/main/java/";
					}
					else {
						srcDir = getUtilTaglibSrcDirName();

						if (Validator.isNull(srcDir)) {
							continue outerLoop;
						}
					}
				}

				StringBundler sb = new StringBundler(3);

				sb.append(srcDir);
				sb.append(
					StringUtil.replace(
						tagClassName, CharPool.PERIOD, CharPool.SLASH));
				sb.append(".java");

				File tagJavaFile = new File(sb.toString());

				if (!tagJavaFile.exists()) {
					continue;
				}

				JavaDocBuilder javaDocBuilder = new JavaDocBuilder(
					new DefaultDocletTagFactory(),
					new ThreadSafeClassLibrary());

				try {
					javaDocBuilder.addSource(tagJavaFile);
				}
				catch (ParseException pe) {
					continue;
				}

				JavaClass tagJavaClass = javaDocBuilder.getClassByName(
					tagClassName);

				Element tagNameElement = tagElement.element("name");

				String tagName = tagNameElement.getStringValue();

				tagJavaClassesMap.put(
					shortName + StringPool.COLON + tagName, tagJavaClass);
			}
		}

		return tagJavaClassesMap;
	}

	private static final String[] _INCLUDES =
		new String[] {"**/*.jsp", "**/*.jspf", "**/*.tpl", "**/*.vm"};

	private static final String[][] _LIFERAY_FRONTEND_DEFINE_OBJECTS =
		new String[][] {
			new String[] {"String", "currentURL", "currentURLObj.toString()"},
			new String[] {
				"PortletURL", "currentURLObj",
				"PortletURLUtil.getCurrent(liferayPortletRequest, " +
					"liferayPortletResponse)"
			},
			new String[] {
				"ResourceBundle", "resourceBundle",
				"ResourceBundleUtil.getBundle(\"content.Language\", locale, " +
					"getClass()"
			},
			new String[] {
				"WindowState", "windowState",
				"liferayPortletRequest.getWindowState()"
			}
		};

	private static final String[][] _LIFERAY_THEME_DEFINE_OBJECTS =
		new String[][] {
			new String[] {"Account", "account", "themeDisplay.getAccount()"},
			new String[] {
				"ColorScheme", "colorScheme", "themeDisplay.getColorScheme()"
			},
			new String[] {"Company", "company", "themeDisplay.getCompany()"},
			new String[] {"Contact", "contact", "themeDisplay.getContact()"},
			new String[] {"Layout", "layout", "themeDisplay.getLayout()"},
			new String[] {
				"List<Layout>", "layouts", "themeDisplay.getLayouts()"
			},
			new String[] {
				"LayoutTypePortlet", "layoutTypePortlet",
				"themeDisplay.getLayoutTypePortlet()"
			},
			new String[] {"Locale", "locale", "themeDisplay.getLocale()"},
			new String[] {
				"PermissionChecker", "permissionChecker",
				"themeDisplay.getPermissionChecker()"
			},
			new String[] {"long", "plid", "themeDisplay.getPlid()"},
			new String[] {
				"PortletDisplay", "portletDisplay",
				"themeDisplay.getPortletDisplay()"
			},
			new String[] {"User", "realUser", "themeDisplay.getRealUser()"},
			new String[] {
				"long", "scopeGroupId", "themeDisplay.getScopeGroupId()"
			},
			new String[] {"Theme", "theme", "themeDisplay.getTheme()"},
			new String[] {
				"ThemeDisplay", "themeDisplay",
				"(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY)"
			},
			new String[] {"TimeZone", "timeZone", "themeDisplay.getTimeZone()"},
			new String[] {"User", "user", "themeDisplay.getUser()"},
			new String[] {
				"long", "portletGroupId", "themeDisplay.getScopeGroupId()"
			}
		};

	private static final String[][] _PORTLET_DEFINE_OBJECTS = new String[][] {
		new String[] {
			"PortletConfig", "portletConfig",
			"(PortletConfig)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_CONFIG)"
		},
		new String[] {
			"String", "portletName", "portletConfig.getPortletName()"
		},
		new String[] {
			"LiferayPortletRequest", "liferayPortletRequest",
			"PortalUtil.getLiferayPortletRequest(portletRequest)"
		},
		new String[] {
			"PortletRequest", "actionRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "eventRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "renderRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "resourceRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletPreferences", "portletPreferences",
			"portletRequest.getPreferences()"
		},
		new String[] {
			"Map<String, String[]>", "portletPreferencesValues",
			"portletPreferences.getMap()"
		},
		new String[] {
			"PortletSession", "portletSession",
			"portletRequest.getPortletSession()"
		},
		new String[] {
			"Map<String, Object>", "portletSessionScope",
			"portletSession.getAttributeMap()"
		},
		new String[] {
			"LiferayPortletResponse", "liferayPortletResponse",
			"PortalUtil.getLiferayPortletResponse(portletResponse)"
		},
		new String[] {
			"PortletResponse", "actionResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "eventResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "renderResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "resourceResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		}
	};

	private static final String _UNUSED_VARIABLES_EXCLUDES =
		"jsp.unused.variables.excludes";

	private final Pattern _compressedJSPImportPattern = Pattern.compile(
		"(<.*\n*page import=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _compressedJSPTaglibPattern = Pattern.compile(
		"(<.*\n*taglib uri=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _defineObjectsPattern = Pattern.compile(
		"\n\t*(<.*:defineObjects />)(\n|$)");
	private final List<String> _duplicateImportClassNames = new ArrayList<>();
	private final Pattern _emptyJavaSourceTagPattern = Pattern.compile(
		"\n\t*<%\n+\t*%>\n");
	private final List<FileCheck> _fileChecks = new ArrayList<>();
	private final List<String> _importClassNames = new ArrayList<>();
	private final Map<String, Integer> _importCountMap = new HashMap<>();
	private final Pattern _importsPattern = Pattern.compile(
		"page import=\"(.+)\"");
	private final Pattern _includeFilePattern = Pattern.compile(
		"\\s*@\\s*include\\s*file=['\"](.*)['\"]");
	private final Pattern _incorrectClosingTagPattern = Pattern.compile(
		"\n(\t*)\t((?!<\\w).)* />\n");
	private Pattern _javaClassPattern = Pattern.compile(
		"\n(private|protected|public).* class ([A-Za-z0-9]+) " +
			"([\\s\\S]*?)\n\\}\n");
	private final Map<String, String> _jspContents = new HashMap<>();
	private final Pattern _jspIncludeFilePattern = Pattern.compile(
		"/.*\\.(jsp[f]?|svg)");
	private final Pattern _logPattern = Pattern.compile(
		"Log _log = LogFactoryUtil\\.getLog\\(\"(.*?)\"\\)");
	private final Pattern _missingEmptyLineBetweenDefineOjbectsPattern =
		Pattern.compile("<.*:defineObjects />\n<.*:defineObjects />\n");
	private boolean _moveFrequentlyUsedImportsToCommonInit;
	private final Pattern _redirectBackURLPattern = Pattern.compile(
		"(String redirect = ParamUtil\\.getString\\(request, \"redirect\".*" +
			"\\);)\n(String backURL = ParamUtil\\.getString\\(request, \"" +
				"backURL\", redirect\\);)");
	private boolean _stripJSPImports = true;
	private final Pattern _subnamePattern = Pattern.compile(
		"\\s(_?sub[A-Z]\\w+)[; ]");
	private final Pattern _taglibURIPattern = Pattern.compile(
		"<%@\\s+taglib uri=.* prefix=\"(.*?)\" %>");
	private final Pattern _taglibVariablePattern = Pattern.compile(
		"(\n\t*String (taglib\\w+) = (.*);)\n\\s*%>\\s+(<[\\S\\s]*?>)\n");
	private final Pattern _uncompressedJSPImportPattern = Pattern.compile(
		"(<.*page import=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _uncompressedJSPTaglibPattern = Pattern.compile(
		"(<.*taglib uri=\".*>\n*)+", Pattern.MULTILINE);
	private String _utilTaglibSrcDirName;
	private final Pattern _xssPattern = Pattern.compile(
		"\\s+([^\\s]+)\\s*=\\s*(Bean)?ParamUtil\\.getString\\(");

}