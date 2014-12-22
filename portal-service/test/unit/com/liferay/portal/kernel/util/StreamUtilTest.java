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

import java.io.IOException;
import java.io.RandomAccessFile;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Random;

import org.apache.commons.io.FileUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
	}

	@After
	public void tearDown() throws IOException {
		Files.delete(_fromFilePath);
		Files.delete(_toFilePath);
	}

	@Test
	public void testTransferFileChannel1MB() throws Exception {
		Files.write(_fromFilePath, _data);

		testTransferFileChannel();
	}

	@Test
	@Ignore
	public void testTransferFileChannel2GB() throws Exception {
		Files.write(_fromFilePath, _data);

		RandomAccessFile file = new RandomAccessFile(
			_fromFilePath.toFile(), "rw");

		file.setLength(Integer.MAX_VALUE);

		file.close();

		testTransferFileChannel();
	}

	protected void testTransferFileChannel() throws Exception {
		try (FileChannel fromFileChannel = FileChannel.open(
				_fromFilePath, StandardOpenOption.READ);
			FileChannel toFileChannel = FileChannel.open(
				_toFilePath, StandardOpenOption.CREATE,
				StandardOpenOption.WRITE)) {

			StreamUtil.transferFileChannel(
				fromFileChannel, toFileChannel, 0);
		}

		boolean contentEquals = FileUtils.contentEquals(
			_fromFilePath.toFile(), _toFilePath.toFile());

		Assert.assertTrue(contentEquals);
	}

	private final byte[] _data = new byte[1024 * 1024];
	private Path _fromFilePath;
	private Path _toFilePath;

}