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

package com.liferay.exportimport.kernel.configuration;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.LinkedHashMap;
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

		Map<String, String[]> parameterMap = _getParameterMap(portletRequest);

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
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());
	}

	public static ExportImportConfiguration
			buildDefaultLocalPublishingExportImportConfiguration(
				User user, long sourceGroupId, long targetGroupId,
				boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		Map<String, Serializable> publishLayoutLocalSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildPublishLayoutLocalSettingsMap(
					user, sourceGroupId, targetGroupId, privateLayout,
					ExportImportHelperUtil.getAllLayoutIds(
						sourceGroupId, privateLayout),
					parameterMap);

		return ExportImportConfigurationLocalServiceUtil.
			addDraftExportImportConfiguration(
				user.getUserId(),
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL,
				publishLayoutLocalSettingsMap);
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

		Map<String, String[]> parameterMap = _getParameterMap(portletRequest);

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
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());
	}

	public static ExportImportConfiguration cloneExportImportConfiguration(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		if (exportImportConfiguration == null) {
			return null;
		}

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				exportImportConfiguration.getUserId(),
				exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(),
				exportImportConfiguration.getDescription(),
				exportImportConfiguration.getType(),
				exportImportConfiguration.getSettingsMap(),
				exportImportConfiguration.getStatus(), new ServiceContext());
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             ExportImportConfigurationParameterMapFactoryUtil#buildParameterMap(
	 *             )}
	 */
	@Deprecated
	public static Map<String, String[]> getDefaultPublishingParameters(
		PortletRequest portletRequest) {

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		Map<String, String[]> requestParameterMap = new LinkedHashMap<>(
			portletRequest.getParameterMap());

		MapUtil.merge(requestParameterMap, parameterMap);

		return parameterMap;
	}

	protected static ExportImportConfiguration
			buildDefaultRemotePublishingExportImportConfiguration(
				User user, long sourceGroupId, boolean privateLayout,
				String remoteAddress, int remotePort, String remotePathContext,
				boolean secureConnection, long remoteGroupId,
				Map<String, String[]> parameterMap)
		throws PortalException {

		Map<String, Serializable> publishLayoutRemoteSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildPublishLayoutRemoteSettingsMap(
					user.getUserId(), sourceGroupId, privateLayout,
					ExportImportHelperUtil.getAllLayoutIdsMap(
						sourceGroupId, privateLayout),
					parameterMap, remoteAddress, remotePort, remotePathContext,
					secureConnection, remoteGroupId, privateLayout,
					user.getLocale(), user.getTimeZone());

		return ExportImportConfigurationLocalServiceUtil.
			addDraftExportImportConfiguration(
				user.getUserId(),
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE,
				publishLayoutRemoteSettingsMap);
	}

	private static Map<String, String[]> _getParameterMap(
		PortletRequest portletRequest) {

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		Map<String, String[]> requestParameterMap = new LinkedHashMap<>(
			portletRequest.getParameterMap());

		requestParameterMap.forEach(parameterMap::putIfAbsent);

		return parameterMap;
	}

}