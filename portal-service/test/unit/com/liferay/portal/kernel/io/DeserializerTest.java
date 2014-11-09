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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class DeserializerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(AnnotatedObjectInputStream.class);
				assertClasses.add(AnnotatedObjectOutputStream.class);
			}

		};

	@Before
	public void setUp() {
		Class<?> clazz =  getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());
	}

	@Test
	public void testBufferInputStream() {
		byte[] data = new byte[_COUNT];

		_random.nextBytes(data);

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(data));

		Deserializer.BufferInputStream bufferInputStream =
			deserializer.new BufferInputStream();

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(data[i], bufferInputStream.read());
		}

		deserializer = new Deserializer(ByteBuffer.wrap(data));

		bufferInputStream = deserializer.new BufferInputStream();

		int size1 = _COUNT * 2 / 3;
		int size2 = _COUNT - size1;

		byte[] newBytes = new byte[size1];

		int count = bufferInputStream.read(newBytes);

		Assert.assertEquals(size1, count);

		for (int i = 0; i < size1; i++) {
			Assert.assertEquals(data[i], newBytes[i]);
		}

		newBytes = new byte[size1];

		count = bufferInputStream.read(newBytes);

		Assert.assertEquals(size2, count);

		for (int i = 0; i < size2; i++) {
			Assert.assertEquals(data[i + size1], newBytes[i]);
		}
	}

	@Test
	public void testDetectBufferUnderflow() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);

		Deserializer deserializer = new Deserializer(byteBuffer);

		deserializer.detectBufferUnderflow(4);

		try {
			deserializer.detectBufferUnderflow(5);

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals("Buffer underflow", ise.getMessage());
		}
	}

	@Test
	public void testReadBoolean() {
		byte[] bytes = new byte[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			bytes[i] = _random.nextBoolean() ? (byte)1 : (byte)0;
		}

		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			if (bytes[i] == 0) {
				Assert.assertFalse(deserializer.readBoolean());
			}
			else {
				Assert.assertTrue(deserializer.readBoolean());
			}
		}
	}

	@Test
	public void testReadByte() {
		byte[] bytes = new byte[_COUNT];

		_random.nextBytes(bytes);

		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(bytes[i], deserializer.readByte());
		}
	}

	@Test
	public void testReadChar() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 2);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		CharBuffer charBuffer = byteBuffer.asCharBuffer();

		char[] chars = new char[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			chars[i] = (char)_random.nextInt();

			charBuffer.put(chars[i]);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(chars[i], deserializer.readChar());
		}
	}

	@Test
	public void testReadDouble() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 8);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

		double[] doubles = new double[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			doubles[i] = _random.nextDouble();

			doubleBuffer.put(doubles[i]);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(
				(Double)doubles[i], (Double)deserializer.readDouble());
		}
	}

	@Test
	public void testReadFloat() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 4);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();

		float[] floats = new float[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			floats[i] = _random.nextFloat();

			floatBuffer.put(floats[i]);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(
				(Float)floats[i], (Float)deserializer.readFloat());
		}
	}

	@Test
	public void testReadInt() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 4);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		IntBuffer intBuffer = byteBuffer.asIntBuffer();

		int[] ints = new int[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			ints[i] = _random.nextInt();

			intBuffer.put(ints[i]);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(ints[i], deserializer.readInt());
		}
	}

	@Test
	public void testReadLong() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 8);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		LongBuffer longBuffer = byteBuffer.asLongBuffer();

		long[] longs = new long[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			longs[i] = _random.nextLong();

			longBuffer.put(longs[i]);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(longs[i], deserializer.readLong());
		}
	}

	@Test
	public void testReadObjectBoolean() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);

		byteBuffer.put(SerializationConstants.TC_BOOLEAN);
		byteBuffer.put((byte)1);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Boolean);
		Assert.assertSame(Boolean.TRUE, object);
	}

	@Test
	public void testReadObjectByte() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);

		byteBuffer.put(SerializationConstants.TC_BYTE);
		byteBuffer.put((byte)101);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Byte);
		Assert.assertSame(Byte.valueOf((byte)101), object);
	}

	@Test
	public void testReadObjectCharacter() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(3);

		byteBuffer.put(SerializationConstants.TC_CHARACTER);
		byteBuffer.putChar('a');

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Character);
		Assert.assertSame(Character.valueOf('a'), object);
	}

	@Test
	public void testReadObjectClass() throws Exception {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		ByteBuffer byteBuffer = ByteBuffer.allocate(className.length() + 11);

		byteBuffer.put(SerializationConstants.TC_CLASS);
		byteBuffer.put((byte)1);
		byteBuffer.putInt(0);
		byteBuffer.put((byte)1);
		byteBuffer.putInt(className.length());
		byteBuffer.put(className.getBytes(StringPool.UTF8));

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Class<?> readClass = deserializer.readObject();

		Assert.assertSame(clazz, readClass);
	}

	@Test
	public void testReadObjectDouble() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(9);

		byteBuffer.put(SerializationConstants.TC_DOUBLE);
		byteBuffer.putDouble(17.58D);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Double);
		Assert.assertEquals(17.58D, object);
	}

	@Test
	public void testReadObjectFloat() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(5);

		byteBuffer.put(SerializationConstants.TC_FLOAT);
		byteBuffer.putFloat(17.58F);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Float);
		Assert.assertEquals(17.58F, object);
	}

	@Test
	public void testReadObjectInteger() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(5);

		byteBuffer.put(SerializationConstants.TC_INTEGER);
		byteBuffer.putInt(101);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Integer);
		Assert.assertSame(Integer.valueOf(101), object);
	}

	@Test
	public void testReadObjectLong() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(9);

		byteBuffer.put(SerializationConstants.TC_LONG);
		byteBuffer.putLong(101);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Long);
		Assert.assertSame(Long.valueOf(101), object);
	}

	@Test
	public void testReadObjectNull() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1);

		byteBuffer.put(SerializationConstants.TC_NULL);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertNull(object);
	}

	@Test
	public void testReadObjectOrdinary() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(SerializationConstants.TC_OBJECT);

		ObjectOutputStream objectOutputStream = new AnnotatedObjectOutputStream(
			unsyncByteArrayOutputStream);

		Date date = new Date(123456);

		objectOutputStream.writeObject(date);

		ByteBuffer byteBuffer =
			unsyncByteArrayOutputStream.unsafeGetByteBuffer();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Date);
		Assert.assertEquals(date, object);
	}

	@Test
	public void testReadObjectOrdinaryNPE() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(SerializationConstants.TC_OBJECT);

		unsyncByteArrayOutputStream.write(1);
		unsyncByteArrayOutputStream.write(new byte[4]);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		Date date = new Date(123456);

		objectOutputStream.writeObject(date);

		ByteBuffer byteBuffer =
			unsyncByteArrayOutputStream.unsafeGetByteBuffer();

		// Corrupt magic header

		byteBuffer.put(6, (byte)0xFF);

		Deserializer deserializer = new Deserializer(byteBuffer);

		try {
			deserializer.readObject();

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertTrue(
				re.getCause() instanceof StreamCorruptedException);
		}
	}

	@Test
	public void testReadObjectShort() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(3);

		byteBuffer.put(SerializationConstants.TC_SHORT);
		byteBuffer.putShort((short)101);

		byteBuffer.flip();

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Short);
		Assert.assertSame(Short.valueOf((short)101), object);
	}

	@Test
	public void testReadObjectString() throws ClassNotFoundException {
		String asciiString = "abcdefghijklmn";

		byte[] buffer = new byte[asciiString.length() + 6];

		buffer[0] = SerializationConstants.TC_STRING;
		buffer[1] = 1;

		BigEndianCodec.putInt(buffer, 2, asciiString.length());

		for (int i = 0; i < asciiString.length(); i++) {
			buffer[6 + i] = (byte)asciiString.charAt(i);
		}

		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

		Deserializer deserializer = new Deserializer(byteBuffer);

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof String);
		Assert.assertEquals(asciiString, object);

		String nonAsciiString = "非ASCII Code中文测试";

		buffer = new byte[nonAsciiString.length() * 2 + 6];

		buffer[0] = SerializationConstants.TC_STRING;
		buffer[1] = 0;

		BigEndianCodec.putInt(buffer, 2, nonAsciiString.length());

		for (int i = 0; i < nonAsciiString.length(); i++) {
			BigEndianCodec.putChar(buffer, 6 + i * 2, nonAsciiString.charAt(i));
		}

		byteBuffer = ByteBuffer.wrap(buffer);

		deserializer = new Deserializer(byteBuffer);

		object = deserializer.readObject();

		Assert.assertTrue(object instanceof String);
		Assert.assertEquals(nonAsciiString, object);
	}

	@Test
	public void testReadObjectUnknowTCCode() throws ClassNotFoundException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1);

		byteBuffer.put((byte)12);

		Deserializer deserializer = new Deserializer(byteBuffer);

		try {
			deserializer.readObject();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals("Unkown TC code 12", ise.getMessage());
		}
	}

	@Test
	public void testReadShort() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 2);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();

		short[] shorts = new short[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			shorts[i] = (short)_random.nextInt();

			shortBuffer.put(shorts[i]);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		for (int i = 0; i < _COUNT; i++) {
			Assert.assertEquals(shorts[i], deserializer.readShort());
		}
	}

	@Test
	public void testReadString() {
		String asciiString = "abcdefghijklmn";

		byte[] buffer = new byte[asciiString.length() + 5];

		buffer[0] = 1;

		BigEndianCodec.putInt(buffer, 1, asciiString.length());

		for (int i = 0; i < asciiString.length(); i++) {
			buffer[5 + i] = (byte)asciiString.charAt(i);
		}

		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

		Deserializer deserializer = new Deserializer(byteBuffer);

		String resultString = deserializer.readString();

		Assert.assertEquals(asciiString, resultString);

		String nonAsciiString = "非ASCII Code中文测试";

		buffer = new byte[nonAsciiString.length() * 2 + 5];

		buffer[0] = 0;

		BigEndianCodec.putInt(buffer, 1, nonAsciiString.length());

		for (int i = 0; i < nonAsciiString.length(); i++) {
			BigEndianCodec.putChar(buffer, 5 + i * 2, nonAsciiString.charAt(i));
		}

		byteBuffer = ByteBuffer.wrap(buffer);

		deserializer = new Deserializer(byteBuffer);

		resultString = deserializer.readString();

		Assert.assertEquals(nonAsciiString, resultString);
	}

	private static final int _COUNT = 1024;

	private Random _random = new Random();

}