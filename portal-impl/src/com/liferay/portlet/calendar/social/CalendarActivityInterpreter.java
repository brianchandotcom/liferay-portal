/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class CalendarActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getEntryTitle(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		CalEvent event = CalEventLocalServiceUtil.getEvent(
			activity.getClassPK());

		return event.getTitle();
	}

	@Override
	protected String getPath(
		SocialActivity activity, ServiceContext serviceContext) {

		StringBundler sb = new StringBundler(4);

		sb.append("/calendar/find_event?redirect=");
		sb.append(HtmlUtil.escapeURL(serviceContext.getCurrentURL()));
		sb.append("&eventId=");
		sb.append(activity.getClassPK());

		return sb.toString();
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == CalendarActivityKeys.ADD_EVENT) {
			if (Validator.isNull(groupName)) {
				return "activity-calendar-event-add-event";
			}
			else {
				return "activity-calendar-event-add-event-in";
			}
		}
		else if (activityType == CalendarActivityKeys.UPDATE_EVENT) {
			if (Validator.isNull(groupName)) {
				return "activity-calendar-event-update-event";
			}
			else {
				return "activity-calendar-event-update-event-in";
			}
		}

		return StringPool.BLANK;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return CalEventPermission.contains(
			permissionChecker, activity.getClassPK(), actionId);
	}

	private static final String[] _CLASS_NAMES = {CalEvent.class.getName()};

}