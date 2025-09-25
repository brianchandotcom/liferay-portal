/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OSDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * @author Wade Cao
 */
public class UncompressUtil {

	public static String unarchive(
			Path tarGzFilePath, Path destinationDirectoryPath)
		throws IOException {

		Files.createDirectories(destinationDirectoryPath);

		try (InputStream inputStream = Files.newInputStream(tarGzFilePath);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream, _CHARS_BUFFER_SIZE);
			GzipCompressorInputStream gzipCompressorInputStream =
				new GzipCompressorInputStream(bufferedInputStream);
			TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(gzipCompressorInputStream)) {

			String rootArchiveName = StringPool.BLANK;

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry =
						tarArchiveInputStream.getNextTarEntry()) != null) {

				if (tarArchiveInputStream.canReadEntryData(tarArchiveEntry)) {
					if (_isZipSlipVulnerable(
							destinationDirectoryPath,
							tarArchiveEntry.getName())) {

						continue;
					}

					if (rootArchiveName.equals(StringPool.BLANK)) {
						rootArchiveName = tarArchiveEntry.getName();
					}

					Path path = destinationDirectoryPath.resolve(
						tarArchiveEntry.getName());

					if (tarArchiveEntry.isDirectory()) {
						Files.createDirectory(path);
					}
					else {
						Files.copy(tarArchiveInputStream, path);
					}

					_setFilePermission(path);
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to read " + tarArchiveEntry.getName() +
								" from tar archive entry");
					}
				}
			}

			return rootArchiveName;
		}
	}

	public static void unzip(Path zipFilePath, Path destinationDirectoryPath)
		throws IOException {

		Files.createDirectories(destinationDirectoryPath);

		try (InputStream inputStream = Files.newInputStream(zipFilePath);
			ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

			ZipEntry zipEntry;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				if (_isZipSlipVulnerable(
						destinationDirectoryPath, zipEntry.getName())) {

					continue;
				}

				Path path = destinationDirectoryPath.resolve(
					zipEntry.getName());

				if (zipEntry.isDirectory()) {
					Files.createDirectory(path);
				}
				else {
					Files.copy(zipInputStream, path);
				}

				_setFilePermission(path);
			}
		}
	}

	private static boolean _isZipSlipVulnerable(
			Path destinationPath, String tarArchiveEntryName)
		throws IOException {

		File canonicalDirectoryFile = destinationPath.toFile();

		String canonicalDirectoryPath =
			canonicalDirectoryFile.getCanonicalPath();

		File destinationFile = new File(
			destinationPath.toFile(), tarArchiveEntryName);

		String canonicalDestinationFile = destinationFile.getCanonicalPath();

		if (!canonicalDestinationFile.startsWith(
				canonicalDirectoryPath + File.separator)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Entry ", tarArchiveEntryName,
						" is outside of the target directory ",
						canonicalDirectoryPath));
			}

			return true;
		}

		return false;
	}

	private static void _setFilePermission(Path path) throws IOException {
		if (OSDetector.isWindows()) {
			File file = path.toFile();

			file.setExecutable(true);
			file.setReadable(true);
			file.setWritable(true);
		}
		else {
			Files.setPosixFilePermissions(
				path, PosixFilePermissions.fromString("rwxrwxrwx"));
		}
	}

	private static final int _CHARS_BUFFER_SIZE = 8192;

	private static final Log _log = LogFactoryUtil.getLog(UncompressUtil.class);

}