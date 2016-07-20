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
String activeView = ParamUtil.getString(request, "activeView", sessionClicksDefaultView);
long date = ParamUtil.getLong(request, "date", System.currentTimeMillis());

JSONArray groupCalendarsJSONArray = CalendarUtil.toCalendarsJSONArray(themeDisplay, groupCalendars);
JSONArray userCalendarsJSONArray = CalendarUtil.toCalendarsJSONArray(themeDisplay, userCalendars);
JSONArray otherCalendarsJSONArray = CalendarUtil.toCalendarsJSONArray(themeDisplay, otherCalendars);

boolean columnOptionsVisible = GetterUtil.getBoolean(SessionClicks.get(request, "com.liferay.calendar.web_columnOptionsVisible", "true"));
%>

<aui:container cssClass="calendar-portlet-column-parent">
	<aui:row>
		<c:if test="<%= !displaySchedulerOnly %>">
			<aui:col cssClass='<%= "calendar-portlet-column-options " + (columnOptionsVisible ? StringPool.BLANK : "hide") %>' id="columnOptions" span="<%= 3 %>">
				<div class="calendar-portlet-mini-calendar" id="<portlet:namespace />miniCalendarContainer"></div>

				<div id="<portlet:namespace />calendarListContainer">
					<div class="calendar-portlet-list">
						<c:if test="<%= themeDisplay.isSignedIn() && showUserEvents %>">
							<div class="calendar-portlet-list-header toggler-header-expanded">
								<span class="calendar-portlet-list-arrow"></span>

								<span class="calendar-portlet-list-text"><liferay-ui:message key="my-calendars" /></span>
							</div>

							<c:if test="<%= userCalendarResource != null %>">
								<span class="calendar-list-item-arrow calendar-resource-arrow" data-calendarResourceId="<%= userCalendarResource.getCalendarResourceId() %>" tabindex="0"><i class="icon-caret-down"></i></span>
							</c:if>

							<div class="calendar-portlet-calendar-list" id="<portlet:namespace />myCalendarList"></div>
						</c:if>
					</div>

					<div class="calendar-portlet-list">
						<c:if test="<%= showSiteCalendars %>">
							<div class="calendar-portlet-list-header toggler-header-expanded">
								<span class="calendar-portlet-list-arrow"></span>

								<span class="calendar-portlet-list-text"><liferay-ui:message arguments="<%= new String[] {groupCalendarResource.getName(locale)} %>" key="x-calendars" /></span>
							</div>

							<c:if test="<%= CalendarResourcePermission.contains(permissionChecker, groupCalendarResource, CalendarActionKeys.ADD_CALENDAR) %>">
								<span class="calendar-list-item-arrow calendar-resource-arrow" data-calendarResourceId="<%= groupCalendarResource.getCalendarResourceId() %>" tabindex="0"><i class="icon-caret-down"></i></span>
							</c:if>

							<div class="calendar-portlet-calendar-list" id="<portlet:namespace />siteCalendarList"></div>
						</c:if>
					</div>

					<div class="calendar-portlet-list">
						<c:if test="<%= themeDisplay.isSignedIn() %>">
							<div class="calendar-portlet-list-header toggler-header-expanded">
								<span class="calendar-portlet-list-arrow"></span>

								<span class="calendar-portlet-list-text"><liferay-ui:message key="other-calendars" /></span>
							</div>

							<div class="calendar-portlet-calendar-list" id="<portlet:namespace />otherCalendarList">
								<input class="calendar-portlet-add-calendars-input" id="<portlet:namespace />addOtherCalendar" placeholder="<liferay-ui:message key="add-other-calendars" />" type="text" />
							</div>
						</c:if>
					</div>
				</div>
			</aui:col>
		</c:if>

		<aui:col cssClass="calendar-portlet-column-grid" id="columnGrid" span="<%= (columnOptionsVisible && !displaySchedulerOnly) ? 9 : 12 %>">
			<c:if test="<%= !displaySchedulerOnly %>">
				<div class="calendar-portlet-column-toggler" id="<portlet:namespace />columnToggler">
					<i class="<%= columnOptionsVisible ? "icon-caret-left" : "icon-caret-right" %>" id="<portlet:namespace />columnTogglerIcon"></i>
				</div>
			</c:if>

			<liferay-util:include page="/scheduler.jsp" servletContext="<%= application %>">
				<liferay-util:param name="activeView" value="<%= activeView %>" />
				<liferay-util:param name="date" value="<%= String.valueOf(date) %>" />

				<portlet:renderURL var="editCalendarBookingURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_calendar_booking.jsp" />
					<portlet:param name="activeView" value="{activeView}" />
					<portlet:param name="allDay" value="{allDay}" />
					<portlet:param name="calendarBookingId" value="{calendarBookingId}" />
					<portlet:param name="calendarId" value="{calendarId}" />
					<portlet:param name="date" value="{date}" />
					<portlet:param name="endTimeDay" value="{endTimeDay}" />
					<portlet:param name="endTimeHour" value="{endTimeHour}" />
					<portlet:param name="endTimeMinute" value="{endTimeMinute}" />
					<portlet:param name="endTimeMonth" value="{endTimeMonth}" />
					<portlet:param name="endTimeYear" value="{endTimeYear}" />
					<portlet:param name="instanceIndex" value="{instanceIndex}" />
					<portlet:param name="startTimeDay" value="{startTimeDay}" />
					<portlet:param name="startTimeHour" value="{startTimeHour}" />
					<portlet:param name="startTimeMinute" value="{startTimeMinute}" />
					<portlet:param name="startTimeMonth" value="{startTimeMonth}" />
					<portlet:param name="startTimeYear" value="{startTimeYear}" />
					<portlet:param name="titleCurrentValue" value="{titleCurrentValue}" />
				</portlet:renderURL>

				<liferay-util:param name="editCalendarBookingURL" value="<%= editCalendarBookingURL %>" />

				<liferay-util:param name="hideAgendaView" value="<%= String.valueOf(!showAgendaView) %>" />
				<liferay-util:param name="hideDayView" value="<%= String.valueOf(!showDayView) %>" />
				<liferay-util:param name="hideWeekView" value="<%= String.valueOf(!showWeekView) %>" />
				<liferay-util:param name="hideMonthView" value="<%= String.valueOf(!showMonthView) %>" />
				<liferay-util:param name="readOnly" value="<%= Boolean.FALSE.toString() %>" />

				<liferay-security:permissionsURL
					modelResource="<%= CalendarBooking.class.getName() %>"
					modelResourceDescription="{modelResourceDescription}"
					resourceGroupId="{resourceGroupId}"
					resourcePrimKey="{resourcePrimKey}"
					var="permissionsCalendarBookingURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<liferay-util:param name="permissionsCalendarBookingURL" value="<%= permissionsCalendarBookingURL %>" />

				<liferay-util:param name="showAddEventBtn" value="<%= String.valueOf((defaultCalendar != null) && CalendarPermission.contains(permissionChecker, defaultCalendar, CalendarActionKeys.MANAGE_BOOKINGS)) %>" />
				<liferay-util:param name="showSchedulerHeader" value="<%= String.valueOf(displaySchedulerHeader) %>" />

				<portlet:renderURL var="viewCalendarBookingURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/view_calendar_booking.jsp" />
					<portlet:param name="calendarBookingId" value="{calendarBookingId}" />
					<portlet:param name="instanceIndex" value="{instanceIndex}" />
				</portlet:renderURL>

				<liferay-util:param name="viewCalendarBookingURL" value="<%= viewCalendarBookingURL %>" />
			</liferay-util:include>
		</aui:col>
	</aui:row>
