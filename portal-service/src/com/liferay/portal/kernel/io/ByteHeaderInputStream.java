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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wraps an input stream to include a header, overriding the input stream's read
 * methods to flush the header buffer before delegating read operations to the
 * underlying input stream.
 *
 * @author Tomas Polesovsky
 */
public class ByteHeaderInputStream extends FilterInputStream {

	/**
	 * Constructs a byte header input stream with the header and input stream.
	 *
	 * @param inputStream the input stream to be wrapped
	 * @param header bytes to be read as a header before the input stream
	 */
	public ByteHeaderInputStream(InputStream inputStream, byte[] header) {
		super(inputStream);

		_header = header;
	}

	/**
	 * Returns the next byte available from the header or the next byte read
	 * from the wrapped input stream.
	 *
	 * @return the next byte available from the header, or the next byte read
	 *         from the wrapped input stream
	 * @throws IOException if an IO exception occurred reading from the wrapped
	 *         input stream
	 */
	@Override
	public int read() throws IOException {
		if (_header == null) {
			return super.read();
		}

		if ((_headerPos + 1) < _header.length) {
			_headerPos++;

			return _header[_headerPos - 1];
		}

		return super.read();
	}

	/**
	 * Reads from the header or from the wrapped input stream, filling up the
	 * destination buffer.
	 *
	 * <p>
	 * If the header hasn't been completely flushed, the header is written into
	 * the destination buffer. Otherwise the bytes are read from the wrapped
	 * input stream.
	 * </p>
	 *
	 * @param  bytes the destination buffer into which the data is read
	 * @return the total number of bytes read into the buffer, or
	 *         <code>-1</code> if there is no more data to be read given the end
	 *         of the stream has been reached
	 * @throws IOException if an IO exception occurred reading from the wrapped
	 *         input stream
	 */
	@Override
	public int read(byte[] bytes) throws IOException {
		return read(bytes, 0, bytes.length);
	}

	/**
	 * Reads up to <code>len</code> bytes from the header or from the wrapped
	 * input stream, filling up the destination buffer starting at the specified
	 * offset.
	 *
	 * <p>
	 * If the header hasn't been completely flushed, the header is written into
	 * the destination buffer. Otherwise the bytes are read from the wrapped
	 * input stream.
	 * </p>
	 *
	 * @param  bytes the destination buffer into which the data is read
	 * @param  off the offset or starting position to write into the destination
	 *         buffer
	 * @param  len the maximum number of bytes to read
	 * @return the total number of bytes read into the buffer, or
	 *         <code>-1</code> if there is no more data to be read given the end
	 *         of the stream has been reached
	 * @throws IOException if an IO exception occurred reading from the wrapped
	 *         input stream
	 */
	@Override
	public int read(byte[] bytes, int off, int len) throws IOException {
		if (_header == null) {
			return super.read(bytes, off, len);
		}

		if (_headerPos < _header.length) {
			if (bytes == null) {
				throw new NullPointerException();
			}
			else if (off < 0 || len < 0 || len > bytes.length - off) {
				throw new IndexOutOfBoundsException();
			}
			else if (len == 0) {
				return 0;
			}

			int remainingBytes = _header.length - _headerPos;

			int bytesToWrite = Math.min(remainingBytes, len);

			System.arraycopy(_header, _headerPos, bytes, off, bytesToWrite);

			_headerPos += bytesToWrite;

			return bytesToWrite;
		}

		return super.read(bytes, off, len);
	}

	private byte[] _header;
	private int _headerPos = 0;

}