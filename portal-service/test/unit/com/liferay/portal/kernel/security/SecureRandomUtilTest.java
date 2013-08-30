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

package com.liferay.portal.kernel.security;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.security.SecureRandom;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class SecureRandomUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		System.setProperty(_BUFFER_SIZE_KEY, "2048");
	}

	@After
	public void tearDown() {
		System.clearProperty(_BUFFER_SIZE_KEY);
	}

	@Test
	public void testConcurrentReload() throws Exception {
		SecureRandom secureRandom = _installPredictableRandom();

		FutureTask<Long> futureTask = new FutureTask<Long>(
			new Callable<Long>() {

				@Override
				public Long call() throws Exception {
					return _reload();
				}

			});

		Thread reloadThread = new Thread(futureTask);

		long gapValue = -1;

		synchronized (secureRandom) {
			reloadThread.start();

			while (reloadThread.getState() != Thread.State.BLOCKED);

			long gapSeed = _getGapSeed();

			gapValue = _reload();

			Assert.assertEquals(_getFirstLong() ^ gapSeed, gapValue);

			Assert.assertEquals(gapValue, _getGapSeed());
		}

		reloadThread.join();

		Assert.assertEquals(
			(Long)(_getFirstLong() ^ gapValue), futureTask.get());
	}

	@Test
	public void testInitialization() throws Exception {

		// Too small buffer size

		System.setProperty(_BUFFER_SIZE_KEY, "10");

		Field bufferSizeField = ReflectionUtil.getDeclaredField(
			SecureRandomUtil.class, "_bufferSize");

		Assert.assertEquals(1024, bufferSizeField.get(null));

		Field bytesField = ReflectionUtil.getDeclaredField(
			SecureRandomUtil.class, "_bytes");

		byte[] bytes = (byte[])bytesField.get(null);

		Assert.assertEquals(1024, bytes.length);
	}

	@Test
	public void testNextBoolean() throws Exception {

		// First load

		_installPredictableRandom();

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

		long firstLong = _getFirstLong();
		long gapSeed = _getGapSeed();

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
	public void testNextByte() throws Exception {

		// First load

		_installPredictableRandom();

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}

		// Gap number

		long firstLong = _getFirstLong();
		long gapSeed = _getGapSeed();

		long result = firstLong ^ gapSeed;

		Assert.assertEquals((byte)result, SecureRandomUtil.nextByte());

		// Second load

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}
	}

	@Test
	public void testNextDouble() throws Exception {

		// First load

		_installPredictableRandom();

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

		long firstLong = _getFirstLong();
		long gapSeed = _getGapSeed();

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
	public void testNextFloat() throws Exception {

		// First load

		_installPredictableRandom();

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

		long firstLong = _getFirstLong();
		long gapSeed = _getGapSeed();

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
	public void testNextInt() throws Exception {

		// First load

		_installPredictableRandom();

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

		long firstLong = _getFirstLong();
		long gapSeed = _getGapSeed();

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
	public void testNextLong() throws Exception {

		// First load

		_installPredictableRandom();

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

		long firstLong = _getFirstLong();
		long gapSeed = _getGapSeed();

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

	private long _getFirstLong() throws Exception {
		Field bytesField = ReflectionUtil.getDeclaredField(
			SecureRandomUtil.class, "_bytes");

		byte[] bytes = (byte[])bytesField.get(null);

		return BigEndianCodec.getLong(bytes, 0);
	}

	private long _getGapSeed() throws Exception {
		Field gapSeedField = ReflectionUtil.getDeclaredField(
			SecureRandomUtil.class, "_gapSeed");

		return gapSeedField.getLong(null);
	}

	private SecureRandom _installPredictableRandom() throws Exception {
		SecureRandom predictableRandom = new PredictableRandom();

		Field secureRandomField = ReflectionUtil.getDeclaredField(
			SecureRandomUtil.class, "_random");

		secureRandomField.set(null, predictableRandom);

		Field bytesField = ReflectionUtil.getDeclaredField(
			SecureRandomUtil.class, "_bytes");

		byte[] bytes = (byte[])bytesField.get(null);

		predictableRandom.nextBytes(bytes);

		return predictableRandom;
	}

	private long _reload() throws Exception {
		Method reloadMethod = ReflectionUtil.getDeclaredMethod(
			SecureRandomUtil.class, "_reload");

		return (Long)reloadMethod.invoke(null);
	}

	private static final String _BUFFER_SIZE_KEY =
		SecureRandomUtil.class.getName() + ".buffer.size";

	private static class PredictableRandom extends SecureRandom {

		@Override
		public synchronized void nextBytes(byte[] bytes) {
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte)_counter.getAndIncrement();
			}
		}

		private final AtomicInteger _counter = new AtomicInteger();

	}

}