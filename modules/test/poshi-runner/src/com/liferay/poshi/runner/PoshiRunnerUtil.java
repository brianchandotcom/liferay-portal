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

package com.liferay.poshi.runner;

import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Karen Dang
 */
public class PoshiRunnerUtil {

	public static String getCommandName(String fileCommandName) {
		int x = fileCommandName.indexOf("#");

		return fileCommandName.substring(x + 1);
	}

	public static String getName(String fileCommandName) {
		int x = fileCommandName.indexOf("#");

		return fileCommandName.substring(0, x);
	}

	public static String getNameFromFileName(String fileName) {
		int x = fileName.lastIndexOf("/");
		int y = fileName.lastIndexOf(".");

		return fileName.substring(x + 1, y);
	}

	public static Element getRootElement(String fileName) throws Exception {
		StringBuilder sb = new StringBuilder();

		int lineNumber = 1;

		BufferedReader bufferedReader = new BufferedReader(
			new StringReader(FileUtil.read(fileName)));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			Matcher matcher = _pattern.matcher(line);

			if (matcher.find()) {
				for (String reservedTag : _reservedTags) {
					if (line.contains("<" + reservedTag)) {
						line = StringUtil.replace(
							line, matcher.group(),
							matcher.group() + " line-number=\"" + lineNumber +
								"\"");

						break;
					}
				}
			}

			sb.append(line);

			lineNumber++;
		}

		String content = sb.toString();

		InputStream inputStream = new ByteArrayInputStream(content.getBytes());

		SAXReader saxReader = new SAXReader();

		Document document = saxReader.read(inputStream);

		return document.getRootElement();
	}

	private static Pattern _pattern = Pattern.compile("<[a-z\\-]+");
	private static List<String> _reservedTags = Arrays.asList(
			new String[] {
				"and", "case", "command", "condition", "contains", "default",
				"definition", "delimiter", "description", "echo", "else",
				"elseif", "equals", "execute", "fail", "for", "if", "isset",
				"not", "or", "property", "set-up", "take-screenshot", "td",
				"tear-down", "then", "tr", "while", "var"
			});

}