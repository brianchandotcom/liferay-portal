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

package com.liferay.portal.scripting.groovy.context;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class GroovyLARUtil {

	static Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE);
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			Boolean.TRUE.toString());
		parameterMap.put(PortletDataHandlerKeys.LOGO, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			UserIdStrategy.CURRENT_USER_ID);

		return parameterMap;
	}

	static void importGlobal(
		GroovyUser groovyUser, InputStream inputStream,
		GroovyScriptingContext groovyScriptingContext) {

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			groovyScriptingContext.getCompanyId())

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					groovyUser.user, companyGroup.getGroupId(), true, null,
					getParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					groovyUser.user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		ExportImportLocalServiceUtil.importLayouts(
			exportImportConfiguration, inputStream);
	}

	static void importLayouts(
		GroovyUser groovyUser, GroovySite groovySite, boolean privateLayout,
		InputStream inputStream) {

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					groovyUser.user, groovySite.group.getGroupId(),
					privateLayout, null, getParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					groovyUser.user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		ExportImportLocalServiceUtil.importLayouts(
			exportImportConfiguration, inputStream);
	}

	static void importPortletInfo(
		GroovyUser groovyUser, long groupId, String portletId,
		InputStream inputStream) {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, false);

		if (layouts.isEmpty()) {
			layouts = LayoutLocalServiceUtil.getLayouts(groupId, true);
		}

		Layout layout = layouts.get(0);

		Map<String, Serializable> importPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportPortletSettingsMap(
					groovyUser.user, layout.getPlid(), groupId, portletId,
					getParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					groovyUser.user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					importPortletSettingsMap);

		ExportImportLocalServiceUtil.importPortletInfo(
			exportImportConfiguration, inputStream);
	}

}