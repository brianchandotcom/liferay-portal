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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Raymond Augé
 */
public class ZipReaderImpl implements ZipReader {

	public ZipReaderImpl(File file) {
		try {
			_zipFile = new ZipFile(file);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}
	}

	public ZipReaderImpl(InputStream inputStream) throws IOException {
		_tempFile = FileUtil.createTempFile(inputStream);

		_zipFile = new ZipFile(_tempFile);
	}

	@Override
	public void close() {
		try {
			_zipFile.close();
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException, ioException);
			}
		}

		if (_tempFile != null) {
			_tempFile.delete();
		}
	}

	@Override
	public List<String> getEntries() {
		_initEntriesByFolder();

		List<String> allEntries = new ArrayList<>();

		for (List<String> folderEntries : _entriesByFolder.values()) {
			allEntries.addAll(folderEntries);
		}

		allEntries.sort(null);

		return allEntries;
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
			_log.error(ioException, ioException);
		}

		return null;
	}

	@Override
	public InputStream getEntryAsInputStream(String name) {
		if ((_zipFile == null) || Validator.isNull(name)) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		ZipEntry zipEntry = _zipFile.getEntry(name);

		try {
			if ((zipEntry != null) && !zipEntry.isDirectory()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Extracting " + name);
				}

				return _zipFile.getInputStream(zipEntry);
			}
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}

		return null;
	}

	@Override
	public String getEntryAsString(String name) {
		if ((_zipFile == null) || Validator.isNull(name)) {
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
		if ((_zipFile == null) || Validator.isNull(path)) {
			return Collections.emptyList();
		}

		_initEntriesByFolder();

		while (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		Path javaPath = Paths.get(path);

		javaPath = javaPath.normalize();

		path = javaPath.toString();

		List<String> folderEntries = _entriesByFolder.get(path);

		if (folderEntries == null) {
			return Collections.emptyList();
		}

		return ListUtil.copy(folderEntries);
	}

	private void _initEntriesByFolder() {
		if (_entriesByFolder != null) {
			return;
		}

		_entriesByFolder = new HashMap<>();

		Enumeration<? extends ZipEntry> zipEntriesEnumeration =
			_zipFile.entries();

		while (zipEntriesEnumeration.hasMoreElements()) {
			ZipEntry zipEntry = zipEntriesEnumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			int pos = fileName.lastIndexOf(CharPool.SLASH);

			String folderName = StringPool.BLANK;

			if (pos > 0) {
				folderName = fileName.substring(0, pos);
			}

			List<String> folderZipEntries = _entriesByFolder.computeIfAbsent(
				folderName, key -> new ArrayList<>());

			folderZipEntries.add(fileName);
		}

		_entriesByFolder.forEach(
			(key, folderZipEntries) -> folderZipEntries.sort(null));
	}

	private static final Log _log = LogFactoryUtil.getLog(ZipReaderImpl.class);

	private Map<String, List<String>> _entriesByFolder;
	private File _tempFile;
	private ZipFile _zipFile;

}