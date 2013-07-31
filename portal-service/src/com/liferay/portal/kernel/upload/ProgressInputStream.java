/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProgressTracker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Minhchau Dang
 */
public class ProgressInputStream extends InputStream {

	public ProgressInputStream(
		PortletRequest portletRequest, InputStream inputStream, long totalSize,
		String progressId) {

		_portletSession = portletRequest.getPortletSession();
		_inputStream = inputStream;
		_totalSize = totalSize;
		_progressId = progressId;

		initProgress();
	}

	@Override
	public int available() throws IOException {
		return _inputStream.available();
	}

	public void clearProgress() {
		_portletSession.removeAttribute(_getPercentAttributeName());
	}

	@Override
	public void close() throws IOException {
		_inputStream.close();
	}

	public long getTotalRead() {
		return _totalRead;
	}

	public void initProgress() {
		ProgressTracker progressTracker = new ProgressTracker(_progressId);

		progressTracker.initialize(_portletSession);
	}

	@Override
	public void mark(int readlimit) {
		_inputStream.mark(readlimit);
	}

	@Override
	public boolean markSupported() {
		return _inputStream.markSupported();
	}

	@Override
	public int read() throws IOException {
		return _inputStream.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = super.read(b, off, len);

		_updateProgress(bytesRead);

		return bytesRead;
	}

	public void readAll(OutputStream outputStream) throws IOException {
		byte[] buffer = new byte[_DEFAULT_INITIAL_BUFFER_SIZE];

		int len = 0;

		while ((len = read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}

		outputStream.close();
	}

	@Override
	public void reset() throws IOException {
		_inputStream.reset();
	}

	@Override
	public long skip(long n) throws IOException {
		long result = _inputStream.skip(n);

		_updateProgress(result);

		return result;
	}

	private String _getPercentAttributeName() {
		return ProgressTracker.PERCENT + _progressId;
	}

	private void _updateProgress(long bytesRead) {
		if (bytesRead > 0) {
			_totalRead += bytesRead;
		}
		else if (_totalSize > 0) {
			_totalRead = _totalSize;
		}

		int percent = 0;

		if (_totalSize > 0) {
			percent = (int)((_totalRead * 100) / _totalSize);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(_totalRead + "/" + _totalSize + "=" + percent);
		}

		ProgressTracker progressTracker =
			(ProgressTracker)_portletSession.getAttribute(
				_getPercentAttributeName(), PortletSession.APPLICATION_SCOPE);

		Integer curPercent = null;

		if (progressTracker != null) {
			curPercent = progressTracker.getPercent();
		}

		if ((curPercent == null) || ((percent - curPercent.intValue()) >= 1)) {
			if (progressTracker == null) {
				progressTracker = new ProgressTracker(_progressId);

				progressTracker.initialize(_portletSession);
			}

			progressTracker.setPercent(percent);
		}
	}

	private static final int _DEFAULT_INITIAL_BUFFER_SIZE = 4 * 1024;

	private static Log _log = LogFactoryUtil.getLog(ProgressInputStream.class);

	private InputStream _inputStream;
	private PortletSession _portletSession;
	private String _progressId;
	private long _totalRead;
	private long _totalSize;

}