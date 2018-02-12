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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDLineBreaksCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		content = _formatLineBreaks(shortFileName, content);

		return content;
	}

	private String _formatLineBreaks(
		String content, Map<String, String> definitionsKeysMap) {

		if (definitionsKeysMap == null) {
			return content;
		}

		for (Map.Entry<String, String> entry : definitionsKeysMap.entrySet()) {
			String definitionKey = entry.getValue();

			Pattern pattern = Pattern.compile(" " + definitionKey + ":");

			Matcher matcher = pattern.matcher(content);

			if (matcher.find()) {
				return StringUtil.replaceFirst(
					content, " ", "\n", matcher.start());
			}
		}

		return content;
	}

	private String _formatLineBreaks(String shortFileName, String content) {
		content = _formatLineBreaks(
			content, BNDSourceUtil.getDefinitionKeysMap());

		Map<String, Map<String, String>> fileSpecificDefinitionKeysMap =
			BNDSourceUtil.getFileSpecificDefinitionKeysMap();

		return _formatLineBreaks(
			content, fileSpecificDefinitionKeysMap.get(shortFileName));
	}

}