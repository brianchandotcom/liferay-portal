/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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

	private String _postProcess(String content) {
		content = content.replaceAll("(?m)^( *-)\n +(.*)", "$1   $2");

		return content.replaceAll("\\n +\\n", "\n\n");
	}

	private String _preProcess(String content) {
		content = content.replaceAll("\\n +\\n", "\n\n");

		StringBundler sb = new StringBundler();

		String[] lines = content.split("\n");

		for (String line : lines) {
			String trimmedLine = line.trim();

			if (Validator.isBlank(trimmedLine)) {
				sb.append("\n");

				continue;
			}

			Matcher matcher = _dashPattern.matcher(line);

			if (matcher.matches()) {
				String indent = matcher.group(1);

				sb.append(StringUtil.trimTrailing(indent));

				sb.append("\n");
				sb.append(indent.replaceFirst("-", " "));
				sb.append(matcher.group(2));
				sb.append("\n");

				continue;
			}

			sb.append(line);
			sb.append("\n");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final String[] _INCLUDES = {
		"**/templates/*.tpl", "**/*.yaml", "**/*.yml"
	};

	private static final Pattern _dashPattern = Pattern.compile("( +- +)(.+)");

}