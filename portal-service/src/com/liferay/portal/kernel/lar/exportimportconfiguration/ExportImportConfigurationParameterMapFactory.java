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

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Akos Thurzo
 */
public class ExportImportConfigurationParameterMapFactory {

	public static Map<String, String[]> buildParameterMap() {
		return buildParameterMap(
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE, true, false,
			true, false, false, false, true, true, true, true, true, true, true,
			true, UserIdStrategy.CURRENT_USER_ID);
	}

	public static Map<String, String[]> buildParameterMap(
		PortletRequest portletRequest) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>(
			portletRequest.getParameterMap());

		if (!parameterMap.containsKey(PortletDataHandlerKeys.DATA_STRATEGY)) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE});
		}

		/*if (!parameterMap.containsKey(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS)) {

			parameterMap.put(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
				new String[] {Boolean.TRUE.toString()});
		}*/

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.DELETE_PORTLET_DATA)) {

			parameterMap.put(
				PortletDataHandlerKeys.DELETE_PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED)) {

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.LAYOUT_SET_SETTINGS)) {

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.LOGO)) {
			parameterMap.put(
				PortletDataHandlerKeys.LOGO,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_CONFIGURATION)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_CONFIGURATION,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.PORTLET_DATA)) {
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA_ALL)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.THEME_REFERENCE)) {
			parameterMap.put(
				PortletDataHandlerKeys.THEME_REFERENCE,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE)) {

			parameterMap.put(
				PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.USER_ID_STRATEGY)) {

			parameterMap.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.CURRENT_USER_ID});
		}

		return parameterMap;
	}

	public static Map<String, String[]> buildParameterMap(
		String dataStrategy, Boolean deleteMissingLayouts,
		Boolean deletePortletData, Boolean ignoreLastPublishDate,
		Boolean layoutSetPrototypeLinkEnabled, Boolean layoutSetSettings,
		Boolean logo, Boolean permissions, Boolean portletConfiguration,
		Boolean portletConfigurationAll, Boolean portletData,
		Boolean portletDataAll, Boolean portletSetupAll, Boolean themeReference,
		Boolean updateLastPublishDate, String userIdStrategy) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		if (Validator.isNull(dataStrategy)) {
			dataStrategy =
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY, new String[] {dataStrategy});

		if (deleteMissingLayouts == null) {
			deleteMissingLayouts = Boolean.TRUE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {deleteMissingLayouts.toString()});

		if (deletePortletData == null) {
			deletePortletData = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {deletePortletData.toString()});

		if (layoutSetPrototypeLinkEnabled == null) {
			layoutSetPrototypeLinkEnabled = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {layoutSetPrototypeLinkEnabled.toString()});

		if (layoutSetSettings == null) {
			layoutSetSettings = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {layoutSetSettings.toString()});

		if (logo == null) {
			logo = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.LOGO, new String[] {logo.toString()});

		if (portletConfiguration == null) {
			portletConfiguration = Boolean.TRUE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {portletConfiguration.toString()});

		if (portletData == null) {
			portletData = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {portletData.toString()});

		if (portletDataAll) {
			portletDataAll = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {portletDataAll.toString()});

		if (themeReference == null) {
			themeReference = Boolean.FALSE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {themeReference.toString()});

		if (updateLastPublishDate) {
			updateLastPublishDate = Boolean.TRUE;
		}

		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {updateLastPublishDate.toString()});

		if (Validator.isNull(userIdStrategy)) {
			userIdStrategy = UserIdStrategy.CURRENT_USER_ID;
		}

		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {userIdStrategy});

		return parameterMap;
	}

}