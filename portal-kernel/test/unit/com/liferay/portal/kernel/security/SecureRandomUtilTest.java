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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.security.SecureRandom;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

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

		FutureTask<Long> futureTask = new FutureTask<>(
			new Callable<Long>() {

				@Override
				public Long call() {
					return reload();
				}

			});

		Thread reloadThread = new Thread(futureTask);

		synchronized (secureRandom) {
			reloadThread.start();

			while (reloadThread.getState() != Thread.State.BLOCKED);

			Assert.assertEquals(getLong(0), reload());
		}

		reloadThread.join();

		Assert.assertEquals((Long)(getLong(0) ^ 1), futureTask.get());
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
			Integer.valueOf(1024),
			ReflectionTestUtil.getFieldValue(
				SecureRandomUtil.class, "_BUFFER_SIZE"));

		byte[] bytes = ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_bytes");

		Assert.assertEquals(Arrays.toString(bytes), 1024, bytes.length);
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

		if ((byte)getLong(7) < 0) {
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

		// Gap number

		if ((byte)(getLong(7) ^ 1) < 0) {
			Assert.assertFalse(SecureRandomUtil.nextBoolean());
		}
		else {
			Assert.assertTrue(SecureRandomUtil.nextBoolean());
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

		Assert.assertEquals((byte)getLong(7), SecureRandomUtil.nextByte());

		// Second load

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}

		// Gap number

		Assert.assertEquals(
			(byte)(getLong(7) ^ 1), SecureRandomUtil.nextByte());
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

		Assert.assertEquals(
			Double.longBitsToDouble(getLong(7)), SecureRandomUtil.nextDouble(),
			0);

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

		// Gap number

		Assert.assertEquals(
			Double.longBitsToDouble(getLong(7) ^ 1),
			SecureRandomUtil.nextDouble(), 0);
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

		Assert.assertEquals(
			Float.intBitsToFloat((int)getLong(7)), SecureRandomUtil.nextFloat(),
			0);

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

		// Gap number

		Assert.assertEquals(
			Float.intBitsToFloat((int)getLong(7) ^ 1),
			SecureRandomUtil.nextFloat(), 0);
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
				BigEndianCodec.getInt(bytes, 0), SecureRandomUtil.nextInt());
		}

		// Gap number

		Assert.assertEquals((int)getLong(7), SecureRandomUtil.nextInt());

		// Second load

		for (int i = 0; i < 512; i++) {
			byte b = (byte)(i * 4);

			byte[] bytes = new byte[4];

			for (int j = 0; j < 4; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getInt(bytes, 0), SecureRandomUtil.nextInt());
		}

		// Gap number

		Assert.assertEquals((int)(getLong(7) ^ 1L), SecureRandomUtil.nextInt());
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
				BigEndianCodec.getLong(bytes, 0), SecureRandomUtil.nextLong());
		}

		// Gap number

		Assert.assertEquals(getLong(7), SecureRandomUtil.nextLong());

		// Second load

		for (int i = 0; i < 256; i++) {
			byte b = (byte)(i * 8);

			byte[] bytes = new byte[8];

			for (int j = 0; j < 8; j++) {
				bytes[j] = (byte)(b + j);
			}

			Assert.assertEquals(
				BigEndianCodec.getLong(bytes, 0), SecureRandomUtil.nextLong());
		}

		// Gap number

		Assert.assertEquals(getLong(7) ^ 1, SecureRandomUtil.nextLong());
	}

	protected long getLong(int offset) {
		byte[] bytes = ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_bytes");

		return BigEndianCodec.getLong(bytes, offset);
	}

	protected SecureRandom installPredictableRandom() {
		ReflectionTestUtil.setFieldValue(
			SecureRandomUtil.class, "_gapRandom",
			new Random() {

				@Override
				public long nextLong() {
					return _counter.getAndIncrement();
				}

				private static final long serialVersionUID = 1L;

				private final AtomicLong _counter = new AtomicLong();

			});

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
			SecureRandomUtil.class, "_reload", new Class<?>[] {int.class}, 0);
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

		@Override
		public long nextLong() {
			return 0;
		}

		private static final long serialVersionUID = 1L;

		private final AtomicInteger _counter = new AtomicInteger();

	}

}