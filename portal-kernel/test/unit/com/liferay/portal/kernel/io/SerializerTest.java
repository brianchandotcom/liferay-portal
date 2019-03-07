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

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SerializerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(AnnotatedObjectInputStream.class);
					assertClasses.add(AnnotatedObjectOutputStream.class);
				}

			},
			NewEnvTestRule.INSTANCE);

	@Before
	public void setUp() {
		System.clearProperty(
			Serializer.class.getName() + ".thread.local.buffer.count.limit");
		System.clearProperty(
			Serializer.class.getName() + ".thread.local.buffer.size.limit");
	}

	@Test
	public void testBufferOutputStream() {
		byte[] data = new byte[1024];

		_random.nextBytes(data);

		Serializer serializer = new Serializer();

		Serializer.BufferOutputStream bufferOutputStream =
			serializer.new BufferOutputStream();

		for (byte b : data) {
			bufferOutputStream.write(b);
		}

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(data, byteBuffer.array());

		serializer = new Serializer();

		bufferOutputStream = serializer.new BufferOutputStream();

		bufferOutputStream.write(data);

		byteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(data, byteBuffer.array());
	}

	@Test
	public void testBufferQueue() {
		Serializer.BufferQueue bufferQueue = new Serializer.BufferQueue();

		// Insert into empty queue

		byte[] buffer2 = new byte[2];

		bufferQueue.enqueue(buffer2);

		Serializer.BufferNode bufferNode1 = bufferQueue.headBufferNode;

		Assert.assertSame(buffer2, bufferNode1.buffer);
		Assert.assertNull(bufferNode1.next);

		// Insert to head

		byte[] buffer4 = new byte[4];

		bufferQueue.enqueue(buffer4);

		bufferNode1 = bufferQueue.headBufferNode;

		Serializer.BufferNode bufferNode2 = bufferNode1.next;

		Assert.assertSame(buffer4, bufferNode1.buffer);
		Assert.assertNotNull(bufferNode1.next);

		Assert.assertSame(buffer2, bufferNode2.buffer);
		Assert.assertNull(bufferNode2.next);

		// Insert to second

		byte[] buffer3 = new byte[3];

		bufferQueue.enqueue(buffer3);

		bufferNode1 = bufferQueue.headBufferNode;

		bufferNode2 = bufferNode1.next;

		Serializer.BufferNode bufferNode3 = bufferNode2.next;

		Assert.assertSame(buffer4, bufferNode1.buffer);
		Assert.assertNotNull(bufferNode1.next);
		Assert.assertSame(buffer3, bufferNode2.buffer);
		Assert.assertNotNull(bufferNode2.next);
		Assert.assertSame(buffer2, bufferNode3.buffer);
		Assert.assertNull(bufferNode3.next);

		// Insert to last

		byte[] buffer1 = new byte[1];

		bufferQueue.enqueue(buffer1);

		bufferNode1 = bufferQueue.headBufferNode;

		bufferNode2 = bufferNode1.next;

		bufferNode3 = bufferNode2.next;

		Serializer.BufferNode bufferNode4 = bufferNode3.next;

		Assert.assertSame(buffer4, bufferNode1.buffer);
		Assert.assertNotNull(bufferNode1.next);
		Assert.assertSame(buffer3, bufferNode2.buffer);
		Assert.assertNotNull(bufferNode2.next);
		Assert.assertSame(buffer2, bufferNode3.buffer);
		Assert.assertNotNull(bufferNode3.next);
		Assert.assertSame(buffer1, bufferNode4.buffer);
		Assert.assertNull(bufferNode4.next);

		// Fill up queue to default count limit

		byte[] buffer5 = new byte[5];
		byte[] buffer6 = new byte[6];
		byte[] buffer7 = new byte[7];
		byte[] buffer8 = new byte[8];

		bufferQueue.enqueue(buffer5);
		bufferQueue.enqueue(buffer6);
		bufferQueue.enqueue(buffer7);
		bufferQueue.enqueue(buffer8);

		// Automatically release smallest on insert to head

		byte[] buffer10 = new byte[10];

		bufferQueue.enqueue(buffer10);

		bufferNode1 = bufferQueue.headBufferNode;

		bufferNode2 = bufferNode1.next;

		bufferNode3 = bufferNode2.next;

		bufferNode4 = bufferNode3.next;

		Serializer.BufferNode bufferNode5 = bufferNode4.next;

		Serializer.BufferNode bufferNode6 = bufferNode5.next;

		Serializer.BufferNode bufferNode7 = bufferNode6.next;

		Serializer.BufferNode bufferNode8 = bufferNode7.next;

		Assert.assertSame(buffer10, bufferNode1.buffer);
		Assert.assertNotNull(bufferNode1.next);
		Assert.assertSame(buffer8, bufferNode2.buffer);
		Assert.assertNotNull(bufferNode2.next);
		Assert.assertSame(buffer7, bufferNode3.buffer);
		Assert.assertNotNull(bufferNode3.next);
		Assert.assertSame(buffer6, bufferNode4.buffer);
		Assert.assertNotNull(bufferNode4.next);
		Assert.assertSame(buffer5, bufferNode5.buffer);
		Assert.assertNotNull(bufferNode5.next);
		Assert.assertSame(buffer4, bufferNode6.buffer);
		Assert.assertNotNull(bufferNode6.next);
		Assert.assertSame(buffer3, bufferNode7.buffer);
		Assert.assertNotNull(bufferNode7.next);
		Assert.assertSame(buffer2, bufferNode8.buffer);
		Assert.assertNull(bufferNode8.next);

		// Automatically release smallest on insert to second

		byte[] buffer9 = new byte[9];

		bufferQueue.enqueue(buffer9);

		bufferNode1 = bufferQueue.headBufferNode;

		bufferNode2 = bufferNode1.next;

		bufferNode3 = bufferNode2.next;

		bufferNode4 = bufferNode3.next;

		bufferNode5 = bufferNode4.next;

		bufferNode6 = bufferNode5.next;

		bufferNode7 = bufferNode6.next;

		bufferNode8 = bufferNode7.next;

		Assert.assertSame(buffer10, bufferNode1.buffer);
		Assert.assertNotNull(bufferNode1.next);
		Assert.assertSame(buffer9, bufferNode2.buffer);
		Assert.assertNotNull(bufferNode2.next);
		Assert.assertSame(buffer8, bufferNode3.buffer);
		Assert.assertNotNull(bufferNode3.next);
		Assert.assertSame(buffer7, bufferNode4.buffer);
		Assert.assertNotNull(bufferNode4.next);
		Assert.assertSame(buffer6, bufferNode5.buffer);
		Assert.assertNotNull(bufferNode5.next);
		Assert.assertSame(buffer5, bufferNode6.buffer);
		Assert.assertNotNull(bufferNode6.next);
		Assert.assertSame(buffer4, bufferNode7.buffer);
		Assert.assertNotNull(bufferNode7.next);
		Assert.assertSame(buffer3, bufferNode8.buffer);
		Assert.assertNull(bufferNode8.next);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testCustomizedClassInitialization() {
		System.setProperty(
			Serializer.class.getName() + ".thread.local.buffer.count.limit",
			String.valueOf(Serializer.THREADLOCAL_BUFFER_COUNT_MIN + 1));
		System.setProperty(
			Serializer.class.getName() + ".thread.local.buffer.size.limit",
			String.valueOf(Serializer.THREADLOCAL_BUFFER_SIZE_MIN + 1));

		Assert.assertEquals(
			Serializer.THREADLOCAL_BUFFER_COUNT_MIN + 1,
			Serializer.THREADLOCAL_BUFFER_COUNT_LIMIT);
		Assert.assertEquals(
			Serializer.THREADLOCAL_BUFFER_SIZE_MIN + 1,
			Serializer.THREADLOCAL_BUFFER_SIZE_LIMIT);
	}

	@Test
	public void testDefaultClassInitialization() {
		Assert.assertEquals(
			Serializer.THREADLOCAL_BUFFER_COUNT_MIN,
			Serializer.THREADLOCAL_BUFFER_COUNT_LIMIT);
		Assert.assertEquals(
			Serializer.THREADLOCAL_BUFFER_SIZE_MIN,
			Serializer.THREADLOCAL_BUFFER_SIZE_LIMIT);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testDefendedClassInitialization() {
		System.setProperty(
			Serializer.class.getName() + ".thread.local.buffer.count.limit",
			String.valueOf(Serializer.THREADLOCAL_BUFFER_COUNT_MIN - 1));
		System.setProperty(
			Serializer.class.getName() + ".thread.local.buffer.size.limit",
			String.valueOf(Serializer.THREADLOCAL_BUFFER_SIZE_MIN - 1));

		Assert.assertEquals(
			Serializer.THREADLOCAL_BUFFER_COUNT_MIN,
			Serializer.THREADLOCAL_BUFFER_COUNT_LIMIT);
		Assert.assertEquals(
			Serializer.THREADLOCAL_BUFFER_SIZE_MIN,
			Serializer.THREADLOCAL_BUFFER_SIZE_LIMIT);
	}

	@Test
	public void testGetBufferGrow() {
		Serializer serializer = new Serializer();

		// OOME

		serializer.index = 1;

		try {
			serializer.getBuffer(Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (OutOfMemoryError oome) {
		}

		// Normal doubling size

		byte[] bytes = new byte[_COUNT];

		_random.nextBytes(bytes);

		serializer.buffer = bytes;
		serializer.index = bytes.length;

		byte[] newBytes = serializer.getBuffer(1);

		Assert.assertEquals(
			Arrays.toString(newBytes), bytes.length * 2, newBytes.length);

		for (int i = 0; i < bytes.length; i++) {
			Assert.assertEquals(bytes[i], newBytes[i]);
		}

		for (int i = bytes.length; i < newBytes.length; i++) {
			Assert.assertEquals(0, newBytes[i]);
		}

		// Doubling size is still less than minimum size

		serializer.buffer = bytes;
		serializer.index = bytes.length;

		newBytes = serializer.getBuffer(_COUNT + 1);

		Assert.assertEquals(
			Arrays.toString(newBytes), bytes.length * 2 + 1, newBytes.length);

		for (int i = 0; i < bytes.length; i++) {
			Assert.assertEquals(bytes[i], newBytes[i]);
		}

		for (int i = bytes.length; i < newBytes.length; i++) {
			Assert.assertEquals(0, newBytes[i]);
		}
	}

	@Test
	public void testReleaseLargeBuffer() throws IOException {
		Serializer.bufferQueueThreadLocal.remove();

		Serializer serializer = new Serializer();

		char[] chars = new char[Serializer.THREADLOCAL_BUFFER_SIZE_LIMIT];

		serializer.writeString(new String(chars));

		serializer.toByteBuffer();

		Serializer.BufferQueue bufferQueue = ReflectionTestUtil.invoke(
			serializer, "_getBufferQueue", new Class<?>[0]);

		Assert.assertEquals(0, bufferQueue.count);

		serializer = new Serializer();

		serializer.writeString(new String(chars));

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		serializer.writeTo(unsyncByteArrayOutputStream);

		Assert.assertEquals(0, bufferQueue.count);
		Assert.assertEquals(
			chars.length * 2 + 5, unsyncByteArrayOutputStream.size());
	}

	@Test
	public void testWriteBoolean() {
		Serializer serializer = new Serializer();

		boolean[] booleans = new boolean[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			booleans[i] = _random.nextBoolean();

			serializer.writeBoolean(booleans[i]);
		}

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		byte[] bytes = byteBuffer.array();

		Assert.assertNull(serializer.buffer);

		for (int i = 0; i < _COUNT; i++) {
			if (booleans[i]) {
				Assert.assertEquals(1, bytes[i]);
			}
			else {
				Assert.assertEquals(0, bytes[i]);
			}
		}
	}

	@Test
	public void testWriteByte() {
		Serializer serializer = new Serializer();

		byte[] bytes = new byte[_COUNT];

		_random.nextBytes(bytes);

		for (int i = 0; i < _COUNT; i++) {
			serializer.writeByte(bytes[i]);
		}

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(bytes, byteBuffer.array());
	}

	@Test
	public void testWriteChar() {
		Serializer serializer = new Serializer();

		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 2);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		CharBuffer charBuffer = byteBuffer.asCharBuffer();

		char[] chars = new char[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			chars[i] = (char)_random.nextInt();

			serializer.writeChar(chars[i]);

			charBuffer.put(chars[i]);
		}

		ByteBuffer serializerByteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(
			byteBuffer.array(), serializerByteBuffer.array());
	}

	@Test
	public void testWriteDouble() {
		Serializer serializer = new Serializer();

		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 8);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();

		double[] doubles = new double[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			doubles[i] = _random.nextDouble();

			serializer.writeDouble(doubles[i]);

			doubleBuffer.put(doubles[i]);
		}

		ByteBuffer serializerByteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(
			byteBuffer.array(), serializerByteBuffer.array());
	}

	@Test
	public void testWriteFloat() {
		Serializer serializer = new Serializer();

		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 4);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();

		float[] floats = new float[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			floats[i] = _random.nextFloat();

			serializer.writeFloat(floats[i]);

			floatBuffer.put(floats[i]);
		}

		ByteBuffer serializerByteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(
			byteBuffer.array(), serializerByteBuffer.array());
	}

	@Test
	public void testWriteInt() {
		Serializer serializer = new Serializer();

		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 4);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		IntBuffer intBuffer = byteBuffer.asIntBuffer();

		int[] ints = new int[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			ints[i] = _random.nextInt();

			serializer.writeInt(ints[i]);

			intBuffer.put(ints[i]);
		}

		ByteBuffer serializerByteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(
			byteBuffer.array(), serializerByteBuffer.array());
	}

	@Test
	public void testWriteLong() {
		Serializer serializer = new Serializer();

		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 8);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		LongBuffer longBuffer = byteBuffer.asLongBuffer();

		long[] longs = new long[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			longs[i] = _random.nextLong();

			serializer.writeLong(longs[i]);

			longBuffer.put(longs[i]);
		}

		ByteBuffer serializerByteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(
			byteBuffer.array(), serializerByteBuffer.array());
	}

	@Test
	public void testWriteObjectBoolean() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Boolean.TRUE);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(2, byteBuffer.limit());
		Assert.assertEquals(
			SerializationConstants.TC_BOOLEAN, byteBuffer.get());
		Assert.assertEquals(1, byteBuffer.get());
	}

	@Test
	public void testWriteObjectByte() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Byte.valueOf((byte)101));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(2, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_BYTE, byteBuffer.get());
		Assert.assertEquals(101, byteBuffer.get());
	}

	@Test
	public void testWriteObjectCharacter() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Character.valueOf('a'));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(3, byteBuffer.limit());
		Assert.assertEquals(
			SerializationConstants.TC_CHARACTER, byteBuffer.get());
		Assert.assertEquals('a', byteBuffer.getChar());
	}

	@Test
	public void testWriteObjectClassWithBlankContextName()
		throws UnsupportedEncodingException {

		Serializer serializer = new Serializer();

		Class<?> clazz = getClass();

		ClassLoaderPool.register(StringPool.BLANK, clazz.getClassLoader());

		try {
			serializer.writeObject(clazz);
		}
		finally {
			ClassLoaderPool.unregister(clazz.getClassLoader());
		}

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		String className = clazz.getName();

		Assert.assertEquals(className.length() + 11, byteBuffer.limit());

		Assert.assertEquals(SerializationConstants.TC_CLASS, byteBuffer.get());
		Assert.assertEquals(1, byteBuffer.get());
		Assert.assertEquals(0, byteBuffer.getInt());
		Assert.assertEquals(1, byteBuffer.get());
		Assert.assertEquals(className.length(), byteBuffer.getInt());
		Assert.assertEquals(
			className,
			new String(
				byteBuffer.array(), byteBuffer.position(),
				byteBuffer.remaining(), StringPool.UTF8));
	}

	@Test
	public void testWriteObjectClassWithNullContextName()
		throws UnsupportedEncodingException {

		Serializer serializer = new Serializer();

		Class<?> clazz = getClass();

		serializer.writeObject(clazz);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		String className = clazz.getName();

		String contextName = StringPool.NULL;

		byte[] contextNameBytes = contextName.getBytes(StringPool.UTF8);

		Assert.assertEquals(
			className.length() + contextName.length() + 11, byteBuffer.limit());

		Assert.assertEquals(SerializationConstants.TC_CLASS, byteBuffer.get());
		Assert.assertEquals(1, byteBuffer.get());
		Assert.assertEquals(contextName.length(), byteBuffer.getInt());
		Assert.assertEquals(
			contextName,
			new String(
				byteBuffer.array(), byteBuffer.position(),
				contextNameBytes.length, StringPool.UTF8));

		byteBuffer.position(byteBuffer.position() + contextNameBytes.length);

		Assert.assertEquals(1, byteBuffer.get());
		Assert.assertEquals(className.length(), byteBuffer.getInt());
		Assert.assertEquals(
			className,
			new String(
				byteBuffer.array(), byteBuffer.position(),
				byteBuffer.remaining(), StringPool.UTF8));
	}

	@Test
	public void testWriteObjectDouble() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Double.valueOf(17.58D));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(9, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_DOUBLE, byteBuffer.get());
		Assert.assertTrue(byteBuffer.getDouble() == 17.58D);
	}

	@Test
	public void testWriteObjectFloat() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Float.valueOf(17.58F));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(5, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_FLOAT, byteBuffer.get());
		Assert.assertTrue(byteBuffer.getFloat() == 17.58F);
	}

	@Test
	public void testWriteObjectInteger() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Integer.valueOf(101));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(5, byteBuffer.limit());
		Assert.assertEquals(
			SerializationConstants.TC_INTEGER, byteBuffer.get());
		Assert.assertEquals(101, byteBuffer.getInt());
	}

	@Test
	public void testWriteObjectLong() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Long.valueOf(101));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(9, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_LONG, byteBuffer.get());
		Assert.assertEquals(101, byteBuffer.getLong());
	}

	@Test
	public void testWriteObjectNull() {
		Serializer serializer = new Serializer();

		serializer.writeObject(null);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(1, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_NULL, byteBuffer.get());
	}

	@Test
	public void testWriteObjectOrdinary() throws Exception {
		Serializer serializer = new Serializer();

		Date date = new Date(123456);

		serializer.writeObject(date);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(SerializationConstants.TC_OBJECT, byteBuffer.get());

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(
				byteBuffer.array(), byteBuffer.position(),
				byteBuffer.remaining());

		ObjectInputStream objectInputStream = new AnnotatedObjectInputStream(
			unsyncByteArrayInputStream);

		Object object = objectInputStream.readObject();

		Assert.assertTrue(object instanceof Date);
		Assert.assertEquals(date, object);
	}

	@Test
	public void testWriteObjectOrdinaryNPE() throws Exception {
		Serializer serializer = new Serializer();

		Serializable serializable = new Serializable() {

			private void writeObject(ObjectOutputStream objectOutputStream)
				throws IOException {

				throw new IOException("Forced IOException");
			}

		};

		try {
			serializer.writeObject(serializable);

			Assert.fail();
		}
		catch (RuntimeException re) {
			String message = re.getMessage();

			Assert.assertTrue(
				message.startsWith(
					"Unable to write ordinary serializable object "));

			Throwable throwable = re.getCause();

			Assert.assertTrue(throwable instanceof IOException);
			Assert.assertEquals("Forced IOException", throwable.getMessage());
		}
	}

	@Test
	public void testWriteObjectShort() {
		Serializer serializer = new Serializer();

		serializer.writeObject(Short.valueOf((short)101));

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(3, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_SHORT, byteBuffer.get());
		Assert.assertEquals(101, byteBuffer.getShort());
	}

	@Test
	public void testWriteObjectString() {
		String asciiString = "abcdefghijklmn";

		Serializer serializer = new Serializer();

		serializer.writeObject(asciiString);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(6 + asciiString.length(), byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_STRING, byteBuffer.get());
		Assert.assertEquals(1, byteBuffer.get());
		Assert.assertEquals(asciiString.length(), byteBuffer.getInt());

		for (int i = 0; i < asciiString.length(); i++) {
			Assert.assertEquals(asciiString.charAt(i), byteBuffer.get());
		}

		String nonAsciiString = "非ASCII Code中文测试";

		serializer = new Serializer();

		serializer.writeObject(nonAsciiString);

		byteBuffer = serializer.toByteBuffer();

		Assert.assertEquals(
			6 + nonAsciiString.length() * 2, byteBuffer.limit());
		Assert.assertEquals(SerializationConstants.TC_STRING, byteBuffer.get());
		Assert.assertEquals(0, byteBuffer.get());
		Assert.assertEquals(nonAsciiString.length(), byteBuffer.getInt());

		for (int i = 0; i < nonAsciiString.length(); i++) {
			Assert.assertEquals(nonAsciiString.charAt(i), byteBuffer.getChar());
		}
	}

	@Test
	public void testWriteShort() {
		Serializer serializer = new Serializer();

		ByteBuffer byteBuffer = ByteBuffer.allocate(_COUNT * 2);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();

		short[] shorts = new short[_COUNT];

		for (int i = 0; i < _COUNT; i++) {
			shorts[i] = (short)_random.nextInt();

			serializer.writeShort(shorts[i]);

			shortBuffer.put(shorts[i]);
		}

		ByteBuffer serializerByteBuffer = serializer.toByteBuffer();

		Assert.assertArrayEquals(
			byteBuffer.array(), serializerByteBuffer.array());
	}

	@Test
	public void testWriteString() throws IOException {
		String asciiString = "abcdefghijklmn";

		Serializer serializer = new Serializer();

		serializer.buffer = new byte[0];

		serializer.writeString(asciiString);

		Assert.assertEquals(serializer.index, 5 + asciiString.length());
		Assert.assertTrue(BigEndianCodec.getBoolean(serializer.buffer, 0));

		int length = BigEndianCodec.getInt(serializer.buffer, 1);

		Assert.assertEquals(asciiString.length(), length);

		ByteBuffer byteBuffer = ByteBuffer.allocate(asciiString.length());

		for (int i = 0; i < asciiString.length(); i++) {
			byteBuffer.put((byte)asciiString.charAt(i));
		}

		byteBuffer.flip();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		serializer.writeTo(unsyncByteArrayOutputStream);

		byte[] data = unsyncByteArrayOutputStream.toByteArray();

		for (int i = 5; i < data.length; i++) {
			Assert.assertEquals(byteBuffer.get(), data[i]);
		}

		String nonAsciiString = "非ASCII Code中文测试";

		serializer = new Serializer();

		serializer.writeString(nonAsciiString);

		Assert.assertEquals(serializer.index, 5 + nonAsciiString.length() * 2);
		Assert.assertFalse(BigEndianCodec.getBoolean(serializer.buffer, 0));

		length = BigEndianCodec.getInt(serializer.buffer, 1);

		Assert.assertEquals(nonAsciiString.length(), length);

		byteBuffer = ByteBuffer.allocate(nonAsciiString.length() * 2);

		byteBuffer.order(ByteOrder.BIG_ENDIAN);

		CharBuffer charBuffer = byteBuffer.asCharBuffer();

		for (int i = 0; i < nonAsciiString.length(); i++) {
			charBuffer.put(nonAsciiString.charAt(i));
		}

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		serializer.writeTo(unsyncByteArrayOutputStream);

		data = unsyncByteArrayOutputStream.toByteArray();

		for (int i = 5; i < data.length; i++) {
			Assert.assertEquals(byteBuffer.get(), data[i]);
		}
	}

	private static final int _COUNT = 1024;

	private final Random _random = new Random();

}