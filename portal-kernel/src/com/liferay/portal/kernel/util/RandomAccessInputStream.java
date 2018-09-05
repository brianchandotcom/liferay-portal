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

package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * This class enables any {@link InputStream} to be seekable by caching its data
 * in a temporary {@link RandomAccessFile}.
 *
 * @author Juan González
 */
public class RandomAccessInputStream extends InputStream {

	public RandomAccessInputStream(InputStream inputStream) throws IOException {
		_inputStream = inputStream;

		_file = FileUtil.createTempFile();

		_randomAccessFileCache = new RandomAccessFile(_file, "rw");
	}

	@Override
	public void close() throws IOException {
		super.close();

		_randomAccessFileCache.close();

		FileUtil.delete(_file);
	}

	@Override
	public synchronized void mark(int readLimit) {
		_markPosition = _pointer;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		long next = _pointer + 1;

		long position = readUntil(next);

		if (position >= next) {
			_randomAccessFileCache.seek(_pointer++);

			return _randomAccessFileCache.read();
		}

		return -1;
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		if (bytes == null) {
			throw new NullPointerException();
		}

		if ((offset < 0) || (length < 0) ||
			((offset + length) > bytes.length)) {

			throw new IndexOutOfBoundsException();
		}

		if (length == 0) {
			return 0;
		}

		long position = readUntil(_pointer + length);

		length = (int)Math.min(length, position - _pointer);

		if (length > 0) {
			_randomAccessFileCache.seek(_pointer);

			_randomAccessFileCache.readFully(bytes, offset, length);

			_pointer += length;

			return length;
		}

		return -1;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (_markPosition != -1) {
			seek(_markPosition);
		}
	}

	public void seek(long position) throws IOException {
		if (position < 0) {
			throw new IOException("Error while seeking stream");
		}

		_pointer = position;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		close();
	}

	protected long readUntil(long position) throws IOException {
		if (position < _length) {
			return position;
		}

		if (_foundEOF) {
			return _length;
		}

		long lengthToRead = position - _length;

		_randomAccessFileCache.seek(_length);

		byte[] buffer = new byte[1024];

		while (lengthToRead > 0) {
			int bytesRead = _inputStream.read(
				buffer, 0, (int)Math.min(lengthToRead, buffer.length));

			if (bytesRead == -1) {
				_foundEOF = true;

				return _length;
			}

			_randomAccessFileCache.setLength(
				_randomAccessFileCache.length() + bytesRead);

			_randomAccessFileCache.write(buffer, 0, bytesRead);

			lengthToRead -= bytesRead;

			_length += bytesRead;
		}

		return position;
	}

	private final File _file;
	private boolean _foundEOF;
	private final InputStream _inputStream;
	private long _length;
	private long _markPosition = -1;
	private long _pointer;
	private final RandomAccessFile _randomAccessFileCache;

}