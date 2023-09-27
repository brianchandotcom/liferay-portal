/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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