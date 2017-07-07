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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

long powwowMeetingId = ParamUtil.getLong(request, "powwowMeetingId");

PowwowMeeting powwowMeeting = PowwowMeetingLocalServiceUtil.fetchPowwowMeeting(powwowMeetingId);

long calendarBookingId = 0;
boolean autoStartVideo = false;
List<PowwowParticipant> powwowParticipants = new ArrayList<PowwowParticipant>();
int status = PowwowMeetingConstants.STATUS_ANY;

if (powwowMeeting != null) {
	calendarBookingId = powwowMeeting.getCalendarBookingId();
	autoStartVideo = PowwowServiceProviderUtil.getOptionAutoStartVideo(powwowMeetingId);
	powwowParticipants = PowwowParticipantLocalServiceUtil.getPowwowParticipants(powwowMeetingId);
	status = powwowMeeting.getStatus();
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='<%= (powwowMeeting != null) ? "edit-meeting" : "new-meeting" %>'
/>

<liferay-portlet:actionURL name="updatePowwowMeeting" var="editPowwowMeetingURL" />

<aui:form action="<%= editPowwowMeetingURL %>" cssClass="edit-meeting" id="fm" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="powwowMeetingId" type="hidden" value="<%= String.valueOf(powwowMeetingId) %>" />

	<aui:model-context bean="<%= powwowMeeting %>" model="<%= PowwowMeeting.class %>" />

	<aui:input autoFocus="<%= true %>" cssClass="meeting-name" name="name" />

	<label class="control-label" for="<portlet:namespace />meetingEventDate"><liferay-ui:message key="meeting-date" /></label>

	<div class="control-group meeting-event-date" id="<portlet:namespace />meetingEventDate">

		<%
		CalendarBooking calendarBooking = CalendarBookingServiceUtil.fetchCalendarBooking(calendarBookingId);

		Calendar startCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);
		Calendar endCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

		if ((calendarBooking != null) && (status != PowwowMeetingConstants.STATUS_COMPLETED)) {
			Date startDate = new Date(calendarBooking.getStartTime());

			startCalendar.setTime(startDate);

			Date endDate = new Date(calendarBooking.getEndTime());

			endCalendar.setTime(endDate);
		}
		else {
			Date currentDate = new Date(System.currentTimeMillis());

			startCalendar.setTime(currentDate);

			endCalendar.setTime(currentDate);

			if (startCalendar.get(Calendar.MINUTE) <= 30) {
				startCalendar.set(Calendar.MINUTE, 30);

				endCalendar.add(Calendar.HOUR, 1);
				endCalendar.set(Calendar.MINUTE, 30);
			}
			else {
				startCalendar.add(Calendar.HOUR, 1);
				startCalendar.set(Calendar.MINUTE, 0);

				endCalendar.add(Calendar.HOUR, 2);
				endCalendar.set(Calendar.MINUTE, 0);
			}
		}
		%>

		<span class="start-date-container" id="<portlet:namespace />startDateContainer">
			<liferay-ui:input-date
				dayParam="startTimeDay"
				dayValue="<%= startCalendar.get(Calendar.DATE) %>"
				disabled="<%= false %>"
				firstDayOfWeek="<%= startCalendar.getFirstDayOfWeek() - 1 %>"
				monthParam="startTimeMonth"
				monthValue="<%= startCalendar.get(Calendar.MONTH) %>"
				name="startDate"
				yearParam="startTimeYear"
				yearValue="<%= startCalendar.get(Calendar.YEAR) %>"
			/>

			<liferay-ui:input-time
				amPmParam="startTimeAmPm"
				amPmValue="<%= startCalendar.get(Calendar.AM_PM) %>"
				dateParam="startDateTime"
				dateValue="<%= startCalendar.getTime() %>"
				disabled="<%= false %>"
				hourParam="startTimeHour"
				hourValue="<%= startCalendar.get(Calendar.HOUR) %>"
				minuteParam="startTimeMinute"
				minuteValue="<%= startCalendar.get(Calendar.MINUTE) %>"
				name="startTime"
			/>
		</span>
		<span class="to"><liferay-ui:message key="to" /></span>

		<span class="end-date-container" id="<portlet:namespace />endDateContainer">
			<liferay-ui:input-date
				dayParam="endTimeDay"
				dayValue="<%= endCalendar.get(Calendar.DATE) %>"
				disabled="<%= false %>"
				firstDayOfWeek="<%= endCalendar.getFirstDayOfWeek() - 1 %>"
				monthParam="endTimeMonth"
				monthValue="<%= endCalendar.get(Calendar.MONTH) %>"
				name="endDate"
				yearParam="endTimeYear"
				yearValue="<%= endCalendar.get(Calendar.YEAR) %>"
			/>

			<liferay-ui:input-time
				amPmParam="endTimeAmPm"
				amPmValue="<%= endCalendar.get(Calendar.AM_PM) %>"
				dateParam="endDateTime"
				dateValue="<%= endCalendar.getTime() %>"
				disabled="<%= false %>"
				hourParam="endTimeHour"
				hourValue="<%= endCalendar.get(Calendar.HOUR) %>"
				minuteParam="endTimeMinute"
				minuteValue="<%= endCalendar.get(Calendar.MINUTE) %>"
				name="endTime"
			/>
		</span>
	</div>

	<aui:input cssClass="meeting-description" name="description" />

	<%
	List<String> availableLanguageIds = new ArrayList<String>();

	String emailSubjectPrefix = "emailSubject_";

	Enumeration<String> enu = portletPreferences.getNames();

	while (enu.hasMoreElements()) {
		String name = enu.nextElement();

		if (name.startsWith(emailSubjectPrefix)) {
			availableLanguageIds.add(name.substring(emailSubjectPrefix.length()));
		}
	}

	String languageId = LanguageUtil.getLanguageId(request);

	if (powwowMeeting != null) {
		languageId = powwowMeeting.getLanguageId();
	}
	%>

	<c:choose>
		<c:when test="<%= availableLanguageIds.size() > 1 %>">
			<aui:select label="email-notification-template-language" name="languageId">

				<%
				for (String currentLanguageId : availableLanguageIds) {
					Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
				%>

					<aui:option label="<%= currentLocale.getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(languageId) %>" value="<%= currentLanguageId %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
		</c:otherwise>
	</c:choose>

	<div class="provider">
		<aui:select cssClass="provider-type-select" disabled="<%= powwowMeeting != null %>" label="provider" name="providerType" required="<%= true %>">

			<%
			for (String providerType : PortletPropsValues.POWWOW_PROVIDER_TYPES) {
				if (PowwowServerLocalServiceUtil.getPowwowServersCount(providerType, true) > 0) {
			%>

					<aui:option value="<%= providerType %>"><%= LanguageUtil.get(request, PowwowServiceProviderUtil.getBrandingLabel(providerType)) %></aui:option>

			<%
				}
			}
			%>

		</aui:select>

		<div class="provider-branding-features">
			<i class="icon-info-sign" id="<portlet:namespace />providerBrandingFeaturesIcon"></i>
		</div>
	</div>

	<%
	boolean requirePassword = true;

	String password = null;

	if (powwowMeetingId > 0) {
		password = PowwowServiceProviderUtil.getOptionPassword(powwowMeetingId);

		if (Validator.isNull(password)) {
			requirePassword = false;
		}
	}
	%>

	<div id="<portlet:namespace />optionPassword">
		<aui:input id="requirePassword" label="require-password" name="requirePassword" onClick='<%= renderResponse.getNamespace() + "toggleRequirePassword(this.checked);" %>' type="checkbox" value="<%= requirePassword %>" />

		<div class="password-container <%= requirePassword ? "" : "hide" %>" id="<portlet:namespace />passwordContainer">
			<aui:input class="form-control" cssClass="meeting-password" id="password" label="" name="password" type="password" value='<%= (password == null) ? "" : password %>' />
		</div>
	</div>

	<aui:input cssClass="optional-field" label="automatically-start-video" name="autoStartVideo" type="checkbox" value="<%= autoStartVideo %>" />

	<h3><liferay-ui:message key="participants" /></h3>

	<aui:fieldset cssClass="participants" id="participants">
		<aui:input name="powwowParticipantData" type="hidden" />

		<div class="row-fields">
			<aui:input autocomplete="off" cssClass="add-participant" inlineField="<%= true %>" label="" name="powwowParticipantInput" placeholder="add-participant-by-name-or-email-address" type="text" />

			<aui:input cssClass="add-participant-name hide" inlineField="<%= true %>" label="" name="powwowParticipantName" placeholder="include-a-name-for-notifying-the-user-by-email" type="text" />
		</div>

		<label><liferay-ui:message key="participant-list" /></label>

		<div class="participant-list" id="<portlet:namespace />powwowParticipantList">
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="submit" type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />toggleRequirePassword',
		function(checked) {
			var passwordContainer = AUI().one('#<portlet:namespace />passwordContainer');

			if (passwordContainer) {
				passwordContainer.toggle(checked);
			}
		},
		['aui-base']
	);
