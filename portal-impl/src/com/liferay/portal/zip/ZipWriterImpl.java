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

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.net.URI;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

		URI fileURI = _file.toURI();

		_uri = URI.create("jar:file:" + fileURI.getPath());

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				_uri, Collections.singletonMap("create", "true"))) {
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	@Override
	public void addEntry(String name, byte[] bytes) throws IOException {
		if (bytes == null) {
			return;
		}

		if (ExportImportThreadLocal.isExportInProcess()) {
			if (_exportEntries == null) {
				_exportEntries = new LinkedList<>();
			}

			_exportEntries.add(
				new AbstractMap.SimpleImmutableEntry<>(name, bytes));

			return;
		}

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				_uri, Collections.emptyMap())) {

			Path path = fileSystem.getPath(name);

			Path parentPath = path.getParent();

			if (parentPath != null) {
				Files.createDirectories(parentPath);
			}

			Files.write(
				path, bytes, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		}
	}

	@Override
	public void addEntry(String name, InputStream inputStream)
		throws IOException {

		if (inputStream == null) {
			return;
		}

		if (ExportImportThreadLocal.isExportInProcess()) {
			addEntry(name, StreamUtil.toByteArray(inputStream));

			return;
		}

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				_uri, Collections.emptyMap())) {

			Path path = fileSystem.getPath(name);

			Path parentPath = path.getParent();

			if (parentPath != null) {
				Files.createDirectories(parentPath);
			}

			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
		}
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
		if (_exportEntries != null) {
			try (FileSystem fileSystem = FileSystems.newFileSystem(
					_uri, Collections.emptyMap())) {

				Iterator<Map.Entry<String, byte[]>> iterator =
					_exportEntries.iterator();

				while (iterator.hasNext()) {
					Map.Entry<String, byte[]> entry = iterator.next();

					iterator.remove();

					Path path = fileSystem.getPath(entry.getKey());

					Path parentPath = path.getParent();

					if (parentPath != null) {
						Files.createDirectories(parentPath);
					}

					Files.write(
						path, entry.getValue(), StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING,
						StandardOpenOption.WRITE);
				}

				_exportEntries = null;
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}
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
	}

	private List<Map.Entry<String, byte[]>> _exportEntries;
	private final File _file;
	private final URI _uri;

}