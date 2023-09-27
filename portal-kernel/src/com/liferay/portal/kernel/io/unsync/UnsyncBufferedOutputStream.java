/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStream extends UnsyncFilterOutputStream {

	public UnsyncBufferedOutputStream(OutputStream outputStream) {
		this(outputStream, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedOutputStream(OutputStream outputStream, int size) {
		super(outputStream);

		if (size <= 0) {
			throw new IllegalArgumentException("Size is less than 0");
		}

		buffer = new byte[size];
	}

	@Override
	public void flush() throws IOException {
		if (count > 0) {
			outputStream.write(buffer, 0, count);

			count = 0;
		}

		outputStream.flush();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		write(bytes, 0, bytes.length);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		if (length >= buffer.length) {
			if (count > 0) {
				outputStream.write(buffer, 0, count);

				count = 0;
			}

			outputStream.write(bytes, offset, length);

			return;
		}

		if ((count > 0) && (length > (buffer.length - count))) {
			outputStream.write(buffer, 0, count);

			count = 0;
		}

		System.arraycopy(bytes, offset, buffer, count, length);

		count += length;
	}

	@Override
	public void write(int b) throws IOException {
		if (count >= buffer.length) {
			outputStream.write(buffer, 0, count);

			count = 0;
		}

		buffer[count++] = (byte)b;
	}

	protected byte[] buffer;
	protected int count;

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

}