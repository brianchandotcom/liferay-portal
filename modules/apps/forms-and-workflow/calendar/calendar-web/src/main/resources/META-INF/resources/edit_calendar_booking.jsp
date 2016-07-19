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
String activeView = ParamUtil.getString(request, "activeView", defaultView);

TimeZone calendarBookingTimeZone = userTimeZone;

boolean allDay = BeanParamUtil.getBoolean(calendarBooking, request, "allDay");

if (allDay) {
	calendarBookingTimeZone = utcTimeZone;
}

java.util.Calendar defaultStartTimeJCalendar = CalendarFactoryUtil.getCalendar(calendarBookingTimeZone);

defaultStartTimeJCalendar.add(java.util.Calendar.HOUR, 1);

defaultStartTimeJCalendar.set(java.util.Calendar.MINUTE, 0);

long date = ParamUtil.getLong(request, "date", defaultStartTimeJCalendar.getTimeInMillis());

long calendarBookingId = BeanPropertiesUtil.getLong(calendarBooking, "calendarBookingId");

int instanceIndex = BeanParamUtil.getInteger(calendarBooking, request, "instanceIndex");

long calendarId = BeanParamUtil.getLong(calendarBooking, request, "calendarId", defaultCalendar.getCalendarId());

long startTime = BeanPropertiesUtil.getLong(calendarBooking, "startTime", defaultStartTimeJCalendar.getTimeInMillis());

java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(startTime, calendarBookingTimeZone);

int startTimeYear = ParamUtil.getInteger(request, "startTimeYear", startTimeJCalendar.get(java.util.Calendar.YEAR));
int startTimeMonth = ParamUtil.getInteger(request, "startTimeMonth", startTimeJCalendar.get(java.util.Calendar.MONTH));
int startTimeDay = ParamUtil.getInteger(request, "startTimeDay", startTimeJCalendar.get(java.util.Calendar.DAY_OF_MONTH));
int startTimeHour = ParamUtil.getInteger(request, "startTimeHour", startTimeJCalendar.get(java.util.Calendar.HOUR_OF_DAY));
int startTimeMinute = ParamUtil.getInteger(request, "startTimeMinute", startTimeJCalendar.get(java.util.Calendar.MINUTE));

int startTimeAmPm = ParamUtil.getInteger(request, "startTimeAmPm");

if (startTimeAmPm == java.util.Calendar.PM) {
	startTimeHour += 12;
}

startTimeJCalendar = CalendarFactoryUtil.getCalendar(startTimeYear, startTimeMonth, startTimeDay, startTimeHour, startTimeMinute, 0, 0, calendarBookingTimeZone);

startTimeJCalendar.setFirstDayOfWeek(weekStartsOn + 1);

startTime = startTimeJCalendar.getTimeInMillis();

java.util.Calendar defaultEndTimeJCalendar = (java.util.Calendar)startTimeJCalendar.clone();

defaultEndTimeJCalendar.add(java.util.Calendar.MINUTE, defaultDuration);

long endTime = BeanPropertiesUtil.getLong(calendarBooking, "endTime", defaultEndTimeJCalendar.getTimeInMillis());

java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(endTime, calendarBookingTimeZone);

int endTimeYear = ParamUtil.getInteger(request, "endTimeYear", endTimeJCalendar.get(java.util.Calendar.YEAR));
int endTimeMonth = ParamUtil.getInteger(request, "endTimeMonth", endTimeJCalendar.get(java.util.Calendar.MONTH));
int endTimeDay = ParamUtil.getInteger(request, "endTimeDay", endTimeJCalendar.get(java.util.Calendar.DAY_OF_MONTH));
int endTimeHour = ParamUtil.getInteger(request, "endTimeHour", endTimeJCalendar.get(java.util.Calendar.HOUR_OF_DAY));
int endTimeMinute = ParamUtil.getInteger(request, "endTimeMinute", endTimeJCalendar.get(java.util.Calendar.MINUTE));

int endTimeAmPm = ParamUtil.getInteger(request, "endTimeAmPm");

