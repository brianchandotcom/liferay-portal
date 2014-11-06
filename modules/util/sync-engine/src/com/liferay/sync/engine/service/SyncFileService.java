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

import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncFileModelListener;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.OSDetector;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;

import java.util.Collections;
import java.util.List;

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
			SyncFile.TYPE_FILE);

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
			SyncFile.TYPE_FOLDER);

		// Remote sync file

		FileEventUtil.addFolder(
			parentFolderId, repositoryId, syncAccountId, name, syncFile);

		return syncFile;
	}

	public static SyncFile addSyncFile(
			String changeLog, String checksum, String description,
			String filePathName, String mimeType, String name,
			long parentFolderId, long repositoryId, int state,
			long syncAccountId, String type)
		throws Exception {

		SyncFile syncFile = new SyncFile();

		syncFile.setChangeLog(changeLog);
		syncFile.setChecksum(checksum);
		syncFile.setDescription(description);
		syncFile.setFilePathName(filePathName);
		syncFile.setLocalSyncTime(System.currentTimeMillis());
		syncFile.setMimeType(mimeType);
		syncFile.setName(name);
		syncFile.setParentFolderId(parentFolderId);
		syncFile.setRepositoryId(repositoryId);
		syncFile.setState(state);
		syncFile.setSyncAccountId(syncAccountId);
		syncFile.setType(type);

		_syncFilePersistence.create(syncFile);

		updateFileKeySyncFile(syncFile);

		IODeltaUtil.checksums(syncFile);

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

	public static SyncFile deleteFileSyncFile(
			long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		if (Files.exists(Paths.get(syncFile.getFilePathName()))) {
			return syncFile;
		}

		syncFile.setUiEvent(SyncFile.UI_EVENT_DELETED_LOCAL);

		deleteSyncFile(syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.deleteFile(syncAccountId, syncFile);
		}

		return syncFile;
	}

	public static SyncFile deleteFolderSyncFile(
			long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		if (Files.exists(Paths.get(syncFile.getFilePathName()))) {
			return syncFile;
		}

		syncFile.setUiEvent(SyncFile.UI_EVENT_DELETED_LOCAL);

		deleteSyncFile(syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.deleteFolder(syncAccountId, syncFile);
		}

		return syncFile;
	}

	public static void deleteSyncFile(SyncFile syncFile) {
		deleteSyncFile(syncFile, true);
	}

	public static void deleteSyncFile(SyncFile syncFile, boolean notify) {
		try {

			// Sync file

			_syncFilePersistence.delete(syncFile, notify);

			// Sync files

			if (!syncFile.isFolder() || (syncFile.getTypePK() == 0)) {
				return;
			}

			List<SyncFile> childSyncFiles = findSyncFiles(
				syncFile.getTypePK(), syncFile.getSyncAccountId());

			for (SyncFile childSyncFile : childSyncFiles) {
				if (childSyncFile.isFolder()) {
					deleteSyncFile(childSyncFile);
				}
				else {
					childSyncFile.setUiEvent(syncFile.getUiEvent());

					_syncFilePersistence.delete(childSyncFile);
				}
			}
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

	public static SyncFile fetchSyncFileByFileKey(
		String fileKey, long syncAccountId) {

		if ((fileKey == null) || fileKey.equals("")) {
			return null;
		}

		try {
			return _syncFilePersistence.fetchByFK_S(fileKey, syncAccountId);
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
		long syncAccountId, int uiEvent) {

		try {
			return _syncFilePersistence.findByS_U(syncAccountId, uiEvent);
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
			return _syncFilePersistence.findByF_L(filePathName, localSyncTime);
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

	public static SyncFile moveFileSyncFile(
			Path filePath, long folderId, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

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

	public static SyncFile resyncFolder(SyncFile syncFile) throws Exception {

		// Sync file

		syncFile.setState(SyncFile.STATE_SYNCED);

		update(syncFile);

		// Sync files

		resyncChildSyncFiles(syncFile);

		// Remote

		FileEventUtil.resyncFolder(syncFile.getSyncAccountId(), syncFile);

		return syncFile;
	}

	public static void unregisterModelListener(
		ModelListener<SyncFile> modelListener) {

		_syncFilePersistence.unregisterModelListener(modelListener);
	}

	public static SyncFile unsyncFolder(String filePathName) throws Exception {

		// Sync file

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePathName);

		if (syncFile == null) {
			return addSyncFile(
				null, null, null, filePathName, null, null, 0, 0,
				SyncFile.STATE_UNSYNCED, 0, null);
		}

		syncFile.setState(SyncFile.STATE_UNSYNCED);

		update(syncFile);

		// Sync files

		unsyncChildSyncFiles(syncFile);

		return syncFile;
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

	public static SyncFile updateFileKeySyncFile(SyncFile syncFile) {
		if (OSDetector.isWindows()) {
			Path filePath = Paths.get(syncFile.getFilePathName());

			FileUtil.writeFileKey(
				filePath, String.valueOf(syncFile.getSyncFileId()));

			syncFile.setFileKey(String.valueOf(syncFile.getSyncFileId()));
		}
		else {
			syncFile.setFileKey(
				FileUtil.getFileKey(syncFile.getFilePathName()));
		}

		return update(syncFile);
	}

	public static SyncFile updateFileSyncFile(
			Path filePath, long syncAccountId, SyncFile syncFile)
		throws Exception {

		// Local sync file

		if (OSDetector.isWindows()) {
			FileUtil.writeFileKey(
				filePath, String.valueOf(syncFile.getSyncFileId()));
		}

		Path deltaFilePath = null;

		String name = _getName(filePath, syncFile);
		String sourceChecksum = syncFile.getChecksum();
		String sourceFileName = syncFile.getName();
		String sourceVersion = syncFile.getVersion();
		String targetChecksum = FileUtil.getChecksum(filePath);

		if (!sourceChecksum.equals(targetChecksum) &&
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

		update(syncFile);

		// Remote sync file

		if ((syncFile.getState() != SyncFile.STATE_ERROR) &&
			(syncFile.getState() != SyncFile.STATE_UNSYNCED)) {

			FileEventUtil.updateFile(
				filePath, syncAccountId, syncFile, deltaFilePath, name,
				sourceChecksum, sourceFileName, sourceVersion, targetChecksum);
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

		if (syncFile.isFile() || (syncFile.getTypePK() == 0)) {
			return syncFile;
		}

		List<SyncFile> childSyncFiles = findSyncFiles(
			syncFile.getTypePK(), syncFile.getSyncAccountId());

		for (SyncFile childSyncFile : childSyncFiles) {
			String childFilePathName = childSyncFile.getFilePathName();

			childFilePathName = childFilePathName.replace(
				sourceFilePathName, targetFilePathName);

			if (childSyncFile.isFolder()) {
				updateSyncFile(
					Paths.get(childFilePathName),
					childSyncFile.getParentFolderId(), childSyncFile);
			}
			else {
				childSyncFile.setFilePathName(childFilePathName);

				update(childSyncFile);
			}
		}

		return syncFile;
	}

	protected static void resyncChildSyncFiles(SyncFile syncFile)
		throws Exception {

		List<SyncFile> childSyncFiles = _syncFilePersistence.queryForEq(
			"parentFolderId", syncFile.getTypePK());

		for (SyncFile childSyncFile : childSyncFiles) {
			childSyncFile.setState(SyncFile.STATE_SYNCED);

			update(childSyncFile);

			if (childSyncFile.isFolder()) {
				resyncChildSyncFiles(childSyncFile);
			}
		}
	}

	protected static void unsyncChildSyncFiles(SyncFile syncFile)
		throws Exception {

		List<SyncFile> childSyncFiles = _syncFilePersistence.queryForEq(
			"parentFolderId", syncFile.getTypePK());

		for (SyncFile childSyncFile : childSyncFiles) {
			childSyncFile.setState(SyncFile.STATE_UNSYNCED);

			update(childSyncFile);

			if (childSyncFile.isFolder()) {
				unsyncChildSyncFiles(childSyncFile);
			}
		}
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