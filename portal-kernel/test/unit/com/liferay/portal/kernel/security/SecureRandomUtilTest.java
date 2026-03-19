/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security;

import com.liferay.petra.io.BigEndianCodec;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
		AtomicInteger counter = installPredictableRandom();

		FutureTask<Long> futureTask = new FutureTask<>(
			new Callable<Long>() {

				@Override
				public Long call() {
					return reload();
				}

			});

		Thread reloadThread = new Thread(futureTask);

		synchronized (counter) {
			reloadThread.start();

			while (reloadThread.getState() != Thread.State.BLOCKED);

			Assert.assertEquals(2048, reload());
		}

		reloadThread.join();

		Assert.assertEquals(4097, (long)futureTask.get());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		new SecureRandomUtil();
	}

	@Test
	public void testHighConcurrency() throws Exception {
		AtomicInteger duplicateCounter = new AtomicInteger();
		List<Future<Void>> list = new ArrayList<>();
		Set<String> values = ConcurrentHashMap.newKeySet();

		Runtime runtime = Runtime.getRuntime();

		int processors = runtime.availableProcessors();

		ExecutorService executorService = Executors.newFixedThreadPool(
			processors);

		for (int i = 0; i < (processors * 10000); i++) {
			list.add(
				executorService.submit(
					() -> {
						if (!values.add(
								SecureRandomUtil.nextLong() + "-" +
									SecureRandomUtil.nextLong())) {

							duplicateCounter.incrementAndGet();
						}

						return null;
					}));
		}

		for (Future<Void> future : list) {
			future.get();
		}

		executorService.shutdownNow();

		Assert.assertEquals(0, duplicateCounter.get());
	}

	@Test
	public void testInitialization() {
		System.setProperty(_KEY_BUFFER_SIZE, "10");

		Assert.assertEquals(
			Integer.valueOf(1024),
			ReflectionTestUtil.getFieldValue(
				SecureRandomUtil.class, "_BUFFER_SIZE"));

		byte[] bytes = ReflectionTestUtil.getFieldValue(
			ReflectionTestUtil.<Object>getFieldValue(
				SecureRandomUtil.class, "_buffer"),
			"_bytes");

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

		AtomicInteger counter = installPredictableRandom();

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}

		// Gap number

		Assert.assertEquals(
			(byte)(counter.get() + 2048), SecureRandomUtil.nextByte());

		// Second load

		for (int i = 0; i < 2048; i++) {
			Assert.assertEquals((byte)i, SecureRandomUtil.nextByte());
		}

		// Gap number

		Assert.assertEquals(
			(byte)(counter.get() + 2048), SecureRandomUtil.nextByte());
	}

	@Test
	public void testNextDouble() {

		// First load

		AtomicInteger counter = installPredictableRandom();

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
			Double.longBitsToDouble(counter.get() + 2048),
			SecureRandomUtil.nextDouble(), 0);

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
			Double.longBitsToDouble(counter.get() + 2048),
			SecureRandomUtil.nextDouble(), 0);
	}

	@Test
	public void testNextFloat() {

		// First load

		AtomicInteger counter = installPredictableRandom();

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
			Float.intBitsToFloat(counter.get() + 2048),
			SecureRandomUtil.nextFloat(), 0);

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
			Float.intBitsToFloat(counter.get() + 2048),
			SecureRandomUtil.nextFloat(), 0);
	}

	@Test
	public void testNextInt() {

		// First load

		AtomicInteger counter = installPredictableRandom();

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

		Assert.assertEquals(counter.get() + 2048, SecureRandomUtil.nextInt());

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

		Assert.assertEquals(counter.get() + 2048, SecureRandomUtil.nextInt());
	}

	@Test
	public void testNextLong() {

		// First load

		AtomicInteger counter = installPredictableRandom();

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

		Assert.assertEquals(counter.get() + 2048, SecureRandomUtil.nextLong());

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

		Assert.assertEquals(counter.get() + 2048, SecureRandomUtil.nextLong());
	}

	protected long getLong(int offset) {
		byte[] bytes = ReflectionTestUtil.getFieldValue(
			ReflectionTestUtil.<Object>getFieldValue(
				SecureRandomUtil.class, "_buffer"),
			"_bytes");

		return BigEndianCodec.getLong(bytes, offset);
	}

	protected AtomicInteger installPredictableRandom() {
		PredictableRandom predictableRandom = new PredictableRandom();

		ReflectionTestUtil.setFieldValue(
			SecureRandomUtil.class, "_random", predictableRandom);

		byte[] bytes = ReflectionTestUtil.getFieldValue(
			ReflectionTestUtil.<Object>getFieldValue(
				SecureRandomUtil.class, "_buffer"),
			"_bytes");

		predictableRandom.nextBytes(bytes);

		return predictableRandom._counter;
	}

	protected long reload() {
		return ReflectionTestUtil.invoke(
			SecureRandomUtil.class, "_reload", new Class<?>[0]);
	}

	private static final String _KEY_BUFFER_SIZE =
		SecureRandomUtil.class.getName() + ".buffer.size";

	private static class PredictableRandom extends SecureRandom {

		@Override
		public void nextBytes(byte[] bytes) {
			synchronized (_counter) {
				for (int i = 0; i < bytes.length; i++) {
					bytes[i] = (byte)_counter.getAndIncrement();
				}
			}
		}

		@Override
		public long nextLong() {
			return _counter.getAndIncrement();
		}

		private static final long serialVersionUID = 1L;

		private final AtomicInteger _counter = new AtomicInteger();

	}

}