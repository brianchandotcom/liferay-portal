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

package com.liferay.calendar.internal.notification;

import com.liferay.calendar.configuration.CalendarServiceConfigurationValues;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationTemplateContext;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.Format;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 */
@Component(immediate = true, service = {})
public class NotificationTemplateContextFactory {

	public static NotificationTemplateContext getInstance(
			NotificationType notificationType,
			NotificationTemplateType notificationTemplateType,
			CalendarBooking calendarBooking, User user)
		throws Exception {

		CalendarBooking parentCalendarBooking =
			calendarBooking.getParentCalendarBooking();

		Calendar calendar = parentCalendarBooking.getCalendar();

		NotificationTemplateContext notificationTemplateContext =
			new NotificationTemplateContext(
				CalendarServiceConfigurationValues.
					CALENDAR_NOTIFICATION_DEFAULT_TYPE);

		CalendarNotificationTemplate calendarNotificationTemplate =
			CalendarNotificationTemplateLocalServiceUtil.
				fetchCalendarNotificationTemplate(
					calendar.getCalendarId(), notificationType,
					notificationTemplateType);

		notificationTemplateContext.setCalendarNotificationTemplate(
			calendarNotificationTemplate);

		notificationTemplateContext.setCompanyId(
			calendarBooking.getCompanyId());
		notificationTemplateContext.setGroupId(calendarBooking.getGroupId());
		notificationTemplateContext.setCalendarId(calendar.getCalendarId());
		notificationTemplateContext.setNotificationTemplateType(
			notificationTemplateType);
		notificationTemplateContext.setNotificationType(notificationType);

		// Attributes

		Map<String, Serializable> attributes = new HashMap<>();

		Format userDateTimeFormat = _getUserDateTimeFormat(
			calendarBooking, user);

		String userTimezoneDisplayName = _getUserTimezoneDisplayName(user);

		String endTime =
			userDateTimeFormat.format(calendarBooking.getEndTime()) +
				StringPool.SPACE + userTimezoneDisplayName;

		attributes.put("endTime", endTime);

		attributes.put("location", calendarBooking.getLocation());

		Group group = _groupLocalService.getGroup(
			user.getCompanyId(), GroupConstants.GUEST);

		String portalURL = _getPortalURL(
			group.getCompanyId(), group.getGroupId());

		attributes.put("portalURL", portalURL);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			user.getLocale(), "com.liferay.calendar.web");

		attributes.put(
			"portletName",
			LanguageUtil.get(
				resourceBundle,
				"javax.portlet.title.".concat(CalendarPortletKeys.CALENDAR)));

		String startTime =
			userDateTimeFormat.format(calendarBooking.getStartTime()) +
				StringPool.SPACE + userTimezoneDisplayName;

		attributes.put("startTime", startTime);

		attributes.put("title", calendarBooking.getTitle(user.getLocale()));

		String calendarBookingURL = _getCalendarBookingURL(
			user, calendarBooking.getCalendarBookingId());

		attributes.put("url", calendarBookingURL);

		notificationTemplateContext.setAttributes(attributes);

		return notificationTemplateContext;
	}

	public static NotificationTemplateContext getInstance(
			NotificationType notificationType,
			NotificationTemplateType notificationTemplateType,
			CalendarBooking calendarBooking, User user,
			ServiceContext serviceContext)
		throws Exception {

		NotificationTemplateContext notificationTemplateContext = getInstance(
			notificationType, notificationTemplateType, calendarBooking, user);

		if ((serviceContext != null) &&
			Validator.isNotNull(
				serviceContext.getAttribute("instanceStartTime"))) {

			long instanceStartTime = (long)serviceContext.getAttribute(
				"instanceStartTime");

			Format userDateTimeFormat = _getUserDateTimeFormat(
				calendarBooking, user);

			String userTimezoneDisplayName = _getUserTimezoneDisplayName(user);

			String instanceStartTimeFormatted =
				userDateTimeFormat.format(instanceStartTime) +
					StringPool.SPACE + userTimezoneDisplayName;

			notificationTemplateContext.setAttribute(
				"instanceStartTime", instanceStartTimeFormatted);
		}

		return notificationTemplateContext;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static String _getCalendarBookingURL(
			User user, long calendarBookingId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(
			user.getCompanyId(), GroupConstants.GUEST);

		Layout layout = _layoutLocalService.fetchLayout(
			group.getDefaultPublicPlid());

		String portalURL = _getPortalURL(
			group.getCompanyId(), group.getGroupId());

		String layoutActualURL = PortalUtil.getLayoutActualURL(layout);

		String url = portalURL + layoutActualURL;

		String namespace = PortalUtil.getPortletNamespace(
			CalendarPortletKeys.CALENDAR);

		url = HttpUtil.addParameter(
			url, namespace + "mvcPath", "/view_calendar_booking.jsp");

		url = HttpUtil.addParameter(
			url, "p_p_id", CalendarPortletKeys.CALENDAR);
		url = HttpUtil.addParameter(url, "p_p_lifecycle", "0");
		url = HttpUtil.addParameter(
			url, "p_p_state", WindowState.MAXIMIZED.toString());
		url = HttpUtil.addParameter(
			url, namespace + "calendarBookingId", calendarBookingId);

		return url;
	}

	private static String _getPortalURL(long companyId, long groupId)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		return company.getPortalURL(groupId);
	}

	private static Format _getUserDateTimeFormat(
		CalendarBooking calendarBooking, User user) {

		TimeZone userTimezone = user.getTimeZone();

		if ((calendarBooking != null) && calendarBooking.isAllDay()) {
			userTimezone = TimeZone.getTimeZone(StringPool.UTC);
		}

		return FastDateFormatFactoryUtil.getDateTime(
			user.getLocale(), userTimezone);
	}

	private static String _getUserTimezoneDisplayName(User user) {
		TimeZone userTimezone = user.getTimeZone();

		return userTimezone.getDisplayName(
			false, TimeZone.SHORT, user.getLocale());
	}

	private static CompanyLocalService _companyLocalService;
	private static GroupLocalService _groupLocalService;
	private static LayoutLocalService _layoutLocalService;

}