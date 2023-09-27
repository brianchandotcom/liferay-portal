/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncFilterInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncFilterOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.channels.FileChannel;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class StreamUtil {

	public static final int BUFFER_SIZE = GetterUtil.getInteger(
		SystemProperties.get(StreamUtil.class.getName() + ".buffer.size"),
		8192);

	public static final boolean FORCE_TIO = GetterUtil.getBoolean(
		SystemProperties.get(StreamUtil.class.getName() + ".force.tio"));

	public static void cleanUp(boolean quiet, Closeable... closeables) {
		IOException ioException1 = null;

		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				}
				catch (IOException ioException2) {
					if (ioException1 == null) {
						ioException1 = ioException2;
					}
					else {
						ioException1.addSuppressed(ioException2);
					}
				}
			}
		}

		if (ioException1 == null) {
			return;
		}

		if (quiet) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException1);
			}
		}
		else {
			ReflectionUtil.throwException(ioException1);
		}
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream)
		throws IOException {

		transfer(inputStream, outputStream, BUFFER_SIZE, true);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, boolean cleanUp)
		throws IOException {

		transfer(inputStream, outputStream, BUFFER_SIZE, cleanUp);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, int bufferSize)
		throws IOException {

		transfer(inputStream, outputStream, bufferSize, true);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, int bufferSize,
			boolean cleanUp)
		throws IOException {

		transfer(inputStream, outputStream, bufferSize, cleanUp, 0);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, int bufferSize,
			boolean cleanUp, long length)
		throws IOException {

		if (inputStream == null) {
			throw new IllegalArgumentException("Input stream is null");
		}

		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream is null");
		}

		if (bufferSize <= 0) {
			bufferSize = BUFFER_SIZE;
		}

		try {
			if (!FORCE_TIO && (inputStream instanceof FileInputStream) &&
				(outputStream instanceof FileOutputStream)) {

				FileInputStream fileInputStream = (FileInputStream)inputStream;
				FileOutputStream fileOutputStream =
					(FileOutputStream)outputStream;

				transferFileChannel(
					fileInputStream.getChannel(), fileOutputStream.getChannel(),
					length);
			}
			else {
				transferByteArray(
					inputStream, outputStream, bufferSize, length);
			}
		}
		finally {
			if (cleanUp) {
				cleanUp(false, inputStream, outputStream);
			}
		}
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, long length)
		throws IOException {

		transfer(inputStream, outputStream, BUFFER_SIZE, true, length);
	}

	public static InputStream uncloseable(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}

		return new UnsyncFilterInputStream(inputStream) {

			@Override
			public void close() {
			}

		};
	}

	public static OutputStream uncloseable(OutputStream outputStream) {
		if (outputStream == null) {
			return null;
		}

		return new UnsyncFilterOutputStream(outputStream) {

			@Override
			public void close() {
			}

		};
	}

	protected static void transferByteArray(
			InputStream inputStream, OutputStream outputStream, int bufferSize,
			long length)
		throws IOException {

		byte[] bytes = new byte[bufferSize];

		if (length > 0) {
			long remainingLength = length;

			while (remainingLength > 0) {
				int readBytes = inputStream.read(
					bytes, 0, (int)Math.min(remainingLength, bufferSize));

				if (readBytes == -1) {
					break;
				}

				outputStream.write(bytes, 0, readBytes);

				remainingLength -= readBytes;
			}
		}
		else {
			int value = -1;

			while ((value = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, value);
			}
		}
	}

	protected static void transferFileChannel(
			FileChannel inputFileChannel, FileChannel outputFileChannel,
			long length)
		throws IOException {

		if (length <= 0) {
			length = inputFileChannel.size() - inputFileChannel.position();
		}

		long count = 0;

		while (count < length) {
			count += inputFileChannel.transferTo(
				inputFileChannel.position() + count, length - count,
				outputFileChannel);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(StreamUtil.class);

}