if (endTimeAmPm == java.util.Calendar.PM) {
	endTimeHour += 12;
}

endTimeJCalendar = CalendarFactoryUtil.getCalendar(endTimeYear, endTimeMonth, endTimeDay, endTimeHour, endTimeMinute, 0, 0, calendarBookingTimeZone);

endTimeJCalendar.setFirstDayOfWeek(weekStartsOn + 1);

endTime = endTimeJCalendar.getTimeInMillis();

long firstReminder = BeanParamUtil.getLong(calendarBooking, request, "firstReminder");
String firstReminderType = BeanParamUtil.getString(calendarBooking, request, "firstReminderType", CalendarServiceConfigurationValues.CALENDAR_NOTIFICATION_DEFAULT_TYPE.name());
long secondReminder = BeanParamUtil.getLong(calendarBooking, request, "secondReminder");
String secondReminderType = BeanParamUtil.getString(calendarBooking, request, "secondReminderType", CalendarServiceConfigurationValues.CALENDAR_NOTIFICATION_DEFAULT_TYPE.name());

JSONArray acceptedCalendarsJSONArray = JSONFactoryUtil.createJSONArray();
JSONArray declinedCalendarsJSONArray = JSONFactoryUtil.createJSONArray();
JSONArray maybeCalendarsJSONArray = JSONFactoryUtil.createJSONArray();
JSONArray pendingCalendarsJSONArray = JSONFactoryUtil.createJSONArray();

boolean approved = false;
boolean hasChildCalendarBookings = false;
boolean hasWorkflowDefinitionLink = false;
boolean invitable = true;
boolean masterBooking = true;
Recurrence recurrence = null;
boolean recurring = false;

Calendar calendar = CalendarServiceUtil.fetchCalendar(calendarId);

if (calendarBooking != null) {
	acceptedCalendarsJSONArray = CalendarUtil.toCalendarBookingsJSONArray(themeDisplay, CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId(), CalendarBookingWorkflowConstants.STATUS_APPROVED));
	declinedCalendarsJSONArray = CalendarUtil.toCalendarBookingsJSONArray(themeDisplay, CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId(), CalendarBookingWorkflowConstants.STATUS_DENIED));
	maybeCalendarsJSONArray = CalendarUtil.toCalendarBookingsJSONArray(themeDisplay, CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId(), CalendarBookingWorkflowConstants.STATUS_MAYBE));

	List<CalendarBooking> pendingCalendarBookings = new ArrayList<CalendarBooking>();

	pendingCalendarBookings.addAll(CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId(), CalendarBookingWorkflowConstants.STATUS_PENDING));
	pendingCalendarBookings.addAll(CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId(), CalendarBookingWorkflowConstants.STATUS_DRAFT));
	pendingCalendarBookings.addAll(CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId(), CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING));

	pendingCalendarsJSONArray = CalendarUtil.toCalendarBookingsJSONArray(themeDisplay, pendingCalendarBookings);

	if (calendarBooking.isMasterBooking()) {
		List<CalendarBooking> childCalendarBookings = CalendarBookingServiceUtil.getChildCalendarBookings(calendarBooking.getParentCalendarBookingId());

		if (childCalendarBookings.size() > 1) {
			hasChildCalendarBookings = true;
		}
	}
	else {
		invitable = false;
		masterBooking = false;
	}

	if (calendarBooking.isRecurring()) {
		recurring = true;
	}

	recurrence = RecurrenceUtil.inTimeZone(calendarBooking.getRecurrenceObj(), startTimeJCalendar, calendarBookingTimeZone);

	approved = calendarBooking.isApproved();

	calendar = calendarBooking.getCalendar();

	CalendarResource calendarResource = calendar.getCalendarResource();

	hasWorkflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), calendarResource.getGroupId(), CalendarBooking.class.getName());
}
else if (calendar != null) {
	JSONObject calendarJSONObject = CalendarUtil.toCalendarJSONObject(themeDisplay, calendar);

	CalendarResource calendarResource = calendar.getCalendarResource();

	if (calendar.getUserId() == themeDisplay.getUserId()) {
		acceptedCalendarsJSONArray.put(calendarJSONObject);
	}
	else {
		pendingCalendarsJSONArray.put(calendarJSONObject);
	}

	hasWorkflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), calendarResource.getGroupId(), CalendarBooking.class.getName());
}

