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

package com.liferay.portal.kernel.lar.exportimportconfiguration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Levente Hudák
 */
public class ExportImportConfigurationFactory {

	public static ExportImportConfiguration
			buildDefaultLocalPublishingExportImportConfiguration(
				User user, long sourceGroupId, long targetGroupId,
				boolean privateLayout)
		throws PortalException {

		Map<String, String[]> parameterMap = getDefaultPublishingParameters();

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				user.getUserId(), sourceGroupId, targetGroupId, privateLayout,
				ExportImportHelperUtil.getAllLayoutIds(
					sourceGroupId, privateLayout),
				parameterMap, null, null, user.getLocale(), user.getTimeZone());

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				user.getUserId(), sourceGroupId, StringPool.BLANK,
				StringPool.BLANK, ExportImportConfigurationConstants.
					TYPE_PUBLISH_LAYOUT_LOCAL,
				settingsMap, WorkflowConstants.STATUS_DRAFT,
				new ServiceContext());
	}

	public static ExportImportConfiguration
			buildDefaultRemotePublishingExportImportConfiguration(
				User user, long sourceGroupId, boolean privateLayout,
				String remoteAddress, int remotePort, String remotePathContext,
				boolean secureConnection, long remoteGroupId)
		throws PortalException {

		Map<String, String[]> parameterMap = getDefaultPublishingParameters();

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				user.getUserId(), sourceGroupId, privateLayout,
				ExportImportHelperUtil.getAllLayoutIdsMap(
					sourceGroupId, privateLayout),
				parameterMap, remoteAddress, remotePort, remotePathContext,
				secureConnection, remoteGroupId, privateLayout, null, null,
				user.getLocale(), user.getTimeZone());

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				user.getUserId(), sourceGroupId, StringPool.BLANK,
				StringPool.BLANK, ExportImportConfigurationConstants.
					TYPE_PUBLISH_LAYOUT_REMOTE,
				settingsMap, WorkflowConstants.STATUS_DRAFT,
				new ServiceContext());
	}

	protected static Map<String, String[]> getDefaultPublishingParameters() {
		Map<String, String[]> parameterMap = StagingUtil.getStagingParameters();

		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE});

		return parameterMap;
	}

}