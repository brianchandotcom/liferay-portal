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

package com.liferay.project.templates.internal.util;

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
						Path dirPath, IOException ioe)
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

	public static void extractDirectory(
			String dirName, final Path destinationDirPath)
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
					catch (Throwable th) {
						throw new RuntimeException(th);
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

				Path fileNamePath = path.getFileName();

				String fileName = fileNamePath.toString();

				if (fileName.matches(regex)) {
					return path;
				}
			}
		}

		return null;
	}

	public static Path getJarPath() throws URISyntaxException {
		URI jarUri = _getJarUri();

		return Paths.get(jarUri.getPath());
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

	public static void setPosixFilePermissions(
			Path path, Set<PosixFilePermission> posixFilePermissions)
		throws IOException {

		try {
			Files.setPosixFilePermissions(path, posixFilePermissions);
		}
		catch (UnsupportedOperationException uoe) {
		}
	}

	private static Map<String, InputStream> _getFilesFromClasspath(
			String dirPathString)
		throws Exception {

		Map<String, InputStream> pathMap = new HashMap<>();

		URL url = FileUtil.class.getResource(dirPathString);

		URI uri = url.toURI();

		if (uri == null) {
			String errorMessage = String.format("%s not found", dirPathString);

			throw new NoSuchElementException(errorMessage);
		}

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
						InputStream is = FileUtil.class.getResourceAsStream(
							pathString);

						pathMap.put(pathString, is);
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

					Path pathToResolve = folderNamePath.resolve(
						relativeDirPath);

					String pathToResolveString = pathToResolve.toString();

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

	private static FileSystem _getJarFileSystem()
		throws IOException, URISyntaxException {

		URI jarUri = _getJarUri();

		Path jarPath = Paths.get(jarUri.getPath());

		FileSystem fileSystem = FileSystems.newFileSystem(jarPath, null);

		return fileSystem;
	}

	private static URI _getJarUri() throws URISyntaxException {
		ProtectionDomain protectionDomain =
			FileUtil.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL jarUrl = codeSource.getLocation();

		return jarUrl.toURI();
	}

}