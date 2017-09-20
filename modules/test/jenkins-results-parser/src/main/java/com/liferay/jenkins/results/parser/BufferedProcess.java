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

package com.liferay.jenkins.results.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Peter Yoo
 */
public class BufferedProcess extends Process {

	public BufferedProcess(int bufferSize, Process process) {
		_bufferSize = bufferSize;
		_process = process;

		_errorStreamBuffer = new StreamBuffer(_process.getErrorStream());

		_errorStream = new ByteArrayInputStream(_errorStreamBuffer._buffer);

		_errorStreamBuffer.start();

		_inputStreamBuffer = new StreamBuffer(_process.getInputStream());

		_inputStream = new ByteArrayInputStream(_inputStreamBuffer._buffer);

		_inputStreamBuffer.start();
	}

	@Override
	public void destroy() {
		_process.destroy();
	}

	@Override
	public int exitValue() {
		return _process.exitValue();
	}

	@Override
	public InputStream getErrorStream() {
		return _errorStream;
	}

	@Override
	public InputStream getInputStream() {
		return _inputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return _process.getOutputStream();
	}

	@Override
	public int waitFor() throws InterruptedException {
		return _process.waitFor();
	}

	private final int _bufferSize;
	private final InputStream _errorStream;
	private final StreamBuffer _errorStreamBuffer;
	private final InputStream _inputStream;
	private final StreamBuffer _inputStreamBuffer;
	private final Process _process;

	private class StreamBuffer extends Thread {

		public StreamBuffer(InputStream inputStream) {
			_inputStream = inputStream;
			_buffer = new byte[_bufferSize];
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