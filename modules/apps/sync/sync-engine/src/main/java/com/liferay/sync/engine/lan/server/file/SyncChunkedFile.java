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

package com.liferay.sync.engine.lan.server.file;

import com.liferay.sync.engine.util.OSDetector;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;

import java.io.IOException;

import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;

/**
 * @author Dennis Ju
 */
public class SyncChunkedFile implements ChunkedInput<ByteBuf> {

	public SyncChunkedFile(
			Path path, long length, int chunkSize, long modifiedTime)
		throws IOException {

		this(path, 0L, length, chunkSize, modifiedTime);
	}

	public SyncChunkedFile(
			Path path, long offset, long length, int chunkSize,
			long modifiedTime)
		throws IOException {

		if (offset != 0L) {
			_fileChannel.position(offset);
		}

		_path = path;
		_offset = offset;
		_chunkSize = chunkSize;
		_modifiedTime = modifiedTime;

		_startOffset = offset;
		_endOffset = offset + length;

		if (OSDetector.isWindows()) {
			_closeAggressively = true;
		}
		else {
			_closeAggressively = false;
		}
	}

	@Override
	public void close() throws Exception {
		if (_fileChannel != null) {
			_fileChannel.close();
		}

		_closed = true;
	}

	@Override
	public boolean isEndOfInput() throws Exception {
		if ((_offset >= _endOffset) || _closed) {
			return true;
		}

		return false;
	}

	@Override
	public long length() {
		return _endOffset - _startOffset;
	}

	@Override
	public long progress() {
		return _offset - _startOffset;
	}

	@Override
	public ByteBuf readChunk(ByteBufAllocator byteBufAllocator)
		throws Exception {

		long offset = _offset;

		if (offset >= _endOffset) {
			return null;
		}

		int chunkSize = (int)Math.min((long)_chunkSize, _endOffset - offset);

		ByteBuf byteBuf = byteBufAllocator.buffer(chunkSize);

		boolean release = true;

		try {
			FileTime currentFileTime = Files.getLastModifiedTime(
				_path, LinkOption.NOFOLLOW_LINKS);

			long currentTime = currentFileTime.toMillis();

			if (currentTime != _modifiedTime) {
				throw new Exception("File modified during transfer: " + _path);
			}

			int bytesRead = 0;

			if (_closeAggressively || (_fileChannel == null)) {
				_fileChannel = FileChannel.open(_path, StandardOpenOption.READ);

				_fileChannel.position(_offset);
			}

			while (true) {
				int localBytesRead = byteBuf.writeBytes(
					_fileChannel, chunkSize - bytesRead);

				if (localBytesRead >= 0) {
					bytesRead += localBytesRead;

					if (bytesRead != chunkSize) {
						continue;
					}
				}

				_offset += bytesRead;

				release = false;

				return byteBuf;
			}
		}
		finally {
			if (_closeAggressively && (_fileChannel != null)) {
				_fileChannel.close();
			}

			if (release) {
				byteBuf.release();
			}
		}
	}

	/**
	 * @deprecated As of 3.3.0, As of Netty 4.1.0, replaced by {@link
	 *             #readChunk(ByteBufAllocator)}
	 */
	@Deprecated
	@Override
	public ByteBuf readChunk(ChannelHandlerContext channelHandlerContext)
		throws Exception {

		return readChunk(channelHandlerContext.alloc());
	}

	private final int _chunkSize;
	private final boolean _closeAggressively;
	private boolean _closed;
	private final long _endOffset;
	private FileChannel _fileChannel;
	private final long _modifiedTime;
	private long _offset;
	private final Path _path;
	private final long _startOffset;

}