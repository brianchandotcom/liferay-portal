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
import com.liferay.adaptive.media.web.internal.background.task.OptimizeImagesAllConfigurationsBackgroundTaskExecutor;
import com.liferay.adaptive.media.web.internal.background.task.OptimizeImagesSingleConfigurationBackgroundTaskExecutor;
import com.liferay.adaptive.media.web.internal.constants.AMPortletKeys;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AMPortletKeys.ADAPTIVE_MEDIA,
		"mvc.command.name=/adaptive_media/optimize_images"
	},
	service = MVCActionCommand.class
)
public class OptimizeImagesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doPermissionCheckedProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String entryUuid = ParamUtil.getString(actionRequest, "entryUuid");

		final String jobName = "optimizeImages-".concat(_portalUUID.generate());

		if (Validator.isNotNull(entryUuid)) {
			_optimizeImagesSingleConfiguration(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), jobName,
				entryUuid);
		}
		else {
			_optimizeImages(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(), jobName);
		}

		SessionMessages.add(actionRequest, "optimizeImages");
	}

	private BackgroundTask _optimizeImages(
			long userId, long companyId, String jobName)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			AMOptimizeImagesBackgroundTaskConstants.COMPANY_ID, companyId);
		taskContextMap.put(
			BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);

		try {
			return _backgroundTaskManager.addBackgroundTask(
				userId, CompanyConstants.SYSTEM, jobName,
				OptimizeImagesAllConfigurationsBackgroundTaskExecutor.class.
					getName(),
				taskContextMap, new ServiceContext());
		}
		catch (PortalException pe) {
			throw new PortalException(
				"Unable to schedule adaptive media images optimization", pe);
		}
	}

	private BackgroundTask _optimizeImagesSingleConfiguration(
			long userId, long companyId, String jobName,
			String configurationEntryUuid)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			AMOptimizeImagesBackgroundTaskConstants.COMPANY_ID, companyId);
		taskContextMap.put(
			AMOptimizeImagesBackgroundTaskConstants.CONFIGURATION_ENTRY_UUID,
			configurationEntryUuid);
		taskContextMap.put(
			BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);

		try {
			return _backgroundTaskManager.addBackgroundTask(
				userId, CompanyConstants.SYSTEM, jobName,
				OptimizeImagesSingleConfigurationBackgroundTaskExecutor.class.
					getName(),
				taskContextMap, new ServiceContext());
		}
		catch (PortalException pe) {
			throw new PortalException(
				"Unable to schedule adaptive media images optimization for " +
					"configuration " + configurationEntryUuid,
				pe);
		}
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private PortalUUID _portalUUID;

}