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

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerService;
import com.liferay.dispatch.web.internal.security.permisison.resource.DispatchTriggerPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Calendar;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"javax.portlet.name=" + DispatchPortletKeys.DISPATCH,
		"mvc.command.name=/dispatch/edit_dispatch_trigger"
	},
	service = MVCActionCommand.class
)
public class EditDispatchTriggerMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteDispatchTrigger(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteDispatchTriggerIds = null;

		long dispatchTriggerId = ParamUtil.getLong(
			actionRequest, "dispatchTriggerId");

		if (dispatchTriggerId > 0) {
			deleteDispatchTriggerIds = new long[] {dispatchTriggerId};
		}
		else {
			deleteDispatchTriggerIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteDispatchTriggerIds"),
				0L);
		}

		for (long deleteDispatchTriggerId : deleteDispatchTriggerIds) {
			_dispatchTriggerService.deleteDispatchTrigger(
				deleteDispatchTriggerId);
		}
	}

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Objects.equals(cmd, Constants.ADD) ||
				Objects.equals(cmd, Constants.UPDATE)) {

				updateDispatchTrigger(actionRequest, actionResponse);
			}
			else if (Objects.equals(cmd, Constants.DELETE)) {
				deleteDispatchTrigger(actionRequest);
			}
			else if (Objects.equals(cmd, "runProcess")) {
				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(actionResponse);

				httpServletResponse.setContentType(
					ContentTypes.APPLICATION_JSON);

				writeJSON(actionResponse, runProcess(actionRequest));

				hideDefaultSuccessMessage(actionRequest);
			}
			else if (Objects.equals(cmd, "schedule")) {
				scheduleDispatchTrigger(actionRequest);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			SessionErrors.add(actionRequest, exception.getClass());
		}
	}

	protected JSONObject runProcess(ActionRequest actionRequest)
		throws PortalException {

		long dispatchTriggerId = ParamUtil.getLong(
			actionRequest, "dispatchTriggerId");

		_checkPermission(actionRequest, dispatchTriggerId);

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			_sendMessage(dispatchTriggerId);
		}
		catch (Exception exception) {
			hideDefaultErrorMessage(actionRequest);

			_log.error(exception, exception);

			jsonObject.put(
				"error", exception.getMessage()
			).put(
				"success", false
			);
		}

		jsonObject.put("success", true);

		return jsonObject;
	}

	protected void scheduleDispatchTrigger(ActionRequest actionRequest)
		throws PortalException {

		long dispatchTriggerId = ParamUtil.getLong(
			actionRequest, "dispatchTriggerId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String cronExpression = ParamUtil.getString(
			actionRequest, "cronExpression");
		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.valueOf(
				ParamUtil.getInteger(actionRequest, "dispatchTaskClusterMode"));
		int endDateMonth = ParamUtil.getInteger(actionRequest, "endDateMonth");
		int endDateDay = ParamUtil.getInteger(actionRequest, "endDateDay");
		int endDateYear = ParamUtil.getInteger(actionRequest, "endDateYear");
		int endDateHour = ParamUtil.getInteger(actionRequest, "endDateHour");
		int endDateMinute = ParamUtil.getInteger(
			actionRequest, "endDateMinute");

		int endDateAmPm = ParamUtil.getInteger(actionRequest, "endDateAmPm");

		if (endDateAmPm == Calendar.PM) {
			endDateHour += 12;
		}

		boolean neverEnd = ParamUtil.getBoolean(actionRequest, "neverEnd");
		boolean overlapAllowed = ParamUtil.getBoolean(
			actionRequest, "overlapAllowed");

		int startDateMonth = ParamUtil.getInteger(
			actionRequest, "startDateMonth");
		int startDateDay = ParamUtil.getInteger(actionRequest, "startDateDay");
		int startDateYear = ParamUtil.getInteger(
			actionRequest, "startDateYear");
		int startDateHour = ParamUtil.getInteger(
			actionRequest, "startDateHour");
		int startDateMinute = ParamUtil.getInteger(
			actionRequest, "startDateMinute");

		int startDateAmPm = ParamUtil.getInteger(
			actionRequest, "startDateAmPm");

		if (startDateAmPm == Calendar.PM) {
			startDateHour += 12;
		}

		_dispatchTriggerService.updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, dispatchTaskClusterMode,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverEnd, overlapAllowed, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute);
	}

	protected DispatchTrigger updateDispatchTrigger(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long dispatchTriggerId = ParamUtil.getLong(
			actionRequest, "dispatchTriggerId");

		String name = ParamUtil.getString(actionRequest, "name");

		UnicodeProperties dispatchTaskSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				ParamUtil.getString(actionRequest, "dispatchTaskSettings")
			).build();

		DispatchTrigger dispatchTrigger = null;

		if (dispatchTriggerId > 0) {
			dispatchTrigger = _dispatchTriggerService.updateDispatchTrigger(
				dispatchTriggerId, dispatchTaskSettingsUnicodeProperties, name);
		}
		else {
			String dispatchTaskExecutorType = ParamUtil.getString(
				actionRequest, "dispatchTaskExecutorType");

			dispatchTrigger = _dispatchTriggerService.addDispatchTrigger(
				_portal.getUserId(actionRequest), dispatchTaskExecutorType,
				dispatchTaskSettingsUnicodeProperties, name);
		}

		return dispatchTrigger;
	}

	protected void writeJSON(ActionResponse actionResponse, Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private void _checkPermission(
			ActionRequest actionRequest, long dispatchTriggerId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DispatchTriggerPermission.contains(
			themeDisplay.getPermissionChecker(), dispatchTriggerId,
			ActionKeys.UPDATE);
	}

	private void _sendMessage(long dispatchTriggerId) {
		Message message = new Message();

		message.setPayload(
			String.valueOf(
				JSONUtil.put("dispatchTriggerId", dispatchTriggerId)));

		_destination.send(message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditDispatchTriggerMVCActionCommand.class);

	@Reference(
		target = "(destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME + ")"
	)
	private Destination _destination;

	@Reference
	private DispatchTriggerService _dispatchTriggerService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}