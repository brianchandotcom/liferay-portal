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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Connor McKay
 */
public class RollingChecksum {

	public RollingChecksum(ReadableByteChannel in, int blockLength)
	 	throws IOException {

		_blockLength = blockLength;
		_reader = new ByteChannelReader(in, _blockLength * LDiff.BUFFER_FACTOR);

		generateWeakChecksum();
	}

	public boolean hasNext() throws IOException {
		_reader.maybeRead(1);

		if (_reader.remaining() >= 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public void nextByte() throws IOException {
		int blockLength = currentBlockLength();
		int x = _reader.get();

		_filePosition++;

		_a -= x;
		_b -= blockLength * x;

		_reader.maybeRead(_blockLength);

		if (_reader.remaining() >= _blockLength) {
			x = _reader.get(_blockLength - 1);

			_a += x;
			_b += _a;
		}
	}

	public void nextBlock() throws IOException {
		_filePosition += _reader.skip(_blockLength);

		generateWeakChecksum();
	}

	protected void generateWeakChecksum() throws IOException {
		_reader.maybeRead(_blockLength);

		_a = 0;
		_b = 0;

		for (int i = 0; i < currentBlockLength(); i++) {
			_a += _reader.get(i);
			_b += _a;
		}
	}

	public int currentBlockLength() {
		return Math.min(_reader.remaining(), _blockLength);
	}

	/**
	 * Returns the weak checksum of the current block.
	 */
	public int weakChecksum() {
		return (_a & 0xffff) | (_b << 16);
	}

	/**
	 * Returns the strong checksum of the current block.
	 */
	public byte[] strongChecksum() {
		ByteBuffer buffer = _reader.getBuffer();

		int oldPosition = buffer.position();
		int oldLimit = buffer.limit();

		buffer.limit(buffer.position() + currentBlockLength());

		_MD5.update(buffer);

		buffer.limit(oldLimit);
		buffer.position(oldPosition);

		return _MD5.digest();
	}

	/**
	 * Returns the first byte of data in the current block.
	 */
	public byte getFirstByte() {
		return _reader.get(0);
	}

	/**
	 * Returns the position of the start of the current block in the file.
	 */
	public int getPosition() {
		return _filePosition;
	}

	private static final MessageDigest _MD5;

	static {
		try {
			_MD5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not found", e);
		}
	}

	protected int _a;
	protected int _b;

	protected int _blockLength;

	protected int _filePosition;

	protected ByteChannelReader _reader;

}