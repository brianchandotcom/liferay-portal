/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class TestReaderWriter implements ZipReader, ZipWriter {

	@Override
	public void addEntry(String name, byte[] bytes) {
		_binaryEntries.add(name);
	}

	@Override
	public void addEntry(String name, InputStream inputStream) {
		_binaryEntries.add(name);
	}

	@Override
	public void addEntry(String name, String s) {
		_entries.put(name, s);
	}

	@Override
	public void addEntry(String name, StringBuilder sb) {
		_entries.put(name, sb.toString());
	}

	@Override
	public void close() {
	}

	@Override
	public byte[] finish() {
		return new byte[0];
	}

	public List<String> getBinaryEntries() {
		return _binaryEntries;
	}

	@Override
	public List<String> getEntries() {
		return new ArrayList<>(_entries.keySet());
	}

	@Override
	public byte[] getEntryAsByteArray(String name) {
		return null;
	}

	@Override
	public InputStream getEntryAsInputStream(String name) {
		return new InputStream() {

			@Override
			public int read() {
				return -1;
			}

		};
	}

	@Override
	public String getEntryAsString(String name) {
		return _entries.get(name);
	}

	@Override
	public File getFile() {
		return null;
	}

	@Override
	public List<String> getFolderEntries(String path) {
		return null;
	}

	@Override
	public String getPath() {
		return StringPool.BLANK;
	}

	@Override
	public void umount() {
	}

	private final List<String> _binaryEntries = new ArrayList<>();
	private final Map<String, String> _entries = new HashMap<>();

}