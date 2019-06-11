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

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.channels.FileChannel;

/**
 * @author Shuyang Zhou
 */
public class StreamUtil {

	public static final int BUFFER_SIZE = Integer.getInteger(
		StreamUtil.class.getName() + ".buffer.size", 8192);

	public static final boolean FORCE_TIO = Boolean.getBoolean(
		StreamUtil.class.getName() + ".force.tio");

	public static void cleanUp(Closeable... closeables) throws IOException {
		IOException ioException = null;

		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				}
				catch (IOException ioe) {
					if (ioException == null) {
						ioException = ioe;
					}
					else {
						ioException.addSuppressed(ioe);
					}
				}
			}
		}

		if (ioException == null) {
			return;
		}

		throw ioException;
	}

	public static byte[] toByteArray(InputStream inputStream)
		throws IOException {

		if (inputStream == null) {
			return null;
		}

		try {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream(inputStream.available());

			_transferByteArray(
				inputStream, unsyncByteArrayOutputStream, BUFFER_SIZE, -1);

			byte[] unsafeByteArray =
				unsyncByteArrayOutputStream.unsafeGetByteArray();

			if (unsafeByteArray.length == unsyncByteArrayOutputStream.size()) {
				return unsafeByteArray;
			}

			return unsyncByteArrayOutputStream.toByteArray();
		}
		finally {
			inputStream.close();
		}
	}

	public static String toString(InputStream inputStream) throws IOException {
		return toString(inputStream, StringPool.UTF8);
	}

	public static String toString(InputStream inputStream, String charsetName)
		throws IOException {

		if (inputStream == null) {
			return null;
		}

		return new String(toByteArray(inputStream), charsetName);
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
			throw new NullPointerException("Input stream is null");
		}

		if (outputStream == null) {
			throw new NullPointerException("Output stream is null");
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

				_transferFileChannel(
					fileInputStream.getChannel(), fileOutputStream.getChannel(),
					length);
			}
			else {
				_transferByteArray(
					inputStream, outputStream, bufferSize, length);
			}
		}
		finally {
			if (cleanUp) {
				cleanUp(inputStream, outputStream);
			}
		}
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, long length)
		throws IOException {

		transfer(inputStream, outputStream, BUFFER_SIZE, true, length);
	}

	private static void _transferByteArray(
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
			int readBytes = -1;

			while ((readBytes = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, readBytes);
			}
		}
	}

	private static void _transferFileChannel(
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

}