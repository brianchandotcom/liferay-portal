/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.rest.dto.v1_0.ExportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.ImportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.RequestPortletDataHandler;
import com.liferay.exportimport.rest.dto.v1_0.RequestPortletDataHandlerControl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Daniel Raposo
 */
public class ParameterMapUtil {

	public static Map<String, String[]> toParameterMap(
		ExportProcessRequest exportProcessRequest) {

		Map<String, String[]> parameterMap = _getDefaultParameterMap();

		_addRequestPortletDataHandlers(
			exportProcessRequest.getRequestPortletDataHandlers(), parameterMap);

		Boolean deletions = exportProcessRequest.getDeletions();

		if (deletions != null) {
			parameterMap.put(
				PortletDataHandlerKeys.DELETIONS,
				new String[] {deletions.toString()});
		}

		Boolean permissions = exportProcessRequest.getPermissions();

		if (permissions != null) {
			parameterMap.put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {permissions.toString()});
		}

		return parameterMap;
	}

	public static Map<String, String[]> toParameterMap(
		ImportProcessRequest importProcessRequest) {

		Map<String, String[]> parameterMap = _getDefaultParameterMap();

		_addRequestPortletDataHandlers(
			importProcessRequest.getRequestPortletDataHandlers(), parameterMap);

		ImportProcessRequest.DataStrategy dataStrategy =
			importProcessRequest.getDataStrategy();

		if (dataStrategy != null) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {"DATA_STRATEGY_" + dataStrategy});
		}

		Boolean deletions = importProcessRequest.getDeletions();

		if (deletions != null) {
			parameterMap.put(
				PortletDataHandlerKeys.DELETIONS,
				new String[] {deletions.toString()});
		}

		Boolean permissions = importProcessRequest.getPermissions();

		if (permissions != null) {
			parameterMap.put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {permissions.toString()});
		}

		ImportProcessRequest.UserIdStrategy userIdStrategy =
			importProcessRequest.getUserIdStrategy();

		if (userIdStrategy != null) {
			parameterMap.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {userIdStrategy.toString()});
		}

		return parameterMap;
	}

	private static void _addRequestPortletDataHandlerControls(
		RequestPortletDataHandlerControl[] requestPortletDataHandlerControls,
		Map<String, String[]> parameterMap) {

		if (requestPortletDataHandlerControls == null) {
			return;
		}

		for (RequestPortletDataHandlerControl requestPortletDataHandlerControl :
				requestPortletDataHandlerControls) {

			String name = requestPortletDataHandlerControl.getName();

			if (Validator.isBlank(name)) {
				continue;
			}

			String[] values = requestPortletDataHandlerControl.getValues();

			if (values == null) {
				String value = requestPortletDataHandlerControl.getValue();

				values = new String[] {
					(value != null) ? value : Boolean.TRUE.toString()
				};
			}

			parameterMap.put(name, values);

			_addRequestPortletDataHandlerControls(
				requestPortletDataHandlerControl.
					getRequestPortletDataHandlerControls(),
				parameterMap);
		}
	}

	private static void _addRequestPortletDataHandlers(
		RequestPortletDataHandler[] requestPortletDataHandlers,
		Map<String, String[]> parameterMap) {

		if (requestPortletDataHandlers == null) {
			return;
		}

		for (RequestPortletDataHandler requestPortletDataHandler :
				requestPortletDataHandlers) {

			String name = requestPortletDataHandler.getName();

			if (Validator.isBlank(name)) {
				continue;
			}

			parameterMap.put(name, new String[] {Boolean.TRUE.toString()});

			_addRequestPortletDataHandlerControls(
				requestPortletDataHandler.
					getRequestPortletDataHandlerControls(),
				parameterMap);
		}
	}

	private static Map<String, String[]> _getDefaultParameterMap() {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID}
		).build();
	}

}