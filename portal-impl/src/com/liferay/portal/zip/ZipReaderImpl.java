/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncFilterInputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Raymond Augé
 */
public class ZipReaderImpl implements ZipReader {

	public ZipReaderImpl(File file) {
		_file = file;
	}

	public ZipReaderImpl(InputStream inputStream) throws IOException {
		this(FileUtil.createTempFile(inputStream));

		_tempFile = _file;
	}

	@Override
	public void close() {
		if (_tempFile != null) {
			_tempFile.delete();
		}
	}

	@Override
	public List<String> getEntries() {
		List<String> entries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(_file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (zipEntry.isDirectory()) {
					continue;
				}

				entries.add(zipEntry.getName());
			}
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}

		entries.sort(null);

		return entries;
	}

	@Override
	public byte[] getEntryAsByteArray(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		try {
			return StreamUtil.toByteArray(getEntryAsInputStream(name));
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return null;
	}

	@Override
	public InputStream getEntryAsInputStream(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		try {
			final ZipFile zipFile = new ZipFile(_file);

			ZipEntry zipEntry = zipFile.getEntry(name);

			if ((zipEntry != null) && !zipEntry.isDirectory()) {
				InputStream inputStream = zipFile.getInputStream(zipEntry);

				// Null check inputStream to overcome
				// jdk.util.zip.ensureTrailingSlash issue in between
				// [JDK 8u141, JDK 8u144)

				if (inputStream != null) {
					return new UnsyncFilterInputStream(inputStream) {

						@Override
						public void close() throws IOException {
							super.close();

							zipFile.close();
						}

					};
				}
			}

			zipFile.close();

			return null;
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	@Override
	public String getEntryAsString(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		byte[] bytes = getEntryAsByteArray(name);

		if (bytes != null) {
			return new String(bytes);
		}

		return null;
	}

	@Override
	public List<String> getFolderEntries(String path) {
		if (Validator.isNull(path)) {
			return Collections.emptyList();
		}

		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		Path javaPath = Paths.get(path);

		javaPath = javaPath.normalize();

		path = javaPath.toString();

		List<String> entries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(_file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (zipEntry.isDirectory()) {
					continue;
				}

				String fileName = zipEntry.getName();

				int pos = fileName.lastIndexOf(CharPool.SLASH);

				String folderName = StringPool.BLANK;

				if (pos > 0) {
					folderName = fileName.substring(0, pos);
				}

				if (folderName.equals(path)) {
					entries.add(fileName);
				}
			}
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}

		entries.sort(null);

		return entries;
	}

	private static final Log _log = LogFactoryUtil.getLog(ZipReaderImpl.class);

	private final File _file;
	private File _tempFile;

}