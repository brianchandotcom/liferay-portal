/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class FileContentReference {

	public static FileContentReference fromBytes(
		long groupId, long fileEntryId, String sourceFileName, String extension,
		String mimeType, byte[] bytes) {

		return fromInputStream(
			groupId, fileEntryId, sourceFileName, extension, mimeType,
			new ByteArrayInputStream(bytes), bytes.length);
	}

	public static FileContentReference fromBytes(
		long groupId, String sourceFileName, String extension, String mimeType,
		byte[] bytes) {

		return fromInputStream(
			groupId, 0, sourceFileName, extension, mimeType,
			new ByteArrayInputStream(bytes), bytes.length);
	}

	public static FileContentReference fromFile(
		long groupId, long fileEntryId, String sourceFileName, String extension,
		String mimeType, File file) {

		return new FileContentReference(
			groupId, fileEntryId, sourceFileName, extension, mimeType, file,
			null, 0);
	}

	public static FileContentReference fromFile(
		long groupId, String sourceFileName, String extension, String mimeType,
		File file) {

		return fromFile(groupId, 0, sourceFileName, extension, mimeType, file);
	}

	public static FileContentReference fromInputStream(
		long groupId, long fileEntryId, String sourceFileName, String extension,
		String mimeType, InputStream inputStream, long size) {

		return new FileContentReference(
			groupId, fileEntryId, sourceFileName, extension, mimeType, null,
			inputStream, size);
	}

	public static FileContentReference fromInputStream(
		long groupId, String sourceFileName, String extension, String mimeType,
		InputStream inputStream, long size) {

		return fromInputStream(
			groupId, 0, sourceFileName, extension, mimeType, inputStream, size);
	}

	public String getExtension() {
		return _extension;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public long getSize() {
		if (_inputStream != null) {
			return _size;
		}

		if (_file != null) {
			return _file.length();
		}

		return 0;
	}

	public String getSourceFileName() {
		return _sourceFileName;
	}

	private FileContentReference(
		long groupId, long fileEntryId, String sourceFileName, String extension,
		String mimeType, File file, InputStream inputStream, long size) {

		_groupId = groupId;
		_fileEntryId = fileEntryId;
		_sourceFileName = sourceFileName;
		_extension = extension;
		_mimeType = mimeType;
		_file = file;
		_inputStream = inputStream;
		_size = size;
	}

	private final String _extension;
	private final File _file;
	private final long _fileEntryId;
	private final long _groupId;
	private final InputStream _inputStream;
	private final String _mimeType;
	private final long _size;
	private final String _sourceFileName;

}