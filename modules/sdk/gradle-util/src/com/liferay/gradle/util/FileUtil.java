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

package com.liferay.gradle.util;

import groovy.lang.Closure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.AntBuilder;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static void concatenate(
			File destinationFile, Iterable<File> sourceFiles)
		throws IOException {

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				destinationFile);
			FileChannel destinationChannel = fileOutputStream.getChannel()) {

			for (File sourceFile : sourceFiles) {
				try (FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
					FileChannel sourceChannel = fileInputStream.getChannel()) {

					sourceChannel.transferTo(
						0, sourceChannel.size(), destinationChannel);
				}
			}
		}
	}

	public static boolean exists(Project project, String fileName) {
		File file = project.file(fileName);

		return file.exists();
	}

	public static File get(Project project, String url) throws IOException {
		return get(project, url, null);
	}

	public static File get(Project project, String url, File destinationFile)
		throws IOException {

		return get(project, url, destinationFile, false, true, false);
	}

	public static synchronized File get(
			Project project, String url, File destinationFile,
			boolean ignoreErrors, boolean tryLocalNetwork, boolean verbose)
		throws IOException {

		String mirrorsCacheArtifactSubdir = url.replaceFirst(
			"https?:\\/\\/(.+\\/).+", "$1");

		File mirrorsCacheArtifactDir = new File(
			_getMirrorsCacheDir(), mirrorsCacheArtifactSubdir);

		String fileName = url.replaceFirst(".+\\/(.+)", "$1");

		File mirrorsCacheArtifactFile = new File(
			mirrorsCacheArtifactDir, fileName);

		if (!mirrorsCacheArtifactFile.exists()) {
			mirrorsCacheArtifactDir.mkdirs();

			String mirrorsUrl = url.replaceFirst(
				"http:\\/\\/", "http://mirrors/");

			if (tryLocalNetwork) {
				try {
					_get(
						project, mirrorsUrl, mirrorsCacheArtifactFile,
						ignoreErrors, verbose);
				}
				catch (Exception e) {
					_get(
						project, url, mirrorsCacheArtifactFile, ignoreErrors,
						verbose);
				}
			}
			else {
				_get(
					project, url, mirrorsCacheArtifactFile, ignoreErrors,
					verbose);
			}
		}

		if (destinationFile == null) {
			return mirrorsCacheArtifactFile;
		}

		Path destinationPath = destinationFile.toPath();

		if (destinationFile.isDirectory()) {
			destinationPath = destinationPath.resolve(fileName);
		}

		Files.createDirectories(destinationPath.getParent());

		Files.copy(
			mirrorsCacheArtifactFile.toPath(), destinationPath,
			StandardCopyOption.REPLACE_EXISTING);

		return destinationPath.toFile();
	}

	public static String getAbsolutePath(File file) {
		String absolutePath = file.getAbsolutePath();

		return absolutePath.replace('\\', '/');
	}

	public static boolean isChild(File file, File parentFile) {
		Path path = file.toPath();

		path = path.toAbsolutePath();

		Path parentPath = parentFile.toPath();

		parentPath = parentPath.toAbsolutePath();

		if (path.startsWith(parentPath)) {
			return true;
		}

		return false;
	}

	public static boolean isUpToDate(
		Project project, Object source, Object target) {

		File sourceFile = project.file(source);
		File targetFile = project.file(target);

		if (!sourceFile.exists() || !targetFile.exists()) {
			return false;
		}

		AntBuilder antBuilder = project.getAnt();

		_invokeAntMethodUpToDate(
			antBuilder, "uptodate", sourceFile, targetFile);

		Map<String, Object> antProperties = antBuilder.getProperties();

		if (antProperties.containsKey("uptodate")) {
			return true;
		}

		return false;
	}

	public static void jar(
		Project project, final File destinationFile, final String duplicate,
		final boolean update, final String[][] filesets) {

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(AntBuilder antBuilder) {
				_invokeAntMethodJar(
					antBuilder, destinationFile, duplicate, update, filesets);
			}

		};

		project.ant(closure);
	}

	public static String read(String resourceName) throws IOException {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader = FileUtil.class.getClassLoader();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
					classLoader.getResourceAsStream(resourceName)))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	public static Properties readProperties(File file) throws IOException {
		Properties properties = new Properties();

		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.load(fileInputStream);
			}
		}

		return properties;
	}

	public static Properties readProperties(Project project, String fileName)
		throws IOException {

		File file = project.file(fileName);

		return readProperties(file);
	}

	public static String relativize(File file, File startFile) {
		Path path = file.toPath();
		Path startPath = startFile.toPath();

		Path relativePath = startPath.relativize(path);

		return relativePath.toString();
	}

	public static File replaceExtension(File file, String extension) {
		String fileName = file.getPath();

		int pos = fileName.lastIndexOf('.');

		if (pos != -1) {
			if (Validator.isNotNull(extension) && !extension.startsWith(".")) {
				extension = "." + extension;
			}

			fileName = fileName.substring(0, pos) + extension;
		}

		return new File(fileName);
	}

	public static String stripExtension(String fileName) {
		int index = fileName.lastIndexOf('.');

		if (index != -1) {
			fileName = fileName.substring(0, index);
		}

		return fileName;
	}

	public static void write(File file, List<String> lines) throws IOException {
		try (PrintWriter printWriter = new PrintWriter(
				new OutputStreamWriter(
					new FileOutputStream(file), StandardCharsets.UTF_8))) {

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);

				if ((i + 1) < lines.size()) {
					printWriter.println(line);
				}
				else {
					printWriter.print(line);
				}
			}
		}
	}

	private static void _get(
		Project project, final String url, final File destinationFile,
		final boolean ignoreErrors, final boolean verbose) {

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(AntBuilder antBuilder) {
				Map<String, Object> args = new HashMap<>();

				args.put("dest", destinationFile);
				args.put("ignoreerrors", ignoreErrors);
				args.put("src", url);
				args.put("verbose", verbose);

				antBuilder.invokeMethod("get", args);
			}

		};

		project.ant(closure);
	}

	private static File _getMirrorsCacheDir() {
		String userHome = System.getProperty("user.home");

		return new File(userHome, ".liferay/mirrors");
	}

	private static void _invokeAntMethodFileset(
		AntBuilder antBuilder, String[] fileset) {

		Map<String, String> args = new HashMap<>();

		args.put("dir", fileset[0]);
		args.put("includes", fileset[1]);

		antBuilder.invokeMethod("fileset", args);
	}

	private static void _invokeAntMethodJar(
		final AntBuilder antBuilder, File destinationFile, String duplicate,
		boolean update, final String[][] filesets) {

		Map<String, Object> args = new HashMap<>();

		args.put("destfile", destinationFile);
		args.put("duplicate", duplicate);
		args.put("update", update);

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall() {
				for (String[] fileset : filesets) {
					_invokeAntMethodFileset(antBuilder, fileset);
				}
			}

		};

		antBuilder.invokeMethod("jar", new Object[] {args, closure});
	}

	private static void _invokeAntMethodUpToDate(
		AntBuilder antBuilder, String property, File sourceFile,
		File targetFile) {

		Map<String, Object> args = new HashMap<>();

		args.put("property", property);
		args.put("srcfile", sourceFile);
		args.put("targetfile", targetFile);

		antBuilder.invokeMethod("uptodate", args);
	}

}