</aui:container>

<div id="<portlet:namespace />message"></div>

<c:if test="<%= !displaySchedulerOnly %>">
	<%@ include file="/view_calendar_menus.jspf" %>
</c:if>

<aui:script use="liferay-calendar-list,liferay-scheduler,liferay-store,liferay-calendar-util">
	Liferay.CalendarUtil.USER_CLASS_NAME_ID = <%= PortalUtil.getClassNameId(User.class) %>;

	Liferay.CalendarUtil.DEFAULT_USER_CALENDAR_ID = <%= (defaultCalendar != null) ? defaultCalendar.getCalendarId() : 0 %>;

	var syncCalendarsMap = function() {
		var calendarLists = [];

		<c:if test="<%= themeDisplay.isSignedIn() || (groupCalendarResource != null) %>">
			calendarLists.push(window.<portlet:namespace />myCalendarList);
		</c:if>

		<c:if test="<%= themeDisplay.isSignedIn() %>">
			calendarLists.push(window.<portlet:namespace />otherCalendarList);
		</c:if>

		<c:if test="<%= showSiteCalendars %>">
			calendarLists.push(window.<portlet:namespace />siteCalendarList);
		</c:if>

		Liferay.CalendarUtil.syncCalendarsMap(calendarLists);
	};

	window.<portlet:namespace />syncCalendarsMap = syncCalendarsMap;

	window.<portlet:namespace />calendarLists = {};

	<c:if test="<%= themeDisplay.isSignedIn() || (groupCalendarResource != null) %>">
		window.<portlet:namespace />myCalendarList = new Liferay.CalendarList(
			{
				after: {
					calendarsChange: syncCalendarsMap,
					'scheduler-calendar:visibleChange': function(event) {
						syncCalendarsMap();

						<portlet:namespace />refreshVisibleCalendarRenderingRules();
					}
				},
				boundingBox: '#<portlet:namespace />myCalendarList',

				<%
				updateCalendarsJSONArray(request, userCalendarsJSONArray);
				%>

				calendars: <%= userCalendarsJSONArray %>,
				scheduler: <portlet:namespace />scheduler,
				showCalendarResourceName: false,
				simpleMenu: window.<portlet:namespace />calendarsMenu,
				visible: <%= !displaySchedulerOnly && themeDisplay.isSignedIn() %>
			}
		).render();

		<c:if test="<%= userCalendarResource != null %>">
			Liferay.CalendarUtil.USER_CALENDAR_RESOURCE_ID = <%= userCalendarResource.getCalendarResourceId() %>;

			window.<portlet:namespace />calendarLists['<%= userCalendarResource.getCalendarResourceId() %>'] = window.<portlet:namespace />myCalendarList;
		</c:if>
	</c:if>

	<c:if test="<%= themeDisplay.isSignedIn() %>">
		window.<portlet:namespace />otherCalendarList = new Liferay.CalendarList(
			{
				after: {
					calendarsChange: function(event) {
						syncCalendarsMap();

						<portlet:namespace />scheduler.load();

						var calendarIds = A.Array.invoke(event.newVal, 'get', 'calendarId');

						Liferay.Store('com.liferay.calendar.web_otherCalendars', calendarIds.join());
					},
					'scheduler-calendar:visibleChange': function(event) {
						syncCalendarsMap();

						<portlet:namespace />refreshVisibleCalendarRenderingRules();
					}
				},
				boundingBox: '#<portlet:namespace />otherCalendarList',

				<%
				updateCalendarsJSONArray(request, otherCalendarsJSONArray);
				%>

				calendars: <%= otherCalendarsJSONArray %>,
				scheduler: <portlet:namespace />scheduler,
				simpleMenu: window.<portlet:namespace />calendarsMenu
			}
		).render();
	</c:if>

	<c:if test="<%= showSiteCalendars %>">
		window.<portlet:namespace />siteCalendarList = new Liferay.CalendarList(
			{
				after: {
					calendarsChange: syncCalendarsMap,
					'scheduler-calendar:visibleChange': function(event) {
						syncCalendarsMap();

						<portlet:namespace />refreshVisibleCalendarRenderingRules();
					}
				},
				boundingBox: '#<portlet:namespace />siteCalendarList',

				<%
				updateCalendarsJSONArray(request, groupCalendarsJSONArray);
				%>

				calendars: <%= groupCalendarsJSONArray %>,
				scheduler: <portlet:namespace />scheduler,
				showCalendarResourceName: false,
				simpleMenu: window.<portlet:namespace />calendarsMenu,
				visible: <%= !displaySchedulerOnly %>
			}
		).render();

		Liferay.CalendarUtil.GROUP_CALENDAR_RESOURCE_ID = <%= groupCalendarResource.getCalendarResourceId() %>;

		window.<portlet:namespace />calendarLists['<%= groupCalendarResource.getCalendarResourceId() %>'] = window.<portlet:namespace />siteCalendarList;
	</c:if>

	syncCalendarsMap();

	A.each(
		Liferay.CalendarUtil.availableCalendars,
		function(item, index) {
			item.on(
				{
					'visibleChange': function(event) {
						var instance = this;

						var calendar = event.currentTarget;

						Liferay.Store('com.liferay.calendar.web_calendar' + calendar.get('calendarId') + 'Visible', event.newVal);
					}
				}
			);
		}
	);
