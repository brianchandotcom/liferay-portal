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

package com.liferay.jenkins.results.parser;

import com.amazonaws.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Set;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author Michael Hashimoto
 */
public class TarGzUtil {

	public static boolean debug = false;

	public static void archive(File src, File destTarGz) {
		if (src == null) {
			throw new RuntimeException("No file specified to archive");
		}

		if (!src.exists()) {
			throw new RuntimeException("Could not find " + src);
		}

		if (src.isDirectory()) {
			File[] filesToArchive = src.listFiles();

			if (filesToArchive == null) {
				throw new RuntimeException("No files to archive in " + src);
			}
		}

		File parent = destTarGz.getParentFile();

		if (!parent.exists()) {
			parent.mkdirs();
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				destTarGz);
			BufferedOutputStream bufferedOutputStream =
				new BufferedOutputStream(fileOutputStream, _BUFFER_SIZE);
			GzipCompressorOutputStream gzipCompressorOutputStream =
				new GzipCompressorOutputStream(bufferedOutputStream);
			TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(gzipCompressorOutputStream)) {

			tarArchiveOutputStream.setLongFileMode(
				TarArchiveOutputStream.LONGFILE_POSIX);

			if (src.isFile()) {
				_archiveFile(src, src, tarArchiveOutputStream);
			}
			else {
				_archiveDir(src, src, tarArchiveOutputStream);
			}

			tarArchiveOutputStream.flush();

			tarArchiveOutputStream.finish();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public static void unarchive(File srcTarGz, File destDir) {
		if (destDir.exists()) {
			try {
				FileUtils.deleteDirectory(destDir);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		try (FileInputStream fileInputStream = new FileInputStream(srcTarGz);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
				fileInputStream, _BUFFER_SIZE);
			GzipCompressorInputStream gzipCompressorInputStream =
				new GzipCompressorInputStream(bufferedInputStream);
			TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(gzipCompressorInputStream)) {

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry =
						tarArchiveInputStream.getNextTarEntry()) != null) {

				if (!tarArchiveInputStream.canReadEntryData(tarArchiveEntry)) {
					System.out.println(
						"Could not read " + tarArchiveEntry.getName());

					continue;
				}

				if (tarArchiveEntry.isDirectory()) {
					_unarchiveDir(destDir, tarArchiveEntry);
				}
				else {
					_unarchiveFile(
						destDir, tarArchiveEntry, tarArchiveInputStream);
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public static enum PosixIntegerValue {

		GROUP_EXECUTE(0010), GROUP_READ(0040), GROUP_WRITE(0020),
		OTHERS_EXECUTE(0001), OTHERS_READ(0004), OTHERS_WRITE(0002),
		OWNER_EXECUTE(0100), OWNER_READ(0400), OWNER_WRITE(0200);

		public Integer getValue() {
			return _value;
		}

		private PosixIntegerValue(Integer value) {
			_value = value;
		}

		private final Integer _value;

	}

	private static void _archiveDir(
			File src, File dir, ArchiveOutputStream archiveOutputStream)
		throws IOException {

		if (!dir.isDirectory()) {
			throw new RuntimeException(dir + " is not a dir");
		}

		File[] files = dir.listFiles();

		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isFile()) {
				_archiveFile(src, file, archiveOutputStream);
			}
			else {
				_archiveDir(src, file, archiveOutputStream);
			}
		}
	}

	private static void _archiveFile(
			File src, File file, ArchiveOutputStream archiveOutputStream)
		throws IOException {

		if (!file.isFile()) {
			throw new RuntimeException(file + " is not a file");
		}

		if (debug) {
			System.out.println(file);
		}

		String filePath = JenkinsResultsParserUtil.getCanonicalPath(file);

		String archiveEntryName = filePath.replace(
			JenkinsResultsParserUtil.getCanonicalPath(src) + "/", "");

		ArchiveEntry archiveEntry = archiveOutputStream.createArchiveEntry(
			file, archiveEntryName);

		if (!(archiveEntry instanceof TarArchiveEntry)) {
			throw new RuntimeException("Invalid archive entry");
		}

		TarArchiveEntry tarArchiveEntry = (TarArchiveEntry)archiveEntry;

		tarArchiveEntry.setMode(
			_getPosixIntegerValue(
				Files.getPosixFilePermissions(file.toPath())));

		archiveOutputStream.putArchiveEntry(tarArchiveEntry);

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			IOUtils.copy(fileInputStream, archiveOutputStream);
		}

		archiveOutputStream.closeArchiveEntry();
	}

	private static Set<PosixFilePermission> _getPosixFilePermissions(
		Integer posixIntegerValue) {

		String binaryString = Integer.toBinaryString(posixIntegerValue);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < binaryString.length(); i++) {
			char c = binaryString.charAt(i);

			if (c == '0') {
				sb.append("-");

				continue;
			}

			int accessIndex = i % 3;

			if (accessIndex == 0) {
				sb.append("r");
			}
			else if (accessIndex == 1) {
				sb.append("w");
			}
			else if (accessIndex == 2) {
				sb.append("x");
			}
		}

		try {
			return PosixFilePermissions.fromString(sb.toString());
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Invalid posix integer value " + posixIntegerValue, e);
		}
	}

	private static Integer _getPosixIntegerValue(
		Set<PosixFilePermission> posixFilePermissions) {

		Integer total = 0;

		for (PosixFilePermission posixFilePermission : posixFilePermissions) {
			if (posixFilePermission == null) {
				continue;
			}

			PosixIntegerValue posixIntegerValue = PosixIntegerValue.valueOf(
				StringUtils.upperCase(posixFilePermission.toString()));

			total += posixIntegerValue.getValue();
		}

		return total;
	}

	private static void _unarchiveDir(
		File destDir, TarArchiveEntry tarArchiveEntry) {

		File dir = new File(destDir, tarArchiveEntry.getName());

		if (debug) {
			System.out.println(dir);
		}

		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	private static void _unarchiveFile(
		File destDir, TarArchiveEntry tarArchiveEntry,
		TarArchiveInputStream tarArchiveInputStream) {

		File file = new File(destDir, tarArchiveEntry.getName());

		if (debug) {
			System.out.println(file);
		}

		File parent = file.getParentFile();

		if (!parent.exists()) {
			parent.mkdirs();
		}

		try {
			try (FileOutputStream fileOutputStream = new FileOutputStream(
					file)) {

				IOUtils.copy(tarArchiveInputStream, fileOutputStream);
			}

			Files.setPosixFilePermissions(
				file.toPath(),
				_getPosixFilePermissions(tarArchiveEntry.getMode()));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final int _BUFFER_SIZE = 8192;

}