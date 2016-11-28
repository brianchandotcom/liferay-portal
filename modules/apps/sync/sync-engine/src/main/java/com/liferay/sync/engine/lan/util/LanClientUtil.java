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

package com.liferay.sync.engine.lan.util;

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncLanClient;
import com.liferay.sync.engine.model.SyncProp;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncPropService;
import com.liferay.sync.engine.service.SyncSiteService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Dennis Ju
 */
public class LanClientUtil {

	public static SyncLanClient createSyncLanClient(int port) throws Exception {
		SyncLanClient syncLanClient = new SyncLanClient();

		Map<String, Set<Long>> endpoints = new HashMap<>();

		for (SyncAccount syncAccount : SyncAccountService.findAll()) {
			if (!syncAccount.isActive() || !syncAccount.isLanEnabled()) {
				continue;
			}

			endpoints.put(
				syncAccount.getLanServerUuid(),
				SyncSiteService.getActiveGroupIds(
					syncAccount.getSyncAccountId()));
		}

		syncLanClient.setEndpoints(endpoints);

		syncLanClient.setPort(port);
		syncLanClient.setSyncLanClientUuid(getSyncLanClientUuid());

		return syncLanClient;
	}

	public static String getSNIHostname(String lanServerUuid) {
		return lanServerUuid + ".com";
	}

	public static String getSyncLanClientUuid() throws Exception {
		String syncLanClientUuid = SyncPropService.getValue(
			SyncProp.KEY_SYNC_LAN_CLIENT_UUID);

		if (syncLanClientUuid == null) {
			UUID uuid = UUID.randomUUID();

			syncLanClientUuid = uuid.toString();

			SyncPropService.updateSyncProp(
				SyncProp.KEY_SYNC_LAN_CLIENT_UUID, syncLanClientUuid);
		}

		return syncLanClientUuid;
	}

}