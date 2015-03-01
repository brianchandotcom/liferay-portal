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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.model.SyncDLObjectUpdate;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.filesystem.util.WatcherRegistry;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.SyncEngineUtil;

import java.io.IOException;

import java.nio.file.FileSystemException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class GetSyncDLObjectUpdateHandler extends BaseSyncDLObjectHandler {

	public GetSyncDLObjectUpdateHandler(Event event) {
		super(event);
	}

	@Override
	public void processResponse(String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		SyncDLObjectUpdate syncDLObjectUpdate = objectMapper.readValue(
			response, new TypeReference<SyncDLObjectUpdate>() {});

		boolean firedProcessingState = false;
		long start = System.currentTimeMillis();

		for (SyncFile targetSyncFile : syncDLObjectUpdate.getSyncDLObjects()) {
			processSyncFile(targetSyncFile);

			if (!firedProcessingState &&
				((System.currentTimeMillis() - start) > 1000)) {

				SyncEngineUtil.fireSyncEngineStateChanged(
					getSyncAccountId(),
					SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSING);

				firedProcessingState = true;
			}
		}

		if (firedProcessingState) {
			SyncEngineUtil.fireSyncEngineStateChanged(
				getSyncAccountId(), SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSED);
		}

		if (getParameterValue("parentFolderId") == null) {
			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				(Long)getParameterValue("repositoryId"), getSyncAccountId());

			if ((syncSite == null) ||
				((Long)getParameterValue("lastAccessTime")
					!= syncSite.getRemoteSyncTime())) {

				return;
			}

			syncSite.setRemoteSyncTime(syncDLObjectUpdate.getLastAccessTime());

			SyncSiteService.update(syncSite);
		}
	}

	protected void addFile(SyncFile syncFile, String filePathName)
		throws Exception {

		Path filePath = Paths.get(filePathName);

		if (Files.exists(filePath) &&
			(syncFile.isFolder() ||
			 !FileUtil.isModified(syncFile, filePath))) {

			return;
		}

		syncFile.setFilePathName(filePathName);
		syncFile.setSyncAccountId(getSyncAccountId());
		syncFile.setUiEvent(SyncFile.UI_EVENT_ADDED_REMOTE);

		if (syncFile.isFolder()) {
			Files.createDirectories(filePath);

			syncFile.setState(SyncFile.STATE_SYNCED);

			SyncFileService.update(syncFile);

			FileUtil.writeFileKey(
				filePath, String.valueOf(syncFile.getSyncFileId()));
		}
		else {
			SyncFileService.update(syncFile);

			downloadFile(syncFile, null, 0, false);
		}
	}

	protected void deleteFile(SyncFile sourceSyncFile, boolean trashed)
		throws Exception {

		if (trashed) {
			sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_TRASHED_REMOTE);
		}
		else {
			sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_DELETED_REMOTE);
		}

		SyncFileService.deleteSyncFile(sourceSyncFile);

		Path sourceFilePath = Paths.get(sourceSyncFile.getFilePathName());

		if (Files.notExists(sourceFilePath)) {
			return;
		}

		if (sourceSyncFile.isFile()) {
			Files.deleteIfExists(sourceFilePath);

			return;
		}

		if (!OSDetector.isLinux()) {
			FileUtils.deleteDirectory(sourceFilePath.toFile());

			return;
		}

		final Watcher watcher = WatcherRegistry.getWatcher(getSyncAccountId());

		Files.walkFileTree(
			sourceFilePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path filePath, IOException ioe)
					throws IOException {

					Files.deleteIfExists(filePath);

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

					Files.deleteIfExists(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	protected void downloadFile(
		SyncFile syncFile, String sourceVersion, long sourceVersionId,
		boolean patch) {

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

	protected void moveFile(
			SyncFile sourceSyncFile, SyncFile targetSyncFile,
			String targetFilePathName)
		throws Exception {

		Path sourceFilePath = Paths.get(sourceSyncFile.getFilePathName());
		Path targetFilePath = Paths.get(targetFilePathName);

		sourceSyncFile = SyncFileService.updateSyncFile(
			targetFilePath, targetSyncFile.getParentFolderId(), sourceSyncFile);

		if (Files.exists(sourceFilePath)) {
			Files.move(sourceFilePath, targetFilePath);

			sourceSyncFile.setState(SyncFile.STATE_SYNCED);
		}
		else if (targetSyncFile.isFolder()) {
			Files.createDirectories(targetFilePath);

			sourceSyncFile.setState(SyncFile.STATE_SYNCED);

			FileUtil.writeFileKey(
				targetFilePath, String.valueOf(sourceSyncFile.getSyncFileId()));
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
			syncFile.getTypePK());

		if (dependentSyncFiles == null) {
			return;
		}

		for (SyncFile dependentSyncFile : dependentSyncFiles) {
			processSyncFile(dependentSyncFile);
		}
	}

	protected void processSyncFile(SyncFile targetSyncFile) {
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

			SyncFile sourceSyncFile = SyncFileService.fetchSyncFile(
				targetSyncFile.getRepositoryId(), getSyncAccountId(),
				targetSyncFile.getTypePK());

			if (isIgnoredFilePath(sourceSyncFile, filePathName) ||
				((sourceSyncFile != null) &&
				 (sourceSyncFile.getModifiedTime() ==
					targetSyncFile.getModifiedTime()))) {

				return;
			}

			if (sourceSyncFile != null) {
				sourceSyncFile.setState(SyncFile.STATE_IN_PROGRESS);
			}

			targetSyncFile.setFilePathName(filePathName);
			targetSyncFile.setState(SyncFile.STATE_IN_PROGRESS);

			String event = targetSyncFile.getEvent();

			if (event.equals(SyncFile.EVENT_ADD) ||
				event.equals(SyncFile.EVENT_GET) ||
				event.equals(SyncFile.EVENT_RESTORE)) {

				if (sourceSyncFile != null) {
					updateFile(sourceSyncFile, targetSyncFile, filePathName);

					processDependentSyncFiles(sourceSyncFile);

					return;
				}

				addFile(targetSyncFile, filePathName);
			}
			else if (event.equals(SyncFile.EVENT_DELETE)) {
				if (sourceSyncFile == null) {
					return;
				}

				deleteFile(sourceSyncFile, false);
			}
			else if (event.equals(SyncFile.EVENT_MOVE)) {
				if (sourceSyncFile == null) {
					addFile(targetSyncFile, filePathName);

					processDependentSyncFiles(targetSyncFile);

					return;
				}

				moveFile(sourceSyncFile, targetSyncFile, filePathName);
			}
			else if (event.equals(SyncFile.EVENT_TRASH)) {
				if (sourceSyncFile == null) {
					return;
				}

				deleteFile(sourceSyncFile, true);
			}
			else if (event.equals(SyncFile.EVENT_UPDATE)) {
				if (sourceSyncFile == null) {
					addFile(targetSyncFile, filePathName);

					processDependentSyncFiles(targetSyncFile);

					return;
				}

				updateFile(sourceSyncFile, targetSyncFile, filePathName);
			}

			processDependentSyncFiles(targetSyncFile);
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			if (e instanceof FileSystemException) {
				String message = e.getMessage();

				if (message.contains("File name too long")) {
					targetSyncFile.setState(SyncFile.STATE_ERROR);
					targetSyncFile.setUiEvent(
						SyncFile.UI_EVENT_FILE_NAME_TOO_LONG);

					SyncFileService.update(targetSyncFile);
				}
			}
		}
	}

	protected void queueSyncFile(long parentFolderId, SyncFile syncFile) {
		List<SyncFile> syncFiles = _dependentSyncFilesMap.get(parentFolderId);

		if (syncFiles == null) {
			syncFiles = new ArrayList<>();

			_dependentSyncFilesMap.put(parentFolderId, syncFiles);
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
		sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_UPDATED_REMOTE);
		sourceSyncFile.setVersion(targetSyncFile.getVersion());
		sourceSyncFile.setVersionId(targetSyncFile.getVersionId());

		SyncFileService.update(sourceSyncFile);

		Path filePath = Paths.get(targetSyncFile.getFilePathName());

		if (filePathChanged && !Files.exists(filePath)) {
			if (targetSyncFile.isFolder()) {
				Path targetFilePath = Paths.get(filePathName);

				Files.createDirectories(targetFilePath);

				sourceSyncFile.setState(SyncFile.STATE_SYNCED);

				SyncFileService.update(sourceSyncFile);

				FileUtil.writeFileKey(
					targetFilePath,
					String.valueOf(sourceSyncFile.getSyncFileId()));
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

			SyncFileService.update(sourceSyncFile);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		GetSyncDLObjectUpdateHandler.class);

	private final Map<Long, List<SyncFile>> _dependentSyncFilesMap =
		new HashMap<>();

}