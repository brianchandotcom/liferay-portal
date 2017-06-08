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

package com.liferay.portal.fabric.netty.fileserver.handlers;

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
	public int compareTo(Path other) {
		return _path.compareTo(unwrapPath(other));
	}

	@Override
	public boolean endsWith(Path other) {
		return _path.endsWith(unwrapPath(other));
	}

	@Override
	public boolean endsWith(String other) {
		return _path.endsWith(other);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof PathWrapper) {
			PathWrapper pathWrapper = (PathWrapper)other;

			other = pathWrapper._path;
		}

		return _path.equals(other);
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
	public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events)
		throws IOException {

		return _path.register(watcher, events);
	}

	@Override
	public WatchKey register(
			WatchService watcher, WatchEvent.Kind<?>[] events,
			WatchEvent.Modifier... modifiers)
		throws IOException {

		return _path.register(watcher, events, modifiers);
	}

	@Override
	public Path relativize(Path other) {
		return wrapPath(_path.relativize(unwrapPath(other)), _fileSystem);
	}

	@Override
	public Path resolve(Path other) {
		return wrapPath(_path.resolve(unwrapPath(other)), _fileSystem);
	}

	@Override
	public Path resolve(String other) {
		return wrapPath(_path.resolve(other), _fileSystem);
	}

	@Override
	public Path resolveSibling(Path other) {
		return wrapPath(_path.resolveSibling(unwrapPath(other)), _fileSystem);
	}

	@Override
	public Path resolveSibling(String other) {
		return wrapPath(_path.resolveSibling(other), _fileSystem);
	}

	@Override
	public boolean startsWith(Path other) {
		return _path.startsWith(unwrapPath(other));
	}

	@Override
	public boolean startsWith(String other) {
		return _path.startsWith(other);
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
	public Path toRealPath(LinkOption... options) throws IOException {
		return wrapPath(_path.toRealPath(options), _fileSystem);
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