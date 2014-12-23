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

package com.liferay.portal.kernel.nio;

import java.io.IOException;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class FileSystemWrapper extends FileSystem {

	public FileSystemWrapper(
		FileSystem fileSystem, FileSystemProvider fileSystemProvider) {

		_fileSystem = fileSystem;
		_fileSystemProvider = fileSystemProvider;
	}

	@Override
	public void close() throws IOException {
		_fileSystem.close();
	}

	@Override
	public Iterable<FileStore> getFileStores() {
		return _fileSystem.getFileStores();
	}

	@Override
	public Path getPath(String first, String... more) {
		return new PathWrapper(_fileSystem.getPath(first, more), this);
	}

	@Override
	public PathMatcher getPathMatcher(String syntaxAndPattern) {
		return _fileSystem.getPathMatcher(syntaxAndPattern);
	}

	@Override
	public Iterable<Path> getRootDirectories() {
		Iterable<Path> iterable = _fileSystem.getRootDirectories();

		final Iterator<Path> iterator = iterable.iterator();

		return new Iterable<Path>() {

			@Override
			public Iterator<Path> iterator() {
				return new Iterator<Path>() {

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public Path next() {
						return new PathWrapper(
							iterator.next(), FileSystemWrapper.this);
					}

					@Override
					public void remove() {
						iterator.remove();
					}

				};
			}

		};
	}

	@Override
	public String getSeparator() {
		return _fileSystem.getSeparator();
	}

	@Override
	public UserPrincipalLookupService getUserPrincipalLookupService() {
		return _fileSystem.getUserPrincipalLookupService();
	}

	@Override
	public boolean isOpen() {
		return _fileSystem.isOpen();
	}

	@Override
	public boolean isReadOnly() {
		return _fileSystem.isReadOnly();
	}

	@Override
	public WatchService newWatchService() throws IOException {
		return _fileSystem.newWatchService();
	}

	@Override
	public FileSystemProvider provider() {
		return _fileSystemProvider;
	}

	@Override
	public Set<String> supportedFileAttributeViews() {
		return _fileSystem.supportedFileAttributeViews();
	}

	private final FileSystem _fileSystem;
	private final FileSystemProvider _fileSystemProvider;

}