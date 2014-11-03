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
import com.liferay.sync.engine.documentlibrary.model.SyncContext;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncUser;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncUserService;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

/**
 * @author Shinn Lok
 */
public class GetSyncContextHandler extends BaseJSONHandler {

	public GetSyncContextHandler(Event event) {
		super(event);
	}

	@Override
	public void processResponse(String response) throws Exception {
		doProcessResponse(response);
	}

	protected SyncContext doProcessResponse(String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		SyncContext syncContext = objectMapper.readValue(
			response, new TypeReference<SyncContext>() {});

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			getSyncAccountId());

		SyncUser remoteSyncUser = syncContext.getSyncUser();

		if (remoteSyncUser == null) {
			throw new HttpResponseException(
				HttpStatus.SC_UNAUTHORIZED, "Authenticated access required");
		}

		SyncUser localSyncUser = SyncUserService.fetchSyncUser(
			syncAccount.getSyncAccountId());

		remoteSyncUser.setSyncAccountId(localSyncUser.getSyncAccountId());
		remoteSyncUser.setSyncUserId(localSyncUser.getSyncUserId());

		SyncUserService.update(remoteSyncUser);

		Map<String, String> portletPreferencesMap =
			syncContext.getPortletPreferencesMap();

		int maxConnections = Integer.parseInt(
			portletPreferencesMap.get(
				SyncContext.PREFERENCE_KEY_MAX_CONNECTIONS));

		syncAccount.setMaxConnections(maxConnections);

		int pollInterval = Integer.parseInt(
			portletPreferencesMap.get(
				SyncContext.PREFERENCE_KEY_POLL_INTERVAL));

		syncAccount.setPollInterval(pollInterval);

		syncAccount.setSocialOfficeInstalled(
			syncContext.isSocialOfficeInstalled());

		SyncAccountService.update(syncAccount);

		return syncContext;
	}

}