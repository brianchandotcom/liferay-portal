/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.amazonaws.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.apache.commons.io.IOUtils;

/**
 * @author Michael Hashimoto
 */
public class TGZUtil {

	public static boolean debug = false;

	public static void archive(File sourceFile, File archiveFile)
		throws IOException {

		if (!sourceFile.exists()) {
			throw new FileNotFoundException("Unable to find " + sourceFile);
		}

		File parentDir = archiveFile.getParentFile();

		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				archiveFile);
			BufferedOutputStream bufferedOutputStream =
				new BufferedOutputStream(fileOutputStream, _CHARS_BUFFER_SIZE);
			GzipCompressorOutputStream gzipCompressorOutputStream =
				new GzipCompressorOutputStream(bufferedOutputStream);
			TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(gzipCompressorOutputStream)) {

			tarArchiveOutputStream.setBigNumberMode(
				TarArchiveOutputStream.BIGNUMBER_POSIX);
			tarArchiveOutputStream.setLongFileMode(
				TarArchiveOutputStream.LONGFILE_POSIX);

			if (sourceFile.isFile()) {
				_archiveFile(
					sourceFile.getParentFile(), sourceFile,
					tarArchiveOutputStream);
			}
			else {
				_archiveDir(
					sourceFile.getParentFile(), sourceFile,
					tarArchiveOutputStream);
			}

			tarArchiveOutputStream.flush();

			tarArchiveOutputStream.finish();
		}
	}

	public static void unarchive(File archiveFile, File destinationDir)
		throws IOException {

		if (!destinationDir.exists()) {
			destinationDir.mkdirs();
		}

		try (FileInputStream fileInputStream = new FileInputStream(archiveFile);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
				fileInputStream, _CHARS_BUFFER_SIZE);
			GzipCompressorInputStream gzipCompressorInputStream =
				new GzipCompressorInputStream(bufferedInputStream);
			TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(gzipCompressorInputStream)) {

			TarArchiveEntry tarArchiveEntry =
				tarArchiveInputStream.getNextTarEntry();

			while (tarArchiveEntry != null) {
				if (tarArchiveInputStream.canReadEntryData(tarArchiveEntry)) {
					if (tarArchiveEntry.isDirectory()) {
						_unarchiveDir(destinationDir, tarArchiveEntry);
					}
					else {
						_unarchiveFile(
							destinationDir, tarArchiveEntry,
							tarArchiveInputStream);
					}
				}
				else {
					System.out.println(
						"Unable to read " + tarArchiveEntry.getName());
				}

				tarArchiveEntry = tarArchiveInputStream.getNextTarEntry();
			}
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
			File sourceRootDir, File dir,
			ArchiveOutputStream archiveOutputStream)
		throws IOException {

		if (debug) {
			System.out.println("Archiving " + dir);
		}

		_archiveFile(sourceRootDir, dir, archiveOutputStream);

		File[] files = dir.listFiles();

		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isFile()) {
				_archiveFile(sourceRootDir, file, archiveOutputStream);
			}
			else {
				_archiveDir(sourceRootDir, file, archiveOutputStream);
			}
		}
	}

	private static void _archiveFile(
			File sourceRootDir, File file,
			ArchiveOutputStream archiveOutputStream)
		throws IOException {

		if (debug) {
			System.out.println("Archiving " + file);
		}

		String filePath = JenkinsResultsParserUtil.getCanonicalPath(file);

		String archiveEntryName = filePath.replace(
			JenkinsResultsParserUtil.getCanonicalPath(sourceRootDir) + "/", "");

		ArchiveEntry archiveEntry = archiveOutputStream.createArchiveEntry(
			file, archiveEntryName);

		if (!(archiveEntry instanceof TarArchiveEntry)) {
			throw new IOException("Invalid archive entry");
		}

		TarArchiveEntry tarArchiveEntry = (TarArchiveEntry)archiveEntry;

		tarArchiveEntry.setMode(
			_getPosixIntegerValue(
				Files.getPosixFilePermissions(file.toPath())));

		archiveOutputStream.putArchiveEntry(tarArchiveEntry);

		if (file.isFile()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				IOUtils.copy(fileInputStream, archiveOutputStream);
			}
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
		catch (Exception exception) {
			throw new RuntimeException(
				"Invalid POSIX integer value " + posixIntegerValue, exception);
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
		File destinationRootDir, TarArchiveEntry tarArchiveEntry) {

		File dir = new File(destinationRootDir, tarArchiveEntry.getName());

		if (debug) {
			System.out.println("Unarchiving " + dir);
		}

		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	private static void _unarchiveFile(
			File destinationRootDir, TarArchiveEntry tarArchiveEntry,
			TarArchiveInputStream tarArchiveInputStream)
		throws IOException {

		File file = new File(destinationRootDir, tarArchiveEntry.getName());

		if (debug) {
			System.out.println("Unarchiving " + file);
		}

		File parentDir = file.getParentFile();

		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			IOUtils.copy(tarArchiveInputStream, fileOutputStream);
		}

		Files.setPosixFilePermissions(
			file.toPath(), _getPosixFilePermissions(tarArchiveEntry.getMode()));
	}

	private static final int _CHARS_BUFFER_SIZE = 8192;

}