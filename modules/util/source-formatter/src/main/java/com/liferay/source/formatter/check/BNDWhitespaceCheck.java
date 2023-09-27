/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.io.IOException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		content = _formatWhitespace(shortFileName, content);

		content = StringUtil.replace(
			content, new String[] {"\n\n", "\t "}, new String[] {"\n", "\t"});

		return super.doProcess(fileName, absolutePath, content);
	}

	private String _formatWhitespace(
		String content, Map<String, String> definitionsKeysMap) {

		if (definitionsKeysMap == null) {
			return content;
		}

		for (Map.Entry<String, String> entry : definitionsKeysMap.entrySet()) {
			String definitionKey = entry.getValue();

			Pattern pattern = Pattern.compile(
				"(\\A|\n)" + definitionKey + ":[^ \\\\\n]");

			Matcher matcher = pattern.matcher(content);

			if (matcher.find()) {
				return StringUtil.insert(
					content, StringPool.SPACE, matcher.end() - 1);
			}
		}

		return content;
	}

	private String _formatWhitespace(String shortFileName, String content) {
		content = _formatWhitespace(
			content, BNDSourceUtil.getDefinitionKeysMap());

		Map<String, Map<String, String>> fileSpecificDefinitionKeysMap =
			BNDSourceUtil.getFileSpecificDefinitionKeysMap();

		return _formatWhitespace(
			content, fileSpecificDefinitionKeysMap.get(shortFileName));
	}

}