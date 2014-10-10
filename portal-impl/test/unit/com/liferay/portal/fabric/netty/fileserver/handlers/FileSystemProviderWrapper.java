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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;

import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author Shuyang Zhou
 */
public class FileSystemProviderWrapper extends FileSystemProvider {

	public FileSystemProviderWrapper(FileSystemProvider fileSystemProvider) {
		_fileSystemProvider = fileSystemProvider;
	}

	@Override
	public void checkAccess(Path path, AccessMode... modes) throws IOException {
		_fileSystemProvider.checkAccess(PathWrapper.unwrapPath(path), modes);
	}

	@Override
	public void copy(Path source, Path target, CopyOption... options)
		throws IOException {

		_fileSystemProvider.copy(
			PathWrapper.unwrapPath(source), PathWrapper.unwrapPath(target),
			options);
	}

	@Override
	public void createDirectory(Path dir, FileAttribute<?>... attrs)
		throws IOException {

		_fileSystemProvider.createDirectory(PathWrapper.unwrapPath(dir), attrs);
	}

	@Override
	public void createLink(Path link, Path existing) throws IOException {
		_fileSystemProvider.createLink(
			PathWrapper.unwrapPath(link), PathWrapper.unwrapPath(existing));
	}

	@Override
	public void createSymbolicLink(
			Path link, Path target, FileAttribute<?>... attrs)
		throws IOException {

		_fileSystemProvider.createSymbolicLink(
			PathWrapper.unwrapPath(link), PathWrapper.unwrapPath(target),
			attrs);
	}

	@Override
	public void delete(Path path) throws IOException {
		_fileSystemProvider.delete(PathWrapper.unwrapPath(path));
	}

	@Override
	public boolean deleteIfExists(Path path) throws IOException {
		return _fileSystemProvider.deleteIfExists(PathWrapper.unwrapPath(path));
	}

	@Override
	public <V extends FileAttributeView> V getFileAttributeView(
		Path path, Class<V> type, LinkOption... options) {

		return _fileSystemProvider.getFileAttributeView(
			PathWrapper.unwrapPath(path), type, options);
	}

	@Override
	public FileStore getFileStore(Path path) throws IOException {
		return _fileSystemProvider.getFileStore(PathWrapper.unwrapPath(path));
	}

	@Override
	public FileSystem getFileSystem(URI uri) {
		return new FileSystemWrapper(
			_fileSystemProvider.getFileSystem(uri), this);
	}

	@Override
	public Path getPath(URI uri) {
		Path path = _fileSystemProvider.getPath(uri);

		return new PathWrapper(
			path, new FileSystemWrapper(path.getFileSystem(), this));
	}

	@Override
	public String getScheme() {
		return _fileSystemProvider.getScheme();
	}

	@Override
	public boolean isHidden(Path path) throws IOException {
		return _fileSystemProvider.isHidden(PathWrapper.unwrapPath(path));
	}

	@Override
	public boolean isSameFile(Path path, Path path2) throws IOException {
		return _fileSystemProvider.isSameFile(
			PathWrapper.unwrapPath(path), PathWrapper.unwrapPath(path2));
	}

	@Override
	public void move(Path source, Path target, CopyOption... options)
		throws IOException {

		_fileSystemProvider.move(
			PathWrapper.unwrapPath(source), PathWrapper.unwrapPath(target),
			options);
	}

	@Override
	public AsynchronousFileChannel newAsynchronousFileChannel(
			Path path, Set<? extends OpenOption> options,
			ExecutorService executor, FileAttribute<?>... attrs)
		throws IOException {

		return _fileSystemProvider.newAsynchronousFileChannel(
			PathWrapper.unwrapPath(path), options, executor, attrs);
	}

	@Override
	public SeekableByteChannel newByteChannel(
			Path path, Set<? extends OpenOption> options,
			FileAttribute<?>... attrs)
		throws IOException {

		return _fileSystemProvider.newByteChannel(
			PathWrapper.unwrapPath(path), options, attrs);
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(
			Path dir, DirectoryStream.Filter<? super Path> filter)
		throws IOException {

		return _fileSystemProvider.newDirectoryStream(
			PathWrapper.unwrapPath(dir), filter);
	}

	@Override
	public FileChannel newFileChannel(
			Path path, Set<? extends OpenOption> options,
			FileAttribute<?>... attrs)
		throws IOException {

		return _fileSystemProvider.newFileChannel(
			PathWrapper.unwrapPath(path), options, attrs);
	}

	@Override
	public FileSystem newFileSystem(Path path, Map<String, ?> env)
		throws IOException {

		return new FileSystemWrapper(
			_fileSystemProvider.newFileSystem(
				PathWrapper.unwrapPath(path), env),
			this);
	}

	@Override
	public FileSystem newFileSystem(URI uri, Map<String, ?> env)
		throws IOException {

		return new FileSystemWrapper(
			_fileSystemProvider.newFileSystem(uri, env), this);
	}

	@Override
	public InputStream newInputStream(Path path, OpenOption... options)
		throws IOException {

		return _fileSystemProvider.newInputStream(
			PathWrapper.unwrapPath(path), options);
	}

	@Override
	public OutputStream newOutputStream(Path path, OpenOption... options)
		throws IOException {

		return _fileSystemProvider.newOutputStream(
			PathWrapper.unwrapPath(path), options);
	}

	@Override
	public <A extends BasicFileAttributes> A readAttributes(
			Path path, Class<A> type, LinkOption... options)
		throws IOException {

		return _fileSystemProvider.readAttributes(
			PathWrapper.unwrapPath(path), type, options);
	}

	@Override
	public Map<String, Object> readAttributes(
			Path path, String attributes, LinkOption... options)
		throws IOException {

		return _fileSystemProvider.readAttributes(
			PathWrapper.unwrapPath(path), attributes, options);
	}

	@Override
	public Path readSymbolicLink(Path link) throws IOException {
		return _fileSystemProvider.readSymbolicLink(
			PathWrapper.unwrapPath(link));
	}

	@Override
	public void setAttribute(
			Path path, String attribute, Object value, LinkOption... options)
		throws IOException {

		_fileSystemProvider.setAttribute(
			PathWrapper.unwrapPath(path), attribute, value, options);
	}

	private final FileSystemProvider _fileSystemProvider;

}