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

package com.liferay.sync.engine.documentlibrary.handler;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.util.JSONUtil;

import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class AddFileFolderHandler extends BaseJSONHandler {

	public AddFileFolderHandler(Event event) {
		super(event);
	}

	@Override
	public void handleException(Exception e) {
		if (e instanceof SocketException) {
			String message = e.getMessage();

			if (message.equals("Broken pipe")) {
				if (_logger.isDebugEnabled()) {
					_logger.debug("Handling exception {}", e.toString());
				}

				SyncFile syncFile = getLocalSyncFile();

				syncFile.setState(SyncFile.STATE_ERROR);
				syncFile.setUiEvent(SyncFile.UI_EVENT_UPLOAD_EXCEPTION);

				SyncFileService.update(syncFile);

				return;
			}
		}

		super.handleException(e);
	}

	@Override
	public void processResponse(String response) throws Exception {
		SyncFile remoteSyncFile = JSONUtil.readValue(response, SyncFile.class);

		SyncFile localSyncFile = getLocalSyncFile();

		localSyncFile.setCompanyId(remoteSyncFile.getCompanyId());
		localSyncFile.setCreateTime(remoteSyncFile.getCreateTime());
		localSyncFile.setExtension(remoteSyncFile.getExtension());
		localSyncFile.setExtraSettings(remoteSyncFile.getExtraSettings());
		localSyncFile.setLockExpirationDate(
			remoteSyncFile.getLockExpirationDate());
		localSyncFile.setLockUserId(remoteSyncFile.getLockUserId());
		localSyncFile.setLockUserName(remoteSyncFile.getLockUserName());
		localSyncFile.setModifiedTime(remoteSyncFile.getModifiedTime());
		localSyncFile.setParentFolderId(remoteSyncFile.getParentFolderId());
		localSyncFile.setSize(remoteSyncFile.getSize());
		localSyncFile.setState(SyncFile.STATE_SYNCED);
		localSyncFile.setSyncAccountId(getSyncAccountId());
		localSyncFile.setTypePK(remoteSyncFile.getTypePK());
		localSyncFile.setTypeUuid(remoteSyncFile.getTypeUuid());
		localSyncFile.setUiEvent(SyncFile.UI_EVENT_UPLOADED);
		localSyncFile.setVersion(remoteSyncFile.getVersion());
		localSyncFile.setVersionId(remoteSyncFile.getVersionId());

		SyncFileService.update(localSyncFile);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		AddFileFolderHandler.class);

}