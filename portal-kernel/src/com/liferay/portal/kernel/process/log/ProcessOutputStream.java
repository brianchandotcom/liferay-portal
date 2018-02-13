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

package com.liferay.portal.kernel.process.log;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

/**
 * @author Shuyang Zhou
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class ProcessOutputStream extends UnsyncByteArrayOutputStream {

	public ProcessOutputStream(ObjectOutputStream objectOutputStream) {
		this(objectOutputStream, false);
	}

	public ProcessOutputStream(
		ObjectOutputStream objectOutputStream, boolean error) {

		_objectOutputStream = objectOutputStream;
		_error = error;
	}

	@Override
	public void close() throws IOException {
		_objectOutputStream.close();
	}

	@Override
	public void flush() throws IOException {
		synchronized (System.out) {
			if (index > 0) {
				byte[] bytes = toByteArray();

				reset();

				byte[] logData = new byte[_logPrefix.length + bytes.length];

				System.arraycopy(_logPrefix, 0, logData, 0, _logPrefix.length);
				System.arraycopy(
					bytes, 0, logData, _logPrefix.length, bytes.length);

				String message = new String(bytes, StringPool.UTF8);

				_objectOutputStream.writeObject(
					new LoggingProcessCallable(message, _error));
			}

			_objectOutputStream.flush();

			_objectOutputStream.reset();
		}
	}

	public void setLogPrefix(byte[] logPrefix) {
		_logPrefix = logPrefix;
	}

	public void writeProcessCallable(ProcessCallable<?> processCallable)
		throws IOException {

		synchronized (System.out) {
			try {
				_objectOutputStream.writeObject(processCallable);
			}
			catch (NotSerializableException nse) {
				_objectOutputStream.reset();

				throw nse;
			}
			finally {
				_objectOutputStream.flush();
			}
		}
	}

	private final boolean _error;
	private byte[] _logPrefix;
	private final ObjectOutputStream _objectOutputStream;

	private static class LoggingProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() {
			if (_error) {
				System.err.print(_message);
			}
			else {
				System.out.print(_message);
			}

			return StringPool.BLANK;
		}

		private LoggingProcessCallable(String message, boolean error) {
			_message = message;
			_error = error;
		}

		private static final long serialVersionUID = 1L;

		private final boolean _error;
		private final String _message;

	}

}