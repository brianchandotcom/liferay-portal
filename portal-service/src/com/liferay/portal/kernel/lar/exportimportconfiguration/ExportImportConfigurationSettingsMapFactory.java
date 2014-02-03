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

import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationSettingsMapFactory {

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long groupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		Date startDate, Date endDate) {

		return buildSettingsMap(
			userId, groupId, 0, privateLayout, layoutIdMap, parameterMap,
			startDate, endDate);
	}

	public static Map<String, Serializable> buildSettingsMap(
		long userId, long sourceGroupId, boolean privateLayout,
		Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, Date startDate, Date endDate) {

		Map<String, Serializable> settingsMap = buildSettingsMap(
			userId, sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			startDate, endDate);

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
		boolean privateLayout, Map<Long, Boolean> layoutIdMap,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {

		Map<String, Serializable> settingsMap =
			new HashMap<String, Serializable>();

		if (endDate != null) {
			settingsMap.put("endDate", endDate);
		}

		if (MapUtil.isNotEmpty(layoutIdMap)) {
			HashMap<Long, Boolean> serializableLayoutIdMap =
				new HashMap<Long, Boolean>(layoutIdMap);

			settingsMap.put("layoutIds", serializableLayoutIdMap);
		}

		if (parameterMap != null) {
			HashMap<String, String[]> serializableParameterMap =
				new HashMap<String, String[]>(parameterMap);

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

		settingsMap.put("userId", userId);

		return settingsMap;
	}

}