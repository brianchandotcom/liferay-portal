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

package com.liferay.exportimport.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"destination.name=" + DestinationNames.LAYOUTS_REMOTE_PUBLISHER,
		"message.status.destination.name=" + DestinationNames.MESSAGE_BUS_MESSAGE_STATUS
	},
	service = LayoutsRemotePublisherMessageListener.class
)
public class LayoutsRemotePublisherMessageListener
	extends BasePublisherMessageListener {

	@Activate
	protected void activate(ComponentContext componentContext) {
		initialize(componentContext);
	}

	@Deactivate
	protected void deactivate() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Override
	protected void doReceive(Message message, MessageStatus messageStatus)
		throws PortalException {

		long exportImportConfigurationId = GetterUtil.getLong(
			message.getPayload());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		messageStatus.setPayload(exportImportConfiguration);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			settingsMap, "privateLayout");
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
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		boolean remotePrivateLayout = MapUtil.getBoolean(
			settingsMap, "remotePrivateLayout");

		initThreadLocals(userId, parameterMap);

		User user = _userLocalService.getUserById(userId);

		CompanyThreadLocal.setCompanyId(user.getCompanyId());

		try {
			StagingUtil.copyRemoteLayouts(
				sourceGroupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				targetGroupId, remotePrivateLayout);
		}
		finally {
			resetThreadLocals();
		}
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.LAYOUTS_REMOTE_PUBLISHER + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private volatile UserLocalService _userLocalService;

}