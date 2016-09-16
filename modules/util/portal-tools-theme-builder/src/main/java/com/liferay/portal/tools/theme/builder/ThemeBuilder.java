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

package com.liferay.portal.tools.theme.builder;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

/**
 * @author David Truong
 */
public class ThemeBuilder {

	public static void main(String[] args) throws Exception {
		try {
			String diffsPath = null;
			String name = null;
			String outputPath = null;
			String parentName = null;
			String templateExtension = null;
			String themeParentPath = null;
			String themeUnstyledPath = null;

			ThemeBuilder themeBuilder = new ThemeBuilder(
				diffsPath, name, outputPath, parentName, templateExtension,
				themeParentPath, themeUnstyledPath);

			themeBuilder.compileTheme();
		}
		catch (Exception e) {
		}
	}

	public ThemeBuilder(
			String diffsPath, String name, String outputPath, String parentName,
			String templateExtension, String themeParentPath,
			String themeUnstyledPath)
		throws Exception {

		_diffsPath = diffsPath;
		_name = name;
		_outputDir = new File(outputPath);
		_parentName = parentName;
		_templateExtension = templateExtension;
		_themeParentPath = themeParentPath;
		_themeUnstyledPath = themeUnstyledPath;
	}

	public void compileTheme() throws IOException {
		_copyThemeParent();

		_createLookAndFeelXml();

		_copyDiffs();

		_buildThumbnails();
	}

	private void _buildThumbnails() throws IOException {
		File screenshotFile = new File(_outputDir, "images/screenshot.png");

		if (!screenshotFile.exists()) {
			return;
		}

		Builder<File> thumbnailBuilder = Thumbnails.of(screenshotFile);

		thumbnailBuilder.size(160, 120);
		thumbnailBuilder.outputFormat("png");
		thumbnailBuilder.toFile(new File(_outputDir, "images/thumbnail.png"));
	}

	private void _copyDiffs() throws IOException {
		File diffsDir = new File(_diffsPath);

		if (!diffsDir.exists()) {
			return;
		}

		_copyFiles(diffsDir);
	}

	private void _copyFiles(File source) throws IOException {
		if (!source.exists()) {
			return;
		}

		final Path outputPath = _outputDir.toPath();
		final Path sourcePath = source.toPath();

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						final Path dir, final BasicFileAttributes attrs)
					throws IOException {

					Files.createDirectories(
						outputPath.resolve(sourcePath.relativize(dir)));

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						final Path file, final BasicFileAttributes attrs)
					throws IOException {

					String fileName = file.toString();

					fileName = StringUtil.toLowerCase(fileName);

					if (fileName.endsWith("vm") &&
						_templateExtension.equals("ftl")) {

						return FileVisitResult.CONTINUE;
					}

					if (fileName.endsWith("ftl") &&
						_templateExtension.equals("vm")) {

						return FileVisitResult.CONTINUE;
					}

					Files.copy(
						file, outputPath.resolve(sourcePath.relativize(file)),
						StandardCopyOption.REPLACE_EXISTING);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _copyThemeDir(String themeName, String themePath)
		throws IOException {

		File themeDir = new File(themePath);

		if (themePath.endsWith("jar")) {
			themeDir = _unzipJar(themeName, themePath);
		}

		_copyFiles(themeDir);
	}

	private void _copyThemeParent() throws IOException {
		if ((_parentName == null) || _parentName.isEmpty()) {
			return;
		}

		_copyThemeDir("_unstyled", _themeUnstyledPath);

		_copyThemeDir(_parentName, _themeParentPath);
	}

	private void _createLookAndFeelXml() throws IOException {
		File webInfDir = new File(_outputDir, "WEB-INF");

		File lookAndFeelXml = new File(webInfDir, "liferay-look-and-feel.xml");

		if (lookAndFeelXml.exists()) {
			return;
		}

		if (!webInfDir.exists()) {
			webInfDir.mkdir();
		}

		lookAndFeelXml.createNewFile();

		byte[] bytes = _read(
			"com/liferay/portal/tools/theme/builder/dependencies/" +
			"liferay-look-and-feel.xml");

		String content = new String(bytes);

		String id = StringUtil.toLowerCase(_name);

		id = id.replaceAll(" ", "_");

		content = content.replace("${id}", id);
		content = content.replace("${name}", _name);
		content = content.replace("${template.extension}", _templateExtension);

		Files.write(lookAndFeelXml.toPath(), content.getBytes("UTF-8"));
	}

	private byte[] _read(String fileName) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		ClassLoader classLoader = ThemeBuilder.class.getClassLoader();

		try (InputStream inputStream =
				classLoader.getResourceAsStream(fileName)) {

			byte[] bytes = new byte[1024];
			int length = 0;

			while ((length = inputStream.read(bytes)) > 0) {
				byteArrayOutputStream.write(bytes, 0, length);
			}
		}

		return byteArrayOutputStream.toByteArray();
	}

	private File _unzipJar(String themeName, String themePath)
		throws IOException {

		Path jarPath = Files.createTempDirectory("themeBuilder");

		try (ZipFile zipFile = new ZipFile(themePath)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/") ||
					!name.startsWith("META-INF/resources/" + themeName)) {

					continue;
				}

				name = name.substring(19 + themeName.length());

				Path path = jarPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(
					zipFile.getInputStream(zipEntry), path,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}

		return jarPath.toFile();
	}

	private final String _diffsPath;
	private final String _name;
	private final File _outputDir;
	private final String _parentName;
	private final String _templateExtension;
	private final String _themeParentPath;
	private final String _themeUnstyledPath;

}