</aui:script>

<aui:script use="aui-base,aui-form-validator,aui-io-request,aui-tooltip,autocomplete-filters,autocomplete-highlighters,autocomplete-list,liferay-plugin-meeting-scheduler,liferay-plugin-meeting-util">
	new A.Tooltip(
		{
			cssClass: 'tooltip-help',
			html: true,
			opacity: 1,
			position: 'right',
			trigger: '#<portlet:namespace />providerBrandingFeaturesIcon',
			visible: false,
			zIndex: 10000
		}
	).render();

	function <portlet:namespace />loadFeatures(providerType) {
		A.all('.optional-field').get('parentNode').hide();

		A.one('#<portlet:namespace />optionPassword').hide();

		<%
		for (String providerType : PortletPropsValues.POWWOW_PROVIDER_TYPES) {
			StringBundler sb = new StringBundler();

			sb.append("<strong>");
			sb.append(LanguageUtil.get(request, "features"));
			sb.append("</strong>");
			sb.append("<ul>");

			for (String brandingFeature : PowwowServiceProviderUtil.getBrandingFeatures(providerType)) {
				sb.append("<li>");
				sb.append(UnicodeLanguageUtil.get(request, brandingFeature));
				sb.append("</li>");
			}

			sb.append("</ul>");
		%>

			if (providerType == '<%= providerType %>') {
				A.one('#<portlet:namespace />providerBrandingFeaturesIcon').attr('data-title', '<%= sb %>');

				<%
				if (PowwowServiceProviderUtil.isSupportsOptionAutoStartVideo(providerType)) {
				%>

					A.one('#<portlet:namespace />autoStartVideo').get('parentNode').show();

				<%
				}
				%>

				<%
				if (PowwowServiceProviderUtil.isSupportsOptionPassword(providerType)) {
				%>

					A.one('#<portlet:namespace />optionPassword').show();

				<%
				}
				%>

			}

		<%
		}
		%>

	}

	var selection = A.one('#<portlet:namespace />providerType');

	selection.on(
		'change',
		function() {
			var providerTypeSelected = A.one('#<portlet:namespace />providerType').val();

			<portlet:namespace />loadFeatures(providerTypeSelected);
		}
	);

	<%
	for (String providerType : PortletPropsValues.POWWOW_PROVIDER_TYPES) {
		if (PowwowServerLocalServiceUtil.getPowwowServersCount(providerType, true) > 0) {
	%>

			<portlet:namespace />loadFeatures('<%= providerType %>');

	<%
			break;
		}
	}
	%>

	new A.FormValidator(
		{
			boundingBox: '#<portlet:namespace />fm',
			rules: {
				'<portlet:namespace />password': {
					alphanum: true,
					rangeLength: [1, 10]
				}
			}
		}
	);

	new Liferay.MeetingScheduler(
		{
			containerId: 'meetingEventDate',
			endDatePickerName: 'endDate',
			endTimePickerName: 'endTime',
			namespace: '<portlet:namespace />',
			startDatePickerName: 'startDate',
			startTimePickerName: 'startTime',
			submitButtonId: 'submit'
		}
	);

	var meetingUtil = new Liferay.MeetingUtil(
		{
			baseActionURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.ACTION_PHASE) %>',
			baseResourceURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.RESOURCE_PHASE) %>',
			creatorEmailAddress: '<%= user.getEmailAddress() %>',
			creatorName: '<%= user.getFullName() %>',
			creatorUserId: <%= user.getUserId() %>,
			formName: 'fm',
			meetingId: <%= (powwowMeeting != null) ? powwowMeeting.getPowwowMeetingId() : -1 %>,
			namespace: '<portlet:namespace />',
			participantDataField: 'powwowParticipantData',
			participantInvitedSelector: '#<portlet:namespace />powwowParticipantList .participant-invited',
			participantKeywords: 'powwowParticipantInput',
			participantList: 'powwowParticipantList',
			participantName: 'powwowParticipantName',
			portletKey: '<%= portletDisplay.getId() %>',
			prefixParticipantEmailAddress: 'powwowParticipantEmailAddress',
			prefixParticipantInvited: 'powwowParticipantInvited',
			prefixParticipantName: 'powwowParticipantName',
			prefixParticipantParticipantUserId: 'powwowParticipantParticipantUserId',
			prefixParticipantType: 'powwowParticipantType',
			redirect: '<%= HtmlUtil.escape(redirect) %>',
			rowFieldsSelector: '#<portlet:namespace />participants .row-fields'
		}
	);

	<%
	for (PowwowParticipant powwowParticipant : powwowParticipants) {
		String cssClass = "user";

		if (UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), powwowParticipant.getEmailAddress()) == null) {
			cssClass = "guest";
		}
	%>

		meetingUtil.addParticipantToList('<%= powwowParticipant.getName() %>', <%= powwowParticipant.getParticipantUserId() %>, '<%= powwowParticipant.getEmailAddress() %>', '<%= cssClass %>', true, <%= powwowParticipant.getType() %>);

	<%
	}
	%>

</aui:script>