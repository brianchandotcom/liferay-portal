/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip.internal.reader;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.net.URI;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergio Sanchez
 */
public class NioZipReaderImpl implements ZipReader {

	public NioZipReaderImpl(File file) {
		_init(file);
	}

	public NioZipReaderImpl(InputStream inputStream) throws IOException {
		_tempFile = FileUtil.createTempFile(inputStream);

		_init(_tempFile);
	}

	@Override
	public void close() {
		try {
			if ((_fileSystem != null) && _fileSystem.isOpen()) {
				_fileSystem.close();
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			if (_tempFile != null) {
				_tempFile.delete();
			}
		}
	}

	@Override
	public List<String> getEntries() {
		try {
			List<String> entries = new ArrayList<>();

			Files.walkFileTree(
				_rootPath,
				new SimpleFileVisitor<>() {

					@Override
					public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes) {

						entries.add(_getRelativePath(path));

						return FileVisitResult.CONTINUE;
					}

				});

			Collections.sort(entries);

			return entries;
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	@Override
	public byte[] getEntryAsByteArray(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		Path path = _resolvePath(name);

		try {
			if (Files.isRegularFile(path)) {
				return Files.readAllBytes(path);
			}
		}
		catch (IOException ioException) {
			_log.error("Error reading entry: " + name, ioException);
		}

		return null;
	}

	@Override
	public InputStream getEntryAsInputStream(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		Path path = _resolvePath(name);

		try {
			if (Files.exists(path) && !Files.isDirectory(path)) {
				return Files.newInputStream(path);
			}
		}
		catch (IOException ioException) {
			_log.error(
				"Error getting InputStream for entry: " + name, ioException);
		}

		return null;
	}

	@Override
	public String getEntryAsString(String name) {
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

		Path folderPath = _resolvePath(path);

		if (!Files.isDirectory(folderPath)) {
			return Collections.emptyList();
		}

		try {
			List<String> folderEntries = new ArrayList<>();

			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(folderPath)) {

				for (Path filePath : directoryStream) {
					if (Files.isRegularFile(filePath)) {
						folderEntries.add(_getRelativePath(filePath));
					}
				}
			}

			Collections.sort(folderEntries);

			return folderEntries;
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
	}

	private String _getRelativePath(Path path) {
		String s = path.toString();

		if (s.startsWith("/")) {
			return s.substring(1);
		}

		return s;
	}

	private void _init(File file) {
		try {
			URI uri = URI.create("jar:" + file.toURI());

			try {
				_fileSystem = FileSystems.newFileSystem(
					uri,
					HashMapBuilder.put(
						"create", "false"
					).build());
			}
			catch (FileSystemAlreadyExistsException
						fileSystemAlreadyExistsException) {

				_log.error(fileSystemAlreadyExistsException);

				_fileSystem = FileSystems.getFileSystem(uri);
			}

			_rootPath = _fileSystem.getPath("/");
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(
				"Failed to initialize ZipNioReader for: " + file.getPath(),
				ioException);
		}
	}

	private Path _resolvePath(String name) {
		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		return _rootPath.resolve(name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NioZipReaderImpl.class);

	private FileSystem _fileSystem;
	private Path _rootPath;
	private File _tempFile;

}