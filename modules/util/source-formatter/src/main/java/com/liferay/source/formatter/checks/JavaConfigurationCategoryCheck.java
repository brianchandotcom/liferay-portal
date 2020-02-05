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

import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaConfigurationCategoryCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("Configuration.java")) {
			return content;
		}

		Matcher matcher = _categoryNamePattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String categoryName = matcher.group(1);

		List<String> categoryKeys = _getCategoryKeys();

		if (!categoryKeys.isEmpty() && !categoryKeys.contains(categoryName)) {
			addMessage(
				fileName, "Invalid category name '" + categoryName + "'",
				getLineNumber(content, matcher.start(1)));
		}

		return content;
	}

	private List<String> _getCategoryKeys() throws IOException {
		if (_categoryKeys != null) {
			return _categoryKeys;
		}

		final List<String> categoryKeys = new ArrayList<>();

		File configurationCategoriesDir = getFile(
			_CONFIGURATION_CATEGORIES_DIR_NAME, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (configurationCategoriesDir == null) {
			_categoryKeys = categoryKeys;

			return _categoryKeys;
		}

		Files.walkFileTree(
			configurationCategoriesDir.toPath(),
			EnumSet.noneOf(FileVisitOption.class), 1,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String absolutePath = SourceUtil.getAbsolutePath(filePath);

					if (!absolutePath.endsWith(".java")) {
						return FileVisitResult.CONTINUE;
					}

					File file = new File(absolutePath);

					String content = FileUtil.read(file);

					Matcher matcher = _categoryKeyPattern.matcher(content);

					if (matcher.find()) {
						categoryKeys.add(matcher.group(1));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		_categoryKeys = categoryKeys;

		return _categoryKeys;
	}

	private static final String _CONFIGURATION_CATEGORIES_DIR_NAME =
		"modules/apps/configuration-admin/configuration-admin-web/src/main" +
			"/java/com/liferay/configuration/admin/web/internal/category";

	private static final Pattern _categoryKeyPattern = Pattern.compile(
		"String\\s+_CATEGORY_KEY\\s+=\\s+\"(\\w+)\"");
	private static final Pattern _categoryNamePattern = Pattern.compile(
		"\n@ExtendedObjectClassDefinition\\(\\s*category\\s+=\\s+\"(\\w+)\"");

	private List<String> _categoryKeys;

}