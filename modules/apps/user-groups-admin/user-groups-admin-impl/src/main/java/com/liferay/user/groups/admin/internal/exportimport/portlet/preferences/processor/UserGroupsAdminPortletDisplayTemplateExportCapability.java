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

package com.liferay.user.groups.admin.internal.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portlet.display.template.exportimport.portlet.preferences.processor.PortletDisplayTemplateExportCapability;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	service = {
		Capability.class,
		UserGroupsAdminPortletDisplayTemplateExportCapability.class
	}
)
public class UserGroupsAdminPortletDisplayTemplateExportCapability
	extends PortletDisplayTemplateExportCapability {

	@Override
	protected String getDisplayStyle(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		return UserGroupsAdminExportImportPortletPreferencesProcessorUtil.
			getDisplayStyle(portletPreferences);
	}

	@Override
	protected long getDisplayStyleGroupId(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		return UserGroupsAdminExportImportPortletPreferencesProcessorUtil.
			getDisplayStyleGroupId(portletPreferences);
	}

	@Override
	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		super.setPortletLocalService(portletLocalService);
	}

}