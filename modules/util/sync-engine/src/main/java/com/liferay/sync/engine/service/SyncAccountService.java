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

import com.liferay.sync.engine.model.ModelListener;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncAccountModelListener;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncUser;
import com.liferay.sync.engine.service.persistence.SyncAccountPersistence;
import com.liferay.sync.engine.util.Encryptor;
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
			String password, int pollInterval, SyncSite[] syncSites,
			SyncUser syncUser, boolean trustSelfSigned, String url)
		throws Exception {

		return addSyncAccount(
			filePathName, login, maxConnections, "", "", false, "", "",
			password, pollInterval, syncSites, syncUser, trustSelfSigned, url);
	}

	public static SyncAccount addSyncAccount(
			String filePathName, String login, int maxConnections,
			String oAuthConsumerKey, String oAuthConsumerSecret,
			boolean oAuthEnabled, String oAuthToken, String oAuthTokenSecret,
			String password, int pollInterval, SyncSite[] syncSites,
			SyncUser syncUser, boolean trustSelfSigned, String url)
		throws Exception {

		// Sync account

		SyncAccount syncAccount = new SyncAccount();

		syncAccount.setFilePathName(filePathName);
		syncAccount.setLogin(login);
		syncAccount.setMaxConnections(maxConnections);
		syncAccount.setOAuthConsumerKey(oAuthConsumerKey);
		syncAccount.setOAuthConsumerSecret(oAuthConsumerSecret);
		syncAccount.setOAuthEnabled(oAuthEnabled);
		syncAccount.setOAuthToken(oAuthToken);
		syncAccount.setOAuthTokenSecret(Encryptor.encrypt(oAuthTokenSecret));
		syncAccount.setPassword(Encryptor.encrypt(password));
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
			null, null, null, filePathName, null,
			String.valueOf(filePath.getFileName()), 0, 0, SyncFile.STATE_SYNCED,
			syncAccount.getSyncAccountId(), SyncFile.TYPE_SYSTEM, false);

		// Sync sites

		if (syncSites != null) {
			for (SyncSite syncSite : syncSites) {

				// Sync site

				String syncSiteName = syncSite.getName();

				if (!FileUtil.isValidFileName(syncSiteName)) {
					syncSiteName = String.valueOf(syncSite.getGroupId());
				}

				syncSite.setFilePathName(
					FileUtil.getFilePathName(
						syncAccount.getFilePathName(), syncSiteName));

				syncSite.setRemoteSyncTime(-1);
				syncSite.setSyncAccountId(syncAccount.getSyncAccountId());

				SyncSiteService.update(syncSite);

				// Sync file

				if (syncSite.isActive() &&
					!Files.exists(Paths.get(syncSite.getFilePathName()))) {

					Files.createDirectories(
						Paths.get(syncSite.getFilePathName()));
				}

				SyncFileService.addSyncFile(
					null, null, null, syncSite.getFilePathName(), null,
					syncSite.getName(), 0, syncSite.getGroupId(),
					SyncFile.STATE_SYNCED, syncSite.getSyncAccountId(),
					SyncFile.TYPE_SYSTEM, false);
			}
		}

		// Sync user

		if (syncUser != null) {
			syncUser.setSyncAccountId(syncAccount.getSyncAccountId());

			SyncUserService.update(syncUser);
		}

		return syncAccount;
	}

	public static void deleteSyncAccount(long syncAccountId) {
		try {

			// Sync account

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
					FileUtils.moveDirectory(
						sourceFilePath.toFile(), targetFilePath.toFile());

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