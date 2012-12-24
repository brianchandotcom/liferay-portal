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
 * Is able to prepend an array of bytes (header) so that it's read before the
 * wrapped InputStream.
 *
 * <p>
 * Class overrides read methods to flush the header buffer first and then
 * delegates reading operations to the original input stream.
 * </p>
 *
 * @author Tomas Polesovsky
 */
public class ByteHeaderInputStream extends FilterInputStream {

	/**
	 * Creates new instance of the stream.
	 *
	 * @param inputStream InputStream to be wrapped
	 * @param header bytes to be read before the inputStream
	 */
	public ByteHeaderInputStream(InputStream inputStream, byte[] header) {
		super(inputStream);

		_header = header;
	}

	/**
	 * Reads one byte from the header or delegates the call wrapped input stream
	 * to return the data.
	 *
	 * @return a next byte from the header or byte from <code>{@link
	 *         FilterInputStream#read()}</code>
	 * @throws IOException if the wrapped inputStream throws the exception
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
	 * Tries to fill the buffer with header or input stream content, method
	 * delegates the call to <code>this.read(bytes, 0, bytes.length)</code>.
	 *
	 * @param  bytes the buffer into which the data is read
	 * @return the total number of bytes read into the buffer, or
	 *         <code>-1</code> if there is no more data because the end of the
	 *         stream has been reached.
	 * @throws IOException if the wrapped inputStream throws the exception
	 */
	@Override
	public int read(byte[] bytes) throws IOException {
		return read(bytes, 0, bytes.length);
	}

	/**
	 * Reads up to the len bytes from header or underlying input stream.
	 *
	 * <p>
	 * If header hasn't been flushed yet, writes rest of the header into the
	 * destination array. Otherwise delegates call to the wrapped input stream.
	 * </p>
	 *
	 * @param  bytes the buffer into which the data is read
	 * @param  off the start offset in the destination array
	 * @param  len the maximum number of bytes read
	 * @return the total number of bytes read into the buffer, or
	 *         <code>-1</code> if there is no more data because the end of the
	 *         stream has been reached.
	 * @throws IOException if the wrapped inputStream throws the exception
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