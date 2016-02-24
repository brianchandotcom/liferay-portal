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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncSiteService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class BatchEventManager {

	public static void fireBatchDownloadEvents() {
		synchronized (_batchDownloadEvents) {
			for (BatchDownloadEvent batchDownloadEvent :
					_batchDownloadEvents.values()) {

				batchDownloadEvent.fireBatchEvent();
			}
		}
	}

	public static void fireBatchEvents() {
		synchronized (_batchDownloadEvents) {
			for (BatchEvent batchEvent : _batchEvents.values()) {
				batchEvent.fireBatchEvent();
			}
		}
	}

	public static BatchDownloadEvent getBatchDownloadEvent(SyncFile syncFile) {
		synchronized (_batchDownloadEvents) {
			try {
				SyncSite syncSite = SyncSiteService.fetchSyncSite(
					syncFile.getRepositoryId(), syncFile.getSyncAccountId());

				BatchDownloadEvent batchDownloadEvent =
					_batchDownloadEvents.get(syncSite.getSyncSiteId());

				if ((batchDownloadEvent != null) &&
					!batchDownloadEvent.isClosed()) {

					return batchDownloadEvent;
				}

				batchDownloadEvent = new BatchDownloadEvent(
					syncSite.getSyncAccountId(), syncSite.getSyncSiteId());

				_batchDownloadEvents.put(
					syncSite.getSyncSiteId(), batchDownloadEvent);

				return batchDownloadEvent;
			}
			catch (Exception e) {
				if (_logger.isDebugEnabled()) {
					_logger.debug(e.getMessage(), e);
				}

				return null;
			}
		}
	}

	public static BatchEvent getBatchEvent(SyncFile syncFile) {
		synchronized (_batchEvents) {
			try {
				SyncSite syncSite = SyncSiteService.fetchSyncSite(
					syncFile.getRepositoryId(), syncFile.getSyncAccountId());

				BatchEvent batchEvent = _batchEvents.get(
					syncSite.getSyncSiteId());

				if ((batchEvent != null) && !batchEvent.isClosed()) {
					return batchEvent;
				}

				batchEvent = new BatchEvent(
					syncSite.getSyncAccountId(), syncSite.getSyncSiteId());

				_batchEvents.put(syncSite.getSyncSiteId(), batchEvent);

				return batchEvent;
			}
			catch (Exception e) {
				if (_logger.isDebugEnabled()) {
					_logger.debug(e.getMessage(), e);
				}

				return null;
			}
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BatchDownloadEvent.class);

	private static final Map<Long, BatchDownloadEvent> _batchDownloadEvents =
		new HashMap<>();
	private static final Map<Long, BatchEvent> _batchEvents = new HashMap<>();

}