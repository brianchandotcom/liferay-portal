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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Daniel Kocsis
 */
public class LayoutsLocalPublisherMessageListener
	extends BasePublisherMessageListener {

	public LayoutsLocalPublisherMessageListener() {
	}

	@Override
	protected void doReceive(Message message, MessageStatus messageStatus)
		throws PortalException, SystemException {

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
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			settingsMap, "privateLayout");
		long[] layoutIds = GetterUtil.getLongValues(
			settingsMap.get("layoutIds"));
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		initThreadLocals(userId, parameterMap);

		try {
			if (layoutIds == null) {
				StagingUtil.publishLayouts(
					userId, sourceGroupId, targetGroupId, privateLayout,
					parameterMap, dateRange.getStartDate(),
					dateRange.getEndDate());
			}
			else {
				StagingUtil.publishLayouts(
					userId, sourceGroupId, targetGroupId, privateLayout,
					layoutIds, parameterMap, dateRange.getStartDate(),
					dateRange.getEndDate());
			}
		}
		finally {
			resetThreadLocals();
		}
	}

}