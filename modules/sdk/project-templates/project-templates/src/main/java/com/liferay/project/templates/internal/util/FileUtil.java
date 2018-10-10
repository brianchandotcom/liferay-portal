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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Andrea Di Giorgi
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

		Map<URL, String> resources = _getResources(dirName);

		for (Map.Entry<URL, String> entry : resources.entrySet()) {
			URL suburl = entry.getKey();

			String substring = entry.getValue();

			Path subpath = Paths.get(
				substring.replaceFirst(dirName + File.separator, ""));

			Path destination = destinationDirPath.resolve(subpath);

			try (InputStream is = suburl.openStream()) {
				Files.createDirectories(destination.getParent());
				Files.copy(
					is, destination, StandardCopyOption.REPLACE_EXISTING);
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

	private static Map<URL, String> _getResources(final String path)
		throws IOException {

		Map<URL, String> urlListToReturn = new HashMap<>();

		Thread currentThread = Thread.currentThread();

		final ClassLoader loader = currentThread.getContextClassLoader();

		try (final InputStream inputStream = loader.getResourceAsStream(path);
			final InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, StandardCharsets.UTF_8);
			final BufferedReader bufferedReader =
				new BufferedReader(inputStreamReader)) {

			List<URL> urls;

			try (Stream<String> lines = bufferedReader.lines()) {
				urls = lines.map(
					l -> path + "/" + l
				).map(
					r -> loader.getResource(r)
				).collect(
					Collectors.toList()
				);
			}

			if ((urls != null) && !urls.isEmpty()) {
				for (URL url : urls) {
					if (url != null) {
						String urlString = url.toString();

						String[] urlSplit = urlString.split(File.separator);

						String name =
							path + File.separator + urlSplit[urlSplit.length -
								1];

						if (_isDirectory(url)) {
							urlListToReturn.putAll(_getResources(name));
						}
						else {
							urlListToReturn.put(url, name);
						}
					}
				}
			}
		}
		catch (Throwable e) {
		}

		return urlListToReturn;
	}

	private static boolean _isDirectory(URL url) throws IOException {
		boolean directory = false;

		String protocol = url.getProtocol();

		String fileString = null;

		if (protocol.equals("file")) {
			fileString = url.getFile();

			File file = new File(fileString);

			return file.isDirectory();
		}
		else if (protocol.equals("jar")) {
			fileString = url.getFile();

			int exclamationIndex = fileString.indexOf('!');

			String jarPathString = fileString.substring(exclamationIndex + 2);

			URL fileUrl = new URL(fileString.substring(0, exclamationIndex));

			fileString = fileUrl.getFile();

			try (ZipFile zipFile = new ZipFile(fileString)) {
				ZipEntry zipEntry = zipFile.getEntry(jarPathString);

				directory = zipEntry.isDirectory();

				if (!directory) {
					try (InputStream input = zipFile.getInputStream(zipEntry)) {
						directory = input == null;
					}
				}
			}
		}
		else {
			throw new RuntimeException("Unrecognized protocol: " + protocol);
		}

		return directory;
	}

}