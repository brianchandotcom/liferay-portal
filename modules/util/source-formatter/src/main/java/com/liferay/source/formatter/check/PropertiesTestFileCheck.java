/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.comparator.PropertyNameComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesTestFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.contains("/dependencies/") ||
			!fileName.endsWith("/test.properties")) {

			return content;
		}

		String rootDirName = SourceUtil.getRootDirName(absolutePath);

		if (!rootDirName.equals(StringPool.BLANK) &&
			absolutePath.equals(rootDirName + "/test.properties")) {

			_checkTestPropertiesOrder(fileName, content);
		}
		else {
			content = _generateProperties(absolutePath, content);

			content = StringUtil.trimTrailing(content);
		}

		content = _formatSQLQuery(content);

		while (true) {
			String sortedTestCategories = _sortTestCategories(
				content, StringPool.BLANK, StringPool.POUND + StringPool.POUND);

			if (content.equals(sortedTestCategories)) {
				break;
			}

			content = sortedTestCategories;
		}

		if (isAttributeValue(
				_CHECK_TESTRAY_MAIN_COMPONENT_NAME_KEY, absolutePath)) {

			_checkTestrayMainComponentName(fileName, absolutePath, content);
		}

		return content;
	}

	private String _addParenthesis(String sqlClauses) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(sqlClauses))) {

			String line = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = StringUtil.trimLeading(line);

				if (line.startsWith("(") || line.startsWith(")")) {
					sb.append(line);
					sb.append("\n");

					continue;
				}

				int x = StringUtil.indexOfAny(
					line, new String[] {" AND \\", " OR \\"});

				if (x == -1) {
					x = line.lastIndexOf("\\");
				}

				if (x == -1) {
					sb.append("(");
					sb.append(line);
					sb.append(")");
					sb.append("\n");

					continue;
				}

				sb.append("(");
				sb.append(line.substring(0, x));
				sb.append(")");
				sb.append(line.substring(x));
				sb.append("\n");
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private Map<String, Map<String, String>> _categorizeProperties(
			String absolutePath, String content)
		throws IOException {

		Map<String, Map<String, String>> propertiesMap = new HashMap<>();

		List<String> categorizedPropertiesPatterns = getAttributeValues(
			_CATEGORIZED_PROPERTIES_PATTERNS_KEY, absolutePath);

		Properties testProperties = new Properties();

		testProperties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)testProperties.propertyNames();

		outerLoop:
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			String value = testProperties.getProperty(key);

			for (String categorizedPropertiesPattern :
					categorizedPropertiesPatterns) {

				String[] parts = StringUtil.split(
					categorizedPropertiesPattern, StringPool.COLON);

				if (parts.length != 2) {
					continue;
				}

				if (key.matches(parts[1])) {
					String categoryName = parts[0];

					Map<String, String> properties = propertiesMap.get(
						categoryName);

					if (properties == null) {
						properties = new HashMap<>();
					}

					properties.put(key, value);

					propertiesMap.put(categoryName, properties);

					continue outerLoop;
				}
			}

			Map<String, String> properties = propertiesMap.get("Others");

			if (properties == null) {
				properties = new HashMap<>();
			}

			properties.put(key, value);

			propertiesMap.put("Others", properties);
		}

		return propertiesMap;
	}

	private String _checkIndentation(String sqlClauses) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(sqlClauses))) {

			int level = 2;

			String line = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.replaceFirst("^\\( *(\\(.+\\)) *\\)$", "$1");

				sb.append(_fixIndentation(line, level));

				sb.append("\n");

				level += getLevel(line, "(", ")");
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private void _checkTestPropertiesOrder(String fileName, String content) {
		String commentCategory = null;
		String commentPrefix = null;
		String previousLine = null;
		String previousPropertyKey = null;

		String[] lines = content.split("\n");

		int lineNumber = 0;

		for (String line : lines) {
			lineNumber++;

			if ((line.startsWith("##") || line.startsWith("    #")) &&
				!line.contains("=")) {

				if (commentPrefix == null) {
					commentCategory = line;
					commentPrefix = line;

					continue;
				}

				if (line.startsWith(commentPrefix) && !line.contains("=")) {
					commentCategory += "\n" + line;

					continue;
				}
			}

			if ((commentCategory != null) &&
				commentCategory.startsWith(commentPrefix + "\n") &&
				commentCategory.endsWith("\n" + commentPrefix)) {

				commentCategory = null;
				commentPrefix = null;
				previousLine = null;
				previousPropertyKey = null;
			}

			if ((commentCategory != null) && !Validator.isBlank(line) &&
				!line.startsWith(StringPool.FOUR_SPACES)) {

				addMessage(
					fileName, "Incorrect indentation on line " + lineNumber);

				return;
			}

			int x = line.indexOf('=');

			if ((x == -1) ||
				((previousLine != null) && previousLine.endsWith("\\"))) {

				previousLine = line;

				continue;
			}

			String propertyKey = StringUtil.trimLeading(line.substring(0, x));

			if (propertyKey.startsWith(StringPool.POUND)) {
				propertyKey = propertyKey.substring(1);
			}

			if (Validator.isNull(previousPropertyKey)) {
				previousLine = line;
				previousPropertyKey = propertyKey;

				continue;
			}

			PropertyNameComparator propertyNameComparator =
				new PropertyNameComparator();

			int compare = propertyNameComparator.compare(
				previousPropertyKey, propertyKey);

			if (compare > 0) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Incorrect order of properties: \"", propertyKey,
						"\" should come before \"", previousPropertyKey, "\""),
					lineNumber);
			}

			previousLine = line;
			previousPropertyKey = propertyKey;
		}
	}

	private void _checkTestrayMainComponentName(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!isModulesFile(absolutePath) ||
			absolutePath.contains("/modules/apps/archived/")) {

			return;
		}

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		String testrayMainComponentName = properties.getProperty(
			"testray.main.component.name");

		if (testrayMainComponentName == null) {
			return;
		}

		List<String> testrayAllTeamsComponentNames =
			_getTestrayAllTeamsComponentNames();

		if (!testrayAllTeamsComponentNames.contains(testrayMainComponentName)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Property value \"", testrayMainComponentName,
					"\" does not exist in \"testray.team.*.component.names\" ",
					"in ", SourceUtil.getRootDirName(absolutePath),
					"/test.properties"));
		}
	}

	private int _compareTo(String sqlClause, String nextSQLClause) {
		if (sqlClause.endsWith("\")") && nextSQLClause.endsWith("\")")) {
			sqlClause = sqlClause.substring(0, sqlClause.length() - 2);
			nextSQLClause = nextSQLClause.substring(
				0, nextSQLClause.length() - 2);
		}

		return sqlClause.compareTo(nextSQLClause);
	}

	private String _fixIndentation(String line, int level) {
		String trimmedLine = StringUtil.trim(line);

		if (Validator.isNull(trimmedLine)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < level; i++) {
			if ((i == (level - 1)) && trimmedLine.startsWith(")")) {
				break;
			}

			sb.append(StringPool.FOUR_SPACES);
		}

		sb.append(trimmedLine);

		return sb.toString();
	}

	private String _formatSQLQuery(String content) throws IOException {
		StringBuffer sb = new StringBuffer();

		Matcher matcher = _sqlPattern1.matcher(content);

		outerLoop:
		while (matcher.find()) {
			String originalSqlClauses = matcher.group(2);

			String sqlClauses = originalSqlClauses.replaceAll("\\\\\n *", "");

			sqlClauses = sqlClauses.replaceAll("\\( +\\(", "((");
			sqlClauses = sqlClauses.replaceAll("\\) +\\)", "))");

			int x = sqlClauses.indexOf("(");

			if (x == -1) {
				continue;
			}

			int y = x;
			String s = StringPool.BLANK;

			while (true) {
				y = sqlClauses.indexOf(")", y + 1);

				if (y == -1) {
					continue outerLoop;
				}

				s = sqlClauses.substring(x, y + 1);

				int level = getLevel(s);

				if (level != 0) {
					continue;
				}

				sqlClauses = StringUtil.replaceFirst(
					sqlClauses, s, _removeRedundantParenthesis(s), x);

				x = sqlClauses.indexOf("(", x + 1);

				if (x == -1) {
					break;
				}

				y = x;
			}

			sqlClauses = StringUtil.replace(sqlClauses, " AND ", " AND \\\n");
			sqlClauses = StringUtil.replace(sqlClauses, " OR ", " OR \\\n");

			sqlClauses = _addParenthesis(sqlClauses);

			sqlClauses = _checkIndentation(sqlClauses);

			sqlClauses = _sort(sqlClauses);

			sqlClauses = "\\\n" + sqlClauses;

			if (originalSqlClauses.endsWith("\\\n")) {
				sqlClauses = sqlClauses + "\n";
			}

			if (!sqlClauses.equals(originalSqlClauses)) {
				String replacement = StringUtil.replaceFirst(
					matcher.group(), matcher.group(2), sqlClauses);

				matcher.appendReplacement(
					sb, Matcher.quoteReplacement(replacement));
			}
		}

		if (sb.length() > 0) {
			matcher.appendTail(sb);

			return sb.toString();
		}

		return content;
	}

	private String _generateProperties(String absolutePath, String content)
		throws IOException {

		Map<String, Map<String, String>> categorizedProperties =
			_categorizeProperties(absolutePath, content);

		if (MapUtil.isEmpty(categorizedProperties)) {
			return content;
		}

		List<String> categoriesLevels = getAttributeValues(
			_CATEGORIES_LEVELS_KEY, absolutePath);

		if (ListUtil.isEmpty(categoriesLevels)) {
			return content;
		}

		String properties = "";

		for (String categoriesLevel : categoriesLevels) {
			String mergedSubcategoriesProperties = "";

			String[] parts = StringUtil.split(
				categoriesLevel, StringPool.COLON);

			for (int j = parts.length - 1; j > 0; j--) {
				String mergedSubcategoryProperties = _mergeProperties(
					false, parts[j], categorizedProperties.get(parts[j]));

				mergedSubcategoriesProperties =
					mergedSubcategoryProperties + mergedSubcategoriesProperties;
			}

			String mergedTopCategoryProperties = _mergeProperties(
				true, parts[0], categorizedProperties.get(parts[0]));

			if (Validator.isBlank(mergedSubcategoriesProperties) &&
				!mergedTopCategoryProperties.contains("=")) {

				continue;
			}

			properties =
				mergedTopCategoryProperties + mergedSubcategoriesProperties +
					properties;
		}

		return properties;
	}

	private String _getSQLClause(String line) {
		Matcher matcher = _sqlPattern2.matcher(line);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private synchronized List<String> _getTestrayAllTeamsComponentNames()
		throws IOException {

		if (_testrayAllTeamsComponentNames != null) {
			return _testrayAllTeamsComponentNames;
		}

		_testrayAllTeamsComponentNames = new ArrayList<>();

		File file = new File(getPortalDir(), "test.properties");

		if (!file.exists()) {
			return _testrayAllTeamsComponentNames;
		}

		Properties properties = new Properties();

		properties.load(new FileInputStream(file));

		List<String> testrayAvailableComponentNames = ListUtil.fromString(
			properties.getProperty("testray.available.component.names"),
			StringPool.COMMA);

		for (String testrayAvailableComponentName :
				testrayAvailableComponentNames) {

			if (!testrayAvailableComponentName.startsWith("${") &&
				!testrayAvailableComponentName.endsWith("}")) {

				continue;
			}

			String testrayTeamComponentName =
				testrayAvailableComponentName.substring(
					2, testrayAvailableComponentName.length() - 1);

			List<String> testrayTeamComponentNames = ListUtil.fromString(
				properties.getProperty(testrayTeamComponentName),
				StringPool.COMMA);

			if (ListUtil.isEmpty(testrayTeamComponentNames)) {
				continue;
			}

			_testrayAllTeamsComponentNames.addAll(testrayTeamComponentNames);
		}

		return _testrayAllTeamsComponentNames;
	}

	private String _mergeProperties(
		boolean topLevel, String categoryName,
		Map<String, String> properitesMap) {

		if (MapUtil.isEmpty(properitesMap) && !topLevel) {
			return StringPool.BLANK;
		}

		String comment = StringPool.FOUR_SPACES;

		if (topLevel) {
			comment = StringPool.POUND;
		}

		comment = comment + StringPool.POUND;

		String categoryHeader = StringBundler.concat(
			comment, "\n", comment, " ", categoryName, "\n", comment, "\n\n");

		if (MapUtil.isEmpty(properitesMap) && topLevel) {
			return categoryHeader;
		}

		StringBundler sb = new StringBundler(categoryHeader);

		List<String> keys = new ArrayList<>(properitesMap.keySet());

		Collections.sort(keys, new PropertyNameComparator());

		for (int i = 0; i < keys.size(); i++) {
			boolean multiLine = false;

			String key = keys.get(i);

			String[] values = StringUtil.split(properitesMap.get(key));

			if ((values.length == 1) && !StringUtil.equals(values[0], "**")) {
				int lineLength = 5 + key.length() + values[0].length();

				if (lineLength > getMaxLineLength()) {
					multiLine = true;
				}
			}
			else if (values.length > 1) {
				multiLine = true;
			}

			if (multiLine && !StringUtil.endsWith(sb.toString(), "\n\n")) {
				sb.append("\n");
			}

			sb.append("    ");
			sb.append(key);
			sb.append("=");

			if (multiLine) {
				for (String value : values) {
					sb.append("\\\n        ");
					sb.append(value);
					sb.append(",");
				}

				sb.setIndex(sb.index() - 1);

				sb.append("\n\n");
			}
			else {
				sb.append(values);

				if (i == (keys.size() - 1)) {
					sb.append("\n");
				}

				sb.append("\n");
			}
		}

		return sb.toString();
	}

	private String _removeRedundantParenthesis(String sqlClause) {
		int x = -1;

		while (true) {
			x = StringUtil.indexOfAny(
				sqlClause, new String[] {" AND ", " OR "}, x + 1);

			if (x == -1) {
				break;
			}

			int level1 = getLevel(sqlClause.substring(0, x));
			int level2 = getLevel(sqlClause.substring(x));

			if ((level1 == 1) && (level2 == -1)) {
				sqlClause = StringUtil.insert(
					sqlClause, "\\\n", sqlClause.length() - 1);
				sqlClause = StringUtil.insert(sqlClause, "\\\n", 1);

				return sqlClause;
			}
		}

		if (sqlClause.startsWith("((")) {
			return _removeRedundantParenthesis(
				sqlClause.substring(1, sqlClause.length() - 1));
		}

		return sqlClause;
	}

	private String _sort(String sqlClauses) {
		Matcher matcher = _sqlPattern2.matcher(sqlClauses);

		while (matcher.find()) {
			int lineNumber = getLineNumber(sqlClauses, matcher.start());

			if (Validator.isNull(matcher.group(4))) {
				continue;
			}

			String nextSQLClause = _getSQLClause(
				SourceUtil.getLine(sqlClauses, lineNumber + 1));

			if (nextSQLClause == null) {
				continue;
			}

			String sqlClause = matcher.group(1);

			if (_compareTo(sqlClause, nextSQLClause) > 0) {
				sqlClauses = StringUtil.replaceFirst(
					sqlClauses, nextSQLClause, sqlClause,
					getLineStartPos(sqlClauses, lineNumber + 1));

				return StringUtil.replaceFirst(
					sqlClauses, sqlClause, nextSQLClause,
					getLineStartPos(sqlClauses, lineNumber));
			}
		}

		return sqlClauses;
	}

	private String _sortTestCategories(
		String content, String indent, String pounds) {

		String indentWithPounds = indent + pounds;

		CommentComparator comparator = new CommentComparator();

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"((?<=\\A|\n\n)", indentWithPounds, "\n", indentWithPounds,
				"( .+)\n", indentWithPounds, "\n\n[\\s\\S]*?)(?=(\n\n",
				indentWithPounds, "\n|\\Z))"));

		Matcher matcher = pattern.matcher(content);

		String previousProperties = null;
		String previousPropertiesComment = null;
		int previousPropertiesStartPosition = -1;

		while (matcher.find()) {
			String properties = matcher.group(1);
			String propertiesComment = matcher.group(2);
			int propertiesStartPosition = matcher.start();

			if (pounds.length() == 2) {
				String newProperties = _sortTestCategories(
					properties, indent + StringPool.FOUR_SPACES,
					StringPool.POUND);

				if (!newProperties.equals(properties)) {
					return StringUtil.replaceFirst(
						content, properties, newProperties,
						propertiesStartPosition);
				}
			}

			if (Validator.isNull(previousProperties)) {
				previousProperties = properties;
				previousPropertiesComment = propertiesComment;
				previousPropertiesStartPosition = propertiesStartPosition;

				continue;
			}

			int value = comparator.compare(
				previousPropertiesComment, propertiesComment);

			if (value > 0) {
				content = StringUtil.replaceFirst(
					content, properties, previousProperties,
					propertiesStartPosition);
				content = StringUtil.replaceFirst(
					content, previousProperties, properties,
					previousPropertiesStartPosition);

				return content;
			}

			previousProperties = properties;
			previousPropertiesComment = propertiesComment;
			previousPropertiesStartPosition = propertiesStartPosition;
		}

		return content;
	}

	private static final String _CATEGORIES_LEVELS_KEY = "categoriesLevels";

	private static final String _CATEGORIZED_PROPERTIES_PATTERNS_KEY =
		"categorizedPropertiesPatterns";

	private static final String _CHECK_TESTRAY_MAIN_COMPONENT_NAME_KEY =
		"checkTestrayMainComponentName";

	private static final Pattern _sqlPattern1 = Pattern.compile(
		"(?<=\\A|\n) +test\\.batch\\.run\\.property(\\.global)?\\.query.+]=" +
			"([\\s\\S]*?[^\\\\])(?=(\\Z|\n))");
	private static final Pattern _sqlPattern2 = Pattern.compile(
		"\\s(\\(.* ([!=]=|~) .+\\))( (AND|OR) )?(\\\\)?");

	private List<String> _testrayAllTeamsComponentNames;

	private class CommentComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String comment1, String comment2) {
			if (comment1.equals(" Default") || comment2.equals(" Others")) {
				return -1;
			}

			if (comment2.equals(" Default") || comment1.equals(" Others")) {
				return 1;
			}

			return super.compare(comment1, comment2);
		}

	}

}