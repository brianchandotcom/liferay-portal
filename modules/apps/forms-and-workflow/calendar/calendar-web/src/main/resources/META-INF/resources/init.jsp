<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.calendar.constants.CalendarActionKeys" %><%@
page import="com.liferay.calendar.constants.CalendarWebKeys" %><%@
page import="com.liferay.calendar.exception.CalendarBookingDurationException" %><%@
page import="com.liferay.calendar.exception.CalendarBookingRecurrenceException" %><%@
page import="com.liferay.calendar.exception.CalendarNameException" %><%@
page import="com.liferay.calendar.exception.CalendarResourceCodeException" %><%@
page import="com.liferay.calendar.exception.CalendarResourceNameException" %><%@
page import="com.liferay.calendar.exception.DuplicateCalendarResourceException" %><%@
page import="com.liferay.calendar.exception.NoSuchResourceException" %><%@
page import="com.liferay.calendar.model.Calendar" %><%@
page import="com.liferay.calendar.model.CalendarBooking" %><%@
page import="com.liferay.calendar.model.CalendarNotificationTemplate" %><%@
page import="com.liferay.calendar.model.CalendarNotificationTemplateConstants" %><%@
page import="com.liferay.calendar.model.CalendarResource" %><%@
page import="com.liferay.calendar.notification.NotificationField" %><%@
page import="com.liferay.calendar.notification.NotificationTemplateType" %><%@
page import="com.liferay.calendar.notification.NotificationType" %><%@
page import="com.liferay.calendar.notification.impl.NotificationUtil" %><%@
page import="com.liferay.calendar.recurrence.Frequency" %><%@
page import="com.liferay.calendar.recurrence.PositionalWeekday" %><%@
page import="com.liferay.calendar.recurrence.Recurrence" %><%@
page import="com.liferay.calendar.recurrence.Weekday" %><%@
page import="com.liferay.calendar.service.CalendarBookingLocalServiceUtil" %><%@
page import="com.liferay.calendar.service.CalendarBookingServiceUtil" %><%@
page import="com.liferay.calendar.service.CalendarNotificationTemplateLocalServiceUtil" %><%@
page import="com.liferay.calendar.service.CalendarResourceServiceUtil" %><%@
page import="com.liferay.calendar.service.CalendarServiceUtil" %><%@
page import="com.liferay.calendar.service.configuration.CalendarServiceConfigurationValues" %><%@
page import="com.liferay.calendar.service.permission.CalendarPermission" %><%@
page import="com.liferay.calendar.service.permission.CalendarPortletPermission" %><%@
page import="com.liferay.calendar.service.permission.CalendarResourcePermission" %><%@
page import="com.liferay.calendar.util.CalendarResourceUtil" %><%@
page import="com.liferay.calendar.util.CalendarUtil" %><%@
page import="com.liferay.calendar.util.ColorUtil" %><%@
page import="com.liferay.calendar.util.JCalendarUtil" %><%@
page import="com.liferay.calendar.util.RecurrenceUtil" %><%@
page import="com.liferay.calendar.util.comparator.CalendarNameComparator" %><%@
page import="com.liferay.calendar.web.internal.search.CalendarResourceDisplayTerms" %><%@
page import="com.liferay.calendar.web.internal.search.CalendarResourceSearch" %><%@
page import="com.liferay.calendar.workflow.CalendarBookingWorkflowConstants" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.BrowserSnifferUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatConstants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.SessionClicks" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.util.comparator.UserScreenNameComparator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.rss.util.RSSUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.Iterator" %><%@
page import="java.util.List" %><%@
page import="java.util.Objects" %><%@
page import="java.util.TimeZone" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
int defaultDuration = GetterUtil.getInteger(portletPreferences.getValue("defaultDuration", null), 60);
String defaultView = portletPreferences.getValue("defaultView", "week");
String timeFormat = GetterUtil.getString(portletPreferences.getValue("timeFormat", "locale"));
String timeZoneId = portletPreferences.getValue("timeZoneId", user.getTimeZoneId());
boolean usePortalTimeZone = GetterUtil.getBoolean(portletPreferences.getValue("usePortalTimeZone", Boolean.TRUE.toString()));
int weekStartsOn = GetterUtil.getInteger(portletPreferences.getValue("weekStartsOn", null));

String sessionClicksDefaultView = SessionClicks.get(request, "com.liferay.calendar.web_defaultView", defaultView);

if (usePortalTimeZone) {
	timeZoneId = user.getTimeZoneId();
}

