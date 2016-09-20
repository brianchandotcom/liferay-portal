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

package com.liferay.sync.engine.document.library.event;

import com.liferay.sync.engine.document.library.event.constants.EventURLPaths;
import com.liferay.sync.engine.document.library.handler.DownloadFileHandler;
import com.liferay.sync.engine.document.library.handler.Handler;
import com.liferay.sync.engine.document.library.util.BatchDownloadEvent;
import com.liferay.sync.engine.document.library.util.BatchEventManager;
import com.liferay.sync.engine.document.library.util.ServerUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.ServerInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;

/**
 * @author Shinn Lok
 */
public class DownloadFileEvent extends BaseEvent {

	public DownloadFileEvent(
		long syncAccountId, Map<String, Object> parameters) {

		super(syncAccountId, EventURLPaths.DOWNLOAD_FILE, parameters);

		_handler = new DownloadFileHandler(this);
	}

	@Override
	public Handler<Void> getHandler() {
		return _handler;
	}

	@Override
	protected void processRequest() throws Exception {
		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		Path filePath = Paths.get(syncFile.getFilePathName());

		syncFile.setPreviousModifiedTime(
			FileUtil.getLastModifiedTime(filePath));

		syncFile.setState(SyncFile.STATE_IN_PROGRESS);
		syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADING);

		SyncFileService.update(syncFile);

		if ((boolean)getParameterValue("batch")) {
			BatchDownloadEvent batchDownloadEvent =
				BatchEventManager.getBatchDownloadEvent(syncFile);

			if (batchDownloadEvent.addEvent(this)) {
				return;
			}
		}

		StringBuilder sb = new StringBuilder();

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			getSyncAccountId());

		String url = ServerUtil.getDownloadURL(
			syncAccount.getSyncAccountId(), syncAccount.getUrl());

		sb.append(url);

		sb.append(getURLPath());
		sb.append("/");
		sb.append(syncFile.getRepositoryId());
		sb.append("/");
		sb.append(syncFile.getTypeUuid());

		if ((boolean)getParameterValue("patch")) {
			sb.append("?patch=true&sourceVersionId=");
			sb.append(getParameterValue("sourceVersionId"));
			sb.append("&targetVersionId=");
			sb.append(getParameterValue("targetVersionId"));
		}
		else {
			sb.append("?version=");
			sb.append(syncFile.getVersion());
			sb.append("&versionId=");
			sb.append(syncFile.getVersionId());
		}

		HttpGet httpGet = new HttpGet(sb.toString());

		Path tempFilePath = FileUtil.getTempFilePath(syncFile);

		if (ServerInfo.supportsPartialDownloads(getSyncAccountId()) &&
			Files.exists(tempFilePath)) {

			long size = Files.size(tempFilePath);

			if (syncFile.getSize() > size) {
				httpGet.setHeader(
					"Range", "bytes=" + Files.size(tempFilePath) + "-");
			}
		}

		executeAsynchronousGet(httpGet);
	}

	private final Handler<Void> _handler;

}