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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.CompatClassImportsCheck;
import com.liferay.source.formatter.checks.CopyrightCheck;
import com.liferay.source.formatter.checks.EmptyArrayCheck;
import com.liferay.source.formatter.checks.EmptyCollectionCheck;
import com.liferay.source.formatter.checks.GetterUtilCheck;
import com.liferay.source.formatter.checks.JSPDefineObjectsCheck;
import com.liferay.source.formatter.checks.JSPEmptyLinesCheck;
import com.liferay.source.formatter.checks.JSPIfStatementCheck;
import com.liferay.source.formatter.checks.JSPImportsCheck;
import com.liferay.source.formatter.checks.JSPLanguageKeysCheck;
import com.liferay.source.formatter.checks.JSPLogFileNameCheck;
import com.liferay.source.formatter.checks.JSPModuleIllegalImportsCheck;
import com.liferay.source.formatter.checks.JSPRedirectBackURLCheck;
import com.liferay.source.formatter.checks.JSPSessionKeysCheck;
import com.liferay.source.formatter.checks.JSPStringMethodsCheck;
import com.liferay.source.formatter.checks.JSPStylingCheck;
import com.liferay.source.formatter.checks.JSPSubnameCheck;
import com.liferay.source.formatter.checks.JSPTagAttributesCheck;
import com.liferay.source.formatter.checks.JSPTaglibVariableCheck;
import com.liferay.source.formatter.checks.JSPUnusedImportCheck;
import com.liferay.source.formatter.checks.JSPUnusedTaglibCheck;
import com.liferay.source.formatter.checks.JSPUnusedVariableCheck;
import com.liferay.source.formatter.checks.JSPWhitespaceCheck;
import com.liferay.source.formatter.checks.JSPXSSVulnerabilitiesCheck;
import com.liferay.source.formatter.checks.MethodCallsOrderCheck;
import com.liferay.source.formatter.checks.PrincipalExceptionCheck;
import com.liferay.source.formatter.checks.ResourceBundleCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.StringBundlerCheck;
import com.liferay.source.formatter.checks.StringUtilCheck;
import com.liferay.source.formatter.checks.UnparameterizedClassCheck;
import com.liferay.source.formatter.checks.ValidatorEqualsCheck;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.ThreadSafeClassLibrary;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.DefaultDocletTagFactory;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;

