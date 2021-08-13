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

package com.liferay.project.templates.extensions.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrea Di Giorgi
 * @author Christopher Bryan Boyd
 * @author Gregory Amerson
 */
public class FileUtil {

	public static void deleteDir(Path dirPath) throws IOException {
		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void deleteFileInPath(String fileName, Path rootDirPath)
		throws IOException {

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path path = dirPath.resolve(fileName);

					if (Files.exists(path)) {
						Files.delete(path);

						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void deleteFiles(Path dirPath, final String... fileNames)
		throws IOException {

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					for (String fileName : fileNames) {
						Files.deleteIfExists(dirPath.resolve(fileName));
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void deleteFilesByPattern(Path dirPath, Pattern pattern)
		throws IOException {

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					Matcher matcher = pattern.matcher(fileName);

					if (matcher.matches()) {
						Files.deleteIfExists(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void extractDirectory(String dirName, Path destinationDirPath)
		throws Exception {

		Map<String, InputStream> filesAndDirectories = _getFilesFromClasspath(
			"/" + dirName);

		for (Map.Entry<String, InputStream> entry :
				filesAndDirectories.entrySet()) {

			Path pathKeyPath = Paths.get(entry.getKey());

			pathKeyPath = pathKeyPath.subpath(1, pathKeyPath.getNameCount());

			try (InputStream inputStream = entry.getValue()) {
				Path destinationPath = Paths.get(
					destinationDirPath.toString(), pathKeyPath.toString());

				if (inputStream != null) {
					Files.createDirectories(destinationPath.getParent());

					try {
						Files.copy(inputStream, destinationPath);
					}
					catch (Throwable throwable) {
						throw new RuntimeException(throwable);
					}
				}
				else {
					Files.createDirectories(destinationPath);
				}
			}
		}
	}

	public static Path getFile(Path dirPath, String glob) throws IOException {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			if (iterator.hasNext()) {
				return iterator.next();
			}
		}

		return null;
	}

	public static Path getFile(Path dirPath, String glob, String regex)
		throws IOException {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			while (iterator.hasNext()) {
				Path path = iterator.next();

				String fileName = String.valueOf(path.getFileName());

				if (fileName.matches(regex)) {
					return path;
				}
			}
		}

		return null;
	}

	public static Path getJarPath() throws URISyntaxException {
		return Paths.get(_getJarURI());
	}

	public static String getManifestProperty(File file, String name)
		throws IOException {

		try (JarFile jarFile = new JarFile(file)) {
			Manifest manifest = jarFile.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			return attributes.getValue(name);
		}
	}

	public static Path getRootDir(Path dirPath, String markerFileName) {
		while (true) {
			if (Files.exists(dirPath.resolve(markerFileName))) {
				return dirPath;
			}

			dirPath = dirPath.getParent();

			if (dirPath == null) {
				return null;
			}
		}
	}

	public static String read(Path path) throws IOException {
		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		return content.replace("\r\n", "\n");
	}

	public static Properties readProperties(Path path) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties;
	}

	public static void replaceString(File file, String search, String replace)
		throws IOException {

		Path path = file.toPath();

		String content = read(path);

		String newContent = content.replace(search, replace);

		Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));
	}

	public static void setPosixFilePermissions(
			Path path, Set<PosixFilePermission> posixFilePermissions)
		throws IOException {

		try {
			Files.setPosixFilePermissions(path, posixFilePermissions);
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}
	}

	private static Map<String, InputStream> _getFilesFromClasspath(
			String dirPathString)
		throws Exception {

		if ((dirPathString != null) && (File.separatorChar == '\\')) {
			dirPathString = dirPathString.replace('\\', '/');
		}

		URL url = FileUtil.class.getResource(dirPathString);

		if (url == null) {
			String errorMessage = String.format("%s not found", dirPathString);

			throw new NoSuchElementException(errorMessage);
		}

		Map<String, InputStream> pathMap = new HashMap<>();

		URI uri = url.toURI();

		String scheme = uri.getScheme();

		if (scheme.contains("jar")) {
			FileSystem jarFileSystem = _getJarFileSystem();

			Path fileSystemPath = jarFileSystem.getPath(dirPathString);

			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(fileSystemPath)) {

				for (Path dirPath : directoryStream) {
					String pathString = dirPath.toString();

					if (Files.isDirectory(dirPath)) {
						pathMap.put(pathString, null);
						pathMap.putAll(_getFilesFromClasspath(pathString));
					}
					else {
						InputStream inputStream =
							FileUtil.class.getResourceAsStream(pathString);

						pathMap.put(pathString, inputStream);
					}
				}
			}
		}
		else {
			Path path = Paths.get(uri);

			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(path)) {

				for (Path dirPath : directoryStream) {
					Path folderNamePath = Paths.get(dirPathString);
					Path relativeDirPath = path.relativize(dirPath);

					String pathToResolveString = String.valueOf(
						folderNamePath.resolve(relativeDirPath));

					if (Files.isDirectory(dirPath)) {
						pathMap.put(pathToResolveString + File.separator, null);
						pathMap.putAll(
							_getFilesFromClasspath(pathToResolveString));
					}
					else {
						InputStream inputStream = new FileInputStream(
							dirPath.toFile());

						pathMap.put(pathToResolveString, inputStream);
					}
				}
			}
		}

		return pathMap;
	}

	private static FileSystem _getJarFileSystem() throws Exception {
		Path jarPath = Paths.get(_getJarURI());

		return FileSystems.newFileSystem(jarPath, null);
	}

	private static URI _getJarURI() throws URISyntaxException {
		ProtectionDomain protectionDomain =
			FileUtil.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL jarURL = codeSource.getLocation();

		return jarURL.toURI();
	}

}