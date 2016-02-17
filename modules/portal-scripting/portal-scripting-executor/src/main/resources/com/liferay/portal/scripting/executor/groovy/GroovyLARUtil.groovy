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

package com.liferay.portal.scripting.executor.groovy;

import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class GroovyLARUtil {

	static Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

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
		parameterMap.put(
			PortletDataHandlerKeys.LOGO, Boolean.TRUE.toString());
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
			PortletDataHandlerKeys.PORTLET_DATA,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			Boolean.TRUE.toString());
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

		LayoutLocalServiceUtil.importLayouts(
			groovyUser.user.getUserId(), companyGroup.getGroupId(), true,
			getParameterMap(), inputStream);
	}

	static void importLayouts(
		GroovyUser groovyUser, GroovySite groovySite, boolean privateLayout,
		InputStream inputStream,
		GroovyScriptingContext groovyScriptingContext) {

		LayoutLocalServiceUtil.importLayouts(
			groovyUser.user.getUserId(), groovySite.group.getGroupId(),
			privateLayout, getParameterMap(), inputStream);
	}

	static void importPortletInfo(
		GroovyUser groovyUser, long groupId, String portletId,
		InputStream inputStream,
		GroovyScriptingContext groovyScriptingContext) {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, false);

		if (layouts.isEmpty()) {
			layouts = LayoutLocalServiceUtil.getLayouts(groupId, true);
		}

		Layout layout = layouts.get(0);

		LayoutLocalServiceUtil.importPortletInfo(
			groovyUser.user.getUserId(), layout.getPlid(),
			groupId, portletId, getParameterMap(), inputStream);
	}

}