boolean displaySchedulerHeader = GetterUtil.getBoolean(portletPreferences.getValue("displaySchedulerHeader", null), true);
boolean displaySchedulerOnly = GetterUtil.getBoolean(portletPreferences.getValue("displaySchedulerOnly", null));
boolean showUserEvents = GetterUtil.getBoolean(portletPreferences.getValue("showUserEvents", null), true);

boolean showAgendaView = GetterUtil.getBoolean(portletPreferences.getValue("showAgendaView", null), true);
boolean showDayView = GetterUtil.getBoolean(portletPreferences.getValue("showDayView", null), true);
boolean showMonthView = GetterUtil.getBoolean(portletPreferences.getValue("showMonthView", null), true);
boolean showWeekView = GetterUtil.getBoolean(portletPreferences.getValue("showWeekView", null), true);

boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean(portletPreferences.getValue("enableRss", null), true);
int rssDelta = GetterUtil.getInteger(portletPreferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = portletPreferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
String rssFeedType = portletPreferences.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
long rssTimeInterval = GetterUtil.getLong(portletPreferences.getValue("rssTimeInterval", StringPool.BLANK), Time.WEEK);

CalendarBooking calendarBooking = (CalendarBooking)request.getAttribute(CalendarWebKeys.CALENDAR_BOOKING);

CalendarResource groupCalendarResource = CalendarResourceUtil.getScopeGroupCalendarResource(liferayPortletRequest, scopeGroupId);
CalendarResource userCalendarResource = null;

if (showUserEvents || !themeDisplay.isSignedIn()) {
	userCalendarResource = CalendarResourceUtil.getUserCalendarResource(liferayPortletRequest, themeDisplay.getUserId());
}

Calendar userDefaultCalendar = null;

if (userCalendarResource != null) {
	long defaultCalendarId = userCalendarResource.getDefaultCalendarId();

	if (defaultCalendarId > 0) {
		userDefaultCalendar = CalendarServiceUtil.getCalendar(defaultCalendarId);
	}
}

List<Calendar> groupCalendars = Collections.emptyList();

boolean showSiteCalendars = false;

if (groupCalendarResource != null) {
	showSiteCalendars = (userCalendarResource == null) || (groupCalendarResource.getCalendarResourceId() != userCalendarResource.getCalendarResourceId());
}

if (showSiteCalendars) {
	groupCalendars = CalendarServiceUtil.search(themeDisplay.getCompanyId(), null, new long[] {groupCalendarResource.getCalendarResourceId()}, null, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);
}

List<Calendar> userCalendars = Collections.emptyList();

if (userCalendarResource != null) {
	userCalendars = CalendarServiceUtil.search(themeDisplay.getCompanyId(), null, new long[] {userCalendarResource.getCalendarResourceId()}, null, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);
}

List<Calendar> otherCalendars = new ArrayList<Calendar>();

long[] calendarIds = StringUtil.split(SessionClicks.get(request, "com.liferay.calendar.web_otherCalendars", StringPool.BLANK), 0L);

Calendar defaultCalendar = null;

for (long calendarId : calendarIds) {
	Calendar calendar = CalendarServiceUtil.fetchCalendar(calendarId);

	if (calendar != null) {
		CalendarResource calendarResource = calendar.getCalendarResource();

		if (calendarResource.isActive()) {
			otherCalendars.add(calendar);
		}
	}
}

for (Calendar groupCalendar : groupCalendars) {
	if (groupCalendar.isDefaultCalendar() && CalendarPermission.contains(themeDisplay.getPermissionChecker(), groupCalendar, CalendarActionKeys.MANAGE_BOOKINGS)) {
		defaultCalendar = groupCalendar;
	}
}

if (defaultCalendar == null) {
	for (Calendar userCalendar : userCalendars) {
		if (userCalendar.isDefaultCalendar()) {
			defaultCalendar = userCalendar;
		}
	}
}

TimeZone userTimeZone = CalendarUtil.getCalendarBookingDisplayTimeZone(calendarBooking, TimeZone.getTimeZone(timeZoneId));
TimeZone utcTimeZone = TimeZone.getTimeZone(StringPool.UTC);

Format dateFormatLongDate = FastDateFormatFactoryUtil.getDate(FastDateFormatConstants.LONG, locale, userTimeZone);

Format dateFormatTime = null;

boolean useIsoTimeFormat = timeFormat.equals("24-hour") || (timeFormat.equals("locale") && !DateUtil.isFormatAmPm(locale));

if (useIsoTimeFormat) {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm", locale, userTimeZone);
}
else {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat("hh:mm a", locale, userTimeZone);
}
%>