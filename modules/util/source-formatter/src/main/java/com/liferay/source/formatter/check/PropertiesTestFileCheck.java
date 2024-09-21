/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.comparator.PropertyNameComparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesTestFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/test.properties")) {
			return content;
		}

		_checkTestPropertiesOrder(fileName, content);

		return _sortTestCategories(
			fileName, content, StringPool.BLANK,
			StringPool.POUND + StringPool.POUND);
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
						"Incorrect order of properties: '", propertyKey,
						"' should come before '", previousPropertyKey, "'"),
					lineNumber);
			}

			previousLine = line;
			previousPropertyKey = propertyKey;
		}
	}

	private String _sortTestCategories(
		String fileName, String content, String indent, String pounds) {

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
					fileName, properties, indent + StringPool.FOUR_SPACES,
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

	private class CommentComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String comment1, String comment2) {
			if (comment1.equals(" Default")) {
				return -1;
			}

			if (comment2.equals(" Default")) {
				return 1;
			}

			return super.compare(comment1, comment2);
		}

	}

}