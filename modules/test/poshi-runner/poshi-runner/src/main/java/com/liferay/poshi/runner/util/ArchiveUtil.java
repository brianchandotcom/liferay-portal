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

package com.liferay.poshi.runner.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Kenji Heigel
 */
public class ArchiveUtil {

	public static void archive(File sourceFile, File targetFile) {
		String targetFileName = targetFile.getName();

		if (!targetFileName.matches(".*\\.(jar|lar|war|zip)")) {
			throw new RuntimeException("Invalid archive path " + targetFile);
		}

		List<ArchiveZipEntry> archiveZipEntries = new ArrayList<>();

		try {
			Path sourceFilePath = Paths.get(sourceFile.getCanonicalPath());

			Files.walkFileTree(
				sourceFilePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
						Path file, BasicFileAttributes attributes) {

						Path targetFilePath = sourceFilePath.relativize(file);

						String targetFilePathString = targetFilePath.toString();

						targetFilePathString = StringUtil.replace(
							targetFilePathString, "\\", "/");

						archiveZipEntries.add(
							new ArchiveZipEntry(targetFilePathString, file));

						return FileVisitResult.CONTINUE;
					}

				});

			targetFile.delete();

			File parentFile = targetFile.getParentFile();

			parentFile.mkdirs();

			File tmpDir = new File(sourceFile.getParentFile(), "tmp");

			tmpDir.mkdir();

			tmpDir.deleteOnExit();

			File tmpFile = new File(tmpDir, targetFileName);

			tmpFile.delete();

			Collections.sort(archiveZipEntries);

			try (ZipOutputStream zipOutputStream = new ZipOutputStream(
					new FileOutputStream(tmpFile))) {

				for (ArchiveZipEntry archiveZipEntry : archiveZipEntries) {
					archiveZipEntry.writeToZipOutputStream(zipOutputStream);
				}
			}

			Files.move(
				Paths.get(tmpFile.getCanonicalPath()),
				Paths.get(targetFile.getCanonicalPath()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to archive " + sourceFile + " to " + targetFile,
				ioException);
		}
	}

	public static void archive(String sourceFilePath, String targetFilePath) {
		archive(new File(sourceFilePath), new File(targetFilePath));
	}

	public static void archive(
		String sourceFile, String targetDir, String archiveType) {

		Path sourceDirPath = Paths.get(sourceFile);

		String archiveFilePath =
			targetDir + "/" + sourceDirPath.getFileName() + "." + archiveType;

		archive(sourceFile, archiveFilePath);
	}

	private static final class ArchiveZipEntry
		extends ZipEntry implements Comparable<ArchiveZipEntry> {

		public ArchiveZipEntry(String name, Path path) {
			super(name);

			_path = path;
		}

		@Override
		public int compareTo(ArchiveZipEntry archiveZipEntry) {
			String manifestFileName = "META-INF/MANIFEST.MF";

			if (manifestFileName.equals(archiveZipEntry.getName())) {
				return 1;
			}

			String name = getName();

			if (manifestFileName.equals(name)) {
				return -1;
			}

			return name.compareTo(archiveZipEntry.getName());
		}

		public void writeToZipOutputStream(ZipOutputStream zipOutputStream) {
			try (InputStream inputStream = Files.newInputStream(_path)) {
				zipOutputStream.putNextEntry(this);

				byte[] bytes = new byte[1024];

				int bytesRead = inputStream.read(bytes);

				while (bytesRead > 0) {
					zipOutputStream.write(bytes, 0, bytesRead);

					bytesRead = inputStream.read(bytes);
				}

				zipOutputStream.flush();

				zipOutputStream.closeEntry();
			}
			catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

		private final Path _path;

	}

}