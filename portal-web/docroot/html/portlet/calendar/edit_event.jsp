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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

CalEvent event = (CalEvent)request.getAttribute(WebKeys.CALENDAR_EVENT);

long eventId = BeanParamUtil.getLong(event, request, "eventId");

String description = BeanParamUtil.getString(event, request, "description");

Calendar startDate = CalendarUtil.roundByMinutes((Calendar)selCal.clone(), 15);

if (event != null) {
	if (!event.isTimeZoneSensitive()) {
		startDate = CalendarFactoryUtil.getCalendar(TimeZoneUtil.getDefault());
	}

	startDate.setTime(event.getStartDate());
}

Calendar endDate = (Calendar)curCal.clone();

endDate.add(Calendar.YEAR, 1);

if (event != null) {
	if (!event.isTimeZoneSensitive()) {
		endDate = CalendarFactoryUtil.getCalendar();
	}

	if (event.getEndDate() != null) {
		endDate.setTime(event.getEndDate());
	}
}

endDate.set(Calendar.HOUR_OF_DAY, 23);
endDate.set(Calendar.MINUTE, 59);
endDate.set(Calendar.SECOND, 59);

int durationHour = BeanParamUtil.getInteger(event, request, "durationHour", 1);
int durationMinute = BeanParamUtil.getInteger(event, request, "durationMinute");
boolean repeating = BeanParamUtil.getBoolean(event, request, "repeating");

Recurrence recurrence = null;

int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
String recurrenceTypeParam = ParamUtil.getString(request, "recurrenceType");
if (Validator.isNull(recurrenceTypeParam) && (event != null)) {
	if (event.getRepeating()) {
		recurrence = event.getRecurrenceObj();
		recurrenceType = recurrence.getFrequency();
	}
}

int endDateType = ParamUtil.getInteger(request, "endDateType");
String endDateTypeParam = ParamUtil.getString(request, "endDateType");
if (Validator.isNull(endDateTypeParam) && (event != null)) {
	if (event.getRepeating() && (recurrence != null)) {
		if (recurrence.getUntil() != null) {
			endDateType = CalEventConstants.END_DATE_TYPE_END_BY;
		}
		else if (recurrence.getOccurrence() > 0) {
			endDateType = CalEventConstants.END_DATE_TYPE_END_AFTER;
		}
	}
}

int endDateOccurrence = ParamUtil.getInteger(request, "endDateOccurrence", 10);
String endDateOccurrenceParam = ParamUtil.getString(request, "endDateOccurrence");
if (Validator.isNull(endDateOccurrenceParam) && (event != null)) {
	if (event.getRepeating() && (recurrence != null)) {
		endDateOccurrence = recurrence.getOccurrence();
	}
}

int remindBy = BeanParamUtil.getInteger(event, request, "remindBy", CalEventConstants.REMIND_BY_EMAIL);
int firstReminder = BeanParamUtil.getInteger(event, request, "firstReminder", (int)Time.MINUTE * 15);
int secondReminder = BeanParamUtil.getInteger(event, request, "secondReminder", (int)Time.MINUTE * 5);
%>

<portlet:actionURL var="editEventURL">
	<portlet:param name="struts_action" value="/calendar/edit_event" />
</portlet:actionURL>