</aui:script>

<aui:script use="aui-base,aui-datatype,liferay-calendar-session-listener">
	window.<portlet:namespace />refreshSchedulerEventTooltipTitle = function(schedulerEvent) {
		schedulerEvent.get('node').attr('title', A.Lang.String.unescapeHTML(schedulerEvent.get('content')));
	};

	<portlet:namespace />scheduler.after(
		['scheduler-events:load'],
		function(event) {
			event.currentTarget.eachEvent(<portlet:namespace />refreshSchedulerEventTooltipTitle);
		}
	);

	<portlet:namespace />scheduler.load();

	new Liferay.CalendarSessionListener(
		{
			calendars: Liferay.CalendarUtil.availableCalendars,
			scheduler: <portlet:namespace />scheduler
		}
	);
</aui:script>

<%!
protected void updateCalendarsJSONArray(HttpServletRequest request, JSONArray calendarsJSONArray) {
	for (int i = 0; i < calendarsJSONArray.length(); i++) {
		JSONObject jsonObject = calendarsJSONArray.getJSONObject(i);

		long calendarId = jsonObject.getLong("calendarId");

		jsonObject.put("color", GetterUtil.getString(SessionClicks.get(request, "com.liferay.calendar.web_calendar" + calendarId + "Color", jsonObject.getString("color"))));
		jsonObject.put("visible", GetterUtil.getBoolean(SessionClicks.get(request, "com.liferay.calendar.web_calendar" + calendarId + "Visible", "true")));
	}
}
%>