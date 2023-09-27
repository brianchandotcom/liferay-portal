/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class YMLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		Set<String> modifiedContents = new HashSet<>();
		Set<String> modifiedMessages = new TreeSet<>();

		String newContent = _preProcess(content);

		newContent = format(
			file, fileName, absolutePath, newContent, content,
			new ArrayList<>(getSourceChecks()), modifiedContents,
			modifiedMessages, 0);

		newContent = _postProcess(newContent);

		return processFormattedFile(
			file, fileName, content, newContent, modifiedMessages);
	}

	private String _fixIncorrectIndentation(String content) {
		Matcher matcher = _sequencesAndMappingsPattern1.matcher(content);

		while (matcher.find()) {
			String s = matcher.group();

			String[] lines = s.split("\n");

			StringBundler sb = new StringBundler();

			for (int i = 1; i < lines.length; i++) {
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.DOUBLE_SPACE);
				sb.append(lines[i]);
			}

			content = StringUtil.replaceFirst(
				content, matcher.group(),
				lines[0] + _fixIncorrectIndentation(sb.toString()));
		}

		return content;
	}

	private String _postProcess(String content) {
		return content.replaceAll("(?m)^( *-)\n +(.*)", "$1 $2");
	}

	private String _preProcess(String content) {
		Matcher matcher = _sequencesAndMappingsPattern2.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(),
				StringBundler.concat(
					matcher.group(1), "\n", matcher.group(2), "  ",
					matcher.group(3)));
		}

		return _fixIncorrectIndentation(content);
	}

	private static final String[] _INCLUDES = {"**/*.yaml", "**/*.yml"};

	private static final Pattern _sequencesAndMappingsPattern1 =
		Pattern.compile("^( *)[^ -].+:(\n\\1-(\n\\1 .+)*)+", Pattern.MULTILINE);
	private static final Pattern _sequencesAndMappingsPattern2 =
		Pattern.compile("(^( *)-)(?: )(.+(\n|\\Z))", Pattern.MULTILINE);

}