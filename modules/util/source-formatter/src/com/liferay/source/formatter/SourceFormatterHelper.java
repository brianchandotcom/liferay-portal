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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class SourceFormatterHelper {

	public SourceFormatterHelper(boolean useProperties) {
		_useProperties = useProperties;
	}

	public void close() throws IOException {
		if (!_useProperties) {
			return;
		}

		String newPropertiesContent = PropertiesUtil.toString(_properties);

		if (!_propertiesContent.equals(newPropertiesContent)) {
			FileUtil.write(_propertiesFile, newPropertiesContent);
		}
	}

	public void init() throws IOException {
		if (!_useProperties) {
			return;
		}

		File basedirFile = new File("./");

		String basedirAbsolutePath = StringUtil.replace(
			basedirFile.getAbsolutePath(), new String[] {".", ":", "/", "\\"},
			new String[] {"_", "_", "_", "_"});

		String propertiesFileName =
			System.getProperty("java.io.tmpdir") + "/SourceFormatter." +
				basedirAbsolutePath;

		_propertiesFile = new File(propertiesFileName);

		if (_propertiesFile.exists()) {
			_propertiesContent = FileUtil.read(_propertiesFile);

			PropertiesUtil.load(_properties, _propertiesContent);
		}
	}

	public void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public void printError(String fileName, String message) {
		if (_useProperties) {
			String encodedFileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			_properties.remove(encodedFileName);
		}

		System.out.println(message);
	}

	public List<String> scanForFiles(
			String baseDir, String[] excludes, String[] includes)
		throws Exception {

		final List<String> fileNames = new ArrayList<>();

		final List<PathMatcher> excludeDirPathMatchers = new ArrayList<>();
		final List<PathMatcher> excludeFilePathMatchers = new ArrayList<>();
		final List<PathMatcher> includeFilePathMatchers = new ArrayList<>();

		FileSystem fileSystem = FileSystems.getDefault();

		for (String exclude : excludes) {
			if (exclude.endsWith("/**")) {
				exclude = exclude.substring(0, exclude.length() - 3);

				excludeDirPathMatchers.add(
					fileSystem.getPathMatcher("glob:" + exclude));
			}
			else {
				excludeFilePathMatchers.add(
					fileSystem.getPathMatcher("glob:" + exclude));
			}
		}

		for (String include : includes) {
			includeFilePathMatchers.add(
				fileSystem.getPathMatcher("glob:" + include));
		}

		Files.walkFileTree(
			Paths.get(baseDir),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (Files.exists(
							dirPath.resolve("source_formatter.ignore"))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					for (PathMatcher pathMatcher : excludeDirPathMatchers) {
						if (pathMatcher.matches(dirPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					for (PathMatcher pathMatcher : excludeFilePathMatchers) {
						if (pathMatcher.matches(filePath)) {
							return FileVisitResult.CONTINUE;
						}
					}

					for (PathMatcher pathMatcher : includeFilePathMatchers) {
						if (!pathMatcher.matches(filePath)) {
							continue;
						}

						String fileName = filePath.toString();

						fileNames.add(fileName);

						if (!_useProperties) {
							return FileVisitResult.CONTINUE;
						}

						File file = new File(fileName);

						String encodedFileName = StringUtil.replace(
							fileName, StringPool.BACK_SLASH, StringPool.SLASH);

						long timestamp = GetterUtil.getLong(
							_properties.getProperty(encodedFileName));

						if (timestamp < file.lastModified()) {
							_properties.setProperty(
								encodedFileName,
								String.valueOf(file.lastModified()));
						}

						return FileVisitResult.CONTINUE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return fileNames;
	}

	private final Properties _properties = new Properties();
	private String _propertiesContent = StringPool.BLANK;
	private File _propertiesFile;
	private final boolean _useProperties;

}