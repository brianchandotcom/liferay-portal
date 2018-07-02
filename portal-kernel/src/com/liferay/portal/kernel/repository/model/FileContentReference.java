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

package com.liferay.portal.kernel.repository.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class FileContentReference {

	public static final FileContentReference fromBytes(
		long fileEntryId, String sourceFileName, String extension,
		String mimeType, byte[] bytes) {

		return fromInputStream(
			fileEntryId, sourceFileName, extension, mimeType,
			new ByteArrayInputStream(bytes), bytes.length);
	}

	public static final FileContentReference fromBytes(
		String sourceFileName, String extension, String mimeType,
		byte[] bytes) {

		return fromInputStream(
			0, sourceFileName, extension, mimeType,
			new ByteArrayInputStream(bytes), bytes.length);
	}

	public static final FileContentReference fromFile(
		long fileEntryId, String sourceFileName, String extension,
		String mimeType, File file) {

		return new FileContentReference(
			fileEntryId, sourceFileName, extension, mimeType, file, null, 0);
	}

	public static final FileContentReference fromFile(
		String sourceFileName, String extension, String mimeType, File file) {

		return fromFile(0, sourceFileName, extension, mimeType, file);
	}

	public static final FileContentReference fromInputStream(
		long fileEntryId, String sourceFileName, String extension,
		String mimeType, InputStream inputStream, long size) {

		return new FileContentReference(
			fileEntryId, sourceFileName, extension, mimeType, null, inputStream,
			size);
	}

	public static final FileContentReference fromInputStream(
		String sourceFileName, String extension, String mimeType,
		InputStream inputStream, long size) {

		return fromInputStream(
			0, sourceFileName, extension, mimeType, inputStream, size);
	}

	public String getExtension() {
		return _extension;
	}

	public long getFileEntryId() {
		return _fileEntryId;
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #FileContentReference(long, String, String, String, File,
	 *             InputStream, long)}
	 */
	@Deprecated
	protected FileContentReference(
		String sourceFileName, String extension, String mimeType, File file,
		InputStream inputStream, long size) {

		this(0, sourceFileName, extension, mimeType, file, inputStream, size);
	}

	private FileContentReference(
		long fileEntryId, String sourceFileName, String extension,
		String mimeType, File file, InputStream inputStream, long size) {

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
	private final InputStream _inputStream;
	private final String _mimeType;
	private final long _size;
	private final String _sourceFileName;

}