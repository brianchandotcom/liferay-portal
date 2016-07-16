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

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncProp;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncPropService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan McCann
 */
public class RateLimiterManager {

	public static synchronized RateLimiter getDownloadRateLimiter(
		long syncAccountId) {

		RateLimiter rateLimiter = getRateLimiter(
			syncAccountId, _downloadRateLimiters);

		_downloadRateLimiterCount++;

		updateDownloadRateLimits();

		return rateLimiter;
	}

	public static synchronized RateLimiter getUploadRateLimiter(
		long syncAccountId) {

		RateLimiter rateLimiter = getRateLimiter(
			syncAccountId, _uploadRateLimiters);

		_uploadRateLimiterCount++;

		updateUploadRateLimits();

		return rateLimiter;
	}

	public static synchronized void removeDownloadRateLimiter(
		long syncAccountId, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _downloadRateLimiters.get(
			syncAccountId);

		if (!rateLimiters.remove(rateLimiter)) {
			return;
		}

		_downloadRateLimiterCount--;

		updateDownloadRateLimits();
	}

	public static synchronized void removeUploadRateLimiter(
		long syncAccountId, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _uploadRateLimiters.get(syncAccountId);

		if (!rateLimiters.remove(rateLimiter)) {
			return;
		}

		_uploadRateLimiterCount--;

		updateUploadRateLimits();
	}

	public static synchronized void updateDownloadRateLimits() {
		if (_downloadRateLimiterCount == 0) {
			return;
		}

		int globalMaxDownloadRate =
			SyncPropService.getInteger(SyncProp.KEY_GLOBAL_MAX_DOWNLOAD_RATE) /
				_downloadRateLimiterCount;

		for (Map.Entry<Long, List<RateLimiter>> entry :
				_downloadRateLimiters.entrySet()) {

			long syncAccountId = entry.getKey();
			List<RateLimiter> rateLimiters = entry.getValue();

			if (rateLimiters.isEmpty()) {
				continue;
			}

			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				syncAccountId);

			updateRateLimits(
				rateLimiters, globalMaxDownloadRate,
				syncAccount.getMaxDownloadRate() / rateLimiters.size());
		}
	}

	public static synchronized void updateUploadRateLimits() {
		if (_uploadRateLimiterCount == 0) {
			return;
		}

		int globalMaxUploadRate =
			SyncPropService.getInteger(SyncProp.KEY_GLOBAL_MAX_UPLOAD_RATE) /
				_uploadRateLimiterCount;

		for (Map.Entry<Long, List<RateLimiter>> entry :
				_uploadRateLimiters.entrySet()) {

			long syncAccountId = entry.getKey();
			List<RateLimiter> rateLimiters = entry.getValue();

			if (rateLimiters.isEmpty()) {
				continue;
			}

			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				syncAccountId);

			updateRateLimits(
				rateLimiters, globalMaxUploadRate,
				syncAccount.getMaxUploadRate() / rateLimiters.size());
		}
	}

	protected static RateLimiter getRateLimiter(
		long syncAccountId, Map<Long, List<RateLimiter>> rateLimiterMap) {

		RateLimiter rateLimiter = RateLimiter.create(1);

		List<RateLimiter> rateLimiters = rateLimiterMap.get(syncAccountId);

		if (rateLimiters == null) {
			rateLimiters = new ArrayList<>();

			rateLimiters.add(rateLimiter);

			rateLimiterMap.put(syncAccountId, rateLimiters);
		}
		else {
			rateLimiters.add(rateLimiter);
		}

		return rateLimiter;
	}

	protected static void updateRateLimits(
		List<RateLimiter> rateLimiters, int globalMaxRateLimit,
		int maxRateLimit) {

		int rate = 0;

		if ((globalMaxRateLimit == 0) && (maxRateLimit == 0)) {
			rate = Integer.MAX_VALUE;
		}
		else if (globalMaxRateLimit == 0) {
			rate = maxRateLimit;
		}
		else if (maxRateLimit == 0) {
			rate = globalMaxRateLimit;
		}
		else {
			rate = Math.min(globalMaxRateLimit, maxRateLimit);
		}

		for (RateLimiter rateLimiter : rateLimiters) {
			rateLimiter.setRate(rate);
		}
	}

	private static int _downloadRateLimiterCount;
	private static final Map<Long, List<RateLimiter>> _downloadRateLimiters =
		new HashMap<>();
	private static int _uploadRateLimiterCount;
	private static final Map<Long, List<RateLimiter>> _uploadRateLimiters =
		new HashMap<>();

}