/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.theme.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.portal.tools.theme.builder.internal.util.FileUtil;
import com.liferay.portal.tools.theme.builder.internal.util.Validator;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class ThemeBuilder {

	public static final String STYLED = "_styled";

	public static final String UNSTYLED = "_unstyled";

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
		catch (ParameterException parameterException) {
			System.err.println(parameterException.getMessage());

			_printHelp(jCommander);
		}
	}

	public ThemeBuilder(
		File diffsDir, String name, File outputDir, File parentDir,
		String parentName, String templateExtension, Integer thumbnailHeight,
		Integer thumbnailWidth, File unstyledDir) {

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

		if (thumbnailHeight == null) {
			thumbnailHeight = ThemeBuilderArgs.DEFAULT_THUMBNAIL_HEIGHT;
		}

		if (thumbnailWidth == null) {
			thumbnailWidth = ThemeBuilderArgs.DEFAULT_THUMBNAIL_WIDTH;
		}

		_diffsDir = diffsDir;
		_name = name;
		_outputDir = outputDir;
		_parentDir = parentDir;
		_parentName = parentName;
		_templateExtension = templateExtension;
		_thumbnailHeight = thumbnailHeight;
		_thumbnailWidth = thumbnailWidth;
		_unstyledDir = unstyledDir;

		System.setProperty("java.awt.headless", "true");
	}

	public ThemeBuilder(ThemeBuilderArgs themeBuilderArgs) {
		this(
			themeBuilderArgs.getDiffsDir(), themeBuilderArgs.getName(),
			themeBuilderArgs.getOutputDir(), themeBuilderArgs.getParentDir(),
			themeBuilderArgs.getParentName(),
			themeBuilderArgs.getTemplateExtension(),
			themeBuilderArgs.getThumbnailHeight(),
			themeBuilderArgs.getThumbnailWidth(),
			themeBuilderArgs.getUnstyledDir());
	}

	public void build() throws Exception {
		if (_unstyledDir != null) {
			_copyTheme(UNSTYLED, _unstyledDir);
		}

		if (_parentDir != null) {
			_copyTheme(_parentName, _parentDir);
		}

		_writeLookAndFeelXml();

		if (_diffsDir != null) {
			_copyTheme(_diffsDir.toPath());
		}

		_writeScreenshotThumbnail(_thumbnailHeight, _thumbnailWidth);
	}

	private static void _printHelp(JCommander jCommander) throws Exception {
		jCommander.usage();
	}

	private void _copyTheme(final Path themeDirPath) throws IOException {
		final Path outputDirPath = _outputDir.toPath();

		Files.walkFileTree(
			themeDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (_isIgnoredFile(path.toString())) {
						return FileVisitResult.CONTINUE;
					}

					String relativePath = String.valueOf(
						themeDirPath.relativize(path));

					Path outputPath = outputDirPath.resolve(relativePath);

					Files.createDirectories(outputPath.getParent());

					Files.copy(
						path, outputPath, StandardCopyOption.REPLACE_EXISTING);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _copyTheme(String themeName, File themeDir) throws Exception {
		Path themePath = themeDir.toPath();

		if (Files.isDirectory(themePath)) {
			_copyTheme(themePath);
		}
		else {
			URI uri = themePath.toUri();

			Map<String, String> properties = new HashMap<>();

			properties.put("create", "false");
			properties.put("encoding", StandardCharsets.UTF_8.name());

			try (FileSystem fileSystem = FileSystems.newFileSystem(
					new URI("jar:" + uri.getScheme(), uri.getPath(), null),
					properties)) {

				String extension = FileUtil.getExtension(
					String.valueOf(themePath.getFileName()));

				if (extension.equalsIgnoreCase("jar")) {
					_copyTheme(
						fileSystem.getPath("/META-INF/resources/" + themeName));
				}
				else {
					_copyTheme(fileSystem.getPath("/"));
				}
			}
		}
	}

	private boolean _isIgnoredFile(String fileName) {
		String extension = FileUtil.getExtension(fileName);

		if ((extension.equalsIgnoreCase("ftl") ||
			 extension.equalsIgnoreCase("vm")) &&
			!extension.equalsIgnoreCase(_templateExtension)) {

			return true;
		}

		if (fileName.endsWith("aui.css") || fileName.endsWith("main.css") ||
			fileName.endsWith("rtl.css")) {

			return true;
		}

		return false;
	}

	private void _writeLookAndFeelXml() throws Exception {
		Path path = _outputDir.toPath();

		path = path.resolve("WEB-INF/liferay-look-and-feel.xml");

		if (Files.exists(path)) {
			return;
		}

		String content = FileUtil.read(
			ThemeBuilder.class, "dependencies/liferay-look-and-feel.xml");

		String id = _name.toLowerCase();

		id = id.replaceAll(" ", "_");

		content = content.replace("[$ID$]", id);

		content = content.replace("[$NAME$]", _name);
		content = content.replace("[$TEMPLATE_EXTENSION$]", _templateExtension);

		Files.createDirectories(path.getParent());

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	private void _writeScreenshotThumbnail(
			int thumbnailHeight, int thumbnailWidth)
		throws Exception {

		File file = new File(_outputDir, "images/screenshot.png");

		if (!file.exists()) {
			return;
		}

		Thumbnails.Builder<File> thumbnailBuilder = Thumbnails.of(file);

		thumbnailBuilder.outputFormat("png");
		thumbnailBuilder.size(thumbnailWidth, thumbnailHeight);

		thumbnailBuilder.toFile(new File(_outputDir, "images/thumbnail.png"));
	}

	private final File _diffsDir;
	private final String _name;
	private final File _outputDir;
	private final File _parentDir;
	private final String _parentName;
	private final String _templateExtension;
	private final int _thumbnailHeight;
	private final int _thumbnailWidth;
	private final File _unstyledDir;

}