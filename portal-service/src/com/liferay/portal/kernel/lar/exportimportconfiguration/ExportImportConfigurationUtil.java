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

package com.liferay.portal.kernel.lar.exportimportconfiguration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Levente Hudák
 */
@ProviderType
public class ExportImportConfigurationUtil {

	public static ExportImportConfiguration createExportLayoutConfiguration(
			PortletRequest portletRequest)
		throws Exception {

		return doCreateConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
	}

	public static ExportImportConfiguration
			createPublishLayoutLocalConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return doCreateConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL);
	}

	public static ExportImportConfiguration
			createPublishLayoutRemoteConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return doCreateConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE);
	}

	protected static ExportImportConfiguration doCreateConfiguration(
			PortletRequest portletRequest, int configurationType)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Map<String, Serializable> configurationContextMap = null;

		if (configurationType ==
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT) {

			configurationContextMap = doCreateConfigurationContextMap(
				portletRequest, userId, groupId, true,
				ExportImportDateUtil.RANGE_ALL);
		}
		else {
			configurationContextMap = doCreateConfigurationContextMap(
				portletRequest, userId, groupId, false,
				ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);
		}

		String exportImportConfigurationName = ParamUtil.getString(
			portletRequest, "exportImportConfigurationName");

		String exportImportConfigurationDescription = ParamUtil.getString(
			portletRequest, "exportImportConfigurationDescription");

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				userId, groupId, exportImportConfigurationName,
				exportImportConfigurationDescription, configurationType,
				configurationContextMap, new ServiceContext());
	}

	protected static Map<String, Serializable> doCreateConfigurationContextMap(
			PortletRequest portletRequest, long userId, long groupId,
			boolean exportConfiguration, String defaultDateRange)
		throws Exception {

		boolean privateLayout = true;

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}
		else {
			privateLayout = ParamUtil.getBoolean(
				portletRequest, "privateLayout");
		}

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			portletRequest, groupId, privateLayout, 0, null, defaultDateRange);

		if (exportConfiguration) {
			return ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				userId, groupId, privateLayout, null,
				portletRequest.getParameterMap(), dateRange.getStartDate(),
				dateRange.getEndDate());
		}

		String scope = ParamUtil.getString(portletRequest, "scope");

		if (Validator.isNull(scope)) {
			scope = "all-pages";
		}

		Map<Long, Boolean> layoutIdMap = null;

		if (scope.equals("selected-pages")) {
			layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(portletRequest);
		}

		Group stagingGroup = GroupLocalServiceUtil.getGroup(groupId);
		Group liveGroup = stagingGroup.getLiveGroup();

		Map<String, String[]> parameterMap = StagingUtil.getStagingParameters(
			portletRequest);

		if (liveGroup != null) {
			return ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				userId, stagingGroup.getGroupId(), liveGroup.getGroupId(),
				privateLayout, layoutIdMap, parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate());
		}

		if (liveGroup == null) {
			parameterMap.put(
				PortletDataHandlerKeys.PUBLISH_TO_REMOTE,
				new String[] {Boolean.TRUE.toString()});
		}

		UnicodeProperties groupTypeSettingsProperties =
			stagingGroup.getTypeSettingsProperties();

		String remoteAddress = ParamUtil.getString(
			portletRequest, "remoteAddress",
			groupTypeSettingsProperties.getProperty("remoteAddress"));

		remoteAddress = StagingUtil.stripProtocolFromRemoteAddress(
			remoteAddress);

		int remotePort = ParamUtil.getInteger(
			portletRequest, "remotePort",
			GetterUtil.getInteger(
				groupTypeSettingsProperties.getProperty("remotePort")));

		String remotePathContext = ParamUtil.getString(
			portletRequest, "remotePathContext",
			groupTypeSettingsProperties.getProperty("remotePathContext"));

		boolean secureConnection = ParamUtil.getBoolean(
			portletRequest, "secureConnection",
			GetterUtil.getBoolean(
				groupTypeSettingsProperties.getProperty("secureConnection")));

		long remoteGroupId = ParamUtil.getLong(
			portletRequest, "remoteGroupId",
			GetterUtil.getLong(
				groupTypeSettingsProperties.getProperty("remoteGroupId")));

		boolean remotePrivateLayout = ParamUtil.getBoolean(
			portletRequest, "remotePrivateLayout");

		StagingUtil.validateRemote(
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId);

		return ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
			userId, groupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout, dateRange.getStartDate(),
			dateRange.getEndDate());
	}

}