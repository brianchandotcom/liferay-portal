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

import static com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL;
import static com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants.TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL;

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

		try {
			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					getExportImportConfiguration(
						MapUtil.getLong(
							taskContextMap, "exportImportConfigurationId"));

			if ((exportImportConfiguration.getType() !=
					TYPE_PUBLISH_LAYOUT_LOCAL) &&
				(exportImportConfiguration.getType() !=
					TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL)) {

				return;
			}

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");

			sourceGroup = GroupLocalServiceUtil.getGroup(sourceGroupId);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDisplayName(HttpServletRequest request) {
		if ((sourceGroup != null) && !sourceGroup.isStagingGroup() &&
			(backgroundTask.getGroupId() == sourceGroup.getGroupId())) {

			return LanguageUtil.get(request, "initial-publication");
		}

		if (Validator.isNull(backgroundTask.getName())) {
			return LanguageUtil.get(request, "untitled");
		}

		return backgroundTask.getName();
	}

	protected Group sourceGroup;

}