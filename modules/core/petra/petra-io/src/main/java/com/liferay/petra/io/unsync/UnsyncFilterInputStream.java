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

package com.liferay.petra.io.unsync;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncFilterInputStream extends InputStream {

	public UnsyncFilterInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public int available() throws IOException {
		return inputStream.available();
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}

	@Override
	public void mark(int readLimit) {
		inputStream.mark(readLimit);
	}

	@Override
	public boolean markSupported() {
		return inputStream.markSupported();
	}

	@Override
	public int read() throws IOException {
		return inputStream.read();
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		return read(bytes, 0, bytes.length);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		return inputStream.read(bytes, offset, length);
	}

	@Override
	public void reset() throws IOException {
		inputStream.reset();
	}

	@Override
	public long skip(long skip) throws IOException {
		return inputStream.skip(skip);
	}

	protected InputStream inputStream;

}