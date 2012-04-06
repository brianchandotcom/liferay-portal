/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.util.ContentUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static void main(String[] args) {
		try {
			_excludes = StringUtil.split(
				GetterUtil.getString(
					System.getProperty("source.formatter.excludes")));

			_sourceFormatterHelper = new SourceFormatterHelper(false);

			_sourceFormatterHelper.init();

			_javaTermAlphabetizeExclusionsProperties = _getExclusionsProperties(
				"source_formatter_javaterm_alphabetize_exclusions.properties");
			_lineLengthExclusionsProperties = _getExclusionsProperties(
				"source_formatter_line_length_exclusions.properties");

			Thread thread1 = new Thread () {

				@Override
				public void run() {
					try {
						_formatJSP();
						_formatAntXML();
						_formatDDLStructuresXML();
						_formatFriendlyURLRoutesXML();
						_formatPortletXML();
						_formatSH();
						_formatStrutsConfigXML();
						_formatTilesDefsXML();
						_formatWebXML();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

			};

			Thread thread2 = new Thread () {

				@Override
				public void run() {
					try {
						_formatJava();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

			};

			thread1.start();
			thread2.start();

			thread1.join();
			thread2.join();

			_sourceFormatterHelper.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String stripJavaImports(
			String content, String packageDir, String className)
		throws IOException {

		Matcher matcher = _javaImportPattern.matcher(content);

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
			if (line.contains("import ")) {
				int importX = line.indexOf(" ");
				int importY = line.lastIndexOf(".");

				String importPackage = line.substring(importX + 1, importY);
				String importClass = line.substring(
					importY + 1, line.length() - 1);

				if (!packageDir.equals(importPackage)) {
					if (!importClass.equals("*")) {
						if (classes.contains(importClass)) {
							sb.append(line);
							sb.append("\n");
						}
					}
					else {
						sb.append(line);
						sb.append("\n");
					}
				}
			}
		}

		imports = _formatImports(sb.toString(), 7);

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

	private static void _addJSPIncludeFileNames(
		String fileName, Set<String> includeFileNames) {

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return;
		}

		for (int x = 0;;) {
			x = content.indexOf("<%@ include file=", x);

			if (x == -1) {
				break;
			}

			x = content.indexOf(StringPool.QUOTE, x);

			if (x == -1) {
				break;
			}

			int y = content.indexOf(StringPool.QUOTE, x + 1);

			if (y == -1) {
				break;
			}

			String includeFileName = content.substring(x + 1, y);

			Matcher matcher = _jspIncludeFilePattern.matcher(includeFileName);

			if (!matcher.find()) {
				throw new RuntimeException(
					"Invalid include " + includeFileName);
			}

			String docrootPath = fileName.substring(
				0, fileName.indexOf("docroot") + 7);

			includeFileName = docrootPath + includeFileName;

			if ((includeFileName.endsWith("jsp") ||
				 includeFileName.endsWith("jspf")) &&
				!includeFileNames.contains(includeFileName) &&
				!includeFileName.contains("html/portlet/init.jsp")) {

				includeFileNames.add(includeFileName);
			}

			x = y;
		}
	}

	private static void _addJSPReferenceFileNames(
		String fileName, Set<String> includeFileNames) {

		for (Map.Entry<String, String> entry : _jspContents.entrySet()) {
			String referenceFileName = entry.getKey();
			String content = entry.getValue();

			if (content.contains("<%@ include file=\"" + fileName) &&
				!includeFileNames.contains(referenceFileName)) {

				includeFileNames.add(referenceFileName);
			}
		}
	}

	private static void _addJSPUnusedImports(
		String fileName, List<String> importLines,
		List<String> unneededImports) {

		for (String importLine : importLines) {
			Set<String> includeFileNames = new HashSet<String>();

			includeFileNames.add(fileName);

			Set<String> checkedFileNames = new HashSet<String>();

			int x = importLine.indexOf(StringPool.QUOTE);
			int y = importLine.indexOf(StringPool.QUOTE, x + 1);

			if ((x == -1) || (y == -1)) {
				continue;
			}

			String className = importLine.substring(x + 1, y);

			className = className.substring(
				className.lastIndexOf(StringPool.PERIOD) + 1);

			if (!_isJSPImportRequired(
					fileName, className, includeFileNames, checkedFileNames)) {

				unneededImports.add(importLine);
			}
		}
	}

	private static List<String> _addMethodParameterTypes(
		String line, List<String> methodParameterTypes) {

		int x = line.indexOf(StringPool.OPEN_PARENTHESIS);

		if (x != -1) {
			line = line.substring(x + 1);

			if (Validator.isNull(line) ||
				line.startsWith(StringPool.CLOSE_PARENTHESIS)) {

				return methodParameterTypes;
			}
		}

		for (x = 0;;) {
			x = line.indexOf(StringPool.SPACE);

			if (x == -1) {
				return methodParameterTypes;
			}

			String parameterType = line.substring(0, x);

			if (parameterType.equals("throws")) {
				return methodParameterTypes;
			}

			methodParameterTypes.add(parameterType);

			int y = line.indexOf(StringPool.COMMA);
			int z = line.indexOf(StringPool.CLOSE_PARENTHESIS);

			if ((y == -1) || ((z != -1) && (z < y))) {
				return methodParameterTypes;
			}

			line = line.substring(y + 1);
			line = line.trim();
		}
	}

	private static void _checkIfClause(
		String ifClause, String fileName, int lineCount) {

		if (ifClause.contains(StringPool.DOUBLE_SLASH) ||
			ifClause.contains("/*") || ifClause.contains("*/")) {

			return;
		}

		int quoteCount = StringUtil.count(ifClause, StringPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return;
		}

		ifClause = _stripQuotes(ifClause);

		ifClause = StringUtil.replace(
			ifClause, new String[] {"'('", "')'"},
			new String[] {StringPool.BLANK, StringPool.BLANK});

		int level = 0;
		int max = StringUtil.count(ifClause, StringPool.OPEN_PARENTHESIS);
		int previousParenthesisPos = -1;

		int[] levels = new int[max];

		for (int i = 0; i < ifClause.length(); i++) {
			char c = ifClause.charAt(i);

			if ((c == CharPool.OPEN_PARENTHESIS) ||
				(c == CharPool.CLOSE_PARENTHESIS)) {

				if (previousParenthesisPos != -1) {
					String s = ifClause.substring(
						previousParenthesisPos + 1, i);

					_checkMissingParentheses(s, fileName, lineCount);
				}

				previousParenthesisPos = i;

				if (c == CharPool.OPEN_PARENTHESIS) {
					levels[level] = i;

					level += 1;
				}
				else {
					int posOpenParenthesis = levels[level - 1];

					if (level > 1) {
						char nextChar = ifClause.charAt(i + 1);
						char previousChar = ifClause.charAt(
							posOpenParenthesis - 1);

						if (!Character.isLetterOrDigit(nextChar) &&
							(nextChar != CharPool.PERIOD) &&
							!Character.isLetterOrDigit(previousChar)) {

							String s = ifClause.substring(
								posOpenParenthesis + 1, i);

							if (Validator.isNotNull(s) &&
								!s.contains(StringPool.SPACE)) {

								_sourceFormatterHelper.printError(
									fileName,
									"redundant parentheses: " + fileName + " " +
										lineCount);
							}
						}

						if ((previousChar == CharPool.OPEN_PARENTHESIS) &&
							(nextChar == CharPool.CLOSE_PARENTHESIS)) {

							_sourceFormatterHelper.printError(
								fileName,
								"redundant parentheses: " + fileName + " " +
									lineCount);
						}
					}

					level -= 1;
				}
			}
		}
	}

	private static void _checkMissingParentheses(
		String s, String fileName, int lineCount) {

		if (Validator.isNull(s)) {
			return;
		}

		boolean containsAndOrOperator = (s.contains("&&") || s.contains("||"));

		boolean containsCompareOperator =
			(s.contains(" == ") || s.contains(" != ") || s.contains(" < ") ||
			 s.contains(" > ") || s.contains(" =< ") || s.contains(" => ") ||
			 s.contains(" <= ") || s.contains(" >= "));

		boolean containsMathOperator =
			(s.contains(" = ") || s.contains(" - ") || s.contains(" + ") ||
			 s.contains(" & ") || s.contains(" % ") || s.contains(" * ") ||
			 s.contains(" / "));

		if (containsCompareOperator &&
			(containsAndOrOperator ||
			 (containsMathOperator && !s.contains(StringPool.OPEN_BRACKET)))) {

			_sourceFormatterHelper.printError(
				fileName, "missing parentheses: " + fileName + " " + lineCount);
		}
	}

	private static boolean _checkTaglibVulnerability(
		String jspContent, String vulnerability) {

		int pos1 = -1;

		do {
			pos1 = jspContent.indexOf(vulnerability, pos1 + 1);

			if (pos1 != -1) {
				int pos2 = jspContent.lastIndexOf(CharPool.LESS_THAN, pos1);

				while ((pos2 > 0) &&
					   (jspContent.charAt(pos2 + 1) == CharPool.PERCENT)) {

					pos2 = jspContent.lastIndexOf(CharPool.LESS_THAN, pos2 - 1);
				}

				String tagContent = jspContent.substring(pos2, pos1);

				if (!tagContent.startsWith("<aui:") &&
					!tagContent.startsWith("<liferay-portlet:") &&
					!tagContent.startsWith("<liferay-util:") &&
					!tagContent.startsWith("<portlet:")) {

					return true;
				}
			}
		}
		while (pos1 != -1);

		return false;
	}

	private static void _checkXSS(String fileName, String jspContent) {
		Matcher matcher = _xssPattern.matcher(jspContent);

		while (matcher.find()) {
			boolean xssVulnerable = false;

			String jspVariable = matcher.group(1);

			String anchorVulnerability = " href=\"<%= " + jspVariable + " %>";

			if (_checkTaglibVulnerability(jspContent, anchorVulnerability)) {
				xssVulnerable = true;
			}

			String inputVulnerability = " value=\"<%= " + jspVariable + " %>";

			if (_checkTaglibVulnerability(jspContent, inputVulnerability)) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability1 = "'<%= " + jspVariable + " %>";

			if (jspContent.contains(inlineStringVulnerability1)) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability2 = "(\"<%= " + jspVariable + " %>";

			if (jspContent.contains(inlineStringVulnerability2)) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability3 = " \"<%= " + jspVariable + " %>";

			if (jspContent.contains(inlineStringVulnerability3)) {
				xssVulnerable = true;
			}

			String documentIdVulnerability = ".<%= " + jspVariable + " %>";

			if (jspContent.contains(documentIdVulnerability)) {
				xssVulnerable = true;
			}

			if (xssVulnerable) {
				_sourceFormatterHelper.printError(
					fileName, "(xss): " + fileName + " (" + jspVariable + ")");
			}
		}
	}

	private static void _compareJavaTermNames(
		String fileName, String previousJavaTermName, String javaTermName,
		int lineCount) {

		if (Validator.isNull(previousJavaTermName) ||
			Validator.isNull(javaTermName)) {

			return;
		}

		if (javaTermName.equals("_log")) {
			_sourceFormatterHelper.printError(
				fileName, "sort: " + fileName + " " + lineCount);

			return;
		}

		if (previousJavaTermName.equals("_instance") ||
			previousJavaTermName.equals("_log")) {

			return;
		}

		if (javaTermName.equals("_instance")) {
			_sourceFormatterHelper.printError(
				fileName, "sort: " + fileName + " " + lineCount);

			return;
		}

		if (previousJavaTermName.compareToIgnoreCase(javaTermName) <= 0) {
			return;
		}

		String javaTermNameLowerCase = javaTermName.toLowerCase();
		String previousJavaTermNameLowerCase =
			previousJavaTermName.toLowerCase();

		if (fileName.contains("persistence") &&
			(previousJavaTermName.startsWith("doCount") &&
			 javaTermName.startsWith("doCount")) ||
			(previousJavaTermName.startsWith("doFind") &&
			 javaTermName.startsWith("doFind")) ||
			(previousJavaTermNameLowerCase.startsWith("count") &&
			 javaTermNameLowerCase.startsWith("count")) ||
			(previousJavaTermNameLowerCase.startsWith("filter") &&
			 javaTermNameLowerCase.startsWith("filter")) ||
			(previousJavaTermNameLowerCase.startsWith("find") &&
			 javaTermNameLowerCase.startsWith("find")) ||
			(previousJavaTermNameLowerCase.startsWith("join") &&
			 javaTermNameLowerCase.startsWith("join"))) {

			return;
		}

		_sourceFormatterHelper.printError(
			fileName, "sort: " + fileName + " " + lineCount);
	}

	private static void _compareMethodParameterTypes(
		String fileName, List<String> previousMethodParameterTypes,
		List<String> methodParameterTypes, int lineCount) {

		if (methodParameterTypes.isEmpty()) {
			_sourceFormatterHelper.printError(
				fileName, "sort: " + fileName + " " + lineCount);

			return;
		}

		for (int i = 0; i < previousMethodParameterTypes.size(); i++) {
			if (methodParameterTypes.size() < (i + 1)) {
				_sourceFormatterHelper.printError(
					fileName, "sort: " + fileName + " " + lineCount);

				return;
			}

			String previousParameterType = previousMethodParameterTypes.get(i);

			if (previousParameterType.endsWith("...")) {
				previousParameterType = StringUtil.replaceLast(
					previousParameterType, "...", StringPool.BLANK);
			}

			String parameterType = methodParameterTypes.get(i);

			if (parameterType.endsWith("...")) {
				parameterType = StringUtil.replaceLast(
					parameterType, "...", StringPool.BLANK);
			}

			if (previousParameterType.compareToIgnoreCase(parameterType) < 0) {
				return;
			}

			if (previousParameterType.compareToIgnoreCase(parameterType) > 0) {
				_sourceFormatterHelper.printError(
					fileName, "sort: " + fileName + " " + lineCount);

				return;
			}

			if (previousParameterType.compareTo(parameterType) > 0) {
				return;
			}

			if (previousParameterType.compareTo(parameterType) < 0) {
				_sourceFormatterHelper.printError(
					fileName, "sort: " + fileName + " " + lineCount);

				return;
			}
		}
	}

	private static String _fixAntXMLProjectName(
			String basedir, String fileName, String content)
		throws IOException {

		int x = 0;

		if (fileName.endsWith("-ext/build.xml")) {
			x = fileName.indexOf("ext/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 4;
			}
		}
		else if (fileName.endsWith("-hook/build.xml")) {
			x = fileName.indexOf("hooks/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 6;
			}
		}
		else if (fileName.endsWith("-layouttpl/build.xml")) {
			x = fileName.indexOf("layouttpl/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 10;
			}
		}
		else if (fileName.endsWith("-portlet/build.xml")) {
			x = fileName.indexOf("portlets/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 9;
			}
		}
		else if (fileName.endsWith("-theme/build.xml")) {
			x = fileName.indexOf("themes/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 7;
			}
		}
		else if (fileName.endsWith("-web/build.xml") &&
				 !fileName.endsWith("/ext-web/build.xml")) {

			x = fileName.indexOf("webs/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 5;
			}
		}
		else {
			return content;
		}

		int y = fileName.indexOf("/", x);

		String correctProjectElementText =
			"<project name=\"" + fileName.substring(x, y) + "\"";

		if (!content.contains(correctProjectElementText)) {
			x = content.indexOf("<project name=\"");

			y = content.indexOf("\"", x) + 1;
			y = content.indexOf("\"", y) + 1;

			content =
				content.substring(0, x) + correctProjectElementText +
					content.substring(y);

			_sourceFormatterHelper.printError(
				fileName, fileName + " has an incorrect project name");

			_fileUtil.write(basedir + fileName, content);
		}

		return content;
	}

	private static void _formatAntXML() throws DocumentException, IOException {
		String basedir = "./";

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(new String[] {"**\\b*.xml"});
		directoryScanner.setExcludes(new String[] {"**\\tools\\**"});

		List<String> fileNames = _sourceFormatterHelper.scanForFiles(
			directoryScanner);

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(fileName, "\\", "/");

			String content = _fileUtil.read(basedir + fileName);

			content = _fixAntXMLProjectName(basedir, fileName, content);

			Document document = _saxReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			String previousName = StringPool.BLANK;

			List<Element> targetElements = rootElement.elements("target");

			for (Element targetElement : targetElements) {
				String name = targetElement.attributeValue("name");

				if (name.equals("Test")) {
					name = name.toLowerCase();
				}

				if (name.compareTo(previousName) < -1) {
					_sourceFormatterHelper.printError(
						fileName,
						fileName + " has an unordered target " + name);

					break;
				}

				previousName = name;
			}
		}
	}

	private static void _formatDDLStructuresXML()
		throws DocumentException, IOException {

		String basedir =
			"./portal-impl/src/com/liferay/portal/events/dependencies/";

		if (!_fileUtil.exists(basedir)) {
			return;
		}

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(new String[] {"**\\*structures.xml"});

		List<String> fileNames = _sourceFormatterHelper.scanForFiles(
			directoryScanner);

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			String content = _fileUtil.read(file);

			String newContent = _formatDDLStructuresXML(content);

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				_sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	private static String _formatDDLStructuresXML(String content)
		throws DocumentException, IOException {

		Document document = _saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		rootElement.sortAttributes(true);

		rootElement.sortElementsByChildElement("structure", "name");

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			Element structureRootElement = structureElement.element("root");

			structureRootElement.sortElementsByAttribute(
				"dynamic-element", "name");

			List<Element> dynamicElementElements =
				structureRootElement.elements("dynamic-element");

			for (Element dynamicElementElement : dynamicElementElements) {
				Element metaDataElement = dynamicElementElement.element(
					"meta-data");

				metaDataElement.sortElementsByAttribute("entry", "name");
			}
		}

		return document.formattedString();
	}

	private static void _formatFriendlyURLRoutesXML()
		throws DocumentException, IOException {

		String basedir = "./";

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(new String[] {"**\\*routes.xml"});
		directoryScanner.setExcludes(
			new String[] {"**\\classes\\**", "**\\bin\\**"});

		List<String> fileNames = _sourceFormatterHelper.scanForFiles(
			directoryScanner);

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			String content = _fileUtil.read(file);

			if (content.contains("<!-- SourceFormatter.Ignore -->")) {
				continue;
			}

			String newContent = _formatFriendlyURLRoutesXML(content);

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				_sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	private static String _formatFriendlyURLRoutesXML(String content)
	 	throws DocumentException {

		Document document = _saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<ComparableRoute> comparableRoutes =
			new ArrayList<ComparableRoute>();

		for (Element routeElement : rootElement.elements("route")) {
			String pattern = routeElement.elementText("pattern");

			ComparableRoute comparableRoute = new ComparableRoute(pattern);

			for (Element generatedParameterElement :
					routeElement.elements("generated-parameter")) {

				String name = generatedParameterElement.attributeValue("name");
				String value = generatedParameterElement.getText();

				comparableRoute.addGeneratedParameter(name, value);
			}

			for (Element ignoredParameterElement :
					routeElement.elements("ignored-parameter")) {

				String name = ignoredParameterElement.attributeValue("name");

				comparableRoute.addIgnoredParameter(name);
			}

			for (Element implicitParameterElement :
					routeElement.elements("implicit-parameter")) {

				String name = implicitParameterElement.attributeValue("name");
				String value = implicitParameterElement.getText();

				comparableRoute.addImplicitParameter(name, value);
			}

			for (Element overriddenParameterElement :
					routeElement.elements("overridden-parameter")) {

				String name = overriddenParameterElement.attributeValue("name");
				String value = overriddenParameterElement.getText();

				comparableRoute.addOverriddenParameter(name, value);
			}

			comparableRoutes.add(comparableRoute);
		}

		Collections.sort(comparableRoutes);

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE routes PUBLIC \"-//Liferay//DTD Friendly URL ");
		sb.append("Routes 6.1.0//EN\" \"http://www.liferay.com/dtd/");
		sb.append("liferay-friendly-url-routes_6_1_0.dtd\">\n\n<routes>\n");

		for (ComparableRoute comparableRoute : comparableRoutes) {
			sb.append("\t<route>\n");
			sb.append("\t\t<pattern>");
			sb.append(comparableRoute.getPattern());
			sb.append("</pattern>\n");

			Map<String, String> generatedParameters =
				comparableRoute.getGeneratedParameters();

			for (Map.Entry<String, String> entry :
					generatedParameters.entrySet()) {

				sb.append("\t\t<generated-parameter name=\"");
				sb.append(entry.getKey());
				sb.append("\">");
				sb.append(entry.getValue());
				sb.append("</generated-parameter>\n");
			}

			Set<String> ignoredParameters =
				comparableRoute.getIgnoredParameters();

			for (String entry : ignoredParameters) {
				sb.append("\t\t<ignored-parameter name=\"");
				sb.append(entry);
				sb.append("\" />\n");
			}

			Map<String, String> implicitParameters =
				comparableRoute.getImplicitParameters();

			for (Map.Entry<String, String> entry :
					implicitParameters.entrySet()) {

				sb.append("\t\t<implicit-parameter name=\"");
				sb.append(entry.getKey());
				sb.append("\">");
				sb.append(entry.getValue());
				sb.append("</implicit-parameter>\n");
			}

			Map<String, String> overriddenParameters =
				comparableRoute.getOverriddenParameters();

			for (Map.Entry<String, String> entry :
					overriddenParameters.entrySet()) {

				sb.append("\t\t<overridden-parameter name=\"");
				sb.append(entry.getKey());
				sb.append("\">");
				sb.append(entry.getValue());
				sb.append("</overridden-parameter>\n");
			}

			sb.append("\t</route>\n");
		}

		sb.append("</routes>");

		return sb.toString();
	}

	private static String _formatImports(String imports, int classStartPos)
		throws IOException {

		if (imports.contains("/*") || imports.contains("*/") ||
			imports.contains("//")) {

			return imports + "\n";
		}

		List<String> importsList = new ArrayList<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if ((line.contains("import=") || line.contains("import ")) &&
				!importsList.contains(line)) {

				importsList.add(line);
			}
		}

		importsList = ListUtil.sort(importsList);

		StringBundler sb = new StringBundler();

		String temp = null;

		for (int i = 0; i < importsList.size(); i++) {
			String s = importsList.get(i);

			int pos = s.indexOf(".");

			pos = s.indexOf(".", pos + 1);

			if (pos == -1) {
				pos = s.indexOf(".");
			}

			String packageLevel = s.substring(classStartPos, pos);

			if ((i != 0) && !packageLevel.equals(temp)) {
				sb.append("\n");
			}

			temp = packageLevel;

			sb.append(s);
			sb.append("\n");
		}

		return sb.toString();
	}

	private static void _formatJava() throws IOException {
		String basedir = "./";

		String copyright = _getCopyright();
		String oldCopyright = _getOldCopyright();

		boolean portalJavaFiles = true;

		Collection<String> fileNames = null;

		if (_fileUtil.exists(basedir + "portal-impl")) {
			fileNames = _getPortalJavaFiles();
		}
		else {
			portalJavaFiles = false;

			fileNames = _getPluginJavaFiles();
		}

		for (String fileName : fileNames) {
			File file = new File(fileName);

			String content = _fileUtil.read(file);

			if (_isGenerated(content)) {
				continue;
			}

			String className = file.getName();

			className = className.substring(0, className.length() - 5);

			String packagePath = fileName;

			int packagePathX = packagePath.indexOf(
				File.separator + "src" + File.separator);
			int packagePathY = packagePath.lastIndexOf(File.separator);

			if ((packagePathX + 5) >= packagePathY) {
				packagePath = StringPool.BLANK;
			}
			else {
				packagePath = packagePath.substring(
					packagePathX + 5, packagePathY);
			}

			packagePath = StringUtil.replace(
				packagePath, File.separator, StringPool.PERIOD);

			if (packagePath.endsWith(".model")) {
				if (content.contains("extends " + className + "Model")) {
					continue;
				}
			}

			String oldContent = content;
			String newContent = StringPool.BLANK;

			for (;;) {
				newContent = _formatJavaContent(fileName, oldContent);

				if (oldContent.equals(newContent)) {
					break;
				}

				oldContent = newContent;
			}

			if (newContent.contains("$\n */")) {
				_sourceFormatterHelper.printError(fileName, "*: " + fileName);

				newContent = StringUtil.replace(
					newContent, "$\n */", "$\n *\n */");
			}

			if ((oldCopyright != null) && newContent.contains(oldCopyright)) {
				newContent = StringUtil.replace(
					newContent, oldCopyright, copyright);

				_sourceFormatterHelper.printError(
					fileName, "old (c): " + fileName);
			}

			if (!newContent.contains(copyright)) {
				String customCopyright = _getCustomCopyright(file);

				if (Validator.isNull(customCopyright) ||
					!newContent.contains(customCopyright)) {

					_sourceFormatterHelper.printError(
						fileName, "(c): " + fileName);
				}
			}

			if (newContent.contains(className + ".java.html")) {
				_sourceFormatterHelper.printError(
					fileName, "Java2HTML: " + fileName);
			}

			if (newContent.contains(" * @author Raymond Aug") &&
				!newContent.contains(" * @author Raymond Aug\u00e9")) {

				newContent = newContent.replaceFirst(
					"Raymond Aug.++", "Raymond Aug\u00e9");

				_sourceFormatterHelper.printError(
					fileName, "UTF-8: " + fileName);
			}

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

			newContent = stripJavaImports(newContent, packagePath, className);

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					";\n/**", "\t/*\n\t *", "else{", "if(", "for(", "while(",
					"List <", "){\n", "]{\n", "\n\n\n"
				},
				new String[] {
					";\n\n/**", "\t/**\n\t *", "else {", "if (", "for (",
					"while (", "List<", ") {\n", "] {\n", "\n\n"
				});

			if (newContent.contains("*/\npackage ")) {
				_sourceFormatterHelper.printError(
					fileName, "package: " + fileName);
			}

			if (!newContent.endsWith("\n\n}") && !newContent.endsWith("{\n}")) {
				_sourceFormatterHelper.printError(fileName, "}: " + fileName);
			}

			if (portalJavaFiles && className.endsWith("ServiceImpl") &&
				newContent.contains("ServiceUtil.")) {

				_sourceFormatterHelper.printError(
					fileName, "ServiceUtil: " + fileName);
			}

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				_sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	private static String _formatJavaContent(String fileName, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		int lineCount = 0;

		String line = null;

		String previousLine = StringPool.BLANK;

		int lineToSkipIfEmpty = 0;

		String javaTermName = null;
		int javaTermType = 0;

		String previousJavaTermName = null;
		int previousJavaTermType = 0;

		List<String> methodParameterTypes = new ArrayList<String>();
		List<String> previousMethodParameterTypes = null;

		boolean readMethodParameterTypes = false;
		boolean hasSameMethodName = false;

		String ifClause = StringPool.BLANK;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			if (line.trim().length() == 0) {
				line = StringPool.BLANK;
			}

			line = StringUtil.trimTrailing(line);

			line = StringUtil.replace(
				line,
				new String[] {
					"* Copyright (c) 2000-2011 Liferay, Inc."
				},
				new String[] {
					"* Copyright (c) 2000-2012 Liferay, Inc."
				});

			if (line.startsWith(StringPool.SPACE) && !line.startsWith(" *")) {
				if (!line.startsWith(StringPool.FOUR_SPACES)) {
					while (line.startsWith(StringPool.SPACE)) {
						line = StringUtil.replaceFirst(
							line, StringPool.SPACE, StringPool.BLANK);
					}
				}
				else {
					int pos = 0;

					String temp = line;

					while (temp.startsWith(StringPool.FOUR_SPACES)) {
						line = StringUtil.replaceFirst(
							line, StringPool.FOUR_SPACES, StringPool.TAB);

						pos++;

						temp = line.substring(pos);
					}
				}
			}

			if (line.contains(StringPool.TAB + "for (") && line.contains(":") &&
				!line.contains(" :")) {

				line = StringUtil.replace(line, ":" , " :");
			}

			line = _replacePrimitiveWrapperInstantiation(
				fileName, line, lineCount);

			String trimmedLine = StringUtil.trimLeading(line);

			if (trimmedLine.startsWith("if (") ||
				trimmedLine.startsWith("else if (") ||
				trimmedLine.startsWith("while (") ||
				Validator.isNotNull(ifClause)) {

				if (Validator.isNull(ifClause) ||
					ifClause.endsWith(StringPool.OPEN_PARENTHESIS)) {

					ifClause = ifClause + trimmedLine;
				}
				else {
					ifClause = ifClause + StringPool.SPACE + trimmedLine;
				}

				if (ifClause.endsWith(") {")) {
					_checkIfClause(ifClause, fileName, lineCount);

					ifClause = StringPool.BLANK;
				}
			}

			String excluded =
				_javaTermAlphabetizeExclusionsProperties.getProperty(
					StringUtil.replace(
						fileName, "\\", "/") + StringPool.AT + lineCount);

			if (excluded == null) {
				excluded = _javaTermAlphabetizeExclusionsProperties.getProperty(
					StringUtil.replace(fileName, "\\", "/"));
			}

			if (line.startsWith(StringPool.TAB + "private ") ||
				line.startsWith(StringPool.TAB + "protected ") ||
				line.startsWith(StringPool.TAB + "public ")) {

				hasSameMethodName = false;

				Tuple tuple = _getJavaTermTuple(line);

				if (tuple != null) {
					javaTermName = (String)tuple.getObject(0);

					if (Validator.isNotNull(javaTermName)) {
						javaTermType = (Integer)tuple.getObject(1);

						boolean isMethod = _isInJavaTermTypeGroup(
							javaTermType, _TYPE_METHOD);

						if (isMethod) {
							readMethodParameterTypes = true;
						}

						if (_isInJavaTermTypeGroup(
								javaTermType, _TYPE_VARIABLE_NOT_STATIC)) {

							char firstChar = javaTermName.charAt(0);

							if (firstChar == CharPool.UNDERLINE) {
								firstChar = javaTermName.charAt(1);
							}

							if (Character.isUpperCase(firstChar)) {
								_sourceFormatterHelper.printError(
									fileName,
									"final: " + fileName + " " + lineCount);
							}
						}

						if (Validator.isNotNull(previousJavaTermName) &&
							(excluded == null)) {

							if (previousJavaTermType > javaTermType) {
								_sourceFormatterHelper.printError(
									fileName,
									"order: " + fileName + " " + lineCount);
							}
							else if (previousJavaTermType == javaTermType) {
								if (isMethod &&
									previousJavaTermName.equals(javaTermName)) {

									hasSameMethodName = true;
								}
								else {
									_compareJavaTermNames(
										fileName, previousJavaTermName,
										javaTermName, lineCount);
								}
							}
						}

						previousJavaTermName = javaTermName;
						previousJavaTermType = javaTermType;
					}
				}
			}

			if (readMethodParameterTypes) {
				methodParameterTypes = _addMethodParameterTypes(
					trimmedLine, methodParameterTypes);

				if (trimmedLine.contains(StringPool.CLOSE_PARENTHESIS)) {
					if (hasSameMethodName) {
						_compareMethodParameterTypes(
							fileName, previousMethodParameterTypes,
							methodParameterTypes, lineCount);
					}

					readMethodParameterTypes = false;

					previousMethodParameterTypes = ListUtil.copy(
						methodParameterTypes);

					methodParameterTypes.clear();
				}
			}

			if (!trimmedLine.contains(StringPool.DOUBLE_SLASH) &&
				!trimmedLine.startsWith(StringPool.STAR)) {

				while (trimmedLine.contains(StringPool.TAB)) {
					line = StringUtil.replaceLast(
						line, StringPool.TAB, StringPool.SPACE);

					trimmedLine = StringUtil.replaceLast(
						trimmedLine, StringPool.TAB, StringPool.SPACE);
				}

				while (trimmedLine.contains(StringPool.DOUBLE_SPACE) &&
					   !trimmedLine.contains(
						   StringPool.QUOTE + StringPool.DOUBLE_SPACE) &&
					   !fileName.contains("Test")) {

					line = StringUtil.replaceLast(
						line, StringPool.DOUBLE_SPACE, StringPool.SPACE);

					trimmedLine = StringUtil.replaceLast(
						trimmedLine, StringPool.DOUBLE_SPACE, StringPool.SPACE);
				}

				if (!line.contains(StringPool.QUOTE)) {
					if ((trimmedLine.startsWith("private ") ||
						 trimmedLine.startsWith("protected ") ||
						 trimmedLine.startsWith("public ")) &&
						line.contains(" (")) {

						line = StringUtil.replace(line, " (", "(");
					}

					if (line.contains(" [")) {
						line = StringUtil.replace(line, " [", "[");
					}

					for (int x = -1;;) {
						x = line.indexOf(StringPool.COMMA, x + 1);

						if (x == -1) {
							break;
						}

						if (line.length() > (x + 1)) {
							char nextChar = line.charAt(x + 1);

							if ((nextChar != CharPool.SPACE) &&
								(nextChar != CharPool.APOSTROPHE)) {

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
			}

			if (line.contains("    ") && !line.matches("\\s*\\*.*")) {
				if (!fileName.endsWith("StringPool.java")) {
					_sourceFormatterHelper.printError(
						fileName, "tab: " + fileName + " " + lineCount);
				}
			}

			if (line.contains("  {") && !line.matches("\\s*\\*.*")) {
				_sourceFormatterHelper.printError(
					fileName, "{:" + fileName + " " + lineCount);
			}

			excluded = _lineLengthExclusionsProperties.getProperty(
				StringUtil.replace(
					fileName, "\\", "/") + StringPool.AT + lineCount);

			if (excluded == null) {
				excluded = _lineLengthExclusionsProperties.getProperty(
					StringUtil.replace(fileName, "\\", "/"));
			}

			String[] combinedLines = null;

			if ((excluded == null) &&
				!line.startsWith("import ") && !line.startsWith("package ") &&
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
				else {
					if (_getLineLength(line) > 80) {
						_sourceFormatterHelper.printError(
							fileName, "> 80: " + fileName + " " + lineCount);
					}
					else {
						int lineTabCount = StringUtil.count(
							line, StringPool.TAB);
						int previousLineTabCount = StringUtil.count(
							previousLine, StringPool.TAB);

						if (previousLine.endsWith(StringPool.COMMA) &&
							previousLine.contains(
								StringPool.OPEN_PARENTHESIS) &&
							!previousLine.contains("for (") &&
							(lineTabCount > previousLineTabCount)) {

							_sourceFormatterHelper.printError(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}

						combinedLines = _getCombinedLines(
							trimmedLine, previousLine, lineTabCount,
							previousLineTabCount);
					}
				}
			}

			if (Validator.isNotNull(combinedLines)) {
				previousLine = combinedLines[0];

				if (combinedLines.length > 1) {
					String addedToPreviousLine = combinedLines[1];

					if (Validator.isNotNull(addedToPreviousLine)) {
						sb.append(previousLine);
						sb.append("\n");

						previousLine = StringUtil.replaceFirst(
							line, addedToPreviousLine, StringPool.BLANK);
					}
				}
				else if (line.endsWith(StringPool.OPEN_CURLY_BRACE)) {
					lineToSkipIfEmpty = lineCount + 1;
				}
			}
			else {
				if ((lineCount > 1) &&
					(Validator.isNotNull(previousLine) ||
					 (lineToSkipIfEmpty != (lineCount - 1)))) {

					sb.append(previousLine);
					sb.append("\n");
				}

				previousLine = line;
			}
		}

		sb.append(previousLine);

		unsyncBufferedReader.close();

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		return newContent;
	}

	private static void _formatJSP() throws IOException {
		String basedir = "./";

		String copyright = _getCopyright();
		String oldCopyright = _getOldCopyright();

		List<String> list = new ArrayList<String>();

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		String[] excludes = {
			"**\\portal\\aui\\**", "**\\bin\\**", "**\\null.jsp", "**\\tmp\\**",
			"**\\tools\\**"
		};

		excludes = ArrayUtil.append(excludes, _excludes);

		directoryScanner.setExcludes(excludes);

		directoryScanner.setIncludes(
			new String[] {"**\\*.jsp", "**\\*.jspf", "**\\*.vm"});

		list.addAll(_sourceFormatterHelper.scanForFiles(directoryScanner));

		String[] fileNames = list.toArray(new String[list.size()]);

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			String content = _fileUtil.read(file);

			fileName = fileName.replace(
				CharPool.BACK_SLASH, CharPool.FORWARD_SLASH);

			_jspContents.put(fileName, content);
		}

		boolean stripJSPImports = true;

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			String content = _fileUtil.read(file);

			String oldContent = content;
			String newContent = StringPool.BLANK;

			for (;;) {
				newContent = _formatJSPContent(fileName, oldContent);

				if (oldContent.equals(newContent)) {
					break;
				}

				oldContent = newContent;
			}

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"<br/>", "\"/>", "\" >", "@page import", "\"%>", ")%>",
					"javascript: "
				},
				new String[] {
					"<br />", "\" />", "\">", "@ page import", "\" %>", ") %>",
					"javascript:"
				});

			if (stripJSPImports) {
				try {
					newContent = _stripJSPImports(fileName, newContent);
				}
				catch (RuntimeException re) {
					stripJSPImports = false;
				}
			}

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"* Copyright (c) 2000-2011 Liferay, Inc."
				},
				new String[] {
					"* Copyright (c) 2000-2012 Liferay, Inc."
				});

			if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf")) {
				if ((oldCopyright != null) &&
					newContent.contains(oldCopyright)) {

					newContent = StringUtil.replace(
						newContent, oldCopyright, copyright);

					_sourceFormatterHelper.printError(
						fileName, "old (c): " + fileName);
				}

				if (!newContent.contains(copyright)) {
					String customCopyright = _getCustomCopyright(file);

					if (Validator.isNull(customCopyright) ||
						!newContent.contains(customCopyright)) {

						_sourceFormatterHelper.printError(
							fileName, "(c): " + fileName);
					}
					else {
						newContent = StringUtil.replace(
							newContent, "<%\n" + customCopyright + "\n%>",
							"<%--\n" + customCopyright + "\n--%>");
					}
				}
				else {
					newContent = StringUtil.replace(
						newContent, "<%\n" + copyright + "\n%>",
						"<%--\n" + copyright + "\n--%>");
				}
			}

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"alert('<%= LanguageUtil.",
					"alert(\"<%= LanguageUtil.", "confirm('<%= LanguageUtil.",
					"confirm(\"<%= LanguageUtil."
				},
				new String[] {
					"alert('<%= UnicodeLanguageUtil.",
					"alert(\"<%= UnicodeLanguageUtil.",
					"confirm('<%= UnicodeLanguageUtil.",
					"confirm(\"<%= UnicodeLanguageUtil."
				});

			if (newContent.contains("    ")) {
				if (!fileName.endsWith("template.vm")) {
					_sourceFormatterHelper.printError(
						fileName, "tab: " + fileName);
				}
			}

			if (fileName.endsWith("init.jsp")) {
				int x = newContent.indexOf("<%@ page import=");

				int y = newContent.lastIndexOf("<%@ page import=");

				y = newContent.indexOf("%>", y);

				if ((x != -1) && (y != -1) && (y > x)) {

					// Set compressImports to false to decompress imports

					boolean compressImports = true;

					if (compressImports) {
						String imports = newContent.substring(x, y);

						imports = StringUtil.replace(
							imports, new String[] {"%>\r\n<%@ ", "%>\n<%@ "},
							new String[] {"%><%@\r\n", "%><%@\n"});

						newContent =
							newContent.substring(0, x) + imports +
								newContent.substring(y);
					}
				}
			}

			_checkXSS(fileName, newContent);

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				_sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	private static String _formatJSPContent(String fileName, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		int lineCount = 0;

		String line = null;

		String previousLine = StringPool.BLANK;

		String currentAttributeAndValue = null;
		String previousAttribute = null;
		String previousAttributeAndValue = null;

		boolean readAttributes = false;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			if (line.trim().length() == 0) {
				line = StringPool.BLANK;
			}

			line = StringUtil.trimTrailing(line);

			if (line.contains("<aui:button ") &&
				line.contains("type=\"button\"")) {

				_sourceFormatterHelper.printError(
					fileName, "aui:button " + fileName + " " + lineCount);
			}

			String trimmedLine = StringUtil.trimLeading(line);
			String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

			if (trimmedPreviousLine.equals("%>") && Validator.isNotNull(line) &&
				!trimmedLine.equals("-->")) {

				sb.append("\n");
			}
			else if (Validator.isNotNull(previousLine) &&
					 !trimmedPreviousLine.equals("<!--") &&
					 trimmedLine.equals("<%")) {

				sb.append("\n");
			}
			else if (trimmedPreviousLine.equals("<%") &&
					 Validator.isNull(line)) {

				continue;
			}
			else if (trimmedPreviousLine.equals("<%") &&
					 trimmedLine.startsWith("//")) {

				sb.append("\n");
			}
			else if (Validator.isNull(previousLine) &&
					 trimmedLine.equals("%>") && (sb.index() > 2)) {

				String lineBeforePreviousLine = sb.stringAt(sb.index() - 3);

				if (!lineBeforePreviousLine.startsWith("//")) {
					sb.setIndex(sb.index() - 1);
				}
			}

			if ((trimmedLine.startsWith("if (") ||
				 trimmedLine.startsWith("else if (") ||
				 trimmedLine.startsWith("while (")) &&
				trimmedLine.endsWith(") {")) {

				_checkIfClause(trimmedLine, fileName, lineCount);
			}

			if (readAttributes) {
				if (!trimmedLine.startsWith(StringPool.FORWARD_SLASH) &&
					!trimmedLine.startsWith(StringPool.GREATER_THAN)) {

					int pos = trimmedLine.indexOf(StringPool.EQUAL);

					if (pos != -1) {
						String attribute = trimmedLine.substring(0, pos);

						if (Validator.isNotNull(previousAttribute)) {
							if (!_isJSPAttributName(attribute)) {
								_sourceFormatterHelper.printError(
									fileName,
									"attribute: " + fileName + " " + lineCount);
							}
							else if (Validator.isNull(
										previousAttributeAndValue) &&
									 (previousAttribute.compareTo(
										 attribute) > 0)) {

								previousAttributeAndValue = previousLine;
								currentAttributeAndValue = line;
							}
						}

						previousAttribute = attribute;
					}
				}
				else {
					previousAttribute = null;

					readAttributes = false;
				}
			}

			if (trimmedLine.startsWith(StringPool.LESS_THAN) &&
				!trimmedLine.startsWith("<%") &&
				!trimmedLine.startsWith("<!")) {

				if (!trimmedLine.contains(StringPool.GREATER_THAN) &&
					!trimmedLine.contains(StringPool.SPACE)) {

					readAttributes = true;
				}
				else {
					line = _sortJSPAttributes(fileName, line, lineCount);
				}
			}

			if (!trimmedLine.contains(StringPool.DOUBLE_SLASH) &&
				!trimmedLine.startsWith(StringPool.STAR)) {

				while (trimmedLine.contains(StringPool.TAB)) {
					line = StringUtil.replaceLast(
						line, StringPool.TAB, StringPool.SPACE);

					trimmedLine = StringUtil.replaceLast(
						trimmedLine, StringPool.TAB, StringPool.SPACE);
				}

				while (trimmedLine.contains(StringPool.DOUBLE_SPACE) &&
					   !trimmedLine.contains(
						   StringPool.QUOTE + StringPool.DOUBLE_SPACE) &&
					   !fileName.endsWith(".vm")) {

					line = StringUtil.replaceLast(
						line, StringPool.DOUBLE_SPACE, StringPool.SPACE);

					trimmedLine = StringUtil.replaceLast(
						trimmedLine, StringPool.DOUBLE_SPACE, StringPool.SPACE);
				}
			}

			int x = line.indexOf("<%@ include file");

			if (x != -1) {
				x = line.indexOf(StringPool.QUOTE, x);

				int y = line.indexOf(StringPool.QUOTE, x + 1);

				if (y != -1) {
					String includeFileName = line.substring(x + 1, y);

					Matcher matcher = _jspIncludeFilePattern.matcher(
						includeFileName);

					if (!matcher.find()) {
						_sourceFormatterHelper.printError(
							fileName, "include: " + fileName + " " + lineCount);
					}
				}
			}

			line = _replacePrimitiveWrapperInstantiation(
				fileName, line, lineCount);

			previousLine = line;

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() -1);
		}

		content = _formatTaglibQuotes(fileName, content, StringPool.QUOTE);
		content = _formatTaglibQuotes(fileName, content, StringPool.APOSTROPHE);

		if (Validator.isNotNull(previousAttributeAndValue)) {
			content = StringUtil.replaceFirst(
				content,
				previousAttributeAndValue + "\n" + currentAttributeAndValue,
				currentAttributeAndValue + "\n" + previousAttributeAndValue);
		}

		return content;
	}

	private static void _formatPortletXML()
		throws DocumentException, IOException {

		String basedir = "./";

		if (_fileUtil.exists(basedir + "portal-impl")) {
			File file = new File(
				basedir + "portal-web/docroot/WEB-INF/portlet-custom.xml");

			String content = _fileUtil.read(file);

			String newContent = _formatPortletXML(content);

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				_sourceFormatterHelper.printError(file.toString(), file);
			}
		}
		else {
			DirectoryScanner directoryScanner = new DirectoryScanner();

			directoryScanner.setBasedir(basedir);
			directoryScanner.setIncludes(new String[] {"**\\portlet.xml"});

			List<String> fileNames = _sourceFormatterHelper.scanForFiles(
				directoryScanner);

			for (String fileName : fileNames) {
				File file = new File(basedir + fileName);

				String content = _fileUtil.read(file);

				String newContent = _formatPortletXML(content);

				if ((newContent != null) && !content.equals(newContent)) {
					_fileUtil.write(file, newContent);

					_sourceFormatterHelper.printError(fileName, file);
				}
			}
		}
	}

	private static String _formatPortletXML(String content)
		throws DocumentException, IOException {

		Document document = _saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		rootElement.sortAttributes(true);

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			portletElement.sortElementsByChildElement("init-param", "name");

			Element portletPreferencesElement = portletElement.element(
				"portlet-preferences");

			if (portletPreferencesElement != null) {
				portletPreferencesElement.sortElementsByChildElement(
					"preference", "name");
			}
		}

		return document.formattedString();
	}

	private static void _formatSH() throws IOException {
		_formatSH("ext/create.sh");
		_formatSH("hooks/create.sh");
		_formatSH("layouttpl/create.sh");
		_formatSH("portlets/create.sh");
		_formatSH("themes/create.sh");
	}

	private static void _formatSH(String fileName) throws IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			return;
		}

		String content = _fileUtil.read(new File(fileName), true);

		if (content.contains("\r")) {
			_sourceFormatterHelper.printError(
				fileName, "Invalid new line character");

			content = StringUtil.replace(content, "\r", "");

			_fileUtil.write(fileName, content);
		}
	}

	private static void _formatStrutsConfigXML()
		throws IOException, DocumentException {

		String basedir = "./";

		if (!_fileUtil.exists(basedir + "portal-impl")) {
			return;
		}

		String fileName = "portal-web/docroot/WEB-INF/struts-config.xml";

		File file = new File(basedir + fileName);

		String content = _fileUtil.read(file);

		Document document = _saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		Element actionMappingsElement = rootElement.element("action-mappings");

		List<Element> actionElements = actionMappingsElement.elements("action");

		String previousPath = StringPool.BLANK;

		for (Element actionElement : actionElements) {
			String path = actionElement.attributeValue("path");

			if (Validator.isNotNull(previousPath) &&
				(previousPath.compareTo(path) > 0) &&
				(!previousPath.startsWith("/portal/") ||
				 path.startsWith("/portal/"))) {

				_sourceFormatterHelper.printError(
					fileName, "sort: " + fileName + " " + path);
			}

			previousPath = path;
		}
	}

	private static String _formatTaglibQuotes(
		String fileName, String content, String quoteType) {

		String quoteFix = StringPool.APOSTROPHE;

		if (quoteFix.equals(quoteType)) {
			quoteFix = StringPool.QUOTE;
		}

		Pattern pattern = Pattern.compile(_getTaglibRegex(quoteType));

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int x = content.indexOf(quoteType + "<%=", matcher.start());
			int y = content.indexOf("%>" + quoteType, x);

			while ((x != -1) && (y != -1)) {
				String result = content.substring(x + 1, y + 2);

				if (result.contains(quoteType)) {
					int lineCount = 1;

					char contentCharArray[] = content.toCharArray();

					for (int i = 0; i < x; i++) {
						if (contentCharArray[i] == CharPool.NEW_LINE) {
							lineCount++;
						}
					}

					if (!result.contains(quoteFix)) {
						StringBundler sb = new StringBundler(5);

						sb.append(content.substring(0, x));
						sb.append(quoteFix);
						sb.append(result);
						sb.append(quoteFix);
						sb.append(content.substring(y + 3, content.length()));

						content = sb.toString();
					}
					else {
						_sourceFormatterHelper.printError(
							fileName, "taglib: " + fileName + " " + lineCount);
					}
				}

				x = content.indexOf(quoteType + "<%=", y);

				if (x > matcher.end()) {
					break;
				}

				y = content.indexOf("%>" + quoteType, x);
			}
		}

		return content;
	}

	private static void _formatTilesDefsXML()
		throws IOException, DocumentException {

		String basedir = "./";

		if (!_fileUtil.exists(basedir + "portal-impl")) {
			return;
		}

		String fileName = "portal-web/docroot/WEB-INF/tiles-defs.xml";

		File file = new File(basedir + fileName);

		String content = _fileUtil.read(file);

		Document document = _saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> definitionElements = rootElement.elements("definition");

		String previousName = StringPool.BLANK;

		for (Element definitionElement : definitionElements) {
			String name = definitionElement.attributeValue("name");

			if (Validator.isNotNull(previousName) &&
				(previousName.compareTo(name) > 0) &&
				!previousName.equals("portlet")) {

				_sourceFormatterHelper.printError(
					fileName, "sort: " + fileName + " " + name);

			}

			previousName = name;
		}
	}

	private static void _formatWebXML() throws IOException {
		String basedir = "./";

		if (_fileUtil.exists(basedir + "portal-impl")) {
			Properties properties = new Properties();

			String propertiesContent = _fileUtil.read(
				basedir + "portal-impl/src/portal.properties");

			PropertiesUtil.load(properties, propertiesContent);

			String[] locales = StringUtil.split(
				properties.getProperty(PropsKeys.LOCALES));

			Arrays.sort(locales);

			Set<String> urlPatterns = new TreeSet<String>();

			for (String locale : locales) {
				int pos = locale.indexOf(StringPool.UNDERLINE);

				String languageCode = locale.substring(0, pos);

				urlPatterns.add(languageCode);
				urlPatterns.add(locale);
			}

			StringBundler sb = new StringBundler();

			for (String urlPattern : urlPatterns) {
				sb.append("\t<servlet-mapping>\n");
				sb.append("\t\t<servlet-name>I18n Servlet</servlet-name>\n");
				sb.append(
					"\t\t<url-pattern>/" + urlPattern +"/*</url-pattern>\n");
				sb.append("\t</servlet-mapping>\n");
			}

			File file = new File(
				basedir + "portal-web/docroot/WEB-INF/web.xml");

			String content = _fileUtil.read(file);

			int x = content.indexOf("<servlet-mapping>");

			x = content.indexOf("<servlet-name>I18n Servlet</servlet-name>", x);

			x = content.lastIndexOf("<servlet-mapping>", x) - 1;

			int y = content.lastIndexOf(
				"<servlet-name>I18n Servlet</servlet-name>");

			y = content.indexOf("</servlet-mapping>", y) + 19;

			String newContent =
				content.substring(0, x) + sb.toString() + content.substring(y);

			x = newContent.indexOf("<security-constraint>");

			x = newContent.indexOf(
				"<web-resource-name>/c/portal/protected</web-resource-name>",
				x);

			x = newContent.indexOf("<url-pattern>", x) - 3;

			y = newContent.indexOf("<http-method>", x);

			y = newContent.lastIndexOf("</url-pattern>", y) + 15;

			sb = new StringBundler();

			sb.append("\t\t\t<url-pattern>/c/portal/protected</url-pattern>\n");

			for (String urlPattern : urlPatterns) {
				sb.append(
					"\t\t\t<url-pattern>/" + urlPattern +
						"/c/portal/protected</url-pattern>\n");
			}

			newContent =
				newContent.substring(0, x) + sb.toString() +
					newContent.substring(y);

			if ((newContent != null) && !content.equals(newContent)) {
				_fileUtil.write(file, newContent);

				System.out.println(file);
			}
		}
		else {
			String webXML = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/web.xml");

			DirectoryScanner directoryScanner = new DirectoryScanner();

			directoryScanner.setBasedir(basedir);
			directoryScanner.setIncludes(new String[] {"**\\web.xml"});

			List<String> fileNames = _sourceFormatterHelper.scanForFiles(
				directoryScanner);

			for (String fileName : fileNames) {
				String content = _fileUtil.read(basedir + fileName);

				if (content.equals(webXML)) {
					_sourceFormatterHelper.printError(fileName, fileName);
				}
			}
		}
	}

	private static String _getClassName(String line) {
		int pos = line.indexOf(" implements ");

		if (pos == -1) {
			pos = line.indexOf(" extends ");
		}

		if (pos == -1) {
			pos = line.indexOf(StringPool.OPEN_CURLY_BRACE);
		}

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		line = line.trim();

		pos = line.lastIndexOf(StringPool.SPACE);

		return line.substring(pos + 1);
	}

	private static String[] _getCombinedLines(
		String line, String previousLine, int lineTabCount,
		int previousLineTabCount) {

		if (Validator.isNull(previousLine)) {
			return null;
		}

		int previousLineLength = _getLineLength(previousLine);
		String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

		if ((line.length() + previousLineLength) < 80) {
			if (trimmedPreviousLine.startsWith("for ") &&
				previousLine.endsWith(StringPool.COLON) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return new String[] {previousLine + StringPool.SPACE + line};
			}

			if (previousLine.endsWith(StringPool.EQUAL) &&
				line.endsWith(StringPool.SEMICOLON)) {

				return new String[] {previousLine + StringPool.SPACE + line};
			}

			if ((trimmedPreviousLine.startsWith("if ") ||
				 trimmedPreviousLine.startsWith("else ")) &&
				(previousLine.endsWith("||") || previousLine.endsWith("&&")) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return new String[] {previousLine + StringPool.SPACE + line};
			}

			if (line.startsWith("throws ") &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return new String[] {previousLine + StringPool.SPACE + line};
			}
		}

		if (previousLine.endsWith(StringPool.EQUAL) &&
			line.endsWith(StringPool.SEMICOLON)) {

			String tempLine = line;

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

			int x = line.indexOf(StringPool.OPEN_PARENTHESIS);
			int y = line.indexOf(StringPool.CLOSE_PARENTHESIS);
			int z = line.indexOf(StringPool.QUOTE);

			if ((x > 0) && ((x + 1) != y) && ((z == -1) || (z > x))) {
				if ((line.charAt(x - 1) != CharPool.SPACE) &&
					(previousLineLength + 1 + x) < 80) {

					String addToPreviousLine = line.substring(0, x + 1);

					if (addToPreviousLine.contains(StringPool.SPACE)) {
						return null;
					}

					return new String[] {
						previousLine + StringPool.SPACE + addToPreviousLine,
						addToPreviousLine
					};
				}
			}
		}

		if (previousLine.endsWith(StringPool.COMMA) &&
			(previousLineTabCount == lineTabCount) &&
			!previousLine.contains(StringPool.CLOSE_CURLY_BRACE)) {

			int x = line.indexOf(StringPool.COMMA);

			if (x != -1) {
				while ((previousLineLength + 1 + x) < 80) {
					String addToPreviousLine = line.substring(0, x + 1);

					if (_isValidJavaParameter(addToPreviousLine)) {
						if (line.equals(addToPreviousLine)) {
							return new String[] {
								previousLine + StringPool.SPACE +
									addToPreviousLine
							};
						}
						else {
							return new String[] {
								previousLine + StringPool.SPACE +
									addToPreviousLine,
								addToPreviousLine + StringPool.SPACE
							};
						}
					}

					String partAfterComma = line.substring(x + 1);

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
					 (!line.startsWith("new ") ||
					  !line.endsWith(StringPool.OPEN_CURLY_BRACE)) &&
					 ((line.length() + previousLineLength) < 80)) {

				return new String[] {
					previousLine + StringPool.SPACE + line, StringPool.BLANK
				};
			}
		}

		if (!previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
			return null;
		}

		if ((line.length() + previousLineLength) > 80) {
			return null;
		}

		if (line.endsWith(StringPool.SEMICOLON)) {
			return new String[] {previousLine + line};
		}

		if ((line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			 line.endsWith(StringPool.CLOSE_PARENTHESIS)) &&
			(trimmedPreviousLine.startsWith("else ") ||
			 trimmedPreviousLine.startsWith("if ") ||
			 trimmedPreviousLine.startsWith("private ") ||
			 trimmedPreviousLine.startsWith("protected ") ||
			 trimmedPreviousLine.startsWith("public "))) {

			return new String[] {previousLine + line};
		}

		return null;
	}

	private static String _getConstructorOrMethodName(String line, int pos) {
		line = line.substring(0, pos);

		int x = line.lastIndexOf(StringPool.SPACE);

		return line.substring(x + 1);
	}

	private static String _getCopyright() throws IOException {
		String copyright = _fileUtil.read("copyright.txt");

		if (Validator.isNull(copyright)) {
			copyright = _fileUtil.read("../copyright.txt");
		}

		if (Validator.isNull(copyright)) {
			copyright = _fileUtil.read("../../copyright.txt");
		}

		return copyright;
	}

	private static String _getCustomCopyright(File file)
		throws IOException {

		String absolutePath = _fileUtil.getAbsolutePath(file);

		for (int x = absolutePath.length();;) {
			x = absolutePath.lastIndexOf(StringPool.SLASH, x);

			if (x == -1) {
				break;
			}

			String copyright = _fileUtil.read(
				absolutePath.substring(0, x + 1) + "copyright.txt");

			if (Validator.isNotNull(copyright)) {
				return copyright;
			}

			x = x - 1;
		}

		return null;
	}

	private static Properties _getExclusionsProperties(String fileName)
		throws IOException {

		Properties exclusionsProperties = new Properties();

		ClassLoader classLoader = SourceFormatter.class.getClassLoader();

		String sourceFormatterExclusions = System.getProperty(
			"source-formatter-exclusions",
			"com/liferay/portal/tools/dependencies/" + fileName);

		URL url = classLoader.getResource(sourceFormatterExclusions);

		if (url == null) {
			return null;
		}

		InputStream inputStream = url.openStream();

		exclusionsProperties.load(inputStream);

		inputStream.close();

		return exclusionsProperties;
	}

	private static Tuple _getJavaTermTuple(String line) {
		int pos = line.indexOf(StringPool.OPEN_PARENTHESIS);

		if (line.startsWith(StringPool.TAB + "public static final ") &&
			(line.endsWith(StringPool.SEMICOLON) ||
			 line.contains(StringPool.EQUAL))) {

			return new Tuple(
				_getVariableName(line), _TYPE_VARIABLE_PUBLIC_STATIC_FINAL);
		}
		else if (line.startsWith(StringPool.TAB + "public static ")) {
			if (line.endsWith(StringPool.SEMICOLON) ||
				line.contains(StringPool.EQUAL)) {

				return new Tuple(
					_getVariableName(line), _TYPE_VARIABLE_PUBLIC_STATIC);
			}

			if (pos != -1) {
				return new Tuple(
					_getConstructorOrMethodName(line, pos),
					_TYPE_METHOD_PUBLIC_STATIC);
			}

			if (line.startsWith(StringPool.TAB + "public static class ")) {
				return new Tuple(
					_getClassName(line), _TYPE_CLASS_PUBLIC_STATIC);
			}
		}
		else if (line.startsWith(StringPool.TAB + "public ")) {
			if (line.contains(StringPool.EQUAL) ||
				(line.endsWith(StringPool.SEMICOLON) &&
				 !line.contains(StringPool.OPEN_PARENTHESIS))) {

				return new Tuple(_getVariableName(line), _TYPE_VARIABLE_PUBLIC);
			}

			if (pos != -1) {
				int spaceCount = StringUtil.count(
					line.substring(0, pos), StringPool.SPACE);

				if (spaceCount == 1) {
					return new Tuple(
						_getConstructorOrMethodName(line, pos),
						_TYPE_CONSTRUCTOR_PUBLIC);
				}

				if (spaceCount > 1) {
					return new Tuple(
						_getConstructorOrMethodName(line, pos),
						_TYPE_METHOD_PUBLIC);
				}
			}
			else if (line.startsWith(StringPool.TAB + "public class ")) {
				return new Tuple(_getClassName(line), _TYPE_CLASS_PUBLIC);
			}
		}
		else if (line.startsWith(StringPool.TAB + "protected static final ")) {
			if (line.endsWith(StringPool.SEMICOLON) ||
				line.contains(StringPool.EQUAL)) {

				return new Tuple(
					_getVariableName(line),
					_TYPE_VARIABLE_PROTECTED_STATIC_FINAL);
			}
		}
		else if (line.startsWith(StringPool.TAB + "protected static ")) {
			if (line.endsWith(StringPool.SEMICOLON) ||
				line.contains(StringPool.EQUAL)) {

				return new Tuple(
					_getVariableName(line), _TYPE_VARIABLE_PROTECTED_STATIC);
			}

			if (pos != -1) {
				return new Tuple(
					_getConstructorOrMethodName(line, pos),
					_TYPE_METHOD_PROTECTED_STATIC);
			}
		}
		else if (line.startsWith(StringPool.TAB + "protected ")) {
			if ((pos != -1) && !line.contains(StringPool.EQUAL)) {
				int spaceCount = StringUtil.count(
					line.substring(0, pos), StringPool.SPACE);

				if (spaceCount == 1) {
					return new Tuple(
						_getConstructorOrMethodName(line, pos),
						_TYPE_CONSTRUCTOR_PROTECTED);
				}

				if (spaceCount > 1) {
					return new Tuple(
						_getConstructorOrMethodName(line, pos),
						_TYPE_METHOD_PROTECTED);
				}
			}

			return new Tuple(_getVariableName(line), _TYPE_VARIABLE_PROTECTED);
		}
		else if (line.startsWith(StringPool.TAB + "private static final ")) {
			if (line.endsWith(StringPool.SEMICOLON) ||
				line.contains(StringPool.EQUAL)) {

				return new Tuple(
					_getVariableName(line),
					_TYPE_VARIABLE_PRIVATE_STATIC_FINAL);
			}
		}
		else if (line.startsWith(StringPool.TAB + "private static ")) {
			if (line.endsWith(StringPool.SEMICOLON) ||
				line.contains(StringPool.EQUAL)) {

				return new Tuple(
					_getVariableName(line), _TYPE_VARIABLE_PRIVATE_STATIC);
			}

			if (pos != -1) {
				return new Tuple(
					_getConstructorOrMethodName(line, pos),
					_TYPE_METHOD_PRIVATE_STATIC);
			}

			if (line.startsWith(StringPool.TAB + "private static class ")) {
				return new Tuple(
					_getClassName(line), _TYPE_CLASS_PRIVATE_STATIC);
			}
		}
		else if (line.startsWith(StringPool.TAB + "private ")) {
			if (line.endsWith(StringPool.SEMICOLON) ||
				line.contains(StringPool.EQUAL)) {

				return new Tuple(
					_getVariableName(line), _TYPE_VARIABLE_PRIVATE);
			}

			if (pos != -1) {
				int spaceCount = StringUtil.count(
					line.substring(0, pos), StringPool.SPACE);

				if (spaceCount == 1) {
					return new Tuple(
						_getConstructorOrMethodName(line, pos),
						_TYPE_CONSTRUCTOR_PRIVATE);
				}

				if (spaceCount > 1) {
					return new Tuple(
						_getConstructorOrMethodName(line, pos),
						_TYPE_METHOD_PRIVATE);
				}
			}
			else if (line.startsWith(StringPool.TAB + "private class ")) {
				return new Tuple(_getClassName(line), _TYPE_CLASS_PRIVATE);
			}
		}

		return null;
	}

	private static List<String> _getJSPDuplicateImports(
		String fileName, String content, List<String> importLines) {

		List<String> duplicateImports = new ArrayList<String>();

		for (String importLine : importLines) {
			int x = content.indexOf("<%@ include file=");

			if (x == -1) {
				continue;
			}

			int y = content.indexOf("<%@ page import=");

			if (y == -1) {
				continue;
			}

			if ((x < y) && _isJSPDuplicateImport(fileName, importLine, false)) {
				duplicateImports.add(importLine);
			}
		}

		return duplicateImports;
	}

	private static int _getLineLength(String line) {
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

	private static String _getOldCopyright() throws IOException {
		String copyright = _fileUtil.read("old-copyright.txt");

		if (Validator.isNull(copyright)) {
			copyright = _fileUtil.read("../old-copyright.txt");
		}

		if (Validator.isNull(copyright)) {
			copyright = _fileUtil.read("../../old-copyright.txt");
		}

		return copyright;
	}

	private static Collection<String> _getPluginJavaFiles() {
		String basedir = "./";

		Collection<String> fileNames = new TreeSet<String>();

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		String[] excludes = {
			"**\\bin\\**", "**\\model\\*Clp.java",
			"**\\model\\impl\\*BaseImpl.java", "**\\model\\impl\\*Model.java",
			"**\\model\\impl\\*ModelImpl.java",
			"**\\service\\**\\model\\*Model.java",
			"**\\service\\**\\model\\*Soap.java",
			"**\\service\\**\\model\\*Wrapper.java",
			"**\\service\\**\\service\\*Service.java",
			"**\\service\\**\\service\\*ServiceClp.java",
			"**\\service\\**\\service\\*ServiceFactory.java",
			"**\\service\\**\\service\\*ServiceUtil.java",
			"**\\service\\**\\service\\*ServiceWrapper.java",
			"**\\service\\**\\service\\ClpSerializer.java",
			"**\\service\\**\\service\\messaging\\*ClpMessageListener.java",
			"**\\service\\**\\service\\persistence\\*Finder.java",
			"**\\service\\**\\service\\persistence\\*Persistence.java",
			"**\\service\\**\\service\\persistence\\*Util.java",
			"**\\service\\base\\*ServiceBaseImpl.java",
			"**\\service\\http\\*JSONSerializer.java",
			"**\\service\\http\\*ServiceHttp.java",
			"**\\service\\http\\*ServiceJSON.java",
			"**\\service\\http\\*ServiceSoap.java",
			"**\\service\\persistence\\*PersistenceImpl.java", "**\\tmp\\**"
		};

		excludes = ArrayUtil.append(excludes, _excludes);

		directoryScanner.setExcludes(excludes);

		directoryScanner.setIncludes(new String[] {"**\\*.java"});

		fileNames.addAll(_sourceFormatterHelper.scanForFiles(directoryScanner));

		return fileNames;
	}

	private static Collection<String> _getPortalJavaFiles() {
		String basedir = "./";

		Collection<String> fileNames = new TreeSet<String>();

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		String[] excludes = {
			"**\\*_IW.java", "**\\PropsValues.java", "**\\bin\\**",
			"**\\classes\\*", "**\\counter\\service\\**", "**\\jsp\\*",
			"**\\model\\impl\\*BaseImpl.java", "**\\model\\impl\\*Model.java",
			"**\\model\\impl\\*ModelImpl.java", "**\\portal\\service\\**",
			"**\\portal-client\\**",
			"**\\portal-service\\**\\model\\*Model.java",
			"**\\portal-service\\**\\model\\*Soap.java",
			"**\\portal-service\\**\\model\\*Wrapper.java",
			"**\\portal-web\\classes\\**\\*.java",
			"**\\portal-web\\test\\**\\*Test.java",
			"**\\portal-web\\test\\**\\*Tests.java",
			"**\\portlet\\**\\service\\**", "**\\tmp\\**", "**\\tools\\tck\\**"
		};

		excludes = ArrayUtil.append(excludes, _excludes);

		directoryScanner.setExcludes(excludes);

		directoryScanner.setIncludes(new String[] {"**\\*.java"});

		fileNames.addAll(_sourceFormatterHelper.scanForFiles(directoryScanner));

		directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		excludes = new String[] {
			"**\\bin\\**", "**\\portal-client\\**", "**\\tools\\ext_tmpl\\**",
			"**\\*_IW.java", "**\\test\\**\\*PersistenceTest.java"
		};

		excludes = ArrayUtil.append(excludes, _excludes);

		directoryScanner.setExcludes(excludes);

		directoryScanner.setIncludes(
			new String[] {
				"**\\com\\liferay\\portal\\service\\ServiceContext*.java",
				"**\\model\\BaseModel.java",
				"**\\model\\impl\\BaseModelImpl.java",
				"**\\service\\PersistedModelLocalService*.java",
				"**\\service\\base\\PrincipalBean.java",
				"**\\service\\http\\*HttpTest.java",
				"**\\service\\http\\*SoapTest.java",
				"**\\service\\http\\TunnelUtil.java",
				"**\\service\\impl\\*.java", "**\\service\\jms\\*.java",
				"**\\service\\permission\\*.java",
				"**\\service\\persistence\\BasePersistence.java",
				"**\\service\\persistence\\BatchSession*.java",
				"**\\service\\persistence\\*FinderImpl.java",
				"**\\service\\persistence\\*Query.java",
				"**\\service\\persistence\\impl\\BasePersistenceImpl.java",
				"**\\portal-impl\\test\\**\\*.java",
				"**\\portal-service\\**\\liferay\\documentlibrary\\**.java",
				"**\\portal-service\\**\\liferay\\lock\\**.java",
				"**\\portal-service\\**\\liferay\\mail\\**.java",
				"**\\util-bridges\\**\\*.java"
			});

		fileNames.addAll(_sourceFormatterHelper.scanForFiles(directoryScanner));

		return fileNames;
	}

	private static String _getTaglibRegex(String quoteType) {
		StringBuilder sb = new StringBuilder();

		sb.append("<(");

		for (int i = 0; i < _TAG_LIBRARIES.length; i++) {
			sb.append(_TAG_LIBRARIES[i]);
			sb.append(StringPool.PIPE);
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append("):([^>]|%>)*");
		sb.append(quoteType);
		sb.append("<%=.*");
		sb.append(quoteType);
		sb.append(".*%>");
		sb.append(quoteType);
		sb.append("([^>]|%>)*>");

		return sb.toString();
	}

	private static String _getVariableName(String line) {
		int x = line.indexOf(StringPool.EQUAL);
		int y = line.lastIndexOf(StringPool.SPACE);

		if (x != -1) {
			line = line.substring(0, x);
			line = StringUtil.trim(line);

			y = line.lastIndexOf(StringPool.SPACE);

			return line.substring(y + 1);
		}

		if (line.endsWith(StringPool.SEMICOLON)) {
			return line.substring(y + 1, line.length() - 1);
		}

		return StringPool.BLANK;
	}

	private static boolean _isGenerated(String content) {
		if (content.contains("* @generated") || content.contains("$ANTLR")) {
			return true;
		}
		else {
			return false;
		}
	}

	private static boolean _isInJavaTermTypeGroup(
		int javaTermType, int[] javaTermTypeGroup) {

		for (int type : javaTermTypeGroup) {
			if (javaTermType == type) {
				return true;
			}
		}

		return false;
	}

	private static boolean _isJSPAttributName(String attributeName) {
		if (Validator.isNull(attributeName)) {
			return false;
		}

		Matcher matcher = _jspAttributeNamePattern.matcher(attributeName);

		return matcher.matches();
	}

	private static boolean _isJSPDuplicateImport(
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

		y = content.indexOf(StringPool.QUOTE, y);

		if (y == -1) {
			return false;
		}

		int z = content.indexOf(StringPool.QUOTE, y + 1);

		if (z == -1) {
			return false;
		}

		String includeFileName = content.substring(y + 1, z);

		String docrootPath = fileName.substring(
			0, fileName.indexOf("docroot") + 7);

		includeFileName = docrootPath + includeFileName;

		return _isJSPDuplicateImport(includeFileName, importLine, true);
	}

	private static boolean _isJSPImportRequired(
		String fileName, String className, Set<String> includeFileNames,
		Set<String> checkedFileNames) {

		if (checkedFileNames.contains(fileName)) {
			return false;
		}

		checkedFileNames.add(fileName);

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		Pattern pattern = Pattern.compile(
			"[^A-Za-z0-9_]" + className + "[^A-Za-z0-9_\"]");

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return true;
		}

		_addJSPIncludeFileNames(fileName, includeFileNames);

		String docrootPath = fileName.substring(
			0, fileName.indexOf("docroot") + 7);

		fileName = fileName.replaceFirst(docrootPath, StringPool.BLANK);

		if (fileName.endsWith("init.jsp") ||
			fileName.contains("init-ext.jsp")) {

			_addJSPReferenceFileNames(fileName, includeFileNames);
		}

		String[] includeFileNamesArray = includeFileNames.toArray(
			new String[includeFileNames.size()]);

		for (String includeFileName : includeFileNamesArray) {
			if (!checkedFileNames.contains(includeFileName) &&
				_isJSPImportRequired(
					includeFileName, className, includeFileNames,
					checkedFileNames)) {

				return true;
			}
		}

		return false;
	}

	private static boolean _isValidJavaParameter(String javaParameter) {
		int quoteCount = StringUtil.count(javaParameter, StringPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return false;
		}

		javaParameter = _stripQuotes(javaParameter);

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

	private static String _replacePrimitiveWrapperInstantiation(
		String fileName, String line, int lineCount) {

		if (true) {
			return line;
		}

		String newLine = StringUtil.replace(
			line,
			new String[] {
				"new Boolean(", "new Byte(", "new Character(", "new Integer(",
				"new Long(", "new Short("
			},
			new String[] {
				"Boolean.valueOf(", "Byte.valueOf(", "Character.valueOf(",
				"Integer.valueOf(", "Long.valueOf(", "Short.valueOf("
			});

		if (!line.equals(newLine)) {
			_sourceFormatterHelper.printError(
				fileName, "> new Primitive(: " + fileName + " " + lineCount);
		}

		return newLine;
	}

	private static String _sortJSPAttributes(
		String fileName, String line, int lineCount) {

		String s = line;

		int x = s.indexOf(StringPool.SPACE);

		if (x == -1) {
			return line;
		}

		s = s.substring(x + 1);

		String previousAttribute = null;
		String previousAttributeAndValue = null;

		boolean wrongOrder = false;

		for (x = 0;;) {
			x = s.indexOf(StringPool.EQUAL);

			if ((x == -1) || (s.length() <= (x + 1))) {
				return line;
			}

			String attribute = s.substring(0, x);

			if (!_isJSPAttributName(attribute)) {
				return line;
			}

			if (Validator.isNotNull(previousAttribute) &&
				(previousAttribute.compareTo(attribute) > 0)) {

				wrongOrder = true;
			}

			s = s.substring(x + 1);

			char delimeter = s.charAt(0);

			if ((delimeter != CharPool.APOSTROPHE) &&
				(delimeter != CharPool.QUOTE)) {

				_sourceFormatterHelper.printError(
					fileName, "delimeter: " + fileName + " " + lineCount);

				return line;
			}

			s = s.substring(1);

			int y = s.indexOf(delimeter);

			if ((y == -1) || (s.length() <= (y + 1))) {
				return line;
			}

			String value = s.substring(0, y);

			if (value.contains("<%") && !value.contains("%>")) {
				int z = s.indexOf("%>");

				if (z == -1) {
					return line;
				}

				y = s.substring(z).indexOf(delimeter);

				value = s.substring(0, y + z);
			}

			StringBundler sb = new StringBundler(5);

			sb.append(attribute);
			sb.append(StringPool.EQUAL);
			sb.append(delimeter);
			sb.append(value);
			sb.append(delimeter);

			String currentAttributeAndValue = sb.toString();

			if (wrongOrder) {
				if (line.contains(currentAttributeAndValue) &&
					line.contains(previousAttributeAndValue)) {

					line = StringUtil.replaceFirst(
						line, previousAttributeAndValue,
						currentAttributeAndValue);

					line = StringUtil.replaceLast(
						line, currentAttributeAndValue,
						previousAttributeAndValue);
				}

				return line;
			}

			s = s.substring(y + 1);

			s = StringUtil.trimLeading(s);

			previousAttribute = attribute;
			previousAttributeAndValue = currentAttributeAndValue;
		}
	}

	private static String _stripJSPImports(String fileName, String content)
		throws IOException {

		fileName = fileName.replace(
			CharPool.BACK_SLASH, CharPool.FORWARD_SLASH);

		if (!fileName.contains("docroot") ||
			fileName.endsWith("init-ext.jsp")) {

			return content;
		}

		Matcher matcher = _jspImportPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String imports = matcher.group();

		imports = StringUtil.replace(
			imports, new String[] {"%><%@\r\n", "%><%@\n"},
			new String[] {"%>\r\n<%@ ", "%>\n<%@ "});

		if (!fileName.endsWith("html/common/init.jsp") &&
			!fileName.endsWith("html/portal/init.jsp")) {

			List<String> importLines = new ArrayList<String>();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(imports));

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.contains("import=")) {
					importLines.add(line);
				}
			}

			List<String> unneededImports = _getJSPDuplicateImports(
				fileName, content, importLines);

			_addJSPUnusedImports(fileName, importLines, unneededImports);

			for (String unneededImport : unneededImports) {
				imports = StringUtil.replace(
					imports, unneededImport, StringPool.BLANK);
			}
		}

		imports = _formatImports(imports, 17);

		String beforeImports = content.substring(0, matcher.start());

		if (Validator.isNull(imports)) {
			beforeImports = StringUtil.replaceLast(
				beforeImports, "\n", StringPool.BLANK);
		}

		String afterImports = content.substring(matcher.end());

		if (Validator.isNull(afterImports)) {
			imports = StringUtil.replaceLast(imports, "\n", StringPool.BLANK);

			content = beforeImports + imports;

			return content;
		}

		content = beforeImports + imports + "\n" + afterImports;

		return content;
	}

	private static String _stripQuotes(String s) {
		String[] parts = StringUtil.split(s, CharPool.QUOTE);

		int i = 1;

		while (i < parts.length) {
			s = StringUtil.replaceFirst(
				s, StringPool.QUOTE + parts[i] + StringPool.QUOTE,
				StringPool.BLANK);

			i = i + 2;
		}

		return s;
	}

	private static final String[] _TAG_LIBRARIES = new String[] {
		"aui", "c", "html", "jsp", "liferay-portlet", "liferay-security",
		"liferay-theme", "liferay-ui", "liferay-util", "portlet", "struts",
		"tiles"
	};

	private static final int _TYPE_CLASS_PRIVATE = 22;

	private static final int _TYPE_CLASS_PRIVATE_STATIC = 21;

	private static final int _TYPE_CLASS_PUBLIC = 8;

	private static final int _TYPE_CLASS_PUBLIC_STATIC = 7;

	private static final int _TYPE_CONSTRUCTOR_PRIVATE = 16;

	private static final int _TYPE_CONSTRUCTOR_PROTECTED = 10;

	private static final int _TYPE_CONSTRUCTOR_PUBLIC = 5;

	private static final int[] _TYPE_METHOD = {
		SourceFormatter._TYPE_METHOD_PRIVATE,
		SourceFormatter._TYPE_METHOD_PRIVATE_STATIC,
		SourceFormatter._TYPE_METHOD_PROTECTED,
		SourceFormatter._TYPE_METHOD_PROTECTED_STATIC,
		SourceFormatter._TYPE_METHOD_PUBLIC,
		SourceFormatter._TYPE_METHOD_PUBLIC_STATIC
	};

	private static final int _TYPE_METHOD_PRIVATE = 17;

	private static final int _TYPE_METHOD_PRIVATE_STATIC = 15;

	private static final int _TYPE_METHOD_PROTECTED = 11;

	private static final int _TYPE_METHOD_PROTECTED_STATIC = 9;

	private static final int _TYPE_METHOD_PUBLIC = 6;

	private static final int _TYPE_METHOD_PUBLIC_STATIC = 4;

	private static final int[] _TYPE_VARIABLE_NOT_STATIC = {
		SourceFormatter._TYPE_VARIABLE_PRIVATE,
		SourceFormatter._TYPE_VARIABLE_PRIVATE_STATIC,
		SourceFormatter._TYPE_VARIABLE_PROTECTED,
		SourceFormatter._TYPE_VARIABLE_PROTECTED_STATIC,
		SourceFormatter._TYPE_VARIABLE_PUBLIC,
		SourceFormatter._TYPE_VARIABLE_PUBLIC_STATIC
	};

	private static final int _TYPE_VARIABLE_PRIVATE = 20;

	private static final int _TYPE_VARIABLE_PRIVATE_STATIC = 19;

	private static final int _TYPE_VARIABLE_PRIVATE_STATIC_FINAL = 18;

	private static final int _TYPE_VARIABLE_PROTECTED = 14;

	private static final int _TYPE_VARIABLE_PROTECTED_STATIC = 13;

	private static final int _TYPE_VARIABLE_PROTECTED_STATIC_FINAL = 12;

	private static final int _TYPE_VARIABLE_PUBLIC = 3;

	private static final int _TYPE_VARIABLE_PUBLIC_STATIC = 2;

	private static final int _TYPE_VARIABLE_PUBLIC_STATIC_FINAL = 1;

	private static String[] _excludes;
	private static FileImpl _fileUtil = FileImpl.getInstance();
	private static Pattern _javaImportPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*;\n+)+", Pattern.MULTILINE);
	private static Properties _javaTermAlphabetizeExclusionsProperties;
	private static Pattern _jspAttributeNamePattern = Pattern.compile(
		"[a-z]+[-_a-zA-Z0-9]*");
	private static Map<String, String> _jspContents =
		new HashMap<String, String>();
	private static Pattern _jspImportPattern = Pattern.compile(
		"(<.*\n*page.import=\".*>\n*)+", Pattern.MULTILINE);
	private static Pattern _jspIncludeFilePattern = Pattern.compile(
		"/.*[.]jsp[f]?");
	private static Properties _lineLengthExclusionsProperties;
	private static SAXReaderImpl _saxReaderUtil = SAXReaderImpl.getInstance();
	private static SourceFormatterHelper _sourceFormatterHelper;
	private static Pattern _xssPattern = Pattern.compile(
		"String\\s+([^\\s]+)\\s*=\\s*(Bean)?ParamUtil\\.getString\\(");

}