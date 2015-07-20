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

package com.liferay.blogs.web.lar.preferencesprocessor;

import com.liferay.exportimport.preferencesprocessor.ExportImportPreferencesProcessorCapability;
import com.liferay.portlet.display.template.preferencesprocessor.PortletDisplayTemplateExportPreferencesProcessorCapability;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	service = {
		ExportImportPreferencesProcessorCapability.class,
		BlogsPortletDisplayTemplateExportPreferencesProcessorCapability.class
	}
)
public class BlogsPortletDisplayTemplateExportPreferencesProcessorCapability
	extends PortletDisplayTemplateExportPreferencesProcessorCapability {

	@Override
	protected String getDisplayStyle(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		return BlogsExportImportPreferencesProcessorUtil.getDisplayStyle(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected long getDisplayStyleGroupId(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		return BlogsExportImportPreferencesProcessorUtil.getDisplayStyleGroupId(
			portletDataContext, portletId, portletPreferences);
	}

}