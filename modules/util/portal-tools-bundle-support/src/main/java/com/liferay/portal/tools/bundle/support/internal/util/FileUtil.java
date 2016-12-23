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

import java.nio.charset.StandardCharsets;
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

	public static void appendTar(File entryFile, Path entryPath, File tarFile)
		throws IOException {

		try (TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(
					new GzipCompressorOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(tarFile))))) {

			_appendTar(entryFile, entryPath, tarArchiveOutputStream);
		}
	}

	public static void appendZip(File entryFile, Path entryPath, File zipFile)
		throws Exception {

		try (FileSystem fileSystem = _createFileSystem(zipFile, false)) {
			_appendZip(entryFile, entryPath, fileSystem);
		}
	}

	public static void copyDirectory(File sourceFile, File destinationFile)
		throws IOException {

		_copyDirectory(sourceFile.toPath(), destinationFile.toPath());
	}

	public static void copyFile(File sourceFile, File destinationFile)
		throws IOException {

		_copyFile(sourceFile.toPath(), destinationFile.toPath());
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

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			BundleSupport.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static void tar(Path sourcePath, File tarFile, boolean includeFolder)
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
					new BufferedOutputStream(new FileOutputStream(tarFile))))) {

			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = parentPath.relativize(path);

						_appendTar(
							path.toFile(), entryPath, tarArchiveOutputStream);

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

		try (FileSystem fileSystem = _createFileSystem(zipFile, false)) {
			Files.walkFileTree(
				fileSystem.getPath("/"),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = path.subpath(
							stripComponents, path.getNameCount());

						_copyFile(
							path, Paths.get(
								destinationPath.toString(),
								entryPath.toString()));

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	public static void zip(
			Path sourcePath, final File zipFile, boolean includeFolder)
		throws Exception {

		final Path parentPath;

		if (includeFolder) {
			parentPath = sourcePath.getParent();
		}
		else {
			parentPath = sourcePath;
		}

		try (FileSystem fileSystem = _createFileSystem(zipFile, true)) {
			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = parentPath.relativize(path);

						_copyFile(
							path, fileSystem.getPath(entryPath.toString()));

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	private static void _appendTar(
			File entryFile, Path entryPath,
			TarArchiveOutputStream tarArchiveOutputStream)
		throws IOException {

		TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(
			entryPath.toFile());

		tarArchiveEntry.setSize(entryFile.length());

		tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);

		IOUtils.copy(new FileInputStream(entryFile), tarArchiveOutputStream);

		tarArchiveOutputStream.closeArchiveEntry();
	}

	private static void _appendZip(
			File entryFile, Path entryPath, FileSystem fileSystem)
		throws IOException {

		Path zipPath = fileSystem.getPath(entryPath.toString());

		_copyFile(entryFile.toPath(), zipPath);
	}

	private static void _copyDirectory(
			final Path sourcePath, final Path destinationPath)
		throws IOException {

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					_copyFile(
						path,
						destinationPath.resolve(sourcePath.relativize(path)));

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _copyFile(Path sourcePath, Path destinationPath)
		throws IOException {

		Files.createDirectories(destinationPath);

		Files.copy(
			sourcePath.toAbsolutePath(), destinationPath,
			StandardCopyOption.REPLACE_EXISTING);

		Files.setLastModifiedTime(
			destinationPath, Files.getLastModifiedTime(sourcePath));
	}

	private static FileSystem _createFileSystem(File file, boolean create)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("create", Boolean.toString(create));
		properties.put("encoding", StandardCharsets.UTF_8.name());

		URI uri = file.toURI();

		return FileSystems.newFileSystem(
			new URI("jar:" + uri.getScheme(), uri.getPath(), null), properties);
	}

}