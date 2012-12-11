/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.welder.fifo;

import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOWelder.AutoRemoveFileInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.security.Permission;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FIFOWelderTest {

	@After
	public void tearDown() {

		// Just in case that someone breaks the test causing left over fifo
		// files which will fail all following tests.

		File tempFolder = new File(System.getProperty("java.io.tmpdir"));

		File[] files = tempFolder.listFiles();

		for (File file : files) {
			if (file.isFile() && file.getName().startsWith("FIFO-")) {
				file.delete();
			}
		}

	}

	@Test
	public void testAutoRemoveFileInputStream() throws Exception {
		final AtomicInteger checkDeleteCount = new AtomicInteger();

		SecurityManager securityManager = new SecurityManager() {

			@Override
			public void checkDelete(String file) {
				if (file.contains("tempFile")) {
					checkDeleteCount.getAndIncrement();
				}
			}

			@Override
			public void checkPermission(Permission permission) {
			}

		};

		// 1) Normal delete

		File tempFile = new File("tempFile");

		Assert.assertTrue(tempFile.createNewFile());

		AutoRemoveFileInputStream autoRemoveFileInputStream =
			new AutoRemoveFileInputStream(tempFile);

		System.setSecurityManager(securityManager);

		try {
			autoRemoveFileInputStream.close();
		}
		finally {
			System.setSecurityManager(null);
		}

		Assert.assertFalse(tempFile.exists());
		Assert.assertEquals(1, checkDeleteCount.get());

		// 2) Close after manual delete

		checkDeleteCount.set(0);
		Assert.assertTrue(tempFile.createNewFile());

		autoRemoveFileInputStream = new AutoRemoveFileInputStream(tempFile);

		Assert.assertTrue(tempFile.delete());

		System.setSecurityManager(securityManager);

		try {
			autoRemoveFileInputStream.close();
		}
		finally {
			System.setSecurityManager(null);
		}

		Assert.assertFalse(tempFile.exists());
		Assert.assertEquals(2, checkDeleteCount.get());
	}

	@Test
	public void testConstructor() throws IOException {

		// 1) Normal creation

		FIFOWelder.ID_COUNTER.set(0);

		FIFOWelder fifoWelder = new FIFOWelder();

		try {
			Assert.assertEquals(1, FIFOWelder.ID_COUNTER.get());
			Assert.assertTrue(fifoWelder.inputFIFOFile.exists());
			Assert.assertTrue(fifoWelder.outputFIFOFile.exists());
		}
		finally {
			fifoWelder.inputFIFOFile.delete();
			fifoWelder.outputFIFOFile.delete();
		}

		String oldTempFolder = System.getProperty("java.io.tmpdir");

		File tempFolder = new File("tempFolder");

		tempFolder.mkdirs();

		tempFolder.setReadOnly();

		System.setProperty("java.io.tmpdir", tempFolder.getAbsolutePath());

		try {
			new FIFOWelder();

			Assert.fail();
		}
		catch (IOException ioe) {
		}
		finally {
			System.setProperty("java.io.tmpdir", oldTempFolder);

			tempFolder.delete();
		}
	}

	@Test
	public void testWelding() throws Exception {

		// 1) Normal welding

		final FIFOWelder fifoWelder = new FIFOWelder();

		FutureTask<MockRegistrationReference> serverWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)fifoWelder.weldServer(
							new MockIntraBand());
					}
				});

		FutureTask<MockRegistrationReference> clientWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)fifoWelder.weldClient(
							new MockIntraBand());
					}
				});

		new Thread(serverWeldingTask).start();
		new Thread(clientWeldingTask).start();

		MockRegistrationReference serverMockRegistrationReference =
			serverWeldingTask.get();
		MockRegistrationReference clientMockRegistrationReference =
			clientWeldingTask.get();

		_ensureConnectted(
			serverMockRegistrationReference.getScatteringByteChannel(),
			clientMockRegistrationReference.getGatheringByteChannel());
		_ensureConnectted(
			clientMockRegistrationReference.getScatteringByteChannel(),
			serverMockRegistrationReference.getGatheringByteChannel());

		serverMockRegistrationReference.closeChannels();
		clientMockRegistrationReference.closeChannels();

		// 2) Welding on used Welder

		try {
			fifoWelder.weldServer(new MockIntraBand());

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertTrue(ioe.getCause() instanceof FileNotFoundException);
		}
		finally {
			fifoWelder.inputFIFOFile.delete();
			fifoWelder.outputFIFOFile.delete();
		}

		try {
			fifoWelder.weldClient(new MockIntraBand());

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertTrue(ioe.getCause() instanceof FileNotFoundException);
		}
		finally {
			fifoWelder.inputFIFOFile.delete();
			fifoWelder.outputFIFOFile.delete();
		}
	}

	private static void _ensureConnectted(
			final ScatteringByteChannel scatteringByteChannel,
			final GatheringByteChannel gatheringByteChannel)
		throws Exception {

		Random random = new Random();

		final byte[] data = new byte[1024 * 1024];

		random.nextBytes(data);

		FutureTask<Void> writeFutureTask = new FutureTask<Void>(
			new Callable<Void>() {

				public Void call() throws Exception {
					ByteBuffer byteBuffer = ByteBuffer.wrap(data);

					while (byteBuffer.hasRemaining()) {
						gatheringByteChannel.write(byteBuffer);
					}

					return null;
				}
			});

		FutureTask<byte[]> readFutureTask = new FutureTask<byte[]>(
			new Callable<byte[]>() {

				public byte[] call() throws Exception {
					ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);

					while (byteBuffer.hasRemaining()) {
						scatteringByteChannel.read(byteBuffer);
					}

					return byteBuffer.array();
				}
			});

		new Thread(writeFutureTask).start();
		new Thread(readFutureTask).start();

		writeFutureTask.get();

		byte[] readData = readFutureTask.get();

		Assert.assertArrayEquals(data, readData);
	}

}