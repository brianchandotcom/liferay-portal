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
import java.io.OutputStream;

/**
 * @author Jonathan McCann
 */
public class RateLimitedOutputStream extends OutputStream {

	public RateLimitedOutputStream(
		OutputStream outputStream, long syncAccountId) {

		_outputStream = outputStream;
		_syncAccountId = syncAccountId;

		_rateLimiter = RateLimiterManager.getUploadRateLimiter(syncAccountId);
	}

	@Override
	public void close() throws IOException {
		RateLimiterManager.removeUploadRateLimiter(
			_syncAccountId, _rateLimiter);

		_outputStream.close();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		_rateLimiter.acquire(bytes.length);

		_outputStream.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		_rateLimiter.acquire(length);

		_outputStream.write(bytes, offset, length);
	}

	@Override
	public void write(int byteValue) throws IOException {
		_rateLimiter.acquire();

		_outputStream.write(byteValue);
	}

	private final OutputStream _outputStream;
	private final RateLimiter _rateLimiter;
	private final long _syncAccountId;

}