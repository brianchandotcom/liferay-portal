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

package com.liferay.portlet.exportimport.configuration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.exportimport.lar.ExportImportDateUtil;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Levente Hudák
 */
public class ExportImportConfigurationFactory {

	public static ExportImportConfiguration
			buildDefaultLocalPublishingExportImportConfiguration(
				PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long sourceGroupId = ParamUtil.getLong(portletRequest, "sourceGroupId");
		long targetGroupId = ParamUtil.getLong(portletRequest, "targetGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			portletRequest, "privateLayout");

		Map<String, String[]> parameterMap = getDefaultPublishingParameters(
			portletRequest);

		return buildDefaultLocalPublishingExportImportConfiguration(
			themeDisplay.getUser(), sourceGroupId, targetGroupId, privateLayout,
			parameterMap);
	}

	public static ExportImportConfiguration
			buildDefaultLocalPublishingExportImportConfiguration(
				User user, long sourceGroupId, long targetGroupId,
				boolean privateLayout)
		throws PortalException {

		return buildDefaultLocalPublishingExportImportConfiguration(
			user, sourceGroupId, targetGroupId, privateLayout,
			getDefaultPublishingParameters());
	}

	public static ExportImportConfiguration
			buildDefaultLocalPublishingExportImportConfiguration(
				User user, long sourceGroupId, long targetGroupId,
				boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
					user.getUserId(), sourceGroupId, targetGroupId,
					privateLayout,
					ExportImportHelperUtil.getAllLayoutIds(
							sourceGroupId, privateLayout),
					parameterMap, user.getLocale(), user.getTimeZone());

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
				PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long sourceGroupId = ParamUtil.getLong(portletRequest, "sourceGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			portletRequest, "privateLayout");
		String remoteAddress = ParamUtil.getString(
			portletRequest, "remoteAddress");
		int remotePort = ParamUtil.getInteger(portletRequest, "remotePort");
		String remotePathContext = ParamUtil.getString(
			portletRequest, "remotePathContext");
		boolean secureConnection = ParamUtil.getBoolean(
			portletRequest, "secureConnection");
		long remoteGroupId = ParamUtil.getLong(portletRequest, "remoteGroupId");

		Map<String, String[]> parameterMap = getDefaultPublishingParameters(
			portletRequest);

		return buildDefaultRemotePublishingExportImportConfiguration(
			themeDisplay.getUser(), sourceGroupId, privateLayout, remoteAddress,
			remotePort, remotePathContext, secureConnection, remoteGroupId,
			parameterMap);
	}

	public static ExportImportConfiguration
			buildDefaultRemotePublishingExportImportConfiguration(
				User user, long sourceGroupId, boolean privateLayout,
				String remoteAddress, int remotePort, String remotePathContext,
				boolean secureConnection, long remoteGroupId)
		throws PortalException {

		return buildDefaultRemotePublishingExportImportConfiguration(
			user, sourceGroupId, privateLayout, remoteAddress, remotePort,
			remotePathContext, secureConnection, remoteGroupId,
			getDefaultPublishingParameters());
	}

	public static Map<String, String[]> getDefaultPublishingParameters(
		PortletRequest portletRequest) {

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap(
				portletRequest);

		return addDefaultPublishingParameters(parameterMap);
	}

	protected static Map<String, String[]> addDefaultPublishingParameters(
		Map<String, String[]> parameterMap) {

		parameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()});
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
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE});

		return parameterMap;
	}

	protected static ExportImportConfiguration
			buildDefaultRemotePublishingExportImportConfiguration(
				User user, long sourceGroupId, boolean privateLayout,
				String remoteAddress, int remotePort, String remotePathContext,
				boolean secureConnection, long remoteGroupId,
				Map<String, String[]> parameterMap)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				user.getUserId(), sourceGroupId, privateLayout,
				ExportImportHelperUtil.getAllLayoutIdsMap(
					sourceGroupId, privateLayout),
				parameterMap, remoteAddress, remotePort, remotePathContext,
				secureConnection, remoteGroupId, privateLayout,
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
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap();

		return addDefaultPublishingParameters(parameterMap);
	}

}