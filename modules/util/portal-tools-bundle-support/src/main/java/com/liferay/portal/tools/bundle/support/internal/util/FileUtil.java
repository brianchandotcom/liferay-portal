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

package com.liferay.portal.tools.bundle.support.internal.util;

import com.liferay.portal.tools.bundle.support.BundleSupport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class FileUtil {

	public static void copyDirectory(File sourceFile, File destinationFile)
		throws IOException {

		copyDirectory(sourceFile.toPath(), destinationFile.toPath());
	}

	public static void copyDirectory(
			final Path sourcePath, final Path destinationPath)
		throws IOException {

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					copyFile(
						path,
						destinationPath.resolve(sourcePath.relativize(path)));

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void copyFile(File sourceFile, File destinationFile)
		throws IOException {

		copyFile(sourceFile.toPath(), destinationFile.toPath());
	}

	public static void copyFile(Path sourcePath, Path destinationPath)
		throws IOException {

		Files.createDirectories(destinationPath);

		Files.copy(
			sourcePath.toAbsolutePath(), destinationPath,
			StandardCopyOption.REPLACE_EXISTING);

		Files.setLastModifiedTime(
			destinationPath, Files.getLastModifiedTime(sourcePath));
	}

	public static FileSystem createFileSystem(File file, boolean create)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("create", Boolean.toString(create));
		properties.put("encoding", "UTF-8");

		URI uri = file.toURI();

		return FileSystems.newFileSystem(
			new URI("jar:" + uri.getScheme(), uri.getPath(), null), properties);
	}

	public static void deleteDirectory(Path sourcePath) throws IOException {
		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioe)
					throws IOException {

					Files.delete(path);

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

	public static String getExtension(String fileName) {
		int pos = fileName.lastIndexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	public static String getFileName(String path) {
		String fileName = path.substring(path.lastIndexOf('/') + 1);

		fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);

		return fileName;
	}

	public static String getFileNameWithExtension(String path) {
		String fileName = getFileName(path);

		fileName = fileName.replace("." + getExtension(fileName), "");

		return fileName;
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			BundleSupport.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static void tar(
			Path sourcePath, Path destinationPath, boolean includeFolder)
		throws Exception {

		final Path parentPath;

		if (includeFolder) {
			parentPath = sourcePath.getParent();
		}
		else {
			parentPath = sourcePath;
		}

		try (TarArchiveOutputStream tarArchiveOutputStream =
			new TarArchiveOutputStream(
				new GzipCompressorOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(destinationPath.toFile()))))) {

			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path childPath = parentPath.relativize(path);

						TarArchiveEntry tarFile = new TarArchiveEntry(
							childPath.toFile());

						tarFile.setSize(basicFileAttributes.size());

						tarArchiveOutputStream.putArchiveEntry(tarFile);

						IOUtils.copy(
							new FileInputStream(path.toFile()),
							tarArchiveOutputStream);

						tarArchiveOutputStream.closeArchiveEntry();

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	public static void untar(
			File tarFile, Path destinationPath, int stripComponents)
		throws IOException {

		try (TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(
					new GzipCompressorInputStream(
						new FileInputStream(tarFile)))) {

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry =
						tarArchiveInputStream.getNextTarEntry()) != null) {

				if (tarArchiveEntry.isDirectory()) {
					continue;
				}

				Path entryPath = Paths.get(tarArchiveEntry.getName());

				entryPath = destinationPath.resolve(
					entryPath.subpath(
						stripComponents, entryPath.getNameCount()));

				Files.createDirectories(entryPath.getParent());

				Files.copy(tarArchiveInputStream, entryPath);

				Date lastModifiedDate = tarArchiveEntry.getLastModifiedDate();

				Files.setLastModifiedTime(
					entryPath, FileTime.fromMillis(lastModifiedDate.getTime()));
			}
		}
	}

	public static void unzip(
			File zipFile, final Path destinationPath, final int stripComponents)
		throws Exception {

		try (FileSystem fileSystem =
				FileUtil.createFileSystem(zipFile, false)) {

			Files.walkFileTree(
				fileSystem.getPath("/"),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path subpath = path.subpath(
							stripComponents, path.getNameCount());

						copyFile(
							path, Paths.get(
								destinationPath.toString(),
								subpath.toString()));

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	public static void zip(
			Path sourcePath, final File destinationFile, boolean includeFolder)
		throws Exception {

		final Path parentPath;

		if (includeFolder) {
			parentPath = sourcePath.getParent();
		}
		else {
			parentPath = sourcePath;
		}

		try (FileSystem fileSystem =
				FileUtil.createFileSystem(destinationFile, true)) {

			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path childPath = parentPath.relativize(path);

						copyFile(
							path, fileSystem.getPath(childPath.toString()));

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	private static final int _BUFFER = 2048;

}