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
import com.liferay.portal.tools.theme.builder.internal.util.FileUtil;
import com.liferay.portal.tools.theme.builder.internal.util.Validator;

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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author David Truong
 */
public class ThemeBuilder {

	public static void main(String[] args) throws Exception {
		Options options = _getOptions();

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			if (commandLine.hasOption(_OPTION_HELP)) {
				_printHelp(options);
			}
			else {
				File diffsDir = (File)commandLine.getParsedOptionValue(
					_OPTION_DIFFS_DIR);
				String name = commandLine.getOptionValue(_OPTION_NAME);
				File outputDir = (File)commandLine.getParsedOptionValue(
					_OPTION_OUTPUT_DIR);
				File parentDir = (File)commandLine.getParsedOptionValue(
					_OPTION_PARENT_PATH);
				String parentName = commandLine.getOptionValue(
					_OPTION_PARENT_NAME);
				String templateExtension = commandLine.getOptionValue(
					_OPTION_TEMPLATE_EXTENSION);
				File unstyledDir = (File)commandLine.getParsedOptionValue(
					_OPTION_UNSTYLED_PATH);

				ThemeBuilder themeBuilder = new ThemeBuilder(
					diffsDir, name, outputDir, parentDir, parentName,
					templateExtension, unstyledDir);

				themeBuilder.build();
			}
		}
		catch (ParseException pe) {
			System.err.println(pe.getMessage());

			_printHelp(options);
		}
	}

	public ThemeBuilder(
		File diffsDir, String name, File outputDir, File parentDir,
		String parentName, String templateExtension, File unstyledDir) {

		if (Validator.isNull(name)) {
			name = _DEFAULT_NAME;
		}

		if (outputDir == null) {
			throw new IllegalArgumentException(
				"The output directory is required");
		}

		if (parentDir == null) {
			if (Validator.isNotNull(parentName) &&
				((unstyledDir == null) ||
				 ((unstyledDir != null) && !parentName.equals(_UNSTYLED)))) {

				throw new IllegalArgumentException("Parent path is required");
			}
		}
		else {
			if (Validator.isNull(parentName)) {
				throw new IllegalArgumentException("Parent name is required");
			}

			if (!parentName.equals(_UNSTYLED) && (unstyledDir == null)) {
				throw new IllegalArgumentException("Unstyled path is required");
			}

			if (parentName.equals(_UNSTYLED) && (unstyledDir != null)) {
				unstyledDir = parentDir;

				parentDir = null;
			}
		}

		if (Validator.isNull(templateExtension)) {
			templateExtension = _DEFAULT_TEMPLATE_EXTENSION;
		}

		_diffsDir = diffsDir;
		_name = name;
		_outputDir = outputDir;
		_parentDir = parentDir;
		_parentName = parentName;
		_templateExtension = templateExtension;
		_unstyledDir = unstyledDir;
	}

	public void build() throws IOException {
		_copyThemeParent();

		_createLookAndFeelXml();

		_copyDiffs();

		_buildThumbnails();
	}

	private static Options _getOptions() {
		Options options = new Options();

		// diffs-dir

		Option.Builder builder = Option.builder("d");

		builder.argName(_OPTION_DIFFS_DIR);
		builder.desc(
			"The directory that contains the files to copy over the parent " +
				"theme.");
		builder.hasArg();
		builder.longOpt(_OPTION_DIFFS_DIR);
		builder.type(File.class);

		options.addOption(builder.build());

		// help

		options.addOption("h", _OPTION_HELP, false, "Print this message.");

		// name

		builder = Option.builder("n");

		builder.argName(_OPTION_NAME);
		builder.desc(
			"The name of the theme (default: \"" + _DEFAULT_NAME + "\").");
		builder.hasArg();
		builder.longOpt(_OPTION_NAME);

		options.addOption(builder.build());

		// output-dir

		builder = Option.builder("o");

		builder.argName(_OPTION_OUTPUT_DIR);
		builder.desc("The directory where to build the theme.");
		builder.hasArg();
		builder.longOpt(_OPTION_OUTPUT_DIR);
		builder.required();
		builder.type(File.class);

		options.addOption(builder.build());

		// parent-name

		builder = Option.builder("m");

		builder.argName(_OPTION_PARENT_NAME);
		builder.desc("The name of the parent theme.");
		builder.hasArg();
		builder.longOpt(_OPTION_PARENT_NAME);

		options.addOption(builder.build());

		// parent-path

		builder = Option.builder("p");

		builder.argName(_OPTION_PARENT_PATH);
		builder.desc("The directory or the JAR file of the parent theme.");
		builder.hasArg();
		builder.longOpt(_OPTION_PARENT_PATH);
		builder.type(File.class);

		options.addOption(builder.build());

		// template-extension

		builder = Option.builder("t");

		builder.argName(_OPTION_TEMPLATE_EXTENSION);
		builder.desc(
			"The extension of the template files (default: \"" +
				_DEFAULT_TEMPLATE_EXTENSION + "\").");
		builder.hasArg();
		builder.longOpt(_OPTION_TEMPLATE_EXTENSION);

		options.addOption(builder.build());

		// unstyled-path

		builder = Option.builder("u");

		builder.argName(_OPTION_UNSTYLED_PATH);
		builder.desc(
			"The directory or the JAR file of Liferay Frontend Theme " +
				"Unstyled.");
		builder.hasArg();
		builder.longOpt(_OPTION_UNSTYLED_PATH);

		options.addOption(builder.build());

		return options;
	}

	private static void _printHelp(Options options) throws Exception {
		HelpFormatter helpFormatter = new HelpFormatter();

		String usage;

		File jarFile = FileUtil.getJarFile();

		if (jarFile.isFile()) {
			usage = "java -jar " + jarFile.getName();
		}
		else {
			usage = ThemeBuilder.class.getName();
		}

		helpFormatter.printHelp(
			usage, "Build a Liferay theme.", options, null, true);
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
		if (!_diffsDir.exists()) {
			return;
		}

		_copyFiles(_diffsDir);
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

	private void _copyThemeDir(String themeName, File themeDir)
		throws IOException {

		if (themeDir.isFile()) {
			themeDir = _unzipJar(themeName, themeDir);
		}

		_copyFiles(themeDir);
	}

	private void _copyThemeParent() throws IOException {
		if (Validator.isNull(_parentName)) {
			return;
		}

		_copyThemeDir("_unstyled", _unstyledDir);

		_copyThemeDir(_parentName, _parentDir);
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

	private File _unzipJar(String themeName, File themeFile)
		throws IOException {

		Path jarPath = Files.createTempDirectory("themeBuilder");

		try (ZipFile zipFile = new ZipFile(themeFile)) {
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

	private static final String _DEFAULT_NAME;

	private static final String _DEFAULT_TEMPLATE_EXTENSION = "ftl";

	private static final String _OPTION_DIFFS_DIR = "diffs-dir";

	private static final String _OPTION_HELP = "help";

	private static final String _OPTION_NAME = "name";

	private static final String _OPTION_OUTPUT_DIR = "output-dir";

	private static final String _OPTION_PARENT_NAME = "parent-name";

	private static final String _OPTION_PARENT_PATH = "parent-path";

	private static final String _OPTION_TEMPLATE_EXTENSION =
		"template-extension";

	private static final String _OPTION_UNSTYLED_PATH = "unstyled-path";

	private static final String _UNSTYLED = "_unstyled";

	static {
		File userDir = new File(System.getProperty("user.dir"));

		_DEFAULT_NAME = userDir.getName();
	}

	private final File _diffsDir;
	private final String _name;
	private final File _outputDir;
	private final File _parentDir;
	private final String _parentName;
	private final String _templateExtension;
	private final File _unstyledDir;

}