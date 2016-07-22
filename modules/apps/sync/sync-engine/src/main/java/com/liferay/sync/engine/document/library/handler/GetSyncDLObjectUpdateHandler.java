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

import com.liferay.sync.engine.document.library.event.Event;
import com.liferay.sync.engine.document.library.event.GetSyncContextEvent;
import com.liferay.sync.engine.document.library.model.SyncDLObjectUpdate;
import com.liferay.sync.engine.document.library.util.BatchEventManager;
import com.liferay.sync.engine.document.library.util.FileEventUtil;
import com.liferay.sync.engine.document.library.util.comparator.SyncFileSizeComparator;
import com.liferay.sync.engine.file.system.Watcher;
import com.liferay.sync.engine.file.system.util.WatcherManager;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.GetterUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.JSONUtil;
import com.liferay.sync.engine.util.SyncEngineUtil;
import com.liferay.sync.engine.util.Validator;

import java.io.IOException;

import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class GetSyncDLObjectUpdateHandler extends BaseSyncDLObjectHandler {

	public GetSyncDLObjectUpdateHandler(final Event event) {
		super(event);

		GetSyncContextEvent getSyncContextEvent = new GetSyncContextEvent(
			getSyncAccountId(), Collections.<String, Object>emptyMap()) {

			@Override
			public void executePost(
					String urlPath, Map<String, Object> parameters)
				throws Exception {

				SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
					getSyncAccountId());

				Session session = SessionManager.getSession(
					getSyncAccountId(), true);

				HttpResponse httpResponse = session.execute(
					new HttpPost(
						syncAccount.getUrl() + "/api/jsonws" + urlPath));

				StatusLine statusLine = httpResponse.getStatusLine();

				if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
					doCancel();

					return;
				}

				Handler<Void> handler = event.getHandler();

				String response = getResponseString(httpResponse);

				if (handler.handlePortalException(getException(response))) {
					doCancel();

					return;
				}

				if (!_firedProcessingState) {
					SyncEngineUtil.fireSyncEngineStateChanged(
						getSyncAccountId(),
						SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSING);

					_firedProcessingState = true;
				}
			}

			@Override
			public void run() {
				SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
					getSyncAccountId());

				SyncSite syncSite = SyncSiteService.fetchSyncSite(
					(Long)event.getParameterValue("repositoryId"),
					getSyncAccountId());

				if ((syncAccount == null) ||
					(syncAccount.getState() != SyncAccount.STATE_CONNECTED) ||
					(syncSite == null) || !syncSite.isActive()) {

					doCancel();

					return;
				}

				super.run();
			}

			protected void doCancel() {
				if (_firedProcessingState) {
					SyncEngineUtil.fireSyncEngineStateChanged(
						getSyncAccountId(),
						SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSED);

					_firedProcessingState = false;
				}

				event.cancel();

				_scheduledFuture.cancel(true);
			}

			private boolean _firedProcessingState;

		};

		_scheduledFuture = _scheduledExecutorService.scheduleWithFixedDelay(
			getSyncContextEvent, 10, 5, TimeUnit.SECONDS);
	}

	@Override
	public void processFinally() {
		_scheduledFuture.cancel(false);

		SyncEngineUtil.fireSyncEngineStateChanged(
			getSyncAccountId(), SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSED);

		SyncSite syncSite = (SyncSite)getParameterValue("syncSite");

		if (syncSite != null) {
			syncSite = SyncSiteService.fetchSyncSite(
				syncSite.getGroupId(), syncSite.getSyncAccountId());

			syncSite.setState(SyncSite.STATE_SYNCED);

			SyncSiteService.update(syncSite);
		}
		else {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				(Long)getParameterValue("repositoryId"), getSyncAccountId(),
				(Long)getParameterValue("parentFolderId"));

			if (syncFile != null) {
				syncFile.setState(SyncFile.STATE_SYNCED);
				syncFile.setUiEvent(SyncFile.UI_EVENT_NONE);

				SyncFileService.update(syncFile);
			}
		}
	}

	@Override
	public void processResponse(String response) throws Exception {
		if (_syncDLObjectUpdate == null) {
			if (response.startsWith("\"")) {
				response = StringEscapeUtils.unescapeJava(response);

				response = response.replaceAll("\n", "\\n");

				response = response.substring(1, response.length() - 1);
			}

			_syncDLObjectUpdate = JSONUtil.readValue(
				response, SyncDLObjectUpdate.class);
		}

		Map<String, Long> settingsModifiedTimes =
			_syncDLObjectUpdate.getSettingsModifiedTimes();

		for (Map.Entry<String, Long> entry : settingsModifiedTimes.entrySet()) {
			String setting = entry.getKey();
			long modifiedTime = entry.getValue();

			if (setting.equals(
					SyncDLObjectUpdate.
						PREFERENCE_KEY_SYNC_CONTEXT_MODIFIED_TIME)) {

				SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
					getSyncAccountId());

				syncAccount.setSyncContextModifiedTime(modifiedTime);

				SyncAccountService.update(syncAccount);
			}
		}

		List<SyncFile> syncFiles = _syncDLObjectUpdate.getSyncFiles();

		if (!syncFiles.isEmpty()) {
			Collections.sort(syncFiles, _syncFileComparator);

			for (SyncFile syncFile : syncFiles) {
				processSyncFile(syncFile);
			}
		}

		if (getParameterValue("parentFolderId") == null) {
			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				(Long)getParameterValue("repositoryId"), getSyncAccountId());

			if ((syncSite == null) ||
				((Long)getParameterValue("lastAccessTime") !=
					syncSite.getRemoteSyncTime())) {

				return;
			}

			long lastAccessTime = _syncDLObjectUpdate.getLastAccessTime();

			if (lastAccessTime == -1) {
				lastAccessTime = 0;
			}

			syncSite.setRemoteSyncTime(lastAccessTime);

			if (_syncDLObjectUpdate.getResultsTotal() <= syncFiles.size()) {
				syncSite.setState(SyncSite.STATE_SYNCED);
			}

			SyncSiteService.update(syncSite);

			if (_syncDLObjectUpdate.getResultsTotal() > syncFiles.size()) {
				FileEventUtil.getUpdates(
					syncSite.getGroupId(), getSyncAccountId(), syncSite,
					GetterUtil.getBoolean(
						getParameterValue("retrieveFromCache")));
			}
		}

		BatchEventManager.fireBatchDownloadEvents();
	}

	protected void addFile(SyncFile syncFile, String filePathName)
		throws Exception {

		Path filePath = Paths.get(filePathName);

		if (isParentUnsynced(syncFile)) {
			if (syncFile.isFolder()) {
				syncFile.setFilePathName(filePath.toString());
				syncFile.setModifiedTime(0);
				syncFile.setState(SyncFile.STATE_UNSYNCED);
				syncFile.setSyncAccountId(getSyncAccountId());
				syncFile.setUiEvent(SyncFile.UI_EVENT_NONE);

				SyncFileService.update(syncFile);
			}

			return;
		}

		if (Files.exists(filePath) &&
			(syncFile.isFolder() || !FileUtil.isModified(syncFile, filePath))) {

			return;
		}

		syncFile.setFilePathName(filePathName);
		syncFile.setSyncAccountId(getSyncAccountId());

		if (syncFile.isFolder()) {
			Files.createDirectories(filePath);

			syncFile.setState(SyncFile.STATE_SYNCED);
			syncFile.setUiEvent(SyncFile.UI_EVENT_ADDED_REMOTE);

			SyncFileService.update(syncFile);

			FileKeyUtil.writeFileKey(
				filePath, String.valueOf(syncFile.getSyncFileId()), false);
		}
		else {
			syncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADING);

			SyncFileService.update(syncFile);

			downloadFile(syncFile, null, 0, false);
		}
	}

	protected void copyFile(SyncFile sourceSyncFile, SyncFile targetSyncFile)
		throws Exception {

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Copying file {} to {}", sourceSyncFile.getFilePathName(),
				targetSyncFile.getFilePathName());
		}

		Path tempFilePath = FileUtil.getTempFilePath(targetSyncFile);

		Files.copy(
			Paths.get(sourceSyncFile.getFilePathName()), tempFilePath,
			StandardCopyOption.REPLACE_EXISTING);

		FileKeyUtil.writeFileKey(
			tempFilePath, String.valueOf(targetSyncFile.getSyncFileId()),
			false);

		Watcher watcher = WatcherManager.getWatcher(getSyncAccountId());

		watcher.addDownloadedFilePathName(targetSyncFile.getFilePathName());

		boolean exists = Files.exists(
			Paths.get(targetSyncFile.getFilePathName()));

		try {
			Files.move(
				tempFilePath, Paths.get(targetSyncFile.getFilePathName()),
				StandardCopyOption.ATOMIC_MOVE,
				StandardCopyOption.REPLACE_EXISTING);
		}
		catch (AccessDeniedException ade) {
			targetSyncFile.setState(SyncFile.STATE_ERROR);
			targetSyncFile.setUiEvent(SyncFile.UI_EVENT_ACCESS_DENIED_LOCAL);

			SyncFileService.update(targetSyncFile);

			return;
		}

		targetSyncFile.setState(SyncFile.STATE_SYNCED);

		if (GetterUtil.getBoolean(
				targetSyncFile.getLocalExtraSettingValue("restoreEvent"))) {

			targetSyncFile.unsetLocalExtraSetting("restoreEvent");

			targetSyncFile.setUiEvent(SyncFile.UI_EVENT_RESTORED_REMOTE);
		}
		else if (exists) {
			targetSyncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADED_UPDATE);
		}
		else {
			targetSyncFile.setUiEvent(SyncFile.UI_EVENT_DOWNLOADED_NEW);
		}

		SyncFileService.update(targetSyncFile);

		IODeltaUtil.copyChecksums(sourceSyncFile, targetSyncFile);
	}

	protected void deleteFile(SyncFile sourceSyncFile, SyncFile targetSyncFile)
		throws Exception {

		if (sourceSyncFile.getUiEvent() == SyncFile.UI_EVENT_DELETED_LOCAL) {
			return;
		}

		sourceSyncFile.setModifiedTime(targetSyncFile.getModifiedTime());

		String event = targetSyncFile.getEvent();

		if (event.equals(SyncFile.EVENT_TRASH)) {
			sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_TRASHED_REMOTE);
		}
		else {
			sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_DELETED_REMOTE);
		}

		sourceSyncFile.setUserId(targetSyncFile.getUserId());
		sourceSyncFile.setUserName(targetSyncFile.getUserName());

		if (!sourceSyncFile.isUnsynced()) {
			SyncFileService.deleteSyncFile(sourceSyncFile);
		}

		Path sourceFilePath = Paths.get(sourceSyncFile.getFilePathName());

		if (Files.notExists(sourceFilePath)) {
			return;
		}

		final Watcher watcher = WatcherManager.getWatcher(getSyncAccountId());

		if (sourceSyncFile.isFile()) {
			watcher.addDeletedFilePathName(sourceSyncFile.getFilePathName());

			FileUtil.deleteFile(sourceFilePath);

			return;
		}

		Files.walkFileTree(
			sourceFilePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path filePath, IOException ioe)
					throws IOException {

					if (ioe != null) {
						return super.postVisitDirectory(filePath, ioe);
					}

					watcher.addDeletedFilePathName(filePath.toString());

					FileUtil.deleteFile(filePath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					watcher.unregisterFilePath(filePath);

					return super.preVisitDirectory(
						filePath, basicFileAttributes);
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					watcher.addDeletedFilePathName(filePath.toString());

					FileUtil.deleteFile(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	protected void downloadFile(
			SyncFile syncFile, String sourceVersion, long sourceVersionId,
			boolean patch)
		throws Exception {

		String checksum = syncFile.getChecksum();

		if (!checksum.isEmpty() && (syncFile.getSize() > 0)) {
			SyncFile sourceSyncFile = SyncFileService.fetchSyncFile(
				checksum, SyncFile.STATE_SYNCED);

			if ((sourceSyncFile != null) &&
				Files.exists(Paths.get(sourceSyncFile.getFilePathName()))) {

				copyFile(sourceSyncFile, syncFile);

				return;
			}
		}

		String targetVersion = syncFile.getVersion();

		if (patch && !sourceVersion.equals("PWC") &&
			!targetVersion.equals("PWC") &&
			(Double.valueOf(sourceVersion) < Double.valueOf(targetVersion))) {

			FileEventUtil.downloadPatch(
				sourceVersionId, getSyncAccountId(), syncFile,
				syncFile.getVersionId());
		}
		else {
			FileEventUtil.downloadFile(getSyncAccountId(), syncFile);
		}
	}

	protected boolean isIgnoredFilePath(
		SyncFile syncFile, String filePathName) {

		if (syncFile != null) {
			filePathName = syncFile.getFilePathName();
		}

		return FileUtil.isIgnoredFilePath(FileUtil.getFilePath(filePathName));
	}

	protected boolean isParentUnsynced(SyncFile syncFile) {
		Path filePath = Paths.get(syncFile.getFilePathName());

		if (FileUtil.isUnsynced(filePath.getParent())) {
			return true;
		}

		return false;
	}

	@Override
	protected void logResponse(String response) {
		try {
			if (response.startsWith("\"")) {
				response = StringEscapeUtils.unescapeJava(response);

				response = response.replaceAll("\n", "\\n");

				response = response.substring(1, response.length() - 1);
			}

			_syncDLObjectUpdate = JSONUtil.readValue(
				response, SyncDLObjectUpdate.class);

			List<SyncFile> syncFiles = _syncDLObjectUpdate.getSyncFiles();

			if (!syncFiles.isEmpty()) {
				super.logResponse(response);
			}
			else {
				super.logResponse("");
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected void moveFile(
			SyncFile sourceSyncFile, SyncFile targetSyncFile,
			String targetFilePathName)
		throws Exception {

		Path targetFilePath = Paths.get(targetFilePathName);

		if (isParentUnsynced(targetSyncFile)) {
			if (sourceSyncFile.isFolder()) {
				List<SyncFile> syncFiles = new ArrayList<>();

				syncFiles.add(sourceSyncFile);

				List<SyncFile> childSyncFiles = SyncFileService.findSyncFiles(
					sourceSyncFile.getFilePathName());

				syncFiles.addAll(childSyncFiles);

				SyncFileService.unsyncFolders(getSyncAccountId(), syncFiles);

				sourceSyncFile = SyncFileService.fetchSyncFile(
					sourceSyncFile.getSyncFileId());

				SyncFileService.updateSyncFile(
					targetFilePath, targetSyncFile.getParentFolderId(),
					sourceSyncFile);
			}
			else {
				deleteFile(sourceSyncFile, targetSyncFile);
			}

			return;
		}

		if (sourceSyncFile.isUnsynced()) {
			if (!isParentUnsynced(sourceSyncFile)) {
				return;
			}

			List<SyncFile> syncFiles = new ArrayList<>();

			syncFiles.add(sourceSyncFile);

			List<SyncFile> childSyncFiles = SyncFileService.findSyncFiles(
				sourceSyncFile.getFilePathName());

			syncFiles.addAll(childSyncFiles);

			SyncFileService.resyncFolders(getSyncAccountId(), syncFiles);

			return;
		}

		Path sourceFilePath = Paths.get(sourceSyncFile.getFilePathName());

		sourceSyncFile = SyncFileService.updateSyncFile(
			targetFilePath, targetSyncFile.getParentFolderId(), sourceSyncFile);

		if (Files.exists(sourceFilePath)) {
			FileUtil.moveFile(sourceFilePath, targetFilePath);

			sourceSyncFile.setState(SyncFile.STATE_SYNCED);
		}
		else if (targetSyncFile.isFolder()) {
			Files.createDirectories(targetFilePath);

			sourceSyncFile.setState(SyncFile.STATE_SYNCED);

			FileKeyUtil.writeFileKey(
				targetFilePath, String.valueOf(sourceSyncFile.getSyncFileId()),
				false);
		}
		else {
			downloadFile(sourceSyncFile, null, 0, false);
		}

		sourceSyncFile.setModifiedTime(targetSyncFile.getModifiedTime());
		sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_MOVED_REMOTE);

		SyncFileService.update(sourceSyncFile);
	}

	protected void processDependentSyncFiles(SyncFile syncFile) {
		List<SyncFile> dependentSyncFiles = _dependentSyncFilesMap.remove(
			getSyncAccountId() + "#" + syncFile.getTypePK());

		if (syncFile.isUnsynced() || (dependentSyncFiles == null)) {
			return;
		}

		for (SyncFile dependentSyncFile : dependentSyncFiles) {
			processSyncFile(dependentSyncFile);
		}
	}

	protected void processSyncFile(SyncFile targetSyncFile) {
		String event = targetSyncFile.getEvent();

		SyncFile sourceSyncFile = SyncFileService.fetchSyncFile(
			targetSyncFile.getRepositoryId(), getSyncAccountId(),
			targetSyncFile.getTypePK());

		if (event.equals(SyncFile.EVENT_DELETE) ||
			event.equals(SyncFile.EVENT_TRASH)) {

			if (sourceSyncFile != null) {
				try {
					deleteFile(sourceSyncFile, targetSyncFile);
				}
				catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
			}

			return;
		}

		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			targetSyncFile.getRepositoryId(), getSyncAccountId(),
			targetSyncFile.getParentFolderId());

		if (parentSyncFile == null) {
			queueSyncFile(targetSyncFile.getParentFolderId(), targetSyncFile);

			return;
		}

		String filePathName = "";

		try {
			filePathName = FileUtil.getFilePathName(
				parentSyncFile.getFilePathName(),
				FileUtil.getSanitizedFileName(
					targetSyncFile.getName(), targetSyncFile.getExtension()));

			if (isIgnoredFilePath(sourceSyncFile, filePathName)) {
				return;
			}

			if ((sourceSyncFile != null) &&
				(sourceSyncFile.getModifiedTime() ==
					targetSyncFile.getModifiedTime())) {

				if (!Validator.isBlank(targetSyncFile.getChecksum())) {
					sourceSyncFile.setChecksum(targetSyncFile.getChecksum());

					SyncFileService.update(sourceSyncFile);
				}

				return;
			}

			if ((sourceSyncFile != null) && !sourceSyncFile.isUnsynced()) {
				sourceSyncFile.setState(SyncFile.STATE_IN_PROGRESS);
			}

			targetSyncFile.setFilePathName(filePathName);
			targetSyncFile.setState(SyncFile.STATE_IN_PROGRESS);

			if (event.equals(SyncFile.EVENT_ADD) ||
				event.equals(SyncFile.EVENT_GET)) {

				if (sourceSyncFile != null) {
					updateFile(sourceSyncFile, targetSyncFile, filePathName);
				}
				else {
					addFile(targetSyncFile, filePathName);
				}
			}
			else if (event.equals(SyncFile.EVENT_MOVE)) {
				if (sourceSyncFile == null) {
					addFile(targetSyncFile, filePathName);
				}
				else if (sourceSyncFile.getParentFolderId() ==
							targetSyncFile.getParentFolderId()) {

					updateFile(sourceSyncFile, targetSyncFile, filePathName);
				}
				else {
					moveFile(sourceSyncFile, targetSyncFile, filePathName);
				}
			}
			else if (event.equals(SyncFile.EVENT_RESTORE)) {
				if (sourceSyncFile != null) {
					updateFile(sourceSyncFile, targetSyncFile, filePathName);
				}
				else if (isParentUnsynced(targetSyncFile)) {
					if (targetSyncFile.isFolder()) {
						targetSyncFile.setModifiedTime(0);
						targetSyncFile.setState(SyncFile.STATE_UNSYNCED);
						targetSyncFile.setSyncAccountId(getSyncAccountId());
						targetSyncFile.setUiEvent(SyncFile.UI_EVENT_NONE);

						SyncFileService.update(targetSyncFile);
					}

					return;
				}
				else {
					targetSyncFile.setLocalExtraSetting("restoreEvent", true);

					SyncFileService.update(targetSyncFile);

					addFile(targetSyncFile, filePathName);
				}
			}
			else if (event.equals(SyncFile.EVENT_UPDATE)) {
				if (sourceSyncFile == null) {
					addFile(targetSyncFile, filePathName);
				}
				else {
					updateFile(sourceSyncFile, targetSyncFile, filePathName);
				}
			}

			processDependentSyncFiles(targetSyncFile);

			if (getParameterValue("parentFolderId") != null) {
				SyncFile syncFile = SyncFileService.fetchSyncFile(
					(Long)getParameterValue("repositoryId"), getSyncAccountId(),
					(Long)getParameterValue("parentFolderId"));

				if (syncFile != null) {
					syncFile.setState(SyncFile.STATE_IN_PROGRESS);
					syncFile.setUiEvent(SyncFile.UI_EVENT_RESYNCING);

					SyncFileService.update(syncFile);
				}
			}
		}
		catch (FileSystemException fse) {
			String message = fse.getMessage();

			if (message.contains("File name too long")) {
				targetSyncFile.setState(SyncFile.STATE_ERROR);
				targetSyncFile.setUiEvent(SyncFile.UI_EVENT_FILE_NAME_TOO_LONG);

				SyncFileService.update(targetSyncFile);
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected void queueSyncFile(long parentFolderId, SyncFile syncFile) {
		List<SyncFile> syncFiles = _dependentSyncFilesMap.get(
			getSyncAccountId() + "#" + parentFolderId);

		if (syncFiles == null) {
			syncFiles = new ArrayList<>();

			_dependentSyncFilesMap.put(
				getSyncAccountId() + "#" + parentFolderId, syncFiles);
		}

		syncFiles.add(syncFile);
	}

	protected void updateFile(
			SyncFile sourceSyncFile, SyncFile targetSyncFile,
			String filePathName)
		throws Exception {

		String sourceVersion = sourceSyncFile.getVersion();
		long sourceVersionId = sourceSyncFile.getVersionId();

		boolean filePathChanged = processFilePathChange(
			sourceSyncFile, targetSyncFile);

		sourceSyncFile.setChangeLog(targetSyncFile.getChangeLog());
		sourceSyncFile.setChecksum(targetSyncFile.getChecksum());
		sourceSyncFile.setDescription(targetSyncFile.getDescription());
		sourceSyncFile.setExtension(targetSyncFile.getExtension());
		sourceSyncFile.setExtraSettings(targetSyncFile.getExtraSettings());
		sourceSyncFile.setLockExpirationDate(
			targetSyncFile.getLockExpirationDate());
		sourceSyncFile.setLockUserId(targetSyncFile.getLockUserId());
		sourceSyncFile.setLockUserName(targetSyncFile.getLockUserName());
		sourceSyncFile.setModifiedTime(targetSyncFile.getModifiedTime());
		sourceSyncFile.setSize(targetSyncFile.getSize());
		sourceSyncFile.setUserId(targetSyncFile.getUserId());
		sourceSyncFile.setUserName(targetSyncFile.getUserName());
		sourceSyncFile.setVersion(targetSyncFile.getVersion());
		sourceSyncFile.setVersionId(targetSyncFile.getVersionId());

		SyncFileService.update(sourceSyncFile);

		if (isParentUnsynced(sourceSyncFile) || sourceSyncFile.isUnsynced()) {
			return;
		}

		Path filePath = Paths.get(targetSyncFile.getFilePathName());

		if (!Files.exists(filePath)) {
			if (targetSyncFile.isFolder()) {
				Path targetFilePath = Paths.get(filePathName);

				Files.createDirectories(targetFilePath);

				sourceSyncFile.setState(SyncFile.STATE_SYNCED);
				sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_UPDATED_REMOTE);

				SyncFileService.update(sourceSyncFile);

				FileKeyUtil.writeFileKey(
					targetFilePath,
					String.valueOf(sourceSyncFile.getSyncFileId()), false);
			}
			else {
				downloadFile(sourceSyncFile, null, 0, false);
			}
		}
		else if (targetSyncFile.isFile() &&
				 FileUtil.isModified(targetSyncFile, filePath)) {

			downloadFile(
				sourceSyncFile, sourceVersion, sourceVersionId,
				!IODeltaUtil.isIgnoredFilePatchingExtension(targetSyncFile));
		}
		else {
			sourceSyncFile.setState(SyncFile.STATE_SYNCED);

			if (filePathChanged) {
				sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_RENAMED_REMOTE);
			}
			else {
				sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_NONE);
			}

			SyncFileService.update(sourceSyncFile);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		GetSyncDLObjectUpdateHandler.class);

	private static final Map<String, List<SyncFile>> _dependentSyncFilesMap =
		new HashMap<>();
	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(5);
	private static final Comparator<SyncFile> _syncFileComparator =
		new SyncFileSizeComparator();

	private final ScheduledFuture<?> _scheduledFuture;
	private SyncDLObjectUpdate _syncDLObjectUpdate;

}