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

package com.liferay.poshi.runner.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Peter Yoo
 */
public class BufferedProcess extends Process {

	public BufferedProcess(int bufferSize, Process process) {
		_process = process;

		_standardErrorInputStreamBuffer = new InputStreamBuffer(
			bufferSize, _process.getErrorStream());

		_standardErrorInputStream = new ByteArrayInputStream(
			_standardErrorInputStreamBuffer._buffer);

		_standardErrorInputStreamBuffer.start();

		_standardOutInputStreamBuffer = new InputStreamBuffer(
			bufferSize, _process.getInputStream());

		_standardOutInputStream = new ByteArrayInputStream(
			_standardOutInputStreamBuffer._buffer);

		_standardOutInputStreamBuffer.start();
	}

	@Override
	public void destroy() {
		_process.destroy();
	}

	@Override
	public int exitValue() {
		int exitValue = _process.exitValue();

		ExecUtil.sleep(_MINIMUM_EXECUTION_TIME);

		return exitValue;
	}

	@Override
	public InputStream getErrorStream() {
		return _standardErrorInputStream;
	}

	@Override
	public InputStream getInputStream() {
		return _standardOutInputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return _process.getOutputStream();
	}

	@Override
	public int waitFor() throws InterruptedException {
		ExecUtil.sleep(_MINIMUM_EXECUTION_TIME);

		return _process.waitFor();
	}

	private static final long _MINIMUM_EXECUTION_TIME = 10;

	private final Process _process;
	private final InputStream _standardErrorInputStream;
	private final InputStreamBuffer _standardErrorInputStreamBuffer;
	private final InputStream _standardOutInputStream;
	private final InputStreamBuffer _standardOutInputStreamBuffer;

	private class InputStreamBuffer extends Thread {

		public InputStreamBuffer(int bufferSize, InputStream inputStream) {
			_buffer = new byte[bufferSize];
			_inputStream = inputStream;
		}

		public void run() {
			try {
				byte[] bytes = new byte[256];

				int bytesRead = 0;

				_index = 0;

				while (bytesRead != -1) {
					bytesRead = _inputStream.read(bytes);

					if (bytesRead > 0) {
						synchronized (_buffer) {
							System.arraycopy(
								bytes, 0, _buffer, _index, bytesRead);

							_index += bytesRead;
						}
					}
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private final byte[] _buffer;
		private int _index;
		private final InputStream _inputStream;

	}

}