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

		String contextName = ClassLoaderPool.getContextName(classLoader);

		writeString(contextName);

		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new BufferOutputStream());

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
	 * This method is final so that JIT can inline it.
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