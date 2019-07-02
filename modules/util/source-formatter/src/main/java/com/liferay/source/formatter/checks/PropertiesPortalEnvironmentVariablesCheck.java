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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class PropertiesPortalEnvironmentVariablesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!isPortalSource()) {
			return content;
		}

		if (!absolutePath.matches(
				".*/portal-impl/src/portal(_.*)?\\.properties")) {

			return content;
		}

		return _formatPortalProperties(content);
	}

	private String _encode(String s) {
		StringBundler sb = new StringBundler();

		sb.append(_ENV_OVERRIDE_PREFIX);

		for (char c : s.toCharArray()) {
			if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
			}
			else {
				sb.append(CharPool.UNDERLINE);

				sb.append(_charPoolChars.get(c));

				sb.append(CharPool.UNDERLINE);
			}
		}

		return sb.toString();
	}

	private String _formatPortalProperties(String content) {
		Matcher matcher = _pattern.matcher(content);

		while (matcher.find()) {
			String variablesContent = matcher.group(4);

			Set<String> environmentVariables = _getEnvironmentVariables(
				variablesContent);

			if (environmentVariables.isEmpty()) {
				continue;
			}

			String commentBlock = matcher.group(1);

			StringBundler sb = new StringBundler();

			if (Validator.isNull(commentBlock)) {
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
			}

			for (String environmentVariable : environmentVariables) {
				sb.append(StringPool.FOUR_SPACES);
				sb.append("# Env: ");
				sb.append(environmentVariable);
				sb.append(StringPool.NEW_LINE);
			}

			sb.append(StringPool.FOUR_SPACES);
			sb.append(StringPool.POUND);
			sb.append(StringPool.NEW_LINE);

			String environmentVariablesComment = sb.toString();

			if (!commentBlock.endsWith(environmentVariablesComment)) {
				return StringUtil.replaceFirst(
					content, variablesContent,
					environmentVariablesComment + variablesContent,
					matcher.start() - 1);
			}
		}

		return content;
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

			environmentVariables.add(_encode(trimmedLine.substring(0, pos)));
		}

		return environmentVariables;
	}

	private static final String _ENV_OVERRIDE_PREFIX = "LIFERAY_";

	private static final Map<Character, String> _charPoolChars =
		new HashMap<Character, String>() {
			{
				try {
					for (Field field : CharPool.class.getFields()) {
						if (Modifier.isStatic(field.getModifiers()) &&
							(field.getType() == char.class)) {

							put(
								field.getChar(null),
								StringUtil.removeChar(
									field.getName(), CharPool.UNDERLINE));
						}
					}
				}
				catch (ReflectiveOperationException roe) {
					throw new ExceptionInInitializerError(roe);
				}
			}
		};

	private static final Pattern _pattern = Pattern.compile(
		"\n((    #( .*)?\n)*)((    ( |#?\\w).*\n)+)");

}