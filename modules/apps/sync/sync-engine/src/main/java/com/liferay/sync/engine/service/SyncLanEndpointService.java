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

import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncLanEndpoint;
import com.liferay.sync.engine.service.persistence.SyncLanEndpointPersistence;

import java.sql.SQLException;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class SyncLanEndpointService {

	public static void deleteSyncLanEndpoint(SyncLanEndpoint syncLanEndpoint) {
		try {
			_syncLanEndpointPersistence.delete(syncLanEndpoint);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static void deleteSyncLanEndpoints(String syncLanClientUuid) {
		try {
			_syncLanEndpointPersistence.deleteBySyncLanClientUuid(
				syncLanClientUuid);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static List<String> findSyncLanClientUuids(
		String lanServerUuid, long repositoryId) {

		try {
			return _syncLanEndpointPersistence.findByL_R(
				lanServerUuid, repositoryId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncLanEndpoint> findSyncLanEndPoints(
		String syncLanClientUuid) {

		try {
			return _syncLanEndpointPersistence.findBySyncLanClientUuid(
				syncLanClientUuid);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static SyncLanEndpointPersistence getSyncLanEndpointPersistence() {
		if (_syncLanEndpointPersistence != null) {
			return _syncLanEndpointPersistence;
		}

		try {
			_syncLanEndpointPersistence = new SyncLanEndpointPersistence();

			return _syncLanEndpointPersistence;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static void registerModelListener(
		ModelListener<SyncLanEndpoint> modelListener) {

		_syncLanEndpointPersistence.registerModelListener(modelListener);
	}

	public static void unregisterModelListener(
		ModelListener<SyncLanEndpoint> modelListener) {

		_syncLanEndpointPersistence.unregisterModelListener(modelListener);
	}

	public static SyncLanEndpoint update(SyncLanEndpoint syncLanEndpoint) {
		try {
			_syncLanEndpointPersistence.createOrUpdate(syncLanEndpoint);

			return syncLanEndpoint;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncLanEndpointService.class);

	private static SyncLanEndpointPersistence _syncLanEndpointPersistence =
		getSyncLanEndpointPersistence();

}