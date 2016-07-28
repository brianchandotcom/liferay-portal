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

import com.liferay.sync.encryptor.SyncEncryptor;
import com.liferay.sync.engine.document.library.util.ServerEventUtil;
import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncAccountModelListener;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncUser;
import com.liferay.sync.engine.service.persistence.SyncAccountPersistence;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.OSDetector;

import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class SyncAccountService {

	public static SyncAccount activateSyncAccount(
		long syncAccountId, boolean reset) {

		SyncAccount syncAccount = fetchSyncAccount(syncAccountId);

		syncAccount.setActive(true);

		update(syncAccount);

		if (reset) {
			List<SyncSite> syncSites = SyncSiteService.findSyncSites(
				syncAccountId);

			for (SyncSite syncSite : syncSites) {
				syncSite.setRemoteSyncTime(-1);

				SyncSiteService.update(syncSite);
			}
		}

		return syncAccount;
	}

	public static SyncAccount addSyncAccount(
			String filePathName, String login, int maxConnections,
			String oAuthConsumerKey, String oAuthConsumerSecret,
			boolean oAuthEnabled, String oAuthToken, String oAuthTokenSecret,
			String password, String pluginVersion, int pollInterval,
			Map<SyncSite, List<SyncFile>> ignoredSyncFiles, SyncUser syncUser,
			boolean trustSelfSigned, String url)
		throws Exception {

		// Sync account

		SyncAccount syncAccount = new SyncAccount();

		syncAccount.setFilePathName(filePathName);
		syncAccount.setLogin(login);
		syncAccount.setMaxConnections(maxConnections);
		syncAccount.setPluginVersion(pluginVersion);
		syncAccount.setOAuthConsumerKey(oAuthConsumerKey);
		syncAccount.setOAuthConsumerSecret(oAuthConsumerSecret);
		syncAccount.setOAuthEnabled(oAuthEnabled);
		syncAccount.setOAuthToken(oAuthToken);
		syncAccount.setOAuthTokenSecret(
			SyncEncryptor.encrypt(oAuthTokenSecret));
		syncAccount.setPassword(SyncEncryptor.encrypt(password));
		syncAccount.setPollInterval(pollInterval);
		syncAccount.setTrustSelfSigned(trustSelfSigned);
		syncAccount.setUrl(url);

		_syncAccountPersistence.create(syncAccount);

		// Sync file

		Path filePath = Paths.get(filePathName);

		Path dataFilePath = Files.createDirectories(filePath.resolve(".data"));

		if (OSDetector.isWindows()) {
			Files.setAttribute(dataFilePath, "dos:hidden", true);
		}

		SyncFileService.addSyncFile(
			null, null, false, null, filePathName, null,
			String.valueOf(filePath.getFileName()), 0, 0, 0,
			SyncFile.STATE_SYNCED, syncAccount.getSyncAccountId(),
			SyncFile.TYPE_SYSTEM);

		// Sync sites

		for (Map.Entry<SyncSite, List<SyncFile>> entry :
				ignoredSyncFiles.entrySet()) {

			// Sync site

			SyncSite syncSite = entry.getKey();

			String siteFilePathName = FileUtil.getFilePathName(
				syncAccount.getFilePathName(), syncSite.getSanitizedName());

			syncSite.setFilePathName(siteFilePathName);
			syncSite.setRemoteSyncTime(-1);
			syncSite.setSyncAccountId(syncAccount.getSyncAccountId());

			SyncSiteService.update(syncSite);

			// Sync file

			SyncFileService.addSyncFile(
				null, null, false, null, syncSite.getFilePathName(), null,
				syncSite.getName(), 0, syncSite.getGroupId(), 0,
				SyncFile.STATE_SYNCED, syncSite.getSyncAccountId(),
				SyncFile.TYPE_SYSTEM);

			if (syncSite.isActive() &&
				!Files.exists(Paths.get(syncSite.getFilePathName()))) {

				Files.createDirectories(Paths.get(syncSite.getFilePathName()));
			}

			// Sync files

			for (SyncFile childSyncFile : entry.getValue()) {
				childSyncFile.setModifiedTime(0);
				childSyncFile.setState(SyncFile.STATE_UNSYNCED);
				childSyncFile.setSyncAccountId(syncSite.getSyncAccountId());

				SyncFileService.update(childSyncFile);
			}
		}

		// Sync user

		if (syncUser != null) {
			syncUser.setSyncAccountId(syncAccount.getSyncAccountId());

			SyncUserService.update(syncUser);
		}

		return syncAccount;
	}

	public static SyncAccount addSyncAccount(
			String filePathName, String login, String password, String url)
		throws Exception {

		return SyncAccountService.addSyncAccount(
			filePathName, login, 1, "", "", false, "", "", password, "1.0.0", 5,
			Collections.<SyncSite, List<SyncFile>>emptyMap(), null, false, url);
	}

	public static void deleteSyncAccount(long syncAccountId) {
		deleteSyncAccount(syncAccountId, true);
	}

	public static void deleteSyncAccount(
		long syncAccountId, boolean unregister) {

		try {

			// Sync account

			if (unregister) {
				ServerEventUtil.unregisterSyncDevice(syncAccountId);
			}

			SyncAccount syncAccount = fetchSyncAccount(syncAccountId);

			_syncAccountPersistence.deleteById(syncAccountId);

			// Sync files

			try {
				deleteSyncFiles(syncAccount);
			}
			catch (IOException ioe) {
				_logger.error(ioe.getMessage(), ioe);
			}

			// Sync sites

			List<SyncSite> syncSites = SyncSiteService.findSyncSites(
				syncAccountId);

			for (SyncSite syncSite : syncSites) {
				SyncSiteService.deleteSyncSite(syncSite.getSyncSiteId());
			}

			// Sync user

			SyncUser syncUser = SyncUserService.fetchSyncUser(syncAccountId);

			if (syncUser != null) {
				SyncUserService.deleteSyncUser(syncUser.getSyncUserId());
			}

			// Sync watch events

			SyncWatchEventService.deleteSyncWatchEvents(syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}
		}
	}

	public static SyncAccount fetchSyncAccount(long syncAccountId) {
		try {
			return _syncAccountPersistence.queryForId(syncAccountId);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncAccount fetchSyncAccount(String uuid) {
		try {
			return _syncAccountPersistence.fetchByUuid(uuid);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static SyncAccount fetchSyncAccountByFilePathName(
		String filePathName) {

		try {
			return _syncAccountPersistence.fetchByFilePathName(filePathName);
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static List<SyncAccount> findAll() {
		try {
			return _syncAccountPersistence.queryForAll();
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptyList();
		}
	}

	public static Set<Long> getActiveSyncAccountIds() {
		if (_activeSyncAccountIds != null) {
			return _activeSyncAccountIds;
		}

		try {
			_activeSyncAccountIds = new HashSet<>(
				_syncAccountPersistence.findByActive(true));

			return _activeSyncAccountIds;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return Collections.emptySet();
		}
	}

	public static SyncAccountPersistence getSyncAccountPersistence() {
		if (_syncAccountPersistence != null) {
			return _syncAccountPersistence;
		}

		try {
			_syncAccountPersistence = new SyncAccountPersistence();

			registerModelListener(new SyncAccountModelListener());

			return _syncAccountPersistence;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static void registerModelListener(
		ModelListener<SyncAccount> modelListener) {

		_syncAccountPersistence.registerModelListener(modelListener);
	}

	public static void resetActiveSyncAccountIds() {
		_activeSyncAccountIds = null;
	}

	public static SyncAccount setFilePathName(
		long syncAccountId, String targetFilePathName) {

		// Sync account

		SyncAccount syncAccount = fetchSyncAccount(syncAccountId);

		String sourceFilePathName = syncAccount.getFilePathName();

		syncAccount.setFilePathName(targetFilePathName);

		update(syncAccount);

		// Sync file

		SyncFile syncFile = SyncFileService.fetchSyncFile(sourceFilePathName);

		syncFile.setFilePathName(targetFilePathName);

		SyncFileService.update(syncFile);

		// Sync files

		if (syncFile.isFolder()) {
			SyncFileService.renameSyncFiles(
				sourceFilePathName, targetFilePathName);
		}

		// Sync sites

		FileSystem fileSystem = FileSystems.getDefault();

		List<SyncSite> syncSites = SyncSiteService.findSyncSites(syncAccountId);

		for (SyncSite syncSite : syncSites) {
			String syncSiteFilePathName = syncSite.getFilePathName();

			syncSiteFilePathName = StringUtils.replaceOnce(
				syncSiteFilePathName,
				sourceFilePathName + fileSystem.getSeparator(),
				targetFilePathName + fileSystem.getSeparator());

			syncSite.setFilePathName(syncSiteFilePathName);

			SyncSiteService.update(syncSite);
		}

		return syncAccount;
	}

	public static void unregisterModelListener(
		ModelListener<SyncAccount> modelListener) {

		_syncAccountPersistence.unregisterModelListener(modelListener);
	}

	public static SyncAccount update(SyncAccount syncAccount) {
		try {
			_syncAccountPersistence.createOrUpdate(syncAccount);

			return syncAccount;
		}
		catch (SQLException sqle) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(sqle.getMessage(), sqle);
			}

			return null;
		}
	}

	public static void updateSyncAccountSyncFile(
			Path targetFilePath, long syncAccountId, boolean moveFile)
		throws Exception {

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		if (!moveFile) {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				syncAccount.getFilePathName());

			if (!FileKeyUtil.hasFileKey(
					targetFilePath, syncFile.getSyncFileId())) {

				throw new Exception(
					"Target folder is not the moved sync data folder");
			}
		}

		syncAccount.setActive(false);

		SyncAccountService.update(syncAccount);

		boolean resetFileKeys = false;

		if (moveFile) {
			Path sourceFilePath = Paths.get(syncAccount.getFilePathName());

			try {
				FileUtil.moveFile(sourceFilePath, targetFilePath, false);
			}
			catch (Exception e1) {
				try {
					FileUtils.copyDirectory(
						sourceFilePath.toFile(), targetFilePath.toFile());

					FileUtil.deleteFile(sourceFilePath);

					resetFileKeys = true;
				}
				catch (Exception e2) {
					syncAccount.setActive(true);

					SyncAccountService.update(syncAccount);

					throw e2;
				}
			}
		}

		syncAccount = setFilePathName(syncAccountId, targetFilePath.toString());

		if (resetFileKeys) {
			FileKeyUtil.writeFileKeys(targetFilePath);
		}

		syncAccount.setActive(true);
		syncAccount.setUiEvent(SyncAccount.UI_EVENT_NONE);

		SyncAccountService.update(syncAccount);
	}

	protected static void deleteSyncFiles(SyncAccount syncAccount)
		throws IOException {

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			syncAccount.getFilePathName());

		SyncFileService.deleteSyncFile(syncFile, false);

		Path filePath = Paths.get(syncAccount.getFilePathName());

		if (!Files.exists(filePath)) {
			return;
		}

		FileUtils.deleteDirectory(filePath.toFile());
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncAccountService.class);

	private static Set<Long> _activeSyncAccountIds;
	private static SyncAccountPersistence _syncAccountPersistence =
		getSyncAccountPersistence();

}