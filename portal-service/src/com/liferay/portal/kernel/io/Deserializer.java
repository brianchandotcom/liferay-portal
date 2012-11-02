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

import com.liferay.portal.kernel.util.CharBufferPool;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ClassResolverUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

/**
 * Deserializes data in a ClassLoader-aware manner. This class is the
 * counterpart of {@link Serializer} for deserialization.
 *
 * @author Shuyang Zhou
 * @see    Serializer
 */
public class Deserializer {

	public Deserializer(ByteBuffer byteBuffer) {
		buffer = byteBuffer.array();
		index = byteBuffer.arrayOffset();
		limit = index + byteBuffer.remaining();
	}

	public boolean readBoolean() {
		detectBufferUnderflow(1);

		return BigEndianCodec.getBoolean(buffer, index++);
	}

	public byte readByte() {
		detectBufferUnderflow(1);

		return buffer[index++];
	}

	public char readChar() {
		detectBufferUnderflow(2);

		char c = BigEndianCodec.getChar(buffer, index);

		index += 2;

		return c;
	}

	public double readDouble() {
		detectBufferUnderflow(8);

		double d = BigEndianCodec.getDouble(buffer, index);

		index += 8;

		return d;
	}

	public float readFloat() {
		detectBufferUnderflow(4);

		float f = BigEndianCodec.getFloat(buffer, index);

		index += 4;

		return f;
	}

	public int readInt() {
		detectBufferUnderflow(4);

		int i = BigEndianCodec.getInt(buffer, index);

		index += 4;

		return i;
	}

	public long readLong() {
		detectBufferUnderflow(8);

		long l = BigEndianCodec.getLong(buffer, index);

		index += 8;

		return l;
	}

	public <T extends Serializable> T readObject()
		throws ClassNotFoundException {

		byte tcByte = buffer[index++];

		switch (tcByte) {
			case SerializationConstants.TC_BOOLEAN:
				return (T)Boolean.valueOf(readBoolean());

			case SerializationConstants.TC_BYTE:
				return (T)Byte.valueOf(readByte());

			case SerializationConstants.TC_CHARACTER:
				return (T)Character.valueOf(readChar());

			case SerializationConstants.TC_CLASS:
				String contextName = readString();
				String className = readString();

				ClassLoader classLoader = ClassLoaderPool.getClassLoader(
					contextName);

				return (T)ClassResolverUtil.resolve(className, classLoader);

			case SerializationConstants.TC_DOUBLE:
				return (T)Double.valueOf(readDouble());

			case SerializationConstants.TC_FLOAT:
				return (T)Float.valueOf(readFloat());

			case SerializationConstants.TC_INTEGER:
				return (T)Integer.valueOf(readInt());

			case SerializationConstants.TC_LONG:
				return (T)Long.valueOf(readLong());

			case SerializationConstants.TC_NULL:
				return null;

			case SerializationConstants.TC_SHORT:
				return (T)Short.valueOf(readShort());

			case SerializationConstants.TC_STRING:
				return (T)readString();

			case SerializationConstants.TC_OBJECT:
				try {
					ObjectInputStream objectInpputStream =
						new AnnotatedObjectInputStream(new BufferInputStream());

					T t = (T)objectInpputStream.readObject();

					objectInpputStream.close();

					return t;
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}

			default :
				throw new IllegalStateException("Unkown TC code " + tcByte);
		}
	}

	public short readShort() {
		detectBufferUnderflow(2);

		short s = BigEndianCodec.getShort(buffer, index);

		index += 2;

		return s;
	}

	public String readString() {
		detectBufferUnderflow(5);

		boolean asciiCode = BigEndianCodec.getBoolean(buffer, index++);

		int length = BigEndianCodec.getInt(buffer, index);

		index += 4;

		if (asciiCode) {
			detectBufferUnderflow(length);

			char[] chars = CharBufferPool.borrow(length);

			for (int i = 0; i < length; i++) {
				chars[i] = (char)buffer[index++];
			}

			return new String(chars, 0, length);
		}
		else {
			detectBufferUnderflow(length * 2);

			char[] chars = CharBufferPool.borrow(length);

			for (int i = 0; i < length; i++) {
				chars[i] = BigEndianCodec.getChar(buffer, index);

				index += 2;
			}

			return new String(chars, 0, length);
		}
	}

	/**
	 * Detects a buffer underflow throwing an {@link
	 * java.lang.IllegalStateException} if the input data is shorter than the
	 * reserved space. This method is final so JIT can perform an inline
	 * expansion.
	 *
	 * @param availableBytes number of bytes available in input buffer
	 */
	protected final void detectBufferUnderflow(int availableBytes) {
		if ((index + availableBytes) > limit) {
			throw new IllegalStateException("Buffer underflow");
		}
	}

	protected byte[] buffer;
	protected int index;
	protected int limit;

	protected class BufferInputStream extends InputStream {

		@Override
		public int read() {
			return buffer[index++];
		}

		@Override
		public int read(byte[] bytes) {
			return read(bytes, 0, bytes.length);
		}

		@Override
		public int read(byte[] bytes, int offset, int length) {
			int remain = limit - index;

			if (remain < length) {
				length = remain;
			}

			System.arraycopy(buffer, index, bytes, offset, length);

			index += length;

			return length;
		}

	}

}