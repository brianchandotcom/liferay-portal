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
 * Serializes data in a ClassLoader-aware manner.
 *
 * <p>
 * The Serializer can perform better than {@link java.io.ObjectOutputStream} and
 * {@link java.io.DataOutputStream}, with respect to encoding primary types,
 * because it uses a more compact format (containing no BlockHeader) and simpler
 * call stack involving {@link BigEndianCodec}, as compared to using an
 * OutputStream wrapper on top of {@link java.io.Bits}.
 * </p>
 *
 * <p>
 * For Strings, the UTF encoding for ObjectOutputStream and DataOutputStream has
 * a 2^16=64K length limitation, which is often too restrictive. Serializer has
 * a 2^32=4G String length limitation, which is generally more than enough. For
 * pure ASCII character Strings, the encoding performance is almost the same, if
 * not better, than ObjectOutputStream and DataOutputStream. For Strings
 * containing non-ASCII characters, the Serializer encodes each char to two
 * bytes rather than performing UTF encoding. There is a trade-off between
 * CPU/memory performance and compression rate.
 * </p>
 *
 * <p>
 * UTF encoding uses more CPU cycles to detect the unicode range for each char
 * and the resulting output is variable length, which increases the memory
 * burden when preparing the decoding buffer. Whereas, encoding each char to two
 * bytes allows for better CPU/memory performance. Although inefficient with
 * compression rates in comparison to UTF encoding, the char to two byte
 * approach significantly simplifies the encoder's logic and the output length
 * is predictably based on the length of the String, so the decoder can manage
 * its decoding buffer efficiently. On average, a system uses more ASCII String
 * scheming than non-ASCII String scheming. In most cases, when all system
 * internal Strings are ASCII Strings and only Strings holding user input
 * information can have non-ASCII characters, this Serializer performs best. In
 * other cases, developers should consider using {@link
 * java.io.ObjectOutputStream} or {@link java.io.DataOutputStream}.
 * </p>
 *
 * <p>
 * For ordinary Objects, all primary type wrappers are encoded to their raw
 * values with one byte type headers. This is much more efficient than
 * ObjectOutputStream's serialization format for primary type wrappers. Strings
 * are output in the same way as {@link #writeString(java.lang.String)}, but
 * also with one byte type headers. Objects are serialized by a new
 * ObjectOutputStream, so no reference handler can be used across Object
 * serialization. This is done intentionally to isolate each object. The
 * Serializer is highly optimized for serializing primary types, but is not as
 * good as ObjectOutputStream for serializing complex objects.
 * </p>
 *
 * <p>
 * On object serialization, the Serializer uses the {@link
 * com.liferay.portal.kernel.util.ClassLoaderPool} to look up the servlet
 * context name corresponding to the object's ClassLoader. The servlet context
 * name is written to the serialization stream. On object deserialization, the
 * {@link Deserializer} uses the ClassLoaderPool to look up the ClassLoader
 * corresponding to the servlet context name read from the deserialization
 * stream. ObjectOutputStream and ObjectInputStream lack these features, making
 * Serializer and Deserializer better choices for ClassLoader-aware Object
 * serialization/deserialization, especially when plugins are involved.
 * </p>
 *
 * @author Shuyang Zhou
 * @see    Deserializer
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

		// The if block is ordered by frequency for better performance

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
		else if (serializable instanceof Class) {
			Class<?> clazz = (Class<?>)serializable;

			ClassLoader classLoader = clazz.getClassLoader();

			String contextName = ClassLoaderPool.getContextName(classLoader);

			writeByte(SerializationConstants.TC_CLASS);
			writeString(contextName);
			writeString(clazz.getName());

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
			writeByte(SerializationConstants.TC_OBJECT);
		}

		try {
			ObjectOutputStream objectOutputStream =
				new AnnotatedObjectOutputStream(new BufferOutputStream());

			objectOutputStream.writeObject(serializable);

			objectOutputStream.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to write ordinary serializable object " + serializable,
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

	/**
	 * Returns the required buffer length. This method is final so JIT can
	 * perform an inline expansion.
	 *
	 * @param  ensureExtraSpace the extra byte space required to meet the
	 *         buffer's minimum length
	 * @return the buffer value
	 */
	protected final byte[] getBuffer(int ensureExtraSpace) {
		int minSize = index + ensureExtraSpace;

		if (minSize < 0) {

			// Cannot create byte[] with length longer than Integer.MAX_VALUE

			throw new OutOfMemoryError();
		}

		int oldSize = buffer.length;

		if (minSize > oldSize) {
			int newSize = oldSize << 1;

			if (newSize < minSize) {

				// Overflow and insufficient growth protection

				newSize = minSize;
			}

			buffer = Arrays.copyOf(buffer, newSize);
		}

		return buffer;
	}

	/**
	 * Softens the local thread's pooled buffer memory.
	 *
	 * <p>
	 * Technically, we should soften each pooled buffer individually to achieve
	 * the best garbage collection (GC) interaction. However, that increases
	 * complexity of pooled buffer access and also burdens the GC's {@link
	 * java.lang.ref.SoftReference} process, hurting performance.
	 * </p>
	 *
	 * <p>
	 * Here, the entire ThreadLocal BufferQueue is softened. For threads that do
	 * serializing often, its BufferQueue will most likely stay valid. For
	 * threads that do serializing only occasionally, its BufferQueue will most
	 * likely be released by GC.
	 * </p>
	 */
	protected static final ThreadLocal<BufferQueue> bufferQueueThreadLocal =
		new SoftReferenceThreadLocal<BufferQueue>() {

		@Override
		protected BufferQueue initialValue() {
			return new BufferQueue();
		}

	};

	protected static final int THREADLOCAL_BUFFER_COUNT_LIMIT;

	protected static final int THREADLOCAL_BUFFER_COUNT_MIN = 8;

	protected static final int THREADLOCAL_BUFFER_SIZE_LIMIT;

	protected static final int THREADLOCAL_BUFFER_SIZE_MIN = 16 * 1024;

	static {
		int threadLocalBufferCountLimit = GetterUtil.getInteger(
			System.getProperty(
				Serializer.class.getName() +
					".thread.local.buffer.count.limit"));

		if (threadLocalBufferCountLimit < THREADLOCAL_BUFFER_COUNT_MIN) {
			threadLocalBufferCountLimit = THREADLOCAL_BUFFER_COUNT_MIN;
		}

		THREADLOCAL_BUFFER_COUNT_LIMIT = threadLocalBufferCountLimit;

		int threadLocalBufferSizeLimit = GetterUtil.getInteger(
			System.getProperty(
				Serializer.class.getName() +
					".thread.local.buffer.size.limit"));

		if (threadLocalBufferSizeLimit < THREADLOCAL_BUFFER_SIZE_MIN) {
			threadLocalBufferSizeLimit = THREADLOCAL_BUFFER_SIZE_MIN;
		}

		THREADLOCAL_BUFFER_SIZE_LIMIT = threadLocalBufferSizeLimit;
	}

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
	 * Represents a descending <code>byte[]</code> queue ordered by array length.
	 *
	 * <p>
	 * The queue is small enough to simply use a linear scan search for
	 * maintaining its order. The entire queue data is held by a
	 * {@link java.lang.ref.SoftReference}, so when necessary, GC can release the whole
	 * buffer cache.
	 * </p>
	 */
	protected static class BufferQueue {

		public void enqueue(byte[] buffer) {
			BufferNode bufferNode = new BufferNode(buffer);

			if (headBufferNode == null) {
				headBufferNode = bufferNode;

				count = 1;

				return;
			}

			BufferNode previousBufferNode = null;
			BufferNode currentBufferNode = headBufferNode;

			while ((currentBufferNode != null) &&
				   (currentBufferNode.buffer.length >
				   		bufferNode.buffer.length)) {

				previousBufferNode = currentBufferNode;

				currentBufferNode = currentBufferNode.next;
			}

			if (previousBufferNode == null) {
				bufferNode.next = headBufferNode;

				headBufferNode = bufferNode;
			}
			else {
				bufferNode.next = currentBufferNode;

				previousBufferNode.next = bufferNode;
			}

			if (++count > THREADLOCAL_BUFFER_COUNT_LIMIT) {
				if (previousBufferNode == null) {
					previousBufferNode = headBufferNode;
				}

				currentBufferNode = previousBufferNode.next;

				while (currentBufferNode.next != null) {
					previousBufferNode = currentBufferNode;
					currentBufferNode = currentBufferNode.next;
				}

				// Dereference

				previousBufferNode.next = null;

				// Help GC

				currentBufferNode.buffer = null;
				currentBufferNode.next = null;
			}
		}

		public byte[] dequeue() {
			if (headBufferNode == null) {
				return new byte[THREADLOCAL_BUFFER_SIZE_MIN];
			}

			BufferNode bufferNode = headBufferNode;

			headBufferNode = headBufferNode.next;

			// Help GC

			bufferNode.next = null;

			return bufferNode.buffer;
		}

		protected int count;
		protected BufferNode headBufferNode;

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

}