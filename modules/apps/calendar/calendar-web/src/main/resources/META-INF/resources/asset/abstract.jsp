<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String languageId = LanguageUtil.getLanguageId(request);
%>

<div class="calendar-asset-abstract">
	<p>
		<liferay-ui:icon
			icon="user"
			markupView="lexicon"
			message=""
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

		<br />
		<br />

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
				<liferay-ui:message key="starts" />: <%= utcLongDateJFormat.format(startTimeJCalendar.getTime()) %>, <%= utcTimeJFormat.format(startTimeJCalendar.getTime()) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="starts" />: <%= longDateJFormat.format(startTimeJCalendar.getTime()) %>, <%= timeJFormat.format(startTimeJCalendar.getTime()) %>
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
				<liferay-ui:message key="ends" />: <%= utcLongDateJFormat.format(endTimeJCalendar.getTime()) %>, <%= utcTimeJFormat.format(endTimeJCalendar.getTime()) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="ends" />: <%= longDateJFormat.format(endTimeJCalendar.getTime()) %>, <%= timeJFormat.format(endTimeJCalendar.getTime()) %>
			</c:otherwise>
		</c:choose>
	</p>
</div>

<br />