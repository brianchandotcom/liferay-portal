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

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.Iterator;

/**
 * @author Shuyang Zhou
 */
public class PathWrapper implements Path {

	public PathWrapper(Path path, FileSystem fileSystem) {
		_path = path;
		_fileSystem = fileSystem;
	}

	@Override
	public int compareTo(Path path) {
		return _path.compareTo(unwrapPath(path));
	}

	@Override
	public boolean endsWith(Path path) {
		return _path.endsWith(unwrapPath(path));
	}

	@Override
	public boolean endsWith(String path) {
		return _path.endsWith(path);
	}

	@Override
	public boolean equals(Object path) {
		if (path instanceof PathWrapper) {
			PathWrapper pathWrapper = (PathWrapper)path;

			path = pathWrapper._path;
		}

		return _path.equals(path);
	}

	@Override
	public Path getFileName() {
		return wrapPath(_path.getFileName(), _fileSystem);
	}

	@Override
	public FileSystem getFileSystem() {
		return _fileSystem;
	}

	@Override
	public Path getName(int index) {
		return wrapPath(_path.getName(index), _fileSystem);
	}

	@Override
	public int getNameCount() {
		return _path.getNameCount();
	}

	@Override
	public Path getParent() {
		return wrapPath(_path.getParent(), _fileSystem);
	}

	@Override
	public Path getRoot() {
		return wrapPath(_path.getRoot(), _fileSystem);
	}

	@Override
	public int hashCode() {
		return _path.hashCode();
	}

	@Override
	public boolean isAbsolute() {
		return _path.isAbsolute();
	}

	@Override
	public Iterator<Path> iterator() {
		final Iterator<Path> iterator = _path.iterator();

		return new Iterator<Path>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Path next() {
				return wrapPath(iterator.next(), _fileSystem);
			}

			@Override
			public void remove() {
				iterator.remove();
			}

		};
	}

	@Override
	public Path normalize() {
		return wrapPath(_path.normalize(), _fileSystem);
	}

	@Override
	public WatchKey register(
			WatchService watcherService, WatchEvent.Kind<?>... kinds)
		throws IOException {

		return _path.register(watcherService, kinds);
	}

	@Override
	public WatchKey register(
			WatchService watcherService, WatchEvent.Kind<?>[] kinds,
			WatchEvent.Modifier... modifiers)
		throws IOException {

		return _path.register(watcherService, kinds, modifiers);
	}

	@Override
	public Path relativize(Path path) {
		return wrapPath(_path.relativize(unwrapPath(path)), _fileSystem);
	}

	@Override
	public Path resolve(Path path) {
		return wrapPath(_path.resolve(unwrapPath(path)), _fileSystem);
	}

	@Override
	public Path resolve(String path) {
		return wrapPath(_path.resolve(path), _fileSystem);
	}

	@Override
	public Path resolveSibling(Path path) {
		return wrapPath(_path.resolveSibling(unwrapPath(path)), _fileSystem);
	}

	@Override
	public Path resolveSibling(String path) {
		return wrapPath(_path.resolveSibling(path), _fileSystem);
	}

	@Override
	public boolean startsWith(Path path) {
		return _path.startsWith(unwrapPath(path));
	}

	@Override
	public boolean startsWith(String path) {
		return _path.startsWith(path);
	}

	@Override
	public Path subpath(int beginIndex, int endIndex) {
		return wrapPath(_path.subpath(beginIndex, endIndex), _fileSystem);
	}

	@Override
	public Path toAbsolutePath() {
		return wrapPath(_path.toAbsolutePath(), _fileSystem);
	}

	@Override
	public File toFile() {
		return _path.toFile();
	}

	@Override
	public Path toRealPath(LinkOption... linkOptions) throws IOException {
		return wrapPath(_path.toRealPath(linkOptions), _fileSystem);
	}

	@Override
	public String toString() {
		return _path.toString();
	}

	@Override
	public URI toUri() {
		return _path.toUri();
	}

	protected static Path unwrapPath(Path path) {
		if (path instanceof PathWrapper) {
			PathWrapper pathWrapper = (PathWrapper)path;

			path = pathWrapper._path;
		}

		return path;
	}

	protected static Path wrapPath(Path path, FileSystem fileSystem) {
		if (path == null) {
			return null;
		}

		return new PathWrapper(path, fileSystem);
	}

	private final FileSystem _fileSystem;
	private final Path _path;

}