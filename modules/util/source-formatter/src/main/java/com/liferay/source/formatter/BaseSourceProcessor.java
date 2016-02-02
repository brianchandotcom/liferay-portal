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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.tools.ant.types.selectors.SelectorUtils;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public abstract class BaseSourceProcessor implements SourceProcessor {

	public static final int PORTAL_MAX_DIR_LEVEL = 5;

	@Override
	public final void format() throws Exception {
		for (String fileName : getFileNames()) {
			try {
				format(fileName);
			}
			catch (Exception e) {
				throw new RuntimeException("Unable to format " + fileName, e);
			}
		}

		postFormat();

		_sourceFormatterHelper.close();
	}

	@Override
	public List<String> getErrorMessages() {
		List<String> errorMessages = new ArrayList<>();

		for (Map.Entry<String, List<String>> entry :
				_errorMessagesMap.entrySet()) {

			errorMessages.addAll(entry.getValue());
		}

		return errorMessages;
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
	public void setSourceFormatterArgs(
		SourceFormatterArgs sourceFormatterArgs) {

		this.sourceFormatterArgs = sourceFormatterArgs;

		_init();
	}

	protected static int getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	protected static boolean isExcludedPath(
		List<String> excludes, String path) {

		return isExcludedPath(excludes, path, -1);
	}

	protected static boolean isExcludedPath(
		List<String> excludes, String path, int lineCount) {

		return isExcludedPath(excludes, path, lineCount, null);
	}

	protected static boolean isExcludedPath(
		List<String> excludes, String path, int lineCount,
		String javaTermName) {

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

	protected static String stripQuotes(String s, char delimeter) {
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
			else if (c == delimeter) {
				insideQuotes = true;
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	protected void checkEmptyCollection(
		String line, String fileName, int lineCount) {

		// LPS-46028

		Matcher matcher = emptyCollectionPattern.matcher(line);

		if (matcher.find()) {
			String collectionType = TextFormatter.format(
				matcher.group(1), TextFormatter.J);

			processErrorMessage(
				fileName,
				"Use Collections.empty" + collectionType + "(): " + fileName +
					" " + lineCount);
		}
	}

	protected void checkIfClauseParentheses(
		String ifClause, String fileName, int lineCount) {

		int quoteCount = StringUtil.count(ifClause, StringPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return;
		}

		ifClause = stripQuotes(ifClause, CharPool.QUOTE);

		ifClause = stripQuotes(ifClause, CharPool.APOSTROPHE);

		if (ifClause.contains(StringPool.DOUBLE_SLASH) ||
			ifClause.contains("/*") || ifClause.contains("*/")) {

			return;
		}

		if (hasRedundantParentheses(ifClause, "||", "&&") ||
			hasRedundantParentheses(ifClause, "&&", "||")) {

			processErrorMessage(
				fileName,
				"redundant parentheses: " + fileName + " " + lineCount);
		}

		ifClause = stripRedundantParentheses(ifClause);

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

					if (hasMissingParentheses(s)) {
						processErrorMessage(
							fileName,
							"missing parentheses: " + fileName + " " +
								lineCount);
					}
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

							if (hasRedundantParentheses(s)) {
								processErrorMessage(
									fileName,
									"redundant parentheses: " + fileName + " " +
										lineCount);
							}
						}

						if ((previousChar == CharPool.OPEN_PARENTHESIS) &&
							(nextChar == CharPool.CLOSE_PARENTHESIS)) {

							processErrorMessage(
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

	protected void checkInefficientStringMethods(
		String line, String fileName, String absolutePath, int lineCount) {

		if (isExcludedPath(getRunOutsidePortalExcludes(), absolutePath)) {
			return;
		}

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
			processErrorMessage(
				fileName,
				"Use StringUtil." + methodName + ": " + fileName + " " +
					lineCount);
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
			_portalLanguageProperties = new Properties();

			File portalLanguagePropertiesFile = new File(
				getFile("portal-impl", PORTAL_MAX_DIR_LEVEL),
				"src/content/Language.properties");

			InputStream inputStream = new FileInputStream(
				portalLanguagePropertiesFile);

			_portalLanguageProperties.load(inputStream);
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

				Properties bndFileLanguageProperties =
					getBNDFileLanguageProperties(fileName);

				if ((bndFileLanguageProperties == null) ||
					!bndFileLanguageProperties.containsKey(languageKey)) {

					processErrorMessage(
						fileName,
						"missing language key: " + languageKey +
							StringPool.SPACE + fileName);
				}
			}
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

	protected void checkResourceUtil(
		String line, String fileName, int lineCount) {

		if (!portalSource || fileName.endsWith("ResourceBundleUtil.java")) {
			return;
		}

		if (line.contains("ResourceBundle.getBundle(")) {
			processErrorMessage(
				fileName,
				"Use ResourceBundleUtil.getBundle instead of " +
					"ResourceBundle.getBundle: " + fileName + " " + lineCount);
		}

		if (line.contains("resourceBundle.getString(")) {
			processErrorMessage(
				fileName,
				"Use ResourceBundleUtil.getString instead of " +
					"resourceBundle.getString: " + fileName + " " + lineCount);
		}
	}

	protected void checkChaining(String line, String fileName, int lineCount) {
		if (line.startsWith("this(")) {
			return;
		}

		if (line.contains(".getClass().")) {
			processErrorMessage(
				fileName, "chaining: " + fileName + " " + lineCount);
		}
	}

	protected void checkStringBundler(
		String line, String fileName, int lineCount) {

		if ((!line.startsWith("sb.append(") && !line.contains("SB.append(")) ||
			!line.endsWith(");")) {

			return;
		}

		int pos = line.indexOf(".append(");

		line = line.substring(pos + 8, line.length() - 2);

		line = stripQuotes(line, CharPool.QUOTE);

		if (!line.contains(" + ")) {
			return;
		}

		String[] lineParts = StringUtil.split(line, " + ");

		for (String linePart : lineParts) {
			int closeParenthesesCount = StringUtil.count(
				linePart, StringPool.CLOSE_PARENTHESIS);
			int openParenthesesCount = StringUtil.count(
				linePart, StringPool.OPEN_PARENTHESIS);

			if (closeParenthesesCount != openParenthesesCount) {
				return;
			}

			if (Validator.isNumber(linePart)) {
				return;
			}
		}

		processErrorMessage(fileName, "plus: " + fileName + " " + lineCount);
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
			String content, String absolutePath, String fileName)
		throws IOException {

		if (_copyright == null) {
			_copyright = getContent(
				sourceFormatterArgs.getCopyrightFileName(),
				PORTAL_MAX_DIR_LEVEL);
		}

		String copyright = _copyright;

		if (fileName.endsWith(".vm") || Validator.isNull(copyright)) {
			return content;
		}

		if (_oldCopyright == null) {
			_oldCopyright = getContent(
				"old-copyright.txt", PORTAL_MAX_DIR_LEVEL);
		}

		if (Validator.isNotNull(_oldCopyright) &&
			content.contains(_oldCopyright)) {

			content = StringUtil.replace(content, _oldCopyright, copyright);

			processErrorMessage(fileName, "old (c): " + fileName);
		}

		if (!content.contains(copyright)) {
			String customCopyright = getCustomCopyright(absolutePath);

			if (Validator.isNotNull(customCopyright)) {
				copyright = customCopyright;
			}

			if (!content.contains(copyright)) {
				processErrorMessage(fileName, "(c): " + fileName);
			}
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
			processErrorMessage(
				fileName,
				"(Unicode)LanguageUtil.format/get methods require " +
					expectedParameter + " parameter instead of " +
						incorrectParameter + " " + fileName);
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

	protected final String format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		_errorMessagesMap.remove(fileName);

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
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);

		File file = new File(fileName);

		String absolutePath = getAbsolutePath(file);

		String content = FileUtil.read(file);

		String newContent = format(file, fileName, absolutePath, content);

		processFormattedFile(file, fileName, content, newContent);
	}

	protected String formatEmptyArray(String line) {
		int pos = line.indexOf("[] {}");

		if ((pos != -1) && !ToolsUtil.isInsideQuotes(line, pos)) {
			return StringUtil.replaceFirst(line, "[] {}", "[0]", pos - 1);
		}

		return line;
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
			String javaClassContent, int javaClassLineCount,
			List<String> checkJavaFieldTypesExcludes,
			List<String> javaTermAccessLevelModifierExcludes,
			List<String> javaTermSortExcludes,
			List<String> testAnnotationsExcludes)
		throws Exception {

		JavaSourceProcessor javaSourceProcessor = null;

		if (this instanceof JavaSourceProcessor) {
			javaSourceProcessor = (JavaSourceProcessor)this;
		}
		else {
			javaSourceProcessor = new JavaSourceProcessor();

			javaSourceProcessor.setSourceFormatterArgs(sourceFormatterArgs);
		}

		JavaClass javaClass = new JavaClass(
			javaClassName, packagePath, file, fileName, absolutePath,
			javaClassContent, javaClassLineCount, StringPool.TAB, null,
			javaTermAccessLevelModifierExcludes, javaSourceProcessor);

		String newJavaClassContent = javaClass.formatJavaTerms(
			getAnnotationsExclusions(), getImmutableFieldTypes(),
			checkJavaFieldTypesExcludes, javaTermSortExcludes,
			testAnnotationsExcludes);

		if (!javaClassContent.equals(newJavaClassContent)) {
			return StringUtil.replaceFirst(
				content, javaClassContent, newJavaClassContent);
		}

		return content;
	}

	protected String formatTagAttributeType(
			String line, String tag, String attributeAndValue)
		throws Exception {

		return line;
	}

	protected String formatWhitespace(String line, boolean javaSource) {
		String trimmedLine = StringUtil.trimLeading(line);

		line = formatWhitespace(line, trimmedLine, javaSource);

		if (javaSource) {
			return line;
		}

		Matcher matcher = javaSourceInsideJSPTagPattern.matcher(line);

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
		linePart = formatIncorrectSyntax(linePart, "for(", "for (", true);
		linePart = formatIncorrectSyntax(linePart, "if(", "if (", true);
		linePart = formatIncorrectSyntax(linePart, "while(", "while (", true);
		linePart = formatIncorrectSyntax(linePart, "List <", "List<", false);
		linePart = formatIncorrectSyntax(linePart, "){", ") {", false);
		linePart = formatIncorrectSyntax(linePart, "]{", "] {", false);

		if (javaSource) {
			linePart = formatIncorrectSyntax(linePart, " [", "[", false);
			linePart = formatIncorrectSyntax(linePart, "{ ", "{", false);
			linePart = formatIncorrectSyntax(linePart, " }", "}", false);
			linePart = formatIncorrectSyntax(linePart, " )", ")", false);
			linePart = formatIncorrectSyntax(linePart, "( ", "(", false);
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

		for (int x = 0;;) {
			x = linePart.indexOf(CharPool.EQUAL, x + 1);

			if (x == -1) {
				break;
			}

			if (ToolsUtil.isInsideQuotes(linePart, x)) {
				continue;
			}

			char c = linePart.charAt(x - 1);

			if (Character.isLetterOrDigit(c)) {
				linePart = StringUtil.replaceFirst(linePart, "=", " =", x);

				break;
			}

			if (x == (linePart.length() - 1)) {
				break;
			}

			c = linePart.charAt(x + 1);

			if (Character.isLetterOrDigit(c)) {
				linePart = StringUtil.replaceFirst(linePart, "=", "= ", x);

				break;
			}
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

	protected String getAbsolutePath(File file) throws Exception {
		String absolutePath = file.getCanonicalPath();

		absolutePath = StringUtil.replace(
			absolutePath, CharPool.BACK_SLASH, CharPool.SLASH);

		return StringUtil.replace(absolutePath, "/./", StringPool.SLASH);
	}

	protected Set<String> getAnnotationsExclusions() {
		if (_annotationsExclusions != null) {
			return _annotationsExclusions;
		}

		_annotationsExclusions = SetUtil.fromArray(
			new String[] {
				"ArquillianResource", "BeanReference", "Inject", "Mock",
				"ServiceReference", "SuppressWarnings"
			});

		return _annotationsExclusions;
	}

	protected Properties getBNDFileLanguageProperties(String fileName)
		throws Exception {

		Tuple bndFileLocationAndContentTuple =
			getBNDFileLocationAndContentTuple(fileName);

		if (bndFileLocationAndContentTuple == null) {
			return null;
		}

		String bndFileLocation =
			(String)bndFileLocationAndContentTuple.getObject(0);

		Properties properties = _bndLanguagePropertiesMap.get(bndFileLocation);

		if (properties != null) {
			return properties;
		}

		String bndContent = (String)bndFileLocationAndContentTuple.getObject(1);

		Matcher matcher = bndContentDirPattern.matcher(bndContent);

		if (matcher.find()) {
			File file = new File(
				bndFileLocation + matcher.group(1) + "/Language.properties");

			if (!file.exists()) {
				return null;
			}

			properties = new Properties();

			InputStream inputStream = new FileInputStream(file);

			properties.load(inputStream);

			_bndLanguagePropertiesMap.put(bndFileLocation, properties);

			return properties;
		}

		return null;
	}

	protected Tuple getBNDFileLocationAndContentTuple(String fileName)
		throws Exception {

		Tuple bndFileLocationAndContentTuple =
			_bndFileLocationAndContentMap.get(fileName);

		if (bndFileLocationAndContentTuple != null) {
			return bndFileLocationAndContentTuple;
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
				String bndContent = FileUtil.read(file);

				bndFileLocationAndContentTuple = new Tuple(
					bndFileLocation, bndContent);

				_bndFileLocationAndContentMap.put(
					fileName, bndFileLocationAndContentTuple);

				return bndFileLocationAndContentTuple;
			}

			bndFileLocation = StringUtil.replaceLast(
				bndFileLocation, StringPool.SLASH, StringPool.BLANK);
		}
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

		for (int i = 0; i < 3; i++) {
			fileNames = getFileNames(basedir, new String[0], includes);

			if (!fileNames.isEmpty()) {
				break;
			}

			basedir = "../" + basedir;
		}

		for (String fileName : fileNames) {
			if (!fileName.startsWith(
					sourceFormatterArgs.getBaseDirName() + "shared")) {

				break;
			}

			File file = new File(basedir + fileName);

			String content = FileUtil.read(file);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			fileName = StringUtil.replace(
				fileName, StringPool.SLASH, StringPool.PERIOD);

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
		for (int i = 0; i < level; i++) {
			File file = new File(
				sourceFormatterArgs.getBaseDirName() + fileName);

			if (file.exists()) {
				return file;
			}

			fileName = "../" + fileName;
		}

		return null;
	}

	protected List<String> getFileNames(
			String basedir, List<String> recentChangesFileNames,
			String[] excludes, String[] includes)
		throws Exception {

		if (_excludes != null) {
			excludes = ArrayUtil.append(excludes, _excludes);
		}

		return _sourceFormatterHelper.getFileNames(
			basedir, recentChangesFileNames, excludes, includes);
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

	protected int getLineCount(String content, int pos) {
		String beforePos = content.substring(0, pos);

		return StringUtil.count(beforePos, StringPool.NEW_LINE) + 1;
	}

	protected String getMainReleaseVersion() {
		if (_mainReleaseVersion != null) {
			return _mainReleaseVersion;
		}

		String releaseVersion = ReleaseInfo.getVersion();

		int pos = releaseVersion.lastIndexOf(CharPool.PERIOD);

		_mainReleaseVersion = releaseVersion.substring(0, pos) + ".0";

		return _mainReleaseVersion;
	}

	protected String getModuleLangDir(String moduleLocation) {
		int x = moduleLocation.lastIndexOf(StringPool.SLASH);

		String baseModuleName = moduleLocation.substring(0, x);

		int y = baseModuleName.lastIndexOf(StringPool.SLASH);

		baseModuleName = baseModuleName.substring(
			y + 1, baseModuleName.length());

		return moduleLocation.substring(0, x + 1) + baseModuleName + "-lang";
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

		Matcher matcher = langMergerPluginPattern.matcher(buildGradleContent);

		if (!matcher.find()) {
			return null;
		}

		String moduleLocation = StringUtil.replaceLast(
			buildGradleFileLocation, StringPool.SLASH, StringPool.BLANK);

		String moduleLangDir = getModuleLangDir(moduleLocation);

		String moduleLangLanguagePropertiesFileName =
			moduleLangDir + "/src/main/resources/content/Language.properties";

		File file = new File(moduleLangLanguagePropertiesFileName);

		if (!file.exists()) {
			return null;
		}

		properties = new Properties();

		InputStream inputStream = new FileInputStream(file);

		properties.load(inputStream);

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

	protected String getProperty(String key) {
		return _properties.getProperty(key);
	}

	protected List<String> getPropertyList(String key) {
		return ListUtil.fromString(
			GetterUtil.getString(getProperty(key)), StringPool.COMMA);
	}

	protected List<String> getRunOutsidePortalExcludes() {
		if (_runOutsidePortalExcludes != null) {
			return _runOutsidePortalExcludes;
		}

		List<String> runOutsidePortalExcludes = getPropertyList(
			"run.outside.portal.excludes");

		_runOutsidePortalExcludes = runOutsidePortalExcludes;

		return _runOutsidePortalExcludes;
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

		boolean containsCompareOperator =
			(s.contains(" == ") || s.contains(" != ") || s.contains(" < ") ||
			 s.contains(" > ") || s.contains(" =< ") || s.contains(" => ") ||
			 s.contains(" <= ") || s.contains(" >= "));
		boolean containsMathOperator =
			(s.contains(" = ") || s.contains(" - ") || s.contains(" + ") ||
			 s.contains(" & ") || s.contains(" % ") || s.contains(" * ") ||
			 s.contains(" / "));

		if (containsCompareOperator &&
			(containsAndOperator || containsOrOperator ||
			 (containsMathOperator && !s.contains(StringPool.OPEN_BRACKET)))) {

			return true;
		}

		return false;
	}

	protected boolean hasRedundantParentheses(String s) {
		if (!s.contains("&&") && !s.contains("||")) {
			for (int x = 0;;) {
				x = s.indexOf(CharPool.CLOSE_PARENTHESIS);

				if (x == -1) {
					break;
				}

				int y = s.substring(0, x).lastIndexOf(
					CharPool.OPEN_PARENTHESIS);

				if (y == -1) {
					break;
				}

				s = s.substring(0, y) + s.substring(x + 1);
			}
		}

		if (Validator.isNotNull(s) && !s.contains(StringPool.SPACE)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean hasRedundantParentheses(
		String s, String operator1, String operator2) {

		String[] parts = StringUtil.split(s, operator1);

		if (parts.length < 3) {
			return false;
		}

		for (int i = 1; i < (parts.length - 1); i++) {
			String part = parts[i];

			if (part.contains(operator2) || part.contains("!(")) {
				continue;
			}

			int closeParenthesesCount = StringUtil.count(
				part, StringPool.CLOSE_PARENTHESIS);
			int openParenthesesCount = StringUtil.count(
				part, StringPool.OPEN_PARENTHESIS);

			if (Math.abs(closeParenthesesCount - openParenthesesCount) == 1) {
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

	protected boolean isModulesFile(String absolutePath) {
		return absolutePath.contains("/modules/");
	}

	protected void postFormat() throws Exception {
	}

	protected void printError(String fileName, String message) {
		_sourceFormatterHelper.printError(fileName, message);
	}

	protected void processErrorMessage(String fileName, String message) {
		List<String> errorMessages = _errorMessagesMap.get(fileName);

		if (errorMessages == null) {
			errorMessages = new ArrayList<>();
		}

		errorMessages.add(message);

		_errorMessagesMap.put(fileName, errorMessages);
	}

	protected void processFormattedFile(
			File file, String fileName, String content, String newContent)
		throws IOException {

		if (sourceFormatterArgs.isPrintErrors()) {
			List<String> errorMessages = _errorMessagesMap.get(fileName);

			if (errorMessages != null) {
				for (String errorMessage : errorMessages) {
					_sourceFormatterHelper.printError(fileName, errorMessage);
				}
			}
		}

		_modifiedFileNames.add(file.getAbsolutePath());

		if (content.equals(newContent)) {
			return;
		}

		if (sourceFormatterArgs.isAutoFix()) {
			FileUtil.write(file, newContent);
		}
		else if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException = new SourceMismatchException(
				fileName, content, newContent);
		}

		if (sourceFormatterArgs.isPrintErrors()) {
			_sourceFormatterHelper.printError(fileName, file);
		}
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

	protected String sortAttributes(
			String fileName, String line, int lineCount,
			boolean allowApostropheDelimeter)
		throws Exception {

		String s = line;

		int x = s.indexOf(CharPool.LESS_THAN);
		int y = s.indexOf(CharPool.SPACE);

		if ((x == -1) || (x >= y)) {
			return line;
		}

		String tag = s.substring(x + 1, y);

		s = s.substring(y + 1);

		String previousAttribute = null;
		String previousAttributeAndValue = null;

		boolean wrongOrder = false;

		for (x = 0;;) {
			x = s.indexOf(CharPool.EQUAL);

			if ((x == -1) || (s.length() <= (x + 1))) {
				return line;
			}

			String attribute = s.substring(0, x);

			if (!isAttributName(attribute)) {
				return line;
			}

			if (Validator.isNotNull(previousAttribute) &&
				(previousAttribute.compareToIgnoreCase(attribute) > 0)) {

				wrongOrder = true;
			}

			s = s.substring(x + 1);

			char delimeter = s.charAt(0);

			if ((delimeter != CharPool.APOSTROPHE) &&
				(delimeter != CharPool.QUOTE)) {

				if (delimeter != CharPool.AMPERSAND) {
					processErrorMessage(
						fileName, "delimeter: " + fileName + " " + lineCount);
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

			if (delimeter == CharPool.APOSTROPHE) {
				if (!value.contains(StringPool.QUOTE)) {
					line = StringUtil.replace(
						line,
						StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
						StringPool.QUOTE + value + StringPool.QUOTE);

					return sortAttributes(
						fileName, line, lineCount, allowApostropheDelimeter);
				}
				else if (!allowApostropheDelimeter) {
					String newValue = StringUtil.replace(
						value, StringPool.QUOTE, "&quot;");

					line = StringUtil.replace(
						line,
						StringPool.APOSTROPHE + value + StringPool.APOSTROPHE,
						StringPool.QUOTE + newValue + StringPool.QUOTE);

					return sortAttributes(
						fileName, line, lineCount, allowApostropheDelimeter);
				}
			}

			StringBundler sb = new StringBundler(5);

			sb.append(attribute);
			sb.append(StringPool.EQUAL);
			sb.append(delimeter);
			sb.append(value);
			sb.append(delimeter);

			String currentAttributeAndValue = sb.toString();

			String newLine = sortHTMLAttributes(
				line, value, currentAttributeAndValue);

			if (!newLine.equals(line)) {
				return sortAttributes(
					fileName, newLine, lineCount, allowApostropheDelimeter);
			}

			newLine = formatTagAttributeType(
				line, tag, currentAttributeAndValue);

			if (!newLine.equals(line)) {
				return sortAttributes(
					fileName, newLine, lineCount, allowApostropheDelimeter);
			}

			if (wrongOrder) {
				if ((StringUtil.count(line, currentAttributeAndValue) == 1) &&
					(StringUtil.count(line, previousAttributeAndValue) == 1)) {

					line = StringUtil.replaceFirst(
						line, previousAttributeAndValue,
						currentAttributeAndValue);

					line = StringUtil.replaceLast(
						line, currentAttributeAndValue,
						previousAttributeAndValue);

					return sortAttributes(
						fileName, line, lineCount, allowApostropheDelimeter);
				}

				return line;
			}

			s = s.substring(y + 1);

			if (s.startsWith(StringPool.GREATER_THAN)) {
				x = s.indexOf(CharPool.SPACE);

				if (x == -1) {
					return line;
				}

				s = s.substring(x + 1);

				previousAttribute = null;
				previousAttributeAndValue = null;
			}
			else {
				s = StringUtil.trimLeading(s);

				previousAttribute = attribute;
				previousAttributeAndValue = currentAttributeAndValue;
			}
		}
	}

	protected String sortHTMLAttributes(
		String line, String value, String attributeAndValue) {

		return line;
	}

	protected String stripRedundantParentheses(String s) {
		for (int x = 0;;) {
			x = s.indexOf(CharPool.OPEN_PARENTHESIS, x + 1);
			int y = s.indexOf(CharPool.CLOSE_PARENTHESIS, x);

			if ((x == -1) || (y == -1)) {
				return s;
			}

			String linePart = s.substring(x + 1, y);

			linePart = StringUtil.replace(
				linePart, StringPool.COMMA, StringPool.BLANK);

			if (Validator.isAlphanumericName(linePart) ||
				Validator.isNull(linePart)) {

				s = s.substring(0, x) + s.substring(y + 1);
			}
		}
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

		if (allowLeadingSpaces || !line.startsWith(StringPool.SPACE) ||
			line.startsWith(" *")) {

			return line;
		}

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

		return line;
	}

	protected static Pattern attributeNamePattern = Pattern.compile(
		"[a-z]+[-_a-zA-Z0-9]*");
	protected static Pattern bndContentDirPattern = Pattern.compile(
		"\tcontent=(.*?)(,\\\\|\n)");
	protected static Pattern emptyCollectionPattern = Pattern.compile(
		"Collections\\.EMPTY_(LIST|MAP|SET)");
	protected static Pattern javaSourceInsideJSPTagPattern = Pattern.compile(
		"<%=(.+?)%>");
	protected static Pattern langMergerPluginPattern = Pattern.compile(
		"^apply[ \t]+plugin[ \t]*:[ \t]+\"com.liferay.lang.merger\"$",
		Pattern.MULTILINE);
	protected static Pattern languageKeyPattern = Pattern.compile(
		"LanguageUtil.(?:get|format)\\([^;%]+|Liferay.Language.get\\('([^']+)");
	protected static boolean portalSource;
	protected static Pattern principalExceptionPattern = Pattern.compile(
		"SessionErrors\\.contains\\(\n?\t*(renderR|r)equest, " +
			"PrincipalException\\.class\\.getName\\(\\)");
	protected static Pattern sessionKeyPattern = Pattern.compile(
		"SessionErrors.(?:add|contains|get)\\([^;%&|!]+|".concat(
			"SessionMessages.(?:add|contains|get)\\([^;%&|!]+"),
		Pattern.MULTILINE);
	protected static Pattern taglibSessionKeyPattern = Pattern.compile(
		"<liferay-ui:error [^>]+>|<liferay-ui:success [^>]+>",
		Pattern.MULTILINE);

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

	private Properties _getProperties() throws Exception {
		String fileName = "source-formatter.properties";

		Properties properties = new Properties();

		List<Properties> propertiesList = new ArrayList<>();

		int level = 2;

		if (portalSource) {
			level = PORTAL_MAX_DIR_LEVEL;
		}

		for (int i = 0; i <= level; i++) {
			try {
				InputStream inputStream = new FileInputStream(
					sourceFormatterArgs.getBaseDirName() + fileName);

				Properties props = new Properties();

				props.load(inputStream);

				propertiesList.add(props);

				break;
			}
			catch (FileNotFoundException fnfe) {
			}

			fileName = "../" + fileName;
		}

		if (propertiesList.isEmpty()) {
			return properties;
		}

		properties = propertiesList.get(0);

		if (propertiesList.size() == 1) {
			return properties;
		}

		for (int i = 1; i < propertiesList.size(); i++) {
			Properties props = propertiesList.get(i);

			Enumeration<String> enu =
				(Enumeration<String>)props.propertyNames();

			while (enu.hasMoreElements()) {
				String key = enu.nextElement();

				String value = props.getProperty(key);

				if (Validator.isNull(value)) {
					continue;
				}

				if (key.contains("excludes")) {
					String existingValue = properties.getProperty(key);

					if (Validator.isNotNull(existingValue)) {
						value = existingValue + StringPool.COMMA + value;
					}

					properties.put(key, value);
				}
				else if (!properties.containsKey(key)) {
					properties.put(key, value);
				}
			}
		}

		return properties;
	}

	private void _init() {
		portalSource = _isPortalSource();

		_errorMessagesMap = new HashMap<>();

		try {
			_properties = _getProperties();

			_sourceFormatterHelper = new SourceFormatterHelper(
				sourceFormatterArgs.isUseProperties());

			_sourceFormatterHelper.init();
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
		else {
			return false;
		}
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
	private Map<String, Tuple> _bndFileLocationAndContentMap = new HashMap<>();
	private Map<String, Properties> _bndLanguagePropertiesMap = new HashMap<>();
	private Map<String, String> _compatClassNamesMap;
	private String _copyright;
	private Map<String, List<String>> _errorMessagesMap = new HashMap<>();
	private String[] _excludes;
	private SourceMismatchException _firstSourceMismatchException;
	private Set<String> _immutableFieldTypes;
	private String _mainReleaseVersion;
	private final List<String> _modifiedFileNames = new ArrayList<>();
	private Map<String, Properties> _moduleLangLanguageProperties =
		new HashMap<>();
	private Map<String, Properties> _moduleLanguageProperties = new HashMap<>();
	private String _oldCopyright;
	private Properties _portalLanguageProperties;
	private Properties _properties;
	private List<String> _runOutsidePortalExcludes;
	private SourceFormatterHelper _sourceFormatterHelper;
	private boolean _usePortalCompatImport;

}