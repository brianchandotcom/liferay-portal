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

package com.liferay.sync.engine.session;

import com.liferay.sync.encryptor.SyncEncryptor;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.util.ServerInfo;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class SessionManager {

	public static Session getSession(long syncAccountId) {
		return getSession(syncAccountId, false);
	}

	public static synchronized Session getSession(
		long syncAccountId, boolean unlimitedConnections) {

		Session session = null;

		if (unlimitedConnections) {
			session = _sessions.get(syncAccountId + "#MAX");
		}
		else {
			session = _sessions.get(String.valueOf(syncAccountId));
		}

		if (session != null) {
			if (ServerInfo.supportsDeviceRegistration(syncAccountId)) {
				SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
					syncAccountId);

				session.addHeader("Sync-UUID", syncAccount.getUuid());
			}

			return session;
		}

		try {
			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				syncAccountId);

			URL url = new URL(syncAccount.getUrl());

			int maxConnections = 0;

			if (unlimitedConnections) {
				maxConnections = Integer.MAX_VALUE;
			}
			else {
				maxConnections = syncAccount.getMaxConnections();
			}

			if (syncAccount.isOAuthEnabled()) {
				session = new Session(
					url, syncAccount.getOAuthConsumerKey(),
					syncAccount.getOAuthConsumerSecret(),
					syncAccount.getOAuthToken(),
					SyncEncryptor.decrypt(syncAccount.getOAuthTokenSecret()),
					syncAccount.isTrustSelfSigned(), maxConnections);
			}
			else {
				session = new Session(
					url, syncAccount.getLogin(),
					SyncEncryptor.decrypt(syncAccount.getPassword()),
					syncAccount.isTrustSelfSigned(), maxConnections);
			}

			if (ServerInfo.supportsDeviceRegistration(syncAccountId)) {
				session.addHeader("Sync-UUID", syncAccount.getUuid());
			}

			if (unlimitedConnections) {
				_sessions.put(syncAccountId + "#MAX", session);
			}
			else {
				session.startTrackTransferRate();

				_sessions.put(String.valueOf(syncAccountId), session);
			}

			return session;
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return null;
		}
	}

	public static void removeSession(long syncAccountId) {
		Session session = _sessions.remove(String.valueOf(syncAccountId));

		if (session != null) {
			removeSession(session);
		}

		session = _sessions.remove(syncAccountId + "#MAX");

		if (session != null) {
			removeSession(session);
		}
	}

	protected static void removeSession(Session session) {
		session.stopTrackTransferRate();

		ExecutorService executorService = session.getExecutorService();

		executorService.shutdownNow();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SessionManager.class);

	private static final Map<String, Session> _sessions = new HashMap<>();

}