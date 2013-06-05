<%--
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
--%>

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CalEvent event = null;

if (row != null) {
	event = (CalEvent)row.getObject();
}
else {
	event = (CalEvent)request.getAttribute("view_event.jsp-event");
}

Recurrence recurrence = null;

int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
if (event.getRepeating()) {
	recurrence = event.getRecurrenceObj();
	recurrenceType = recurrence.getFrequency();
}

int dailyType = ParamUtil.getInteger(request, "dailyType");
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByDay() != null) {
		dailyType = 1;
	}
}

int dailyInterval = ParamUtil.getInteger(request, "dailyInterval", 1);
if (event.getRepeating() && (recurrence != null)) {
	dailyInterval = recurrence.getInterval();
}

int weeklyInterval = ParamUtil.getInteger(request, "weeklyInterval", 1);
if (event.getRepeating() && (recurrence != null)) {
	weeklyInterval = recurrence.getInterval();
}

boolean weeklyPosSu = _getWeeklyDayPos(request, Calendar.SUNDAY, event, recurrence);
boolean weeklyPosMo = _getWeeklyDayPos(request, Calendar.MONDAY, event, recurrence);
boolean weeklyPosTu = _getWeeklyDayPos(request, Calendar.TUESDAY, event, recurrence);
boolean weeklyPosWe = _getWeeklyDayPos(request, Calendar.WEDNESDAY, event, recurrence);
boolean weeklyPosTh = _getWeeklyDayPos(request, Calendar.THURSDAY, event, recurrence);
boolean weeklyPosFr = _getWeeklyDayPos(request, Calendar.FRIDAY, event, recurrence);
boolean weeklyPosSa = _getWeeklyDayPos(request, Calendar.SATURDAY, event, recurrence);

int monthlyType = ParamUtil.getInteger(request, "monthlyType");
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonthDay() == null) {
		monthlyType = 1;
	}
}

int monthlyDay0 = ParamUtil.getInteger(request, "monthlyDay0", 15);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonthDay() != null) {
		monthlyDay0 = recurrence.getByMonthDay()[0];
	}
}

int monthlyInterval0 = ParamUtil.getInteger(request, "monthlyInterval0", 1);
if (event.getRepeating() && (recurrence != null)) {
	monthlyInterval0 = recurrence.getInterval();
}

int monthlyPos = ParamUtil.getInteger(request, "monthlyPos", 1);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		monthlyPos = recurrence.getByMonth()[0];
	}
	else if (recurrence.getByDay() != null) {
		monthlyPos = recurrence.getByDay()[0].getDayPosition();
	}
}

int monthlyDay1 = ParamUtil.getInteger(request, "monthlyDay1", Calendar.SUNDAY);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		monthlyDay1 = -1;
	}
	else if (recurrence.getByDay() != null) {
		monthlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
	}
}

int monthlyInterval1 = ParamUtil.getInteger(request, "monthlyInterval1", 1);
if (event.getRepeating() && (recurrence != null)) {
	monthlyInterval1 = recurrence.getInterval();
}

int yearlyType = 0;
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonthDay() == null) {
		yearlyType = 1;
	}
}

int yearlyMonth0 = ParamUtil.getInteger(request, "yearlyMonth0", Calendar.JANUARY);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonth() == null) {
		yearlyMonth0 = recurrence.getDtStart().get(Calendar.MONTH);
	}
	else {
		yearlyMonth0 = recurrence.getByMonth()[0];
	}
}

int yearlyDay0 = ParamUtil.getInteger(request, "yearlyDay0", 15);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonthDay() == null) {
		yearlyDay0 = recurrence.getDtStart().get(Calendar.DATE);
	}
	else {
		yearlyDay0 = recurrence.getByMonthDay()[0];
	}
}

int yearlyInterval0 = ParamUtil.getInteger(request, "yearlyInterval0", 1);
if (event.getRepeating() && (recurrence != null)) {
	yearlyInterval0 = recurrence.getInterval();
}

int yearlyPos = ParamUtil.getInteger(request, "yearlyPos", 1);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByDay() != null) {
		yearlyPos = recurrence.getByDay()[0].getDayPosition();
	}
}

int yearlyDay1 = ParamUtil.getInteger(request, "yearlyDay1", Calendar.SUNDAY);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByDay() != null) {
		yearlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
	}
}

int yearlyMonth1 = ParamUtil.getInteger(request, "yearlyMonth1", Calendar.JANUARY);
if (event.getRepeating() && (recurrence != null)) {
	if (recurrence.getByMonth() != null) {
		yearlyMonth1 = recurrence.getByMonth()[0];
	}
}

int yearlyInterval1 = ParamUtil.getInteger(request, "yearlyInterval1", 1);
if (event.getRepeating() && (recurrence != null)) {
	yearlyInterval1 = recurrence.getInterval();
}
%>

<c:if test="<%= (recurrenceType == Recurrence.DAILY) %>">
	<liferay-ui:message key="repeat-daily" />:

	<c:if test="<%= (dailyType == 0) %>">
		<%= dailyInterval %><aui:spacer /><liferay-ui:message key="day-s" />
	</c:if>

	<c:if test="<%= (dailyType == 1) %>">
		<liferay-ui:message key="every-weekday" />
	</c:if>
</c:if>

