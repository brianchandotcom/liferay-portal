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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class YMLWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = StringUtil.replace(
			content, CharPool.TAB, StringPool.FOUR_SPACES);

		content = _checkDefinitions(content, StringPool.BLANK, 0);

		return super.doProcess(fileName, absolutePath, content);
	}

	private String _checkDefinitions(String content, String indent, int level) {
		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);

		for (String definition : definitions) {
			String expectedIndent = StringPool.BLANK;

			for (int i = 0; i < level; i++) {
				expectedIndent = expectedIndent + StringPool.FOUR_SPACES;
			}

			if (!indent.equals(expectedIndent)) {
				content = StringUtil.replaceFirst(
					content, definition,
					expectedIndent + StringUtil.trimLeading(definition));
			}

			String nestedDefinitionIndent =
				YMLSourceUtil.getNestedDefinitionIndent(definition);

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				List<String> nestedDefinitions = YMLSourceUtil.getDefinitions(
					definition, nestedDefinitionIndent);
				String newDefinition = _checkDefinitions(
					definition, nestedDefinitionIndent, level + 1);

				if (newDefinition.equals(definition) &&
					nestedDefinitions.isEmpty()) {

					String[] lines = StringUtil.splitLines(newDefinition);

					if (lines.length > 1) {
						expectedIndent =
							expectedIndent + StringPool.FOUR_SPACES;

						for (int i = 1; i < lines.length; i++) {
							String line = lines[i];

							String expectedLine =
								expectedIndent + StringUtil.trimLeading(line);

							if (!line.equals(expectedLine)) {
								newDefinition = StringUtil.replaceFirst(
									newDefinition, line, expectedLine);
							}
						}
					}
				}

				if (!newDefinition.equals(definition)) {
					content = StringUtil.replaceFirst(
						content, definition, newDefinition);
				}
			}
		}

		return content;
	}

}