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
import java.io.InputStream;

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

import java.text.DecimalFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

		try (FileSystem fileSystem = _createFileSystem(
				zipFile.toPath(), false)) {

			_appendZip(entryFile, entryPath, fileSystem);
		}
	}

	public static void copyDirectory(
			final Path dirPath, final Path destinationDirPath)
		throws IOException {

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path relativePath = dirPath.relativize(path);

					Path destinationPath = destinationDirPath.resolve(
						relativePath);

					copyFile(path, destinationPath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static void copyFile(Path path, Path destinationPath)
		throws IOException {

		Files.createDirectories(destinationPath.getParent());

		Files.copy(path, destinationPath, StandardCopyOption.REPLACE_EXISTING);

		Files.setLastModifiedTime(
			destinationPath, Files.getLastModifiedTime(path));
	}

	public static void deleteDirectory(Path dirPath) throws IOException {
		if (Files.notExists(dirPath)) {
			return;
		}

		Files.walkFileTree(
			dirPath,
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

	public static String getFileLength(long length) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		if (length > _FILE_LENGTH_MB) {
			return decimalFormat.format((double)length / _FILE_LENGTH_MB) +
				" MB";
		}

		if (length > +_FILE_LENGTH_KB) {
			return decimalFormat.format((double)length / _FILE_LENGTH_KB) +
				" KB";
		}

		return decimalFormat.format(length) + " B";
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			BundleSupport.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static boolean isPosixSupported(Path path) {
		FileSystem fileSystem = path.getFileSystem();

		Set<String> supportedFileAttributeViews =
			fileSystem.supportedFileAttributeViews();

		return supportedFileAttributeViews.contains("posix");
	}

	public static String read(File file) throws IOException {
		return new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
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
						new BufferedOutputStream(
							new FileOutputStream(tarFile))))) {

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

	public static void unpack(
			Path path, Path destinationDirPath, int stripComponents)
		throws Exception {

		String fileName = String.valueOf(path.getFileName());

		if (fileName.endsWith(".gz") || fileName.endsWith(".tar") ||
			fileName.endsWith(".tgz")) {

			_untar(path, destinationDirPath, stripComponents);
		}
		else if (fileName.endsWith(".zip")) {
			_unzip(path, destinationDirPath, stripComponents);
		}
		else {
			throw new UnsupportedOperationException(
				"Unsupported format for " + fileName);
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

		try (FileSystem fileSystem = _createFileSystem(
				zipFile.toPath(), true)) {

			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path entryPath = parentPath.relativize(path);

						copyFile(
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

		copyFile(entryFile.toPath(), zipPath);
	}

	private static FileSystem _createFileSystem(Path path, boolean create)
		throws Exception {

		URI uri = path.toUri();

		Map<String, String> properties = new HashMap<>();

		properties.put("create", Boolean.toString(create));
		properties.put("encoding", StandardCharsets.UTF_8.name());

		return FileSystems.newFileSystem(
			new URI("jar:" + uri.getScheme(), uri.getPath(), null), properties);
	}

	private static void _untar(
			Path tarPath, Path destinationDirPath, int stripComponents)
		throws IOException {

		try (InputStream inputStream = Files.newInputStream(tarPath);
			TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(
					new GzipCompressorInputStream(inputStream))) {

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry =
						tarArchiveInputStream.getNextTarEntry()) != null) {

				if (tarArchiveEntry.isDirectory()) {
					continue;
				}

				Path destinationPath = Paths.get(tarArchiveEntry.getName());

				destinationPath = destinationDirPath.resolve(
					destinationPath.subpath(
						stripComponents, destinationPath.getNameCount()));

				Files.createDirectories(destinationPath.getParent());

				Files.copy(tarArchiveInputStream, destinationPath);

				Date lastModifiedDate = tarArchiveEntry.getLastModifiedDate();

				Files.setLastModifiedTime(
					destinationPath,
					FileTime.fromMillis(lastModifiedDate.getTime()));
			}
		}
	}

	private static void _unzip(
			Path zipPath, final Path destinationDirPath,
			final int stripComponents)
		throws Exception {

		try (FileSystem fileSystem = _createFileSystem(zipPath, false)) {
			Files.walkFileTree(
				fileSystem.getPath("/"),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path relativePath = path.subpath(
							stripComponents, path.getNameCount());

						Path destinationPath = destinationDirPath.resolve(
							relativePath.toString());

						copyFile(path, destinationPath);

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	private static final long _FILE_LENGTH_KB = 1024;

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

}