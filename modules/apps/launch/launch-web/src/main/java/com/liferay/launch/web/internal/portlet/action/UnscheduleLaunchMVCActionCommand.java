/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.web.internal.portlet.action;

import com.liferay.launch.publisher.LaunchPublisher;
import com.liferay.launch.web.internal.constants.LaunchPortletKeys;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jose Luis Navarro
 */
@Component(
	property = {
		"jakarta.portlet.name=" + LaunchPortletKeys.LAUNCH,
		"mvc.command.name=/launch/unschedule_launch"
	},
	service = MVCActionCommand.class
)
public class UnscheduleLaunchMVCActionCommand
	extends BaseLaunchMVCActionCommand {

	@Override
	protected void doLaunchCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		long launchId = ParamUtil.getLong(actionRequest, "launchId");

		_objectEntryService.checkModelResourcePermission(
			_objectEntryLocalService.getObjectEntry(launchId),
			ActionKeys.UPDATE);

		_launchPublisher.unschedulePublish(launchId);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			JSONUtil.put(
				"redirect",
				portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"))));
	}

	@Reference
	private LaunchPublisher _launchPublisher;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

}