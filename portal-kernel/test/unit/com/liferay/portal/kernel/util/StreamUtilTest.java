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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.nio.FileChannelWrapper;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class StreamUtilTest {

	@Before
	public void setUp() throws IOException {
		_fromFilePath = Files.createTempFile(null, null);
		_toFilePath = Files.createTempFile(null, null);

		Random random = new Random();

		random.nextBytes(_data);

		Files.write(_fromFilePath, _data);
	}

	@After
	public void tearDown() throws IOException {
		Files.delete(_fromFilePath);
		Files.delete(_toFilePath);
	}

	@Test
	public void testTransferFileChannel() throws Exception {
		try (FileChannel fromFileChannel = new FileChannelWrapper(
				FileChannel.open(_fromFilePath, StandardOpenOption.READ)) {

				@Override
				public long transferTo(
						long position, long count, WritableByteChannel target)
					throws IOException {

					return super.transferTo(position, _data.length / 4, target);
				}

			};

			FileChannel toFileChannel = FileChannel.open(
				_toFilePath, StandardOpenOption.CREATE,
				StandardOpenOption.WRITE)) {

			ByteBuffer byteBuffer = ByteBuffer.allocate(_data.length / 2);

			while (byteBuffer.hasRemaining()) {
				fromFileChannel.read(byteBuffer);
			}

			byteBuffer.flip();

			toFileChannel.write(byteBuffer);

			StreamUtil.transferFileChannel(
				fromFileChannel, toFileChannel,
				_data.length - byteBuffer.capacity());
		}

		Assert.assertArrayEquals(_data, Files.readAllBytes(_toFilePath));
	}

	private final byte[] _data = new byte[1024 * 1024];
	private Path _fromFilePath;
	private Path _toFilePath;

}