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

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncFileModelListener;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.MSOfficeFileUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class SyncFileService {

	public static SyncFile addFileSyncFile(
			Path filePath, long folderId, long repositoryId, long syncAccountId)
		throws Exception {

		// Local sync file

		if (Files.notExists(filePath)) {
			return null;
		}

		String checksum = FileUtil.getChecksum(filePath);
		String name = String.valueOf(filePath.getFileName());
		String mimeType = Files.probeContentType(filePath);

		SyncFile syncFile = addSyncFile(
			null, checksum, null, filePath.toString(), mimeType, name, folderId,
			repositoryId, SyncFile.STATE_SYNCED, syncAccountId,
			SyncFile.TYPE_FILE, true);

		// Remote sync file

		FileEventUtil.addFile(
			filePath, folderId, repositoryId, syncAccountId, checksum, name,
			mimeType, syncFile);

		return syncFile;
	}

	public static SyncFile addFolderSyncFile(
			Path filePath, long parentFolderId, long repositoryId,
			long syncAccountId)
		throws Exception {

		// Local sync file

		if (Files.notExists(filePath)) {
			return null;
		}

		String name = String.valueOf(filePath.getFileName());

		SyncFile syncFile = addSyncFile(
			null, null, null, filePath.toString(),
			Files.probeContentType(filePath), name, parentFolderId,
			repositoryId, SyncFile.STATE_SYNCED, syncAccountId,
			SyncFile.TYPE_FOLDER, false);

		// Remote sync file

		FileEventUtil.addFolder(
			parentFolderId, repositoryId, syncAccountId, name, syncFile);

		return syncFile;
	}

	public static SyncFile addSyncFile(
			String changeLog, String checksum, String description,
			String filePathName, String mimeType, String name,
			long parentFolderId, long repositoryId, int state,
			long syncAccountId, String type, boolean createChecksums)
		throws Exception {

		SyncFile syncFile = new SyncFile();

		syncFile.setChangeLog(changeLog);
		syncFile.setChecksum(checksum);
		syncFile.setDescription(description);
		syncFile.setFilePathName(filePathName);

		if (MSOfficeFileUtil.isLegacyExcelFile(Paths.get(filePathName))) {
			syncFile.setLocalExtraSetting(
				"lastSavedDate",
				MSOfficeFileUtil.getLastSavedDate(Paths.get(filePathName)));
		}

		syncFile.setLocalSyncTime(System.currentTimeMillis());
		syncFile.setMimeType(mimeType);
		syncFile.setName(name);
		syncFile.setParentFolderId(parentFolderId);
		syncFile.setRepositoryId(repositoryId);
		syncFile.setSize(Files.size(Paths.get(filePathName)));
		syncFile.setState(state);
		syncFile.setSyncAccountId(syncAccountId);
		syncFile.setType(type);

		_syncFilePersistence.create(syncFile);

		FileKeyUtil.writeFileKey(
			Paths.get(filePathName), String.valueOf(syncFile.getSyncFileId()),
			true);

		if (createChecksums) {
			IODeltaUtil.checksums(syncFile);
		}

		return syncFile;
	}

	public static SyncFile cancelCheckOutSyncFile(
			long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		update(syncFile);

		// Remote sync file

		FileEventUtil.cancelCheckOut(syncAccountId, syncFile);

		return syncFile;
	}

	public static SyncFile checkInSyncFile(
			long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		update(syncFile);

		// Remote sync file

		FileEventUtil.checkInFile(syncAccountId, syncFile);

		return syncFile;
	}

	public static SyncFile checkOutSyncFile(
			long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		update(syncFile);

		// Remote sync file

		FileEventUtil.checkOutFile(syncAccountId, syncFile);

		return syncFile;
	}

	public static SyncFile copySyncFile(
			SyncFile sourceSyncFile, Path filePath, long folderId,
			long repositoryId, long syncAccountId)
		throws Exception {

		// Local sync file

		if (Files.notExists(filePath)) {
			return null;
		}

		String checksum = FileUtil.getChecksum(filePath);
		String name = String.valueOf(filePath.getFileName());
		String mimeType = Files.probeContentType(filePath);

		SyncFile targetSyncFile = addSyncFile(
			null, checksum, null, filePath.toString(), mimeType, name, folderId,
			repositoryId, SyncFile.STATE_SYNCED, syncAccountId,
			SyncFile.TYPE_FILE, false);

		IODeltaUtil.copyChecksums(sourceSyncFile, targetSyncFile);

		// Remote sync file

		FileEventUtil.copyFile(
			sourceSyncFile.getTypePK(), folderId, repositoryId, syncAccountId,
			name, targetSyncFile);

		return targetSyncFile;
	}

	public static void deleteSyncFile(SyncFile syncFile) {
		deleteSyncFile(syncFile, true);
	}

	public static void deleteSyncFile(
		final SyncFile syncFile, final boolean notify) {

		try {

			// Sync file

			doDeleteSyncFile(syncFile, notify);

			// Sync files

			if (!syncFile.isFolder()) {
				return;
			}

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					List<SyncFile> childSyncFiles =
						_syncFilePersistence.findByParentFilePathName(
							syncFile.getFilePathName());

					for (SyncFile childSyncFile : childSyncFiles) {
						childSyncFile.setUiEvent(syncFile.getUiEvent());

						doDeleteSyncFile(childSyncFile, notify);
					}

					return null;
				}

			};

			_syncFilePersistence.callBatchTasks(callable);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static SyncFile fetchSyncFile(long syncFileId) {
		try {
			return _syncFilePersistence.queryForId(syncFileId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncFile fetchSyncFile(
		long repositoryId, long syncAccountId, long typePK) {

		try {
			return _syncFilePersistence.fetchByR_S_T(
				repositoryId, syncAccountId, typePK);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncFile fetchSyncFile(String filePathName) {
		try {
			return _syncFilePersistence.fetchByFilePathName(filePathName);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncFile fetchSyncFile(String checksum, int state)
		throws SQLException {

		try {
			return _syncFilePersistence.fetchByC_S(checksum, state);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static List<SyncFile> findSyncFiles(long syncAccountId) {
		try {
			return _syncFilePersistence.findBySyncAccountId(syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncFile> findSyncFiles(
		long repositoryId, int state, long syncAccountId) {

		try {
			return _syncFilePersistence.findByR_S_S(
				repositoryId, state, syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncFile> findSyncFiles(
		long syncAccountId, int uiEvent, String orderByColumn,
		boolean ascending) {

		try {
			return _syncFilePersistence.findByS_U(
				syncAccountId, uiEvent, orderByColumn, ascending);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncFile> findSyncFiles(
		long parentFolderId, long syncAccountId) {

		try {
			return _syncFilePersistence.findByP_S(
				parentFolderId, syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncFile> findSyncFiles(
		String filePathName, long localSyncTime) {

		try {
			return _syncFilePersistence.findByPF_L(filePathName, localSyncTime);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncFile> findSyncFilesByRepositoryId(
		long repositoryId, long syncAccountId) {

		try {
			return _syncFilePersistence.findByR_S(repositoryId, syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static SyncFilePersistence getSyncFilePersistence() {
		if (_syncFilePersistence != null) {
			return _syncFilePersistence;
		}

		try {
			_syncFilePersistence = new SyncFilePersistence();

			registerModelListener(new SyncFileModelListener());

			return _syncFilePersistence;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static long getSyncFilesCount(int uiEvent) {
		try {
			return _syncFilePersistence.countByUIEvent(uiEvent);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return 0;
		}
	}

	public static long getSyncFilesCount(long syncAccountId, int uiEvent) {
		try {
			return _syncFilePersistence.countByS_U(syncAccountId, uiEvent);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return 0;
		}
	}

	public static long getSyncFilesCount(
		long syncAccountId, String type, int uiEvent) {

		try {
			return _syncFilePersistence.countByS_T_U(
				syncAccountId, type, uiEvent);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return 0;
		}
	}

	public static SyncFile moveFileSyncFile(
			Path filePath, long folderId, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		SyncFile targetSyncFile = fetchSyncFile(filePath.toString());

		if (targetSyncFile != null) {
			deleteSyncFile(targetSyncFile, false);
		}

		syncFile.setFilePathName(filePath.toString());
		syncFile.setParentFolderId(folderId);

		update(syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.moveFile(folderId, syncAccountId, syncFile);
		}

		return syncFile;
	}

	public static SyncFile moveFolderSyncFile(
			Path filePath, long parentFolderId, long syncAccountId,
			SyncFile syncFile)
		throws Exception {

		// Local sync file

		SyncFile targetSyncFile = fetchSyncFile(filePath.toString());

		if (targetSyncFile != null) {
			deleteSyncFile(targetSyncFile, false);
		}

		updateSyncFile(filePath, parentFolderId, syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.moveFolder(parentFolderId, syncAccountId, syncFile);
		}

		return syncFile;
	}

	public static void registerModelListener(
		ModelListener<SyncFile> modelListener) {

		_syncFilePersistence.registerModelListener(modelListener);
	}

	public static SyncFile renameFileSyncFile(
			Path filePath, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		String name = _getName(filePath, syncFile);
		String sourceFileName = syncFile.getName();
		long sourceVersionId = syncFile.getVersionId();

		syncFile.setFilePathName(filePath.toString());
		syncFile.setName(name);

		update(syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.updateFile(
				filePath, syncAccountId, syncFile, null, name,
				syncFile.getChecksum(), sourceFileName, sourceVersionId,
				syncFile.getChecksum());
		}

		return syncFile;
	}

	public static SyncFile renameFolderSyncFile(
			Path filePath, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		updateSyncFile(filePath, syncFile.getParentFolderId(), syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.updateFolder(filePath, syncAccountId, syncFile);
		}

		return syncFile;
	}

	public static void renameSyncFiles(
		String sourceFilePathName, String targetFilePathName) {

		try {
			_syncFilePersistence.renameByParentFilePathName(
				sourceFilePathName, targetFilePathName);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static SyncFile resyncFolder(SyncFile syncFile) throws Exception {
		if (syncFile.getState() != SyncFile.STATE_UNSYNCED) {
			return syncFile;
		}

		setStatuses(syncFile, SyncFile.STATE_SYNCED, SyncFile.UI_EVENT_NONE);

		// Remote

		FileEventUtil.resyncFolder(syncFile.getSyncAccountId(), syncFile);

		return syncFile;
	}

	public static void setStatuses(
		SyncFile parentSyncFile, int state, int uiEvent) {

		try {
			parentSyncFile.setState(state);
			parentSyncFile.setUiEvent(uiEvent);

			update(parentSyncFile);

			if (parentSyncFile.isFolder()) {
				_syncFilePersistence.updateByParentFilePathName(
					parentSyncFile.getFilePathName(), state, uiEvent);
			}
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static void unregisterModelListener(
		ModelListener<SyncFile> modelListener) {

		_syncFilePersistence.unregisterModelListener(modelListener);
	}

	public static SyncFile unsyncFolder(
			long syncAccountId, SyncFile targetSyncFile)
		throws Exception {

		if (targetSyncFile.getState() == SyncFile.STATE_UNSYNCED) {
			return targetSyncFile;
		}

		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			targetSyncFile.getRepositoryId(), syncAccountId,
			targetSyncFile.getParentFolderId());

		if (parentSyncFile == null) {
			return targetSyncFile;
		}

		String filePathName = FileUtil.getFilePathName(
			parentSyncFile.getFilePathName(),
			FileUtil.getSanitizedFileName(targetSyncFile.getName(), null));

		SyncFile sourceSyncFile = SyncFileService.fetchSyncFile(filePathName);

		if (sourceSyncFile == null) {
			targetSyncFile.setFilePathName(filePathName);
			targetSyncFile.setModifiedTime(0);
			targetSyncFile.setState(SyncFile.STATE_UNSYNCED);
			targetSyncFile.setSyncAccountId(syncAccountId);

			return update(targetSyncFile);
		}

		sourceSyncFile.setModifiedTime(0);

		setStatuses(
			sourceSyncFile, SyncFile.STATE_UNSYNCED, SyncFile.UI_EVENT_NONE);

		return sourceSyncFile;
	}

	public static void unsyncFolders(
			long syncAccountId, List<SyncFile> targetSyncFiles)
		throws Exception {

		for (SyncFile targetSyncFile : targetSyncFiles) {
			unsyncFolder(syncAccountId, targetSyncFile);
		}
	}

	public static SyncFile update(SyncFile syncFile) {
		try {
			_syncFilePersistence.createOrUpdate(syncFile);

			return syncFile;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncFile updateFileSyncFile(
			Path filePath, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		FileKeyUtil.writeFileKey(
			filePath, String.valueOf(syncFile.getSyncFileId()), true);

		Path deltaFilePath = null;

		if (MSOfficeFileUtil.isLegacyExcelFile(filePath)) {
			syncFile.setLocalExtraSetting(
				"lastSavedDate", MSOfficeFileUtil.getLastSavedDate(filePath));
		}

		String name = _getName(filePath, syncFile);
		String sourceChecksum = syncFile.getChecksum();
		String sourceFileName = syncFile.getName();
		long sourceVersionId = syncFile.getVersionId();
		String targetChecksum = FileUtil.getChecksum(filePath);

		if (!FileUtil.checksumsEqual(sourceChecksum, targetChecksum) &&
			!IODeltaUtil.isIgnoredFilePatchingExtension(syncFile)) {

			deltaFilePath = Files.createTempFile(
				String.valueOf(filePath.getFileName()), ".tmp");

			deltaFilePath = IODeltaUtil.delta(
				filePath, IODeltaUtil.getChecksumsFilePath(syncFile),
				deltaFilePath);

			IODeltaUtil.checksums(syncFile);
		}

		syncFile.setChecksum(targetChecksum);
		syncFile.setFilePathName(filePath.toString());
		syncFile.setName(name);
		syncFile.setSize(Files.size(filePath));

		update(syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.updateFile(
				filePath, syncAccountId, syncFile, deltaFilePath, name,
				sourceChecksum, sourceFileName, sourceVersionId,
				targetChecksum);
		}

		return syncFile;
	}

	public static SyncFile updateFolderSyncFile(
			Path filePath, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		updateSyncFile(filePath, syncFile.getParentFolderId(), syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.updateFolder(filePath, syncAccountId, syncFile);
		}

		return syncFile;
	}

	public static SyncFile updateSyncFile(
		Path filePath, long parentFolderId, SyncFile syncFile) {

		// Sync file

		String sourceFilePathName = syncFile.getFilePathName();
		String targetFilePathName = filePath.toString();

		syncFile.setFilePathName(targetFilePathName);
		syncFile.setLocalSyncTime(System.currentTimeMillis());
		syncFile.setName(String.valueOf(filePath.getFileName()));
		syncFile.setParentFolderId(parentFolderId);

		update(syncFile);

		// Sync files

		if (syncFile.isFolder()) {
			renameSyncFiles(sourceFilePathName, targetFilePathName);
		}

		return syncFile;
	}

	protected static void doDeleteSyncFile(SyncFile syncFile, boolean notify)
		throws SQLException {

		if (syncFile.isFile()) {
			final Path checksumsFilePath = IODeltaUtil.getChecksumsFilePath(
				syncFile);
			final Path tempFilePath = FileUtil.getTempFilePath(syncFile);

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					FileUtil.deleteFile(checksumsFilePath);
					FileUtil.deleteFile(tempFilePath);
				}

			};

			ExecutorService executorService = SyncEngine.getExecutorService();

			executorService.execute(runnable);
		}

		_syncFilePersistence.delete(syncFile, notify);
	}

	private static String _getName(Path filePath, SyncFile syncFile) {
		String name = String.valueOf(filePath.getFileName());

		String sanitizedFileName = FileUtil.getSanitizedFileName(
			syncFile.getName(), syncFile.getExtension());

		if (sanitizedFileName.equals(
				FileUtil.getSanitizedFileName(name, syncFile.getExtension()))) {

			name = syncFile.getName();
		}

		return name;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncFileService.class);

	private static SyncFilePersistence _syncFilePersistence =
		getSyncFilePersistence();

}