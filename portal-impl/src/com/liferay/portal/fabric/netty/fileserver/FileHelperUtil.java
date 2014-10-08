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

package com.liferay.portal.fabric.netty.fileserver;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Shuyang Zhou
 */
public class FileHelperUtil {

	public static final Path TEMP_DIR_PATH = Paths.get(
		System.getProperty("java.io.tmpdir"));

	public static void delete(final boolean quiet, Path... paths) {
		try {
			for (Path path : paths) {
				Files.walkFileTree(
					path,
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult postVisitDirectory(
								Path dir, IOException ioe)
							throws IOException {

							if ((ioe != null) && !quiet) {
								throw ioe;
							}

							Files.delete(dir);

							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(
								Path file, IOException ioe)
							throws IOException {

							if (quiet || (ioe instanceof NoSuchFileException)) {
								return FileVisitResult.CONTINUE;
							}

							throw ioe;
						}

						@Override
						public FileVisitResult visitFile(
								Path file,
								BasicFileAttributes basicFileAttributes)
							throws IOException {

							Files.delete(file);

							return FileVisitResult.CONTINUE;
						}

					});
			}
		}
		catch (IOException ioe) {
			if (!quiet) {
				ReflectionUtil.throwException(ioe);
			}
		}
	}

	public static void delete(Path... paths) {
		delete(false, paths);
	}

	public static void move(Path fromPath, final Path toPath)
		throws IOException {

		move(fromPath, toPath, true);
	}

	public static void move(
			final Path fromPath, final Path toPath, boolean tryAtomicMove)
		throws IOException {

		final AtomicBoolean atomicMove = new AtomicBoolean(tryAtomicMove);
		final AtomicBoolean touched = new AtomicBoolean();
		final Map<Path, FileTime> fileTimes = new HashMap<Path, FileTime>();

		try {
			Files.walkFileTree(
				fromPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult postVisitDirectory(
							Path dir, IOException ioe)
						throws IOException {

						Files.setLastModifiedTime(
							toPath.resolve(fromPath.relativize(dir)),
							fileTimes.remove(dir));

						if (atomicMove.get()) {
							Files.delete(dir);
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path toFile = toPath.resolve(fromPath.relativize(file));

						if (atomicMove.get()) {
							try {
								Files.move(
									file, toFile,
									StandardCopyOption.ATOMIC_MOVE,
									StandardCopyOption.REPLACE_EXISTING);

								touched.set(true);

								return FileVisitResult.CONTINUE;
							}
							catch (AtomicMoveNotSupportedException amnse) {
								atomicMove.set(false);
							}
						}

						Files.copy(
							file, toFile, StandardCopyOption.COPY_ATTRIBUTES,
							StandardCopyOption.REPLACE_EXISTING);

						return FileVisitResult.CONTINUE;
					}

				@Override
				public FileVisitResult preVisitDirectory(
						Path dir, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.copy(
						dir, toPath.resolve(fromPath.relativize(dir)),
						StandardCopyOption.COPY_ATTRIBUTES,
						StandardCopyOption.REPLACE_EXISTING);

					fileTimes.put(dir, Files.getLastModifiedTime(dir));

					return FileVisitResult.CONTINUE;
				}

			});
		}
		catch (IOException ioe) {
			delete(true, toPath);

			if (touched.get()) {
				throw new IOException(
					"Source path " + fromPath + " was left in an " +
						"inconsistent state",
					ioe);
			}

			throw ioe;
		}

		if (!atomicMove.get()) {
			delete(false, fromPath);
		}
	}

	public static Path unzip(Path sourcePath, Path destDirPath)
		throws IOException {

		Path destPath = Files.createTempDirectory(destDirPath, null);

		try (InputStream inputStream = Files.newInputStream(sourcePath)) {
			long startTime = System.currentTimeMillis();

			long rawSize = unzip(new ZipInputStream(inputStream), destPath);

			if (_log.isDebugEnabled()) {
				long zippedSize = Files.size(sourcePath);

				long time = (System.currentTimeMillis() - startTime) / 1000;

				double compressionRatio = rawSize / zippedSize;

				StringBundler sb = new StringBundler(13);

				sb.append("Unzipped ");
				sb.append(sourcePath);
				sb.append(" (");
				sb.append(zippedSize);
				sb.append(" bytes) to ");
				sb.append(destPath);
				sb.append(" (");
				sb.append(rawSize);
				sb.append(" bytes)\" in ");
				sb.append(time);
				sb.append("s with a ");
				sb.append(compressionRatio);
				sb.append("compression ratio");

				_log.debug(sb.toString());
			}
		}
		catch (IOException ioe) {
			delete(destPath);

			throw ioe;
		}

		return destPath;
	}