import java.util.ArrayList;
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

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = formatJSP(fileName, absolutePath, content);

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

		// LPS-47682

		newContent = fixIncorrectParameterTypeForLanguageUtil(
			newContent, true, fileName);

		// LPS-62786

		checkPropertyUtils(fileName, newContent);

		JSPSourceTabCalculator jspSourceTabCalculator =
			new JSPSourceTabCalculator();

		newContent = jspSourceTabCalculator.calculateTabs(
			fileName, newContent, (JSPSourceProcessor)this);

		return newContent;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> fileNames = getFileNames(excludes, getIncludes());

		if (fileNames.isEmpty()) {
			return fileNames;
		}

		excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> allFileNames = getFileNames(
			sourceFormatterArgs.getBaseDirName(), null, excludes,
			getIncludes(), true);

		_contentsMap = _getContentsMap(allFileNames);

		if (sourceFormatterArgs.isFormatCurrentBranch() ||
			sourceFormatterArgs.isFormatLatestAuthor() ||
			sourceFormatterArgs.isFormatLocalChanges()) {

			return JSPSourceUtil.addIncludedAndReferencedFileNames(
				fileNames, new HashSet<String>(), _contentsMap);
		}

		return fileNames;
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	protected String formatJSP(
			String fileName, String absolutePath, String content)
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

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
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

	@Override
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected List<SourceCheck> getModuleSourceChecks() {
		return _moduleSourceChecks;
	}

	@Override
	protected void populateSourceChecks() throws Exception {
		if (_contentsMap == null) {
			_contentsMap = _getContentsMap(sourceFormatterArgs.getFileNames());
		}

		_sourceChecks.add(new JSPWhitespaceCheck());

		_sourceChecks.add(new CopyrightCheck(getCopyright()));
		_sourceChecks.add(new EmptyArrayCheck());
		_sourceChecks.add(new EmptyCollectionCheck());
		_sourceChecks.add(new GetterUtilCheck());
		_sourceChecks.add(
			new JSPDefineObjectsCheck(
				portalSource, subrepository,
				getPluginsInsideModulesDirectoryNames()));
		_sourceChecks.add(new JSPEmptyLinesCheck());
		_sourceChecks.add(new JSPIfStatementCheck());
		_sourceChecks.add(new JSPImportsCheck(portalSource, subrepository));
		_sourceChecks.add(new JSPLogFileNameCheck(subrepository));
		_sourceChecks.add(new JSPRedirectBackURLCheck());
		_sourceChecks.add(new JSPSessionKeysCheck());
		_sourceChecks.add(new JSPStylingCheck());
		_sourceChecks.add(new JSPSubnameCheck());
		_sourceChecks.add(
			new JSPTagAttributesCheck(
				portalSource, subrepository,
				_getPrimitiveTagAttributeDataTypes(), _getTagJavaClassesMap()));
		_sourceChecks.add(new JSPTaglibVariableCheck());
		_sourceChecks.add(new JSPUnusedImportCheck(_contentsMap));
		_sourceChecks.add(new JSPXSSVulnerabilitiesCheck());
		_sourceChecks.add(
			new MethodCallsOrderCheck(getExcludes(METHOD_CALL_SORT_EXCLUDES)));
		_sourceChecks.add(new PrincipalExceptionCheck());
		_sourceChecks.add(new StringBundlerCheck(-1));
		_sourceChecks.add(new StringUtilCheck());
		_sourceChecks.add(new UnparameterizedClassCheck());
		_sourceChecks.add(new ValidatorEqualsCheck());

		if (portalSource || subrepository) {
			_sourceChecks.add(
				new JSPStringMethodsCheck(
					getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES)));
			_sourceChecks.add(new JSPUnusedTaglibCheck(_contentsMap));
			_sourceChecks.add(
				new JSPUnusedVariableCheck(
					getExcludes(_UNUSED_VARIABLES_EXCLUDES), _contentsMap));
			_sourceChecks.add(
				new ResourceBundleCheck(
					getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES)));
		}
		else {
			if (GetterUtil.getBoolean(
					getProperty("use.portal.compat.import"))) {

				_sourceChecks.add(
					new CompatClassImportsCheck(getCompatClassNamesMap()));
			}
		}

		if (portalSource) {
			_sourceChecks.add(
				new JSPLanguageKeysCheck(
					getExcludes(LANGUAGE_KEYS_CHECK_EXCLUDES),
					getPortalLanguageProperties()));
		}
	}

	@Override
	protected void populateModuleSourceChecks() throws Exception {
		_moduleSourceChecks.add(new JSPModuleIllegalImportsCheck());
	}

	private Map<String, String> _getContentsMap(List<String> fileNames)
		throws Exception {

		Map<String, String> contentsMap = new HashMap<>();

		if (ListUtil.isEmpty(fileNames)) {
			return contentsMap;
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File file = new File(fileName);

			String content = FileUtil.read(file);

			if (content == null) {
				continue;
			}

			Matcher matcher = _includeFilePattern.matcher(content);

			String newContent = content;

			while (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, matcher.group(),
					"@ include file=\"" + matcher.group(1) + "\"",
					matcher.start());
			}

			processFormattedFile(file, fileName, content, newContent);

			contentsMap.put(fileName, newContent);
		}

		return contentsMap;
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
						srcDir = _getUtilTaglibSrcDirName();

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

	private String _getUtilTaglibSrcDirName() {
		File utilTaglibDir = getFile("util-taglib/src", PORTAL_MAX_DIR_LEVEL);

		if (utilTaglibDir == null) {
			return StringPool.BLANK;
		}

		String utilTaglibSrcDirName = utilTaglibDir.getAbsolutePath();

		utilTaglibSrcDirName = StringUtil.replace(
			utilTaglibSrcDirName, CharPool.BACK_SLASH, CharPool.SLASH);

		utilTaglibSrcDirName += StringPool.SLASH;

		return utilTaglibSrcDirName;
	}

	private static final String[] _INCLUDES =
		new String[] {"**/*.jsp", "**/*.jspf", "**/*.tpl", "**/*.vm"};

	private static final String _UNUSED_VARIABLES_EXCLUDES =
		"jsp.unused.variables.excludes";

	private Map<String, String> _contentsMap;
	private final Pattern _includeFilePattern = Pattern.compile(
		"\\s*@\\s*include\\s*file=['\"](.*)['\"]");
	private final Pattern _jspIncludeFilePattern = Pattern.compile(
		"/.*\\.(jsp[f]?|svg)");
	private final List<SourceCheck> _moduleSourceChecks = new ArrayList<>();
	private final List<SourceCheck> _sourceChecks = new ArrayList<>();

}