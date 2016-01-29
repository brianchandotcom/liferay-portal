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

package com.liferay.background.task.portlet.web.action;

import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.exception.NoSuchBackgroundTaskException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=*", "mvc.command.name=deleteBackgroundTask"
	},
	service = MVCActionCommand.class
)
public class DeleteBackgroundTaskMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteBackgroundTask(ActionRequest actionRequest)
		throws PortalException {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, "backgroundTaskId");

		_backgroundTaskLocalService.deleteBackgroundTask(backgroundTaskId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			deleteBackgroundTask(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchBackgroundTaskException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setBackgroundTaskLocalService(
		BackgroundTaskLocalService backgroundTaskLocalService) {

		_backgroundTaskLocalService = backgroundTaskLocalService;
	}

	private BackgroundTaskLocalService _backgroundTaskLocalService;

}