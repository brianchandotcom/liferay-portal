/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.portlet.action;

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.web.internal.security.permisison.resource.SchedulerResponsePermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = {
		"jakarta.portlet.name=" + DispatchPortletKeys.DISPATCH,
		"mvc.command.name=/dispatch/edit_scheduler_response"
	},
	service = MVCActionCommand.class
)
public class EditSchedulerResponseMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String groupName = ParamUtil.getString(actionRequest, "groupName");
		String jobName = ParamUtil.getString(actionRequest, "jobName");
		StorageType storageType = StorageType.valueOf(
			ParamUtil.getString(actionRequest, "storageType"));

		if (Objects.equals(cmd, "pause")) {
			_pause(groupName, jobName, permissionChecker, storageType);
		}
		else if (Objects.equals(cmd, "resume")) {
			_resume(groupName, jobName, permissionChecker, storageType);
		}
		else if (Objects.equals(cmd, "runScheduledJob")) {
			_run(groupName, jobName, permissionChecker, storageType);
		}
	}

	private void _checkPermission(
			String groupName, String jobName,
			PermissionChecker permissionChecker, StorageType storageType)
		throws Exception {

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, groupName, storageType);

		if (schedulerResponse == null) {
			throw new SchedulerException(
				StringBundler.concat(
					"Unable to find scheduled job with group name \"",
					groupName, "\" and job name \"", jobName, "\""));
		}

		if (!SchedulerResponsePermission.contains(
				permissionChecker, schedulerResponse)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, ActionKeys.UPDATE);
		}
	}

	private void _pause(
			String groupName, String jobName,
			PermissionChecker permissionChecker, StorageType storageType)
		throws Exception {

		_checkPermission(groupName, jobName, permissionChecker, storageType);

		_schedulerEngineHelper.pause(jobName, groupName, storageType);
	}

	private void _resume(
			String groupName, String jobName,
			PermissionChecker permissionChecker, StorageType storageType)
		throws Exception {

		_checkPermission(groupName, jobName, permissionChecker, storageType);

		_schedulerEngineHelper.resume(jobName, groupName, storageType);
	}

	private void _run(
			String groupName, String jobName,
			PermissionChecker permissionChecker, StorageType storageType)
		throws Exception {

		_checkPermission(groupName, jobName, permissionChecker, storageType);

		_schedulerEngineHelper.run(
			permissionChecker.getCompanyId(), jobName, groupName, storageType);
	}

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}