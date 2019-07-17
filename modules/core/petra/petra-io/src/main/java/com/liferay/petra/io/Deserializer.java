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

package com.liferay.petra.io;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.lang.ClassResolverUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Deserializes data in a ClassLoader-aware manner. This class is the
 * counterpart of {@link Serializer} for deserialization.
 *
 * @author Shuyang Zhou
 * @see    Serializer
 */
public class Deserializer {

	public Deserializer(ByteBuffer byteBuffer) {
		_buffer = byteBuffer.array();

		_index = byteBuffer.arrayOffset();

		_limit = _index + byteBuffer.remaining();
	}

	public boolean readBoolean() {
		_detectBufferUnderflow(1);

		return BigEndianCodec.getBoolean(_buffer, _index++);
	}

	public byte readByte() {
		_detectBufferUnderflow(1);

		return _buffer[_index++];
	}

	public char readChar() {
		_detectBufferUnderflow(2);

		char c = BigEndianCodec.getChar(_buffer, _index);

		_index += 2;

		return c;
	}

	public double readDouble() {
		_detectBufferUnderflow(8);

		double d = BigEndianCodec.getDouble(_buffer, _index);

		_index += 8;

		return d;
	}

	public float readFloat() {
		_detectBufferUnderflow(4);

		float f = BigEndianCodec.getFloat(_buffer, _index);

		_index += 4;

		return f;
	}

	public int readInt() {
		_detectBufferUnderflow(4);

		int i = BigEndianCodec.getInt(_buffer, _index);

		_index += 4;

		return i;
	}

	public long readLong() {
		_detectBufferUnderflow(8);

		long l = BigEndianCodec.getLong(_buffer, _index);

		_index += 8;

		return l;
	}

	public <T extends Serializable> T readObject()
		throws ClassNotFoundException {

		byte tcByte = _buffer[_index++];

		if (tcByte == SerializationConstants.TC_BOOLEAN) {
			return (T)Boolean.valueOf(readBoolean());
		}
		else if (tcByte == SerializationConstants.TC_BYTE) {
			return (T)Byte.valueOf(readByte());
		}
		else if (tcByte == SerializationConstants.TC_CHARACTER) {
			return (T)Character.valueOf(readChar());
		}
		else if (tcByte == SerializationConstants.TC_CLASS) {
			String contextName = readString();
			String className = readString();

			return (T)ClassResolverUtil.resolve(
				className, ClassLoaderPool.getClassLoader(contextName));
		}
		else if (tcByte == SerializationConstants.TC_DOUBLE) {
			return (T)Double.valueOf(readDouble());
		}
		else if (tcByte == SerializationConstants.TC_FLOAT) {
			return (T)Float.valueOf(readFloat());
		}
		else if (tcByte == SerializationConstants.TC_INTEGER) {
			return (T)Integer.valueOf(readInt());
		}
		else if (tcByte == SerializationConstants.TC_LONG) {
			return (T)Long.valueOf(readLong());
		}
		else if (tcByte == SerializationConstants.TC_NULL) {
			return null;
		}
		else if (tcByte == SerializationConstants.TC_SHORT) {
			return (T)Short.valueOf(readShort());
		}
		else if (tcByte == SerializationConstants.TC_STRING) {
			return (T)readString();
		}
		else if (tcByte == SerializationConstants.TC_OBJECT) {
			try {
				ObjectInputStream objectInputStream =
					new ProtectedAnnotatedObjectInputStream(
						new BufferInputStream());

				return (T)objectInputStream.readObject();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		throw new IllegalStateException("Unkown TC code " + tcByte);
	}

	public short readShort() {
		_detectBufferUnderflow(2);

		short s = BigEndianCodec.getShort(_buffer, _index);

		_index += 2;

		return s;
	}

	public String readString() {
		_detectBufferUnderflow(5);

		boolean asciiCode = BigEndianCodec.getBoolean(_buffer, _index++);

		int length = BigEndianCodec.getInt(_buffer, _index);

		_index += 4;

		if (asciiCode) {
			_detectBufferUnderflow(length);

			String s = new String(_buffer, _index, length);

			_index += length;

			return s;
		}

		length <<= 1;

		_detectBufferUnderflow(length);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_buffer, _index, length);

		_index += length;

		CharBuffer charBuffer = byteBuffer.asCharBuffer();

		return charBuffer.toString();
	}

	/**
	 * Detects a buffer underflow throwing an {@link IllegalStateException} if
	 * the input data is shorter than the reserved space. This method is final
	 * so JIT can perform an inline expansion.
	 *
	 * @param availableBytes number of bytes available in input buffer
	 */
	private void _detectBufferUnderflow(int availableBytes) {
		if ((_index + availableBytes) > _limit) {
			throw new IllegalStateException("Buffer underflow");
		}
	}

	private final byte[] _buffer;
	private int _index;
	private final int _limit;

	private class BufferInputStream extends InputStream {

		@Override
		public int read() {
			return _buffer[_index++];
		}

		@Override
		public int read(byte[] bytes) {
			return read(bytes, 0, bytes.length);
		}

		@Override
		public int read(byte[] bytes, int offset, int length) {
			int remain = _limit - _index;

			if (remain < length) {
				length = remain;
			}

			System.arraycopy(_buffer, _index, bytes, offset, length);

			_index += length;

			return length;
		}

	}

}