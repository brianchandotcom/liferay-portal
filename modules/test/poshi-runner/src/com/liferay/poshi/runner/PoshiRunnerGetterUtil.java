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
		int y = classCommandName.indexOf("(");

		if (y != -1) {
			return classCommandName.substring(x + 1, y);
		}

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

		Matcher matcher = _parameterPattern.matcher(classCommandName);

		String[] parameters = null;

		while (matcher.find()) {
			String parameterString = matcher.group(1);

			parameterString = parameterString.replaceAll("\"", "");

			parameters = parameterString.split(",");
		}

		String className = getClassNameFromClassCommandName(classCommandName);
		String commandName = getCommandNameFromClassCommandName(
			classCommandName);

		if (className.equals("MathUtil")) {
			Integer[] integers = new Integer[parameters.length];

			for (int i = 0; i < parameters.length; i++) {
				integers[i] = Integer.parseInt(parameters[i].trim());
			}

			Method[] methods = MathUtil.class.getDeclaredMethods();

			for (Method method : methods) {
				String methodName = method.getName();

				if (methodName.equals(commandName)) {
					Class<?>[] parameterTypes = method.getParameterTypes();

					if (parameterTypes.length > 1 ) {
						Object returnObject = method.invoke(
							null, (Object[])integers);

						return returnObject.toString();
					}
					else {
						Object returnObject = method.invoke(
							null, new Object[] {integers});

						return returnObject.toString();
					}
				}
			}
		}
		else {
			List<Class<?>> parameterClasses = new ArrayList<>();

			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					parameters[i] = parameters[i].trim();

					parameterClasses.add(String.class);
				}
			}

			Class<?> clazz = null;
			Object object = null;

			if (className.equals("selenium")) {
				LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

				clazz = liferaySelenium.getClass();
				object = liferaySelenium;
			}
			else {
				clazz = Class.forName(
					"com.liferay.poshi.runner.util." + className);
			}

			Method method = clazz.getMethod(
				commandName,
				parameterClasses.toArray(new Class[parameterClasses.size()]));

			Object returnObject = method.invoke(object, (Object[])parameters);

			return returnObject.toString();
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