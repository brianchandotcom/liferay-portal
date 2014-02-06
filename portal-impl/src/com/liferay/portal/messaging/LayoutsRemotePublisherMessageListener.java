/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging;

import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.messaging.BaseMessageStatusMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Daniel Kocsis
 */
public class LayoutsRemotePublisherMessageListener
	extends BaseMessageStatusMessageListener {

	public LayoutsRemotePublisherMessageListener() {
	}

	@Override
	protected void doReceive(Message message, MessageStatus messageStatus)
		throws Exception {

		long exportImportConfigurationId = GetterUtil.getLong(
			message.getPayload());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		messageStatus.setPayload(exportImportConfiguration);

		Map<String, Serializable> configurationContextMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(configurationContextMap, "userId");
		long sourceGroupId = MapUtil.getLong(
			configurationContextMap, "sourceGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			configurationContextMap, "privateLayout");
		Map<Long, Boolean> layoutIdMap =
			(Map<Long, Boolean>)configurationContextMap.get("layoutIdMap");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)configurationContextMap.get("parameterMap");
		String remoteAddress = MapUtil.getString(
			configurationContextMap, "remoteAddress");
		int remotePort = MapUtil.getInteger(
			configurationContextMap, "remotePort");
		String remotePathContext = MapUtil.getString(
			configurationContextMap, "remotePathContext");
		boolean secureConnection = MapUtil.getBoolean(
			configurationContextMap, "secureConnection");
		long remoteGroupId = MapUtil.getLong(
			configurationContextMap, "remoteGroupId");
		boolean remotePrivateLayout = MapUtil.getBoolean(
			configurationContextMap, "remotePrivateLayout");
		Date startDate = (Date)configurationContextMap.get("startDate");
		Date endDate = (Date)configurationContextMap.get("endDate");

		String range = MapUtil.getString(parameterMap, "range");

		if (range.equals(ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE)) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				sourceGroupId, privateLayout);

			long lastPublishDate = GetterUtil.getLong(
				layoutSet.getSettingsProperty("last-publish-date"));

			if (lastPublishDate > 0) {
				endDate = new Date();

				startDate = new Date(lastPublishDate);
			}
		}
		else if (range.equals(ExportImportDateUtil.RANGE_LAST)) {
			int last = MapUtil.getInteger(parameterMap, "last");

			if (last > 0) {
				Date scheduledFireTime = new Date();

				startDate = new Date(
					scheduledFireTime.getTime() - (last * Time.HOUR));

				endDate = scheduledFireTime;
			}
		}

		initThreadLocals(userId, parameterMap);

		try {
			StagingUtil.copyRemoteLayouts(
				sourceGroupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				remoteGroupId, remotePrivateLayout, startDate, endDate);
		}
		finally {
			resetThreadLocals();
		}
	}

	protected void initThreadLocals(
			long userId, Map<String, String[]> parameterMap)
		throws Exception {

		User user = UserLocalServiceUtil.getUserById(userId);

		CompanyThreadLocal.setCompanyId(user.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		PrincipalThreadLocal.setName(user.getUserId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setPathMain(PortalUtil.getPathMain());
		serviceContext.setSignedIn(!user.isDefaultUser());
		serviceContext.setUserId(user.getUserId());

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String param = entry.getKey();
			String[] values = entry.getValue();

			if (ArrayUtil.isNotEmpty(values)) {
				if (values.length == 1) {
					attributes.put(param, values[0]);
				}
				else {
					attributes.put(param, values);
				}
			}
		}

		serviceContext.setAttributes(attributes);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	protected void resetThreadLocals() {
		CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);
		PermissionThreadLocal.setPermissionChecker(null);
		PrincipalThreadLocal.setName(null);
		ServiceContextThreadLocal.popServiceContext();
	}

}