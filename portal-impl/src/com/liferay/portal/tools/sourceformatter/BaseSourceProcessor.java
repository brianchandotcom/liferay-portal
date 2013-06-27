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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public abstract class BaseSourceProcessor implements SourceProcessor {

	@Override
	public void format(boolean useProperties, boolean throwException)
		throws Exception {

		_init(useProperties, throwException);

		doFormat();

		sourceFormatterHelper.close();
	}

	@Override
	public List<String> getErrorMessages() {
		return _errorMessages;
	}

	protected static String formatImports(String imports, int classStartPos)
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

	protected void checkIfClauseParentheses(
		String ifClause, String fileName, int lineCount) {

		int quoteCount = StringUtil.count(ifClause, StringPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return;
		}

		ifClause = stripQuotes(ifClause, StringPool.QUOTE);

		ifClause = stripQuotes(ifClause, StringPool.APOSTROPHE);

		if (ifClause.contains(StringPool.DOUBLE_SLASH) ||
			ifClause.contains("/*") || ifClause.contains("*/")) {

			return;
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

	protected void checkLanguageKeys(
			String fileName, String content, Pattern pattern)
		throws IOException {

		String fileExtension = fileUtil.getExtension(fileName);

		if (!portalSource || fileExtension.equals("vm")) {
			return;
		}

		if (_portalLanguageKeysProperties == null) {
			_portalLanguageKeysProperties = new Properties();

			ClassLoader classLoader =
				BaseSourceProcessor.class.getClassLoader();

			InputStream inputStream = classLoader.getResourceAsStream(
				"content/Language.properties");

			_portalLanguageKeysProperties.load(inputStream);
		}

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String[] languageKeys = getLanguageKeys(matcher);

			for (String languageKey : languageKeys) {
				if (Validator.isNumber(languageKey) ||
					languageKey.endsWith(StringPool.DASH) ||
					languageKey.endsWith(StringPool.PERIOD) ||
					languageKey.endsWith(StringPool.UNDERLINE) ||
					languageKey.startsWith(StringPool.DASH) ||
					languageKey.startsWith(StringPool.OPEN_BRACKET) ||
					languageKey.startsWith(StringPool.OPEN_CURLY_BRACE) ||
					languageKey.startsWith(StringPool.PERIOD) ||
					languageKey.startsWith(StringPool.UNDERLINE)) {

					continue;
				}

				if (!_portalLanguageKeysProperties.containsKey(languageKey)) {
					processErrorMessage(
						fileName,
						"missing language key: " + languageKey +
							StringPool.SPACE + fileName);
				}
			}
		}
	}

	protected abstract void doFormat() throws Exception;

	protected String fixCompatClassImports(String fileName, String content)
		throws IOException {

		if (portalSource ||
			!mainReleaseVersion.equals(MAIN_RELEASE_VERSION_6_1_0) ||
			fileName.contains("/portal-compat-shared/")) {

			return content;
		}

		Map<String, String> compatClassNamesMap = getCompatClassNamesMap();

		String newContent = content;

		for (Map.Entry<String, String> entry : compatClassNamesMap.entrySet()) {
			String compatClassName = entry.getKey();
			String extendedClassName = entry.getValue();

			Pattern pattern = Pattern.compile(extendedClassName + "\\W");

			for (;;) {
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
			String content, String copyright, String oldCopyright, File file,
			String fileName)
		throws IOException {

		if (fileName.endsWith(".vm")) {
			return content;
		}

		if ((oldCopyright != null) && content.contains(oldCopyright)) {
			content = StringUtil.replace(content, oldCopyright, copyright);

			processErrorMessage(fileName, "old (c): " + fileName);
		}

		if (!content.contains(copyright)) {
			String customCopyright = getCustomCopyright(file);

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

		int x = content.indexOf("* Copyright (c) 2000-20");

		if (x == -1) {
			return content;
		}

		int y = copyright.indexOf("* Copyright (c) 2000-20");

		if (y == -1) {
			return content;
		}

		String contentCopyrightYear = content.substring(x, x + 25);
		String copyrightYear = copyright.substring(y, y + 25);

		return StringUtil.replace(content, contentCopyrightYear, copyrightYear);
	}

	protected String fixSessionKey(
		String fileName, String content, Pattern pattern) {

		if (mainReleaseVersion.equals(MAIN_RELEASE_VERSION_6_1_0)) {
			return content;
		}

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

	protected Map<String, String> getCompatClassNamesMap() throws IOException {
		if (_compatClassNamesMap != null) {
			return _compatClassNamesMap;
		}

		_compatClassNamesMap = new HashMap<String, String>();

		String[] includes = new String[] {
			"**\\portal-compat-shared\\src\\com\\liferay\\compat\\**\\*.java"
		};

		List<String> fileNames = getFileNames(new String[0], includes);

		for (String fileName : fileNames) {
			File file = new File(fileName);

			String content = fileUtil.read(file);

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
				_compatClassNamesMap.put(compatClassName, extendedClassName);
			}
		}

		return _compatClassNamesMap;
	}

	protected String getCopyright() throws IOException {
		String copyright = fileUtil.read("copyright.txt");

		if (Validator.isNull(copyright)) {
			copyright = fileUtil.read("../copyright.txt");
		}

		if (Validator.isNull(copyright)) {
			copyright = fileUtil.read("../../copyright.txt");
		}

		return copyright;
	}

	protected String getCustomCopyright(File file) throws IOException {
		String absolutePath = fileUtil.getAbsolutePath(file);

		for (int x = absolutePath.length();;) {
			x = absolutePath.lastIndexOf(StringPool.SLASH, x);

			if (x == -1) {
				break;
			}

			String copyright = fileUtil.read(
				absolutePath.substring(0, x + 1) + "copyright.txt");

			if (Validator.isNotNull(copyright)) {
				return copyright;
			}

			x = x - 1;
		}

		return null;
	}

	protected List<String> getFileNames(
		String basedir, String[] excludes, String[] includes) {

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		excludes = ArrayUtil.append(excludes, _excludes);

		directoryScanner.setExcludes(excludes);

		directoryScanner.setIncludes(includes);

		return sourceFormatterHelper.scanForFiles(directoryScanner);
	}

	protected List<String> getFileNames(String[] excludes, String[] includes) {
		return getFileNames(BASEDIR, excludes, includes);
	}

	protected String[] getLanguageKeys(Matcher matcher) {
		if (matcher.groupCount() > 0) {
			String languageKey = matcher.group(1);

			if (Validator.isNotNull(languageKey)) {
				return new String[] {languageKey};
			}
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

	protected String getOldCopyright() throws IOException {
		String copyright = fileUtil.read("old-copyright.txt");

		if (Validator.isNull(copyright)) {
			copyright = fileUtil.read("../old-copyright.txt");
		}

		if (Validator.isNull(copyright)) {
			copyright = fileUtil.read("../../old-copyright.txt");
		}

		return copyright;
	}

	protected boolean hasMissingParentheses(String s) {
		if (Validator.isNull(s)) {
			return false;
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

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean hasRedundantParentheses(String s) {
		if (!s.contains("&&") && !s.contains("||")) {
			for (int x = 0;;) {
				x = s.indexOf(StringPool.CLOSE_PARENTHESIS);

				if (x == -1) {
					break;
				}

				int y = s.substring(0, x).lastIndexOf(
					StringPool.OPEN_PARENTHESIS);

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

	protected void processErrorMessage(String fileName, String message) {
		if (_throwException) {
			_errorMessages.add(message);
		}
		else {
			sourceFormatterHelper.printError(fileName, message);
		}
	}

	protected String replacePrimitiveWrapperInstantiation(
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
			processErrorMessage(
				fileName, "> new Primitive(: " + fileName + " " + lineCount);
		}

		return newLine;
	}

	protected String stripQuotes(String s, String delimeter) {
		String[] parts = StringUtil.split(s, delimeter);

		int i = 1;

		while (i < parts.length) {
			s = StringUtil.replaceFirst(
				s, delimeter + parts[i] + delimeter, StringPool.BLANK);

			i = i + 2;
		}

		return s;
	}

	protected String stripRedundantParentheses(String s) {
		for (int x = 0;;) {
			x = s.indexOf(StringPool.OPEN_PARENTHESIS, x + 1);
			int y = s.indexOf(StringPool.CLOSE_PARENTHESIS, x);

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

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			sb.append(trimLine(line, allowLeadingSpaces));
			sb.append("\n");
		}

		unsyncBufferedReader.close();

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

	protected static final String BASEDIR = "./";

	protected static final String MAIN_RELEASE_VERSION_6_1_0 = "6.1.0";

	protected static final String MAIN_RELEASE_VERSION_6_2_0 = "6.2.0";

	protected static FileImpl fileUtil = FileImpl.getInstance();
	protected static Pattern languageKeyPattern = Pattern.compile(
		"LanguageUtil.(?:get|format)\\([^;%]+|Liferay.Language.get\\('([^']+)");
	protected static String mainReleaseVersion;
	protected static boolean portalSource;
	protected static SAXReaderImpl saxReaderUtil = SAXReaderImpl.getInstance();
	protected static Pattern sessionKeyPattern = Pattern.compile(
		"SessionErrors.(?:add|contains|get)\\([^;%&|!]+|".concat(
			"SessionMessages.(?:add|contains|get)\\([^;%&|!]+"),
		Pattern.MULTILINE);
	protected static SourceFormatterHelper sourceFormatterHelper;
	protected static Pattern taglibSessionKeyPattern = Pattern.compile(
		"<liferay-ui:error [^>]+>|<liferay-ui:success [^>]+>",
		Pattern.MULTILINE);

	private void _init(boolean useProperties, boolean throwException)
		throws Exception {

		_errorMessages = new ArrayList<String>();

		sourceFormatterHelper = new SourceFormatterHelper(useProperties);

		sourceFormatterHelper.init();

		if (_initialized) {
			return;
		}

		_setVersion();

		_excludes = StringUtil.split(
			GetterUtil.getString(
				System.getProperty("source.formatter.excludes")));

		portalSource = _isPortalSource();

		_throwException = throwException;

		_initialized = true;
	}

	private boolean _isPortalSource() {
		if (fileUtil.exists(BASEDIR + "portal-impl")) {
			return true;
		}
		else {
			return false;
		}
	}

	private void _setVersion() throws Exception {
		String releaseInfoVersion = ReleaseInfo.getVersion();

		if (releaseInfoVersion.startsWith("6.1")) {
			mainReleaseVersion = MAIN_RELEASE_VERSION_6_1_0;
		}
		else if (releaseInfoVersion.startsWith("6.2")) {
			mainReleaseVersion = MAIN_RELEASE_VERSION_6_2_0;
		}
		else {
			throw new Exception(
				"Invalid release information: " + ReleaseInfo.getVersion());
		}
	}

	private Map<String, String> _compatClassNamesMap;
	private static List<String> _errorMessages = new ArrayList<String>();
	private static String[] _excludes;
	private static boolean _initialized;
	private static Properties _portalLanguageKeysProperties;
	private static boolean _throwException;

}