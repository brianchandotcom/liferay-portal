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

package com.liferay.sync.engine.model;

import com.liferay.sync.engine.lan.server.LanEngine;
import com.liferay.sync.engine.session.rate.limiter.RateLimiterManager;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class SyncPropModelListener implements ModelListener<SyncProp> {

	@Override
	public void onCreate(SyncProp syncProp) {
		updateRateLimits(syncProp);
	}

	@Override
	public void onRemove(SyncProp syncProp) {
	}

	@Override
	public void onUpdate(
		SyncProp syncProp, Map<String, Object> originalValues) {

		updateLanSync(syncProp);

		updateRateLimits(syncProp);
	}

	protected void updateLanSync(SyncProp syncProp) {
		String key = syncProp.getKey();

		if (!key.equals(SyncProp.KEY_LAN_ENABLED)) {
			return;
		}

		if (Boolean.valueOf(syncProp.getValue())) {
			LanEngine.start();
		}
		else {
			LanEngine.stop();
		}
	}

	protected void updateRateLimits(SyncProp syncProp) {
		String key = syncProp.getKey();

		if (key.equals(SyncProp.KEY_GLOBAL_MAX_DOWNLOAD_RATE)) {
			RateLimiterManager.updateDownloadRateLimits();
		}

		if (key.equals(SyncProp.KEY_GLOBAL_MAX_UPLOAD_RATE)) {
			RateLimiterManager.updateUploadRateLimits();
		}
	}

}