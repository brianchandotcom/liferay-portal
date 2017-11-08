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

package com.liferay.petra.io;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class StreamUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Test
	public void testCleanUp() throws IOException {
		StreamUtil.cleanUp(new Closeable[] {null});

		IOException ioException1 = new IOException();
		IOException ioException2 = new IOException();

		try {
			StreamUtil.cleanUp(
				() -> {
					throw ioException1;
				},
				() -> {
				},
				() -> {
					throw ioException2;
				});

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertSame(ioException1, ioe);

			Throwable[] throwables = ioe.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);
			Assert.assertSame(ioException2, throwables[0]);
		}
	}

	@Test
	public void testConstructor() {
		new StreamUtil();
	}

	@Test
	public void testTransferByteArray() throws IOException {
		byte[] bytes = new byte[1024];

		Random random = new Random();

		random.nextBytes(bytes);

		// Close

		AtomicBoolean inputStreamClosed = new AtomicBoolean();
		AtomicBoolean outputStreamClosed = new AtomicBoolean();

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes) {

				@Override
				public void close() throws IOException {
					inputStreamClosed.set(true);
				}

			};

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream() {

				@Override
				public void close() throws IOException {
					outputStreamClosed.set(true);
				}

			};

		StreamUtil.transfer(
			unsyncByteArrayInputStream, unsyncByteArrayOutputStream);

		Assert.assertArrayEquals(
			bytes, unsyncByteArrayOutputStream.toByteArray());

		Assert.assertTrue(inputStreamClosed.get());
		Assert.assertTrue(outputStreamClosed.get());

		// Not close

		inputStreamClosed.set(false);
		outputStreamClosed.set(false);

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(bytes) {

			@Override
			public void close() throws IOException {
				inputStreamClosed.set(true);
			}

		};

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream() {

			@Override
			public void close() throws IOException {
				outputStreamClosed.set(true);
			}

		};

		StreamUtil.transfer(
			unsyncByteArrayInputStream, unsyncByteArrayOutputStream, false);

		Assert.assertArrayEquals(
			bytes, unsyncByteArrayOutputStream.toByteArray());

		Assert.assertFalse(inputStreamClosed.get());
		Assert.assertFalse(outputStreamClosed.get());

		// Customized buffer

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(bytes);

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(
			unsyncByteArrayInputStream, unsyncByteArrayOutputStream, 10);

		Assert.assertArrayEquals(
			bytes, unsyncByteArrayOutputStream.toByteArray());

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(bytes);

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(
			unsyncByteArrayInputStream, unsyncByteArrayOutputStream, -1);

		Assert.assertArrayEquals(
			bytes, unsyncByteArrayOutputStream.toByteArray());

		// Customized length

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(bytes);

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(
			unsyncByteArrayInputStream, unsyncByteArrayOutputStream, 512L);

		Assert.assertArrayEquals(
			Arrays.copyOf(bytes, 512),
			unsyncByteArrayOutputStream.toByteArray());

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(bytes);

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(
			unsyncByteArrayInputStream, unsyncByteArrayOutputStream, 2048L);

		Assert.assertArrayEquals(
			bytes, unsyncByteArrayOutputStream.toByteArray());

		// FileInputStream and UnsyncByteArrayOutputStream

		File inputFile = File.createTempFile("input", null);
		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		try {
			Files.write(inputFile.toPath(), bytes);

			StreamUtil.transfer(
				new FileInputStream(inputFile), unsyncByteArrayOutputStream);

			Assert.assertArrayEquals(
				bytes, unsyncByteArrayOutputStream.toByteArray());
		}
		finally {
			inputFile.delete();
		}
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testTransferFileChannel() throws IOException {
		System.setProperty(StreamUtil.class.getName() + ".force.tio", "false");

		try {
			_testTransferFileChannel();
		}
		finally {
			System.clearProperty(StreamUtil.class.getName() + ".force.tio");
		}
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testTransferFileChannelForceTIO() throws IOException {
		System.setProperty(StreamUtil.class.getName() + ".force.tio", "true");

		try {
			_testTransferFileChannel();
		}
		finally {
			System.clearProperty(StreamUtil.class.getName() + ".force.tio");
		}
	}

	@Test
	public void testTransferNPEs() throws IOException {
		try {
			StreamUtil.transfer(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Input stream is null", npe.getMessage());
		}

		try {
			StreamUtil.transfer(
				new UnsyncByteArrayInputStream(new byte[0]), null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Output stream is null", npe.getMessage());
		}
	}

	private void _testTransferFileChannel() throws IOException {
		byte[] bytes = new byte[1024];

		Random random = new Random();

		random.nextBytes(bytes);

		File inputFile = File.createTempFile("input", null);
		File outputFile = File.createTempFile("output", null);

		try {
			Files.write(inputFile.toPath(), bytes);

			StreamUtil.transfer(
				new FileInputStream(inputFile),
				new FileOutputStream(outputFile));

			Assert.assertArrayEquals(
				bytes, Files.readAllBytes(outputFile.toPath()));

			StreamUtil.transfer(
				new FileInputStream(inputFile),
				new FileOutputStream(outputFile), 512L);

			Assert.assertArrayEquals(
				Arrays.copyOf(bytes, 512),
				Files.readAllBytes(outputFile.toPath()));
		}
		finally {
			inputFile.delete();
			outputFile.delete();
		}
	}

}