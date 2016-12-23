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

package com.liferay.sync.engine.document.library.handler;

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.document.library.event.Event;
import com.liferay.sync.engine.document.library.util.FileEventUtil;
import com.liferay.sync.engine.file.system.Watcher;
import com.liferay.sync.engine.file.system.util.WatcherManager;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.session.rate.limiter.RateLimitedInputStream;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.GetterUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.MSOfficeFileUtil;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import java.util.concurrent.ExecutorService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.ConnectionClosedException;
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
		if (isEventCancelled()) {
			return;
		}

		if (e instanceof ConnectionClosedException) {
			String message = e.getMessage();

			if (message.startsWith("Premature end of Content-Length")) {
				_logger.error(message, e);

				removeEvent();

				SyncFile localSyncFile = getLocalSyncFile();

				if (localSyncFile == null) {
					return;
				}

				FileEventUtil.downloadFile(
					getSyncAccountId(), localSyncFile, false);

				return;
			}
		}
		else if (e instanceof HttpResponseException) {
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

			removeEvent();

			SyncFile syncFile = getLocalSyncFile();

			if (syncFile == null) {
				return;
			}

			if ((boolean)getParameterValue("patch")) {
				FileEventUtil.downloadFile(getSyncAccountId(), syncFile, false);
			}
			else {
				handleException(syncFile);
			}

			return;
		}

		super.handleException(e);
	}

	@Override
	public boolean handlePortalException(String exception) throws Exception {
		SyncFile syncFile = getLocalSyncFile();

		if (syncFile == null) {
			return true;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Handling exception {} file path {}", exception,
				syncFile.getFilePathName());
		}

		if (exception.endsWith("PrincipalException")) {
			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_INVALID_PERMISSIONS);

			SyncFileService.update(syncFile);

			return true;
		}
		else if (exception.endsWith("NoSuchFileVersionException") &&
				 (boolean)getParameterValue("patch")) {

			removeEvent();

			FileEventUtil.downloadFile(getSyncAccountId(), syncFile, false);

			return true;
		}
		else if (exception.endsWith("NoSuchFileEntryException") ||
				 exception.endsWith("NoSuchFileException")) {

			SyncFileService.deleteSyncFile(syncFile);

			return true;
		}

		removeEvent();

		handleException(syncFile);

		return true;
	}

	protected void copyFile(
			final SyncFile syncFile, Path filePath, InputStream inputStream,
			boolean append)
		throws Exception {

		OutputStream outputStream = null;

		Watcher watcher = WatcherManager.getWatcher(getSyncAccountId());

		try {
			Path tempFilePath = FileUtil.getTempFilePath(syncFile);

			boolean exists = FileUtil.exists(filePath);

			if (append) {
				outputStream = Files.newOutputStream(
					tempFilePath, StandardOpenOption.APPEND);

				IOUtils.copyLarge(inputStream, outputStream);
			}
			else {
				if (exists && (boolean)getParameterValue("patch")) {
					Files.copy(
						filePath, tempFilePath,
						StandardCopyOption.REPLACE_EXISTING);

					IODeltaUtil.patch(tempFilePath, inputStream);
				}
				else {
					Files.copy(
						inputStream, tempFilePath,
						StandardCopyOption.REPLACE_EXISTING);
				}
			}

			watcher.addDownloadedFilePathName(filePath.toString());

			if (GetterUtil.getBoolean(
					syncFile.getLocalExtraSettingValue("restoreEvent"))) {

				syncFile.unsetLocalExtraSetting("restoreEvent");

				syncFile.setUiEvent(SyncFile.UI_EVENT_RESTORED_REMOTE);
			}
			else if (exists) {
				syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADED_UPDATE);
			}
			else {
				syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADED_NEW);
			}

			FileKeyUtil.writeFileKey(
				tempFilePath, String.valueOf(syncFile.getSyncFileId()), false);

			FileUtil.setModifiedTime(tempFilePath, syncFile.getModifiedTime());

			if (MSOfficeFileUtil.isLegacyExcelFile(filePath)) {
				syncFile.setLocalExtraSetting(
					"lastSavedDate",
					MSOfficeFileUtil.getLastSavedDate(tempFilePath));
			}

			Files.move(
				tempFilePath, filePath, StandardCopyOption.ATOMIC_MOVE,
				StandardCopyOption.REPLACE_EXISTING);

			ExecutorService executorService = SyncEngine.getExecutorService();

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					IODeltaUtil.checksums(syncFile);

					syncFile.setState(SyncFile.STATE_SYNCED);

					SyncFileService.update(syncFile);
				}

			};

			executorService.execute(runnable);
		}
		catch (FileSystemException fse) {
			if (fse instanceof AccessDeniedException) {
				_logger.error(fse.getMessage(), fse);

				syncFile.setState(SyncFile.STATE_ERROR);
				syncFile.setUiEvent(SyncFile.UI_EVENT_ACCESS_DENIED_LOCAL);

				SyncFileService.update(syncFile);

				return;
			}
			else if (fse instanceof NoSuchFileException) {
				if (isEventCancelled()) {
					SyncFileService.deleteSyncFile(syncFile);

					return;
				}
			}

			watcher.removeDownloadedFilePathName(filePath.toString());

			String message = fse.getMessage();

			_logger.error(message, fse);

			syncFile.setState(SyncFile.STATE_ERROR);

			if (message.contains("File name too long")) {
				syncFile.setUiEvent(SyncFile.UI_EVENT_FILE_NAME_TOO_LONG);
			}

			SyncFileService.update(syncFile);
		}
		finally {
			StreamUtil.cleanUp(outputStream);
		}
	}

	@Override
	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {

		Header errorHeader = httpResponse.getFirstHeader("Sync-Error");

		if (errorHeader != null) {
			handleSiteDeactivatedException();
		}

		long syncAccountId = getSyncAccountId();

		final Session session = SessionManager.getSession(syncAccountId);

		Header tokenHeader = httpResponse.getFirstHeader("Sync-JWT");

		if (tokenHeader != null) {
			session.addHeader("Sync-JWT", tokenHeader.getValue());
		}

		InputStream inputStream = null;

		SyncFile syncFile = getLocalSyncFile();

		if ((syncFile == null) || isUnsynced(syncFile)) {
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

			inputStream = new RateLimitedInputStream(
				inputStream, syncAccountId);

			if (httpResponse.getFirstHeader("Accept-Ranges") != null) {
				copyFile(syncFile, filePath, inputStream, true);
			}
			else {
				copyFile(syncFile, filePath, inputStream, false);
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void handleException(SyncFile syncFile) {
		syncFile.setState(SyncFile.STATE_ERROR);
		syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOAD_EXCEPTION);

		SyncFileService.update(syncFile);

		Path filePathName = Paths.get(syncFile.getFilePathName());

		if (FileUtil.notExists(filePathName)) {
			try {
				Files.createFile(filePathName);
			}
			catch (IOException ioe) {
				_logger.error(ioe.getMessage(), ioe);
			}
		}
	}

	protected boolean isUnsynced(SyncFile syncFile) {
		if (syncFile != null) {
			syncFile = SyncFileService.fetchSyncFile(syncFile.getSyncFileId());
		}

		if (syncFile == null) {
			return true;
		}

		if (syncFile.getState() == SyncFile.STATE_UNSYNCED) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Skipping file {}. File is unsynced.", syncFile.getName());
			}

			return true;
		}

		Path filePath = Paths.get(syncFile.getFilePathName());

		if (FileUtil.notExists(filePath.getParent())) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Skipping file {}. Missing parent file path {}.",
					syncFile.getName(), filePath.getParent());
			}

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_PARENT_MISSING);

			SyncFileService.update(syncFile);

			return true;
		}

		return false;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		DownloadFileHandler.class);

}