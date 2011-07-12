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
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Connor McKay
 */
public class LDiff {

	public static final int BUFFER_FACTOR = 16;

	public static final byte DATA_KEY = 1;

	public static final byte EOF_KEY = 0;

	public static final byte PROTOCOL_VERSION = 1;

	public static final byte REFERENCE_KEY = 2;

	public static final byte REFERENCE_RANGE_KEY = 3;

	public static void checksums(
		 	FileChannel original, ByteChannelWriter checksums)
		throws IOException {

		checksums(original, checksums, 512);
	}

	public static void checksums(
			FileChannel original, ByteChannelWriter checksums, int blockLength)
		throws IOException {

		RollingChecksum reader = new RollingChecksum(original, blockLength);

		checksums.resizeBuffer(BUFFER_FACTOR * 20);
		ByteBuffer buffer = checksums.getBuffer();

		// Write header

		int numBlocks = (int)Math.ceil(original.size() / (double)blockLength);

		checksums.ensureSpace(9);
		buffer.put(PROTOCOL_VERSION);
		buffer.putInt(blockLength);
		buffer.putInt(numBlocks);

		while (reader.hasNext()) {
			reader.nextBlock();

			checksums.ensureSpace(20);
			buffer.putInt(reader.weakChecksum());
			buffer.put(reader.strongChecksum());
		}
	}

	public static void delta(
			ReadableByteChannel modified, ByteChannelReader checksums,
			ByteChannelWriter delta)
		throws IOException {

		Differ deltaWriter = new Differ();

		deltaWriter.delta(modified, checksums, delta);
	}

	public static void patch(
			FileChannel original, WritableByteChannel patched,
			ByteChannelReader delta)
		throws IOException {

		Patcher patcher = new Patcher();
		patcher.patch(original, patched, delta);
	}

}