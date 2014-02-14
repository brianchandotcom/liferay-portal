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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Levente Hudák
 */
@ProviderType
public class ExportImportConfigurationHelper {

	public static ExportImportConfiguration
			addExportLayoutExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return addExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
	}

	public static ExportImportConfiguration
			addPublishLayoutLocalExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return addExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL);
	}

	public static ExportImportConfiguration
			addPublishLayoutRemoteExportImportConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		return addExportImportConfiguration(
			portletRequest,
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE);
	}

	public static void exportLayoutsByExportImportConfiguration(
			long configurationId)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(configurationId);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		String taskName = MapUtil.getString(parameterMap, "exportFileName");

		long groupId = MapUtil.getLong(settingsMap, "sourceGroupId");

		boolean privateLayout = GetterUtil.getBoolean(
			settingsMap.get("privateLayout"));

		Map<Long, Boolean> layoutIdMap = (Map<Long, Boolean>)settingsMap.get(
			"layoutIdMap");

		long[] layoutIds = ExportImportHelperUtil.getLayoutIds(layoutIdMap);

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		String fileName = MapUtil.getString(parameterMap, "exportFileName");

		LayoutServiceUtil.exportLayoutsAsFileInBackground(
			taskName, groupId, privateLayout, layoutIds, parameterMap,
			dateRange.getStartDate(), dateRange.getEndDate(), fileName);
	}

	protected static ExportImportConfiguration addExportImportConfiguration(
			PortletRequest portletRequest, int type)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String exportImportConfigurationName = ParamUtil.getString(
			portletRequest, "exportImportConfigurationName");
		String exportImportConfigurationDescription = ParamUtil.getString(
			portletRequest, "exportImportConfigurationDescription");

		Map<String, Serializable> settingsMap = buildSettingsMap(
			themeDisplay, portletRequest, groupId, type);

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				themeDisplay.getUserId(), groupId,
				exportImportConfigurationName,
				exportImportConfigurationDescription, type, settingsMap,
				new ServiceContext());
	}

	protected static Map<String, Serializable> buildSettingsMap(
			ThemeDisplay themeDisplay, PortletRequest portletRequest,
			long groupId, int type)
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

		String defaultDateRange =
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE;

		if (type == ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT) {
			defaultDateRange = ExportImportDateUtil.RANGE_ALL;
		}

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			portletRequest, groupId, privateLayout, 0, null, defaultDateRange);

		if (type == ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT) {
			return ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				themeDisplay.getUserId(), groupId, privateLayout, null,
				portletRequest.getParameterMap(), dateRange.getStartDate(),
				dateRange.getEndDate(), themeDisplay.getLocale(),
				themeDisplay.getTimeZone());
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
				themeDisplay.getUserId(), stagingGroup.getGroupId(),
				liveGroup.getGroupId(), privateLayout, layoutIdMap,
				parameterMap, dateRange.getStartDate(), dateRange.getEndDate(),
				themeDisplay.getLocale(), themeDisplay.getTimeZone());
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
			themeDisplay.getUserId(), groupId, privateLayout, layoutIdMap,
			parameterMap, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId, remotePrivateLayout,
			dateRange.getStartDate(), dateRange.getEndDate(),
			themeDisplay.getLocale(), themeDisplay.getTimeZone());
	}

}