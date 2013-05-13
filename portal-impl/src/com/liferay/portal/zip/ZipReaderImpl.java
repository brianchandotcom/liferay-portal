/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;

import de.schlichtherle.io.ArchiveBusyWarningException;
import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;
import de.schlichtherle.io.archive.zip.ZipDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class ZipReaderImpl implements ZipReader {

	static {
		File.setDefaultArchiveDetector(
			new DefaultArchiveDetector(
				ArchiveDetector.ALL, "lar|" + ArchiveDetector.ALL.getSuffixes(),
				new ZipDriver()));
	}

	public ZipReaderImpl(InputStream inputStream) throws IOException {
		_zipFile = new File(FileUtil.createTempFile("zip"));

		OutputStream outputStream = new FileOutputStream(_zipFile);

		try {
			File.cat(inputStream, outputStream);
		}
		finally {
			outputStream.close();
			inputStream.close();
		}
	}

	public ZipReaderImpl(java.io.File file) {
		_zipFile = new File(file);
	}

	@Override
	public void close() {
		try {
			File.umount(_zipFile);
		}
		catch (ArchiveBusyWarningException abwe) {
			if (_log.isWarnEnabled()) {
				_log.warn(abwe, abwe);
			}
		}
		catch (ArchiveException ae) {
			_log.error(ae, ae);
		}
	}

	@Override
	public List<String> getEntries() {
		List<String> folderEntries = new ArrayList<String>();

		File[] files = (File[])_zipFile.listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				folderEntries.add(file.getEnclEntryName());
			}
			else {
				processDirectory(file, folderEntries);
			}
		}

		return folderEntries;
	}

	@Override
	public byte[] getEntryAsByteArray(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		byte[] bytes = null;

		try {
			InputStream is = getEntryAsInputStream(name);

			if (is != null) {
				bytes = FileUtil.getBytes(is);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return bytes;
	}

	@Override
	public InputStream getEntryAsInputStream(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		File file = new File(_zipFile, name, DefaultArchiveDetector.NULL);

		if (file.exists() && !file.isDirectory()) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Extracting " + name);
				}

				return new FileInputStream(file);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return null;
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

		List<String> folderEntries = new ArrayList<String>();

		File directory = new File(_zipFile.getPath() + StringPool.SLASH + path);

		if (!directory.exists()) {
			return folderEntries;
		}

		File[] files = (File[])directory.listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				folderEntries.add(file.getEnclEntryName());
			}
		}

		return folderEntries;
	}

	protected void processDirectory(
		File directory, List<String> folderEntries) {

		File[] files = (File[])directory.listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				folderEntries.add(file.getEnclEntryName());
			}
			else {
				processDirectory(file, folderEntries);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ZipReaderImpl.class);

	private File _zipFile;

}