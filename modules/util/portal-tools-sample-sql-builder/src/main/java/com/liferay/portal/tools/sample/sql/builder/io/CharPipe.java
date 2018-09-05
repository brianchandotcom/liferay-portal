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

package com.liferay.portal.tools.sample.sql.builder.io;

import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.CharBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class CharPipe {

	public CharPipe() {
		this(_DEFAULT_BUFFER_SIZE);
	}

	public CharPipe(int bufferSize) {
		buffer = new char[bufferSize];
		count = 0;
		readIndex = 0;
		writeIndex = 0;
	}

	public void close() {
		close(false);
	}

	public void close(boolean force) {
		_pipeWriter.close();

		if (force) {
			_pipeReader.close();
			buffer = null;
		}
		else {
			bufferLock.lock();

			finished = true;

			try {
				notEmpty.signalAll();
			}
			finally {
				bufferLock.unlock();
			}
		}
	}

	public Reader getReader() {
		return _pipeReader;
	}

	public Writer getWriter() {
		return _pipeWriter;
	}

	protected char[] buffer;
	protected Lock bufferLock = new ReentrantLock();
	protected int count;
	protected boolean finished;
	protected Condition notEmpty = bufferLock.newCondition();
	protected Condition notFull = bufferLock.newCondition();
	protected int readIndex;
	protected int writeIndex;

	private static final int _DEFAULT_BUFFER_SIZE = 1024 * 8;

	private final PipeReader _pipeReader = new PipeReader();
	private final PipeWriter _pipeWriter = new PipeWriter();

	private class PipeReader extends Reader {

		@Override
		public void close() {
			bufferLock.lock();

			try {
				_closed = true;

				notEmpty.signalAll();
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public void mark(int readAheadLimit) throws IOException {
			throw new IOException("Mark is not supported");
		}

		@Override
		public boolean markSupported() {
			return false;
		}

		@Override
		public int read() throws IOException {
			if (_closed) {
				throw new IOException("Stream closed");
			}

			bufferLock.lock();

			try {
				if (waitUntilNotEmpty()) {
					return -1;
				}

				char result = buffer[readIndex];

				_increaseReadIndex(1);

				return result;
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public int read(char[] chars) throws IOException {
			return read(chars, 0, chars.length);
		}

		@Override
		public int read(char[] chars, int offset, int length)
			throws IOException {

			if (_closed) {
				throw new IOException("Stream closed");
			}

			if (length <= 0) {
				return 0;
			}

			bufferLock.lock();

			try {
				if (waitUntilNotEmpty()) {
					return -1;
				}

				int read = length;

				if (length > count) {
					read = count;
				}

				if ((buffer.length - readIndex) >= read) {

					// One step read

					System.arraycopy(buffer, readIndex, chars, offset, read);
				}
				else {

					// Two step read

					int tailLength = buffer.length - readIndex;

					int headLength = read - tailLength;

					System.arraycopy(
						buffer, readIndex, chars, offset, tailLength);
					System.arraycopy(
						buffer, 0, chars, offset + tailLength, headLength);
				}

				_increaseReadIndex(read);

				return read;
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public int read(CharBuffer charBuffer) throws IOException {
			if (_closed) {
				throw new IOException("Stream closed");
			}

			int length = charBuffer.remaining();

			if (length <= 0) {
				return 0;
			}

			char[] tempBuffer = new char[length];

			int read = read(tempBuffer, 0, length);

			if (read > 0) {
				charBuffer.put(tempBuffer, 0, read);
			}

			return read;
		}

		@Override
		public boolean ready() throws IOException {
			if (_closed) {
				throw new IOException("Stream closed");
			}

			bufferLock.lock();

			try {
				if (count > 0) {
					return true;
				}

				return false;
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public void reset() throws IOException {
			throw new IOException("Reset is not supported");
		}

		@Override
		public long skip(long skip) throws IOException {
			if (skip < 0) {
				throw new IllegalArgumentException("Skip value is negative");
			}

			if (_closed) {
				throw new IOException("Stream closed");
			}

			int skipBufferSize = (int)Math.min(skip, _MAX_SKIP_BUFFER_SIZE);

			bufferLock.lock();

			try {
				if ((_skipBuffer == null) ||
					(_skipBuffer.length < skipBufferSize)) {

					_skipBuffer = new char[skipBufferSize];
				}

				long remaining = skip;

				while (remaining > 0) {
					int skipped = read(
						_skipBuffer, 0,
						(int)Math.min(remaining, skipBufferSize));

					if (skipped == -1) {
						break;
					}

					remaining -= skipped;
				}

				return skip - remaining;
			}
			finally {
				bufferLock.unlock();
			}
		}

		protected boolean waitUntilNotEmpty() throws IOException {
			while ((count == 0) && !finished) {
				notEmpty.awaitUninterruptibly();

				if (_closed) {
					throw new IOException("Stream closed");
				}
			}

			if ((count == 0) && finished) {
				return true;
			}

			return false;
		}

		private void _increaseReadIndex(int consumed) {
			readIndex += consumed;

			if (readIndex >= buffer.length) {
				readIndex -= buffer.length;
			}

			if (count == buffer.length) {
				notFull.signalAll();
			}

			count -= consumed;
		}

		private static final int _MAX_SKIP_BUFFER_SIZE = 8192;

		private volatile boolean _closed;
		private char[] _skipBuffer;

	}

	private class PipeWriter extends Writer {

		@Override
		public Writer append(char c) throws IOException {
			write(c);

			return this;
		}

		@Override
		public Writer append(CharSequence charSequence) throws IOException {
			String string = null;

			if (charSequence == null) {
				string = StringPool.NULL;
			}
			else {
				string = charSequence.toString();
			}

			write(string, 0, string.length());

			return this;
		}

		@Override
		public Writer append(CharSequence charSequence, int start, int end)
			throws IOException {

			String string = null;

			if (charSequence == null) {
				string = StringPool.NULL;
			}
			else {
				charSequence = charSequence.subSequence(start, end);

				string = charSequence.toString();
			}

			write(string, 0, string.length());

			return this;
		}

		@Override
		public void close() {
			bufferLock.lock();

			try {
				_closed = true;

				notFull.signalAll();
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public void flush() {
		}

		@Override
		public void write(char[] chars) throws IOException {
			write(chars, 0, chars.length);
		}

		@Override
		public void write(char[] chars, int offset, int length)
			throws IOException {

			if (_closed) {
				throw new IOException("Stream closed");
			}

			if (length <= 0) {
				return;
			}

			bufferLock.lock();

			try {
				int remaining = length;

				while (remaining > 0) {
					waitUntilNotFull();

					int write = remaining;

					if (remaining > (buffer.length - count)) {
						write = buffer.length - count;
					}

					int sourceBegin = offset + length - remaining;

					if ((buffer.length - writeIndex) >= write) {

						// One step write

						System.arraycopy(
							chars, sourceBegin, buffer, writeIndex, write);
					}
					else {

						// Two step write

						int tailLength = buffer.length - writeIndex;

						int headLength = write - tailLength;

						System.arraycopy(
							chars, sourceBegin, buffer, writeIndex, tailLength);
						System.arraycopy(
							chars, sourceBegin + tailLength, buffer, 0,
							headLength);
					}

					_increaseWriteIndex(write);

					remaining -= write;
				}
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public void write(int c) throws IOException {
			if (_closed) {
				throw new IOException("Stream closed");
			}

			bufferLock.lock();

			try {
				waitUntilNotFull();

				buffer[writeIndex] = (char)c;

				_increaseWriteIndex(1);
			}
			finally {
				bufferLock.unlock();
			}
		}

		@Override
		public void write(String string) throws IOException {
			write(string, 0, string.length());
		}

		@Override
		public void write(String string, int offset, int length)
			throws IOException {

			if (_closed) {
				throw new IOException("Stream closed");
			}

			if (length <= 0) {
				return;
			}

			bufferLock.lock();

			try {
				int remaining = length;

				while (remaining > 0) {
					waitUntilNotFull();

					int write = remaining;

					if (remaining > (buffer.length - count)) {
						write = buffer.length - count;
					}

					int sourceBegin = offset + length - remaining;

					if ((buffer.length - writeIndex) >= write) {

						// One step write

						string.getChars(
							sourceBegin, sourceBegin + write, buffer,
							writeIndex);
					}
					else {

						// Two step write

						int tailLength = buffer.length - writeIndex;

						int headLength = write - tailLength;

						string.getChars(
							sourceBegin, sourceBegin + tailLength, buffer,
							writeIndex);
						string.getChars(
							sourceBegin + tailLength,
							sourceBegin + tailLength + headLength, buffer, 0);
					}

					_increaseWriteIndex(write);

					remaining -= write;
				}
			}
			finally {
				bufferLock.unlock();
			}
		}

		protected void waitUntilNotFull() throws IOException {
			while (count == buffer.length) {
				notFull.awaitUninterruptibly();

				if (_closed) {
					throw new IOException("Stream closed");
				}
			}
		}

		private void _increaseWriteIndex(int produced) {
			writeIndex += produced;

			if (writeIndex >= buffer.length) {
				writeIndex -= buffer.length;
			}

			if (count == 0) {
				notEmpty.signalAll();
			}

			count += produced;
		}

		private volatile boolean _closed;

	}

}