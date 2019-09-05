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

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Akos Thurzo
 */
public class ExportImportConfigurationParameterMapFactoryUtil {

	public static Map<String, String[]> buildFullPublishParameterMap() {
		return _exportImportConfigurationParameterMapFactory.
			buildFullPublishParameterMap();
	}

	public static Map<String, String[]> buildParameterMap() {
		return _exportImportConfigurationParameterMapFactory.
			buildParameterMap();
	}

	public static Map<String, String[]> buildParameterMap(
		PortletRequest portletRequest) {

		return _exportImportConfigurationParameterMapFactory.buildParameterMap(
			portletRequest);
	}

	public static Map<String, String[]> buildParameterMap(
		String dataStrategy, Boolean deleteMissingLayouts,
		Boolean deletePortletData, Boolean deletions,
		Boolean ignoreLastPublishDate, Boolean layoutSetPrototypeLinkEnabled,
		Boolean layoutSetSettings, Boolean logo, Boolean permissions,
		Boolean portletConfiguration, Boolean portletConfigurationAll,
		List<String> portletConfigurationPortletIds, Boolean portletData,
		Boolean portletDataAll, List<String> portletDataPortletIds,
		Boolean portletSetupAll, List<String> portletSetupPortletIds,
		String range, Boolean themeReference, Boolean updateLastPublishDate,
		String userIdStrategy) {

		return _exportImportConfigurationParameterMapFactory.buildParameterMap(
			dataStrategy, deleteMissingLayouts, deletePortletData, deletions,
			ignoreLastPublishDate, layoutSetPrototypeLinkEnabled,
			layoutSetSettings, logo, permissions, portletConfiguration,
			portletConfigurationAll, portletConfigurationPortletIds,
			portletData, portletDataAll, portletDataPortletIds, portletSetupAll,
			portletSetupPortletIds, range, themeReference,
			updateLastPublishDate, userIdStrategy);
	}

	private static volatile ExportImportConfigurationParameterMapFactory
		_exportImportConfigurationParameterMapFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportConfigurationParameterMapFactory.class,
				ExportImportConfigurationParameterMapFactoryUtil.class,
				"_exportImportConfigurationParameterMapFactory", false);

}