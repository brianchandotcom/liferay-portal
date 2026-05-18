/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.rest.dto.v1_0.ExportRequest;
import com.liferay.exportimport.rest.dto.v1_0.RequestPortletDataHandler;
import com.liferay.exportimport.rest.dto.v1_0.RequestPortletDataHandlerControl;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Raposo
 */
public class ExportRequestParameterMapUtil {

	public static Map<String, String[]> toParameterMap(
		ExportRequest exportRequest) {

		Map<String, String[]> parameterMap = new HashMap<>();

		RequestPortletDataHandler[] requestPortletDataHandlers =
			exportRequest.getRequestPortletDataHandlers();

		if (requestPortletDataHandlers != null) {
			for (RequestPortletDataHandler requestPortletDataHandler :
					requestPortletDataHandlers) {

				_addToParameterMap(requestPortletDataHandler, parameterMap);
			}
		}

		String range = exportRequest.getRangeAsString();

		if (range == null) {
			range = ExportImportDateUtil.RANGE_ALL;
		}

		parameterMap.put("range", new String[] {range});

		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.putIfAbsent(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	private static void _addToParameterMap(
		RequestPortletDataHandler requestPortletDataHandler,
		Map<String, String[]> parameterMap) {

		String name = requestPortletDataHandler.getName();

		if (Validator.isBlank(name)) {
			return;
		}

		parameterMap.put(name, new String[] {Boolean.TRUE.toString()});

		RequestPortletDataHandlerControl[] requestPortletDataHandlerControls =
			requestPortletDataHandler.getRequestPortletDataHandlerControls();

		if (requestPortletDataHandlerControls != null) {
			for (RequestPortletDataHandlerControl
					requestPortletDataHandlerControl :
						requestPortletDataHandlerControls) {

				_addToParameterMap(
					requestPortletDataHandlerControl, parameterMap);
			}
		}
	}

	private static void _addToParameterMap(
		RequestPortletDataHandlerControl requestPortletDataHandlerControl,
		Map<String, String[]> parameterMap) {

		String name = requestPortletDataHandlerControl.getName();

		if (Validator.isBlank(name)) {
			return;
		}

		String[] values = requestPortletDataHandlerControl.getValues();

		if (values == null) {
			String value = requestPortletDataHandlerControl.getValue();

			if (value == null) {
				value = Boolean.TRUE.toString();
			}

			values = new String[] {value};
		}

		parameterMap.put(name, values);

		RequestPortletDataHandlerControl[] requestPortletDataHandlerControls =
			requestPortletDataHandlerControl.
				getRequestPortletDataHandlerControls();

		if (requestPortletDataHandlerControls != null) {
			for (RequestPortletDataHandlerControl
					nestedRequestPortletDataHandlerControl :
						requestPortletDataHandlerControls) {

				_addToParameterMap(
					nestedRequestPortletDataHandlerControl, parameterMap);
			}
		}
	}

}