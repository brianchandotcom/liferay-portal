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

package com.liferay.sync.engine.service;

import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.persistence.SyncWatchEventPersistence;

import java.sql.SQLException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael Young
 */
public class SyncWatchEventService {

	public static SyncWatchEvent addSyncWatchEvent(
			String eventType, String filePathName, String fileType,
			String previousFilePathName, long syncAccountId)
		throws Exception {

		SyncWatchEvent syncWatchEvent = new SyncWatchEvent();

		syncWatchEvent.setEventType(eventType);
		syncWatchEvent.setFilePathName(filePathName);
		syncWatchEvent.setFileType(fileType);
		syncWatchEvent.setPreviousFilePathName(previousFilePathName);
		syncWatchEvent.setSyncAccountId(syncAccountId);
		syncWatchEvent.setTimestamp(System.currentTimeMillis());

		_syncWatchEventPersistence.create(syncWatchEvent);

		_lastSyncWatchEvents.put(syncAccountId, syncWatchEvent);

		return syncWatchEvent;
	}

	public static void deleteSyncWatchEvent(long syncWatchEventId) {
		try {
			SyncWatchEvent syncWatchEvent = fetchSyncWatchEvent(
				syncWatchEventId);

			if (syncWatchEvent == null) {
				return;
			}

			SyncWatchEvent lastSyncWatchEvent = _lastSyncWatchEvents.get(
				syncWatchEvent.getSyncAccountId());

			if ((lastSyncWatchEvent != null) &&
				(lastSyncWatchEvent.getSyncWatchEventId() ==
					syncWatchEventId)) {

				_lastSyncWatchEvents.remove(syncWatchEvent.getSyncAccountId());
			}

			_syncWatchEventPersistence.deleteById(syncWatchEventId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static void deleteSyncWatchEvents(long syncAccountId) {
		try {
			_lastSyncWatchEvents.remove(syncAccountId);

			_syncWatchEventPersistence.deleteBySyncAccountId(syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static SyncWatchEvent fetchDuplicateSyncWatchEvent(
		SyncWatchEvent syncWatchEvent) {

		try {
			return _syncWatchEventPersistence.fetchByE_F_NotT_First(
				syncWatchEvent.getEventType(), syncWatchEvent.getFilePathName(),
				syncWatchEvent.getTimestamp());
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncWatchEvent fetchSyncWatchEvent(long syncWatchEventId) {
		try {
			return _syncWatchEventPersistence.queryForId(syncWatchEventId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncWatchEvent fetchSyncWatchEvent(
		String eventType, String filePathName, long timestamp) {

		try {
			return _syncWatchEventPersistence.fetchByE_F_T_First(
				eventType, filePathName, timestamp);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static List<SyncWatchEvent> findAll() {
		try {
			return _syncWatchEventPersistence.queryForAll();
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncWatchEvent> findBySyncAccountId(long syncAccountId) {
		try {
			return _syncWatchEventPersistence.findBySyncAccountId(
				syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncWatchEvent> findBySyncAccountId(
		long syncAccountId, String orderByColumn, boolean ascending) {

		try {
			return _syncWatchEventPersistence.findBySyncAccountId(
				syncAccountId, orderByColumn, ascending);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static SyncWatchEvent getLastSyncWatchEvent(long syncAccountId) {
		return _lastSyncWatchEvents.get(syncAccountId);
	}

	public static SyncWatchEventPersistence getSyncWatchEventPersistence() {
		if (_syncWatchEventPersistence != null) {
			return _syncWatchEventPersistence;
		}

		try {
			_syncWatchEventPersistence = new SyncWatchEventPersistence();
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}

		return _syncWatchEventPersistence;
	}

	public static boolean hasSyncWatchEvents(long syncAccountId) {
		try {
			if (_syncWatchEventPersistence.fetchBySyncAccountId_First(
					syncAccountId) == null) {

				return false;
			}

			return true;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return false;
		}
	}

	public static SyncWatchEvent update(SyncWatchEvent syncWatchEvent) {
		try {
			_syncWatchEventPersistence.createOrUpdate(syncWatchEvent);

			return syncWatchEvent;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventService.class);

	private static final Map<Long, SyncWatchEvent> _lastSyncWatchEvents =
		new HashMap<>();
	private static SyncWatchEventPersistence _syncWatchEventPersistence =
		getSyncWatchEventPersistence();

}