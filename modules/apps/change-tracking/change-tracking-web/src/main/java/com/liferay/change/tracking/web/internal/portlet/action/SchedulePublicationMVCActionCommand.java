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

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.change.tracking.web.internal.scheduler.ScheduledPublishInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS,
		"mvc.command.name=/change_lists/reschedule_publication",
		"mvc.command.name=/change_lists/schedule_publication",
		"mvc.command.name=/change_lists/unschedule_publication"
	},
	service = MVCActionCommand.class
)
public class SchedulePublicationMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Date publishDate = null;

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (!actionName.equals("/change_lists/unschedule_publication")) {
			int day = ParamUtil.getInteger(actionRequest, "publishTimeDay");
			int minute = ParamUtil.getInteger(
				actionRequest, "publishTimeMinute");
			int month = ParamUtil.getInteger(actionRequest, "publishTimeMonth");
			int year = ParamUtil.getInteger(actionRequest, "publishTimeYear");

			int hour = ParamUtil.getInteger(actionRequest, "publishTimeHour");

			if (ParamUtil.getInteger(actionRequest, "publishTimeAmPm") ==
					Calendar.PM) {

				hour += 12;
			}

			publishDate = _portal.getDate(
				month, day, year, hour, minute, themeDisplay.getTimeZone(),
				PortalException.class);

			Date currentDate = new Date(System.currentTimeMillis());

			if (!publishDate.after(currentDate)) {
				SessionErrors.add(actionRequest, "publishTime");

				_portal.copyRequestParameters(actionRequest, actionResponse);

				actionResponse.setRenderParameter(
					"mvcPath", "/change_lists/schedule_publication.jsp");

				return;
			}
		}

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		if (!actionName.equals("/change_lists/schedule_publication")) {
			for (ScheduledPublishInfo scheduledPublishInfo :
					_publishScheduler.getScheduledPublishInfos()) {

				CTCollection ctCollection =
					scheduledPublishInfo.getCTCollection();

				if (ctCollection.getCtCollectionId() == ctCollectionId) {
					_publishScheduler.unschedulePublish(
						scheduledPublishInfo.getJobName());
				}
			}
		}

		if (publishDate != null) {
			_publishScheduler.schedulePublish(
				ctCollectionId, themeDisplay.getUserId(), publishDate);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private PublishScheduler _publishScheduler;

}