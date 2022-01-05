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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 */
public class ConfigurationEnvBuilder {

	public static String buildContent(String[] configurationJavaFileNames)
		throws IOException {

		StringBundler sb = new StringBundler();

		sb.append("##\n## OSGi Configuration Overrides\n##\n");

		Matcher matcher = _pattern.matcher("");

		for (String configurationJavaFileName : configurationJavaFileNames) {
			if (configurationJavaFileName.contains(
					"/build/compile-include-sources/")) {

				continue;
			}

			Path path = Paths.get(configurationJavaFileName);

			String fullyQualifiedName = configurationJavaFileName.substring(
				configurationJavaFileName.indexOf(
					StringBundler.concat("com", File.separator, "liferay")),
				configurationJavaFileName.indexOf(".java"));

			fullyQualifiedName = StringUtil.replace(
				fullyQualifiedName, File.separator, StringPool.PERIOD);

			List<String> lines = Files.readAllLines(path);

			for (String line : lines) {
				if (line.contains("public class")) {
					break;
				}

				matcher.reset(line);

				if (matcher.matches()) {
					String configurationKey = StringBundler.concat(
						"configuration.override.", fullyQualifiedName,
						StringPool.UNDERLINE, matcher.group(1));

					sb.append("\n");
					sb.append("    #\n");
					sb.append("    # Env: ");
					sb.append(
						ToolsUtil.encodeEnvironmentProperty(configurationKey));
					sb.append("\n");
					sb.append("    #\n");
					sb.append("    #");
					sb.append(configurationKey);
					sb.append(StringPool.EQUAL);
				}
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String[] configurationJavaFileNames = StringUtil.split(
			arguments.get("configuration.java.files"));

		Path path = Paths.get(arguments.get("output.file"));

		String content = new String(Files.readAllBytes(path));

		int index = content.indexOf("##\n## OSGi Configuration Overrides");

		content = content.substring(0, index);

		content = content.concat(buildContent(configurationJavaFileNames));

		Files.write(path, content.getBytes());
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\s*public .* ([^\\s]+)\\(\\);");

}