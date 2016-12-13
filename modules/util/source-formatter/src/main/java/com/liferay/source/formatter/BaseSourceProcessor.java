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
import com.liferay.portal.kernel.nio.charset.CharsetDecoderUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.xml.SAXReaderFactory;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.apache.tools.ant.types.selectors.SelectorUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public abstract class BaseSourceProcessor implements SourceProcessor {

	public static final int PLUGINS_MAX_DIR_LEVEL =
		ToolsUtil.PLUGINS_MAX_DIR_LEVEL;

	public static final int PORTAL_MAX_DIR_LEVEL =
		ToolsUtil.PORTAL_MAX_DIR_LEVEL;

	@Override
	public final void format() throws Exception {
		preFormat();

		List<String> fileNames = getFileNames();

		ExecutorService executorService = Executors.newFixedThreadPool(
			sourceFormatterArgs.getProcessorThreadCount());

		List<Future<Void>> futures = new ArrayList<>(fileNames.size());

		for (final String fileName : fileNames) {
			Future<Void> future = executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						try {
							format(fileName);

							return null;
						}
						catch (Exception e) {
							throw new RuntimeException(
								"Unable to format " + fileName, e);
						}
					}

				});

			futures.add(future);
		}

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdown();

		postFormat();

		_sourceFormatterHelper.close();
	}

	public final List<String> getFileNames() throws Exception {
		List<String> fileNames = sourceFormatterArgs.getFileNames();

		if (fileNames != null) {
			return fileNames;
		}

		return doGetFileNames();
	}

	@Override
	public SourceMismatchException getFirstSourceMismatchException() {
		return _firstSourceMismatchException;
	}

	@Override
	public List<String> getModifiedFileNames() {
		return _modifiedFileNames;
	}

	@Override
	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		Set<SourceFormatterMessage> sourceFormatterMessages =
			new TreeSet<>();

		for (Map.Entry<String, Set<SourceFormatterMessage>> entry :
				_sourceFormatterMessagesMap.entrySet()) {

			sourceFormatterMessages.addAll(entry.getValue());
		}

		return sourceFormatterMessages;
	}

	@Override
	public void processMessage(String fileName, String message) {
		processMessage(fileName, message, -1);
	}

	@Override
	public void processMessage(String fileName, String message, int lineCount) {
		Set<SourceFormatterMessage> sourceFormatterMessages =
			_sourceFormatterMessagesMap.get(fileName);

		if (sourceFormatterMessages == null) {
			sourceFormatterMessages = new TreeSet<>();
		}

		sourceFormatterMessages.add(
			new SourceFormatterMessage(fileName, message, lineCount));

		_sourceFormatterMessagesMap.put(fileName, sourceFormatterMessages);
	}

	@Override
	public void setProperties(Properties properties) {
		_properties = properties;
	}

	@Override
	public void setSourceFormatterArgs(
		SourceFormatterArgs sourceFormatterArgs) {

		this.sourceFormatterArgs = sourceFormatterArgs;

		_init();
	}

	protected boolean addExtraEmptyLine(
		String previousLine, String line, boolean javaSource) {

		String trimmedLine = StringUtil.trimLeading(line);
		String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

		if (this instanceof JSPSourceProcessor) {
			if (trimmedPreviousLine.matches("(--)?%>") &&
				Validator.isNotNull(line) && !trimmedLine.equals("-->")) {

				return true;
			}

			if (Validator.isNotNull(previousLine) &&
				!trimmedPreviousLine.equals("<!--") &&
				trimmedLine.matches("<%(--)?")) {

				return true;
			}

			if (trimmedPreviousLine.equals("<%") &&
				trimmedLine.startsWith("//")) {

				return true;
			}

			if (trimmedPreviousLine.startsWith("//") &&
				trimmedLine.equals("%>")) {

				return true;
			}
		}

		if (!javaSource) {
			return false;
		}

		if (Validator.isNull(previousLine) || Validator.isNull(line) ||
			previousLine.contains("/*") || previousLine.endsWith("*/")) {

			return false;
		}

		if ((trimmedPreviousLine.startsWith("// ") &&
			 !trimmedLine.startsWith("// ")) ||
			(!trimmedPreviousLine.startsWith("// ") &&
			 trimmedLine.startsWith("// "))) {

			return true;
		}

		if (!trimmedPreviousLine.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			!trimmedPreviousLine.endsWith(StringPool.COLON) &&
			(trimmedLine.startsWith("for (") ||
			 trimmedLine.startsWith("if (") ||
			 trimmedLine.startsWith("try {"))) {

			return true;
		}

		if (previousLine.endsWith(
				StringPool.TAB + StringPool.CLOSE_CURLY_BRACE) &&
			!trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) &&
			!trimmedLine.startsWith(StringPool.CLOSE_PARENTHESIS) &&
			!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
			!trimmedLine.equals("*/") && !trimmedLine.startsWith("catch ") &&
			!trimmedLine.startsWith("else ") &&
			!trimmedLine.startsWith("finally ") &&
			!trimmedLine.startsWith("while ")) {

			return true;
		}

		return false;
	}

	protected int adjustLevel(int level, String text, String s, int diff) {
		String[] lines = StringUtil.splitLines(text);

		forLoop:
		for (String line : lines) {
			line = StringUtil.trim(line);

			if (line.startsWith("//")) {
				continue;
			}

			int x = -1;

			while (true) {
				x = line.indexOf(s, x + 1);

				if (x == -1) {
					continue forLoop;
				}

				if (!ToolsUtil.isInsideQuotes(line, x)) {
					level += diff;
				}
			}
		}

		return level;
	}

	protected void checkEmptyCollection(
		String line, String fileName, int lineCount) {

		// LPS-46028

		Matcher matcher = emptyCollectionPattern.matcher(line);

		if (matcher.find()) {
			String collectionType = TextFormatter.format(
				matcher.group(1), TextFormatter.J);

			processMessage(
				fileName, "Use Collections.empty" + collectionType + "()",
				lineCount);
		}
	}

	protected void checkGetterUtilGet(String fileName, String content)
		throws Exception {

		Matcher matcher = getterUtilGetPattern.matcher(content);

		while (matcher.find()) {
			List<String> parametersList = getParameterList(matcher.group());

			if (parametersList.size() != 2) {
				continue;
			}

			String defaultVariableName =
				"DEFAULT_" + StringUtil.toUpperCase(matcher.group(1));

			Field defaultValuefield = GetterUtil.class.getDeclaredField(
				defaultVariableName);

			String defaultValue = String.valueOf(defaultValuefield.get(null));

			String value = parametersList.get(1);

			if (value.equals("StringPool.BLANK")) {
				value = StringPool.BLANK;
			}

			if (Objects.equals(value, defaultValue)) {
				processMessage(
					fileName,
					"No need to pass default value '" + parametersList.get(1) +
						"'",
					getLineCount(content, matcher.start()));
			}
		}
	}

	protected void checkIfClauseParentheses(
		String ifClause, String fileName, int lineCount) {

		int quoteCount = StringUtil.count(ifClause, CharPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return;
		}

		ifClause = stripQuotes(ifClause);

		if (ifClause.contains(StringPool.DOUBLE_SLASH) ||
			ifClause.contains("/*") || ifClause.contains("*/")) {

			return;
		}

		if (hasRedundantParentheses(ifClause, "||", "&&") ||
			hasRedundantParentheses(ifClause, "&&", "||")) {

			processMessage(
				fileName, "Redundant parentheses in if-statement", lineCount);

			return;
		}

		int previousParenthesisPos = -1;

		for (int i = 0; i < ifClause.length(); i++) {
			char c = ifClause.charAt(i);

			if ((c != CharPool.OPEN_PARENTHESIS) &&
				(c != CharPool.CLOSE_PARENTHESIS)) {

				continue;
			}

			if (previousParenthesisPos != -1) {
				String s = ifClause.substring(previousParenthesisPos + 1, i);

				if (hasMissingParentheses(s)) {
					processMessage(
						fileName, "Missing parentheses in if-statement",
						lineCount);

					return;
				}
			}

			if (previousParenthesisPos == -1) {
				previousParenthesisPos = i;

				continue;
			}

			previousParenthesisPos = i;

			if (c != CharPool.OPEN_PARENTHESIS) {
				continue;
			}

			char previousChar = ifClause.charAt(i - 1);

			if ((previousChar != CharPool.OPEN_PARENTHESIS) &&
				(previousChar != CharPool.SPACE)) {

				continue;
			}

			int j = i;

			while (true) {
				j = ifClause.indexOf(StringPool.CLOSE_PARENTHESIS, j + 1);

				String s = ifClause.substring(i + 1, j);

				if (getLevel(s) == 0) {
					char nextChar = ifClause.charAt(j + 1);

					if (((previousChar == CharPool.OPEN_PARENTHESIS) &&
						 (nextChar == CharPool.CLOSE_PARENTHESIS)) ||
						(((nextChar == CharPool.CLOSE_PARENTHESIS) ||
						  (nextChar == CharPool.SPACE)) &&
						 hasRedundantParentheses(s))) {

						processMessage(
							fileName,
							"Redundant parentheses in if-statement",
							lineCount);
					}

					break;
				}
			}
		}
	}

	protected void checkInefficientStringMethods(
		String line, String fileName, int lineCount) {

		String methodName = "toLowerCase";

		int pos = line.indexOf(".toLowerCase()");

		if (pos == -1) {
			methodName = "toUpperCase";

			pos = line.indexOf(".toUpperCase()");
		}

		if ((pos == -1) && !line.contains("StringUtil.equalsIgnoreCase(")) {
			methodName = "equalsIgnoreCase";

			pos = line.indexOf(".equalsIgnoreCase(");
		}

		if (pos != -1) {
			processMessage(fileName, "Use StringUtil." + methodName, lineCount);
		}
	}

	protected void checkInefficientStringMethods(
		String line, String fileName, String absolutePath, int lineCount,
		boolean javaSource) {

		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {
			return;
		}

		if (javaSource) {
			checkInefficientStringMethods(line, fileName, lineCount);

			return;
		}

		Matcher matcher = javaSourceInsideJSPLinePattern.matcher(line);

		while (matcher.find()) {
			checkInefficientStringMethods(
				matcher.group(1), fileName, lineCount);
		}
	}

	protected void checkLanguageKeys(
			String fileName, String absolutePath, String content,
			Pattern pattern)
		throws Exception {

		String fileExtension = FilenameUtils.getExtension(fileName);

		if (!portalSource || fileExtension.equals("vm")) {
			return;
		}

		if (_portalLanguageProperties == null) {
			Properties portalLanguageProperties = new Properties();

			File portalLanguagePropertiesFile = getFile(
				"portal-impl/src/content/Language.properties",
				PORTAL_MAX_DIR_LEVEL);

			if (portalLanguagePropertiesFile != null) {
				InputStream inputStream = new FileInputStream(
					portalLanguagePropertiesFile);

				portalLanguageProperties.load(inputStream);
			}

			_portalLanguageProperties = portalLanguageProperties;
		}

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String[] languageKeys = getLanguageKeys(matcher);

			for (String languageKey : languageKeys) {
				if (Validator.isNumber(languageKey) ||
					languageKey.endsWith(StringPool.DASH) ||
					languageKey.endsWith(StringPool.OPEN_BRACKET) ||
					languageKey.endsWith(StringPool.PERIOD) ||
					languageKey.endsWith(StringPool.UNDERLINE) ||
					languageKey.startsWith(StringPool.DASH) ||
					languageKey.startsWith(StringPool.OPEN_BRACKET) ||
					languageKey.startsWith(StringPool.OPEN_CURLY_BRACE) ||
					languageKey.startsWith(StringPool.PERIOD) ||
					languageKey.startsWith(StringPool.UNDERLINE) ||
					_portalLanguageProperties.containsKey(languageKey)) {

					continue;
				}

				Properties moduleLanguageProperties =
					getModuleLanguageProperties(fileName);

				if ((moduleLanguageProperties != null) &&
					moduleLanguageProperties.containsKey(languageKey)) {

					continue;
				}

				Properties moduleLangLanguageProperties =
					getModuleLangLanguageProperties(absolutePath);

				if ((moduleLangLanguageProperties != null) &&
					moduleLangLanguageProperties.containsKey(languageKey)) {

					continue;
				}

				BNDSettings bndSettings = getBNDSettings(fileName);

				Properties bndFileLanguageProperties =
					bndSettings.getLanguageProperties();

				if ((bndFileLanguageProperties != null) &&
					!bndFileLanguageProperties.containsKey(languageKey)) {

					processMessage(
						fileName, "Missing language key '" + languageKey + "'");
				}

				putBNDSettings(bndSettings);
			}
		}
	}

	protected void checkOrder(
		String fileName, Element rootElement, String elementName,
		String parentElementName, ElementComparator elementComparator) {

		if (rootElement == null) {
			return;
		}

		List<Element> elements = rootElement.elements(elementName);

		Element previousElement = null;

		for (Element element : elements) {
			if ((previousElement != null) &&
				(elementComparator.compare(previousElement, element) > 0)) {

				StringBundler sb = new StringBundler(7);

				sb.append("Incorrect order '");
				sb.append(elementName);
				sb.append("':");

				if (Validator.isNotNull(parentElementName)) {
					sb.append(StringPool.SPACE);
					sb.append(parentElementName);
				}

				sb.append(StringPool.SPACE);
				sb.append(elementComparator.getElementName(element));

				processMessage(fileName, sb.toString());
			}

			previousElement = element;
		}
	}

	protected String checkPrincipalException(String content) {
		String newContent = content;

		Matcher matcher = principalExceptionPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String replacement = StringUtil.replace(
				match, "class.getName", "getNestedClasses");

			newContent = StringUtil.replace(newContent, match, replacement);
		}

		return newContent;
	}

	protected void checkPropertyUtils(String fileName, String content) {
		if (fileName.endsWith("TypeConvertorUtil.java")) {
			return;
		}

		if (content.contains("org.apache.commons.beanutils.PropertyUtils")) {
			processMessage(
				fileName,
				"Do not use org.apache.commons.beanutils.PropertyUtils, see " +
					"LPS-62786");
		}
	}

	protected void checkResourceUtil(
		String line, String fileName, String absolutePath, int lineCount) {

		if (!portalSource || fileName.endsWith("ResourceBundleUtil.java") ||
			isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {

			return;
		}

		if (line.contains("ResourceBundle.getBundle(")) {
			processMessage(
				fileName,
				"Use ResourceBundleUtil.getBundle instead of " +
					"ResourceBundle.getBundle, see LPS-58529",
				lineCount);
		}

		if (line.contains("resourceBundle.getString(")) {
			processMessage(
				fileName,
				"Use ResourceBundleUtil.getString instead of " +
					"resourceBundle.getString, see LPS-58529",
				lineCount);
		}
	}

	protected void checkStringUtilReplace(String fileName, String content)
		throws Exception {

		Matcher matcher = stringUtilReplacePattern.matcher(content);

		while (matcher.find()) {
			List<String> parametersList = getParameterList(matcher.group());

			if (parametersList.size() != 3) {
				return;
			}

			String secondParameter = parametersList.get(1);

			Matcher singleLengthMatcher = singleLengthStringPattern.matcher(
				secondParameter);

			if (!singleLengthMatcher.find()) {
				continue;
			}

			String fieldName = singleLengthMatcher.group(2);

			if (fieldName != null) {
				Field field = StringPool.class.getDeclaredField(fieldName);

				String value = (String)field.get(null);

				if (value.length() != 1) {
					continue;
				}
			}

			String method = matcher.group(1);

			StringBundler sb = new StringBundler(5);

			sb.append("Use StringUtil.");
			sb.append(method);
			sb.append("(String, char, char) or StringUtil.");
			sb.append(method);
			sb.append("(String, char, String) instead");

			processMessage(
				fileName, sb.toString(),
				getLineCount(content, matcher.start()));
		}
	}

	protected void checkUTF8(File file, String fileName) throws Exception {
		byte[] bytes = FileUtil.getBytes(file);

		try {
			CharsetDecoder charsetDecoder =
				CharsetDecoderUtil.getCharsetDecoder(
					StringPool.UTF8, CodingErrorAction.REPORT);

			charsetDecoder.decode(ByteBuffer.wrap(bytes));
		}
		catch (Exception e) {
			processMessage(fileName, "UTF-8");
		}
	}

	protected abstract String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception;

	protected abstract List<String> doGetFileNames() throws Exception;

	protected String fixCompatClassImports(String absolutePath, String content)
		throws Exception {

		if (portalSource || !_usePortalCompatImport ||
			absolutePath.contains("/ext-") ||
			absolutePath.contains("/portal-compat-shared/")) {

			return content;
		}

		Map<String, String> compatClassNamesMap = getCompatClassNamesMap();

		String newContent = content;

		for (Map.Entry<String, String> entry : compatClassNamesMap.entrySet()) {
			String compatClassName = entry.getKey();
			String extendedClassName = entry.getValue();

			Pattern pattern = Pattern.compile(extendedClassName + "\\W");

			while (true) {
				Matcher matcher = pattern.matcher(newContent);

				if (!matcher.find()) {
					break;
				}

				newContent =
					newContent.substring(0, matcher.start()) + compatClassName +
						newContent.substring(matcher.end() - 1);
			}
		}

		return newContent;
	}

	protected String fixCopyright(
			String content, String absolutePath, String fileName,
			String className)
		throws IOException {

		if (_copyright == null) {
			_copyright = getContent(
				sourceFormatterArgs.getCopyrightFileName(),
				PORTAL_MAX_DIR_LEVEL);
		}

		String copyright = _copyright;

		if (fileName.endsWith(".tpl") || fileName.endsWith(".vm") ||
			Validator.isNull(copyright)) {

			return content;
		}

		if (_oldCopyright == null) {
			_oldCopyright = getContent(
				"old-copyright.txt", PORTAL_MAX_DIR_LEVEL);
		}

		if (Validator.isNotNull(_oldCopyright) &&
			content.contains(_oldCopyright)) {

			content = StringUtil.replace(content, _oldCopyright, copyright);

			processMessage(fileName, "File contains old copyright information");
		}

		if (!content.contains(copyright)) {
			String customCopyright = getCustomCopyright(absolutePath);

			if (Validator.isNotNull(customCopyright)) {
				copyright = customCopyright;
			}

			if (!content.contains(copyright)) {
				processMessage(fileName, "(c)");
			}
			else if (!content.startsWith(copyright) &&
					 !content.startsWith("<%--\n" + copyright)) {

				processMessage(fileName, "File must start with copyright");
			}
		}
		else if (!content.startsWith(copyright) &&
				 !content.startsWith("<%--\n" + copyright)) {

			processMessage(fileName, "File must start with copyright");
		}

		if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf")) {
			content = StringUtil.replace(
				content, "<%\n" + copyright + "\n%>",
				"<%--\n" + copyright + "\n--%>");
		}

		int x = content.indexOf("* Copyright (c) 2000-");

		if (x == -1) {
			return content;
		}

		int y = content.indexOf("Liferay", x);

		String contentCopyrightYear = content.substring(x, y);

		x = copyright.indexOf("* Copyright (c) 2000-");

		if (x == -1) {
			return content;
		}

		y = copyright.indexOf("Liferay", x);

		String copyrightYear = copyright.substring(x, y);

		return StringUtil.replace(content, contentCopyrightYear, copyrightYear);
	}

	protected String fixEmptyLinesBetweenTags(String content) {
		Matcher matcher = _emptyLineBetweenTagsPattern.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(4);

			if (!tabs1.equals(tabs2)) {
				continue;
			}

			String lineBreaks = matcher.group(3);
			String tagName1 = matcher.group(2);
			String tagName2 = matcher.group(5);

			if (tagName1.endsWith(":when") ||
				(tagName1.matches("dd|dt|li|span|td|th|tr") &&
				 tagName2.matches("dd|dt|li|span|td|th|tr"))) {

				if (lineBreaks.equals("\n\n")) {
					return StringUtil.replaceFirst(
						content, "\n\n", "\n", matcher.end(1));
				}
			}
			else if (lineBreaks.equals("\n")) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end(1));
			}
		}

		return content;
	}

	protected String fixEmptyLinesInNestedTags(String content) {
		content = fixEmptyLinesInNestedTags(
			content, _emptyLineInNestedTagsPattern1, true);

		return fixEmptyLinesInNestedTags(
			content, _emptyLineInNestedTagsPattern2, false);
	}

	protected String fixEmptyLinesInNestedTags(
		String content, Pattern pattern, boolean startTag) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String tabs2 = null;

			if (startTag) {
				String secondLine = matcher.group(3);

				if (secondLine.equals("<%") || secondLine.startsWith("<%--") ||
					secondLine.startsWith("<!--")) {

					continue;
				}

				tabs2 = matcher.group(2);
			}
			else {
				String firstLine = matcher.group(2);

				if (firstLine.equals("%>")) {
					continue;
				}

				tabs2 = matcher.group(3);
			}

			String tabs1 = matcher.group(1);

			if ((startTag && ((tabs1.length() + 1) == tabs2.length())) ||
				(!startTag && ((tabs1.length() - 1) == tabs2.length()))) {

				content = StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.end(1));
			}
		}

		return content;
	}

	protected String fixIncorrectParameterTypeForLanguageUtil(
		String content, boolean autoFix, String fileName) {

		if (portalSource) {
			return content;
		}

		String expectedParameter = getProperty(
			"languageutil.expected.parameter");
		String incorrectParameter = getProperty(
			"languageutil.incorrect.parameter");

		if (!content.contains(
				"LanguageUtil.format(" + incorrectParameter + ", ") &&
			!content.contains(
				"LanguageUtil.get(" + incorrectParameter + ", ")) {

			return content;
		}

		if (autoFix) {
			content = StringUtil.replace(
				content,
				new String[] {
					"LanguageUtil.format(" + incorrectParameter + ", ",
					"LanguageUtil.get(" + incorrectParameter + ", "
				},
				new String[] {
					"LanguageUtil.format(" + expectedParameter + ", ",
					"LanguageUtil.get(" + expectedParameter + ", "
				});
		}
		else {
			processMessage(
				fileName,
				"(Unicode)LanguageUtil.format/get methods require " +
					expectedParameter + " parameter instead of " +
						incorrectParameter);
		}

		return content;
	}

	protected String fixSessionKey(
		String fileName, String content, Pattern pattern) {

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String newContent = content;

		do {
			String match = matcher.group();

			String s = null;

			if (pattern.equals(sessionKeyPattern)) {
				s = StringPool.COMMA;
			}
			else if (pattern.equals(taglibSessionKeyPattern)) {
				s = "key=";
			}

			int x = match.indexOf(s);

			if (x == -1) {
				continue;
			}

			x = x + s.length();

			String substring = match.substring(x).trim();

			String quote = StringPool.BLANK;

			if (substring.startsWith(StringPool.APOSTROPHE)) {
				quote = StringPool.APOSTROPHE;
			}
			else if (substring.startsWith(StringPool.QUOTE)) {
				quote = StringPool.QUOTE;
			}
			else {
				continue;
			}

			int y = match.indexOf(quote, x);
			int z = match.indexOf(quote, y + 1);

			if ((y == -1) || (z == -1)) {
				continue;
			}

			String prefix = match.substring(0, y + 1);
			String suffix = match.substring(z);
			String oldKey = match.substring(y + 1, z);

			boolean alphaNumericKey = true;

			for (char c : oldKey.toCharArray()) {
				if (!Validator.isChar(c) && !Validator.isDigit(c) &&
					(c != CharPool.DASH) && (c != CharPool.UNDERLINE)) {

					alphaNumericKey = false;
				}
			}

			if (!alphaNumericKey) {
				continue;
			}

			String newKey = TextFormatter.format(oldKey, TextFormatter.O);

			newKey = TextFormatter.format(newKey, TextFormatter.M);

			if (newKey.equals(oldKey)) {
				continue;
			}

			String oldSub = prefix.concat(oldKey).concat(suffix);
			String newSub = prefix.concat(newKey).concat(suffix);

			newContent = StringUtil.replaceFirst(newContent, oldSub, newSub);
		}
		while (matcher.find());

		return newContent;
	}

	protected String fixUnparameterizedClassType(String content) {
		Matcher matcher = unparameterizedClassTypePattern1.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "Class", "Class<?>", matcher.start());
		}

		matcher = unparameterizedClassTypePattern2.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "Class", "Class<?>", matcher.start());
		}

		return content;
	}

	protected final String format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		_sourceFormatterMessagesMap.remove(fileName);

		checkUTF8(file, fileName);

		if (!(this instanceof JavaSourceProcessor) &&
			absolutePath.matches(".*\\/modules\\/.*\\/src\\/.*\\/java\\/.*")) {

			processMessage(
				fileName, "Only *.java files are allowed in /src/*/java/");
		}

		String newContent = doFormat(file, fileName, absolutePath, content);

		newContent = StringUtil.replace(
			newContent, StringPool.RETURN, StringPool.BLANK);

		if (content.equals(newContent)) {
			return content;
		}

		return format(file, fileName, absolutePath, newContent);
	}

	protected final void format(String fileName) throws Exception {
		if (!_isMatchPath(fileName)) {
			return;
		}

		fileName = StringUtil.replace(
			fileName, CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = getAbsolutePath(fileName);

		File file = new File(absolutePath);

		String content = FileUtil.read(file);

		String newContent = format(file, fileName, absolutePath, content);

		processFormattedFile(file, fileName, content, newContent);
	}

	protected String formatAttributes(
			String fileName, String line, String tag, int lineCount,
			boolean escapeQuotes)
		throws Exception {

		String s = tag;

		int y = s.indexOf(CharPool.SPACE);

		if (y == -1) {
			return line;
		}

		String tagName = s.substring(1, y);

		s = s.substring(y + 1);

		String previousAttribute = null;
		String previousAttributeAndValue = null;

		boolean wrongOrder = false;

		for (int x = 0;;) {
			x = s.indexOf(CharPool.EQUAL);

			if ((x == -1) || (s.length() <= (x + 1))) {
				return line;
			}

			String attribute = s.substring(0, x);

			String trimmedAttribute = StringUtil.trim(attribute);

			if (!isAttributName(trimmedAttribute)) {
				return line;
			}

			if (!attribute.equals(trimmedAttribute)) {
				return StringUtil.replace(
					line, attribute + "=", trimmedAttribute + "=");
			}

			if (Validator.isNotNull(previousAttribute) &&
				(previousAttribute.compareToIgnoreCase(attribute) > 0)) {

				wrongOrder = true;
			}

			s = s.substring(x + 1);

			char delimeter = s.charAt(0);

			if ((delimeter != CharPool.APOSTROPHE) &&
				(delimeter != CharPool.QUOTE)) {

				if (delimeter == CharPool.SPACE) {
					return StringUtil.replace(
						line, attribute + "= ", attribute + "=");
				}

				if (delimeter != CharPool.AMPERSAND) {
					processMessage(
						fileName, "Incorrect delimeter '" + delimeter + "'",
						lineCount);
				}

				return line;
			}

			s = s.substring(1);

			String value = null;

			y = -1;

			while (true) {
				y = s.indexOf(delimeter, y + 1);

				if ((y == -1) || (s.length() <= (y + 1))) {
					return line;
				}

				value = s.substring(0, y);

				if (value.startsWith("<%")) {
					if (getLevel(value, "<%", "%>") == 0) {
						break;
					}
				}
				else if (getLevel(
							value, StringPool.LESS_THAN,
							StringPool.GREATER_THAN) ==
								0) {

					break;
				}
			}

			if (delimeter == CharPool.APOSTROPHE) {
				if (escapeQuotes) {
					String newValue = StringUtil.replace(
						value, StringPool.QUOTE, "&quot;");

					return StringUtil.replace(
						line,
						StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
						StringPool.QUOTE + newValue + StringPool.QUOTE);
				}

				if (!value.contains(StringPool.QUOTE) ||
					!tagName.contains(StringPool.COLON)) {

					return StringUtil.replace(
						line,
						StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
						StringPool.QUOTE + value + StringPool.QUOTE);
				}
			}

			if ((delimeter == CharPool.QUOTE) &&
				value.contains(StringPool.QUOTE) &&
				tagName.contains(StringPool.COLON)) {

				return StringUtil.replace(
					line, StringPool.QUOTE + value + StringPool.QUOTE,
					StringPool.APOSTROPHE + value + StringPool.APOSTROPHE);
			}

			StringBundler sb = new StringBundler(5);

			sb.append(attribute);
			sb.append(StringPool.EQUAL);
			sb.append(delimeter);
			sb.append(value);
			sb.append(delimeter);

			String currentAttributeAndValue = sb.toString();

			if (!tagName.contains(StringPool.COLON)) {
				String newLine = sortHTMLAttributes(
					line, value, currentAttributeAndValue);

				if (!newLine.equals(line)) {
					return newLine;
				}
			}

			String newLine = formatTagAttributeType(
				line, tagName, currentAttributeAndValue);

			if (!newLine.equals(line)) {
				return newLine;
			}

			if (wrongOrder) {
				if ((StringUtil.count(line, currentAttributeAndValue) == 1) &&
					(StringUtil.count(line, previousAttributeAndValue) == 1)) {

					line = StringUtil.replaceFirst(
						line, previousAttributeAndValue,
						currentAttributeAndValue);

					return StringUtil.replaceLast(
						line, currentAttributeAndValue,
						previousAttributeAndValue);
				}

				return line;
			}

			s = s.substring(y + 1);

			if (s.startsWith(StringPool.GREATER_THAN)) {
				return line;
			}

			s = StringUtil.trimLeading(s);

			previousAttribute = attribute;
			previousAttributeAndValue = currentAttributeAndValue;
		}
	}

	protected String formatDefinitionKey(
		String fileName, String content, String definitionKey) {

		return content;
	}

	protected String formatEmptyArray(String line) {
		Matcher matcher = emptyArrayPattern.matcher(line);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(line, matcher.end(1))) {
				continue;
			}

			String replacement = StringUtil.replace(
				matcher.group(1), "[]", "[0]");

			return StringUtil.replaceFirst(
				line, matcher.group(), replacement, matcher.start());
		}

		return line;
	}

	protected String formatIncorrectSyntax(String line, String regex) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(line);

		if (!matcher.find()) {
			return line;
		}

		if (ToolsUtil.isInsideQuotes(line, matcher.start(1))) {
			return line;
		}

		String whitespace = matcher.group(2);

		if (whitespace.length() > 0) {
			return line;
		}

		return line.substring(0, matcher.start(2)) + StringPool.SPACE +
			line.substring(matcher.start(2));
	}

	protected String formatIncorrectSyntax(
		String line, String incorrectSyntax, String correctSyntax,
		boolean lineStart) {

		if (lineStart) {
			if (line.startsWith(incorrectSyntax)) {
				line = StringUtil.replaceFirst(
					line, incorrectSyntax, correctSyntax);
			}

			return line;
		}

		for (int x = -1;;) {
			x = line.indexOf(incorrectSyntax, x + 1);

			if (x == -1) {
				return line;
			}

			if (!ToolsUtil.isInsideQuotes(line, x)) {
				line = StringUtil.replaceFirst(
					line, incorrectSyntax, correctSyntax, x);
			}
		}
	}

	protected String formatJavaTerms(
			String javaClassName, String packagePath, File file,
			String fileName, String absolutePath, String content,
			String javaClassContent, int javaClassLineCount, String indent,
			String checkJavaFieldTypesExcludesProperty,
			String javaTermSortExcludesProperty,
			String testAnnotationsExcludesProperty)
		throws Exception {

		JavaSourceProcessor javaSourceProcessor = null;

		if (this instanceof JavaSourceProcessor) {
			javaSourceProcessor = (JavaSourceProcessor)this;
		}
		else {
			javaSourceProcessor = new JavaSourceProcessor();

			javaSourceProcessor.setProperties(_properties);
			javaSourceProcessor.setSourceFormatterArgs(sourceFormatterArgs);
		}

		JavaClass javaClass = new JavaClass(
			javaClassName, packagePath, file, fileName, absolutePath, content,
			javaClassContent, javaClassLineCount, indent + StringPool.TAB, null,
			javaSourceProcessor);

		String newJavaClassContent = javaClass.formatJavaTerms(
			getAnnotationsExclusions(), getImmutableFieldTypes(),
			checkJavaFieldTypesExcludesProperty, javaTermSortExcludesProperty,
			testAnnotationsExcludesProperty);

		if (!javaClassContent.equals(newJavaClassContent)) {
			return StringUtil.replaceFirst(
				content, javaClassContent, newJavaClassContent);
		}

		return content;
	}

	protected String formatStringBundler(
		String fileName, String content, int maxLineLength) {

		Matcher matcher = sbAppendPattern.matcher(content);

		matcherIteration:
		while (matcher.find()) {
			String appendValue = stripQuotes(matcher.group(2), CharPool.QUOTE);

			appendValue = StringUtil.replace(appendValue, "+\n", "+ ");

			if (!appendValue.contains(" + ")) {
				continue;
			}

			String[] appendValueParts = StringUtil.split(appendValue, " + ");

			for (String appendValuePart : appendValueParts) {
				if ((getLevel(appendValuePart) != 0) ||
					Validator.isNumber(appendValuePart)) {

					continue matcherIteration;
				}
			}

			processMessage(
				fileName, "Incorrect use of '+' inside StringBundler",
				getLineCount(content, matcher.start(1)));
		}

		matcher = sbAppendWithStartingSpacePattern.matcher(content);

		while (matcher.find()) {
			String firstLine = matcher.group(1);

			if (firstLine.endsWith("\\n\");")) {
				continue;
			}

			if ((maxLineLength != -1) &&
				(getLineLength(firstLine) >= maxLineLength)) {

				processMessage(
					fileName,
					"Do not append string starting with space to StringBundler",
					getLineCount(content, matcher.start(3)));
			}
			else {
				content = StringUtil.replaceFirst(
					content, "\");\n", " \");\n", matcher.start(2));
				content = StringUtil.replaceFirst(
					content, "(\" ", "(\"", matcher.start(3));
			}
		}

		return content;
	}

	protected String formatTagAttributeType(
			String line, String tagName, String attributeAndValue)
		throws Exception {

		return line;
	}

	protected String formatWhitespace(String line, boolean javaSource) {
		String trimmedLine = StringUtil.trimLeading(line);

		line = formatWhitespace(line, trimmedLine, javaSource);

		if (javaSource) {
			return line;
		}

		Matcher matcher = javaSourceInsideJSPLinePattern.matcher(line);

		while (matcher.find()) {
			String linePart = matcher.group(1);

			if (!linePart.startsWith(StringPool.SPACE)) {
				return StringUtil.replace(
					line, matcher.group(), "<%= " + linePart + "%>");
			}

			if (!linePart.endsWith(StringPool.SPACE)) {
				return StringUtil.replace(
					line, matcher.group(), "<%=" + linePart + " %>");
			}

			line = formatWhitespace(line, linePart, true);
		}

		return line;
	}

	protected String formatWhitespace(
		String line, String linePart, boolean javaSource) {

		String originalLinePart = linePart;

		linePart = formatIncorrectSyntax(linePart, "catch(", "catch (", true);
		linePart = formatIncorrectSyntax(linePart, "else{", "else {", true);
		linePart = formatIncorrectSyntax(
			linePart, "else if(", "else if (", true);
		linePart = formatIncorrectSyntax(linePart, "for(", "for (", true);
		linePart = formatIncorrectSyntax(linePart, "if(", "if (", true);
		linePart = formatIncorrectSyntax(linePart, "while(", "while (", true);
		linePart = formatIncorrectSyntax(linePart, "List <", "List<", false);

		if (javaSource) {
			linePart = formatIncorrectSyntax(linePart, " ...", "...", false);
			linePart = formatIncorrectSyntax(linePart, " [", "[", false);
			linePart = formatIncorrectSyntax(linePart, "{ ", "{", false);
			linePart = formatIncorrectSyntax(linePart, " }", "}", false);
			linePart = formatIncorrectSyntax(linePart, " )", ")", false);
			linePart = formatIncorrectSyntax(linePart, "( ", "(", false);
			linePart = formatIncorrectSyntax(linePart, "){", ") {", false);
			linePart = formatIncorrectSyntax(linePart, "]{", "] {", false);
			linePart = formatIncorrectSyntax(linePart, "\\w(( ?)=)");
			linePart = formatIncorrectSyntax(linePart, "(=( ?))\\w");
			linePart = formatIncorrectSyntax(linePart, "for \\(.*(( ?):)");
			linePart = formatIncorrectSyntax(linePart, "for \\(.*(:( ?)).+");
		}

		if (!linePart.startsWith("##")) {
			for (int x = 0;;) {
				x = linePart.indexOf(StringPool.DOUBLE_SPACE, x + 1);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(linePart, x)) {
					continue;
				}

				linePart = StringUtil.replaceFirst(
					linePart, StringPool.DOUBLE_SPACE, StringPool.SPACE, x);
			}
		}

		if (!javaSource) {
			line = StringUtil.replace(line, originalLinePart, linePart);

			return formatIncorrectSyntax(
				line, StringPool.SPACE + StringPool.TAB, StringPool.TAB, false);
		}

		if (!line.contains(StringPool.DOUBLE_SLASH)) {
			while (linePart.contains(StringPool.TAB)) {
				linePart = StringUtil.replaceLast(
					linePart, StringPool.TAB, StringPool.SPACE);
			}
		}

		if (line.contains(StringPool.DOUBLE_SLASH)) {
			line = StringUtil.replace(line, originalLinePart, linePart);

			return formatIncorrectSyntax(
				line, StringPool.SPACE + StringPool.TAB, StringPool.TAB, false);
		}

		int pos = linePart.indexOf(") ");

		if ((pos != -1) && ((pos + 2) < linePart.length()) &&
			!linePart.contains(StringPool.AT) &&
			!ToolsUtil.isInsideQuotes(linePart, pos)) {

			String linePart2 = linePart.substring(pos + 2);

			if (Character.isLetter(linePart2.charAt(0)) &&
				!linePart2.startsWith("default") &&
				!linePart2.startsWith("instanceof") &&
				!linePart2.startsWith("return") &&
				!linePart2.startsWith("throws")) {

				linePart = StringUtil.replaceFirst(
					linePart, StringPool.SPACE, StringPool.BLANK, pos);
			}
		}

		pos = linePart.indexOf(" (");

		if ((pos != -1) && !linePart.contains(StringPool.EQUAL) &&
			!ToolsUtil.isInsideQuotes(linePart, pos) &&
			(linePart.startsWith("private ") ||
			 linePart.startsWith("protected ") ||
			 linePart.startsWith("public "))) {

			linePart = StringUtil.replaceFirst(linePart, " (", "(", pos);
		}

		for (int x = -1;;) {
			int posComma = linePart.indexOf(CharPool.COMMA, x + 1);
			int posSemicolon = linePart.indexOf(CharPool.SEMICOLON, x + 1);

			if ((posComma == -1) && (posSemicolon == -1)) {
				break;
			}

			x = Math.min(posComma, posSemicolon);

			if (x == -1) {
				x = Math.max(posComma, posSemicolon);
			}

			if (ToolsUtil.isInsideQuotes(linePart, x)) {
				continue;
			}

			if (linePart.length() > (x + 1)) {
				char nextChar = linePart.charAt(x + 1);

				if ((nextChar != CharPool.APOSTROPHE) &&
					(nextChar != CharPool.CLOSE_PARENTHESIS) &&
					(nextChar != CharPool.SPACE) &&
					(nextChar != CharPool.STAR)) {

					linePart = StringUtil.insert(
						linePart, StringPool.SPACE, x + 1);
				}
			}

			if (x > 0) {
				char previousChar = linePart.charAt(x - 1);

				if (previousChar == CharPool.SPACE) {
					linePart = linePart.substring(0, x - 1).concat(
						linePart.substring(x));
				}
			}
		}

		line = StringUtil.replace(line, originalLinePart, linePart);

		return formatIncorrectSyntax(
			line, StringPool.SPACE + StringPool.TAB, StringPool.TAB, false);
	}

	protected String getAbsolutePath(String fileName) {
		Path filePath = Paths.get(fileName);

		filePath = filePath.toAbsolutePath();

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	protected Set<String> getAnnotationsExclusions() {
		if (_annotationsExclusions != null) {
			return _annotationsExclusions;
		}

		_annotationsExclusions = SetUtil.fromArray(
			new String[] {
				"ArquillianResource", "BeanReference", "Captor", "Inject",
				"Mock", "Parameter", "Reference", "ServiceReference",
				"SuppressWarnings"
			});

		return _annotationsExclusions;
	}

	protected BNDSettings getBNDSettings(String fileName) throws Exception {
		for (Map.Entry<String, BNDSettings> entry :
				_bndSettingsMap.entrySet()) {

			String bndFileLocation = entry.getKey();

			if (fileName.startsWith(bndFileLocation)) {
				return entry.getValue();
			}
		}

		String bndFileLocation = fileName;

		while (true) {
			int pos = bndFileLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			bndFileLocation = bndFileLocation.substring(0, pos + 1);

			File file = new File(bndFileLocation + "bnd.bnd");

			if (file.exists()) {
				return new BNDSettings(bndFileLocation, FileUtil.read(file));
			}

			bndFileLocation = StringUtil.replaceLast(
				bndFileLocation, StringPool.SLASH, StringPool.BLANK);
		}
	}

	protected Map<String, BNDSettings> getBNDSettingsMap() {
		return _bndSettingsMap;
	}

	protected Map<String, String> getCompatClassNamesMap() throws Exception {
		if (_compatClassNamesMap != null) {
			return _compatClassNamesMap;
		}

		Map<String, String> compatClassNamesMap = new HashMap<>();

		String[] includes = new String[] {
			"**/portal-compat-shared/src/com/liferay/compat/**/*.java"
		};

		String basedir = sourceFormatterArgs.getBaseDirName();

		List<String> fileNames = new ArrayList<>();

		for (int i = 0; i < PLUGINS_MAX_DIR_LEVEL; i++) {
			File sharedDir = new File(basedir + "shared");

			if (sharedDir.exists()) {
				fileNames = getFileNames(basedir, new String[0], includes);

				break;
			}

			basedir = basedir + "../";
		}

		for (String fileName : fileNames) {
			File file = new File(fileName);

			String content = FileUtil.read(file);

			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			fileName = StringUtil.replace(
				fileName, CharPool.SLASH, CharPool.PERIOD);

			int pos = fileName.indexOf("com.");

			String compatClassName = fileName.substring(pos);

			compatClassName = compatClassName.substring(
				0, compatClassName.length() - 5);

			String extendedClassName = StringUtil.replace(
				compatClassName, "compat.", StringPool.BLANK);

			if (content.contains("extends " + extendedClassName)) {
				compatClassNamesMap.put(compatClassName, extendedClassName);
			}
		}

		_compatClassNamesMap = compatClassNamesMap;

		return _compatClassNamesMap;
	}

	protected String getContent(String fileName, int level) throws IOException {
		File file = getFile(fileName, level);

		if (file != null) {
			String content = FileUtil.read(file);

			if (Validator.isNotNull(content)) {
				return content;
			}
		}

		return StringPool.BLANK;
	}

	protected String getCustomCopyright(String absolutePath)
		throws IOException {

		for (int x = absolutePath.length();;) {
			x = absolutePath.lastIndexOf(CharPool.SLASH, x);

			if (x == -1) {
				break;
			}

			String copyright = FileUtil.read(
				new File(absolutePath.substring(0, x + 1) + "copyright.txt"));

			if (Validator.isNotNull(copyright)) {
				return copyright;
			}

			x = x - 1;
		}

		return null;
	}

	protected File getFile(String fileName, int level) {
		return _sourceFormatterHelper.getFile(
			sourceFormatterArgs.getBaseDirName(), fileName, level);
	}

	protected List<String> getFileNames(
			String basedir, List<String> recentChangesFileNames,
			String[] excludes, String[] includes)
		throws Exception {

		return getFileNames(
			basedir, recentChangesFileNames, excludes, includes,
			sourceFormatterArgs.isIncludeSubrepositories());
	}

	protected List<String> getFileNames(
			String basedir, List<String> recentChangesFileNames,
			String[] excludes, String[] includes,
			boolean includeSubrepositories)
		throws Exception {

		if (_excludes != null) {
			excludes = ArrayUtil.append(excludes, _excludes);
		}

		return _sourceFormatterHelper.getFileNames(
			basedir, recentChangesFileNames, excludes, includes,
			includeSubrepositories);
	}

	protected List<String> getFileNames(
			String basedir, String[] excludes, String[] includes)
		throws Exception {

		return getFileNames(
			basedir, sourceFormatterArgs.getRecentChangesFileNames(), excludes,
			includes);
	}

	protected List<String> getFileNames(String[] excludes, String[] includes)
		throws Exception {

		return getFileNames(
			sourceFormatterArgs.getBaseDirName(), excludes, includes);
	}

	protected Set<String> getImmutableFieldTypes() {
		if (_immutableFieldTypes != null) {
			return _immutableFieldTypes;
		}

		Set<String> immutableFieldTypes = SetUtil.fromArray(
			new String[] {
				"boolean", "byte", "char", "double", "float", "int", "long",
				"short", "Boolean", "Byte", "Character", "Class", "Double",
				"Float", "Int", "Long", "Number", "Short", "String"
			});

		immutableFieldTypes.addAll(getPropertyList("immutable.field.types"));

		_immutableFieldTypes = immutableFieldTypes;

		return _immutableFieldTypes;
	}

	protected String[] getLanguageKeys(Matcher matcher) {
		int groupCount = matcher.groupCount();

		if (groupCount == 1) {
			String languageKey = matcher.group(1);

			if (Validator.isNotNull(languageKey)) {
				return new String[] {languageKey};
			}
		}
		else if (groupCount == 2) {
			String languageKey = matcher.group(2);

			languageKey = TextFormatter.format(languageKey, TextFormatter.P);

			return new String[] {languageKey};
		}

		StringBundler sb = new StringBundler();

		String match = matcher.group();

		int count = 0;

		for (int i = 0; i < match.length(); i++) {
			char c = match.charAt(i);

			switch (c) {
				case CharPool.CLOSE_PARENTHESIS:
					if (count <= 1) {
						return new String[0];
					}

					count--;

					break;

				case CharPool.OPEN_PARENTHESIS:
					count++;

					break;

				case CharPool.QUOTE:
					if (count > 1) {
						break;
					}

					while (i < match.length()) {
						i++;

						if (match.charAt(i) == CharPool.QUOTE) {
							String languageKey = sb.toString();

							if (match.startsWith("names")) {
								return StringUtil.split(languageKey);
							}
							else {
								return new String[] {languageKey};
							}
						}

						sb.append(match.charAt(i));
					}
			}
		}

		return new String[0];
	}

	protected int getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	protected int getLevel(String s) {
		return getLevel(
			s, new String[] {StringPool.OPEN_PARENTHESIS},
			new String[] {StringPool.CLOSE_PARENTHESIS}, 0);
	}

	protected int getLevel(
		String s, String increaseLevelString, String decreaseLevelString) {

		return getLevel(
			s, new String[] {increaseLevelString},
			new String[] {decreaseLevelString}, 0);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings,
		String[] decreaseLevelStrings) {

		return getLevel(s, increaseLevelStrings, decreaseLevelStrings, 0);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings, String[] decreaseLevelStrings,
		int startLevel) {

		int level = startLevel;

		for (String increaseLevelString : increaseLevelStrings) {
			level = adjustLevel(level, s, increaseLevelString, 1);
		}

		for (String decreaseLevelString : decreaseLevelStrings) {
			level = adjustLevel(level, s, decreaseLevelString, -1);
		}

		return level;
	}

	protected String getLine(String content, int lineCount) {
		int nextLineStartPos = getLineStartPos(content, lineCount);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	protected int getLineCount(String content, int pos) {
		return StringUtil.count(content, 0, pos, CharPool.NEW_LINE) + 1;
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

	protected int getLineStartPos(String content, int lineCount) {
		int x = 0;

		for (int i = 1; i < lineCount; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	protected ComparableVersion getMainReleaseComparableVersion(
			String fileName, String absolutePath, boolean checkModuleVersion)
		throws Exception {

		boolean usePortalReleaseVersion = true;

		if (checkModuleVersion &&
			(!portalSource || isModulesFile(absolutePath))) {

			usePortalReleaseVersion = false;
		}

		String releaseVersion = StringPool.BLANK;

		if (usePortalReleaseVersion) {
			if (_mainReleaseComparableVersion != null) {
				return _mainReleaseComparableVersion;
			}

			releaseVersion = ReleaseInfo.getVersion();
		}
		else {
			BNDSettings bndSettings = getBNDSettings(fileName);

			releaseVersion = bndSettings.getReleaseVersion();

			if (releaseVersion == null) {
				return null;
			}

			putBNDSettings(bndSettings);
		}

		int pos = releaseVersion.lastIndexOf(CharPool.PERIOD);

		String mainReleaseVersion = releaseVersion.substring(0, pos) + ".0";

		ComparableVersion mainReleaseComparableVersion = new ComparableVersion(
			mainReleaseVersion);

		if (usePortalReleaseVersion) {
			_mainReleaseComparableVersion = mainReleaseComparableVersion;
		}

		return mainReleaseComparableVersion;
	}

	protected List<String> getModuleLangDirNames(
		String moduleLocation, String buildGradleContent) {

		List<String> moduleLangDirNames = new ArrayList<>();

		Matcher matcher = mergeLangPattern.matcher(buildGradleContent);

		if (matcher.find()) {
			String[] sourceDirs = StringUtil.split(matcher.group(1));

			for (String sourceDir : sourceDirs) {
				sourceDir = StringUtil.trim(sourceDir);

				moduleLangDirNames.add(
					moduleLocation + StringPool.SLASH +
						sourceDir.substring(1, sourceDir.length() - 1));
			}

			return moduleLangDirNames;
		}

		int x = moduleLocation.lastIndexOf(StringPool.SLASH);

		String baseModuleName = moduleLocation.substring(0, x);

		int y = baseModuleName.lastIndexOf(StringPool.SLASH);

		baseModuleName = baseModuleName.substring(
			y + 1, baseModuleName.length());

		String moduleLangDirName =
			moduleLocation.substring(0, x + 1) + baseModuleName +
				"-lang/src/main/resources/content";

		File moduleLangDir = new File(moduleLangDirName);

		if (!moduleLangDir.exists() &&
			moduleLangDirName.contains("/modules/ee/")) {

			moduleLangDirName = StringUtil.replaceFirst(
				moduleLangDirName, "/modules/ee/", "/modules/");
		}

		moduleLangDirNames.add(moduleLangDirName);

		return moduleLangDirNames;
	}

	protected Properties getModuleLangLanguageProperties(String absolutePath)
		throws Exception {

		Properties properties = _moduleLangLanguageProperties.get(absolutePath);

		if (properties != null) {
			return properties;
		}

		String buildGradleContent = null;
		String buildGradleFileLocation = absolutePath;

		while (true) {
			int pos = buildGradleFileLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			buildGradleFileLocation = buildGradleFileLocation.substring(
				0, pos + 1);

			File file = new File(buildGradleFileLocation + "build.gradle");

			if (file.exists()) {
				buildGradleContent = FileUtil.read(file);

				break;
			}

			buildGradleFileLocation = StringUtil.replaceLast(
				buildGradleFileLocation, StringPool.SLASH, StringPool.BLANK);
		}

		Matcher matcher = applyLangMergerPluginPattern.matcher(
			buildGradleContent);

		if (!matcher.find()) {
			return null;
		}

		String moduleLocation = StringUtil.replaceLast(
			buildGradleFileLocation, StringPool.SLASH, StringPool.BLANK);

		List<String> moduleLangDirNames = getModuleLangDirNames(
			moduleLocation, buildGradleContent);

		properties = new Properties();

		for (String moduleLangDirName : moduleLangDirNames) {
			String moduleLangLanguagePropertiesFileName =
				moduleLangDirName + "/Language.properties";

			File file = new File(moduleLangLanguagePropertiesFileName);

			if (!file.exists()) {
				continue;
			}

			InputStream inputStream = new FileInputStream(file);

			properties.load(inputStream);
		}

		_moduleLangLanguageProperties.put(absolutePath, properties);

		return properties;
	}

	protected Properties getModuleLanguageProperties(String fileName) {
		Properties properties = _moduleLanguageProperties.get(fileName);

		if (properties != null) {
			return properties;
		}

		StringBundler sb = new StringBundler(3);

		int pos = fileName.indexOf("/docroot/");

		if (pos != -1) {
			sb.append(fileName.substring(0, pos + 9));
			sb.append("WEB-INF/src/");
		}
		else {
			pos = fileName.indexOf("src/");

			if (pos == -1) {
				return null;
			}

			sb.append(fileName.substring(0, pos + 4));

			if (fileName.contains("src/main/")) {
				sb.append("main/resources/");
			}
		}

		sb.append("content/Language.properties");

		try {
			properties = new Properties();

			InputStream inputStream = new FileInputStream(sb.toString());

			properties.load(inputStream);

			_moduleLanguageProperties.put(fileName, properties);

			return properties;
		}
		catch (Exception e) {
		}

		return null;
	}

	protected List<String> getParameterList(String methodCall) {
		String parameters = null;

		int x = -1;

		while (true) {
			x = methodCall.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

			parameters = methodCall.substring(0, x + 1);

			if ((getLevel(parameters, "(", ")") == 0) &&
				(getLevel(parameters, "{", "}") == 0)) {

				break;
			}
		}

		x = parameters.indexOf(StringPool.OPEN_PARENTHESIS);

		parameters = parameters.substring(x + 1, parameters.length() - 1);

		return splitParameters(parameters);
	}

	protected List<String> getPluginsInsideModulesDirectoryNames()
		throws Exception {

		if (_pluginsInsideModulesDirectoryNames != null) {
			return _pluginsInsideModulesDirectoryNames;
		}

		List<String> pluginsInsideModulesDirectoryNames = new ArrayList<>();

		List<String> pluginBuildFileNames = getFileNames(
			new String[0],
			new String[] {
				"**/modules/apps/**/build.xml",
				"**/modules/private/apps/**/build.xml"
			});

		for (String pluginBuildFileName : pluginBuildFileNames) {
			pluginBuildFileName = StringUtil.replace(
				pluginBuildFileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String absolutePath = getAbsolutePath(pluginBuildFileName);

			int x = absolutePath.indexOf("/modules/apps/");

			if (x == -1) {
				x = absolutePath.indexOf("/modules/private/apps/");
			}

			int y = absolutePath.lastIndexOf(StringPool.SLASH);

			pluginsInsideModulesDirectoryNames.add(
				absolutePath.substring(x, y + 1));
		}

		_pluginsInsideModulesDirectoryNames =
			pluginsInsideModulesDirectoryNames;

		return _pluginsInsideModulesDirectoryNames;
	}

	protected String getProperty(String key) {
		return _properties.getProperty(key);
	}

	protected List<String> getPropertyList(String key) {
		return ListUtil.fromString(
			GetterUtil.getString(getProperty(key)), StringPool.COMMA);
	}

	protected boolean hasMissingParentheses(String s) {
		if (Validator.isNull(s)) {
			return false;
		}

		boolean containsAndOperator = s.contains("&&");
		boolean containsOrOperator = s.contains("||");

		if (containsAndOperator && containsOrOperator) {
			return true;
		}

		boolean containsCompareOperator = false;

		if ((s.contains(" == ") || s.contains(" != ") || s.contains(" < ") ||
			 s.contains(" > ") || s.contains(" =< ") || s.contains(" => ") ||
			 s.contains(" <= ") || s.contains(" >= "))) {

			containsCompareOperator = true;
		}

		boolean containsMathOperator = false;

		if ((s.contains(" = ") || s.contains(" - ") || s.contains(" + ") ||
			 s.contains(" & ") || s.contains(" % ") || s.contains(" * ") ||
			 s.contains(" / "))) {

			containsMathOperator = true;
		}

		if (containsCompareOperator &&
			(containsAndOperator || containsOrOperator ||
			 (containsMathOperator && !s.contains(StringPool.OPEN_BRACKET)))) {

			return true;
		}

		return false;
	}

	protected boolean hasRedundantParentheses(String s) {
		int x = -1;

		while (true) {
			x = s.indexOf(StringPool.SPACE, x + 1);

			if (x == -1) {
				break;
			}

			if (getLevel(s.substring(0, x)) == 0) {
				return false;
			}
		}

		return true;
	}

	protected boolean hasRedundantParentheses(
		String s, String operator1, String operator2) {

		while (true) {
			int x = s.indexOf("!(");

			if (x == -1) {
				break;
			}

			int y = x;

			while (true) {
				y = s.indexOf(")", y + 1);

				String linePart = s.substring(x, y + 1);

				if (getLevel(linePart) == 0) {
					break;
				}
			}

			s = StringUtil.replaceFirst(s, ")", StringPool.BLANK, y);
			s = StringUtil.replaceFirst(s, "!(", StringPool.BLANK, x);
		}

		String[] parts = StringUtil.split(s, operator1);

		if (parts.length < 3) {
			return false;
		}

		for (int i = 1; i < (parts.length - 1); i++) {
			String part = parts[i];

			if (part.contains(operator2)) {
				continue;
			}

			if (Math.abs(getLevel(part)) == 1) {
				return true;
			}
		}

		return false;
	}

	protected boolean isAllowedVariableType(
		String content, String variableName,
		String[] variableTypeRegexStrings) {

		if (variableTypeRegexStrings.length == 0) {
			return true;
		}

		for (String variableTypeRegex : variableTypeRegexStrings) {
			StringBundler sb = new StringBundler(5);

			sb.append("[\\s\\S]*\\W");
			sb.append(variableTypeRegex);
			sb.append("\\s+");
			sb.append(variableName);
			sb.append("\\W[\\s\\S]*");

			if (content.matches(sb.toString())) {
				return true;
			}

			sb = new StringBundler(5);

			sb.append("[\\s\\S]*\\W");
			sb.append(variableName);
			sb.append(" =\\s+new ");
			sb.append(variableTypeRegex);
			sb.append("[\\s\\S]*");

			if (content.matches(sb.toString())) {
				return true;
			}
		}

		return false;
	}

	protected boolean isAttributName(String attributeName) {
		if (Validator.isNull(attributeName)) {
			return false;
		}

		Matcher matcher = attributeNamePattern.matcher(attributeName);

		return matcher.matches();
	}

	protected boolean isExcludedPath(String property, String path) {
		return isExcludedPath(property, path, -1);
	}

	protected boolean isExcludedPath(
		String property, String path, int lineCount) {

		return isExcludedPath(property, path, lineCount, null);
	}

	protected boolean isExcludedPath(
		String property, String path, int lineCount, String javaTermName) {

		List<String> excludes = _exclusionPropertiesMap.get(property);

		if (excludes == null) {
			excludes = getPropertyList(property);

			_exclusionPropertiesMap.put(property, excludes);
		}

		if (ListUtil.isEmpty(excludes)) {
			return false;
		}

		String pathWithJavaTermName = null;

		if (Validator.isNotNull(javaTermName)) {
			pathWithJavaTermName = path + StringPool.AT + javaTermName;
		}

		String pathWithLineCount = null;

		if (lineCount > 0) {
			pathWithLineCount = path + StringPool.AT + lineCount;
		}

		for (String exclude : excludes) {
			if (exclude.startsWith("**")) {
				exclude = exclude.substring(2);
			}

			if (exclude.endsWith("**")) {
				exclude = exclude.substring(0, exclude.length() - 2);

				if (path.contains(exclude)) {
					return true;
				}

				continue;
			}

			if (path.endsWith(exclude) ||
				((pathWithJavaTermName != null) &&
				 pathWithJavaTermName.endsWith(exclude)) ||
				((pathWithLineCount != null) &&
				 pathWithLineCount.endsWith(exclude))) {

				return true;
			}
		}

		return false;
	}

	protected boolean isModulesFile(String absolutePath) {
		return isModulesFile(absolutePath, false);
	}

	protected boolean isModulesFile(
		String absolutePath, boolean includePlugins) {

		if (includePlugins) {
			return absolutePath.contains("/modules/");
		}

		try {
			for (String directoryName :
					getPluginsInsideModulesDirectoryNames()) {

				if (absolutePath.contains(directoryName)) {
					return false;
				}
			}
		}
		catch (Exception e) {
		}

		return absolutePath.contains("/modules/");
	}

	protected void postFormat() throws Exception {
	}

	protected void preFormat() throws Exception {
	}

	protected void printError(String fileName, String message) {
		if (sourceFormatterArgs.isPrintErrors()) {
			_sourceFormatterHelper.printError(fileName, message);
		}
	}

	protected void processFormattedFile(
			File file, String fileName, String content, String newContent)
		throws IOException {

		if (!content.equals(newContent)) {
			if (sourceFormatterArgs.isPrintErrors()) {
				_sourceFormatterHelper.printError(fileName, file);
			}

			if (sourceFormatterArgs.isAutoFix()) {
				FileUtil.write(file, newContent);
			}
			else if (_firstSourceMismatchException == null) {
				_firstSourceMismatchException = new SourceMismatchException(
					fileName, content, newContent);
			}
		}

		if (sourceFormatterArgs.isPrintErrors()) {
			Set<SourceFormatterMessage> sourceFormatterMessages =
				_sourceFormatterMessagesMap.get(fileName);

			if (sourceFormatterMessages != null) {
				for (SourceFormatterMessage sourceFormatterMessage :
						sourceFormatterMessages) {

					_sourceFormatterHelper.printError(
						fileName, sourceFormatterMessage.toString());
				}
			}
		}

		_modifiedFileNames.add(file.getAbsolutePath());
	}

	protected void putBNDSettings(BNDSettings bndSettings) {
		_bndSettingsMap.put(bndSettings.getFileLocation(), bndSettings);
	}

	protected Document readXML(String content) throws DocumentException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(new UnsyncStringReader(content));
	}

	protected String replacePrimitiveWrapperInstantiation(String line) {
		return StringUtil.replace(
			line,
			new String[] {
				"new Boolean(", "new Byte(", "new Character(", "new Double(",
				"new Float(", "new Integer(", "new Long(", "new Short("
			},
			new String[] {
				"Boolean.valueOf(", "Byte.valueOf(", "Character.valueOf(",
				"Double.valueOf(", "Float.valueOf(", "Integer.valueOf(",
				"Long.valueOf(", "Short.valueOf("
			});
	}

	protected String sortDefinitions(
		String fileName, String content, Comparator<String> comparator) {

		String previousDefinition = null;

		Matcher matcher = _definitionPattern.matcher(content);

		while (matcher.find()) {
			String newContent = formatDefinitionKey(
				fileName, content, matcher.group(1));

			if (!newContent.equals(content)) {
				return newContent;
			}

			String definition = matcher.group();

			if (Validator.isNotNull(matcher.group(1))) {
				definition = definition.substring(0, definition.length() - 1);
			}

			if (Validator.isNotNull(previousDefinition)) {
				int value = comparator.compare(previousDefinition, definition);

				if (value > 0) {
					content = StringUtil.replaceFirst(
						content, previousDefinition, definition);
					content = StringUtil.replaceLast(
						content, definition, previousDefinition);

					return content;
				}

				if (value == 0) {
					return StringUtil.replaceFirst(
						content, previousDefinition + "\n", StringPool.BLANK);
				}
			}

			previousDefinition = definition;
		}

		return content;
	}

	protected String sortHTMLAttributes(
		String line, String value, String attributeAndValue) {

		return line;
	}

	protected String sortMethodCall(
		String content, String methodName, String... variableTypeRegexStrings) {

		Pattern codeBlockPattern = Pattern.compile(
			"(\t+(\\w*)\\." + methodName + "\\(\\s*\".*?\\);\n)+",
			Pattern.DOTALL);

		Matcher codeBlockMatcher = codeBlockPattern.matcher(content);

		PutOrSetParameterNameComparator putOrSetParameterNameComparator =
			new PutOrSetParameterNameComparator();

		while (codeBlockMatcher.find()) {
			if (!isAllowedVariableType(
					content, codeBlockMatcher.group(2),
					variableTypeRegexStrings)) {

				continue;
			}

			String codeBlock = codeBlockMatcher.group();

			Pattern singleLineMethodCallPattern = Pattern.compile(
				"\t*\\w*\\." + methodName + "\\((.*?)\\);\n", Pattern.DOTALL);

			Matcher singleLineMatcher = singleLineMethodCallPattern.matcher(
				codeBlock);

			String previousParameters = null;
			String previousPutOrSetParameterName = null;

			while (singleLineMatcher.find()) {
				String parameters = singleLineMatcher.group(1);

				List<String> parametersList = splitParameters(parameters);

				String putOrSetParameterName = parametersList.get(0);

				if ((previousPutOrSetParameterName != null) &&
					(putOrSetParameterNameComparator.compare(
						previousPutOrSetParameterName, putOrSetParameterName) >
							0)) {

					String newCodeBlock = StringUtil.replaceFirst(
						codeBlock, previousParameters, parameters);
					newCodeBlock = StringUtil.replaceLast(
						newCodeBlock, parameters, previousParameters);

					return StringUtil.replace(content, codeBlock, newCodeBlock);
				}

				previousParameters = parameters;
				previousPutOrSetParameterName = putOrSetParameterName;
			}
		}

		return content;
	}

	protected String sortMethodCalls(String absolutePath, String content) {
		if (isExcludedPath(METHOD_CALL_SORT_EXCLUDES, absolutePath)) {
			return content;
		}

		content = sortMethodCall(
			content, "add", "ConcurrentSkipListSet<.*>", "HashSet<.*>",
			"TreeSet<.*>");
		content = sortMethodCall(
			content, "put", "ConcurrentHashMap<.*>", "HashMap<.*>",
			"JSONObject", "TreeMap<.*>");
		content = sortMethodCall(content, "setAttribute");

		return content;
	}

	protected List<String> splitParameters(String parameters) {
		List<String> parametersList = new ArrayList<>();

		int x = -1;

		while (true) {
			x = parameters.indexOf(StringPool.COMMA, x + 1);

			if (x == -1) {
				parametersList.add(StringUtil.trim(parameters));

				return parametersList;
			}

			if (ToolsUtil.isInsideQuotes(parameters, x)) {
				continue;
			}

			String linePart = parameters.substring(0, x);

			if ((getLevel(linePart, "(", ")") == 0) &&
				(getLevel(linePart, "{", "}") == 0)) {

				parametersList.add(StringUtil.trim(linePart));

				parameters = parameters.substring(x + 1);

				x = -1;
			}
		}
	}

	protected String stripQuotes(String s) {
		return stripQuotes(s, CharPool.APOSTROPHE, CharPool.QUOTE);
	}

	protected String stripQuotes(String s, char... delimeters) {
		List<Character> delimetersList = ListUtil.toList(delimeters);

		char delimeter = CharPool.SPACE;
		boolean insideQuotes = false;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (insideQuotes) {
				if (c == delimeter) {
					int precedingBackSlashCount = 0;

					for (int j = (i - 1); j >= 0; j--) {
						if (s.charAt(j) == CharPool.BACK_SLASH) {
							precedingBackSlashCount += 1;
						}
						else {
							break;
						}
					}

					if ((precedingBackSlashCount == 0) ||
						((precedingBackSlashCount % 2) == 0)) {

						insideQuotes = false;
					}
				}
			}
			else if (delimetersList.contains(c)) {
				delimeter = c;
				insideQuotes = true;
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	protected String trimContent(String content, boolean allowLeadingSpaces)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(trimLine(line, allowLeadingSpaces));
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	protected String trimLine(String line, boolean allowLeadingSpaces) {
		if (line.trim().length() == 0) {
			return StringPool.BLANK;
		}

		line = StringUtil.trimTrailing(line);

		if (allowLeadingSpaces || line.startsWith(" *")) {
			return line;
		}

		while (line.matches("^\t*    .*")) {
			line = StringUtil.replaceFirst(
				line, StringPool.FOUR_SPACES, StringPool.TAB);
		}

		while (line.startsWith(StringPool.SPACE)) {
			line = StringUtil.replaceFirst(
				line, StringPool.SPACE, StringPool.BLANK);
		}

		return line;
	}

	protected static final String METHOD_CALL_SORT_EXCLUDES =
		"method.call.sort.excludes";

	protected static final String RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

	protected static Pattern applyLangMergerPluginPattern = Pattern.compile(
		"^apply[ \t]+plugin[ \t]*:[ \t]+\"com.liferay.lang.merger\"$",
		Pattern.MULTILINE);
	protected static Pattern attributeNamePattern = Pattern.compile(
		"[a-z]+[-_a-zA-Z0-9]*");
	protected static Pattern emptyArrayPattern = Pattern.compile(
		"((\\[\\])+) \\{\\}");
	protected static Pattern emptyCollectionPattern = Pattern.compile(
		"Collections\\.EMPTY_(LIST|MAP|SET)");
	protected static Pattern getterUtilGetPattern = Pattern.compile(
		"GetterUtil\\.get(Boolean|Double|Float|Integer|Number|Object|Short|" +
			"String)\\((.*?)\\);\n",
		Pattern.DOTALL);
	protected static Pattern javaSourceInsideJSPLinePattern = Pattern.compile(
		"<%=(.+?)%>");
	protected static Pattern languageKeyPattern = Pattern.compile(
		"LanguageUtil.(?:get|format)\\([^;%]+|Liferay.Language.get\\('([^']+)");
	protected static Pattern mergeLangPattern = Pattern.compile(
		"mergeLang \\{\\s*sourceDirs = \\[(.*?)\\]", Pattern.DOTALL);
	protected static boolean portalSource;
	protected static Pattern principalExceptionPattern = Pattern.compile(
		"SessionErrors\\.contains\\(\n?\t*(renderR|r)equest, " +
			"PrincipalException\\.class\\.getName\\(\\)");
	protected static Pattern sbAppendPattern = Pattern.compile(
		"\\s*\\w*(sb|SB)[0-9]?\\.append\\(\\s*(\\S.*?)\\);\n", Pattern.DOTALL);
	protected static Pattern sbAppendWithStartingSpacePattern = Pattern.compile(
		"\n(\t*\\w*(sb|SB)[0-9]?\\.append\\(\".*\"\\);)\n\\s*\\w*(sb|SB)" +
			"[0-9]?\\.append\\(\" .*\"\\);\n");
	protected static Pattern sessionKeyPattern = Pattern.compile(
		"SessionErrors.(?:add|contains|get)\\([^;%&|!]+|".concat(
			"SessionMessages.(?:add|contains|get)\\([^;%&|!]+"),
		Pattern.MULTILINE);
	protected static Pattern singleLengthStringPattern = Pattern.compile(
		"^(\".\"|StringPool\\.([A-Z_]+))$");
	protected static Pattern stringUtilReplacePattern = Pattern.compile(
		"StringUtil\\.(replace(First|Last)?)\\((.*?)\\);\n", Pattern.DOTALL);
	protected static Pattern taglibSessionKeyPattern = Pattern.compile(
		"<liferay-ui:error [^>]+>|<liferay-ui:success [^>]+>",
		Pattern.MULTILINE);
	protected static Pattern unparameterizedClassTypePattern1 =
		Pattern.compile("\\Wnew Class[^<\\w]");
	protected static Pattern unparameterizedClassTypePattern2 =
		Pattern.compile("\\WClass[\\[\\]]* \\w+ =");
	protected static Pattern validatorEqualsPattern = Pattern.compile(
		"\\WValidator\\.equals\\(");

	protected SourceFormatterArgs sourceFormatterArgs;

	private String[] _getExcludes() {
		if (sourceFormatterArgs.getFileNames() != null) {
			return new String[0];
		}

		List<String> excludesList = ListUtil.fromString(
			GetterUtil.getString(
				System.getProperty("source.formatter.excludes")));

		excludesList.addAll(getPropertyList("source.formatter.excludes"));

		return excludesList.toArray(new String[excludesList.size()]);
	}

	private void _init() {
		try {
			_sourceFormatterHelper = new SourceFormatterHelper(
				sourceFormatterArgs.isUseProperties());

			_sourceFormatterHelper.init();

			portalSource = _isPortalSource();

			_sourceFormatterMessagesMap = new HashMap<>();
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		_excludes = _getExcludes();

		_usePortalCompatImport = GetterUtil.getBoolean(
			getProperty("use.portal.compat.import"));
	}

	private boolean _isMatchPath(String fileName) {
		for (String pattern : getIncludes()) {
			if (SelectorUtils.matchPath(_normalizePattern(pattern), fileName)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isPortalSource() {
		if (getFile("portal-impl", PORTAL_MAX_DIR_LEVEL) != null) {
			return true;
		}

		// Subrepositories should be treated as portal

		String baseDirAbsolutePath = getAbsolutePath(
			sourceFormatterArgs.getBaseDirName());

		int x = baseDirAbsolutePath.length();

		for (int i = 0; i < 2; i++) {
			x = baseDirAbsolutePath.lastIndexOf(CharPool.FORWARD_SLASH, x - 1);

			if (x == -1) {
				return false;
			}

			String dirName = baseDirAbsolutePath.substring(x + 1);

			if (dirName.startsWith("com-liferay-")) {
				return true;
			}
		}

		return false;
	}

	private String _normalizePattern(String originalPattern) {
		String pattern = originalPattern.replace(
			CharPool.SLASH, File.separatorChar);

		pattern = pattern.replace(CharPool.BACK_SLASH, File.separatorChar);

		if (pattern.endsWith(File.separator)) {
			pattern += SelectorUtils.DEEP_TREE_MATCH;
		}

		return pattern;
	}

	private Set<String> _annotationsExclusions;
	private Map<String, BNDSettings> _bndSettingsMap =
		new ConcurrentHashMap<>();
	private Map<String, String> _compatClassNamesMap;
	private String _copyright;
	private final Pattern _definitionPattern = Pattern.compile(
		"^([A-Za-z-]+?)[:=][\\s\\S]*?([^\\\\]\n|\\Z)", Pattern.MULTILINE);
	private final Pattern _emptyLineBetweenTagsPattern = Pattern.compile(
		"\n(\t*)</([-\\w:]+)>(\n*)(\t*)<([-\\w:]+)[> ]");
	private final Pattern _emptyLineInNestedTagsPattern1 = Pattern.compile(
		"\n(\t*)(?:<\\w.*[^/])?>\n\n(\t*)(<.*)\n");
	private final Pattern _emptyLineInNestedTagsPattern2 = Pattern.compile(
		"\n(\t*)(.*>)\n\n(\t*)</.*(\n|$)");
	private String[] _excludes;
	private Map<String, List<String>> _exclusionPropertiesMap = new HashMap<>();
	private SourceMismatchException _firstSourceMismatchException;
	private Set<String> _immutableFieldTypes;
	private ComparableVersion _mainReleaseComparableVersion;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private final Map<String, Properties> _moduleLangLanguageProperties =
		new HashMap<>();
	private final Map<String, Properties> _moduleLanguageProperties =
		new HashMap<>();
	private String _oldCopyright;
	private List<String> _pluginsInsideModulesDirectoryNames;
	private Properties _portalLanguageProperties;
	private Properties _properties;
	private SourceFormatterHelper _sourceFormatterHelper;
	private Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();
	private boolean _usePortalCompatImport;

	private class PutOrSetParameterNameComparator
		extends NaturalOrderStringComparator {

		@Override
		public int compare(
			String putOrSetParameterName1, String putOrSetParameterName2) {

			String strippedParameterName1 = stripQuotes(putOrSetParameterName1);
			String strippedParameterName2 = stripQuotes(putOrSetParameterName2);

			if (strippedParameterName1.contains(StringPool.OPEN_PARENTHESIS) ||
				strippedParameterName2.contains(StringPool.OPEN_PARENTHESIS)) {

				return 0;
			}

			Matcher matcher = _multipleLineParameterNamePattern.matcher(
				putOrSetParameterName1);

			if (matcher.find()) {
				putOrSetParameterName1 = matcher.replaceAll(StringPool.BLANK);
			}

			matcher = _multipleLineParameterNamePattern.matcher(
				putOrSetParameterName2);

			if (matcher.find()) {
				putOrSetParameterName2 = matcher.replaceAll(StringPool.BLANK);
			}

			if (putOrSetParameterName1.matches("\".*\"") &&
				putOrSetParameterName2.matches("\".*\"")) {

				String strippedQuotes1 = putOrSetParameterName1.substring(
					1, putOrSetParameterName1.length() - 1);
				String strippedQuotes2 = putOrSetParameterName2.substring(
					1, putOrSetParameterName2.length() - 1);

				return super.compare(strippedQuotes1, strippedQuotes2);
			}

			int value = super.compare(
				putOrSetParameterName1, putOrSetParameterName2);

			if (putOrSetParameterName1.startsWith(StringPool.QUOTE) ^
				putOrSetParameterName2.startsWith(StringPool.QUOTE)) {

				return -value;
			}

			return value;
		}

		private final Pattern _multipleLineParameterNamePattern =
			Pattern.compile("\" \\+\n\t+\"");

	}

}