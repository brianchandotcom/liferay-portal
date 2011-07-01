/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.ldiff;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Connor McKay
 */
public class ByteChannelReader {

	public ByteChannelReader(ReadableByteChannel channel) throws IOException {
		this(channel, 1024);
	}

	public ByteChannelReader(ReadableByteChannel channel, int bufferLength)
		throws IOException {

		_buffer = ByteBuffer.allocate(bufferLength);
		_channel = channel;

		if (_channel.read(_buffer) == -1) {
			_eof = true;
		}
		else {
			_eof = false;
		}

		_buffer.flip();
	}

	public void ensureData(int length) throws IOException {
		if (_buffer.remaining() < length) {
			read();

			if (_eof || _buffer.remaining() < length) {
				throw new IOException("Unexpected EOF");
			}
		}
	}

	public byte get() {
		return _buffer.get();
	}

	public byte get(int offset) {
		return _buffer.get(_buffer.position() + offset);
	}

	public ByteBuffer getBuffer() {
		return _buffer;
	}

	public boolean hasRemaining() {
		return _buffer.hasRemaining();
	}

	public void maybeRead(int length) throws IOException {
		if (!_eof && _buffer.remaining() < length) {
			read();
		}
	}

	public void read() throws IOException {
		if (_eof) {
			return;
		}

		_buffer.compact();

		if (_channel.read(_buffer) == -1) {
			_eof = true;
		}
		else {
			_eof = false;
		}

		_buffer.flip();
	}

	public int remaining() {
		return _buffer.remaining();
	}

	public void resizeBuffer(int minBufferLength) {
		if (_buffer.capacity() >= minBufferLength) {
			return;
		}

		ByteBuffer newBuffer = ByteBuffer.allocate(minBufferLength);

		newBuffer.put(_buffer);
		newBuffer.flip();

		_buffer = newBuffer;
	}

	public int skip(int length) {
		length = Math.min(_buffer.remaining(), length);

		_buffer.position(_buffer.position() + length);

		return length;
	}

	private ByteBuffer _buffer;
	private ReadableByteChannel _channel;
	private boolean _eof = false;

}