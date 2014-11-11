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

package com.liferay.portal.kernel.security;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.NewEnvMethodRule;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.security.SecureRandom;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class SecureRandomUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		System.setProperty(_KEY_BUFFER_SIZE, "2048");
	}

	@After
	public void tearDown() {
		System.clearProperty(_KEY_BUFFER_SIZE);
	}

	@Test
	public void testConcurrentReload() throws Exception {
		SecureRandom secureRandom = installPredictableRandom();

		FutureTask<Long> futureTask = new FutureTask<Long>(
			new Callable<Long>() {

				@Override
				public Long call() {
					return reload();
				}

			});

		Thread reloadThread = new Thread(futureTask);

		long gapValue = -1;

		synchronized (secureRandom) {
			reloadThread.start();

			while (reloadThread.getState() != Thread.State.BLOCKED);

			long gapSeed = getGapSeed();

			gapValue = reload();

			Assert.assertEquals(getFirstLong() ^ gapSeed, gapValue);
			Assert.assertEquals(gapValue, getGapSeed());
		}

		reloadThread.join();

		Assert.assertEquals(
			(Long)(getFirstLong() ^ gapValue), futureTask.get());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		new SecureRandomUtil();
	}

	@Test
	public void testInitialization() {
		System.setProperty(_KEY_BUFFER_SIZE, "10");

		Assert.assertEquals(
			1024,
			ReflectionTestUtil.getFieldValue(
				SecureRandomUtil.class, "_BUFFER_SIZE"));

		byte[] bytes = ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_bytes");

		Assert.assertEquals(1024, bytes.length);
	}

	@Test
	public void testNextBoolean() {

		// First load

		installPredictableRandom();

		for (int i = 0; i < 2048; i++) {
			byte b = (byte)i;

			if (b < 0) {
				Assert.assertFalse(SecureRandomUtil.nextBoolean());
			}
			else {
				Assert.assertTrue(SecureRandomUtil.nextBoolean());
			}
		}

		// Gap number

		long firstLong = getFirstLong();
		long gapSeed = getGapSeed();

		long result = firstLong ^ gapSeed;

		if ((byte)result < 0) {
			Assert.assertFalse(SecureRandomUtil.nextBoolean());
		}
		else {
			Assert.assertTrue(SecureRandomUtil.nextBoolean());
		}

		// Second load

		for (int i = 0; i < 2048; i++) {
			byte b = (byte)i;

			if (b < 0) {
				Assert.assertFalse(SecureRandomUtil.nextBoolean());
			}
			else {
				Assert.assertTrue(SecureRandomUtil.nextBoolean());
			}
		}
	}

	@Test
	public void testNextByte() {

		// First load

		installPredictableRandom();

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}

		// Gap number

		long firstLong = getFirstLong();
		long gapSeed = getGapSeed();

		long result = firstLong ^ gapSeed;

		Assert.assertEquals((byte)result, SecureRandomUtil.nextByte());

		// Second load

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}
	}

	@Test
	public void testNextDouble() {

		// First load

		installPredictableRandom();

		for (int i = 0; i < 256; i++) {
			byte b = (byte)(i * 8);

			byte[] bytes = new byte[8];

			for (int j = 0; j < 8; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getDouble(bytes, 0),
				SecureRandomUtil.nextDouble(), 0);
		}

		// Gap number

		long firstLong = getFirstLong();
		long gapSeed = getGapSeed();

		long result = firstLong ^ gapSeed;

		Assert.assertEquals(
			Double.longBitsToDouble(result), SecureRandomUtil.nextDouble(), 0);

		// Second load

		for (int i = 0; i < 256; i++) {
			byte b = (byte)(i * 8);

			byte[] bytes = new byte[8];

			for (int j = 0; j < 8; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getDouble(bytes, 0),
				SecureRandomUtil.nextDouble(), 0);
		}
	}

	@Test
	public void testNextFloat() {

		// First load

		installPredictableRandom();

		for (int i = 0; i < 512; i++) {
			byte b = (byte)(i * 4);

			byte[] bytes = new byte[4];

			for (int j = 0; j < 4; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getFloat(bytes, 0), SecureRandomUtil.nextFloat(),
				0);
		}

		// Gap number

		long firstLong = getFirstLong();
		long gapSeed = getGapSeed();

		long result = firstLong ^ gapSeed;

		Assert.assertEquals(
			Float.intBitsToFloat((int)result), SecureRandomUtil.nextFloat(), 0);

		// Second load

		for (int i = 0; i < 512; i++) {
			byte b = (byte)(i * 4);

			byte[] bytes = new byte[4];

			for (int j = 0; j < 4; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getFloat(bytes, 0), SecureRandomUtil.nextFloat(),
				0);
		}
	}

	@Test
	public void testNextInt() {

		// First load

		installPredictableRandom();

		for (int i = 0; i < 512; i++) {
			byte b = (byte)(i * 4);

			byte[] bytes = new byte[4];

			for (int j = 0; j < 4; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getInt(bytes, 0), SecureRandomUtil.nextInt(), 0);
		}

		// Gap number

		long firstLong = getFirstLong();
		long gapSeed = getGapSeed();

		long result = firstLong ^ gapSeed;

		Assert.assertEquals((int)result, SecureRandomUtil.nextInt(), 0);

		// Second load

		for (int i = 0; i < 512; i++) {
			byte b = (byte)(i * 4);

			byte[] bytes = new byte[4];

			for (int j = 0; j < 4; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getInt(bytes, 0), SecureRandomUtil.nextInt(), 0);
		}
	}

	@Test
	public void testNextLong() {

		// First load

		installPredictableRandom();

		for (int i = 0; i < 256; i++) {
			byte b = (byte)(i * 8);

			byte[] bytes = new byte[8];

			for (int j = 0; j < 8; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getLong(bytes, 0), SecureRandomUtil.nextLong(),
				0);
		}

		// Gap number

		long firstLong = getFirstLong();
		long gapSeed = getGapSeed();

		long result = firstLong ^ gapSeed;

		Assert.assertEquals(result, SecureRandomUtil.nextLong(), 0);

		// Second load

		for (int i = 0; i < 256; i++) {
			byte b = (byte)(i * 8);

			byte[] bytes = new byte[8];

			for (int j = 0; j < 8; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getLong(bytes, 0), SecureRandomUtil.nextLong(),
				0);
		}
	}

	@Rule
	public final NewEnvMethodRule newEnvMethodRule = new NewEnvMethodRule();

	protected long getFirstLong() {
		byte[] bytes = ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_bytes");

		return BigEndianCodec.getLong(bytes, 0);
	}

	protected long getGapSeed() {
		return ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_gapSeed");
	}

	protected SecureRandom installPredictableRandom() {
		SecureRandom predictableRandom = new PredictableRandom();

		ReflectionTestUtil.setFieldValue(
			SecureRandomUtil.class, "_random", predictableRandom);

		byte[] bytes = ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_bytes");

		predictableRandom.nextBytes(bytes);

		return predictableRandom;
	}

	protected long reload() {
		return ReflectionTestUtil.invoke(
			SecureRandomUtil.class, "_reload", new Class<?>[0]);
	}

	private static final String _KEY_BUFFER_SIZE =
		SecureRandomUtil.class.getName() + ".buffer.size";

	private static class PredictableRandom extends SecureRandom {

		@Override
		public synchronized void nextBytes(byte[] bytes) {
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte)_counter.getAndIncrement();
			}
		}

		private AtomicInteger _counter = new AtomicInteger();

	}

}