<c:if test="<%= (recurrenceType == Recurrence.WEEKLY) %>">
	<liferay-ui:message key="repeat-weekly" />:

	<abbr class="rrule" title="FREQ=WEEKLY">
		<liferay-ui:message key="recur-every" /><aui:spacer /><%= dailyInterval %><aui:spacer /><liferay-ui:message key="weeks-on" />

		<%= weeklyPosSu ? (days[0] + ",") : "" %>
		<%= weeklyPosMo ? (days[1] + ",") : "" %>
		<%= weeklyPosTu ? (days[2] + ",") : "" %>
		<%= weeklyPosWe ? (days[3] + ",") : "" %>
		<%= weeklyPosTh ? (days[4] + ",") : "" %>
		<%= weeklyPosFr ? (days[5] + ",") : "" %>
		<%= weeklyPosSa ? days[6] : "" %>
	</abbr>
</c:if>

<c:if test="<%= (recurrenceType == Recurrence.MONTHLY) %>">
	<liferay-ui:message key="repeat-monthly" />:

	<c:if test="<%= (monthlyType == 0) %>">
		<liferay-ui:message key="day" /><aui:spacer /><%= monthlyDay0 %><aui:spacer /><liferay-ui:message key="of-every" /><aui:spacer /><%= monthlyInterval0 %><aui:spacer /><liferay-ui:message key="month-s" />
	</c:if>

	<c:if test="<%= (monthlyType == 1) %>">
		<liferay-ui:message key="the" />

		<%= (monthlyPos == 1) ? LanguageUtil.get(pageContext, "first") : "" %>
		<%= (monthlyPos == 2) ? LanguageUtil.get(pageContext, "second") : "" %>
		<%= (monthlyPos == 3) ? LanguageUtil.get(pageContext, "third") : "" %>
		<%= (monthlyPos == 4) ? LanguageUtil.get(pageContext, "fourth") : "" %>
		<%= (monthlyPos == -1) ? LanguageUtil.get(pageContext, "last") : "" %>

		<%= (monthlyDay1 == Calendar.SUNDAY) ? days[0] : "" %>
		<%= (monthlyDay1 == Calendar.MONDAY) ? days[1] : "" %>
		<%= (monthlyDay1 == Calendar.TUESDAY) ? days[2] : "" %>
		<%= (monthlyDay1 == Calendar.WEDNESDAY) ? days[3] : "" %>
		<%= (monthlyDay1 == Calendar.THURSDAY) ? days[4] : "" %>
		<%= (monthlyDay1 == Calendar.FRIDAY) ? days[5] : "" %>
		<%= (monthlyDay1 == Calendar.SATURDAY) ? days[6] : "" %>

		<liferay-ui:message key="of-every" /><aui:spacer /><%= monthlyInterval1 %><aui:spacer /><liferay-ui:message key="month-s" />
	</c:if>
</c:if>

<c:if test="<%= (recurrenceType == Recurrence.YEARLY) %>">
	<liferay-ui:message key="repeat-yearly" />:
		<abbr class="rrule" title="FREQ=YEARLY">
		<c:if test="<%= (yearlyType == 0) %>">
			<liferay-ui:message arguments="<%= new Object[] {months[yearlyMonth0], yearlyDay0, yearlyInterval0} %>" key="x-x-of-every-x-years" />
		</c:if>

		<c:if test="<%= (yearlyType == 1) %>">
			<liferay-ui:message key="the" />

			<%= (yearlyPos == 1) ? LanguageUtil.get(pageContext, "first") : "" %>
			<%= (yearlyPos == 2) ? LanguageUtil.get(pageContext, "second") : "" %>
			<%= (yearlyPos == 3) ? LanguageUtil.get(pageContext, "third") : "" %>
			<%= (yearlyPos == 4) ? LanguageUtil.get(pageContext, "fourth") : "" %>
			<%= (yearlyPos == -1) ? LanguageUtil.get(pageContext, "last") : "" %>

			<%= (yearlyDay1 == Calendar.SUNDAY) ? days[0] : "" %>
			<%= (yearlyDay1 == Calendar.MONDAY) ? days[1] : "" %>
			<%= (yearlyDay1 == Calendar.TUESDAY) ? days[2] : "" %>
			<%= (yearlyDay1 == Calendar.WEDNESDAY) ? days[3] : "" %>
			<%= (yearlyDay1 == Calendar.THURSDAY) ? days[4] : "" %>
			<%= (yearlyDay1 == Calendar.FRIDAY) ? days[5] : "" %>
			<%= (yearlyDay1 == Calendar.SATURDAY) ? days[6] : "" %>

			<liferay-ui:message key="of" /><aui:spacer /><%= months[yearlyMonth1] %><aui:spacer /><liferay-ui:message key="of-every" /><aui:spacer /><%= yearlyInterval1 %><aui:spacer /><liferay-ui:message key="year-s" />
		</c:if>
	</abbr>
</c:if>

<%!
private boolean _getWeeklyDayPos(HttpServletRequest req, int day, CalEvent event, Recurrence recurrence) {
	boolean weeklyPos = ParamUtil.getBoolean(req, "weeklyDayPos" + day);

	String weeklyPosParam = ParamUtil.getString(req, "weeklyDayPos" + day);

	if (Validator.isNull(weeklyPosParam) && (event != null)) {
		if (event.getRepeating() && (recurrence != null)) {
			DayAndPosition[] dayPositions = recurrence.getByDay();

			if (dayPositions != null) {
				for (int i = 0; i < dayPositions.length; i++) {
					if (dayPositions[i].getDayOfWeek() == day) {
						return true;
					}
				}
			}
		}
	}

	return weeklyPos;
}
%>