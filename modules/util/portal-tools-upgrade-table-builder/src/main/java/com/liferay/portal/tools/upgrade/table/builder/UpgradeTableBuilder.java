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

package com.liferay.portal.tools.upgrade.table.builder;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeTableBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDirName = GetterUtil.getString(
			arguments.get("upgrade.base.dir"),
			UpgradeTableBuilderArgs.BASE_DIR_NAME);
		boolean osgiModule = GetterUtil.getBoolean(
			arguments.get("upgrade.osgi.module"),
			UpgradeTableBuilderArgs.OSGI_MODULE);
		String releaseInfoVersion = arguments.get(
			"upgrade.release.info.version");
		String upgradeTableDirName = arguments.get("upgrade.table.dir");

		try {
			new UpgradeTableBuilder(
				baseDirName, osgiModule, releaseInfoVersion,
				upgradeTableDirName);
		}
		catch (Exception exception) {
			ArgumentsUtil.processMainException(arguments, exception);
		}
	}

	public UpgradeTableBuilder(
			String baseDirName, boolean osgiModule, String releaseInfoVersion,
			String upgradeTableDirName)
		throws Exception {

		_baseDirName = baseDirName;

		_osgiModule = osgiModule;

		if (_osgiModule) {
			_releaseInfoVersion = null;
		}
		else {
			_releaseInfoVersion = releaseInfoVersion;
		}

		_upgradeTableDirName = upgradeTableDirName;

		FileSystem fileSystem = FileSystems.getDefault();

		final PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:**/upgrade/v**/util/*Table.java");

		final AtomicBoolean tableFilesFound = new AtomicBoolean();

		Files.walkFileTree(
			Paths.get(_baseDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (pathMatcher.matches(path)) {
						tableFilesFound.set(true);

						_buildUpgradeTable(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		if (!tableFilesFound.get()) {
			System.out.println(
				"No files matching the pattern \"" + _baseDirName +
					"/**/upgrade/v**/util/*Table.java\" have been found.");
		}
	}

	private void _buildUpgradeTable(Path path) throws IOException {
		String pathString = path.toString();

		pathString = StringUtil.replace(pathString, '\\', '/');

		String upgradeFileVersion = pathString.replaceFirst(
			".*/upgrade/v(.+)/util.*", "$1");

		upgradeFileVersion = StringUtil.replace(upgradeFileVersion, '_', '.');

		if (upgradeFileVersion.contains("to")) {
			upgradeFileVersion = upgradeFileVersion.replaceFirst(
				".+\\.to\\.(.+)", "$1");
		}

		String fileName = String.valueOf(path.getFileName());

		String tableName = fileName.substring(0, fileName.length() - 10);

		String upgradeFileName = tableName + "ModelImpl.java";

		Path upgradeFilePath = Paths.get(
			_upgradeTableDirName, upgradeFileVersion, upgradeFileName);

		if (Files.notExists(upgradeFilePath)) {
			if (!_isRelevantUpgradePackage(upgradeFileVersion)) {
				return;
			}

			upgradeFilePath = _getUpgradeFilePath(upgradeFileName);

			if (upgradeFilePath == null) {
				return;
			}
		}

		String content = _read(path);

		String packagePath = _getPackagePath(content);

		String className = fileName.substring(0, fileName.length() - 5);

		String upgradeFileContent = _read(upgradeFilePath);

		try {
			content = _getContent(packagePath, className, upgradeFileContent);
		}
		catch (Exception exception) {
			throw new IOException(exception.getMessage(), exception.getCause());
		}

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	private List<Path> _findFiles(
			String baseDirName, String pattern, final int limit)
		throws IOException {

		final List<Path> paths = new ArrayList<>();

		FileSystem fileSystem = FileSystems.getDefault();

		final PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:" + pattern);

		Files.walkFileTree(
			Paths.get(baseDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					if (pathMatcher.matches(filePath)) {
						paths.add(filePath);

						if (paths.size() == limit) {
							return FileVisitResult.TERMINATE;
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return paths;
	}

	private String _getContent(
			String packagePath, String className, String content)
		throws Exception {

		int x = content.indexOf("public static final String TABLE_NAME =");

		if (x == -1) {
			x = content.indexOf("public static String TABLE_NAME =");
		}

		String tableName = content.substring(
			content.indexOf("=", x) + 1, content.indexOf(";", x));

		int y = content.indexOf(
			"public static final String TABLE_SQL_CREATE =");

		if (y == -1) {
			y = content.lastIndexOf("public static String TABLE_SQL_CREATE =");
		}

		String tableSQLCreate = content.substring(
			content.indexOf("=", y) + 1, content.indexOf(";", y));

		return FreeMarkerUtil.process(
			"com/liferay/portal/tools/upgrade/table/builder/dependencies" +
				"/upgradeTable.ftl",
			HashMapBuilder.put(
				"className", className
			).put(
				"copyright", _getCopyright()
			).put(
				"package", packagePath
			).put(
				"tableName", tableName
			).put(
				"tableSQLCreate", tableSQLCreate
			).build());
	}

	private String _getCopyright() throws Exception {
		Path path = Paths.get(_baseDirName);

		path = path.toAbsolutePath();

		while (path != null) {
			Path copyrightFilePath = path.resolve("copyright.txt");

			if (Files.exists(copyrightFilePath)) {
				return _read(copyrightFilePath);
			}

			path = path.getParent();
		}

		return null;
	}

	private String _getPackagePath(String content) {
		Matcher matcher = _packagePathPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private String _getSchemaVersion() throws IOException {
		Properties properties = new Properties();

		Path path = Paths.get(_baseDirName, "bnd.bnd");

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties.getProperty("Liferay-Require-SchemaVersion");
	}

	private Path _getUpgradeFilePath(String fileName) throws IOException {
		List<Path> paths = _findFiles(_baseDirName, "**/" + fileName, 1);

		if (paths.isEmpty()) {
			return null;
		}

		return paths.get(0);
	}

	private boolean _isRelevantUpgradePackage(String upgradeFileVersion)
		throws IOException {

		String currentVersion = null;

		if (_osgiModule) {
			currentVersion = _getSchemaVersion();
		}
		else {
			currentVersion = _releaseInfoVersion.substring(0, 3);
		}

		if (!upgradeFileVersion.startsWith(currentVersion)) {
			return false;
		}

		return true;
	}

	private String _read(Path path) throws IOException {
		String s = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		return StringUtil.replace(s, "\r\n", "\n");
	}

	private static final Pattern _packagePathPattern = Pattern.compile(
		"package (.+?);");

	private final String _baseDirName;
	private final boolean _osgiModule;
	private final String _releaseInfoVersion;
	private final String _upgradeTableDirName;

}