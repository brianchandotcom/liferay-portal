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

package com.liferay.sync.engine.session.rate.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jonathan McCann
 */
public class RateLimitedInputStream extends InputStream {

	public RateLimitedInputStream(InputStream inputStream, long syncAccountId) {
		_inputStream = inputStream;
		_syncAccountId = syncAccountId;

		_rateLimiter = RateLimiterManager.getDownloadRateLimiter(syncAccountId);
	}

	@Override
	public void close() throws IOException {
		RateLimiterManager.removeDownloadRateLimiter(
			_syncAccountId, _rateLimiter);

		_inputStream.close();
	}

	@Override
	public int read() throws IOException {
		_rateLimiter.acquire();

		return _inputStream.read();
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		_rateLimiter.acquire(bytes.length);

		return _inputStream.read(bytes);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		_rateLimiter.acquire(length);

		return _inputStream.read(bytes, offset, length);
	}

	private final InputStream _inputStream;
	private final RateLimiter _rateLimiter;
	private final long _syncAccountId;

}