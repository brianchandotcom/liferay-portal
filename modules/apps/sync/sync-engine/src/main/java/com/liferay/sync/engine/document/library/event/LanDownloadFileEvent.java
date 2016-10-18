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

import com.liferay.sync.engine.document.library.handler.Handler;
import com.liferay.sync.engine.document.library.handler.LanDownloadFileHandler;
import com.liferay.sync.engine.lan.session.LanSession;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.util.FileUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;

/**
 * @author Dennis Ju
 */
public class LanDownloadFileEvent extends BaseEvent {

	public LanDownloadFileEvent(
		long syncAccountId, Map<String, Object> parameters) {

		super(syncAccountId, "", parameters);

		_handler = new LanDownloadFileHandler(this);
	}

	@Override
	public void cancel() {
		if (_httpGet != null) {
			_httpGet.abort();
		}

		super.cancel();
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

		LanSession lanSession = LanSession.getLanSession();

		_httpGet = lanSession.downloadFile(this);
	}

	private final Handler<Void> _handler;
	private HttpGet _httpGet;

}