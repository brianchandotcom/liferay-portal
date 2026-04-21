/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.servlet.SessionMessages;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class InfoItemUtil {

	public static long getDepotEntryId(HttpServletRequest httpServletRequest) {
		Object object = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		DepotEntry depotEntry =
			object instanceof DepotEntry ? (DepotEntry)object : null;

		if (depotEntry == null) {
			depotEntry = DepotEntryLocalServiceUtil.fetchGroupDepotEntry(
				getGroupId(httpServletRequest));
		}

		if (depotEntry != null) {
			return depotEntry.getDepotEntryId();
		}

		return 0;
	}

	public static long getGroupId(HttpServletRequest httpServletRequest) {
		Object object = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		DepotEntry depotEntry =
			object instanceof DepotEntry ? (DepotEntry)object : null;

		if (depotEntry != null) {
			return depotEntry.getGroupId();
		}

		ObjectEntry objectEntry =
			object instanceof ObjectEntry ? (ObjectEntry)object : null;

		if (objectEntry != null) {
			return objectEntry.getGroupId();
		}

		ObjectEntryFolder objectEntryFolder =
			object instanceof ObjectEntryFolder ? (ObjectEntryFolder)object :
				null;

		if (objectEntryFolder != null) {
			return objectEntryFolder.getGroupId();
		}

		return 0;
	}

	public static String getRestoredInfoFieldValue(
		HttpServletRequest httpServletRequest, String infoFieldUniqueId) {

		Map<String, Object> infoFormParameterMap = _getInfoFormParameterMap(
			httpServletRequest);

		if (infoFormParameterMap == null) {
			return null;
		}

		Object restoredValue = infoFormParameterMap.get(infoFieldUniqueId);

		if (restoredValue == null) {
			return null;
		}

		return String.valueOf(restoredValue);
	}

	private static Map<String, Object> _getInfoFormParameterMap(
		HttpServletRequest httpServletRequest) {

		Map<String, Object> cachedMap =
			(Map<String, Object>)httpServletRequest.getAttribute(
				_INFO_FORM_PARAMETER_MAP_ATTRIBUTE);

		if (cachedMap != null) {
			return cachedMap;
		}

		for (String key : SessionMessages.keySet(httpServletRequest)) {
			if (!key.startsWith("infoFormParameterMap")) {
				continue;
			}

			Object value = SessionMessages.get(httpServletRequest, key);

			if (value instanceof Map map) {
				httpServletRequest.setAttribute(
					_INFO_FORM_PARAMETER_MAP_ATTRIBUTE, map);

				return map;
			}
		}

		return null;
	}

	private static final String _INFO_FORM_PARAMETER_MAP_ATTRIBUTE =
		InfoItemUtil.class.getName() + "#infoFormParameterMap";

}