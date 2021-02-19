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

package com.liferay.dispatch.web.internal.portlet.action;

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.portal.kernel.scheduler.DispatchSchedulerEngineHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = {
		"javax.portlet.name=" + DispatchPortletKeys.DISPATCH,
		"mvc.command.name=/dispatch/edit_scheduled_task_dispatch_trigger"
	},
	service = MVCActionCommand.class
)
public class EditScheduledTaskDispatchTriggerMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Objects.equals(cmd, "runScheduledTask")) {
			_runScheduledTask(actionRequest);
		}
		else if (Objects.equals(cmd, "pause") ||
				 Objects.equals(cmd, "resume")) {

			_updateScheduledTask(actionRequest, cmd);
		}
	}

	private void _runScheduledTask(ActionRequest actionRequest)
		throws Exception {

		String jobName = ParamUtil.getString(actionRequest, "jobName");
		String groupName = ParamUtil.getString(actionRequest, "groupName");
		StorageType storageType = StorageType.valueOf(
			ParamUtil.getString(actionRequest, "storageType"));

		_dispatchSchedulerEngineHelper.run(
			jobName, groupName, storageType);
	}

	private void _updateScheduledTask(ActionRequest actionRequest, String cmd)
		throws Exception {

		String jobName = ParamUtil.getString(actionRequest, "jobName");
		String groupName = ParamUtil.getString(actionRequest, "groupName");
		StorageType storageType = StorageType.valueOf(
			ParamUtil.getString(actionRequest, "storageType"));

		if (Objects.equals(cmd, "pause")) {
			_dispatchSchedulerEngineHelper.
				pause(
					jobName, groupName, storageType);
		}
		else {
			_dispatchSchedulerEngineHelper.
				resume(
					jobName, groupName, storageType);
		}
	}

	@Reference
	private DispatchSchedulerEngineHelper
		_dispatchSchedulerEngineHelper;

}