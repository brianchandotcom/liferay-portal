/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.soy.builder.commands;

import com.beust.jcommander.Parameters;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Replace 'goog.getMsg' definitions.",
	commandNames = "replace-translation"
)
public class ReplaceSoyTranslationCommand extends BaseSoyJsCommand {

	@Override
	public void execute(Path path) throws IOException {
		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		Matcher matcher = _pattern.matcher(content);

		StringBuffer sb = new StringBuffer();

		boolean found = false;

		while (matcher.find()) {
			found = true;

			String replacement = getReplacement(
				matcher.group(1), matcher.group(2), matcher.group(3));

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(replacement));
		}

		matcher.appendTail(sb);

		if (found) {
			content = sb.toString();

			Files.write(path, content.getBytes(StandardCharsets.UTF_8));
		}
	}

	protected String getReplacement(
		String variableName, String languageKey, String argumentsObject) {

		StringBuilder sb = new StringBuilder();

		sb.append("var ");
		sb.append(variableName);

		// Split string to avoid SF error

		sb.append(" = Liferay.Language");
		sb.append(".get('");

		sb.append(_fixLanguageKey(languageKey));
		sb.append("');");

		int argumentReplaces = 0;

		if ((argumentsObject != null) && !argumentsObject.isEmpty()) {
			argumentReplaces = _appendArgumentReplaces(
				sb, argumentsObject, variableName);
		}

		if (argumentReplaces == 0) {
			_appendArgumentMarkerReplace(sb, variableName);
		}

		return sb.toString();
	}

	private void _appendArgumentMarkerReplace(
		StringBuilder sb, String variableName) {

		sb.append(System.lineSeparator());
		sb.append(variableName);
		sb.append(" = ");
		sb.append(variableName);
		sb.append(".replace(/{(\\d+)}/g, '\\x01$1\\x01')");
	}

	private int _appendArgumentReplaces(
		StringBuilder sb, String argumentsObject, String variableName) {

		int i = 0;

		Matcher matcher = _argumentsObjectPattern.matcher(argumentsObject);

		while (matcher.find()) {
			sb.append(System.lineSeparator());

			sb.append(variableName);
			sb.append(" = ");
			sb.append(variableName);
			sb.append(".replace('{");
			sb.append(i);
			sb.append("}', ");
			sb.append(matcher.group(1));
			sb.append(");");

			i++;
		}

		return i;
	}

	private String _fixLanguageKey(String languageKey) {
		Matcher matcher = _languageKeyPlaceholderPattern.matcher(languageKey);

		return matcher.replaceAll("x");
	}

	private static final Pattern _argumentsObjectPattern = Pattern.compile(
		"'[^:]+'\\s*:\\s*([\\d\\w\\._]+)+");
	private static final Pattern _languageKeyPlaceholderPattern =
		Pattern.compile("\\{\\$\\w+\\}");
	private static final Pattern _pattern = Pattern.compile(
		"var (MSG_EXTERNAL_\\d+(?:\\$\\$\\d+)?) = " +
			"goog\\.getMsg\\(\\s*'([\\w-\\.,()\\{\\}\\$]+)'" +
				"\\s*(?:,\\s*\\{([\\s\\S]+?)\\})?\\);");

}