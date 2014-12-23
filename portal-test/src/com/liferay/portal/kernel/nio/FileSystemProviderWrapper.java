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
	public void copy(
			Path sourcePath, Path targetPath, CopyOption... copyOptions)
		throws IOException {

		_fileSystemProvider.copy(
			PathWrapper.unwrapPath(sourcePath),
			PathWrapper.unwrapPath(targetPath), copyOptions);
	}

	@Override
	public void createDirectory(
			Path dirPath, FileAttribute<?>... fileAttributes)
		throws IOException {

		_fileSystemProvider.createDirectory(
			PathWrapper.unwrapPath(dirPath), fileAttributes);
	}

	@Override
	public void createLink(Path linkPath, Path existingPath)
		throws IOException {

		_fileSystemProvider.createLink(
			PathWrapper.unwrapPath(linkPath),
			PathWrapper.unwrapPath(existingPath));
	}

	@Override
	public void createSymbolicLink(
			Path linkPath, Path targetPath, FileAttribute<?>... fileAttributes)
		throws IOException {

		_fileSystemProvider.createSymbolicLink(
			PathWrapper.unwrapPath(linkPath),
			PathWrapper.unwrapPath(targetPath), fileAttributes);
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
		Path path, Class<V> clazz, LinkOption... linkOtions) {

		return _fileSystemProvider.getFileAttributeView(
			PathWrapper.unwrapPath(path), clazz, linkOtions);
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
	public boolean isSameFile(Path path1, Path path2) throws IOException {
		return _fileSystemProvider.isSameFile(
			PathWrapper.unwrapPath(path1), PathWrapper.unwrapPath(path2));
	}

	@Override
	public void move(
			Path sourcePath, Path targetPath, CopyOption... copyOptions)
		throws IOException {

		_fileSystemProvider.move(
			PathWrapper.unwrapPath(sourcePath),
			PathWrapper.unwrapPath(targetPath), copyOptions);
	}

	@Override
	public AsynchronousFileChannel newAsynchronousFileChannel(
			Path path, Set<? extends OpenOption> openOptions,
			ExecutorService executorService, FileAttribute<?>... fileAttribute)
		throws IOException {

		return _fileSystemProvider.newAsynchronousFileChannel(
			PathWrapper.unwrapPath(path), openOptions, executorService,
			fileAttribute);
	}

	@Override
	public SeekableByteChannel newByteChannel(
			Path path, Set<? extends OpenOption> openOptions,
			FileAttribute<?>... fileAttributes)
		throws IOException {

		return _fileSystemProvider.newByteChannel(
			PathWrapper.unwrapPath(path), openOptions, fileAttributes);
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(
			Path dirPath, DirectoryStream.Filter<? super Path> filter)
		throws IOException {

		return _fileSystemProvider.newDirectoryStream(
			PathWrapper.unwrapPath(dirPath), filter);
	}

	@Override
	public FileChannel newFileChannel(
			Path path, Set<? extends OpenOption> openOptions,
			FileAttribute<?>... fileAttributes)
		throws IOException {

		return _fileSystemProvider.newFileChannel(
			PathWrapper.unwrapPath(path), openOptions, fileAttributes);
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
	public InputStream newInputStream(Path path, OpenOption... openOptions)
		throws IOException {

		return _fileSystemProvider.newInputStream(
			PathWrapper.unwrapPath(path), openOptions);
	}

	@Override
	public OutputStream newOutputStream(Path path, OpenOption... openOptions)
		throws IOException {

		return _fileSystemProvider.newOutputStream(
			PathWrapper.unwrapPath(path), openOptions);
	}

	@Override
	public <A extends BasicFileAttributes> A readAttributes(
			Path path, Class<A> clazz, LinkOption... linkOptions)
		throws IOException {

		return _fileSystemProvider.readAttributes(
			PathWrapper.unwrapPath(path), clazz, linkOptions);
	}

	@Override
	public Map<String, Object> readAttributes(
			Path path, String attributes, LinkOption... linkOptions)
		throws IOException {

		return _fileSystemProvider.readAttributes(
			PathWrapper.unwrapPath(path), attributes, linkOptions);
	}

	@Override
	public Path readSymbolicLink(Path link) throws IOException {
		return _fileSystemProvider.readSymbolicLink(
			PathWrapper.unwrapPath(link));
	}

	@Override
	public void setAttribute(
			Path path, String attribute, Object value,
			LinkOption... linkOptions)
		throws IOException {

		_fileSystemProvider.setAttribute(
			PathWrapper.unwrapPath(path), attribute, value, linkOptions);
	}

	private final FileSystemProvider _fileSystemProvider;

}