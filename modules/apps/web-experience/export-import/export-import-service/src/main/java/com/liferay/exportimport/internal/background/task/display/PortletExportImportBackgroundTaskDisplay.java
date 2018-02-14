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

package com.liferay.exportimport.internal.background.task.display;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akos Thurzo
 */
public class PortletExportImportBackgroundTaskDisplay
	extends ExportImportBackgroundTaskDisplay {

	public PortletExportImportBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		super(backgroundTask);

		try {
			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					getExportImportConfiguration(
						MapUtil.getLong(
							taskContextMap, "exportImportConfigurationId"));

			if ((exportImportConfiguration.getType() !=
					ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET) &&
				(exportImportConfiguration.getType() !=
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET) &&
				(exportImportConfiguration.getType() !=
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_LOCAL)) {

				throw new PortalException(
					"Invalid export import configuration type: " +
						exportImportConfiguration.getType());
			}

			portlet = PortletLocalServiceUtil.getPortletById(
				backgroundTask.getName());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDisplayName(HttpServletRequest request) {
		if (Validator.isNull(backgroundTask.getName())) {
			return LanguageUtil.get(request, "untitled");
		}

		return portlet.getDisplayName();
	}

	protected Portlet portlet;

}