	public static long unzip(ZipInputStream zipInputStream, Path destPath)
		throws IOException {

		final AtomicLong rawSize = new AtomicLong();

		try (ZipInputStream autoCloseZipInputStream = zipInputStream) {
			ZipEntry zipEntry = null;

			while ((zipEntry = autoCloseZipInputStream.getNextEntry()) !=
						null) {

				if (zipEntry.isDirectory()) {
					continue;
				}

				Path entryPath = destPath.resolve(zipEntry.getName());

				Files.createDirectories(entryPath.getParent());

				long size = Files.copy(autoCloseZipInputStream, entryPath);

				rawSize.addAndGet(size);

				Files.setLastModifiedTime(
					entryPath,
					FileTime.fromMillis(
						BigEndianCodec.getLong(zipEntry.getExtra(), 0)));

				long length = BigEndianCodec.getLong(zipEntry.getExtra(), 8);

				if (size != length) {
					throw new IOException(
						"Zip stream for entry " + zipEntry.getName() +
							" is " + size + " bytes but should " + length +
								" bytes");
				}
			}
		}

		return rawSize.get();
	}

	public static Path zip(
			Path sourcePath, Path destDirPath,
			CompressionLevel compressionLevel)
		throws IOException {

		Path zipPath = Files.createTempFile(destDirPath, null, null);

		try (OutputStream outputStream = Files.newOutputStream(zipPath)) {
			ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

			zipOutputStream.setLevel(compressionLevel.getLevel());

			long startTime = System.currentTimeMillis();

			long rawSize = zip(sourcePath, zipOutputStream);

			if (_log.isDebugEnabled()) {
				long zippedSize = Files.size(zipPath);

				long time = (System.currentTimeMillis() - startTime) / 1000;

				double compressionRatio = rawSize / zippedSize;

				StringBundler sb = new StringBundler(13);

				sb.append("Zipped ");
				sb.append(sourcePath);
				sb.append(" (");
				sb.append(rawSize);
				sb.append(" bytes) to ");
				sb.append(zipPath);
				sb.append(" (");
				sb.append(zippedSize);
				sb.append(" bytes)\" in ");
				sb.append(time);
				sb.append("s with a ");
				sb.append(compressionRatio);
				sb.append("compression ratio");

				_log.debug(sb.toString());
			}
		}
		catch (IOException ioe) {
			Files.delete(zipPath);

			throw ioe;
		}

		return zipPath;
	}

	public static long zip(
			final Path sourcePath, ZipOutputStream zipOutputStream)
		throws IOException {

		final AtomicLong rawSize = new AtomicLong();

		final byte[] buffer = new byte[16];

		try (ZipOutputStream autoClosezipOutputStream = zipOutputStream) {
			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path relativePath = sourcePath.relativize(file);

						ZipEntry zipEntry = new ZipEntry(
							relativePath.toString());

						FileTime fileTime =
							basicFileAttributes.lastModifiedTime();

						rawSize.addAndGet(basicFileAttributes.size());

						BigEndianCodec.putLong(buffer, 0, fileTime.toMillis());
						BigEndianCodec.putLong(
							buffer, 8, basicFileAttributes.size());

						zipEntry.setExtra(buffer);

						autoClosezipOutputStream.putNextEntry(zipEntry);

						Files.copy(file, autoClosezipOutputStream);

						autoClosezipOutputStream.closeEntry();

						return FileVisitResult.CONTINUE;
					}

				});
		}

		return rawSize.get();
	}

	private static final Log _log = LogFactoryUtil.getLog(FileHelperUtil.class);

}