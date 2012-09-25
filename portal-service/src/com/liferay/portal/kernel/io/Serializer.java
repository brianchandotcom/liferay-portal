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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.memory.SoftReferenceThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.Arrays;

/**
 * More compact primary types format(compares to
 * {@link java.io.ObjectOutputStream}) and ClassLoader aware Serializer.<p>
 *
 * For primary types, this class can provide better encoding performance than
 * {@link java.io.ObjectOutputStream}/{@link java.io.DataOutputStream}. Because
 * it has a more compact format (No BlockHeader) and more simple call stack
 * ({@link com.liferay.portal.kernel.io.BigEndianCodec} compares to OutputStream
 * wrapper on top of {@link java.io.Bits})<p>
 *
 * For String, unlike {@link java.io.ObjectOutputStream}/
 * {@link java.io.DataOutputStream}'s UTF encoding has a 2^16=64K length
 * limitation(Quite oftenly, that is not enough). Serializer extends the
 * limitation to 2^32=4G which is generally more than enough.<br>
 * For pure ASCII characters String, the encoding performance is almost same
 * (if not better) as {@link java.io.ObjectOutputStream}/
 * {@link java.io.DataOutputStream}.<br>
 * For String contains non-ASCII characters, Serializer is directly encoding
 * each char into two bytes rather than doing a UTF encoding. This is a trade
 * off for better CPU/Memory performance with the price of low compress rate.
 * UTF encoding costs more CPU cycles to detect unicode range for each char, the
 * output result has a variable length, which increase the memory burden on
 * decoder side to prepare decoding buffer. A plain char to two bytes encoding,
 * although is inefficient in compress rate comparing to UTF encoding, but it
 * significantly simplifies the encoder's logic, and output length is
 * predictable by just knowing the length of the String, so the decoder can
 * manage its decoding buffer very efficient.<br>
 * In average, any system should have more ASCII String usage than non-ASCII
 * String. All system internal String are ASCII String, only String holding user
 * input info may have non-ASCII character, this trade off should offer positive
 * performance affect in most cases, for other cases, developer should consider
 * to just use the {@link java.io.ObjectOutputStream}/
 * {@link java.io.DataOutputStream}.<p>
 *
 * For ordinary Object, all primary type wrappers are encoded into its raw value
 * with one byte type header. This is much more efficient than
 * {@link java.io.ObjectOutputStream}'s serialization format for primary type
 * wrappers.<br>
 * For String, it is outputed in the same way as
 * {@link #writeString(java.lang.String)}, but with one byte type header.<br>
 * For other serializable Object, each Object is serialized by a new
 * {@link java.io.ObjectOutputStream}, which means no reference handler can be
 * used across object serialization. This is on purpose to make each object is
 * isolated. Serializer is highly optimized for primary types, it is not good at
 * complex object serialization(compares to {@link java.io.ObjectOutputStream}).
 * At the beginning of object serialization, Serializer writes out the
 * ClassLoader mapping contextName(see
 * {@link com.liferay.portal.kernel.util.ClassLoaderPool}), so on Deserializer
 * side, it can use the right ClassLoader to deserialize the Object, this is an
 * important feature that {@link java.io.ObjectOutputStream}/
 * {@link java.io.ObjectInputStream} don't provide. For ClassLoader aware Object
 * serialize/deserialize(when plugin is involved),
 * {@link com.liferay.portal.kernel.io.Serializer}/
 * {@link com.liferay.portal.kernel.io.Deserializer} is a much better choice.
 *
 * @author Shuyang Zhou
 * @see Deserializer
 */
public class Serializer {

	public Serializer() {
		BufferQueue bufferQueue = bufferQueueThreadLocal.get();

		buffer = bufferQueue.dequeue();
	}

	public ByteBuffer toByteBuffer() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(Arrays.copyOf(buffer, index));

		if (buffer.length <= THREADLOCAL_BUFFER_SIZE_LIMIT) {
			BufferQueue bufferQueue = bufferQueueThreadLocal.get();

			bufferQueue.enqueue(buffer);
		}

		buffer = null;

