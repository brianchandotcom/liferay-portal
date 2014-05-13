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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

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
	public void format(
			boolean useProperties, boolean printErrors, boolean autoFix,
			String mainReleaseVersion)
		throws Exception {

		_init(useProperties, printErrors, autoFix, mainReleaseVersion);

		format();

		sourceFormatterHelper.close();
	}

	@Override
	public String format(
			String fileName, boolean useProperties, boolean printErrors,
			boolean autoFix, String mainReleaseVersion)
		throws Exception {

		try {
			_init(useProperties, printErrors, autoFix, mainReleaseVersion);

			return format(fileName);
		}
		finally {
			sourceFormatterHelper.close();
		}
	}

	@Override
	public List<String> getErrorMessages() {
		return _errorMessages;
	}

	@Override
	public SourceMismatchException getFirstSourceMismatchException() {
		return _firstSourceMismatchException;
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

		if (mainReleaseVersion.equals(MAIN_RELEASE_VERSION_6_1_0) ||
			isRunsOutsidePortal(absolutePath)) {

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
					languageKey.endsWith(StringPool.OPEN_BRACKET) ||
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

	protected void compareAndAutoFixContent(
			File file, String fileName, String content, String newContent)
		throws IOException {

		if (content.equals(newContent)) {
			return;
		}

		fileName = StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);

		if (_autoFix) {
			fileUtil.write(file, newContent);
		}
		else if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				new SourceMismatchException(fileName, content, newContent);
		}

		if (_printErrors) {
			sourceFormatterHelper.printError(fileName, file);
		}
	}

	protected String fixCompatClassImports(String absolutePath, String content)
		throws IOException {

		if (portalSource ||
			!mainReleaseVersion.equals(MAIN_RELEASE_VERSION_6_1_0) ||
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
			String content, String copyright, String oldCopyright,
			String absolutePath, String fileName)
		throws IOException {

		if (fileName.endsWith(".vm")) {
			return content;
		}

		if ((oldCopyright != null) && content.contains(oldCopyright)) {
			content = StringUtil.replace(content, oldCopyright, copyright);

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

	protected abstract void format() throws Exception;

	protected String format(String fileName) throws Exception {
		return null;
	}

	protected Map<String, String> getCompatClassNamesMap() throws IOException {
		if (_compatClassNamesMap != null) {
			return _compatClassNamesMap;
		}

		Map<String, String> compatClassNamesMap = new HashMap<String, String>();

		String[] includes = new String[] {
			"**\\portal-compat-shared\\src\\com\\liferay\\compat\\**\\*.java"
		};

		String basedir = BASEDIR;

		List<String> fileNames = new ArrayList<String>();

		for (int i = 0; i < 3; i++) {
			fileNames = getFileNames(basedir, new String[0], includes);

			if (!fileNames.isEmpty()) {
				break;
			}

			basedir = "../" + basedir;
		}

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

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
				compatClassNamesMap.put(compatClassName, extendedClassName);
			}
		}

		_compatClassNamesMap = compatClassNamesMap;

		return _compatClassNamesMap;
	}

	protected String getCopyright() throws IOException {
		if (Validator.isNotNull(_copyright)) {
			return _copyright;
		}

		_copyright = fileUtil.read("copyright.txt");

		if (Validator.isNull(_copyright)) {
			_copyright = fileUtil.read("../copyright.txt");
		}

		if (Validator.isNull(_copyright)) {
			_copyright = fileUtil.read("../../copyright.txt");
		}

		return _copyright;
	}

	protected String getCustomCopyright(String absolutePath)
		throws IOException {

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

	protected List<Tuple> getExclusionsTuples(String fileName)
		throws IOException {

		List<Tuple> exclusionsTuples = new ArrayList<Tuple>();

		if (portalSource) {
			ClassLoader classLoader =
				BaseSourceProcessor.class.getClassLoader();

			String sourceFormatterExclusions = System.getProperty(
				"source-formatter-exclusions",
				"com/liferay/portal/tools/dependencies/" + fileName);

			URL url = classLoader.getResource(sourceFormatterExclusions);

			if (url != null) {
				exclusionsTuples.add(new Tuple(url.openStream(), 0));
			}

			return exclusionsTuples;
		}

		try {
			exclusionsTuples.add(new Tuple(new FileInputStream(fileName), 0));
		}
		catch (FileNotFoundException fnfe) {
		}

		try {
			exclusionsTuples.add(
				new Tuple(new FileInputStream("../" + fileName), 1));
		}
		catch (FileNotFoundException fnfe) {
		}

		try {
			exclusionsTuples.add(
				new Tuple(new FileInputStream("../../" + fileName), 2));
		}
		catch (FileNotFoundException fnfe) {
		}

		return exclusionsTuples;
	}

	protected Properties getExclusionsProperties(String fileName)
		throws IOException {

		List<Tuple> exclusionsTuples = getExclusionsTuples(fileName);

		if (exclusionsTuples.isEmpty()) {
			return null;
		}

		Properties allProperties = new Properties();

		for (Tuple exclusionsTuple : exclusionsTuples) {
			InputStream inputStream = (InputStream)exclusionsTuple.getObject(0);

			Properties properties = new Properties();

			properties.load(inputStream);

			inputStream.close();

			if (!portalSource) {
				int pluginsDirectoryLevel = (Integer)exclusionsTuple.getObject(
					1);

				properties = stripTopLevelDirectories(
					properties, pluginsDirectoryLevel);
			}

			allProperties.putAll(properties);
		}

		return allProperties;
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

	protected String getOldCopyright() throws IOException {
		if (Validator.isNotNull(_oldCopyright)) {
			return _oldCopyright;
		}

		_oldCopyright = fileUtil.read("old-copyright.txt");

		if (Validator.isNull(_oldCopyright)) {
			_oldCopyright = fileUtil.read("../old-copyright.txt");
		}

		if (Validator.isNull(_oldCopyright)) {
			_oldCopyright = fileUtil.read("../../old-copyright.txt");
		}

		return _oldCopyright;
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

	protected boolean isExcluded(Properties properties, String fileName) {
		return isExcluded(properties, fileName, -1);
	}

	protected boolean isExcluded(
		Properties properties, String fileName, int lineCount) {

		return isExcluded(properties, fileName, lineCount, null);
	}

	protected boolean isExcluded(
		Properties properties, String fileName, int lineCount,
		String javaTermName) {

		if (properties == null) {
			return false;
		}

		if (properties.getProperty(fileName) != null) {
			return true;
		}

		if ((lineCount > 0) &&
			(properties.getProperty(fileName + StringPool.AT + lineCount) !=
				null)) {

			return true;
		}

		if (Validator.isNotNull(javaTermName) &&
			(properties.getProperty(fileName + StringPool.AT + javaTermName) !=
				null)) {

			return true;
		}

		return false;
	}

	protected boolean isRunsOutsidePortal(String absolutePath) {
		if (absolutePath.contains("/sync-engine-shared/")) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void processErrorMessage(String fileName, String message) {
		_errorMessages.add(message);

		if (_printErrors) {
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

	protected String sortAttributes(
		String fileName, String line, int lineCount,
		boolean allowApostropheDelimeter) {

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

			if (!isAttributName(attribute)) {
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

				if (delimeter != CharPool.AMPERSAND) {
					processErrorMessage(
						fileName, "delimeter: " + fileName + " " + lineCount);
				}

				return line;
			}

			s = s.substring(1);

			String value = null;

			int y = -1;

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
				x = s.indexOf(StringPool.SPACE);

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

	protected String stripQuotes(String s, char delimeter) {
		boolean insideQuotes = false;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (insideQuotes) {
				if (c == delimeter) {
					if ((c > 1) && (s.charAt(i - 1) == CharPool.BACK_SLASH) &&
						(s.charAt(i - 2) != CharPool.BACK_SLASH)) {

						continue;
					}

					insideQuotes = false;
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

	protected Properties stripTopLevelDirectories(
			Properties properties, int level)
		throws IOException {

		if (level == 0) {
			return properties;
		}

		File dir = new File(".");

		String dirName = dir.getCanonicalPath();

		dirName = StringUtil.replace(
			dirName, StringPool.BACK_SLASH, StringPool.SLASH);

		int pos = dirName.length();

		for (int i = 0; i < level; i++) {
			pos = dirName.lastIndexOf(StringPool.SLASH, pos - 1);
		}

		String topLevelDirNames = dirName.substring(pos + 1) + StringPool.SLASH;

		Properties newProperties = new Properties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();

			if (!key.startsWith(topLevelDirNames)) {
				continue;
			}

			key = StringUtil.replaceFirst(
				key, topLevelDirNames, StringPool.BLANK);

			String value = (String)entry.getValue();

			newProperties.setProperty(key, value);
		}

		return newProperties;
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

	protected static final String MAIN_RELEASE_LATEST_VERSION =
		BaseSourceProcessor.MAIN_RELEASE_VERSION_7_0_0;

	protected static final String MAIN_RELEASE_VERSION_6_1_0 = "6.1.0";

	protected static final String MAIN_RELEASE_VERSION_6_2_0 = "6.2.0";

	protected static final String MAIN_RELEASE_VERSION_7_0_0 = "7.0.0";

	protected static Pattern attributeNamePattern = Pattern.compile(
		"[a-z]+[-_a-zA-Z0-9]*");
	protected static Pattern emptyCollectionPattern = Pattern.compile(
		"Collections\\.EMPTY_(LIST|MAP|SET)");
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

	private String[] _getExcludes() throws IOException {
		List<String> excludesList = ListUtil.fromString(
			GetterUtil.getString(
				System.getProperty("source.formatter.excludes")));

		List<Tuple> exclusionsTuples = getExclusionsTuples(
			"source_formatter_excludes.txt");

		for (Tuple exclusionsTuple : exclusionsTuples) {
			InputStream inputStream = (InputStream)exclusionsTuple.getObject(0);

			StringUtil.readLines(inputStream, excludesList);
		}

		return excludesList.toArray(new String[excludesList.size()]);
	}

	private void _init(
			boolean useProperties, boolean printErrors, boolean autoFix,
			String mainReleaseVersion)
		throws Exception {

		_errorMessages = new ArrayList<String>();

		sourceFormatterHelper = new SourceFormatterHelper(useProperties);

		sourceFormatterHelper.init();

		if (_initialized) {
			return;
		}

		_autoFix = autoFix;

		BaseSourceProcessor.mainReleaseVersion = mainReleaseVersion;

		portalSource = _isPortalSource();

		_excludes = _getExcludes();

		_printErrors = printErrors;

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

	private boolean _autoFix;
	private Map<String, String> _compatClassNamesMap;
	private String _copyright;
	private List<String> _errorMessages = new ArrayList<String>();
	private String[] _excludes;
	private SourceMismatchException _firstSourceMismatchException;
	private boolean _initialized;
	private String _oldCopyright;
	private Properties _portalLanguageKeysProperties;
	private boolean _printErrors;

}