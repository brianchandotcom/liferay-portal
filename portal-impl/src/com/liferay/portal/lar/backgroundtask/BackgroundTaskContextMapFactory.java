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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author     Michael C. Han
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationSettingsMapFactory}
 */
@Deprecated
public class BackgroundTaskContextMapFactory {

	public static Map<String, Serializable> buildTaskContextMap(
		long userId, long plid, long groupId, String portletId,
		Map<String, String[]> parameterMap, String cmd, Date startDate,
		Date endDate, String fileName) {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		if (cmd != null) {
			taskContextMap.put(Constants.CMD, cmd);
		}

		if (endDate != null) {
			taskContextMap.put("endDate", endDate);
		}

		taskContextMap.put("fileName", fileName);
		taskContextMap.put("groupId", groupId);

		if (parameterMap != null) {
			HashMap<String, String[]> serializableParameterMap = new HashMap<>(
				parameterMap);

			taskContextMap.put("parameterMap", serializableParameterMap);
		}

		taskContextMap.put("plid", plid);

		if (Validator.isNotNull(portletId)) {
			taskContextMap.put("portletId", portletId);
		}

		if (startDate != null) {
			taskContextMap.put("startDate", startDate);
		}

		taskContextMap.put("userId", userId);

		return taskContextMap;
	}

	public static Map<String, Serializable> buildTaskContextMap(
		long userId, long groupId, long[] layoutIds,
		Map<String, String[]> parameterMap, String cmd, Date startDate,
		Date endDate, String fileName) {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		if (cmd != null) {
			taskContextMap.put(Constants.CMD, cmd);
		}

		if (endDate != null) {
			taskContextMap.put("endDate", endDate);
		}

		taskContextMap.put("fileName", fileName);
		taskContextMap.put("groupId", groupId);

		if (ArrayUtil.isNotEmpty(layoutIds)) {
			taskContextMap.put("layoutIds", layoutIds);
		}

		if (parameterMap != null) {
			HashMap<String, String[]> serializableParameterMap = new HashMap<>(
				parameterMap);

			taskContextMap.put("parameterMap", serializableParameterMap);
		}

		if (startDate != null) {
			taskContextMap.put("startDate", startDate);
		}

		taskContextMap.put("userId", userId);

		return taskContextMap;
	}

}