long[] groupIds = null;

if (showUserEvents) {
	groupIds = ArrayUtil.append(user.getGroupIds(), new long[] {user.getGroupId(), scopeGroupId});
}
else {
	groupIds = ArrayUtil.append(user.getGroupIds(), new long[] {scopeGroupId});
}

List<Calendar> manageableCalendars = CalendarServiceUtil.search(themeDisplay.getCompanyId(), groupIds, null, null, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new CalendarNameComparator(true), CalendarActionKeys.MANAGE_BOOKINGS);

CalendarResource guestCalendarResource = CalendarResourceUtil.fetchGuestCalendarResource(themeDisplay.getCompanyId());

if (guestCalendarResource != null) {
	manageableCalendars.remove(guestCalendarResource.getDefaultCalendar());
}

long[] otherCalendarIds = StringUtil.split(SessionClicks.get(request, "com.liferay.calendar.web_otherCalendars", StringPool.BLANK), 0L);

for (long otherCalendarId : otherCalendarIds) {
	Calendar otherCalendar = CalendarServiceUtil.fetchCalendar(otherCalendarId);

	if (otherCalendar == null) {
		continue;
	}

	CalendarResource otherCalendarResource = otherCalendar.getCalendarResource();

	if (otherCalendarResource.isActive() && !manageableCalendars.contains(otherCalendar) && CalendarPermission.contains(themeDisplay.getPermissionChecker(), otherCalendar, CalendarActionKeys.MANAGE_BOOKINGS)) {
		manageableCalendars.add(otherCalendar);
	}
}

Iterator<Calendar> manageableCalendarsIterator = manageableCalendars.iterator();

while (manageableCalendarsIterator.hasNext()) {
	Calendar curCalendar = manageableCalendarsIterator.next();

	if (!CalendarServiceUtil.isManageableFromGroup(curCalendar.getCalendarId(), themeDisplay.getScopeGroupId())) {
		manageableCalendarsIterator.remove();
	}
}
%>

<liferay-portlet:actionURL name="updateFormCalendarBooking" var="updateFormCalendarBookingURL" />

