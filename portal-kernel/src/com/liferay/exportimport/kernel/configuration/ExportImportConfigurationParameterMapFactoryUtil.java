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

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Akos Thurzo
 */
@ProviderType
public class ExportImportConfigurationParameterMapFactoryUtil {

	public static Map<String, String[]> buildParameterMap() {
		return
			_exportImportConfigurationParameterMapFactory.buildParameterMap();
	}

	public static Map<String, String[]> buildParameterMap(
		PortletRequest portletRequest) {

		return _exportImportConfigurationParameterMapFactory.buildParameterMap(
			portletRequest);
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

		return _exportImportConfigurationParameterMapFactory.buildParameterMap(
			dataStrategy, deleteMissingLayouts, deletePortletData,
			ignoreLastPublishDate, layoutSetPrototypeLinkEnabled,
			layoutSetSettings, logo, permissions, portletConfiguration,
			portletConfigurationAll, portletData, portletDataAll,
			portletSetupAll, range, themeReference, updateLastPublishDate,
			userIdStrategy);
	}

	private static volatile ExportImportConfigurationParameterMapFactory
		_exportImportConfigurationParameterMapFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportConfigurationParameterMapFactory.class,
				ExportImportConfigurationParameterMapFactoryUtil.class,
				"_exportImportConfigurationParameterMapFactory", false);

}