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

<%@ include file="/init.jsp" %>

<%
String languageId = LanguageUtil.getLanguageId(request);
%>

<div class="calendar-asset-full-content">

	<%
	String description = calendarBooking.getDescription(languageId);
	%>

	<c:if test="<%= Validator.isNotNull(description) %>">
		<div>
			<%= description %>
		</div>
	</c:if>

	<p>
		<liferay-ui:icon
			icon="user"
			markupView="lexicon"
			message="owner"
		/>

		<%
		Calendar calendar = calendarBooking.getCalendar();
		%>

		<strong><%= HtmlUtil.escape(calendar.getName(languageId)) %></strong>

		<%
		List<CalendarBooking> childCalendarBookings = calendarBooking.getChildCalendarBookings();
		%>

		<c:if test="<%= !childCalendarBookings.isEmpty() %>">
			<br />

			<liferay-ui:icon
				icon="globe"
				markupView="lexicon"
				message="resources"
			/>

			<liferay-ui:message key="resources" />:

			<%
			List<String> calendarResourcesNames = new ArrayList<String>();

			for (CalendarBooking childCalendarBooking : childCalendarBookings) {
				CalendarResource calendarResource = childCalendarBooking.getCalendarResource();

				calendarResourcesNames.add(calendarResource.getName(languageId));
			}
			%>

			<%= HtmlUtil.escape(StringUtil.merge(calendarResourcesNames, ", ")) %>
		</c:if>

		<c:if test="<%= calendarBooking.isRecurring() %>">
			<br /><br />

			<liferay-ui:icon
				icon="list"
				markupView="lexicon"
				message="recurring"
			/>

			<liferay-ui:message key="recurring" />
		</c:if>

		<br /><br />

		<liferay-ui:icon
			icon="calendar"
			markupView="lexicon"
			message="starts"
		/>

		<%
		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(calendarBooking.getStartTime(), user.getTimeZone());
		%>

		<c:choose>
			<c:when test="<%= calendarBooking.isAllDay() %>">
				<liferay-ui:message key="starts" />: <%= utcLongDateJFormat.format(startTimeJCalendar.getTime()) + ", " + utcTimeJFormat.format(startTimeJCalendar.getTime()) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="starts" />: <%= longDateJFormat.format(startTimeJCalendar.getTime()) + ", " + timeJFormat.format(startTimeJCalendar.getTime()) %>
			</c:otherwise>
		</c:choose>

		<br />

		<liferay-ui:icon
			icon="calendar"
			markupView="lexicon"
			message="ends"
		/>

		<%
		java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(calendarBooking.getEndTime(), user.getTimeZone());
		%>

		<c:choose>
			<c:when test="<%= calendarBooking.isAllDay() %>">
				<liferay-ui:message key="ends" />: <%= utcLongDateJFormat.format(endTimeJCalendar.getTime()) + ", " + utcTimeJFormat.format(endTimeJCalendar.getTime()) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="ends" />: <%= longDateJFormat.format(endTimeJCalendar.getTime()) + ", " + timeJFormat.format(endTimeJCalendar.getTime()) %>
			</c:otherwise>
		</c:choose>

		<%
		java.util.Calendar nowJCalendar = CalendarFactoryUtil.getCalendar(timeZone);

		CalendarBooking nextCalendarBooking = RecurrenceUtil.getCalendarBookingInstance(calendarBooking, RecurrenceUtil.getIndexOfInstance(calendarBooking.getRecurrence(), startTimeJCalendar.getTimeInMillis(), nowJCalendar.getTimeInMillis()));
		%>

		<c:if test="<%= nextCalendarBooking != null %>">
			<br /><br />

			<liferay-ui:icon
				icon="calendar"
				markupView="lexicon"
				message="next-event-starts"
			/>

			<%
			java.util.Calendar nextEventStartTimeJCalendar = JCalendarUtil.getJCalendar(nextCalendarBooking.getStartTime(), user.getTimeZone());
			%>

			<liferay-ui:message key="next-event-starts" />: <%= longDateJFormat.format(nextEventStartTimeJCalendar.getTime()) + ", " + timeJFormat.format(nextEventStartTimeJCalendar.getTime()) %>

			<br />

			<liferay-ui:icon
				icon="calendar"
				markupView="lexicon"
				message="next-event-ends"
			/>

			<%
			java.util.Calendar nextEventEndTimeJCalendar = JCalendarUtil.getJCalendar(nextCalendarBooking.getEndTime(), user.getTimeZone());
			%>

			<liferay-ui:message key="next-event-ends" />: <%= longDateJFormat.format(nextEventEndTimeJCalendar.getTime()) + ", " + timeJFormat.format(nextEventEndTimeJCalendar.getTime()) %>
		</c:if>

		<c:if test="<%= Validator.isNotNull(calendarBooking.getLocation()) %>">
			<br /><br />

			<liferay-ui:icon
				icon="geolocation"
				markupView="lexicon"
				message="location"
			/>

			<liferay-ui:message key="location" />: <a href="https://maps.google.com.br/maps?q=<%= HtmlUtil.escapeHREF(calendarBooking.getLocation()) %>" target="_blank"><%= HtmlUtil.escape(calendarBooking.getLocation()) %></a>
		</c:if>

		<liferay-expando:custom-attributes-available
			className="<%= CalendarBooking.class.getName() %>"
		>
			<liferay-expando:custom-attribute-list
				className="<%= CalendarBooking.class.getName() %>"
				classPK="<%= (calendarBooking != null) ? calendarBooking.getCalendarBookingId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-expando:custom-attributes-available>
	</p>
</div>

<br />