<aui:form action="<%= updateFormCalendarBookingURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateCalendarBooking();" %>'>
	<aui:input name="mvcPath" type="hidden" value="/edit_calendar_booking.jsp" />

	<liferay-portlet:renderURL var="redirectURL">
		<liferay-portlet:param name="mvcPath" value="/edit_calendar_booking.jsp" />
		<liferay-portlet:param name="calendarBookingId" value="<%= String.valueOf(calendarBookingId) %>" />
	</liferay-portlet:renderURL>

	<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
	<aui:input name="calendarBookingId" type="hidden" value="<%= calendarBookingId %>" />
	<aui:input name="instanceIndex" type="hidden" value="<%= instanceIndex %>" />
	<aui:input name="childCalendarIds" type="hidden" />
	<aui:input name="allFollowing" type="hidden" />
	<aui:input name="updateCalendarBookingInstance" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

	<liferay-ui:error exception="<%= CalendarBookingDurationException.class %>" message="please-enter-a-start-date-that-comes-before-the-end-date" />
	<liferay-ui:error exception="<%= CalendarBookingRecurrenceException.class %>" message="the-last-repeating-date-should-come-after-the-event-start-date" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= calendarBooking %>" model="<%= CalendarBooking.class %>" />

	<aui:fieldset markupView="lexicon">
		<aui:input defaultLanguageId="<%= themeDisplay.getLanguageId() %>" name="title" />

		<div class="<%= allDay ? "allday-class-active" : "" %>" id="<portlet:namespace />startDateContainer">
			<aui:input ignoreRequestValue="<%= true %>" label="start-date" name="startTime" timeFormat="<%= timeFormat %>" value="<%= startTimeJCalendar %>" />
		</div>

		<div class="<%= allDay ? "allday-class-active" : "" %>" id="<portlet:namespace />endDateContainer">
			<aui:input ignoreRequestValue="<%= true %>" label="end-date" name="endTime" timeFormat="<%= timeFormat %>" value="<%= endTimeJCalendar %>" />
		</div>

		<aui:input checked="<%= allDay %>" name="allDay" />

		<aui:field-wrapper cssClass="calendar-portlet-recurrence-container" inlineField="<%= true %>" label="">
			<aui:input checked="<%= recurring %>" name="repeat" type="checkbox" />

			<a class="calendar-portlet-recurrence-summary" href="javascript:;" id="<portlet:namespace />summary"></a>
		</aui:field-wrapper>

		<aui:input defaultLanguageId="<%= themeDisplay.getLanguageId() %>" name="description" />
	</aui:fieldset>

	<aui:fieldset markupView="lexicon">
		<liferay-ui:panel-container extended="<%= true %>" id="calendarBookingDetailsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="calendarBookingDetailsPanel" markupView="lexicon" persistState="<%= true %>" title="details">
				<aui:select label="calendar" name="calendarId">

					<%
					for (Calendar curCalendar : manageableCalendars) {
						if ((calendarBooking != null) && (curCalendar.getCalendarId() != calendarId) && (CalendarBookingLocalServiceUtil.getCalendarBookingsCount(curCalendar.getCalendarId(), calendarBooking.getParentCalendarBookingId()) > 0)) {
							continue;
						}

						CalendarResource curCalendarResource = curCalendar.getCalendarResource();

						String calendarName = curCalendar.getName(locale);
						String calendarResourceName = curCalendarResource.getName(locale);

						if (!calendarName.equals(calendarResourceName)) {
							calendarName = calendarResourceName + StringPool.SPACE + StringPool.DASH + StringPool.SPACE + calendarName;
						}
					%>

						<aui:option selected="<%= curCalendar.getCalendarId() == calendarId %>" value="<%= curCalendar.getCalendarId() %>"><%= HtmlUtil.escape(calendarName) %></aui:option>

					<%
					}
					%>

				</aui:select>

				<aui:input name="location" />

				<liferay-ui:custom-attributes-available className="<%= CalendarBooking.class.getName() %>">
					<liferay-ui:custom-attribute-list
						className="<%= CalendarBooking.class.getName() %>"
						classPK="<%= (calendarBooking != null) ? calendarBooking.getCalendarBookingId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</liferay-ui:custom-attributes-available>

				<c:if test="<%= calendarBooking == null %>">
					<aui:field-wrapper label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= CalendarBooking.class.getName() %>"
						/>
					</aui:field-wrapper>
				</c:if>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" defaultState='<%= BrowserSnifferUtil.isMobile(request) ? "closed" : "open" %>' extended="<%= false %>" id="calendarBookingInvitationPanel" markupView="lexicon" persistState="<%= true %>" title="invitations">
				<c:if test="<%= invitable %>">
					<aui:input inputCssClass="calendar-portlet-invite-resources-input" label="" name="inviteResource" placeholder="add-people-groups-rooms" type="text" />

					<div class="separator"><!-- --></div>
				</c:if>

				<aui:row cssClass="calendar-booking-invitations">
					<aui:col width="<%= (calendarBooking != null) ? 25 : 50 %>">
						<label class="field-label">
							<liferay-ui:message key="pending" /> (<span id="<portlet:namespace />pendingCounter"><%= pendingCalendarsJSONArray.length() %></span>)
						</label>

						<div class="calendar-portlet-calendar-list" id="<portlet:namespace />calendarListPending"></div>
					</aui:col>

					<aui:col width="<%= (calendarBooking != null) ? 25 : 50 %>">
						<label class="field-label">
							<liferay-ui:message key="accepted" /> (<span id="<portlet:namespace />acceptedCounter"><%= acceptedCalendarsJSONArray.length() %></span>)
						</label>

						<div class="calendar-portlet-calendar-list" id="<portlet:namespace />calendarListAccepted"></div>
					</aui:col>

					<c:if test="<%= calendarBooking != null %>">
						<aui:col width="<%= 25 %>">
							<label class="field-label">
								<liferay-ui:message key="maybe" /> (<span id="<portlet:namespace />maybeCounter"><%= maybeCalendarsJSONArray.length() %></span>)
							</label>

							<div class="calendar-portlet-calendar-list" id="<portlet:namespace />calendarListMaybe"></div>
						</aui:col>

						<aui:col width="<%= 25 %>">
							<label class="field-label">
								<liferay-ui:message key="declined" /> (<span id="<portlet:namespace />declinedCounter"><%= declinedCalendarsJSONArray.length() %></span>)
							</label>

							<div class="calendar-portlet-calendar-list" id="<portlet:namespace />calendarListDeclined"></div>
						</aui:col>
					</c:if>

					<aui:col width="<%= 100 %>">
						<div class="calendar-portlet-list-header toggler-header-collapsed" id="<portlet:namespace />checkAvailability">
							<span class="calendar-portlet-list-arrow"></span>

							<span class="calendar-portlet-list-text"><liferay-ui:message key="resources-availability" /></span>
						</div>

						<div class="calendar-portlet-availability">
							<div class="toggler-content-collapsed" id="<portlet:namespace />schedulerContainer">
								<div id="<portlet:namespace />message"></div>

								<liferay-util:include page="/scheduler.jsp" servletContext="<%= application %>">
									<liferay-util:param name="activeView" value="<%= activeView %>" />
									<liferay-util:param name="date" value="<%= String.valueOf(startTime) %>" />
									<liferay-util:param name="filterCalendarBookings" value='<%= "window." + renderResponse.getNamespace() + "filterCalendarBookings" %>' />
									<liferay-util:param name="hideAgendaView" value="<%= Boolean.TRUE.toString() %>" />
									<liferay-util:param name="hideMonthView" value="<%= Boolean.TRUE.toString() %>" />
									<liferay-util:param name="preventPersistence" value="<%= Boolean.TRUE.toString() %>" />
									<liferay-util:param name="readOnly" value="<%= Boolean.TRUE.toString() %>" />
								</liferay-util:include>
							</div>
						</div>
					</aui:col>
				</aui:row>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="calendarBookingReminderPanel" markupView="lexicon" persistState="<%= true %>" title="reminders">
				<div class="calendar-booking-reminders" id="<portlet:namespace />reminders"></div>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="calendarBookingCategorizationPanel" markupView="lexicon" persistState="<%= true %>" title="categorization">
				<aui:input classPK="<%= calendarBookingId %>" name="categories" type="assetCategories" />

				<aui:input classPK="<%= calendarBookingId %>" name="tags" type="assetTags" />
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="calendarBookingAssetLinksPanel" markupView="lexicon" persistState="<%= true %>" title="related-assets">
				<liferay-ui:input-asset-links
					className="<%= CalendarBooking.class.getName() %>"
					classPK="<%= calendarBookingId %>"
				/>
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<aui:button-row>
			<div class="alert alert-info <%= (hasWorkflowDefinitionLink && approved) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />approvalProcessAlert">
				<liferay-ui:message arguments="<%= ResourceActionsUtil.getModelResource(locale, CalendarBooking.class.getName()) %>" key="this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again" translateArguments="<%= false %>" />
			</div>

			<%
			String publishButtonLabel = "publish";

			if (hasWorkflowDefinitionLink) {
				publishButtonLabel = "submit-for-publication";
			}
			%>

			<aui:button name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<aui:button name="saveButton" primary="<%= false %>" type="submit" value="save-as-draft" />

			<c:if test="<%= calendarBooking != null %>">
				<liferay-security:permissionsURL
					modelResource="<%= CalendarBooking.class.getName() %>"
					modelResourceDescription="<%= calendarBooking.getTitle(locale) %>"
					redirect="<%= redirectURL %>"
					resourceGroupId="<%= calendarBooking.getGroupId() %>"
					resourcePrimKey="<%= String.valueOf(calendarBooking.getCalendarBookingId()) %>"
					var="permissionsCalendarBookingURL"
				/>

				<aui:button href="<%= permissionsCalendarBookingURL %>" value="permissions" />
			</c:if>
		</aui:button-row>
	</aui:fieldset>

	<%@ include file="/calendar_booking_recurrence_container.jspf" %>
