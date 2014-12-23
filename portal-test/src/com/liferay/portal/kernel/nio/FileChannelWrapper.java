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

import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Shuyang Zhou
 */
public class FileChannelWrapper extends FileChannel {

	public FileChannelWrapper(FileChannel fileChannel) {
		_fileChannel = fileChannel;
	}

	@Override
	public void force(boolean metaData) throws IOException {
		_fileChannel.force(metaData);
	}

	@Override
	public FileLock lock(long position, long size, boolean shared)
		throws IOException {

		return _fileChannel.lock(position, size, shared);
	}

	@Override
	public MappedByteBuffer map(MapMode mapMode, long position, long size)
		throws IOException {

		return _fileChannel.map(mapMode, position, size);
	}

	@Override
	public long position() throws IOException {
		return _fileChannel.position();
	}

	@Override
	public FileChannel position(long newPosition) throws IOException {
		_fileChannel.position(newPosition);

		return this;
	}

	@Override
	public int read(ByteBuffer byteBuffer) throws IOException {
		return _fileChannel.read(byteBuffer);
	}

	@Override
	public int read(ByteBuffer byteBuffer, long position) throws IOException {
		return _fileChannel.read(byteBuffer, position);
	}

	@Override
	public long read(ByteBuffer[] byteBuffers, int offset, int length)
		throws IOException {

		return _fileChannel.read(byteBuffers, offset, length);
	}

	@Override
	public long size() throws IOException {
		return _fileChannel.size();
	}

	@Override
	public long transferFrom(
			ReadableByteChannel readableByteChannel, long position, long count)
		throws IOException {

		return _fileChannel.transferFrom(readableByteChannel, position, count);
	}

	@Override
	public long transferTo(
			long position, long count, WritableByteChannel target)
		throws IOException {

		return _fileChannel.transferTo(position, count, target);
	}

	@Override
	public FileChannel truncate(long size) throws IOException {
		_fileChannel.truncate(size);

		return this;
	}

	@Override
	public FileLock tryLock(long position, long size, boolean shared)
		throws IOException {

		return _fileChannel.tryLock(position, size, shared);
	}

	@Override
	public int write(ByteBuffer byteBuffer) throws IOException {
		return _fileChannel.write(byteBuffer);
	}

	@Override
	public int write(ByteBuffer byteBuffer, long position) throws IOException {
		return _fileChannel.write(byteBuffer, position);
	}

	@Override
	public long write(ByteBuffer[] byteBuffers, int offset, int length)
		throws IOException {

		return _fileChannel.write(byteBuffers, offset, length);
	}

	@Override
	protected void implCloseChannel() {
		ReflectionTestUtil.invoke(
			_fileChannel, "implCloseChannel", new Class<?>[0]);
	}

	private final FileChannel _fileChannel;

}