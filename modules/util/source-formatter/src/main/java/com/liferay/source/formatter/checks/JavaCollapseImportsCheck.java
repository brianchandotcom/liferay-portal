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

import java.io.File;
import java.io.FilenameFilter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaCollapseImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String jarDirectoryName = getAttributeValue(
			_JAR_DIRECTORY_NAME_KEY, absolutePath);

		if (Validator.isNull(jarDirectoryName)) {
			return content;
		}

		Map<String, Set<String>> classNamesMap = _getClassNamesMap(
			jarDirectoryName);

		if (!classNamesMap.isEmpty()) {
			return _collapseImports(content, classNamesMap);
		}

		return content;
	}

	private Map<String, Set<String>> _addClassNames(
		Map<String, Set<String>> classNamesMap, String packageName,
		Set<String> classNames) {

		Set<String> existingClassNames = classNamesMap.get(packageName);

		if (existingClassNames != null) {
			classNames.addAll(existingClassNames);
		}

		classNamesMap.put(packageName, classNames);

		return classNamesMap;
	}

	private String _collapseImports(
		String content, Map<String, Set<String>> classNamesMap) {

		Matcher matcher = _wildcardImportPattern.matcher(content);

		while (matcher.find()) {
			String packageName = matcher.group(1);

			Set<String> classNames = classNamesMap.get(packageName);

			if (classNames == null) {
				continue;
			}

			StringBundler sb = new StringBundler(classNames.size() * 5);

			for (String className : classNames) {
				sb.append("import ");
				sb.append(packageName);
				sb.append(StringPool.PERIOD);
				sb.append(className);
				sb.append(";\n");
			}

			return StringUtil.replaceFirst(
				content, matcher.group(), sb.toString(), matcher.start());
		}

		return content;
	}

	private synchronized Map<String, Set<String>> _getClassNamesMap(
			String dirName)
		throws Exception {

		if (_classNamesMap != null) {
			return _classNamesMap;
		}

		_classNamesMap = new HashMap<>();

		File directory = getFile(dirName, getMaxDirLevel());

		if (directory == null) {
			return _classNamesMap;
		}

		File[] files = directory.listFiles(
			new FilenameFilter() {

				@Override
				public boolean accept(File file, String name) {
					return name.endsWith(".jar");
				}

			});

		for (File file : files) {
			try (JarFile jarFile = new JarFile(file)) {
				Enumeration<JarEntry> enumeration = jarFile.entries();

				Set<String> classNames = new HashSet<>();
				String previousPackageName = null;

				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();

					String name = jarEntry.getName();

					if (!name.startsWith("com/liferay/") ||
						(!name.endsWith(".class") && !name.endsWith(".java")) ||
						name.contains("$")) {

						continue;
					}

					int x = name.lastIndexOf("/");
					int y = name.lastIndexOf(".");

					String className = name.substring(x + 1, y);

					String packageName = StringUtil.replace(
						name.substring(0, x), CharPool.SLASH, CharPool.PERIOD);

					if ((previousPackageName != null) &&
						!packageName.equals(previousPackageName)) {

						_classNamesMap = _addClassNames(
							_classNamesMap, previousPackageName, classNames);

						classNames = new HashSet<>();
					}

					previousPackageName = packageName;

					classNames.add(className);
				}

				if (previousPackageName != null) {
					_classNamesMap = _addClassNames(
						_classNamesMap, previousPackageName, classNames);
				}
			}
		}

		return _classNamesMap;
	}

	private static final String _JAR_DIRECTORY_NAME_KEY = "jarDirectoryName";

	private static final Pattern _wildcardImportPattern = Pattern.compile(
		"import (com\\.liferay\\..*)\\.\\*;");

	private Map<String, Set<String>> _classNamesMap;

}