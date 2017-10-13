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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BigEndianCodecTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testBoolean() {
		boolean[] booleans = new boolean[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			booleans[i] = _random.nextBoolean();
		}

		byte[] bytes = new byte[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			BigEndianCodec.putBoolean(bytes, i, booleans[i]);
		}

		for (int i = 0; i < _COUNT; i++) {
			if (booleans[i]) {
				Assert.assertEquals(1, bytes[i]);
			}
			else {
				Assert.assertEquals(0, bytes[i]);
			}
		}

		boolean[] newBooleans = new boolean[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newBooleans[i] = BigEndianCodec.getBoolean(bytes, i);
		}

		Assert.assertTrue(Arrays.equals(booleans, newBooleans));
	}

	@Test
	public void testChar() {
		char[] chars = new char[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			chars[i] = (char)_random.nextInt();
		}

		byte[] bytes = new byte[_COUNT * 2];

		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		CharBuffer charBuffer = byteBuffer.asCharBuffer();

		for (int i = 0; i < _COUNT; i++) {
			charBuffer.put(chars[i]);

			BigEndianCodec.putChar(bytes, i * 2, chars[i]);
		}

		Assert.assertArrayEquals(byteBuffer.array(), bytes);

		char[] newChars = new char[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newChars[i] = BigEndianCodec.getChar(bytes, i * 2);
		}

		Assert.assertArrayEquals(chars, newChars);
	}

	@Test
	public void testConstructor() {
		new BigEndianCodec();
	}

	@Test
	public void testDouble() {
		double[] doubles = new double[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			doubles[i] = _random.nextDouble();
		}

		byte[] bytes = new byte[_COUNT * 8];

		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

		for (int i = 0; i < _COUNT; i++) {
			doubleBuffer.put(doubles[i]);

			BigEndianCodec.putDouble(bytes, i * 8, doubles[i]);
		}

		Assert.assertArrayEquals(byteBuffer.array(), bytes);

		double[] newDoubles = new double[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newDoubles[i] = BigEndianCodec.getDouble(bytes, i * 8);
		}

		Assert.assertTrue(Arrays.equals(doubles, newDoubles));
	}

	@Test
	public void testFloat() {
		float[] floats = new float[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			floats[i] = _random.nextFloat();
		}

		byte[] bytes = new byte[_COUNT * 4];

		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();

		for (int i = 0; i < _COUNT; i++) {
			floatBuffer.put(floats[i]);

			BigEndianCodec.putFloat(bytes, i * 4, floats[i]);
		}

		Assert.assertArrayEquals(byteBuffer.array(), bytes);

		float[] newFloats = new float[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newFloats[i] = BigEndianCodec.getFloat(bytes, i * 4);
		}

		Assert.assertTrue(Arrays.equals(floats, newFloats));
	}

	@Test
	public void testInt() {
		int[] ints = new int[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			ints[i] = _random.nextInt();
		}

		byte[] bytes = new byte[_COUNT * 4];

		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		IntBuffer intBuffer = byteBuffer.asIntBuffer();

		for (int i = 0; i < _COUNT; i++) {
			intBuffer.put(ints[i]);

			BigEndianCodec.putInt(bytes, i * 4, ints[i]);
		}

		Assert.assertArrayEquals(byteBuffer.array(), bytes);

		int[] newInts = new int[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newInts[i] = BigEndianCodec.getInt(bytes, i * 4);
		}

		Assert.assertTrue(Arrays.equals(ints, newInts));
	}

	@Test
	public void testLong() {
		long[] longs = new long[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			longs[i] = _random.nextLong();
		}

		byte[] bytes = new byte[_COUNT * 8];

		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		LongBuffer longBuffer = byteBuffer.asLongBuffer();

		for (int i = 0; i < _COUNT; i++) {
			longBuffer.put(longs[i]);

			BigEndianCodec.putLong(bytes, i * 8, longs[i]);
		}

		Assert.assertArrayEquals(byteBuffer.array(), bytes);

		long[] newLongs = new long[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newLongs[i] = BigEndianCodec.getLong(bytes, i * 8);
		}

		Assert.assertTrue(Arrays.equals(longs, newLongs));
	}

	@Test
	public void testShort() {
		short[] shorts = new short[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			shorts[i] = (short)_random.nextInt();
		}

		byte[] bytes = new byte[_COUNT * 2];

		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();

		for (int i = 0; i < _COUNT; i++) {
			shortBuffer.put(shorts[i]);

			BigEndianCodec.putShort(bytes, i * 2, shorts[i]);
		}

		Assert.assertArrayEquals(byteBuffer.array(), bytes);

		short[] newShorts = new short[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			newShorts[i] = BigEndianCodec.getShort(bytes, i * 2);
		}

		Assert.assertTrue(Arrays.equals(shorts, newShorts));
	}

	private static final int _COUNT = 1024;

	private final Random _random = new Random();

}