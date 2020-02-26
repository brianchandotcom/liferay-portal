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

package com.liferay.adaptive.media.web.internal.portlet.action;

import com.liferay.adaptive.media.constants.AMOptimizeImagesBackgroundTaskConstants;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.web.internal.background.task.OptimizeImagesSingleConfigurationBackgroundTaskExecutor;
import com.liferay.adaptive.media.web.internal.constants.AMPortletKeys;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AMPortletKeys.ADAPTIVE_MEDIA,
		"mvc.command.name=/adaptive_media/adapted_images_percentage"
	},
	service = MVCResourceCommand.class
)
public class AdaptedImagesPercentageMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		String entryUuid = ParamUtil.getString(resourceRequest, "entryUuid");

		int entriesCount = _amImageEntryLocalService.getAMImageEntriesCount(
			companyId, entryUuid);

		int expectedEntriesCount =
			_amImageEntryLocalService.getExpectedAMImageEntriesCount(companyId);

		List<BackgroundTask> optimizeImageSingleBackgroundTasks =
			BackgroundTaskManagerUtil.getBackgroundTasks(
				CompanyConstants.SYSTEM,
				OptimizeImagesSingleConfigurationBackgroundTaskExecutor.class.
					getName(),
				BackgroundTaskConstants.STATUS_IN_PROGRESS);

		boolean isTaskInProgress = false;

		for (BackgroundTask backgroundTask :
				optimizeImageSingleBackgroundTasks) {

			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			String configurationEntryUuid = (String)taskContextMap.get(
				AMOptimizeImagesBackgroundTaskConstants.
					CONFIGURATION_ENTRY_UUID);

			if (configurationEntryUuid == entryUuid) {
				isTaskInProgress = true;
			}
		}

		JSONObject jsonObject = null;

		int min = Math.min(entriesCount, expectedEntriesCount);

		if (isTaskInProgress) {
			jsonObject = JSONUtil.put(
				"adaptedImages", String.valueOf(min)
			).put(
				"errors", 0
			).put(
				"totalImages", String.valueOf(expectedEntriesCount)
			);
		}
		else {
			jsonObject = JSONUtil.put(
				"adaptedImages", String.valueOf(min)
			).put(
				"errors", String.valueOf(expectedEntriesCount - min)
			).put(
				"totalImages", String.valueOf(expectedEntriesCount)
			);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

}