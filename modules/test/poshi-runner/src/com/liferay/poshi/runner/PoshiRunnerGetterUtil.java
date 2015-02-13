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

import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.MathUtil;
import com.liferay.poshi.runner.util.StringPool;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerGetterUtil {

	public static String getCanonicalPath(String dir) {
		try {
			File file = new File(dir);

			return file.getCanonicalPath();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return dir;
	}

	public static String getClassNameFromClassCommandName(
		String classCommandName) {

		int x = classCommandName.indexOf("#");

		return classCommandName.substring(0, x);
	}

	public static String getClassNameFromFilePath(String filePath) {
		int x = filePath.lastIndexOf("/");
		int y = filePath.lastIndexOf(".");

		return filePath.substring(x + 1, y);
	}

	public static String getClassTypeFromFilePath(String filePath) {
		int x = filePath.lastIndexOf(".");

		return filePath.substring(x + 1);
	}

	public static String getCommandNameFromClassCommandName(
		String classCommandName) {

		int x = classCommandName.indexOf("#");

		return classCommandName.substring(x + 1);
	}

	public static String getProjectDir() {
		File file = new File(StringPool.PERIOD);

		String absolutePath = file.getAbsolutePath();

		return absolutePath.substring(0, absolutePath.length() - 1);
	}

	public static Element getRootElementFromFilePath(String filePath)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		int lineNumber = 1;

		BufferedReader bufferedReader = new BufferedReader(
			new StringReader(FileUtil.read(filePath)));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			Matcher matcher = _tagPattern.matcher(line);

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

	public static String getVarMethodValue(Element element) throws Exception {
		String classCommandName = PoshiRunnerVariablesUtil.replaceCommandVars(
			element.attributeValue("method"));

		String commandName = getCommandNameFromClassCommandName(
			classCommandName);

		Matcher matcher = _parameterPattern.matcher(commandName);

		String[] parameters = null;

		while (matcher.find()) {
			String parameterString = matcher.group(1);

			parameterString = parameterString.replaceAll("\"", "");

			parameters = parameterString.split(",");
		}

		commandName = commandName.replaceAll("(\\(.*\\))+", "");

		LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

		Class clazz = liferaySelenium.getClass();

		Object object = liferaySelenium;

		String className = getClassNameFromClassCommandName(classCommandName);

		if (!className.equals("selenium")) {
			clazz = Class.forName("com.liferay.poshi.runner.util." + className);

			object = null;
		}

		if (className.equals("MathUtil")) {
			Integer[] integer = new Integer[parameters.length];

			for (int i = 0; i < parameters.length; i++) {
				integer[i] = Integer.parseInt(parameters[i].trim());
			}

			Method[] mathMethods = MathUtil.class.getDeclaredMethods();

			for (Method mathMethod : mathMethods) {
				if (mathMethod.getName().equals(commandName)) {
					Class[] parameterTypes = mathMethod.getParameterTypes();

					Method method = MathUtil.class.getMethod(
						commandName, parameterTypes);

					if (parameterTypes.length > 1 ) {
						Object obj = method.invoke(object, integer);

						return obj.toString();
					}
					else {
						Object obj = method.invoke(
							object, new Object[]{integer});

						return obj.toString();
					}
				}
			}
		}
		else {
			List<Class> parameterClasses = new ArrayList<>();

			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					parameters[i] = parameters[i].trim();

					parameterClasses.add(String.class);
				}
			}

			Method method = clazz.getMethod(
				commandName,
				parameterClasses.toArray(new Class[parameterClasses.size()]));

			method.setAccessible(true);

			Object obj = method.invoke(object, parameters);

			return obj.toString();
		}

		return null;
	}

	private static final Pattern _parameterPattern = Pattern.compile(
		"\\(([^)]+)\\)");
	private static final List<String> _reservedTags = Arrays.asList(
		new String[] {
			"and", "case", "command", "condition", "contains", "default",
			"definition", "delimiter", "description", "echo", "else", "elseif",
			"equals", "execute", "fail", "for", "if", "isset", "not", "or",
			"property", "set-up", "take-screenshot", "td", "tear-down", "then",
			"tr", "while", "var"
		});
	private static final Pattern _tagPattern = Pattern.compile("<[a-z\\-]+");

}