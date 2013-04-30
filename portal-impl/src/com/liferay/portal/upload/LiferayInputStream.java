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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletInputStreamAdapter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Myunghun Kim
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 */
public class LiferayInputStream extends ServletInputStreamAdapter {

	public static final long THRESHOLD_SIZE = GetterUtil.getLong(
		PropsUtil.get(LiferayInputStream.class.getName() + ".threshold.size"));

	public LiferayInputStream(HttpServletRequest request) throws IOException {
		super(request.getInputStream());

		_session = request.getSession();
		_totalSize = request.getContentLength();

		if (_totalSize < 0) {
			String contentLength = request.getHeader("content-length");

			if (Validator.isNotNull(contentLength)) {
				_totalSize = Long.parseLong(contentLength);
			}
		}
	}

	public ServletInputStream getCachedInputStream() {
		if (_totalSize < THRESHOLD_SIZE) {
			return this;
		}
		else {
			return new ServletInputStreamAdapter(
				new UnsyncByteArrayInputStream(
					_cachedBytes.unsafeGetByteArray(), 0, _cachedBytes.size()));
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = super.read(b, off, len);

		if (bytesRead > 0) {
			_totalRead += bytesRead;
		}
		else {
			return bytesRead;
		}

		int percent = (int)((_totalRead * 100L) / _totalSize);

		if (_log.isDebugEnabled()) {
			_log.debug(bytesRead + "/" + _totalRead + "=" + percent);
		}

		if ((_totalSize > 0) && (_totalSize < THRESHOLD_SIZE)) {
			_cachedBytes.write(b, off, bytesRead);
		}

		ProgressTracker progressTracker =
			(ProgressTracker)_session.getAttribute(LiferayFileUpload.PERCENT);

		Integer curPercent = null;

		if (progressTracker != null) {
			curPercent = progressTracker.getPercent();
		}

		if ((curPercent == null) || ((percent - curPercent.intValue()) >= 1)) {
			if (progressTracker == null) {
				progressTracker = new ProgressTracker(
					_session, StringPool.BLANK);

				progressTracker.initialize();
			}

			progressTracker.setPercent(percent);
		}

		return bytesRead;
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayInputStream.class);

	private UnsyncByteArrayOutputStream _cachedBytes =
		new UnsyncByteArrayOutputStream();
	private HttpSession _session;
	private long _totalRead;
	private long _totalSize;

}