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
import com.liferay.sync.engine.document.library.util.FileEventUtil;
import com.liferay.sync.engine.document.library.util.comparator.SyncFileFilePathNameComparator;
import com.liferay.sync.engine.file.system.Watcher;
import com.liferay.sync.engine.file.system.util.WatcherManager;
import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncFileModelListener;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.IODeltaUtil;
import com.liferay.sync.engine.util.MSOfficeFileUtil;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.sql.SQLException;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

		if (FileUtil.notExists(filePath)) {
			return null;
		}

		String checksum = FileUtil.getChecksum(filePath);
		String name = String.valueOf(filePath.getFileName());
		String mimeType = Files.probeContentType(filePath);
		long size = Files.size(filePath);

		SyncFile syncFile = addSyncFile(
			null, checksum, true, null, filePath.toString(), mimeType, name,
			folderId, repositoryId, size, SyncFile.STATE_SYNCED, syncAccountId,
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

		if (FileUtil.notExists(filePath)) {
			return null;
		}

		String name = String.valueOf(filePath.getFileName());

		SyncFile syncFile = addSyncFile(
			null, null, false, null, filePath.toString(),
			Files.probeContentType(filePath), name, parentFolderId,
			repositoryId, 0, SyncFile.STATE_SYNCED, syncAccountId,
			SyncFile.TYPE_FOLDER);

		// Remote sync file

		FileEventUtil.addFolder(
			parentFolderId, repositoryId, syncAccountId, name, syncFile);

		return syncFile;
	}

	public static SyncFile addSyncFile(
			String changeLog, String checksum, boolean createChecksums,
			String description, String filePathName, String mimeType,
			String name, long parentFolderId, long repositoryId, long size,
			int state, long syncAccountId, String type)
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
		syncFile.setSize(size);
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

		if (FileUtil.notExists(filePath)) {
			return null;
		}

		String checksum = FileUtil.getChecksum(filePath);
		String name = String.valueOf(filePath.getFileName());
		String mimeType = Files.probeContentType(filePath);
		long size = Files.size(filePath);

		SyncFile targetSyncFile = addSyncFile(
			null, checksum, false, null, filePath.toString(), mimeType, name,
			folderId, repositoryId, size, SyncFile.STATE_SYNCED, syncAccountId,
			SyncFile.TYPE_FILE);

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

	public static void deleteSyncFile(SyncFile syncFile, boolean notify) {
		try {

			// Sync file

			doDeleteSyncFile(syncFile, notify);

			// Sync files

			deleteSyncFiles(syncFile, notify);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static void deleteSyncFiles(
		final SyncFile syncFile, final boolean notify) {

		try {
			if (!syncFile.isFolder()) {
				return;
			}

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					List<SyncFile> childSyncFiles = findSyncFiles(
						syncFile.getFilePathName());

					for (SyncFile childSyncFile : childSyncFiles) {
						if (childSyncFile.isSystem()) {
							continue;
						}

						childSyncFile.setModifiedTime(
							syncFile.getModifiedTime());
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

	public static SyncFile fetchSyncFile(
		long repositoryId, long syncAccountId, long typePK, long versionId) {

		try {
			return _syncFilePersistence.fetchByR_S_T_V(
				repositoryId, syncAccountId, typePK, versionId);
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

	public static List<SyncFile> findSyncFiles(int state, long syncAccountId) {
		try {
			return _syncFilePersistence.findByS_S(state, syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
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
		long repositoryId, long syncAccountId, String type) {

		try {
			return _syncFilePersistence.findByR_S_T(
				repositoryId, syncAccountId, type);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static List<SyncFile> findSyncFiles(String parentFilePathName) {
		try {
			return _syncFilePersistence.findByParentFilePathName(
				parentFilePathName);
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

	public static long getSyncFilesCount(Integer... uiEvents) {
		try {
			return _syncFilePersistence.countByUIEvents(uiEvents);
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

	public static boolean hasSyncFiles(long syncAccountId, int uiEvent) {
		try {
			SyncFile syncFile = _syncFilePersistence.fetchByS_U_First(
				syncAccountId, uiEvent);

			if (syncFile == null) {
				return false;
			}

			return true;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return false;
		}
	}

	public static boolean hasSyncFiles(
		long repositoryId, int state, long syncAccountId) {

		try {
			SyncFile syncFile = _syncFilePersistence.fetchByR_S_S_First(
				repositoryId, state, syncAccountId);

			if (syncFile == null) {
				return false;
			}

			return true;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return false;
		}
	}

	public static boolean hasSyncFiles(String parentFilePathName, int state) {
		try {
			SyncFile syncFile = _syncFilePersistence.fetchByPF_S_First(
				parentFilePathName, state);

			if (syncFile == null) {
				return false;
			}

			return true;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return false;
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
			!syncFile.isUnsynced()) {

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
			!syncFile.isUnsynced()) {

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

	public static void resyncFolders(
			long syncAccountId, List<SyncFile> syncFiles)
		throws Exception {

		Map<Long, SyncFile> resyncedSyncFileMap = new HashMap<>();

		Set<Long> resyncedSyncFileIds = resyncedSyncFileMap.keySet();

		Collections.sort(syncFiles, _syncFileFilePathNameComparator);

		for (SyncFile syncFile : syncFiles) {
			SyncFile localSyncFile = fetchSyncFile(syncFile.getFilePathName());

			if (localSyncFile != null) {
				if (localSyncFile.getState() != SyncFile.STATE_UNSYNCED) {
					continue;
				}

				syncFile = localSyncFile;
			}
			else {
				syncFile.setSyncAccountId(syncAccountId);
			}

			syncFile.setModifiedTime(0);
			syncFile.setState(SyncFile.STATE_IN_PROGRESS);
			syncFile.setUiEvent(SyncFile.UI_EVENT_RESYNCING);

			update(syncFile);

			if (isAncestorInList(syncFile, resyncedSyncFileIds)) {
				continue;
			}

			resyncedSyncFileMap.put(syncFile.getTypePK(), syncFile);
		}

		// Remote

		for (SyncFile syncFile : resyncedSyncFileMap.values()) {
			FileEventUtil.resyncFolder(syncFile.getSyncAccountId(), syncFile);
		}
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

	public static void unsyncFolders(
			long syncAccountId, List<SyncFile> syncFiles)
		throws Exception {

		Set<Long> unsyncedSyncFileIds = new HashSet<>();

		Collections.sort(syncFiles, _syncFileFilePathNameComparator);

		for (SyncFile syncFile : syncFiles) {
			SyncFile localSyncFile = fetchSyncFile(syncFile.getFilePathName());

			if (localSyncFile != null) {
				if (localSyncFile.getState() == SyncFile.STATE_UNSYNCED) {
					continue;
				}

				syncFile = localSyncFile;
			}
			else {
				syncFile.setSyncAccountId(syncAccountId);
			}

			syncFile.setState(SyncFile.STATE_UNSYNCED);
			syncFile.setUiEvent(SyncFile.UI_EVENT_NONE);

			update(syncFile);

			if (isAncestorInList(syncFile, unsyncedSyncFileIds) ||
				!FileUtil.exists(Paths.get(syncFile.getFilePathName()))) {

				continue;
			}

			final Watcher watcher = WatcherManager.getWatcher(
				syncFile.getSyncAccountId());

			Files.walkFileTree(
				Paths.get(syncFile.getFilePathName()),
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
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						watcher.addDeletedFilePathName(filePath.toString());

						FileUtil.deleteFile(filePath);

						SyncFile childSyncFile = fetchSyncFile(
							filePath.toString());

						if (childSyncFile != null) {
							deleteSyncFile(childSyncFile, false);
						}

						return FileVisitResult.CONTINUE;
					}

				});

			unsyncedSyncFileIds.add(syncFile.getSyncFileId());
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

		if (FileUtil.notExists(filePath)) {
			return null;
		}

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
			!syncFile.isUnsynced()) {

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

	protected static boolean isAncestorInList(
		SyncFile syncFile, Set<Long> folderIds) {

		if (folderIds.contains(syncFile.getParentFolderId())) {
			return true;
		}

		if (syncFile.getParentFolderId() == 0) {
			return false;
		}

		SyncFile parentSyncFile = fetchSyncFile(
			syncFile.getRepositoryId(), syncFile.getSyncAccountId(),
			syncFile.getParentFolderId());

		return isAncestorInList(parentSyncFile, folderIds);
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

	private static final Comparator<SyncFile> _syncFileFilePathNameComparator =
		new SyncFileFilePathNameComparator();
	private static SyncFilePersistence _syncFilePersistence =
		getSyncFilePersistence();

}