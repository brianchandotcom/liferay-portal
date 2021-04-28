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

package com.liferay.portal.tools;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 */
public class ConfigurationEnvBuilder {

	public static String generateConfigOverrideContent(
			String[] configurationFiles)
		throws IOException {

		Map<String, String> configOverrides = new TreeMap<>();

		Matcher matcher = _pattern.matcher("");

		for (String configurationFile : configurationFiles) {
			Path path = Paths.get(configurationFile);

			String fullyQualifiedName = configurationFile.substring(
				configurationFile.indexOf(
					StringBundler.concat("com", File.separator, "liferay")),
				configurationFile.indexOf(".java"));

			fullyQualifiedName = StringUtil.replace(
				fullyQualifiedName, File.separator, StringPool.PERIOD);

			List<String> lines = Files.readAllLines(path);

			for (String line : lines) {
				if (line.contains("public class")) {
					break;
				}

				matcher.reset(line);

				if (matcher.matches()) {
					String decoded = StringBundler.concat(
						fullyQualifiedName, StringPool.UNDERLINE,
						matcher.group(1));

					String override = decoded;

					configOverrides.put(decoded, _encode(override));
				}
			}
		}

		StringBundler sb = new StringBundler((configOverrides.size() * 5) + 2);

		sb.append(_HEADER);
		sb.append("\n\n");

		for (Map.Entry<String, String> entry : configOverrides.entrySet()) {
			sb.append("#\n#");
			sb.append(entry.getKey());
			sb.append("\n#\n");
			sb.append(entry.getValue());
			sb.append("\n\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String[] configurationFiles = StringUtil.split(
			arguments.get("config.files"));

		String content = generateConfigOverrideContent(configurationFiles);

		Path configPath = Paths.get("config-env.txt");

		Files.write(configPath, content.getBytes());
	}

	private static String _encode(String string) {
		StringBundler sb = new StringBundler();

		sb.append(_PREFIX);

		for (char c : string.toCharArray()) {
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

	private static final String _HEADER =
		"# These environment variables can be used to override modules " +
			"configurations";

	private static final String _PREFIX = "LIFERAY_CONFIGURATION_OVERRIDE_";

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
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new ExceptionInInitializerError(
						reflectiveOperationException);
				}
			}
		};

	private static final Pattern _pattern = Pattern.compile(
		"\\s*public .* ([^\\s]+)\\(\\);");

}