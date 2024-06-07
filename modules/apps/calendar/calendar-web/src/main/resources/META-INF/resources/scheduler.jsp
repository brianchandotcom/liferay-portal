<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String activeView = ParamUtil.getString(request, "activeView", sessionClicksDefaultView);
long date = ParamUtil.getLong(request, "date", System.currentTimeMillis());
String editCalendarBookingURL = ParamUtil.getString(request, "editCalendarBookingURL");
String filterCalendarBookings = ParamUtil.getString(request, "filterCalendarBookings", null);
boolean hideAgendaView = ParamUtil.getBoolean(request, "hideAgendaView");
boolean hideDayView = ParamUtil.getBoolean(request, "hideDayView");
boolean hideMonthView = ParamUtil.getBoolean(request, "hideMonthView");
boolean hideWeekView = ParamUtil.getBoolean(request, "hideWeekView");
String permissionsCalendarBookingURL = ParamUtil.getString(request, "permissionsCalendarBookingURL");
boolean readOnly = ParamUtil.getBoolean(request, "readOnly");
boolean showSchedulerHeader = ParamUtil.getBoolean(request, "showSchedulerHeader", true);
String viewCalendarBookingURL = ParamUtil.getString(request, "viewCalendarBookingURL");
%>

<div class="calendar-portlet-wrapper" id="<portlet:namespace />scheduler"></div>

<%@ include file="/event_recorder.jspf" %>

