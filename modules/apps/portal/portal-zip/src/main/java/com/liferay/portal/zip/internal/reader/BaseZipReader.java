/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip.internal.reader;

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

import java.util.Collections;
import java.util.List;

/**
 * @author Carlos Correa
 */
public abstract class BaseZipReader implements ZipReader {

	public BaseZipReader(File file) {
		this.file = file;
	}

	public BaseZipReader(InputStream inputStream) throws IOException {
		this(FileUtil.createTempFile(inputStream));

		_tempFile = file;
	}

	@Override
	public void close() {
		if (_tempFile != null) {
			_deleteFile(_tempFile);
		}
	}

	@Override
	public final List<String> getEntries() {
		try {
			List<String> entries = doGetEntries();

			Collections.sort(entries);

			return entries;
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	@Override
	public final byte[] getEntryAsByteArray(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		try {
			return doGetEntryAsByteArray(name);
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return null;
	}

	@Override
	public final InputStream getEntryAsInputStream(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		try {
			return doGetEntryAsInputStream(name);
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	@Override
	public final String getEntryAsString(String name) {
		byte[] bytes = getEntryAsByteArray(name);

		if (bytes != null) {
			return new String(bytes);
		}

		return null;
	}

	@Override
	public final List<String> getFolderEntries(String path) {
		if (Validator.isNull(path)) {
			return Collections.emptyList();
		}

		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		try {
			List<String> folderEntries = doGetFolderEntries(path);

			Collections.sort(folderEntries);

			return folderEntries;
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	protected abstract List<String> doGetEntries() throws IOException;

	protected abstract byte[] doGetEntryAsByteArray(String name)
		throws IOException;

	protected abstract InputStream doGetEntryAsInputStream(String name)
		throws IOException;

	protected abstract List<String> doGetFolderEntries(String path)
		throws IOException;

	protected final File file;

	private void _deleteFile(File file) {
		if (!file.delete() && _log.isWarnEnabled()) {
			_log.warn(
				"Failed to delete temporary file " + file.getAbsolutePath());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseZipReader.class);

	private File _tempFile;

}