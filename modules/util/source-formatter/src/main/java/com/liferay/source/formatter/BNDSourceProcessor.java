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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		// LPS-61288

		if (fileName.endsWith("-web/bnd.bnd") &&
			content.contains("Require-SchemaVersion: 1.0.0")) {

			processErrorMessage(
				fileName,
				"Do not include the header Require-SchemaVersion in web " +
					"modules: " + fileName);
		}

		Matcher matcher = _incorrectTabPattern.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.TAB, matcher.start());
		}

		matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.SPACE, matcher.start());
		}

		content = sortDefinitions(content);

		return trimContent(content, false);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	protected String sortDefinitions(String content) {
		String previousDefinition = null;

		DefinitionComparator definitionComparator = new DefinitionComparator();

		Matcher matcher = _bndDefinitionPattern.matcher(content);

		while (matcher.find()) {
			String definition = matcher.group();

			if (Validator.isNotNull(matcher.group(1))) {
				definition = definition.substring(0, definition.length() - 1);
			}

			if (Validator.isNotNull(previousDefinition)) {
				int value = definitionComparator.compare(
					previousDefinition, definition);

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

	private static final String[] _INCLUDES = new String[] {"**/*.bnd"};

	private Pattern _bndDefinitionPattern = Pattern.compile(
		"^[A-Za-z-][\\s\\S]*?([^\\\\]\n|\\Z)", Pattern.MULTILINE);
	private Pattern _incorrectTabPattern = Pattern.compile(
		"\n[^\t].*:\\\\\n(\t{2,})[^\t]");
	private Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*:(\\\\\n\t).*(\n[^\t]|\\Z)");

	private static class DefinitionComparator implements Comparator<String> {

		@Override
		public int compare(String definition1, String definition2) {
			if (definition1.startsWith(StringPool.DASH) ^
				definition2.startsWith(StringPool.DASH)) {

				return -definition1.compareTo(definition2);
			}

			return definition1.compareTo(definition2);
		}

	}

}