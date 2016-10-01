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

package com.liferay.sync.engine.util;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Shinn Lok
 */
@PrepareForTest(FileUtil.class)
@RunWith(PowerMockRunner.class)
public class FileLockRetryUtilTest {

	@Before
	public void setUp() throws Exception {
		FileLockRetryUtil.init();

		_filePath = Files.createTempFile("test", "test");

		_fileChannel = FileChannel.open(
			_filePath, StandardOpenOption.READ, StandardOpenOption.WRITE);

		_fileLock = FileUtil.getFileLock(_fileChannel);
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.releaseFileLock(_fileLock);

		StreamUtil.cleanUp(_fileChannel);

		Files.deleteIfExists(_filePath);

		FileLockRetryUtil.shutdown();
	}

	@Test
	public void testFileLockRetryDelete() throws Exception {
		lockFile();

		final CountDownLatch countDownLatch = new CountDownLatch(2);

		PathCallable pathCallable = new PathCallable(_filePath) {

			@Override
			public Object call() throws Exception {
				Files.delete(_filePath);

				countDownLatch.countDown();

				return null;
			}

		};

		FileLockRetryUtil.registerPathCallable(pathCallable);

		Assert.assertTrue(FileUtil.exists(_filePath));

		countDownLatch.await(5, TimeUnit.SECONDS);

		Assert.assertTrue(FileUtil.exists(_filePath));

		unlockFile();

		countDownLatch.await(5, TimeUnit.SECONDS);

		Assert.assertTrue(FileUtil.notExists(_filePath));
	}

	protected void lockFile() {
		PowerMockito.stub(
			PowerMockito.method(FileUtil.class, "getFileLock")
		).toReturn(
			null
		);
	}

	protected void unlockFile() {
		PowerMockito.stub(
			PowerMockito.method(FileUtil.class, "getFileLock")
		).toReturn(
			_fileLock
		);
	}

	private FileChannel _fileChannel;
	private FileLock _fileLock;
	private Path _filePath;

}