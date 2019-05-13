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

package com.liferay.document.library.internal.security.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.util.Arrays;

/**
 * @author Adolfo Pérez
 */
public class SafePNGInputStream extends InputStream {

	public SafePNGInputStream(BufferedInputStream bufferedInputStream) {
		_bufferedInputStream = bufferedInputStream;
	}

	@Override
	public int read() throws IOException {
		if (_firstRun) {
			_detectPNGSignature();

			_firstRun = false;
			_readForwardByteCount = _PNG_SIGNATURE.length;
		}

		if (!_png) {
			return _bufferedInputStream.read();
		}

		if (_readForwardByteCount > 0) {
			_readForwardByteCount--;

			return _bufferedInputStream.read();
		}

		_bufferedInputStream.mark(_CHUNK_LENGTH_SIZE + _CHUNK_ID_SIZE);

		long chunkLength = _readChunkLength();

		if (chunkLength == -1) {
			_bufferedInputStream.reset();

			return _bufferedInputStream.read();
		}

		byte[] bytes = new byte[_CHUNK_ID_SIZE];

		int n = _bufferedInputStream.read(bytes);

		if ((n != _CHUNK_ID_SIZE) || !Arrays.equals(_ZTXT_CHUNK_ID, bytes)) {
			_bufferedInputStream.reset();

			_readForwardByteCount =
				_CHUNK_LENGTH_SIZE + _CHUNK_ID_SIZE + chunkLength + _CRC_SIZE -
					1;

			return _bufferedInputStream.read();
		}

		long bytesToSkip = chunkLength + _CRC_SIZE;

		while (bytesToSkip > 0) {
			long skipped = _bufferedInputStream.skip(bytesToSkip);

			if (skipped <= 0) {
				break;
			}

			bytesToSkip -= skipped;
		}

		return read();
	}

	private void _detectPNGSignature() throws IOException {
		_bufferedInputStream.mark(_PNG_SIGNATURE.length);

		byte[] bytes = new byte[_PNG_SIGNATURE.length];

		int n = _bufferedInputStream.read(bytes);

		if ((n == bytes.length) && Arrays.equals(_PNG_SIGNATURE, bytes)) {
			_png = true;
		}

		_bufferedInputStream.reset();
	}

	private long _readChunkLength() throws IOException {
		byte[] bytes = new byte[_CHUNK_LENGTH_SIZE * 2];

		Arrays.fill(bytes, (byte)0);

		int n = _bufferedInputStream.read(
			bytes, bytes.length / 2, _CHUNK_LENGTH_SIZE);

		if (n != _CHUNK_LENGTH_SIZE) {
			return -1;
		}

		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		return byteBuffer.getLong();
	}

	private static final int _CHUNK_ID_SIZE = 4;

	private static final int _CHUNK_LENGTH_SIZE = 4;

	private static final int _CRC_SIZE = 4;

	private static final byte[] _PNG_SIGNATURE = {
		-119, 80, 78, 71, 13, 10, 26, 10
	};

	private static final byte[] _ZTXT_CHUNK_ID = {122, 84, 88, 116};

	private final BufferedInputStream _bufferedInputStream;
	private boolean _firstRun = true;
	private boolean _png;
	private long _readForwardByteCount;

}