<aui:form action="<%= editEventURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEvent();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="eventId" type="hidden" value="<%= eventId %>" />
	<aui:input name="description" type="hidden" />

	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= (event == null) %>"
		title='<%= (event == null) ? "new-event" : event.getTitle() %>'
	/>

	<liferay-ui:error exception="<%= EventDurationException.class %>" message="please-enter-a-longer-duration" />
	<liferay-ui:error exception="<%= EventStartDateException.class %>" message="please-enter-a-valid-start-date" />
	<liferay-ui:error exception="<%= EventTitleException.class %>" message="please-enter-a-valid-title" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= event %>" model="<%= CalEvent.class %>" />

	<aui:fieldset>
		<aui:input name="startDate" value="<%= startDate %>" />

		<aui:field-wrapper label="duration">
			<aui:select cssClass="event-duration-hour" label="hours" name="durationHour">

				<%
				for (int i = 0; i <= 24 ; i++) {
				%>

					<aui:option label="<%= i %>" selected="<%= durationHour == i %>" />

				<%
				}
				%>

			</aui:select>
			<aui:select label="minutes" name="durationMinute">

				<%
				for (int i=0; i < 60 ; i = i + 5) {
				%>

					<aui:option label='<%= ":" + (i < 10 ? "0" : StringPool.BLANK) + i %>' selected="<%= durationMinute == i %>" value="<%= i %>" />

				<%
				}
				%>

			</aui:select>
		</aui:field-wrapper>

		<aui:input label="all-day-event" name="allDay" type="checkbox" value="<%= event == null ? false : event.isAllDay() %>" />

		<aui:input name="timeZoneSensitive" type="checkbox" value="<%= event == null ? true : event.isTimeZoneSensitive() %>" />

		<aui:input name="title" />

		<aui:field-wrapper label="description">
			<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />
		</aui:field-wrapper>

		<aui:input name="location" />

		<aui:select name="type">

			<%
			for (int i = 0; i < CalEventConstants.TYPES.length; i++) {
			%>

				<aui:option value="<%= HtmlUtil.escapeAttribute(CalEventConstants.TYPES[i]) %>"><%= HtmlUtil.escape(CalEventConstants.TYPES[i]) %></aui:option>

			<%
			}
			%>

		</aui:select>

		<liferay-ui:custom-attributes-available className="<%= CalEvent.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= CalEvent.class.getName() %>"
				classPK="<%= eventId %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= event == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= CalEvent.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<br />

	<liferay-ui:panel-container cssClass="calendar-event-details" extended="<%= true %>" id="calendarEventDetailsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="calendarEventCateogrizationPanel" persistState="<%= true %>" title="categorization">
			<aui:fieldset>
				<aui:input name="categories" type="assetCategories" />

				<aui:input name="tags" type="assetTags" />
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="calendarEventAssetLinksPanel" persistState="<%= true %>" title="related-assets">
			<aui:fieldset>
				<liferay-ui:input-asset-links
					className="<%= CalEvent.class.getName() %>"
					classPK="<%= eventId %>"
				/>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="calendarRepeatPanel" persistState="<%= true %>" title="repeat">
			<liferay-ui:error exception="<%= EventEndDateException.class %>" message="please-enter-a-valid-end-date" />

			<liferay-ui:input-repeat event="<%= event %>" />

			<aui:fieldset cssClass='<%= recurrenceType == Recurrence.NO_RECURRENCE ? "hide" : StringPool.BLANK %>' id="repeatUntilOptions">
				<aui:field-wrapper cssClass="end-date-field" label="repeat-until" name="endDateType">
					<aui:input checked="<%= endDateType == CalEventConstants.END_DATE_TYPE_NONE %>" cssClass="input-container" label="no-end-date" name="endDateType" type="radio" value="<%= CalEventConstants.END_DATE_TYPE_NONE %>" />

					<%--<aui:input checked="<%= endDateType == CalEventConstants.END_DATE_TYPE_END_AFTER %>" cssClass="input-container" inlineField="<%= true %>" label="end-after" name="endDateType" type="radio" value="<%= CalEventConstants.END_DATE_TYPE_END_AFTER %>" />--%>

					<%--<aui:input label="occurrence-s" maxlength="3" name="endDateOccurrence" size="3" type="text" value="<%= endDateOccurrence %>" />--%>

					<aui:input checked="<%= endDateType == CalEventConstants.END_DATE_TYPE_END_BY %>" cssClass="input-container" inlineField="<%= true %>" label="end-by" name="endDateType" type="radio" value="<%= CalEventConstants.END_DATE_TYPE_END_BY %>" />

					<aui:input inlineField="<%= true %>" label="" name="endDate" value="<%= endDate %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="calendarRemindersPanel" persistState="<%= true %>" title="reminders">
			<aui:fieldset>
				<span class="field-row">
					<aui:select inlineField="<%= true %>" inlineLabel="left" label="remind-me" name="firstReminder">

						<%
						for (int i = 0; i < CalEventConstants.REMINDERS.length; i++) {
						%>

							<aui:option selected="<%= (firstReminder == CalEventConstants.REMINDERS[i]) %>" value="<%= CalEventConstants.REMINDERS[i] %>"><%= LanguageUtil.getTimeDescription(pageContext, CalEventConstants.REMINDERS[i]) %></aui:option>

						<%
						}
						%>

					</aui:select>

					<aui:select inlineField="<%= true %>" inlineLabel="left" label="before-and-again" name="secondReminder" suffix="before-the-event-by">

						<%
						for (int i = 0; i < CalEventConstants.REMINDERS.length; i++) {
						%>

							<aui:option selected="<%= (secondReminder == CalEventConstants.REMINDERS[i]) %>" value="<%= CalEventConstants.REMINDERS[i] %>"><%= LanguageUtil.getTimeDescription(pageContext, CalEventConstants.REMINDERS[i]) %></aui:option>

						<%
						}
						%>

					</aui:select>
				</span>

				<aui:field-wrapper cssClass="reminders" label="">
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_NONE %>" label="do-not-send-a-reminder" name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_NONE %>" />
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_EMAIL %>" label='<%= LanguageUtil.get(pageContext, "email-address") + " (" + HtmlUtil.escape(user.getEmailAddress()) + ")" %>' name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_EMAIL %>" />
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_SMS %>" label='<%= LanguageUtil.get(pageContext, "sms") + (Validator.isNotNull(contact.getSmsSn()) ? " (" + HtmlUtil.escape(contact.getSmsSn()) + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_SMS %>" />
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_AIM %>" label='<%= LanguageUtil.get(pageContext, "aim") + (Validator.isNotNull(contact.getAimSn()) ? " (" + HtmlUtil.escape(contact.getAimSn()) + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_AIM %>" />
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_ICQ %>" label='<%= LanguageUtil.get(pageContext, "icq") + (Validator.isNotNull(contact.getIcqSn()) ? " (" + HtmlUtil.escape(contact.getIcqSn()) + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_ICQ %>" />
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_MSN %>" label='<%= LanguageUtil.get(pageContext, "windows-live-messenger") + (Validator.isNotNull(contact.getMsnSn()) ? " (" + HtmlUtil.escape(contact.getMsnSn()) + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_MSN %>" />
					<aui:input checked="<%= remindBy == CalEventConstants.REMIND_BY_YM %>" label='<%= LanguageUtil.get(pageContext, "yim") + (Validator.isNotNull(contact.getYmSn()) ? " (" + HtmlUtil.escape(contact.getYmSn()) + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventConstants.REMIND_BY_YM %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" value="save" />

		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />getDescription() {
		return window.<portlet:namespace />editor.getHTML();
	}

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(description) %>";
	}

	function <portlet:namespace />saveEvent() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (event == null) ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />description.value = <portlet:namespace />getDescription();
		submitForm(document.<portlet:namespace />fm);
	}

	<%-- LEP-6018 --%>

	document.<portlet:namespace />fm.<portlet:namespace />endDateHour.disabled = true;
	document.<portlet:namespace />fm.<portlet:namespace />endDateMinute.disabled = true;
	document.<portlet:namespace />fm.<portlet:namespace />endDateAmPm.disabled = true;
</aui:script>

<aui:script use="aui-base">
	var allDayCheckbox = A.one('#<portlet:namespace />allDayCheckbox');

	var durationHour = A.one('#<portlet:namespace />durationHour');
	var durationMinute = A.one('#<portlet:namespace />durationMinute');

	var timeZoneSensitiveCheckbox = A.one('#<portlet:namespace />timeZoneSensitiveCheckbox');

	var startDateHour = document.<portlet:namespace />fm.<portlet:namespace />startDateHour;
	var startDateMinute = document.<portlet:namespace />fm.<portlet:namespace />startDateMinute;
	var startDateAmPm = document.<portlet:namespace />fm.<portlet:namespace />startDateAmPm;

	allDayCheckbox.on(
		'change',
		function() {
			var allDayChecked = allDayCheckbox.attr('checked');

			if (!allDayChecked && durationHour && (durationHour.val() === '24')) {
				durationHour.val('1');
			}

			if (allDayChecked) {
				durationHour.val('24');
				durationMinute.val('0');

				durationHour.attr('disabled', true);
				durationMinute.attr('disabled', true);

				timeZoneSensitiveCheckbox.attr('checked', false);
				timeZoneSensitiveCheckbox.attr('disabled', true);

				startDateHour.disabled = true;
				startDateMinute.disabled = true;
				startDateAmPm.disabled = true;
			}
			else {
				durationHour.attr('disabled', false);
				durationMinute.attr('disabled', false);

				timeZoneSensitiveCheckbox.attr('disabled', false);

				if (timeZoneSensitiveCheckbox.previous().val() === 'true') {
					timeZoneSensitiveCheckbox.attr('checked', true);
				}

				startDateHour.disabled = false;
				startDateMinute.disabled = false;
				startDateAmPm.disabled = false;
			}
		}
	);

	A.all('#<portlet:namespace />recurrenceTypeNever, #<portlet:namespace />recurrenceTypeDaily, #<portlet:namespace />recurrenceTypeWeekly, #<portlet:namespace />recurrenceTypeMonthly, #<portlet:namespace />recurrenceTypeYearly').on(
		'change',
		function(event) {
			var repeatUntilOptions = A.one('#<portlet:namespace />repeatUntilOptions');

			if (repeatUntilOptions) {
				var currentTarget = event.currentTarget;

				var showOptions = (currentTarget.attr('checked') && currentTarget.attr('id') != '<portlet:namespace />recurrenceTypeNever');

				repeatUntilOptions.toggle(showOptions);
			}
		}
	);
</aui:script>

<%
if (event != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/calendar/view_event");
	portletURL.setParameter("redirect", currentURL);
	portletURL.setParameter("eventId", String.valueOf(event.getEventId()));

	PortalUtil.addPortletBreadcrumbEntry(request, event.getTitle(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-event"), currentURL);
}
%>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.calendar.edit_event.jsp";
%>