</aui:form>

<aui:script>
	function <portlet:namespace />filterCalendarBookings(calendarBooking) {
		return <%= calendarBookingId %> !== calendarBooking.calendarBookingId;
	}

	function <portlet:namespace />resolver(data) {
		var A = AUI();

		var answers = data.answers;

		if (!answers.cancel) {
			A.one('#<portlet:namespace />allFollowing').val(!!answers.allFollowing);
			A.one('#<portlet:namespace />updateCalendarBookingInstance').val(!!answers.updateInstance);

			submitForm(document.<portlet:namespace />fm);
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />updateCalendarBooking',
		function() {
			var A = AUI();

			<c:if test="<%= invitable %>">
				var calendarId = A.one('#<portlet:namespace />calendarId').val();
				var childCalendarIds = A.Object.keys(Liferay.CalendarUtil.availableCalendars);

				A.Array.remove(childCalendarIds, A.Array.indexOf(childCalendarIds, calendarId));

				A.one('#<portlet:namespace />childCalendarIds').val(childCalendarIds.join(','));
			</c:if>

			Liferay.CalendarMessageUtil.promptSchedulerEventUpdate(
				{
					calendarName: '<%= HtmlUtil.escapeJS(calendar.getName(locale)) %>',
					hasChild: <%= hasChildCalendarBookings %>,
					masterBooking: <%= masterBooking %>,
					recurring: <%= recurring %>,
					resolver: <portlet:namespace />resolver
				}
			);
		},
		['liferay-calendar-message-util', 'json']
	);

	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />title);

	<%
	String titleCurrentValue = ParamUtil.getString(request, "titleCurrentValue");
	%>

	<c:if test="<%= Validator.isNotNull(titleCurrentValue) && ((calendarBooking == null) || !Objects.equals(titleCurrentValue, calendarBooking.getTitle(locale))) %>">
		document.<portlet:namespace />fm.<portlet:namespace />title.value = '<%= HtmlUtil.escapeJS(titleCurrentValue) %>';
		document.<portlet:namespace />fm.<portlet:namespace />title_<%= themeDisplay.getLanguageId() %>.value = '<%= HtmlUtil.escapeJS(titleCurrentValue) %>';
	</c:if>
