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
import java.nio.channels.WritableByteChannel;

/**
 * @author Connor McKay
 */
public class ByteChannelWriter {

	public ByteChannelWriter(WritableByteChannel channel) {
		this(channel, 1024);
	}

	public ByteChannelWriter(WritableByteChannel channel, int bufferLength) {
		_buffer = ByteBuffer.allocate(bufferLength);
		_channel = channel;
	}

	public void ensureSpace(int length) throws IOException {
		if (_buffer.remaining() < length) {
			write();
		}
	}

	public void finish() throws IOException {
		_buffer.flip();
		_channel.write(_buffer);
	}

	public ByteBuffer getBuffer() {
		return _buffer;
	}

	public void resizeBuffer(int minBufferLength) {
		if (_buffer.capacity() >= minBufferLength) {
			return;
		}

		ByteBuffer newBuffer = ByteBuffer.allocate(minBufferLength);

		_buffer.flip();

		newBuffer.put(_buffer);

		_buffer = newBuffer;
	}

	protected void write() throws IOException {
		_buffer.flip();
		_channel.write(_buffer);
		_buffer.clear();
	}

	protected ByteBuffer _buffer;
	protected WritableByteChannel _channel;

}