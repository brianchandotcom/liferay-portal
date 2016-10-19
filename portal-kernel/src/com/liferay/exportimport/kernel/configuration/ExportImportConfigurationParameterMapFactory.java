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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Akos Thurzo
 */
@ProviderType
public class ExportImportConfigurationParameterMapFactory {

	public static Map<String, String[]> buildParameterMap() {
		return buildParameterMap(
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE, true, false,
			false, false, false, false, true, true, true, true, true, true,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE, true, true,
			UserIdStrategy.CURRENT_USER_ID);
	}

	public static Map<String, String[]> buildParameterMap(
		PortletRequest portletRequest) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>(
			portletRequest.getParameterMap());

		if (!parameterMap.containsKey(PortletDataHandlerKeys.DATA_STRATEGY)) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE
				});
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
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS)) {

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
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
		Boolean portletDataAll, Boolean portletSetupAll, String range,
		Boolean themeReference, Boolean updateLastPublishDate,
		String userIdStrategy) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		// Data strategy

		String dataStrategyParameter =
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE;

		if (Validator.isNotNull(dataStrategy)) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {dataStrategyParameter});
		}

		// Delete missing layouts

		boolean deleteMissingLayoutsParameter = true;

		if (deleteMissingLayouts != null) {
			deleteMissingLayoutsParameter = deleteMissingLayouts.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {String.valueOf(deleteMissingLayoutsParameter)});

		// Delete portlet data

		boolean deletePortletDataParameter = false;

		if (deletePortletData != null) {
			deletePortletDataParameter = deletePortletData.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {String.valueOf(deletePortletDataParameter)});

		// Ignore last publish date

		boolean ignoreLastPublishDateParameter = true;

		if (ignoreLastPublishDate != null) {
			ignoreLastPublishDateParameter =
				ignoreLastPublishDate.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {String.valueOf(ignoreLastPublishDateParameter)});

		// Layout set prototype link enabled

		boolean layoutSetPrototypeLinkEnabledParameter = false;

		if (layoutSetPrototypeLinkEnabled != null) {
			layoutSetPrototypeLinkEnabledParameter =
				layoutSetPrototypeLinkEnabled.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {
				String.valueOf(layoutSetPrototypeLinkEnabledParameter)
			});

		// Layout set prototype settings

		boolean layoutSetPrototypeSettingsParameter = false;

		if (layoutSetPrototypeLinkEnabled != null) {
			layoutSetPrototypeSettingsParameter =
				layoutSetPrototypeLinkEnabled.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {String.valueOf(layoutSetPrototypeSettingsParameter)});

		// Layout set settings

		boolean layoutSetSettingsParameter = false;

		if (layoutSetSettings != null) {
			layoutSetSettingsParameter = layoutSetSettings.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {String.valueOf(layoutSetSettingsParameter)});

		// Logo

		boolean logoParameter = false;

		if (logo != null) {
			logoParameter = logo.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {String.valueOf(logoParameter)});

		// Permissions

		boolean permissionsParameter = true;

		if (permissions != null) {
			permissionsParameter = permissions.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {String.valueOf(permissionsParameter)});

		// Portlet configuration

		boolean portletConfigurationParameter = true;

		if (portletConfiguration != null) {
			portletConfigurationParameter = portletConfiguration.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {String.valueOf(portletConfigurationParameter)});

		// Portlet configuration all

		boolean portletConfigurationAllParameter = true;

		if (portletConfigurationAll != null) {
			portletConfigurationAllParameter =
				portletConfigurationAll.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {String.valueOf(portletConfigurationAllParameter)});

		// Portlet data

		boolean portletDataParameter = false;

		if (portletData != null) {
			portletDataParameter = portletData.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {String.valueOf(portletDataParameter)});

		// Portlet data all

		boolean portletDataAllParameter = false;

		if (portletDataAll != null) {
			portletDataAllParameter = portletDataAll.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {String.valueOf(portletDataAllParameter)});

		// Portlet setup all

		boolean portletSetupAllParameter = true;

		if (portletSetupAll != null) {
			portletSetupAllParameter = portletSetupAll.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {String.valueOf(portletSetupAllParameter)});

		// Range

		String rangeParameter =
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE;

		if (Validator.isNotNull(range)) {
			rangeParameter = range;
		}

		parameterMap.put(
			ExportImportDateUtil.RANGE, new String[] {rangeParameter});

		// Theme reference

		boolean themeReferenceParameter = false;

		if (themeReference != null) {
			themeReferenceParameter = themeReference.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {String.valueOf(themeReferenceParameter)});

		// Update last publish date

		boolean updateLastPublishDateParameter = true;

		if (updateLastPublishDate != null) {
			updateLastPublishDateParameter =
				updateLastPublishDate.booleanValue();
		}

		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {String.valueOf(updateLastPublishDateParameter)});

		// User id strategy

		String userIdStrategyParameter = UserIdStrategy.CURRENT_USER_ID;

		if (Validator.isNotNull(userIdStrategy)) {
			userIdStrategyParameter = userIdStrategy;
		}

		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {userIdStrategyParameter});

		return parameterMap;
	}

}