/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/get_publication_status"
	},
	service = MVCResourceCommand.class
)
public class GetPublicationStatusMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		long ctProcessId = ParamUtil.getLong(resourceRequest, "ctProcessId");

		CTProcess ctProcess = _ctProcessLocalService.fetchCTProcess(
			ctProcessId);

		if (ctProcess == null) {
			_writeJSON(
				resourceRequest, resourceResponse, "danger",
				_language.get(httpServletRequest, "failed"), false);

			return;
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		if (backgroundTask == null) {
			_writeJSON(
				resourceRequest, resourceResponse, "danger",
				_language.get(httpServletRequest, "failed"), false);

			return;
		}

		if (backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_IN_PROGRESS) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"percentage",
					() -> {
						BackgroundTaskDisplay backgroundTaskDisplay =
							_backgroundTaskDisplayFactory.
								getBackgroundTaskDisplay(
									backgroundTask.getBackgroundTaskId());

						return backgroundTaskDisplay.getPercentage();
					}));

			return;
		}

		String displayType = "danger";
		String label = _language.get(httpServletRequest, "failed");
		boolean published = false;

		if ((backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_FAILED) &&
			StringUtil.matchesIgnoreCase(
				backgroundTask.getStatusMessage(), "duplicate entry")) {

			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			label = _language.get(httpServletRequest, "conflict");

			_writeJSON(
				resourceRequest, resourceResponse, displayType, label,
				published);

			throw new PortalException(
				StringBundler.concat(
					"The selected changes cannot be moved to the destination ",
					"change tracking collection ",
					taskContextMap.get("toCTCollectionId"),
					" because one or more of your selected changes from the ",
					"source change tracking collection ",
					taskContextMap.get("fromCTCollectionId"),
					" conflicts with a preexisting change in the destination ",
					"change tracking collection"));
		}

		if (backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_SUCCESSFUL) {

			displayType = "success";
			label = _language.get(httpServletRequest, "published");
			published = true;
		}

		_writeJSON(
			resourceRequest, resourceResponse, displayType, label, published);
	}

	private void _writeJSON(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			String displayType, String label, boolean published)
		throws IOException {

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"displayType", displayType
			).put(
				"label", label
			).put(
				"published", published
			));
	}

	@Reference
	private BackgroundTaskDisplayFactory _backgroundTaskDisplayFactory;

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}