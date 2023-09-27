/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.parser;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleFileParser {

	public static GradleFile parse(String fileName, String content) {
		Set<String> applyPlugins = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		String bodyBlock = StringPool.BLANK;
		String buildScriptBlock = StringPool.BLANK;
		String extScriptBlock = StringPool.BLANK;
		String importsBlock = StringPool.BLANK;
		String initializeBlock = StringPool.BLANK;
		String pluginsScriptBlock = StringPool.BLANK;
		Set<String> properties = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		Set<String> tasks = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		Matcher matcher = _importsPattern.matcher(content);

		while (matcher.find()) {
			importsBlock = importsBlock + matcher.group();

			content = StringUtil.replaceFirst(
				content, matcher.group(), StringPool.BLANK);
		}

		Matcher taskMatcher = _taskPattern.matcher(content);

		for (String line : StringUtil.splitLines(content)) {
			if (Validator.isNull(buildScriptBlock) &&
				line.matches("^buildscript\\s+.*\\{")) {

				buildScriptBlock = line;

				continue;
			}

			if (Validator.isNotNull(buildScriptBlock) &&
				!buildScriptBlock.endsWith("\n}")) {

				buildScriptBlock = buildScriptBlock + "\n" + line;

				continue;
			}

			if (Validator.isNull(extScriptBlock) &&
				line.matches("^ext\\s+.*\\{")) {

				extScriptBlock = line;

				continue;
			}

			if (Validator.isNotNull(extScriptBlock) &&
				!extScriptBlock.endsWith("\n}")) {

				extScriptBlock = extScriptBlock + "\n" + line;

				continue;
			}

			if (Validator.isNull(pluginsScriptBlock) &&
				line.matches("^plugins\\s+.*\\{")) {

				pluginsScriptBlock = line;

				continue;
			}

			if (Validator.isNotNull(pluginsScriptBlock) &&
				!pluginsScriptBlock.endsWith("\n}")) {

				pluginsScriptBlock = pluginsScriptBlock + "\n" + line;

				continue;
			}

			if (line.matches("^apply plugin.*")) {
				applyPlugins.add(line);
			}
			else if (line.matches("^sourceCompatibility\\s*=.*$")) {
				properties.add(line);
			}
			else if (line.matches("^targetCompatibility\\s*=.*$")) {
				properties.add(line);
			}
			else if (line.matches("^task\\s+.*$") && !line.contains("{")) {
				tasks.add(line);
			}
			else {
				if (taskMatcher.matches() && tasks.isEmpty()) {
					initializeBlock = initializeBlock + "\n" + line;
				}
				else {
					bodyBlock = bodyBlock + "\n" + line;
				}
			}
		}

		return new GradleFile(
			applyPlugins, StringUtil.trim(bodyBlock),
			StringUtil.trim(buildScriptBlock), content,
			StringUtil.trim(extScriptBlock), fileName, importsBlock,
			StringUtil.trim(initializeBlock),
			StringUtil.trim(pluginsScriptBlock), properties, tasks);
	}

	private static final Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*\n+)+", Pattern.MULTILINE);
	private static final Pattern _taskPattern = Pattern.compile(
		".*^task\\s+.*$.*", Pattern.DOTALL | Pattern.MULTILINE);

}