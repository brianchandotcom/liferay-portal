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

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collections;
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

	public static File get(
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

		Files.copy(mirrorsCacheArtifactFile.toPath(), destinationPath);

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

	public static void unzip(
		Project project, final File sourceFile, final File destinationFile,
		final int cutDirs, final String[] excludes, final String[] includes) {

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(AntBuilder antBuilder) {
				_invokeAntMethodUnzip(
					antBuilder, sourceFile, destinationFile, cutDirs, excludes,
					includes);
			}

		};

		project.ant(closure);
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

	private static void _invokeAntMethod(
		AntBuilder antBuilder, String method, String paramName,
		Object paramValue) {

		Map<String, Object> args = Collections.singletonMap(
			paramName, paramValue);

		antBuilder.invokeMethod(method, args);
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

	private static void _invokeAntMethodPatternset(
		final AntBuilder antBuilder, final String[] excludes,
		final String[] includes) {

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall() {
				if (ArrayUtil.isNotEmpty(excludes)) {
					for (String exclude : excludes) {
						_invokeAntMethod(
							antBuilder, "exclude", "name", exclude);
					}
				}

				if (ArrayUtil.isNotEmpty(includes)) {
					for (String include : includes) {
						_invokeAntMethod(
							antBuilder, "include", "name", include);
					}
				}
			}

		};

		antBuilder.invokeMethod("patternset", closure);
	}

	private static void _invokeAntMethodUnzip(
		final AntBuilder antBuilder, File sourceFile, File destinationFile,
		final int cutDirs, final String[] excludes, final String[] includes) {

		Map<String, Object> args = new HashMap<>();

		args.put("dest", destinationFile);
		args.put("src", sourceFile);

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall() {
				if (cutDirs > 0) {
					_invokeAntMethod(
						antBuilder, "cutdirsmapper", "dirs", cutDirs);
				}

				_invokeAntMethodPatternset(antBuilder, excludes, includes);
			}

		};

		antBuilder.invokeMethod("unzip", new Object[] {args, closure});
	}

}