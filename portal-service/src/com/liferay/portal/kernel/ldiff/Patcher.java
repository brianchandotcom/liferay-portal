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
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Connor McKay
 */
public class Patcher {

	public static final int NATIVE_TRANSFER_THRESHOLD = 1000000;

	public void patch(
			FileChannel original, WritableByteChannel patched,
			ByteChannelReader delta)
		throws IOException {

		delta.resizeBuffer(5);
		ByteBuffer deltaBuffer = delta.getBuffer();

		// Read header

		delta.ensureData(5);

		if (LDiff.PROTOCOL_VERSION != deltaBuffer.get()) {
			throw new IOException("Unknown protocol version");
		}

		int blockLength = deltaBuffer.getInt();

		// Apply patch

		delta.resizeBuffer(blockLength * LDiff.BUFFER_FACTOR + 5);
		deltaBuffer = delta.getBuffer();

		while (true) {
			delta.ensureData(1);
			byte key = deltaBuffer.get();

			if (key == LDiff.REFERENCE_RANGE_KEY) {
				delta.ensureData(9);
				int firstBlockNumber = deltaBuffer.getInt();
				int lastBlockNumber = deltaBuffer.getInt();

				long position = firstBlockNumber * (long)blockLength;
				long length = (lastBlockNumber - firstBlockNumber + 1) *
					(long)blockLength;

				transfer(original, patched, position, length);
			}
			else if (key == LDiff.REFERENCE_KEY) {
				delta.ensureData(4);

				int blockNumber = deltaBuffer.getInt();

				long position = blockNumber * (long)blockLength;

				transfer(original, patched, position, blockLength);
			}
			else if (key == LDiff.DATA_KEY) {
				delta.ensureData(4);

				int length = deltaBuffer.getInt();

				delta.ensureData(length);

				int oldLimit = deltaBuffer.limit();

				deltaBuffer.limit(deltaBuffer.position() + length);
				patched.write(deltaBuffer);
				deltaBuffer.limit(oldLimit);
			}
			else if (key == LDiff.EOF_KEY) {
				return;
			}
			else {
				throw new IOException("Invalid key");
			}
		}
	}

	protected void transfer(
			FileChannel source, WritableByteChannel destination, long position,
			long length)
		throws IOException {

		if (length > Patcher.NATIVE_TRANSFER_THRESHOLD) {
			source.transferTo(position, length, destination);
		}
		else {
			_transferBuffer.clear();
			_transferBuffer.limit((int)length);

			source.read(_transferBuffer, position);

			_transferBuffer.flip();

			destination.write(_transferBuffer);
		}
	}

	private ByteBuffer _transferBuffer =
		ByteBuffer.allocate(Patcher.NATIVE_TRANSFER_THRESHOLD);

}