		return byteBuffer;
	}

	public void writeBoolean(boolean b) {
		BigEndianCodec.putBoolean(getBuffer(1), index++, b);
	}

	public void writeByte(byte b) {
		getBuffer(1)[index++] = b;
	}

	public void writeChar(char c) {
		BigEndianCodec.putChar(getBuffer(2), index, c);

		index += 2;
	}

	public void writeDouble(double d) {
		BigEndianCodec.putDouble(getBuffer(8), index, d);

		index += 8;
	}

	public void writeFloat(float f) {
		BigEndianCodec.putFloat(getBuffer(4), index, f);

		index += 4;
	}

	public void writeInt(int i) {
		BigEndianCodec.putInt(getBuffer(4), index, i);

		index += 4;
	}

	public void writeLong(long l) {
		BigEndianCodec.putLong(getBuffer(8), index, l);

		index += 8;
	}

	public void writeObject(Serializable serializable) {
		// test ordered by frequency, don't change
		if (serializable == null) {
			writeByte(SerializationConstants.TC_NULL);

			return;
		}
		else if (serializable instanceof Long) {
			writeByte(SerializationConstants.TC_LONG);
			writeLong((Long)serializable);

			return;
		}
		else if (serializable instanceof String) {
			writeByte(SerializationConstants.TC_STRING);
			writeString((String)serializable);

			return;
		}
		else if (serializable instanceof Integer) {
			writeByte(SerializationConstants.TC_INTEGER);
			writeInt((Integer)serializable);

			return;
		}
		else if (serializable instanceof Boolean) {
			writeByte(SerializationConstants.TC_BOOLEAN);
			writeBoolean((Boolean)serializable);

			return;
		}
		else if (serializable instanceof Short) {
			writeByte(SerializationConstants.TC_SHORT);
			writeShort((Short)serializable);

			return;
		}
		else if (serializable instanceof Character) {
			writeByte(SerializationConstants.TC_CHARACTER);
			writeChar((Character)serializable);

			return;
		}
		else if (serializable instanceof Byte) {
			writeByte(SerializationConstants.TC_BYTE);
			writeByte((Byte)serializable);

			return;
		}
		else if (serializable instanceof Double) {
			writeByte(SerializationConstants.TC_DOUBLE);
			writeDouble((Double)serializable);

			return;
		}
		else if (serializable instanceof Float) {
			writeByte(SerializationConstants.TC_FLOAT);
			writeFloat((Float)serializable);

			return;
		}
		else {
			writeByte(SerializationConstants.TC_CONTEXT_NAME);
		}

		ClassLoader classLoader = serializable.getClass().getClassLoader();

		String contextName = ClassLoaderPool.getContextNameByClassLoader(
			classLoader);

		writeString(contextName);

		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new BufferOutputStream());

			objectOutputStream.writeObject(serializable);

			objectOutputStream.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Failed to write ordinary Serializable object " + serializable,
				ioe);
		}
	}

	public void writeShort(short s) {
		BigEndianCodec.putShort(getBuffer(2), index, s);

		index += 2;
	}

	public void writeString(String s) {
		int length = s.length();

		boolean asciiCode = true;

		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);

			if ((c == 0) || (c > 127)) {
				asciiCode = false;
				break;
			}
		}

		BigEndianCodec.putBoolean(buffer, index++, asciiCode);

		if (asciiCode) {
			byte[] buffer = getBuffer(length + 4);

			BigEndianCodec.putInt(buffer, index, length);

			index += 4;

			for (int i = 0; i < length; i++) {
				char c = s.charAt(i);

				buffer[index++] = (byte)c;
			}
		}
		else {
			byte[] buffer = getBuffer(length * 2 + 4);

			BigEndianCodec.putInt(buffer, index, length);

			index += 4;

			for (int i = 0; i < length; i++) {
				char c = s.charAt(i);

				BigEndianCodec.putChar(buffer, index, c);

				index += 2;
			}
		}
	}

	public void writeTo(OutputStream outputStream) throws IOException {
		outputStream.write(buffer, 0, index);

		if (buffer.length <= THREADLOCAL_BUFFER_SIZE_LIMIT) {
			BufferQueue bufferQueue = bufferQueueThreadLocal.get();

			bufferQueue.enqueue(buffer);
		}

		buffer = null;
	}

	// Keep this final so that JIT can inline this method
	protected final byte[] getBuffer(int ensureExtraSpace) {
		int minSize = index + ensureExtraSpace;

		if (minSize < 0) {
			// Can not create byte[] with length longer than Integer.MAX_VALUE
			throw new OutOfMemoryError();
		}

		int oldSize = buffer.length;

		if (minSize > oldSize) {
			int newSize = oldSize << 1;

			if (newSize < minSize) {
				// Overflow and insufficient grow protection
				newSize = minSize;
			}

			buffer = Arrays.copyOf(buffer, newSize);
		}

		return buffer;
	}

	/**
	 * Coarse-grained pooled buffer memory soften. Technically, we should soften
	 * each pooled buffer individually, to achieve best GC interactive. However
	 * that will increase complexity of pooled buffer accessing and also add
	 * burden to GC SoftReference processing, which will harm performance. Here
	 * we soften the entire ThreadLocal BufferQueue, for threads that do
	 * serializing oftenly, most likely its BufferQueue will stay valid, for
	 * threads that do serializing occasionally, most likely its BufferQueue
	 * will be released by GC.
	 */
	protected static final ThreadLocal<BufferQueue> bufferQueueThreadLocal =
		new SoftReferenceThreadLocal<BufferQueue>() {

		@Override
		protected BufferQueue initialValue() {
			return new BufferQueue();
		}

	};
	protected static final int MIN_THREADLOCAL_BUFFER_COUNT = 8;
	protected static final int MIN_THREADLOCAL_BUFFER_SIZE = 16 * 1024;
	protected static final int THREADLOCAL_BUFFER_COUNT_LIMIT;
	protected static final int THREADLOCAL_BUFFER_SIZE_LIMIT;

	protected byte[] buffer;
	protected int index;

	protected static class BufferNode {

		public BufferNode(byte[] buffer) {
			this.buffer = buffer;
		}

		protected byte[] buffer;
		protected BufferNode next;

	}

	/**
	 * A descending ordered byte[] queue by array length. The queue is small
	 * enough to simply use a linear scan search for maintaining order.
	 * The entire queue data is held by a SoftReference, so when necessary GC
	 * can release the whole buffer cache.
	 */
	protected static class BufferQueue {

		public void enqueue(byte[] buffer) {
			BufferNode bufferNode = new BufferNode(buffer);

			if (head == null) {
				// Empty queue
				head = bufferNode;
				count = 1;

				return;
			}

			BufferNode previous = null;
			BufferNode current = head;

			while ((current != null) &&
				(current.buffer.length > bufferNode.buffer.length)) {

				previous = current;
				current = current.next;
			}

			if (previous == null) {
				// Insert to head
				bufferNode.next = head;
				head = bufferNode;
			}
			else {
				// Insert
				bufferNode.next = current;
				previous.next = bufferNode;
			}

			if (++count > THREADLOCAL_BUFFER_COUNT_LIMIT) {
				// release the last smallest buffer
				if (previous == null) {
					previous = head;
				}

				current = previous.next;

				while (current.next != null) {
					previous = current;
					current = current.next;
				}

				// Dereference
				previous.next = null;

				// Help GC
				current.buffer = null;
				current.next = null;
			}
		}

		public byte[] dequeue() {
			if (head == null) {
				// Empty queue
				return new byte[MIN_THREADLOCAL_BUFFER_SIZE];
			}

			BufferNode bufferNode = head;

			head = head.next;

			// Help GC
			bufferNode.next = null;

			return bufferNode.buffer;
		}

		protected int count;
		protected BufferNode head;

	}

	protected class BufferOutputStream extends OutputStream {

		@Override
		public void write(byte[] bytes) {
			write(bytes, 0, bytes.length);
		}

		@Override
		public void write(byte[] bytes, int offset, int length) {
			byte[] buffer = getBuffer(length);

			System.arraycopy(bytes, offset, buffer, index, length);

			index += length;
		}

		@Override
		public void write(int b) {
			getBuffer(1)[index++] = (byte)b;
		}

	}

	static {
		int threadLocalBufferCountLimit = GetterUtil.getInteger(
			System.getProperty(
				Serializer.class.getName() +
			".threadlocal.buffer.count.limit"));

		if (threadLocalBufferCountLimit < MIN_THREADLOCAL_BUFFER_COUNT) {
			threadLocalBufferCountLimit = MIN_THREADLOCAL_BUFFER_COUNT;
		}

		THREADLOCAL_BUFFER_COUNT_LIMIT = threadLocalBufferCountLimit;

		int threadLocalBufferSizeLimit = GetterUtil.getInteger(
			System.getProperty(
				Serializer.class.getName() +
			".threadlocal.buffer.size.limit"));

		if (threadLocalBufferSizeLimit < MIN_THREADLOCAL_BUFFER_SIZE) {
			threadLocalBufferSizeLimit = MIN_THREADLOCAL_BUFFER_SIZE;
		}

		THREADLOCAL_BUFFER_SIZE_LIMIT = threadLocalBufferSizeLimit;
	}

}