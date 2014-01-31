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

package com.liferay.portal.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lar.exportimportconfiguration.ExportImportConfigurationConstants;
import com.liferay.portal.lar.exportimportconfiguration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.LinkedHashMap;
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

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		boolean privateLayout = ParamUtil.getBoolean(
			portletRequest, "privateLayout");

		DateRange dateRange = ExportImportHelperUtil.getDateRange(
			portletRequest, groupId, privateLayout, 0, null, "all");

		String exportImportConfigurationName = ParamUtil.getString(
			portletRequest, "exportImportConfigurationName");

		String exportImportConfigurationDescription = ParamUtil.getString(
			portletRequest, "exportImportConfigurationDescription");

		Map<String, Serializable> configurationContextMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				userId, groupId, privateLayout, null,
				portletRequest.getParameterMap(), dateRange.getStartDate(),
				dateRange.getEndDate());

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				userId, groupId, exportImportConfigurationName,
				exportImportConfigurationDescription,
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
				configurationContextMap, new ServiceContext());
	}

	public static ExportImportConfiguration
			createPublishLayoutLocalConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Group liveGroup = GroupLocalServiceUtil.getGroup(groupId);
		Group stagingGroup = liveGroup.getStagingGroup();

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(portletRequest, "scope");

		Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

		if (scope.equals("selected-pages")) {
			layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(portletRequest);
		}

		Map<String, String[]> parameterMap = StagingUtil.getStagingParameters(
			portletRequest);

		DateRange dateRange = ExportImportHelperUtil.getDateRange(
			portletRequest, stagingGroup.getGroupId(), privateLayout, 0, null,
			"fromLastPublishDate");

		String exportImportConfigurationName = ParamUtil.getString(
			portletRequest, "exportImportConfigurationName");

		String exportImportConfigurationDescription = ParamUtil.getString(
			portletRequest, "exportImportConfigurationDescription");

		Map<String, Serializable> configurationContextMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				userId, stagingGroup.getGroupId(), groupId, privateLayout,
				layoutIdMap, parameterMap, dateRange.getStartDate(),
				dateRange.getEndDate());

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				userId, groupId, exportImportConfigurationName,
				exportImportConfigurationDescription,
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL,
				configurationContextMap, new ServiceContext());
	}

	public static ExportImportConfiguration
			createPublishLayoutRemoteConfiguration(
				PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(portletRequest, "scope");

		if (Validator.isNull(scope)) {
			scope = "all-pages";
		}

		Map<Long, Boolean> layoutIdMap = null;

		if (scope.equals("selected-pages")) {
			layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(portletRequest);
		}

		Map<String, String[]> parameterMap = StagingUtil.getStagingParameters(
			portletRequest);

		parameterMap.put(
			PortletDataHandlerKeys.PUBLISH_TO_REMOTE,
			new String[] {Boolean.TRUE.toString()});

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UnicodeProperties groupTypeSettingsProperties =
			group.getTypeSettingsProperties();

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

		DateRange dateRange = ExportImportHelperUtil.getDateRange(
			portletRequest, groupId, privateLayout, 0, null,
			"fromLastPublishDate");

		String exportImportConfigurationName = ParamUtil.getString(
			portletRequest, "exportImportConfigurationName");

		String exportImportConfigurationDescription = ParamUtil.getString(
			portletRequest, "exportImportConfigurationDescription");

		Map<String, Serializable> configurationContextMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				userId, groupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				remoteGroupId, remotePrivateLayout, dateRange.getStartDate(),
				dateRange.getEndDate());

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				userId, remoteGroupId, exportImportConfigurationName,
				exportImportConfigurationDescription,
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE,
				configurationContextMap, new ServiceContext());
	}

}