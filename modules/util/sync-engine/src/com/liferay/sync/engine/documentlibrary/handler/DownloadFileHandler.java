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
import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.filesystem.WatcherRegistry;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.InputStream;

import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;

import java.util.List;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class DownloadFileHandler extends BaseHandler {

	public DownloadFileHandler(Event event) {
		super(event);
	}

	@Override
	public void handleException(Exception e) {
		if (!(e instanceof HttpResponseException)) {
			super.handleException(e);

			return;
		}

		_logger.error(e.getMessage(), e);

		HttpResponseException hre = (HttpResponseException)e;

		int statusCode = hre.getStatusCode();

		if (statusCode != HttpStatus.SC_NOT_FOUND) {
			super.handleException(e);

			return;
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			getSyncAccountId());

		if (syncAccount.getState() != SyncAccount.STATE_CONNECTED) {
			super.handleException(e);

			return;
		}

		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		SyncFileService.deleteSyncFile(syncFile, false);
	}

	protected void copyFile(
			SyncFile syncFile, Path filePath, InputStream inputStream)
		throws Exception {

		Watcher watcher = WatcherRegistry.getWatcher(getSyncAccountId());

		List<String> downloadedFilePathNames =
			watcher.getDownloadedFilePathNames();

		downloadedFilePathNames.add(filePath.toString());

		try {
			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				getSyncAccountId());

			Path tempFilePath = FileUtil.getFilePath(
				syncAccount.getFilePathName(), ".data",
				String.valueOf(syncFile.getSyncFileId()));

			if (Files.exists(filePath)) {
				Files.copy(
					filePath, tempFilePath,
					StandardCopyOption.REPLACE_EXISTING);
			}

			if ((Boolean)getParameterValue("patch")) {
				IODeltaUtil.patch(tempFilePath, inputStream);
			}
			else {
				Files.copy(
					inputStream, tempFilePath,
					StandardCopyOption.REPLACE_EXISTING);
			}

			downloadedFilePathNames.add(filePath.toString());

			if (syncFile.getFileKey() == null) {
				syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADED_NEW);
			}
			else {
				syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADED_UPDATE);
			}

			if (OSDetector.isWindows()) {
				SyncFileService.updateFileKeySyncFile(syncFile, tempFilePath);
			}

			FileTime fileTime = FileTime.fromMillis(syncFile.getModifiedTime());

			Files.setLastModifiedTime(tempFilePath, fileTime);

			Files.move(
				tempFilePath, filePath, StandardCopyOption.ATOMIC_MOVE,
				StandardCopyOption.REPLACE_EXISTING);

			syncFile.setState(SyncFile.STATE_SYNCED);

			SyncFileService.update(syncFile);

			if (!OSDetector.isWindows()) {
				SyncFileService.updateFileKeySyncFile(syncFile);
			}

			IODeltaUtil.checksums(syncFile);
		}
		catch (FileSystemException fse) {
			downloadedFilePathNames.remove(filePath.toString());

			String message = fse.getMessage();

			if (message.contains("File name too long")) {
				syncFile.setState(SyncFile.STATE_ERROR);
				syncFile.setUiEvent(SyncFile.UI_EVENT_FILE_NAME_TOO_LONG);

				SyncFileService.update(syncFile);
			}
		}
	}

	@Override
	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {

		Header errorHeader = httpResponse.getFirstHeader("Sync-Error");

		if (errorHeader != null) {
			handleSiteDeactivatedException();
		}

		final Session session = SessionManager.getSession(getSyncAccountId());

		Header tokenHeader = httpResponse.getFirstHeader("Sync-JWT");

		if (tokenHeader != null) {
			session.setToken(tokenHeader.getValue());
		}

		InputStream inputStream = null;

		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		if (isUnsynced(syncFile)) {
			return;
		}

		Path filePath = Paths.get(syncFile.getFilePathName());

		try {
			HttpEntity httpEntity = httpResponse.getEntity();

			inputStream = new CountingInputStream(httpEntity.getContent()) {

				@Override
				protected synchronized void afterRead(int n) {
					session.incrementDownloadedBytes(n);

					super.afterRead(n);
				}

			};

			copyFile(syncFile, filePath, inputStream);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected boolean isUnsynced(SyncFile syncFile) {
		syncFile = SyncFileService.fetchSyncFile(syncFile.getSyncFileId());

		if ((syncFile == null) ||
			(syncFile.getState() == SyncFile.STATE_UNSYNCED)) {

			return true;
		}

		return false;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		DownloadFileHandler.class);

}