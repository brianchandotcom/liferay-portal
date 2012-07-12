/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.social;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class CalendarActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!CalEventPermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		int activityType = activity.getType();

		// Link

		CalEvent event = CalEventLocalServiceUtil.getEvent(
			activity.getClassPK());

		String link =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/calendar/find_event?redirect=" +
					HtmlUtil.escapeURL(themeDisplay.getURLCurrent()) +
						"&eventId=" + activity.getClassPK();

		// Title

		String titlePattern = null;

		if (activityType == CalendarActivityKeys.ADD_EVENT) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-calendar-add-event";
			}
			else {
				titlePattern = "activity-calendar-add-event-in";
			}
		}
		else if (activityType == CalendarActivityKeys.UPDATE_EVENT) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-calendar-update-event";
			}
			else {
				titlePattern = "activity-calendar-update-event-in";
			}
		}

		String eventTitle = getValue(
			activity.getExtraData(), "title", event.getTitle());

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, wrapLink(link, eventTitle)
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String body = StringPool.BLANK;

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		CalEvent.class.getName()
	};

}