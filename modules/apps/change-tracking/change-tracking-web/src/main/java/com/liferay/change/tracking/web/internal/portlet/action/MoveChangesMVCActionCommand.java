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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/move_changes"
	},
	service = MVCActionCommand.class
)
public class MoveChangesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fromCTCollectionId = ParamUtil.getLong(
			actionRequest, "fromCTCollectionId");
		long toCTCollectionId = ParamUtil.getLong(
			actionRequest, "toCTCollectionId");

		CTProcess ctProcess = null;

		String displayType = null;
		String label = null;

		if ((fromCTCollectionId != toCTCollectionId) &&
			(fromCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) &&
			(toCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			long[] ctEntryIds = ParamUtil.getLongValues(
				actionRequest, "ctEntryIds");

			try {
				CTCollection fromCTCollection =
					_ctCollectionLocalService.getCTCollection(
						fromCTCollectionId);
				CTCollection toCTCollection =
					_ctCollectionLocalService.getCTCollection(toCTCollectionId);

				Map<Long, List<ConflictInfo>> conflictInfoMap =
					_ctCollectionLocalService.checkConflicts(
						fromCTCollection.getCompanyId(), ctEntryIds,
						fromCTCollectionId, fromCTCollection.getName(),
						toCTCollectionId, toCTCollection.getName());

				if (conflictInfoMap.isEmpty()) {
					ctProcess = _ctCollectionService.moveCTEntries(
						fromCTCollectionId, toCTCollectionId, ctEntryIds);
				}
				else {
					displayType = "danger";
					label = _language.get(themeDisplay.getLocale(), "conflict");
				}
			}
			catch (PortalException portalException) {
				SessionErrors.add(actionRequest, portalException.getClass());
			}
		}

		hideDefaultSuccessMessage(actionRequest);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		JSONObject jsonObject = JSONUtil.put(
			"redirect", ctProcess == null
		).put(
			"redirectURL", redirect
		);

		if (ctProcess != null) {
			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(actionResponse);

			ResourceURL statusURL = liferayPortletResponse.createResourceURL();

			statusURL.setParameter(
				"ctProcessId", String.valueOf(ctProcess.getCtProcessId()));
			statusURL.setResourceID("/change_tracking/get_publication_status");

			jsonObject.put("statusURL", statusURL.toString());

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.fetchBackgroundTask(
					ctProcess.getBackgroundTaskId());

			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_SUCCESSFUL) {

				displayType = "success";
				label = _language.get(themeDisplay.getLocale(), "published");
			}

			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_FAILED) {

				displayType = "danger";
				label = _language.get(themeDisplay.getLocale(), "failed");
			}

			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_NEW) {

				displayType = "warning";
				label = _language.get(themeDisplay.getLocale(), "pending");
			}
		}

		if ((displayType != null) && (label != null)) {
			jsonObject.put(
				"displayType", displayType
			).put(
				"label", label
			);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}