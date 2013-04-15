/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.nio.intraband.welder.WelderTestUtil;
import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOWelder.AutoRemoveFileInputStream;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.io.File;
import java.io.IOException;

import java.security.Permission;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FIFOWelderTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		File tempFile = new File("tempFile");

		tempFile.delete();
	}

	@After
	public void tearDown() {
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
		File tempFile = new File("tempFile");

		Assert.assertTrue(tempFile.createNewFile());

		AutoRemoveFileInputStream autoRemoveFileInputStream =
			new AutoRemoveFileInputStream(tempFile);

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

		System.setSecurityManager(securityManager);

		try {
			autoRemoveFileInputStream.close();
		}
		finally {
			System.setSecurityManager(null);
		}

		Assert.assertFalse(tempFile.exists());
		Assert.assertEquals(1, checkDeleteCount.get());

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
		AtomicLong idCounter = FIFOWelder.idCounter;

		idCounter.set(0);

		FIFOWelder fifoWelder = new FIFOWelder();

		try {
			Assert.assertEquals(1, idCounter.get());
			Assert.assertTrue(fifoWelder.inputFIFOFile.exists());
			Assert.assertTrue(fifoWelder.outputFIFOFile.exists());
		}
		finally {
			File inputFIFOFile = fifoWelder.inputFIFOFile;

			inputFIFOFile.delete();

			File outputFIFOFile = fifoWelder.outputFIFOFile;

			outputFIFOFile.delete();
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
	public void testWeld() throws Exception {
		final FIFOWelder serverFifoWelder = new FIFOWelder();
		final FIFOWelder clientFIFOWelder = WelderTestUtil.transform(
			serverFifoWelder);

		FutureTask<MockRegistrationReference> serverWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)serverFifoWelder.weld(
							new MockIntraBand());
					}
				});

		Thread serverWeldingThread = new Thread(serverWeldingTask);

		serverWeldingThread.start();

		FutureTask<MockRegistrationReference> clientWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)clientFIFOWelder.weld(
							new MockIntraBand());
					}
				});

		Thread clientWeldingThread = new Thread(clientWeldingTask);

		clientWeldingThread.start();

		MockRegistrationReference serverMockRegistrationReference =
			serverWeldingTask.get();

		MockRegistrationReference clientMockRegistrationReference =
			clientWeldingTask.get();

		WelderTestUtil.assertConnectted(
			serverMockRegistrationReference.getScatteringByteChannel(),
			clientMockRegistrationReference.getGatheringByteChannel());
		WelderTestUtil.assertConnectted(
			clientMockRegistrationReference.getScatteringByteChannel(),
			serverMockRegistrationReference.getGatheringByteChannel());

		serverFifoWelder.destroy();
		clientFIFOWelder.destroy();

		try {
			serverFifoWelder.weld(new MockIntraBand());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Unable to weld a welder with state DESTROYED",
				ise.getMessage());
		}
		finally {
			serverFifoWelder.inputFIFOFile.delete();
			serverFifoWelder.outputFIFOFile.delete();
		}

		try {
			clientFIFOWelder.weld(new MockIntraBand());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Unable to weld a welder with state DESTROYED",
				ise.getMessage());
		}
		finally {
			serverFifoWelder.inputFIFOFile.delete();
			serverFifoWelder.outputFIFOFile.delete();
		}
	}

}