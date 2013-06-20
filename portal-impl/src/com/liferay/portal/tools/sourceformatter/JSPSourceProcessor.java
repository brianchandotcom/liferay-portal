/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPSourceProcessor extends BaseSourceProcessor {

	protected void addJSPIncludeFileNames(
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
				!includeFileName.endsWith("html/portlet/init.jsp") &&
				!includeFileName.endsWith("html/taglib/init.jsp") &&
				!includeFileNames.contains(includeFileName)) {

				includeFileNames.add(includeFileName);
			}

			x = y;
		}
	}

	protected void addJSPReferenceFileNames(
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

	protected void addJSPUnusedImports(
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

			if (!isClassOrVariableRequired(
					fileName, importLine, className, includeFileNames,
					checkedFileNames)) {

				unneededImports.add(importLine);
			}
		}
	}

	protected boolean checkTaglibVulnerability(
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

	protected void checkXSS(String fileName, String jspContent) {
		Matcher matcher = _xssPattern.matcher(jspContent);

		while (matcher.find()) {
			boolean xssVulnerable = false;

			String jspVariable = matcher.group(1);

			String anchorVulnerability = " href=\"<%= " + jspVariable + " %>";

			if (checkTaglibVulnerability(jspContent, anchorVulnerability)) {
				xssVulnerable = true;
			}

			String inputVulnerability = " value=\"<%= " + jspVariable + " %>";

			if (checkTaglibVulnerability(jspContent, inputVulnerability)) {
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
				processErrorMessage(
					fileName, "(xss): " + fileName + " (" + jspVariable + ")");
			}
		}
	}

	@Override
	protected void doFormat() throws Exception {
		String copyright = getCopyright();
		String oldCopyright = getOldCopyright();

		String[] excludes = new String[] {
			"**\\portal\\aui\\**", "**\\bin\\**", "**\\null.jsp", "**\\tmp\\**",
			"**\\tools\\**"
		};
		String[] includes = new String[] {
			"**\\*.jsp", "**\\*.jspf", "**\\*.vm"
		};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = fileUtil.read(file);

			_jspContents.put(fileName, content);
		}

		boolean stripJSPImports = true;

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = fileUtil.read(file);

			String oldContent = content;
			String newContent = StringPool.BLANK;

			for (;;) {
				newContent = formatJSP(fileName, oldContent);

				if (oldContent.equals(newContent)) {
					break;
				}

				oldContent = newContent;
			}

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"<br/>", "\"/>", "\" >", "@page import", "\"%>", ")%>",
					"else{", "for(", "function (", "if(", "javascript: ",
					"while(", "){\n"
				},
				new String[] {
					"<br />", "\" />", "\">", "@ page import", "\" %>", ") %>",
					"else {", "for (", "function(", "if (", "javascript:",
					"while (", ") {\n"
				});

			if (stripJSPImports) {
				try {
					newContent = stripJSPImports(fileName, newContent);
				}
				catch (RuntimeException re) {
					stripJSPImports = false;
				}
			}

			newContent = fixCopyright(
				newContent, copyright, oldCopyright, file, fileName);

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
				if (!fileName.matches(".*template.*\\.vm$")) {
					processErrorMessage(fileName, "tab: " + fileName);
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

			newContent = fixSessionKey(fileName, newContent, sessionKeyPattern);
			newContent = fixSessionKey(
				fileName, newContent, taglibSessionKeyPattern);

			checkLanguageKeys(fileName, newContent, languageKeyPattern);
			checkLanguageKeys(fileName, newContent, _taglibLanguageKeyPattern);
			checkXSS(fileName, newContent);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	protected String formatJSP(String fileName, String content)
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

		String currentException = null;
		String previousException = null;

		boolean hasUnsortedExceptions = false;

		boolean javaSource = false;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			if (!fileName.contains("jsonw") ||
				!fileName.endsWith("action.jsp")) {

				line = trimLine(line, false);
			}

			if (line.contains("<aui:button ") &&
				line.contains("type=\"button\"")) {

				processErrorMessage(
					fileName, "aui:button " + fileName + " " + lineCount);
			}

			String trimmedLine = StringUtil.trimLeading(line);
			String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

			if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
				javaSource = true;
			}
			else if (trimmedLine.equals("%>")) {
				javaSource = false;
			}

			if (javaSource && hasUnusedVariable(fileName, trimmedLine)) {
				processErrorMessage(
					fileName, "Unused varibale: " + fileName + " " + lineCount);
			}

			if (!trimmedLine.equals("%>") && line.contains("%>") &&
				!line.contains("--%>") && !line.contains(" %>")) {

				line = StringUtil.replace(line, "%>", " %>");
			}

			if (line.contains("<%=") && !line.contains("<%= ")) {
				line = StringUtil.replace(line, "<%=", "<%= ");
			}

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

				checkIfClauseParentheses(trimmedLine, fileName, lineCount);
			}

			if (readAttributes) {
				if (!trimmedLine.startsWith(StringPool.FORWARD_SLASH) &&
					!trimmedLine.startsWith(StringPool.GREATER_THAN)) {

					int pos = trimmedLine.indexOf(StringPool.EQUAL);

					if (pos != -1) {
						String attribute = trimmedLine.substring(0, pos);

						if (!trimmedLine.endsWith(StringPool.QUOTE) &&
							!trimmedLine.endsWith(StringPool.APOSTROPHE)) {

							processErrorMessage(
								fileName,
								"attribute: " + fileName + " " + lineCount);

							readAttributes = false;
						}
						else if (trimmedLine.endsWith(StringPool.APOSTROPHE) &&
								 !trimmedLine.contains(StringPool.QUOTE)) {

							line = StringUtil.replace(
								line, StringPool.APOSTROPHE, StringPool.QUOTE);

							readAttributes = false;
						}
						else if (Validator.isNotNull(previousAttribute)) {
							if (!isJSPAttributName(attribute)) {
								processErrorMessage(
									fileName,
									"attribute: " + fileName + " " + lineCount);

								readAttributes = false;
							}
							else if (Validator.isNull(
										previousAttributeAndValue) &&
									 (previousAttribute.compareTo(
										 attribute) > 0)) {

								previousAttributeAndValue = previousLine;
								currentAttributeAndValue = line;
							}
						}

						if (!readAttributes) {
							previousAttribute = null;
							previousAttributeAndValue = null;
						}
						else {
							previousAttribute = attribute;
						}
					}
				}
				else {
					previousAttribute = null;

					readAttributes = false;
				}
			}

			if (!hasUnsortedExceptions) {
				int i = line.indexOf("<liferay-ui:error exception=\"<%=");

				if (i != -1) {
					currentException = line.substring(i + 33);

					if (Validator.isNotNull(previousException) &&
						(previousException.compareTo(currentException) > 0)) {

						hasUnsortedExceptions = true;
					}
				}

				if (!hasUnsortedExceptions) {
					previousException = currentException;
					currentException = null;
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
					line = sortJSPAttributes(fileName, line, lineCount);
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

			if (!fileName.endsWith("/touch.jsp")) {
				int x = line.indexOf("<%@ include file");

				if (x != -1) {
					x = line.indexOf(StringPool.QUOTE, x);

					int y = line.indexOf(StringPool.QUOTE, x + 1);

					if (y != -1) {
						String includeFileName = line.substring(x + 1, y);

						Matcher matcher = _jspIncludeFilePattern.matcher(
							includeFileName);

						if (!matcher.find()) {
							processErrorMessage(
								fileName,
								"include: " + fileName + " " + lineCount);
						}
					}
				}
			}

			line = replacePrimitiveWrapperInstantiation(
				fileName, line, lineCount);

			previousLine = line;

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		content = formatTaglibQuotes(fileName, content, StringPool.QUOTE);
		content = formatTaglibQuotes(fileName, content, StringPool.APOSTROPHE);

		if (Validator.isNotNull(previousAttributeAndValue)) {
			content = StringUtil.replaceFirst(
				content,
				previousAttributeAndValue + "\n" + currentAttributeAndValue,
				currentAttributeAndValue + "\n" + previousAttributeAndValue);
		}

		if (hasUnsortedExceptions) {
			if ((StringUtil.count(content, currentException) > 1) ||
				(StringUtil.count(content, previousException) > 1)) {

				processErrorMessage(
					fileName, "unsorted exceptions: " + fileName);
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

	protected String formatTaglibQuotes(
		String fileName, String content, String quoteType) {

		String quoteFix = StringPool.APOSTROPHE;

		if (quoteFix.equals(quoteType)) {
			quoteFix = StringPool.QUOTE;
		}

		Pattern pattern = Pattern.compile(getTaglibRegex(quoteType));

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int x = content.indexOf(quoteType + "<%=", matcher.start());
			int y = content.indexOf("%>" + quoteType, x);

			while ((x != -1) && (y != -1)) {
				String result = content.substring(x + 1, y + 2);

				if (result.contains(quoteType)) {
					int lineCount = 1;

					char[] contentCharArray = content.toCharArray();

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
						processErrorMessage(
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

	protected List<String> getJSPDuplicateImports(
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

			if ((x < y) && isJSPDuplicateImport(fileName, importLine, false)) {
				duplicateImports.add(importLine);
			}
		}

		return duplicateImports;
	}

	protected String getTaglibRegex(String quoteType) {
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

	protected String getVariableName(String line) {
		if (!line.endsWith(";") || line.startsWith("//")) {
			return null;
		}

		String variableName = null;

		int x = line.indexOf(" = ");

		if (x == -1) {
			int y = line.lastIndexOf(" ");

			if (y != -1) {
				variableName = line.substring(y + 1, line.length() - 1);
			}
		}
		else {
			line = line.substring(0, x);

			int y = line.lastIndexOf(" ");

			if (y != -1) {
				variableName = line.substring(y + 1);
			}
		}

		if (Validator.isVariableName(variableName)) {
			return variableName;
		}

		return null;
	}

	protected boolean hasUnusedVariable(String fileName, String line) {
		if (line.contains(": ")) {
			return false;
		}

		String variableName = getVariableName(line);

		if (Validator.isNull(variableName) || variableName.equals("false") ||
			variableName.equals("true")) {

			return false;
		}

		Set<String> includeFileNames = new HashSet<String>();

		includeFileNames.add(fileName);

		Set<String> checkedFileNames = new HashSet<String>();

		return !isClassOrVariableRequired(
			fileName, line, variableName, includeFileNames, checkedFileNames);
	}

	protected boolean isClassOrVariableRequired(
		String fileName, String line, String name, Set<String> includeFileNames,
		Set<String> checkedFileNames) {

		if (checkedFileNames.contains(fileName)) {
			return false;
		}

		checkedFileNames.add(fileName);

		String content = _jspContents.get(fileName);

		content = StringUtil.replace(content, line, StringPool.BLANK);

		if (Validator.isNull(content)) {
			return false;
		}

		Pattern pattern = Pattern.compile(
			"[^A-Za-z0-9_]" + name + "[^A-Za-z0-9_]");

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return true;
		}

		addJSPIncludeFileNames(fileName, includeFileNames);

		String docrootPath = fileName.substring(
			0, fileName.indexOf("docroot") + 7);

		fileName = fileName.replaceFirst(docrootPath, StringPool.BLANK);

		if (fileName.endsWith("init.jsp") ||
			fileName.contains("init-ext.jsp")) {

			addJSPReferenceFileNames(fileName, includeFileNames);
		}

		String[] includeFileNamesArray = includeFileNames.toArray(
			new String[includeFileNames.size()]);

		for (String includeFileName : includeFileNamesArray) {
			if (!checkedFileNames.contains(includeFileName) &&
				isClassOrVariableRequired(
					includeFileName, line, name, includeFileNames,
					checkedFileNames)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isJSPAttributName(String attributeName) {
		if (Validator.isNull(attributeName)) {
			return false;
		}

		Matcher matcher = _jspAttributeNamePattern.matcher(attributeName);

		return matcher.matches();
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

		return isJSPDuplicateImport(includeFileName, importLine, true);
	}

	protected String sortJSPAttributes(
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

			if (!isJSPAttributName(attribute)) {
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

				processErrorMessage(
					fileName, "delimeter: " + fileName + " " + lineCount);

				return line;
			}

			s = s.substring(1);

			String value = null;

			int y = -1;

			for (;;) {
				y = s.indexOf(delimeter, y + 1);

				if ((y == -1) || (s.length() <= (y + 1))) {
					return line;
				}

				value = s.substring(0, y);

				if (value.startsWith("<%")) {
					int endJavaCodeSignCount = StringUtil.count(value, "%>");
					int startJavaCodeSignCount = StringUtil.count(value, "<%");

					if (endJavaCodeSignCount == startJavaCodeSignCount) {
						break;
					}
				}
				else {
					int greaterThanCount = StringUtil.count(
						value, StringPool.GREATER_THAN);
					int lessThanCount = StringUtil.count(
						value, StringPool.LESS_THAN);

					if (greaterThanCount == lessThanCount) {
						break;
					}
				}
			}

			if ((delimeter == CharPool.APOSTROPHE) &&
				!value.contains(StringPool.QUOTE)) {

				return StringUtil.replace(
					line, StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
					StringPool.QUOTE + value + StringPool.QUOTE);
			}

			StringBundler sb = new StringBundler(5);

			sb.append(attribute);
			sb.append(StringPool.EQUAL);
			sb.append(delimeter);
			sb.append(value);
			sb.append(delimeter);

			String currentAttributeAndValue = sb.toString();

			if (wrongOrder) {
				if ((StringUtil.count(line, currentAttributeAndValue) == 1) &&
					(StringUtil.count(line, previousAttributeAndValue) == 1)) {

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

	protected String stripJSPImports(String fileName, String content)
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

			List<String> unneededImports = getJSPDuplicateImports(
				fileName, content, importLines);

			addJSPUnusedImports(fileName, importLines, unneededImports);

			for (String unneededImport : unneededImports) {
				imports = StringUtil.replace(
					imports, unneededImport, StringPool.BLANK);
			}
		}

		imports = formatImports(imports, 17);

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

	private static final String[] _TAG_LIBRARIES = new String[] {
		"aui", "c", "html", "jsp", "liferay-portlet", "liferay-security",
		"liferay-theme", "liferay-ui", "liferay-util", "portlet", "struts",
		"tiles"
	};

	private Pattern _jspAttributeNamePattern = Pattern.compile(
		"[a-z]+[-_a-zA-Z0-9]*");
	private Map<String, String> _jspContents = new HashMap<String, String>();
	private Pattern _jspImportPattern = Pattern.compile(
		"(<.*\n*page.import=\".*>\n*)+", Pattern.MULTILINE);
	private Pattern _jspIncludeFilePattern = Pattern.compile("/.*[.]jsp[f]?");
	private Pattern _taglibLanguageKeyPattern = Pattern.compile(
		"(?:confirmation|label|(?:M|m)essage|message key|names|title)=\"[^A-Z" +
			"<=%\\[\\s]+\"");
	private Pattern _xssPattern = Pattern.compile(
		"\\s+([^\\s]+)\\s*=\\s*(Bean)?ParamUtil\\.getString\\(");

}