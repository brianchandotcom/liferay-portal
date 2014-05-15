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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletRequest;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationSettingsMapFactory {

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long groupId, boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Date startDate, Date endDate,
		Locale locale, TimeZone timeZone) {

		return buildSettingsMap(
			userId, groupId, 0, privateLayout, layoutIds, parameterMap,
			startDate, endDate, locale, timeZone);
	}

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, Date startDate, Date endDate,
		Locale locale, TimeZone timeZone) {

		Map<String, Serializable> settingsMap = buildSettingsMap(
			userId, sourceGroupId, privateLayout, null, parameterMap, startDate,
			endDate, locale, timeZone);

		if (MapUtil.isNotEmpty(layoutIdMap)) {
			HashMap<Long, Boolean> serializableLayoutIdMap =
				new HashMap<Long, Boolean>(layoutIdMap);

			settingsMap.put("layoutIdMap", serializableLayoutIdMap);
		}

		settingsMap.put("remoteAddress", remoteAddress);
		settingsMap.put("remoteGroupId", remoteGroupId);
		settingsMap.put("remotePathContext", remotePathContext);
		settingsMap.put("remotePort", remotePort);
		settingsMap.put("remotePrivateLayout", remotePrivateLayout);
		settingsMap.put("secureConnection", secureConnection);

		return settingsMap;
	}

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Date startDate, Date endDate,
		Locale locale, TimeZone timeZone) {

		Map<String, Serializable> settingsMap =
			new HashMap<String, Serializable>();

		if (endDate != null) {
			settingsMap.put("endDate", endDate);
		}

		if (ArrayUtil.isNotEmpty(layoutIds)) {
			settingsMap.put("layoutIds", layoutIds);
		}

		settingsMap.put("locale", locale);

		if (parameterMap != null) {
			HashMap<String, String[]> serializableParameterMap =
				new HashMap<String, String[]>(parameterMap);

			serializableParameterMap.put(
				PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
				new String[] {Boolean.TRUE.toString()});

			settingsMap.put("parameterMap", serializableParameterMap);
		}

		settingsMap.put("privateLayout", privateLayout);
		settingsMap.put("sourceGroupId", sourceGroupId);

		if (startDate != null) {
			settingsMap.put("startDate", startDate);
		}

		if (targetGroupId > 0) {
			settingsMap.put("targetGroupId", targetGroupId);
		}

		settingsMap.put("timezone", timeZone);
		settingsMap.put("userId", userId);

		return settingsMap;
	}

	public static Map<String, Serializable> buildSettingsMap(
			PortletRequest portletRequest, long groupId, int type)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean privateLayout = ParamUtil.getBoolean(
			portletRequest, "privateLayout");

		Map<Long, Boolean> layoutIdMap = ExportImportHelperUtil.getLayoutIdMap(
			portletRequest);

		String defaultDateRange =
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE;

		if (type == ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT) {
			defaultDateRange = ExportImportDateUtil.RANGE_ALL;
		}

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			portletRequest, groupId, privateLayout, 0, null, defaultDateRange);

		if (type == ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT) {
			long[] layoutIds = ExportImportHelperUtil.getLayoutIds(layoutIdMap);

			return buildSettingsMap(
				themeDisplay.getUserId(), groupId, privateLayout, layoutIds,
				portletRequest.getParameterMap(), dateRange.getStartDate(),
				dateRange.getEndDate(), themeDisplay.getLocale(),
				themeDisplay.getTimeZone());
		}

		Group stagingGroup = GroupLocalServiceUtil.getGroup(groupId);

		Group liveGroup = stagingGroup.getLiveGroup();

		Map<String, String[]> parameterMap = StagingUtil.getStagingParameters(
			portletRequest);

		if (liveGroup != null) {
			long[] layoutIds = ExportImportHelperUtil.getLayoutIds(
				layoutIdMap, liveGroup.getGroupId());

			return buildSettingsMap(
				themeDisplay.getUserId(), stagingGroup.getGroupId(),
				liveGroup.getGroupId(), privateLayout, layoutIds, parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate(),
				themeDisplay.getLocale(), themeDisplay.getTimeZone());
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
			groupId, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId);

		return buildSettingsMap(
			themeDisplay.getUserId(), groupId, privateLayout, layoutIdMap,
			parameterMap, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId, remotePrivateLayout,
			dateRange.getStartDate(), dateRange.getEndDate(),
			themeDisplay.getLocale(), themeDisplay.getTimeZone());
	}

}