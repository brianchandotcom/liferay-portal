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

package com.liferay.portal.zip;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Raymond Augé
 */
public class ZipWriterImpl implements ZipWriter {

	public ZipWriterImpl() {
		this(
			new File(
				StringBundler.concat(
					SystemProperties.get(SystemProperties.TMP_DIR),
					StringPool.SLASH, PortalUUIDUtil.generate(), ".zip")));

		FinalizeManager.register(
			_file, new DeleteFileFinalizeAction(_file.getAbsolutePath()),
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);
	}

	public ZipWriterImpl(File file) {
		_file = file.getAbsoluteFile();

		File parentFile = _file.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		try {
			_zipOutputStream = new ZipOutputStream(new FileOutputStream(_file));
		}
		catch (FileNotFoundException fileNotFoundException) {
			throw new UncheckedIOException(fileNotFoundException);
		}
	}

	@Override
	public void addEntry(String name, byte[] bytes) throws IOException {
		if (bytes == null) {
			return;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		ZipEntry zipEntry = new ZipEntry(name);

		_zipOutputStream.putNextEntry(zipEntry);

		_zipOutputStream.write(bytes);

		_zipOutputStream.closeEntry();
	}

	@Override
	public void addEntry(String name, InputStream inputStream)
		throws IOException {

		if (inputStream == null) {
			return;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		ZipEntry zipEntry = new ZipEntry(name);

		_zipOutputStream.putNextEntry(zipEntry);

		StreamUtil.transfer(inputStream, _zipOutputStream, false);

		_zipOutputStream.closeEntry();
	}

	@Override
	public void addEntry(String name, String s) throws IOException {
		if (s == null) {
			return;
		}

		addEntry(name, s.getBytes(StringPool.UTF8));
	}

	@Override
	public void addEntry(String name, StringBuilder sb) throws IOException {
		if (sb == null) {
			return;
		}

		addEntry(name, sb.toString());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFile()}
	 */
	@Deprecated
	@Override
	public byte[] finish() throws IOException {
		return FileUtil.getBytes(getFile());
	}

	@Override
	public File getFile() {
		try {
			_zipOutputStream.close();
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}

		return _file;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFile()}
	 */
	@Deprecated
	@Override
	public String getPath() {
		return _file.getPath();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFile()}
	 */
	@Deprecated
	@Override
	public void umount() {
		try {
			_zipOutputStream.close();
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to unmount file entry", ioException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ZipWriterImpl.class);

	private final File _file;
	private final ZipOutputStream _zipOutputStream;

}