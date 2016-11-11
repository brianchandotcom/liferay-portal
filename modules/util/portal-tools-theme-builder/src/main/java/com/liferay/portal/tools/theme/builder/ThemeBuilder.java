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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.portal.tools.theme.builder.internal.util.FileUtil;
import com.liferay.portal.tools.theme.builder.internal.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
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
 * @author Andrea Di Giorgi
 */
public class ThemeBuilder {

	public static void main(String[] args) throws Exception {
		ThemeBuilderArgs themeBuilderArgs = new ThemeBuilderArgs();

		JCommander jCommander = new JCommander(themeBuilderArgs);

		try {
			File jarFile = FileUtil.getJarFile();

			if (jarFile.isFile()) {
				jCommander.setProgramName("java -jar " + jarFile.getName());
			}
			else {
				jCommander.setProgramName(ThemeBuilder.class.getName());
			}

			jCommander.parse(args);

			if (themeBuilderArgs.isHelp()) {
				_printHelp(jCommander);
			}
			else {
				ThemeBuilder themeBuilder = new ThemeBuilder(themeBuilderArgs);

				themeBuilder.build();
			}
		}
		catch (ParameterException pe) {
			System.err.println(pe.getMessage());

			_printHelp(jCommander);
		}
	}

	public ThemeBuilder(
		File diffsDir, String name, File outputDir, File parentDir,
		String parentName, String templateExtension, File unstyledDir) {

		if (Validator.isNull(name)) {
			name = ThemeBuilderArgs.DEFAULT_NAME;
		}

		if (outputDir == null) {
			throw new IllegalArgumentException(
				"The output directory is required");
		}

		if (parentDir == null) {
			if (Validator.isNotNull(parentName) &&
				((unstyledDir == null) ||
				 ((unstyledDir != null) && !parentName.equals(UNSTYLED)))) {

				throw new IllegalArgumentException("Parent path is required");
			}
		}
		else {
			if (Validator.isNull(parentName)) {
				throw new IllegalArgumentException("Parent name is required");
			}

			if (!parentName.equals(UNSTYLED) && (unstyledDir == null)) {
				throw new IllegalArgumentException("Unstyled path is required");
			}

			if (parentName.equals(UNSTYLED) && (unstyledDir != null)) {
				unstyledDir = parentDir;

				parentDir = null;
			}
		}

		if (Validator.isNull(templateExtension)) {
			templateExtension = ThemeBuilderArgs.DEFAULT_TEMPLATE_EXTENSION;
		}
		else {
			templateExtension = templateExtension.toLowerCase();
		}

		_diffsDir = diffsDir;
		_name = name;
		_outputDir = outputDir;
		_parentDir = parentDir;
		_parentName = parentName;
		_templateExtension = templateExtension;
		_unstyledDir = unstyledDir;

		System.setProperty("java.awt.headless", "true");
	}

	public ThemeBuilder(ThemeBuilderArgs themeBuilderArgs) {
		this(
			themeBuilderArgs.getDiffsDir(), themeBuilderArgs.getName(),
			themeBuilderArgs.getOutputDir(), themeBuilderArgs.getParentDir(),
			themeBuilderArgs.getParentName(),
			themeBuilderArgs.getTemplateExtension(),
			themeBuilderArgs.getUnstyledDir());
	}

	public void build() throws IOException {
		if (_unstyledDir != null) {
			_copyTheme(UNSTYLED, _unstyledDir);
		}

		if (_parentDir != null) {
			_copyTheme(_parentName, _parentDir);
		}

		_writeLookAndFeelXml();

		if (_diffsDir != null) {
			_copyTheme(_diffsDir);
		}

		_writeScreenshotThumbnail();
	}

	protected static final String STYLED = "_styled";

	protected static final String UNSTYLED = "_unstyled";

	private static void _printHelp(JCommander jCommander) throws Exception {
		jCommander.usage();
	}

	private void _copyTheme(File themeDir) throws IOException {
		final Path outputDirPath = _outputDir.toPath();
		final Path themeDirPath = themeDir.toPath();

		Files.walkFileTree(
			themeDirPath,
			new SimpleFileVisitor<Path>() {

				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (_isIgnoredTemplateFile(path.toString())) {
						return FileVisitResult.CONTINUE;
					}

					Path outputPath = outputDirPath.resolve(
						themeDirPath.relativize(path));

					Files.createDirectories(outputPath.getParent());

					Files.copy(
						path, outputPath, StandardCopyOption.REPLACE_EXISTING);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _copyTheme(String themeName, File themeDir)
		throws IOException {

		if (themeDir.isDirectory()) {
			_copyTheme(themeDir);

			return;
		}

		Path outputDirPath = _outputDir.toPath();

		try (ZipFile zipFile = new ZipFile(themeDir)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/") ||
					!name.startsWith("META-INF/resources/" + themeName) ||
					_isIgnoredTemplateFile(name)) {

					continue;
				}

				name = name.substring(20 + themeName.length());

				Path path = outputDirPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(
					zipFile.getInputStream(zipEntry), path,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	private boolean _isIgnoredTemplateFile(String fileName) {
		String extension = FileUtil.getExtension(fileName);

		if ((extension.equalsIgnoreCase("ftl") ||
			 extension.equalsIgnoreCase("vm")) &&
			!extension.equalsIgnoreCase(_templateExtension)) {

			return true;
		}

		return false;
	}

	private void _writeLookAndFeelXml() throws IOException {
		Path path = _outputDir.toPath();

		path = path.resolve("WEB-INF/liferay-look-and-feel.xml");

		if (Files.exists(path)) {
			return;
		}

		String content = FileUtil.read(
			"com/liferay/portal/tools/theme/builder/dependencies" +
				"/liferay-look-and-feel.xml");

		String id = _name.toLowerCase();

		id = id.replaceAll(" ", "_");

		content = content.replace("[$ID$]", id);

		content = content.replace("[$NAME$]", _name);
		content = content.replace("[$TEMPLATE_EXTENSION$]", _templateExtension);

		Files.createDirectories(path.getParent());

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	private void _writeScreenshotThumbnail() throws IOException {
		File file = new File(_outputDir, "images/screenshot.png");

		if (!file.exists()) {
			return;
		}

		Builder<File> thumbnailBuilder = Thumbnails.of(file);

		thumbnailBuilder.outputFormat("png");
		thumbnailBuilder.size(160, 120);

		thumbnailBuilder.toFile(new File(_outputDir, "images/thumbnail.png"));
	}

	private final File _diffsDir;
	private final String _name;
	private final File _outputDir;
	private final File _parentDir;
	private final String _parentName;
	private final String _templateExtension;
	private final File _unstyledDir;

}