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

package com.liferay.calendar.web.internal.social;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.social.CalendarActivityKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "javax.portlet.name=" + CalendarPortletKeys.CALENDAR,
	service = SocialActivityInterpreter.class
)
public class CalendarActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(
				activity.getClassPK());

		long plid = _portal.getPlidFromPortletId(
			calendarBooking.getGroupId(), CalendarPortletKeys.CALENDAR);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			serviceContext.getRequest(), CalendarPortletKeys.CALENDAR, plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view_calendar_booking.jsp");
		portletURL.setParameter("backURL", serviceContext.getCurrentURL());
		portletURL.setParameter(
			"calendarBookingId", String.valueOf(activity.getClassPK()));
		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL.toString();
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == CalendarActivityKeys.ADD_CALENDAR_BOOKING) {
			if (Validator.isNull(groupName)) {
				return "activity-calendar-booking-add-booking";
			}

			return "activity-calendar-booking-add-booking-in";
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-calendar-booking-move-to-trash";
			}

			return "activity-calendar-booking-move-to-trash-in";
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-calendar-booking-restore-from-trash";
			}

			return "activity-calendar-booking-restore-from-trash-in";
		}
		else if (activityType == CalendarActivityKeys.UPDATE_CALENDAR_BOOKING) {
			if (Validator.isNull(groupName)) {
				return "activity-calendar-booking-update-booking";
			}

			return "activity-calendar-booking-update-booking-in";
		}

		return StringPool.BLANK;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(
				activity.getClassPK());

		return _calendarModelResourcePermission.contains(
			permissionChecker, calendarBooking.getCalendarId(), actionId);
	}

	private static final String[] _CLASS_NAMES = {
		CalendarBooking.class.getName()
	};

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)"
	)
	private ModelResourcePermission<Calendar> _calendarModelResourcePermission;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.calendar.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}