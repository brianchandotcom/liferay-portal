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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.gradle.api.AntBuilder;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

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

	public static String createTempFileName(String prefix, String extension) {
		StringBuilder sb = new StringBuilder();

		if (Validator.isNotNull(prefix)) {
			sb.append(prefix);
		}

		sb.append(System.currentTimeMillis());
		sb.append(UUID.randomUUID());

		if (Validator.isNotNull(extension)) {
			sb.append('.');
			sb.append(extension);
		}

		return sb.toString();
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

		return get(project, url, destinationFile, false, true);
	}

	public static File get(
			Project project, String url, File destinationFile,
			boolean ignoreErrors, boolean tryLocalNetwork)
		throws IOException {

		return get(
			project, url, null, null, destinationFile, ignoreErrors,
			tryLocalNetwork);
	}

	public static synchronized File get(
			Project project, String url, String username, String password,
			File destinationFile, boolean ignoreErrors, boolean tryLocalNetwork)
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
				"https?:\\/\\/", "http://mirrors.lax.liferay.com/");

			if (tryLocalNetwork) {
				try {
					_get(
						project, mirrorsUrl, null, null,
						mirrorsCacheArtifactFile, ignoreErrors);
				}
				catch (Exception e) {
					_get(
						project, url, username, password,
						mirrorsCacheArtifactFile, ignoreErrors);
				}
			}
			else {
				_get(
					project, url, username, password, mirrorsCacheArtifactFile,
					ignoreErrors);
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

	public static char getDriveLetter(File file) {
		if (!OSDetector.isWindows()) {
			throw new UnsupportedOperationException();
		}

		String absolutePath = getAbsolutePath(file);

		char driveLetter = absolutePath.charAt(0);

		return Character.toLowerCase(driveLetter);
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

		boolean upToDate = false;

		try {
			long sourceLastModified = _getLastModified(sourceFile);
			long targetLastModified = _getLastModified(targetFile);

			if (targetLastModified >= sourceLastModified) {
				upToDate = true;
			}
		}
		catch (IOException ioe) {
			throw new GradleException(ioe.getMessage(), ioe);
		}

		return upToDate;
	}

	public static void jar(
		Project project, final File destinationFile, final String duplicate,
		final boolean update, final String[][] filesets) {

		Closure<Void> closure = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public void doCall(AntBuilder antBuilder) {
				_invokeAntMethodJar(
					antBuilder, destinationFile, duplicate, update, filesets,
					null);
			}

		};

		project.ant(closure);
	}

	public static String merge(Iterable<File> files, String separator) {
		StringBuilder sb = new StringBuilder();

		for (File file : files) {
			sb.append(getAbsolutePath(file));
			sb.append(separator);
		}

		sb.setLength(sb.length() - separator.length());

		return sb.toString();
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

	public static FileCollection shrinkClasspath(
		Project project, FileCollection fileCollection) {

		if (!OSDetector.isWindows()) {
			return fileCollection;
		}

		List<File> shrunkClasspath = new ArrayList<>();

		Map<Character, File> driveJarDirs = new HashMap<>();

		driveJarDirs.put(getDriveLetter(_TMP_DIR), _TMP_DIR);
		driveJarDirs.put(
			getDriveLetter(project.getBuildDir()), project.getBuildDir());

		char curDriveLetter = 0;
		List<File> curDriveFiles = new ArrayList<>();

		for (File file : fileCollection) {
			char driveLetter = getDriveLetter(file);

			if (curDriveLetter != driveLetter) {
				File jarFile = _createClasspathJarFile(
					project, curDriveFiles, driveJarDirs.get(curDriveLetter));

				if (jarFile != null) {
					shrunkClasspath.add(jarFile);
				}

				curDriveLetter = driveLetter;
				curDriveFiles.clear();
			}

			if (driveJarDirs.containsKey(driveLetter)) {
				curDriveFiles.add(file);
			}
			else {
				shrunkClasspath.add(file);
			}
		}

		File jarFile = _createClasspathJarFile(
			project, curDriveFiles, driveJarDirs.get(curDriveLetter));

		if (jarFile != null) {
			shrunkClasspath.add(jarFile);
		}

		return project.files(shrunkClasspath);
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

	private static File _createClasspathJarFile(
		Project project, List<File> files, File jarDir) {

		if (files.isEmpty()) {
			return null;
		}

		if (files.size() == 1) {
			return files.get(0);
		}

		AntBuilder antBuilder = project.createAntBuilder();

		jarDir.mkdirs();

		File jarFile = new File(jarDir, createTempFileName("classpath", "jar"));

		jarFile.deleteOnExit();

		String manifestClasspathProperty =
			"manifest.classpath." + jarFile.getName();

		_invokeAntMethodManifestClasspath(
			antBuilder, files, jarFile, manifestClasspathProperty);

		String manifestClasspath = String.valueOf(
			antBuilder.getProperty(manifestClasspathProperty));

		File manifestFile = new File(
			jarFile.getParentFile(), jarFile.getName() + ".manifest");

		try {
			_invokeAntMethodManifest(
				antBuilder, manifestFile,
				Collections.singletonMap("Class-Path", manifestClasspath));

			_invokeAntMethodJar(
				antBuilder, jarFile, null, false, new String[0][0],
				manifestFile);
		}
		finally {
			project.delete(manifestFile);
		}

		return jarFile;
	}

	private static void _get(
		Project project, final String url, final String username,
		final String password, File destinationFile,
		final boolean ignoreErrors) {

		final File tmpFile = new File(
			destinationFile.getParentFile(),
			destinationFile.getName() + ".tmp");

		project.delete(destinationFile, tmpFile);

		Closure<Void> closure = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public void doCall(AntBuilder antBuilder) {
				Map<String, Object> args = new HashMap<>();

				args.put("dest", tmpFile);
				args.put("ignoreerrors", ignoreErrors);
				args.put("src", url);

				if (Validator.isNotNull(username) &&
					Validator.isNotNull(password)) {

					args.put("password", password);
					args.put("username", username);
				}

				if (_logger.isLifecycleEnabled()) {
					_logger.lifecycle(
						"Trying to download " + url + " to " + tmpFile);
				}

				antBuilder.invokeMethod("get", args);
			}

		};

		project.ant(closure);

		if (!tmpFile.renameTo(destinationFile)) {
			throw new GradleException(
				"Unable to rename " + tmpFile + " to " + destinationFile);
		}
	}

	private static long _getLastModified(File file) throws IOException {
		if (file.isFile()) {
			return file.lastModified();
		}

		final AtomicLong lastModified = new AtomicLong();

		Files.walkFileTree(
			file.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					FileTime fileTime = basicFileAttributes.lastModifiedTime();

					long fileTimeMillis = fileTime.toMillis();

					if (fileTimeMillis > lastModified.longValue()) {
						lastModified.set(fileTimeMillis);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return lastModified.get();
	}

	private static File _getMirrorsCacheDir() {
		String userHome = System.getProperty("user.home");

		return new File(userHome, ".liferay/mirrors");
	}

	private static void _invokeAntMethod(
		AntBuilder antBuilder, String name, String argKey, Object argValue) {

		antBuilder.invokeMethod(
			name, Collections.singletonMap(argKey, argValue));
	}

	private static void _invokeAntMethodClasspath(
		final AntBuilder antBuilder, final String path) {

		Closure<Void> closure = new Closure<Void>(antBuilder) {

			@SuppressWarnings("unused")
			public void doCall() {
				_invokeAntMethod(antBuilder, "pathelement", "path", path);
			}

		};

		antBuilder.invokeMethod("classpath", closure);
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
		boolean update, final String[][] filesets, File manifestFile) {

		Map<String, Object> args = new HashMap<>();

		args.put("destfile", destinationFile);

		if (Validator.isNotNull(duplicate)) {
			args.put("duplicate", duplicate);
		}

		if (manifestFile != null) {
			args.put("manifest", manifestFile);
		}

		args.put("update", update);

		Closure<Void> closure = new Closure<Void>(antBuilder) {

			@SuppressWarnings("unused")
			public void doCall() {
				for (String[] fileset : filesets) {
					_invokeAntMethodFileset(antBuilder, fileset);
				}
			}

		};

		antBuilder.invokeMethod("jar", new Object[] {args, closure});
	}

	private static void _invokeAntMethodManifest(
		final AntBuilder antBuilder, File file,
		final Map<String, String> attributes) {

		Map<String, File> args = Collections.singletonMap("file", file);

		Closure<Void> closure = new Closure<Void>(antBuilder) {

			@SuppressWarnings("unused")
			public void doCall() {
				Map<String, String> args = new HashMap<>();

				for (Map.Entry<String, String> entry : attributes.entrySet()) {
					args.put("name", entry.getKey());
					args.put("value", entry.getValue());

					antBuilder.invokeMethod("attribute", args);
				}
			}

		};

		antBuilder.invokeMethod("manifest", new Object[] {args, closure});
	}

	private static void _invokeAntMethodManifestClasspath(
		final AntBuilder antBuilder, final Iterable<File> files, File jarFile,
		String property) {

		Map<String, Object> args = new HashMap<>();

		args.put("jarfile", jarFile);
		args.put("maxParentLevels", 99);
		args.put("property", property);

		Closure<Void> closure = new Closure<Void>(antBuilder) {

			@SuppressWarnings("unused")
			public void doCall() {
				_invokeAntMethodClasspath(
					antBuilder, merge(files, File.pathSeparator));
			}

		};

		antBuilder.invokeMethod(
			"manifestclasspath", new Object[] {args, closure});
	}

	private static final File _TMP_DIR = new File(
		System.getProperty("java.io.tmpdir"));

	private static final Logger _logger = Logging.getLogger(FileUtil.class);

}