</aui:script>

<aui:script use="json,liferay-calendar-interval-selector,liferay-calendar-interval-selector-scheduler-event-link,liferay-calendar-list,liferay-calendar-recurrence-util,liferay-calendar-reminders,liferay-calendar-simple-menu,liferay-calendar-util">
	var defaultCalendarId = <%= calendarId %>;

	var scheduler = window.<portlet:namespace />scheduler;

	A.one('#<portlet:namespace />saveButton').on(
		'click',
		function() {
			A.one('#<portlet:namespace />workflowAction').val('<%= WorkflowConstants.ACTION_SAVE_DRAFT %>');
		}
	);

	A.one('#<portlet:namespace />publishButton').on(
		'click',
		function() {
			A.one('#<portlet:namespace />workflowAction').val('<%= WorkflowConstants.ACTION_PUBLISH %>');
		}
	);

	var syncCalendarsMap = function() {
		Liferay.CalendarUtil.syncCalendarsMap(
			[
				window.<portlet:namespace />calendarListAccepted,

				<c:if test="<%= calendarBooking != null %>">
					window.<portlet:namespace />calendarListDeclined,
					window.<portlet:namespace />calendarListMaybe,
				</c:if>

				window.<portlet:namespace />calendarListPending
			]
		);

		A.each(
			Liferay.CalendarUtil.availableCalendars,
			function(item, index) {
				item.set('disabled', true);
			}
		);
	};

	var calendarsMenu = Liferay.CalendarUtil.getCalendarsMenu(
		{
			content: '#<portlet:namespace />schedulerContainer',
			defaultCalendarId: defaultCalendarId,
			header: '#<portlet:namespace />checkAvailability',
			invitable: <%= invitable %>,
			scheduler: scheduler
		}
	);

	window.<portlet:namespace />calendarListPending = new Liferay.CalendarList(
		{
			after: {
				calendarsChange: function(event) {
					var instance = this;

					A.one('#<portlet:namespace />pendingCounter').html(event.newVal.length);

					syncCalendarsMap();

					scheduler.load();
				},
				'scheduler-calendar:visibleChange': syncCalendarsMap
			},
			boundingBox: '#<portlet:namespace />calendarListPending',
			calendars: <%= pendingCalendarsJSONArray %>,
			scheduler: <portlet:namespace />scheduler,
			simpleMenu: calendarsMenu,
			strings: {
				emptyMessage: '<liferay-ui:message key="no-pending-invites" />'
			}
		}
	).render();

	window.<portlet:namespace />calendarListAccepted = new Liferay.CalendarList(
		{
			after: {
				calendarsChange: function(event) {
					var instance = this;

					A.one('#<portlet:namespace />acceptedCounter').html(event.newVal.length);

					syncCalendarsMap();

					scheduler.load();
				},
				'scheduler-calendar:visibleChange': syncCalendarsMap
			},
			boundingBox: '#<portlet:namespace />calendarListAccepted',
			calendars: <%= acceptedCalendarsJSONArray %>,
			scheduler: <portlet:namespace />scheduler,
			simpleMenu: calendarsMenu,
			strings: {
				emptyMessage: '<liferay-ui:message key="no-accepted-invites" />'
			}
		}
	).render();

	<c:if test="<%= calendarBooking != null %>">
		window.<portlet:namespace />calendarListDeclined = new Liferay.CalendarList(
			{
				after: {
					calendarsChange: function(event) {
						var instance = this;

						A.one('#<portlet:namespace />declinedCounter').html(event.newVal.length);

						syncCalendarsMap();

						scheduler.load();
					},
					'scheduler-calendar:visibleChange': syncCalendarsMap
				},
				boundingBox: '#<portlet:namespace />calendarListDeclined',
				calendars: <%= declinedCalendarsJSONArray %>,
				scheduler: <portlet:namespace />scheduler,
				simpleMenu: calendarsMenu,
				strings: {
					emptyMessage: '<liferay-ui:message key="no-declined-invites" />'
				}
			}
		).render();

		window.<portlet:namespace />calendarListMaybe = new Liferay.CalendarList(
			{
				after: {
					calendarsChange: function(event) {
						var instance = this;

						A.one('#<portlet:namespace />maybeCounter').html(event.newVal.length);

						syncCalendarsMap();

						scheduler.load();
					},
					'scheduler-calendar:visibleChange': syncCalendarsMap
				},
				boundingBox: '#<portlet:namespace />calendarListMaybe',
				calendars: <%= maybeCalendarsJSONArray %>,
				scheduler: <portlet:namespace />scheduler,
				simpleMenu: calendarsMenu,
				strings: {
					emptyMessage: '<liferay-ui:message key="no-outstanding-invites" />'
				}
			}
		).render();
	</c:if>

	syncCalendarsMap();

	var intervalSelector = new Liferay.IntervalSelector(
		{
			endDatePicker: Liferay.component('<portlet:namespace />endTimeDatePicker'),
			endTimePicker: Liferay.component('<portlet:namespace />endTimeTimeTimePicker'),
			startDatePicker: Liferay.component('<portlet:namespace />startTimeDatePicker'),
			startTimePicker: Liferay.component('<portlet:namespace />startTimeTimeTimePicker')
		}
	);

	var placeholderSchedulerEvent = new Liferay.SchedulerEvent(
		{
			borderColor: '#000',
			borderStyle: 'dashed',
			borderWidth: '2px',
			color: '#F8F8F8',
			content: '',
			editingEvent: true,
			endDate: new Date(<%= endTimeYear %>, <%= endTimeMonth %>, <%= endTimeDay %>, <%= endTimeHour %>, <%= endTimeMinute %>),
			preventDateChange: true,
			scheduler: scheduler,
			startDate: new Date(<%= startTimeYear %>, <%= startTimeMonth %>, <%= startTimeDay %>, <%= startTimeHour %>, <%= startTimeMinute %>)
		}
	);

	var intervalSelectorSchedulerEventLink = new Liferay.IntervalSelectorSchedulerEventLink(
		{
			intervalSelector: intervalSelector,
			schedulerEvent: placeholderSchedulerEvent
		}
	);

	scheduler.after(
		'*:load',
		function(event) {
			scheduler.addEvents(placeholderSchedulerEvent);

			scheduler.syncEventsUI();
		}
	);

	<c:if test="<%= invitable %>">
		var manageableCalendars = {};

		<%= CalendarUtil.toCalendarsJSONArray(themeDisplay, manageableCalendars) %>.forEach(
			function(item, index) {
				manageableCalendars[item.calendarId] = item;
			}
		);

		A.one('#<portlet:namespace />calendarId').on(
			'valueChange',
			function(event) {
				var calendarId = parseInt(event.target.val(), 10);

				var calendar = manageableCalendars[calendarId];

				[
					<portlet:namespace />calendarListAccepted,

					<c:if test="<%= calendarBooking != null %>">
						<portlet:namespace />calendarListDeclined, <portlet:namespace />calendarListMaybe,
					</c:if>

					<portlet:namespace />calendarListPending
				].forEach(
					function(calendarList) {
						calendarList.remove(calendarList.getCalendar(calendarId));
						calendarList.remove(calendarList.getCalendar(defaultCalendarId));
					}
				);

				<portlet:namespace />calendarListPending.add(calendar);

				defaultCalendarId = calendarId;

				if (calendar.hasWorkflowDefinitionLink) {
					A.one('#<portlet:namespace />approvalProcessAlert').toggleClass('hide', <%= !approved %>);
					A.one('#<portlet:namespace />publishButton').setContent('<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "submit-for-publication")) %>');
				}
				else {
					A.one('#<portlet:namespace />approvalProcessAlert').toggleClass('hide', true);
					A.one('#<portlet:namespace />publishButton').setContent('<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "publish")) %>');
				}
			}
		);

		var inviteResourcesInput = A.one('#<portlet:namespace />inviteResource');

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="calendarResources" var="calendarResourcesURL"></liferay-portlet:resourceURL>

		Liferay.CalendarUtil.createCalendarsAutoComplete(
			'<%= calendarResourcesURL %>',
			inviteResourcesInput,
			function(event) {
				var calendar = event.result.raw;

				calendar.disabled = true;

				<portlet:namespace />calendarListPending.add(calendar);

				inviteResourcesInput.val('');
			}
		);
	</c:if>

	window.<portlet:namespace />reminders = new Liferay.Reminders(
		{
			portletNamespace: '<portlet:namespace />',
			render: '#<portlet:namespace />reminders',
			values: [
				{
					interval: <%= firstReminder %>,
					type: '<%= HtmlUtil.escapeJS(firstReminderType) %>'
				},
				{
					interval: <%= secondReminder %>,
					type: '<%= HtmlUtil.escapeJS(secondReminderType) %>'
				}
			]
		}
	);

	var allDayCheckbox = A.one('#<portlet:namespace />allDay');

	allDayCheckbox.after(
		'click',
		function() {
			var endDateContainer = A.one('#<portlet:namespace />endDateContainer');
			var startDateContainer = A.one('#<portlet:namespace />startDateContainer');

			var checked = allDayCheckbox.get('checked');

			if (checked) {
				placeholderSchedulerEvent.set('allDay', true);
			}
			else {
				placeholderSchedulerEvent.set('allDay', false);

				endDateContainer.show();
			}

			endDateContainer.toggleClass('allday-class-active', checked);
			startDateContainer.toggleClass('allday-class-active', checked);

			scheduler.syncEventsUI();
		}
	);

	scheduler.load();
</aui:script>