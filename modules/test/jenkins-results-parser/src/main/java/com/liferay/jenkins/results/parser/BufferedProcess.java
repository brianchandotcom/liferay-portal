/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;

/**
 * @author Peter Yoo
 */
public class BufferedProcess extends Process {

	public BufferedProcess(int bufferSize, Process process) {
		_process = process;

		_standardErrorInputStreamBuffer = new InputStreamBuffer(
			bufferSize, process.getErrorStream());

		_standardErrorInputStreamBuffer.start();

		_standardOutInputStreamBuffer = new InputStreamBuffer(
			bufferSize, process.getInputStream());

		_standardOutInputStreamBuffer.start();
	}

	@Override
	public void destroy() {
		_process.destroy();
	}

	@Override
	public int exitValue() {
		int exitValue = _process.exitValue();

		JenkinsResultsParserUtil.sleep(_MILLIS_EXECUTION_TIME_MIN);

		return exitValue;
	}

	@Override
	public InputStream getErrorStream() {
		return _standardErrorInputStreamBuffer.toInputStream();
	}

	@Override
	public InputStream getInputStream() {
		return _standardOutInputStreamBuffer.toInputStream();
	}

	@Override
	public OutputStream getOutputStream() {
		return _process.getOutputStream();
	}

	@Override
	public int waitFor() throws InterruptedException {
		JenkinsResultsParserUtil.sleep(_MILLIS_EXECUTION_TIME_MIN);

		return _process.waitFor();
	}

	private static final long _MILLIS_EXECUTION_TIME_MIN = 10;

	private final Process _process;
	private final InputStreamBuffer _standardErrorInputStreamBuffer;
	private final InputStreamBuffer _standardOutInputStreamBuffer;

	private class InputStreamBuffer extends Thread {

		public InputStreamBuffer(int bufferSize, InputStream inputStream) {
			_inputStream = inputStream;

			_buffer = new byte[bufferSize];
		}

		public void run() {
			try {
				byte[] bytes = new byte[Math.min(256, _buffer.length)];

				int bytesRead = 0;

				_index = 0;

				while (bytesRead != -1) {
					bytesRead = _inputStream.read(bytes);

					if (bytesRead > 0) {
						int spaceAvailable = _buffer.length - _index;

						if (bytesRead > spaceAvailable) {
							int spaceNeeded = bytesRead - spaceAvailable;

							_makeSpace(spaceNeeded);
						}

						synchronized (_buffer) {
							System.arraycopy(
								bytes, 0, _buffer, _index, bytesRead);

							_index += bytesRead;
							_totalBytesRead += bytesRead;
						}
					}
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		public InputStream toInputStream() {
			byte[] bytes = null;

			if (_totalBytesRead > _buffer.length) {
				String message = JenkinsResultsParserUtil.combine(
					"[Truncated ",
					String.valueOf(_totalBytesRead - _buffer.length),
					" bytes] \n...");

				byte[] messageBytes = message.getBytes();

				bytes = new byte[messageBytes.length + _index];

				System.arraycopy(
					messageBytes, 0, bytes, 0, messageBytes.length);

				System.arraycopy(
					_buffer, 0, bytes, messageBytes.length, _index);
			}
			else {
				bytes = Arrays.copyOf(_buffer, _index);
			}

			return new ByteArrayInputStream(bytes);
		}

		private void _makeSpace(int spacesNeeded) {
			if ((_index - spacesNeeded) < 0) {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"Unable to shift buffer content left ",
						String.valueOf(spacesNeeded), " spaces"));
			}

			System.arraycopy(
				_buffer, spacesNeeded, _buffer, 0, _index - spacesNeeded);

			_index -= spacesNeeded;
		}

		private final byte[] _buffer;
		private int _index;
		private final InputStream _inputStream;
		private int _totalBytesRead;

	}

}