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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.io.IOException;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class PropertiesPortalEnvironmentVariablesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!isPortalSource() ||
			!absolutePath.matches(
				".*/portal-impl/src/portal(_.*)?\\.properties")) {

			return content;
		}

		return _formatPortalProperties(content);
	}

	private String _addEnvVariables(
		String content, String commentsBlock, String environmentVariablesBlock,
		String variablesContent, int lineNumber) {

		Set<String> environmentVariables = _getEnvironmentVariables(
			variablesContent);

		if (environmentVariables.isEmpty()) {
			return content;
		}

		StringBundler sb = new StringBundler();

		if (Validator.isNull(commentsBlock) &&
			Validator.isNull(environmentVariablesBlock)) {

			sb.append(StringPool.NEW_LINE);
			sb.append(StringPool.FOUR_SPACES);
			sb.append(StringPool.POUND);
			sb.append(StringPool.NEW_LINE);
		}

		for (String environmentVariable : environmentVariables) {
			sb.append("    # Env: ");
			sb.append(environmentVariable);
			sb.append(StringPool.NEW_LINE);
		}

		sb.append(StringPool.FOUR_SPACES);
		sb.append(StringPool.POUND);
		sb.append(StringPool.NEW_LINE);

		String environmentVariablesComment = sb.toString();

		if (environmentVariablesBlock.equals(environmentVariablesComment)) {
			return content;
		}

		int pos = getLineStartPos(content, lineNumber + 1);

		if (Validator.isNotNull(environmentVariablesBlock)) {
			return StringUtil.replaceFirst(
				content, environmentVariablesBlock, environmentVariablesComment,
				pos);
		}

		variablesContent = StringUtil.replaceLast(variablesContent, "\n", "");

		return StringUtil.replaceFirst(
			content, variablesContent,
			environmentVariablesComment + variablesContent,
			getLineStartPos(content, lineNumber + 1));
	}

	private String _formatPortalProperties(String content) {
		StringBundler commentBlockSB = new StringBundler();
		StringBundler environmentVariablesBlockSB = new StringBundler();
		StringBundler variablesContentSB = new StringBundler();

		int lineNumber = 0;

		String[] lines = StringUtil.splitLines(content);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (line.startsWith("        ") || line.matches("    #?\\w.*")) {
				variablesContentSB.append(line);
				variablesContentSB.append("\n");

				continue;
			}

			if (variablesContentSB.index() > 0) {
				String newContent = _addEnvVariables(
					content, commentBlockSB.toString(),
					environmentVariablesBlockSB.toString(),
					variablesContentSB.toString(), lineNumber);

				if (!content.equals(newContent)) {
					return newContent;
				}

				environmentVariablesBlockSB = new StringBundler();
				variablesContentSB = new StringBundler();

				lineNumber = i;
			}

			if (line.startsWith("    # Env: ") ||
				((environmentVariablesBlockSB.index() > 0) &&
				 line.equals("    #"))) {

				environmentVariablesBlockSB.append(line);
				environmentVariablesBlockSB.append("\n");
			}
			else if (line.equals("    #") || line.startsWith("    # ")) {
				commentBlockSB.append(line);
				commentBlockSB.append("\n");
			}
		}

		return _addEnvVariables(
			content, commentBlockSB.toString(),
			environmentVariablesBlockSB.toString(),
			variablesContentSB.toString(), lineNumber);
	}

	private Set<String> _getEnvironmentVariables(String s) {
		Set<String> environmentVariables = new TreeSet<>();

		for (String line : StringUtil.splitLines(s)) {
			String trimmedLine = StringUtil.trim(line);

			if (trimmedLine.startsWith(StringPool.POUND)) {
				trimmedLine = trimmedLine.substring(1);

				trimmedLine = StringUtil.trim(trimmedLine);
			}

			int pos = trimmedLine.indexOf(StringPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			environmentVariables.add(
				ToolsUtil.encodeEnvironmentProperty(
					trimmedLine.substring(0, pos)));
		}

		return environmentVariables;
	}

}