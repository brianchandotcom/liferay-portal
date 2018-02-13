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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.parser.GradleFile;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class GradleBodyCheck extends BaseGradleFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, GradleFile gradleFile,
			String content)
		throws Exception {

		if (absolutePath.contains("/project-templates-")) {
			return content;
		}

		String bodyBlock = gradleFile.getBodyBlock();

		if (Validator.isNull(bodyBlock)) {
			return content;
		}

		String newBlock = StringPool.BLANK;
		Set<String> newBlocks = new TreeSet<>();
		String oldBlocks = StringPool.BLANK;

		for (String line : StringUtil.splitLines(bodyBlock)) {
			if (line.matches(
					"(allprojects|project|subprojects|(else|for|if|while)\\s)" +
						".*\\{")) {

				return content;
			}

			if (Validator.isNull(newBlock) && line.matches("\\w+\\s*\\{")) {
				newBlock = line;
				oldBlocks = oldBlocks + "\n" + line;

				continue;
			}

			if (Validator.isNotNull(newBlock) && !newBlock.endsWith("\n}")) {
				newBlock = newBlock + "\n" + line;
				oldBlocks = oldBlocks + "\n" + line;

				if (newBlock.endsWith("\n}")) {
					newBlocks.add(newBlock);

					newBlock = StringPool.BLANK;
				}

				continue;
			}

			oldBlocks = oldBlocks + "\n" + line;

			if (Validator.isNotNull(line)) {
				newBlocks.clear();
				oldBlocks = StringPool.BLANK;
			}
		}

		if (newBlocks.isEmpty()) {
			return content;
		}

		StringBundler sb = new StringBundler(2 * newBlocks.size());

		for (String s : newBlocks) {
			sb.append(s);
			sb.append("\n\n");
		}

		sb.setIndex(sb.index() - 1);

		return StringUtil.replaceLast(
			content, StringUtil.trim(oldBlocks), sb.toString());
	}

}