<aui:script use="aui-toggler,json,liferay-calendar-list,liferay-scheduler">
	var calendarContainer = Liferay.component(
		'<%= portletDisplay.getNamespace() %>calendarContainer'
	);

	var remoteServices = Liferay.component(
		'<%= portletDisplay.getNamespace() %>remoteServices'
	);

	var showMoreStrings = {
		close: '<liferay-ui:message key="close" />',
		showMore: '<liferay-ui:message key="show-x-more" />',
	};

	<c:if test="<%= !hideDayView %>">
		window.<%= portletDisplay.getNamespace() %>dayView =
			new Liferay.SchedulerDayView({
				headerViewConfig: {
					eventsOverlayConstrain:
						'#p_p_id<%= portletDisplay.getNamespace() %>',
					strings: showMoreStrings,
				},
				height: 700,
				isoTime: <%= useIsoTimeFormat %>,
				readOnly: <%= readOnly %>,
				strings: {
					allDay: '<liferay-ui:message key="all-day" />',
				},
			});
	</c:if>

	<c:if test="<%= !hideWeekView %>">
		window.<%= portletDisplay.getNamespace() %>weekView =
			new Liferay.SchedulerWeekView({
				headerViewConfig: {
					displayDaysInterval: A.DataType.DateMath.WEEK_LENGTH,
					eventsOverlayConstrain:
						'#p_p_id<%= portletDisplay.getNamespace() %>',
					strings: showMoreStrings,
				},
				height: 700,
				isoTime: <%= useIsoTimeFormat %>,
				readOnly: <%= readOnly %>,
				strings: {
					allDay: '<liferay-ui:message key="all-day" />',
				},
			});
	</c:if>

	<c:if test="<%= !hideMonthView %>">
		window.<%= portletDisplay.getNamespace() %>monthView =
			new Liferay.SchedulerMonthView({
				eventsOverlayConstrain: '#p_p_id<%= portletDisplay.getNamespace() %>',
				height: 'auto',
				isoTime: <%= useIsoTimeFormat %>,
				readOnly: <%= readOnly %>,
				strings: showMoreStrings,
			});
	</c:if>

	<c:if test="<%= !hideAgendaView %>">
		window.<%= portletDisplay.getNamespace() %>agendaView =
			new Liferay.SchedulerAgendaView({
				daysCount: <%= maxDaysDisplayed + 1 %>,
				height: 700,
				isoTime: <%= useIsoTimeFormat %>,
				readOnly: <%= readOnly %>,
				strings: {
					noEvents: '<liferay-ui:message key="no-events" />',
				},
			});
	</c:if>

	<c:if test="<%= !readOnly && (defaultCalendar != null) %>">
		var width = Math.min(window.innerWidth, 550);

		window.<%= portletDisplay.getNamespace() %>eventRecorder =
			new Liferay.SchedulerEventRecorder({
				bodyTemplate: new A.Template(
					A.one(
						'#<%= portletDisplay.getNamespace() %>eventRecorderBodyTpl'
					).html()
				),
				calendarContainer: calendarContainer,
				calendarId: <%= defaultCalendar.getCalendarId() %>,
				color: '<%= ColorUtil.toHexString(defaultCalendar.getColor()) %>',
				duration: <%= defaultDuration %>,
				editCalendarBookingURL:
					'<%= HtmlUtil.escapeJS(editCalendarBookingURL) %>',
				headerTemplate: new A.Template(
					A.one(
						'#<%= portletDisplay.getNamespace() %>eventRecorderHeaderTpl'
					).html()
				),
				permissionsCalendarBookingURL:
					'<%= HtmlUtil.escapeJS(permissionsCalendarBookingURL) %>',
				popover: {
					width: width,
				},
				portletNamespace: '<%= portletDisplay.getNamespace() %>',
				remoteServices: remoteServices,
				showHeader: <%= showSchedulerHeader %>,
				strings: {
					'description-hint': '<liferay-ui:message key="description-hint" />',
				},
				viewCalendarBookingURL:
					'<%= HtmlUtil.escapeJS(viewCalendarBookingURL) %>',
			});
	</c:if>

	var views = [];

	<c:if test="<%= !hideDayView %>">
		views.push(window.<%= portletDisplay.getNamespace() %>dayView);
	</c:if>

	<c:if test="<%= !hideWeekView %>">
		views.push(window.<%= portletDisplay.getNamespace() %>weekView);
	</c:if>

	<c:if test="<%= !hideMonthView %>">
		views.push(window.<%= portletDisplay.getNamespace() %>monthView);
	</c:if>

	<c:if test="<%= !hideAgendaView %>">
		views.push(window.<%= portletDisplay.getNamespace() %>agendaView);
	</c:if>

	window.<%= portletDisplay.getNamespace() %>scheduler = new Liferay.Scheduler({
		activeView:
			window[
				'<%= portletDisplay.getNamespace() %><%= HtmlUtil.escapeJS(activeView) %>View'
			],
		ariaLabels: {
			agenda: '<liferay-ui:message key="agenda-view" />',
			calendar: '<liferay-ui:message key="calendar-views" />',
			day: '<liferay-ui:message key="day-view" />',
			month: '<liferay-ui:message key="month-view" />',
			next: '<liferay-ui:message key="next" />',
			previous: '<liferay-ui:message key="previous" />',
			today: '<liferay-ui:message key="today-view" />',
			week: '<liferay-ui:message key="week-view" />',
			year: '<liferay-ui:message key="year-view" />',
		},
		boundingBox: '#<%= portletDisplay.getNamespace() %>scheduler',
		calendarContainer: calendarContainer,

		<%
		java.util.Calendar nowJCalendar = CalendarFactoryUtil.getCalendar(userTimeZone);
		%>

		currentTime: new Date(
			<%= nowJCalendar.get(java.util.Calendar.YEAR) %>,
			<%= nowJCalendar.get(java.util.Calendar.MONTH) %>,
			<%= nowJCalendar.get(java.util.Calendar.DAY_OF_MONTH) %>,
			<%= nowJCalendar.get(java.util.Calendar.HOUR_OF_DAY) %>,
			<%= nowJCalendar.get(java.util.Calendar.MINUTE) %>
		),
		currentTimeFn: A.bind(remoteServices.getCurrentTime, remoteServices),

		<%
		java.util.Calendar dateJCalendar = CalendarFactoryUtil.getCalendar(userTimeZone);

		dateJCalendar.setTimeInMillis(date);
		%>

		date: new Date(
			<%= dateJCalendar.get(java.util.Calendar.YEAR) %>,
			<%= dateJCalendar.get(java.util.Calendar.MONTH) %>,
			<%= dateJCalendar.get(java.util.Calendar.DAY_OF_MONTH) %>
		),

		<c:if test="<%= !themeDisplay.isSignedIn() || ((defaultCalendar != null) && !CalendarPermission.contains(themeDisplay.getPermissionChecker(), defaultCalendar, CalendarActionKeys.MANAGE_BOOKINGS)) %>">
			disabled: true,
		</c:if>

		eventRecorder: window.<%= portletDisplay.getNamespace() %>eventRecorder,
		eventsPerPage: <%= eventsPerPage %>,
		filterCalendarBookings:
			window['<%= HtmlUtil.escapeJS(filterCalendarBookings) %>'],
		firstDayOfWeek: <%= weekStartsOn %>,
		items: A.Object.values(calendarContainer.get('availableCalendars')),
		maxDaysDisplayed: <%= maxDaysDisplayed %>,
		portletNamespace: '<%= portletDisplay.getNamespace() %>',
		preventPersistence:
			<%= ParamUtil.getBoolean(request, "preventPersistence") %>,
		remoteServices: remoteServices,
		render: true,
		showAddEventBtn: <%= ParamUtil.getBoolean(request, "showAddEventBtn") %>,
		showHeader: <%= showSchedulerHeader %>,
		strings: {
			agenda: '<liferay-ui:message key="agenda" />',
			currentDate: '<liferay-ui:message key="current-date" />',
			day: '<liferay-ui:message key="day" />',
			month: '<liferay-ui:message key="month" />',
			today: '<liferay-ui:message key="today" />',
			week: '<liferay-ui:message key="week" />',
			year: '<liferay-ui:message key="year" />',
		},

		<%
		java.util.Calendar todayJCalendar = CalendarFactoryUtil.getCalendar(userTimeZone);
		%>

		todayDate: new Date(
			<%= todayJCalendar.get(java.util.Calendar.YEAR) %>,
			<%= todayJCalendar.get(java.util.Calendar.MONTH) %>,
			<%= todayJCalendar.get(java.util.Calendar.DAY_OF_MONTH) %>
		),
		views: views,
	});

	var destroySchedulers = function (event) {
		if (event.portletId === '<%= portletDisplay.getId() %>') {
			window.<%= portletDisplay.getNamespace() %>scheduler.destroy();

			Liferay.detach('destroyPortlet', destroySchedulers);
		}
	};

	Liferay.on('destroyPortlet', destroySchedulers);
</aui:script>