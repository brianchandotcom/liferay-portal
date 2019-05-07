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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akos Thurzo
 */
public class LayoutStagingBackgroundTaskDisplay
	extends ExportImportBackgroundTaskDisplay {

	public LayoutStagingBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		super(backgroundTask);

		Map<String, Serializable> contextMap =
			backgroundTask.getTaskContextMap();

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				fetchExportImportConfiguration(
					MapUtil.getLong(contextMap, "exportImportConfigurationId"));

		if (exportImportConfiguration != null) {
			if ((exportImportConfiguration.getType() !=
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL) &&
				(exportImportConfiguration.getType() !=
					ExportImportConfigurationConstants.
						TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL)) {

				return;
			}

			contextMap = exportImportConfiguration.getSettingsMap();
		}

		long sourceGroupId = MapUtil.getLong(contextMap, "sourceGroupId");

		sourceGroup = GroupLocalServiceUtil.fetchGroup(sourceGroupId);
	}

	@Override
	public String getDisplayName(HttpServletRequest httpServletRequest) {
		if ((sourceGroup != null) && !sourceGroup.isStagingGroup() &&
			(backgroundTask.getGroupId() == sourceGroup.getGroupId())) {

			return LanguageUtil.get(httpServletRequest, "initial-publication");
		}

		if (Validator.isNull(backgroundTask.getName())) {
			return LanguageUtil.get(httpServletRequest, "untitled");
		}

		return backgroundTask.getName();
	}

	protected Group sourceGroup;

}