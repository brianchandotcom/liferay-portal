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

package com.liferay.portal.lar.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Bruno Farache
 * @author Daniel Kocsis
 */
public class LayoutsRemotePublisherMessageListener
	extends BasePublisherMessageListener {

	@Override
	protected void doReceive(Message message, MessageStatus messageStatus)
		throws PortalException {

		long exportImportConfigurationId = GetterUtil.getLong(
			message.getPayload());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		messageStatus.setPayload(exportImportConfiguration);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		Map<Long, Boolean> layoutIdMap = (Map<Long, Boolean>)settingsMap.get(
			"layoutIdMap");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		String remoteAddress = MapUtil.getString(settingsMap, "remoteAddress");
		int remotePort = MapUtil.getInteger(settingsMap, "remotePort");
		String remotePathContext = MapUtil.getString(
			settingsMap, "remotePathContext");
		boolean secureConnection = MapUtil.getBoolean(
			settingsMap, "secureConnection");
		long remoteGroupId = MapUtil.getLong(settingsMap, "remoteGroupId");

		initThreadLocals(userId, parameterMap);

		User user = UserLocalServiceUtil.getUserById(userId);

		CompanyThreadLocal.setCompanyId(user.getCompanyId());

		try {
			StagingUtil.copyRemoteLayouts(
				sourceGroupId, layoutIdMap, parameterMap, remoteAddress,
				remotePort, remotePathContext, secureConnection, remoteGroupId);
		}
		finally {
			resetThreadLocals();
		}
	}

}